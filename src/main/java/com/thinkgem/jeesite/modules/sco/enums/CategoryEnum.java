package com.thinkgem.jeesite.modules.sco.enums;

/**
 * 种类代号：1物资2车辆3印刷4图文5软件
 * @author tankai
 *
 */
public enum CategoryEnum {
    wuzi(1,"物资"),
    cheliang(2,"车辆"),
    yinshua(3,"印刷"),
    tuwen(4,"图文"),
    ruanjian(5,"软件"),
    ;
    
    private  Integer id;
    private  String name;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private CategoryEnum(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
    
    
}
