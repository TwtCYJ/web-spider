package com.twst.spider.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.twst.spider.model.MJBInfo;
import org.jsoup.Jsoup;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: ${description}
 * @author: chenyingjie
 * @create: 2018-12-27 15:48
 **/
public class NewsPageProcessor implements PageProcessor {

    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);
    private static final String domainUrl = "http://roll.hexun.com/roolNews_listRool.action?type=all&ids=120&date=2018-12-27&page=1&tempTime=51529940";
    private static Map<String, JSONObject> map = new HashMap<>();

    // process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
    @Override
    public void process(Page page) {
        MJBInfo info = new MJBInfo();
        if (page.getUrl().toString().equals(domainUrl)) {
            List<String> list = new JsonPathSelector("$.list").selectList(page.getRawText());
            List<String> urls = new ArrayList<>();
            for (String s : list) {
                JSONObject object = JSON.parseObject(s);
                String target = object.getString("titleLink");
                map.put(target, object);
                urls.add(target);
            }
            page.addTargetRequests(urls);
        } else {
            String html = page.getHtml().xpath("/html/body/div[7]/div[1]/div[1]/div[1]").toString();
            String cont = Jsoup.parse(html).text();
            String auth = cont.substring(cont.indexOf("责任编辑：") + 5, cont.length() - 1);
            String content = cont.substring(0, cont.indexOf("责任编辑：") - 1);
            if (map.containsKey(page.getUrl().toString())) {
                JSONObject object = map.get(page.getUrl().toString());
                String title = object.getString("title");
                String the_data = object.getString("time");
                info.setThe_data(the_data);
                info.setTitle(title);
                info.setContent(content);
                info.setAuth(auth);
                info.setCreate_time((int) System.currentTimeMillis());
//                try {
//                    Db.use().insert(Entity.create("bd_mjb_infomation")
//                            .set("title", title).set("content", content)
//                            .set("auth", auth).set("the_data", the_data)
//                            .set("type", 1));
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
                page.putField("info",info);
            }
        }
    }


    @Override
    public Site getSite() {
        return site;
    }


    public static void main(String[] args) {
        Spider.create(new NewsPageProcessor())
                .addUrl(domainUrl)
                .addPipeline(new JsonFilePipeline("/Users/chenyingjie/IdeaProjects/web-spider/db/"))
                .thread(5)
                .run();
    }
}
