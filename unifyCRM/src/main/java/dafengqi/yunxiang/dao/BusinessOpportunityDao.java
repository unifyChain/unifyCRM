package dafengqi.yunxiang.dao;

import java.util.List;

import dafengqi.yunxiang.model.BusinessOpportunity;

public interface BusinessOpportunityDao extends GenericDao<BusinessOpportunity, Long> {

	public List<?> getItems(BusinessOpportunity businessOpportunity);
	public List<?> getItemsOfMy(BusinessOpportunity businessOpportunity);
	public BusinessOpportunity edit(BusinessOpportunity businessOpportunity);

	public int saveBusinessOpportunity(BusinessOpportunity businessOpportunity);
	public int update(BusinessOpportunity businessOpportunity);

}