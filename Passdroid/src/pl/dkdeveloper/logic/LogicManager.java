package pl.dkdeveloper.logic;

import java.util.ArrayList;
import java.util.List;

import pl.dkdeveloper.model.Category;

public class LogicManager {
	
	private List<Category> _categoryList;

	public LogicManager() {
		// TODO Auto-generated constructor stub
	}
	
	public void testInit() {
		_categoryList = new ArrayList<Category>();
		
		_categoryList.add(new Category("Home"));
		_categoryList.add(new Category("Work"));
		_categoryList.add(new Category("Money"));
	}
	
	public List<Category> getCategoryList() {		
		testInit();
		return _categoryList;
	}

}
