package top.chsh.boot.config.service.impl;

import jakarta.annotation.Resource;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.chsh.boot.config.enums.ResultStatus;
import top.chsh.boot.config.model.Mail;
import top.chsh.boot.config.service.MailService;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Service
public class MailServiceImpl implements MailService {
    @Value("${spring.mail.username}")
    private String from;

    @Resource
    private JavaMailSender javaMailSender;
    /**
     * 发送简单邮件
     * @param mail
     * @return
     */
    @Override
    public ResultStatus sendSimpleMail(Mail mail){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(mail.getTo());
        message.setSubject(mail.getSubject());
        message.setText(mail.getContent());
        try{
            javaMailSender.send(message);
            return ResultStatus.SUCCESS;
        } catch (Exception e){
            return ResultStatus.FAIL;
        }
    }
    /**
     * 发送HTML邮件
     * @param mail
     * @return
     */
    @Override
    public ResultStatus sendHtmlMail(Mail mail){
        //TODO: 待实现
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false, StandardCharsets.UTF_8.name());
            helper.setFrom(from);
            helper.setTo(mail.getTo());
            helper.setSubject(mail.getSubject());
            helper.setText(mail.getContent(), true);
            javaMailSender.send(message);
            return ResultStatus.SUCCESS;
        } catch (Exception e){
            return ResultStatus.FAIL;
        }
    }

    @Override
    public ResultStatus sendAttachmentsMail(Mail mail, MultipartFile[] files) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
            helper.setFrom(from);
            helper.setTo(mail.getTo());
            helper.setSubject(mail.getSubject());

            helper.setText(mail.getContent(), true);

            if (files != null) {
                for (MultipartFile f : files) {
                    if (f != null && !f.isEmpty()) {
                        helper.addAttachment(Objects.requireNonNull(f.getOriginalFilename()),
                                new ByteArrayResource(f.getBytes()));
                    }
                }
            }
            javaMailSender.send(message);
            return ResultStatus.SUCCESS;
        } catch (Exception e) {
            return ResultStatus.FAIL;
        }
    }
}
