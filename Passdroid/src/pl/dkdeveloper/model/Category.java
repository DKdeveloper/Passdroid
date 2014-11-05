package pl.dkdeveloper.model;

public class Category {

	public Category() {
		// TODO Auto-generated constructor stub
	}
	
	public Category(String categoryName) {
		this.CategoryName = categoryName;
	}
	
	private String CategoryName;

	public String getCategoryName() {
		return CategoryName;
	}

	public void setCategoryName(String categoryName) {
		CategoryName = categoryName;
	}

}
