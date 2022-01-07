package {{ cookiecutter.basePackage }}.config.interceptor;

import {{ cookiecutter.basePackage }}.common.utils.JwtUtils;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class JWTInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取请求头中的令牌
        String token = request.getHeader("token");

        Map<String, Object> map = new HashMap<>();
        try {
            JwtUtils.verify(token);
            map.put("status", true);
            map.put("msg", "请求成功");
            return true;
        } catch (SignatureVerificationException e) {
            map.put("msg", "签名不一致异常");
        } catch (TokenExpiredException e) {
            map.put("msg", "令牌过期异常");
        } catch (AlgorithmMismatchException e) {
            map.put("msg", "算法不匹配异常");
        } catch (InvalidClaimException e) {
            map.put("msg", "失效的payload异常");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", "其他异常");
        }
        map.put("status", false);

        // 对象转换成json
        String json = new ObjectMapper().writeValueAsString(map);
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.println(json);
        return false;
    }
}
