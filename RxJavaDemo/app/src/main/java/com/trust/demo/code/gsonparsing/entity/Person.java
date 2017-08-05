package com.trust.demo.code.gsonparsing.entity;

import java.util.ArrayList;

public class Person {
private String name;
private User user;
private ArrayList<User> list;

public Person(String name, ArrayList<User> list) {
	super();
	this.name = name;
	this.list = list;
}
public Person(String name, User user, ArrayList<User> list) {
	super();
	this.name = name;
	this.user = user;
	this.list = list;
}
public ArrayList<User> getList() {
	return list;
}
public void setList(ArrayList<User> list) {
	this.list = list;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public User getUser() {
	return user;
}
public void setUser(User user) {
	this.user = user;
}
@Override
public String toString() {
	return "Person [name=" + name + ", user=" + user + "]";
}
public Person(String name, User user) {
	super();
	this.name = name;
	this.user = user;
}
public Person() {
	super();
}

}
