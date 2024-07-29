package com.starobject.k8s_httpsession.model;
import java.io.Serializable;
public class Counter {
    
    private int count;

    public Counter() {
        this.count = 0;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void increment() {
        this.count++;
    }

    public void reset() {
        this.count = 0;
    }
}

