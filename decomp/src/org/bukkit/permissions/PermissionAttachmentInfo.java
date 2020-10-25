/*    */ package org.bukkit.permissions;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PermissionAttachmentInfo
/*    */ {
/*    */   private final Permissible permissible;
/*    */   private final String permission;
/*    */   private final PermissionAttachment attachment;
/*    */   private final boolean value;
/*    */   
/*    */   public PermissionAttachmentInfo(Permissible permissible, String permission, PermissionAttachment attachment, boolean value) {
/* 14 */     if (permissible == null)
/* 15 */       throw new IllegalArgumentException("Permissible may not be null"); 
/* 16 */     if (permission == null) {
/* 17 */       throw new IllegalArgumentException("Permissions may not be null");
/*    */     }
/*    */     
/* 20 */     this.permissible = permissible;
/* 21 */     this.permission = permission;
/* 22 */     this.attachment = attachment;
/* 23 */     this.value = value;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 32 */   public Permissible getPermissible() { return this.permissible; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 41 */   public String getPermission() { return this.permission; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 51 */   public PermissionAttachment getAttachment() { return this.attachment; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 60 */   public boolean getValue() { return this.value; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\permissions\PermissionAttachmentInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */