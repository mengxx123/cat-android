package com.cjh.cat.manager;

import static com.cjh.cat.part.PortraitPart.BACKGROUND;
import static com.cjh.cat.part.PortraitPart.BUBBLE;
import static com.cjh.cat.part.PortraitPart.EAR;
import static com.cjh.cat.part.PortraitPart.EXPRESSION;
import static com.cjh.cat.part.PortraitPart.EYE;
import static com.cjh.cat.part.PortraitPart.FACE;
import static com.cjh.cat.part.PortraitPart.FEATURE;
import static com.cjh.cat.part.PortraitPart.FOOT;
import static com.cjh.cat.part.PortraitPart.HAIR_BG;
import static com.cjh.cat.part.PortraitPart.HAIR_COLOR;
import static com.cjh.cat.part.PortraitPart.MOUTH;
import static com.cjh.cat.part.PortraitPart.NOSE;
import static com.cjh.cat.part.PortraitPart.OTHER;
import static com.cjh.cat.part.PortraitPart.SKIN_COLOR;
import static com.cjh.cat.part.PortraitPart.WHISKERS;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultDocument;
import org.dom4j.tree.DefaultElement;

import android.content.Context;

import com.cjh.cat.R;
import com.cjh.cat.part.PortraitPart;
import com.cjh.cat.util.LogUtil;

public class DataManager {
	
	private static final DataManager instance = new DataManager();
	private static final String ID = "ID";

	private Document bgDocument;
	private Document portraitDocument;
	private Document bubbleDocument;
	private Element dataRootElement;

	private boolean isInitFinished;
	
	private List<String> bubbleStrings = new ArrayList<String>(3);

	private DataManager() {}

	public static DataManager instance() {
		return instance;
	}

	public void init(Context context) {
		for (PortraitPart part : PortraitPart.values()) {
			MementoManager.instance().addMemento(part);
		}
		initDataRootElement(context);
		initBackgroundDocument();
		initPortraitDocument();
		initBubbleDocument();
		
		isInitFinished = true;
		LogUtil.d("数据初始化完毕");
	}

