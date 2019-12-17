package cn.lyn4ever.springsecuritydemo.bean;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 这个类对应数据库的表
 *
 * @Author: lyn4ever
 * @Date: 2019/11/27 14:22
 * @Version: 1.0
 */
public class SysUser implements UserDetails {

    private String username;//用户名
    private String password;//密码
    private boolean enabled;//是否可用
    private List<Role> roles;//用户角色集合

/******************下边这些override方法返回true,只是为了文件测试，实际建议从库中查询用户状态**********************/
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        /*
        为什么要给这个role集合加上ROLE_前缀？
        因为SpringSecurity在配置时，权限有个前缀ROLE_
        如果不想加，有两种解决方案：
        1.在存进数据库时加上ROLE_前缀，
        2.在配置文件中修改这个配置，后边会说
         */
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        }
        return authorities;
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
