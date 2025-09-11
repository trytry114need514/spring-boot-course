package top.chsh.boot.config.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.chsh.boot.config.config.OssConfig;
import top.chsh.boot.config.service.OssService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Slf4j
public class OssServiceImpl implements OssService {

    @Resource
    private OssConfig ossConfig;

    @Override
    public String upload(MultipartFile file) {
        if (file != null) {
            String originalFilename = file.getOriginalFilename();
            assert originalFilename != null;
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFileName = UUID.randomUUID()+suffix;
            log.info("新文件名: {}", newFileName);
            //读取配置文件
            String endpoint = ossConfig.getEndpoint();
            String bucket = ossConfig.getBucket();
            String accessKey = ossConfig.getAccessKey();
            String secretKey = ossConfig.getSecretKey();
            String dir = ossConfig.getDir();

            OSS ossClient = new OSSClientBuilder().build(endpoint,accessKey,secretKey);

            ObjectMetadata meta = new ObjectMetadata();
            String mimeType = null;
            try {
                mimeType = Files.probeContentType(Paths.get(originalFilename));
            } catch (IOException e) {
                log.error("无法推断文件MIME类型：", e);
            }
            if (mimeType != null) {
                meta.setContentType(mimeType);
            } else {
                // 如果无法获取，使用通用的二进制流类型
                meta.setContentType("application/octet-stream");
            }

            // 关键改动：强制浏览器预览而不是下载
            meta.setHeader("Content-Disposition", "inline");

            String uploadPath = dir + newFileName;
            InputStream inputStream;
            try{
                inputStream = file.getInputStream();
            }catch(IOException e) {
                throw new RuntimeException(e);
            }

            ossClient.putObject(bucket,uploadPath,inputStream,meta);

            try {
                // 获取新上传文件的元数据
                ObjectMetadata objectMetadata = ossClient.getObjectMetadata(bucket, uploadPath);
                String contentDispositionHeader = objectMetadata.getContentDisposition();
                log.info("上传文件成功，验证元数据：");
                log.info("Content-Type: {}", objectMetadata.getContentType());
                log.info("Content-Disposition: {}", contentDispositionHeader);

                if ("inline".equals(contentDispositionHeader)) {
                    log.info("元数据已正确设置为 'inline'，问题可能出在OSS的全局配置或URL参数上。");
                } else {
                    log.error("元数据 'Content-Disposition' 未正确设置为 'inline'。");
                }
            } catch (Exception e) {
                log.error("获取文件元数据失败", e);
            }

            ossClient.shutdown();
            return "https://" + bucket + "." + endpoint + "/" +uploadPath;
        }
        return "上传失败";
    }
}
