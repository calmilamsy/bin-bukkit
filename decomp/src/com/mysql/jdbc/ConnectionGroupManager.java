/*     */ package com.mysql.jdbc;
/*     */ 
/*     */ import com.mysql.jdbc.jmx.LoadBalanceConnectionGroupManager;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ public class ConnectionGroupManager
/*     */ {
/*  38 */   private static HashMap GROUP_MAP = new HashMap();
/*     */   
/*  40 */   private static LoadBalanceConnectionGroupManager mbean = new LoadBalanceConnectionGroupManager();
/*     */   
/*     */   private static boolean hasRegisteredJmx = false;
/*     */ 
/*     */   
/*     */   public static ConnectionGroup getConnectionGroupInstance(String groupName) {
/*  46 */     if (GROUP_MAP.containsKey(groupName)) {
/*  47 */       return (ConnectionGroup)GROUP_MAP.get(groupName);
/*     */     }
/*  49 */     ConnectionGroup group = new ConnectionGroup(groupName);
/*  50 */     GROUP_MAP.put(groupName, group);
/*  51 */     return group;
/*     */   }
/*     */   
/*     */   public static void registerJmx() {
/*  55 */     if (hasRegisteredJmx) {
/*     */       return;
/*     */     }
/*     */     
/*  59 */     mbean.registerJmx();
/*  60 */     hasRegisteredJmx = true;
/*     */   }
/*     */ 
/*     */   
/*  64 */   public static ConnectionGroup getConnectionGroup(String groupName) { return (ConnectionGroup)GROUP_MAP.get(groupName); }
/*     */ 
/*     */   
/*     */   private static Collection getGroupsMatching(String group) {
/*  68 */     if (group == null || group.equals("")) {
/*  69 */       Set s = new HashSet();
/*     */       
/*  71 */       s.addAll(GROUP_MAP.values());
/*  72 */       return s;
/*     */     } 
/*  74 */     Set s = new HashSet();
/*  75 */     Object o = GROUP_MAP.get(group);
/*  76 */     if (o != null) {
/*  77 */       s.add(o);
/*     */     }
/*  79 */     return s;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addHost(String group, String host, boolean forExisting) {
/*  84 */     Collection s = getGroupsMatching(group);
/*  85 */     for (Iterator i = s.iterator(); i.hasNext();) {
/*  86 */       ((ConnectionGroup)i.next()).addHost(host, forExisting);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getActiveHostCount(String group) {
/*  92 */     Set active = new HashSet();
/*  93 */     Collection s = getGroupsMatching(group);
/*  94 */     for (Iterator i = s.iterator(); i.hasNext();) {
/*  95 */       active.addAll(((ConnectionGroup)i.next()).getInitialHosts());
/*     */     }
/*  97 */     return active.size();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static long getActiveLogicalConnectionCount(String group) {
/* 103 */     int count = 0;
/* 104 */     Collection s = getGroupsMatching(group);
/* 105 */     for (Iterator i = s.iterator(); i.hasNext();) {
/* 106 */       count = (int)(count + ((ConnectionGroup)i.next()).getActiveLogicalConnectionCount());
/*     */     }
/* 108 */     return count;
/*     */   }
/*     */   
/*     */   public static long getActivePhysicalConnectionCount(String group) {
/* 112 */     int count = 0;
/* 113 */     Collection s = getGroupsMatching(group);
/* 114 */     for (Iterator i = s.iterator(); i.hasNext();) {
/* 115 */       count = (int)(count + ((ConnectionGroup)i.next()).getActivePhysicalConnectionCount());
/*     */     }
/* 117 */     return count;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getTotalHostCount(String group) {
/* 122 */     Collection s = getGroupsMatching(group);
/* 123 */     Set hosts = new HashSet();
/* 124 */     for (Iterator i = s.iterator(); i.hasNext(); ) {
/* 125 */       ConnectionGroup cg = (ConnectionGroup)i.next();
/* 126 */       hosts.addAll(cg.getInitialHosts());
/* 127 */       hosts.addAll(cg.getClosedHosts());
/*     */     } 
/* 129 */     return hosts.size();
/*     */   }
/*     */   
/*     */   public static long getTotalLogicalConnectionCount(String group) {
/* 133 */     long count = 0L;
/* 134 */     Collection s = getGroupsMatching(group);
/* 135 */     for (Iterator i = s.iterator(); i.hasNext();) {
/* 136 */       count += ((ConnectionGroup)i.next()).getTotalLogicalConnectionCount();
/*     */     }
/* 138 */     return count;
/*     */   }
/*     */   
/*     */   public static long getTotalPhysicalConnectionCount(String group) {
/* 142 */     long count = 0L;
/* 143 */     Collection s = getGroupsMatching(group);
/* 144 */     for (Iterator i = s.iterator(); i.hasNext();) {
/* 145 */       count += ((ConnectionGroup)i.next()).getTotalPhysicalConnectionCount();
/*     */     }
/* 147 */     return count;
/*     */   }
/*     */   
/*     */   public static long getTotalTransactionCount(String group) {
/* 151 */     long count = 0L;
/* 152 */     Collection s = getGroupsMatching(group);
/* 153 */     for (Iterator i = s.iterator(); i.hasNext();) {
/* 154 */       count += ((ConnectionGroup)i.next()).getTotalTransactionCount();
/*     */     }
/* 156 */     return count;
/*     */   }
/*     */ 
/*     */   
/* 160 */   public static void removeHost(String group, String host) throws SQLException { removeHost(group, host, false); }
/*     */ 
/*     */   
/*     */   public static void removeHost(String group, String host, boolean removeExisting) {
/* 164 */     Collection s = getGroupsMatching(group);
/* 165 */     for (Iterator i = s.iterator(); i.hasNext();) {
/* 166 */       ((ConnectionGroup)i.next()).removeHost(host, removeExisting);
/*     */     }
/*     */   }
/*     */   
/*     */   public static String getActiveHostLists(String group) {
/* 171 */     Collection s = getGroupsMatching(group);
/* 172 */     Map hosts = new HashMap();
/* 173 */     for (i = s.iterator(); i.hasNext(); ) {
/*     */       
/* 175 */       Collection l = ((ConnectionGroup)i.next()).getInitialHosts();
/* 176 */       for (Iterator j = l.iterator(); j.hasNext(); ) {
/* 177 */         String host = j.next().toString();
/* 178 */         Object o = hosts.get(host);
/* 179 */         if (o == null) {
/* 180 */           o = new Integer(true);
/*     */         } else {
/* 182 */           o = new Integer(((Integer)o).intValue() + 1);
/*     */         } 
/* 184 */         hosts.put(host, o);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 189 */     StringBuffer sb = new StringBuffer();
/* 190 */     String sep = "";
/* 191 */     for (Iterator i = hosts.keySet().iterator(); i.hasNext(); ) {
/* 192 */       String host = i.next().toString();
/*     */       
/* 194 */       sb.append(sep);
/* 195 */       sb.append(host);
/* 196 */       sb.append('(');
/* 197 */       sb.append(hosts.get(host));
/* 198 */       sb.append(')');
/* 199 */       sep = ",";
/*     */     } 
/* 201 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public static String getRegisteredConnectionGroups() {
/* 205 */     s = getGroupsMatching(null);
/* 206 */     StringBuffer sb = new StringBuffer();
/* 207 */     String sep = "";
/* 208 */     for (Iterator i = s.iterator(); i.hasNext(); ) {
/* 209 */       String group = ((ConnectionGroup)i.next()).getGroupName();
/* 210 */       sb.append(sep);
/* 211 */       sb.append(group);
/* 212 */       sep = ",";
/*     */     } 
/* 214 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\mysql\jdbc\ConnectionGroupManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */