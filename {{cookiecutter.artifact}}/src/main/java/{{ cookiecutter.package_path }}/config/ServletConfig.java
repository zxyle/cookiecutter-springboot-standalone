package {{ cookiecutter.basePackage }}.config;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ServletComponentScan // servlet filter listener
public class ServletConfig {
}
