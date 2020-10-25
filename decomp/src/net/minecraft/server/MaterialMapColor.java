/*    */ package net.minecraft.server;
/*    */ 
/*    */ public class MaterialMapColor
/*    */ {
/*  5 */   public static final MaterialMapColor[] a = new MaterialMapColor[16];
/*    */   
/*  7 */   public static final MaterialMapColor b = new MaterialMapColor(false, false);
/*  8 */   public static final MaterialMapColor c = new MaterialMapColor(true, 8368696);
/*  9 */   public static final MaterialMapColor d = new MaterialMapColor(2, 16247203);
/* 10 */   public static final MaterialMapColor e = new MaterialMapColor(3, 10987431);
/* 11 */   public static final MaterialMapColor f = new MaterialMapColor(4, 16711680);
/* 12 */   public static final MaterialMapColor g = new MaterialMapColor(5, 10526975);
/* 13 */   public static final MaterialMapColor h = new MaterialMapColor(6, 10987431);
/* 14 */   public static final MaterialMapColor i = new MaterialMapColor(7, '簀');
/* 15 */   public static final MaterialMapColor j = new MaterialMapColor(8, 16777215);
/* 16 */   public static final MaterialMapColor k = new MaterialMapColor(9, 10791096);
/* 17 */   public static final MaterialMapColor l = new MaterialMapColor(10, 12020271);
/* 18 */   public static final MaterialMapColor m = new MaterialMapColor(11, 7368816);
/* 19 */   public static final MaterialMapColor n = new MaterialMapColor(12, 4210943);
/* 20 */   public static final MaterialMapColor o = new MaterialMapColor(13, 6837042);
/*    */   
/*    */   public final int p;
/*    */   public final int q;
/*    */   
/*    */   private MaterialMapColor(int paramInt1, int paramInt2) {
/* 26 */     this.q = paramInt1;
/* 27 */     this.p = paramInt2;
/* 28 */     a[paramInt1] = this;
/*    */   }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\MaterialMapColor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */