package dafengqi.yunxiang.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dafengqi.yunxiang.dao.QuoteDao;
import dafengqi.yunxiang.model.Product;
import dafengqi.yunxiang.model.Quote;
import dafengqi.yunxiang.model.QuoteProduct;
import dafengqi.yunxiang.service.QuoteManager;

@Service("quoteManager")
public class QuoteManagerImpl extends GenericManagerImpl<Quote, Long> implements QuoteManager, Serializable {

	QuoteDao quoteDao;
 
	@Autowired
	public QuoteManagerImpl(QuoteDao quoteDao) {

		this.quoteDao = quoteDao;

	}
	@Override
	public List getItems(Quote quote) {
		return quoteDao.getItems(quote);
	}

	@Override
	public int saveQuote(Quote quote, List<QuoteProduct> quoteProducts) {
		return quoteDao.saveQuote(quote,quoteProducts);
	}

	@Override
	public int update(Quote quote, List<QuoteProduct> quoteProducts) {
		return quoteDao.update(quote,quoteProducts);
	}

	@Override
	public Quote edit(Quote quote) {
		return quoteDao.edit(quote);
	}

//	@Override
//	 public Product getProductByBarCode(String barCode, String mechanismId) {
//	  return quoteDao.getProductByBarCode(barCode,mechanismId);
//	 }

}