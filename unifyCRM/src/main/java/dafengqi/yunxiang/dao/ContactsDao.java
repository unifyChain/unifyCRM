package dafengqi.yunxiang.dao;

import java.util.List;

import dafengqi.yunxiang.model.Contacts;

public interface ContactsDao extends GenericDao<Contacts, Long> {

	public List<?> getItems(Contacts contacts);
	public List<?> getItemsOfMy(Contacts contacts);
	public Contacts edit(Contacts contacts);
	public int saveContacts(Contacts contacts);
	public int update(Contacts contacts);

}