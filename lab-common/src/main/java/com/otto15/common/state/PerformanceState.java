package com.otto15.common.state;

import java.util.concurrent.atomic.AtomicBoolean;

public class PerformanceState {

    private final AtomicBoolean performanceStatus = new AtomicBoolean(true);

    private PerformanceState() {

    }

    public static class PerformanceStateHolder {
        public static final PerformanceState HOLDER_INSTANCE =
                new PerformanceState();
    }


    public static PerformanceState getInstance() {
        return PerformanceStateHolder.HOLDER_INSTANCE;
    }

    public boolean getPerformanceStatus() {
        return performanceStatus.get();
    }


    public void switchPerformanceStatus() {
        performanceStatus.set(!performanceStatus.get());
    }

}
