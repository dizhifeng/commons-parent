package org.minz.commons.excel;

/**
 * excel读取设置
 *
 * @author zhengmin 2019/1/3
 */
public class ReadConfig {

  /**
   * sheet序号
   */
  private int sheetNumber;

  /**
   * 开始读取的行号
   */
  private int startLineNumber;

  /**
   * 读取结束的行号
   */
  private Integer endLineNumber;

  public ReadConfig(int sheetNumber, int startLineNumber) {
    this.sheetNumber = sheetNumber;
    this.startLineNumber = startLineNumber;
  }

  public ReadConfig(int sheetNumber, int startLineNumber, Integer endLineNumber) {
    this.sheetNumber = sheetNumber;
    this.startLineNumber = startLineNumber;
    this.endLineNumber = endLineNumber;
  }

  public static ReadConfig of(int sheetNumber, int startLineNumber) {
    return new ReadConfig(sheetNumber, startLineNumber);
  }

  public static ReadConfig of(int sheetNumber, int startLineNumber, Integer endLineNumber) {
    return new ReadConfig(sheetNumber, startLineNumber, endLineNumber);
  }

  public int getSheetNumber() {
    return sheetNumber;
  }

  public void setSheetNumber(int sheetNumber) {
    this.sheetNumber = sheetNumber;
  }

  public int getStartLineNumber() {
    return startLineNumber;
  }

  public void setStartLineNumber(int startLineNumber) {
    this.startLineNumber = startLineNumber;
  }

  public Integer getEndLineNumber() {
    return endLineNumber;
  }

  public void setEndLineNumber(Integer endLineNumber) {
    this.endLineNumber = endLineNumber;
  }
}
