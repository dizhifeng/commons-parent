package org.minz.commons.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.minz.commons.excel.convertor.FieldConverter;

/**
 * excel写入类
 *
 * @author zhengmin 2019/1/3
 */
public class ExcelWriter<T> {

  private SXSSFWorkbook workbook;
  private SXSSFSheet sheet;
  private AtomicInteger counter;
  private Class<T> clazz;
  private int cells;

  private CellStyle leftAlignStype = null;
  private CellStyle centerAlignStype = null;
  private CellStyle rightlignStype = null;

  public ExcelWriter(String sheetName, Class<T> clazz) {
    this.clazz = clazz;
    this.workbook = new SXSSFWorkbook(100);
    this.workbook.setCompressTempFiles(true);
    sheet = workbook.createSheet(sheetName);
    sheet.trackAllColumnsForAutoSizing();
    sheet.setVerticallyCenter(true);

    leftAlignStype = workbook.createCellStyle();
    leftAlignStype.setAlignment(HorizontalAlignment.LEFT);
    leftAlignStype.setVerticalAlignment(VerticalAlignment.CENTER);

    centerAlignStype = workbook.createCellStyle();
    centerAlignStype.setAlignment(HorizontalAlignment.CENTER);
    centerAlignStype.setVerticalAlignment(VerticalAlignment.CENTER);

    rightlignStype = workbook.createCellStyle();
    rightlignStype.setAlignment(HorizontalAlignment.RIGHT);
    rightlignStype.setVerticalAlignment(VerticalAlignment.CENTER);

    Row titleRow = sheet.createRow(0);
    Field[] fields = clazz.getDeclaredFields();
    for (Field field : fields) {
      field.setAccessible(true);
      if (field.isAnnotationPresent(WriteColumn.class)) {
        WriteColumn writeColumn = field.getAnnotation(WriteColumn.class);
        Cell cell = titleRow.createCell(writeColumn.value(), CellType.STRING);
        cell.setCellStyle(centerAlignStype);
        cell.setCellValue(writeColumn.title());
        cells++;
      }
    }
    counter = new AtomicInteger(1);
  }

  public ExcelWriter append(List<T> data) {
    DataFormat dataFormat = workbook.createDataFormat();
    short text = 0x31;
    leftAlignStype.setDataFormat(text);
    centerAlignStype.setDataFormat(text);
    rightlignStype.setDataFormat(text);

    for (T t : data) {
      Field[] fields = t.getClass().getDeclaredFields();
      Row row = sheet.createRow(counter.get());
      for (Field field : fields) {
        field.setAccessible(true);
        if (field.isAnnotationPresent(WriteColumn.class)) {
          WriteColumn writeColumn = field.getAnnotation(WriteColumn.class);
          Cell cell = row.createCell(writeColumn.value(), CellType.STRING);
          switch (writeColumn.align()) {
            case LEFT:
              cell.setCellStyle(leftAlignStype);
              break;
            case CENTER:
              cell.setCellStyle(centerAlignStype);
              break;
            case RIGHT:
              cell.setCellStyle(rightlignStype);
              break;
          }

          try {
            Object value = field.get(t);
            FieldConverter fieldConvertor = writeColumn.converter().newInstance();
            cell.setCellValue(fieldConvertor.convert(value));
          } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
          }
        }
      }
      this.counter.updateAndGet(n -> n + 1);
    }
    return this;
  }

  public void write(OutputStream out) {
    try {
      for (int i = 0; i < cells; i++) {
        sheet.autoSizeColumn(i);
      }
      workbook.write(out);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      workbook.dispose();
    }
  }
}
