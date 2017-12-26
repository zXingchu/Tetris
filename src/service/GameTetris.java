package service;

import java.awt.Point;
import java.util.Random;
import config.GameConfig;
import dto.GameDto;
import entity.GameAct;

public class GameTetris implements GameService {

	/**
	 * ��Ϸ���ݶ���
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
	 * �������(��)
	 */
	public void keyUp() {
		if (this.dto.isPause())
			return;
		synchronized (this.dto) {
			this.dto.getGameAct().round(this.dto.getGameMap());
		}
	}

	/**
	 * �������(��)
	 */
	public boolean keyDown() {
		if (this.dto.isPause())
			return true;
		synchronized (this.dto) {
			// ���������ƶ�
			if (this.dto.getGameAct().move(0, 1, this.dto.getGameMap())) {
				return true;
			}
			// ��������ƶ�,���벼����ͼ
			boolean[][] map = this.dto.getGameMap();
			Point[] act = this.dto.getGameAct().getActPoint();
			for (int i = 0; i < act.length; i++) {
				map[act[i].x][act[i].y] = true;
			}
			// �жϲ�ִ�����У����õ�����������Ϊ����ֵ
			int plusExp = this.plusExp();
			this.plusPoint(plusExp);
			// ˢ��һ���·���
			this.dto.getGameAct().init(this.dto.getNext());
			this.dto.setNext(random.nextInt(MAX_TYPR));

			if (this.isLose()) {
				// ������Ϸ��ʼ״̬Ϊfalse
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
	 * �ӷ���������
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
	 * ���в���
	 */
	private int plusExp() {
		int exp = 0;
		// �����Ϸ��ͼ
		boolean[][] map = this.dto.getGameMap();
		// ɨ����Ϸ��ͼ���Ƿ��������
		for (int y = 0; y < GameDto.GAMEZONE_H; y++) {
			if (isCanRemoveLine(y, map)) {
				this.removeLine(y, map);
				exp++;
			}
		}
		return exp;
	}

	/**
	 * ���д���
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
	 * y��ɨ��
	 * 
	 * @param y
	 * @return
	 */
	private boolean isCanRemoveLine(int y, boolean[][] map) {
		// y��ɨ��
		for (int x = 0; x < GameDto.GAMEZONE_W; x++) {
			if (!map[x][y]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * �������(��)
	 */
	public void keyLeft() {
		if (this.dto.isPause())
			return;
		synchronized (this.dto) {
			this.dto.getGameAct().move(-1, 0, this.dto.getGameMap());
		}
	}

	/**
	 * �������(��)
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
		// ˲������
		while (this.keyDown())
			;
	}

	@Override
	public void keyFunLeft() {
		// ��Ӱ����
		this.dto.changeShowShaow();
	}

	/**
	 * ��ͣ
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
