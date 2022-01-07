package {{ cookiecutter.basePackage }}.biz.sample.service.impl;

import {{ cookiecutter.basePackage }}.base.response.ApiResponse;
import {{ cookiecutter.basePackage }}.biz.sample.entity.User;
import {{ cookiecutter.basePackage }}.biz.sample.mapper.UserMapper;
import {{ cookiecutter.basePackage }}.biz.sample.service.UserService;
import {{ cookiecutter.basePackage }}.common.constant.Constant;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public ApiResponse list(Integer pageNum, Integer pageSize) {
        ApiResponse response;
        Page<User> page = new Page<>(pageNum, pageSize);
        Page<User> data = userMapper.selectPage(page, null);

        response = new ApiResponse(
                Constant.Response.SUCCESS_CODE,
                Constant.Response.SUCCESS_MESSAGE,
                true, data);
        return response;
    }

    @Override
    public ApiResponse save(User user) {
        ApiResponse response;
        userMapper.insert(user);
        response = new ApiResponse(
                Constant.Response.SUCCESS_CODE,
                Constant.Response.SUCCESS_MESSAGE,
                true);
        return response;
    }

}