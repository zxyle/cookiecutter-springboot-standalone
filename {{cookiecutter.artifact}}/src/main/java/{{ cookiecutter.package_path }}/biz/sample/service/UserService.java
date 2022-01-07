package {{ cookiecutter.basePackage }}.biz.sample.service;

import {{ cookiecutter.basePackage }}.base.response.ApiResponse;
import {{ cookiecutter.basePackage }}.biz.sample.entity.User;

public interface UserService {

    ApiResponse list(Integer pageNum, Integer pageSize);

    ApiResponse save(User user);

}
