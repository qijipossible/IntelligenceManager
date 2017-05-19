package spider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import spider.monitor.SpiderMonitor;
import spider.monitor.SpiderStatusMXBean;

public class SpiderBean {
	
	private static SpiderBean instance;
	
	private SpiderMonitor spiderMonitor;
	private List<SpiderStatusMXBean> spiderStatuses;
	private Map<String, Integer> indexes;
	
	private SpiderBean() {
		indexes = new HashMap<String, Integer>();
		this.spiderMonitor = SpiderMonitor.getInstance();
		spiderStatuses = spiderMonitor.spiderStatuses;
	}
	
	public Map<String, Integer> getIndexes(){
		int scanned = 0, downloaded = 0, left = 0;
		for(SpiderStatusMXBean spiderStatus : spiderStatuses){
			scanned += spiderStatus.getSuccessPageCount();
			left += spiderStatus.getLeftPageCount();
		}
		
		indexes.put("scanned", scanned);
		indexes.put("downloaded", downloaded);
		indexes.put("left", left);
		
		return indexes;
	}
	
	public static SpiderBean getInstance(){
		if(instance == null){
			instance  = new SpiderBean();
		}
		return instance;
	}
	
}
