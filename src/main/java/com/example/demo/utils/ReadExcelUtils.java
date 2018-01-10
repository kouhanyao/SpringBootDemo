package com.example.demo.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;

/**
 * Created by 寇含尧 on 2017/12/26.
 */
public class ReadExcelUtils {
    private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";
    private static final SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 判断Excel的版本,获取Workbook
     *
     * @param in
     * @param fileName
     * @return
     * @throws IOException
     */
    public static Workbook getWorkbok(InputStream in, String fileName) throws IOException {
        Workbook wb = null;
        if (fileName.endsWith(EXCEL_XLS)) {  //Excel 2003
            wb = new HSSFWorkbook(in);
        } else if (fileName.endsWith(EXCEL_XLSX)) {  // Excel 2007/2010
            wb = new XSSFWorkbook(in);
        }
        return wb;
    }

    /**
     * 判断文件是否是excel
     *
     * @throws Exception
     */
    public static void checkExcelVaild(String fileName) throws Exception {
        if (StringUtil.isBlank(fileName)) {
            throw new Exception("文件不存在");
        }
        if (!((fileName.endsWith(EXCEL_XLS) || fileName.endsWith(EXCEL_XLSX)))) {
            throw new Exception("文件不是Excel");
        }
    }

    /**
     * 读取Excel测试，兼容 Excel 2003/2007/2010
     *
     * @param fileName 文件名称
     * @throws Exception
     */
    public static void read(InputStream is, String fileName) throws Exception {
        //FileInputStream is = null;
        try {
            // 同时支持Excel 2003、2007
            //File fileName = new File("F:/temp/testexcel.xlsx"); // 创建文件对象
            //is = new FileInputStream(excelFile); // 文件流
            checkExcelVaild(fileName);
            Workbook workbook = getWorkbok(is, fileName);
            //Workbook workbook = WorkbookFactory.create(is); // 这种方式 Excel2003/2007/2010都是可以处理的

            int sheetCount = workbook.getNumberOfSheets(); // Sheet的数量
            //遍历sheet
            for (int numSheet = 0; numSheet < sheetCount; numSheet++) {
                //设置当前excel中sheet的下标：0开始
                Sheet sheet = workbook.getSheetAt(numSheet);
                if (sheet == null) {
                    continue;
                }
                //遍历sheet中的每一行
                int rowCount = sheet.getLastRowNum();
                for (int rowNum = 1; rowNum <= rowCount; rowNum++) {
                    Row row = sheet.getRow(rowNum);
                    if (row != null) {
                        Cell cell0 = row.getCell(0);//String.valueOf(hssfCell.getStringCellValue())
                        Cell cell1 = row.getCell(1);
                        Cell cell2 = row.getCell(2);
                        Cell cell3 = row.getCell(3);
                        Cell cell4 = row.getCell(4);
                        Cell cell5 = row.getCell(5);
                        Cell cell6 = row.getCell(6);
                        Cell cell7 = row.getCell(7);

                        String str0 = getValue(cell0);
                        String str1 = getValue(cell1);
                        String str2 = getValue(cell2);
                        String str3 = getValue(cell3);
                        String str4 = getValue(cell4);
                        String str5 = getValue(cell5);
                        String str6 = getValue(cell6);
                        String str7 = getValue(cell7);

                        System.out.println("=================");
                        System.out.println(str0);
                        System.out.println(str1);
                        System.out.println(str2);
                        System.out.println(str3);
                        System.out.println(str4);
                        System.out.println(str5);
                        System.out.println(str6);
                        System.out.println(str7);
                        /*String strcellContent = "尊敬的".concat((strcellUserName == null) ? "" : strcellUserName).concat("医生，登录乐乐医提交您手持工牌的照片，即可完成医生认证，在线接单，解答患者问题，提升医生品牌，增加个人收入，戳链接:www.baidu.com");
                        if (strcellPhone != null && !"".equals(cellPhone)) {
                            if (cellPhone.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                DecimalFormat df = new DecimalFormat("0");
                                strcellPhone = df.format(cellPhone.getNumericCellValue());
                            }
                        }*/
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            is.close();
        }
    }


    private static String getValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        int cellType = cell.getCellType();
        String cellValue = "";
        switch (cellType) {
            case Cell.CELL_TYPE_STRING:     // 文本
                cellValue = cell.getRichStringCellValue().getString();
                break;
            case Cell.CELL_TYPE_NUMERIC:    // 数字、日期
                if (DateUtil.isCellDateFormatted(cell)) {
                    cellValue = fmt.format(cell.getDateCellValue());
                } else {
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    cellValue = String.valueOf(cell.getRichStringCellValue().getString());
                }
                break;
            case Cell.CELL_TYPE_BOOLEAN:    // 布尔型
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_BLANK: // 空白
                cellValue = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_ERROR: // 错误
                cellValue = "错误#";
                break;
            case Cell.CELL_TYPE_FORMULA:    // 公式
                // 得到对应单元格的公式
                //cellValue = cell.getCellFormula() + "#";
                // 得到对应单元格的字符串
                cell.setCellType(Cell.CELL_TYPE_STRING);
                cellValue = String.valueOf(cell.getRichStringCellValue().getString());
                break;
        }
        return cellValue;
    }
}
