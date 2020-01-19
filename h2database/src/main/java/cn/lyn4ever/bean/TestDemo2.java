package cn.lyn4ever.bean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TestDemo2 {

    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(BeanConfig.class);

        JdbcTemplate jdbcTemplate = context.getBean("jdbcTemplate", JdbcTemplate.class);

        String selectSql = "select * from teacher";

        List query = jdbcTemplate.query(selectSql, new RowMapper() {
            @Nullable
            @Override
            public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                Teacher teacher = new Teacher();
                teacher.setId(resultSet.getInt(1));
                teacher.setName(resultSet.getString(2));
                teacher.setAge(resultSet.getInt(3));
                return teacher;
            }
        });

        query.forEach(o -> System.out.println(o));

        //以下键盘输入事件，就是让程序一直处于运行状态，然后使用idea右边的工具连一个这个数据库
//        Scanner sc = new Scanner(System.in);
//        sc.next();
    }
}
