package com.trust.demo.gsonparsing.entity;


public class FromJsonUtils {
	private Class cls;
	private String json;

	public FromJsonUtils(Class cls,String json){
		this.cls=cls;
		this.json=json;
	}
	public Common fromJson(){
		Common result=null;
		try {
			result=Common.fromJson(json,cls);
		} catch (Exception e) {
			// TODO: handle exception
			result=null;
			e.printStackTrace();
		}
		return result;
	}  
	public CommonList fromJsonList(){
		CommonList list=null;
		try {
			list = CommonList.fromJson(json,
					cls);
		} catch (Exception e) {
			// TODO: handle exception
			list=null;
			e.printStackTrace();
		}
		return list;
	}
}
