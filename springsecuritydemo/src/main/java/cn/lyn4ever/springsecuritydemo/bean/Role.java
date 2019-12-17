package cn.lyn4ever.springsecuritydemo.bean;

/**
 * @Author: 007
 * @Date: 2019/11/27 14:38
 * @Version: 1.0
 */
public class Role {
    private Long id;
    private String name;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
