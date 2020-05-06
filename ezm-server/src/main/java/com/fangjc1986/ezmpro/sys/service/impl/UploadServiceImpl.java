package com.fangjc1986.ezmpro.sys.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectResult;
import com.fangjc1986.ezmpro.sys.mapper.UploadMapper;
import com.fangjc1986.ezmpro.sys.model.Upload;
import com.fangjc1986.ezmpro.sys.service.IUploadService;
import com.fangjc1986.support.exception.ServiceException;
import com.fangjc1986.support.service.BaseService;
import com.fangjc1986.support.util.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author EricFang
 * @since 2020-04-02
 */
@Service
public class UploadServiceImpl extends BaseService<UploadMapper, Upload> implements IUploadService {

    @Value("${p.uploadDir}")
    String uploadDir;

    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    public String upload2Local(MultipartFile file) {
        if (file.isEmpty()) {
            throw new ServiceException("文件不能为空");
        }
        String fileName = file.getOriginalFilename();
        if (fileName == null) throw new ServiceException("文件名称不能为空");
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String relPath = File.pathSeparator + UUID.randomUUID().toString() + suffix;
        String path = uploadDir + date + relPath;
        File dest = new File(path);
        try {
            file.transferTo(dest);
            return relPath;
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new ServiceException("上传失败");
    }

    @Value("${ali.accessKeyId}")
    String aliAccessKeyId;
    @Value("${ali.accessKeySecret}")
    String aliAccessKeySecret;
    @Value("${ali.OSSBucket}")
    String aliOSSBucket;
    @Value("${ali.OSSEndPoint}")
    String aliOSSEndPoint;
    @Value("${ali.OSSDomain}")
    String aliOssDomain;

    /**
     * 将文件上传到ali OSS服务器
     *
     * @param file
     * @return
     */
    public String upload2AliOSS(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new ServiceException("文件不能为空");
            }
            String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
            //String relPath = File.pathSeparator + UUID.randomUUID().toString() + suffix;
            String path = date + "_" + UUID.randomUUID().toString() + StringUtil.getSuffix(Objects.requireNonNull(file.getOriginalFilename()));
            // Endpoint以杭州为例，其它Region请按实际情况填写。
            String endpoint = aliOSSEndPoint;
            // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
            String accessKeyId = aliAccessKeyId;
            String accessKeySecret = aliAccessKeySecret;

            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 上传文件流。
            InputStream inputStream = file.getInputStream();
            PutObjectResult res = ossClient.putObject(aliOSSBucket, path, inputStream);
            // 关闭OSSClient。
            ossClient.shutdown();
            return aliOssDomain + path;
        } catch (Exception e) {
            throw new ServiceException("文件上传失败");
        }

    }

}
