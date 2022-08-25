package jp.co.weserve.arimitsu.beantest.reloadproperties;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;

import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.ReloadingFileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.event.EventListener;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.reloading.PeriodicReloadingTrigger;
import org.apache.commons.configuration2.reloading.ReloadingController;
import org.apache.commons.configuration2.reloading.ReloadingEvent;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Configuration
public class PropertiesScopeConfig {

    @Bean
    public BeanFactoryPostProcessor beanFactoryPostProcessor() {
        return new BeanFactoryPostProcessor() {
            @Override
            public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) throws BeansException {
                factory.registerScope("properties", new PropertiesScope());
            }
        };
    }

    @Bean
    public ReloadingFileBasedConfigurationBuilder<FileBasedConfiguration> reloadingFileBasedConfigurationBuilder() throws Exception {
        File propertiesFile = new File("application.properties");
        ReloadingFileBasedConfigurationBuilder<FileBasedConfiguration> builder
            = new ReloadingFileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                .configure(new Parameters().fileBased().setFile(propertiesFile));
        return builder;
    }

    @Bean
    public PeriodicReloadingTrigger periodicReloadingTrigger(ReloadingFileBasedConfigurationBuilder<FileBasedConfiguration> builder) {
        ReloadingController controller = builder.getReloadingController();
        controller.addEventListener(ReloadingEvent.ANY, new EventListener<>() {
            @Override
            public void onEvent(ReloadingEvent event) {
                PropertiesContextHolder.newContext();
            }
        });
        PeriodicReloadingTrigger trigger = new PeriodicReloadingTrigger(controller, null, 10, TimeUnit.SECONDS);
        trigger.start();
        return trigger;
    }

    @Bean
    public PropertySource<ReloadingFileBasedConfigurationBuilder<FileBasedConfiguration>> reloadablePropertySource(
        ConfigurableEnvironment environment,
        ReloadingFileBasedConfigurationBuilder<FileBasedConfiguration> builder
    ) {
        PropertySource<ReloadingFileBasedConfigurationBuilder<FileBasedConfiguration>> propertySource
            = new PropertySource<>("reloadable", builder) {
                @Override
                public Object getProperty(String name) {
                    try {
                        return this.source.getConfiguration().getProperty(name);
                    } catch (ConfigurationException ex) {
                        throw new IllegalStateException("Loading ReloadablePropertySource failed.", ex);
                    }
                }
            };
        environment.getPropertySources().addFirst(propertySource);
        return propertySource;
    }

    @Getter
    @Setter
    @Component
    @Scope(scopeName = "properties", proxyMode = ScopedProxyMode.TARGET_CLASS)
    @ConfigurationProperties("reloadtest")
    public static class ReloadTestBean {
        private String hoge;
        private String fuga;

        @PreDestroy
        public void destroy() {
            System.out.println("ReloadTestBean has destroyed!");
        }
    }
}
