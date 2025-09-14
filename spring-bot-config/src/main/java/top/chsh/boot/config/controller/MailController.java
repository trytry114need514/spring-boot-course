package top.chsh.boot.config.controller;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.chsh.boot.config.common.ApiResponse;
import top.chsh.boot.config.enums.ResultStatus;
import top.chsh.boot.config.model.Mail;
import top.chsh.boot.config.service.MailService;

@RestController
@RequestMapping("/mail")
public class MailController {
    @Resource
    private MailService mailService;
    /**
     * 发送简单邮件
     * @param mail
     * @return
     */
    @PostMapping("/simple")
    public ResponseEntity<ApiResponse<ResultStatus>> sendSimpleMail(@Valid @RequestBody Mail mail){
        ResultStatus rs = mailService.sendSimpleMail(mail);
        if (rs == ResultStatus.SUCCESS){
            return ResponseEntity.ok(ApiResponse.success("发送成功",rs));
        }
        return ResponseEntity.badRequest().body(ApiResponse.error("发送失败"));
    }

    /**
     * 发送HTML邮件
     * @param mail
     * @return
     */
    @PostMapping("/html")
    public ResponseEntity<ApiResponse<ResultStatus>> sendHtmlMail(@Valid @RequestBody Mail mail){
        ResultStatus rs = mailService.sendHtmlMail(mail);
        return rs == ResultStatus.SUCCESS ?
                ResponseEntity.ok(ApiResponse.success("发送成功",rs)) :
                ResponseEntity.badRequest().body(ApiResponse.error("发送失败"));
    }

    /**
     * 带附件的邮件
     * @param mail
     * @param files
     * @return
     */
    @PostMapping(value = "/attachment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<ResultStatus>> sendAttachmentsMail(
            @Valid @RequestPart("mail") Mail mail,
            @RequestPart("files") MultipartFile[] files) {
        ResultStatus rs = mailService.sendAttachmentsMail(mail, files);
        return rs == ResultStatus.SUCCESS ?
                ResponseEntity.ok(ApiResponse.success("发送成功", rs)) :
                ResponseEntity.badRequest().body(ApiResponse.error("发送失败"));
    }
}
