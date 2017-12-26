package ui;

import java.awt.Graphics;

import config.GameConfig;

public class LayerPoint extends Layer {

	private static final int POINT_BIT = 5;
	
	private final int LEVEL_UP = GameConfig.getSYSTEM_CONFIG().getLevelUp();
	
	private final int pointY;

	private final int rmlineY;

	private final int comX;

	private final int expY;

	public LayerPoint(int x, int y, int w, int h) {
		super(x, y, w, h);
		this.comX = this.w - IMG_NUMBER_W * POINT_BIT - PADDING;
		this.pointY = PADDING;
		this.rmlineY = pointY + Img.POINT.getHeight(null) + PADDING;
		this.expY = rmlineY + Img.RMLINE.getHeight(null) + PADDING;
	}

	public void paint(Graphics g) {
		this.createWindow(g);

		g.drawImage(Img.POINT, this.x + PADDING, this.y + pointY, null);

		this.drawNumberLeftPad(comX, pointY, this.dto.getNowPoint(), POINT_BIT, g);

		g.drawImage(Img.RMLINE, this.x + PADDING, this.y + rmlineY, null);

		this.drawNumberLeftPad(comX, rmlineY, this.dto.getNowRemoveLine(), POINT_BIT, g);

		int rmLine = this.dto.getNowRemoveLine();
		this.drawRect(expY, "ÏÂÒ»¼¶", null, (double) (rmLine % LEVEL_UP) / (double) LEVEL_UP, g);
	}

}
