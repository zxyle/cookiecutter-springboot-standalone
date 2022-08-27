#!/usr/bin/env bash

# 执行前需要加可执行权限: chmod +x restart.sh
systemctl restart {{ cookiecutter.artifact }}
echo "{{ cookiecutter.artifact }}服务重启完成."