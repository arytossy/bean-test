package jp.co.weserve.arimitsu.beantest.methodinjection;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.weserve.arimitsu.beantest.methodinjection.NormalProto.NormalProtoFactory;

@RestController
public class MethodInjectionTestController {

    private final NormalProtoFactory factory;
    private final ProxyProto apple;
    private final ProxyProto banana;
    private final ProxyProto citrus;

    public MethodInjectionTestController(
        NormalProtoFactory factory,
        ProxyProto apple,
        ProxyProto banana,
        ProxyProto citrus
    ) {
        this.factory = factory;
        this.apple = apple;
        this.banana = banana;
        this.citrus = citrus;
    }

    @GetMapping("/method-injection-test")
    public String methodInjectionTest() {

        Map<String, NormalProto> normalList = new HashMap<>();

        NormalProto ash = factory.create();
        ash.setName("ash");
        normalList.put("ash", ash);

        NormalProto brown = factory.create();
        brown.setName("brown");
        normalList.put("brown", brown);

        NormalProto choco = factory.create();
        choco.setName("choco");
        normalList.put("choco", choco);

        Map<String, ProxyProto> proxyList = new HashMap<>();

        apple.setName("apple");
        proxyList.put("apple", apple);

        banana.setName("banana");
        proxyList.put("banana", banana);

        citrus.setName("citrus");
        proxyList.put("citrus", citrus);

        Map<String, Map<String, ? extends TestProto>> result = new HashMap<>();
        result.put("Normal", normalList);
        result.put("Proxy", proxyList);

        return result.entrySet().stream()
            .map(subset -> {
                String header = String.format("<h3>%s Prototype Bean</h3>", subset.getKey());
                String body = subset.getValue().entrySet().stream()
                    .map(item -> String.format(
                        "<li>set: %s, get: %s</li>",
                        item.getKey(),
                        item.getValue().getName()))
                    .collect(Collectors.joining("", "<ul>", "</ul>"));
                return String.format("<p>%s</p>", header + body);
            })
            .collect(Collectors.joining(""));
    }
}
