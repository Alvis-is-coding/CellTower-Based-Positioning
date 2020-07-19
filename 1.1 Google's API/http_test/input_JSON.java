package http_test;

import java.io.*;

import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class input_JSON {
    public static JSONObject parseJSONFile(String filename) throws JSONException, IOException {
        String content = new String(Files.readAllBytes(Paths.get(filename)));
        return new JSONObject(content);
    }

    public static void main(String[] args) throws IOException, JSONException {
        String filename = "response.json";
        JSONObject jsonObject = parseJSONFile(filename);
        System.out.println(jsonObject.toString());
        //do anything you want with jsonObject
    }


}

