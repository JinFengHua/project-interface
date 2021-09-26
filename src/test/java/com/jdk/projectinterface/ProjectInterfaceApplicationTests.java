package com.jdk.projectinterface;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.thymeleaf.util.StringUtils;
import sun.net.www.http.HttpClient;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
class ProjectInterfaceApplicationTests {

    @Autowired
    public KService service;


    @Test
    public void test(){
        service.doUpdateCourse();
    }

    @Test
    public void test1(){
        String s = "高二年级14班\n" +
                "高二年级15班\n" +
                "高二年级1班\n" +
                "高二年级2班";
        String pattern = "[^0-9]";
        Matcher matcher = Pattern.compile(pattern).matcher(s);
        System.out.println(matcher.replaceAll("").trim());
    }

    private List<String> list = new ArrayList<>();
    private List<String> errorList = new ArrayList<>();

    @Test
    public void getCity() {
        String html = getNetData("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2020/33.html");
        Document document = Jsoup.parse(html);
        Elements citytable = document.getElementsByClass("citytable");
        for (Element element : citytable) {
            Elements citytrs = element.getElementsByClass("citytr");
            for (Element citytr : citytrs) {
                String href = citytr.select("a").attr("href");
                String value = citytr.select("a").text();
                String cityValue = value.replaceAll(" ",",");
                getCounty(href, cityValue);
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (String s : list) {
            stringBuilder.append(s);
            stringBuilder.append("\n");
        }

        stringBuilder.append("错误链接：\n");
        for (String s : errorList) {
            stringBuilder.append(s);
            stringBuilder.append("\n");
        }
        writeTxt(stringBuilder.toString());
    }

    public void getCounty(String url, String str){
        url = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2020/" + url;
        String nextUrl = url.substring(0, url.lastIndexOf("/") + 1);
//        http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2020/33/01/330102.html
        String html = getNetData(url);
        if (StringUtils.isEmpty(html)){
            list.add(str);
            return;
        }
        Document document = Jsoup.parse(html);
        Elements citytable = document.getElementsByClass("countytable");
        for (Element element : citytable) {
            Elements citytrs = element.getElementsByClass("countytr");
            for (Element citytr : citytrs) {
                String href = citytr.select("a").attr("href");
                String value = citytr.select("a").text();
                value = value.replaceAll(" ",",");
                value = str + "," + value;
                if (StringUtils.isEmpty(href)){
                    value = citytr.select("td").text();
                    value = value.replaceAll(" ",",");
                    value = str + "," + value;
                    list.add(value);
                    continue;
                }
                getTown(nextUrl + href,value);
            }
        }
    }

    public void getTown(String url, String str){
        String nextUrl = url.substring(0, url.lastIndexOf("/") + 1);
        String html = getNetData(url);
        Document document = Jsoup.parse(html);
        Elements citytable = document.getElementsByClass("towntable");
        for (Element element : citytable) {
            Elements citytrs = element.getElementsByClass("towntr");
            for (Element citytr : citytrs) {
                String href = citytr.select("a").attr("href");
                String value = citytr.select("a").text();
                value = value.replaceAll(" ",",");
                value = str + "," + value;
                if (StringUtils.isEmpty(href)){
                    value = citytr.select("td").text();
                    value = value.replaceAll(" ",",");
                    value = str + "," + value;
                    list.add(value);
                    continue;
                }
                getVillage(nextUrl + href, value);
            }
        }
    }

    public void getVillage(String url, String str){
        String html = getNetData(url);
        System.out.println("当前访问链接：" + url);
        if (StringUtils.isEmpty(html)){
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            html = getNetData(url);
            if (StringUtils.isEmpty(html)){
                errorList.add(html);
            }
        }
        Document document = Jsoup.parse(html);
        Elements citytable = document.getElementsByClass("villagetable");
        for (Element element : citytable) {
            Elements citytrs = element.getElementsByClass("villagetr");
            for (Element citytr : citytrs) {
                String href = citytr.select("a").attr("href");
                String value = citytr.select("a").text();
                value = value.replaceAll(" ",",");
                value = str + "," + value;
                if (StringUtils.isEmpty(href)){
                    value = citytr.select("td").text();
                    value = value.replaceAll(" ",",");
                    value = str + "," + value;
                    list.add(value);
                    continue;
                }
            }
        }
    }

    @Test
    public void writeTxt(String data){
        try{
            File file =new File("result2.txt");

            //if file doesnt exists, then create it
            if(!file.exists()){
                file.createNewFile();
            }

            //true = append file
            FileWriter fileWritter = new FileWriter(file.getName(),true);
            fileWritter.write(data);
            fileWritter.close();

            System.out.println("Done");

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public String getNetData(String url){
        String html = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        try {
            // 第一步：创建HttpClient对象
            httpClient = HttpClients.createDefault();
            // 第二步：拼接请求参数并创建httpPost对象
            URIBuilder builder = new URIBuilder(url);
            HttpGet httpGet = new HttpGet(builder.build());
            // 第三步：给httpPost设置JSON格式的参数
            httpGet.setHeader("Content-type", "application/json");
            // 第四步：发送HttpPost请求，获取返回值,调接口获取返回值时，必须用此方法
            String result = httpClient.execute(httpGet, responseHandler);
            html = new String(result.getBytes(StandardCharsets.ISO_8859_1), "gb2312");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return html;
    }
}