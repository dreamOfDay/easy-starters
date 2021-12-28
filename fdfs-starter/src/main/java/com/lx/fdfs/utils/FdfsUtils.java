package com.lx.fdfs.utils;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadCallback;
import com.github.tobato.fastdfs.service.DefaultFastFileStorageClient;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.lx.components.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Author: yuj
 * @Date: 2021/12/28
 * @Describe:
 */
@Slf4j
public class FdfsUtils {

    private static FastFileStorageClient dfsClient;

    private static FastFileStorageClient getDfsClient(){
        if (dfsClient == null){
            dfsClient = SpringContextUtils.getBean(DefaultFastFileStorageClient.class);
        }
        return dfsClient;
    }

    public static FastFileStorageClient dfsClient(){
        return getDfsClient();
    }

    /**
     * 上传到 fastdfs 的方法
     * @param uploadPath 上传的本地路径
     * @param type 类型 例如 txt,json,png...
     * @return 上传到fastdfs的全路径，groupName/realPath
     */
    public static String uploadFile2Fdfs(String uploadPath, String type){
        try {
            FileInputStream fileInputStream = new FileInputStream(uploadPath);
            StorePath storePath = dfsClient().uploadFile(fileInputStream, fileInputStream.available(), type, null);
            log.info("上传到fastdfs上的路径为：{}",storePath.getFullPath());
            return storePath.getFullPath();
        } catch (FileNotFoundException e) {
            log.info("上传文件出错：{}",uploadPath);
            e.printStackTrace();
        } catch (IOException e) {
            log.info("上传文件出错：{}",uploadPath);
            e.printStackTrace();
        }
        throw new IllegalArgumentException("上传文件出错");
    }

    public static String uploadFile2Fdfs(InputStream inputStream, String type){
        try {
            StorePath storePath = dfsClient().uploadFile(inputStream, inputStream.available(), type, null);
            return storePath.getFullPath();
        } catch (IOException e) {
            log.info("上传文件出错");
            e.printStackTrace();
        }
        throw new IllegalArgumentException("上传文件出错");
    }

    /**
     * 从 fastdfs 上下载文件
     * @param remoteUrl fastdfs 上的路径
     * @return 字节数组
     */
    public static byte[] downloadFileFromFdfs(String remoteUrl){
        String groupName = remoteUrl.substring(0,remoteUrl.indexOf("/"));
        String realPath = remoteUrl.substring(remoteUrl.indexOf("/")+1);
        return dfsClient().downloadFile(groupName, realPath, new DownloadByteArray());
    }

    public static  <T> T downloadFileFromFdfs(String remoteUrl, DownloadCallback<T> downloadCallback){
        String groupName = remoteUrl.substring(0,remoteUrl.indexOf("/"));
        String realPath = remoteUrl.substring(remoteUrl.indexOf("/")+1);
        return dfsClient().downloadFile(groupName, realPath, downloadCallback);
    }

}
