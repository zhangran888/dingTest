import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author:zr
 * @CreateTime:2023-04-27
 * @Description：TODO
 */
public class TestExcelCase2 {

    public static void main(String[] args) {
        List<String> headers = new ArrayList<String>();
        headers.add("姓名");
        headers.add("年龄");
        headers.add("性别");

        List<List<String>> rows = new ArrayList();
        List<String> row1 = new ArrayList<String>();
        row1.add("张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三");
        row1.add("18");
        row1.add("男");
        rows.add(row1);

        List<String> row2 = new ArrayList<String>();
        row2.add("李四");
        row2.add("2222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222vv");
        row2.add("女");
        rows.add(row2);

        sendTableMessage("线上问题报告", headers, rows);

//        getTableMarkdown(headers,rows);

    }

    public static void sendTableMessage(String title, List<String> headers, List<List<String>> rows) {
        try {
            URL url = new URL("https://oapi.dingtalk.com/robot/send?access_token=0124de2636f7b79658a0101354e2f7a061d3f87112e7850348109dd59fc15071");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());

            StringBuilder sb = new StringBuilder();
            sb.append("{\n");
            sb.append("\"msgtype\": \"markdown\",\n");
            sb.append("\"markdown\": {\n");
            sb.append("\"title\":\"").append(title).append("\",\n");
            sb.append("\"text\": \"");

            sb.append(getTableMarkdown(headers, rows));
//            sb.append("\",\n");
//            sb.append("\"pc_only\": \"true\",\n");
//            sb.append("\"width\": \"1600\",\n"); // 设置组件宽度为 600 像素
//            sb.append("\"height\": \"1400\n"); // 设置组件高度为 400 像素

            sb.append("\"\n}\n}");

            writer.write(sb.toString());
            writer.flush();
            conn.getResponseCode();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getTableMarkdown(List<String> headers, List<List<String>> rows) {
//        StringBuilder sb = new StringBuilder();

       /* // 表头
        sb.append("|");
        for (String header : headers) {
            sb.append(header).append("|");
        }
        sb.append("\n");

        // 分隔线
        sb.append("|");
        for (int i = 0; i < headers.size(); i++) {
            sb.append(":---:|");
        }
        sb.append("\n");

        // 行数据
        for (List<String> row : rows) {
            sb.append("|");
            for (String cell : row) {
                sb.append(cell).append("|");
            }
            sb.append("\n");
        }
        return sb.toString();*/

        // 计算每个列的最大宽度
        int[] columnWidths = {0, 0, 0};
        for (int i = 0; i < rows.size(); i++) {
            for (int j = 0; j < rows.get(i).size(); j++) {
                String cellText = rows.get(i).get(j);
                int cellLength = cellText.length();
                if (cellLength > columnWidths[j]) {
                    columnWidths[j] = cellLength;
                }
            }
            // 更新列头最大宽度
            String headerText = headers.get(i);
            int headerLength = headerText.length();
            if (headerLength > columnWidths[i]) {
                columnWidths[i] = headerLength;
            }
        }

// 计算表格宽度和列边距
        int tableWidth = Arrays.stream(columnWidths).sum() + columnWidths.length * 3 + 1;
        String hMargin = repeatChar(' ', 2);


// 拼接表格头
        StringBuilder sb = new StringBuilder();
        sb.append("|");
        for (int i = 0; i < headers.size(); i++) {
            String format = "|%-" + (columnWidths[i] + 2) + "s";
            sb.append(String.format(format, hMargin + headers.get(i) + hMargin));
        }
        sb.append("|\n");

// 拼接分隔行
        sb.append("|");
        for (int i = 0; i < headers.size(); i++) {
            String separator = repeatChar('-', columnWidths[i] + 2);
            sb.append(separator + "|");
        }
        sb.append("\n");

// 拼接内容行
        for (List<String> row : rows) {
            sb.append("|");
            for (int j = 0; j < row.size(); j++) {
                String format = "|%-" + (columnWidths[j] + 2) + "s";
                sb.append(String.format(format, hMargin + row.get(j) + hMargin));
            }
            sb.append("|\n");
        }
        return sb.toString();

    }

    public static String repeatChar(char ch, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(ch);
        }
        return sb.toString();
    }
}
