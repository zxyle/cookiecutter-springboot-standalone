import os
from typing import List


def remove_files(files: List[str]):
    for file_name in files:
        if os.path.exists(file_name):
            os.remove(file_name)


def remove_open_source_files():
    file_names = ["LICENSE"]
    remove_files(file_names)


def remove_build_tool(tool: str):
    file_names = []
    if tool == "maven":
        file_names.extend(["build.gradle", "settings.gradle", "build.gradle.kts", "settings.gradle.kts"])
    else:
        file_names.extend(["pom.xml"])

    remove_files(file_names)


def git_init():
    cmd1 = 'git config --local user.name "{{ cookiecutter.author_name }}"'
    cmd2 = 'git config --local user.email "{{ cookiecutter.email }}"'
    try:
        os.system(cmd1)
        os.system(cmd2)
    except:
        pass


def main():
    if "{{ cookiecutter.open_source_license }}" == "Not open source":
        remove_open_source_files()

    tool = "maven"
    remove_build_tool(tool)
    git_init()


if __name__ == "__main__":
    main()
