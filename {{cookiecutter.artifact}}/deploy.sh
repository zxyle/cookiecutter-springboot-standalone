#!/usr/bin/env bash

# 部署服务器（需要配置免密登陆）
server=root@127.0.0.1
# 部署目录
dir=/opt/webapps/{{ cookiecutter.artifact.replace('-api', '') }}/{{ cookiecutter.artifact }}/

function package() {
    mvn clean package -Dmaven.test.skip=true
}

function restart() {
    ssh $server "systemctl restart {{ cookiecutter.artifact }}"
}

function publish() {
    package
    # TODO 增加jar包备份
    scp -rvC $target/*.jar $server:$dir
    restart
}


function usage() {
    echo "Usage: sh deploy.sh [package|publish]"
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
  *)
    usage
    ;;
esac