	/** 加载素材的XML文件，初始化数据 **/
	private void initDataRootElement(Context context) {
		SAXReader reader = new SAXReader();
		InputStream dataInputStream = context.getResources().openRawResource(R.raw.data);
		try {
			dataRootElement = reader.read(dataInputStream).getRootElement();
			dataInputStream.close();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initBackgroundDocument() {
		Element rootElement = createSvgRootElement();
		bgDocument = new DefaultDocument(rootElement);
		rootElement.add(createGElement(BACKGROUND));
	}

	private void initPortraitDocument() {
		Element rootElement = createSvgRootElement();
		portraitDocument = new DefaultDocument(rootElement);
		rootElement.add(createGElement(HAIR_BG));
		
		rootElement.add(createGElement(EAR));
		rootElement.add(createGElement(FACE));
		rootElement.add(createGElement(WHISKERS));
		rootElement.add(createGElement(EYE));
		rootElement.add(createGElement(NOSE));
		rootElement.add(createGElement(MOUTH));
		rootElement.add(createGElement(FEATURE));
		
		rootElement.add(createGElement(EXPRESSION));
		rootElement.add(createGElement(FOOT));
		rootElement.add(createGElement(OTHER));
	}

	private void initBubbleDocument() {
		Element rootElement = createSvgRootElement();
		bubbleDocument = new DefaultDocument(rootElement);
		rootElement.add(createGElement(BUBBLE));
	}

	private Element createSvgRootElement() {
		Element rootElement = new DefaultElement("svg");
		rootElement.addAttribute("version", "1.1");
		rootElement.addAttribute("id", "1");
		rootElement.addAttribute("xmlns", "http://www.w3.org/2000/svg");
		rootElement.addAttribute("xmlns:xlink", "http://www.w3.org/1999/xlink");
		rootElement.addAttribute("x", "0px");
		rootElement.addAttribute("y", "0px");
		rootElement.addAttribute("width", "640px");
		rootElement.addAttribute("height", "640px");
		rootElement.addAttribute("viewBox", "0 0 640 640");
		rootElement.addAttribute("enable-background", "new 0 0 640 640");
		return rootElement.addAttribute("xml:space", "preserve");
	}

	private Element createGElement(PortraitPart component) {
		Element gElement = new DefaultElement("g");
		gElement.addAttribute(ID, component.getType());
		addPathElements(gElement, component);
		return gElement;
	}

	private void addPathElements(Element parentElement, PortraitPart component) {
		Element graphElement = (Element) dataRootElement.element(component.getType()).elementByID(component.getId());
		if (graphElement == null) {
			return;
		}
		
		Element copy = graphElement.createCopy();
		parentElement.add(dealElem(copy));
		/*
		List<Element> elements = graphElement.elements();
		for (Element element : elements) {
			Element copy = element.createCopy();
			Attribute attribute = copy.attribute("fill");
			if (attribute != null) {
				String fillValue = attribute.getStringValue();
				if (HAIR_COLOR.getType().equals(fillValue)) {
					attribute.setValue(HAIR_COLOR.getId());
				} else if (SKIN_COLOR.getType().equals(fillValue)) {
					attribute.setValue(SKIN_COLOR.getId());
				} else if (PortraitPart.EYE_COLOR.getType().equals(fillValue)) {
					attribute.setValue(PortraitPart.EYE_COLOR.getId());
				}
			}

			parentElement.add(copy);
		}
		*/
	}
	
	private Element dealElem(Element elem) {
		
		Attribute attribute = elem.attribute("fill");
		if (attribute != null) {
			String fillValue = attribute.getStringValue();
			if (HAIR_COLOR.getType().equals(fillValue)) {
				attribute.setValue(HAIR_COLOR.getId());
			} else if (SKIN_COLOR.getType().equals(fillValue)) {
				attribute.setValue(SKIN_COLOR.getId());
			} else if (PortraitPart.EYE_COLOR.getType().equals(fillValue)) {
				attribute.setValue(PortraitPart.EYE_COLOR.getId());
			} else if ("earColor".equals(fillValue)) {
				attribute.setValue("#ff0000");
			} else if ("earColor2".equals(fillValue)) {
				attribute.setValue("#FFCCB3");
			}
		}

		List<Element> elements = elem.elements();
		
		for (Element element : elements) {
			dealElem(element);
		}
		return elem;
	}
	
	/*
	private void addPathElements(Element parentElement, PortraitPart component) {
		Element graphElement = (Element) dataRootElement.element(component.getType()).elementByID(component.getId());
		if (graphElement == null) {
			return;
		}

		List<Element> elements = graphElement.elements();
		for (Element element : elements) {
			Element copy = element.createCopy();
			Attribute attribute = copy.attribute("fill");
			if (attribute != null) {
				String fillValue = attribute.getStringValue();
				if (HAIR_COLOR.getType().equals(fillValue)) {
					attribute.setValue(HAIR_COLOR.getId());
				} else if (SKIN_COLOR.getType().equals(fillValue)) {
					attribute.setValue(SKIN_COLOR.getId());
				} else if (PortraitPart.EYE_COLOR.getType().equals(fillValue)) {
					attribute.setValue(PortraitPart.EYE_COLOR.getId());
				}
			}

			parentElement.add(copy);
		}
	}
	*/
	private void dealElement(Element elem) {
		
	}
	
	private void updateDocument(Document document, PortraitPart... parts) {
		for (PortraitPart part : parts) {
			updateDocument(document, part);
		}
	}

	private void updateDocument(Document document, PortraitPart part) {
		Element gElement = document.getRootElement().elementByID(part.getType());
		if (gElement == null) {
			return;
		}
		gElement.clearContent();
		gElement.addAttribute(ID, part.getType());
		addPathElements(gElement, part);
	}

	/**
	 * 更新画像
	 * 
	 * @param document
	 * @param parts
	 * @return
	 */
	public Document updatePortrait(PortraitPart... parts) {
		updateDocument(portraitDocument, parts);
		return portraitDocument;
	}

	/**
	 * 更新背景
	 * 
	 * @param document
	 * @param parts
	 * @return
	 */
	public Document updateBackground(PortraitPart part) {
		updateDocument(bgDocument, part);
		return bgDocument;
	}

	/**
	 * 更新气泡
	 * 
	 * @param document
	 * @param parts
	 * @return
	 */
	public Document updateBubble(PortraitPart part) {
		updateDocument(bubbleDocument, part);
		return bubbleDocument;
	}

	public List<String> getBubbleStrings() {
		return bubbleStrings;
	}

	public void setBubbleEdit(String bubbleString) {
		int length = bubbleString.length();
		if (bubbleString == null || length == 0) {
			return;
		}
		bubbleStrings.clear();
		if (length <= 10) {
			bubbleStrings.add(bubbleString);
		} else if (length > 10 && length <= 20) {
			bubbleStrings.add(bubbleString.substring(0, 10));
			bubbleStrings.add(bubbleString.substring(10, length));
		} else {
			bubbleStrings.add(bubbleString.substring(0, 10));
			bubbleStrings.add(bubbleString.substring(10, 20));
			bubbleStrings.add(bubbleString.substring(20, length));
		}
	}
	
	public boolean isInitFinished() {
		return isInitFinished;
	}
}
