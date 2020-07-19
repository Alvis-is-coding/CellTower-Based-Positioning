package com.branwn.cellularpositioning;


import android.os.AsyncTask;
import android.telephony.CellInfo;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;

import java.io.*;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class getLocation extends AsyncTask {

    public static SItude post_JSON_and_get_response(CellInfo cellInfo ){

        String radioType = "", carrier = "";
        int cID = 0, LAC = 0, MCC = 0, MNC = 0, dbm = 0;

        SItude itude = new SItude();
        if (cellInfo != null) {
            if (cellInfo instanceof CellInfoGsm) {
                radioType = "gsm";
                cID = ((CellInfoGsm) cellInfo).getCellIdentity().getCid();
                LAC = ((CellInfoGsm) cellInfo).getCellIdentity().getLac();
                MCC = ((CellInfoGsm) cellInfo).getCellIdentity().getMcc();
                MNC = ((CellInfoGsm) cellInfo).getCellIdentity().getMnc();
                dbm = ((CellInfoGsm) cellInfo).getCellSignalStrength().getDbm();
            }
            if (cellInfo instanceof CellInfoWcdma) {
                radioType = "wcdma";
                cID = ((CellInfoWcdma) cellInfo).getCellIdentity().getCid();
                LAC = ((CellInfoWcdma) cellInfo).getCellIdentity().getLac();
                MCC = ((CellInfoWcdma) cellInfo).getCellIdentity().getMcc();
                MNC = ((CellInfoWcdma) cellInfo).getCellIdentity().getMnc();
                dbm = ((CellInfoWcdma) cellInfo).getCellSignalStrength().getDbm();
            }
            if (cellInfo instanceof CellInfoLte) {
                radioType = "lte";
                cID = ((CellInfoLte) cellInfo).getCellIdentity().getCi();
                LAC = ((CellInfoLte) cellInfo).getCellIdentity().getTac();
                MCC = ((CellInfoLte) cellInfo).getCellIdentity().getMcc();
                MNC = ((CellInfoLte) cellInfo).getCellIdentity().getMnc();
                dbm = ((CellInfoLte) cellInfo).getCellSignalStrength().getDbm();
            }
        }

        int errorCode = 0;

        try {



            JSONObject holder = new JSONObject();
            holder.put("homeMobileCountryCode", MCC);
            holder.put("homeMobileNetworkCode", MNC);
            holder.put("radioType", radioType);
            holder.put("carrier", carrier);
            holder.put("considerIp", "true");

            JSONObject tower = new JSONObject();
            tower.put("cellId", cID);
            tower.put("locationAreaCode", LAC);
            tower.put("mobileCountryCode", MCC);
            tower.put("mobileNetworkCode", MNC);
            tower.put("age", 0);
            tower.put("signalStrength", dbm);
//            tower.put("timingAdvance", 15);

            JSONArray towerarray = new JSONArray();
            towerarray.put(tower);
            holder.put("cell_towers", towerarray);

            errorCode = 1;
//            System.out.println(holder);

            URL url = new URL("https://www.googleapis.com/geolocation/v1/geolocate?key=AIzaSyB-luioErFZmQFYlMPZfSWg3DVWWvvxwAI");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setDoOutput(true);

            conn.setDoInput(true);

            conn.setChunkedStreamingMode(0);

            conn.setAllowUserInteraction(false);

            conn.setInstanceFollowRedirects(true);


            conn.setUseCaches(false);

            conn.setRequestMethod("POST");

            conn.setRequestProperty("Connection", "Keep-Alive");

            conn.setRequestProperty("Charset", "UTF-8");

            byte[] data = (holder.toString()).getBytes();

            conn.setRequestProperty("Content-Length", String.valueOf(data.length));


            conn.setRequestProperty("Content-Type", "application/json");

            errorCode = 2;

            conn.connect();
            errorCode = 21;
            OutputStream  out = conn.getOutputStream();
            errorCode = 22;

            out.write((holder.toString()).getBytes());
            errorCode = 23;
            out.flush();
            errorCode = 24;
            out.close();

            errorCode = 3;

            System.out.println(conn.getResponseCode());


            if (conn.getResponseCode() == 200) {
                errorCode = 4;
                System.out.println("connection succeeded ");
            } else {
                System.out.println("no++");
            }

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

                JSONObject json = new JSONObject(a.toString());
                JSONObject subjosn = new JSONObject(json.getJSONObject("location").toString());

                String latitude = subjosn.get("lat").toString();
                String longitude = subjosn.get("lng").toString();
                String accuracy = json.get("accuracy").toString();

                if (latitude!=null){
                    itude.latitude = latitude;
                    itude.longitude = longitude;
                    itude.accuracy = accuracy;
                }




                errorCode = 8;
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                errorCode = 9;
            }


        } catch (Exception e) {

            itude.accuracy = "Error Code "+ Integer.toString(errorCode);
        }



        return itude;

    }

    @Override
    protected Object doInBackground(Object[] objects) {


        return null;
    }
}