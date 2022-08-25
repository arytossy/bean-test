package jp.co.weserve.arimitsu.beantest.reloadproperties;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class PropertiesContextHolder {

    private PropertiesContextHolder() {}

    private static int id = -1;

    private static final Map<Integer, PropertiesContext> contexts
        = Collections.synchronizedMap(new HashMap<>());

    static {
        newContext();
    }

    public static synchronized void newContext() {
        int previous = id;
        contexts.put(++id, new PropertiesContext());
        closeIfDisused(previous);
    }

    public static PropertiesContext getContext(int contextId) {
        return contexts.get(contextId);
    }

    public static synchronized void startRequest(int contextId) {
        if (contexts.containsKey(contextId)) {
            contexts.get(contextId).startRequest();
        }
    }

    public static synchronized void endRequest(int contextId) {
        if (contexts.containsKey(contextId)) {
            contexts.get(contextId).endRequest();
            closeIfDisused(contextId);
        }
    }

    public static int getCurrentId() {
        return id;
    }

    private static void closeIfDisused(int contextId) {
        if (contextId == getCurrentId()) return;
        PropertiesContext context = contexts.get(contextId);
        if (context != null && context.getRequestCount() <= 0) {
            context.close();
            contexts.remove(contextId);
        }
    }
}
