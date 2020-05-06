package com.fangjc1986.support.util;

import com.fangjc1986.support.exception.ServiceException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;


public class ExcelUtil {

    public static Workbook getWb(MultipartFile mf) {
        String filepath = mf.getOriginalFilename();
        assert filepath != null;
        String ext = filepath.substring(filepath.lastIndexOf("."));
        Workbook wb = null;
        try {
            InputStream is = mf.getInputStream();
            if (".xls".equals(ext)) {
                wb = new HSSFWorkbook(is);
            } else if (".xlsx".equals(ext)) {
                wb = new XSSFWorkbook(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }

    /**
     * double 转换为 String
     *
     * @param val
     * @return
     */
    public static String double2String(Double val) {
        String s = String.format("%.8f", val);
        s = s.replaceAll("0+$", "");
        s = s.replaceAll("\\.$", "");
        return s;
    }

    /**
     * 尝试使用字符串获取后
     * 再次尝试使用数字格式获取
     *
     * @param row
     * @param columnIndex
     * @return
     */
    public static String getCellString(Row row, int columnIndex, boolean needThrowError) {
        try {
            return row.getCell(columnIndex).getStringCellValue();
        } catch (Exception e) {
            try {
                return double2String(row.getCell(columnIndex).getNumericCellValue());
            } catch (Exception ex) {
                if (needThrowError) throw new ServiceException(
                    String.format("第%s行第%s列数据格式错误！", row.getRowNum(), columnIndex)
                );
                return null;
            }
        }
    }


    /**
     * 尝试使用字符串获取后
     * 再次尝试使用数字格式获取
     *
     * @param row
     * @param columnIndex
     * @return
     */
    public static Integer getCellInteger(Row row, int columnIndex, boolean needThrowError) {
        String res = getCellString(row, columnIndex, needThrowError);
        try {
            return Integer.parseInt(Objects.requireNonNull(res));
        } catch (Exception e) {
            if (needThrowError) throw new ServiceException(
                String.format("第%s行第%s列数据格式错误！", row.getRowNum(), columnIndex)
            );
            return null;
        }
    }


}