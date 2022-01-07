# {{ cookiecutter.artifact }}
_{{ cookiecutter.project_short_description }}_

## Intro


{% if cookiecutter.open_source_license != 'Not open source' -%}
## License
{{ cookiecutter.open_source_license }}
{% endif %}