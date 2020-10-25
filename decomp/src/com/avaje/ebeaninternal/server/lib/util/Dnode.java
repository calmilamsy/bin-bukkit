/*     */ package com.avaje.ebeaninternal.server.lib.util;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Dnode
/*     */ {
/*  39 */   int level = 0;
/*     */   
/*     */   String nodeName;
/*     */   
/*     */   String nodeContent;
/*     */   
/*     */   ArrayList<Dnode> children;
/*     */   
/*  47 */   LinkedHashMap<String, String> attrList = new LinkedHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toXml() {
/*  59 */     StringBuilder sb = new StringBuilder();
/*  60 */     generate(sb);
/*  61 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StringBuilder generate(StringBuilder sb) {
/*  68 */     if (sb == null) {
/*  69 */       sb = new StringBuilder();
/*     */     }
/*  71 */     sb.append("<").append(this.nodeName);
/*  72 */     Iterator<String> it = attributeNames();
/*  73 */     while (it.hasNext()) {
/*  74 */       String attr = (String)it.next();
/*  75 */       Object attrValue = getAttribute(attr);
/*  76 */       sb.append(" ").append(attr).append("=\"");
/*  77 */       if (attrValue != null) {
/*  78 */         sb.append(attrValue);
/*     */       }
/*  80 */       sb.append("\"");
/*     */     } 
/*     */     
/*  83 */     if (this.nodeContent == null && !hasChildren()) {
/*  84 */       sb.append(" />");
/*     */     } else {
/*     */       
/*  87 */       sb.append(">");
/*  88 */       if (this.children != null && this.children.size() > 0) {
/*  89 */         for (int i = 0; i < this.children.size(); i++) {
/*  90 */           Dnode child = (Dnode)this.children.get(i);
/*  91 */           child.generate(sb);
/*     */         } 
/*     */       }
/*  94 */       if (this.nodeContent != null) {
/*  95 */         sb.append(this.nodeContent);
/*     */       }
/*  97 */       sb.append("</").append(this.nodeName).append(">");
/*     */     } 
/*  99 */     return sb;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 106 */   public String getNodeName() { return this.nodeName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 113 */   public void setNodeName(String nodeName) { this.nodeName = nodeName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 120 */   public String getNodeContent() { return this.nodeContent; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 127 */   public void setNodeContent(String nodeContent) { this.nodeContent = nodeContent; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 134 */   public boolean hasChildren() { return (getChildrenCount() > 0); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getChildrenCount() {
/* 141 */     if (this.children == null) {
/* 142 */       return 0;
/*     */     }
/* 144 */     return this.children.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean remove(Dnode node) {
/* 151 */     if (this.children == null) {
/* 152 */       return false;
/*     */     }
/* 154 */     if (this.children.remove(node)) {
/* 155 */       return true;
/*     */     }
/* 157 */     Iterator<Dnode> it = this.children.iterator();
/* 158 */     while (it.hasNext()) {
/* 159 */       Dnode child = (Dnode)it.next();
/* 160 */       if (child.remove(node)) {
/* 161 */         return true;
/*     */       }
/*     */     } 
/* 164 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Dnode> children() {
/* 171 */     if (this.children == null) {
/* 172 */       return null;
/*     */     }
/* 174 */     return this.children;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addChild(Dnode child) {
/* 181 */     if (this.children == null) {
/* 182 */       this.children = new ArrayList();
/*     */     }
/* 184 */     this.children.add(child);
/* 185 */     child.setLevel(this.level + 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 192 */   public int getLevel() { return this.level; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLevel(int level) {
/* 199 */     this.level = level;
/* 200 */     if (this.children != null) {
/* 201 */       for (int i = 0; i < this.children.size(); i++) {
/* 202 */         Dnode child = (Dnode)this.children.get(i);
/* 203 */         child.setLevel(level + 1);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 213 */   public Dnode find(String nodeName) { return find(nodeName, null, null); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 222 */   public Dnode find(String nodeName, String attrName, Object value) { return find(nodeName, attrName, value, -1); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dnode find(String nodeName, String attrName, Object value, int maxLevel) {
/* 234 */     ArrayList<Dnode> list = new ArrayList<Dnode>();
/* 235 */     findByNode(list, nodeName, true, attrName, value, maxLevel);
/* 236 */     if (list.size() >= 1) {
/* 237 */       return (Dnode)list.get(0);
/*     */     }
/* 239 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Dnode> findAll(String nodeName, int maxLevel) {
/* 247 */     int level = -1;
/* 248 */     if (maxLevel > 0) {
/* 249 */       level = this.level + maxLevel;
/*     */     }
/* 251 */     return findAll(nodeName, null, null, level);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Dnode> findAll(String nodeName, String attrName, Object value, int maxLevel) {
/* 259 */     if (nodeName == null && attrName == null) {
/* 260 */       throw new RuntimeException("You can not have both nodeName and attrName null");
/*     */     }
/* 262 */     ArrayList<Dnode> list = new ArrayList<Dnode>();
/* 263 */     findByNode(list, nodeName, false, attrName, value, maxLevel);
/* 264 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void findByNode(List<Dnode> list, String node, boolean findOne, String attrName, Object value, int maxLevel) {
/* 272 */     if (findOne && list.size() == 1) {
/*     */       return;
/*     */     }
/* 275 */     if ((node == null || node.equals(this.nodeName)) && (
/* 276 */       attrName == null || value.equals(getAttribute(attrName)))) {
/* 277 */       list.add(this);
/* 278 */       if (findOne) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */     
/* 283 */     if (maxLevel <= 0 || this.level < maxLevel)
/*     */     {
/*     */       
/* 286 */       if (this.children != null)
/*     */       {
/* 288 */         for (int i = 0; i < this.children.size(); i++) {
/* 289 */           Dnode child = (Dnode)this.children.get(i);
/* 290 */           child.findByNode(list, node, findOne, attrName, value, maxLevel);
/*     */         } 
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 299 */   public Iterator<String> attributeNames() { return this.attrList.keySet().iterator(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 306 */   public String getAttribute(String name) { return (String)this.attrList.get(name); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getStringAttr(String name, String defaultValue) {
/* 316 */     Object o = this.attrList.get(name);
/* 317 */     if (o == null) {
/* 318 */       return defaultValue;
/*     */     }
/* 320 */     return o.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 328 */   public void setAttribute(String name, String value) { this.attrList.put(name, value); }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 332 */     StringBuilder sb = new StringBuilder();
/* 333 */     sb.append("[").append(getNodeName()).append(" ").append(this.attrList).append("]");
/* 334 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebeaninternal\server\li\\util\Dnode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */