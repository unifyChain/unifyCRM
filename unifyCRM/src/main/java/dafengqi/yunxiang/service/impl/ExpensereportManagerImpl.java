package dafengqi.yunxiang.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dafengqi.yunxiang.dao.ExpensereportDao;
import dafengqi.yunxiang.model.Cost;
import dafengqi.yunxiang.model.ExpenseReport;
import dafengqi.yunxiang.service.ExpensereportManager;

@Service("expensereportManager")
public class ExpensereportManagerImpl extends GenericManagerImpl<ExpenseReport, Long> implements ExpensereportManager, Serializable {

	ExpensereportDao expensereportDao;

	@Autowired
	public ExpensereportManagerImpl(ExpensereportDao expensereportDao) {

		this.expensereportDao = expensereportDao;

	}

	@Override
	public List getItems(ExpenseReport expensereport) {
		return expensereportDao.getItems(expensereport);
	}
	@Override
	public List getItemsOfMy(ExpenseReport expensereport) {
		return expensereportDao.getItemsOfMy(expensereport);
	}

	@Override
	public List editmx(Cost cost) {
		return expensereportDao.editmx(cost);
	}

	@Override
	public int saveExpensereport(ExpenseReport expensereport, List<Cost> cost) {
		return expensereportDao.saveExpensereport(expensereport,cost);
	}
	@Override
	public int update(ExpenseReport expensereport) {
		return expensereportDao.update(expensereport);
	}

	@Override
	public ExpenseReport edit(ExpenseReport expensereport) {
		return expensereportDao.edit(expensereport);
	}

}