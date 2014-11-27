package pl.dkdeveloper.model;

import java.util.ArrayList;
import java.util.List;
import org.simpleframework.xml.Default;

@Default
public class Store {
	private List<Category> Categories = new ArrayList<Category>();

	public List<Category> getCategories() {
		return Categories;
	}

	public void setCategories(List<Category> categories) {
		Categories = categories;
	}
	
	public void addCategory(Category category) {
		Categories.add(category);
	}
	
	public Category getCategoryByName(String categoryName) {
		for(Category c : Categories)
		{
			if(c.getCategoryName().equals(categoryName))
			{
				return c;
			}
		}
		
		return null;
	}
	
	public void UpdateCategory(Category category) {
		int location = Categories.indexOf(category);
		Categories.set(location, category);
	}

	public void removeCategoryByName(String categoryName) {
		for(Category c : Categories)
		{
			if(c.getCategoryName().equals(categoryName))
			{
				Categories.remove(c);
				break;
			}
		}
			
		
	}

	public void updateCategoryWithNewName(String categoryName,
			String newCategoryName) {
		for(Category c : Categories)
		{
			if(c.getCategoryName().equals(categoryName))
			{
				c.setCategoryName(newCategoryName);
				break;
			}
		}
		
	}
	
	public void removePasswordByName(Category category, String password) {
		for(Password p : category.getPasswords())
		{
			if(p.getName().equals(password))
			{
				category.getPasswords().remove(p);
				break;
			}
		}
	}
	
	public Password getPasswordByName(Category category, String passName) {
		for(Password p : category.getPasswords())
		{
			if(p.getName().equals(passName))
			{
				return p;
			}
		}
		
		return null;
	}
	
	public void updatePasswordByName(Category category, String oldName, Password newPassword) {
			int index = category.getPasswords().indexOf(this.getPasswordByName(category, oldName));
			category.getPasswords().set(index, newPassword);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
