package service;

import java.awt.Point;
import java.util.Random;
import config.GameConfig;
import dto.GameDto;
import entity.GameAct;

public class GameTetris implements GameService {

	/**
	 * 游戏数据对象
	 */
	private GameDto dto;

	private Random random = new Random();

	private static final int MAX_TYPR = GameConfig.getSYSTEM_CONFIG().getTypeConfig().size() - 1;

	private final int LEVEL_UP = GameConfig.getSYSTEM_CONFIG().getLevelUp();
	private final int rmPoint = GameConfig.getSYSTEM_CONFIG().getRmPoint();
	private final int reward = GameConfig.getSYSTEM_CONFIG().getReward();

	public GameTetris(GameDto dto) {
		this.dto = dto;

	}

	/**
	 * 方块操作(上)
	 */
	public void keyUp() {
		if (this.dto.isPause())
			return;
		synchronized (this.dto) {
			this.dto.getGameAct().round(this.dto.getGameMap());
		}
	}

	/**
	 * 方块操作(下)
	 */
	public boolean keyDown() {
		if (this.dto.isPause())
			return true;
		synchronized (this.dto) {
			// 方块向下移动
			if (this.dto.getGameAct().move(0, 1, this.dto.getGameMap())) {
				return true;
			}
			// 方块结束移动,加入布尔地图
			boolean[][] map = this.dto.getGameMap();
			Point[] act = this.dto.getGameAct().getActPoint();
			for (int i = 0; i < act.length; i++) {
				map[act[i].x][act[i].y] = true;
			}
			// 判断并执行消行，并得到所消行数作为经验值
			int plusExp = this.plusExp();
			this.plusPoint(plusExp);
			// 刷新一个新方块
			this.dto.getGameAct().init(this.dto.getNext());
			this.dto.setNext(random.nextInt(MAX_TYPR));

			if (this.isLose()) {
				// 设置游戏开始状态为false
				this.dto.setStart(false);
			}
		}
		return false;
	}


	private boolean isLose() {
		Point[] actPoints = this.dto.getGameAct().getActPoint();
		boolean[][] map = this.dto.getGameMap();
		for (int i = 0; i < actPoints.length; i++) {
			if (map[actPoints[i].x][actPoints[i].y]) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 加分升级操作
	 * 
	 * @param plusExp
	 */
	private void plusPoint(int plusExp) {
		int lv = this.dto.getNowlevel();
		int rmLine = this.dto.getNowRemoveLine();
		if (rmLine % LEVEL_UP + plusExp >= LEVEL_UP) {
			this.dto.setNowLevel(++lv);
		}
		this.dto.setNowRemoveLine(rmLine + plusExp);
		this.dto.setNowPoint(this.dto.getNowPoint() + plusExp * this.rmPoint + this.reward * plusExp * (plusExp - 1));
	}

	/**
	 * 消行操作
	 */
	private int plusExp() {
		int exp = 0;
		// 获得游戏地图
		boolean[][] map = this.dto.getGameMap();
		// 扫描游戏地图，是否可以消行
		for (int y = 0; y < GameDto.GAMEZONE_H; y++) {
			if (isCanRemoveLine(y, map)) {
				this.removeLine(y, map);
				exp++;
			}
		}
		return exp;
	}

	/**
	 * 消行处理
	 * 
	 * @param y
	 */
	private void removeLine(int rowNumber, boolean[][] map) {
		for (int x = 0; x < GameDto.GAMEZONE_W; x++) {
			for (int y = rowNumber; y > 0; y--) {
				map[x][y] = map[x][y - 1];
			}
			map[x][0] = false;
		}

	}

	/**
	 * y行扫描
	 * 
	 * @param y
	 * @return
	 */
	private boolean isCanRemoveLine(int y, boolean[][] map) {
		// y行扫描
		for (int x = 0; x < GameDto.GAMEZONE_W; x++) {
			if (!map[x][y]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 方块操作(左)
	 */
	public void keyLeft() {
		if (this.dto.isPause())
			return;
		synchronized (this.dto) {
			this.dto.getGameAct().move(-1, 0, this.dto.getGameMap());
		}
	}

	/**
	 * 方块操作(右)
	 */
	public void keyRight() {
		if (this.dto.isPause())
			return;
		synchronized (this.dto) {
			this.dto.getGameAct().move(1, 0, this.dto.getGameMap());
		}
	}
	// ================================================================

	public void testLevelUp() {

	}

	@Override
	public void keyFunUp() {
		this.plusPoint(4);
	}

	@Override
	public void keyFunDown() {
		if (this.dto.isPause())
			return;
		// 瞬间下落
		while (this.keyDown())
			;
	}

	@Override
	public void keyFunLeft() {
		// 阴影开关
		this.dto.changeShowShaow();
	}

	/**
	 * 暂停
	 */
	@Override
	public void keyFunRight() {
		if (this.dto.isStart()) {
			this.dto.changePause();
		}
	}

	@Override
	public void startGame() {
		this.dto.setGameAct(new GameAct(this.dto.getNext()));
		this.dto.setStart(true);
		this.dto.dtoInit();
	}

	@Override
	public void mainAction() {
		this.keyDown();

	}

}
