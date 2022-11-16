package dafengqi.yunxiang.dao;

import java.util.List;

import dafengqi.yunxiang.model.Collection;
import dafengqi.yunxiang.model.CollectionDetail;
import dafengqi.yunxiang.model.Invoicing;

public interface CollectionDao extends GenericDao<Collection, Long> {
	public List<?> getItems(Collection collection);
	public List<?> getItemsOfMy(Collection collection);
	public List<?> getItemsRecord(Collection collection);
	public List<?> getItemsRecordOfMy(Collection collection);
	public List<?> getItemsInvoicing(Invoicing invoicing);
	public List<?> getItemsInvoicingOfMy(Invoicing invoicing);
	public List<?> editmx(Collection collection);
	public int saveCollection(Collection collection,List<CollectionDetail> collectionDetails);
	public int saveCollectionRecord(Collection collection);
	public int saveCollectioncreateinvoicing(Invoicing invoicing);
	public Collection view(Collection collection);
	public Collection viewRecord(Collection collection);
	public Invoicing viewInvoicing(Invoicing invoicing);

}