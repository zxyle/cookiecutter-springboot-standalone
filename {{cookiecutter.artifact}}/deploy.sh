#!/usr/bin/env bash

# 部署服务器（需要配置免密登陆）
server=root@127.0.0.1
# 部署目录
dir=/opt/webapps/{{ cookiecutter.artifact.replace('-api', '') }}/{{ cookiecutter.artifact }}/
jar={{ cookiecutter.artifact }}-{{ cookiecutter.version }}.jar

# 拉取代码并打包
function package() {
    git pull
    mvn clean package -Dmaven.test.skip=true
}

# 重启服务
function restart() {
    ssh $server "systemctl restart security-api"
    exit 0
}

# 备份jar包到backup目录
function backup() {
    ssh $server "mkdir -p $dir/backup"
    ssh $server "cp $dir/$jar $dir/backup/backup.jar"
}

# 当发现部署失败时，可以回滚到上一个版本
function rollback() {
    ssh $server "mv $dir/backup/backup.jar $dir/$jar"
    restart
}

# 发布
function publish() {
    package
    backup
    # 将lib目录下的依赖jar包同步到服务器
    rsync -avz --progress --delete ./target/lib/ $server:$dir/lib
    scp -rvC ./target/*.jar $server:$dir
    restart
}

# 使用说明，用来提示输入参数
function usage() {
    echo "Usage: sh deploy.sh [package|publish|restart|rollback|backup]"
    exit 0
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
  *)
    usage
    ;;
esac
