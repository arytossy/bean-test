package jp.co.weserve.arimitsu.beantest.reloadproperties;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

public class PropertiesContext {

    private final Map<String, Object> objects = new HashMap<>();

    private final Map<String, Runnable> destructionCallbacks = new HashMap<>();

    @Getter
    private int requestCount = 0;

    public synchronized void startRequest() {
        this.requestCount++;
    }

    public synchronized void endRequest() {
        this.requestCount--;
    }

    public synchronized Object getObject(String name) {
        if (this.objects.containsKey(name)) {
            return objects.get(name);
        } else {
            return null;
        }
    }

    public synchronized void setObject(String name, Object object) {
        this.objects.put(name, object);
    }

    public synchronized void registerDestructionCallback(String name, Runnable callback) {
        this.destructionCallbacks.put(name, callback);
    }

    public synchronized Object remove(String name) {
        if (this.destructionCallbacks.containsKey(name)) {
            this.destructionCallbacks.remove(name);
        }
        if (this.objects.containsKey(name)) {
            return this.objects.remove(name);
        } else {
            return null;
        }
    }

    public synchronized void close() {
        this.destructionCallbacks.values().forEach(Runnable::run);
        this.objects.clear();
        this.destructionCallbacks.clear();
    }
}
