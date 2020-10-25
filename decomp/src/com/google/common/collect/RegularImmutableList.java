/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import javax.annotation.Nullable;
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
/*     */ @GwtCompatible(serializable = true, emulated = true)
/*     */ class RegularImmutableList<E>
/*     */   extends ImmutableList<E>
/*     */ {
/*     */   private final int offset;
/*     */   private final int size;
/*     */   private final Object[] array;
/*     */   
/*     */   RegularImmutableList(Object[] array, int offset, int size) {
/*  41 */     this.offset = offset;
/*  42 */     this.size = size;
/*  43 */     this.array = array;
/*     */   }
/*     */ 
/*     */   
/*  47 */   RegularImmutableList(Object[] array) { this(array, 0, array.length); }
/*     */ 
/*     */ 
/*     */   
/*  51 */   public int size() { return this.size; }
/*     */ 
/*     */ 
/*     */   
/*  55 */   public boolean isEmpty() { return false; }
/*     */ 
/*     */ 
/*     */   
/*  59 */   public boolean contains(Object target) { return (indexOf(target) != -1); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  65 */   public UnmodifiableIterator<E> iterator() { return Iterators.forArray(this.array, this.offset, this.size); }
/*     */ 
/*     */   
/*     */   public Object[] toArray() {
/*  69 */     Object[] newArray = new Object[size()];
/*  70 */     System.arraycopy(this.array, this.offset, newArray, 0, this.size);
/*  71 */     return newArray;
/*     */   }
/*     */   
/*     */   public <T> T[] toArray(T[] other) {
/*  75 */     if (other.length < this.size) {
/*  76 */       other = (T[])ObjectArrays.newArray(other, this.size);
/*  77 */     } else if (other.length > this.size) {
/*  78 */       other[this.size] = null;
/*     */     } 
/*  80 */     System.arraycopy(this.array, this.offset, other, 0, this.size);
/*  81 */     return other;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public E get(int index) {
/*  87 */     Preconditions.checkElementIndex(index, this.size);
/*  88 */     return (E)this.array[index + this.offset];
/*     */   }
/*     */   
/*     */   public int indexOf(Object target) {
/*  92 */     if (target != null) {
/*  93 */       for (int i = this.offset; i < this.offset + this.size; i++) {
/*  94 */         if (this.array[i].equals(target)) {
/*  95 */           return i - this.offset;
/*     */         }
/*     */       } 
/*     */     }
/*  99 */     return -1;
/*     */   }
/*     */   
/*     */   public int lastIndexOf(Object target) {
/* 103 */     if (target != null) {
/* 104 */       for (int i = this.offset + this.size - 1; i >= this.offset; i--) {
/* 105 */         if (this.array[i].equals(target)) {
/* 106 */           return i - this.offset;
/*     */         }
/*     */       } 
/*     */     }
/* 110 */     return -1;
/*     */   }
/*     */   
/*     */   public ImmutableList<E> subList(int fromIndex, int toIndex) {
/* 114 */     Preconditions.checkPositionIndexes(fromIndex, toIndex, this.size);
/* 115 */     return (fromIndex == toIndex) ? ImmutableList.of() : new RegularImmutableList(this.array, this.offset + fromIndex, toIndex - fromIndex);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 122 */   public ListIterator<E> listIterator() { return listIterator(0); }
/*     */ 
/*     */   
/*     */   public ListIterator<E> listIterator(final int start) {
/* 126 */     Preconditions.checkPositionIndex(start, this.size);
/*     */     
/* 128 */     return new ListIterator<E>()
/*     */       {
/*     */         int index;
/*     */         
/* 132 */         public boolean hasNext() { return (super.index < super.this$0.size); }
/*     */ 
/*     */         
/* 135 */         public boolean hasPrevious() { return (super.index > 0); }
/*     */ 
/*     */ 
/*     */         
/* 139 */         public int nextIndex() { return super.index; }
/*     */ 
/*     */         
/* 142 */         public int previousIndex() { return super.index - 1; }
/*     */ 
/*     */         
/*     */         public E next() {
/*     */           E result;
/*     */           try {
/* 148 */             result = (E)super.this$0.get(super.index);
/* 149 */           } catch (IndexOutOfBoundsException rethrown) {
/* 150 */             throw new NoSuchElementException();
/*     */           } 
/* 152 */           super.index++;
/* 153 */           return result;
/*     */         }
/*     */         public E previous() {
/*     */           E result;
/*     */           try {
/* 158 */             result = (E)super.this$0.get(super.index - 1);
/* 159 */           } catch (IndexOutOfBoundsException rethrown) {
/* 160 */             throw new NoSuchElementException();
/*     */           } 
/* 162 */           super.index--;
/* 163 */           return result;
/*     */         }
/*     */ 
/*     */         
/* 167 */         public void set(E o) { throw new UnsupportedOperationException(); }
/*     */ 
/*     */         
/* 170 */         public void add(E o) { throw new UnsupportedOperationException(); }
/*     */ 
/*     */         
/* 173 */         public void remove() { throw new UnsupportedOperationException(); }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object object) {
/* 179 */     if (object == this) {
/* 180 */       return true;
/*     */     }
/* 182 */     if (!(object instanceof List)) {
/* 183 */       return false;
/*     */     }
/*     */     
/* 186 */     List<?> that = (List)object;
/* 187 */     if (size() != that.size()) {
/* 188 */       return false;
/*     */     }
/*     */     
/* 191 */     int index = this.offset;
/* 192 */     if (object instanceof RegularImmutableList) {
/* 193 */       RegularImmutableList<?> other = (RegularImmutableList)object;
/* 194 */       for (int i = other.offset; i < other.offset + other.size; i++) {
/* 195 */         if (!this.array[index++].equals(other.array[i])) {
/* 196 */           return false;
/*     */         }
/*     */       } 
/*     */     } else {
/* 200 */       for (Object element : that) {
/* 201 */         if (!this.array[index++].equals(element)) {
/* 202 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/* 206 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 212 */     int hashCode = 1;
/* 213 */     for (int i = this.offset; i < this.offset + this.size; i++) {
/* 214 */       hashCode = 31 * hashCode + this.array[i].hashCode();
/*     */     }
/* 216 */     return hashCode;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 220 */     StringBuilder sb = new StringBuilder(size() * 16);
/* 221 */     sb.append('[').append(this.array[this.offset]);
/* 222 */     for (int i = this.offset + 1; i < this.offset + this.size; i++) {
/* 223 */       sb.append(", ").append(this.array[i]);
/*     */     }
/* 225 */     return sb.append(']').toString();
/*     */   }
/*     */ 
/*     */   
/* 229 */   int offset() { return this.offset; }
/*     */ 
/*     */ 
/*     */   
/* 233 */   Object[] array() { return this.array; }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\RegularImmutableList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */