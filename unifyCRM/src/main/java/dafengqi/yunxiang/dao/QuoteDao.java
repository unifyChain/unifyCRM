package dafengqi.yunxiang.dao;

import java.util.List;

import dafengqi.yunxiang.model.Quote;
import dafengqi.yunxiang.model.QuoteProduct;

public interface QuoteDao extends GenericDao<Quote, Long> {
 
	public List<?> getItems(Quote quote);
	
	public Quote edit(Quote quote);
	

	public int saveQuote(Quote quote, List<QuoteProduct> quoteProducts);

	public int update(Quote quote, List<QuoteProduct> quoteProducts);
	

}