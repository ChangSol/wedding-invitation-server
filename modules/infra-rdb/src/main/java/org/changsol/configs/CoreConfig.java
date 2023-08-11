package org.changsol.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * CoreConfig Class
 */
@Configuration
@EnableJpaAuditing // Audit 사용
public class CoreConfig {
}