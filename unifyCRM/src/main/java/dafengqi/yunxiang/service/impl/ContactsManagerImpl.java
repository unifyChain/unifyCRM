package dafengqi.yunxiang.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dafengqi.yunxiang.dao.ContactsDao;
import dafengqi.yunxiang.model.Contacts;
import dafengqi.yunxiang.service.ContactsManager;

@Service("contactsManager")
public class ContactsManagerImpl extends GenericManagerImpl<Contacts, Long> implements ContactsManager, Serializable {

	ContactsDao contactsDao;

	@Autowired
	public ContactsManagerImpl(ContactsDao contactsDao) {

		this.contactsDao = contactsDao;

	}
	@Override
	public List getItems(Contacts contacts) {
		return contactsDao.getItems(contacts);
	}
	@Override
	public List getItemsOfMy(Contacts contacts) {
		return contactsDao.getItemsOfMy(contacts);
	}
	@Override
	public int saveContacts(Contacts contacts) {
		return contactsDao.saveContacts(contacts);
	}
	@Override
	public int update(Contacts contacts) {
		return contactsDao.update(contacts);
	}

	@Override
	public Contacts edit(Contacts contacts) {
		return contactsDao.edit(contacts);
	}



}