/*      */ package org.ibex.nestedvm;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.net.InetAddress;
/*      */ import org.ibex.nestedvm.util.Seekable;
/*      */ 
/*      */ public abstract class UnixRuntime extends Runtime implements Cloneable {
/*      */   private int pid;
/*      */   private UnixRuntime parent;
/*      */   private static final GlobalState defaultGS;
/*      */   private GlobalState gs;
/*      */   private String cwd;
/*      */   private UnixRuntime execedRuntime;
/*      */   private Object children;
/*      */   private Vector activeChildren;
/*      */   private Vector exitedChildren;
/*      */   private static final Method runtimeCompilerCompile;
/*      */   
/*   19 */   public final int getPid() { return this.pid; }
/*      */   static  { Object object;
/*   21 */     defaultGS = new GlobalState();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  400 */       object = Class.forName("org.ibex.nestedvm.RuntimeCompiler").getMethod("compile", new Class[] { Seekable.class, String.class, String.class });
/*  401 */     } catch (NoSuchMethodException noSuchMethodException) {
/*  402 */       object = null;
/*  403 */     } catch (ClassNotFoundException classNotFoundException) {
/*  404 */       object = null;
/*      */     } 
/*  406 */     runtimeCompilerCompile = object; }
/*      */   public void setGlobalState(GlobalState paramGlobalState) { if (this.state != 1) throw new IllegalStateException("can't change GlobalState when running");  if (paramGlobalState == null) throw new NullPointerException("gs is null");  this.gs = paramGlobalState; }
/*      */   protected UnixRuntime(int paramInt1, int paramInt2) { this(paramInt1, paramInt2, false); }
/*      */   protected UnixRuntime(int paramInt1, int paramInt2, boolean paramBoolean) { super(paramInt1, paramInt2, paramBoolean); if (!paramBoolean) { this.gs = defaultGS; String str = Platform.getProperty("user.dir"); this.cwd = (str == null) ? null : this.gs.mapHostPath(str); if (this.cwd == null) this.cwd = "/";  this.cwd = this.cwd.substring(1); }  } private static String posixTZ() { StringBuffer stringBuffer = new StringBuffer(); TimeZone timeZone = TimeZone.getDefault(); int i = timeZone.getRawOffset() / 1000; stringBuffer.append(Platform.timeZoneGetDisplayName(timeZone, false, false)); if (i > 0) { stringBuffer.append("-"); } else { i = -i; }  stringBuffer.append(i / 3600); i %= 3600; if (i > 0) stringBuffer.append(":").append(i / 60);  i %= 60; if (i > 0) stringBuffer.append(":").append(i);  if (timeZone.useDaylightTime()) stringBuffer.append(Platform.timeZoneGetDisplayName(timeZone, true, false));  return stringBuffer.toString(); } private static boolean envHas(String paramString, String[] paramArrayOfString) { for (byte b = 0; b < paramArrayOfString.length; b++) { if (paramArrayOfString[b] != null && paramArrayOfString[b].startsWith(paramString + "=")) return true;  }  return false; } String[] createEnv(String[] paramArrayOfString) { String[] arrayOfString1 = new String[7]; int i = 0; if (paramArrayOfString == null) paramArrayOfString = new String[0];  if (!envHas("USER", paramArrayOfString) && Platform.getProperty("user.name") != null) arrayOfString1[i++] = "USER=" + Platform.getProperty("user.name");  String str; if (!envHas("HOME", paramArrayOfString) && (str = Platform.getProperty("user.home")) != null && (str = this.gs.mapHostPath(str)) != null) arrayOfString1[i++] = "HOME=" + str;  if (!envHas("TMPDIR", paramArrayOfString) && (str = Platform.getProperty("java.io.tmpdir")) != null && (str = this.gs.mapHostPath(str)) != null) arrayOfString1[i++] = "TMPDIR=" + str;  if (!envHas("SHELL", paramArrayOfString)) arrayOfString1[i++] = "SHELL=/bin/sh";  if (!envHas("TERM", paramArrayOfString) && !win32Hacks) arrayOfString1[i++] = "TERM=vt100";  if (!envHas("TZ", paramArrayOfString)) arrayOfString1[i++] = "TZ=" + posixTZ();  if (!envHas("PATH", paramArrayOfString)) arrayOfString1[i++] = "PATH=/usr/local/bin:/usr/bin:/bin:/usr/local/sbin:/usr/sbin:/sbin";  String[] arrayOfString2 = new String[paramArrayOfString.length + i]; byte b; for (b = 0; b < i; ) { arrayOfString2[b] = arrayOfString1[b]; b++; }  for (b = 0; b < paramArrayOfString.length; ) { arrayOfString2[i++] = paramArrayOfString[b]; b++; }  return arrayOfString2; } private static class ProcessTableFullExn extends RuntimeException {
/*  410 */     private ProcessTableFullExn() {} } void _started() { UnixRuntime[] arrayOfUnixRuntime = this.gs.tasks; synchronized (this.gs) { if (this.pid != 0) { UnixRuntime unixRuntime = arrayOfUnixRuntime[this.pid]; if (unixRuntime == null || unixRuntime == this || unixRuntime.pid != this.pid || unixRuntime.parent != this.parent) throw new Error("should never happen");  synchronized (this.parent.children) { int i = this.parent.activeChildren.indexOf(unixRuntime); if (i == -1) throw new Error("should never happen");  this.parent.activeChildren.setElementAt(this, i); }  } else { int i = -1; int j = this.gs.nextPID; int k; for (k = j; k < arrayOfUnixRuntime.length; ) { if (arrayOfUnixRuntime[k] == null) { i = k; break; }  k++; }  if (i == -1) for (k = 1; k < j; ) { if (arrayOfUnixRuntime[k] == null) { i = k; break; }  k++; }   if (i == -1) throw new ProcessTableFullExn(null);  this.pid = i; this.gs.nextPID = i + 1; }  arrayOfUnixRuntime[this.pid] = this; }  } int _syscall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7) throws Runtime.ErrnoException, Runtime.FaultException { switch (paramInt1) { case 11: return sys_kill(paramInt2, paramInt3);case 25: return sys_fork();case 23: return sys_pipe(paramInt2);case 24: return sys_dup2(paramInt2, paramInt3);case 39: return sys_dup(paramInt2);case 26: return sys_waitpid(paramInt2, paramInt3, paramInt4);case 14: return sys_stat(paramInt2, paramInt3);case 33: return sys_lstat(paramInt2, paramInt3);case 18: return sys_mkdir(paramInt2, paramInt3);case 27: return sys_getcwd(paramInt2, paramInt3);case 22: return sys_chdir(paramInt2);case 28: return sys_exec(paramInt2, paramInt3, paramInt4);case 36: return sys_getdents(paramInt2, paramInt3, paramInt4, paramInt5);case 20: return sys_unlink(paramInt2);case 46: return sys_getppid();case 56: return sys_socket(paramInt2, paramInt3, paramInt4);case 57: return sys_connect(paramInt2, paramInt3, paramInt4);case 58: return sys_resolve_hostname(paramInt2, paramInt3, paramInt4);case 60: return sys_setsockopt(paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);case 61: return sys_getsockopt(paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);case 63: return sys_bind(paramInt2, paramInt3, paramInt4);case 62: return sys_listen(paramInt2, paramInt3);case 59: return sys_accept(paramInt2, paramInt3, paramInt4);case 64: return sys_shutdown(paramInt2, paramInt3);case 53: return sys_sysctl(paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7);case 65: return sys_sendto(paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7);case 66: return sys_recvfrom(paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7);case 67: return sys_select(paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);case 78: return sys_access(paramInt2, paramInt3);case 52: return sys_realpath(paramInt2, paramInt3);case 76: return sys_chown(paramInt2, paramInt3, paramInt4);case 43: return sys_chown(paramInt2, paramInt3, paramInt4);case 77: return sys_fchown(paramInt2, paramInt3, paramInt4);case 74: return sys_chmod(paramInt2, paramInt3, paramInt4);case 75: return sys_fchmod(paramInt2, paramInt3, paramInt4);case 29: return sys_fcntl_lock(paramInt2, paramInt3, paramInt4);case 73: return sys_umask(paramInt2); }  return super._syscall(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7); } Runtime.FD _open(String paramString, int paramInt1, int paramInt2) throws Runtime.ErrnoException { paramString = normalizePath(paramString); Runtime.FD fD = this.gs.open(this, paramString, paramInt1, paramInt2); if (fD != null && paramString != null) fD.setNormalizedPath(paramString);  return fD; } private int sys_getppid() { return (this.parent == null) ? 1 : this.parent.pid; } private int sys_chown(int paramInt1, int paramInt2, int paramInt3) { return 0; } private int sys_lchown(int paramInt1, int paramInt2, int paramInt3) { return 0; } private int sys_fchown(int paramInt1, int paramInt2, int paramInt3) { return 0; } private int sys_chmod(int paramInt1, int paramInt2, int paramInt3) { return 0; } public Class runtimeCompile(Seekable paramSeekable, String paramString) throws IOException { if (runtimeCompilerCompile == null) {
/*  411 */       System.err.println("WARNING: Exec attempted but RuntimeCompiler not found!");
/*  412 */       return null;
/*      */     } 
/*      */ 
/*      */     
/*  416 */     try { return (Class)runtimeCompilerCompile.invoke(null, new Object[] { paramSeekable, "unixruntime,maxinsnpermethod=256,lessconstants", paramString }); }
/*  417 */     catch (IllegalAccessException illegalAccessException)
/*  418 */     { illegalAccessException.printStackTrace();
/*  419 */       return null; }
/*  420 */     catch (InvocationTargetException invocationTargetException)
/*  421 */     { Throwable throwable = invocationTargetException.getTargetException();
/*  422 */       if (throwable instanceof IOException) throw (IOException)throwable; 
/*  423 */       if (throwable instanceof RuntimeException) throw (RuntimeException)throwable; 
/*  424 */       if (throwable instanceof Error) throw (Error)throwable; 
/*  425 */       throwable.printStackTrace();
/*  426 */       return null; }  }
/*      */   private int sys_fchmod(int paramInt1, int paramInt2, int paramInt3) { return 0; }
/*      */   private int sys_umask(int paramInt) { return 0; }
/*      */   private int sys_access(int paramInt1, int paramInt2) throws Runtime.ErrnoException, Runtime.ReadFaultException { return (this.gs.stat(this, cstring(paramInt1)) == null) ? -2 : 0; }
/*      */   private int sys_realpath(int paramInt1, int paramInt2) throws Runtime.ErrnoException, Runtime.ReadFaultException { String str = normalizePath(cstring(paramInt1)); byte[] arrayOfByte = getNullTerminatedBytes(str); if (arrayOfByte.length > 1024) return -34;  copyout(arrayOfByte, paramInt2, arrayOfByte.length); return 0; } private int sys_kill(int paramInt1, int paramInt2) throws Runtime.ErrnoException, Runtime.ReadFaultException { if (paramInt1 != paramInt1) return -3;  if (paramInt2 < 0 || paramInt2 >= 32) return -22;  switch (paramInt2) { case 0: return 0;case 17: case 18: case 19: case 20: case 21: case 22: case 23: case 28: break;default: exit(128 + paramInt2, true); break; }  return 0; } private int sys_waitpid(int paramInt1, int paramInt2, int paramInt3) { if ((paramInt3 & 0xFFFFFFFE) != 0) return -22;  if (paramInt1 == 0 || paramInt1 < -1) { System.err.println("WARNING: waitpid called with a pid of " + paramInt1); return -10; }  boolean bool = ((paramInt3 & true) == 0) ? 1 : 0; if (paramInt1 != -1 && (paramInt1 <= 0 || paramInt1 >= this.gs.tasks.length)) return -10;  if (this.children == null) return bool ? -10 : 0;  UnixRuntime unixRuntime = null; synchronized (this.children) { while (true) { if (paramInt1 == -1) { if (this.exitedChildren.size() > 0) { unixRuntime = (UnixRuntime)this.exitedChildren.elementAt(this.exitedChildren.size() - 1); this.exitedChildren.removeElementAt(this.exitedChildren.size() - 1); }  } else if (paramInt1 > 0) { if (paramInt1 >= this.gs.tasks.length) return -10;  UnixRuntime unixRuntime1 = this.gs.tasks[paramInt1]; if (unixRuntime1.parent != this) return -10;  if (unixRuntime1.state == 4) { if (!this.exitedChildren.removeElement(unixRuntime1)) throw new Error("should never happen");  unixRuntime = unixRuntime1; }  } else { throw new Error("should never happen"); }  if (unixRuntime == null) { if (!bool) return 0;  try { this.children.wait(); continue; } catch (InterruptedException interruptedException) { continue; }  }  break; }  this.gs.tasks[unixRuntime.pid] = null; }  if (paramInt2 != 0) memWrite(paramInt2, unixRuntime.exitStatus() << 8);  return unixRuntime.pid; } void _exited() { if (this.children != null) synchronized (this.children) { Enumeration enumeration; for (enumeration = this.exitedChildren.elements(); enumeration.hasMoreElements(); ) { UnixRuntime unixRuntime1 = (UnixRuntime)enumeration.nextElement(); this.gs.tasks[unixRuntime1.pid] = null; }  this.exitedChildren.removeAllElements(); for (enumeration = this.activeChildren.elements(); enumeration.hasMoreElements(); ) { UnixRuntime unixRuntime1 = (UnixRuntime)enumeration.nextElement(); unixRuntime1.parent = null; }  this.activeChildren.removeAllElements(); }   UnixRuntime unixRuntime = this.parent; if (unixRuntime == null) { this.gs.tasks[this.pid] = null; } else { synchronized (unixRuntime.children) { if (this.parent == null) { this.gs.tasks[this.pid] = null; } else { if (!this.parent.activeChildren.removeElement(this)) throw new Error("should never happen _exited: pid: " + this.pid);  this.parent.exitedChildren.addElement(this); this.parent.children.notify(); }  }  }  } protected Object clone() throws CloneNotSupportedException { UnixRuntime unixRuntime = (UnixRuntime)super.clone(); unixRuntime.pid = 0; unixRuntime.parent = null; unixRuntime.children = null; unixRuntime.activeChildren = unixRuntime.exitedChildren = null; return unixRuntime; } private int sys_fork() { UnixRuntime unixRuntime; try { unixRuntime = (UnixRuntime)clone(); } catch (Exception exception) { exception.printStackTrace(); return -12; }  unixRuntime.parent = this; try { unixRuntime._started(); } catch (ProcessTableFullExn processTableFullExn) { return -12; }  if (this.children == null) { this.children = new Object(); this.activeChildren = new Vector(); this.exitedChildren = new Vector(); }  this.activeChildren.addElement(unixRuntime); Runtime.CPUState cPUState = new Runtime.CPUState(); getCPUState(cPUState); cPUState.r[2] = 0; cPUState.pc += 4; unixRuntime.setCPUState(cPUState); unixRuntime.state = 2; new ForkedProcess(unixRuntime); return unixRuntime.pid; } public static final class ForkedProcess extends Thread {
/*  431 */     private final UnixRuntime initial; public ForkedProcess(UnixRuntime param1UnixRuntime) { this.initial = param1UnixRuntime; start(); } public void run() { UnixRuntime.executeAndExec(this.initial); } } public static int runAndExec(UnixRuntime paramUnixRuntime, String paramString, String[] paramArrayOfString) { return runAndExec(paramUnixRuntime, concatArgv(paramString, paramArrayOfString)); } public static int runAndExec(UnixRuntime paramUnixRuntime, String[] paramArrayOfString) { paramUnixRuntime.start(paramArrayOfString); return executeAndExec(paramUnixRuntime); } public static int executeAndExec(UnixRuntime paramUnixRuntime) { while (true) { while (paramUnixRuntime.execute()) { if (paramUnixRuntime.state != 5) return paramUnixRuntime.exitStatus();  paramUnixRuntime = paramUnixRuntime.execedRuntime; }  System.err.println("WARNING: Pause requested while executing runAndExec()"); }  } private String[] readStringArray(int paramInt) throws Runtime.ReadFaultException { byte b1 = 0; for (null = paramInt; memRead(null) != 0; ) { b1++; null += 4; }  String[] arrayOfString = new String[b1]; byte b2; int i; for (b2 = 0, i = paramInt; b2 < b1; ) { arrayOfString[b2] = cstring(memRead(i)); b2++; i += 4; }  return arrayOfString; } private int sys_exec(int paramInt1, int paramInt2, int paramInt3) { return exec(normalizePath(cstring(paramInt1)), readStringArray(paramInt2), readStringArray(paramInt3)); } private int exec(String paramString, String[] paramArrayOfString1, String[] paramArrayOfString2) throws Runtime.ErrnoException { if (paramArrayOfString1.length == 0) paramArrayOfString1 = new String[] { "" };
/*      */     
/*  433 */     if (paramString.equals("bin/busybox") && getClass().getName().endsWith("BusyBox")) {
/*  434 */       return execClass(getClass(), paramArrayOfString1, paramArrayOfString2);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  442 */     Runtime.FStat fStat = this.gs.stat(this, paramString);
/*  443 */     if (fStat == null) return -2; 
/*  444 */     GlobalState.CacheEnt cacheEnt = (GlobalState.CacheEnt)this.gs.execCache.get(paramString);
/*  445 */     long l1 = fStat.mtime();
/*  446 */     long l2 = fStat.size();
/*  447 */     if (cacheEnt != null) {
/*      */       
/*  449 */       if (cacheEnt.time == l1 && cacheEnt.size == l2) {
/*  450 */         if (cacheEnt.o instanceof Class)
/*  451 */           return execClass((Class)cacheEnt.o, paramArrayOfString1, paramArrayOfString2); 
/*  452 */         if (cacheEnt.o instanceof String[])
/*  453 */           return execScript(paramString, (String[])cacheEnt.o, paramArrayOfString1, paramArrayOfString2); 
/*  454 */         throw new Error("should never happen");
/*      */       } 
/*      */       
/*  457 */       this.gs.execCache.remove(paramString);
/*      */     } 
/*      */     
/*  460 */     fD = this.gs.open(this, paramString, 0, 0);
/*  461 */     if (fD == null) throw new Runtime.ErrnoException(2); 
/*  462 */     Seekable seekable = fD.seekable();
/*  463 */     if (seekable == null) throw new Runtime.ErrnoException(13);
/*      */     
/*  465 */     byte[] arrayOfByte = new byte[4096]; try {
/*      */       String[] arrayOfString; byte b2; int j; byte b1;
/*      */       Class clazz;
/*  468 */       int i = seekable.read(arrayOfByte, 0, arrayOfByte.length);
/*  469 */       if (i == -1) throw new Runtime.ErrnoException(8);
/*      */       
/*  471 */       switch (arrayOfByte[0]) {
/*      */         case 127:
/*  473 */           if (i < 4) seekable.tryReadFully(arrayOfByte, i, 4 - i); 
/*  474 */           if (arrayOfByte[1] != 69 || arrayOfByte[2] != 76 || arrayOfByte[3] != 70) return -8; 
/*  475 */           seekable.seek(0);
/*  476 */           System.err.println("Running RuntimeCompiler for " + paramString);
/*  477 */           clazz = runtimeCompile(seekable, paramString);
/*  478 */           System.err.println("RuntimeCompiler finished for " + paramString);
/*  479 */           if (clazz == null) throw new Runtime.ErrnoException(8); 
/*  480 */           this.gs.execCache.put(paramString, new GlobalState.CacheEnt(l1, l2, clazz));
/*  481 */           return execClass(clazz, paramArrayOfString1, paramArrayOfString2);
/*      */         case 35:
/*  483 */           if (i == 1) {
/*  484 */             null = seekable.read(arrayOfByte, 1, arrayOfByte.length - 1);
/*  485 */             if (null == -1) return -8; 
/*  486 */             i += null;
/*      */           } 
/*  488 */           if (arrayOfByte[1] != 33) return -8; 
/*  489 */           null = 2;
/*  490 */           i -= 2;
/*      */           label94: while (true) {
/*  492 */             for (int k = null; k < null + i; ) { if (arrayOfByte[k] == 10) { null = k; break label94; }  k++; }
/*  493 */              null += i;
/*  494 */             if (null == arrayOfByte.length)
/*  495 */               break;  i = seekable.read(arrayOfByte, null, arrayOfByte.length - null);
/*      */           } 
/*  497 */           b1 = 2;
/*  498 */           for (; b1 < null && arrayOfByte[b1] == 32; b1++);
/*  499 */           if (b1 == null) throw new Runtime.ErrnoException(8); 
/*  500 */           j = b1;
/*  501 */           for (; j < null && arrayOfByte[j] != 32; j++);
/*  502 */           b2 = j;
/*  503 */           for (; j < null && arrayOfByte[j] == 32; j++);
/*  504 */           arrayOfString = new String[] { new String(arrayOfByte, b1, b2 - b1), (j < null) ? new String(arrayOfByte, j, null - j) : null };
/*      */ 
/*      */ 
/*      */           
/*  508 */           this.gs.execCache.put(paramString, new GlobalState.CacheEnt(l1, l2, arrayOfString));
/*  509 */           return execScript(paramString, arrayOfString, paramArrayOfString1, paramArrayOfString2);
/*      */       } 
/*  511 */       return -8;
/*      */     }
/*  513 */     catch (IOException iOException) {
/*  514 */       return -5;
/*      */     } finally {
/*  516 */       fD.close();
/*      */     }  }
/*      */ 
/*      */   
/*      */   public int execScript(String paramString, String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3) throws Runtime.ErrnoException {
/*  521 */     String[] arrayOfString = new String[paramArrayOfString2.length - 1 + ((paramArrayOfString1[true] != null) ? 3 : 2)];
/*  522 */     int i = paramArrayOfString1[0].lastIndexOf('/');
/*  523 */     arrayOfString[0] = (i == -1) ? paramArrayOfString1[0] : paramArrayOfString1[0].substring(i + 1);
/*  524 */     arrayOfString[1] = "/" + paramString;
/*  525 */     i = 2;
/*  526 */     if (paramArrayOfString1[true] != null) arrayOfString[i++] = paramArrayOfString1[1];  byte b;
/*  527 */     for (b = 1; b < paramArrayOfString2.length; ) { arrayOfString[i++] = paramArrayOfString2[b]; b++; }
/*  528 */      if (i != arrayOfString.length) throw new Error("p != newArgv.length"); 
/*  529 */     System.err.println("Execing: " + paramArrayOfString1[0]);
/*  530 */     for (b = 0; b < arrayOfString.length; ) { System.err.println("execing [" + b + "] " + arrayOfString[b]); b++; }
/*  531 */      return exec(paramArrayOfString1[0], arrayOfString, paramArrayOfString3);
/*      */   }
/*      */   
/*      */   public int execClass(Class paramClass, String[] paramArrayOfString1, String[] paramArrayOfString2) {
/*      */     try {
/*  536 */       UnixRuntime unixRuntime = (UnixRuntime)paramClass.getDeclaredConstructor(new Class[] { boolean.class }).newInstance(new Object[] { Boolean.TRUE });
/*  537 */       return exec(unixRuntime, paramArrayOfString1, paramArrayOfString2);
/*  538 */     } catch (Exception exception) {
/*  539 */       exception.printStackTrace();
/*  540 */       return -8;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private int exec(UnixRuntime paramUnixRuntime, String[] paramArrayOfString1, String[] paramArrayOfString2) {
/*  546 */     for (byte b = 0; b < 64; ) { if (this.closeOnExec[b]) closeFD(b);  b++; }
/*  547 */      paramUnixRuntime.fds = this.fds;
/*  548 */     paramUnixRuntime.closeOnExec = this.closeOnExec;
/*      */     
/*  550 */     this.fds = null;
/*  551 */     this.closeOnExec = null;
/*      */     
/*  553 */     paramUnixRuntime.gs = this.gs;
/*  554 */     paramUnixRuntime.sm = this.sm;
/*  555 */     paramUnixRuntime.cwd = this.cwd;
/*  556 */     paramUnixRuntime.pid = this.pid;
/*  557 */     paramUnixRuntime.parent = this.parent;
/*  558 */     paramUnixRuntime.start(paramArrayOfString1, paramArrayOfString2);
/*      */     
/*  560 */     this.state = 5;
/*  561 */     this.execedRuntime = paramUnixRuntime;
/*      */     
/*  563 */     return 0;
/*      */   }
/*      */   
/*      */   static class Pipe {
/*  567 */     private final byte[] pipebuf = new byte[2048];
/*      */     
/*      */     private int readPos;
/*      */     private int writePos;
/*  571 */     public final Runtime.FD reader = new Reader(this);
/*  572 */     public final Runtime.FD writer = new Writer(this);
/*      */     public class Reader extends Runtime.FD { private final UnixRuntime.Pipe this$0;
/*  574 */       public Reader(UnixRuntime.Pipe this$0) { this.this$0 = this$0; }
/*  575 */       protected Runtime.FStat _fstat() { return new Runtime.SocketFStat(); }
/*      */       public int read(byte[] param2ArrayOfByte, int param2Int1, int param2Int2) {
/*  577 */         if (param2Int2 == 0) return 0; 
/*  578 */         synchronized (this.this$0) {
/*  579 */           while (this.this$0.writePos != -1 && this.this$0.readPos == this.this$0.writePos) { 
/*  580 */             try { this.this$0.wait(); } catch (InterruptedException interruptedException) {} }
/*      */           
/*  582 */           if (this.this$0.writePos == -1) return 0; 
/*  583 */           param2Int2 = Math.min(param2Int2, this.this$0.writePos - this.this$0.readPos);
/*  584 */           System.arraycopy(this.this$0.pipebuf, this.this$0.readPos, param2ArrayOfByte, param2Int1, param2Int2);
/*  585 */           this.this$0.readPos += param2Int2;
/*  586 */           if (this.this$0.readPos == this.this$0.writePos) this.this$0.notify(); 
/*  587 */           return param2Int2;
/*      */         } 
/*      */       }
/*  590 */       public int flags() { return 0; }
/*  591 */       public void _close() { synchronized (this.this$0) { this.this$0.readPos = -1; this.this$0.notify(); }
/*      */          } }
/*      */     public class Writer extends Runtime.FD { private final UnixRuntime.Pipe this$0;
/*  594 */       public Writer(UnixRuntime.Pipe this$0) { this.this$0 = this$0; }
/*  595 */       protected Runtime.FStat _fstat() { return new Runtime.SocketFStat(); }
/*      */       public int write(byte[] param2ArrayOfByte, int param2Int1, int param2Int2) {
/*  597 */         if (param2Int2 == 0) return 0; 
/*  598 */         synchronized (this.this$0) {
/*  599 */           if (this.this$0.readPos == -1) throw new Runtime.ErrnoException(32); 
/*  600 */           if (this.this$0.pipebuf.length - this.this$0.writePos < Math.min(param2Int2, 512)) {
/*      */             
/*  602 */             while (this.this$0.readPos != -1 && this.this$0.readPos != this.this$0.writePos) { 
/*  603 */               try { this.this$0.wait(); } catch (InterruptedException interruptedException) {} }
/*      */             
/*  605 */             if (this.this$0.readPos == -1) throw new Runtime.ErrnoException(32); 
/*  606 */             this.this$0.readPos = this.this$0.writePos = 0;
/*      */           } 
/*  608 */           param2Int2 = Math.min(param2Int2, this.this$0.pipebuf.length - this.this$0.writePos);
/*  609 */           System.arraycopy(param2ArrayOfByte, param2Int1, this.this$0.pipebuf, this.this$0.writePos, param2Int2);
/*  610 */           if (this.this$0.readPos == this.this$0.writePos) this.this$0.notify(); 
/*  611 */           this.this$0.writePos += param2Int2;
/*  612 */           return param2Int2;
/*      */         } 
/*      */       }
/*  615 */       public int flags() { return 1; }
/*  616 */       public void _close() { synchronized (this.this$0) { this.this$0.writePos = -1; this.this$0.notify(); }
/*      */          } }
/*      */   }
/*      */   
/*      */   private int sys_pipe(int paramInt) {
/*  621 */     Pipe pipe = new Pipe();
/*      */     
/*  623 */     int i = addFD(pipe.reader);
/*  624 */     if (i < 0) return -23; 
/*  625 */     int j = addFD(pipe.writer);
/*  626 */     if (j < 0) { closeFD(i); return -23; }
/*      */     
/*      */     try {
/*  629 */       memWrite(paramInt, i);
/*  630 */       memWrite(paramInt + 4, j);
/*  631 */     } catch (FaultException faultException) {
/*  632 */       closeFD(i);
/*  633 */       closeFD(j);
/*  634 */       return -14;
/*      */     } 
/*  636 */     return 0;
/*      */   }
/*      */   
/*      */   private int sys_dup2(int paramInt1, int paramInt2) throws Runtime.ErrnoException, Runtime.ReadFaultException {
/*  640 */     if (paramInt1 == paramInt2) return 0; 
/*  641 */     if (paramInt1 < 0 || paramInt1 >= 64) return -81; 
/*  642 */     if (paramInt2 < 0 || paramInt2 >= 64) return -81; 
/*  643 */     if (this.fds[paramInt1] == null) return -81; 
/*  644 */     if (this.fds[paramInt2] != null) this.fds[paramInt2].close(); 
/*  645 */     this.fds[paramInt2] = this.fds[paramInt1].dup();
/*  646 */     return 0;
/*      */   }
/*      */   
/*      */   private int sys_dup(int paramInt) {
/*  650 */     if (paramInt < 0 || paramInt >= 64) return -81; 
/*  651 */     if (this.fds[paramInt] == null) return -81; 
/*  652 */     Runtime.FD fD = this.fds[paramInt].dup();
/*  653 */     int i = addFD(fD);
/*  654 */     if (i < 0) { fD.close(); return -23; }
/*  655 */      return i;
/*      */   }
/*      */   
/*      */   private int sys_stat(int paramInt1, int paramInt2) throws Runtime.ErrnoException, Runtime.ReadFaultException {
/*  659 */     Runtime.FStat fStat = this.gs.stat(this, normalizePath(cstring(paramInt1)));
/*  660 */     if (fStat == null) return -2; 
/*  661 */     return stat(fStat, paramInt2);
/*      */   }
/*      */   
/*      */   private int sys_lstat(int paramInt1, int paramInt2) throws Runtime.ErrnoException, Runtime.ReadFaultException {
/*  665 */     Runtime.FStat fStat = this.gs.lstat(this, normalizePath(cstring(paramInt1)));
/*  666 */     if (fStat == null) return -2; 
/*  667 */     return stat(fStat, paramInt2);
/*      */   }
/*      */   
/*      */   private int sys_mkdir(int paramInt1, int paramInt2) throws Runtime.ErrnoException, Runtime.ReadFaultException {
/*  671 */     this.gs.mkdir(this, normalizePath(cstring(paramInt1)), paramInt2);
/*  672 */     return 0;
/*      */   }
/*      */   
/*      */   private int sys_unlink(int paramInt) {
/*  676 */     this.gs.unlink(this, normalizePath(cstring(paramInt)));
/*  677 */     return 0;
/*      */   }
/*      */   
/*      */   private int sys_getcwd(int paramInt1, int paramInt2) throws Runtime.ErrnoException, Runtime.ReadFaultException {
/*  681 */     byte[] arrayOfByte = getBytes(this.cwd);
/*  682 */     if (paramInt2 == 0) return -22; 
/*  683 */     if (paramInt2 < arrayOfByte.length + 2) return -34; 
/*  684 */     memset(paramInt1, 47, 1);
/*  685 */     copyout(arrayOfByte, paramInt1 + 1, arrayOfByte.length);
/*  686 */     memset(paramInt1 + arrayOfByte.length + 1, 0, 1);
/*  687 */     return paramInt1;
/*      */   }
/*      */   
/*      */   private int sys_chdir(int paramInt) {
/*  691 */     String str = normalizePath(cstring(paramInt));
/*  692 */     Runtime.FStat fStat = this.gs.stat(this, str);
/*  693 */     if (fStat == null) return -2; 
/*  694 */     if (fStat.type() != 16384) return -20; 
/*  695 */     this.cwd = str;
/*  696 */     return 0;
/*      */   }
/*      */   
/*      */   private int sys_getdents(int paramInt1, int paramInt2, int paramInt3, int paramInt4) throws Runtime.FaultException, Runtime.ErrnoException {
/*  700 */     paramInt3 = Math.min(paramInt3, 16776192);
/*  701 */     if (paramInt1 < 0 || paramInt1 >= 64) return -81; 
/*  702 */     if (this.fds[paramInt1] == null) return -81; 
/*  703 */     byte[] arrayOfByte = byteBuf(paramInt3);
/*  704 */     int i = this.fds[paramInt1].getdents(arrayOfByte, 0, paramInt3);
/*  705 */     copyout(arrayOfByte, paramInt2, i);
/*  706 */     return i;
/*      */   }
/*      */ 
/*      */   
/*      */   void _preCloseFD(Runtime.FD paramFD) {
/*  711 */     Seekable seekable = paramFD.seekable();
/*  712 */     if (seekable == null)
/*      */       return; 
/*      */     
/*  715 */     try { for (byte b = 0; b < this.gs.locks.length; b++) {
/*  716 */         Seekable.Lock lock = this.gs.locks[b];
/*  717 */         if (lock != null && 
/*  718 */           seekable.equals(lock.seekable()) && lock.getOwner() == this) {
/*  719 */           lock.release();
/*  720 */           this.gs.locks[b] = null;
/*      */         } 
/*      */       }  }
/*  723 */     catch (IOException iOException) { throw new RuntimeException(iOException); }
/*      */   
/*      */   }
/*      */   void _postCloseFD(Runtime.FD paramFD) {
/*  727 */     if (paramFD.isMarkedForDeleteOnClose()) {
/*  728 */       try { this.gs.unlink(this, paramFD.getNormalizedPath()); }
/*  729 */       catch (Throwable throwable) {}
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int sys_fcntl_lock(int paramInt1, int paramInt2, int paramInt3) {
/*  746 */     if (paramInt2 != 7 && paramInt2 != 8) return sys_fcntl(paramInt1, paramInt2, paramInt3);
/*      */     
/*  748 */     if (paramInt1 < 0 || paramInt1 >= 64) return -81; 
/*  749 */     if (this.fds[paramInt1] == null) return -81; 
/*  750 */     Runtime.FD fD = this.fds[paramInt1];
/*      */     
/*  752 */     if (paramInt3 == 0) return -22; 
/*  753 */     int i = memRead(paramInt3);
/*  754 */     int j = memRead(paramInt3 + 4);
/*  755 */     int k = memRead(paramInt3 + 8);
/*  756 */     int m = i >> 16;
/*  757 */     int n = i & 0xFF;
/*      */     
/*  759 */     Seekable.Lock[] arrayOfLock = this.gs.locks;
/*  760 */     Seekable seekable = fD.seekable();
/*  761 */     if (seekable == null) return -22;
/*      */ 
/*      */     
/*      */     try {
/*  765 */       switch (n) { case 0: break;
/*      */         case 1:
/*  767 */           j += seekable.pos(); break;
/*  768 */         case 2: j += seekable.length(); break;
/*  769 */         default: return -1; }
/*      */ 
/*      */       
/*  772 */       if (paramInt2 == 7) {
/*      */ 
/*      */ 
/*      */         
/*  776 */         for (null = 0; null < arrayOfLock.length; null++) {
/*  777 */           if (arrayOfLock[null] != null && seekable.equals(arrayOfLock[null].seekable()))
/*      */           {
/*  779 */             if (arrayOfLock[null].overlaps(j, k))
/*      */             {
/*  781 */               if (arrayOfLock[null].getOwner() != this)
/*      */               {
/*  783 */                 if (!arrayOfLock[null].isShared() || m != 1)
/*      */                 {
/*      */ 
/*      */                   
/*  787 */                   return 0; }  } 
/*      */             }
/*      */           }
/*      */         } 
/*  791 */         Seekable.Lock lock = seekable.lock(j, k, (m == 1));
/*      */         
/*  793 */         if (lock != null) {
/*  794 */           memWrite(paramInt3, 196608);
/*  795 */           lock.release();
/*      */         } 
/*      */         
/*  798 */         return 0;
/*      */       } 
/*      */ 
/*      */       
/*  802 */       if (paramInt2 != 8) return -22;
/*      */       
/*  804 */       if (m == 3) {
/*      */         
/*  806 */         for (byte b = 0; b < arrayOfLock.length; b++) {
/*  807 */           if (arrayOfLock[b] != null && seekable.equals(arrayOfLock[b].seekable()))
/*      */           {
/*  809 */             if (arrayOfLock[b].getOwner() == this) {
/*      */               
/*  811 */               int i1 = (int)arrayOfLock[b].position();
/*  812 */               if (i1 >= j && (
/*  813 */                 j == 0 || k == 0 || 
/*  814 */                 i1 + arrayOfLock[b].size() <= (j + k)))
/*      */               
/*      */               { 
/*  817 */                 arrayOfLock[b].release();
/*  818 */                 arrayOfLock[b] = null; } 
/*      */             }  } 
/*  820 */         }  return 0;
/*      */       } 
/*  822 */       if (m == 1 || m == 2) {
/*      */         
/*  824 */         for (byte b1 = 0; b1 < arrayOfLock.length; b1++) {
/*  825 */           if (arrayOfLock[b1] != null && seekable.equals(arrayOfLock[b1].seekable()))
/*      */           {
/*      */             
/*  828 */             if (arrayOfLock[b1].getOwner() == this) {
/*      */               
/*  830 */               if (arrayOfLock[b1].contained(j, k)) {
/*  831 */                 arrayOfLock[b1].release();
/*  832 */                 arrayOfLock[b1] = null;
/*  833 */               } else if (arrayOfLock[b1].contains(j, k)) {
/*  834 */                 if (arrayOfLock[b1].isShared() == ((m == 1))) {
/*      */                   
/*  836 */                   memWrite(paramInt3 + 4, (int)arrayOfLock[b1].position());
/*  837 */                   memWrite(paramInt3 + 8, (int)arrayOfLock[b1].size());
/*  838 */                   return 0;
/*      */                 } 
/*  840 */                 arrayOfLock[b1].release();
/*  841 */                 arrayOfLock[b1] = null;
/*      */               
/*      */               }
/*      */ 
/*      */             
/*      */             }
/*  847 */             else if (arrayOfLock[b1].overlaps(j, k) && (!arrayOfLock[b1].isShared() || m == 2)) {
/*      */               
/*  849 */               return -11;
/*      */             } 
/*      */           }
/*      */         } 
/*      */         
/*  854 */         Seekable.Lock lock = seekable.lock(j, k, (m == 1));
/*  855 */         if (lock == null) return -11; 
/*  856 */         lock.setOwner(this);
/*      */         
/*      */         byte b2;
/*  859 */         for (b2 = 0; b2 < arrayOfLock.length && 
/*  860 */           arrayOfLock[b2] != null; b2++);
/*  861 */         if (b2 == arrayOfLock.length) return -46; 
/*  862 */         arrayOfLock[b2] = lock;
/*  863 */         return 0;
/*      */       } 
/*      */       
/*  866 */       return -22;
/*      */     } catch (IOException iOException) {
/*      */       
/*  869 */       throw new RuntimeException(iOException);
/*      */     } 
/*      */   }
/*      */   static class SocketFD extends Runtime.FD { public static final int TYPE_STREAM = 0; public static final int TYPE_DGRAM = 1; public static final int LISTEN = 2; int flags; int options; Socket s; ServerSocket ss; DatagramSocket ds; InetAddress bindAddr; int bindPort; InetAddress connectAddr; int connectPort; DatagramPacket dp;
/*      */     InputStream is;
/*      */     OutputStream os;
/*      */     
/*  876 */     public int type() { return this.flags & true; }
/*  877 */     public boolean listen() { return ((this.flags & 0x2) != 0); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  895 */     private static final byte[] EMPTY = new byte[0]; public SocketFD(int param1Int) { this.bindPort = -1;
/*      */       this.connectPort = -1;
/*  897 */       this.flags = param1Int;
/*  898 */       if (param1Int == 1)
/*  899 */         this.dp = new DatagramPacket(EMPTY, false);  }
/*      */ 
/*      */     
/*      */     public void setOptions() {
/*      */       try {
/*  904 */         if (this.s != null && type() == 0 && !listen()) {
/*  905 */           Platform.socketSetKeepAlive(this.s, ((this.options & 0x8) != 0));
/*      */         }
/*  907 */       } catch (SocketException socketException) {
/*  908 */         socketException.printStackTrace();
/*      */       } 
/*      */     }
/*      */     
/*      */     public void _close() {
/*      */       try {
/*  914 */         if (this.s != null) this.s.close(); 
/*  915 */         if (this.ss != null) this.ss.close(); 
/*  916 */         if (this.ds != null) this.ds.close(); 
/*  917 */       } catch (IOException iOException) {}
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int read(byte[] param1ArrayOfByte, int param1Int1, int param1Int2) {
/*  923 */       if (type() == 1) return recvfrom(param1ArrayOfByte, param1Int1, param1Int2, null, null); 
/*  924 */       if (this.is == null) throw new Runtime.ErrnoException(32); 
/*      */       try {
/*  926 */         int i = this.is.read(param1ArrayOfByte, param1Int1, param1Int2);
/*  927 */         return (i < 0) ? 0 : i;
/*  928 */       } catch (IOException iOException) {
/*  929 */         throw new Runtime.ErrnoException(5);
/*      */       } 
/*      */     }
/*      */     
/*      */     public int recvfrom(byte[] param1ArrayOfByte, int param1Int1, int param1Int2, InetAddress[] param1ArrayOfInetAddress, int[] param1ArrayOfInt) throws Runtime.ErrnoException {
/*  934 */       if (type() == 0) return read(param1ArrayOfByte, param1Int1, param1Int2);
/*      */       
/*  936 */       if (param1Int1 != 0) throw new IllegalArgumentException("off must be 0"); 
/*  937 */       this.dp.setData(param1ArrayOfByte);
/*  938 */       this.dp.setLength(param1Int2);
/*      */       try {
/*  940 */         if (this.ds == null) this.ds = new DatagramSocket(); 
/*  941 */         this.ds.receive(this.dp);
/*  942 */       } catch (IOException iOException) {
/*  943 */         iOException.printStackTrace();
/*  944 */         throw new Runtime.ErrnoException(5);
/*      */       } 
/*  946 */       if (param1ArrayOfInetAddress != null) {
/*  947 */         param1ArrayOfInetAddress[0] = this.dp.getAddress();
/*  948 */         param1ArrayOfInt[0] = this.dp.getPort();
/*      */       } 
/*  950 */       return this.dp.getLength();
/*      */     }
/*      */     
/*      */     public int write(byte[] param1ArrayOfByte, int param1Int1, int param1Int2) {
/*  954 */       if (type() == 1) return sendto(param1ArrayOfByte, param1Int1, param1Int2, null, -1);
/*      */       
/*  956 */       if (this.os == null) throw new Runtime.ErrnoException(32); 
/*      */       try {
/*  958 */         this.os.write(param1ArrayOfByte, param1Int1, param1Int2);
/*  959 */         return param1Int2;
/*  960 */       } catch (IOException iOException) {
/*  961 */         throw new Runtime.ErrnoException(5);
/*      */       } 
/*      */     }
/*      */     
/*      */     public int sendto(byte[] param1ArrayOfByte, int param1Int1, int param1Int2, InetAddress param1InetAddress, int param1Int3) throws Runtime.ErrnoException {
/*  966 */       if (param1Int1 != 0) throw new IllegalArgumentException("off must be 0"); 
/*  967 */       if (type() == 0) return write(param1ArrayOfByte, param1Int1, param1Int2);
/*      */       
/*  969 */       if (param1InetAddress == null) {
/*  970 */         param1InetAddress = this.connectAddr;
/*  971 */         param1Int3 = this.connectPort;
/*      */         
/*  973 */         if (param1InetAddress == null) throw new Runtime.ErrnoException('');
/*      */       
/*      */       } 
/*  976 */       this.dp.setAddress(param1InetAddress);
/*  977 */       this.dp.setPort(param1Int3);
/*  978 */       this.dp.setData(param1ArrayOfByte);
/*  979 */       this.dp.setLength(param1Int2);
/*      */       
/*      */       try {
/*  982 */         if (this.ds == null) this.ds = new DatagramSocket(); 
/*  983 */         this.ds.send(this.dp);
/*  984 */       } catch (IOException iOException) {
/*  985 */         iOException.printStackTrace();
/*  986 */         if ("Network is unreachable".equals(iOException.getMessage())) throw new Runtime.ErrnoException(118); 
/*  987 */         throw new Runtime.ErrnoException(5);
/*      */       } 
/*  989 */       return this.dp.getLength();
/*      */     }
/*      */     
/*  992 */     public int flags() { return 2; }
/*  993 */     public Runtime.FStat _fstat() { return new Runtime.SocketFStat(); } }
/*      */ 
/*      */   
/*      */   private int sys_socket(int paramInt1, int paramInt2, int paramInt3) {
/*  997 */     if (paramInt1 != 2 || (paramInt2 != 1 && paramInt2 != 2)) return -123; 
/*  998 */     return addFD(new SocketFD((paramInt2 == 1) ? 0 : 1));
/*      */   }
/*      */   
/*      */   private SocketFD getSocketFD(int paramInt) throws Runtime.ErrnoException {
/* 1002 */     if (paramInt < 0 || paramInt >= 64) throw new Runtime.ErrnoException(81); 
/* 1003 */     if (this.fds[paramInt] == null) throw new Runtime.ErrnoException(81); 
/* 1004 */     if (!(this.fds[paramInt] instanceof SocketFD)) throw new Runtime.ErrnoException(108);
/*      */     
/* 1006 */     return (SocketFD)this.fds[paramInt];
/*      */   }
/*      */   private int sys_connect(int paramInt1, int paramInt2, int paramInt3) {
/*      */     InetAddress inetAddress;
/* 1010 */     SocketFD socketFD = getSocketFD(paramInt1);
/*      */     
/* 1012 */     if (socketFD.type() == 0 && (socketFD.s != null || socketFD.ss != null)) return -127; 
/* 1013 */     int i = memRead(paramInt2);
/* 1014 */     if ((i >>> 16 & 0xFF) != 2) return -106; 
/* 1015 */     int j = i & 0xFFFF;
/* 1016 */     byte[] arrayOfByte = new byte[4];
/* 1017 */     copyin(paramInt2 + 4, arrayOfByte, 4);
/*      */ 
/*      */     
/*      */     try {
/* 1021 */       inetAddress = Platform.inetAddressFromBytes(arrayOfByte);
/* 1022 */     } catch (UnknownHostException unknownHostException) {
/* 1023 */       return -125;
/*      */     } 
/*      */     
/* 1026 */     socketFD.connectAddr = inetAddress;
/* 1027 */     socketFD.connectPort = j;
/*      */     try {
/*      */       Socket socket;
/* 1030 */       switch (socketFD.type()) {
/*      */         case 0:
/* 1032 */           socket = new Socket(inetAddress, j);
/* 1033 */           socketFD.s = socket;
/* 1034 */           socketFD.setOptions();
/* 1035 */           socketFD.is = socket.getInputStream();
/* 1036 */           socketFD.os = socket.getOutputStream();
/*      */           break;
/*      */         
/*      */         case 1:
/*      */           break;
/*      */         default:
/* 1042 */           throw new Error("should never happen");
/*      */       } 
/* 1044 */     } catch (IOException iOException) {
/* 1045 */       return -111;
/*      */     } 
/*      */     
/* 1048 */     return 0;
/*      */   }
/*      */   private int sys_resolve_hostname(int paramInt1, int paramInt2, int paramInt3) {
/*      */     InetAddress[] arrayOfInetAddress;
/* 1052 */     String str = cstring(paramInt1);
/* 1053 */     int i = memRead(paramInt3);
/*      */     
/*      */     try {
/* 1056 */       arrayOfInetAddress = InetAddress.getAllByName(str);
/* 1057 */     } catch (UnknownHostException unknownHostException) {
/* 1058 */       return 1;
/*      */     } 
/* 1060 */     int j = min(i / 4, arrayOfInetAddress.length);
/* 1061 */     for (byte b = 0; b < j; b++, paramInt2 += 4) {
/* 1062 */       byte[] arrayOfByte = arrayOfInetAddress[b].getAddress();
/* 1063 */       copyout(arrayOfByte, paramInt2, 4);
/*      */     } 
/* 1065 */     memWrite(paramInt3, j * 4);
/* 1066 */     return 0;
/*      */   }
/*      */   private int sys_setsockopt(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) throws Runtime.ReadFaultException, Runtime.ErrnoException {
/*      */     int i;
/* 1070 */     SocketFD socketFD = getSocketFD(paramInt1);
/* 1071 */     switch (paramInt2) {
/*      */       case 65535:
/* 1073 */         switch (paramInt3) {
/*      */           case 4:
/*      */           case 8:
/* 1076 */             if (paramInt5 != 4) return -22; 
/* 1077 */             i = memRead(paramInt4);
/* 1078 */             if (i != 0) { socketFD.options |= paramInt3; }
/* 1079 */             else { socketFD.options &= (paramInt3 ^ 0xFFFFFFFF); }
/* 1080 */              socketFD.setOptions();
/* 1081 */             return 0;
/*      */         } 
/*      */         
/* 1084 */         System.err.println("Unknown setsockopt name passed: " + paramInt3);
/* 1085 */         return -109;
/*      */     } 
/*      */     
/* 1088 */     System.err.println("Unknown setsockopt leve passed: " + paramInt2);
/* 1089 */     return -109;
/*      */   }
/*      */   private int sys_getsockopt(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) throws Runtime.ReadFaultException, Runtime.ErrnoException {
/*      */     byte b;
/*      */     int i;
/* 1094 */     SocketFD socketFD = getSocketFD(paramInt1);
/* 1095 */     switch (paramInt2) {
/*      */       case 65535:
/* 1097 */         switch (paramInt3) {
/*      */           case 4:
/*      */           case 8:
/* 1100 */             i = memRead(paramInt5);
/* 1101 */             if (i < 4) return -22; 
/* 1102 */             b = ((socketFD.options & paramInt3) != 0) ? 1 : 0;
/* 1103 */             memWrite(paramInt4, b);
/* 1104 */             memWrite(paramInt5, 4);
/* 1105 */             return 0;
/*      */         } 
/*      */         
/* 1108 */         System.err.println("Unknown setsockopt name passed: " + paramInt3);
/* 1109 */         return -109;
/*      */     } 
/*      */     
/* 1112 */     System.err.println("Unknown setsockopt leve passed: " + paramInt2);
/* 1113 */     return -109;
/*      */   }
/*      */ 
/*      */   
/*      */   private int sys_bind(int paramInt1, int paramInt2, int paramInt3) {
/* 1118 */     SocketFD socketFD = getSocketFD(paramInt1);
/*      */     
/* 1120 */     if (socketFD.type() == 0 && (socketFD.s != null || socketFD.ss != null)) return -127; 
/* 1121 */     int i = memRead(paramInt2);
/* 1122 */     if ((i >>> 16 & 0xFF) != 2) return -106; 
/* 1123 */     int j = i & 0xFFFF;
/* 1124 */     InetAddress inetAddress = null;
/* 1125 */     if (memRead(paramInt2 + 4) != 0) {
/* 1126 */       byte[] arrayOfByte = new byte[4];
/* 1127 */       copyin(paramInt2 + 4, arrayOfByte, 4);
/*      */       
/*      */       try {
/* 1130 */         inetAddress = Platform.inetAddressFromBytes(arrayOfByte);
/* 1131 */       } catch (UnknownHostException unknownHostException) {
/* 1132 */         return -125;
/*      */       } 
/*      */     } 
/*      */     
/* 1136 */     switch (socketFD.type()) {
/*      */       case 0:
/* 1138 */         socketFD.bindAddr = inetAddress;
/* 1139 */         socketFD.bindPort = j;
/* 1140 */         return 0;
/*      */       
/*      */       case 1:
/* 1143 */         if (socketFD.ds != null) socketFD.ds.close(); 
/*      */         try {
/* 1145 */           socketFD.ds = (inetAddress != null) ? new DatagramSocket(j, inetAddress) : new DatagramSocket(j);
/* 1146 */         } catch (IOException iOException) {
/* 1147 */           return -112;
/*      */         } 
/* 1149 */         return 0;
/*      */     } 
/*      */     
/* 1152 */     throw new Error("should never happen");
/*      */   }
/*      */ 
/*      */   
/*      */   private int sys_listen(int paramInt1, int paramInt2) throws Runtime.ErrnoException, Runtime.ReadFaultException {
/* 1157 */     SocketFD socketFD = getSocketFD(paramInt1);
/* 1158 */     if (socketFD.type() != 0) return -95; 
/* 1159 */     if (socketFD.ss != null || socketFD.s != null) return -127; 
/* 1160 */     if (socketFD.bindPort < 0) return -95;
/*      */     
/*      */     try {
/* 1163 */       socketFD.ss = new ServerSocket(socketFD.bindPort, paramInt2, socketFD.bindAddr);
/* 1164 */       socketFD.flags |= 0x2;
/* 1165 */       return 0;
/* 1166 */     } catch (IOException iOException) {
/* 1167 */       return -112;
/*      */     } 
/*      */   }
/*      */   
/*      */   private int sys_accept(int paramInt1, int paramInt2, int paramInt3) {
/*      */     Socket socket;
/* 1173 */     SocketFD socketFD1 = getSocketFD(paramInt1);
/* 1174 */     if (socketFD1.type() != 0) return -95; 
/* 1175 */     if (!socketFD1.listen()) return -95;
/*      */     
/* 1177 */     int i = memRead(paramInt3);
/*      */     
/* 1179 */     ServerSocket serverSocket = socketFD1.ss;
/*      */     
/*      */     try {
/* 1182 */       socket = serverSocket.accept();
/* 1183 */     } catch (IOException iOException) {
/* 1184 */       return -5;
/*      */     } 
/*      */     
/* 1187 */     if (i >= 8) {
/* 1188 */       memWrite(paramInt2, 0x6020000 | socket.getPort());
/* 1189 */       byte[] arrayOfByte = socket.getInetAddress().getAddress();
/* 1190 */       copyout(arrayOfByte, paramInt2 + 4, 4);
/* 1191 */       memWrite(paramInt3, 8);
/*      */     } 
/*      */     
/* 1194 */     SocketFD socketFD2 = new SocketFD(false);
/* 1195 */     socketFD2.s = socket;
/*      */     try {
/* 1197 */       socketFD2.is = socket.getInputStream();
/* 1198 */       socketFD2.os = socket.getOutputStream();
/* 1199 */     } catch (IOException iOException) {
/* 1200 */       return -5;
/*      */     } 
/* 1202 */     int j = addFD(socketFD2);
/* 1203 */     if (j == -1) { socketFD2.close(); return -23; }
/* 1204 */      return j;
/*      */   }
/*      */   
/*      */   private int sys_shutdown(int paramInt1, int paramInt2) throws Runtime.ErrnoException, Runtime.ReadFaultException {
/* 1208 */     SocketFD socketFD = getSocketFD(paramInt1);
/* 1209 */     if (socketFD.type() != 0 || socketFD.listen()) return -95; 
/* 1210 */     if (socketFD.s == null) return -128;
/*      */     
/* 1212 */     Socket socket = socketFD.s;
/*      */     
/*      */     try {
/* 1215 */       if (paramInt2 == 0 || paramInt2 == 2) Platform.socketHalfClose(socket, false); 
/* 1216 */       if (paramInt2 == 1 || paramInt2 == 2) Platform.socketHalfClose(socket, true); 
/* 1217 */     } catch (IOException iOException) {
/* 1218 */       return -5;
/*      */     } 
/*      */     
/* 1221 */     return 0;
/*      */   }
/*      */   private int sys_sendto(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) throws Runtime.ErrnoException, Runtime.ReadFaultException {
/*      */     InetAddress inetAddress;
/* 1225 */     SocketFD socketFD = getSocketFD(paramInt1);
/* 1226 */     if (paramInt4 != 0) throw new Runtime.ErrnoException(22);
/*      */     
/* 1228 */     int i = memRead(paramInt5);
/* 1229 */     if ((i >>> 16 & 0xFF) != 2) return -106; 
/* 1230 */     int j = i & 0xFFFF;
/*      */     
/* 1232 */     byte[] arrayOfByte1 = new byte[4];
/* 1233 */     copyin(paramInt5 + 4, arrayOfByte1, 4);
/*      */     try {
/* 1235 */       inetAddress = Platform.inetAddressFromBytes(arrayOfByte1);
/* 1236 */     } catch (UnknownHostException unknownHostException) {
/* 1237 */       return -125;
/*      */     } 
/*      */     
/* 1240 */     paramInt3 = Math.min(paramInt3, 16776192);
/* 1241 */     byte[] arrayOfByte2 = byteBuf(paramInt3);
/* 1242 */     copyin(paramInt2, arrayOfByte2, paramInt3);
/*      */     try {
/* 1244 */       return socketFD.sendto(arrayOfByte2, 0, paramInt3, inetAddress, j);
/* 1245 */     } catch (ErrnoException errnoException) {
/* 1246 */       if (errnoException.errno == 32) exit(141, true); 
/* 1247 */       throw errnoException;
/*      */     } 
/*      */   }
/*      */   
/*      */   private int sys_recvfrom(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) throws Runtime.ErrnoException, Runtime.ReadFaultException {
/* 1252 */     SocketFD socketFD = getSocketFD(paramInt1);
/* 1253 */     if (paramInt4 != 0) throw new Runtime.ErrnoException(22);
/*      */     
/* 1255 */     InetAddress[] arrayOfInetAddress = (paramInt5 == 0) ? null : new InetAddress[1];
/* 1256 */     int[] arrayOfInt = (paramInt5 == 0) ? null : new int[1];
/*      */     
/* 1258 */     paramInt3 = Math.min(paramInt3, 16776192);
/* 1259 */     byte[] arrayOfByte = byteBuf(paramInt3);
/* 1260 */     int i = socketFD.recvfrom(arrayOfByte, 0, paramInt3, arrayOfInetAddress, arrayOfInt);
/* 1261 */     copyout(arrayOfByte, paramInt2, i);
/*      */     
/* 1263 */     if (paramInt5 != 0) {
/* 1264 */       memWrite(paramInt5, 0x20000 | arrayOfInt[0]);
/* 1265 */       byte[] arrayOfByte1 = arrayOfInetAddress[0].getAddress();
/* 1266 */       copyout(arrayOfByte1, paramInt5 + 4, 4);
/*      */     } 
/*      */     
/* 1269 */     return i;
/*      */   }
/*      */ 
/*      */   
/* 1273 */   private int sys_select(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) throws Runtime.ReadFaultException, Runtime.ErrnoException { return -88; }
/*      */ 
/*      */   
/*      */   private static String hostName() {
/*      */     try {
/* 1278 */       return InetAddress.getLocalHost().getHostName();
/* 1279 */     } catch (UnknownHostException unknownHostException) {
/* 1280 */       return "darkstar";
/*      */     } 
/*      */   }
/*      */   
/*      */   private int sys_sysctl(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) throws Runtime.ErrnoException, Runtime.ReadFaultException {
/* 1285 */     if (paramInt5 != 0) return -1; 
/* 1286 */     if (paramInt2 == 0) return -2; 
/* 1287 */     if (paramInt3 == 0) return 0;
/*      */     
/* 1289 */     String str = null;
/* 1290 */     switch (memRead(paramInt1)) {
/*      */       case 1:
/* 1292 */         if (paramInt2 != 2)
/* 1293 */           break;  switch (memRead(paramInt1 + 4)) { case 1:
/* 1294 */             str = "NestedVM"; break;
/* 1295 */           case 10: str = hostName(); break;
/* 1296 */           case 2: str = "1.0"; break;
/* 1297 */           case 4: case 0: break; }  str = "NestedVM Kernel Version 1.0";
/*      */         break;
/*      */       
/*      */       case 6:
/* 1301 */         if (paramInt2 != 2)
/* 1302 */           break;  switch (memRead(paramInt1 + 4)) { case 1: case 0:
/* 1303 */             break; }  str = "NestedVM Virtual Machine";
/*      */         break;
/*      */     } 
/*      */     
/* 1307 */     if (str == null) return -2; 
/* 1308 */     int i = memRead(paramInt4);
/* 1309 */     if (str instanceof String) {
/* 1310 */       byte[] arrayOfByte = getNullTerminatedBytes((String)str);
/* 1311 */       if (i < arrayOfByte.length) return -12; 
/* 1312 */       i = arrayOfByte.length;
/* 1313 */       copyout(arrayOfByte, paramInt3, i);
/* 1314 */       memWrite(paramInt4, i);
/* 1315 */     } else if (str instanceof Integer) {
/* 1316 */       if (i < 4) return -12; 
/* 1317 */       memWrite(paramInt3, ((Integer)str).intValue());
/*      */     } else {
/* 1319 */       throw new Error("should never happen");
/*      */     } 
/* 1321 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public static final class GlobalState
/*      */   {
/*      */     Hashtable execCache;
/*      */     
/*      */     final UnixRuntime[] tasks;
/*      */     
/*      */     int nextPID;
/*      */     Seekable.Lock[] locks;
/*      */     private MP[] mps;
/*      */     private UnixRuntime.FS root;
/*      */     
/* 1336 */     public GlobalState() { this(255); }
/* 1337 */     public GlobalState(int param1Int) { this(param1Int, true); } public GlobalState(int param1Int, boolean param1Boolean) { this.execCache = new Hashtable(); this.nextPID = 1; this.locks = new Seekable.Lock[16];
/*      */       this.mps = new MP[0];
/* 1339 */       this.tasks = new UnixRuntime[param1Int + 1];
/* 1340 */       if (param1Boolean) {
/* 1341 */         File file = null;
/* 1342 */         if (Platform.getProperty("nestedvm.root") != null) {
/* 1343 */           file = new File(Platform.getProperty("nestedvm.root"));
/* 1344 */           if (!file.isDirectory()) throw new IllegalArgumentException("nestedvm.root is not a directory"); 
/*      */         } else {
/* 1346 */           String str = Platform.getProperty("user.dir");
/* 1347 */           file = Platform.getRoot(new File((str != null) ? str : "."));
/*      */         } 
/*      */         
/* 1350 */         addMount("/", new UnixRuntime.HostFS(file));
/*      */         
/* 1352 */         if (Platform.getProperty("nestedvm.root") == null) {
/* 1353 */           File[] arrayOfFile = Platform.listRoots();
/* 1354 */           for (byte b = 0; b < arrayOfFile.length; b++) {
/* 1355 */             String str = arrayOfFile[b].getPath();
/* 1356 */             if (str.endsWith(File.separator))
/* 1357 */               str = str.substring(0, str.length() - 1); 
/* 1358 */             if (str.length() != 0 && str.indexOf('/') == -1) {
/* 1359 */               addMount("/" + str.toLowerCase(), new UnixRuntime.HostFS(arrayOfFile[b]));
/*      */             }
/*      */           } 
/*      */         } 
/* 1363 */         addMount("/dev", new UnixRuntime.DevFS());
/* 1364 */         addMount("/resource", new UnixRuntime.ResourceFS());
/* 1365 */         addMount("/cygdrive", new UnixRuntime.CygdriveFS());
/*      */       }  }
/*      */ 
/*      */     
/* 1369 */     public String mapHostPath(String param1String) { return mapHostPath(new File(param1String)); }
/*      */     
/*      */     public String mapHostPath(File param1File) {
/*      */       UnixRuntime.FS fS;
/* 1373 */       synchronized (this) { this.mps = this.mps; fS = this.root; }
/* 1374 */        if (!param1File.isAbsolute()) param1File = new File(param1File.getAbsolutePath()); 
/* 1375 */       for (int i = this.mps.length; i >= 0; i--) {
/* 1376 */         UnixRuntime.FS fS1 = (i == this.mps.length) ? fS : (this.mps[i]).fs;
/* 1377 */         String str = (i == this.mps.length) ? "" : (this.mps[i]).path;
/* 1378 */         if (fS1 instanceof UnixRuntime.HostFS) {
/* 1379 */           File file = ((UnixRuntime.HostFS)fS1).getRoot();
/* 1380 */           if (!file.isAbsolute()) file = new File(file.getAbsolutePath()); 
/* 1381 */           if (param1File.getPath().startsWith(file.getPath())) {
/* 1382 */             char c = File.separatorChar;
/* 1383 */             String str1 = param1File.getPath().substring(file.getPath().length());
/* 1384 */             if (c != '/') {
/* 1385 */               char[] arrayOfChar = str1.toCharArray();
/* 1386 */               for (byte b = 0; b < arrayOfChar.length; b++) {
/* 1387 */                 if (arrayOfChar[b] == '/') { arrayOfChar[b] = c; }
/* 1388 */                 else if (arrayOfChar[b] == c) { arrayOfChar[b] = '/'; }
/*      */               
/* 1390 */               }  str1 = new String(arrayOfChar);
/*      */             } 
/* 1392 */             return "/" + ((str.length() == 0) ? "" : (str + "/")) + str1;
/*      */           } 
/*      */         } 
/*      */       } 
/* 1396 */       return null;
/*      */     }
/*      */     static class MP implements Sort.Comparable { public String path; public UnixRuntime.FS fs;
/*      */       
/* 1400 */       public MP(String param2String, UnixRuntime.FS param2FS) { this.path = param2String; this.fs = param2FS; }
/*      */ 
/*      */       
/*      */       public int compareTo(Object param2Object) {
/* 1404 */         if (!(param2Object instanceof MP)) return 1; 
/* 1405 */         return -this.path.compareTo(((MP)param2Object).path);
/*      */       } }
/*      */ 
/*      */     
/*      */     public UnixRuntime.FS getMount(String param1String) {
/* 1410 */       if (!param1String.startsWith("/")) throw new IllegalArgumentException("Mount point doesn't start with a /"); 
/* 1411 */       if (param1String.equals("/")) return this.root; 
/* 1412 */       param1String = param1String.substring(1);
/* 1413 */       for (byte b = 0; b < this.mps.length; b++) {
/* 1414 */         if ((this.mps[b]).path.equals(param1String)) return (this.mps[b]).fs; 
/* 1415 */       }  return null;
/*      */     }
/*      */     
/*      */     public void addMount(String param1String, UnixRuntime.FS param1FS) {
/* 1419 */       if (getMount(param1String) != null) throw new IllegalArgumentException("mount point already exists"); 
/* 1420 */       if (!param1String.startsWith("/")) throw new IllegalArgumentException("Mount point doesn't start with a /");
/*      */       
/* 1422 */       if (param1FS.owner != null) param1FS.owner.removeMount(param1FS); 
/* 1423 */       param1FS.owner = this;
/*      */       
/* 1425 */       if (param1String.equals("/")) { this.root = param1FS; param1FS.devno = 1; return; }
/* 1426 */        param1String = param1String.substring(1);
/* 1427 */       int i = this.mps.length;
/* 1428 */       MP[] arrayOfMP = new MP[i + 1];
/* 1429 */       if (i != 0) System.arraycopy(this.mps, 0, arrayOfMP, 0, i); 
/* 1430 */       arrayOfMP[i] = new MP(param1String, param1FS);
/* 1431 */       Sort.sort(arrayOfMP);
/* 1432 */       this.mps = arrayOfMP;
/* 1433 */       int j = 0;
/* 1434 */       for (byte b = 0; b < this.mps.length; ) { j = Runtime.max(j, (this.mps[b]).fs.devno); b++; }
/* 1435 */        param1FS.devno = j + 2;
/*      */     }
/*      */     
/*      */     public void removeMount(UnixRuntime.FS param1FS) {
/* 1439 */       for (byte b = 0; b < this.mps.length; ) { if ((this.mps[b]).fs == param1FS) { removeMount(b); return; }  b++; }
/* 1440 */        throw new IllegalArgumentException("mount point doesn't exist");
/*      */     }
/*      */     
/*      */     public void removeMount(String param1String) {
/* 1444 */       if (!param1String.startsWith("/")) throw new IllegalArgumentException("Mount point doesn't start with a /"); 
/* 1445 */       if (param1String.equals("/")) {
/* 1446 */         removeMount(-1);
/*      */       } else {
/* 1448 */         param1String = param1String.substring(1);
/*      */         byte b;
/* 1450 */         for (b = 0; b < this.mps.length && !(this.mps[b]).path.equals(param1String); b++);
/* 1451 */         if (b == this.mps.length) throw new IllegalArgumentException("mount point doesn't exist"); 
/* 1452 */         removeMount(b);
/*      */       } 
/*      */     }
/*      */     
/*      */     private void removeMount(int param1Int) {
/* 1457 */       if (param1Int == -1) { this.root.owner = null; this.root = null; return; }
/* 1458 */        MP[] arrayOfMP = new MP[this.mps.length - 1];
/* 1459 */       System.arraycopy(this.mps, 0, arrayOfMP, 0, param1Int);
/* 1460 */       System.arraycopy(this.mps, 0, arrayOfMP, param1Int, this.mps.length - param1Int - 1);
/* 1461 */       this.mps = arrayOfMP;
/*      */     }
/*      */     
/*      */     private Object fsop(int param1Int1, UnixRuntime param1UnixRuntime, String param1String, int param1Int2, int param1Int3) throws Runtime.ErrnoException {
/* 1465 */       int i = param1String.length();
/* 1466 */       if (i != 0) {
/*      */         MP[] arrayOfMP;
/* 1468 */         synchronized (this) { arrayOfMP = this.mps; }
/* 1469 */          for (byte b = 0; b < arrayOfMP.length; b++) {
/* 1470 */           MP mP = arrayOfMP[b];
/* 1471 */           int j = mP.path.length();
/* 1472 */           if (param1String.startsWith(mP.path) && (i == j || param1String.charAt(j) == '/'))
/* 1473 */             return mP.fs.dispatch(param1Int1, param1UnixRuntime, (i == j) ? "" : param1String.substring(j + 1), param1Int2, param1Int3); 
/*      */         } 
/*      */       } 
/* 1476 */       return this.root.dispatch(param1Int1, param1UnixRuntime, param1String, param1Int2, param1Int3);
/*      */     }
/*      */     
/* 1479 */     public final Runtime.FD open(UnixRuntime param1UnixRuntime, String param1String, int param1Int1, int param1Int2) throws Runtime.ErrnoException { return (Runtime.FD)fsop(1, param1UnixRuntime, param1String, param1Int1, param1Int2); }
/* 1480 */     public final Runtime.FStat stat(UnixRuntime param1UnixRuntime, String param1String) throws Runtime.ErrnoException { return (Runtime.FStat)fsop(2, param1UnixRuntime, param1String, 0, 0); }
/* 1481 */     public final Runtime.FStat lstat(UnixRuntime param1UnixRuntime, String param1String) throws Runtime.ErrnoException { return (Runtime.FStat)fsop(3, param1UnixRuntime, param1String, 0, 0); }
/* 1482 */     public final void mkdir(UnixRuntime param1UnixRuntime, String param1String, int param1Int) throws Runtime.ErrnoException { fsop(4, param1UnixRuntime, param1String, param1Int, 0); }
/* 1483 */     public final void unlink(UnixRuntime param1UnixRuntime, String param1String) throws Runtime.ErrnoException { fsop(5, param1UnixRuntime, param1String, 0, 0); }
/*      */     
/*      */     private static class CacheEnt { public final long time;
/*      */       public final long size;
/*      */       public final Object o;
/*      */       
/* 1489 */       public CacheEnt(long param2Long1, long param2Long2, Object param2Object) { this.time = param2Long1; this.size = param2Long2; this.o = param2Object; } } } private static class CacheEnt { public final long time; public final long size; public final Object o; public CacheEnt(long param1Long1, long param1Long2, Object param1Object) { this.time = param1Long1; this.size = param1Long2; this.o = param1Object; } }
/*      */ 
/*      */ 
/*      */   
/*      */   public static abstract class FS
/*      */   {
/*      */     static final int OPEN = 1;
/*      */     static final int STAT = 2;
/*      */     static final int LSTAT = 3;
/*      */     static final int MKDIR = 4;
/*      */     static final int UNLINK = 5;
/*      */     UnixRuntime.GlobalState owner;
/*      */     int devno;
/*      */     
/*      */     Object dispatch(int param1Int1, UnixRuntime param1UnixRuntime, String param1String, int param1Int2, int param1Int3) throws Runtime.ErrnoException {
/* 1504 */       switch (param1Int1) { case 1:
/* 1505 */           return open(param1UnixRuntime, param1String, param1Int2, param1Int3);
/* 1506 */         case 2: return stat(param1UnixRuntime, param1String);
/* 1507 */         case 3: return lstat(param1UnixRuntime, param1String);
/* 1508 */         case 4: mkdir(param1UnixRuntime, param1String, param1Int2); return null;
/* 1509 */         case 5: unlink(param1UnixRuntime, param1String); return null; }
/* 1510 */        throw new Error("should never happen");
/*      */     }
/*      */ 
/*      */     
/* 1514 */     public Runtime.FStat lstat(UnixRuntime param1UnixRuntime, String param1String) throws Runtime.ErrnoException { return stat(param1UnixRuntime, param1String); }
/*      */     
/*      */     public abstract Runtime.FD open(UnixRuntime param1UnixRuntime, String param1String, int param1Int1, int param1Int2) throws Runtime.ErrnoException;
/*      */     
/*      */     public abstract Runtime.FStat stat(UnixRuntime param1UnixRuntime, String param1String) throws Runtime.ErrnoException;
/*      */     
/*      */     public abstract void mkdir(UnixRuntime param1UnixRuntime, String param1String, int param1Int) throws Runtime.ErrnoException;
/*      */     
/*      */     public abstract void unlink(UnixRuntime param1UnixRuntime, String param1String) throws Runtime.ErrnoException;
/*      */   }
/*      */   
/*      */   private String normalizePath(String paramString) {
/* 1526 */     boolean bool = paramString.startsWith("/");
/* 1527 */     int i = this.cwd.length();
/*      */ 
/*      */     
/* 1530 */     if (!paramString.startsWith(".") && paramString.indexOf("./") == -1 && paramString.indexOf("//") == -1 && !paramString.endsWith(".")) {
/* 1531 */       return bool ? paramString.substring(1) : ((i == 0) ? paramString : ((paramString.length() == 0) ? this.cwd : (this.cwd + "/" + paramString)));
/*      */     }
/* 1533 */     char[] arrayOfChar1 = new char[paramString.length() + 1];
/* 1534 */     char[] arrayOfChar2 = new char[arrayOfChar1.length + (bool ? -1 : this.cwd.length())];
/* 1535 */     paramString.getChars(0, paramString.length(), arrayOfChar1, 0);
/* 1536 */     byte b = 0; int j = 0;
/*      */     
/* 1538 */     if (bool) { 
/* 1539 */       do { b++; } while (arrayOfChar1[b] == '/'); }
/* 1540 */     else if (i != 0)
/* 1541 */     { this.cwd.getChars(0, i, arrayOfChar2, 0);
/* 1542 */       j = i; }
/*      */ 
/*      */     
/* 1545 */     while (arrayOfChar1[b] != '\000') {
/* 1546 */       if (b != 0) {
/* 1547 */         for (; arrayOfChar1[b] != '\000' && arrayOfChar1[b] != '/'; arrayOfChar2[j++] = arrayOfChar1[b++]);
/* 1548 */         if (arrayOfChar1[b] == '\000')
/* 1549 */           break;  for (; arrayOfChar1[b] == '/'; b++);
/*      */       } 
/*      */ 
/*      */       
/* 1553 */       if (arrayOfChar1[b] == '\000')
/* 1554 */         break;  if (arrayOfChar1[b] != '.') { arrayOfChar2[j++] = '/'; arrayOfChar2[j++] = arrayOfChar1[b++]; continue; }
/*      */       
/* 1556 */       if (arrayOfChar1[b + 1] == '\000' || arrayOfChar1[b + 1] == '/') { b++; continue; }
/* 1557 */        if (arrayOfChar1[b + 1] == '.' && (arrayOfChar1[b + 2] == '\000' || arrayOfChar1[b + 2] == '/')) {
/*      */         
/* 1559 */         b += 2;
/* 1560 */         if (j > 0) j--; 
/* 1561 */         for (; j > 0 && arrayOfChar2[j] != '/'; j--);
/*      */         
/*      */         continue;
/*      */       } 
/*      */       
/* 1566 */       b++;
/* 1567 */       arrayOfChar2[j++] = '/';
/* 1568 */       arrayOfChar2[j++] = '.';
/*      */     } 
/* 1570 */     if (j > 0 && arrayOfChar2[j - 1] == '/') j--;
/*      */     
/* 1572 */     int k = (arrayOfChar2[0] == '/') ? 1 : 0;
/* 1573 */     return new String(arrayOfChar2, k, j - k);
/*      */   }
/*      */   
/*      */   Runtime.FStat hostFStat(File paramFile, Object paramObject) {
/* 1577 */     boolean bool = false;
/*      */     try {
/* 1579 */       FileInputStream fileInputStream = new FileInputStream(paramFile);
/* 1580 */       switch (fileInputStream.read()) { case 127:
/* 1581 */           bool = (fileInputStream.read() == 69 && fileInputStream.read() == 76 && fileInputStream.read() == 70); break;
/* 1582 */         case 35: bool = (fileInputStream.read() == 33); break; }
/*      */       
/* 1584 */       fileInputStream.close();
/* 1585 */     } catch (IOException iOException) {}
/* 1586 */     HostFS hostFS = (HostFS)paramObject;
/* 1587 */     short s = hostFS.inodes.get(paramFile.getAbsolutePath());
/* 1588 */     int i = hostFS.devno;
/* 1589 */     return new Runtime.HostFStat(this, paramFile, bool, s, i) { private final int val$inode; private final int val$devno; private final UnixRuntime this$0;
/* 1590 */         public int inode() { return this.val$inode; }
/* 1591 */         public int dev() { return this.val$devno; } }
/*      */       ;
/*      */   }
/*      */   
/*      */   Runtime.FD hostFSDirFD(File paramFile, Object paramObject) {
/* 1596 */     HostFS hostFS = (HostFS)paramObject;
/* 1597 */     hostFS.getClass(); return new HostFS.HostDirFD(hostFS, paramFile);
/*      */   }
/*      */   
/*      */   public static class HostFS extends FS { InodeCache inodes;
/*      */     protected File root;
/*      */     
/* 1603 */     public File getRoot() { return this.root; }
/*      */     
/*      */     protected File hostFile(String param1String) {
/* 1606 */       char c = File.separatorChar;
/* 1607 */       if (c != '/') {
/* 1608 */         char[] arrayOfChar = param1String.toCharArray();
/* 1609 */         for (byte b = 0; b < arrayOfChar.length; b++) {
/* 1610 */           char c1 = arrayOfChar[b];
/* 1611 */           if (c1 == '/') { arrayOfChar[b] = c; }
/* 1612 */           else if (c1 == c) { arrayOfChar[b] = '/'; }
/*      */         
/* 1614 */         }  param1String = new String(arrayOfChar);
/*      */       } 
/* 1616 */       return new File(this.root, param1String);
/*      */     }
/*      */     
/* 1619 */     public HostFS(String param1String) { this(new File(param1String)); } public HostFS(File param1File) { this.inodes = new InodeCache('ྠ');
/* 1620 */       this.root = param1File; }
/*      */     
/*      */     public Runtime.FD open(UnixRuntime param1UnixRuntime, String param1String, int param1Int1, int param1Int2) throws Runtime.ErrnoException {
/* 1623 */       File file = hostFile(param1String);
/* 1624 */       return param1UnixRuntime.hostFSOpen(file, param1Int1, param1Int2, this);
/*      */     }
/*      */     
/*      */     public void unlink(UnixRuntime param1UnixRuntime, String param1String) throws Runtime.ErrnoException {
/* 1628 */       File file = hostFile(param1String);
/* 1629 */       if (param1UnixRuntime.sm != null && !param1UnixRuntime.sm.allowUnlink(file)) throw new Runtime.ErrnoException(true); 
/* 1630 */       if (!file.exists()) throw new Runtime.ErrnoException(2); 
/* 1631 */       if (!file.delete()) {
/*      */ 
/*      */         
/* 1634 */         boolean bool = false;
/* 1635 */         for (byte b = 0; b < 64; b++) {
/* 1636 */           if (param1UnixRuntime.fds[b] != null) {
/* 1637 */             String str = param1UnixRuntime.fds[b].getNormalizedPath();
/* 1638 */             if (str != null && str.equals(param1String)) {
/* 1639 */               param1UnixRuntime.fds[b].markDeleteOnClose();
/* 1640 */               bool = true;
/*      */             } 
/*      */           } 
/*      */         } 
/* 1644 */         if (!bool) throw new Runtime.ErrnoException(true); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public Runtime.FStat stat(UnixRuntime param1UnixRuntime, String param1String) throws Runtime.ErrnoException {
/* 1649 */       File file = hostFile(param1String);
/* 1650 */       if (param1UnixRuntime.sm != null && !param1UnixRuntime.sm.allowStat(file)) throw new Runtime.ErrnoException(13); 
/* 1651 */       if (!file.exists()) return null; 
/* 1652 */       return param1UnixRuntime.hostFStat(file, this);
/*      */     }
/*      */     
/*      */     public void mkdir(UnixRuntime param1UnixRuntime, String param1String, int param1Int) throws Runtime.ErrnoException {
/* 1656 */       File file1 = hostFile(param1String);
/* 1657 */       if (param1UnixRuntime.sm != null && !param1UnixRuntime.sm.allowWrite(file1)) throw new Runtime.ErrnoException(13); 
/* 1658 */       if (file1.exists() && file1.isDirectory()) throw new Runtime.ErrnoException(17); 
/* 1659 */       if (file1.exists()) throw new Runtime.ErrnoException(20); 
/* 1660 */       File file2 = getParentFile(file1);
/* 1661 */       if (file2 != null && (!file2.exists() || !file2.isDirectory())) throw new Runtime.ErrnoException(20); 
/* 1662 */       if (!file1.mkdir()) throw new Runtime.ErrnoException(5); 
/*      */     }
/*      */     
/*      */     private static File getParentFile(File param1File) {
/* 1666 */       String str = param1File.getParent();
/* 1667 */       return (str == null) ? null : new File(str);
/*      */     }
/*      */     public class HostDirFD extends UnixRuntime.DirFD { private final File f; private final File[] children;
/*      */       private final UnixRuntime.HostFS this$0;
/*      */       
/*      */       public HostDirFD(UnixRuntime.HostFS this$0, File param2File) {
/* 1673 */         this.this$0 = this$0;
/* 1674 */         this.f = param2File;
/* 1675 */         String[] arrayOfString = param2File.list();
/* 1676 */         this.children = new File[arrayOfString.length];
/* 1677 */         for (byte b = 0; b < arrayOfString.length; ) { this.children[b] = new File(param2File, arrayOfString[b]); b++; }
/*      */       
/* 1679 */       } public int size() { return this.children.length; }
/* 1680 */       public String name(int param2Int) { return this.children[param2Int].getName(); }
/* 1681 */       public int inode(int param2Int) { return this.this$0.inodes.get(this.children[param2Int].getAbsolutePath()); }
/*      */       public int parentInode() {
/* 1683 */         File file = UnixRuntime.HostFS.getParentFile(this.f);
/*      */         
/* 1685 */         return (file == null) ? myInode() : this.this$0.inodes.get(file.getAbsolutePath());
/*      */       }
/* 1687 */       public int myInode() { return this.this$0.inodes.get(this.f.getAbsolutePath()); }
/* 1688 */       public int myDev() { return this.this$0.devno; } } } public class HostDirFD extends DirFD { private final File f; private final File[] children; private final UnixRuntime.HostFS this$0; public HostDirFD(UnixRuntime this$0, File param1File) { this.this$0 = this$0; this.f = param1File; String[] arrayOfString = param1File.list(); this.children = new File[arrayOfString.length]; for (byte b = 0; b < arrayOfString.length; ) { this.children[b] = new File(param1File, arrayOfString[b]); b++; }  } public int size() { return this.children.length; } public String name(int param1Int) { return this.children[param1Int].getName(); } public int inode(int param1Int) { return this.this$0.inodes.get(this.children[param1Int].getAbsolutePath()); } public int parentInode() { File file = UnixRuntime.HostFS.getParentFile(this.f); return (file == null) ? myInode() : this.this$0.inodes.get(file.getAbsolutePath()); } public int myInode() { return this.this$0.inodes.get(this.f.getAbsolutePath()); } public int myDev() { return this.this$0.devno; } }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class CygdriveFS
/*      */     extends HostFS
/*      */   {
/*      */     protected File hostFile(String param1String) {
/* 1697 */       char c = param1String.charAt(0);
/*      */       
/* 1699 */       if (c < 'a' || c > 'z' || param1String.charAt(1) != '/') {
/* 1700 */         return null;
/*      */       }
/* 1702 */       param1String = c + ":" + param1String.substring(1).replace('/', '\\');
/* 1703 */       return new File(param1String);
/*      */     }
/*      */     
/* 1706 */     public CygdriveFS() { super("/"); }
/*      */   }
/*      */   
/*      */   private static void putInt(byte[] paramArrayOfByte, int paramInt1, int paramInt2) {
/* 1710 */     paramArrayOfByte[paramInt1 + 0] = (byte)(paramInt2 >>> 24 & 0xFF);
/* 1711 */     paramArrayOfByte[paramInt1 + 1] = (byte)(paramInt2 >>> 16 & 0xFF);
/* 1712 */     paramArrayOfByte[paramInt1 + 2] = (byte)(paramInt2 >>> 8 & 0xFF);
/* 1713 */     paramArrayOfByte[paramInt1 + 3] = (byte)(paramInt2 >>> 0 & 0xFF);
/*      */   }
/*      */   
/*      */   public static abstract class DirFD extends Runtime.FD {
/* 1717 */     private int pos = -2;
/*      */     protected abstract int size();
/*      */     protected abstract String name(int param1Int);
/*      */     protected abstract int inode(int param1Int);
/*      */     protected abstract int myDev();
/*      */     protected abstract int parentInode();
/*      */     protected abstract int myInode();
/*      */     
/* 1725 */     public int flags() { return 0; }
/*      */     
/*      */     public int getdents(byte[] param1ArrayOfByte, int param1Int1, int param1Int2) {
/* 1728 */       int i = param1Int1;
/*      */ 
/*      */       
/* 1731 */       for (; param1Int2 > 0 && this.pos < size(); this.pos++) {
/* 1732 */         byte[] arrayOfByte; String str; int k; int j; switch (this.pos) {
/*      */           case -2:
/*      */           case -1:
/* 1735 */             j = (this.pos == -1) ? parentInode() : myInode();
/* 1736 */             if (j == -1)
/* 1737 */               break;  k = 9 + ((this.pos == -1) ? 2 : 1);
/* 1738 */             if (k > param1Int2)
/* 1739 */               break;  param1ArrayOfByte[param1Int1 + 8] = 46;
/* 1740 */             if (this.pos == -1) param1ArrayOfByte[param1Int1 + 9] = 46;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1751 */             param1ArrayOfByte[param1Int1 + k - 1] = 0;
/* 1752 */             k = k + 3 & 0xFFFFFFFC;
/* 1753 */             UnixRuntime.putInt(param1ArrayOfByte, param1Int1, k);
/* 1754 */             UnixRuntime.putInt(param1ArrayOfByte, param1Int1 + 4, j);
/* 1755 */             param1Int1 += k;
/* 1756 */             param1Int2 -= k; break;default: str = name(this.pos); arrayOfByte = Runtime.getBytes(str); k = arrayOfByte.length + 9; if (k > param1Int2) break;  j = inode(this.pos); System.arraycopy(arrayOfByte, 0, param1ArrayOfByte, param1Int1 + 8, arrayOfByte.length); param1ArrayOfByte[param1Int1 + k - 1] = 0; k = k + 3 & 0xFFFFFFFC; UnixRuntime.putInt(param1ArrayOfByte, param1Int1, k); UnixRuntime.putInt(param1ArrayOfByte, param1Int1 + 4, j); param1Int1 += k; param1Int2 -= k; break;
/*      */         } 
/* 1758 */       }  return param1Int1 - i;
/*      */     }
/*      */     
/*      */     protected Runtime.FStat _fstat() {
/* 1762 */       return new Runtime.FStat(this) { private final UnixRuntime.DirFD this$0;
/* 1763 */           public int type() { return 16384; }
/* 1764 */           public int inode() { return this.this$0.myInode(); }
/* 1765 */           public int dev() { return this.this$0.myDev(); } }
/*      */         ;
/*      */     } }
/*      */   
/*      */   public static class DevFS extends FS { private static final int ROOT_INODE = 1;
/*      */     private static final int NULL_INODE = 2;
/*      */     private static final int ZERO_INODE = 3;
/*      */     private static final int FD_INODE = 4;
/*      */     private static final int FD_INODES = 32;
/*      */     
/*      */     private abstract class DevFStat extends Runtime.FStat { private final UnixRuntime.DevFS this$0;
/*      */       
/* 1777 */       private DevFStat(UnixRuntime.DevFS this$0) { this.this$0 = this$0; }
/* 1778 */       public int dev() { return this.this$0.devno; }
/* 1779 */       public int mode() { return 438; }
/* 1780 */       public int type() { return 8192; }
/* 1781 */       public int nlink() { return 1; }
/*      */       public abstract int inode(); }
/*      */     private abstract class DevDirFD extends UnixRuntime.DirFD { private final UnixRuntime.DevFS this$0;
/*      */       
/* 1785 */       private DevDirFD(UnixRuntime.DevFS this$0) { this.this$0 = this$0; }
/* 1786 */       public int myDev() { return this.this$0.devno; } }
/*      */ 
/*      */     
/* 1789 */     private Runtime.FD devZeroFD = new Runtime.FD(this) { private final UnixRuntime.DevFS this$0;
/*      */         
/*      */         public int read(byte[] param2ArrayOfByte, int param2Int1, int param2Int2) {
/* 1792 */           for (int i = param2Int1; i < param2Int1 + param2Int2; ) { param2ArrayOfByte[i] = 0; i++; }
/* 1793 */            return param2Int2;
/*      */         }
/* 1795 */         public int write(byte[] param2ArrayOfByte, int param2Int1, int param2Int2) { return param2Int2; }
/* 1796 */         public int seek(int param2Int1, int param2Int2) throws Runtime.ErrnoException, Runtime.ReadFaultException { return 0; }
/* 1797 */         public Runtime.FStat _fstat() { return new UnixRuntime.DevFS.DevFStat(this) { private final UnixRuntime.DevFS.null this$1; public int inode() { return 3; } }
/* 1798 */             ; } public int flags() { return 2; } }
/*      */     ;
/* 1800 */     private Runtime.FD devNullFD = new Runtime.FD(this) { private final UnixRuntime.DevFS this$0;
/* 1801 */         public int read(byte[] param2ArrayOfByte, int param2Int1, int param2Int2) { return 0; }
/* 1802 */         public int write(byte[] param2ArrayOfByte, int param2Int1, int param2Int2) { return param2Int2; }
/* 1803 */         public int seek(int param2Int1, int param2Int2) throws Runtime.ErrnoException, Runtime.ReadFaultException { return 0; }
/* 1804 */         public Runtime.FStat _fstat() { return new UnixRuntime.DevFS.DevFStat(this) { private final UnixRuntime.DevFS.null this$1; public int inode() { return 2; } }
/* 1805 */             ; } public int flags() { return 2; } }
/*      */     ;
/*      */     
/*      */     public Runtime.FD open(UnixRuntime param1UnixRuntime, String param1String, int param1Int1, int param1Int2) throws Runtime.ErrnoException {
/* 1809 */       if (param1String.equals("null")) return this.devNullFD; 
/* 1810 */       if (param1String.equals("zero")) return this.devZeroFD; 
/* 1811 */       if (param1String.startsWith("fd/")) {
/*      */         int i;
/*      */         try {
/* 1814 */           i = Integer.parseInt(param1String.substring(4));
/* 1815 */         } catch (NumberFormatException numberFormatException) {
/* 1816 */           return null;
/*      */         } 
/* 1818 */         if (i < 0 || i >= 64) return null; 
/* 1819 */         if (param1UnixRuntime.fds[i] == null) return null; 
/* 1820 */         return param1UnixRuntime.fds[i].dup();
/*      */       } 
/* 1822 */       if (param1String.equals("fd")) {
/* 1823 */         byte b1 = 0;
/* 1824 */         for (null = 0; null < 64; ) { if (param1UnixRuntime.fds[null] != null) b1++;  null++; }
/* 1825 */          int[] arrayOfInt = new int[b1];
/* 1826 */         b1 = 0;
/* 1827 */         for (byte b2 = 0; b2 < 64; ) { if (param1UnixRuntime.fds[b2] != null) arrayOfInt[b1++] = b2;  b2++; }
/* 1828 */          return new DevDirFD(this, arrayOfInt) { private final int[] val$files; private final UnixRuntime.DevFS this$0;
/* 1829 */             public int myInode() { return 4; }
/* 1830 */             public int parentInode() { return 1; }
/* 1831 */             public int inode(int param2Int) { return 32 + param2Int; }
/* 1832 */             public String name(int param2Int) { return Integer.toString(this.val$files[param2Int]); }
/* 1833 */             public int size() { return this.val$files.length; } }
/*      */           ;
/*      */       } 
/* 1836 */       if (param1String.equals("")) {
/* 1837 */         return new DevDirFD(this) { private final UnixRuntime.DevFS this$0;
/* 1838 */             public int myInode() { return 1; }
/*      */             
/* 1840 */             public int parentInode() { return 1; }
/*      */             public int inode(int param2Int) {
/* 1842 */               switch (param2Int) { case 0:
/* 1843 */                   return 2;
/* 1844 */                 case 1: return 3;
/* 1845 */                 case 2: return 4; }
/* 1846 */                return -1;
/*      */             }
/*      */ 
/*      */             
/*      */             public String name(int param2Int) {
/* 1851 */               switch (param2Int) { case 0:
/* 1852 */                   return "null";
/* 1853 */                 case 1: return "zero";
/* 1854 */                 case 2: return "fd"; }
/* 1855 */                return null;
/*      */             }
/*      */             
/* 1858 */             public int size() { return 3; } }
/*      */           ;
/*      */       }
/* 1861 */       return null;
/*      */     }
/*      */     
/*      */     public Runtime.FStat stat(UnixRuntime param1UnixRuntime, String param1String) throws Runtime.ErrnoException {
/* 1865 */       if (param1String.equals("null")) return this.devNullFD.fstat(); 
/* 1866 */       if (param1String.equals("zero")) return this.devZeroFD.fstat(); 
/* 1867 */       if (param1String.startsWith("fd/")) {
/*      */         int i;
/*      */         try {
/* 1870 */           i = Integer.parseInt(param1String.substring(3));
/* 1871 */         } catch (NumberFormatException numberFormatException) {
/* 1872 */           return null;
/*      */         } 
/* 1874 */         if (i < 0 || i >= 64) return null; 
/* 1875 */         if (param1UnixRuntime.fds[i] == null) return null; 
/* 1876 */         return param1UnixRuntime.fds[i].fstat();
/*      */       } 
/* 1878 */       if (param1String.equals("fd")) return new Runtime.FStat(this) { private final UnixRuntime.DevFS this$0; public int inode() { return 4; } public int dev() { return this.this$0.devno; } public int type() { return 16384; } public int mode() { return 292; } }; 
/* 1879 */       if (param1String.equals("")) return new Runtime.FStat(this) { private final UnixRuntime.DevFS this$0; public int inode() { return 1; } public int dev() { return this.this$0.devno; } public int type() { return 16384; } public int mode() { return 292; } }; 
/* 1880 */       return null;
/*      */     }
/*      */     
/* 1883 */     public void mkdir(UnixRuntime param1UnixRuntime, String param1String, int param1Int) throws Runtime.ErrnoException { throw new Runtime.ErrnoException(30); }
/* 1884 */     public void unlink(UnixRuntime param1UnixRuntime, String param1String) throws Runtime.ErrnoException { throw new Runtime.ErrnoException(30); } }
/*      */ 
/*      */   
/*      */   public static class ResourceFS
/*      */     extends FS {
/* 1889 */     final InodeCache inodes = new InodeCache('Ǵ');
/*      */     
/* 1891 */     public Runtime.FStat lstat(UnixRuntime param1UnixRuntime, String param1String) throws Runtime.ErrnoException { return stat(param1UnixRuntime, param1String); }
/* 1892 */     public void mkdir(UnixRuntime param1UnixRuntime, String param1String, int param1Int) throws Runtime.ErrnoException { throw new Runtime.ErrnoException(30); }
/* 1893 */     public void unlink(UnixRuntime param1UnixRuntime, String param1String) throws Runtime.ErrnoException { throw new Runtime.ErrnoException(30); }
/*      */     
/*      */     Runtime.FStat connFStat(URLConnection param1URLConnection) {
/* 1896 */       return new Runtime.FStat(this, param1URLConnection) { private final URLConnection val$conn; private final UnixRuntime.ResourceFS this$0;
/* 1897 */           public int type() { return 32768; }
/* 1898 */           public int nlink() { return 1; }
/* 1899 */           public int mode() { return 292; }
/* 1900 */           public int size() { return this.val$conn.getContentLength(); }
/* 1901 */           public int mtime() { return (int)(this.val$conn.getDate() / 1000L); }
/* 1902 */           public int inode() { return this.this$0.inodes.get(this.val$conn.getURL().toString()); }
/* 1903 */           public int dev() { return this.this$0.devno; } }
/*      */         ;
/*      */     }
/*      */     
/*      */     public Runtime.FStat stat(UnixRuntime param1UnixRuntime, String param1String) throws Runtime.ErrnoException {
/* 1908 */       URL uRL = param1UnixRuntime.getClass().getResource("/" + param1String);
/* 1909 */       if (uRL == null) return null; 
/*      */       try {
/* 1911 */         return connFStat(uRL.openConnection());
/* 1912 */       } catch (IOException iOException) {
/* 1913 */         throw new Runtime.ErrnoException(5);
/*      */       } 
/*      */     }
/*      */     
/*      */     public Runtime.FD open(UnixRuntime param1UnixRuntime, String param1String, int param1Int1, int param1Int2) throws Runtime.ErrnoException {
/* 1918 */       if ((param1Int1 & 0xFFFFFFFC) != 0) {
/*      */         
/* 1920 */         System.err.println("WARNING: Unsupported flags passed to ResourceFS.open(\"" + param1String + "\"): " + Runtime.toHex(param1Int1 & 0xFFFFFFFC));
/* 1921 */         throw new Runtime.ErrnoException('');
/*      */       } 
/* 1923 */       if ((param1Int1 & 0x3) != 0) throw new Runtime.ErrnoException(30); 
/* 1924 */       URL uRL = param1UnixRuntime.getClass().getResource("/" + param1String);
/* 1925 */       if (uRL == null) return null; 
/*      */       
/* 1927 */       try { URLConnection uRLConnection = uRL.openConnection();
/* 1928 */         Seekable.InputStream inputStream = new Seekable.InputStream(uRLConnection.getInputStream());
/* 1929 */         return new Runtime.SeekableFD(this, inputStream, param1Int1, uRLConnection) { private final URLConnection val$conn; private final UnixRuntime.ResourceFS this$0; protected Runtime.FStat _fstat() { return this.this$0.connFStat(this.val$conn); } }; }
/* 1930 */       catch (FileNotFoundException fileNotFoundException)
/* 1931 */       { if (fileNotFoundException.getMessage() != null && fileNotFoundException.getMessage().indexOf("Permission denied") >= 0) throw new Runtime.ErrnoException(13); 
/* 1932 */         return null; }
/* 1933 */       catch (IOException iOException) { throw new Runtime.ErrnoException(5); }
/*      */     
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\ibex\nestedvm\UnixRuntime.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.0.4
 */