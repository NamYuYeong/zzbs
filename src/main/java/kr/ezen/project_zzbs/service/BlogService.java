package kr.ezen.project_zzbs.service;

import kr.ezen.project_zzbs.domain.Blog;
import kr.ezen.project_zzbs.dto.PlaceDTO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BlogService {

    @PostMapping
    public List<Blog> getBlogDatas(PlaceDTO placeDTO) throws IOException {
        String blog_Url = "https://search.naver.com/search.naver?ssc=tab.blog.all&sm=tab_jum&query=";

        String url = blog_Url+ placeDTO.getPlace_name();

        List<Blog> blogList = new ArrayList<>();
        Document document = Jsoup.connect(url).get();

        Elements contents = document.select("section.sc_new div.api_subject_bx ul.lst_view li.bx div.detail_box");
        for (Element content : contents) {
            Blog blog = Blog.builder()
                    .btitle(content.select("div.title_area a.title_link").text())
                    .bdescription(content.select("div.dsc_area a.dsc_link").text())
                    .url(content.select("a").attr("abs:href"))
                    .build();
            blogList.add(blog);
        }

        return blogList;
    }
}
