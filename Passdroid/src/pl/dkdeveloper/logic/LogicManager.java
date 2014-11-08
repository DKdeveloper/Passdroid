package pl.dkdeveloper.logic;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;

import pl.dkdeveloper.model.Category;
import pl.dkdeveloper.model.Store;
import pl.dkdeveloper.passdroid.PassdroidApplication;

public class LogicManager {

	public List<Category> getDefaultCategoryList() {
		List<Category> list = new ArrayList<Category>();

		list.add(new Category("Home"));
		list.add(new Category("Work"));
		list.add(new Category("Money"));

		return list;
	}

	public void createDatabase(String path) {
		setDatabasePath(path);
		Store store = new Store();
		store.setCategories(getDefaultCategoryList());
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
