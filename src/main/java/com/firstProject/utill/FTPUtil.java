package com.firstProject.utill;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * created by 吴家俊 on 2018/7/25.
 */
public class FTPUtil {

    private static final Logger logger = LoggerFactory.getLogger(FTPUtil.class);

    private static String ftpIp = PropertiesUtil.getProperty("ftp.server.ip");
    private static String ftpUser = PropertiesUtil.getProperty("ftp.user");
    private static String ftpPass = PropertiesUtil.getProperty("ftp.pass");

    public static boolean uploadFile(List<File> fileList) throws IOException {

        FTPUtil ftpUtil = new FTPUtil(ftpIp,21,ftpUser,ftpPass);
        logger.info("开始上传文件");
        String remotePath = "img";
        boolean result =  ftpUtil.uploadFile(remotePath, fileList);
        return result;
    }

    private boolean uploadFile(String remotePath,List<File> fileList) throws IOException {

        boolean uploded = true;
        FileInputStream fis = null;

        if(connectFtpServer(this.ip,this.userName,this.pwd)){
            try {
                ftpClient.changeWorkingDirectory(remotePath);
                ftpClient.setBufferSize(1024);
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                ftpClient.setControlEncoding("UTF-8");
                ftpClient.enterLocalPassiveMode();

                for(File fileItem : fileList){
                    fis = new FileInputStream(fileItem);
                    ftpClient.storeFile(fileItem.getName(),fis);
                }
            } catch (IOException e) {
                logger.error("文件上传失败",e);
                uploded = false;
            }finally{
                fis.close();
                ftpClient.disconnect();
            }

        }
        return uploded;
    }

    private boolean connectFtpServer(String ip,String userName,String pwd){
        ftpClient = new FTPClient();
        boolean isSuccess = false;
        try {
            ftpClient.connect(ip);
            isSuccess = ftpClient.login(userName,pwd);
        } catch (IOException e) {
            logger.error("连接ftp服务器异常",e);
        }
        return isSuccess;
    }







    private String ip;
    private int host;
    private String userName;
    private String pwd;
    private FTPClient ftpClient;

    public FTPUtil(String ip, int host, String userName, String pwd) {
        this.ip = ip;
        this.host = host;
        this.userName = userName;
        this.pwd = pwd;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getHost() {
        return host;
    }

    public void setHost(int host) {
        this.host = host;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}