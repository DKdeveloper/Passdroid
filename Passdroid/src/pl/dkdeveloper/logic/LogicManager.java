package pl.dkdeveloper.logic;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import pl.dkdeveloper.model.Category;
import pl.dkdeveloper.model.Password;
import pl.dkdeveloper.model.Store;
import pl.dkdeveloper.passdroid.PassdroidApplication;

public class LogicManager {
	
	//sol uzyta przy generowaniu hala
	public static String SALT = "QOOIWQDUAUIASIAS";

	// Inicjalizacja przykładowych kategorii
	public List<Category> InitExampleCategories() {
		List<Category> list = new ArrayList<Category>();
		list.add(new Category("Home"));
		list.add(new Category("Work"));
		list.add(new Category("Internet"));

		return list;
	}

	// Dodanie testowych hasel
	public List<Password> InitExamplePasswords() {
		List<Password> list = new ArrayList<Password>();
		list.add(new Password("Pass1", "Mariusz", "qwe"));
		list.add(new Password("Pass2", "Mariusz", "qwe"));
		list.add(new Password("Pass3", "Mariusz", "qwe"));

		return list;
	}

	// Pobranie listy kategorii
	public List<Category> getCategoryList() {
		List<Category> list = new ArrayList<Category>(getStore()
				.getCategories());
		return list;
	}

	//Pobranie hasel na podstawie nazwy kategorii
	public List<Password> getPasswordByCategory(String category) {
		Category cat = new Category();

		for (Category c : getStore().getCategories()) {
			if (c.getCategoryName().contains(category)) {
				cat = c;
			}
		}

		return cat.getPasswords();
	}

	//Tworzenie bazy danych
	public void createDatabase(String path, String password) throws Exception {
		setDatabasePath(path);
		Store store = new Store();
		store.setCategories(InitExampleCategories());
		saveDatabase(store,password);
	}

	//Zapisywanie bazy danych
	public void saveDatabase(Store ob,String password) throws Exception {
		if(password == null || password.equals(""))
			throw new Exception("Password is not set!");
		
		
		String path = getDatabasePath();
		if (path.equals(""))
			return;

		try {
			SecretKeyFactory f = SecretKeyFactory
					.getInstance("PBKDF2WithHmacSHA1");
			KeySpec ks = new PBEKeySpec(password.toCharArray(),
					SALT.getBytes(), 1024, 256);
			SecretKey s = f.generateSecret(ks);
			SecretKeySpec k = new SecretKeySpec(s.getEncoded(), "AES");

			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, k);

			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(path));
			CipherOutputStream cos = new CipherOutputStream(bos, cipher);

			Serializer serializer = new Persister();
			serializer.write(ob, cos);
			cos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Zapisanie falszywej bazy
		saveFakeDb(ob);
		
	}

	//Zapisywanie falszywej bazy
	private void saveFakeDb(Store originalStore) {
		Store fakeStore = new Store();
		
		for(Category c : originalStore.getCategories())
		{
			Category category = new Category(c.getCategoryName());
			for(Password p : c.getPasswords())
			{
				category.addPassword(new Password(p.getName(), p.getLogin(), 
						PasswordGenerator.getRandomPassword()));
			}
			fakeStore.addCategory(category);
		}
		
		String path = getFakeDatabasePath();
		File file = new File(path);
		Serializer serializer = new Persister();
		try {
			serializer.write(fakeStore,file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	//Odczyt falszywej bazy
	public Store loadFakeDb()
	{
		String path = getFakeDatabasePath();
		File file = new File(path);
		Serializer serializer = new Persister();
		Store store = null;
		try {
			store = serializer.read(Store.class, file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return store;
	}

	//Odczyt prawdziwej bazy
	public void loadDatabase(String password) throws Exception {
		String path = getDatabasePath();
		
		if (path.equals(""))
			throw new Exception("Database not found!!");
		Serializer serializer = new Persister();
		Store store = null;
		
		SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		KeySpec ks = new PBEKeySpec(password.toCharArray(),
				SALT.getBytes(), 1024, 256);
		SecretKey s = f.generateSecret(ks);
		SecretKeySpec k = new SecretKeySpec(s.getEncoded(), "AES");

		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, k);

		BufferedInputStream buf = new BufferedInputStream(new FileInputStream(
				new File(path)));
		CipherInputStream cis = new CipherInputStream(buf, cipher);
		
		store = serializer.read(Store.class, cis);
		cis.close();

		if (store == null)
			throw new Exception("Can't load database");
		setStore(store);

	}

	//Pobranie sciezki do bazy
	public String getDatabasePath() {
		String path = PreferencesHeleper.getString("db_path");
		return path;
	}

	//Zapis sciezki do bazy
	public void setDatabasePath(String path) {
		PreferencesHeleper.setString("db_path", path);
	}
	
	//Pobranie sciezki do falszywej bazy
	public String getFakeDatabasePath() {
		String path = PreferencesHeleper.getString("fake_db_path");
		return path;
	}

	//Zapis sciezki do falszywej bazy
	public void setFakeDatabasePath(String path) {
		PreferencesHeleper.setString("fake_db_path", path);
	}
	
	//Sprawdzenie czy baza istnieje
	public boolean databaseExist() {
		String path = getDatabasePath();
		return !path.equals("");
	}
	
	//Sprawdzenie czy uzytkownik uwierzytelniony
	public boolean isAuthenticated()
	{
		return ! (PassdroidApplication.getPassword() == null || PassdroidApplication.getPassword().equals(""));
	}

	public Store getStore() {
		return PassdroidApplication.getStore();
	}

	public void setStore(Store store) {
		PassdroidApplication.setStore(store);
	}
	
	//Zapis falszywego hasla
	public String getFakePassword() {
		String pass = PreferencesHeleper.getString("fake_password");
		return pass;
	}
	
	//Odczyt falszywego hasla
	public void setFakePassword(String pass) {
		PreferencesHeleper.setString("fake_password", pass);
	}

}
