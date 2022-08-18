package jp.co.weserve.arimitsu.beantest.litemode;

import lombok.Getter;

public class HogeBean {

    private static int count = 0;

    @Getter
    private String name;

    @Getter
    private int index;

    public HogeBean(String name) {
        this.name = name;
        this.index = HogeBean.count;
        HogeBean.count++;
    }
}
