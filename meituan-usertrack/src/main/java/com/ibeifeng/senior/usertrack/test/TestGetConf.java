package com.ibeifeng.senior.usertrack.test;

import com.ibeifeng.senior.usertrack.conf.ConfigurationManager;
import com.ibeifeng.senior.usertrack.constant.Constants;
import java.util.concurrent.ThreadLocalRandom;



public class TestGetConf {
    public static void main(String[] args) {
        String driver = ConfigurationManager.getProperty(Constants.JDBC_URL);
        System.out.println(driver);

        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < 10; i++) {
            System.out.println(random.nextDouble(100)
            );

        }
    }
}
