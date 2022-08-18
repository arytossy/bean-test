package jp.co.weserve.arimitsu.beantest.items;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

public class OrJoinedConditionItem extends ConditionItem {

    private final List<ConditionItem> list = new ArrayList<>();

    public OrJoinedConditionItem() {
        super(null);
    }

    @Override
    public String toSql(String variableName, int listIndex) {
        List<String> sqlItems = new ArrayList<>();
        for (
            ListIterator<ConditionItem> iterator = this.list.listIterator();
            iterator.hasNext();
        ) {
            int index = iterator.nextIndex();
            ConditionItem item = iterator.next();
            sqlItems.add(item.toSql(String.format("%s[%d].list", variableName, listIndex), index));
        }
        return sqlItems.stream().collect(Collectors.joining(" OR ", "(", ")"));
    }

    public void add(ConditionItem item) {
        this.list.add(item);
    }
}
