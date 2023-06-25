package Timer;
import org.json.JSONObject;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SendMessageDing {
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://172.18.1.31:3307/zentao";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "123456";
    private static final String DINGTALK_WEBHOOK_URL = "https://oapi.dingtalk.com/robot/send?access_token=0124de2636f7b79658a0101354e2f7a061d3f87112e7850348109dd59fc15071";


//    public static void main(String[] args) {
////        sendMessage();
//    }



    public static void sendMessage(){
//        SpringApplication.run(ZentaoSentMessageApplication.class, args);
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            stmt = conn.createStatement();

//            String sql = "SELECT b.id as ID,p.`name` as 产品名称, b.title as BUG标题, b.deadline as 截止时间, u1.realname as  指派给 "
//                    + "FROM zt_bug b JOIN zt_project p ON b.project = p.id "
//                    + "JOIN zt_user u1 ON b.assignedTo = u1.account "
//                    + "WHERE b.title LIKE '%线上问题%' AND b.status = 'active' AND b.deadline != '0000-00-00' AND b.deadline<NOW()";;

            String sql = "SELECT b.id as ID,p.`name` as 产品名称, b.title as BUG标题, b.deadline as 截止时间, u1.realname as  指派给 "
                    + "FROM zt_bug b JOIN zt_project p ON b.project = p.id "
                    + "JOIN zt_user u1 ON b.assignedTo = u1.account "
                    + "WHERE b.title LIKE '%线上问题%' AND b.status = 'active'";;

            ResultSet rs = stmt.executeQuery(sql);

            StringBuilder message = new StringBuilder();
            message.append("| 产品名称 | BUG标题  | 截止时间 |  指派给 |\n");
            message.append("| :-:       | :-:       | :-:       |:-:       |\n");

            boolean hasContent = false; // 是否有内容

            while (rs.next()) {
                int id = rs.getInt("ID");
                String productName = rs.getString("产品名称");
                String bugTitle = String.format("<a href=\"http://zentao.datac.com/zentao/bug-view-%d.html\">%s</a>", id, rs.getString("BUG标题"));
                String deadline = rs.getString("截止时间");
                String assignedTo = rs.getString("指派给");
                message.append("| " + productName + " | " + bugTitle +  " | " + deadline +  " | " + assignedTo + " |\n");
                hasContent = true;
            }

            if (hasContent) {
                JSONObject json = new JSONObject();
                json.put("msgtype", "markdown");
                JSONObject markdown = new JSONObject();
                markdown.put("title", "线上超时未解决问题列表");
                markdown.put("text", message.toString());
                json.put("markdown", markdown);
                post(DINGTALK_WEBHOOK_URL, json.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (Exception e) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (Exception e) {
            }
        }
    }


    private static void post(String url, String body) {
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            con.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream(), StandardCharsets.UTF_8);
            writer.write(body);
            writer.flush();
            writer.close();
            int responseCode = con.getResponseCode();
            System.out.println("DingTalkBot: HTTP POST request to " + url + " returned " + responseCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

};