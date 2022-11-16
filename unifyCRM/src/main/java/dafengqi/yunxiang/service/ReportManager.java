package dafengqi.yunxiang.service;

import java.util.List;

import dafengqi.yunxiang.model.Report;

public interface ReportManager extends GenericManager<Report, Long>{
	
	
	
	
	
	
	
	
	
	//DC
	List<Report> getItemsContractsUmmary(Report report);
	List<Report> getItemsCustomerNumBerranKing(Report report);
	List<Report> getItemsCustomerNumBerranKings(Report report);
	List<Report> getItemsDepartmentHonor(Report report);
	List<Report> getItemsPersonalHonor(Report report);
	List<Report> getItemsNewBusiness(Report report);
	List<Report> getItemsPayMentCollectionPlan(Report report);
	List<Report> getItemsPerformancegoalCompletionranking(Report report);
	List<Report> getItemsSales(Report report);
	List<Report> getItemsPerformanceobjectives(Report report);
	List<Report> getItemsWinOrderOpportUnity(Report report);
	List<Report> getItemsSalesVolume(Report report);
	List<Report> getItemsSalesVolumes(Report report);
	List<Report> getItems(Report report);
	List<Report> getItemslx(Report report);
	List<Report> getItemsSalesCollectionRanking(Report report);

}
