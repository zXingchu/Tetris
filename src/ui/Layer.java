package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import config.FrameConfig;
import config.GameConfig;
import dto.GameDto;

public abstract class Layer {

	/**
	 * 内边距
	 */
	protected static final int PADDING;

	/**
	 * 边框宽度
	 */
	protected static final int BORDER;

	static {
		// 获得游戏配置
		FrameConfig fCfg = GameConfig.getFRAME_CONFIG();
		PADDING = fCfg.getPadding();
		BORDER = fCfg.getBorder();
	}

	private int WINODW_W = Img.WINDOW.getWidth(null);
	private int WINDOW_H = Img.WINDOW.getHeight(null);

	protected static final int IMG_NUMBER_W = Img.NUM.getWidth(null) / 10;
	protected static final int IMG_NUMBER_H = Img.NUM.getHeight(null);

	protected static final int IMG_RECT_H = Img.RECT.getHeight(null);
	protected static final int IMG_RECT_W = Img.RECT.getWidth(null);

	/**
	 * 值槽宽度
	 */
	private final int rectW;

	private static final Font font = new Font("黑体", Font.BOLD, 25);
	/**
	 * 窗口左上角x坐标
	 */
	protected int x;
	/**
	 * 窗口左上角y坐标
	 */
	protected int y;
	/**
	 * 窗口宽度
	 */
	protected int w;
	/**
	 * 窗口高度
	 */
	protected int h;
	/**
	 * 窗口高度
	 */
	protected GameDto dto = null;

	protected Layer(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;

		this.rectW = this.w - (PADDING << 1);
	}

	/**
	 * 绘制窗口
	 */
	protected void createWindow(Graphics g) {
		// 左上
		g.drawImage(Img.WINDOW, x, y, x + BORDER, y + BORDER, 0, 0, BORDER, BORDER, null);
		// 中上
		g.drawImage(Img.WINDOW, x + BORDER, y, x + w - BORDER, y + BORDER, BORDER, 0, WINODW_W - BORDER, BORDER, null);
		// 右上
		g.drawImage(Img.WINDOW, x + w, y, x + w - BORDER, y + BORDER, WINODW_W - BORDER, 0, WINODW_W, BORDER, null);
		// 左中
		g.drawImage(Img.WINDOW, x, y + BORDER, x + BORDER, y + h - BORDER, 0, BORDER, BORDER, WINDOW_H - BORDER, null);
		// 中
		g.drawImage(Img.WINDOW, x + BORDER, y + BORDER, x + w - BORDER, y + h - BORDER, BORDER, BORDER, WINODW_W - BORDER,
				WINDOW_H - BORDER, null);
		// 右中
		g.drawImage(Img.WINDOW, x + w - BORDER, y + BORDER, x + w, y + h - BORDER, WINODW_W - BORDER, BORDER, WINODW_W,
				WINDOW_H - BORDER, null);
		// 左下
		g.drawImage(Img.WINDOW, x, y + h - BORDER, x + BORDER, y + h, 0, WINDOW_H - BORDER, BORDER, WINDOW_H, null);
		// 中下
		g.drawImage(Img.WINDOW, x + BORDER, y + h - BORDER, x + w - BORDER, y + h, BORDER, WINDOW_H - BORDER, WINODW_W - BORDER,
				WINDOW_H, null);
		// 右下
		g.drawImage(Img.WINDOW, x + w - BORDER, y + h - BORDER, x + w, y + h, WINODW_W - BORDER, WINDOW_H - BORDER, WINODW_W,
				WINDOW_H, null);
	}

	/**
	 * 刷新游戏内容
	 * 
	 * @param g
	 */
	abstract public void paint(Graphics g);

	public void setDto(GameDto dto) {
		this.dto = dto;
	}

	public void drawNumberLeftPad(int x, int y, int num, int maxBit, Graphics g) {
		String strNum = Integer.toString(num);
		for (int i = 0; i < maxBit; i++) {
			if (maxBit - i <= strNum.length()) {
				int idx = i - maxBit + strNum.length();
				int bit = strNum.charAt(idx) - '0';
				g.drawImage(Img.NUM, this.x + x + i * IMG_NUMBER_W, this.y + y, this.x + x + (i + 1) * IMG_NUMBER_W,
						this.y + y + IMG_NUMBER_H, bit * IMG_NUMBER_W, 0, (bit + 1) * IMG_NUMBER_W, IMG_NUMBER_H, null);
			}
		}
	}

	/**
	 * 绘制指槽
	 * 
	 * @param title
	 * @param number
	 * @param value
	 * @param maxValue
	 * @param g
	 */
	protected void drawRect(int y, String title, String number, double precent, Graphics g) {
		// 各种值初始化
		int rect_x = this.x + PADDING;
		int rect_y = this.y + y;
		// 绘制背景
		g.setColor(Color.BLACK);
		g.fillRect(rect_x, rect_y, this.rectW, IMG_RECT_H + 4);
		g.setColor(Color.WHITE);
		g.fillRect(rect_x + 1, rect_y + 1, this.rectW - 2, IMG_RECT_H + 2);
		g.setColor(Color.BLACK);
		g.fillRect(rect_x + 2, rect_y + 2, this.rectW - 4, IMG_RECT_H);
		g.setColor(Color.BLUE);

		// 求出宽度
		int width = (int) (precent * (this.rectW - 4));
		// 求出颜色
		int subIdx = (int) (precent * IMG_RECT_W) - 1;
		// 求出槽值
		g.drawImage(Img.RECT, rect_x + 2, rect_y + 2, rect_x + 2 + width, rect_y + 2 + IMG_RECT_H, subIdx, 0,
				subIdx + 1, IMG_RECT_H, null);
		g.setColor(Color.WHITE);
		g.setFont(font);
		g.drawString(title, rect_x+4, rect_y + 22);
		if (number != null) {
			g.drawString(number, rect_x+224, rect_y + 24);
		}
	}

	/**
	 * 正中绘图
	 * 
	 * @param g
	 * @param img
	 */
	protected void drawImageAtCenter(Graphics g, Image img) {
		int imgW = img.getWidth(null);
		int imgH = img.getHeight(null);
		int imgX = this.w - imgW >> 1;
		int imgY = this.h - imgH >> 1;
		g.drawImage(img, this.x + imgX, this.y + imgY, null);
	}
	
	
}
