/*     */ package com.avaje.ebean.enhance.asm;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class Item
/*     */ {
/*     */   int index;
/*     */   int type;
/*     */   int intVal;
/*     */   long longVal;
/*     */   String strVal1;
/*     */   String strVal2;
/*     */   String strVal3;
/*     */   int hashCode;
/*     */   Item next;
/*     */   
/*     */   Item() {}
/*     */   
/* 116 */   Item(int index) { this.index = index; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Item(int index, Item i) {
/* 126 */     this.index = index;
/* 127 */     this.type = i.type;
/* 128 */     this.intVal = i.intVal;
/* 129 */     this.longVal = i.longVal;
/* 130 */     this.strVal1 = i.strVal1;
/* 131 */     this.strVal2 = i.strVal2;
/* 132 */     this.strVal3 = i.strVal3;
/* 133 */     this.hashCode = i.hashCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void set(int intVal) {
/* 142 */     this.type = 3;
/* 143 */     this.intVal = intVal;
/* 144 */     this.hashCode = 0x7FFFFFFF & this.type + intVal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void set(long longVal) {
/* 153 */     this.type = 5;
/* 154 */     this.longVal = longVal;
/* 155 */     this.hashCode = 0x7FFFFFFF & this.type + (int)longVal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void set(float floatVal) {
/* 164 */     this.type = 4;
/* 165 */     this.intVal = Float.floatToRawIntBits(floatVal);
/* 166 */     this.hashCode = 0x7FFFFFFF & this.type + (int)floatVal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void set(double doubleVal) {
/* 175 */     this.type = 6;
/* 176 */     this.longVal = Double.doubleToRawLongBits(doubleVal);
/* 177 */     this.hashCode = 0x7FFFFFFF & this.type + (int)doubleVal;
/*     */   }
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
/*     */   void set(int type, String strVal1, String strVal2, String strVal3) {
/* 194 */     this.type = type;
/* 195 */     this.strVal1 = strVal1;
/* 196 */     this.strVal2 = strVal2;
/* 197 */     this.strVal3 = strVal3;
/* 198 */     switch (type) {
/*     */       case 1:
/*     */       case 7:
/*     */       case 8:
/*     */       case 13:
/* 203 */         this.hashCode = 0x7FFFFFFF & type + strVal1.hashCode();
/*     */         return;
/*     */       case 12:
/* 206 */         this.hashCode = 0x7FFFFFFF & type + strVal1.hashCode() * strVal2.hashCode();
/*     */         return;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 213 */     this.hashCode = 0x7FFFFFFF & type + strVal1.hashCode() * strVal2.hashCode() * strVal3.hashCode();
/*     */   }
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
/*     */   boolean isEqualTo(Item i) {
/* 226 */     if (i.type == this.type) {
/* 227 */       switch (this.type) {
/*     */         case 3:
/*     */         case 4:
/* 230 */           return (i.intVal == this.intVal);
/*     */         case 5:
/*     */         case 6:
/*     */         case 15:
/* 234 */           return (i.longVal == this.longVal);
/*     */         case 1:
/*     */         case 7:
/*     */         case 8:
/*     */         case 13:
/* 239 */           return i.strVal1.equals(this.strVal1);
/*     */         case 14:
/* 241 */           return (i.intVal == this.intVal && i.strVal1.equals(this.strVal1));
/*     */         case 12:
/* 243 */           return (i.strVal1.equals(this.strVal1) && i.strVal2.equals(this.strVal2));
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 249 */       return (i.strVal1.equals(this.strVal1) && i.strVal2.equals(this.strVal2) && i.strVal3.equals(this.strVal3));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 254 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\avaje\ebean\enhance\asm\Item.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */