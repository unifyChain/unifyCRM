package dafengqi.yunxiang.dao;

import java.util.List;

import dafengqi.yunxiang.model.Cost;
import dafengqi.yunxiang.model.ExpenseReport;

public interface ExpensereportDao extends GenericDao<ExpenseReport, Long> {

	public List<?> getItems(ExpenseReport expensereport);
	public List<?> getItemsOfMy(ExpenseReport expensereport);
	public List<?> editmx(Cost cost);
	public ExpenseReport edit(ExpenseReport expensereport);

	public int saveExpensereport(ExpenseReport expensereport, List<Cost> cost);


	public int update(ExpenseReport expensereport);

}