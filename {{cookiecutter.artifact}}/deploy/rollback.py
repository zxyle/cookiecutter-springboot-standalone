# 安装包回滚
# pip install inquirer

import os
import glob
import inquirer
from os.path import basename

BACKUP_DIR = "./backup"


def scan_jars():
    return [basename(jar) for jar in glob.glob(f"{BACKUP_DIR}/*")]


def main():
    jars = scan_jars()
    questions = [
        inquirer.List('version',
                      message="你要回滚那次备份?",
                      choices=jars,
                      ),
        inquirer.Confirm('goon',
                         message="确定回滚该版本吗?", default=False),
    ]

    # 扫描备份文件夹 按日期倒序输出
    answers = inquirer.prompt(questions)

    jar = answers.get("version")
    fd = f"{BACKUP_DIR}/{jar}"
    if os.path.exists(fd):
        filename, t, comment = jar.rsplit(".", 2)
        os.system(f"cp {fd} {filename}")
        print("回滚成功.")
        os.remove(fd)
    else:
        print(f"文件: {fd} 不存在.")


if __name__ == '__main__':
    main()
