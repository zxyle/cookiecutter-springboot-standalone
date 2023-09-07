# Makefile

# 定义变量, 分别是服务器地址（需要配置免密登陆）、项目部署目录、后端项目目录、前端项目目录、后端jar包名称
SERVER = root@127.0.0.1
BASEDIR = /opt/webapps/{{ cookiecutter.artifact.replace('-api', '') }}
BACKEND_DIR = $(BASEDIR)/{{ cookiecutter.artifact }}
FRONTEND_DIR = $(BASEDIR)/{{ cookiecutter.artifact.replace('-api', '-web') }}
JAR = {{ cookiecutter.artifact }}-{{ cookiecutter.version }}.jar

LC_CTYPE := en_US.UTF-8

REDIS_RPM = redis-7.0.12-1.el7.remi.x86_64.rpm
JDK_RPM = jdk-8u202-linux-x64.rpm
# 默认目标
deploy: publish

# 拉取最新代码并打包
package:
	# git pull
	mvn clean package -Dmaven.test.skip=true

# 重启服务
restart:
	ssh $(SERVER) "systemctl restart {{ cookiecutter.artifact }}"
	# 显示运行日志
	ssh $(SERVER) "tail -200f $(BACKEND_DIR)/logs/log_info.log"

# 备份jar包到backup目录
backup:
	ssh $(SERVER) "mkdir -p $(BACKEND_DIR)/backup"
	ssh $(SERVER) "cp $(BACKEND_DIR)/$(JAR) $(BACKEND_DIR)/backup/backup.jar"

# 当发现部署失败时，可以回滚到上一个版本
rollback:
	ssh $(SERVER) "mv $(BACKEND_DIR)/backup/backup.jar $(BACKEND_DIR)/$(JAR)"
	restart

# 发布
publish: package backup
	# 将lib目录下的依赖jar包同步到服务器
	rsync -avz --progress --delete ./target/lib/ $(SERVER):$(BACKEND_DIR)/lib
	scp -rC ./target/*.jar $(SERVER):$(BACKEND_DIR)
	restart
	clean
	@echo "Deploy success!"

# 初始化前后端运行环境
init: package
	ssh $(SERVER) "mkdir -p $(BACKEND_DIR)"
	rsync -avz --progress --delete ./target/lib/ $(SERVER):$(BACKEND_DIR)/lib
	scp -rvC ./target/*.jar $(SERVER):$(BACKEND_DIR)
	scp -rvC ./deploy/* $(SERVER):$(BACKEND_DIR)
	scp -rvC ./src/main/resources/application.properties $(SERVER):$(BACKEND_DIR)/config
	scp -rvC ./src/main/resources/application-dev.properties $(SERVER):$(BACKEND_DIR)/config
	scp -rvC ./src/main/resources/application-prod.properties $(SERVER):$(BACKEND_DIR)/config
	scp -rvC ./src/main/resources/logback-spring.xml $(SERVER):$(BACKEND_DIR)/config
	scp -rvC ./target/lib $(SERVER):$(BACKEND_DIR)
	ssh $(SERVER) "rm -rf $(BACKEND_DIR)/deploy.sh $(BACKEND_DIR)/Makefile"
	ssh $(SERVER) "chmod +x $(BACKEND_DIR)/restart.sh"
	ssh $(SERVER) "mv $(BACKEND_DIR)/*.service /etc/systemd/system"
	ssh $(SERVER) "systemctl daemon-reload"
	ssh $(SERVER) "systemctl enable demo-api --now"

	# 前端配置
	ssh $(SERVER) "mkdir -p $(FRONTEND_DIR)"
	ssh $(SERVER) "mv $(BACKEND_DIR)/index.html $(FRONTEND_DIR)/"

	# Nginx配置 (如果没有安装Nginx，需要先安装)
	ssh $(SERVER) "mv $(BACKEND_DIR)/*.conf /etc/nginx/conf.d/"
	ssh $(SERVER) "systemctl enable nginx --now"
	ssh $(SERVER) "nginx -t"
	ssh $(SERVER) "nginx -s reload"

# 安装JDK
jdk:
	scp ~/developer/software/jdk/$(JDK_RPM) $(SERVER):$(BACKEND_DIR)
	ssh $(SERVER) "rpm -ivh $(BACKEND_DIR)/$(JDK_RPM)"
	ssh $(SERVER) "rm -rf $(BACKEND_DIR)/$(JDK_RPM)"
	ssh $(SERVER) "java -version"
	ssh $(SERVER) "javac -version"

# 安装redis
redis:
	scp ~/developer/software/redis/$(REDIS_RPM) $(SERVER):$(BACKEND_DIR)
	ssh $(SERVER) "rpm -ivh $(BACKEND_DIR)/$(REDIS_RPM)"
	ssh $(SERVER) "rm -rf $(BACKEND_DIR)/$(REDIS_RPM)"
	ssh $(SERVER) "redis-server --version"
	ssh $(SERVER) "systemctl enable redis --now"

# 安装nginx
nginx:
	ssh $(SERVER) "yum install -y epel-release"
	ssh $(SERVER) "yum install -y nginx"
	ssh $(SERVER) "systemctl enable nginx --now"
	ssh $(SERVER) "nginx -t"
	ssh $(SERVER) "nginx -s reload"

# 使用说明
help:
	@echo "Usage: make [package|publish|restart|rollback|backup|init|deploy|clean|jdk|redis|nginx]"

# 清理
clean:
	@find . -type d -name "target" -exec rm -r {} +
	@find . -type d -name "logs" -exec rm -r {} +
	@echo "Clean success!"

.PHONY: package restart backup rollback publish init help clean jdk redis nginx
