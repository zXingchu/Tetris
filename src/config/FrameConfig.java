package config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

public class FrameConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final String title;

	private final int width;

	private final int height;

	private final int padding;

	private final int border;

	private final int windowUp;

	private final int sizeRol;

	private final int loseIdx;
	/**
	 * Õº≤„ Ù–‘
	 */
	private List<LayerConfig> layersConfig;

	private final ButtonConfig buttonConfig;

	public FrameConfig(Element frame) {
		this.title = frame.attributeValue("title");
		this.width = Integer.parseInt(frame.attributeValue("width"));
		this.height = Integer.parseInt(frame.attributeValue("height"));
		this.padding = Integer.parseInt(frame.attributeValue("padding"));
		this.border = Integer.parseInt(frame.attributeValue("border"));
		this.windowUp = Integer.parseInt(frame.attributeValue("windowUp"));
		this.sizeRol = Integer.parseInt(frame.attributeValue("sizeRol"));
		this.loseIdx = Integer.parseInt(frame.attributeValue("loseIdx"));
		@SuppressWarnings("unchecked")
		List<Element> layers = frame.elements("layer");
		layersConfig = new ArrayList<LayerConfig>();
		for (Element layer : layers) {
			LayerConfig lc = new LayerConfig(layer.attributeValue("className"),
					Integer.parseInt(layer.attributeValue("x")), Integer.parseInt(layer.attributeValue("y")),
					Integer.parseInt(layer.attributeValue("w")), Integer.parseInt(layer.attributeValue("h")));
			layersConfig.add(lc);
		}

		buttonConfig = new ButtonConfig(frame.element("button"));
	}

	public String getTitle() {
		return title;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getPadding() {
		return padding;
	}

	public int getBorder() {
		return border;
	}

	public int getWindowUp() {
		return windowUp;
	}

	public List<LayerConfig> getLayersConfig() {
		return layersConfig;
	}

	public int getSizeRol() {
		return sizeRol;
	}

	public int getLoseIdx() {
		return loseIdx;
	}

	public ButtonConfig getButtonConfig() {
		return buttonConfig;
	}

}
