package {{ cookiecutter.basePackage }}.biz.sample.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

/**
 * url参数校验示例
 */
@RestController
@Validated
public class ValidationPathController {

    /**
     * url参数校验示例1
     *
     * @param id 用户ID
     */
    @GetMapping("/person/{id}")
    public ResponseEntity<Integer> getPersonByID(@Valid @PathVariable("id") @Max(value = 5, message = "超过 id 的范围了") Integer id) {
        return ResponseEntity.ok().body(id);
    }

    /**
     * url参数校验示例2
     *
     * @param name 姓名
     */
    @PutMapping("/person")
    public ResponseEntity<String> getPersonByName(@Valid @RequestParam("name") @Size(max = 6, message = "超过 name 的范围了") String name) {
        return ResponseEntity.ok().body(name);
    }
}
