/*     */ package joptsimple.internal;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ColumnarData
/*     */ {
/*  45 */   private static final String LINE_SEPARATOR = System.getProperty("line.separator");
/*     */ 
/*     */   
/*     */   private static final int TOTAL_WIDTH = 80;
/*     */   
/*     */   private final ColumnWidthCalculator widthCalculator;
/*     */   
/*     */   private final List<Column> columns;
/*     */   
/*     */   private final String[] headers;
/*     */ 
/*     */   
/*     */   public ColumnarData(String... headers) {
/*  58 */     this.headers = (String[])headers.clone();
/*  59 */     this.widthCalculator = new ColumnWidthCalculator();
/*  60 */     this.columns = new LinkedList();
/*     */     
/*  62 */     clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addRow(Object... rowData) {
/*  73 */     int[] numberOfCellsAddedAt = addRowCells(rowData);
/*  74 */     addPaddingCells(numberOfCellsAddedAt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String format() {
/*  83 */     StringBuilder buffer = new StringBuilder();
/*     */     
/*  85 */     writeHeadersOn(buffer);
/*  86 */     writeSeparatorsOn(buffer);
/*  87 */     writeRowsOn(buffer);
/*     */     
/*  89 */     return buffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clear() {
/*  96 */     this.columns.clear();
/*     */     
/*  98 */     int desiredColumnWidth = this.widthCalculator.calculate(80, this.headers.length);
/*  99 */     for (String each : this.headers)
/* 100 */       this.columns.add(new Column(each, desiredColumnWidth)); 
/*     */   }
/*     */   
/*     */   private void writeHeadersOn(StringBuilder buffer) {
/* 104 */     for (Iterator<Column> iter = this.columns.iterator(); iter.hasNext();) {
/* 105 */       ((Column)iter.next()).writeHeaderOn(buffer, iter.hasNext());
/*     */     }
/* 107 */     buffer.append(LINE_SEPARATOR);
/*     */   }
/*     */   
/*     */   private void writeSeparatorsOn(StringBuilder buffer) {
/* 111 */     for (Iterator<Column> iter = this.columns.iterator(); iter.hasNext();) {
/* 112 */       ((Column)iter.next()).writeSeparatorOn(buffer, iter.hasNext());
/*     */     }
/* 114 */     buffer.append(LINE_SEPARATOR);
/*     */   }
/*     */   
/*     */   private void writeRowsOn(StringBuilder buffer) {
/* 118 */     int maxHeight = ((Column)Collections.max(this.columns, Column.BY_HEIGHT)).height();
/*     */     
/* 120 */     for (int i = 0; i < maxHeight; i++)
/* 121 */       writeRowOn(buffer, i); 
/*     */   }
/*     */   
/*     */   private void writeRowOn(StringBuilder buffer, int rowIndex) {
/* 125 */     for (Iterator<Column> iter = this.columns.iterator(); iter.hasNext();) {
/* 126 */       ((Column)iter.next()).writeCellOn(rowIndex, buffer, iter.hasNext());
/*     */     }
/* 128 */     buffer.append(LINE_SEPARATOR);
/*     */   }
/*     */   
/*     */   private int arrayMax(int[] numbers) {
/* 132 */     int maximum = Integer.MIN_VALUE;
/*     */     
/* 134 */     for (int each : numbers) {
/* 135 */       maximum = Math.max(maximum, each);
/*     */     }
/* 137 */     return maximum;
/*     */   }
/*     */   
/*     */   private int[] addRowCells(Object... rowData) {
/* 141 */     int[] cellsAddedAt = new int[rowData.length];
/*     */     
/* 143 */     Iterator<Column> iter = this.columns.iterator();
/* 144 */     for (int i = 0; iter.hasNext() && i < rowData.length; i++) {
/* 145 */       cellsAddedAt[i] = ((Column)iter.next()).addCells(rowData[i]);
/*     */     }
/* 147 */     return cellsAddedAt;
/*     */   }
/*     */   
/*     */   private void addPaddingCells(int... numberOfCellsAddedAt) {
/* 151 */     int maxHeight = arrayMax(numberOfCellsAddedAt);
/*     */     
/* 153 */     Iterator<Column> iter = this.columns.iterator();
/* 154 */     for (int i = 0; iter.hasNext() && i < numberOfCellsAddedAt.length; i++)
/* 155 */       addPaddingCellsForColumn((Column)iter.next(), maxHeight, numberOfCellsAddedAt[i]); 
/*     */   }
/*     */   
/*     */   private void addPaddingCellsForColumn(Column column, int maxHeight, int numberOfCellsAdded) {
/* 159 */     for (int i = 0; i < maxHeight - numberOfCellsAdded; i++)
/* 160 */       column.addCell(""); 
/*     */   }
/*     */ }


/* Location:              C:\Users\calmilamsy\Downloads\craftbukkit-1092-0f15ea67210f5e56ac6a23b7a714564654888e9f.jar!\joptsimple\internal\ColumnarData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.4
 */