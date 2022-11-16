package dafengqi.yunxiang.dao;

import java.util.List;

import dafengqi.yunxiang.model.Inquiry;
import dafengqi.yunxiang.model.InquiryProduct;

public interface InquiryDao extends GenericDao<Inquiry, Long> {
 
	public List<?> getItems(Inquiry inquiry);
	
	public Inquiry edit(Inquiry inquiry);
	

	public int saveInquiry(Inquiry inquiry, List<InquiryProduct> inquiryProducts);

	public int update(Inquiry inquiry, List<InquiryProduct> inquiryProducts);
	

}