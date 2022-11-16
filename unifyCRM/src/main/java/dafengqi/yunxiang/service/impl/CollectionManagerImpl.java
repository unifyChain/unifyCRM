package dafengqi.yunxiang.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dafengqi.yunxiang.dao.CollectionDao;
import dafengqi.yunxiang.model.Collection;
import dafengqi.yunxiang.model.CollectionDetail;
import dafengqi.yunxiang.model.Invoicing;
import dafengqi.yunxiang.service.CollectionManager;

@Service("collectionManager")
public class CollectionManagerImpl extends GenericManagerImpl<Collection, Long> implements CollectionManager, Serializable {

	CollectionDao collectionDao;

	@Autowired
	public CollectionManagerImpl(CollectionDao collectionDao) {

		this.collectionDao = collectionDao;

	}
	@Override
	public List getItems(Collection collection) {
		return collectionDao.getItems(collection);
	}
	@Override
	public List getItemsOfMy(Collection collection) {
		return collectionDao.getItemsOfMy(collection);
	}
	@Override
	public List getItemsRecord(Collection collection) {
		return collectionDao.getItemsRecord(collection);
	}
	@Override
	public List getItemsRecordOfMy(Collection collection) {
		return collectionDao.getItemsRecordOfMy(collection);
	}
	@Override
	public List getItemsInvoicing(Invoicing invoicing) {
		return collectionDao.getItemsInvoicing(invoicing);
	}
	@Override
	public List getItemsInvoicingOfMy(Invoicing invoicing) {
		return collectionDao.getItemsInvoicingOfMy(invoicing);
	}


	@Override
	public List editmx(Collection collection) {
		return collectionDao.editmx(collection);
	}

	@Override
	public int saveCollection(Collection collection,List<CollectionDetail> collectionDetails) {
		return collectionDao.saveCollection(collection,collectionDetails);
	}
	@Override
	public int saveCollectionRecord(Collection collection) {
		return collectionDao.saveCollectionRecord(collection);
	}
	@Override
	public int saveCollectioncreateinvoicing(Invoicing invoicing) {
		return collectionDao.saveCollectioncreateinvoicing(invoicing);
	}
	@Override
	public Collection view(Collection collection) {
		return collectionDao.view(collection);
	}
	@Override
	public Collection viewRecord(Collection collection) {
		return collectionDao.viewRecord(collection);
	}
	@Override
	public Invoicing viewInvoicing(Invoicing invoicing) {
		return collectionDao.viewInvoicing(invoicing);
	}
	

}