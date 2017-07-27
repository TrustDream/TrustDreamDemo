package com.trust.demo.gsonparsing.entity;

import java.io.Serializable;

public class User implements Serializable{
private String id;
private String age;
private String sex;
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getAge() {
	return age;
}
public void setAge(String age) {
	this.age = age;
}
public String getSex() {
	return sex;
}
public void setSex(String sex) {
	this.sex = sex;
}
public User(String id, String age, String sex) {
	super();
	this.id = id;
	this.age = age;
	this.sex = sex;
}
public User() {
	super();
}
@Override
public String toString() {
	return "User [id=" + id + ", age=" + age + ", sex=" + sex + "]";
}

}
