package config;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ConfigReader {

	public static void readConfig() throws DocumentException{
		SAXReader read=new SAXReader();
		Document doc=read.read("config/cfg.xml");
		Element game=doc.getRootElement();
		Element frame=game.element("frame");
		@SuppressWarnings("unchecked")
		List<Element> layers=frame.elements("layer");
		for (Element layer : layers) {
			System.out.print(layer.attributeValue("className"));
			System.out.print(Integer.parseInt(layer.attributeValue("x"))+" ");
			System.out.print(Integer.parseInt(layer.attributeValue("y"))+" ");
			System.out.print(Integer.parseInt(layer.attributeValue("w"))+" ");
			System.out.println(Integer.parseInt(layer.attributeValue("h")));
		}
		String str=frame.attributeValue("width");
		System.out.println(str);
	}
	
	public static void main(String[] args) {
		try {
			readConfig();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
}
