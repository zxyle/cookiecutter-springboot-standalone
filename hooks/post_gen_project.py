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


def read_all_files(directory):
    files = []
    for root, dirs, files in os.walk(directory):
        for file_name in files:
            if file_name.endswith(".java"):
                file_path = os.path.join(root, file_name)
                files.append(file_path)
    return files


# 在spring boot 3.0中，javax.*包被移除，需要替换为jakarta.*包
def replace_namespace():
    if "{{ cookiecutter.bootVersion }}".split(".")[0] != "3":
        return

    files = read_all_files("src/main/java")
    for file_path in files:
        with open(file_path, 'r') as file:
            content = file.read()

            updated_content = content.replace("import javax.validation", "import jakarta.validation")
            updated_content = updated_content.replace("import javax.servlet", "import jakarta.servlet")
            updated_content = updated_content.replace("import javax.annotation", "import jakarta.annotation")
            updated_content = updated_content.replace("import javax.mail", "import jakarta.mail")
            # updated_content = updated_content.replace("import javax.xml", "import jakarta.xml")
            # updated_content = updated_content.replace("import javax.transaction", "import jakarta.transaction")
            # updated_content = updated_content.replace("import javax.ws", "import jakarta.ws")
            # updated_content = updated_content.replace("import javax.security", "import jakarta.security")
            # updated_content = updated_content.replace("import javax.json", "import jakarta.json")
            # updated_content = updated_content.replace("import javax.enterprise", "import jakarta.enterprise")

            with open(file_path, 'w') as file1:
                file1.write(updated_content)


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
    # replace_namespace()


if __name__ == "__main__":
    main()
