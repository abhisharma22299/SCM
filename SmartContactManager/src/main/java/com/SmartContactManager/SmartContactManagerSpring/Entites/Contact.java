package com.SmartContactManager.SmartContactManagerSpring.Entites;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="CONTACT")
public class Contact {

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int CId;
	private String name; 
	private String secondName; 
	private String work;
	private String phone; 
	private String email;
	private String image;
	@Column(length=500)
	private String description;
	@ManyToOne
    @JsonIgnore
	private User user;
	
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getCId() {
		return CId;
	}
	public void setCId(int cId) {
		CId = cId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSecondName() {
		return secondName;
	}
	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}
	public String getWork() {
		return work;
	}
	public void setWork(String work) {
		this.work = work;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	} 
	
	@Override
	public boolean equals(Object obj) {
		return this.CId==((Contact)obj).getCId();
	}
	
//	
//	@Override
//	public String toString() {
//		return "Contact [CId=" + CId + ", name=" + name + ", secondName=" + secondName + ", work=" + work + ", phone="
//				+ phone + ", email=" + email + ", image=" + image + ", description=" + description + ", user=" + user
//				+ "]";
//	}
//	

	
}
