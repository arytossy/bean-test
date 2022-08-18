package jp.co.weserve.arimitsu.beantest.items;

import java.util.Date;

public class DateConditionItem extends ConditionItem {

    private Date dateVal;

    public DateConditionItem(String columnName, Date dateVal) {
        super(columnName);
        this.dateVal = dateVal;
    }

    @Override
    public String toSql(String variableName, int listIndex) {
        return String.format("%s = #{%s[%d].dateVal}", this.columnName, variableName, listIndex);
    }
}
