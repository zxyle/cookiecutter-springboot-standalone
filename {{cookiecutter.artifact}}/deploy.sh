#!/usr/bin/env bash

# 配合阿里云 云效流水线部署使用

# 备份之前jar包 到备份目录内
cp /opt/webapps/{{ cookiecutter.artifact.replace('-api', '') }}/{{ cookiecutter.artifact }}/{{ cookiecutter.artifact }}-{{ cookiecutter.version }}.jar /opt/webapps/{{ cookiecutter.artifact.replace('-api', '') }}/{{ cookiecutter.artifact }}/backup/backup.jar
echo "备份之前jar包 到备份目录内完成."

# 复制新jar包
cp /home/admin/application/target/{{ cookiecutter.artifact }}-{{ cookiecutter.version }}.jar /opt/webapps/{{ cookiecutter.artifact.replace('-api', '') }}/{{ cookiecutter.artifact }}/{{ cookiecutter.artifact }}-{{ cookiecutter.version }}.jar
cp -r /home/admin/application/target/lib/* /opt/webapps/{{ cookiecutter.artifact.replace('-api', '') }}/{{ cookiecutter.artifact }}/lib/
echo "复制新jar包完成."

# 重启
systemctl restart {{ cookiecutter.artifact }}
echo "{{ cookiecutter.artifact }}服务重启完成."

# 删除制品
rm -rf /home/admin/application
rm -rf /home/admin/app/package.tgz
echo "删除制品完成."