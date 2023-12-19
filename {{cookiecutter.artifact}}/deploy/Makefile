# 前端部署工具

# 使用步骤:
#   1. 配置免密登录 ssh-copy-id -i ~/.ssh/id_rsa.pub root@[替换成目标服务器IP]
#   2. 以后部署使用 make publish
#   3. 回滚上一版本 make rollback


# 定义变量
SERVER = root@localhost
APP_DIR = /opt/webapps/{{ cookiecutter.artifact.replace('-api', '') }}
DIR = $(APP_DIR)/{{ cookiecutter.artifact.replace('-api', '-web') }}
TARGET = dist

# 默认目标
deploy: publish

# 编译前端代码
build:
	# yarn build
	pnpm build

# 备份前端代码
backup:
	ssh $(SERVER) "tar -zcvf $(BASEDIR)/backup.tgz -C ${DIR}/ ."

# 回滚上一版本
rollback:
	ssh $(SERVER) "rm -rf ${DIR}/*"
	ssh $(SERVER) "tar -xzvf $(BASEDIR)/backup.tgz -C ${DIR}/"
	ssh $(SERVER) "rm -rf $(BASEDIR)/backup.tgz"

# 编译打包前端代码到发布到服务器
publish: build backup
	ssh $(SERVER) "rm -rf $(DIR)/*"
	scp -rvC $(TARGET)/* $(SERVER):$(DIR)

# 使用说明
help:
	@echo "Usage: make [build|publish|backup|rollback]"

.PHONY: build publish backup rollback help
