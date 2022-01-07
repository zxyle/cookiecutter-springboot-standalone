package {{ cookiecutter.basePackage }}.common.exception;


import {{ cookiecutter.basePackage }}.common.enums.ErrorEnum;
import {{ cookiecutter.basePackage }}.common.enums.ErrorNetWorkEnum;

public class BlException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String code;
    private String message;

    public BlException(ErrorEnum errorEnum) {
        this.code = errorEnum.getCode();
        this.message = errorEnum.getMessage();
    }

    public BlException(ErrorNetWorkEnum errorEnum) {
        this.code = errorEnum.getCode();
        this.message = errorEnum.getMessage();
    }

    public BlException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
