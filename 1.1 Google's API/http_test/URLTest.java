package http_test;


import java.awt.image.ImageProducer;
import java.net.URL;

public class URLTest {
    public static void main(String[] args) {
        try {
            //根据地址创建
            URL url=new URL("http://www.baidu.com/img/baidu_jgylogo3.gif");
            //取得信息
            System.out.println("url.getAuthority()\t " + url.getAuthority());
            System.out.println("url.getPath()\t "+url.getPath());
            System.out.println("url.getPort()\t "+url.getPort());
            System.out.println("url.getDefaultPort()\t "+url.getDefaultPort());
            System.out.println("url.getFile()\t "+url.getFile());
            System.out.println("url.getProtocol()\t "+url.getProtocol());
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}


