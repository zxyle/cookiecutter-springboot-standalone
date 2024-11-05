package {{ cookiecutter.basePackage }}.common.util;

import org.springframework.http.HttpStatus;

import {{ cookiecutter.namespace }}.servlet.ServletOutputStream;
import {{ cookiecutter.namespace }}.servlet.http.HttpServletResponse;
import java.io.IOException;

public final class ResponseUtil {

    private ResponseUtil() {
    }

    public static void forbidden(HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write("{\"message\": \"禁止访问\", \"success\": false, \"code\": \"403\"}".getBytes());
        response.setStatus(HttpStatus.FORBIDDEN.value());
    }
}
