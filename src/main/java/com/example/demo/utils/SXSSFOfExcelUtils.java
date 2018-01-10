package com.example.demo.utils;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.FileOutputStream;
import java.util.Date;

/**
 * Created by 寇含尧 on 2017/12/21.
 *
 * SXSS和XSSF均属于Apache的poi,但是sxss写数据,优势是可以写入大量的数据;而xssf既可以读,也可以写.
 */

public class SXSSFOfExcelUtils {

    /**
     * 创建表头
     * @return
     */
    public static void createTableHeader(SXSSFSheet sheet)
    {
        String[] tableHeader = {"序号","版本","接收时刻","设备","入接口","出接口",
                "源IP","目的IP","下一跳","协议","端口","对端端口","TOS","源AS","目的AS","TCP_FLAG","pad1","pad2"};
        SXSSFRow headerRow = sheet.createRow((short) 0);
        for(int i = 0;i < tableHeader.length;i++)
        {
            SXSSFCell headerCell = headerRow.createCell((short) i);
            headerCell.setCellValue(tableHeader[i]);
        }
    }

    public static void main(String[] args) throws Throwable {
        // keep 100 rows in memory, exceeding rows will be flushed to disk
        SXSSFWorkbook wb = new SXSSFWorkbook(100);

        //POI设置EXCEL单元格格式为文本、小数、百分比、货币、日期、科学计数法和中文大写
        //http://javacrazyer.iteye.com/blog/894850
        //此处转换为时间格式为2017/12/21  17:09:20
        CreationHelper createHelper = wb.getCreationHelper();
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("m/d/yy h:mm:ss"));

        //创建sheet
        SXSSFSheet sh = wb.createSheet("new sheet");
        createTableHeader(sh);
        for (int rownum = 0; rownum < 1000; rownum++) {
            SXSSFRow row = sh.createRow(rownum+1);
            for (int cellnum = 0; cellnum < 10; cellnum++) {
                if (rownum == 0) {
                    // 首行
                    //row.createCell(rownum).setCellValue("column" + rownum);
                } else {
                    // 数据
                    if (cellnum == 0) {
                        row.createCell(cellnum).setCellValue(254215151);
                    } else if(cellnum == 1){
                        row.createCell(cellnum).setCellValue(Math.random());
                    }else if(cellnum == 2){
                        SXSSFCell sxssfCell = row.createCell(cellnum);
                        sxssfCell.setCellValue(new Date());
                        sxssfCell.setCellStyle(cellStyle);
                    }else if(cellnum == 3){
                        CellUtil.createCell(row, cellnum, String.valueOf(new Date()), cellStyle);
                    }else if(cellnum == 4){
                        CellUtil.createCell(row, cellnum, String.valueOf(Math.random()));
                    }else if(cellnum == 5){
                        CellUtil.createCell(row, cellnum, String.valueOf(Math.random()));
                    }else if(cellnum == 6){
                        CellUtil.createCell(row, cellnum, "adDDD");
                    }else if(cellnum == 7){
                        CellUtil.createCell(row, cellnum, String.valueOf(Math.random()));
                    }else if(cellnum == 8){
                        CellUtil.createCell(row, cellnum, String.valueOf(Math.random()));
                    }else if(cellnum == 9){
                        CellUtil.createCell(row, cellnum, String.valueOf(Math.random()));
                    }
                }
            }

        }

        FileOutputStream out = new FileOutputStream("F:/temp/sxssf.xlsx");
        wb.write(out);
        out.close();

        // dispose of temporary files backing this workbook on disk
        wb.dispose();
    }
}
