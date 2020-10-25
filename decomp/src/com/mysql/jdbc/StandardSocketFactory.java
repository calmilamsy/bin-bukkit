/*     */ package com.mysql.jdbc;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.InetAddress;
/*     */ import java.net.Socket;
/*     */ import java.net.SocketException;
/*     */ import java.util.Properties;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StandardSocketFactory
/*     */   implements SocketFactory
/*     */ {
/*     */   public static final String TCP_NO_DELAY_PROPERTY_NAME = "tcpNoDelay";
/*     */   public static final String TCP_KEEP_ALIVE_DEFAULT_VALUE = "true";
/*     */   public static final String TCP_KEEP_ALIVE_PROPERTY_NAME = "tcpKeepAlive";
/*     */   public static final String TCP_RCV_BUF_PROPERTY_NAME = "tcpRcvBuf";
/*     */   public static final String TCP_SND_BUF_PROPERTY_NAME = "tcpSndBuf";
/*     */   public static final String TCP_TRAFFIC_CLASS_PROPERTY_NAME = "tcpTrafficClass";
/*     */   public static final String TCP_RCV_BUF_DEFAULT_VALUE = "0";
/*     */   public static final String TCP_SND_BUF_DEFAULT_VALUE = "0";
/*     */   public static final String TCP_TRAFFIC_CLASS_DEFAULT_VALUE = "0";
/*     */   public static final String TCP_NO_DELAY_DEFAULT_VALUE = "true";
/*     */   private static Method setTraficClassMethod;
/*     */   
/*     */   static  {
/*     */     try {
/*  70 */       setTraficClassMethod = Socket.class.getMethod("setTrafficClass", new Class[] { int.class });
/*     */     }
/*  72 */     catch (SecurityException e) {
/*  73 */       setTraficClassMethod = null;
/*  74 */     } catch (NoSuchMethodException e) {
/*  75 */       setTraficClassMethod = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*  80 */   protected String host = null;
/*     */ 
/*     */   
/*  83 */   protected int port = 3306;
/*     */ 
/*     */   
/*  86 */   protected Socket rawSocket = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 100 */   public Socket afterHandshake() throws SocketException, IOException { return this.rawSocket; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 115 */   public Socket beforeHandshake() throws SocketException, IOException { return this.rawSocket; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void configureSocket(Socket sock, Properties props) throws SocketException, IOException {
/*     */     try {
/* 129 */       sock.setTcpNoDelay(Boolean.valueOf(props.getProperty("tcpNoDelay", "true")).booleanValue());
/*     */ 
/*     */ 
/*     */       
/* 133 */       String keepAlive = props.getProperty("tcpKeepAlive", "true");
/*     */ 
/*     */       
/* 136 */       if (keepAlive != null && keepAlive.length() > 0) {
/* 137 */         sock.setKeepAlive(Boolean.valueOf(keepAlive).booleanValue());
/*     */       }
/*     */ 
/*     */       
/* 141 */       int receiveBufferSize = Integer.parseInt(props.getProperty("tcpRcvBuf", "0"));
/*     */ 
/*     */       
/* 144 */       if (receiveBufferSize > 0) {
/* 145 */         sock.setReceiveBufferSize(receiveBufferSize);
/*     */       }
/*     */       
/* 148 */       int sendBufferSize = Integer.parseInt(props.getProperty("tcpSndBuf", "0"));
/*     */ 
/*     */       
/* 151 */       if (sendBufferSize > 0) {
/* 152 */         sock.setSendBufferSize(sendBufferSize);
/*     */       }
/*     */       
/* 155 */       int trafficClass = Integer.parseInt(props.getProperty("tcpTrafficClass", "0"));
/*     */ 
/*     */ 
/*     */       
/* 159 */       if (trafficClass > 0 && setTraficClassMethod != null) {
/* 160 */         setTraficClassMethod.invoke(sock, new Object[] { new Integer(trafficClass) });
/*     */       }
/*     */     }
/* 163 */     catch (Throwable t) {
/* 164 */       unwrapExceptionToProperClassAndThrowIt(t);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Socket connect(String hostname, int portNumber, Properties props) throws SocketException, IOException {
/* 174 */     if (props != null) {
/* 175 */       this.host = hostname;
/*     */       
/* 177 */       this.port = portNumber;
/*     */       
/* 179 */       Method connectWithTimeoutMethod = null;
/* 180 */       Method socketBindMethod = null;
/* 181 */       Class socketAddressClass = null;
/*     */       
/* 183 */       String localSocketHostname = props.getProperty("localSocketAddress");
/*     */ 
/*     */       
/* 186 */       String connectTimeoutStr = props.getProperty("connectTimeout");
/*     */       
/* 188 */       int connectTimeout = 0;
/*     */       
/* 190 */       boolean wantsTimeout = (connectTimeoutStr != null && connectTimeoutStr.length() > 0 && !connectTimeoutStr.equals("0"));
/*     */ 
/*     */ 
/*     */       
/* 194 */       boolean wantsLocalBind = (localSocketHostname != null && localSocketHostname.length() > 0);
/*     */ 
/*     */       
/* 197 */       boolean needsConfigurationBeforeConnect = socketNeedsConfigurationBeforeConnect(props);
/*     */       
/* 199 */       if (wantsTimeout || wantsLocalBind || needsConfigurationBeforeConnect) {
/*     */         
/* 201 */         if (connectTimeoutStr != null) {
/*     */           try {
/* 203 */             connectTimeout = Integer.parseInt(connectTimeoutStr);
/* 204 */           } catch (NumberFormatException nfe) {
/* 205 */             throw new SocketException("Illegal value '" + connectTimeoutStr + "' for connectTimeout");
/*     */           } 
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/* 213 */           socketAddressClass = Class.forName("java.net.SocketAddress");
/*     */ 
/*     */           
/* 216 */           connectWithTimeoutMethod = Socket.class.getMethod("connect", new Class[] { socketAddressClass, int.class });
/*     */ 
/*     */ 
/*     */           
/* 220 */           socketBindMethod = Socket.class.getMethod("bind", new Class[] { socketAddressClass });
/*     */         
/*     */         }
/* 223 */         catch (NoClassDefFoundError noClassDefFound) {
/*     */         
/* 225 */         } catch (NoSuchMethodException noSuchMethodEx) {
/*     */         
/* 227 */         } catch (Throwable catchAll) {}
/*     */ 
/*     */ 
/*     */         
/* 231 */         if (wantsLocalBind && socketBindMethod == null) {
/* 232 */           throw new SocketException("Can't specify \"localSocketAddress\" on JVMs older than 1.4");
/*     */         }
/*     */ 
/*     */         
/* 236 */         if (wantsTimeout && connectWithTimeoutMethod == null) {
/* 237 */           throw new SocketException("Can't specify \"connectTimeout\" on JVMs older than 1.4");
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 242 */       if (this.host != null) {
/* 243 */         if (!wantsLocalBind && !wantsTimeout && !needsConfigurationBeforeConnect) {
/* 244 */           InetAddress[] possibleAddresses = InetAddress.getAllByName(this.host);
/*     */ 
/*     */           
/* 247 */           Throwable caughtWhileConnecting = null;
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 252 */           for (int i = 0; i < possibleAddresses.length; i++) {
/*     */             try {
/* 254 */               this.rawSocket = new Socket(possibleAddresses[i], this.port);
/*     */ 
/*     */               
/* 257 */               configureSocket(this.rawSocket, props);
/*     */               
/*     */               break;
/* 260 */             } catch (Exception ex) {
/* 261 */               caughtWhileConnecting = ex;
/*     */             } 
/*     */           } 
/*     */           
/* 265 */           if (this.rawSocket == null) {
/* 266 */             unwrapExceptionToProperClassAndThrowIt(caughtWhileConnecting);
/*     */           }
/*     */         } else {
/*     */ 
/*     */           
/*     */           try {
/*     */             
/* 273 */             InetAddress[] possibleAddresses = InetAddress.getAllByName(this.host);
/*     */ 
/*     */             
/* 276 */             Throwable caughtWhileConnecting = null;
/*     */             
/* 278 */             Object localSockAddr = null;
/*     */             
/* 280 */             Class inetSocketAddressClass = null;
/*     */             
/* 282 */             Constructor addrConstructor = null;
/*     */             
/*     */             try {
/* 285 */               inetSocketAddressClass = Class.forName("java.net.InetSocketAddress");
/*     */ 
/*     */               
/* 288 */               addrConstructor = inetSocketAddressClass.getConstructor(new Class[] { InetAddress.class, int.class });
/*     */ 
/*     */ 
/*     */               
/* 292 */               if (wantsLocalBind) {
/* 293 */                 localSockAddr = addrConstructor.newInstance(new Object[] { InetAddress.getByName(localSocketHostname), new Integer(false) });
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*     */               }
/*     */ 
/*     */ 
/*     */             
/*     */             }
/* 303 */             catch (Throwable ex) {
/* 304 */               unwrapExceptionToProperClassAndThrowIt(ex);
/*     */             } 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 310 */             for (int i = 0; i < possibleAddresses.length; i++) {
/*     */               
/*     */               try {
/* 313 */                 this.rawSocket = new Socket();
/*     */                 
/* 315 */                 configureSocket(this.rawSocket, props);
/*     */                 
/* 317 */                 Object sockAddr = addrConstructor.newInstance(new Object[] { possibleAddresses[i], new Integer(this.port) });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 324 */                 socketBindMethod.invoke(this.rawSocket, new Object[] { localSockAddr });
/*     */ 
/*     */                 
/* 327 */                 connectWithTimeoutMethod.invoke(this.rawSocket, new Object[] { sockAddr, new Integer(connectTimeout) });
/*     */ 
/*     */ 
/*     */                 
/*     */                 break;
/* 332 */               } catch (Exception ex) {
/* 333 */                 this.rawSocket = null;
/*     */                 
/* 335 */                 caughtWhileConnecting = ex;
/*     */               } 
/*     */             } 
/*     */             
/* 339 */             if (this.rawSocket == null) {
/* 340 */               unwrapExceptionToProperClassAndThrowIt(caughtWhileConnecting);
/*     */             }
/*     */           }
/* 343 */           catch (Throwable t) {
/* 344 */             unwrapExceptionToProperClassAndThrowIt(t);
/*     */           } 
/*     */         } 
/*     */         
/* 348 */         return this.rawSocket;
/*     */       } 
/*     */     } 
/*     */     
/* 352 */     throw new SocketException("Unable to create socket");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean socketNeedsConfigurationBeforeConnect(Properties props) {
/* 361 */     int receiveBufferSize = Integer.parseInt(props.getProperty("tcpRcvBuf", "0"));
/*     */ 
/*     */     
/* 364 */     if (receiveBufferSize > 0) {
/* 365 */       return true;
/*     */     }
/*     */     
/* 368 */     int sendBufferSize = Integer.parseInt(props.getProperty("tcpSndBuf", "0"));
/*     */ 
/*     */     
/* 371 */     if (sendBufferSize > 0) {
/* 372 */       return true;
/*     */     }
/*     */     
/* 375 */     int trafficClass = Integer.parseInt(props.getProperty("tcpTrafficClass", "0"));
/*     */ 
/*     */ 
/*     */     
/* 379 */     if (trafficClass > 0 && setTraficClassMethod != null) {
/* 380 */       return true;
/*     */     }
/*     */     
/* 383 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void unwrapExceptionToProperClassAndThrowIt(Throwable caughtWhileConnecting) throws SocketException, IOException {
/* 389 */     if (caughtWhileConnecting instanceof InvocationTargetException)
/*     */     {
/*     */ 
/*     */       
/* 393 */       caughtWhileConnecting = ((InvocationTargetException)caughtWhileConnecting).getTargetException();
/*     */     }
/*     */ 
/*     */     
/* 397 */     if (caughtWhileConnecting instanceof SocketException) {
/* 398 */       throw (SocketException)caughtWhileConnecting;
/*     */     }
/*     */     
/* 401 */     if (caughtWhileConnecting instanceof IOException) {
/* 402 */       throw (IOException)caughtWhileConnecting;
/*     */     }
/*     */     
/* 405 */     throw new SocketException(caughtWhileConnecting.toString());
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\StandardSocketFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */