package entity;

public class CrawlingState {
	private int scanned;
	private int dowloaded;
	private int left;
	public int getScanned() {
		return scanned;
	}
	public void setScanned(int scanned) {
		this.scanned = scanned;
	}
	public int getDowloaded() {
		return dowloaded;
	}
	public void setDowloaded(int dowloaded) {
		this.dowloaded = dowloaded;
	}
	public int getLeft() {
		return left;
	}
	public void setLeft(int left) {
		this.left = left;
	}
	@Override
	public String toString() {
		return "CrawlingState [scanned=" + scanned + ", dowloaded=" + dowloaded + ", left=" + left + "]";
	}
	public CrawlingState(int scanned, int dowloaded, int left) {
		super();
		this.scanned = scanned;
		this.dowloaded = dowloaded;
		this.left = left;
	}
	public CrawlingState() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
