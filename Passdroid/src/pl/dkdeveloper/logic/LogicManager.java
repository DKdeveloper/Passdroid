package pl.dkdeveloper.logic;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import pl.dkdeveloper.model.Category;
import pl.dkdeveloper.model.Password;
import pl.dkdeveloper.model.Store;
import pl.dkdeveloper.passdroid.PassdroidApplication;

public class LogicManager {

	// Generate stubs for first DB
	public List<Category> InitExampleCategories() {
		List<Category> list = new ArrayList<Category>();
		list.add(new Category("Home", InitExamplePasswords()));
		list.add(new Category("Work", InitExamplePasswords()));
		list.add(new Category("Internet", InitExamplePasswords()));
		
		return list;		
	}
	
	// Generate stubs for first DB
		public List<Password> InitExamplePasswords() {
			List<Password> list = new ArrayList<Password>();
			list.add(new Password("Pass1", "Mariusz", "qwe"));
			list.add(new Password("Pass2", "Mariusz", "qwe"));
			list.add(new Password("Pass3", "Mariusz", "qwe"));
			
			return list;		
		}
	
	// get categories from DB
	public List<Category> getCategoryList() {
		List<Category> list = new ArrayList<Category>(getStore().getCategories());
		return list;
	}
	
	public List<Password> getPasswordByCategory(String category) {
		Category cat = new Category();
		
		for (Category c : getStore().getCategories()) {
			if (c.getCategoryName().contains(category)) {
				cat = c;
			}
		}		
		
		return cat.getPasswords();
	}	

	public void createDatabase(String path) {
		setDatabasePath(path);
		Store store = new Store();
		store.setCategories(InitExampleCategories());
		saveDatabase(store);
	}

	public void saveDatabase(Object ob) {
		String path = getDatabasePath();
		if (path.equals(""))
			return;
		Serializer serializer = new Persister();
		File xmlFile = new File(path);

		try {
			serializer.write(ob, xmlFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadDatabase() throws Exception {
		String path = getDatabasePath();
		if (path.equals(""))
			throw new Exception("Database not found!!");
		Serializer serializer = new Persister();
		File xmlFile = new File(path);
		Store store = null;
		try {
			store = serializer.read(Store.class, xmlFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (store == null)
			throw new Exception("Can't load database");
		setStore(store);
		
	}

	public String getDatabasePath() {
		String path = PreferencesHeleper.getString("db_path");
		return path;
	}

	public void setDatabasePath(String path) {
		PreferencesHeleper.setString("db_path", path);
	}

	public boolean databaseExist() {
		String path = getDatabasePath();
		return !path.equals("");
	}

	public Store getStore() {
		return PassdroidApplication.getStore();
	}

	public void setStore(Store store) {
		PassdroidApplication.setStore(store);
		}

}
