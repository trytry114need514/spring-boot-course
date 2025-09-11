package top.chsh.boot.config.model;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class TeamTest {
    @Resource
    private  Team team;
    @Test
    void testTeam() {
//        log.info("团队创建成功");
//        log.info(String.valueOf(team));
        assertEquals("WebLearn",team.getName());
        assertEquals("张三",team.getLeader());
    }
}