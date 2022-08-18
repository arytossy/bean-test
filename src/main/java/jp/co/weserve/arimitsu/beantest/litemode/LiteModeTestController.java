package jp.co.weserve.arimitsu.beantest.litemode;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LiteModeTestController {

    private final BeanWrapper normalWrapperByMethodCall1;
    private final BeanWrapper normalWrapperByMethodCall2;
    private final BeanWrapper normalWrapperByInjection1;
    private final BeanWrapper normalWrapperByInjection2;
    private final BeanWrapper liteWrapperByMethodCall1;
    private final BeanWrapper liteWrapperByMethodCall2;
    private final BeanWrapper liteWrapperByInjection1;
    private final BeanWrapper liteWrapperByInjection2;
    private final HogeBean directNormalHoge;
    private final HogeBean directLiteHoge;

    public LiteModeTestController(
        BeanWrapper normalWrapperByMethodCall1,
        BeanWrapper normalWrapperByMethodCall2,
        BeanWrapper normalWrapperByInjection1,
        BeanWrapper normalWrapperByInjection2,
        BeanWrapper liteWrapperByMethodCall1,
        BeanWrapper liteWrapperByMethodCall2,
        BeanWrapper liteWrapperByInjection1,
        BeanWrapper liteWrapperByInjection2,
        HogeBean normalHoge,
        HogeBean liteHoge
    ) {
        this.normalWrapperByMethodCall1 = normalWrapperByMethodCall1;
        this.normalWrapperByMethodCall2 = normalWrapperByMethodCall2;
        this.normalWrapperByInjection1 = normalWrapperByInjection1;
        this.normalWrapperByInjection2 = normalWrapperByInjection2;
        this.liteWrapperByMethodCall1 = liteWrapperByMethodCall1;
        this.liteWrapperByMethodCall2 = liteWrapperByMethodCall2;
        this.liteWrapperByInjection1 = liteWrapperByInjection1;
        this.liteWrapperByInjection2 = liteWrapperByInjection2;
        this.directNormalHoge = normalHoge;
        this.directLiteHoge = liteHoge;
    }

    @GetMapping("/lite-mode-test")
    public String liteModeTest() {
        return Stream.concat(
            Stream.of(
                normalWrapperByMethodCall1,
                normalWrapperByMethodCall2,
                normalWrapperByInjection1,
                normalWrapperByInjection2,
                liteWrapperByMethodCall1,
                liteWrapperByMethodCall2,
                liteWrapperByInjection1,
                liteWrapperByInjection2
            ).map(wrapper -> wrapper.discribe()),
            Stream.of(
                directNormalHoge,
                directLiteHoge
            ).map(hoge -> String.format("direct: %s[%d]", hoge.getName(), hoge.getIndex()))
        )
        .map(item -> "<p>" + item + "</p>")
        .collect(Collectors.joining("", "<html><body>", "</body></html>"));
    }
}
