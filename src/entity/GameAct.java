package entity;

import java.awt.Point;
import java.util.List;

import config.GameConfig;

public class GameAct {

	/**
	 * ��������
	 */
	private Point[] actPoints;
	/**
	 * 
	 */
	private int typeCode;
	
	private final static int MIN_X = GameConfig.getSYSTEM_CONFIG().getMinX();
	private final static int MAX_X = GameConfig.getSYSTEM_CONFIG().getMaxX();
	private final static int MIN_Y = GameConfig.getSYSTEM_CONFIG().getMinY();
	private final static int MAX_Y = GameConfig.getSYSTEM_CONFIG().getMaxY();

	private static List<Point[]> TYPE_CONFIG=GameConfig.getSYSTEM_CONFIG().getTypeConfig();;

	public GameAct(int typeCode) {
		this.init(typeCode);
	}

	public void init(int typeCode) {
		this.typeCode=typeCode;
		Point[] Points = TYPE_CONFIG.get(typeCode);
		actPoints = new Point[Points.length];
		for (int i = 0; i < Points.length; i++) {
			actPoints[i] = new Point(Points[i].x, Points[i].y);
		}
	}

	public Point[] getActPoint() {
		return actPoints;
	}

	/**
	 * �����ƶ�
	 * 
	 * @param moveX
	 *            X��ƫ����
	 * @param moveY
	 *            Y��ƫ����
	 */
	public boolean move(int moveX, int moveY, boolean[][] gameMap) {

		for (int i = 0; i < actPoints.length; i++) {
			int nowX = actPoints[i].x + moveX;
			int nowY = actPoints[i].y + moveY;
			if (this.isOverZone(nowX, nowY, gameMap))
				return false;
		}
		// �ƶ�����
		for (int i = 0; i < actPoints.length; i++) {
			actPoints[i].x += moveX;
			actPoints[i].y += moveY;
		}

		return true;
	}

	/**
	 * ������ת
	 * 
	 * 
	 */
	public void round(boolean[][] gameMap) {
		
		if(this.typeCode==4)
			return ;
		for (int i = 1; i < actPoints.length; i++) {
			int nowX = actPoints[0].x + actPoints[0].y - actPoints[i].y;
			int nowY = actPoints[0].y - actPoints[0].x + actPoints[i].x;
			if (this.isOverZone(nowX, nowY, gameMap))
				return;
		}

		for (int i = 1; i < actPoints.length; i++) {
			int nowX = actPoints[0].x + actPoints[0].y - actPoints[i].y;
			int nowY = actPoints[0].y - actPoints[0].x + actPoints[i].x;
			actPoints[i].x = nowX;
			actPoints[i].y = nowY;
		}
	}

	private boolean isOverZone(int x, int y, boolean[][] gameMap) {
		return (x < MIN_X || x > MAX_X || y < MIN_Y || y > MAX_Y || gameMap[x][y]);
	}

	/**
	 * ��÷�����
	 * @return
	 */
	public int getTypeCode() {
		return typeCode;
	}
	
	
}
