# Makefile

# 定义变量, 分别是服务器地址（需要配置免密登陆）、项目部署目录、后端部署目录、前端部署目录、后端jar包名称
# 配置免密登陆: ssh-copy-id -i ~/.ssh/id_rsa.pub root@127.0.0.1
SERVER = root@127.0.0.1
APP_DIR = /opt/webapps/{{ cookiecutter.artifact.replace('-api', '') }}
BACKEND_DIR = $(APP_DIR)/{{ cookiecutter.artifact }}
FRONTEND_DIR = $(APP_DIR)/{{ cookiecutter.artifact.replace('-api', '-web') }}
JAR = {{ cookiecutter.artifact }}-{{ cookiecutter.version }}.jar

LC_CTYPE := en_US.UTF-8

DOWNLOAD_DIR = ~/developer/software
REDIS_RPM = redis-7.0.12-1.el7.remi.x86_64.rpm
REDIS_TARGET_FILE = $(DOWNLOAD_DIR)/redis/$(REDIS_RPM)
REDIS_REMOTE_URL = https://mirrors.tuna.tsinghua.edu.cn/remi/enterprise/7/remi/x86_64/$(REDIS_RPM)

JDK_RPM = jdk-8u202-linux-x64.rpm
JDK_TARGET_FILE = $(DOWNLOAD_DIR)/jdk/$(JDK_RPM)
JDK_REMOTE_URL = https://repo.huaweicloud.com/java/jdk/8u202-b08/$(JDK_RPM)

MYSQL_VERSION = 8.0.28-1.el7.x86_64
MYSQL_RPM = mysql-$(MYSQL_VERSION).rpm-bundle.tar
MYSQL_TARGET_FILE = $(DOWNLOAD_DIR)/mysql/$(MYSQL_RPM)
MYSQL_REMOTE_URL = https://mirrors.aliyun.com/mysql/MySQL-8.0/$(MYSQL_RPM)


# 默认目标
deploy: publish

# 拉取最新代码后构建及上传api文档
package:
	# git pull
	# mvn clean package -Dmaven.test.skip=true
	mvn -Dfile.encoding=UTF-8 smart-doc:torna-rest -B clean package -Dmaven.test.skip=true -Dautoconfig.skip

# 重启服务
restart:
	ssh $(SERVER) "systemctl restart {{ cookiecutter.artifact }}"
	# 显示运行日志
	ssh $(SERVER) "tail -200f $(BACKEND_DIR)/logs/app.log"

# 备份jar包到backup目录
backup:
	ssh $(SERVER) "mkdir -p $(BACKEND_DIR)/backup"
	ssh $(SERVER) "cp $(BACKEND_DIR)/$(JAR) $(BACKEND_DIR)/backup/backup.jar"

# 当出现部署失败时，可以回滚到上一个版本
rollback:
	ssh $(SERVER) "mv $(BACKEND_DIR)/backup/backup.jar $(BACKEND_DIR)/$(JAR)"
	$(MAKE) restart

