package {{ cookiecutter.group }}.{{ cookiecutter.artifact.replace('-', '') }};

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class {{ cookiecutter.app_name }}ApplicationTests {

  @Test
  void contextLoads() {
  }

}
