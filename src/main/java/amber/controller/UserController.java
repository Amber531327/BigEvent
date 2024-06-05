package amber.controller;

import amber.pojo.Result;
import amber.pojo.User;
import amber.service.UserService;
import amber.utils.JwtUtil;
import amber.utils.Md5Util;
import amber.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        //查询用户
        User u=userService.findByUserName(username);
        if (u==null){
            //没有占用
            userService.register(username,password);
            return Result.success();
        }else {
            //占用
            return Result.error("用户名已被占用");
        }
    }
    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password){
        //根据用户名查询用户
        User loginUser = userService.findByUserName(username);
        if (loginUser==null){
            return Result.error("用户名错误");
        }

        //判断密码是否正确
        if (Md5Util.getMD5String(password).equals(loginUser.getPassword())){
            HashMap<String, Object> claims = new HashMap<>();
            claims.put("id",loginUser.getId());
            claims.put("username",loginUser.getUsername());
            String token = JwtUtil.genToken(claims);
            return Result.success(token);
        }
        return Result.error("密码错误");
    }

    @GetMapping("/userInfo")
    public Result<User> getUserInfo(){
        //根据用户名查询用户
        Map<String,Object> map= ThreadLocalUtil.get();//用ThreadLocal获取
        String username = (String)map.get("username");
        User user = userService.findByUserName(username);
        return Result.success(user);
    }

    @PutMapping("/update")
    public Result update(@Validated @RequestBody User user){
        userService.update(user);
        return Result.success();
    }

    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String AvatarUrl){
        userService.updateAvater(AvatarUrl);
        return Result.success();
    }

    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String,String> params){
        //校验参数
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");

        if (!StringUtils.hasLength(oldPwd)||!StringUtils.hasLength(newPwd)||!StringUtils.hasLength(rePwd)){
            return Result.error("缺少必要的参数");
        }
        //原密码是否正确
        //根据用户名拿到原密码，再和old_pwd比对
        Map<String,Object> map= ThreadLocalUtil.get();
        String  username = (String) map.get("username");
        User user = userService.findByUserName(username);
        if(!user.getPassword().equals(Md5Util.getMD5String(oldPwd))){
            return Result.error("原密码不正确");
        };
        if (!rePwd.equals(newPwd)){
            return Result.error("两次填写的新密码不一样");
        }
        userService.updatePwd(newPwd);
        return Result.success();
    }
}
