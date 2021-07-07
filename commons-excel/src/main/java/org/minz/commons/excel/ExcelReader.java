package org.minz.commons.excel;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.minz.commons.excel.validator.FieldValidator;

/**
 * excel读取类
 *
 * @author zhengmin 2019/1/3
 */
public class ExcelReader {

  private final ReadConfig readConfig;

  public ExcelReader(ReadConfig readConfig) {
    this.readConfig = readConfig;
  }

  public <T> List<T> read(InputStream inputStream, Class<T> modelClass)
      throws IOException, InvalidFormatException, IllegalAccessException, InstantiationException {
    List<T> dataList = new LinkedList<>();
    Field[] fields = modelClass.getDeclaredFields();
    Workbook workbook = WorkbookFactory.create(inputStream);
    Sheet sheet = workbook.getSheetAt(readConfig.getSheetNumber());
    int rowCount = readConfig.getEndLineNumber() == null ? sheet.getPhysicalNumberOfRows()
        : readConfig.getEndLineNumber();
    for (int i = readConfig.getStartLineNumber(); i < rowCount; i++) {
      Row row = sheet.getRow(i);
      T entity = modelClass.newInstance();
      for (Field field : fields) {
        if (field.isAnnotationPresent(ReadColumn.class)) {
          field.setAccessible(true);
          ReadColumn readColumn = field.getAnnotation(ReadColumn.class);
          Integer idx = readColumn.value();
          Cell cell = row.getCell(idx);
          Object cellValue = null;
          if (cell != null) {
            switch (cell.getCellTypeEnum()) {
              case NUMERIC:
                cellValue = cell.getNumericCellValue();
                break;
              case STRING:
                cellValue = cell.getStringCellValue();
                break;
            }
          }
          if (cellValue == null && readColumn.required()) {
            throw new RuntimeException(
                "第 " + (i + 1) + " 行，第 " + (idx + 1) + " 列赋值出错：数据不能为空");
          }
          FieldValidator fieldValidator = readColumn.validator().newInstance();
          boolean fieldValid = fieldValidator.validate(cellValue);
          if (fieldValid) {
            try {
              field.set(entity, readColumn.typeHandler().newInstance().convert(cellValue));
            } catch (Exception e) {
              throw new RuntimeException(
                  "第 " + (i + 1) + " 行，第 " + (idx + 1) + " 列赋值出错，单元格格式错误：" + e.getMessage());
            }
          } else {
            throw new RuntimeException(
                "第 " + (i + 1) + " 行，第 " + (idx + 1) + " 列赋值出错，数据验证失败：" + fieldValidator
                    .getErrorMessage());
          }
        }
      }
      dataList.add(entity);
    }
    return dataList;
  }
}
