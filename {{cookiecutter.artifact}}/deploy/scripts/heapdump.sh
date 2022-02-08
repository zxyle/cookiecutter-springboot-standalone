#!/usr/bin/env bash

# 1 现场保存
pid=$(ps -ef | grep "{{ cookiecutter.app_name }}Application" | grep -v grep | awk '{print $2}')
echo $pid

# 第二步：执行以下脚本（将内存、cpu以及网络环境生成镜像）
jmap -histo:live ${pid} > histo.snap
jmap -heap ${pid} > heap.snap
jmap -dump:live,format=b,file=dump.snap ${pid}
jstack ${pid}> jstack.snap

# 第三步: 如何使用
