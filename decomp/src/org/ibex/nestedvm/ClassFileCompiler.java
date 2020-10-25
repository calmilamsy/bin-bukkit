/*      */ package org.ibex.nestedvm;
/*      */ 
/*      */ import java.io.DataInputStream;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStream;
/*      */ import java.io.PrintStream;
/*      */ import org.ibex.classgen.CGConst;
/*      */ import org.ibex.classgen.ClassFile;
/*      */ import org.ibex.classgen.MethodGen;
/*      */ import org.ibex.classgen.Type;
/*      */ import org.ibex.nestedvm.util.ELF;
/*      */ import org.ibex.nestedvm.util.Seekable;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ClassFileCompiler
/*      */   extends Compiler
/*      */   implements CGConst
/*      */ {
/*      */   private static final boolean OPTIMIZE_CP = true;
/*      */   private OutputStream os;
/*      */   private File outDir;
/*   45 */   private PrintStream warn = System.err; private final Type.Class me; private ClassFile cg; private MethodGen clinit; private MethodGen init; private static int initDataCount; private int startOfMethod; private int endOfMethod; private MethodGen.PhantomTarget returnTarget; private MethodGen.PhantomTarget defaultTarget;
/*      */   private MethodGen.PhantomTarget[] insnTargets;
/*      */   private MethodGen mg;
/*      */   private static final int UNREACHABLE = 1;
/*      */   private static final int SKIP_NEXT = 2;
/*      */   private boolean textDone;
/*      */   
/*   52 */   public ClassFileCompiler(String paramString1, String paramString2, OutputStream paramOutputStream) throws IOException { this(new Seekable.File(paramString1), paramString2, paramOutputStream); }
/*      */   public ClassFileCompiler(Seekable paramSeekable, String paramString, OutputStream paramOutputStream) throws IOException {
/*   54 */     this(paramSeekable, paramString);
/*   55 */     if (paramOutputStream == null) throw new NullPointerException(); 
/*   56 */     this.os = paramOutputStream;
/*      */   }
/*      */   public ClassFileCompiler(Seekable paramSeekable, String paramString, File paramFile) throws IOException {
/*   59 */     this(paramSeekable, paramString);
/*   60 */     if (paramFile == null) throw new NullPointerException(); 
/*   61 */     this.outDir = paramFile;
/*      */   }
/*      */   
/*   64 */   private ClassFileCompiler(Seekable paramSeekable, String paramString) throws IOException { super(paramSeekable, paramString);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  441 */     this.startOfMethod = 0;
/*  442 */     this.endOfMethod = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1749 */     this.regLocalMapping = new int[67];
/* 1750 */     this.regLocalWritten = new boolean[67];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1831 */     this.preSetRegStack = new int[8]; this.me = Type.Class.instance(this.fullClassName); }
/*      */   public void setWarnWriter(PrintStream paramPrintStream) { this.warn = paramPrintStream; }
/*      */   protected void _go() throws Compiler.Exn, IOException { try { __go(); } catch (org.ibex.classgen.ClassFile.Exn exn) { exn.printStackTrace(this.warn); throw new Compiler.Exn("Class generation exception: " + exn.toString()); }  }
/*      */   private void __go() throws Compiler.Exn, IOException { if (!this.pruneCases) throw new Compiler.Exn("-o prunecases MUST be enabled for ClassFileCompiler");  Type.Class clazz1 = Type.Class.instance(this.runtimeClass); this.cg = new ClassFile(this.me, clazz1, 49); if (this.source != null) this.cg.setSourceFile(this.source);  this.cg.addField("pc", Type.INT, 2); this.cg.addField("hi", Type.INT, 2); this.cg.addField("lo", Type.INT, 2); this.cg.addField("fcsr", Type.INT, 2); int i; for (i = 1; i < 32; ) { this.cg.addField("r" + i, Type.INT, 2); i++; }  for (i = 0; i < 32; ) { this.cg.addField("f" + i, this.singleFloat ? Type.FLOAT : Type.INT, 2); i++; }  this.clinit = this.cg.addMethod("<clinit>", Type.VOID, Type.NO_ARGS, 10); this.init = this.cg.addMethod("<init>", Type.VOID, Type.NO_ARGS, 1); this.init.add((byte)42); this.init.add((byte)18, this.pageSize); this.init.add((byte)18, this.totalPages); this.init.add((byte)-73, this.me.method("<init>", Type.VOID, new Type[] { Type.INT, Type.INT })); this.init.add((byte)-79); this.init = this.cg.addMethod("<init>", Type.VOID, new Type[] { Type.BOOLEAN }, 1); this.init.add((byte)42); this.init.add((byte)18, this.pageSize); this.init.add((byte)18, this.totalPages); this.init.add((byte)27); this.init.add((byte)-73, this.me.method("<init>", Type.VOID, new Type[] { Type.INT, Type.INT, Type.BOOLEAN })); this.init.add((byte)-79); this.init = this.cg.addMethod("<init>", Type.VOID, new Type[] { Type.INT, Type.INT }, 1); this.init.add((byte)42); this.init.add((byte)27); this.init.add((byte)28); this.init.add((byte)3); this.init.add((byte)-73, this.me.method("<init>", Type.VOID, new Type[] { Type.INT, Type.INT, Type.BOOLEAN })); this.init.add((byte)-79); this.init = this.cg.addMethod("<init>", Type.VOID, new Type[] { Type.INT, Type.INT, Type.BOOLEAN }, 1); this.init.add((byte)42); this.init.add((byte)27); this.init.add((byte)28); this.init.add((byte)29); this.init.add((byte)-73, clazz1.method("<init>", Type.VOID, new Type[] { Type.INT, Type.INT, Type.BOOLEAN })); if (this.onePage) { this.cg.addField("page", Type.INT.makeArray(), 18); this.init.add((byte)42); this.init.add((byte)89); this.init.add((byte)-76, this.me.field("readPages", Type.INT.makeArray(2))); this.init.add((byte)18, 0); this.init.add((byte)50); this.init.add((byte)-75, this.me.field("page", Type.INT.makeArray())); }  if (this.supportCall) this.cg.addField("symbols", Type.Class.instance(this.hashClass), 26);  i = 0; for (byte b1 = 0; b1 < this.elf.sheaders.length; b1++) { ELF.SHeader sHeader1 = this.elf.sheaders[b1]; String str = sHeader1.name; if (sHeader1.addr != 0) { i = Math.max(i, sHeader1.addr + sHeader1.size); if (str.equals(".text")) { emitText(sHeader1.addr, new DataInputStream(sHeader1.getInputStream()), sHeader1.size); } else if (str.equals(".data") || str.equals(".sdata") || str.equals(".rodata") || str.equals(".ctors") || str.equals(".dtors")) { emitData(sHeader1.addr, new DataInputStream(sHeader1.getInputStream()), sHeader1.size, str.equals(".rodata")); } else if (str.equals(".bss") || str.equals(".sbss")) { emitBSS(sHeader1.addr, sHeader1.size); } else { throw new Compiler.Exn("Unknown segment: " + str); }  }  }  this.init.add((byte)-79); if (this.supportCall) { Type.Class clazz = Type.Class.instance(this.hashClass); this.clinit.add((byte)-69, clazz); this.clinit.add((byte)89); this.clinit.add((byte)89); this.clinit.add((byte)-73, clazz.method("<init>", Type.VOID, Type.NO_ARGS)); this.clinit.add((byte)-77, this.me.field("symbols", clazz)); ELF.Symbol[] arrayOfSymbol = (this.elf.getSymtab()).symbols; for (byte b = 0; b < arrayOfSymbol.length; b++) { ELF.Symbol symbol = arrayOfSymbol[b]; if (symbol.type == 2 && symbol.binding == 1 && (symbol.name.equals("_call_helper") || !symbol.name.startsWith("_"))) { this.clinit.add((byte)89); this.clinit.add((byte)18, symbol.name); this.clinit.add((byte)-69, Type.INTEGER_OBJECT); this.clinit.add((byte)89); this.clinit.add((byte)18, symbol.addr); this.clinit.add((byte)-73, Type.INTEGER_OBJECT.method("<init>", Type.VOID, new Type[] { Type.INT })); this.clinit.add((byte)-74, clazz.method("put", Type.OBJECT, new Type[] { Type.OBJECT, Type.OBJECT })); this.clinit.add((byte)87); }  }  this.clinit.add((byte)87); }  this.clinit.add((byte)-79); ELF.SHeader sHeader = this.elf.sectionWithName(".text"); MethodGen methodGen1 = this.cg.addMethod("trampoline", Type.VOID, Type.NO_ARGS, 2); int j = methodGen1.size(); methodGen1.add((byte)42); methodGen1.add((byte)-76, this.me.field("state", Type.INT)); methodGen1.add((byte)-103, methodGen1.size() + 2); methodGen1.add((byte)-79); methodGen1.add((byte)42); methodGen1.add((byte)42); methodGen1.add((byte)-76, this.me.field("pc", Type.INT)); methodGen1.add((byte)18, this.methodShift); methodGen1.add((byte)124); int k = sHeader.addr >>> this.methodShift; int m = sHeader.addr + sHeader.size + this.maxBytesPerMethod - 1 >>> this.methodShift; MethodGen.Switch.Table table = new MethodGen.Switch.Table(k, m - 1); methodGen1.add((byte)-86, table); for (null = k; null < m; null++) { table.setTargetForVal(null, methodGen1.size()); methodGen1.add((byte)-73, this.me.method("run_" + toHex(null << this.methodShift), Type.VOID, Type.NO_ARGS)); methodGen1.add((byte)-89, j); }  table.setDefaultTarget(methodGen1.size()); methodGen1.add((byte)87); methodGen1.add((byte)-69, Type.Class.instance("org.ibex.nestedvm.Runtime$ExecutionException")); methodGen1.add((byte)89); methodGen1.add((byte)-69, Type.STRINGBUFFER); methodGen1.add((byte)89); methodGen1.add((byte)18, "Jumped to invalid address in trampoline (r2: "); methodGen1.add((byte)-73, Type.STRINGBUFFER.method("<init>", Type.VOID, new Type[] { Type.STRING })); methodGen1.add((byte)42); methodGen1.add((byte)-76, this.me.field("r2", Type.INT)); methodGen1.add((byte)-74, Type.STRINGBUFFER.method("append", Type.STRINGBUFFER, new Type[] { Type.INT })); methodGen1.add((byte)18, " pc: "); methodGen1.add((byte)-74, Type.STRINGBUFFER.method("append", Type.STRINGBUFFER, new Type[] { Type.STRING })); methodGen1.add((byte)42); methodGen1.add((byte)-76, this.me.field("pc", Type.INT)); methodGen1.add((byte)-74, Type.STRINGBUFFER.method("append", Type.STRINGBUFFER, new Type[] { Type.INT })); methodGen1.add((byte)18, ")"); methodGen1.add((byte)-74, Type.STRINGBUFFER.method("append", Type.STRINGBUFFER, new Type[] { Type.STRING })); methodGen1.add((byte)-74, Type.STRINGBUFFER.method("toString", Type.STRING, Type.NO_ARGS)); methodGen1.add((byte)-73, Type.Class.instance("org.ibex.nestedvm.Runtime$ExecutionException").method("<init>", Type.VOID, new Type[] { Type.STRING })); methodGen1.add((byte)-65); addConstReturnMethod("gp", this.gp.addr); addConstReturnMethod("entryPoint", this.elf.header.entry); addConstReturnMethod("heapStart", i); if (this.userInfo != null) { addConstReturnMethod("userInfoBase", this.userInfo.addr); addConstReturnMethod("userInfoSize", this.userInfo.size); }  if (this.supportCall) { Type.Class clazz = Type.Class.instance(this.hashClass); MethodGen methodGen = this.cg.addMethod("lookupSymbol", Type.INT, new Type[] { Type.STRING }, 4); methodGen.add((byte)-78, this.me.field("symbols", clazz)); methodGen.add((byte)43); methodGen.add((byte)-74, clazz.method("get", Type.OBJECT, new Type[] { Type.OBJECT })); methodGen.add((byte)89); int i3 = methodGen.add((byte)-58); methodGen.add((byte)-64, Type.INTEGER_OBJECT); methodGen.add((byte)-74, Type.INTEGER_OBJECT.method("intValue", Type.INT, Type.NO_ARGS)); methodGen.add((byte)-84); methodGen.setArg(i3, methodGen.size()); methodGen.add((byte)87); methodGen.add((byte)2); methodGen.add((byte)-84); }  Type.Class clazz2 = Type.Class.instance("org.ibex.nestedvm.Runtime$CPUState"); MethodGen methodGen2 = this.cg.addMethod("setCPUState", Type.VOID, new Type[] { clazz2 }, 4); MethodGen methodGen3 = this.cg.addMethod("getCPUState", Type.VOID, new Type[] { clazz2 }, 4); methodGen2.add((byte)43); methodGen3.add((byte)43); methodGen2.add((byte)-76, clazz2.field("r", Type.INT.makeArray())); methodGen3.add((byte)-76, clazz2.field("r", Type.INT.makeArray())); methodGen2.add((byte)77); methodGen3.add((byte)77); for (null = 1; null < 32; null++) { methodGen2.add((byte)42); methodGen2.add((byte)44); methodGen2.add((byte)18, null); methodGen2.add((byte)46); methodGen2.add((byte)-75, this.me.field("r" + null, Type.INT)); methodGen3.add((byte)44); methodGen3.add((byte)18, null); methodGen3.add((byte)42); methodGen3.add((byte)-76, this.me.field("r" + null, Type.INT)); methodGen3.add((byte)79); }  methodGen2.add((byte)43); methodGen3.add((byte)43); methodGen2.add((byte)-76, clazz2.field("f", Type.INT.makeArray())); methodGen3.add((byte)-76, clazz2.field("f", Type.INT.makeArray())); methodGen2.add((byte)77); methodGen3.add((byte)77); for (null = 0; null < 32; null++) { methodGen2.add((byte)42); methodGen2.add((byte)44); methodGen2.add((byte)18, null); methodGen2.add((byte)46); if (this.singleFloat) methodGen2.add((byte)-72, Type.FLOAT_OBJECT.method("intBitsToFloat", Type.FLOAT, new Type[] { Type.INT }));  methodGen2.add((byte)-75, this.me.field("f" + null, this.singleFloat ? Type.FLOAT : Type.INT)); methodGen3.add((byte)44); methodGen3.add((byte)18, null); methodGen3.add((byte)42); methodGen3.add((byte)-76, this.me.field("f" + null, this.singleFloat ? Type.FLOAT : Type.INT)); if (this.singleFloat) methodGen3.add((byte)-72, Type.FLOAT_OBJECT.method("floatToIntBits", Type.INT, new Type[] { Type.FLOAT }));  methodGen3.add((byte)79); }  String[] arrayOfString = { "hi", "lo", "fcsr", "pc" }; for (byte b2 = 0; b2 < arrayOfString.length; b2++) { methodGen2.add((byte)42); methodGen2.add((byte)43); methodGen2.add((byte)-76, clazz2.field(arrayOfString[b2], Type.INT)); methodGen2.add((byte)-75, this.me.field(arrayOfString[b2], Type.INT)); methodGen3.add((byte)43); methodGen3.add((byte)42); methodGen3.add((byte)-76, this.me.field(arrayOfString[b2], Type.INT)); methodGen3.add((byte)-75, clazz2.field(arrayOfString[b2], Type.INT)); }  methodGen2.add((byte)-79); methodGen3.add((byte)-79); MethodGen methodGen4 = this.cg.addMethod("_execute", Type.VOID, Type.NO_ARGS, 4); int n = methodGen4.size(); methodGen4.add((byte)42); methodGen4.add((byte)-73, this.me.method("trampoline", Type.VOID, Type.NO_ARGS)); int i1 = methodGen4.size(); methodGen4.add((byte)-79); int i2 = methodGen4.size(); methodGen4.add((byte)76); methodGen4.add((byte)-69, Type.Class.instance("org.ibex.nestedvm.Runtime$FaultException")); methodGen4.add((byte)89); methodGen4.add((byte)43); methodGen4.add((byte)-73, Type.Class.instance("org.ibex.nestedvm.Runtime$FaultException").method("<init>", Type.VOID, new Type[] { Type.Class.instance("java.lang.RuntimeException") })); methodGen4.add((byte)-65); methodGen4.addExceptionHandler(n, i1, i2, Type.Class.instance("java.lang.RuntimeException")); methodGen4.addThrow(Type.Class.instance("org.ibex.nestedvm.Runtime$ExecutionException")); MethodGen methodGen5 = this.cg.addMethod("main", Type.VOID, new Type[] { Type.STRING.makeArray() }, 9); methodGen5.add((byte)-69, this.me); methodGen5.add((byte)89); methodGen5.add((byte)-73, this.me.method("<init>", Type.VOID, Type.NO_ARGS)); methodGen5.add((byte)18, this.fullClassName); methodGen5.add((byte)42); if (this.unixRuntime) { Type.Class clazz = Type.Class.instance("org.ibex.nestedvm.UnixRuntime"); methodGen5.add((byte)-72, clazz.method("runAndExec", Type.INT, new Type[] { clazz, Type.STRING, Type.STRING.makeArray() })); } else { methodGen5.add((byte)-74, this.me.method("run", Type.INT, new Type[] { Type.STRING, Type.STRING.makeArray() })); }  methodGen5.add((byte)-72, Type.Class.instance("java.lang.System").method("exit", Type.VOID, new Type[] { Type.INT })); methodGen5.add((byte)-79); if (this.outDir != null) { if (!this.outDir.isDirectory()) throw new IOException("" + this.outDir + " isn't a directory");  this.cg.dump(this.outDir); } else { this.cg.dump(this.os); }  }
/* 1835 */   private void addConstReturnMethod(String paramString, int paramInt) { MethodGen methodGen = this.cg.addMethod(paramString, Type.INT, Type.NO_ARGS, 4); methodGen.add((byte)18, paramInt); methodGen.add((byte)-84); } private void emitData(int paramInt1, DataInputStream paramDataInputStream, int paramInt2, boolean paramBoolean) throws Compiler.Exn, IOException { if ((paramInt1 & 0x3) != 0 || (paramInt2 & 0x3) != 0) throw new Compiler.Exn("Data section on weird boundaries");  int i = paramInt1 + paramInt2; while (paramInt1 < i) { int j = Math.min(paramInt2, 28000); StringBuffer stringBuffer = new StringBuffer(); for (byte b = 0; b < j; b += 7) { long l = 0L; byte b1; for (b1 = 0; b1 < 7; b1++) { l <<= 8; byte b2 = (b + b1 < paramInt2) ? paramDataInputStream.readByte() : 1; l |= b2 & 0xFFL; }  for (b1 = 0; b1 < 8; b1++) stringBuffer.append((char)(int)(l >>> 7 * (7 - b1) & 0x7FL));  }  String str = "_data" + ++initDataCount; this.cg.addField(str, Type.INT.makeArray(), 26); this.clinit.add((byte)18, stringBuffer.toString()); this.clinit.add((byte)18, j / 4); this.clinit.add((byte)-72, Type.Class.instance("org.ibex.nestedvm.Runtime").method("decodeData", Type.INT.makeArray(), new Type[] { Type.STRING, Type.INT })); this.clinit.add((byte)-77, this.me.field(str, Type.INT.makeArray())); this.init.add((byte)42); this.init.add((byte)-78, this.me.field(str, Type.INT.makeArray())); this.init.add((byte)18, paramInt1); this.init.add((byte)18, paramBoolean ? 1 : 0); this.init.add((byte)-74, this.me.method("initPages", Type.VOID, new Type[] { Type.INT.makeArray(), Type.INT, Type.BOOLEAN })); paramInt1 += j; paramInt2 -= j; }  paramDataInputStream.close(); } private void emitBSS(int paramInt1, int paramInt2) throws Compiler.Exn { if ((paramInt1 & 0x3) != 0) throw new Compiler.Exn("BSS section on weird boundaries");  paramInt2 = paramInt2 + 3 & 0xFFFFFFFC; int i = paramInt2 / 4; this.init.add((byte)42); this.init.add((byte)18, paramInt1); this.init.add((byte)18, i); this.init.add((byte)-74, this.me.method("clearPages", Type.VOID, new Type[] { Type.INT, Type.INT })); } private boolean jumpable(int paramInt) { return (this.jumpableAddresses.get(new Integer(paramInt)) != null); } private void emitText(int paramInt1, DataInputStream paramDataInputStream, int paramInt2) throws Compiler.Exn, IOException { if (this.textDone) throw new Compiler.Exn("Multiple text segments");  this.textDone = true; if ((paramInt1 & 0x3) != 0 || (paramInt2 & 0x3) != 0) throw new Compiler.Exn("Section on weird boundaries");  int i = paramInt2 / 4; byte b = -1; boolean bool = true; boolean bool1 = false; for (int j = 0; j < i; j++, paramInt1 += 4) { int k = bool ? paramDataInputStream.readInt() : b; b = (j == i - 1) ? -1 : paramDataInputStream.readInt(); if (paramInt1 >= this.endOfMethod) { endMethod(paramInt1, bool1); startMethod(paramInt1); }  if (this.insnTargets[j % this.maxInsnPerMethod] != null) { this.insnTargets[j % this.maxInsnPerMethod].setTarget(this.mg.size()); bool1 = false; } else if (bool1) { continue; }  try { int m = emitInstruction(paramInt1, k, b); bool1 = ((m & true) != 0); bool = ((m & 0x2) != 0) ? 1 : 0; } catch (Exn exn) { exn.printStackTrace(this.warn); this.warn.println("Exception at " + toHex(paramInt1)); throw exn; } catch (RuntimeException runtimeException) { this.warn.println("Exception at " + toHex(paramInt1)); throw runtimeException; }  if (bool) { paramInt1 += 4; j++; }  }  endMethod(0, bool1); paramDataInputStream.close(); } private void startMethod(int paramInt) { this.startOfMethod = paramInt & this.methodMask; this.endOfMethod = this.startOfMethod + this.maxBytesPerMethod; this.mg = this.cg.addMethod("run_" + toHex(this.startOfMethod), Type.VOID, Type.NO_ARGS, 18); if (this.onePage) { this.mg.add((byte)42); this.mg.add((byte)-76, this.me.field("page", Type.INT.makeArray())); this.mg.add((byte)77); } else { this.mg.add((byte)42); this.mg.add((byte)-76, this.me.field("readPages", Type.INT.makeArray(2))); this.mg.add((byte)77); this.mg.add((byte)42); this.mg.add((byte)-76, this.me.field("writePages", Type.INT.makeArray(2))); this.mg.add((byte)78); }  this.returnTarget = new MethodGen.PhantomTarget(); this.insnTargets = new MethodGen.PhantomTarget[this.maxBytesPerMethod / 4]; int[] arrayOfInt = new int[this.maxBytesPerMethod / 4]; Object[] arrayOfObject = new Object[this.maxBytesPerMethod / 4]; byte b = 0; for (int i = paramInt; i < this.endOfMethod; i += 4) { if (jumpable(i)) { this.insnTargets[(i - this.startOfMethod) / 4] = new MethodGen.PhantomTarget(); arrayOfObject[b] = new MethodGen.PhantomTarget(); arrayOfInt[b] = i; b++; }  }  MethodGen.Switch.Lookup lookup = new MethodGen.Switch.Lookup(b); System.arraycopy(arrayOfInt, 0, lookup.vals, 0, b); System.arraycopy(arrayOfObject, 0, lookup.targets, 0, b); lookup.setDefaultTarget(this.defaultTarget = new MethodGen.PhantomTarget()); fixupRegsStart(); this.mg.add((byte)42); this.mg.add((byte)-76, this.me.field("pc", Type.INT)); this.mg.add((byte)-85, lookup); } private void endMethod(int paramInt, boolean paramBoolean) { if (this.startOfMethod == 0) return;  if (!paramBoolean) { preSetPC(); this.mg.add((byte)18, paramInt); setPC(); this.jumpableAddresses.put(new Integer(paramInt), Boolean.TRUE); }  this.returnTarget.setTarget(this.mg.size()); fixupRegsEnd(); this.mg.add((byte)-79); this.defaultTarget.setTarget(this.mg.size()); if (this.debugCompiler) { this.mg.add((byte)-69, Type.Class.instance("org.ibex.nestedvm.Runtime$ExecutionException")); this.mg.add((byte)89); this.mg.add((byte)-69, Type.STRINGBUFFER); this.mg.add((byte)89); this.mg.add((byte)18, "Jumped to invalid address: "); this.mg.add((byte)-73, Type.STRINGBUFFER.method("<init>", Type.VOID, new Type[] { Type.STRING })); this.mg.add((byte)42); this.mg.add((byte)-76, this.me.field("pc", Type.INT)); this.mg.add((byte)-74, Type.STRINGBUFFER.method("append", Type.STRINGBUFFER, new Type[] { Type.INT })); this.mg.add((byte)-74, Type.STRINGBUFFER.method("toString", Type.STRING, Type.NO_ARGS)); this.mg.add((byte)-73, Type.Class.instance("org.ibex.nestedvm.Runtime$ExecutionException").method("<init>", Type.VOID, new Type[] { Type.STRING })); this.mg.add((byte)-65); } else { this.mg.add((byte)-69, Type.Class.instance("org.ibex.nestedvm.Runtime$ExecutionException")); this.mg.add((byte)89); this.mg.add((byte)18, "Jumped to invalid address"); this.mg.add((byte)-73, Type.Class.instance("org.ibex.nestedvm.Runtime$ExecutionException").method("<init>", Type.VOID, new Type[] { Type.STRING })); this.mg.add((byte)-65); }  this.endOfMethod = this.startOfMethod = 0; } private void leaveMethod() throws Compiler.Exn, IOException { this.mg.add((byte)-89, this.returnTarget); } private void link(int paramInt) { preSetReg(31); if (this.lessConstants) { int i = paramInt + 8 + 32768 & 0xFFFF0000; int j = paramInt + 8 - i; if (j < -32768 || j > 32767) throw new Error("should never happen " + j);  this.mg.add((byte)18, i); this.mg.add((byte)18, j); this.mg.add((byte)96); } else { this.mg.add((byte)18, paramInt + 8); }  setReg(); } private void branch(int paramInt1, int paramInt2) throws Compiler.Exn { if ((paramInt1 & this.methodMask) == (paramInt2 & this.methodMask)) { this.mg.add((byte)-89, this.insnTargets[(paramInt2 - this.startOfMethod) / 4]); } else { preSetPC(); this.mg.add((byte)18, paramInt2); setPC(); leaveMethod(); }  } private int doIfInstruction(byte paramByte, int paramInt1, int paramInt2, int paramInt3) throws Compiler.Exn { emitInstruction(-1, paramInt3, -1); if ((paramInt2 & this.methodMask) == (paramInt1 & this.methodMask)) { this.mg.add(paramByte, this.insnTargets[(paramInt2 - this.startOfMethod) / 4]); } else { int j = this.mg.add(MethodGen.negate(paramByte)); branch(paramInt1, paramInt2); this.mg.setArg(j, this.mg.size()); }  if (!jumpable(paramInt1 + 4)) return 2;  if (paramInt1 + 4 == this.endOfMethod) { this.jumpableAddresses.put(new Integer(paramInt1 + 8), Boolean.TRUE); branch(paramInt1, paramInt1 + 8); return 1; }  int i = this.mg.add((byte)-89); this.insnTargets[(paramInt1 + 4 - this.startOfMethod) / 4].setTarget(this.mg.size()); emitInstruction(-1, paramInt3, 1); this.mg.setArg(i, this.mg.size()); return 2; } private static final Float POINT_5_F = new Float(0.5F); private static final Double POINT_5_D = new Double(0.5D); private static final Long FFFFFFFF = new Long(4294967295L); private static final int R = 0; private static final int F = 32; private static final int HI = 64; private static final int LO = 65; private static final int FCSR = 66; private boolean preSetReg(int paramInt) { this.preSetRegStack[this.preSetRegStackPos] = paramInt;
/* 1836 */     this.preSetRegStackPos++;
/* 1837 */     if (doLocal(paramInt)) {
/* 1838 */       return false;
/*      */     }
/* 1840 */     this.mg.add((byte)42);
/* 1841 */     return true; }
/*      */   private static final int REG_COUNT = 67;
/*      */   private int emitInstruction(int paramInt1, int paramInt2, int paramInt3) throws Compiler.Exn { MethodGen.Switch.Table table; int i12, i11, i10; MethodGen methodGen = this.mg; if (paramInt2 == -1) throw new Compiler.Exn("insn is -1");  null = 0; int i = paramInt2 >>> 26 & 0xFF; int j = paramInt2 >>> 21 & 0x1F; int k = paramInt2 >>> 16 & 0x1F; int m = paramInt2 >>> 16 & 0x1F; int n = paramInt2 >>> 11 & 0x1F; int i1 = paramInt2 >>> 11 & 0x1F; int i2 = paramInt2 >>> 6 & 0x1F; int i3 = paramInt2 >>> 6 & 0x1F; int i4 = paramInt2 & 0x3F; int i5 = paramInt2 >>> 6 & 0xFFFFF; int i6 = paramInt2 & 0x3FFFFFF; int i7 = paramInt2 & 0xFFFF; int i8 = paramInt2 << 16 >> 16; int i9 = i8; switch (i) { case 0: switch (i4) { case 0: if (paramInt2 != 0) { preSetReg(0 + n); pushRegWZ(0 + k); methodGen.add((byte)18, i2); methodGen.add((byte)120); setReg(); }  return null;case 2: preSetReg(0 + n); pushRegWZ(0 + k); methodGen.add((byte)18, i2); methodGen.add((byte)124); setReg(); return null;case 3: preSetReg(0 + n); pushRegWZ(0 + k); methodGen.add((byte)18, i2); methodGen.add((byte)122); setReg(); return null;case 4: preSetReg(0 + n); pushRegWZ(0 + k); pushRegWZ(0 + j); methodGen.add((byte)120); setReg(); return null;case 6: preSetReg(0 + n); pushRegWZ(0 + k); pushRegWZ(0 + j); methodGen.add((byte)124); setReg(); return null;case 7: preSetReg(0 + n); pushRegWZ(0 + k); pushRegWZ(0 + j); methodGen.add((byte)122); setReg(); return null;case 8: if (paramInt1 == -1) throw new Compiler.Exn("pc modifying insn in delay slot");  emitInstruction(-1, paramInt3, -1); preSetPC(); pushRegWZ(0 + j); setPC(); leaveMethod(); return 1;case 9: if (paramInt1 == -1) throw new Compiler.Exn("pc modifying insn in delay slot");  emitInstruction(-1, paramInt3, -1); link(paramInt1); preSetPC(); pushRegWZ(0 + j); setPC(); leaveMethod(); return 1;case 12: preSetPC(); methodGen.add((byte)18, paramInt1); setPC(); restoreChangedRegs(); preSetReg(2); methodGen.add((byte)42); pushRegZ(2); pushRegZ(4); pushRegZ(5); pushRegZ(6); pushRegZ(7); pushRegZ(8); pushRegZ(9); methodGen.add((byte)-74, this.me.method("syscall", Type.INT, new Type[] { Type.INT, Type.INT, Type.INT, Type.INT, Type.INT, Type.INT, Type.INT })); setReg(); methodGen.add((byte)42); methodGen.add((byte)-76, this.me.field("state", Type.INT)); i10 = methodGen.add((byte)-103); preSetPC(); methodGen.add((byte)18, paramInt1 + 4); setPC(); leaveMethod(); methodGen.setArg(i10, methodGen.size()); return null;case 13: methodGen.add((byte)-69, Type.Class.instance("org.ibex.nestedvm.Runtime$ExecutionException")); methodGen.add((byte)89); methodGen.add((byte)18, "BREAK Code " + toHex(i5)); methodGen.add((byte)-73, Type.Class.instance("org.ibex.nestedvm.Runtime$ExecutionException").method("<init>", Type.VOID, new Type[] { Type.STRING })); methodGen.add((byte)-65); return 1;case 16: preSetReg(0 + n); pushReg(64); setReg(); return null;case 17: preSetReg(64); pushRegZ(0 + j); setReg(); return null;case 18: preSetReg(0 + n); pushReg(65); setReg(); return null;case 19: preSetReg(65); pushRegZ(0 + j); setReg(); return null;case 24: pushRegWZ(0 + j); methodGen.add((byte)-123); pushRegWZ(0 + k); methodGen.add((byte)-123); methodGen.add((byte)105); methodGen.add((byte)92); methodGen.add((byte)-120); if (preSetReg(65)) methodGen.add((byte)95);  setReg(); methodGen.add((byte)18, 32); methodGen.add((byte)125); methodGen.add((byte)-120); if (preSetReg(64)) methodGen.add((byte)95);  setReg(); return null;case 25: pushRegWZ(0 + j); methodGen.add((byte)-123); methodGen.add((byte)18, FFFFFFFF); methodGen.add(127); pushRegWZ(0 + k); methodGen.add((byte)-123); methodGen.add((byte)18, FFFFFFFF); methodGen.add(127); methodGen.add((byte)105); methodGen.add((byte)92); methodGen.add((byte)-120); if (preSetReg(65)) methodGen.add((byte)95);  setReg(); methodGen.add((byte)18, 32); methodGen.add((byte)125); methodGen.add((byte)-120); if (preSetReg(64)) methodGen.add((byte)95);  setReg(); return null;case 26: pushRegWZ(0 + j); pushRegWZ(0 + k); methodGen.add((byte)92); methodGen.add((byte)108); if (preSetReg(65)) methodGen.add((byte)95);  setReg(); methodGen.add((byte)112); if (preSetReg(64)) methodGen.add((byte)95);  setReg(); return null;case 27: pushRegWZ(0 + k); methodGen.add((byte)89); setTmp(); i10 = methodGen.add((byte)-103); pushRegWZ(0 + j); methodGen.add((byte)-123); methodGen.add((byte)18, FFFFFFFF); methodGen.add(127); methodGen.add((byte)92); pushTmp(); methodGen.add((byte)-123); methodGen.add((byte)18, FFFFFFFF); methodGen.add(127); methodGen.add((byte)94); methodGen.add((byte)109); methodGen.add((byte)-120); if (preSetReg(65)) methodGen.add((byte)95);  setReg(); methodGen.add((byte)113); methodGen.add((byte)-120); if (preSetReg(64)) methodGen.add((byte)95);  setReg(); methodGen.setArg(i10, methodGen.size()); return null;case 32: throw new Compiler.Exn("ADD (add with oveflow trap) not suported");case 33: preSetReg(0 + n); if (k != 0 && j != 0) { pushReg(0 + j); pushReg(0 + k); methodGen.add((byte)96); } else if (j != 0) { pushReg(0 + j); } else { pushRegZ(0 + k); }  setReg(); return null;case 34: throw new Compiler.Exn("SUB (add with oveflow trap) not suported");case 35: preSetReg(0 + n); if (k != 0 && j != 0) { pushReg(0 + j); pushReg(0 + k); methodGen.add((byte)100); } else if (k != 0) { pushReg(0 + k); methodGen.add((byte)116); } else { pushRegZ(0 + j); }  setReg(); return null;case 36: preSetReg(0 + n); pushRegWZ(0 + j); pushRegWZ(0 + k); methodGen.add((byte)126); setReg(); return null;case 37: preSetReg(0 + n); pushRegWZ(0 + j); pushRegWZ(0 + k); methodGen.add(-128); setReg(); return null;case 38: preSetReg(0 + n); pushRegWZ(0 + j); pushRegWZ(0 + k); methodGen.add((byte)-126); setReg(); return null;case 39: preSetReg(0 + n); if (j != 0 || k != 0) { if (j != 0 && k != 0) { pushReg(0 + j); pushReg(0 + k); methodGen.add(-128); } else if (j != 0) { pushReg(0 + j); } else { pushReg(0 + k); }  methodGen.add((byte)2); methodGen.add((byte)-126); } else { methodGen.add((byte)18, -1); }  setReg(); return null;case 42: preSetReg(0 + n); if (j != k) { pushRegZ(0 + j); pushRegZ(0 + k); i10 = methodGen.add((byte)-95); methodGen.add((byte)3); int i13 = methodGen.add((byte)-89); methodGen.setArg(i10, methodGen.add((byte)4)); methodGen.setArg(i13, methodGen.size()); } else { methodGen.add((byte)18, 0); }  setReg(); return null;case 43: preSetReg(0 + n); if (j != k) { if (j != 0) { pushReg(0 + j); methodGen.add((byte)-123); methodGen.add((byte)18, FFFFFFFF); methodGen.add(127); pushReg(0 + k); methodGen.add((byte)-123); methodGen.add((byte)18, FFFFFFFF); methodGen.add(127); methodGen.add((byte)-108); i10 = methodGen.add((byte)-101); } else { pushReg(0 + k); i10 = methodGen.add((byte)-102); }  methodGen.add((byte)3); int i13 = methodGen.add((byte)-89); methodGen.setArg(i10, methodGen.add((byte)4)); methodGen.setArg(i13, methodGen.size()); } else { methodGen.add((byte)18, 0); }  setReg(); return null; }  throw new Compiler.Exn("Illegal instruction 0/" + i4);case 1: switch (k) { case 0: if (paramInt1 == -1) throw new Compiler.Exn("pc modifying insn in delay slot");  pushRegWZ(0 + j); return doIfInstruction((byte)-101, paramInt1, paramInt1 + i9 * 4 + 4, paramInt3);case 1: if (paramInt1 == -1) throw new Compiler.Exn("pc modifying insn in delay slot");  pushRegWZ(0 + j); return doIfInstruction((byte)-100, paramInt1, paramInt1 + i9 * 4 + 4, paramInt3);case 16: if (paramInt1 == -1) throw new Compiler.Exn("pc modifying insn in delay slot");  pushRegWZ(0 + j); i10 = methodGen.add((byte)-100); emitInstruction(-1, paramInt3, -1); link(paramInt1); branch(paramInt1, paramInt1 + i9 * 4 + 4); methodGen.setArg(i10, methodGen.size()); return null;case 17: if (paramInt1 == -1) throw new Compiler.Exn("pc modifying insn in delay slot");  i10 = -1; if (j != 0) { pushRegWZ(0 + j); i10 = methodGen.add((byte)-101); }  emitInstruction(-1, paramInt3, -1); link(paramInt1); branch(paramInt1, paramInt1 + i9 * 4 + 4); if (i10 != -1) methodGen.setArg(i10, methodGen.size());  if (i10 == -1) null |= 0x1;  return null; }  throw new Compiler.Exn("Illegal Instruction 1/" + k);case 2: if (paramInt1 == -1) throw new Compiler.Exn("pc modifying insn in delay slot");  emitInstruction(-1, paramInt3, -1); branch(paramInt1, paramInt1 & 0xF0000000 | i6 << 2); return 1;case 3: if (paramInt1 == -1) throw new Compiler.Exn("pc modifying insn in delay slot");  i12 = paramInt1 & 0xF0000000 | i6 << 2; emitInstruction(-1, paramInt3, -1); link(paramInt1); branch(paramInt1, i12); return 1;case 4: if (paramInt1 == -1) throw new Compiler.Exn("pc modifying insn in delay slot");  if (j == k) { emitInstruction(-1, paramInt3, -1); branch(paramInt1, paramInt1 + i9 * 4 + 4); null |= 0x1; } else { if (j == 0 || k == 0) { pushReg((k == 0) ? (0 + j) : (0 + k)); return doIfInstruction((byte)-103, paramInt1, paramInt1 + i9 * 4 + 4, paramInt3); }  pushReg(0 + j); pushReg(0 + k); return doIfInstruction((byte)-97, paramInt1, paramInt1 + i9 * 4 + 4, paramInt3); }  return null;case 5: if (paramInt1 == -1) throw new Compiler.Exn("pc modifying insn in delay slot");  pushRegWZ(0 + j); if (k == 0) return doIfInstruction((byte)-102, paramInt1, paramInt1 + i9 * 4 + 4, paramInt3);  pushReg(0 + k); return doIfInstruction((byte)-96, paramInt1, paramInt1 + i9 * 4 + 4, paramInt3);case 6: if (paramInt1 == -1) throw new Compiler.Exn("pc modifying insn in delay slot");  pushRegWZ(0 + j); return doIfInstruction((byte)-98, paramInt1, paramInt1 + i9 * 4 + 4, paramInt3);case 7: if (paramInt1 == -1) throw new Compiler.Exn("pc modifying insn in delay slot");  pushRegWZ(0 + j); return doIfInstruction((byte)-99, paramInt1, paramInt1 + i9 * 4 + 4, paramInt3);case 8: throw new Compiler.Exn("ADDI (add immediate with oveflow trap) not suported");case 9: if (j != 0 && i8 != 0 && j == k && doLocal(k) && i8 >= -32768 && i8 <= 32767) { this.regLocalWritten[k] = true; methodGen.add((byte)-124, new MethodGen.Pair(getLocalForReg(k), i8)); } else { preSetReg(0 + k); addiu(j, i8); setReg(); }  return null;case 10: preSetReg(0 + k); pushRegWZ(0 + j); methodGen.add((byte)18, i8); i10 = methodGen.add((byte)-95); methodGen.add((byte)3); i11 = methodGen.add((byte)-89); methodGen.setArg(i10, methodGen.add((byte)4)); methodGen.setArg(i11, methodGen.size()); setReg(); return null;case 11: preSetReg(0 + k); pushRegWZ(0 + j); methodGen.add((byte)-123); methodGen.add((byte)18, FFFFFFFF); methodGen.add(127); methodGen.add((byte)18, new Long(i8 & 0xFFFFFFFFL)); methodGen.add((byte)-108); i10 = methodGen.add((byte)-101); methodGen.add((byte)3); i11 = methodGen.add((byte)-89); methodGen.setArg(i10, methodGen.add((byte)4)); methodGen.setArg(i11, methodGen.size()); setReg(); return null;case 12: preSetReg(0 + k); pushRegWZ(0 + j); methodGen.add((byte)18, i7); methodGen.add((byte)126); setReg(); return null;case 13: preSetReg(0 + k); if (j != 0 && i7 != 0) { pushReg(0 + j); methodGen.add((byte)18, i7); methodGen.add(-128); } else if (j != 0) { pushReg(0 + j); } else { methodGen.add((byte)18, i7); }  setReg(); return null;case 14: preSetReg(0 + k); pushRegWZ(0 + j); methodGen.add((byte)18, i7); methodGen.add((byte)-126); setReg(); return null;case 15: preSetReg(0 + k); methodGen.add((byte)18, i7 << 16); setReg(); return null;case 16: throw new Compiler.Exn("TLB/Exception support not implemented");case 17: switch (j) { case 0: preSetReg(0 + k); pushReg(32 + n); setReg(); return null;case 2: if (i1 != 31) throw new Compiler.Exn("FCR " + i1 + " unavailable");  preSetReg(0 + k); pushReg(66); setReg(); return null;case 4: preSetReg(32 + n); if (k != 0) { pushReg(0 + k); } else { methodGen.add((byte)3); }  setReg(); return null;case 6: if (i1 != 31) throw new Compiler.Exn("FCR " + i1 + " unavailable");  preSetReg(66); pushReg(0 + k); setReg(); return null;case 8: pushReg(66); methodGen.add((byte)18, 8388608); methodGen.add((byte)126); return doIfInstruction(((paramInt2 >>> 16 & true) == 0) ? -103 : -102, paramInt1, paramInt1 + i9 * 4 + 4, paramInt3);case 16: case 17: i12 = (j == 17) ? 1 : 0; switch (i4) { case 0: preSetDouble(32 + i3, i12); pushDouble(32 + i1, i12); pushDouble(32 + m, i12); methodGen.add((i12 != 0) ? 99 : 98); setDouble(i12); return null;case 1: preSetDouble(32 + i3, i12); pushDouble(32 + i1, i12); pushDouble(32 + m, i12); methodGen.add((i12 != 0) ? 103 : 102); setDouble(i12); return null;case 2: preSetDouble(32 + i3, i12); pushDouble(32 + i1, i12); pushDouble(32 + m, i12); methodGen.add((i12 != 0) ? 107 : 106); setDouble(i12); return null;case 3: preSetDouble(32 + i3, i12); pushDouble(32 + i1, i12); pushDouble(32 + m, i12); methodGen.add((i12 != 0) ? 111 : 110); setDouble(i12); return null;case 5: preSetDouble(32 + i3, i12); pushDouble(32 + i1, i12); methodGen.add((i12 != 0) ? 92 : 89); methodGen.add((i12 != 0) ? 14 : 11); methodGen.add((i12 != 0) ? -104 : -106); i10 = methodGen.add((byte)-99); methodGen.add((i12 != 0) ? 14 : 11); if (i12 != 0) { methodGen.add((byte)94); methodGen.add((byte)88); } else { methodGen.add((byte)95); }  methodGen.add((i12 != 0) ? 103 : 102); methodGen.setArg(i10, methodGen.size()); setDouble(i12); return null;case 6: preSetReg(32 + i3); pushReg(32 + i1); setReg(); if (i12 != 0) { preSetReg(32 + i3 + 1); pushReg(32 + i1 + 1); setReg(); }  return null;case 7: preSetDouble(32 + i3, i12); pushDouble(32 + i1, i12); methodGen.add((i12 != 0) ? 119 : 118); setDouble(i12); return null;case 32: preSetFloat(32 + i3); pushDouble(32 + i1, i12); if (i12 != 0) methodGen.add((byte)-112);  setFloat(); return null;case 33: preSetDouble(32 + i3); pushDouble(32 + i1, i12); if (i12 == 0) methodGen.add((byte)-115);  setDouble(); return null;case 36: table = new MethodGen.Switch.Table(false, 3); preSetReg(32 + i3); pushDouble(32 + i1, i12); pushReg(66); methodGen.add((byte)6); methodGen.add((byte)126); methodGen.add((byte)-86, table); table.setTarget(2, methodGen.size()); if (i12 == 0) methodGen.add((byte)-115);  methodGen.add((byte)-72, Type.Class.instance("java.lang.Math").method("ceil", Type.DOUBLE, new Type[] { Type.DOUBLE })); if (i12 == 0) methodGen.add((byte)-112);  i10 = methodGen.add((byte)-89); table.setTarget(0, methodGen.size()); methodGen.add((byte)18, (i12 != 0) ? POINT_5_D : POINT_5_F); methodGen.add((i12 != 0) ? 99 : 98); table.setTarget(3, methodGen.size()); if (i12 == 0) methodGen.add((byte)-115);  methodGen.add((byte)-72, Type.Class.instance("java.lang.Math").method("floor", Type.DOUBLE, new Type[] { Type.DOUBLE })); if (i12 == 0) methodGen.add((byte)-112);  table.setTarget(1, methodGen.size()); table.setDefaultTarget(methodGen.size()); methodGen.setArg(i10, methodGen.size()); methodGen.add((i12 != 0) ? -114 : -117); setReg(); return null;case 50: case 60: case 62: preSetReg(66); pushReg(66); methodGen.add((byte)18, -8388609); methodGen.add((byte)126); pushDouble(32 + i1, i12); pushDouble(32 + m, i12); methodGen.add((i12 != 0) ? -104 : -106); switch (i4) { case 50: i10 = methodGen.add((byte)-102); break;case 60: i10 = methodGen.add((byte)-100); break;case 62: i10 = methodGen.add((byte)-99); break;default: i10 = -1; break; }  methodGen.add((byte)18, 8388608); methodGen.add(-128); methodGen.setArg(i10, methodGen.size()); setReg(); return null; }  throw new Compiler.Exn("Invalid Instruction 17/" + j + "/" + i4);case 20: switch (i4) { case 32: preSetFloat(32 + i3); pushReg(32 + i1); methodGen.add((byte)-122); setFloat(); return null;case 33: preSetDouble(32 + i3); pushReg(32 + i1); methodGen.add((byte)-121); setDouble(); return null; }  throw new Compiler.Exn("Invalid Instruction 17/" + j + "/" + i4); }  throw new Compiler.Exn("Invalid Instruction 17/" + j);case 18: case 19: throw new Compiler.Exn("coprocessor 2 and 3 instructions not available");case 32: preSetReg(0 + k); addiu(0 + j, i8); setTmp(); preMemRead(); pushTmp(); memRead(true); pushTmp(); methodGen.add((byte)2); methodGen.add((byte)-126); methodGen.add((byte)6); methodGen.add((byte)126); methodGen.add((byte)6); methodGen.add((byte)120); methodGen.add((byte)124); methodGen.add((byte)-111); setReg(); return null;case 33: preSetReg(0 + k); addiu(0 + j, i8); setTmp(); preMemRead(); pushTmp(); memRead(true); pushTmp(); methodGen.add((byte)2); methodGen.add((byte)-126); methodGen.add((byte)5); methodGen.add((byte)126); methodGen.add((byte)6); methodGen.add((byte)120); methodGen.add((byte)124); methodGen.add((byte)-109); setReg(); return null;case 34: preSetReg(0 + k); addiu(0 + j, i8); setTmp(); pushRegWZ(0 + k); methodGen.add((byte)18, 16777215); pushTmp(); methodGen.add((byte)2); methodGen.add((byte)-126); methodGen.add((byte)6); methodGen.add((byte)126); methodGen.add((byte)6); methodGen.add((byte)120); methodGen.add((byte)124); methodGen.add((byte)126); preMemRead(); pushTmp(); memRead(true); pushTmp(); methodGen.add((byte)6); methodGen.add((byte)126); methodGen.add((byte)6); methodGen.add((byte)120); methodGen.add((byte)120); methodGen.add(-128); setReg(); return null;case 35: preSetReg(0 + k); memRead(0 + j, i8); setReg(); return null;case 36: preSetReg(0 + k); addiu(0 + j, i8); setTmp(); preMemRead(); pushTmp(); memRead(true); pushTmp(); methodGen.add((byte)2); methodGen.add((byte)-126); methodGen.add((byte)6); methodGen.add((byte)126); methodGen.add((byte)6); methodGen.add((byte)120); methodGen.add((byte)124); methodGen.add((byte)18, 255); methodGen.add((byte)126); setReg(); return null;case 37: preSetReg(0 + k); addiu(0 + j, i8); setTmp(); preMemRead(); pushTmp(); memRead(true); pushTmp(); methodGen.add((byte)2); methodGen.add((byte)-126); methodGen.add((byte)5); methodGen.add((byte)126); methodGen.add((byte)6); methodGen.add((byte)120); methodGen.add((byte)124); methodGen.add((byte)-110); setReg(); return null;case 38: preSetReg(0 + k); addiu(0 + j, i8); setTmp(); pushRegWZ(0 + k); methodGen.add((byte)18, -256); pushTmp(); methodGen.add((byte)6); methodGen.add((byte)126); methodGen.add((byte)6); methodGen.add((byte)120); methodGen.add((byte)120); methodGen.add((byte)126); preMemRead(); pushTmp(); memRead(true); pushTmp(); methodGen.add((byte)2); methodGen.add((byte)-126); methodGen.add((byte)6); methodGen.add((byte)126); methodGen.add((byte)6); methodGen.add((byte)120); methodGen.add((byte)124); methodGen.add(-128); setReg(); return null;case 40: addiu(0 + j, i8); setTmp(); preMemRead(true); pushTmp(); memRead(true); methodGen.add((byte)18, -16777216); pushTmp(); methodGen.add((byte)6); methodGen.add((byte)126); methodGen.add((byte)6); methodGen.add((byte)120); methodGen.add((byte)124); methodGen.add((byte)2); methodGen.add((byte)-126); methodGen.add((byte)126); if (k != 0) { pushReg(0 + k); methodGen.add((byte)18, 255); methodGen.add((byte)126); } else { methodGen.add((byte)18, 0); }  pushTmp(); methodGen.add((byte)2); methodGen.add((byte)-126); methodGen.add((byte)6); methodGen.add((byte)126); methodGen.add((byte)6); methodGen.add((byte)120); methodGen.add((byte)120); methodGen.add(-128); memWrite(); return null;case 41: addiu(0 + j, i8); setTmp(); preMemRead(true); pushTmp(); memRead(true); methodGen.add((byte)18, 65535); pushTmp(); methodGen.add((byte)5); methodGen.add((byte)126); methodGen.add((byte)6); methodGen.add((byte)120); methodGen.add((byte)120); methodGen.add((byte)126); if (k != 0) { pushReg(0 + k); methodGen.add((byte)18, 65535); methodGen.add((byte)126); } else { methodGen.add((byte)18, 0); }  pushTmp(); methodGen.add((byte)2); methodGen.add((byte)-126); methodGen.add((byte)5); methodGen.add((byte)126); methodGen.add((byte)6); methodGen.add((byte)120); methodGen.add((byte)120); methodGen.add(-128); memWrite(); return null;case 42: addiu(0 + j, i8); setTmp(); preMemRead(true); pushTmp(); memRead(true); methodGen.add((byte)18, -256); pushTmp(); methodGen.add((byte)2); methodGen.add((byte)-126); methodGen.add((byte)6); methodGen.add((byte)126); methodGen.add((byte)6); methodGen.add((byte)120); methodGen.add((byte)120); methodGen.add((byte)126); pushRegWZ(0 + k); pushTmp(); methodGen.add((byte)6); methodGen.add((byte)126); methodGen.add((byte)6); methodGen.add((byte)120); methodGen.add((byte)124); methodGen.add(-128); memWrite(); return null;case 43: preMemWrite1(); preMemWrite2(0 + j, i8); pushRegZ(0 + k); memWrite(); return null;case 46: addiu(0 + j, i8); setTmp(); preMemRead(true); pushTmp(); memRead(true); methodGen.add((byte)18, 16777215); pushTmp(); methodGen.add((byte)6); methodGen.add((byte)126); methodGen.add((byte)6); methodGen.add((byte)120); methodGen.add((byte)124); methodGen.add((byte)126); pushRegWZ(0 + k); pushTmp(); methodGen.add((byte)2); methodGen.add((byte)-126); methodGen.add((byte)6); methodGen.add((byte)126); methodGen.add((byte)6); methodGen.add((byte)120); methodGen.add((byte)120); methodGen.add(-128); memWrite(); return null;case 48: preSetReg(0 + k); memRead(0 + j, i8); setReg(); return null;case 49: preSetReg(32 + k); memRead(0 + j, i8); setReg(); return null;case 56: preSetReg(0 + k); preMemWrite1(); preMemWrite2(0 + j, i8); pushReg(0 + k); memWrite(); methodGen.add((byte)18, 1); setReg(); return null;case 57: preMemWrite1(); preMemWrite2(0 + j, i8); pushReg(32 + k); memWrite(); return null; }  throw new Compiler.Exn("Invalid Instruction: " + i + " at " + toHex(paramInt1)); }
/*      */   private static final String[] regField = { "r0", "r1", "r2", "r3", "r4", "r5", "r6", "r7", "r8", "r9", "r10", "r11", "r12", "r13", "r14", "r15", "r16", "r17", "r18", "r19", "r20", "r21", "r22", "r23", "r24", "r25", "r26", "r27", "r28", "r29", "r30", "r31", "f0", "f1", "f2", "f3", "f4", "f5", "f6", "f7", "f8", "f9", "f10", "f11", "f12", "f13", "f14", "f15", "f16", "f17", "f18", "f19", "f20", "f21", "f22", "f23", "f24", "f25", "f26", "f27", "f28", "f29", "f30", "f31", "hi", "lo", "fcsr" };
/*      */   private static final int MAX_LOCALS = 4;
/* 1846 */   private static final int LOAD_LENGTH = 3; private int[] regLocalMapping; private boolean[] regLocalWritten; private int nextAvailLocal; private int loadsStart; private int preSetRegStackPos; private int[] preSetRegStack; private int memWriteStage; private boolean didPreMemRead; private boolean preMemReadDoPreWrite; private boolean doLocal(int paramInt) { return (paramInt == 2 || paramInt == 3 || paramInt == 4 || paramInt == 29); } private int getLocalForReg(int paramInt) { if (this.regLocalMapping[paramInt] != 0) return this.regLocalMapping[paramInt];  this.regLocalMapping[paramInt] = this.nextAvailLocal++; return this.regLocalMapping[paramInt]; } private void fixupRegsStart() throws Compiler.Exn, IOException { byte b; for (b = 0; b < 67; b++) { this.regLocalMapping[b] = 0; this.regLocalWritten[b] = false; }  this.nextAvailLocal = this.onePage ? 4 : 5; this.loadsStart = this.mg.size(); for (b = 0; b < 12; b++) this.mg.add((byte)0);  } private void fixupRegsEnd() throws Compiler.Exn, IOException { int i = this.loadsStart; for (byte b = 0; b < 67; b++) { if (this.regLocalMapping[b] != 0) { this.mg.set(i++, (byte)42); this.mg.set(i++, (byte)-76, this.me.field(regField[b], Type.INT)); this.mg.set(i++, (byte)54, this.regLocalMapping[b]); if (this.regLocalWritten[b]) { this.mg.add((byte)42); this.mg.add((byte)21, this.regLocalMapping[b]); this.mg.add((byte)-75, this.me.field(regField[b], Type.INT)); }  }  }  } private void restoreChangedRegs() throws Compiler.Exn, IOException { for (byte b = 0; b < 67; b++) { if (this.regLocalWritten[b]) { this.mg.add((byte)42); this.mg.add((byte)21, this.regLocalMapping[b]); this.mg.add((byte)-75, this.me.field(regField[b], Type.INT)); }  }  } private int pushRegWZ(int paramInt) { if (paramInt == 0) { this.warn.println("Warning: Pushing r0!"); (new Exception()).printStackTrace(this.warn); }  return pushRegZ(paramInt); } private int pushRegZ(int paramInt) { if (paramInt == 0) return this.mg.add((byte)3);  return pushReg(paramInt); } private int pushReg(int paramInt) { int i = this.mg.size(); if (doLocal(paramInt)) { this.mg.add((byte)21, getLocalForReg(paramInt)); } else if (paramInt >= 32 && paramInt <= 63 && this.singleFloat) { this.mg.add((byte)42); this.mg.add((byte)-76, this.me.field(regField[paramInt], Type.FLOAT)); this.mg.add((byte)-72, Type.FLOAT_OBJECT.method("floatToIntBits", Type.INT, new Type[] { Type.FLOAT })); } else { this.mg.add((byte)42); this.mg.add((byte)-76, this.me.field(regField[paramInt], Type.INT)); }  return i; } private int setReg() { if (this.preSetRegStackPos == 0) throw new RuntimeException("didn't do preSetReg"); 
/* 1847 */     this.preSetRegStackPos--;
/* 1848 */     int i = this.preSetRegStack[this.preSetRegStackPos];
/* 1849 */     int j = this.mg.size();
/* 1850 */     if (doLocal(i)) {
/* 1851 */       this.mg.add((byte)54, getLocalForReg(i));
/* 1852 */       this.regLocalWritten[i] = true;
/* 1853 */     } else if (i >= 32 && i <= 63 && this.singleFloat) {
/* 1854 */       this.mg.add((byte)-72, Type.FLOAT_OBJECT.method("intBitsToFloat", Type.FLOAT, new Type[] { Type.INT }));
/* 1855 */       this.mg.add((byte)-75, this.me.field(regField[i], Type.FLOAT));
/*      */     } else {
/* 1857 */       this.mg.add((byte)-75, this.me.field(regField[i], Type.INT));
/*      */     } 
/* 1859 */     return j; }
/*      */ 
/*      */   
/* 1862 */   private int preSetPC() { return this.mg.add((byte)42); }
/*      */   
/* 1864 */   private int setPC() { return this.mg.add((byte)-75, this.me.field("pc", Type.INT)); }
/*      */ 
/*      */ 
/*      */   
/* 1868 */   private int pushFloat(int paramInt) { return pushDouble(paramInt, false); }
/*      */   private int pushDouble(int paramInt, boolean paramBoolean) throws Compiler.Exn {
/* 1870 */     if (paramInt < 32 || paramInt >= 64) throw new IllegalArgumentException("" + paramInt); 
/* 1871 */     int i = this.mg.size();
/* 1872 */     if (paramBoolean) {
/* 1873 */       if (this.singleFloat) throw new Compiler.Exn("Double operations not supported when singleFloat is enabled"); 
/* 1874 */       if (paramInt == 63) throw new Compiler.Exn("Tried to use a double in f31"); 
/* 1875 */       pushReg(paramInt + 1);
/* 1876 */       this.mg.add((byte)-123);
/* 1877 */       this.mg.add((byte)18, 32);
/* 1878 */       this.mg.add((byte)121);
/* 1879 */       pushReg(paramInt);
/* 1880 */       this.mg.add((byte)-123);
/* 1881 */       this.mg.add((byte)18, FFFFFFFF);
/* 1882 */       this.mg.add(127);
/* 1883 */       this.mg.add((byte)-127);
/* 1884 */       this.mg.add((byte)-72, Type.DOUBLE_OBJECT.method("longBitsToDouble", Type.DOUBLE, new Type[] { Type.LONG }));
/* 1885 */     } else if (this.singleFloat) {
/* 1886 */       this.mg.add((byte)42);
/* 1887 */       this.mg.add((byte)-76, this.me.field(regField[paramInt], Type.FLOAT));
/*      */     } else {
/* 1889 */       pushReg(paramInt);
/* 1890 */       this.mg.add((byte)-72, Type.Class.instance("java.lang.Float").method("intBitsToFloat", Type.FLOAT, new Type[] { Type.INT }));
/*      */     } 
/* 1892 */     return i;
/*      */   }
/*      */   
/* 1895 */   private void preSetFloat(int paramInt) { preSetDouble(paramInt, false); }
/* 1896 */   private void preSetDouble(int paramInt) { preSetDouble(paramInt, true); }
/* 1897 */   private void preSetDouble(int paramInt, boolean paramBoolean) { preSetReg(paramInt); }
/*      */   
/* 1899 */   private int setFloat() { return setDouble(false); }
/* 1900 */   private int setDouble() { return setDouble(true); }
/*      */   private int setDouble(boolean paramBoolean) throws Compiler.Exn {
/* 1902 */     int i = this.preSetRegStack[this.preSetRegStackPos - 1];
/* 1903 */     if (i < 32 || i >= 64) throw new IllegalArgumentException("" + i); 
/* 1904 */     int j = this.mg.size();
/* 1905 */     if (paramBoolean) {
/* 1906 */       if (this.singleFloat) throw new Compiler.Exn("Double operations not supported when singleFloat is enabled"); 
/* 1907 */       if (i == 63) throw new Compiler.Exn("Tried to use a double in f31"); 
/* 1908 */       this.mg.add((byte)-72, Type.DOUBLE_OBJECT.method("doubleToLongBits", Type.LONG, new Type[] { Type.DOUBLE }));
/* 1909 */       this.mg.add((byte)92);
/* 1910 */       this.mg.add((byte)18, 32);
/* 1911 */       this.mg.add((byte)125);
/* 1912 */       this.mg.add((byte)-120);
/* 1913 */       if (preSetReg(i + 1))
/* 1914 */         this.mg.add((byte)95); 
/* 1915 */       setReg();
/* 1916 */       this.mg.add((byte)-120);
/* 1917 */       setReg();
/* 1918 */     } else if (this.singleFloat) {
/*      */       
/* 1920 */       this.preSetRegStackPos--;
/* 1921 */       this.mg.add((byte)-75, this.me.field(regField[i], Type.FLOAT));
/*      */     } else {
/*      */       
/* 1924 */       this.mg.add((byte)-72, Type.FLOAT_OBJECT.method("floatToRawIntBits", Type.INT, new Type[] { Type.FLOAT }));
/* 1925 */       setReg();
/*      */     } 
/* 1927 */     return j;
/*      */   }
/*      */   
/* 1930 */   private void pushTmp() throws Compiler.Exn, IOException { this.mg.add((byte)27); }
/* 1931 */   private void setTmp() throws Compiler.Exn, IOException { this.mg.add((byte)60); }
/*      */   
/*      */   private void addiu(int paramInt1, int paramInt2) throws Compiler.Exn {
/* 1934 */     if (paramInt1 != 0 && paramInt2 != 0) {
/* 1935 */       pushReg(paramInt1);
/* 1936 */       this.mg.add((byte)18, paramInt2);
/* 1937 */       this.mg.add((byte)96);
/* 1938 */     } else if (paramInt1 != 0) {
/* 1939 */       pushReg(paramInt1);
/*      */     } else {
/* 1941 */       this.mg.add((byte)18, paramInt2);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void preMemWrite1() throws Compiler.Exn, IOException {
/* 1946 */     if (this.memWriteStage != 0) throw new Error("pending preMemWrite1/2"); 
/* 1947 */     this.memWriteStage = 1;
/* 1948 */     if (this.onePage) {
/* 1949 */       this.mg.add((byte)44);
/* 1950 */     } else if (this.fastMem) {
/* 1951 */       this.mg.add((byte)25, 3);
/*      */     } else {
/* 1953 */       this.mg.add((byte)42);
/*      */     } 
/*      */   }
/*      */   private void preMemWrite2(int paramInt1, int paramInt2) throws Compiler.Exn {
/* 1957 */     addiu(paramInt1, paramInt2);
/* 1958 */     preMemWrite2();
/*      */   }
/*      */   
/* 1961 */   private void preMemWrite2() throws Compiler.Exn, IOException { preMemWrite2(false); }
/*      */   private void preMemWrite2(boolean paramBoolean) {
/* 1963 */     if (this.memWriteStage != 1) throw new Error("pending preMemWrite2 or no preMemWrite1"); 
/* 1964 */     this.memWriteStage = 2;
/*      */     
/* 1966 */     if (this.nullPointerCheck) {
/* 1967 */       this.mg.add((byte)89);
/* 1968 */       this.mg.add((byte)42);
/* 1969 */       this.mg.add((byte)95);
/*      */       
/* 1971 */       this.mg.add((byte)-74, this.me.method("nullPointerCheck", Type.VOID, new Type[] { Type.INT }));
/*      */     } 
/*      */     
/* 1974 */     if (this.onePage) {
/* 1975 */       this.mg.add((byte)5);
/* 1976 */       this.mg.add((byte)124);
/* 1977 */     } else if (this.fastMem) {
/* 1978 */       if (!paramBoolean)
/* 1979 */         this.mg.add((byte)90); 
/* 1980 */       this.mg.add((byte)18, this.pageShift);
/* 1981 */       this.mg.add((byte)124);
/* 1982 */       this.mg.add((byte)50);
/* 1983 */       if (paramBoolean) {
/* 1984 */         pushTmp();
/*      */       } else {
/* 1986 */         this.mg.add((byte)95);
/* 1987 */       }  this.mg.add((byte)5);
/* 1988 */       this.mg.add((byte)124);
/* 1989 */       this.mg.add((byte)18, (this.pageSize >> 2) - 1);
/* 1990 */       this.mg.add((byte)126);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void memWrite() throws Compiler.Exn, IOException {
/* 1996 */     if (this.memWriteStage != 2) throw new Error("didn't do preMemWrite1 or preMemWrite2"); 
/* 1997 */     this.memWriteStage = 0;
/*      */     
/* 1999 */     if (this.onePage) {
/* 2000 */       this.mg.add((byte)79);
/* 2001 */     } else if (this.fastMem) {
/* 2002 */       this.mg.add((byte)79);
/*      */     } else {
/*      */       
/* 2005 */       this.mg.add((byte)-74, this.me.method("unsafeMemWrite", Type.VOID, new Type[] { Type.INT, Type.INT }));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void memRead(int paramInt1, int paramInt2) throws Compiler.Exn {
/* 2012 */     preMemRead();
/* 2013 */     addiu(paramInt1, paramInt2);
/* 2014 */     memRead();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2020 */   private void preMemRead() throws Compiler.Exn, IOException { preMemRead(false); }
/*      */   private void preMemRead(boolean paramBoolean) {
/* 2022 */     if (this.didPreMemRead) throw new Error("pending preMemRead"); 
/* 2023 */     this.didPreMemRead = true;
/* 2024 */     this.preMemReadDoPreWrite = paramBoolean;
/* 2025 */     if (this.onePage) {
/* 2026 */       this.mg.add((byte)44);
/* 2027 */     } else if (this.fastMem) {
/* 2028 */       this.mg.add((byte)25, paramBoolean ? 3 : 2);
/*      */     } else {
/* 2030 */       this.mg.add((byte)42);
/*      */     } 
/*      */   }
/*      */   
/* 2034 */   private void memRead() throws Compiler.Exn, IOException { memRead(false); }
/*      */   
/*      */   private void memRead(boolean paramBoolean) {
/* 2037 */     if (!this.didPreMemRead) throw new Error("didn't do preMemRead"); 
/* 2038 */     this.didPreMemRead = false;
/* 2039 */     if (this.preMemReadDoPreWrite) {
/* 2040 */       this.memWriteStage = 2;
/*      */     }
/* 2042 */     if (this.nullPointerCheck) {
/* 2043 */       this.mg.add((byte)89);
/* 2044 */       this.mg.add((byte)42);
/* 2045 */       this.mg.add((byte)95);
/* 2046 */       this.mg.add((byte)-74, this.me.method("nullPointerCheck", Type.VOID, new Type[] { Type.INT }));
/*      */     } 
/*      */     
/* 2049 */     if (this.onePage) {
/* 2050 */       this.mg.add((byte)5);
/* 2051 */       this.mg.add((byte)124);
/* 2052 */       if (this.preMemReadDoPreWrite)
/* 2053 */         this.mg.add((byte)92); 
/* 2054 */       this.mg.add((byte)46);
/* 2055 */     } else if (this.fastMem) {
/* 2056 */       if (!paramBoolean)
/* 2057 */         this.mg.add((byte)90); 
/* 2058 */       this.mg.add((byte)18, this.pageShift);
/* 2059 */       this.mg.add((byte)124);
/* 2060 */       this.mg.add((byte)50);
/* 2061 */       if (paramBoolean) {
/* 2062 */         pushTmp();
/*      */       } else {
/* 2064 */         this.mg.add((byte)95);
/* 2065 */       }  this.mg.add((byte)5);
/* 2066 */       this.mg.add((byte)124);
/* 2067 */       this.mg.add((byte)18, (this.pageSize >> 2) - 1);
/* 2068 */       this.mg.add((byte)126);
/* 2069 */       if (this.preMemReadDoPreWrite)
/* 2070 */         this.mg.add((byte)92); 
/* 2071 */       this.mg.add((byte)46);
/*      */     } else {
/*      */       
/* 2074 */       if (this.preMemReadDoPreWrite) {
/* 2075 */         this.mg.add((byte)92);
/*      */       }
/* 2077 */       this.mg.add((byte)-74, this.me.method("unsafeMemRead", Type.INT, new Type[] { Type.INT }));
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\ibex\nestedvm\ClassFileCompiler.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.0.4
 */