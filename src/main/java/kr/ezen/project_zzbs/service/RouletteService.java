package kr.ezen.project_zzbs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RouletteService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Async
    public Map<String, Object> getRandomFood() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String sql = "SELECT * FROM zzbs ORDER BY RAND() LIMIT 1";
        return jdbcTemplate.queryForMap(sql);
    }
}