/*     */ package javax.persistence;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.URL;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.persistence.spi.PersistenceProvider;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Persistence
/*     */ {
/*  48 */   public static String PERSISTENCE_PROVIDER = "javax.persistence.spi.PeristenceProvider";
/*  49 */   protected static final Set<PersistenceProvider> providers = new HashSet();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  60 */   public static EntityManagerFactory createEntityManagerFactory(String persistenceUnitName) { return createEntityManagerFactory(persistenceUnitName, null); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EntityManagerFactory createEntityManagerFactory(String persistenceUnitName, Map properties) {
/*  76 */     EntityManagerFactory emf = null;
/*  77 */     if (providers.size() == 0) {
/*     */       try {
/*  79 */         findAllProviders();
/*  80 */       } catch (IOException exc) {}
/*     */     }
/*  82 */     for (PersistenceProvider provider : providers) {
/*  83 */       emf = provider.createEntityManagerFactory(persistenceUnitName, properties);
/*  84 */       if (emf != null) {
/*     */         break;
/*     */       }
/*     */     } 
/*  88 */     if (emf == null) {
/*  89 */       throw new PersistenceException("No Persistence provider for EntityManager named " + persistenceUnitName);
/*     */     }
/*  91 */     return emf;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void findAllProviders() {
/*  97 */     loader = Thread.currentThread().getContextClassLoader();
/*  98 */     Enumeration<URL> resources = loader.getResources("META-INF/services/" + PersistenceProvider.class.getName());
/*     */     
/* 100 */     Set<String> names = new HashSet<String>();
/* 101 */     while (resources.hasMoreElements()) {
/* 102 */       URL url = (URL)resources.nextElement();
/* 103 */       is = url.openStream();
/*     */       try {
/* 105 */         names.addAll(providerNamesFromReader(new BufferedReader(new InputStreamReader(is))));
/*     */       } finally {
/* 107 */         is.close();
/*     */       } 
/*     */     } 
/* 110 */     for (String s : names) {
/*     */       
/* 112 */       try { providers.add((PersistenceProvider)loader.loadClass(s).newInstance()); }
/* 113 */       catch (ClassNotFoundException exc) {  }
/* 114 */       catch (InstantiationException exc) {  }
/* 115 */       catch (IllegalAccessException exc) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 120 */   private static final Pattern nonCommentPattern = Pattern.compile("^([^#]+)");
/*     */   
/*     */   private static Set<String> providerNamesFromReader(BufferedReader reader) throws IOException {
/* 123 */     Set<String> names = new HashSet<String>();
/*     */     String line;
/* 125 */     while ((line = reader.readLine()) != null) {
/* 126 */       line = line.trim();
/* 127 */       Matcher m = nonCommentPattern.matcher(line);
/* 128 */       if (m.find()) {
/* 129 */         names.add(m.group().trim());
/*     */       }
/*     */     } 
/* 132 */     return names;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\javax\persistence\Persistence.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */