package com.SmartContactManager.SmartContactManagerSpring.Entites;



import java.util.*;




import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;






@Entity
@Table(name="USER")
public class User {
    @Id
    @GeneratedValue(strategy =	GenerationType.AUTO)
	private int id;
    @NotBlank(message="Name will not be Null")
    @Size(min=2,max=30,message="Minimum 3 chatacte and maximum 30 characte required")
	private String name;
	@Column(unique=true)
	private String email;
	private String password;
	private String role;
	private boolean enable;
	private String imageurl;
	@Column(length=500)
	private String about;	
	
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", role=" + role
				+ ", enable=" + enable + ", imageurl=" + imageurl + ", about=" + about + ", contacts=" + contacts + "]";
	}
	// orphan removal is to when any child entity is unlinked with parent entity
	// then this perform a operation and remove the the unlink parent entity
//	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="user")
	@OneToMany(mappedBy="user",	cascade=CascadeType.ALL,orphanRemoval=true)
	private List<Contact> contacts=new ArrayList<>();
	
	public List<Contact> getContacts() {
		return contacts;
	}
	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}
	public User() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	public String getImageurl() {
		return imageurl;
	}
	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
  


	
}
