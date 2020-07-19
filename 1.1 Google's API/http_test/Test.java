package http_test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Test {
    public static void main(String[] args) throws Exception {
        URL url = new URL("http://pic29.nipic.com/20130514/12477194_083818249176_2.jpg");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        InputStream in = connection.getInputStream();
        FileOutputStream out = new FileOutputStream(new File("d://1.jpg"));
        int temp = 0;
        while ((temp = in.read()) != -1) {
            out.write(temp);
        }
        in.close();
        out.close();
    }
}