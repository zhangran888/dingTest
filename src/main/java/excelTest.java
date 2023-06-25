import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public class excelTest {
    public static void main(String[] args) {
        excelTest a = new excelTest();
        a.writeExcelPOI();
    }

    public void writeExcelPOI() {
        try {
            String fileName = "C:\\Users\\18379\\Downloads\\无还本续贷填报模板.xlsx";  //
            XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(fileName));
            Map<String, String> fields = new HashMap<String, String>();
            fields = getFieldMap();  //获取要修改字段的集合
            String fillStr="";    //存储aaa文件里的数据
            String[] fillSplit=null;
            XSSFSheet xSheet = xwb.getSheetAt(0);  //获取excel表的第一个sheet
            for (int i = 0; i <= xSheet.getLastRowNum(); i++) {  //遍历所有的行
                if(xSheet.getRow(i)==null){ //这行为空执行下次循环
                    continue;
                }

                for (int j = 0; j <=  xSheet.getRow(i).getPhysicalNumberOfCells(); j++) {  //遍历当前行的所有列
                    if(xSheet.getRow(i).getCell(j)==null){//为空执行下次循环
//                            System.out.println("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq");
                        continue;
                    }
                    fillStr = (xSheet.getRow(i)).getCell(j).toString();//获取当前单元格的数据


                    fillSplit=fillStr.split("");//切割，本人的数据是以"_"为分隔符的这个可以根据自己情况改变
//                        XSSFRow xRow = xSheet.createRow(i);
//                        XSSFCell xCell = xRow.createCell(j);
                    XSSFCell xCell=xSheet.getRow(i).getCell(j); //获取单元格对象，这块不能向上边那两句代码那么写，不能用createXXX，用的话会只把第一列的数据改掉
                    xCell.setCellValue(fields.get(fillSplit[0].trim())==null?fillStr:fields.get(fillSplit[0].trim()));//修改数据，看数据是否和字段集合中的数据匹配，不匹配使用元数据
//                        System.out.println(fields.get(i));
                }
            }

            FileOutputStream out = new FileOutputStream(fileName);
            xwb.write(out);
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String getValue(XSSFCell xCell) {
        if (xCell.getCellType() == XSSFCell.CELL_TYPE_BOOLEAN) {

            return String.valueOf(xCell.getBooleanCellValue());
        } else if (xCell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {

            return String.valueOf(xCell.getNumericCellValue());
        } else {

            return String.valueOf(xCell.getStringCellValue());
        }

    }

    private Map<String, String> getFieldMap(){
        Map<String, String> fields = new HashMap<String, String>();
        fields.put("order_No", "续贷合同编号（必填项）");
//            try{
//            String fileName = "D:\\yuan.xlsx";
//            XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(fileName));
//
//            XSSFSheet xSheet = xwb.getSheetAt(3);
//            for (int i = 0; i <= xSheet.getLastRowNum(); i++) {
//                fields.put(xSheet.getRow(i).getCell(0).toString(),xSheet.getRow(i).getCell(1).toString());
////                System.out.println("---"+xSheet.getRow(i).getCell(0)+"*---"+fields.get("A1"));
//                }
//            }
//            catch(Exception e){
//                e.printStackTrace();
//            }
        return fields;
    }

}


