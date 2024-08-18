#!/usr/bin/env bash

# 手动启动

java -server -Dspring.profiles.active=dev -jar -Dloader.path=../lib ../{{ cookiecutter.artifact }}-{{ cookiecutter.version }}.jar
