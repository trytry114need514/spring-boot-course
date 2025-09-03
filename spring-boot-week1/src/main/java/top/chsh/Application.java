package top.chsh;


import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.GetMapping;

public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    @GetMapping("/hello")
    public String hello() {
        return "Hello World!";
    }
}
