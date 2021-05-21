package com.azericard.testoracle.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class ConnectionService implements Runnable {

    @SneakyThrows
    @Override
    public void run() {
        Resource resource = new ClassPathResource("application.properties");
        FileReader reader=new FileReader(resource.getFile());
        Properties p=new Properties();
        p.load(reader);

        String url = p.getProperty("spring.datasource.url");
        String username = p.getProperty("spring.datasource.username");
        String password = p.getProperty("spring.datasource.password");


        LocalDateTime now = LocalDateTime.now();

        Connection con = DriverManager.getConnection(
                url,
                username, password);

        con.close();

        LocalDateTime then = LocalDateTime.now();
        Duration duration = Duration.between(now, then);

        long nano = Math.abs(duration.toNanos());
        long milliSeconds = Math.abs(duration.toMillis());
        long seconds = milliSeconds / 1000;
        long minutes = Math.abs(duration.toMinutes());


        String diff =
//                "Start date : " + now + "<br>\n" +
//                "Difference in minutes : " + minutes + "<br>\n" +
//                "\n" +   "Difference in seconds : " + seconds + "\n"
                "Difference in milliseconds :; " + milliSeconds
//                "Difference in nanoseconds : " + nano + "<br>\n" +
//                "End date : " + then
                ;
        System.out.println(diff);
//        return diff;
    }
}
