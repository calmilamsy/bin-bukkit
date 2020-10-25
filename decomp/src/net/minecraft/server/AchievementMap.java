/*    */ package net.minecraft.server;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.InputStreamReader;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class AchievementMap {
/*  9 */   public static AchievementMap a = new AchievementMap();
/*    */   
/* 11 */   private Map b = new HashMap();
/*    */   
/*    */   private AchievementMap() {
/*    */     try {
/* 15 */       BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(AchievementMap.class.getResourceAsStream("/achievement/map.txt")));
/*    */       String str;
/* 17 */       while ((str = bufferedReader.readLine()) != null) {
/* 18 */         String[] arrayOfString = str.split(",");
/* 19 */         int i = Integer.parseInt(arrayOfString[0]);
/* 20 */         this.b.put(Integer.valueOf(i), arrayOfString[1]);
/*    */       } 
/* 22 */       bufferedReader.close();
/* 23 */     } catch (Exception exception) {
/* 24 */       exception.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/* 29 */   public static String a(int paramInt) { return (String)a.b.get(Integer.valueOf(paramInt)); }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\AchievementMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */