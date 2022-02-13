#!/usr/bin/env bash

# 第1步: 现场保存
pid=$(ps -ef | grep "{{ cookiecutter.artifact }}" | grep -v grep | awk '{print $2}')
echo $pid

# 第2步：执行以下脚本（将内存、cpu以及网络环境生成镜像）
jmap -histo:live ${pid} > histo.snap
jmap -heap ${pid} > heap.snap
jmap -dump:live,format=b,file=dump.snap ${pid}
jstack ${pid}> jstack.snap

# 第3步: 如何使用
