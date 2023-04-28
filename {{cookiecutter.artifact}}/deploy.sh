#!/usr/bin/env bash

# 部署服务器（需要配置免密登陆）
server=root@127.0.0.1
# 部署目录
basedir=/opt/webapps/{{ cookiecutter.artifact.replace('-api', '') }}
backend_dir=$basedir/{{ cookiecutter.artifact }}/
frontend_dir=$basedir/{{ cookiecutter.artifact.replace('-api', '-web') }}/
jar={{ cookiecutter.artifact }}-{{ cookiecutter.version }}.jar

# 拉取最新代码并打包
function package() {
    git pull
    mvn clean package -Dmaven.test.skip=true
}

# 重启服务
function restart() {
    ssh $server "systemctl restart {{ cookiecutter.artifact }}"
    # 显示日志
    ssh $server "tail -200f $backend_dir/logs/log_info.log"
}

# 备份jar包到backup目录
function backup() {
    ssh $server "mkdir -p $backend_dir/backup"
    ssh $server "cp $backend_dir/$jar $backend_dir/backup/backup.jar"
}

# 当发现部署失败时，可以回滚到上一个版本
function rollback() {
    ssh $server "mv $backend_dir/backup/backup.jar $backend_dir/$jar"
    restart
}

# 发布
function publish() {
    package
    backup
    # 将lib目录下的依赖jar包同步到服务器
    rsync -avz --progress --delete ./target/lib/ $server:$backend_dir/lib
    scp -rC ./target/*.jar $server:$backend_dir
    restart
}

# 使用说明，用来提示输入参数
function usage() {
    echo "Usage: sh deploy.sh [package|publish|restart|rollback|backup|init]"
    exit 0
}

# 初始化前后端运行环境
function init() {
    package
    ssh $server "mkdir -p $backend_dir"
    rsync -avz --progress --delete ./target/lib/ $server:$backend_dir/lib
    scp -rvC ./target/*.jar $server:$backend_dir
    scp -rvC ./deploy/* $server:$backend_dir
    scp -rvC ./src/main/resources/application.properties $server:$backend_dir/config
    scp -rvC ./src/main/resources/application-dev.properties $server:$backend_dir/config
    scp -rvC ./src/main/resources/application-prod.properties $server:$backend_dir/config
    scp -rvC ./src/main/resources/logback-spring.xml $server:$backend_dir/config
    scp -rvC ./target/lib $server:$backend_dir
    ssh $server "mv $backend_dir/*.service /etc/systemd/system"
    ssh $server "systemctl daemon-reload"
    ssh $server "systemctl enable {{ cookiecutter.artifact }} --now"

    # 前端配置
    ssh $server "mkdir -p $frontend_dir"
    ssh $server "mv $backend_dir/index.html $frontend_dir/"

    # Nginx配置
    ssh $server "systemctl enable nginx --now"
    ssh $server "mv $backend_dir/*.conf /etc/nginx/conf.d/"
    ssh $server "nginx -t"
    ssh $server "nginx -s reload"
    # TODO 80端口冲突，需要删除nginx.conf中的80端口配置
}


#根据输入参数，选择执行对应方法，不输入则执行使用说明
case "$1" in
  "package")
    package
    ;;
  "publish")
    publish
    ;;
  "backup")
    backup
    ;;
  "rollback")
    rollback
    ;;
  "init")
    init
    ;;
  *)
    usage
    ;;
esac
