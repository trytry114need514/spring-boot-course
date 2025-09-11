package top.chsh.boot.config.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Data
@Component
public class Team {
    @Value("${team.name}")
    @NotNull
    @NotEmpty
    @NotBlank
    @Size(min = 3,max = 20)
    private String name;

    @Value("${team.leader}")
    private String leader;

    @Value("${team.phone}")
    @Pattern(regexp = "^1[3-9]\\d{9}$")
    private String phone;

    @Value("${team.age}")
    @Min(1)
    @Max(4)
    private Integer age;

    @Past
    private LocalDate createTime;

}
