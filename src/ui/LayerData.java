package ui;

import java.awt.Graphics;
import java.awt.Image;
import java.util.List;

import config.GameConfig;
import dto.Player;

public abstract class LayerData extends Layer {

	/**
	 * 显示最大数据行数
	 */
	private static final int MAX_ROW = GameConfig.getDATA_CONFIG().getMaxRow();
	/**
	 * 起始Y坐标
	 */
	private static int STATR_Y = 0;

	private static final int RECT_H = IMG_RECT_H + 4;

	/**
	 * 间距
	 */
	private static int SPA = 0;

	public LayerData(int x, int y, int w, int h) {
		super(x, y, w, h);
		SPA = (this.h - RECT_H * 5 - (PADDING << 1) - Img.DB.getHeight(null)) / MAX_ROW;
		STATR_Y = PADDING + Img.DB.getHeight(null) + SPA;
	}

	@Override
	abstract public void paint(Graphics g);

	public void showData(Image imgTitle, List<Player> players, Graphics g) {
		g.drawImage(imgTitle, this.x + PADDING, this.y + PADDING, null);
		// List<Player> players = this.dto.getDbRecode();
		int nowPoint = this.dto.getNowPoint();
		for (int i = 0; i < MAX_ROW; i++) {
			Player player = players.get(i);
			double percent = (double) nowPoint / (double) player.getPoint();
			percent = percent > 1 ? 1.0 : percent;
			String strPoint = player.getPoint() == 0 ? null : Integer.toString(player.getPoint());
			this.drawRect(STATR_Y + i * (RECT_H + SPA), player.getName(), strPoint, percent, g);

		}
	}
}
