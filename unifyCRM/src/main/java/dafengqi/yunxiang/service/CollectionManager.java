package dafengqi.yunxiang.service;

import java.util.List;

import dafengqi.yunxiang.model.Collection;
import dafengqi.yunxiang.model.CollectionDetail;
import dafengqi.yunxiang.model.Invoicing;

public interface CollectionManager extends GenericManager<Collection, Long> {

	List<CollectionDetail> getItems(Collection collection);
	List<CollectionDetail> getItemsOfMy(Collection collection);
	List<Collection> getItemsRecord(Collection collection);
	Collection view(Collection collection);
	Collection viewRecord(Collection collection);
	Invoicing viewInvoicing(Invoicing invoicing);
	List<Collection> getItemsRecordOfMy(Collection collection);
	List<Invoicing> getItemsInvoicing(Invoicing invoicing);
	List<Invoicing> getItemsInvoicingOfMy(Invoicing invoicing);
	List<CollectionDetail> editmx(Collection collection);

	int saveCollection(Collection collection,List<CollectionDetail> collectionDetails);
	int saveCollectionRecord(Collection collection);
	int saveCollectioncreateinvoicing(Invoicing invoicing);

}