# 部署（打包 -> 备份 -> 同步lib包 -> 上传jar包 -> 重启服务 -> 清理）
publish: package backup
	# 将lib目录下的依赖jar包同步到服务器
	rsync -avz --progress --delete ./target/lib/ $(SERVER):$(BACKEND_DIR)/lib
	scp -rC ./target/*.jar $(SERVER):$(BACKEND_DIR)
	$(MAKE) restart
	$(MAKE) clean
	@echo "已成功部署部署"

# 初始化前后端运行环境
# 服务器需要安装rsync, yum install rsync -y
init: package
	ssh $(SERVER) "mkdir -p $(BACKEND_DIR)"
	rsync -avz --progress --delete ./target/lib/ $(SERVER):$(BACKEND_DIR)/lib
	scp -rvC ./target/*.jar $(SERVER):$(BACKEND_DIR)
	scp -rvC ./deploy/* $(SERVER):$(BACKEND_DIR)
	scp -rvC ./target/lib $(SERVER):$(BACKEND_DIR)
	ssh $(SERVER) "rm -rf $(BACKEND_DIR)/deploy.sh $(BACKEND_DIR)/Makefile"
	ssh $(SERVER) "chmod +x $(BACKEND_DIR)/restart.sh"
	ssh $(SERVER) "mv $(BACKEND_DIR)/*.service /etc/systemd/system"
	ssh $(SERVER) "systemctl daemon-reload"
	ssh $(SERVER) "systemctl enable {{ cookiecutter.artifact }} --now"

	# 前端配置一个测试页面
	ssh $(SERVER) "mkdir -p $(FRONTEND_DIR)"
	ssh $(SERVER) "mv $(BACKEND_DIR)/index.html $(FRONTEND_DIR)/"

	# 删除多余文件
	ssh $(SERVER) "rm -rf $(BACKEND_DIR)/Caddyfile $(BACKEND_DIR)/*.conf"

# 安装JDK
jdk:
	@if [ ! -f $(JDK_TARGET_FILE) ]; then \
		echo "Downloading $(JDK_TARGET_FILE) from $(JDK_REMOTE_URL)"; \
		mkdir -p $(DOWNLOAD_DIR)/jdk; \
		wget -O $(JDK_TARGET_FILE) $(JDK_REMOTE_URL); \
	else \
		echo "$(JDK_TARGET_FILE) already exists."; \
	fi
	scp $(DOWNLOAD_DIR)/jdk/$(JDK_RPM) $(SERVER):/tmp
	ssh $(SERVER) "rpm -ivh /tmp/$(JDK_RPM)"
	ssh $(SERVER) "rm -rf /tmp/$(JDK_RPM)"
	ssh $(SERVER) "java -version"
	ssh $(SERVER) "javac -version"

# 安装redis
redis:
	@if [ ! -f $(REDIS_TARGET_FILE) ]; then \
		echo "Downloading $(REDIS_TARGET_FILE) from $(REDIS_REMOTE_URL)"; \
		mkdir -p $(DOWNLOAD_DIR)/redis; \
		wget -O $(REDIS_TARGET_FILE) $(REDIS_REMOTE_URL); \
	else \
		echo "$(REDIS_TARGET_FILE) already exists."; \
	fi
	scp $(REDIS_TARGET_FILE) $(SERVER):/tmp
	ssh $(SERVER) "rpm -ivh /tmp/$(REDIS_RPM)"
	ssh $(SERVER) "rm -rf /tmp/$(REDIS_RPM)"
	ssh $(SERVER) "redis-server --version"
	ssh $(SERVER) "echo 'requirepass RGCqg&fju#Q6xypF' >> /etc/redis.conf"
	ssh $(SERVER) "echo 'bind 0.0.0.0' >> /etc/redis.conf"
	ssh $(SERVER) "systemctl enable redis --now"

# 安装mysql
mysql:
	@if [ ! -f $(MYSQL_TARGET_FILE) ]; then \
		echo "Downloading $(MYSQL_TARGET_FILE) from $(MYSQL_REMOTE_URL)"; \
		mkdir -p $(DOWNLOAD_DIR)/mysql; \
		wget -O $(MYSQL_TARGET_FILE) $(MYSQL_REMOTE_URL); \
	else \
		echo "$(MYSQL_TARGET_FILE) already exists."; \
	fi
	scp $(MYSQL_TARGET_FILE) $(SERVER):/tmp
	ssh $(SERVER) "yum install libaio* -y"
	ssh $(SERVER) "tar -xvf /tmp/$(MYSQL_RPM) -C /tmp"
	ssh $(SERVER) "rpm -ivh /tmp/mysql-community-client-plugins-$(MYSQL_VERSION).rpm --force --nodeps"
	ssh $(SERVER) "rpm -ivh /tmp/mysql-community-devel-$(MYSQL_VERSION).rpm --force --nodeps"
	ssh $(SERVER) "rpm -ivh /tmp/mysql-community-common-$(MYSQL_VERSION).rpm --force --nodeps"
	ssh $(SERVER) "rpm -ivh /tmp/mysql-community-libs-$(MYSQL_VERSION).rpm --force --nodeps"
	ssh $(SERVER) "rpm -ivh /tmp/mysql-community-libs-compat-$(MYSQL_VERSION).rpm --force --nodeps"
	ssh $(SERVER) "rpm -ivh /tmp/mysql-community-client-$(MYSQL_VERSION).rpm --force --nodeps"
	ssh $(SERVER) "rpm -ivh /tmp/mysql-community-server-$(MYSQL_VERSION).rpm --force --nodeps"
	ssh $(SERVER) "rm -rf /tmp/mysql-*"
	ssh $(SERVER) "systemctl enable mysqld --now"
	ssh $(SERVER) "grep 'password' /var/log/mysqld.log"
	# 手动登录mysql执行以下sql
	# mysql -uroot -p 输入上面的密码
	# ALTER USER 'root'@'localhost' IDENTIFIED BY 'Hr!CyEH22akHghgLk7';
	# use mysql;
	# UPDATE mysql.user SET host = '%' WHERE user = 'root';
	# GRANT ALL PRIVILEGES ON *.* TO 'root'@'%';
	# FLUSH PRIVILEGES;
	# quit;

# 安装nginx
nginx:
	ssh $(SERVER) "yum install -y epel-release"
	ssh $(SERVER) "yum install -y nginx"
	scp -rvC ./deploy/*.conf $(SERVER):/etc/nginx/conf.d/
	ssh $(SERVER) "systemctl enable nginx --now"
	ssh $(SERVER) "nginx -t"
	ssh $(SERVER) "nginx -s reload"

# 安装caddy
caddy:
	ssh $(SERVER) "yum install yum-plugin-copr -y"
	ssh $(SERVER) "yum copr enable @caddy/caddy -y"
	ssh $(SERVER) "yum install caddy -y"
	ssh $(SERVER) "mkdir -p /var/log/caddy"
	ssh $(SERVER) "chown caddy:caddy /var/log/caddy"
	ssh $(SERVER) "chmod 755 /var/log/caddy"
	scp -rvC ./deploy/Caddyfile $(SERVER):/etc/caddy/
	ssh $(SERVER) "cd /etc/caddy && caddy fmt --overwrite"
	ssh $(SERVER) "systemctl enable caddy --now"

# 使用docker-compose运行应用
docker: package
	docker-compose up -d

# 快速查看日志
log:
	ssh $(SERVER) "tail -500f $(BACKEND_DIR)/logs/app.log"
	#ssh $(SERVER) "tail -500f /var/log/nginx/access.log"
	#ssh $(SERVER) "tail -500f /var/log/nginx/error.log"
	#ssh $(SERVER) "tail -500f /var/log/caddy/access.log"

# 使用说明
help:
	@echo "Usage: make [package|publish|restart|rollback|backup|init|deploy|clean|jdk|redis|nginx|caddy|mysql|docker|log|ssh]"

# 清理
clean:
	@find . -type d -name "target" -exec rm -r {} +
	@find . -type d -name "logs" -exec rm -r {} +
	@echo "Clean success!"

# ssh 连接后端服务器部署目录
ssh:
	ssh $(SERVER) -t "cd $(BACKEND_DIR); bash"

.PHONY: package publish restart rollback backup init deploy clean jdk redis nginx caddy mysql help docker log ssh
