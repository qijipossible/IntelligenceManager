package service;


public class ControlService {
	private static class ControlServiceHolder{
		private static final ControlService INSTANCE = new ControlService();
	}
	private ControlService(){}
	public static final ControlService getInstance(){
		return ControlServiceHolder.INSTANCE;
	}
	public int isCrawling = 0;
	public void startCrawling(String key){
		isCrawling = 1;
	}
	public void stopCrawling(){
		isCrawling = 0;
	}

}
