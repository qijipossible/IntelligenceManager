package service;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import entity.CrawlingState;

public class DataService {
	private static class DataServiceHolder {
		private static final DataService INSTANCE = new DataService();
	}

	private DataService() {
	}

	public static final DataService getInstance() {
		return DataServiceHolder.INSTANCE;
	}

	public CrawlingState getCrawlingState() {
		return getTestCrawlingState();
	}
	
	//测试
	public CrawlingState getTestCrawlingState() {
		CrawlingState crawlingState = new CrawlingState();
		Random random = new Random();
		crawlingState.setScanned(random.nextInt());
		crawlingState.setDowloaded(random.nextInt());
		crawlingState.setLeft(random.nextInt());
		return crawlingState;
         
	}
}

