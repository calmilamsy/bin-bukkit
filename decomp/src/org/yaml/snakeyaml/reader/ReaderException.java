/*    */ package org.yaml.snakeyaml.reader;
/*    */ 
/*    */ import org.yaml.snakeyaml.error.YAMLException;
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
/*    */ public class ReaderException
/*    */   extends YAMLException
/*    */ {
/*    */   private static final long serialVersionUID = 8710781187529689083L;
/*    */   private String name;
/*    */   private char character;
/*    */   private int position;
/*    */   
/*    */   public ReaderException(String name, int position, char character, String message) {
/* 28 */     super(message);
/* 29 */     this.name = name;
/* 30 */     this.character = character;
/* 31 */     this.position = position;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 36 */   public String toString() { return "unacceptable character #" + Integer.toHexString(this.character).toUpperCase() + " " + getMessage() + "\nin \"" + this.name + "\", position " + this.position; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\reader\ReaderException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */