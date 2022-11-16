package dafengqi.yunxiang.service;

import java.util.Date;
import java.util.List;

import dafengqi.yunxiang.model.BusinessOpportunity;

public interface BusinessOpportunityManager extends GenericManager<BusinessOpportunity, Long> {

	List<BusinessOpportunity> getItems(BusinessOpportunity sj);
	List<BusinessOpportunity> getItemsOfMy(BusinessOpportunity sj);
	BusinessOpportunity edit(BusinessOpportunity sj);
	int saveBusinessOpportunity(BusinessOpportunity sj);
	int update(BusinessOpportunity sj);
	int followUp(String departmentid, String id, String type,Date time,String content,String name,String status,String lxrid,String lxrmc,Date followtime,String createId,String cjsj,String mechanismid,String createName,String from);

}