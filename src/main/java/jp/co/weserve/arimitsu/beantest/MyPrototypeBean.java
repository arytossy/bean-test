package jp.co.weserve.arimitsu.beantest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "prototype")
public class MyPrototypeBean {

    private String state;

    private final MySingletonBean singletonBean;

    public MyPrototypeBean(MySingletonBean singletonBean) {
        this.singletonBean = singletonBean;
    }

    public void changeState(String next) {
        this.state = next;
        singletonBean.changeState(next);
    }

    public String getState() {
        return "prototype: " + this.state;
    }

    public String getSingletonState() {
        return singletonBean.getState();
    }
}
