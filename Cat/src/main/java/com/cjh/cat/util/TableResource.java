package com.cjh.cat.util;

import com.cjh.cat.part.PortraitPart;

public class TableResource {
	
	private PortraitPart part;
	private String title;
	private int[] resourceIds;

	public PortraitPart getPortraitPart() {
		return part;
	}

	public void setPortraitPart(PortraitPart part) {
		this.part = part;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int[] getResourceIds() {
		return resourceIds;
	}

	public void setResourceIds(int[] resourceIds) {
		this.resourceIds = resourceIds;
	}

}
