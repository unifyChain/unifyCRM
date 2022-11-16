package dafengqi.yunxiang.service;

import java.util.List;

import dafengqi.yunxiang.model.Product;
import dafengqi.yunxiang.model.Quote;
import dafengqi.yunxiang.model.QuoteProduct;

public interface QuoteManager extends GenericManager<Quote, Long> {

	List<Quote> getItems(Quote quote);
	Quote edit(Quote quote);

	int saveQuote(Quote quote, List<QuoteProduct> quoteProducts);

	int update(Quote quote, List<QuoteProduct> quoteProducts);
	
//	Product getProductByBarCode(String barCode, String mechanismId);
	


}