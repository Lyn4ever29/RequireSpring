package cn.lyn4ever.firstday.bean;

public class Computer {

    private String size;
    private String color;


    public Computer() {
    }

    public Computer(String size, String color) {
        this.size = size;
        this.color = color;
    }


    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
