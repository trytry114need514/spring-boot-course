package top.chsh.boot.config.controller;


import cn.hutool.core.img.ImgUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;

@Slf4j
@RestController
public class QrCodeController {
    @Value("${custom.qrcode.url}")
    private String qrcodeUrl;


    @RestController
    public class qrCodeController {

        @GetMapping("/qrcode")
        public ResponseEntity<byte[]> generateQrcode() {
            try {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                QrCodeUtil.generate(qrcodeUrl, 200, 200, ImgUtil.IMAGE_TYPE_PNG, outputStream);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_PNG);

                return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
            } catch (Exception e) {
                log.error("生成二维码时出错", e);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }
}
