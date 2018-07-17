package com.thinkgem.jeesite.modules.sco.domain;

import java.util.List;


public class ScoSerTreeElement {
	
	private ScoSerTreeElement parent;
	private String id;
	private String name;
	private boolean selected;
	private List<ScoSerTreeElement> children;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public List<ScoSerTreeElement> getChildren() {
		return children;
	}
	public void setChildren(List<ScoSerTreeElement> children) {
		this.children = children;
	}
	public ScoSerTreeElement getParent() {
		return parent;
	}
	public void setParent(ScoSerTreeElement parent) {
		this.parent = parent;
	}
	
	
	
	
	
}
