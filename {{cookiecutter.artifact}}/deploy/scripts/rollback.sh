#!/usr/bin/env bash

mv ../backup/backup.jar ../{{ cookiecutter.artifact }}-{{ cookiecutter.version }}.jar
systemctl restart {{ cookiecutter.artifact }}
echo "{{ cookiecutter.artifact }}服务重启完成."

tail -200f ../logs/app.log