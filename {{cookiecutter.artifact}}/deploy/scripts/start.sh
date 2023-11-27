#!/usr/bin/env bash

# 手动启动

java -server -Dspring.profiles.active=prod -jar -Dloader.path=../lib ../{{ cookiecutter.artifact }}-{{ cookiecutter.version }}.jar
