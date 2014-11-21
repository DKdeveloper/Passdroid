package pl.dkdeveloper.logic;

import java.util.ArrayList;
import java.util.List;

import pl.dkdeveloper.model.Category;
import pl.dkdeveloper.model.Password;
import pl.dkdeveloper.model.Store;

public class FakeStore {
	public static Store getFakeStore()
	{
		List<Category> list = new ArrayList<Category>();
		
		Category dom =  new Category("Dom");
		Password pin = new Password("PIN do konta", "", "5945");
		dom.addPassword(pin);
		Password konto = new Password("Konto bankowe online", "31999321", "!@kto32pytanieBladzi1");
		dom.addPassword(konto);
		list.add(dom);
		
		Category praca =  new Category("Praca");
		Password loginDoSystemu = new Password("Dane do systemu", "pracownikDKD", "#@po@wer!23");
		praca.addPassword(loginDoSystemu);
		list.add(praca);
		
		Store store = new Store();
		store.setCategories(list);
		return store;
	}
}
