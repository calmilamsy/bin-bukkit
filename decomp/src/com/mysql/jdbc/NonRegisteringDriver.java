/*     */ package com.mysql.jdbc;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.net.URLDecoder;
/*     */ import java.sql.Connection;
/*     */ import java.sql.Driver;
/*     */ import java.sql.DriverPropertyInfo;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Properties;
/*     */ import java.util.StringTokenizer;
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
/*     */ public class NonRegisteringDriver
/*     */   implements Driver
/*     */ {
/*     */   private static final String ALLOWED_QUOTES = "\"'";
/*     */   private static final String REPLICATION_URL_PREFIX = "jdbc:mysql:replication://";
/*     */   private static final String URL_PREFIX = "jdbc:mysql://";
/*     */   private static final String MXJ_URL_PREFIX = "jdbc:mysql:mxj://";
/*     */   private static final String LOADBALANCE_URL_PREFIX = "jdbc:mysql:loadbalance://";
/*     */   public static final String DBNAME_PROPERTY_KEY = "DBNAME";
/*     */   public static final boolean DEBUG = false;
/*     */   public static final int HOST_NAME_INDEX = 0;
/*     */   public static final String HOST_PROPERTY_KEY = "HOST";
/*     */   public static final String NUM_HOSTS_PROPERTY_KEY = "NUM_HOSTS";
/*     */   public static final String PASSWORD_PROPERTY_KEY = "password";
/*     */   public static final int PORT_NUMBER_INDEX = 1;
/*     */   public static final String PORT_PROPERTY_KEY = "PORT";
/*     */   public static final String PROPERTIES_TRANSFORM_KEY = "propertiesTransform";
/*     */   public static final boolean TRACE = false;
/*     */   public static final String USE_CONFIG_PROPERTY_KEY = "useConfigs";
/*     */   public static final String USER_PROPERTY_KEY = "user";
/*     */   public static final String PROTOCOL_PROPERTY_KEY = "PROTOCOL";
/*     */   public static final String PATH_PROPERTY_KEY = "PATH";
/*     */   
/* 138 */   static int getMajorVersionInternal() { return safeIntParse("5"); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 147 */   static int getMinorVersionInternal() { return safeIntParse("1"); }
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
/*     */   protected static String[] parseHostPortPair(String hostPortPair) throws SQLException {
/* 168 */     String[] splitValues = new String[2];
/*     */     
/* 170 */     if (StringUtils.startsWithIgnoreCaseAndWs(hostPortPair, "address")) {
/* 171 */       splitValues[0] = hostPortPair.trim();
/* 172 */       splitValues[1] = null;
/*     */       
/* 174 */       return splitValues;
/*     */     } 
/*     */     
/* 177 */     int portIndex = hostPortPair.indexOf(":");
/*     */     
/* 179 */     String hostname = null;
/*     */     
/* 181 */     if (portIndex != -1) {
/* 182 */       if (portIndex + 1 < hostPortPair.length()) {
/* 183 */         String portAsString = hostPortPair.substring(portIndex + 1);
/* 184 */         hostname = hostPortPair.substring(0, portIndex);
/*     */         
/* 186 */         splitValues[0] = hostname;
/*     */         
/* 188 */         splitValues[1] = portAsString;
/*     */       } else {
/* 190 */         throw SQLError.createSQLException(Messages.getString("NonRegisteringDriver.37"), "01S00", null);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 195 */       splitValues[0] = hostPortPair;
/* 196 */       splitValues[1] = null;
/*     */     } 
/*     */     
/* 199 */     return splitValues;
/*     */   }
/*     */   
/*     */   private static int safeIntParse(String intAsString) {
/*     */     try {
/* 204 */       return Integer.parseInt(intAsString);
/* 205 */     } catch (NumberFormatException nfe) {
/* 206 */       return 0;
/*     */     } 
/*     */   }
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
/* 236 */   public boolean acceptsURL(String url) throws SQLException { return (parseURL(url, null) != null); }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Connection connect(String url, Properties info) throws SQLException {
/* 285 */     if (url != null) {
/* 286 */       if (StringUtils.startsWithIgnoreCase(url, "jdbc:mysql:loadbalance://"))
/* 287 */         return connectLoadBalanced(url, info); 
/* 288 */       if (StringUtils.startsWithIgnoreCase(url, "jdbc:mysql:replication://"))
/*     */       {
/* 290 */         return connectReplicationConnection(url, info);
/*     */       }
/*     */     } 
/*     */     
/* 294 */     Properties props = null;
/*     */     
/* 296 */     if ((props = parseURL(url, info)) == null) {
/* 297 */       return null;
/*     */     }
/*     */     
/* 300 */     if (!"1".equals(props.getProperty("NUM_HOSTS"))) {
/* 301 */       return connectFailover(url, info);
/*     */     }
/*     */     
/*     */     try {
/* 305 */       return ConnectionImpl.getInstance(host(props), port(props), props, database(props), url);
/*     */ 
/*     */     
/*     */     }
/* 309 */     catch (SQLException sqlEx) {
/*     */ 
/*     */       
/* 312 */       throw sqlEx;
/* 313 */     } catch (Exception ex) {
/* 314 */       SQLException sqlEx = SQLError.createSQLException(Messages.getString("NonRegisteringDriver.17") + ex.toString() + Messages.getString("NonRegisteringDriver.18"), "08001", null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 320 */       sqlEx.initCause(ex);
/*     */       
/* 322 */       throw sqlEx;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private Connection connectLoadBalanced(String url, Properties info) throws SQLException {
/* 328 */     Properties parsedProps = parseURL(url, info);
/*     */ 
/*     */     
/* 331 */     parsedProps.remove("roundRobinLoadBalance");
/*     */     
/* 333 */     if (parsedProps == null) {
/* 334 */       return null;
/*     */     }
/*     */     
/* 337 */     int numHosts = Integer.parseInt(parsedProps.getProperty("NUM_HOSTS"));
/*     */     
/* 339 */     List<String> hostList = new ArrayList<String>();
/*     */     
/* 341 */     for (int i = 0; i < numHosts; i++) {
/* 342 */       int index = i + 1;
/*     */       
/* 344 */       hostList.add(parsedProps.getProperty("HOST." + index) + ":" + parsedProps.getProperty("PORT." + index));
/*     */     } 
/*     */ 
/*     */     
/* 348 */     LoadBalancingConnectionProxy proxyBal = new LoadBalancingConnectionProxy(hostList, parsedProps);
/*     */ 
/*     */     
/* 351 */     return (Connection)Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] { Connection.class }, proxyBal);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Connection connectFailover(String url, Properties info) throws SQLException {
/* 358 */     Properties parsedProps = parseURL(url, info);
/*     */ 
/*     */     
/* 361 */     parsedProps.remove("roundRobinLoadBalance");
/* 362 */     parsedProps.setProperty("autoReconnect", "false");
/*     */     
/* 364 */     if (parsedProps == null) {
/* 365 */       return null;
/*     */     }
/*     */     
/* 368 */     int numHosts = Integer.parseInt(parsedProps.getProperty("NUM_HOSTS"));
/*     */ 
/*     */     
/* 371 */     List<String> hostList = new ArrayList<String>();
/*     */     
/* 373 */     for (int i = 0; i < numHosts; i++) {
/* 374 */       int index = i + 1;
/*     */       
/* 376 */       hostList.add(parsedProps.getProperty("HOST." + index) + ":" + parsedProps.getProperty("PORT." + index));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 382 */     FailoverConnectionProxy connProxy = new FailoverConnectionProxy(hostList, parsedProps);
/*     */ 
/*     */     
/* 385 */     return (Connection)Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] { Connection.class }, connProxy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Connection connectReplicationConnection(String url, Properties info) throws SQLException {
/* 392 */     Properties parsedProps = parseURL(url, info);
/*     */     
/* 394 */     if (parsedProps == null) {
/* 395 */       return null;
/*     */     }
/*     */     
/* 398 */     Properties masterProps = (Properties)parsedProps.clone();
/* 399 */     Properties slavesProps = (Properties)parsedProps.clone();
/*     */ 
/*     */ 
/*     */     
/* 403 */     slavesProps.setProperty("com.mysql.jdbc.ReplicationConnection.isSlave", "true");
/*     */ 
/*     */     
/* 406 */     int numHosts = Integer.parseInt(parsedProps.getProperty("NUM_HOSTS"));
/*     */     
/* 408 */     if (numHosts < 2) {
/* 409 */       throw SQLError.createSQLException("Must specify at least one slave host to connect to for master/slave replication load-balancing functionality", "01S00", null);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 415 */     for (int i = 1; i < numHosts; i++) {
/* 416 */       int index = i + 1;
/*     */       
/* 418 */       masterProps.remove("HOST." + index);
/* 419 */       masterProps.remove("PORT." + index);
/*     */       
/* 421 */       slavesProps.setProperty("HOST." + i, parsedProps.getProperty("HOST." + index));
/* 422 */       slavesProps.setProperty("PORT." + i, parsedProps.getProperty("PORT." + index));
/*     */     } 
/*     */     
/* 425 */     masterProps.setProperty("NUM_HOSTS", "1");
/* 426 */     slavesProps.remove("HOST." + numHosts);
/* 427 */     slavesProps.remove("PORT." + numHosts);
/* 428 */     slavesProps.setProperty("NUM_HOSTS", String.valueOf(numHosts - 1));
/* 429 */     slavesProps.setProperty("HOST", slavesProps.getProperty("HOST.1"));
/* 430 */     slavesProps.setProperty("PORT", slavesProps.getProperty("PORT.1"));
/*     */     
/* 432 */     return new ReplicationConnection(masterProps, slavesProps);
/*     */   }
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
/* 444 */   public String database(Properties props) { return props.getProperty("DBNAME"); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 453 */   public int getMajorVersion() { return getMajorVersionInternal(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 462 */   public int getMinorVersion() { return getMinorVersionInternal(); }
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
/*     */   public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
/* 493 */     if (info == null) {
/* 494 */       info = new Properties();
/*     */     }
/*     */     
/* 497 */     if (url != null && url.startsWith("jdbc:mysql://")) {
/* 498 */       info = parseURL(url, info);
/*     */     }
/*     */     
/* 501 */     DriverPropertyInfo hostProp = new DriverPropertyInfo("HOST", info.getProperty("HOST"));
/*     */     
/* 503 */     hostProp.required = true;
/* 504 */     hostProp.description = Messages.getString("NonRegisteringDriver.3");
/*     */     
/* 506 */     DriverPropertyInfo portProp = new DriverPropertyInfo("PORT", info.getProperty("PORT", "3306"));
/*     */     
/* 508 */     portProp.required = false;
/* 509 */     portProp.description = Messages.getString("NonRegisteringDriver.7");
/*     */     
/* 511 */     DriverPropertyInfo dbProp = new DriverPropertyInfo("DBNAME", info.getProperty("DBNAME"));
/*     */     
/* 513 */     dbProp.required = false;
/* 514 */     dbProp.description = "Database name";
/*     */     
/* 516 */     DriverPropertyInfo userProp = new DriverPropertyInfo("user", info.getProperty("user"));
/*     */     
/* 518 */     userProp.required = true;
/* 519 */     userProp.description = Messages.getString("NonRegisteringDriver.13");
/*     */     
/* 521 */     DriverPropertyInfo passwordProp = new DriverPropertyInfo("password", info.getProperty("password"));
/*     */ 
/*     */     
/* 524 */     passwordProp.required = true;
/* 525 */     passwordProp.description = Messages.getString("NonRegisteringDriver.16");
/*     */ 
/*     */     
/* 528 */     DriverPropertyInfo[] dpi = ConnectionPropertiesImpl.exposeAsDriverPropertyInfo(info, 5);
/*     */ 
/*     */     
/* 531 */     dpi[0] = hostProp;
/* 532 */     dpi[1] = portProp;
/* 533 */     dpi[2] = dbProp;
/* 534 */     dpi[3] = userProp;
/* 535 */     dpi[4] = passwordProp;
/*     */     
/* 537 */     return dpi;
/*     */   }
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
/* 554 */   public String host(Properties props) { return props.getProperty("HOST", "localhost"); }
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
/* 570 */   public boolean jdbcCompliant() { return false; }
/*     */ 
/*     */ 
/*     */   
/*     */   public Properties parseURL(String url, Properties defaults) throws SQLException {
/* 575 */     Properties urlProps = (defaults != null) ? new Properties(defaults) : new Properties();
/*     */ 
/*     */     
/* 578 */     if (url == null) {
/* 579 */       return null;
/*     */     }
/*     */     
/* 582 */     if (!StringUtils.startsWithIgnoreCase(url, "jdbc:mysql://") && !StringUtils.startsWithIgnoreCase(url, "jdbc:mysql:mxj://") && !StringUtils.startsWithIgnoreCase(url, "jdbc:mysql:loadbalance://") && !StringUtils.startsWithIgnoreCase(url, "jdbc:mysql:replication://"))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 589 */       return null;
/*     */     }
/*     */     
/* 592 */     int beginningOfSlashes = url.indexOf("//");
/*     */     
/* 594 */     if (StringUtils.startsWithIgnoreCase(url, "jdbc:mysql:mxj://"))
/*     */     {
/* 596 */       urlProps.setProperty("socketFactory", "com.mysql.management.driverlaunched.ServerLauncherSocketFactory");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 605 */     int index = url.indexOf("?");
/*     */     
/* 607 */     if (index != -1) {
/* 608 */       String paramString = url.substring(index + 1, url.length());
/* 609 */       url = url.substring(0, index);
/*     */       
/* 611 */       StringTokenizer queryParams = new StringTokenizer(paramString, "&");
/*     */       
/* 613 */       while (queryParams.hasMoreTokens()) {
/* 614 */         String parameterValuePair = queryParams.nextToken();
/*     */         
/* 616 */         int indexOfEquals = StringUtils.indexOfIgnoreCase(0, parameterValuePair, "=");
/*     */ 
/*     */         
/* 619 */         String parameter = null;
/* 620 */         String value = null;
/*     */         
/* 622 */         if (indexOfEquals != -1) {
/* 623 */           parameter = parameterValuePair.substring(0, indexOfEquals);
/*     */           
/* 625 */           if (indexOfEquals + 1 < parameterValuePair.length()) {
/* 626 */             value = parameterValuePair.substring(indexOfEquals + 1);
/*     */           }
/*     */         } 
/*     */         
/* 630 */         if (value != null && value.length() > 0 && parameter != null && parameter.length() > 0) {
/*     */           
/*     */           try {
/* 633 */             urlProps.put(parameter, URLDecoder.decode(value, "UTF-8"));
/*     */           }
/* 635 */           catch (UnsupportedEncodingException badEncoding) {
/*     */             
/* 637 */             urlProps.put(parameter, URLDecoder.decode(value));
/* 638 */           } catch (NoSuchMethodError nsme) {
/*     */             
/* 640 */             urlProps.put(parameter, URLDecoder.decode(value));
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 646 */     url = url.substring(beginningOfSlashes + 2);
/*     */     
/* 648 */     String hostStuff = null;
/*     */     
/* 650 */     int slashIndex = StringUtils.indexOfIgnoreCaseRespectMarker(0, url, "/", "\"'", "\"'", true);
/*     */     
/* 652 */     if (slashIndex != -1) {
/* 653 */       hostStuff = url.substring(0, slashIndex);
/*     */       
/* 655 */       if (slashIndex + 1 < url.length()) {
/* 656 */         urlProps.put("DBNAME", url.substring(slashIndex + 1, url.length()));
/*     */       }
/*     */     } else {
/*     */       
/* 660 */       hostStuff = url;
/*     */     } 
/*     */     
/* 663 */     int numHosts = 0;
/*     */     
/* 665 */     if (hostStuff != null && hostStuff.trim().length() > 0) {
/* 666 */       List<String> hosts = StringUtils.split(hostStuff, ",", "\"'", "\"'", false);
/*     */ 
/*     */       
/* 669 */       for (String hostAndPort : hosts) {
/* 670 */         numHosts++;
/*     */         
/* 672 */         String[] hostPortPair = parseHostPortPair(hostAndPort);
/*     */         
/* 674 */         if (hostPortPair[false] != null && hostPortPair[0].trim().length() > 0) {
/* 675 */           urlProps.setProperty("HOST." + numHosts, hostPortPair[0]);
/*     */         } else {
/* 677 */           urlProps.setProperty("HOST." + numHosts, "localhost");
/*     */         } 
/*     */         
/* 680 */         if (hostPortPair[true] != null) {
/* 681 */           urlProps.setProperty("PORT." + numHosts, hostPortPair[1]); continue;
/*     */         } 
/* 683 */         urlProps.setProperty("PORT." + numHosts, "3306");
/*     */       } 
/*     */     } else {
/*     */       
/* 687 */       numHosts = 1;
/* 688 */       urlProps.setProperty("HOST.1", "localhost");
/* 689 */       urlProps.setProperty("PORT.1", "3306");
/*     */     } 
/*     */     
/* 692 */     urlProps.setProperty("NUM_HOSTS", String.valueOf(numHosts));
/* 693 */     urlProps.setProperty("HOST", urlProps.getProperty("HOST.1"));
/* 694 */     urlProps.setProperty("PORT", urlProps.getProperty("PORT.1"));
/*     */     
/* 696 */     String propertiesTransformClassName = urlProps.getProperty("propertiesTransform");
/*     */ 
/*     */     
/* 699 */     if (propertiesTransformClassName != null) {
/*     */       try {
/* 701 */         ConnectionPropertiesTransform propTransformer = (ConnectionPropertiesTransform)Class.forName(propertiesTransformClassName).newInstance();
/*     */ 
/*     */         
/* 704 */         urlProps = propTransformer.transformProperties(urlProps);
/* 705 */       } catch (InstantiationException e) {
/* 706 */         throw SQLError.createSQLException("Unable to create properties transform instance '" + propertiesTransformClassName + "' due to underlying exception: " + e.toString(), "01S00", null);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 712 */       catch (IllegalAccessException e) {
/* 713 */         throw SQLError.createSQLException("Unable to create properties transform instance '" + propertiesTransformClassName + "' due to underlying exception: " + e.toString(), "01S00", null);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 719 */       catch (ClassNotFoundException e) {
/* 720 */         throw SQLError.createSQLException("Unable to create properties transform instance '" + propertiesTransformClassName + "' due to underlying exception: " + e.toString(), "01S00", null);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 729 */     if (Util.isColdFusion() && urlProps.getProperty("autoConfigureForColdFusion", "true").equalsIgnoreCase("true")) {
/*     */       
/* 731 */       String configs = urlProps.getProperty("useConfigs");
/*     */       
/* 733 */       StringBuffer newConfigs = new StringBuffer();
/*     */       
/* 735 */       if (configs != null) {
/* 736 */         newConfigs.append(configs);
/* 737 */         newConfigs.append(",");
/*     */       } 
/*     */       
/* 740 */       newConfigs.append("coldFusion");
/*     */       
/* 742 */       urlProps.setProperty("useConfigs", newConfigs.toString());
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 748 */     String configNames = null;
/*     */     
/* 750 */     if (defaults != null) {
/* 751 */       configNames = defaults.getProperty("useConfigs");
/*     */     }
/*     */     
/* 754 */     if (configNames == null) {
/* 755 */       configNames = urlProps.getProperty("useConfigs");
/*     */     }
/*     */     
/* 758 */     if (configNames != null) {
/* 759 */       List splitNames = StringUtils.split(configNames, ",", true);
/*     */       
/* 761 */       Properties configProps = new Properties();
/*     */       
/* 763 */       Iterator namesIter = splitNames.iterator();
/*     */       
/* 765 */       while (namesIter.hasNext()) {
/* 766 */         String configName = (String)namesIter.next();
/*     */         
/*     */         try {
/* 769 */           InputStream configAsStream = getClass().getResourceAsStream("configs/" + configName + ".properties");
/*     */ 
/*     */ 
/*     */           
/* 773 */           if (configAsStream == null) {
/* 774 */             throw SQLError.createSQLException("Can't find configuration template named '" + configName + "'", "01S00", null);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 780 */           configProps.load(configAsStream);
/* 781 */         } catch (IOException ioEx) {
/* 782 */           SQLException sqlEx = SQLError.createSQLException("Unable to load configuration template '" + configName + "' due to underlying IOException: " + ioEx, "01S00", null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 788 */           sqlEx.initCause(ioEx);
/*     */           
/* 790 */           throw sqlEx;
/*     */         } 
/*     */       } 
/*     */       
/* 794 */       Iterator propsIter = urlProps.keySet().iterator();
/*     */       
/* 796 */       while (propsIter.hasNext()) {
/* 797 */         String key = propsIter.next().toString();
/* 798 */         String property = urlProps.getProperty(key);
/* 799 */         configProps.setProperty(key, property);
/*     */       } 
/*     */       
/* 802 */       urlProps = configProps;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 807 */     if (defaults != null) {
/* 808 */       Iterator propsIter = defaults.keySet().iterator();
/*     */       
/* 810 */       while (propsIter.hasNext()) {
/* 811 */         String key = propsIter.next().toString();
/* 812 */         if (!key.equals("NUM_HOSTS")) {
/* 813 */           String property = defaults.getProperty(key);
/* 814 */           urlProps.setProperty(key, property);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 819 */     return urlProps;
/*     */   }
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
/* 831 */   public int port(Properties props) { return Integer.parseInt(props.getProperty("PORT", "3306")); }
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
/* 845 */   public String property(String name, Properties props) { return props.getProperty(name); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Properties expandHostKeyValues(String host) {
/* 855 */     Properties hostProps = new Properties();
/*     */     
/* 857 */     if (isHostPropertiesList(host)) {
/* 858 */       host = host.substring("address=".length() + 1);
/* 859 */       List<String> hostPropsList = StringUtils.split(host, ")", "'\"", "'\"", true);
/*     */       
/* 861 */       for (String propDef : hostPropsList) {
/* 862 */         if (propDef.startsWith("(")) {
/* 863 */           propDef = propDef.substring(1);
/*     */         }
/*     */         
/* 866 */         List<String> kvp = StringUtils.split(propDef, "=", "'\"", "'\"", true);
/*     */         
/* 868 */         String key = (String)kvp.get(0);
/* 869 */         String value = (kvp.size() > 1) ? (String)kvp.get(1) : null;
/*     */         
/* 871 */         if (value != null && ((value.startsWith("\"") && value.endsWith("\"")) || (value.startsWith("'") && value.endsWith("'")))) {
/* 872 */           value = value.substring(1, value.length() - 1);
/*     */         }
/*     */         
/* 875 */         if (value != null) {
/* 876 */           if ("HOST".equalsIgnoreCase(key) || "DBNAME".equalsIgnoreCase(key) || "PORT".equalsIgnoreCase(key) || "PROTOCOL".equalsIgnoreCase(key) || "PATH".equalsIgnoreCase(key)) {
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 881 */             key = key.toUpperCase(Locale.ENGLISH);
/* 882 */           } else if ("user".equalsIgnoreCase(key) || "password".equalsIgnoreCase(key)) {
/*     */             
/* 884 */             key = key.toLowerCase(Locale.ENGLISH);
/*     */           } 
/*     */           
/* 887 */           hostProps.setProperty(key, value);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 892 */     return hostProps;
/*     */   }
/*     */ 
/*     */   
/* 896 */   public static boolean isHostPropertiesList(String host) throws SQLException { return (host != null && StringUtils.startsWithIgnoreCase(host, "address=")); }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\NonRegisteringDriver.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */