package br.com.game;

import io.sentry.Sentry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Date;

@SpringBootApplication
@RestController
public class GameLogApplication extends SpringBootServletInitializer {

    @Value("${sentry.dsn}")
    private String dsn;

    public static void main(String[] args) {
        SpringApplication.run(GameLogApplication.class, args);
    }

    @PostConstruct
    public void loadSentry(){
        if(dsn == null || dsn.isEmpty())
            return;

        Sentry.init(dsn);
        Sentry.capture("API Startup at: " + new Date().toString());
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(GameLogApplication.class);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String healthCheck(){
        return "API ROCK's - " + new Date().toString();
    }
}
