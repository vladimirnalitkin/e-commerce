package com.van.services.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.Collection;
import java.util.TimeZone;

import static com.van.services.constant.AppConstants.*;

@Slf4j
public abstract class AbstractConfiguration {
    private final Environment env;

    @Autowired
    public AbstractConfiguration(Environment env) {
        log.debug("Run AbstractConfiguration configuration");
        this.env = env;
    }

    @PostConstruct
    public void initApplication() {
        log.debug("PostConstruct run");
        TimeZone.setDefault(TimeZone.getTimeZone(env.getProperty("spring.jpa.properties.time_zone", "UTC")));

        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
        if (activeProfiles.contains(SPRING_PROFILE_DEVELOPMENT) &&
                activeProfiles.contains(SPRING_PROFILE_PRODUCTION)) {
            log.error("You have misconfigured your application! It should not run " + "with both the 'dev' and 'prod' profiles at the same time.");
        }
        if (activeProfiles.contains(SPRING_PROFILE_DEVELOPMENT) &&
                activeProfiles.contains(SPRING_PROFILE_CLOUD)) {
            log.error("You have misconfigured your application! It should not " + "run with both the 'dev' and 'cloud' profiles at the same time.");
        }
    }
}
