package com.ibeifeng.senior.usertrack.test;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;


public class RemoteShellTool {
    private Connection conn;
    private String ipAddr;
    private String charset = Charset.defaultCharset().toString();
    private String userName;
    private String password;

    public RemoteShellTool( String ipAddr, String userName, String password, String charset) {
        this.ipAddr = ipAddr;
        this.userName = userName;
        this.password = password;
        if(charset !=null){
            this.charset = charset;
        }
    }
    public boolean login() throws IOException {
        conn = new Connection(ipAddr);
        //连接
        conn.connect();
        //认证
        return  conn.authenticateWithPassword(userName,password);
    }

    public String exec(String cmds) {
        InputStream in = null;
        String result = "";
        try {
            if(this.login()){
                //打开一个会话
                Session session = conn.openSession();
                session.execCommand(cmds);
                in = session.getStdout();
                result = this.processStdout(in,this.charset);
                conn.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String processStdout(InputStream in, String charset) {
        byte[]  buffer = new byte[1024];
        StringBuffer sb = new StringBuffer();
        try {
            while (in.read(buffer)!=-1){
                sb.append(new String(buffer,charset));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  sb.toString();
    }

    public static void main(String[] args) {
//      args[0]="{1}";
        RemoteShellTool rst = new RemoteShellTool("bigdata.server1","hejunhong","123456","utf-8");
        System.out.println(rst.exec("/opt/cdh5/hadoop-2.5.0-cdh5.3.6/sbin/hadoop-daemon.sh start datanode"));
        System.out.println(rst.exec("ls /opt/cdh5/"));
//String submit="/opt/cdh5/spark-2.2.1-bin-2.6.0-cdh5.14.2/bin/spark-submit --master yarn --deloy-modle cluster" +
//        "--class spark.jar  hdfs://  "+args[0]+ " --conf  spark.ssss:20";
    }
}
