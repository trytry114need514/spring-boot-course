package top.chsh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    @GetMapping("/hello")
    public String hello() {
        return "Hello Java!";
    }

    @GetMapping("/string")
    public String string(){
        return "Hello World!";
    }
}
