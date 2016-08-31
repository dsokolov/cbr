package me.ilich.cbr.model;

import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

public class Cache {

    @Nullable
    private ValCurs valCurs = null;

    public void replace(ValCurs v) {
        valCurs = v;
    }

    @Nullable
    public ValCurs get() {
        return valCurs;
    }

    @VisibleForTesting
    public void clear() {
        valCurs = null;
    }

    public boolean contains() {
        return valCurs != null;
    }

}
