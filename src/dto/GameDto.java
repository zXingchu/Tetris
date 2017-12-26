package dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import config.GameConfig;
import entity.GameAct;
import util.GameFunction;

public class GameDto {

	/**
	 * ��Ϸ����Ⱥ͸߶�
	 */
	public static final int GAMEZONE_W = GameConfig.getSYSTEM_CONFIG().getMaxX() + 1;
	public static final int GAMEZONE_H = GameConfig.getSYSTEM_CONFIG().getMaxY() + 1;
	/**
	 * ���ݿ��¼
	 */
	private List<Player> dbRecode;
	/**
	 * ���ؼ�¼
	 */
	private List<Player> diskRecode;
	/**
	 * ��Ϸ��ͼ
	 */
	private boolean[][] gameMap;
	/**
	 * ���䷽��
	 */
	private GameAct gameAct;
	/**
	 * ��һ������
	 */
	private int next;
	/**
	 * �ֵȼ�
	 */
	private int nowLevel;
	/**
	 * �ַ���
	 */
	private int nowPoint;
	/**
	 * ��������
	 */
	private int nowRemoveLine;
	/**
	 * ��Ϸ��ʼ״̬��
	 */
	private boolean start;
	/**
	 * �Ƿ���ʾ��Ӱ
	 */
	private boolean showShaow;
	/**
	 * ��ͣ
	 */
	private boolean pause;

	private long sleepTime;
	
	public GameDto() {
		this.dtoInit();
	}

	/**
	 * dto��ʼ��
	 */
	public void dtoInit() {
		this.next = new Random().nextInt(6);
		this.gameMap = new boolean[GAMEZONE_W][GAMEZONE_H];
		this.nowLevel = 1;
		this.nowPoint = 0;
		this.nowRemoveLine = 0;
		this.pause=false;
		this.sleepTime=GameFunction.getSleepTimeByLevel(nowLevel);
	}

	public void setDbRecode(List<Player> dbRecode) {
		this.dbRecode = this.setFillRecode(dbRecode);
	}

	public void setDiskRecode(List<Player> diskRecode) {
		this.diskRecode = this.setFillRecode(diskRecode);
	}

	private List<Player> setFillRecode(List<Player> players) {
		if (players == null) {
			players = new ArrayList<Player>();
		}
		while (players.size() < 5) {
			players.add(new Player("No Data", -1));
		}
		Collections.sort(players);
		return players;
	}

	public void setGameMap(boolean[][] gameMap) {
		this.gameMap = gameMap;
	}

	public void setGameAct(GameAct gameAct) {
		this.gameAct = gameAct;
	}

	public void setNext(int next) {
		this.next = next;
	}

	public void setNowLevel(int nowLevel) {
		this.nowLevel = nowLevel;
		this.sleepTime=GameFunction.getSleepTimeByLevel(nowLevel);
	}

	public void setNowPoint(int nowPoint) {
		this.nowPoint = nowPoint;
	}

	public void setNowRemoveLine(int nowRemoveLine) {
		this.nowRemoveLine = nowRemoveLine;
	}

	public List<Player> getDbRecode() {
		return dbRecode;
	}

	public List<Player> getDiskRecode() {
		return diskRecode;
	}

	public boolean[][] getGameMap() {
		return gameMap;
	}

	public GameAct getGameAct() {
		return gameAct;
	}

	public int getNext() {
		return next;
	}

	public int getNowlevel() {
		return nowLevel;
	}

	public int getNowPoint() {
		return nowPoint;
	}

	public int getNowRemoveLine() {
		return nowRemoveLine;
	}

	public boolean isStart() {
		return start;
	}

	public void setStart(boolean start) {
		this.start = start;
	}

	public boolean isShowShaow() {
		return showShaow;
	}

	public void changeShowShaow() {
		this.showShaow = !this.showShaow;
	}

	public boolean isPause() {
		return pause;
	}

	public void changePause() {
		this.pause = !this.pause;
	}

	public long getSleepTime() {
		return sleepTime;
	}

}
