package jp.co.weserve.arimitsu.beantest.litemode;

public class BeanWrapper {

    private final String name;
    private final HogeBean hogeBean;

    public BeanWrapper(String name, HogeBean hogeBean) {
        this.name = name;
        this.hogeBean = hogeBean;
    }

    public String discribe() {
        return String.format(
            "wapper name: %s, hoge bean: %s[%d]",
            this.name, hogeBean.getName(), hogeBean.getIndex());
    }
}
