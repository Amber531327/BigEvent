package amber.service;

import amber.pojo.User;

public interface UserService {
    //根据用户名查询用户
    User findByUserName(String username);

    //注册
    void register(String username, String password);
    //更新用户基本信息
    void update(User user);
    //更新用户头像
    void updateAvater(String avatarUrl);
    //更新用户密码
    void updatePwd(String newPwd);
}
