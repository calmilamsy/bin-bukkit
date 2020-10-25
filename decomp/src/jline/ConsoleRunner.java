/*    */ package jline;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import java.util.StringTokenizer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ConsoleRunner
/*    */ {
/*    */   public static final String property = "jline.history";
/*    */   static Class array$Ljava$lang$String;
/*    */   
/*    */   public static void main(String[] args) throws Exception {
/* 23 */     String historyFileName = null;
/*    */     
/* 25 */     List argList = new ArrayList(Arrays.asList(args));
/*    */     
/* 27 */     if (argList.size() == 0) {
/* 28 */       usage();
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 33 */     historyFileName = System.getProperty("jline.history", null);
/*    */ 
/*    */     
/* 36 */     String mainClass = (String)argList.remove(0);
/*    */ 
/*    */     
/* 39 */     ConsoleReader reader = new ConsoleReader();
/*    */     
/* 41 */     if (historyFileName != null) {
/* 42 */       reader.setHistory(new History(new File(System.getProperty("user.home"), ".jline-" + mainClass + "." + historyFileName + ".history")));
/*    */     
/*    */     }
/*    */     else {
/*    */       
/* 47 */       reader.setHistory(new History(new File(System.getProperty("user.home"), ".jline-" + mainClass + ".history")));
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 52 */     String completors = System.getProperty(ConsoleRunner.class.getName() + ".completors", "");
/*    */     
/* 54 */     List completorList = new ArrayList();
/*    */     
/* 56 */     StringTokenizer tok = new StringTokenizer(completors, ",");
/* 57 */     while (tok.hasMoreTokens()) {
/* 58 */       completorList.add((Completor)Class.forName(tok.nextToken()).newInstance());
/*    */     }
/*    */ 
/*    */     
/* 62 */     if (completorList.size() > 0) {
/* 63 */       reader.addCompletor(new ArgumentCompletor(completorList));
/*    */     }
/*    */     
/* 66 */     ConsoleReaderInputStream.setIn(reader);
/*    */     
/*    */     try {
/* 69 */       Class.forName(mainClass).getMethod("main", new Class[] { (array$Ljava$lang$String == null) ? (array$Ljava$lang$String = class$("[Ljava.lang.String;")) : array$Ljava$lang$String }).invoke(null, new Object[] { argList.toArray(new String[0]) });
/*    */     
/*    */     }
/*    */     finally {
/*    */       
/* 74 */       ConsoleReaderInputStream.restoreIn();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/* 79 */   private static void usage() { System.out.println("Usage: \n   java [-Djline.history='name'] " + ConsoleRunner.class.getName() + " <target class name> [args]" + "\n\nThe -Djline.history option will avoid history" + "\nmangling when running ConsoleRunner on the same application." + "\n\nargs will be passed directly to the target class name."); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\jline\ConsoleRunner.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.0.4
 */