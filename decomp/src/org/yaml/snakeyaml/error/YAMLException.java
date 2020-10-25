/*    */ package org.yaml.snakeyaml.error;
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
/*    */ public class YAMLException
/*    */   extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = -4738336175050337570L;
/*    */   
/* 26 */   public YAMLException(String message) { super(message); }
/*    */ 
/*    */ 
/*    */   
/* 30 */   public YAMLException(Throwable cause) { super(cause); }
/*    */ 
/*    */ 
/*    */   
/* 34 */   public YAMLException(String message, Throwable cause) { super(message, cause); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\error\YAMLException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */