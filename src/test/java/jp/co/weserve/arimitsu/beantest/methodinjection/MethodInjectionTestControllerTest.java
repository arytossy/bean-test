package jp.co.weserve.arimitsu.beantest.methodinjection;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jp.co.weserve.arimitsu.beantest.methodinjection.NormalProto.NormalProtoFactory;

@ExtendWith(MockitoExtension.class)
public class MethodInjectionTestControllerTest {

    @Mock
    private NormalProtoFactory factory;

    @Mock
    private ProxyProto apple;

    @Mock
    private ProxyProto banana;

    @Mock
    private ProxyProto citrus;

    @InjectMocks
    private MethodInjectionTestController controller;

    private final List<NormalProto> normalProtos = new ArrayList<>();

    @BeforeEach
    void setup() {
        when(factory.create()).thenAnswer(invocation -> {
            NormalProto mocked = mock(NormalProto.class);
            normalProtos.add(mocked);
            return mocked;
        });
    }

    @Test
    void testMethodInjectionTest() {
        for (
            ListIterator<NormalProto> iterator = normalProtos.listIterator();
            iterator.hasNext();
        ) {
            int index = iterator.nextIndex();
            NormalProto mock = iterator.next();
            when(mock.getName()).thenReturn("" + index);
        }
        System.out.println(controller.methodInjectionTest());
    }
}
