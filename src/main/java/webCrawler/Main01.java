package webCrawler;

import com.github.slugify.Slugify;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main01 {

    static final String MAIN_PAGE_URL = "https://www.infoworld.com";


    public static void main(String[] args) {

        Map<String, String> map = mapArticles(MAIN_PAGE_URL + "/category/java");

        Set<String> links = map.keySet();
        String articleContent;
        String uuid;
        for (String link : links) {
//            System.out.println(link);
            articleContent = loadArticleContent(link);
            uuid = UUID.randomUUID().toString();
//            System.out.println(uuid);
//            System.out.println(articleContent);
            saveDataToFile(articleContent, uuid + "-" + map.get(link));
        }
    }



    public static Map<String, String> mapArticles(String mainAdressURL) {
        Map<String, String> articleMap = new HashMap<>();
        try {
            Document doc = Jsoup.connect(mainAdressURL).get();
            Elements ele = doc.select("div.article h3 a");

            for (Element e : ele) {
                String link = MAIN_PAGE_URL + e.attr("href");
                String title = e.text();
                articleMap.put(link, title);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return articleMap;
    }


    public static String loadArticleContent(String articleUrl) {
        StringBuilder content = new StringBuilder();
        try {
            Document doc = Jsoup.connect(articleUrl).get();
            Elements ele = doc.select("div[id=drr-container] p");
            for (Element e : ele) {
                content.append(e.text());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }


    public static void saveDataToFile(String data, String filenameToSlug) {
        Slugify slg = Slugify.builder().build();
        String sluggedFilename = slg.slugify(filenameToSlug);

        File file = new File(sluggedFilename);
        try {
            FileUtils.writeStringToFile(file, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
