package org.bukkit.scheduler;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import org.bukkit.plugin.Plugin;

public interface BukkitScheduler {
  int scheduleSyncDelayedTask(Plugin paramPlugin, Runnable paramRunnable, long paramLong);
  
  int scheduleSyncDelayedTask(Plugin paramPlugin, Runnable paramRunnable);
  
  int scheduleSyncRepeatingTask(Plugin paramPlugin, Runnable paramRunnable, long paramLong1, long paramLong2);
  
  int scheduleAsyncDelayedTask(Plugin paramPlugin, Runnable paramRunnable, long paramLong);
  
  int scheduleAsyncDelayedTask(Plugin paramPlugin, Runnable paramRunnable);
  
  int scheduleAsyncRepeatingTask(Plugin paramPlugin, Runnable paramRunnable, long paramLong1, long paramLong2);
  
  <T> Future<T> callSyncMethod(Plugin paramPlugin, Callable<T> paramCallable);
  
  void cancelTask(int paramInt);
  
  void cancelTasks(Plugin paramPlugin);
  
  void cancelAllTasks();
  
  boolean isCurrentlyRunning(int paramInt);
  
  boolean isQueued(int paramInt);
  
  List<BukkitWorker> getActiveWorkers();
  
  List<BukkitTask> getPendingTasks();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\scheduler\BukkitScheduler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */