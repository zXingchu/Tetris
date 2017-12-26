package ui;

import java.awt.Graphics;

public class LayerLevel extends Layer {

	public LayerLevel(int x, int y, int w, int h) {
		super(x, y, w, h);
	}

	public void paint(Graphics g) {
		this.createWindow(g);
		
		g.drawImage(Img.LV, this.x + PADDING, this.y + PADDING, null);

		this.drawNumberLeftPad(32, 64, this.dto.getNowlevel(), 3, g);
	}

}
