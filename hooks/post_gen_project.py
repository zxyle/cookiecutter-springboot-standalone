import os


def remove_open_source_files():
    file_names = ["LICENSE"]
    for file_name in file_names:
        os.remove(file_name)


def main():
    if "{{ cookiecutter.open_source_license }}" == "Not open source":
        remove_open_source_files()


if __name__ == "__main__":
    main()
