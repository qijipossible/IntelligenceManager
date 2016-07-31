package spider.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import javax.management.JMException;

import properties.Configure;
import service.DataManager;
import service.SiteManager;
import spider.monitor.MonitorManager;
import spider.monitor.MySpiderMonitor;
import spider.pipeline.ConsolePipeline;
import spider.pipeline.MysqlPipeline;
import spider.processor.GeneralProcessor;
import spider.processor.SearchListProcessor;
import us.codecraft.webmagic.Spider;

public class Crawler {
	
	Spider siteSpider;
	Spider[] pageSpiders;
	MySpiderMonitor spiderMonitor;
	MonitorManager monitorManager;
	
	public Crawler(){
		spiderMonitor = (MySpiderMonitor) MySpiderMonitor.instance();
		monitorManager = MonitorManager.getInstance();
	}
	
	public void start(){

		String keyword = DataManager.getKeyword();
		siteSpider = Spider.create(new SearchListProcessor())
				.addUrl("http://cn.bing.com/search?q="+ keyword +"%22+filetype%3Ahtml")
				.addPipeline(new ConsolePipeline())
				.thread(1);
		try {
			spiderMonitor.register(siteSpider);
			monitorManager.setSiteSearchSpider(siteSpider);
		} catch (JMException e1) {
		}
		siteSpider.run();
		System.out.print("寻找网站结束，共找到"+SiteManager.getSitesSize()+"个网站\n");
		
		List<String> sites = SiteManager.getSites();
		LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(sites);
		//并行的爬虫数量
		int spiderNum = Configure.SPIDER_NUMBER;
		pageSpiders = new Spider[spiderNum];
		//每个爬虫分到的网站数
		int each = sites.size()/spiderNum + 1;
		//每一个爬虫
		for (int i=0;i<spiderNum;i++) {
			//每个爬虫分到each个网站
			ArrayList<String> list = new ArrayList<String>();
			for(int j=0;j<each;j++){
				if(queue.peek() == null) break;
				list.add("http://cn.bing.com/search?q=site%3A"+queue.poll()+"+%22"+keyword+"%22+filetype%3Ahtml");
			}
			String[] urls = list.toArray(new String[list.size()]);
			pageSpiders[i] = Spider.create(new GeneralProcessor())
					.addUrl(urls)
					.addPipeline(new MysqlPipeline())
					.thread(2);
			try {
				spiderMonitor.register(pageSpiders[i]);
			} catch (JMException e) {
			}
			pageSpiders[i].start();
		}
		monitorManager.setSpiders(pageSpiders);
	}
	
	public void stop(){
		if(siteSpider != null) siteSpider.stop();
		if(pageSpiders == null) return;
		for(int i=0;i<pageSpiders.length;i++){
			if(pageSpiders[i] == null) continue;
			pageSpiders[i].stop();
		}
	}

	public static void main(String[] args) {
		new Crawler();
	}

}
