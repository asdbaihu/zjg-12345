package com.szjlxh.jlxh.zjg122345;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class ZjgRepoPageProcessor implements PageProcessor{
	
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

        page.putField("author", page.getUrl().regex("http://www.zjg12345\\.com/(\\w+)/.*").toString());
        page.putField("name", page.getHtml().xpath("//a[@class='num']/@href").toString());
        page.putField("pageInk", page.getHtml().css("#ZSGongShi_Pager > table > tbody > tr > td.num"));
        
//        page.putField("questions", page.getHtml().xpath("//font[@color='#559cfd']/text()").toString().trim());
//        page.putField("answers", page.getHtml().xpath("//td[@colspan='2']/font/text()").toString().trim());
        page.putField("table",page.getHtml().xpath("//*[@id='caseregisterz']/table[2]").toString().trim());
        page.putField("links", page.getHtml().links());
        
        
        if (page.getResultItems().get("table") == null) {
            //skip this page
            page.setSkip(true);
        }
 //       page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));
        // 部分三：从页面发现后续的url地址来抓取
        page.addTargetRequests(page.getHtml().links().regex("(http://www.zjg12345\\.com/(\\w+)/.*)").all());
	}
	
	public static void main(String[]args){
		
		Spider.create(new ZjgRepoPageProcessor())
        //从"http://www.zjg12345.com/zjgbmweb/ywxxtemplate/zskgs.aspx"开始抓
        .addUrl("http://www.zjg12345.com/zjgbmweb/ywxxtemplate/zskgs.aspx")
        //开启5个线程抓取
        .thread(5)
        //启动爬虫
        .run();
	}
}