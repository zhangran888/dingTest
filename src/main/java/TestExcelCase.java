import org.json.JSONObject;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:zr
 * @CreateTime:2023-04-27
 * @Description：TODO
 */
public class TestExcelCase {

    public static void main(String[] args) {
        List<String> headers = new ArrayList<String>();
        headers.add("姓名");
        headers.add("年龄");
        headers.add("性别");
        headers.add("简介");
        headers.add("描述");

        List<List<String>> rows = new ArrayList();
        List<String> row1 = new ArrayList<String>();
        row1.add("张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三");
        row1.add("18");
        row1.add("男");
        row1.add("完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美");
        row1.add("完美完美完美完美完美完美完美完美dasdf完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美");
        rows.add(row1);

        List<String> row2 = new ArrayList<String>();
        row2.add("李四");
        row2.add("2222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222vv");
        row2.add("女");
        row1.add("完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美");
        row1.add("完美完美完美完美完美完美完美完美dasdf完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美完美");
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
        StringBuilder sb = new StringBuilder();

        // 表头
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
        return sb.toString();
    }
}
