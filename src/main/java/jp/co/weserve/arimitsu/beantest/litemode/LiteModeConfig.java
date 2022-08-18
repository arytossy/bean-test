package jp.co.weserve.arimitsu.beantest.litemode;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class LiteModeConfig {

    @Bean
    public HogeBean liteHoge() {
        return new HogeBean("lite");
    }

    @Bean
    public BeanWrapper liteWrapperByMethodCall1() {
        return new BeanWrapper("lite_by_method_call_1", liteHoge());
    }

    @Bean
    public BeanWrapper liteWrapperByMethodCall2() {
        return new BeanWrapper("lite_by_method_call_2", liteHoge());
    }

    @Bean
    public BeanWrapper liteWrapperByInjection1(HogeBean liteHoge) {
        return new BeanWrapper("lite_by_injection_1", liteHoge);
    }

    @Bean
    public BeanWrapper liteWrapperByInjection2(HogeBean liteHoge) {
        return new BeanWrapper("lite_by_injection_2", liteHoge);
    }
}
