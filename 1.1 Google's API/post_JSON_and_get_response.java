import java.io.*;

import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class post_JSON_and_get_response {
    public static void main(String args[])
    {
        int errorCode = 0;
        try {


            /** 构造POST的JSON数据 */
            JSONObject holder = new JSONObject();
//            holder.put("homeMobileCountryCode", "310");
//            holder.put("homeMobileNetworkCode", "410");
            holder.put("considerIp", "true");
            holder.put("radioType", "wcdma");
            holder.put("carrier", "EE");


            JSONObject tower = new JSONObject();
            tower.put("cellId", 57734166);
            tower.put("locationAreaCode", 125);
            tower.put("mobileCountryCode", 234);
            tower.put("mobileNetworkCode", 15);
            tower.put("age", 0);
            tower.put("signalStrength", -65);
//            tower.put("timingAdvance", 15);

            JSONArray towerarray = new JSONArray();
            towerarray.put(tower);
            holder.put("cell_towers", towerarray);

            errorCode = 1;
            System.out.println(holder);
            // 创建url资源
            URL url = new URL("https://www.googleapis.com/geolocation/v1/geolocate?key=AIzaSyB-luioErFZmQFYlMPZfSWg3DVWWvvxwAI");
            // 建立http连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 设置允许输出
            conn.setDoOutput(true);

            conn.setDoInput(true);

            // 设置不用缓存
            conn.setUseCaches(false);
            // 设置传递方式
            conn.setRequestMethod("POST");
            // 设置维持长连接
            conn.setRequestProperty("Connection", "Keep-Alive");
            // 设置文件字符集:
            conn.setRequestProperty("Charset", "UTF-8");
            //转换为字节数组
            byte[] data = (holder.toString()).getBytes();
            // 设置文件长度
            conn.setRequestProperty("Content-Length", String.valueOf(data.length));

            // 设置文件类型:
            conn.setRequestProperty("Content-Type", "application/json");

            errorCode = 2;
            // 开始连接请求
            conn.connect();
            OutputStream  out = conn.getOutputStream();
            // 写入请求的字符串
            out.write((holder.toString()).getBytes());
            out.flush();
            out.close();

            errorCode = 3;

            System.out.println(conn.getResponseCode());

            // 请求返回的状态
            if (conn.getResponseCode() == 200) {
                errorCode = 4;
                System.out.println("connection succeeded ");
            } else {
                System.out.println("no++");
            }
            // 请求返回的数据
            errorCode = 5;
            InputStream in = conn.getInputStream();
            errorCode = 6;
            String a = null;
            errorCode = 7;
            try {
                byte[] data1 = new byte[in.available()];
                in.read(data1);
                // 转成JSON
                a = new String(data1);
                System.out.println(a);


                JSONObject json = new JSONObject(a.toString());
                JSONObject subjosn = new JSONObject(json.getJSONObject("location").toString());

                System.out.println(json.toString());
                System.out.println(subjosn.toString());

                String accuracy = json.get("accuracy").toString();
                String latitude = subjosn.get("lat").toString();
                String longitude = subjosn.get("lng").toString();

                System.out.println("accuracy: "+ accuracy);
                System.out.println("latitude: "+ latitude);
                System.out.println("longitude: "+ longitude);


                //输出创建的json到本地
                File File = new File("response.json");
                //创建输出流
                FileOutputStream outStream = new FileOutputStream(File);

                //写入数据
                outStream.write(data1);

                //关闭输出流
                outStream.close();


                errorCode = 8;
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                errorCode = 9;
            }


        } catch (Exception e) {
            System.out.println("Error code "+ errorCode);
        }

    }
}