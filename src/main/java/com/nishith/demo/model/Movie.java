package com.nishith.demo.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class Movie {
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private int movieid;
	private String name;
 @JsonFormat(pattern ="dd-MM-yyyy")
	private LocalDate date; //use "instance" insterd of localdatetime 
 private String imgname;
 private String imgtype;
 @Lob
 private byte[] image;
 
	public String getImgname() {
	return imgname;
}
 public void setImgname(String imgname) {
	this.imgname = imgname;
 }
 public String getImgtype() {
	return imgtype;
 }
 public void setImgtype(String imgtype) {
	this.imgtype = imgtype;
 }
 public byte[] getImage() {
	return image;
 }
 public void setImage(byte[] image) {
	this.image = image;
 }
	public int getId() {
		return movieid;
	}
	public void setId(int id) {
		this.movieid = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public Movie(String name, LocalDate date) {
		super();
		this.name = name;
		this.date = date;
	}
	
	public Movie()
	{}
	

}
