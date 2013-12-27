package com.ancientshores.AncientRPG.Util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Frederik Haselmeier
 * Date: 23.02.13
 * Time: 21:18
 */
public class LinkedStringHashMap<T> extends LinkedHashMap<String, T> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public LinkedStringHashMap() {
    }

    @Override
    public boolean containsKey(Object c) {
        if (!(c instanceof String)) {
            return false;
        }
        for (Map.Entry<String, T> entry : this.entrySet()) {
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
        for (Map.Entry<String, T> entry : this.entrySet()) {
            if (entry.getKey().equalsIgnoreCase((String) c)) {
                return entry.getValue();
            }
        }
        return null;
    }
}
