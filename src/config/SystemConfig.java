package config;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

public class SystemConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int minX;
	private final int maxX;
	private final int minY;
	private final int maxY;
	private final int levelUp;
	private final List<Point[]> typeConfig;
	private final int rmPoint;
	private final int reward;

	public SystemConfig(Element system) {
		this.minX = Integer.parseInt(system.attributeValue("minX"));
		this.maxX = Integer.parseInt(system.attributeValue("maxX"));
		this.minY = Integer.parseInt(system.attributeValue("minY"));
		this.maxY = Integer.parseInt(system.attributeValue("maxY"));
		this.levelUp = Integer.parseInt(system.attributeValue("levelUp"));
		Element plusPoint = system.element("plusPoint");
		this.rmPoint = Integer.parseInt(plusPoint.attributeValue("rmPoint"));
		this.reward = Integer.parseInt(plusPoint.attributeValue("reward"));
		@SuppressWarnings("unchecked")
		List<Element> rects = system.elements("rect");
		typeConfig = new ArrayList<Point[]>(rects.size());
		for (Element rect : rects) {
			@SuppressWarnings("unchecked")
			List<Element> pointConfig = rect.elements("Point");
			Point[] points = new Point[pointConfig.size()];
			for (int i = 0; i < points.length; i++) {
				int x = Integer.parseInt(pointConfig.get(i).attributeValue("x"));
				int y = Integer.parseInt(pointConfig.get(i).attributeValue("y"));
				points[i] = new Point(x, y);
			}
			typeConfig.add(points);
		}

	}

	public int getMinX() {
		return minX;
	}

	public int getMaxX() {
		return maxX;
	}

	public int getMinY() {
		return minY;
	}

	public int getMaxY() {
		return maxY;
	}

	public int getLevelUp() {
		return levelUp;
	}

	public List<Point[]> getTypeConfig() {
		return typeConfig;
	}

	public int getRmPoint() {
		return rmPoint;
	}

	public int getReward() {
		return reward;
	}

}
