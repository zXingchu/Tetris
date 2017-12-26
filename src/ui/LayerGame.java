package ui;

import java.awt.Graphics;
import java.awt.Point;
import config.GameConfig;

public class LayerGame extends Layer {

	private static final int SIZE_ROL = GameConfig.getFRAME_CONFIG().getSizeRol();

	/**
	 * 左右边距
	 */
	private static final int LEFT_SIDE = GameConfig.getSYSTEM_CONFIG().getMinX();
	private static final int RIGHT_SIDE = GameConfig.getSYSTEM_CONFIG().getMaxX();

	private static final int LOSE_IDX = GameConfig.getFRAME_CONFIG().getLoseIdx();

	public LayerGame(int x, int y, int w, int h) {
		super(x, y, w, h);
	}

	public void paint(Graphics g) {
		this.createWindow(g);
		if (this.dto.getGameAct() != null) {
			Point[] points = this.dto.getGameAct().getActPoint();
			this.drawShadow(points, g);
			// 绘制活动方块
			this.drawMainAct(points, g);
			this.drawMap(g);
		}

		if (this.dto.isPause()) {
			this.drawImageAtCenter(g, Img.PAUSE);
		}
	}

	private void drawMainAct(Point[] points, Graphics g) {
		int typeCode = this.dto.getGameAct().getTypeCode();
		// 绘制移动方块
		for (int i = 0; i < points.length; i++) {
			this.drawActByPoint(points[i].x, points[i].y, typeCode + 1, g);
		}
	}

	private void drawMap(Graphics g) {
		// 绘制结束移动方块
		boolean[][] map = this.dto.getGameMap();

		int lv = this.dto.getNowlevel();
		int imgIdx = lv == 0 ? 0 : (lv - 1) % 7 + 1;

		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map[x].length; y++) {
				if (map[x][y])
					this.drawActByPoint(x, y, imgIdx, g);
			}
		}
	}

	/**
	 * 绘制阴影
	 * 
	 * @param points
	 * @param b
	 */
	private void drawShadow(Point[] points, Graphics g) {
		if (!this.dto.isShowShaow())
			return;
		int leftX = RIGHT_SIDE;
		int rightX = LEFT_SIDE;
		for (Point p : points) {
			leftX = p.x < leftX ? p.x : leftX;
			rightX = p.x > rightX ? p.x : rightX;
		}
		g.drawImage(Img.SHADOW, this.x + BORDER + (leftX << SIZE_ROL), this.y + BORDER,
				(rightX - leftX + 1) << SIZE_ROL, this.h - (BORDER << 1), null);
	}

	private void drawActByPoint(int x, int y, int imgIdx, Graphics g) {
		imgIdx = this.dto.isStart() ? imgIdx : LOSE_IDX;
		g.drawImage(Img.ACT, this.x + (x << SIZE_ROL) + BORDER, this.y + (y << SIZE_ROL) + BORDER,
				this.x + (x + 1 << SIZE_ROL) + BORDER, this.y + (y + 1 << SIZE_ROL) + BORDER, imgIdx << SIZE_ROL, 0,
				(imgIdx + 1) << SIZE_ROL, 1 << SIZE_ROL, null);
	}
}
