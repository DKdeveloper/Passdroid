package pl.dkdeveloper.model;


import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Default;

@Default
public class Category {

	public Category() {
		Passwords = new ArrayList<Password>();
	}
	
	public Category(String categoryName) {
		this.CategoryName = categoryName;
		this.Passwords = new ArrayList<Password>();
	}
	
	public Category(String categoryName, List<Password> passwords) {
		this.CategoryName = categoryName;
		this.Passwords = new ArrayList<Password>(passwords);
	}

	private String CategoryName;
	private List<Password> Passwords;
	
	public String getCategoryName() {
		return CategoryName;
	}

	public void setCategoryName(String categoryName) {
		CategoryName = categoryName;
	}

	public List<Password> getPasswords() {
		return Passwords;
	}

	public void setPasswords(List<Password> passwords) {
		Passwords = passwords;
	}

}
