package com.dcservice.common;

import java.util.concurrent.ThreadFactory;

import com.dcservice.all.base.BaseBaseClass;

public class ThreadFactoryEx extends BaseBaseClass implements ThreadFactory {
  private String name;

  /**
  * 
  */
  public ThreadFactoryEx(String name) {
    this.name = name;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.concurrent.ThreadFactory#newThread(java.lang.Runnable)
   */
  @Override
  public Thread newThread(Runnable r) {
    return new Thread(r, name);
  }
}
