/*    */ package org.yaml.snakeyaml.constructor;
/*    */ 
/*    */ import org.yaml.snakeyaml.error.Mark;
/*    */ import org.yaml.snakeyaml.error.MarkedYAMLException;
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
/*    */ public class ConstructorException
/*    */   extends MarkedYAMLException
/*    */ {
/*    */   private static final long serialVersionUID = -8816339931365239910L;
/*    */   
/* 30 */   protected ConstructorException(String context, Mark contextMark, String problem, Mark problemMark, Throwable cause) { super(context, contextMark, problem, problemMark, cause); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 35 */   protected ConstructorException(String context, Mark contextMark, String problem, Mark problemMark) { this(context, contextMark, problem, problemMark, null); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\constructor\ConstructorException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */