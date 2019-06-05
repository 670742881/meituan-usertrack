package com.ibeifeng.senior.usertrack.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by ibf on 10/25.
 */
public class ProcessTest {
    public static void main(String[] args) {
     String command = "sh /home/hadoop/shell/startDFS.sh ";//在本机还可以操作 怎么控制远程
     String command1 = "C:\\WINDOWS\\system32\\calc.exe";
        try {
            Process process = Runtime.getRuntime().exec(command);

            int exitValue = process.waitFor(); //进程没有结束的话，会阻塞等待状态

            if (exitValue == 0) {
                System.out.println("Success!!" + exitValue);
            } else {
                System.out.println("Failure!!" + exitValue);
                InputStream is = process.getErrorStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line = null;
                System.out.println("====Error Msg====");
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
                System.out.println("====Error Msg====");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
