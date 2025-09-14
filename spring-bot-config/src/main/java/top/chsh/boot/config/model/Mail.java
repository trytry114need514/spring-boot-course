package top.chsh.boot.config.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Mail {
    private String to;
    private String subject;
    private String content;
}
