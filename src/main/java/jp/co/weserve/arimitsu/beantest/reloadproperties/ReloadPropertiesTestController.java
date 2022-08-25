package jp.co.weserve.arimitsu.beantest.reloadproperties;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import jp.co.weserve.arimitsu.beantest.reloadproperties.PropertiesScopeConfig.ReloadTestBean;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ReloadPropertiesTestController {

    private final ReloadTestBean reloadTestBean;

    public ReloadPropertiesTestController(ReloadTestBean reloadTestBean) {
        this.reloadTestBean = reloadTestBean;
    }

    @GetMapping("/reload-test")
    public String reloadTest() {
        return String.format("hoge: %s, fuga: %s", reloadTestBean.getHoge(), reloadTestBean.getFuga());
    }

    @GetMapping("/heavy-request/{interval}/{count}")
    public String heavyRequest(@PathVariable int interval, @PathVariable int count) throws Exception {
        for (int i = 0; i < count; i++) {
            log.info("HEAVY REQUEST ... hoge: {}, fuga: {}", reloadTestBean.getHoge(), reloadTestBean.getFuga());
            Thread.sleep((long)(interval * 1000));
        }
        return "Completed ... !";
    }
}
