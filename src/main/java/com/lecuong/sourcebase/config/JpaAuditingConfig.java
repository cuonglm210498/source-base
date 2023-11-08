package com.lecuong.sourcebase.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author CuongLM
 * @created 06/11/2023 - 7:17 PM
 * @project source-base
 */
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {

    /**
     * Tự động điền created_date, created_by, modified_date, modified_by
     */
}
