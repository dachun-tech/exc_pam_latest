package com.thinkgem.jeesite.modules.sco.enums;

/**
 * 类型汽车维修，印刷，图文制作，喷绘，视频制作，软件开发
 * 
 * @author tankai
 *
 */
public enum TypeEnum {
    cheliang(4,"车辆"),
    yinshua(6,"印刷"),
    tuwen(5,"图文"),
    ruanjian(9,"软件"),
//    penhui(5,"喷绘"),
    shipin(8,"视频制作"),
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
	private TypeEnum(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
	
	
	
	public static TypeEnum findById(String id){
		for (TypeEnum value : TypeEnum.values()) {
			if((value.getId()+"").equals(id)){
				return value;
			}
		}
		return null;
	}
    
    
}
