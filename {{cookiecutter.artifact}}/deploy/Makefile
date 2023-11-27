# Makefile

# 前端部署命令
# make publish

# 定义变量
# 配置免密登录 ssh-copy-id -i ~/.ssh/id_rsa.pub root@localhost
SERVER = root@127.0.0.1
BASEDIR = /opt/webapps/{{ cookiecutter.artifact.replace('-api', '') }}
DIR = $(BASEDIR)/{{ cookiecutter.artifact.replace('-api', '-web') }}
TARGET = dist

# 默认目标
deploy: publish

# 编译前端代码
build:
	pnpm build

# 备份前端代码
backup:
	ssh $(SERVER) "tar -zcvf $(BASEDIR)/backup.tgz -C ${DIR}/ ."

# 回滚上一版本
rollback:
	ssh $(SERVER) "rm -rf ${DIR}/*"
	ssh $(SERVER) "tar -xzvf $(BASEDIR)/backup.tgz -C ${DIR}/"
	ssh $(SERVER) "rm -rf $(BASEDIR)/backup.tgz"

# 发布前端代码到服务器
publish: build backup
	ssh $(SERVER) "rm -rf $(DIR)/*"
	scp -rvC $(TARGET)/* $(SERVER):$(DIR)

# 使用说明
help:
	@echo "Usage: make [build|publish|backup|rollback]"

.PHONY: build publish backup rollback help
