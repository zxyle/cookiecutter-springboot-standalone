# {{ cookiecutter.artifact }}
_{{ cookiecutter.project_short_description }}_

## Intro
初始化账号: admin, 密码: qw12345678


{% if cookiecutter.open_source_license != 'Not open source' -%}
## License
{{ cookiecutter.open_source_license }}
{% endif %}