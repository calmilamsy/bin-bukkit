/*    */ package org.ibex.nestedvm;
/*    */ 
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.IOException;
/*    */ import org.ibex.nestedvm.util.Seekable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RuntimeCompiler
/*    */ {
/* 13 */   public static Class compile(Seekable paramSeekable) throws IOException, Compiler.Exn { return compile(paramSeekable, null); }
/* 14 */   public static Class compile(Seekable paramSeekable, String paramString) throws IOException, Compiler.Exn { return compile(paramSeekable, paramString, null); }
/*    */   public static Class compile(Seekable paramSeekable, String paramString1, String paramString2) throws IOException, Compiler.Exn {
/*    */     byte[] arrayOfByte;
/* 17 */     String str = "nestedvm.runtimecompiled";
/*    */     
/*    */     try {
/* 20 */       arrayOfByte = runCompiler(paramSeekable, str, paramString1, paramString2, null);
/* 21 */     } catch (Exn exn) {
/* 22 */       if (exn.getMessage() != null || exn.getMessage().indexOf("constant pool full") != -1) {
/* 23 */         arrayOfByte = runCompiler(paramSeekable, str, paramString1, paramString2, "lessconstants");
/*    */       } else {
/* 25 */         throw exn;
/*    */       } 
/* 27 */     }  return (new SingleClassLoader(null)).fromBytes(str, arrayOfByte);
/*    */   }
/*    */   
/*    */   private static byte[] runCompiler(Seekable paramSeekable, String paramString1, String paramString2, String paramString3, String paramString4) throws IOException, Compiler.Exn {
/* 31 */     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/*    */     
/*    */     try {
/* 34 */       ClassFileCompiler classFileCompiler = new ClassFileCompiler(paramSeekable, paramString1, byteArrayOutputStream);
/* 35 */       classFileCompiler.parseOptions("nosupportcall,maxinsnpermethod=256");
/* 36 */       classFileCompiler.setSource(paramString3);
/* 37 */       if (paramString2 != null) classFileCompiler.parseOptions(paramString2); 
/* 38 */       if (paramString4 != null) classFileCompiler.parseOptions(paramString4); 
/* 39 */       classFileCompiler.go();
/*    */     } finally {
/* 41 */       paramSeekable.seek(0);
/*    */     } 
/*    */     
/* 44 */     byteArrayOutputStream.close();
/* 45 */     return byteArrayOutputStream.toByteArray();
/*    */   }
/*    */   
/*    */   private static class SingleClassLoader extends ClassLoader {
/*    */     private SingleClassLoader() {}
/*    */     
/* 51 */     public Class loadClass(String param1String, boolean param1Boolean) throws ClassNotFoundException { return super.loadClass(param1String, param1Boolean); }
/*    */     
/* 53 */     public Class fromBytes(String param1String, byte[] param1ArrayOfByte) { return fromBytes(param1String, param1ArrayOfByte, 0, param1ArrayOfByte.length); }
/*    */     public Class fromBytes(String param1String, byte[] param1ArrayOfByte, int param1Int1, int param1Int2) {
/* 55 */       Class clazz = defineClass(param1String, param1ArrayOfByte, param1Int1, param1Int2);
/* 56 */       resolveClass(clazz);
/* 57 */       return clazz;
/*    */     }
/*    */   }
/*    */   
/*    */   public static void main(String[] paramArrayOfString) throws Exception {
/* 62 */     if (paramArrayOfString.length == 0) {
/* 63 */       System.err.println("Usage: RuntimeCompiler mipsbinary");
/* 64 */       System.exit(1);
/*    */     } 
/* 66 */     UnixRuntime unixRuntime = (UnixRuntime)compile(new Seekable.File(paramArrayOfString[0]), "unixruntime").newInstance();
/* 67 */     System.err.println("Instansiated: " + unixRuntime);
/* 68 */     System.exit(UnixRuntime.runAndExec(unixRuntime, paramArrayOfString));
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\ibex\nestedvm\RuntimeCompiler.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.0.4
 */