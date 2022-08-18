package jp.co.weserve.arimitsu.beantest.items;

public abstract class ConditionItem {

    protected String columnName;

    public ConditionItem(String columnName) {
        this.columnName = columnName;
    }

    public abstract String toSql(String variableName, int listIndex);
}
