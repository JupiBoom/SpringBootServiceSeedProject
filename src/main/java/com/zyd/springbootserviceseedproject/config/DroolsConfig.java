package com.zyd.springbootserviceseedproject.config;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Drools规则引擎配置类
 * 
 * @author zyd
 * @since 2024-01-01
 */
@Configuration
public class DroolsConfig {

    private static final String RULES_PATH = "rules/";

    @Bean
    public KieContainer kieContainer() {
        KieServices kieServices = KieServices.Factory.get();
        KieRepository kieRepository = kieServices.getRepository();
        kieRepository.addKieModule(kieRepository::getDefaultReleaseId);
        KieBuilder kieBuilder = kieServices.newKieBuilder(createKieFileSystem(kieServices));
        kieBuilder.buildAll();
        return kieServices.newKieContainer(kieRepository.getDefaultReleaseId());
    }

    private KieFileSystem createKieFileSystem(KieServices kieServices) {
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        // 加载规则文件
        kieFileSystem.write(ResourceFactory.newClassPathResource(RULES_PATH + "promotion-rules.drl"));
        kieFileSystem.write(ResourceFactory.newClassPathResource(RULES_PATH + "coupon-rules.drl"));
        kieFileSystem.write(ResourceFactory.newClassPathResource(RULES_PATH + "discount-combination-rules.drl"));
        return kieFileSystem;
    }

    @Bean
    public KieSession kieSession(KieContainer kieContainer) {
        return kieContainer.newKieSession();
    }

    @Bean
    public KieBase kieBase(KieContainer kieContainer) {
        return kieContainer.getKieBase();
    }
}