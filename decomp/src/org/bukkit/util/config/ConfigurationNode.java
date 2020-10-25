/*     */ package org.bukkit.util.config;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TreeMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ConfigurationNode
/*     */ {
/*     */   protected Map<String, Object> root;
/*     */   
/*  17 */   protected ConfigurationNode(Map<String, Object> root) { this.root = root; }
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
/*  28 */   public Map<String, Object> getAll() { return recursiveBuilder(this.root); }
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
/*     */   protected Map<String, Object> recursiveBuilder(Map<String, Object> node) {
/*  40 */     Map<String, Object> map = new TreeMap<String, Object>();
/*     */     
/*  42 */     Set<String> keys = node.keySet();
/*  43 */     for (String k : keys) {
/*  44 */       Object tmp = node.get(k);
/*  45 */       if (tmp instanceof Map) {
/*  46 */         Map<String, Object> rec = recursiveBuilder((Map)tmp);
/*     */         
/*  48 */         Set<String> subkeys = rec.keySet();
/*  49 */         for (String sk : subkeys) {
/*  50 */           map.put(k + "." + sk, rec.get(sk));
/*     */         }
/*     */         continue;
/*     */       } 
/*  54 */       map.put(k, tmp);
/*     */     } 
/*     */ 
/*     */     
/*  58 */     return map;
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
/*     */   public Object getProperty(String path) {
/*  73 */     if (!path.contains(".")) {
/*  74 */       Object val = this.root.get(path);
/*     */       
/*  76 */       if (val == null) {
/*  77 */         return null;
/*     */       }
/*  79 */       return val;
/*     */     } 
/*     */     
/*  82 */     String[] parts = path.split("\\.");
/*  83 */     Map<String, Object> node = this.root;
/*     */     
/*  85 */     for (int i = 0; i < parts.length; i++) {
/*  86 */       Object o = node.get(parts[i]);
/*     */       
/*  88 */       if (o == null) {
/*  89 */         return null;
/*     */       }
/*     */       
/*  92 */       if (i == parts.length - 1) {
/*  93 */         return o;
/*     */       }
/*     */       
/*     */       try {
/*  97 */         node = (Map)o;
/*  98 */       } catch (ClassCastException e) {
/*  99 */         return null;
/*     */       } 
/*     */     } 
/*     */     
/* 103 */     return null;
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
/*     */   public void setProperty(String path, Object value) {
/* 115 */     if (!path.contains(".")) {
/* 116 */       this.root.put(path, value);
/*     */       
/*     */       return;
/*     */     } 
/* 120 */     String[] parts = path.split("\\.");
/* 121 */     Map<String, Object> node = this.root;
/*     */     
/* 123 */     for (int i = 0; i < parts.length; i++) {
/* 124 */       Object o = node.get(parts[i]);
/*     */ 
/*     */       
/* 127 */       if (i == parts.length - 1) {
/* 128 */         node.put(parts[i], value);
/*     */         
/*     */         return;
/*     */       } 
/* 132 */       if (o == null || !(o instanceof Map)) {
/*     */         
/* 134 */         o = new HashMap();
/* 135 */         node.put(parts[i], o);
/*     */       } 
/*     */       
/* 138 */       node = (Map)o;
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
/*     */   public String getString(String path) {
/* 152 */     Object o = getProperty(path);
/*     */     
/* 154 */     if (o == null) {
/* 155 */       return null;
/*     */     }
/* 157 */     return o.toString();
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
/*     */   public String getString(String path, String def) {
/* 170 */     String o = getString(path);
/*     */     
/* 172 */     if (o == null) {
/* 173 */       setProperty(path, def);
/* 174 */       return def;
/*     */     } 
/* 176 */     return o;
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
/*     */   public int getInt(String path, int def) {
/* 190 */     Integer o = castInt(getProperty(path));
/*     */     
/* 192 */     if (o == null) {
/* 193 */       setProperty(path, Integer.valueOf(def));
/* 194 */       return def;
/*     */     } 
/* 196 */     return o.intValue();
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
/*     */   public double getDouble(String path, double def) {
/* 211 */     Double o = castDouble(getProperty(path));
/*     */     
/* 213 */     if (o == null) {
/* 214 */       setProperty(path, Double.valueOf(def));
/* 215 */       return def;
/*     */     } 
/* 217 */     return o.doubleValue();
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
/*     */   public boolean getBoolean(String path, boolean def) {
/* 231 */     Boolean o = castBoolean(getProperty(path));
/*     */     
/* 233 */     if (o == null) {
/* 234 */       setProperty(path, Boolean.valueOf(def));
/* 235 */       return def;
/*     */     } 
/* 237 */     return o.booleanValue();
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
/*     */   public List<String> getKeys(String path) {
/* 250 */     if (path == null) {
/* 251 */       return new ArrayList(this.root.keySet());
/*     */     }
/* 253 */     Object o = getProperty(path);
/*     */     
/* 255 */     if (o == null)
/* 256 */       return null; 
/* 257 */     if (o instanceof Map) {
/* 258 */       return new ArrayList(((Map)o).keySet());
/*     */     }
/* 260 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 270 */   public List<String> getKeys() { return new ArrayList(this.root.keySet()); }
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
/*     */   public List<Object> getList(String path) {
/* 282 */     Object o = getProperty(path);
/*     */     
/* 284 */     if (o == null)
/* 285 */       return null; 
/* 286 */     if (o instanceof List) {
/* 287 */       return (List)o;
/*     */     }
/* 289 */     return null;
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
/*     */   public List<String> getStringList(String path, List<String> def) {
/* 306 */     List<Object> raw = getList(path);
/*     */     
/* 308 */     if (raw == null) {
/* 309 */       return (def != null) ? def : new ArrayList();
/*     */     }
/*     */     
/* 312 */     List<String> list = new ArrayList<String>();
/*     */     
/* 314 */     for (Object o : raw) {
/* 315 */       if (o == null) {
/*     */         continue;
/*     */       }
/*     */       
/* 319 */       list.add(o.toString());
/*     */     } 
/*     */     
/* 322 */     return list;
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
/*     */   public List<Integer> getIntList(String path, List<Integer> def) {
/* 337 */     List<Object> raw = getList(path);
/*     */     
/* 339 */     if (raw == null) {
/* 340 */       return (def != null) ? def : new ArrayList();
/*     */     }
/*     */     
/* 343 */     List<Integer> list = new ArrayList<Integer>();
/*     */     
/* 345 */     for (Object o : raw) {
/* 346 */       Integer i = castInt(o);
/*     */       
/* 348 */       if (i != null) {
/* 349 */         list.add(i);
/*     */       }
/*     */     } 
/*     */     
/* 353 */     return list;
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
/*     */   public List<Double> getDoubleList(String path, List<Double> def) {
/* 368 */     List<Object> raw = getList(path);
/*     */     
/* 370 */     if (raw == null) {
/* 371 */       return (def != null) ? def : new ArrayList();
/*     */     }
/*     */     
/* 374 */     List<Double> list = new ArrayList<Double>();
/*     */     
/* 376 */     for (Object o : raw) {
/* 377 */       Double i = castDouble(o);
/*     */       
/* 379 */       if (i != null) {
/* 380 */         list.add(i);
/*     */       }
/*     */     } 
/*     */     
/* 384 */     return list;
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
/*     */   public List<Boolean> getBooleanList(String path, List<Boolean> def) {
/* 399 */     List<Object> raw = getList(path);
/*     */     
/* 401 */     if (raw == null) {
/* 402 */       return (def != null) ? def : new ArrayList();
/*     */     }
/*     */     
/* 405 */     List<Boolean> list = new ArrayList<Boolean>();
/*     */     
/* 407 */     for (Object o : raw) {
/* 408 */       Boolean tetsu = castBoolean(o);
/*     */       
/* 410 */       if (tetsu != null) {
/* 411 */         list.add(tetsu);
/*     */       }
/*     */     } 
/*     */     
/* 415 */     return list;
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
/*     */   public List<ConfigurationNode> getNodeList(String path, List<ConfigurationNode> def) {
/* 431 */     List<Object> raw = getList(path);
/*     */     
/* 433 */     if (raw == null) {
/* 434 */       return (def != null) ? def : new ArrayList();
/*     */     }
/*     */     
/* 437 */     List<ConfigurationNode> list = new ArrayList<ConfigurationNode>();
/*     */     
/* 439 */     for (Object o : raw) {
/* 440 */       if (o instanceof Map) {
/* 441 */         list.add(new ConfigurationNode((Map)o));
/*     */       }
/*     */     } 
/*     */     
/* 445 */     return list;
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
/*     */   public ConfigurationNode getNode(String path) {
/* 458 */     Object raw = getProperty(path);
/*     */     
/* 460 */     if (raw instanceof Map) {
/* 461 */       return new ConfigurationNode((Map)raw);
/*     */     }
/*     */     
/* 464 */     return null;
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
/*     */   public Map<String, ConfigurationNode> getNodes(String path) {
/* 476 */     Object o = getProperty(path);
/*     */     
/* 478 */     if (o == null)
/* 479 */       return null; 
/* 480 */     if (o instanceof Map) {
/* 481 */       Map<String, ConfigurationNode> nodes = new HashMap<String, ConfigurationNode>();
/*     */       
/* 483 */       for (Map.Entry<String, Object> entry : ((Map)o).entrySet()) {
/* 484 */         if (entry.getValue() instanceof Map) {
/* 485 */           nodes.put(entry.getKey(), new ConfigurationNode((Map)entry.getValue()));
/*     */         }
/*     */       } 
/*     */       
/* 489 */       return nodes;
/*     */     } 
/* 491 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Integer castInt(Object o) {
/* 502 */     if (o == null)
/* 503 */       return null; 
/* 504 */     if (o instanceof Byte)
/* 505 */       return Integer.valueOf(((Byte)o).byteValue()); 
/* 506 */     if (o instanceof Integer)
/* 507 */       return (Integer)o; 
/* 508 */     if (o instanceof Double)
/* 509 */       return Integer.valueOf((int)((Double)o).doubleValue()); 
/* 510 */     if (o instanceof Float)
/* 511 */       return Integer.valueOf((int)((Float)o).floatValue()); 
/* 512 */     if (o instanceof Long) {
/* 513 */       return Integer.valueOf((int)((Long)o).longValue());
/*     */     }
/* 515 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Double castDouble(Object o) {
/* 526 */     if (o == null)
/* 527 */       return null; 
/* 528 */     if (o instanceof Float)
/* 529 */       return Double.valueOf(((Float)o).floatValue()); 
/* 530 */     if (o instanceof Double)
/* 531 */       return (Double)o; 
/* 532 */     if (o instanceof Byte)
/* 533 */       return Double.valueOf(((Byte)o).byteValue()); 
/* 534 */     if (o instanceof Integer)
/* 535 */       return Double.valueOf(((Integer)o).intValue()); 
/* 536 */     if (o instanceof Long) {
/* 537 */       return Double.valueOf(((Long)o).longValue());
/*     */     }
/* 539 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Boolean castBoolean(Object o) {
/* 550 */     if (o == null)
/* 551 */       return null; 
/* 552 */     if (o instanceof Boolean) {
/* 553 */       return (Boolean)o;
/*     */     }
/* 555 */     return null;
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
/*     */   public void removeProperty(String path) {
/* 567 */     if (!path.contains(".")) {
/* 568 */       this.root.remove(path);
/*     */       
/*     */       return;
/*     */     } 
/* 572 */     String[] parts = path.split("\\.");
/* 573 */     Map<String, Object> node = this.root;
/*     */     
/* 575 */     for (int i = 0; i < parts.length; i++) {
/* 576 */       Object o = node.get(parts[i]);
/*     */ 
/*     */       
/* 579 */       if (i == parts.length - 1) {
/* 580 */         node.remove(parts[i]);
/*     */         
/*     */         return;
/*     */       } 
/* 584 */       node = (Map)o;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukki\\util\config\ConfigurationNode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */