package com.zyd.springbootserviceseedproject.config;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieRepository;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;

/**
 * Drools规则引擎配置类
 * @author zyd
 * @date 2024-05-20
 */
//@Configuration
public class DroolsConfig {

    private static final String RULES_PATH = "rules/";

    //@Bean
    public KieContainer kieContainer() {
        KieServices kieServices = KieServices.Factory.get();
        KieRepository kieRepository = kieServices.getRepository();
        kieRepository.addKieModule(kieRepository::getDefaultReleaseId);
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();

        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        try {
            Resource[] resources = resourcePatternResolver.getResources("classpath*:" + RULES_PATH + "*.drl");
            for (Resource resource : resources) {
                kieFileSystem.write(ResourceFactory.newClassPathResource(RULES_PATH + resource.getFilename(), "UTF-8"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();

        return kieServices.newKieContainer(kieRepository.getDefaultReleaseId());
    }

    //@Bean
    public KieSession kieSession(KieContainer kieContainer) {
        return kieContainer.newKieSession();
    }
}