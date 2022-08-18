package jp.co.weserve.arimitsu.beantest.items;

public class StringConditionItem extends ConditionItem {

    private String strVal;

    public StringConditionItem(String columnName, String strVal) {
        super(columnName);
        this.strVal = strVal;
    }

    @Override
    public String toSql(String variableName, int listIndex) {
        return String.format("%s = #{%s[%d].strVal}", this.columnName, variableName, listIndex);
    }
}
