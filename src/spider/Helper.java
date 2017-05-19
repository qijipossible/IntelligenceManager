package spider;

import spider.monitor.SpiderMonitor;
import us.codecraft.webmagic.Spider;

public class Helper {

	Spider spider;
	SpiderMonitor spiderMonitor;

	public void startCrawling(String... keywords) {

	}

	public int stopCrawling() {
		if (spider != null) {
			spider.stop();
			spider = null;
			return 1;
		} else {
			return 0;
		}
	}
}
