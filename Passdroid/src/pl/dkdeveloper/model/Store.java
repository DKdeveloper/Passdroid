package pl.dkdeveloper.model;

import java.util.List;
import org.simpleframework.xml.Default;

@Default
public class Store {
	private List<Category> Categories;

	public List<Category> getCategories() {
		return Categories;
	}

	public void setCategories(List<Category> categories) {
		Categories = categories;
	}
	
	public void addCategory(Category category) {
		Categories.add(category);
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
}
