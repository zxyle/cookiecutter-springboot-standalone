package {{ cookiecutter.basePackage }}.common.exception;

import {{ cookiecutter.basePackage }}.common.response.ApiResponse;
import {{ cookiecutter.basePackage }}.common.constant.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = {Exception.class, NullPointerException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<ApiResponse> exceptionHandler(HttpServletRequest request, Exception e, HttpServletResponse response) {
        logger.error("接口报错 路径:【{}】, 状态码:【{}】, 错误原因:【{}】.", request.getServletPath(), response.getStatus(), e.getMessage());
        ApiResponse<Object> apiResponse = new ApiResponse<>(Constant.Response.ERROR_CODE, Constant.Response.ERROR_EXCEPTION, false);
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {CustomerException.class})
    public ResponseEntity<String> customerExceptionHandler(HttpServletRequest request, Exception e, HttpServletResponse response) {
        logger.error(request.getServletPath(), response.getStatus(), e.getMessage());
        int status = response.getStatus();
        return ResponseEntity.status(status).body(e.getMessage());
    }

    // 处理JSON方式 数据校验失败
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    // 处理表单方式 数据校验失败
    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
