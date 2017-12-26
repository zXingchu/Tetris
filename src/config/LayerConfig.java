package config;

import java.io.Serializable;

public class LayerConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String classname;

	private int x;

	private int y;

	private int w;

	private int h;

	public LayerConfig(String classname, int x, int y, int w, int h) {
		this.classname = classname;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public String getClassname() {
		return classname;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getW() {
		return w;
	}

	public int getH() {
		return h;
	}

}
