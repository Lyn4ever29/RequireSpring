package cn.lyn4ever.aop.aopconfig;

public class Teacher {

    private Student student;

    public void tellStudent(){
        student.sleep();
        student.talk();
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
