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
        file_names.extend(
            ["build.gradle", "settings.gradle", "build.gradle.kts", "settings.gradle.kts", "gradlew", "gradlew.bat"])
    else:
        file_names.extend(["pom.xml", "mvnw", "mvnw.bat"])

    remove_files(file_names)


def git_init():
    commands = [
        'git init --initial-branch=master',
        'git config --local user.name "{{ cookiecutter.author_name }}"',
        'git config --local user.email "{{ cookiecutter.email }}"',
        'git add .',
        'git commit -m "Initial commit"',
        'git checkout -b dev/{{ cookiecutter.version }}'
    ]
    try:
        for command in commands:
            os.system(command)
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
