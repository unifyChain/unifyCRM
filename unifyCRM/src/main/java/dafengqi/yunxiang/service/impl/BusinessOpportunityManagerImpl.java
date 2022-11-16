package dafengqi.yunxiang.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dafengqi.yunxiang.dao.BusinessOpportunityDao;
import dafengqi.yunxiang.model.BusinessOpportunity;
import dafengqi.yunxiang.service.BusinessOpportunityManager;

@Service("businessOpportunityManager")
public class BusinessOpportunityManagerImpl extends GenericManagerImpl<BusinessOpportunity, Long> implements BusinessOpportunityManager, Serializable {

	BusinessOpportunityDao businessOpportunityDao;

	@Autowired
	public BusinessOpportunityManagerImpl(BusinessOpportunityDao businessOpportunityDao) {

		this.businessOpportunityDao = businessOpportunityDao;

	}

	@Override
	public List getItems(BusinessOpportunity businessOpportunity) {
		return businessOpportunityDao.getItems(businessOpportunity);
	}
	@Override
	public List getItemsOfMy(BusinessOpportunity businessOpportunity) {
		return businessOpportunityDao.getItemsOfMy(businessOpportunity);
	}
	@Override
	public int saveBusinessOpportunity(BusinessOpportunity businessOpportunity) {
		return businessOpportunityDao.saveBusinessOpportunity(businessOpportunity);
	}
	@Override
	public int update(BusinessOpportunity businessOpportunity) {
		return businessOpportunityDao.update(businessOpportunity);
	}

	@Override
	public BusinessOpportunity edit(BusinessOpportunity businessOpportunity) {
		return businessOpportunityDao.edit(businessOpportunity);
	}
	@Override
	public int followUp(String departmentid, String id, String type,Date time,String content,String name,String status,String lxrid,String lxrmc,Date followtime,String createId,String cjsj,String mechanismid,String createName,String from) {
		return businessOpportunityDao.followUp(departmentid, id, type,time,content,name,status,lxrid,lxrmc,followtime,createId,cjsj,mechanismid,createName,from);
	}

}