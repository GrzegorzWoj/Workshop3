package webCrawler;

import com.github.slugify.Slugify;

public class test {

    public static void main(String[] args) {
        Slugify slg = Slugify.builder().build();
        String sluggedFilename = slg.slugify("filename To Slug ?");
        System.out.println(sluggedFilename);
    }
}
