package cn.lyn4ever.springsecuritydemo.service;

import cn.lyn4ever.springsecuritydemo.bean.Role;
import cn.lyn4ever.springsecuritydemo.bean.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 007
 * @Date: 2019/11/27 14:45
 * @Version: 1.0
 */
public class UserDetailService implements UserDetailsService {

    /*
    这个类在项目中有配置的，想看的可以下载源码后查看
     */
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /*
        这个方法主要是用username查询用户信息，然后返回一个UserDetails类
        我们的SysUser类实现了UserDeatils类，就可以直接返回，
        如果没有实现这个类，我们就要构建这个类（其实也是返回它的一个子类，因为接口不能实例化）
         */

        //这一段是从数据库中查询用户信息，我们这里直接省略，自己构建一个类,假装是从库中查出来的
        SysUser user = new SysUser();
        user.setUsername("lyn4ever");
        //注意这个用户密码如果是明文的, 要使用{noop}前缀
        //我们使用Spring Security的加密方法,这个在后边验证用户密码时会闭合验证
        user.setPassword(passwordEncoder.encode("lyn4ever"));
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("管理员"));
        roles.add(new Role("用户"));
        user.setRoles(roles);

        //直接返回这个就可以，这里说一点，如果是从数据库中没有查出用户，不建设返回null,而是返回一个new SysUser()对象
        return user;
    }
}
