package com.van.services;

import com.van.services.model.ItemDescription;
import com.van.services.repository.ItemDescriptionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.reactive.config.EnableWebFlux;

import java.util.Arrays;
import java.util.Collection;

import static com.van.services.constant.AppConstants.SPRING_PROFILE_DEVELOPMENT;

@EnableWebFlux
@SpringBootApplication
@Slf4j
public class SpringApp {

    @Autowired
    private Environment env;
    @Autowired
    private ItemDescriptionRepository itemDescriptionRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringApp.class, args);
    }

    @Bean
    public CommandLineRunner CommandLineRunnerBean() {
        return (args) -> {
            Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
            if (activeProfiles.contains(SPRING_PROFILE_DEVELOPMENT)) {
                log.debug("Insert test ItemDescriptions");
                itemDescriptionRepository.save(ItemDescription.builder().id("1").title("test_item_title").price(34.5).build()).block();
                itemDescriptionRepository.save(ItemDescription.builder().id("2").title("test_item_title2").price(7.5).build()).block();
            }
        };
    }

}
