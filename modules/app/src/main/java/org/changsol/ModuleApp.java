package org.changsol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * SrpingBoot 시작
 */
@SpringBootApplication(exclude = {GsonAutoConfiguration.class}, scanBasePackages = {"org.changsol"})
public class ModuleApp {
    public static void main(String[] args) {
        SpringApplication.run(ModuleApp.class, args);
    }
}

