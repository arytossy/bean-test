package jp.co.weserve.arimitsu.beantest.items;

public class CharConditionItem extends ConditionItem {

    private Character charVal;

    public CharConditionItem(String columnName, Character charVal) {
        super(columnName);
        this.charVal = charVal;
    }

    @Override
    public String toSql(String variableName, int listIndex) {
        return String.format("%s = #{%s[%d].charVal}", this.columnName, variableName, listIndex);
    }
}
