package com.nishith.demo.model;

import org.springframework.boot.autoconfigure.web.WebProperties.Resources.Chain.Strategy;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Theather {
@Id
@GeneratedValue(strategy  = GenerationType.IDENTITY)
private int theatherid;
private String name;
private String location;
public int getId() {
	return theatherid;
}
public void setId(int id) {
	this.theatherid = id;
}
@Override
public String toString() {
	return "Theather [id=" + theatherid + ", name=" + name + ", location=" + location + "]";
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getLocation() {
	return location;
}
public void setLocation(String location) {
	this.location = location;
}
public Theather(String name, String location) {
	super();
	this.name = name;
	this.location = location;
}
public Theather() {
	super();
}


}
