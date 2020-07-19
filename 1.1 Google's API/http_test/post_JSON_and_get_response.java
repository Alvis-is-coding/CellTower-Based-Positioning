package http_test;

import java.io.*;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class post_JSON_and_get_response {
    private final static boolean SHOW_LOGS = false;
    private static int errorCode = 0;

    public static void main(String args[]){
        for(int i = 1;i<=3;i++){
            String filename = "cell_" + i + ".json";
            try {
                byte[] data;
                data = connect_and_get_response(filename);
                show_location_on_console(data);
                outputJSONFile(data, filename);
            }catch(Exception e){

            }
        }

    }
    public static byte[] connect_and_get_response(String mFilename) throws Exception{
        try {
            JSONObject holder;
//            holder = createJSONObject();
            holder = parseJSONFile(mFilename);


            errorCode = 1;

            if(SHOW_LOGS){System.out.println(holder);}
            // 创建url资源
            URL url = new URL("https://www.googleapis.com/geolocation/v1/geolocate?key=AIzaSyAgNqYBp4NfKmN7Chp8r5NVDdiinYMtxC4");
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

            if(SHOW_LOGS){System.out.println(conn.getResponseCode());}

            // 请求返回的状态
            if (conn.getResponseCode() == 200) {
                errorCode = 4;
                if (SHOW_LOGS){System.out.println("connection success ");}
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
                if(SHOW_LOGS){System.out.println(a);}


                errorCode = 8;
                return data1;
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                errorCode = 9;
            }


        } catch (Exception e) {
            System.out.println("Error code "+ errorCode);
        }


        return null;
    }
    public static JSONObject createJSONObject() throws JSONException, IOException{

        /** 构造POST的JSON数据 */
        JSONObject holder = new JSONObject();
//            holder.put("homeMobileCountryCode", "310");
//            holder.put("homeMobileNetworkCode", "410");
        holder.put("considerIp", "false");
        holder.put("radioType", "wcdma");
//            holder.put("carrier", "EE");


        JSONObject tower = new JSONObject();
        tower.put("cellId", 14022088); //cid
        tower.put("locationAreaCode", 114); //lac
        tower.put("mobileCountryCode", 234); //mcc
        tower.put("mobileNetworkCode", 20); //mnc
        tower.put("age", 0);
        tower.put("signalStrength", -75);
//            tower.put("timingAdvance", 15);

        JSONArray towerarray = new JSONArray();
        towerarray.put(tower);
        holder.put("cellTowers", towerarray);

        return holder;
    }
    public static JSONObject parseJSONFile(String filename) throws JSONException, IOException {
        String content = new String(Files.readAllBytes(Paths.get(filename)));
        return new JSONObject(content);
    }
    public static void outputJSONFile(byte[] data1, String filename) throws JSONException, IOException{
        String outputFilename = "response_to_" + filename;

        //输出创建的json到本地
        File File = new File(outputFilename);
        //创建输出流
        FileOutputStream outStream = new FileOutputStream(File);

        //写入数据
        outStream.write(data1);

        //关闭输出流
        outStream.close();
    }
    public static void show_location_on_console(byte[] data1) throws JSONException {
        String a = new String(data1);
        JSONObject json = new JSONObject(a.toString());
        JSONObject subjosn = new JSONObject(json.getJSONObject("location").toString());
//
//                System.out.println(json.toString());
//                System.out.println(subjosn.toString());

        String accuracy = json.get("accuracy").toString();
        String latitude = subjosn.get("lat").toString();
        String longitude = subjosn.get("lng").toString();


        System.out.println("\nlatitude:\t"+ latitude);
        System.out.println("longitude:\t"+ longitude);
        System.out.println("accuracy:\t"+ accuracy);


    }


    
}