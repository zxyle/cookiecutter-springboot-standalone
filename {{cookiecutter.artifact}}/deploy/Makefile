# Makefile

# 前端部署命令
# make publish

# 定义变量
# 配置免密登录 ssh-copy-id -i ~/.ssh/id_rsa.pub root@localhost
SERVER = root@127.0.0.1
DIR = /opt/webapps/{{ cookiecutter.artifact.replace('-api', '') }}/{{ cookiecutter.artifact.replace('-api', '-web') }}
TARGET = dist

# 默认目标
deploy: publish

# 编译前端代码
build:
	pnpm build

# 发布前端代码到服务器
publish: build
	ssh $(SERVER) "rm -rf $(DIR)/*"
	scp -rvC $(TARGET)/* $(SERVER):$(DIR)

# 使用说明
help:
	@echo "Usage: make [build|publish]"

.PHONY: build publish help
