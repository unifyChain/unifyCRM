package dafengqi.yunxiang.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dafengqi.yunxiang.dao.InquiryDao;
import dafengqi.yunxiang.model.Inquiry;
import dafengqi.yunxiang.model.InquiryProduct;
import dafengqi.yunxiang.model.Product;
import dafengqi.yunxiang.service.InquiryManager;

@Service("inquiryManager")
public class InquiryManagerImpl extends GenericManagerImpl<Inquiry, Long> implements InquiryManager, Serializable {

	InquiryDao inquiryDao;
 
	@Autowired
	public InquiryManagerImpl(InquiryDao inquiryDao) {

		this.inquiryDao = inquiryDao;

	}
	@Override
	public List getItems(Inquiry inquiry) {
		return inquiryDao.getItems(inquiry);
	}

	@Override
	public int saveInquiry(Inquiry inquiry, List<InquiryProduct> inquiryProducts) {
		return inquiryDao.saveInquiry(inquiry,inquiryProducts);
	}

	@Override
	public int update(Inquiry inquiry, List<InquiryProduct> inquiryProducts) {
		return inquiryDao.update(inquiry,inquiryProducts);
	}

	@Override
	public Inquiry edit(Inquiry inquiry) {
		return inquiryDao.edit(inquiry);
	}

//	@Override
//	 public Product getProductByBarCode(String barCode, String mechanismId) {
//	  return inquiryDao.getProductByBarCode(barCode,mechanismId);
//	 }
}