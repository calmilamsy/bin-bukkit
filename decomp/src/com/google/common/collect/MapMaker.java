/*      */ package com.google.common.collect;
/*      */ 
/*      */ import com.google.common.annotations.GwtCompatible;
/*      */ import com.google.common.annotations.GwtIncompatible;
/*      */ import com.google.common.base.FinalizableReferenceQueue;
/*      */ import com.google.common.base.FinalizableSoftReference;
/*      */ import com.google.common.base.FinalizableWeakReference;
/*      */ import com.google.common.base.Function;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.lang.reflect.Field;
/*      */ import java.util.TimerTask;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import java.util.concurrent.ConcurrentMap;
/*      */ import java.util.concurrent.TimeUnit;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ @GwtCompatible(emulated = true)
/*      */ public final class MapMaker
/*      */ {
/*   99 */   private Strength keyStrength = Strength.STRONG;
/*  100 */   private Strength valueStrength = Strength.STRONG;
/*  101 */   private long expirationNanos = 0L;
/*      */   private boolean useCustomMap;
/*  103 */   private final CustomConcurrentHashMap.Builder builder = new CustomConcurrentHashMap.Builder();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MapMaker initialCapacity(int initialCapacity) {
/*  123 */     this.builder.initialCapacity(initialCapacity);
/*  124 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @GwtIncompatible("java.util.concurrent.ConcurrentHashMap concurrencyLevel")
/*      */   public MapMaker concurrencyLevel(int concurrencyLevel) {
/*  147 */     this.builder.concurrencyLevel(concurrencyLevel);
/*  148 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @GwtIncompatible("java.lang.ref.WeakReference")
/*  167 */   public MapMaker weakKeys() { return setKeyStrength(Strength.WEAK); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @GwtIncompatible("java.lang.ref.SoftReference")
/*  186 */   public MapMaker softKeys() { return setKeyStrength(Strength.SOFT); }
/*      */ 
/*      */   
/*      */   private MapMaker setKeyStrength(Strength strength) {
/*  190 */     if (this.keyStrength != Strength.STRONG) {
/*  191 */       throw new IllegalStateException("Key strength was already set to " + this.keyStrength + ".");
/*      */     }
/*      */     
/*  194 */     this.keyStrength = strength;
/*  195 */     this.useCustomMap = true;
/*  196 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @GwtIncompatible("java.lang.ref.WeakReference")
/*  219 */   public MapMaker weakValues() { return setValueStrength(Strength.WEAK); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @GwtIncompatible("java.lang.ref.SoftReference")
/*  242 */   public MapMaker softValues() { return setValueStrength(Strength.SOFT); }
/*      */ 
/*      */   
/*      */   private MapMaker setValueStrength(Strength strength) {
/*  246 */     if (this.valueStrength != Strength.STRONG) {
/*  247 */       throw new IllegalStateException("Value strength was already set to " + this.valueStrength + ".");
/*      */     }
/*      */     
/*  250 */     this.valueStrength = strength;
/*  251 */     this.useCustomMap = true;
/*  252 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MapMaker expiration(long duration, TimeUnit unit) {
/*  266 */     if (this.expirationNanos != 0L) {
/*  267 */       throw new IllegalStateException("expiration time of " + this.expirationNanos + " ns was already set");
/*      */     }
/*      */     
/*  270 */     if (duration <= 0L) {
/*  271 */       throw new IllegalArgumentException("invalid duration: " + duration);
/*      */     }
/*  273 */     this.expirationNanos = unit.toNanos(duration);
/*  274 */     this.useCustomMap = true;
/*  275 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  288 */   public <K, V> ConcurrentMap<K, V> makeMap() { return this.useCustomMap ? (new StrategyImpl(this)).map : new ConcurrentHashMap(this.builder.getInitialCapacity(), 0.75F, this.builder.getConcurrencyLevel()); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  336 */   public <K, V> ConcurrentMap<K, V> makeComputingMap(Function<? super K, ? extends V> computingFunction) { return (new StrategyImpl(this, computingFunction)).map; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final abstract enum Strength
/*      */   {
/*      */     WEAK, SOFT, STRONG;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     abstract boolean equal(Object param1Object1, Object param1Object2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     abstract int hash(Object param1Object);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     abstract <K, V> MapMaker.ValueReference<K, V> referenceValue(MapMaker.ReferenceEntry<K, V> param1ReferenceEntry, V param1V);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     abstract <K, V> MapMaker.ReferenceEntry<K, V> newEntry(CustomConcurrentHashMap.Internals<K, V, MapMaker.ReferenceEntry<K, V>> param1Internals, K param1K, int param1Int, MapMaker.ReferenceEntry<K, V> param1ReferenceEntry);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     abstract <K, V> MapMaker.ReferenceEntry<K, V> copyEntry(K param1K, MapMaker.ReferenceEntry<K, V> param1ReferenceEntry1, MapMaker.ReferenceEntry<K, V> param1ReferenceEntry2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static  {
/*      */       // Byte code:
/*      */       //   0: new com/google/common/collect/MapMaker$Strength$1
/*      */       //   3: dup
/*      */       //   4: ldc 'WEAK'
/*      */       //   6: iconst_0
/*      */       //   7: invokespecial <init> : (Ljava/lang/String;I)V
/*      */       //   10: putstatic com/google/common/collect/MapMaker$Strength.WEAK : Lcom/google/common/collect/MapMaker$Strength;
/*      */       //   13: new com/google/common/collect/MapMaker$Strength$2
/*      */       //   16: dup
/*      */       //   17: ldc 'SOFT'
/*      */       //   19: iconst_1
/*      */       //   20: invokespecial <init> : (Ljava/lang/String;I)V
/*      */       //   23: putstatic com/google/common/collect/MapMaker$Strength.SOFT : Lcom/google/common/collect/MapMaker$Strength;
/*      */       //   26: new com/google/common/collect/MapMaker$Strength$3
/*      */       //   29: dup
/*      */       //   30: ldc 'STRONG'
/*      */       //   32: iconst_2
/*      */       //   33: invokespecial <init> : (Ljava/lang/String;I)V
/*      */       //   36: putstatic com/google/common/collect/MapMaker$Strength.STRONG : Lcom/google/common/collect/MapMaker$Strength;
/*      */       //   39: iconst_3
/*      */       //   40: anewarray com/google/common/collect/MapMaker$Strength
/*      */       //   43: dup
/*      */       //   44: iconst_0
/*      */       //   45: getstatic com/google/common/collect/MapMaker$Strength.WEAK : Lcom/google/common/collect/MapMaker$Strength;
/*      */       //   48: aastore
/*      */       //   49: dup
/*      */       //   50: iconst_1
/*      */       //   51: getstatic com/google/common/collect/MapMaker$Strength.SOFT : Lcom/google/common/collect/MapMaker$Strength;
/*      */       //   54: aastore
/*      */       //   55: dup
/*      */       //   56: iconst_2
/*      */       //   57: getstatic com/google/common/collect/MapMaker$Strength.STRONG : Lcom/google/common/collect/MapMaker$Strength;
/*      */       //   60: aastore
/*      */       //   61: putstatic com/google/common/collect/MapMaker$Strength.$VALUES : [Lcom/google/common/collect/MapMaker$Strength;
/*      */       //   64: return
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #342	-> 0
/*      */       //   #371	-> 13
/*      */       //   #400	-> 26
/*      */       //   #341	-> 39
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class StrategyImpl<K, V>
/*      */     extends Object
/*      */     implements Serializable, CustomConcurrentHashMap.ComputingStrategy<K, V, ReferenceEntry<K, V>>
/*      */   {
/*      */     final MapMaker.Strength keyStrength;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final MapMaker.Strength valueStrength;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final ConcurrentMap<K, V> map;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final long expirationNanos;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     CustomConcurrentHashMap.Internals<K, V, MapMaker.ReferenceEntry<K, V>> internals;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static final long serialVersionUID = 0L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     StrategyImpl(MapMaker maker) {
/*  472 */       this.keyStrength = maker.keyStrength;
/*  473 */       this.valueStrength = maker.valueStrength;
/*  474 */       this.expirationNanos = maker.expirationNanos;
/*      */       
/*  476 */       this.map = maker.builder.buildMap(this);
/*      */     }
/*      */ 
/*      */     
/*      */     StrategyImpl(MapMaker maker, Function<? super K, ? extends V> computer) {
/*  481 */       this.keyStrength = maker.keyStrength;
/*  482 */       this.valueStrength = maker.valueStrength;
/*  483 */       this.expirationNanos = maker.expirationNanos;
/*      */       
/*  485 */       this.map = maker.builder.buildComputingMap(this, computer);
/*      */     }
/*      */     
/*      */     public void setValue(MapMaker.ReferenceEntry<K, V> entry, V value) {
/*  489 */       setValueReference(entry, this.valueStrength.referenceValue(entry, value));
/*      */       
/*  491 */       if (this.expirationNanos > 0L) {
/*  492 */         scheduleRemoval(entry.getKey(), value);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void scheduleRemoval(K key, V value) {
/*  506 */       final WeakReference<K> keyReference = new WeakReference<K>(key);
/*  507 */       final WeakReference<V> valueReference = new WeakReference<V>(value);
/*  508 */       ExpirationTimer.instance.schedule(new TimerTask()
/*      */           {
/*      */             public void run() {
/*  511 */               K key = (K)keyReference.get();
/*  512 */               if (key != null)
/*      */               {
/*  514 */                 MapMaker.StrategyImpl.this.map.remove(key, valueReference.get());
/*      */               }
/*      */             }
/*      */           }TimeUnit.NANOSECONDS.toMillis(this.expirationNanos));
/*      */     }
/*      */ 
/*      */     
/*  521 */     public boolean equalKeys(K a, Object b) { return this.keyStrength.equal(a, b); }
/*      */ 
/*      */ 
/*      */     
/*  525 */     public boolean equalValues(V a, Object b) { return this.valueStrength.equal(a, b); }
/*      */ 
/*      */ 
/*      */     
/*  529 */     public int hashKey(Object key) { return this.keyStrength.hash(key); }
/*      */ 
/*      */ 
/*      */     
/*  533 */     public K getKey(MapMaker.ReferenceEntry<K, V> entry) { return (K)entry.getKey(); }
/*      */ 
/*      */ 
/*      */     
/*  537 */     public int getHash(MapMaker.ReferenceEntry<K, V> entry) { return entry.getHash(); }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  542 */     public MapMaker.ReferenceEntry<K, V> newEntry(K key, int hash, MapMaker.ReferenceEntry<K, V> next) { return this.keyStrength.newEntry(this.internals, key, hash, next); }
/*      */ 
/*      */ 
/*      */     
/*      */     public MapMaker.ReferenceEntry<K, V> copyEntry(K key, MapMaker.ReferenceEntry<K, V> original, MapMaker.ReferenceEntry<K, V> newNext) {
/*  547 */       MapMaker.ValueReference<K, V> valueReference = original.getValueReference();
/*  548 */       if (valueReference == COMPUTING) {
/*  549 */         MapMaker.ReferenceEntry<K, V> newEntry = newEntry(key, original.getHash(), newNext);
/*      */         
/*  551 */         newEntry.setValueReference(new FutureValueReference(original, newEntry));
/*      */         
/*  553 */         return newEntry;
/*      */       } 
/*  555 */       MapMaker.ReferenceEntry<K, V> newEntry = newEntry(key, original.getHash(), newNext);
/*      */       
/*  557 */       newEntry.setValueReference(valueReference.copyFor(newEntry));
/*  558 */       return newEntry;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public V waitForValue(MapMaker.ReferenceEntry<K, V> entry) throws InterruptedException {
/*  568 */       MapMaker.ValueReference<K, V> valueReference = entry.getValueReference();
/*  569 */       if (valueReference == COMPUTING) {
/*  570 */         synchronized (entry) {
/*      */           
/*  572 */           while ((valueReference = entry.getValueReference()) == COMPUTING) {
/*  573 */             entry.wait();
/*      */           }
/*      */         } 
/*      */       }
/*  577 */       return (V)valueReference.waitForValue();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public V getValue(MapMaker.ReferenceEntry<K, V> entry) throws InterruptedException {
/*  585 */       MapMaker.ValueReference<K, V> valueReference = entry.getValueReference();
/*  586 */       return (V)valueReference.get();
/*      */     }
/*      */ 
/*      */     
/*      */     public V compute(K key, MapMaker.ReferenceEntry<K, V> entry, Function<? super K, ? extends V> computer) {
/*      */       V value;
/*      */       try {
/*  593 */         value = (V)computer.apply(key);
/*  594 */       } catch (ComputationException e) {
/*      */ 
/*      */         
/*  597 */         setValueReference(entry, new MapMaker.ComputationExceptionReference(e.getCause()));
/*      */         
/*  599 */         throw e;
/*  600 */       } catch (Throwable t) {
/*  601 */         setValueReference(entry, new MapMaker.ComputationExceptionReference(t));
/*      */         
/*  603 */         throw new ComputationException(t);
/*      */       } 
/*      */       
/*  606 */       if (value == null) {
/*  607 */         String message = computer + " returned null for key " + key + ".";
/*      */         
/*  609 */         setValueReference(entry, new MapMaker.NullOutputExceptionReference(message));
/*      */         
/*  611 */         throw new NullOutputException(message);
/*      */       } 
/*  613 */       setValue(entry, value);
/*      */       
/*  615 */       return value;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void setValueReference(MapMaker.ReferenceEntry<K, V> entry, MapMaker.ValueReference<K, V> valueReference) {
/*  624 */       boolean notifyOthers = (entry.getValueReference() == COMPUTING);
/*  625 */       entry.setValueReference(valueReference);
/*  626 */       if (notifyOthers) {
/*  627 */         synchronized (entry) {
/*  628 */           entry.notifyAll();
/*      */         } 
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     private class FutureValueReference
/*      */       extends Object
/*      */       implements MapMaker.ValueReference<K, V>
/*      */     {
/*      */       final MapMaker.ReferenceEntry<K, V> original;
/*      */       
/*      */       final MapMaker.ReferenceEntry<K, V> newEntry;
/*      */ 
/*      */       
/*      */       FutureValueReference(MapMaker.ReferenceEntry<K, V> original, MapMaker.ReferenceEntry<K, V> newEntry) {
/*  644 */         this.original = original;
/*  645 */         this.newEntry = newEntry;
/*      */       }
/*      */       
/*      */       public V get() {
/*  649 */         success = false;
/*      */         try {
/*  651 */           V value = (V)this.original.getValueReference().get();
/*  652 */           success = true;
/*  653 */           return value;
/*      */         } finally {
/*  655 */           if (!success) {
/*  656 */             removeEntry();
/*      */           }
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/*  662 */       public MapMaker.ValueReference<K, V> copyFor(MapMaker.ReferenceEntry<K, V> entry) { return new FutureValueReference(MapMaker.StrategyImpl.this, this.original, entry); }
/*      */ 
/*      */       
/*      */       public V waitForValue() {
/*  666 */         success = false;
/*      */         
/*      */         try {
/*  669 */           V value = (V)MapMaker.StrategyImpl.this.waitForValue(this.original);
/*  670 */           success = true;
/*  671 */           return value;
/*      */         } finally {
/*  673 */           if (!success) {
/*  674 */             removeEntry();
/*      */           }
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  686 */       void removeEntry() { MapMaker.StrategyImpl.this.internals.removeEntry(this.newEntry); }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  692 */     public MapMaker.ReferenceEntry<K, V> getNext(MapMaker.ReferenceEntry<K, V> entry) { return entry.getNext(); }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  697 */     public void setInternals(CustomConcurrentHashMap.Internals<K, V, MapMaker.ReferenceEntry<K, V>> internals) { this.internals = internals; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void writeObject(ObjectOutputStream out) throws IOException {
/*  707 */       out.writeObject(this.keyStrength);
/*  708 */       out.writeObject(this.valueStrength);
/*  709 */       out.writeLong(this.expirationNanos);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  715 */       out.writeObject(this.internals);
/*  716 */       out.writeObject(this.map);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static class Fields
/*      */     {
/*  725 */       static final Field keyStrength = findField("keyStrength");
/*  726 */       static final Field valueStrength = findField("valueStrength");
/*  727 */       static final Field expirationNanos = findField("expirationNanos");
/*  728 */       static final Field internals = findField("internals");
/*  729 */       static final Field map = findField("map");
/*      */       
/*      */       static Field findField(String name) {
/*      */         try {
/*  733 */           Field f = MapMaker.StrategyImpl.class.getDeclaredField(name);
/*  734 */           f.setAccessible(true);
/*  735 */           return f;
/*  736 */         } catch (NoSuchFieldException e) {
/*  737 */           throw new AssertionError(e);
/*      */         } 
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
/*      */       try {
/*  745 */         Fields.keyStrength.set(this, in.readObject());
/*  746 */         Fields.valueStrength.set(this, in.readObject());
/*  747 */         Fields.expirationNanos.set(this, Long.valueOf(in.readLong()));
/*  748 */         Fields.internals.set(this, in.readObject());
/*  749 */         Fields.map.set(this, in.readObject());
/*  750 */       } catch (IllegalAccessException e) {
/*  751 */         throw new AssertionError(e);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  774 */   private static final ValueReference<Object, Object> COMPUTING = new ValueReference<Object, Object>()
/*      */     {
/*      */       public Object get() {
/*  777 */         return null;
/*      */       }
/*      */ 
/*      */       
/*  781 */       public ValueReference<Object, Object> copyFor(MapMaker.ReferenceEntry<Object, Object> entry) { throw new AssertionError(); }
/*      */ 
/*      */       
/*  784 */       public Object waitForValue() { throw new AssertionError(); }
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  794 */   private static <K, V> ValueReference<K, V> computing() { return COMPUTING; }
/*      */   
/*      */   private static class NullOutputExceptionReference<K, V>
/*      */     extends Object
/*      */     implements ValueReference<K, V>
/*      */   {
/*      */     final String message;
/*      */     
/*  802 */     NullOutputExceptionReference(String message) { this.message = message; }
/*      */ 
/*      */     
/*  805 */     public V get() { return null; }
/*      */ 
/*      */ 
/*      */     
/*  809 */     public MapMaker.ValueReference<K, V> copyFor(MapMaker.ReferenceEntry<K, V> entry) { return this; }
/*      */ 
/*      */     
/*  812 */     public V waitForValue() { throw new NullOutputException(this.message); }
/*      */   }
/*      */   
/*      */   private static class ComputationExceptionReference<K, V>
/*      */     extends Object
/*      */     implements ValueReference<K, V>
/*      */   {
/*      */     final Throwable t;
/*      */     
/*  821 */     ComputationExceptionReference(Throwable t) { this.t = t; }
/*      */ 
/*      */     
/*  824 */     public V get() { return null; }
/*      */ 
/*      */ 
/*      */     
/*  828 */     public MapMaker.ValueReference<K, V> copyFor(MapMaker.ReferenceEntry<K, V> entry) { return this; }
/*      */ 
/*      */     
/*  831 */     public V waitForValue() { throw new AsynchronousComputationException(this.t); }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class QueueHolder
/*      */   {
/*  837 */     static final FinalizableReferenceQueue queue = new FinalizableReferenceQueue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class StrongEntry<K, V>
/*      */     extends Object
/*      */     implements ReferenceEntry<K, V>
/*      */   {
/*      */     final K key;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final CustomConcurrentHashMap.Internals<K, V, MapMaker.ReferenceEntry<K, V>> internals;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final int hash;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     StrongEntry(CustomConcurrentHashMap.Internals<K, V, MapMaker.ReferenceEntry<K, V>> internals, K key, int hash)
/*      */     {
/*  896 */       this.valueReference = MapMaker.computing();
/*      */       this.internals = internals;
/*      */       this.key = key;
/*  899 */       this.hash = hash; } public MapMaker.ValueReference<K, V> getValueReference() { return this.valueReference; }
/*      */     
/*      */     public K getKey() { return (K)this.key; }
/*      */     
/*  903 */     public void setValueReference(MapMaker.ValueReference<K, V> valueReference) { this.valueReference = valueReference; }
/*      */ 
/*      */     
/*  906 */     public void valueReclaimed() { this.internals.removeEntry(this, null); }
/*      */ 
/*      */     
/*  909 */     public MapMaker.ReferenceEntry<K, V> getNext() { return null; }
/*      */ 
/*      */     
/*  912 */     public int getHash() { return this.hash; }
/*      */   }
/*      */   
/*      */   private static class LinkedStrongEntry<K, V>
/*      */     extends StrongEntry<K, V> {
/*      */     final MapMaker.ReferenceEntry<K, V> next;
/*      */     
/*      */     LinkedStrongEntry(CustomConcurrentHashMap.Internals<K, V, MapMaker.ReferenceEntry<K, V>> internals, K key, int hash, MapMaker.ReferenceEntry<K, V> next) {
/*  920 */       super(internals, key, hash);
/*  921 */       this.next = next;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  927 */     public MapMaker.ReferenceEntry<K, V> getNext() { return this.next; }
/*      */   }
/*      */   
/*      */   private static class SoftEntry<K, V>
/*      */     extends FinalizableSoftReference<K>
/*      */     implements ReferenceEntry<K, V>
/*      */   {
/*      */     final CustomConcurrentHashMap.Internals<K, V, MapMaker.ReferenceEntry<K, V>> internals;
/*      */     final int hash;
/*      */     
/*      */     SoftEntry(CustomConcurrentHashMap.Internals<K, V, MapMaker.ReferenceEntry<K, V>> internals, K key, int hash) {
/*  938 */       super(key, MapMaker.QueueHolder.queue);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  955 */       this.valueReference = MapMaker.computing();
/*      */       this.internals = internals;
/*      */       this.hash = hash;
/*  958 */     } public MapMaker.ValueReference<K, V> getValueReference() { return this.valueReference; }
/*      */     public K getKey() { return (K)get(); }
/*      */     public void finalizeReferent() { this.internals.removeEntry(this); }
/*      */     
/*  962 */     public void setValueReference(MapMaker.ValueReference<K, V> valueReference) { this.valueReference = valueReference; }
/*      */ 
/*      */     
/*  965 */     public void valueReclaimed() { this.internals.removeEntry(this, null); }
/*      */ 
/*      */     
/*  968 */     public MapMaker.ReferenceEntry<K, V> getNext() { return null; }
/*      */ 
/*      */     
/*  971 */     public int getHash() { return this.hash; }
/*      */   }
/*      */   
/*      */   private static class LinkedSoftEntry<K, V> extends SoftEntry<K, V> {
/*      */     final MapMaker.ReferenceEntry<K, V> next;
/*      */     
/*      */     LinkedSoftEntry(CustomConcurrentHashMap.Internals<K, V, MapMaker.ReferenceEntry<K, V>> internals, K key, int hash, MapMaker.ReferenceEntry<K, V> next) {
/*  978 */       super(internals, key, hash);
/*  979 */       this.next = next;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  985 */     public MapMaker.ReferenceEntry<K, V> getNext() { return this.next; }
/*      */   }
/*      */   
/*      */   private static class WeakEntry<K, V>
/*      */     extends FinalizableWeakReference<K>
/*      */     implements ReferenceEntry<K, V>
/*      */   {
/*      */     final CustomConcurrentHashMap.Internals<K, V, MapMaker.ReferenceEntry<K, V>> internals;
/*      */     final int hash;
/*      */     
/*      */     WeakEntry(CustomConcurrentHashMap.Internals<K, V, MapMaker.ReferenceEntry<K, V>> internals, K key, int hash) {
/*  996 */       super(key, MapMaker.QueueHolder.queue);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1013 */       this.valueReference = MapMaker.computing();
/*      */       this.internals = internals;
/*      */       this.hash = hash;
/* 1016 */     } public K getKey() { return (K)get(); } public MapMaker.ValueReference<K, V> getValueReference() { return this.valueReference; }
/*      */     
/*      */     public void finalizeReferent() { this.internals.removeEntry(this); }
/*      */     
/* 1020 */     public void setValueReference(MapMaker.ValueReference<K, V> valueReference) { this.valueReference = valueReference; }
/*      */ 
/*      */     
/* 1023 */     public void valueReclaimed() { this.internals.removeEntry(this, null); }
/*      */ 
/*      */     
/* 1026 */     public MapMaker.ReferenceEntry<K, V> getNext() { return null; }
/*      */ 
/*      */     
/* 1029 */     public int getHash() { return this.hash; }
/*      */   }
/*      */   
/*      */   private static class LinkedWeakEntry<K, V> extends WeakEntry<K, V> {
/*      */     final MapMaker.ReferenceEntry<K, V> next;
/*      */     
/*      */     LinkedWeakEntry(CustomConcurrentHashMap.Internals<K, V, MapMaker.ReferenceEntry<K, V>> internals, K key, int hash, MapMaker.ReferenceEntry<K, V> next) {
/* 1036 */       super(internals, key, hash);
/* 1037 */       this.next = next;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1043 */     public MapMaker.ReferenceEntry<K, V> getNext() { return this.next; }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class WeakValueReference<K, V>
/*      */     extends FinalizableWeakReference<V>
/*      */     implements ValueReference<K, V>
/*      */   {
/*      */     final MapMaker.ReferenceEntry<K, V> entry;
/*      */     
/*      */     WeakValueReference(V referent, MapMaker.ReferenceEntry<K, V> entry) {
/* 1054 */       super(referent, MapMaker.QueueHolder.queue);
/* 1055 */       this.entry = entry;
/*      */     }
/*      */ 
/*      */     
/* 1059 */     public void finalizeReferent() { this.entry.valueReclaimed(); }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1064 */     public MapMaker.ValueReference<K, V> copyFor(MapMaker.ReferenceEntry<K, V> entry) { return new WeakValueReference(get(), entry); }
/*      */ 
/*      */ 
/*      */     
/* 1068 */     public V waitForValue() { return (V)get(); }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class SoftValueReference<K, V>
/*      */     extends FinalizableSoftReference<V>
/*      */     implements ValueReference<K, V>
/*      */   {
/*      */     final MapMaker.ReferenceEntry<K, V> entry;
/*      */     
/*      */     SoftValueReference(V referent, MapMaker.ReferenceEntry<K, V> entry) {
/* 1079 */       super(referent, MapMaker.QueueHolder.queue);
/* 1080 */       this.entry = entry;
/*      */     }
/*      */ 
/*      */     
/* 1084 */     public void finalizeReferent() { this.entry.valueReclaimed(); }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1089 */     public MapMaker.ValueReference<K, V> copyFor(MapMaker.ReferenceEntry<K, V> entry) { return new SoftValueReference(get(), entry); }
/*      */ 
/*      */ 
/*      */     
/* 1093 */     public V waitForValue() { return (V)get(); }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class StrongValueReference<K, V>
/*      */     extends Object
/*      */     implements ValueReference<K, V>
/*      */   {
/*      */     final V referent;
/*      */     
/* 1103 */     StrongValueReference(V referent) { this.referent = referent; }
/*      */ 
/*      */ 
/*      */     
/* 1107 */     public V get() { return (V)this.referent; }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1112 */     public MapMaker.ValueReference<K, V> copyFor(MapMaker.ReferenceEntry<K, V> entry) { return this; }
/*      */ 
/*      */ 
/*      */     
/* 1116 */     public V waitForValue() { return (V)get(); }
/*      */   }
/*      */   
/*      */   private static interface ReferenceEntry<K, V> {
/*      */     MapMaker.ValueReference<K, V> getValueReference();
/*      */     
/*      */     void setValueReference(MapMaker.ValueReference<K, V> param1ValueReference);
/*      */     
/*      */     void valueReclaimed();
/*      */     
/*      */     ReferenceEntry<K, V> getNext();
/*      */     
/*      */     int getHash();
/*      */     
/*      */     K getKey();
/*      */   }
/*      */   
/*      */   private static interface ValueReference<K, V> {
/*      */     V get();
/*      */     
/*      */     ValueReference<K, V> copyFor(MapMaker.ReferenceEntry<K, V> param1ReferenceEntry);
/*      */     
/*      */     V waitForValue();
/*      */   }
/*      */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\com\google\common\collect\MapMaker.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */