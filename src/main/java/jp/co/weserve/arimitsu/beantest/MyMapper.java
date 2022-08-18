package jp.co.weserve.arimitsu.beantest;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import jp.co.weserve.arimitsu.beantest.items.ConditionItem;

@Mapper
public interface MyMapper {

    public Date getSysDate();

    @SelectProvider(type = SqlProvider.class, method = "generateSql")
    public int countWithDynamicConditions(List<ConditionItem> conditions);

    public static class SqlProvider implements ProviderMethodResolver {

        public String generateSql(List<ConditionItem> conditions) {
            // SQL sql = new SQL()
            //     .SELECT("count(*)")
            //     .FROM("test_table");
            // for (ListIterator<ConditionItem> iterator = conditions.listIterator(); iterator.hasNext();) {
            //     int index = iterator.nextIndex();
            //     ConditionItem item = iterator.next();
            //     sql.WHERE(item.toSql("conditions", index));
            // }
            // return sql.toString();

            System.out.println(MainSchemaProvider.getMainSchema());

            return new SQL() {{
                SELECT("count(*)");
                FROM("test_table");
                for (ListIterator<ConditionItem> iterator = conditions.listIterator(); iterator.hasNext();) {
                    int index = iterator.nextIndex();
                    ConditionItem item = iterator.next();
                    WHERE(item.toSql("conditions", index));
                }
            }}.toString();
        }

        @Component
        private static class MainSchemaProvider {
            private static ApplicationContext context;
            private MainSchemaProvider(ApplicationContext context) {
                MainSchemaProvider.context = context;
            }
            private static String getMainSchema() {
                MySingletonBean myBean = context.getBean(MySingletonBean.class);
                myBean.changeState(LocalDateTime.now().toString());
                return myBean.getState();
            }
        }
    }
}
