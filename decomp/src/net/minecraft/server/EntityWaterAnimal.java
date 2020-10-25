/*    */ package net.minecraft.server;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityWaterAnimal
/*    */   extends EntityCreature
/*    */   implements IAnimal
/*    */ {
/* 22 */   public EntityWaterAnimal(World paramWorld) { super(paramWorld); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 28 */   public boolean b_() { return true; }
/*    */ 
/*    */ 
/*    */   
/* 32 */   public void b(NBTTagCompound paramNBTTagCompound) { super.b(paramNBTTagCompound); }
/*    */ 
/*    */ 
/*    */   
/* 36 */   public void a(NBTTagCompound paramNBTTagCompound) { super.a(paramNBTTagCompound); }
/*    */ 
/*    */ 
/*    */   
/* 40 */   public boolean d() { return this.world.containsEntity(this.boundingBox); }
/*    */ 
/*    */ 
/*    */   
/* 44 */   public int e() { return 120; }
/*    */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\net\minecraft\server\EntityWaterAnimal.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */