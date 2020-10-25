/*    */ package net.minecraft.server;
/*    */ 
/*    */ public class TileEntitySign
/*    */   extends TileEntity {
/*  5 */   public String[] lines = { "", "", "", "" };
/*  6 */   public int b = -1;
/*    */   
/*    */   private boolean isEditable = true;
/*    */ 
/*    */   
/*    */   public void b(NBTTagCompound nbttagcompound) {
/* 12 */     super.b(nbttagcompound);
/* 13 */     nbttagcompound.setString("Text1", this.lines[0]);
/* 14 */     nbttagcompound.setString("Text2", this.lines[1]);
/* 15 */     nbttagcompound.setString("Text3", this.lines[2]);
/* 16 */     nbttagcompound.setString("Text4", this.lines[3]);
/*    */   }
/*    */   
/*    */   public void a(NBTTagCompound nbttagcompound) {
/* 20 */     this.isEditable = false;
/* 21 */     super.a(nbttagcompound);
/*    */     
/* 23 */     for (int i = 0; i < 4; i++) {
/* 24 */       this.lines[i] = nbttagcompound.getString("Text" + (i + 1));
/* 25 */       if (this.lines[i].length() > 15) {
/* 26 */         this.lines[i] = this.lines[i].substring(0, 15);
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public Packet f() {
/* 32 */     String[] astring = new String[4];
/*    */     
/* 34 */     for (int i = 0; i < 4; i++) {
/* 35 */       astring[i] = this.lines[i];
/*    */ 
/*    */       
/* 38 */       if (this.lines[i].length() > 15) {
/* 39 */         astring[i] = this.lines[i].substring(0, 15);
/*    */       }
/*    */     } 
/*    */ 
/*    */     
/* 44 */     return new Packet130UpdateSign(this.x, this.y, this.z, astring);
/*    */   }
/*    */ 
/*    */   
/* 48 */   public boolean a() { return this.isEditable; }
/*    */ 
/*    */ 
/*    */   
/* 52 */   public void a(boolean flag) { this.isEditable = flag; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\TileEntitySign.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */