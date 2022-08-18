package jp.co.weserve.arimitsu.beantest.items;

public class NumberConditionItem extends ConditionItem {

    private Integer numVal;

    public NumberConditionItem(String columnName, Integer numVal) {
        super(columnName);
        this.numVal = numVal;
    }

    @Override
    public String toSql(String variableName, int listIndex) {
        return String.format("%s = #{%s[%d].numVal}", this.columnName, variableName, listIndex);
    }
}
