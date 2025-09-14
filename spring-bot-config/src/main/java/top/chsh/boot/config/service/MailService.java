package top.chsh.boot.config.service;

import org.springframework.web.multipart.MultipartFile;
import top.chsh.boot.config.enums.ResultStatus;
import top.chsh.boot.config.model.Mail;

public interface MailService {
    ResultStatus sendSimpleMail(Mail mail);
    /**
     * 发送HTML格式的邮件
     * @param mail
     * @return
     */
    ResultStatus sendHtmlMail(Mail mail);

    /**
     * 发送带附件的邮件
     * @param mail
     * @return
     */
    ResultStatus sendAttachmentsMail(Mail mail, MultipartFile[] files);
}
