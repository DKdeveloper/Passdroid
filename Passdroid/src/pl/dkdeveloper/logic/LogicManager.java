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

public class LogicManager {

	public List<Category> getDefaultCategoryList() {
		List<Category> list = new ArrayList<Category>();

		list.add(new Category("Home"));
		list.add(new Category("Work"));
		list.add(new Category("Money"));

		return list;
	}

	public void CreateDatabase(String path) {

		Store store = new Store();
		store.setCategories(getDefaultCategoryList());
		SerializeToFile(store, path);

	}

	public void SerializeToFile(Object ob, String path) {
		Serializer serializer = new Persister();
		File xmlFile = new File(path);

		try {
			serializer.write(ob, xmlFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String GetDatabasePath()
	{
		return "";
	}

}
