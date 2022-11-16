package dafengqi.yunxiang.service;

import java.util.List;

import dafengqi.yunxiang.model.Contacts;

public interface ContactsManager extends GenericManager<Contacts, Long> {

	List<Contacts> getItems(Contacts contacts);
	List<Contacts> getItemsOfMy(Contacts contacts);
	Contacts edit(Contacts contacts);
	int saveContacts(Contacts contacts);
	int update(Contacts contacts);
}