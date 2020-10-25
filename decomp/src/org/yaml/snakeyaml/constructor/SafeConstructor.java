/*     */ package org.yaml.snakeyaml.constructor;
/*     */ 
/*     */ import java.math.BigInteger;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TimeZone;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.yaml.snakeyaml.error.YAMLException;
/*     */ import org.yaml.snakeyaml.nodes.MappingNode;
/*     */ import org.yaml.snakeyaml.nodes.Node;
/*     */ import org.yaml.snakeyaml.nodes.NodeId;
/*     */ import org.yaml.snakeyaml.nodes.NodeTuple;
/*     */ import org.yaml.snakeyaml.nodes.ScalarNode;
/*     */ import org.yaml.snakeyaml.nodes.SequenceNode;
/*     */ import org.yaml.snakeyaml.nodes.Tag;
/*     */ import org.yaml.snakeyaml.util.Base64Coder;
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
/*     */ public class SafeConstructor
/*     */   extends BaseConstructor
/*     */ {
/*  49 */   public static ConstructUndefined undefinedConstructor = new ConstructUndefined();
/*     */   
/*     */   public SafeConstructor() {
/*  52 */     this.yamlConstructors.put(Tag.NULL, new ConstructYamlNull());
/*  53 */     this.yamlConstructors.put(Tag.BOOL, new ConstructYamlBool());
/*  54 */     this.yamlConstructors.put(Tag.INT, new ConstructYamlInt());
/*  55 */     this.yamlConstructors.put(Tag.FLOAT, new ConstructYamlFloat());
/*  56 */     this.yamlConstructors.put(Tag.BINARY, new ConstructYamlBinary());
/*  57 */     this.yamlConstructors.put(Tag.TIMESTAMP, new ConstructYamlTimestamp());
/*  58 */     this.yamlConstructors.put(Tag.OMAP, new ConstructYamlOmap());
/*  59 */     this.yamlConstructors.put(Tag.PAIRS, new ConstructYamlPairs());
/*  60 */     this.yamlConstructors.put(Tag.SET, new ConstructYamlSet());
/*  61 */     this.yamlConstructors.put(Tag.STR, new ConstructYamlStr());
/*  62 */     this.yamlConstructors.put(Tag.SEQ, new ConstructYamlSeq());
/*  63 */     this.yamlConstructors.put(Tag.MAP, new ConstructYamlMap());
/*  64 */     this.yamlConstructors.put(null, undefinedConstructor);
/*  65 */     this.yamlClassConstructors.put(NodeId.scalar, undefinedConstructor);
/*  66 */     this.yamlClassConstructors.put(NodeId.sequence, undefinedConstructor);
/*  67 */     this.yamlClassConstructors.put(NodeId.mapping, undefinedConstructor);
/*     */   }
/*     */   
/*     */   private void flattenMapping(MappingNode node) {
/*  71 */     List<NodeTuple> merge = new ArrayList<NodeTuple>();
/*  72 */     int index = 0;
/*  73 */     List<NodeTuple> nodeValue = node.getValue();
/*  74 */     while (index < nodeValue.size()) {
/*  75 */       Node keyNode = ((NodeTuple)nodeValue.get(index)).getKeyNode();
/*  76 */       Node valueNode = ((NodeTuple)nodeValue.get(index)).getValueNode();
/*  77 */       if (keyNode.getTag().equals(Tag.MERGE)) {
/*  78 */         List<Node> vals; SequenceNode sn; List<List<NodeTuple>> submerge; MappingNode mn; nodeValue.remove(index);
/*  79 */         switch (valueNode.getNodeId()) {
/*     */           case mapping:
/*  81 */             mn = (MappingNode)valueNode;
/*  82 */             flattenMapping(mn);
/*  83 */             merge.addAll(mn.getValue());
/*     */             continue;
/*     */           case sequence:
/*  86 */             submerge = new ArrayList<List<NodeTuple>>();
/*  87 */             sn = (SequenceNode)valueNode;
/*  88 */             vals = sn.getValue();
/*  89 */             for (Node subnode : vals) {
/*  90 */               if (!(subnode instanceof MappingNode)) {
/*  91 */                 throw new ConstructorException("while constructing a mapping", node.getStartMark(), "expected a mapping for merging, but found " + subnode.getNodeId(), subnode.getStartMark());
/*     */               }
/*     */ 
/*     */               
/*  95 */               MappingNode mnode = (MappingNode)subnode;
/*  96 */               flattenMapping(mnode);
/*  97 */               submerge.add(mnode.getValue());
/*     */             } 
/*  99 */             Collections.reverse(submerge);
/* 100 */             for (List<NodeTuple> value : submerge) {
/* 101 */               merge.addAll(value);
/*     */             }
/*     */             continue;
/*     */         } 
/* 105 */         throw new ConstructorException("while constructing a mapping", node.getStartMark(), "expected a mapping or list of mappings for merging, but found " + valueNode.getNodeId(), valueNode.getStartMark());
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 110 */       if (keyNode.getTag().equals(Tag.VALUE)) {
/* 111 */         keyNode.setTag(Tag.STR);
/* 112 */         index++; continue;
/*     */       } 
/* 114 */       index++;
/*     */     } 
/*     */     
/* 117 */     if (!merge.isEmpty()) {
/* 118 */       merge.addAll(nodeValue);
/* 119 */       node.setValue(merge);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void constructMapping2ndStep(MappingNode node, Map<Object, Object> mapping) {
/* 124 */     flattenMapping(node);
/* 125 */     super.constructMapping2ndStep(node, mapping);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void constructSet2ndStep(MappingNode node, Set<Object> set) {
/* 130 */     flattenMapping(node);
/* 131 */     super.constructSet2ndStep(node, set);
/*     */   }
/*     */   
/*     */   public class ConstructYamlNull extends AbstractConstruct {
/*     */     public Object construct(Node node) {
/* 136 */       SafeConstructor.this.constructScalar((ScalarNode)node);
/* 137 */       return null;
/*     */     }
/*     */   }
/*     */   
/* 141 */   private static final Map<String, Boolean> BOOL_VALUES = new HashMap(); private static final Pattern TIMESTAMP_REGEXP; private static final Pattern YMD_REGEXP;
/*     */   
/* 143 */   static  { BOOL_VALUES.put("yes", Boolean.TRUE);
/* 144 */     BOOL_VALUES.put("no", Boolean.FALSE);
/* 145 */     BOOL_VALUES.put("true", Boolean.TRUE);
/* 146 */     BOOL_VALUES.put("false", Boolean.FALSE);
/* 147 */     BOOL_VALUES.put("on", Boolean.TRUE);
/* 148 */     BOOL_VALUES.put("off", Boolean.FALSE);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 254 */     TIMESTAMP_REGEXP = Pattern.compile("^([0-9][0-9][0-9][0-9])-([0-9][0-9]?)-([0-9][0-9]?)(?:(?:[Tt]|[ \t]+)([0-9][0-9]?):([0-9][0-9]):([0-9][0-9])(?:\\.([0-9]*))?(?:[ \t]*(?:Z|([-+][0-9][0-9]?)(?::([0-9][0-9])?)?))?)?$");
/*     */     
/* 256 */     YMD_REGEXP = Pattern.compile("^([0-9][0-9][0-9][0-9])-([0-9][0-9]?)-([0-9][0-9]?)$"); }
/*     */   public class ConstructYamlBool extends AbstractConstruct {
/*     */     public Object construct(Node node) { String val = (String)SafeConstructor.this.constructScalar((ScalarNode)node); return BOOL_VALUES.get(val.toLowerCase()); } } public class ConstructYamlInt extends AbstractConstruct {
/*     */     public Object construct(Node node) { String value = SafeConstructor.this.constructScalar((ScalarNode)node).toString().replaceAll("_", ""); int sign = 1; char first = value.charAt(0); if (first == '-') { sign = -1; value = value.substring(1); } else if (first == '+') { value = value.substring(1); }  int base = 10; if ("0".equals(value)) return new Integer(false);  if (value.startsWith("0b")) { value = value.substring(2); base = 2; } else if (value.startsWith("0x")) { value = value.substring(2); base = 16; } else if (value.startsWith("0")) { value = value.substring(1); base = 8; } else { if (value.indexOf(':') != -1) { String[] digits = value.split(":"); int bes = 1; int val = 0; for (int i = 0, j = digits.length; i < j; i++) { val = (int)(val + Long.parseLong(digits[j - i - 1]) * bes); bes *= 60; }  return SafeConstructor.this.createNumber(sign, String.valueOf(val), 10); }  return SafeConstructor.this.createNumber(sign, value, 10); }  return SafeConstructor.this.createNumber(sign, value, base); } } private Number createNumber(int sign, String number, int radix) { BigInteger bigInteger; if (sign < 0) number = "-" + number;  try { bigInteger = Integer.valueOf(number, radix); } catch (NumberFormatException e) { try { bigInteger = Long.valueOf(number, radix); } catch (NumberFormatException e1) { bigInteger = new BigInteger(number, radix); }  }  return bigInteger; } public class ConstructYamlFloat extends AbstractConstruct {
/*     */     public Object construct(Node node) { String value = SafeConstructor.this.constructScalar((ScalarNode)node).toString().replaceAll("_", ""); int sign = 1; char first = value.charAt(0); if (first == '-') { sign = -1; value = value.substring(1); } else if (first == '+') { value = value.substring(1); }  String valLower = value.toLowerCase(); if (".inf".equals(valLower)) return new Double((sign == -1) ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY);  if (".nan".equals(valLower)) return new Double(NaND);  if (value.indexOf(':') != -1) { String[] digits = value.split(":"); int bes = 1; double val = 0.0D; for (int i = 0, j = digits.length; i < j; i++) { val += Double.parseDouble(digits[j - i - 1]) * bes; bes *= 60; }  return new Double(sign * val); }  Double d = Double.valueOf(value); return new Double(d.doubleValue() * sign); }
/*     */   } public class ConstructYamlBinary extends AbstractConstruct {
/*     */     public Object construct(Node node) { return Base64Coder.decode(SafeConstructor.this.constructScalar((ScalarNode)node).toString().toCharArray()); }
/* 263 */   } public class ConstructYamlTimestamp extends AbstractConstruct { public Calendar getCalendar() { return this.calendar; }
/*     */     private Calendar calendar;
/*     */     public Object construct(Node node) {
/*     */       TimeZone timeZone;
/* 267 */       ScalarNode scalar = (ScalarNode)node;
/* 268 */       String nodeValue = scalar.getValue();
/* 269 */       Matcher match = YMD_REGEXP.matcher(nodeValue);
/* 270 */       if (match.matches()) {
/* 271 */         String year_s = match.group(1);
/* 272 */         String month_s = match.group(2);
/* 273 */         String day_s = match.group(3);
/* 274 */         this.calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
/* 275 */         this.calendar.clear();
/* 276 */         this.calendar.set(1, Integer.parseInt(year_s));
/*     */         
/* 278 */         this.calendar.set(2, Integer.parseInt(month_s) - 1);
/* 279 */         this.calendar.set(5, Integer.parseInt(day_s));
/* 280 */         return this.calendar.getTime();
/*     */       } 
/* 282 */       match = TIMESTAMP_REGEXP.matcher(nodeValue);
/* 283 */       if (!match.matches()) {
/* 284 */         throw new YAMLException("Unexpected timestamp: " + nodeValue);
/*     */       }
/* 286 */       String year_s = match.group(1);
/* 287 */       String month_s = match.group(2);
/* 288 */       String day_s = match.group(3);
/* 289 */       String hour_s = match.group(4);
/* 290 */       String min_s = match.group(5);
/* 291 */       String sec_s = match.group(6);
/* 292 */       String fract_s = match.group(7);
/* 293 */       String timezoneh_s = match.group(8);
/* 294 */       String timezonem_s = match.group(9);
/*     */       
/* 296 */       int usec = 0;
/* 297 */       if (fract_s != null) {
/* 298 */         usec = Integer.parseInt(fract_s);
/* 299 */         if (usec != 0) {
/* 300 */           while (10 * usec < 1000) {
/* 301 */             usec *= 10;
/*     */           }
/*     */         }
/*     */       } 
/*     */       
/* 306 */       if (timezoneh_s != null) {
/* 307 */         String time = (timezonem_s != null) ? (":" + timezonem_s) : "00";
/* 308 */         timeZone = TimeZone.getTimeZone("GMT" + timezoneh_s + time);
/*     */       } else {
/*     */         
/* 311 */         timeZone = TimeZone.getTimeZone("UTC");
/*     */       } 
/* 313 */       this.calendar = Calendar.getInstance(timeZone);
/* 314 */       this.calendar.set(1, Integer.parseInt(year_s));
/*     */       
/* 316 */       this.calendar.set(2, Integer.parseInt(month_s) - 1);
/* 317 */       this.calendar.set(5, Integer.parseInt(day_s));
/* 318 */       this.calendar.set(11, Integer.parseInt(hour_s));
/* 319 */       this.calendar.set(12, Integer.parseInt(min_s));
/* 320 */       this.calendar.set(13, Integer.parseInt(sec_s));
/* 321 */       this.calendar.set(14, usec);
/* 322 */       return this.calendar.getTime();
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public class ConstructYamlOmap
/*     */     extends AbstractConstruct
/*     */   {
/*     */     public Object construct(Node node) {
/* 331 */       Map<Object, Object> omap = new LinkedHashMap<Object, Object>();
/* 332 */       if (!(node instanceof SequenceNode)) {
/* 333 */         throw new ConstructorException("while constructing an ordered map", node.getStartMark(), "expected a sequence, but found " + node.getNodeId(), node.getStartMark());
/*     */       }
/*     */ 
/*     */       
/* 337 */       SequenceNode snode = (SequenceNode)node;
/* 338 */       for (Node subnode : snode.getValue()) {
/* 339 */         if (!(subnode instanceof MappingNode)) {
/* 340 */           throw new ConstructorException("while constructing an ordered map", node.getStartMark(), "expected a mapping of length 1, but found " + subnode.getNodeId(), subnode.getStartMark());
/*     */         }
/*     */ 
/*     */         
/* 344 */         MappingNode mnode = (MappingNode)subnode;
/* 345 */         if (mnode.getValue().size() != 1) {
/* 346 */           throw new ConstructorException("while constructing an ordered map", node.getStartMark(), "expected a single mapping item, but found " + mnode.getValue().size() + " items", mnode.getStartMark());
/*     */         }
/*     */ 
/*     */         
/* 350 */         Node keyNode = ((NodeTuple)mnode.getValue().get(0)).getKeyNode();
/* 351 */         Node valueNode = ((NodeTuple)mnode.getValue().get(0)).getValueNode();
/* 352 */         Object key = SafeConstructor.this.constructObject(keyNode);
/* 353 */         Object value = SafeConstructor.this.constructObject(valueNode);
/* 354 */         omap.put(key, value);
/*     */       } 
/* 356 */       return omap;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public class ConstructYamlPairs
/*     */     extends AbstractConstruct
/*     */   {
/*     */     public Object construct(Node node) {
/* 365 */       if (!(node instanceof SequenceNode)) {
/* 366 */         throw new ConstructorException("while constructing pairs", node.getStartMark(), "expected a sequence, but found " + node.getNodeId(), node.getStartMark());
/*     */       }
/*     */       
/* 369 */       SequenceNode snode = (SequenceNode)node;
/* 370 */       List<Object[]> pairs = new ArrayList<Object[]>(snode.getValue().size());
/* 371 */       for (Node subnode : snode.getValue()) {
/* 372 */         if (!(subnode instanceof MappingNode)) {
/* 373 */           throw new ConstructorException("while constructingpairs", node.getStartMark(), "expected a mapping of length 1, but found " + subnode.getNodeId(), subnode.getStartMark());
/*     */         }
/*     */ 
/*     */         
/* 377 */         MappingNode mnode = (MappingNode)subnode;
/* 378 */         if (mnode.getValue().size() != 1) {
/* 379 */           throw new ConstructorException("while constructing pairs", node.getStartMark(), "expected a single mapping item, but found " + mnode.getValue().size() + " items", mnode.getStartMark());
/*     */         }
/*     */ 
/*     */         
/* 383 */         Node keyNode = ((NodeTuple)mnode.getValue().get(0)).getKeyNode();
/* 384 */         Node valueNode = ((NodeTuple)mnode.getValue().get(0)).getValueNode();
/* 385 */         Object key = SafeConstructor.this.constructObject(keyNode);
/* 386 */         Object value = SafeConstructor.this.constructObject(valueNode);
/* 387 */         pairs.add(new Object[] { key, value });
/*     */       } 
/* 389 */       return pairs;
/*     */     }
/*     */   }
/*     */   
/*     */   public class ConstructYamlSet implements Construct {
/*     */     public Object construct(Node node) {
/* 395 */       if (node.isTwoStepsConstruction()) {
/* 396 */         return SafeConstructor.this.createDefaultSet();
/*     */       }
/* 398 */       return SafeConstructor.this.constructSet((MappingNode)node);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void construct2ndStep(Node node, Object object) {
/* 404 */       if (node.isTwoStepsConstruction()) {
/* 405 */         SafeConstructor.this.constructSet2ndStep((MappingNode)node, (Set)object);
/*     */       } else {
/* 407 */         throw new YAMLException("Unexpected recursive set structure. Node: " + node);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public class ConstructYamlStr
/*     */     extends AbstractConstruct {
/* 414 */     public Object construct(Node node) { return (String)SafeConstructor.this.constructScalar((ScalarNode)node); }
/*     */   }
/*     */   
/*     */   public class ConstructYamlSeq
/*     */     implements Construct {
/*     */     public Object construct(Node node) {
/* 420 */       SequenceNode seqNode = (SequenceNode)node;
/* 421 */       if (node.isTwoStepsConstruction()) {
/* 422 */         return SafeConstructor.this.createDefaultList(seqNode.getValue().size());
/*     */       }
/* 424 */       return SafeConstructor.this.constructSequence(seqNode);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void construct2ndStep(Node node, Object data) {
/* 430 */       if (node.isTwoStepsConstruction()) {
/* 431 */         SafeConstructor.this.constructSequenceStep2((SequenceNode)node, (List)data);
/*     */       } else {
/* 433 */         throw new YAMLException("Unexpected recursive sequence structure. Node: " + node);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public class ConstructYamlMap implements Construct {
/*     */     public Object construct(Node node) {
/* 440 */       if (node.isTwoStepsConstruction()) {
/* 441 */         return SafeConstructor.this.createDefaultMap();
/*     */       }
/* 443 */       return SafeConstructor.this.constructMapping((MappingNode)node);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void construct2ndStep(Node node, Object object) {
/* 449 */       if (node.isTwoStepsConstruction()) {
/* 450 */         SafeConstructor.this.constructMapping2ndStep((MappingNode)node, (Map)object);
/*     */       } else {
/* 452 */         throw new YAMLException("Unexpected recursive mapping structure. Node: " + node);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class ConstructUndefined
/*     */     extends AbstractConstruct {
/* 459 */     public Object construct(Node node) { throw new ConstructorException(null, null, "could not determine a constructor for the tag " + node.getTag(), node.getStartMark()); }
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\org\yaml\snakeyaml\constructor\SafeConstructor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */