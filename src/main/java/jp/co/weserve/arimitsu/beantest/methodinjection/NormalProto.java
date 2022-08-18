package jp.co.weserve.arimitsu.beantest.methodinjection;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class NormalProto extends TestProto {

    @Component
    public static interface NormalProtoFactory {
        @Lookup
        public NormalProto create();
    }
}
