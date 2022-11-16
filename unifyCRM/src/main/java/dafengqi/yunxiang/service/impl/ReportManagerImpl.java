package dafengqi.yunxiang.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dafengqi.yunxiang.dao.ReportDao;
import dafengqi.yunxiang.model.Report;
import dafengqi.yunxiang.service.ReportManager;

@Service("reportManager")
public class ReportManagerImpl extends GenericManagerImpl<Report, Long> implements ReportManager, Serializable {
	
	ReportDao reportDao;
	 
	@Autowired
	public ReportManagerImpl(ReportDao reportDao) {

		this.reportDao = reportDao;

	}
	
	
	
	//DC

	@Override
	public List getItemsContractsUmmary(Report report) {
		return reportDao.getItemsContractsUmmary(report);
	}
	@Override
	public List getItemsCustomerNumBerranKing(Report report) {
		return reportDao.getItemsCustomerNumBerranKing(report);
	}
	@Override
	public List getItemsCustomerNumBerranKings(Report report) {
		return reportDao.getItemsCustomerNumBerranKings(report);
	}
	@Override
	public List getItemsDepartmentHonor(Report report) {
		return reportDao.getItemsDepartmentHonor(report);
	}
	@Override
	public List getItemsPersonalHonor(Report report) {
		return reportDao.getItemsPersonalHonor(report);
	}
	@Override
	public List getItemsNewBusiness(Report report) {
		return reportDao.getItemsNewBusiness(report);
	}
	@Override
	public List getItemsPayMentCollectionPlan(Report report) {
		return reportDao.getItemsPayMentCollectionPlan(report);
	}
	@Override
	public List getItemsPerformancegoalCompletionranking(Report report) {
		return reportDao.getItemsPerformancegoalCompletionranking(report);
	}
	@Override
	public List getItemsPerformanceobjectives(Report report) {
		return reportDao.getItemsPerformanceobjectives(report);
	}
	@Override
	public List getItemsSales(Report report) {
		return reportDao.getItemsSales(report);
	}
	@Override
	public List getItemsWinOrderOpportUnity(Report report) {
		return reportDao.getItemsWinOrderOpportUnity(report);
	}
	@Override
	public List getItemsSalesVolume(Report report) {
		return reportDao.getItemsSalesVolume(report);
	}
	@Override
	public List getItemsSalesVolumes(Report report) {
		return reportDao.getItemsSalesVolumes(report);
	}
	@Override
	public List getItemsSalesCollectionRanking(Report report) {
		return reportDao.getItemsSalesCollectionRanking(report);
	}
	@Override
	public List getItems(Report report) {
		return reportDao.getItems(report);
	}
	@Override
	public List getItemslx(Report report) {
		return reportDao.getItemslx(report);
	}
}
