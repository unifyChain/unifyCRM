package dafengqi.yunxiang.service;

import java.util.List;

import dafengqi.yunxiang.model.Cost;
import dafengqi.yunxiang.model.ExpenseReport;

public interface ExpensereportManager extends GenericManager<ExpenseReport, Long> {

	List<ExpenseReport> getItems(ExpenseReport expensereport);
	List<ExpenseReport> getItemsOfMy(ExpenseReport expensereport);
	List<Cost> editmx(Cost cost);
	ExpenseReport edit(ExpenseReport expensereport);

	int saveExpensereport(ExpenseReport expensereport, List<Cost> cost);

	int update(ExpenseReport expensereport);

}