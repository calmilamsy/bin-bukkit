/*     */ package jline;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.net.JarURLConnection;
/*     */ import java.net.URL;
/*     */ import java.net.URLClassLoader;
/*     */ import java.net.URLConnection;
/*     */ import java.util.Arrays;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ import java.util.jar.JarEntry;
/*     */ import java.util.jar.JarFile;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClassNameCompletor
/*     */   extends SimpleCompletor
/*     */ {
/*  28 */   public ClassNameCompletor() throws IOException { this(null); }
/*     */ 
/*     */ 
/*     */   
/*     */   public ClassNameCompletor(SimpleCompletor.SimpleCompletorFilter filter) throws IOException {
/*  33 */     super(getClassNames(), filter);
/*  34 */     setDelimiter(".");
/*     */   }
/*     */   
/*     */   public static String[] getClassNames() throws IOException {
/*  38 */     urls = new HashSet();
/*     */     
/*  40 */     loader = ClassNameCompletor.class.getClassLoader();
/*  41 */     for (; loader != null; 
/*  42 */       loader = loader.getParent()) {
/*  43 */       if (loader instanceof URLClassLoader)
/*     */       {
/*     */ 
/*     */         
/*  47 */         urls.addAll(Arrays.asList(((URLClassLoader)loader).getURLs()));
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  53 */     Class[] systemClasses = { String.class, javax.swing.JFrame.class };
/*     */ 
/*     */ 
/*     */     
/*  57 */     for (i = 0; i < systemClasses.length; i++) {
/*  58 */       URL classURL = systemClasses[i].getResource("/" + systemClasses[i].getName().replace('.', '/') + ".class");
/*     */ 
/*     */       
/*  61 */       if (classURL != null) {
/*  62 */         URLConnection uc = classURL.openConnection();
/*     */         
/*  64 */         if (uc instanceof JarURLConnection) {
/*  65 */           urls.add(((JarURLConnection)uc).getJarFileURL());
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  70 */     Set classes = new HashSet();
/*     */     
/*  72 */     for (i = urls.iterator(); i.hasNext(); ) {
/*  73 */       URL url = (URL)i.next();
/*  74 */       File file = new File(url.getFile());
/*     */       
/*  76 */       if (file.isDirectory()) {
/*  77 */         Set files = getClassFiles(file.getAbsolutePath(), new HashSet(), file, new int[] { 200 });
/*     */         
/*  79 */         classes.addAll(files);
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/*  84 */       if (file == null || !file.isFile()) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */       
/*  89 */       JarFile jf = new JarFile(file);
/*     */       
/*  91 */       for (Enumeration e = jf.entries(); e.hasMoreElements(); ) {
/*  92 */         JarEntry entry = (JarEntry)e.nextElement();
/*     */         
/*  94 */         if (entry == null) {
/*     */           continue;
/*     */         }
/*     */         
/*  98 */         String name = entry.getName();
/*     */         
/* 100 */         if (!name.endsWith(".class")) {
/*     */           continue;
/*     */         }
/*     */ 
/*     */         
/* 105 */         classes.add(name);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 111 */     Set classNames = new TreeSet();
/*     */     
/* 113 */     for (Iterator i = classes.iterator(); i.hasNext(); ) {
/* 114 */       String name = (String)i.next();
/* 115 */       classNames.add(name.replace('/', '.').substring(0, name.length() - 6));
/*     */     } 
/*     */ 
/*     */     
/* 119 */     return (String[])classNames.toArray(new String[classNames.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Set getClassFiles(String root, Set holder, File directory, int[] maxDirectories) {
/* 125 */     maxDirectories[0] = maxDirectories[0] - 1; if (maxDirectories[0] < 0) {
/* 126 */       return holder;
/*     */     }
/*     */     
/* 129 */     File[] files = directory.listFiles();
/*     */     
/* 131 */     for (int i = 0; files != null && i < files.length; i++) {
/* 132 */       String name = files[i].getAbsolutePath();
/*     */       
/* 134 */       if (name.startsWith(root))
/*     */       {
/* 136 */         if (files[i].isDirectory()) {
/* 137 */           getClassFiles(root, holder, files[i], maxDirectories);
/* 138 */         } else if (files[i].getName().endsWith(".class")) {
/* 139 */           holder.add(files[i].getAbsolutePath().substring(root.length() + 1));
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 144 */     return holder;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\jline\ClassNameCompletor.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.0.4
 */