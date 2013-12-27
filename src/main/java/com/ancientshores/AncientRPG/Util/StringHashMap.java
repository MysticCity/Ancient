package com.ancientshores.AncientRPG.Util;

import java.util.concurrent.ConcurrentHashMap;

public class StringHashMap<T> extends ConcurrentHashMap<String, T> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public StringHashMap() {
    }

    @Override
    public boolean containsKey(Object c) {
        if (!(c instanceof String)) {
            return false;
        }
        for (Entry<String, T> entry : this.entrySet()) {
            if (entry.getKey().equalsIgnoreCase((String) c)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public T get(Object c) {
        if (!(c instanceof String)) {
            return null;
        }
        for (Entry<String, T> entry : this.entrySet()) {
            if (entry.getKey().equalsIgnoreCase((String) c)) {
                return entry.getValue();
            }
        }
        return null;
    }
}
