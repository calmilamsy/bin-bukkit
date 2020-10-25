package com.avaje.ebean.enhance.asm;

public interface ClassVisitor {
  void visit(int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3, String[] paramArrayOfString);
  
  void visitSource(String paramString1, String paramString2);
  
  void visitOuterClass(String paramString1, String paramString2, String paramString3);
  
  AnnotationVisitor visitAnnotation(String paramString, boolean paramBoolean);
  
  void visitAttribute(Attribute paramAttribute);
  
  void visitInnerClass(String paramString1, String paramString2, String paramString3, int paramInt);
  
  FieldVisitor visitField(int paramInt, String paramString1, String paramString2, String paramString3, Object paramObject);
  
  MethodVisitor visitMethod(int paramInt, String paramString1, String paramString2, String paramString3, String[] paramArrayOfString);
  
  void visitEnd();
}


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\enhance\asm\ClassVisitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */