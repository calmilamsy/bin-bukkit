package com.avaje.ebean;

import java.util.concurrent.TimeUnit;

public interface BackgroundExecutor {
  void execute(Runnable paramRunnable);
  
  void executePeriodically(Runnable paramRunnable, long paramLong, TimeUnit paramTimeUnit);
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\BackgroundExecutor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */