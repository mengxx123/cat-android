package com.cjh.cat.manager;

import java.util.HashMap;
import java.util.Map;

import com.cjh.cat.part.PortraitPart;
import com.cjh.cat.part.PortraitPart.Memento;

public class MementoManager {
	private static final MementoManager instance = new MementoManager();
	private Map<PortraitPart, Memento> mementoMap = new HashMap<PortraitPart, Memento>();
	private Map<PortraitPart, Memento> leftPortraitPartMap = new HashMap<PortraitPart, Memento>();

	private MementoManager() {
	}

	public static MementoManager instance() {
		return instance;
	}

	public Memento pullMemento(PortraitPart part) {
		return mementoMap.remove(part);
	}

	private void restoreMemento(Map<PortraitPart, Memento> map, PortraitPart part) {
		Memento memento = map.remove(part);
		part.restoreMemento(memento);
	}

	private void restoreMemento(Map<PortraitPart, Memento> map, PortraitPart... parts) {
		for (PortraitPart part : parts) {
			restoreMemento(map, part);
		}
	}

	private void addMemento(Map<PortraitPart, Memento> map, PortraitPart part) {
		map.put(part, part.createMemento());
	}

	private void addMemento(Map<PortraitPart, Memento> map, PortraitPart... parts) {
		for (PortraitPart part : parts) {
			addMemento(map, part);
		}
	}

	public void restoreMemento(PortraitPart part) {
		restoreMemento(mementoMap, part);
	}

	public void restoreMemento(PortraitPart... parts) {
		restoreMemento(mementoMap, parts);
	}

	public void addMemento(PortraitPart part) {
		addMemento(mementoMap, part);
	}

	public void addMemento(PortraitPart... parts) {
		addMemento(mementoMap, parts);
	}

	public void restoreLeftPortraitMemento(PortraitPart... parts) {
		restoreMemento(leftPortraitPartMap, parts);
	}

	public void addLeftPortraitemento(PortraitPart... parts) {
		addMemento(leftPortraitPartMap, parts);
	}
}
