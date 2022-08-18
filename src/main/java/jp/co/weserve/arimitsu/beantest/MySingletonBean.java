package jp.co.weserve.arimitsu.beantest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class MySingletonBean {

    private String state;

    public void changeState(String next) {
        this.state = next;
    }

    public String getState() {
        return "singleton: " + this.state;
    }
}
