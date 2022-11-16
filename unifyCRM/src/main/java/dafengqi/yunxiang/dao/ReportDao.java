package dafengqi.yunxiang.dao;

import java.util.List;

import dafengqi.yunxiang.model.Report;

public interface ReportDao  extends GenericDao<Report, Long> {
	
	
	
	//DC
	public List<?> getItemsContractsUmmary(Report report);
	public List<?> getItemsCustomerNumBerranKing(Report report);
	public List<?> getItemsCustomerNumBerranKings(Report report);
	public List<?> getItemsDepartmentHonor(Report report);
	public List<?> getItemsPersonalHonor(Report report);
	public List<?> getItemsNewBusiness(Report report);
	public List<?> getItemsPayMentCollectionPlan(Report report);
	public List<?> getItemsPerformancegoalCompletionranking(Report report);
	public List<?> getItemsPerformanceobjectives(Report report);
	public List<?> getItemsSales(Report report);
	public List<?> getItemsWinOrderOpportUnity(Report report);
	public List<?> getItemsSalesVolume(Report report);
	public List<?> getItemsSalesVolumes(Report report);
	public List<?> getItems(Report report);
	public List<?> getItemslx(Report report);
	public List<?> getItemsSalesCollectionRanking(Report report);
}
