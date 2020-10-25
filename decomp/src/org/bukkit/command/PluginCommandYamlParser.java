/*    */ package org.bukkit.command;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PluginCommandYamlParser
/*    */ {
/*    */   public static List<Command> parse(Plugin plugin) {
/* 14 */     List<Command> pluginCmds = new ArrayList<Command>();
/* 15 */     Object object = plugin.getDescription().getCommands();
/*    */     
/* 17 */     if (object == null) {
/* 18 */       return pluginCmds;
/*    */     }
/*    */     
/* 21 */     Map<String, Map<String, Object>> map = (Map)object;
/*    */     
/* 23 */     if (map != null) {
/* 24 */       for (Map.Entry<String, Map<String, Object>> entry : map.entrySet()) {
/* 25 */         Command newCmd = new PluginCommand((String)entry.getKey(), plugin);
/* 26 */         Object description = ((Map)entry.getValue()).get("description");
/* 27 */         Object usage = ((Map)entry.getValue()).get("usage");
/* 28 */         Object aliases = ((Map)entry.getValue()).get("aliases");
/* 29 */         Object permission = ((Map)entry.getValue()).get("permission");
/*    */         
/* 31 */         if (description != null) {
/* 32 */           newCmd.setDescription(description.toString());
/*    */         }
/*    */         
/* 35 */         if (usage != null) {
/* 36 */           newCmd.setUsage(usage.toString());
/*    */         }
/*    */         
/* 39 */         if (aliases != null) {
/* 40 */           List<String> aliasList = new ArrayList<String>();
/*    */           
/* 42 */           if (aliases instanceof List) {
/* 43 */             for (Object o : (List)aliases) {
/* 44 */               aliasList.add(o.toString());
/*    */             }
/*    */           } else {
/* 47 */             aliasList.add(aliases.toString());
/*    */           } 
/*    */           
/* 50 */           newCmd.setAliases(aliasList);
/*    */         } 
/*    */         
/* 53 */         if (permission != null) {
/* 54 */           newCmd.setPermission(permission.toString());
/*    */         }
/*    */         
/* 57 */         pluginCmds.add(newCmd);
/*    */       } 
/*    */     }
/* 60 */     return pluginCmds;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\command\PluginCommandYamlParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */