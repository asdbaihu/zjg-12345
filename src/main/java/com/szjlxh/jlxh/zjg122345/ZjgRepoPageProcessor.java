package com.szjlxh.jlxh.zjg122345;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;


public class ZjgRepoPageProcessor implements PageProcessor {

    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    public Site getSite() {
        // TODO Auto-generated method stub
        return site;
    }

    // process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
    public void process(Page page) {
        // TODO Auto-generated method stub
        // 部分二：定义如何抽取页面信息，并保存下来

//        page.putField("author", page.getUrl().regex("http://www.zjg12345\\.com/ywxxtemplate/zskgs.aspx\"/(\\w+)/.*").toString());
//        page.putField("name", page.getHtml().xpath("//a[@class='num']/@href").toString());

        List listQuestions = page.getHtml().css("#ZSGongShi_dlstFeedBack > tbody > tr> td > table > tbody > tr:nth-child(1) > td:nth-child(1)> font","text").all();
        List listAnswers = page.getHtml().css("#ZSGongShi_dlstFeedBack > tbody > tr> td > table > tbody > tr:nth-child(2) > td >font","text").all();
        List listDepartments = page.getHtml().css("#ZSGongShi_dlstFeedBack > tbody > tr> td > table > tbody > tr > td> font:nth-child(2)","text").all();

        List departments = page.getHtml().css("#ZSGongShi_dept>option","text").all();

        for(Object u : departments){
            System.out.println(u.toString());
        }

//        for (int i = 0; i<listQuestions.size();i++) {
//            System.out.println(i+"."+listQuestions.get(i).toString());
//            System.out.println(" "+listAnswers.get(i).toString());
//            System.out.println(" "+listDepartments.get(i).toString());
//        }


//        if (page.getResultItems().get("question") == null) {
//            page.setSkip(true);
//        }
        //       page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));
        // 部分三：从页面发现后续的url地址来抓取
        page.addTargetRequests(page.getHtml().links().regex("(http://www.zjg12345\\.com/ywxxtemplate/zskgs.aspx\"/(\\w+)/.*)").all());
    }

    public static void main(String[] args) {

        Spider.create(new ZjgRepoPageProcessor())
                //从"http://www.zjg12345.com/zjgbmweb/ywxxtemplate/zskgs.aspx"开始抓
                .addUrl("http://www.zjg12345.com/zjgbmweb/ywxxtemplate/zskgs.aspx")
                //开启5个线程抓取
                .thread(5)
                //启动爬虫
                .run();
    }
}