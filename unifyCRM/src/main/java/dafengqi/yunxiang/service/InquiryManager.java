package dafengqi.yunxiang.service;

import java.util.List;

import dafengqi.yunxiang.model.Inquiry;
import dafengqi.yunxiang.model.InquiryProduct;
import dafengqi.yunxiang.model.Product;

public interface InquiryManager extends GenericManager<Inquiry, Long> {

	List<Inquiry> getItems(Inquiry inquiry);
	Inquiry edit(Inquiry inquiry);

	int saveInquiry(Inquiry inquiry, List<InquiryProduct> inquiryProducts);

	int update(Inquiry inquiry, List<InquiryProduct> inquiryProducts);
	
//	Product getProductByBarCode(String barCode, String mechanismId);

}