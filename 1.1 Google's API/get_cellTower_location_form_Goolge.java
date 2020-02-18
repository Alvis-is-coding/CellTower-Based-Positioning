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



            //Create JSON data for POST
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
            // create url
            URL url = new URL("https://www.googleapis.com/geolocation/v1/geolocate?key=AIzaSyB-luioErFZmQFYlMPZfSWg3DVWWvvxwAI");
            // setup http conncetion
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // set to allow output
            conn.setDoOutput(true);

            conn.setDoInput(true);

            // set don't use cache
            conn.setUseCaches(false);
            // set request method
            conn.setRequestMethod("POST");
            // keep connection alive
            conn.setRequestProperty("Connection", "Keep-Alive");
            
            conn.setRequestProperty("Charset", "UTF-8");
            // convert into byte[]
            byte[] data = (holder.toString()).getBytes();
            // set content length
            conn.setRequestProperty("Content-Length", String.valueOf(data.length));

            // set content type
            conn.setRequestProperty("Content-Type", "application/json");

            errorCode = 2;
            // start request connetion
            conn.connect();
            OutputStream  out = conn.getOutputStream();
            // request input data / bytes
            out.write((holder.toString()).getBytes());
            out.flush();
            out.close();

            errorCode = 3;

            System.out.println(conn.getResponseCode());

            // request response code
            if (conn.getResponseCode() == 200) {
                errorCode = 4;
                System.out.println("connection succeeded ");
            } else {
                System.out.println("no++");
            }
            // request response data
            errorCode = 5;
            InputStream in = conn.getInputStream();
            errorCode = 6;
            String a = null;
            errorCode = 7;
            try {
                byte[] data1 = new byte[in.available()];
                in.read(data1);
                // convert into JSON
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


                
                // create a JSON file to store
                File File = new File("response.json");
                // output stream
                
                FileOutputStream outStream = new FileOutputStream(File);
                // input data into the file
                
                outStream.write(data1);

                // close stream
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