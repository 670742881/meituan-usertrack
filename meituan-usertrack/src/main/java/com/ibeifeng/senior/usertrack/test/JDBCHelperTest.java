package com.ibeifeng.senior.usertrack.test;

import com.ibeifeng.senior.usertrack.jdbc.JDBCHelper;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

/**
 * 表的创建DDL语句：
     CREATE TABLE `task` (
     `id` int(11) NOT NULL AUTO_INCREMENT,
     `name` varchar(255) DEFAULT NULL,
     `age` int(11) DEFAULT NULL,
     PRIMARY KEY (`id`)
     ) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8
 *
 *
 * Created by XuanYu on 2016/6/4.
 */
public class JDBCHelperTest {

    public static void main(String[] args) throws Exception {
        // 获取JDBCHelper的单例
        JDBCHelper jdbcHelper = JDBCHelper.getInstance();

        // 测试普通的增删改语句
		jdbcHelper.executeUpdate(
				"insert into task (name,age) values(?,?)",
				new Object[]{"王二", 28});

        // 测试查询语句
		final Map<String, Object> testUser = new HashMap<String, Object>();

		// 设计一个内部接口QueryCallback
		// 那么在执行查询语句的时候，我们就可以封装和指定自己的查询结果的处理逻辑
		// 封装在一个内部接口的匿名内部类对象中，传入JDBCHelper的方法
		// 在方法内部，可以回调我们定义的逻辑，处理查询结果
		// 并将查询结果，放入外部的变量中
		jdbcHelper.executeQuery(
            "select name,age from task where id=?",
            new Object[]{1},
            new JDBCHelper.QueryCallback() {
                public void process(ResultSet rs) throws Exception {
                    if(rs.next()) {
                        String name = rs.getString(1);
                        int age = rs.getInt(2);

                        // 匿名内部类的使用，有一个很重要的知识点
                        // 如果要访问外部类中的一些成员，比如方法内的局部变量
                        // 那么，必须将局部变量，声明为final类型，才可以访问
                        // 否则是访问不了的
                        testUser.put("name", name);
                        testUser.put("age", age);
                    }
                }
            });

		System.out.println(testUser.get("name") + ":" + testUser.get("age"));
    }

}
