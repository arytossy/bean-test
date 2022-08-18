package jp.co.weserve.arimitsu.beantest;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.co.weserve.arimitsu.beantest.items.CharConditionItem;
import jp.co.weserve.arimitsu.beantest.items.ConditionItem;
import jp.co.weserve.arimitsu.beantest.items.DateConditionItem;
import jp.co.weserve.arimitsu.beantest.items.NumberConditionItem;
import jp.co.weserve.arimitsu.beantest.items.OrJoinedConditionItem;
import jp.co.weserve.arimitsu.beantest.items.StringConditionItem;

@RestController
public abstract class MyController {

    private final SimpleObject simpleObject;
    private final MyMapper mapper;

    public MyController(SimpleObject simpleObject, MyMapper mapper) {
        this.simpleObject = simpleObject;
        this.mapper = mapper;
    }

    @GetMapping("/")
    public Object home() {
        return simpleObject.callBean();
    }

    @GetMapping("/scope-test/change-state")
    public String changeState(@RequestParam String next) {
        MyPrototypeBean prototypeBean = createMyPrototypeBean();
        prototypeBean.changeState(next);
        return
            prototypeBean.getSingletonState()
            + "\n"
            + prototypeBean.getState()
        ;
    }

    @GetMapping("/scope-test/get-state")
    public String getState() {
        MyPrototypeBean prototypeBean = createMyPrototypeBean();
        return
            prototypeBean.getSingletonState()
            + "\n"
            + prototypeBean.getState()
        ;
    }

    @GetMapping("/array-test")
    public String arrayTest() {
        Map<Integer, String> myMap = new HashMap<>();
        myMap.put(3, "three");
        myMap.put(10, "ten");
        myMap.put(12, "twelve");
        return myMap.toString();
    }

    @GetMapping("/db/now")
    public String getNow() {
        return mapper.getSysDate().toString();
    }

    @GetMapping("/db/dynamic-conditions")
    public int applyDynamicConditions() {
        List<ConditionItem> conditions = new ArrayList<>();
        conditions.add(new NumberConditionItem("num_column", 5));
        conditions.add(new StringConditionItem("str_column", "fuga"));
        conditions.add(new DateConditionItem("date_column", new Date()));
        // conditions.add(new CharConditionItem("char_column", '1'));
        OrJoinedConditionItem orItems = new OrJoinedConditionItem();
        orItems.add(new CharConditionItem("char_column", '1'));
        orItems.add(new CharConditionItem("char_column", '2'));
        conditions.add(orItems);
        return mapper.countWithDynamicConditions(conditions);
    }

    @Lookup
    protected abstract MyPrototypeBean createMyPrototypeBean();
}
