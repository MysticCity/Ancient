package com.ancientshores.AncientRPG.Util;

import java.util.HashSet;

public class StringHashSet extends HashSet<String> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public StringHashSet() {
    }

    @Override
    public boolean contains(Object c) {
        if (!(c instanceof String)) {
            return false;
        }
        for (String entry : this) {
            if (entry.equalsIgnoreCase((String) c)) {
                return true;
            }
        }
        return false;
    }
}
