import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

/**
 * @Author:zr
 * @CreateTime:2023-04-24
 * @Description：使用钉钉机器人进行消息发送
 */
public class TestDing {
    public static void main(String[] args) throws IOException {
        testMarkDownDing();
    }


    public static void testMarkDownDing() throws IOException {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");

        String webhookUrl = "https://oapi.dingtalk.com/robot/send?access_token=0124de2636f7b79658a0101354e2f7a061d3f87112e7850348109dd59fc15071"; // 钉钉机器人的webhook URL

        JSONObject json = new JSONObject();
        json.put("msgtype", "markdown");

        JSONObject text = new JSONObject();
        text.put("content", "线上问题报告 Hello, World! "); // 发送的消息内容，消息内容中需要匹配关键字，否则无法发送成功
        json.put("text", text);


        Request request = new Request.Builder()
                .url(webhookUrl)
                .post(RequestBody.create(mediaType, json.toString())) // 将json串转成请求体RequestBody
                .build();

        Response response = client.newCall(request).execute();

        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code " + response);
        }

        System.out.println(response.body().string());
    }


    public static void testTxtDing() throws IOException {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");

        String webhookUrl = "https://oapi.dingtalk.com/robot/send?access_token=0124de2636f7b79658a0101354e2f7a061d3f87112e7850348109dd59fc15071"; // 钉钉机器人的webhook URL

        JSONObject json = new JSONObject();
        json.put("msgtype", "text");

        JSONObject text = new JSONObject();
        text.put("content", "线上问题报告 Hello, World! "); // 发送的消息内容，消息内容中需要匹配关键字，否则无法发送成功
        json.put("text", text);


        Request request = new Request.Builder()
                .url(webhookUrl)
                .post(RequestBody.create(mediaType, json.toString())) // 将json串转成请求体RequestBody
                .build();

        Response response = client.newCall(request).execute();

        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code " + response);
        }

        System.out.println(response.body().string());
    }
}
