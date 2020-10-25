/*    */ package org.bukkit.fillr;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.InputStreamReader;
/*    */ import java.net.URL;
/*    */ import java.net.URLConnection;
/*    */ import org.json.simple.JSONObject;
/*    */ import org.json.simple.parser.JSONParser;
/*    */ import org.json.simple.parser.ParseException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FillReader
/*    */ {
/*    */   private static final String BASE_URL = "http://taylorkelly.me/pnfo.php";
/*    */   private String currVersion;
/*    */   private String file;
/*    */   private String name;
/*    */   private String notes;
/*    */   private boolean stable;
/*    */   
/*    */   public FillReader(String name) {
/*    */     try {
/* 26 */       String result = "";
/*    */       
/*    */       try {
/* 29 */         URL url = new URL("http://taylorkelly.me/pnfo.php?name=" + name);
/*    */         
/* 31 */         System.out.println("http://taylorkelly.me/pnfo.php?name=" + name);
/* 32 */         URLConnection conn = url.openConnection();
/* 33 */         StringBuilder buf = new StringBuilder();
/* 34 */         BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
/*    */         
/*    */         String line;
/* 37 */         while ((line = rd.readLine()) != null) {
/* 38 */           buf.append(line);
/*    */         }
/* 40 */         result = buf.toString();
/* 41 */         rd.close();
/* 42 */         JSONParser parser = new JSONParser();
/*    */ 
/*    */         
/* 45 */         Object obj = parser.parse(result);
/* 46 */         JSONObject jsonObj = (JSONObject)obj;
/*    */         
/* 48 */         this.currVersion = (String)jsonObj.get("plugin_version");
/* 49 */         this.name = (String)jsonObj.get("plugin_name");
/* 50 */         this.file = (String)jsonObj.get("plugin_file");
/* 51 */         this.stable = ((Boolean)jsonObj.get("plugin_stable")).booleanValue();
/* 52 */         this.notes = (String)jsonObj.get("plugin_notes");
/* 53 */       } catch (ParseException e) {
/* 54 */         e.printStackTrace();
/*    */       } 
/* 56 */     } catch (Exception e) {
/* 57 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/* 62 */   public String getCurrVersion() { return this.currVersion; }
/*    */ 
/*    */ 
/*    */   
/* 66 */   public String getFile() { return this.file; }
/*    */ 
/*    */ 
/*    */   
/* 70 */   public String getName() { return this.name; }
/*    */ 
/*    */ 
/*    */   
/* 74 */   public String getNotes() { return this.notes; }
/*    */ 
/*    */ 
/*    */   
/* 78 */   public void setStable(boolean stable) { this.stable = stable; }
/*    */ 
/*    */ 
/*    */   
/* 82 */   public boolean isStable() { return this.stable; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\fillr\FillReader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */