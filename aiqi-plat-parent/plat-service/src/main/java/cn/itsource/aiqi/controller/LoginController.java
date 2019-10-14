package cn.itsource.aiqi.controller;

import cn.itsource.aiqi.doqi.User;
import cn.itsource.aiqi.util.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "登录的Controller")
public class LoginController {

    /**
     * 登录接口
     * @return
     */
    @ApiOperation(value = "登录的接口")
    @PostMapping("/login")
    public AjaxResult login(@RequestBody User user){
        String username = "admin";
        String password = "admin";
        if(username.equals(user.getUsername())&&password.equals(user.getPassword())){
            return AjaxResult.me().setSuccess(true).setMessage("登录成功!").setRestObj(user);
        }
        return AjaxResult.me().setSuccess(false).setMessage("登录失败!");
    }
}
