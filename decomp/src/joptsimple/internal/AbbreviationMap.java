/*     */ package joptsimple.internal;
/*     */ 
/*     */ import java.util.Map;
/*     */ import java.util.TreeMap;
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
/*     */ public class AbbreviationMap<V>
/*     */   extends Object
/*     */ {
/*     */   private String key;
/*     */   private V value;
/*  67 */   private final Map<Character, AbbreviationMap<V>> children = new TreeMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int keysBeyond;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  79 */   public boolean contains(String aKey) { return (get(aKey) != null); }
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
/*     */   public V get(String aKey) {
/*  92 */     char[] chars = charsOf(aKey);
/*     */     
/*  94 */     AbbreviationMap<V> child = this;
/*  95 */     for (char each : chars) {
/*  96 */       child = (AbbreviationMap)child.children.get(Character.valueOf(each));
/*  97 */       if (child == null) {
/*  98 */         return null;
/*     */       }
/*     */     } 
/* 101 */     return (V)child.value;
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
/*     */   public void put(String aKey, V newValue) {
/* 114 */     if (newValue == null)
/* 115 */       throw new NullPointerException(); 
/* 116 */     if (aKey.length() == 0) {
/* 117 */       throw new IllegalArgumentException();
/*     */     }
/* 119 */     char[] chars = charsOf(aKey);
/* 120 */     add(chars, newValue, 0, chars.length);
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
/*     */   public void putAll(Iterable<String> keys, V newValue) {
/* 133 */     for (String each : keys)
/* 134 */       put(each, newValue); 
/*     */   }
/*     */   
/*     */   private boolean add(char[] chars, V newValue, int offset, int length) {
/* 138 */     if (offset == length) {
/* 139 */       this.value = newValue;
/* 140 */       boolean wasAlreadyAKey = (this.key != null);
/* 141 */       this.key = new String(chars);
/* 142 */       return !wasAlreadyAKey;
/*     */     } 
/*     */     
/* 145 */     char nextChar = chars[offset];
/* 146 */     AbbreviationMap<V> child = (AbbreviationMap)this.children.get(Character.valueOf(nextChar));
/* 147 */     if (child == null) {
/* 148 */       child = new AbbreviationMap<V>();
/* 149 */       this.children.put(Character.valueOf(nextChar), child);
/*     */     } 
/*     */     
/* 152 */     boolean newKeyAdded = child.add(chars, newValue, offset + 1, length);
/*     */     
/* 154 */     if (newKeyAdded) {
/* 155 */       this.keysBeyond++;
/*     */     }
/* 157 */     if (this.key == null) {
/* 158 */       this.value = (this.keysBeyond > 1) ? null : newValue;
/*     */     }
/* 160 */     return newKeyAdded;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove(String aKey) {
/* 171 */     if (aKey.length() == 0) {
/* 172 */       throw new IllegalArgumentException();
/*     */     }
/* 174 */     char[] keyChars = charsOf(aKey);
/* 175 */     remove(keyChars, 0, keyChars.length);
/*     */   }
/*     */   
/*     */   private boolean remove(char[] aKey, int offset, int length) {
/* 179 */     if (offset == length) {
/* 180 */       return removeAtEndOfKey();
/*     */     }
/* 182 */     char nextChar = aKey[offset];
/* 183 */     AbbreviationMap<V> child = (AbbreviationMap)this.children.get(Character.valueOf(nextChar));
/* 184 */     if (child == null || !child.remove(aKey, offset + 1, length)) {
/* 185 */       return false;
/*     */     }
/* 187 */     this.keysBeyond--;
/* 188 */     if (child.keysBeyond == 0)
/* 189 */       this.children.remove(Character.valueOf(nextChar)); 
/* 190 */     if (this.keysBeyond == 1 && this.key == null) {
/* 191 */       setValueToThatOfOnlyChild();
/*     */     }
/* 193 */     return true;
/*     */   }
/*     */   
/*     */   private void setValueToThatOfOnlyChild() {
/* 197 */     Map.Entry<Character, AbbreviationMap<V>> entry = (Map.Entry)this.children.entrySet().iterator().next();
/* 198 */     AbbreviationMap<V> onlyChild = (AbbreviationMap)entry.getValue();
/* 199 */     this.value = onlyChild.value;
/*     */   }
/*     */   
/*     */   private boolean removeAtEndOfKey() {
/* 203 */     if (this.key == null) {
/* 204 */       return false;
/*     */     }
/* 206 */     this.key = null;
/* 207 */     if (this.keysBeyond == 1) {
/* 208 */       setValueToThatOfOnlyChild();
/*     */     } else {
/* 210 */       this.value = null;
/*     */     } 
/* 212 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, V> toJavaUtilMap() {
/* 221 */     Map<String, V> mappings = new TreeMap<String, V>();
/* 222 */     addToMappings(mappings);
/* 223 */     return mappings;
/*     */   }
/*     */   
/*     */   private void addToMappings(Map<String, V> mappings) {
/* 227 */     if (this.key != null) {
/* 228 */       mappings.put(this.key, this.value);
/*     */     }
/* 230 */     for (AbbreviationMap<V> each : this.children.values())
/* 231 */       each.addToMappings(mappings); 
/*     */   }
/*     */   
/*     */   private static char[] charsOf(String aKey) {
/* 235 */     char[] chars = new char[aKey.length()];
/* 236 */     aKey.getChars(0, aKey.length(), chars, 0);
/* 237 */     return chars;
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\joptsimple\internal\AbbreviationMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */