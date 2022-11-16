package dafengqi.yunxiang.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dafengqi.yunxiang.dao.CostDao;
import dafengqi.yunxiang.model.Cost;
import dafengqi.yunxiang.service.CostManager;

@Service("costManager")
public class CostManagerImpl extends GenericManagerImpl<Cost, Long> implements CostManager, Serializable {

	CostDao costDao;

	@Autowired
	public CostManagerImpl(CostDao costDao) {

		this.costDao = costDao;

	}

	@Override
	public List getItems(Cost cost) {
		return costDao.getItems(cost);
	}

	@Override
	public List getItemsOfMy(Cost cost) {
		return costDao.getItemsOfMy(cost);
	}

	@Override
	public int saveCost(Cost cost) {
		return costDao.saveCost(cost);
	}

	@Override
	public int update(Cost cost) {
		return costDao.update(cost);
	}

	@Override
	public Cost edit(Cost cost) {
		return costDao.edit(cost);
	}

}