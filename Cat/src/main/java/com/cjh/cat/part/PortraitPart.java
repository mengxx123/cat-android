package com.cjh.cat.part;


public enum PortraitPart {
	
	EAR("earData", "ear01"), 
	HAIR_COLOR("hairColor", "#f00"), 
	FACE("faceData", "face01"), 
	SKIN_COLOR("faceColor", "#c8dfaa"), 
	WHISKERS("whiskerData", "whiskers01"), // 胡须
	EYE("eyeData", "eye01"), 
	EYE_COLOR("eyeColor", "#ff0000"), 
	MOUTH("mouthData", "mouth01"), 
	NOSE("noseData", "nose00"), 
	FEATURE("featureData", "feature00"), // 特征
	OTHER("otherData", "other00"), 
	FOOT("footData", "foot01"), 
	BACKGROUND("backgroundData", "bg01"), 
	EXPRESSION("expressData", "express00"), 
	BUBBLE("bubbleData", "bubble0", true), 
	HAIR_BG("bgHairData", "hair0");

	private String type;
	private String id;
	private boolean editable;
	private Memento memento;

	private PortraitPart(String type, String id, boolean editable) {
		this.type = type;
		this.id = id;
		this.editable = editable;
		memento = createMemento();
	}

	private PortraitPart(String type, String id) {
		this.type = type;
		this.id = id;
		memento = createMemento();
	}

	public String getDefaultId(boolean isMale) {
		switch (this) {
		case EAR:
			if (isMale) {
				return "hairg10001";
			}
			break;
		case BACKGROUND:
		default:
			break;
		}
		return id;

	}

	public String getType() {
		return type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Memento createMemento() {
		return new Memento(id);
	}

	/**
	 * 恢复状态
	 * @param memento
	 * @return ID是否变化
	 */
	public void restoreMemento(Memento memento) {
		if (memento == null) {
			return;
		}

		setId(memento.getId());
	}

	public void restore() {
		restoreMemento(memento);
	}

	/**
	 * 判断是否为初始值
	 * @return
	 */
	public boolean isOrignal() {
		return id.equals(memento.getId());
	}

	public boolean isEditable() {
		if (editable) {
			switch (this) {
			case BUBBLE:
				return id.equals("bubble0edit");
			default:
				break;
			}
		}
		return false;
	}

	public static class Memento {
		private String id = null;

		protected Memento(String id) {
			this.id = id;
		}

		private String getId() {
			return id;
		}
	}
}