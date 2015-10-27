package com.dcservice.all.base;

import java.util.logging.Logger;

import com.ocpsoft.shade.org.apache.commons.logging.Log;
import com.ocpsoft.shade.org.apache.commons.logging.impl.LogFactoryImpl;

public class BaseBaseClass {
	private Log baseLogger = LogFactoryImpl.getFactory().getLog(this.getClass());
	
	private Log getLog(){
		return baseLogger;
	}
}
