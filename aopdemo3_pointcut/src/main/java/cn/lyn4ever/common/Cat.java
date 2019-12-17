package cn.lyn4ever.common;

import cn.lyn4ever.annotaion.MyAdvice;

/**
 * 用最入门的cat类
 */
public class Cat {
    
    public void sleep(){
        System.out.println("sleep....");
    }


    public void walk(){
        System.out.println("walking....");
    }

    @MyAdvice
    public void eat(){
        System.out.println("eating....");
    }
}
