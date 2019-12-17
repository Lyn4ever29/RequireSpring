package cn.lyn4ever.common;

/**
 * 几个通知类中共用的类
 */
public class Person {

    private String name;

    public boolean saySomething(String something){
        System.out.println("Pereson类中说了一句："+something);
        return true;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
