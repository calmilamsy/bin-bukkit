/*     */ package org.bukkit.craftbukkit.inventory;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import net.minecraft.server.IInventory;
/*     */ import net.minecraft.server.ItemStack;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ public class CraftInventory
/*     */   implements Inventory {
/*     */   protected IInventory inventory;
/*     */   
/*  14 */   public CraftInventory(IInventory inventory) { this.inventory = inventory; }
/*     */ 
/*     */ 
/*     */   
/*  18 */   public IInventory getInventory() { return this.inventory; }
/*     */ 
/*     */ 
/*     */   
/*  22 */   public int getSize() { return getInventory().getSize(); }
/*     */ 
/*     */ 
/*     */   
/*  26 */   public String getName() { return getInventory().getName(); }
/*     */ 
/*     */ 
/*     */   
/*  30 */   public ItemStack getItem(int index) { return new CraftItemStack(getInventory().getItem(index)); }
/*     */ 
/*     */   
/*     */   public ItemStack[] getContents() {
/*  34 */     ItemStack[] items = new ItemStack[getSize()];
/*  35 */     ItemStack[] mcItems = getInventory().getContents();
/*     */     
/*  37 */     for (int i = 0; i < mcItems.length; i++) {
/*  38 */       items[i] = (mcItems[i] == null) ? null : new CraftItemStack(mcItems[i]);
/*     */     }
/*     */     
/*  41 */     return items;
/*     */   }
/*     */   
/*     */   public void setContents(ItemStack[] items) {
/*  45 */     if (getInventory().getContents().length != items.length) {
/*  46 */       throw new IllegalArgumentException("Invalid inventory size; expected " + getInventory().getContents().length);
/*     */     }
/*     */     
/*  49 */     ItemStack[] mcItems = getInventory().getContents();
/*     */     
/*  51 */     for (int i = 0; i < items.length; i++) {
/*  52 */       ItemStack item = items[i];
/*  53 */       if (item == null || item.getTypeId() <= 0) {
/*  54 */         mcItems[i] = null;
/*     */       } else {
/*  56 */         mcItems[i] = new ItemStack(item.getTypeId(), item.getAmount(), item.getDurability());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*  62 */   public void setItem(int index, ItemStack item) { getInventory().setItem(index, (item == null) ? null : new ItemStack(item.getTypeId(), item.getAmount(), item.getDurability())); }
/*     */ 
/*     */   
/*     */   public boolean contains(int materialId) {
/*  66 */     for (ItemStack item : getContents()) {
/*  67 */       if (item != null && item.getTypeId() == materialId) {
/*  68 */         return true;
/*     */       }
/*     */     } 
/*  71 */     return false;
/*     */   }
/*     */ 
/*     */   
/*  75 */   public boolean contains(Material material) { return contains(material.getId()); }
/*     */ 
/*     */   
/*     */   public boolean contains(ItemStack item) {
/*  79 */     if (item == null) {
/*  80 */       return false;
/*     */     }
/*  82 */     for (ItemStack i : getContents()) {
/*  83 */       if (item.equals(i)) {
/*  84 */         return true;
/*     */       }
/*     */     } 
/*  87 */     return false;
/*     */   }
/*     */   
/*     */   public boolean contains(int materialId, int amount) {
/*  91 */     int amt = 0;
/*  92 */     for (ItemStack item : getContents()) {
/*  93 */       if (item != null && item.getTypeId() == materialId) {
/*  94 */         amt += item.getAmount();
/*     */       }
/*     */     } 
/*  97 */     return (amt >= amount);
/*     */   }
/*     */ 
/*     */   
/* 101 */   public boolean contains(Material material, int amount) { return contains(material.getId(), amount); }
/*     */ 
/*     */   
/*     */   public boolean contains(ItemStack item, int amount) {
/* 105 */     if (item == null) {
/* 106 */       return false;
/*     */     }
/* 108 */     int amt = 0;
/* 109 */     for (ItemStack i : getContents()) {
/* 110 */       if (item.equals(i)) {
/* 111 */         amt += item.getAmount();
/*     */       }
/*     */     } 
/* 114 */     return (amt >= amount);
/*     */   }
/*     */   
/*     */   public HashMap<Integer, ItemStack> all(int materialId) {
/* 118 */     HashMap<Integer, ItemStack> slots = new HashMap<Integer, ItemStack>();
/*     */     
/* 120 */     ItemStack[] inventory = getContents();
/* 121 */     for (int i = 0; i < inventory.length; i++) {
/* 122 */       ItemStack item = inventory[i];
/* 123 */       if (item != null && item.getTypeId() == materialId) {
/* 124 */         slots.put(Integer.valueOf(i), item);
/*     */       }
/*     */     } 
/* 127 */     return slots;
/*     */   }
/*     */ 
/*     */   
/* 131 */   public HashMap<Integer, ItemStack> all(Material material) { return all(material.getId()); }
/*     */ 
/*     */   
/*     */   public HashMap<Integer, ItemStack> all(ItemStack item) {
/* 135 */     HashMap<Integer, ItemStack> slots = new HashMap<Integer, ItemStack>();
/* 136 */     if (item != null) {
/* 137 */       ItemStack[] inventory = getContents();
/* 138 */       for (int i = 0; i < inventory.length; i++) {
/* 139 */         if (item.equals(inventory[i])) {
/* 140 */           slots.put(Integer.valueOf(i), inventory[i]);
/*     */         }
/*     */       } 
/*     */     } 
/* 144 */     return slots;
/*     */   }
/*     */   
/*     */   public int first(int materialId) {
/* 148 */     ItemStack[] inventory = getContents();
/* 149 */     for (int i = 0; i < inventory.length; i++) {
/* 150 */       ItemStack item = inventory[i];
/* 151 */       if (item != null && item.getTypeId() == materialId) {
/* 152 */         return i;
/*     */       }
/*     */     } 
/* 155 */     return -1;
/*     */   }
/*     */ 
/*     */   
/* 159 */   public int first(Material material) { return first(material.getId()); }
/*     */ 
/*     */   
/*     */   public int first(ItemStack item) {
/* 163 */     if (item == null) {
/* 164 */       return -1;
/*     */     }
/* 166 */     ItemStack[] inventory = getContents();
/* 167 */     for (int i = 0; i < inventory.length; i++) {
/* 168 */       if (item.equals(inventory[i])) {
/* 169 */         return i;
/*     */       }
/*     */     } 
/* 172 */     return -1;
/*     */   }
/*     */   
/*     */   public int firstEmpty() {
/* 176 */     ItemStack[] inventory = getContents();
/* 177 */     for (int i = 0; i < inventory.length; i++) {
/* 178 */       if (inventory[i] == null) {
/* 179 */         return i;
/*     */       }
/*     */     } 
/* 182 */     return -1;
/*     */   }
/*     */   
/*     */   public int firstPartial(int materialId) {
/* 186 */     ItemStack[] inventory = getContents();
/* 187 */     for (int i = 0; i < inventory.length; i++) {
/* 188 */       ItemStack item = inventory[i];
/* 189 */       if (item != null && item.getTypeId() == materialId && item.getAmount() < item.getMaxStackSize()) {
/* 190 */         return i;
/*     */       }
/*     */     } 
/* 193 */     return -1;
/*     */   }
/*     */ 
/*     */   
/* 197 */   public int firstPartial(Material material) { return firstPartial(material.getId()); }
/*     */ 
/*     */   
/*     */   public int firstPartial(ItemStack item) {
/* 201 */     ItemStack[] inventory = getContents();
/* 202 */     if (item == null) {
/* 203 */       return -1;
/*     */     }
/* 205 */     for (int i = 0; i < inventory.length; i++) {
/* 206 */       ItemStack cItem = inventory[i];
/* 207 */       if (cItem != null && cItem.getTypeId() == item.getTypeId() && cItem.getAmount() < cItem.getMaxStackSize() && cItem.getDurability() == item.getDurability()) {
/* 208 */         return i;
/*     */       }
/*     */     } 
/* 211 */     return -1;
/*     */   }
/*     */   
/*     */   public HashMap<Integer, ItemStack> addItem(ItemStack... items) {
/* 215 */     HashMap<Integer, ItemStack> leftover = new HashMap<Integer, ItemStack>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 223 */     for (int i = 0; i < items.length; i++) {
/* 224 */       ItemStack item = items[i];
/*     */       
/*     */       while (true) {
/* 227 */         int firstPartial = firstPartial(item);
/*     */ 
/*     */         
/* 230 */         if (firstPartial == -1) {
/*     */           
/* 232 */           int firstFree = firstEmpty();
/*     */           
/* 234 */           if (firstFree == -1) {
/*     */             
/* 236 */             leftover.put(Integer.valueOf(i), item);
/*     */             
/*     */             break;
/*     */           } 
/* 240 */           if (item.getAmount() > getMaxItemStack()) {
/* 241 */             setItem(firstFree, new CraftItemStack(item.getTypeId(), getMaxItemStack(), item.getDurability()));
/* 242 */             item.setAmount(item.getAmount() - getMaxItemStack());
/*     */             continue;
/*     */           } 
/* 245 */           setItem(firstFree, item);
/*     */ 
/*     */           
/*     */           break;
/*     */         } 
/*     */         
/* 251 */         ItemStack partialItem = getItem(firstPartial);
/*     */         
/* 253 */         int amount = item.getAmount();
/* 254 */         int partialAmount = partialItem.getAmount();
/* 255 */         int maxAmount = partialItem.getMaxStackSize();
/*     */ 
/*     */         
/* 258 */         if (amount + partialAmount <= maxAmount) {
/* 259 */           partialItem.setAmount(amount + partialAmount);
/*     */           
/*     */           break;
/*     */         } 
/*     */         
/* 264 */         partialItem.setAmount(maxAmount);
/* 265 */         item.setAmount(amount + partialAmount - maxAmount);
/*     */       } 
/*     */     } 
/*     */     
/* 269 */     return leftover;
/*     */   }
/*     */   
/*     */   public HashMap<Integer, ItemStack> removeItem(ItemStack... items) {
/* 273 */     HashMap<Integer, ItemStack> leftover = new HashMap<Integer, ItemStack>();
/*     */ 
/*     */ 
/*     */     
/* 277 */     for (int i = 0; i < items.length; i++) {
/* 278 */       ItemStack item = items[i];
/* 279 */       int toDelete = item.getAmount();
/*     */       
/*     */       do {
/* 282 */         int first = first(item.getType());
/*     */ 
/*     */         
/* 285 */         if (first == -1) {
/* 286 */           item.setAmount(toDelete);
/* 287 */           leftover.put(Integer.valueOf(i), item);
/*     */           break;
/*     */         } 
/* 290 */         ItemStack itemStack = getItem(first);
/* 291 */         int amount = itemStack.getAmount();
/*     */         
/* 293 */         if (amount <= toDelete) {
/* 294 */           toDelete -= amount;
/*     */           
/* 296 */           clear(first);
/*     */         } else {
/*     */           
/* 299 */           itemStack.setAmount(amount - toDelete);
/* 300 */           setItem(first, itemStack);
/* 301 */           toDelete = 0;
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 306 */       while (toDelete > 0);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 311 */     return leftover;
/*     */   }
/*     */ 
/*     */   
/* 315 */   private int getMaxItemStack() { return getInventory().getMaxStackSize(); }
/*     */ 
/*     */   
/*     */   public void remove(int materialId) {
/* 319 */     ItemStack[] items = getContents();
/* 320 */     for (int i = 0; i < items.length; i++) {
/* 321 */       if (items[i] != null && items[i].getTypeId() == materialId) {
/* 322 */         clear(i);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 328 */   public void remove(Material material) { remove(material.getId()); }
/*     */ 
/*     */   
/*     */   public void remove(ItemStack item) {
/* 332 */     ItemStack[] items = getContents();
/* 333 */     for (int i = 0; i < items.length; i++) {
/* 334 */       if (items[i] != null && items[i].equals(item)) {
/* 335 */         clear(i);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 341 */   public void clear(int index) { setItem(index, null); }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 345 */     for (int i = 0; i < getSize(); i++)
/* 346 */       clear(i); 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\bukkit\craftbukkit\inventory\CraftInventory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */