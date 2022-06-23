# 安装包备份
# pip3 install inquirer -i https://mirrors.aliyun.com/pypi/simple/


import glob
import os
from datetime import datetime
from os.path import basename

import inquirer

BACKUP_DIR = "./backup"


def scan_local_jars():
    return [basename(jar) for jar in glob.glob(f"*.jar")]


def main():
    jars = scan_local_jars()
    if not jars:
        print("没有可备份jar包.")
        return

    if len(jars) > 1:
        questions = [
            inquirer.List('jar', message="要备份那个jar包?", choices=jars),
        ]
        answers = inquirer.prompt(questions)
        jar = answers.get("jar")
    else:
        jar = jars[0]

    comment = input("请输入该版本备注: ")
    now = datetime.now().strftime("%Y-%m-%d@%H:%M:%S")
    suffix = f".{now}.{comment}"

    if not os.path.exists(BACKUP_DIR):
        os.mkdir(BACKUP_DIR)

    target_dest = f"{BACKUP_DIR}/{jar}{suffix}"
    os.system(f"cp {jar} {target_dest}")

    print(f"已成功备份到： {target_dest}")


if __name__ == '__main__':
    main()
