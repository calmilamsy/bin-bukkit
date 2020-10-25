/*    */ package org.yaml.snakeyaml.scanner;
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
/*    */ public class ScannerException
/*    */   extends MarkedYAMLException
/*    */ {
/*    */   private static final long serialVersionUID = 4782293188600445954L;
/*    */   
/* 48 */   public ScannerException(String context, Mark contextMark, String problem, Mark problemMark, String note) { super(context, contextMark, problem, problemMark, note); }
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
/* 65 */   public ScannerException(String context, Mark contextMark, String problem, Mark problemMark) { this(context, contextMark, problem, problemMark, null); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\scanner\ScannerException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */