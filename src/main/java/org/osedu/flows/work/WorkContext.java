package org.osedu.flows.work;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Work execution context. This can be used to pass initial parameters
 * to the workflow and share data between work units.
 * <strong>Work context instances are thread-safe.</strong>
 */
public class WorkContext {

    private final Map<String, Object> context = new ConcurrentHashMap<>();

    public void put(String key, Object value) {
        context.put(key, value);
    }

    public Object get(String key) {
        return context.get(key);
    }

    public Set<Map.Entry<String, Object>> getEntrySet() {
        return context.entrySet();
    }

    @Override
    public String toString() {
        return "WorkContext{" +
                "context=" + context +
                '}';
    }
}
