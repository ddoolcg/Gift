package com.lcg.test;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Segment;
import net.htmlparser.jericho.Source;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * 扒小说
 *
 * @author lei.chuguang Email:475825657@qq.com
 * @version 1.0
 * @since 2017/2/24 11:12
 */
public class Book {
    public static void main(String[] args) {
        try {
            FileWriter fileWriter = new FileWriter("a.txt", false);
//            for (int i = 1937052; i <= 1978285; i++) {
//                String s = getCaptchaId(i);
//                if (s != null)
//                    fileWriter.write(s);
//                if (s != null && s.length() > 30) {
//                    System.out.println(i + "成功");
//                } else {
//                    System.err.println(i + "失败");
//                }
//            }
            biqugeStart(fileWriter, "http://www.biquge.info/25_25339/", "14115312.html");
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getCaptchaId(int i) {
        try {
            URL url = new URL("http://www.biquge.com/22_22926/" + i + ".html");
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setReadTimeout(5000);
            if (connection.getResponseCode() == 200) {
                InputStream inputStream = connection.getInputStream();
                Source source = new Source(inputStream);
                String s1 = "\n\n\n\n" + source.getFirstElement("title").getContent().toString().replace("_笔趣阁", "") + "\n\n";
                //
                Element content = source.getElementById("content");
                String s = content.getContent().toString();
                if (s != null && s.length() > 500) {
                    return s1 += s.substring(104).replace("&nbsp;", "").replace("<br/>", "\n");
                }
                return s1;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * www.biquge.com
     */
    public static void biqugeStart(FileWriter fileWriter, String path, String name) {
        try {
            URL url = new URL(path + name);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setReadTimeout(5000);
            if (connection.getResponseCode() == 200) {
                InputStream inputStream = connection.getInputStream();
                Source source = new Source(inputStream);
                String title = source.getFirstElement("title").getContent().toString().replace("_笔趣阁", "");
                String s1 = "\n\n\n\n" + title + "\n\n";
                //
                Element content = source.getElementById("content");
                if (content == null)
                    return;
                Segment content1 = content.getContent();
                if (content1 != null) {
                    String s = content1.toString();
                    if (s != null && s.length() > 500) {
                        s1 += s.substring(0).replace("&nbsp;", "").replace("<br/>", "\n").replace("<br />", "\n");
                        fileWriter.write(s1);
                        System.out.println(name + " " + title + " 成功");
                    }
                }
                //
                List<Element> elements = source.getAllElements("a");
                for (Element element : elements) {
                    if (element.getContent() != null && "下一章".equals(element.getContent().toString())) {
                        biqugeStart(fileWriter, path, element.getAttributeValue("href"));
                        return;
                    }
                }
            }
            System.err.println(name + " 失败");
            String end = ".html";
            Integer integer = Integer.parseInt(name.substring(0, name.length() - 5));
            biqugeStart(fileWriter, path, (integer + 1) + end);
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof IOException)
                biqugeStart(fileWriter, path, name);
        }
    }
}
