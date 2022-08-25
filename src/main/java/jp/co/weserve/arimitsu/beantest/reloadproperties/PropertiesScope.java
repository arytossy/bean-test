package jp.co.weserve.arimitsu.beantest.reloadproperties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class PropertiesScope implements Scope {

    public static final String ATTRIBUTE_NAME = "PropertiesScope_ContextId";

    private PropertiesContext getContext() {

        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();

        if (attributes != null && attributes instanceof ServletRequestAttributes) {

            HttpServletRequest request = ((ServletRequestAttributes)attributes).getRequest();
            Object contextId = request.getAttribute(ATTRIBUTE_NAME);

            if (contextId != null && contextId instanceof Integer) {

                PropertiesContext context = PropertiesContextHolder.getContext((int)contextId);
                if (context != null) {
                    return context;
                }
            }
        }

        throw new IllegalStateException("No properties-context. Probably failure or missing to initialize.");
    }

    @Override
    public Object get(String name, ObjectFactory<?> factory) {

        PropertiesContext context = getContext();
        Object scopedObject = context.getObject(name);

        if (scopedObject == null) {
            scopedObject = factory.getObject();
            context.setObject(name, scopedObject);
        }
        return scopedObject;
    }

    @Override
    public String getConversationId() {
        return null;
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback) {
        getContext().registerDestructionCallback(name, callback);
    }

    @Override
    public Object remove(String name) {
        return getContext().remove(name);
    }

    @Override
    public Object resolveContextualObject(String key) {
        return null;
    }
}
