package com.otto15.common.network;

import com.otto15.common.state.PerformanceState;

import java.io.IOException;

public interface NetworkListener {

    Response listen(Request request) throws IOException;
    PerformanceState getPerformanceState();

}
