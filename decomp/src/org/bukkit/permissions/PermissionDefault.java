/*    */ package org.bukkit.permissions;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ public static enum PermissionDefault
/*    */ {
/* 10 */   TRUE(new String[] { "true" }),
/* 11 */   FALSE(new String[] { "false" }),
/* 12 */   OP(new String[] { "op", "isop", "operator", "isoperator", "admin", "isadmin" }),
/* 13 */   NOT_OP(new String[] { "!op", "notop", "!operator", "notoperator", "!admin", "notadmin" });
/*    */   
/*    */   static  {
/* 16 */     lookup = new HashMap();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 59 */     for (PermissionDefault value : values()) {
/* 60 */       for (String name : value.names)
/* 61 */         lookup.put(name, value); 
/*    */     } 
/*    */   }
/*    */   
/*    */   private final String[] names;
/*    */   private static final Map<String, PermissionDefault> lookup;
/*    */   
/*    */   PermissionDefault(String... names) { this.names = names; }
/*    */   
/*    */   public boolean getValue(boolean op) {
/*    */     switch (this) {
/*    */       case TRUE:
/*    */         return true;
/*    */       case FALSE:
/*    */         return false;
/*    */       case OP:
/*    */         return op;
/*    */       case NOT_OP:
/*    */         return !op;
/*    */     } 
/*    */     return false;
/*    */   }
/*    */   
/*    */   public static PermissionDefault getByName(String name) { return (PermissionDefault)lookup.get(name.toLowerCase().replaceAll("[^a-z!]", "")); }
/*    */   
/*    */   public String toString() { return this.names[0]; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\permissions\PermissionDefault.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */