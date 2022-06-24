#!/usr/bin/env bash

# 前端部署命令
bash deploy.sh publish

# 部署服务器
server=root@127.0.0.1
# 部署目录
dir=/opt/webapps/{{ cookiecutter.artifact.replace('-api', '') }}/{{ cookiecutter.artifact.replace('-api', '-web') }}
# 编译后生成产物目录
target=dist

function build() {
    yarn build
}

function publish() {
    build
    ssh $server "rm -rf $dir/*"
    scp -rvC $target/* $server:$dir
}

function usage() {
    echo "Usage: sh deploy.sh [build|publish]"
    exit 0
}


#根据输入参数，选择执行对应方法，不输入则执行使用说明
case "$1" in
  "build")
    build
    ;;
  "publish")
    publish
    ;;
  *)
    usage
    ;;
esac
