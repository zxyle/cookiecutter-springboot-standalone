# 安装包回滚
# pip3 install inquirer -i https://mirrors.aliyun.com/pypi/simple/

import glob
import os
from os.path import basename

import inquirer

BACKUP_DIR = "./backup"


def scan_jars():
    return [basename(jar) for jar in glob.glob(f"{BACKUP_DIR}/*")]


def main():
    jars = scan_jars()
    questions = [
        inquirer.List('version', message="你要回滚那次备份?", choices=jars),
        inquirer.Confirm('goon', message="确定回滚该版本吗?", default=False),
    ]

    answers = inquirer.prompt(questions)

    jar = answers.get("version")
    fd = f"{BACKUP_DIR}/{jar}"
    if os.path.exists(fd):
        filename, t, comment = jar.rsplit(".", 2)
        os.system(f"cp {fd} {filename}")
        print(f"已回滚到 {jar}.")
        os.remove(fd)
    else:
        print(f"文件: {fd} 不存在.")


if __name__ == '__main__':
    main()
