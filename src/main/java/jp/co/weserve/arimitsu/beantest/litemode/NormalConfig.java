package jp.co.weserve.arimitsu.beantest.litemode;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NormalConfig {

    @Bean
    public HogeBean normalHoge() {
        return new HogeBean("normal");
    }

    @Bean
    public BeanWrapper normalWrapperByMethodCall1() {
        return new BeanWrapper("normal_by_method_call_1", normalHoge());
    }

    @Bean
    public BeanWrapper normalWrapperByMethodCall2() {
        return new BeanWrapper("normal_by_method_call_2", normalHoge());
    }

    @Bean
    public BeanWrapper normalWrapperByInjection1(HogeBean normalHoge) {
        return new BeanWrapper("normal_by_injection_1", normalHoge);
    }

    @Bean
    public BeanWrapper normalWrapperByInjection2(HogeBean normalHoge) {
        return new BeanWrapper("normal_by_injection_2", normalHoge);
    }
}
