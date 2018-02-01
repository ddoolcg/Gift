package com.lcg.test

import net.htmlparser.jericho.Source
import java.io.FileWriter
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

/**
 * 扒小说

 * @author lei.chuguang Email:475825657@qq.com
 * *
 * @version 1.0
 * *
 * @since 2017/2/24 11:12
 */
object Book {
    @JvmStatic fun main(args: Array<String>) {
        try {
            val fileWriter = FileWriter("a.txt", false)
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
            biqugeStart(fileWriter, "https://www.qu.la/book/38531/", "2635609.html")
            fileWriter.flush()
            fileWriter.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun getCaptchaId(i: Int): String? {
        try {
            val url = URL("http://www.biquge.com/22_22926/$i.html")
            val connection = url
                    .openConnection() as HttpURLConnection
            connection.readTimeout = 5000
            if (connection.responseCode == 200) {
                val inputStream = connection.inputStream
                val source = Source(inputStream)
                var s1 = "\n\n\n\n" + source.getFirstElement("title").content.toString().replace("_笔趣阁", "") + "\n\n"
                //
                val content = source.getElementById("content")
                val s = content.content.toString()
                if (s.length > 500) {
                    s1 += s.substring(104).replace("&nbsp;", "").replace("<br/>", "\n")
                }
                return s1
            }
            return null
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }

    /**
     * www.biquge.com
     */
    fun biqugeStart(fileWriter: FileWriter, path: String, name: String) {
        try {
            val url = URL(path + name)
            val connection = url
                    .openConnection() as HttpURLConnection
            connection.readTimeout = 5000
            if (connection.responseCode == 200) {
                val inputStream = connection.inputStream
                val source = Source(inputStream)
                val title = source.getFirstElement("title").content.toString().replace("_笔趣阁", "")
                var s1 = "\n\n\n\n" + title + "\n\n"
                //
                val content = source.getElementById("content") ?: return
                val content1 = content.content
                if (content1 != null) {
                    val s = content1.toString()
                    if (s.length > 500) {
                        s1 += s.substring(0).replace("&nbsp;", "").replace("<br/>", "\n").replace("<br />", "\n")
                        fileWriter.write(s1)
                        println("$name $title 成功")
                    }
                }
                //
                val elements = source.getAllElements("a")
                for (element in elements) {
                    if (element.content != null && "下一章" == element.content.toString()) {
                        biqugeStart(fileWriter, path, element.getAttributeValue("href"))
                        return
                    }
                }
            }
            System.err.println(name + " 失败")
            val end = ".html"
            val integer = Integer.parseInt(name.substring(0, name.length - 5))
            biqugeStart(fileWriter, path, (integer + 1).toString() + end)
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is IOException)
                biqugeStart(fileWriter, path, name)
        }

    }

}
