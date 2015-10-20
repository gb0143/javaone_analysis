package com.dcservice.all.base;

import sun.rmi.runtime.Log;

public class BaseBaseClass {
    private Log baseLogger = LogFactoryImpl.getFactory().getLog(this.getClass());

    private Log getLog() {
	return baseLogger;
    }
}
