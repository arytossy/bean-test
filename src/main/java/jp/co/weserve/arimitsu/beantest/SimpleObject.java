package jp.co.weserve.arimitsu.beantest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SimpleObject {

    @Autowired
    private MyBean myBean;

    public String callBean() {
        return myBean.piyo();
    }
}
