package top.minecode.service.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

public class HttpHelper {

    public static String send(String url, String param) {
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        try {
            String urlString = url + "?" + param;
            URL realUrl = new URL(urlString);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine())!= null) {
                result.append(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result.toString();
    }

    public static String urlEncode(Map<String, String> dataItems) {
        StringBuffer buffer = null;
        try {
            for (String key : dataItems.keySet()) {
                if (buffer == null) {
                    buffer = new StringBuffer();
                    buffer.append(URLEncoder.encode((key), "UTF-8")).append("=")
                            .append(URLEncoder.encode(dataItems.get(key), "UTF-8"));
                } else {
                    buffer.append("&").append(URLEncoder.encode((key), "UTF-8"))
                            .append("=").append(URLEncoder.encode(dataItems.get(key), "UTF-8"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer == null ? "" : buffer.toString();
    }

}
