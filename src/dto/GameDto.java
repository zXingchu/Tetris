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
	 * 游戏屏宽度和高度
	 */
	public static final int GAMEZONE_W = GameConfig.getSYSTEM_CONFIG().getMaxX() + 1;
	public static final int GAMEZONE_H = GameConfig.getSYSTEM_CONFIG().getMaxY() + 1;
	/**
	 * 数据库记录
	 */
	private List<Player> dbRecode;
	/**
	 * 本地记录
	 */
	private List<Player> diskRecode;
	/**
	 * 游戏地图
	 */
	private boolean[][] gameMap;
	/**
	 * 下落方块
	 */
	private GameAct gameAct;
	/**
	 * 下一个方块
	 */
	private int next;
	/**
	 * 现等级
	 */
	private int nowLevel;
	/**
	 * 现分数
	 */
	private int nowPoint;
	/**
	 * 消除行数
	 */
	private int nowRemoveLine;
	/**
	 * 游戏开始状态否
	 */
	private boolean start;
	/**
	 * 是否显示阴影
	 */
	private boolean showShaow;
	/**
	 * 暂停
	 */
	private boolean pause;

	private long sleepTime;
	
	public GameDto() {
		this.dtoInit();
	}

	/**
	 * dto初始化
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
