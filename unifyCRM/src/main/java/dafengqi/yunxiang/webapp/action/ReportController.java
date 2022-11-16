
package dafengqi.yunxiang.webapp.action;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.TreeNode;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.optionconfig.animation.Animation;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.legend.LegendLabel;
import org.primefaces.model.charts.optionconfig.title.Title;

import dafengqi.yunxiang.model.Dict;
import dafengqi.yunxiang.model.Report;
import dafengqi.yunxiang.model.Role;
import dafengqi.yunxiang.service.DictManager;
import dafengqi.yunxiang.service.ReportManager;
import dafengqi.yunxiang.util.JsfUtil;


@ManagedBean(name = "reportController")
@SessionScoped
public class ReportController extends BasePage implements Serializable {


	private static final long serialVersionUID = 6498739293085202452L;
	
	private List<Role> modules;
	private List<Role> modelsSalesForeCast;
	private List<Role> modelsNewBusiness;
	private List<Role> modelsPayMentCollectionPlan;
	private List<Role> modelsWinOrderOpportUnity;
	private List<Role> modelsContractsUmmary;
	private List<Role> modelsPerformanceobjectives;
	private List<Role> modelsCustomerNumBerranKing;
	private List<Role> modelsPerformancegoalCompletionranking;
	private List<Role> modelsSalesVolume;
	private List<Role> modelsDepartmentHonor;
	private List<Role> modelsPersonalHonor;
	private List<Role> modelsCuetransFormation;
	private List<Role> modelsSalesCollectionRanking;
	private List<Report> itemsPerformanceobjectives = null;
	private List<Report> items = null;
	private List<Report> itemslx = null;
	private List<Report> itemsPerformancegoalCompletionranking = null;
	private List<Report> itemsSalesVolume = null;
	private List<Report> itemsDepartmentHonor = null;
	private List<Report> itemsPersonalHonor = null;
	private List<Report> itemsDepartmentHonorList = null;
	private List<Report> itemsPersonalHonorList = null;
	private List<Report> itemsSalesVolumes = null;
	private List<Report> itemsPerformancegoalCompletionrankingUser = null;
	private List<Report> itemsSales = null;
	private List<Report> itemsCustomerNumBerranKing = null;
	private List<Report> itemsSalesCollectionRanking = null;
	private List<Report> itemsSalesCollectionRankingDepartment = null;
	private List<Report> itemsCustomerNumBerranKings = null;
	private List<Report> itemsPayMentCollectionPlan = null;
	private List<Report> itemsWinOrderOpportUnity = null;
	private List<Report> itemsCuetransFormation = null;
	private List<Report> itemsContractsUmmary = null;
	private List<Report> itemsPerformanceobjectivesYear = null;
	private List<Report> itemsNewBusiness = null;
    private BarChartModel barModel;
    private BarChartModel barModellx;
    private BarChartModel newBusinessModel;
    private BarChartModel performanceobjectivesbarModel;
    private BarChartModel performancegoalCompletionrankingModel;
    private BarChartModel salesVolumeModel;
    private BarChartModel departmentHonorModel;
    private BarChartModel personalHonorModel;
    private BarChartModel salesVolumeModels;
    private BarChartModel performancegoalCompletionrankingbarModel;
    private BarChartModel salesModel;
    private BarChartModel payMentCollectionPlanModel;
    private BarChartModel winOrderOpportUnityModel;
    private BarChartModel cuetransFormationModel;
    private BarChartModel contractsUmmaryModel;
    private BarChartModel performanceobjectivesModel;
    private String type="跟进对象次数";
	private Report selectedReport = new Report();
	
	private DictManager dictManager;
	private ReportManager reportManager;
	
	private List<?> warehouse = new ArrayList();
	
	private Dict selected;
	
	int c = 0;
	private int c0 = 0;
	private int c01 = 0;
	private int c02 = 0;
	private int c03 = 0;
	private int c04 = 0;
	private int c05 = 0;
	private int c06 = 0;
	private int c07 = 0;
	private int c08 = 0;
	private int c09 = 0;
	private int c10 = 0;
	private int c11 = 0;
	private int c12 = 0;
	private int c13 = 0;
	private int c14 = 0;
	private int c15 = 0;
	private int c16 = 0;
	private int c17 = 0;
	private int c18 = 0;
	private int c19 = 0;
	private int c20 = 0;

	public ReportController() {
	}


	@PostConstruct 
	void init() {
		selectedReport = new Report();
		String mechanismId = (String) getSession().getAttribute("MECHANISMID");
		String from = (String) getSession().getAttribute("FROM");
		String departmentid = (String) getSession().getAttribute("BMJLID");
		if(departmentid==null) {
			departmentid="";
		}
		selectedReport.setFrom(from);
		selectedReport.setDepartmentIds(departmentid);
		selectedReport.setMechanismId(mechanismId);
		String username = (String) getSession().getAttribute("USERNAME");
		selectedReport.setUsername(username);
		itemsContractsUmmary = reportManager.getItemsContractsUmmary(selectedReport);
		BigDecimal tjhts=new BigDecimal(0);
		BigDecimal tjhtzje=new BigDecimal(0);
		for(Report report:itemsContractsUmmary) {
			if(report.getContractNumber()==null) {
				
			}else {
				tjhts=tjhts.add(report.getContractNumber());
			}
			if(report.getTotalContractAmount()==null) {
				
			}else {
				tjhtzje=tjhtzje.add(report.getTotalContractAmount());
			}
		}
		selectedReport.setContractNumber(tjhts);
		selectedReport.setContractAmount(tjhtzje);
		BigDecimal sjdqd=new BigDecimal(0);
		BigDecimal sjdqdje=new BigDecimal(0);
		itemsWinOrderOpportUnity = reportManager.getItemsWinOrderOpportUnity(selectedReport);
		for(Report report:itemsWinOrderOpportUnity) {
			if(report.getWinSingular()==null) {
				
			}else {
				sjdqd=sjdqd.add(report.getWinSingular());
			}
			if(report.getToWinASingleAmount()==null) {
				
			}else {
				sjdqdje=sjdqdje.add(report.getToWinASingleAmount());
			}
		}
		selectedReport.setSignTheSingular(sjdqd);
		selectedReport.setSignTheBillAmount(sjdqdje);
		BigDecimal jhhk=new BigDecimal(0);
		BigDecimal hk=new BigDecimal(0);
		itemsPayMentCollectionPlan = reportManager.getItemsPayMentCollectionPlan(selectedReport);
		for(Report report:itemsPayMentCollectionPlan) {
			
			if(report.getReceivableAmount()==null) {
				
			}else {
				jhhk=jhhk.add(report.getReceivableAmount());
			}
			if(report.getInvoiceAmount()==null) {
				
			}else {
				hk=hk.add(report.getInvoiceAmount());
			}
		}
		selectedReport.setAmountOfPlannedPaymentCollection(jhhk);
		selectedReport.setAmountPaidBack(hk);
		itemsNewBusiness = reportManager.getItemsNewBusiness(selectedReport);
		int xs=0;
		int kh=0;
		int sj=0;
		for(Report report:itemsNewBusiness) {
			xs=xs+=report.getClueNumber();
			kh=kh+=report.getCustomerNumber();
			sj=sj+=report.getBusinessOpportunitiesNumber();
		}
		selectedReport.setClueNumber(xs);
		selectedReport.setCustomerNumber(kh);
		selectedReport.setBusinessOpportunitiesNumber(sj);
//		items = reportManager.getItems(selectedReport);
//		int tj=0;
//		for(Report report:items) {
//			tj=tj+=report.getTj();
//		}
//		selectedReport.setTj(tj);
	}
	public void displaySelectedMultipledepartment(TreeNode nodes) {
			if(selectedReport==null) {
				selectedReport=new Report();
			}
			if(nodes==null){
				JsfUtil.warn("请上级机构!");
			}else{
				String nodestr = nodes.getData().toString();
				String[] nodearr = nodestr.split("-");
				selectedReport.setDepartmentId(nodearr[1]);
				selectedReport.setDepartmentIds(nodearr[1]);
				selectedReport.setDepartmentName(nodearr[0]);
			}
	}
    /**
     * 获取 当前年年度、日、小 始结束时
     */

    private final static SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
    
    private final static SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");;


    /**
     * 获得本周的第天，周一
     *
     * @return
     */
    public static Date getCurrentWeekDayStartTime() {
        Calendar c = Calendar.getInstance();
        try {
            int weekday = c.get(Calendar.DAY_OF_WEEK) - 2;
            c.add(Calendar.DATE, -weekday);
            c.setTime(longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c.getTime();
    }

    /**
     * 获得本周的最后一天，周日
     *
     * @return
     */
    public static Date getCurrentWeekDayEndTime() {
        Calendar c = Calendar.getInstance();
        try {
            int weekday = c.get(Calendar.DAY_OF_WEEK);
            c.add(Calendar.DATE, 8 - weekday);
            c.setTime(longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c.getTime();
    }

    /**
     * 获得本天的开始时
     *
     * @return
     */
    public static Date getCurrentDayStartTime() {
        Date now = new Date();
        try {
            now = shortSdf.parse(shortSdf.format(now));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 获得本天的结束时
     *
     * @return
     */
    public static Date getCurrentDayEndTime() {
        Date now = new Date();
        try {
            now = longSdf.parse(shortSdf.format(now) + " 23:59:59");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 获得本月的开始时
     *
     * @return
     */
    public static Date getCurrentMonthStartTime() {
        Calendar c = Calendar.getInstance();
        Date now = null;
        try {
            c.set(Calendar.DATE, 1);
            now = shortSdf.parse(shortSdf.format(c.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 本月的结束时
     *
     * @return
     */
    public static Date getCurrentMonthEndTime() {
        Calendar c = Calendar.getInstance();
        Date now = null;
        try {
            c.set(Calendar.DATE, 1);
            c.add(Calendar.MONTH, 1);
            c.add(Calendar.DATE, -1);
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 当前年的始时
     *
     * @return
     */
    public static Date getCurrentYearStartTime() {
        Calendar c = Calendar.getInstance();
        Date now = null;
        try {
            c.set(Calendar.MONTH, 0);
            c.set(Calendar.DATE, 1);
            now = shortSdf.parse(shortSdf.format(c.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 当前年的结束时间
     *
     * @return
     */
    public static Date getCurrentYearEndTime() {
        Calendar c = Calendar.getInstance();
        Date now = null;
        try {
            c.set(Calendar.MONTH, 11);
            c.set(Calendar.DATE, 31);
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 当前季度的开始时
     *
     * @return
     */
    public static Date getCurrentQuarterStartTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3)
                c.set(Calendar.MONTH, 0);
            else if (currentMonth >= 4 && currentMonth <= 6)
                c.set(Calendar.MONTH, 3);
            else if (currentMonth >= 7 && currentMonth <= 9)
                c.set(Calendar.MONTH, 4);
            else if (currentMonth >= 10 && currentMonth <= 12)
                c.set(Calendar.MONTH, 9);
            c.set(Calendar.DATE, 1);
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 当前季度的结束时
     *
     * @return
     */
    public static Date getCurrentQuarterEndTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3) {
                c.set(Calendar.MONTH, 2);
                c.set(Calendar.DATE, 31);
            } else if (currentMonth >= 4 && currentMonth <= 6) {
                c.set(Calendar.MONTH, 5);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 7 && currentMonth <= 9) {
                c.set(Calendar.MONTH, 8);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 10 && currentMonth <= 12) {
                c.set(Calendar.MONTH, 11);
                c.set(Calendar.DATE, 31);
            }
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 获取/后半年的始时
     *
     * @return
     */
    public static Date getHalfYearStartTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 6) {
                c.set(Calendar.MONTH, 0);
            } else if (currentMonth >= 7 && currentMonth <= 12) {
                c.set(Calendar.MONTH, 6);
            }
            c.set(Calendar.DATE, 1);
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;

    }

    /**
     * 获取/后半年的结束时间
     *
     * @return
     */
    public static Date getHalfYearEndTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 6) {
                c.set(Calendar.MONTH, 5);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 7 && currentMonth <= 12) {
                c.set(Calendar.MONTH, 11);
                c.set(Calendar.DATE, 31);
            }
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }
	public String writeFollowUp(String writeFollowUpTime) {
		if(selectedReport==null){
			selectedReport=new Report();
		}
		if(writeFollowUpTime.equals("全部")){
			selectedReport.setTime("全部");
		}
		if(writeFollowUpTime.equals("今日")){
			selectedReport.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayStartTime().getTime()));
			selectedReport.setZtime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayEndTime().getTime()));
		}
		if(writeFollowUpTime.equals("本周")){
			selectedReport.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentWeekDayStartTime().getTime()));
			selectedReport.setZtime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentWeekDayEndTime().getTime()));
		}
		if(writeFollowUpTime.equals("本月")){
			selectedReport.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthStartTime().getTime()));
			selectedReport.setZtime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthEndTime().getTime()));
		}
		if(writeFollowUpTime.equals("本季")){
			selectedReport.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentQuarterStartTime().getTime()));
			selectedReport.setZtime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentQuarterEndTime().getTime()));
		}
		if(writeFollowUpTime.equals("本年")){
			selectedReport.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearStartTime().getTime()));
			selectedReport.setZtime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearEndTime().getTime()));
		} 
		items = null;
		itemsSales=null;
		itemsPayMentCollectionPlan=null;
		itemsWinOrderOpportUnity=null;
		itemsCuetransFormation=null;
		itemsContractsUmmary=null;
		itemsNewBusiness=null;
		itemsPerformanceobjectives=null;
		itemsPerformanceobjectivesYear=null;
		itemsCustomerNumBerranKings=null;
		itemsCustomerNumBerranKing=null;
		itemsSalesCollectionRanking=null;
		itemsPerformancegoalCompletionranking=null;
		itemsPerformancegoalCompletionrankingUser=null;
		itemsSalesVolume=null;
		itemsPersonalHonor=null;
		itemsDepartmentHonor=null;
		return null;
	}

	public String actualFollowUp(String actualFollowUpTime) {
		if(selectedReport==null){
			selectedReport=new Report();
		}
		if(actualFollowUpTime.equals("全部")){
			selectedReport.setTime("全部");
		}
		if(actualFollowUpTime.equals("今日")){
			selectedReport.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayStartTime().getTime()));
			selectedReport.setZtime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayEndTime().getTime()));
		}
		if(actualFollowUpTime.equals("本周")){
			selectedReport.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentWeekDayStartTime().getTime()));
			selectedReport.setZtime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentWeekDayEndTime().getTime()));
		}
		if(actualFollowUpTime.equals("本月")){
			selectedReport.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthStartTime().getTime()));
			selectedReport.setZtime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthEndTime().getTime()));
		}
		if(actualFollowUpTime.equals("本季")){
			selectedReport.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentQuarterStartTime().getTime()));
			selectedReport.setZtime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentQuarterEndTime().getTime()));
		}
		if(actualFollowUpTime.equals("本年")){
			selectedReport.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearStartTime().getTime()));
			selectedReport.setZtime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearEndTime().getTime()));
		}
		items = null;
		itemsSales=null;
		itemsPayMentCollectionPlan=null;
		itemsWinOrderOpportUnity=null;
		itemsCuetransFormation=null;
		itemsContractsUmmary=null;
		itemsNewBusiness=null;
		itemsPerformanceobjectives=null;
		itemsPerformanceobjectivesYear=null;
		itemsCustomerNumBerranKings=null;
		itemsCustomerNumBerranKing=null;
		itemsSalesCollectionRanking=null;
		itemsPerformancegoalCompletionranking=null;
		itemsPerformancegoalCompletionrankingUser=null;
		itemsSalesVolume=null;
		itemsPersonalHonor=null;
		itemsDepartmentHonor=null;
		return null;
	}
	public List<Report> calcs() {
		if(selectedReport==null){
			selectedReport=new Report();
		}
		itemsSales=null;
		itemsPayMentCollectionPlan=null;
		itemsWinOrderOpportUnity=null;
		itemsCuetransFormation=null;
		itemsContractsUmmary=null;
		itemsNewBusiness=null;
		itemsPerformanceobjectives=null;
		itemsPerformanceobjectivesYear=null;
		itemsCustomerNumBerranKings=null;
		itemsCustomerNumBerranKing=null;
		itemsSalesCollectionRanking=null;
		itemsPerformancegoalCompletionranking=null;
		itemsPerformancegoalCompletionrankingUser=null;
		itemsSalesVolume=null;
		itemsPersonalHonor=null;
		itemsDepartmentHonor=null;
		return itemsSales;
	}

	public String type(String types) {
		type=types;
		return type;
	}
	public Report reset() {
		selectedReport=new Report();
		return selectedReport;
	}
	
	
	public List<Role> getModules() {
		if(modules==null) {
			String table = "role_module";
			String roleid = (String) getSession().getAttribute("ROLEID");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			modules=this.getModulesByRole(table, roleid, mechanismId, "跟进记录报表");
		}
		return modules;
	}

	public void setModules(List<Role> modules) {
		this.modules = modules;
	}

	public List<Role> getModelsSalesForeCast() {
		if(modelsSalesForeCast==null) {
			String table = "role_module";
			String roleid = (String) getSession().getAttribute("ROLEID");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			modelsSalesForeCast=this.getModulesByRole(table, roleid, mechanismId, "售预测报");
		}
		return modelsSalesForeCast;
	}

	public void setModelsSalesForeCast(List<Role> modelsSalesForeCast) {
		this.modelsSalesForeCast = modelsSalesForeCast;
	}

	public List<Role> getModelsNewBusiness() {
		if(modelsNewBusiness==null) {
			String table = "role_module";
			String roleid = (String) getSession().getAttribute("ROLEID");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			modelsNewBusiness=this.getModulesByRole(table, roleid, mechanismId, "业务新增汇");
		}
	
		return modelsNewBusiness;
	}

	public void setModelsNewBusiness(List<Role> modelsNewBusiness) {
		this.modelsNewBusiness = modelsNewBusiness;
	}

	public List<Role> getModelsPayMentCollectionPlan() {
		if(modelsPayMentCollectionPlan==null) {
			String table = "role_module";
			String roleid = (String) getSession().getAttribute("ROLEID");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			modelsPayMentCollectionPlan=this.getModulesByRole(table, roleid, mechanismId, "回款计划汇");
		}
		return modelsPayMentCollectionPlan;
	}

	public void setModelsPayMentCollectionPlan(List<Role> modelsPayMentCollectionPlan) {
		this.modelsPayMentCollectionPlan = modelsPayMentCollectionPlan;
	}

	public List<Role> getModelsWinOrderOpportUnity() {
		if(modelsWinOrderOpportUnity==null) {
			String table = "role_module";
			String roleid = (String) getSession().getAttribute("ROLEID");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			modelsWinOrderOpportUnity=this.getModulesByRole(table, roleid, mechanismId, "赢单商机汇");
		}
		return modelsWinOrderOpportUnity;
	}

	public void setModelsWinOrderOpportUnity(List<Role> modelsWinOrderOpportUnity) {
		this.modelsWinOrderOpportUnity = modelsWinOrderOpportUnity;
	}

	public List<Role> getModelsContractsUmmary() {
		if(modelsContractsUmmary==null) {
			String table = "role_module";
			String roleid = (String) getSession().getAttribute("ROLEID");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			modelsContractsUmmary=this.getModulesByRole(table, roleid, mechanismId, "合同汇");
		}
		return modelsContractsUmmary;
	}

	public void setModelsContractsUmmary(List<Role> modelsContractsUmmary) {
		this.modelsContractsUmmary = modelsContractsUmmary;
	}

	public List<Role> getModelsPerformanceobjectives() {
		if(modelsPerformanceobjectives==null) {
			String table = "role_module";
			String roleid = (String) getSession().getAttribute("ROLEID");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			modelsPerformanceobjectives=this.getModulesByRole(table, roleid, mechanismId, "业绩目标完成度报");
		}
		return modelsPerformanceobjectives;
	}

	public void setModelsPerformanceobjectives(List<Role> modelsPerformanceobjectives) {
		this.modelsPerformanceobjectives = modelsPerformanceobjectives;
	}

	public List<Role> getModelsCustomerNumBerranKing() {
		if(modelsCustomerNumBerranKing==null) {
			String table = "role_module";
			String roleid = (String) getSession().getAttribute("ROLEID");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			modelsCustomerNumBerranKing=this.getModulesByRole(table, roleid, mechanismId, "客户数量排名报表");
		}
		return modelsCustomerNumBerranKing;
	}

	public void setModelsCustomerNumBerranKing(List<Role> modelsCustomerNumBerranKing) {
		this.modelsCustomerNumBerranKing = modelsCustomerNumBerranKing;
	}

	public List<Role> getModelsPerformancegoalCompletionranking() {
		if(modelsCustomerNumBerranKing==null) {
			String table = "role_module";
			String roleid = (String) getSession().getAttribute("ROLEID");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			modelsCustomerNumBerranKing=this.getModulesByRole(table, roleid, mechanismId, "业绩目标完成度排名报");
		}
		return modelsPerformancegoalCompletionranking;
	}

	public void setModelsPerformancegoalCompletionranking(List<Role> modelsPerformancegoalCompletionranking) {
		this.modelsPerformancegoalCompletionranking = modelsPerformancegoalCompletionranking;
	}

	public List<Role> getModelsSalesVolume() {
		if(modelsSalesVolume==null) {
			String table = "role_module";
			String roleid = (String) getSession().getAttribute("ROLEID");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			modelsSalesVolume=this.getModulesByRole(table, roleid, mechanismId, "售额排名报表");
		}
		return modelsSalesVolume;
	}

	public void setModelsSalesVolume(List<Role> modelsSalesVolume) {
		this.modelsSalesVolume = modelsSalesVolume;
	}

	public List<Role> getModelsDepartmentHonor() {
		if(modelsDepartmentHonor==null) {
			String table = "role_module";
			String roleid = (String) getSession().getAttribute("ROLEID");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			modelsDepartmentHonor=this.getModulesByRole(table, roleid, mechanismId, "部门荣誉");
		}
		return modelsDepartmentHonor;
	}

	public void setModelsDepartmentHonor(List<Role> modelsDepartmentHonor) {
		this.modelsDepartmentHonor = modelsDepartmentHonor;
	}

	public List<Role> getModelsPersonalHonor() {
		if(modelsPersonalHonor==null) {
			String table = "role_module";
			String roleid = (String) getSession().getAttribute("ROLEID");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			modelsPersonalHonor=this.getModulesByRole(table, roleid, mechanismId, "个人荣誉");
		}
		return modelsPersonalHonor;
	}

	public void setModelsPersonalHonor(List<Role> modelsPersonalHonor) {
		this.modelsPersonalHonor = modelsPersonalHonor;
	}

	public List<Role> getModelsCuetransFormation() {
		if(modelsCuetransFormation==null) {
			String table = "role_module";
			String roleid = (String) getSession().getAttribute("ROLEID");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			modelsCuetransFormation=this.getModulesByRole(table, roleid, mechanismId, "线索转化");
		}
		return modelsCuetransFormation;
	}

	public void setModelsCuetransFormation(List<Role> modelsCuetransFormation) {
		this.modelsCuetransFormation = modelsCuetransFormation;
	}

	public List<Role> getModelsSalesCollectionRanking() {
		if(modelsSalesCollectionRanking==null) {
			String table = "role_module";
			String roleid = (String) getSession().getAttribute("ROLEID");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			modelsSalesCollectionRanking=this.getModulesByRole(table, roleid, mechanismId, "售回款排名报");
		}
		return modelsSalesCollectionRanking;
	}

	public void setModelsSalesCollectionRanking(List<Role> modelsSalesCollectionRanking) {
		this.modelsSalesCollectionRanking = modelsSalesCollectionRanking;
	}


	public DictManager getDictManager() {
		return dictManager;
	}


	public void setDictManager(DictManager dictManager) {
		this.dictManager = dictManager;
	}


	public List<?> getWarehouse() {
		
		if(warehouse==null || warehouse.size()==0) {
			if(selected==null){
				selected=new Dict();
			}
			selected.setDictType("warehouse");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String mechanismName = (String) getSession().getAttribute("MECHANISMNAME");
			selected.setMechanismId(mechanismId);
			selected.setMechanismName(mechanismName);
			String from = (String) getSession().getAttribute("FROM");
			selected.setFrom(from);
			warehouse = dictManager.getItemsByDictType(selected);
			int i = warehouse.size();
			switch (i) {

			case 1:
				c01 = 1;

				break;

			case 2:
				c01 = 1;
				c02 = 1;

				break;

			case 3:
				c01 = 1;
				c02 = 1;
				c03 = 1;
				break;
			case 4:
				c01 = 1;
				c02 = 1;
				c03 = 1;
				c04 = 1;
				break;
			case 5:
				c01 = 1;
				c02 = 1;
				c03 = 1;
				c04 = 1;
				c05 = 1;
				break;
			case 6:
				c01 = 1;
				c02 = 1;
				c03 = 1;
				c04 = 1;
				c05 = 1;
				c06 = 1;
				break;
			case 7:
				c01 = 1;
				c02 = 1;
				c03 = 1;
				c04 = 1;
				c05 = 1;
				c06 = 1;
				c07 = 1;
				break;
			case 8:
				c01 = 1;
				c02 = 1;
				c03 = 1;
				c04 = 1;
				c05 = 1;
				c06 = 1;
				c07 = 1;
				c08 = 1;
				break;

			case 9:
				c01 = 1;
				c02 = 1;
				c03 = 1;
				c04 = 1;
				c05 = 1;
				c06 = 1;
				c07 = 1;
				c08 = 1;
				c09 = 1;
				break;
			case 10:
				c01 = 1;
				c02 = 1;
				c03 = 1;
				c04 = 1;
				c05 = 1;
				c06 = 1;
				c07 = 1;
				c08 = 1;
				c09 = 1;
				c10 = 1;
				break;
			case 11:
				c01 = 1;
				c02 = 1;
				c03 = 1;
				c04 = 1;
				c05 = 1;
				c06 = 1;
				c07 = 1;
				c08 = 1;
				c09 = 1;
				c10 = 1;
				c11 = 1;
				break;
			case 12:
				c01 = 1;
				c02 = 1;
				c03 = 1;
				c04 = 1;
				c05 = 1;
				c06 = 1;
				c07 = 1;
				c08 = 1;
				c09 = 1;
				c10 = 1;
				c11 = 1;
				c12 = 1;
				break;
			case 13:
				c01 = 1;
				c02 = 1;
				c03 = 1;
				c04 = 1;
				c05 = 1;
				c06 = 1;
				c07 = 1;
				c08 = 1;
				c09 = 1;
				c10 = 1;
				c11 = 1;
				c12 = 1;
				c13 = 1;
				break;
			case 14:
				c01 = 1;
				c02 = 1;
				c03 = 1;
				c04 = 1;
				c05 = 1;
				c06 = 1;
				c07 = 1;
				c08 = 1;
				c09 = 1;
				c10 = 1;
				c11 = 1;
				c12 = 1;
				c13 = 1;
				c14 = 1;
				break;
			case 15:
				c01 = 1;
				c02 = 1;
				c03 = 1;
				c04 = 1;
				c05 = 1;
				c06 = 1;
				c07 = 1;
				c08 = 1;
				c09 = 1;
				c10 = 1;
				c11 = 1;
				c12 = 1;
				c13 = 1;
				c14 = 1;
				c15 = 1;
				break;
			case 16:
				c01 = 1;
				c02 = 1;
				c03 = 1;
				c04 = 1;
				c05 = 1;
				c06 = 1;
				c07 = 1;
				c08 = 1;
				c09 = 1;
				c10 = 1;
				c11 = 1;
				c12 = 1;
				c13 = 1;
				c14 = 1;
				c15 = 1;
				c16 = 1;
				break;
			case 17:
				c01 = 1;
				c02 = 1;
				c03 = 1;
				c04 = 1;
				c05 = 1;
				c06 = 1;
				c07 = 1;
				c08 = 1;
				c09 = 1;
				c10 = 1;
				c11 = 1;
				c12 = 1;
				c13 = 1;
				c14 = 1;
				c15 = 1;
				c16 = 1;
				c17 = 1;
				break;
			case 18:
				c01 = 1;
				c02 = 1;
				c03 = 1;
				c04 = 1;
				c05 = 1;
				c06 = 1;
				c07 = 1;
				c08 = 1;
				c09 = 1;
				c10 = 1;
				c11 = 1;
				c12 = 1;
				c13 = 1;
				c14 = 1;
				c15 = 1;
				c16 = 1;
				c17 = 1;
				c18 = 1;
				break;
			case 19:
				c01 = 1;
				c02 = 1;
				c03 = 1;
				c04 = 1;
				c05 = 1;
				c06 = 1;
				c07 = 1;
				c08 = 1;
				c09 = 1;
				c10 = 1;
				c11 = 1;
				c12 = 1;
				c13 = 1;
				c14 = 1;
				c15 = 1;
				c16 = 1;
				c17 = 1;
				c18 = 1;
				c19 = 1;
				break;

			case 20:
				c01 = 1;
				c02 = 1;
				c03 = 1;
				c04 = 1;
				c05 = 1;
				c06 = 1;
				c07 = 1;
				c08 = 1;
				c09 = 1;
				c10 = 1;
				c11 = 1;
				c12 = 1;
				c13 = 1;
				c14 = 1;
				c15 = 1;
				c16 = 1;
				c17 = 1;
				c18 = 1;
				c19 = 1;
				c20 = 1;
				break;

			}
		}
		
		return warehouse;
	}


	public void setWarehouse(List<?> warehouse) {
		this.warehouse = warehouse;
	}


	public Dict getSelected() {
		return selected;
	}


	public void setSelected(Dict selected) {
		this.selected = selected;
	}


	public int getC() {
		return c;
	}


	public void setC(int c) {
		this.c = c;
	}


	public int getC0() {
		return c0;
	}


	public void setC0(int c0) {
		this.c0 = c0;
	}


	public int getC01() {
		return c01;
	}


	public void setC01(int c01) {
		this.c01 = c01;
	}


	public int getC02() {
		return c02;
	}


	public void setC02(int c02) {
		this.c02 = c02;
	}


	public int getC03() {
		return c03;
	}


	public void setC03(int c03) {
		this.c03 = c03;
	}


	public int getC04() {
		return c04;
	}


	public void setC04(int c04) {
		this.c04 = c04;
	}


	public int getC05() {
		return c05;
	}


	public void setC05(int c05) {
		this.c05 = c05;
	}


	public int getC06() {
		return c06;
	}


	public void setC06(int c06) {
		this.c06 = c06;
	}


	public int getC07() {
		return c07;
	}


	public void setC07(int c07) {
		this.c07 = c07;
	}


	public int getC08() {
		return c08;
	}


	public void setC08(int c08) {
		this.c08 = c08;
	}


	public int getC09() {
		return c09;
	}


	public void setC09(int c09) {
		this.c09 = c09;
	}


	public int getC10() {
		return c10;
	}


	public void setC10(int c10) {
		this.c10 = c10;
	}


	public int getC11() {
		return c11;
	}


	public void setC11(int c11) {
		this.c11 = c11;
	}


	public int getC12() {
		return c12;
	}


	public void setC12(int c12) {
		this.c12 = c12;
	}


	public int getC13() {
		return c13;
	}


	public void setC13(int c13) {
		this.c13 = c13;
	}


	public int getC14() {
		return c14;
	}


	public void setC14(int c14) {
		this.c14 = c14;
	}


	public int getC15() {
		return c15;
	}


	public void setC15(int c15) {
		this.c15 = c15;
	}


	public int getC16() {
		return c16;
	}


	public void setC16(int c16) {
		this.c16 = c16;
	}


	public int getC17() {
		return c17;
	}


	public void setC17(int c17) {
		this.c17 = c17;
	}


	public int getC18() {
		return c18;
	}


	public void setC18(int c18) {
		this.c18 = c18;
	}


	public int getC19() {
		return c19;
	}


	public void setC19(int c19) {
		this.c19 = c19;
	}


	public int getC20() {
		return c20;
	}


	public void setC20(int c20) {
		this.c20 = c20;
	}


	public List<Report> getItemsPerformanceobjectives() {
		if(selectedReport==null){
			selectedReport=new Report();
		}
		String mechanismId = (String) getSession().getAttribute("MECHANISMID");
		String from = (String) getSession().getAttribute("FROM");
		String departmentid = (String) getSession().getAttribute("BMJLID");
		if(departmentid==null) {
			departmentid="";
		}
		selectedReport.setFrom(from);
		selectedReport.setDepartmentIds(departmentid);
		selectedReport.setMechanismId(mechanismId);
		String username = (String) getSession().getAttribute("USERNAME");
		selectedReport.setUsername(username);
		if (itemsPerformanceobjectives == null||itemsPerformanceobjectives.size()==0) {
			itemsPerformanceobjectives = reportManager.getItemsPerformanceobjectives(selectedReport);
		}
		return itemsPerformanceobjectives;
	}

	public void setItemsPerformanceobjectives(List<Report> itemsPerformanceobjectives) {
		this.itemsPerformanceobjectives = itemsPerformanceobjectives;
	}

	public List<Report> getItemsPerformancegoalCompletionranking() {
		if(selectedReport==null){
			selectedReport=new Report();
		}
		String mechanismId = (String) getSession().getAttribute("MECHANISMID");
		String from = (String) getSession().getAttribute("FROM");
		String departmentid = (String) getSession().getAttribute("BMJLID");
		if(departmentid==null) {
			departmentid="";
		}
		selectedReport.setFrom(from);
		selectedReport.setDepartmentIds(departmentid);
		selectedReport.setMechanismId(mechanismId);
		String username = (String) getSession().getAttribute("USERNAME");
		selectedReport.setUsername(username);
		if (itemsPerformancegoalCompletionranking == null||itemsPerformancegoalCompletionranking.size()==0) {
			itemsPerformancegoalCompletionranking = reportManager.getItemsPerformancegoalCompletionranking(selectedReport);
		}
		return itemsPerformancegoalCompletionranking;
	}

	public void setItemsPerformancegoalCompletionranking(List<Report> itemsPerformancegoalCompletionranking) {
		this.itemsPerformancegoalCompletionranking = itemsPerformancegoalCompletionranking;
	}

	public List<Report> getItemsSalesVolume() {
		if(selectedReport==null){
			selectedReport=new Report();
		}
		String mechanismId = (String) getSession().getAttribute("MECHANISMID");
		String from = (String) getSession().getAttribute("FROM");
		String departmentid = (String) getSession().getAttribute("BMJLID");
		if(departmentid==null) {
			departmentid="";
		}
		selectedReport.setFrom(from);
		selectedReport.setDepartmentIds(departmentid);
		selectedReport.setMechanismId(mechanismId);
		if (itemsSalesVolume == null||itemsSalesVolume.size()==0) {
			itemsSalesVolume = reportManager.getItemsSalesVolume(selectedReport);
		}
		return itemsSalesVolume;
	}

	public void setItemsSalesVolume(List<Report> itemsSalesVolume) {
		this.itemsSalesVolume = itemsSalesVolume;
	}

	public List<Report> getItemsDepartmentHonor() {
		if(selectedReport==null){
			selectedReport=new Report();
		}
		String mechanismId = (String) getSession().getAttribute("MECHANISMID");
		String from = (String) getSession().getAttribute("FROM");
		String departmentid = (String) getSession().getAttribute("BMJLID");
		if(departmentid==null) {
			departmentid="";
		}
		selectedReport.setFrom(from);
		selectedReport.setDepartmentIds(departmentid);
		selectedReport.setMechanismId(mechanismId);
		String username = (String) getSession().getAttribute("USERNAME");
		selectedReport.setUsername(username);
		if (itemsDepartmentHonor == null||itemsDepartmentHonor.size()==0) {
			itemsDepartmentHonor = reportManager.getItemsDepartmentHonor(selectedReport);
		}
		return itemsDepartmentHonor;
	}

	public void setItemsDepartmentHonor(List<Report> itemsDepartmentHonor) {
		this.itemsDepartmentHonor = itemsDepartmentHonor;
	}

	public List<Report> getItemsPersonalHonor() {
		if(selectedReport==null){
			selectedReport=new Report();
		}
		String mechanismId = (String) getSession().getAttribute("MECHANISMID");
		String from = (String) getSession().getAttribute("FROM");
		String departmentid = (String) getSession().getAttribute("BMJLID");
		if(departmentid==null) {
			departmentid="";
		}
		selectedReport.setFrom(from);
		selectedReport.setDepartmentIds(departmentid);
		selectedReport.setMechanismId(mechanismId);
		String username = (String) getSession().getAttribute("USERNAME");
		selectedReport.setUsername(username);
		if (itemsPersonalHonor == null||itemsPersonalHonor.size()==0) {
			itemsPersonalHonor = reportManager.getItemsPersonalHonor(selectedReport);
		}
		return itemsPersonalHonor;
	}

	public void setItemsPersonalHonor(List<Report> itemsPersonalHonor) {
		this.itemsPersonalHonor = itemsPersonalHonor;
	}

	public List<Report> getItemsDepartmentHonorList() {
		return itemsDepartmentHonorList;
	}

	public void setItemsDepartmentHonorList(List<Report> itemsDepartmentHonorList) {
		this.itemsDepartmentHonorList = itemsDepartmentHonorList;
	}

	public List<Report> getItemsPersonalHonorList() {
		return itemsPersonalHonorList;
	}

	public void setItemsPersonalHonorList(List<Report> itemsPersonalHonorList) {
		this.itemsPersonalHonorList = itemsPersonalHonorList;
	}

	public List<Report> getItemsSalesVolumes() {
		if(selectedReport==null){
			selectedReport=new Report();
		}
		String mechanismId = (String) getSession().getAttribute("MECHANISMID");
		String from = (String) getSession().getAttribute("FROM");
		String departmentid = (String) getSession().getAttribute("BMJLID");
		if(departmentid==null) {
			departmentid="";
		}
		selectedReport.setFrom(from);
		selectedReport.setDepartmentIds(departmentid);
		selectedReport.setMechanismId(mechanismId);
		String username = (String) getSession().getAttribute("USERNAME");
		selectedReport.setUsername(username);
		if (itemsSalesVolumes == null||itemsSalesVolumes.size()==0) {
			itemsSalesVolumes = reportManager.getItemsSalesVolumes(selectedReport);
		}
		return itemsSalesVolumes;
	}

	public void setItemsSalesVolumes(List<Report> itemsSalesVolumes) {
		this.itemsSalesVolumes = itemsSalesVolumes;
	}

	public List<Report> getItemsPerformancegoalCompletionrankingUser() {
		return itemsPerformancegoalCompletionrankingUser;
	}

	public void setItemsPerformancegoalCompletionrankingUser(List<Report> itemsPerformancegoalCompletionrankingUser) {
		this.itemsPerformancegoalCompletionrankingUser = itemsPerformancegoalCompletionrankingUser;
	}

	public List<Report> getItemsSales() {
		if(selectedReport==null){
			selectedReport=new Report();
		}
		String mechanismId = (String) getSession().getAttribute("MECHANISMID");
		String from = (String) getSession().getAttribute("FROM");
		String departmentid = (String) getSession().getAttribute("BMJLID");
		if(departmentid==null) {
			departmentid="";
		}
		selectedReport.setFrom(from);
		selectedReport.setDepartmentIds(departmentid);
		selectedReport.setMechanismId(mechanismId);
		String username = (String) getSession().getAttribute("USERNAME");
		selectedReport.setUsername(username);
		if (itemsSales == null||itemsSales.size()==0) {
			itemsSales = reportManager.getItemsSales(selectedReport);
		}
		return itemsSales;
	}

	public void setItemsSales(List<Report> itemsSales) {
		this.itemsSales = itemsSales;
	}

	public List<Report> getItemsCustomerNumBerranKing() {
		if(selectedReport==null){
			selectedReport=new Report();
		}
		String mechanismId = (String) getSession().getAttribute("MECHANISMID");
		String from = (String) getSession().getAttribute("FROM");
		String departmentid = (String) getSession().getAttribute("BMJLID");
		if(departmentid==null) {
			departmentid="";
		}
		selectedReport.setFrom(from);
		selectedReport.setDepartmentIds(departmentid);
		selectedReport.setMechanismId(mechanismId);
		String username = (String) getSession().getAttribute("USERNAME");
		selectedReport.setUsername(username);
		if (itemsCustomerNumBerranKing == null||itemsCustomerNumBerranKing.size()==0) {
			itemsCustomerNumBerranKing = reportManager.getItemsCustomerNumBerranKing(selectedReport);
		}
		return itemsCustomerNumBerranKing;
	}

	public void setItemsCustomerNumBerranKing(List<Report> itemsCustomerNumBerranKing) {
		this.itemsCustomerNumBerranKing = itemsCustomerNumBerranKing;
	}

	public List<Report> getItemsSalesCollectionRanking() {
		if(selectedReport==null){
			selectedReport=new Report();
		}
		String mechanismId = (String) getSession().getAttribute("MECHANISMID");
		String from = (String) getSession().getAttribute("FROM");
		String departmentid = (String) getSession().getAttribute("BMJLID");
		if(departmentid==null) {
			departmentid="";
		}
		selectedReport.setFrom(from);
		selectedReport.setDepartmentIds(departmentid);
		selectedReport.setMechanismId(mechanismId);
		String username = (String) getSession().getAttribute("USERNAME");
		selectedReport.setUsername(username);
		if (itemsSalesCollectionRanking == null||itemsSalesCollectionRanking.size()==0) {
			itemsSalesCollectionRanking = reportManager.getItemsSalesCollectionRanking(selectedReport);
		}
		return itemsSalesCollectionRanking;
	}

	public void setItemsSalesCollectionRanking(List<Report> itemsSalesCollectionRanking) {
		this.itemsSalesCollectionRanking = itemsSalesCollectionRanking;
	}

	public List<Report> getItemsSalesCollectionRankingDepartment() {
		return itemsSalesCollectionRankingDepartment;
	}

	public void setItemsSalesCollectionRankingDepartment(List<Report> itemsSalesCollectionRankingDepartment) {
		this.itemsSalesCollectionRankingDepartment = itemsSalesCollectionRankingDepartment;
	}

	public List<Report> getItemsCustomerNumBerranKings() {
		if(selectedReport==null){
			selectedReport=new Report();
		}
		String mechanismId = (String) getSession().getAttribute("MECHANISMID");
		String from = (String) getSession().getAttribute("FROM");
		String departmentid = (String) getSession().getAttribute("BMJLID");
		if(departmentid==null) {
			departmentid="";
		}
		selectedReport.setFrom(from);
		selectedReport.setDepartmentIds(departmentid);
		selectedReport.setMechanismId(mechanismId);
		String username = (String) getSession().getAttribute("USERNAME");
		selectedReport.setUsername(username);
		if (itemsCustomerNumBerranKings == null||itemsCustomerNumBerranKings.size()==0) {
			itemsCustomerNumBerranKings = reportManager.getItemsCustomerNumBerranKings(selectedReport);
		}
		return itemsCustomerNumBerranKings;
	}

	public void setItemsCustomerNumBerranKings(List<Report> itemsCustomerNumBerranKings) {
		this.itemsCustomerNumBerranKings = itemsCustomerNumBerranKings;
	}

	public List<Report> getItemsPayMentCollectionPlan() {
		if(selectedReport==null){
			selectedReport=new Report();
		}
		String mechanismId = (String) getSession().getAttribute("MECHANISMID");
		String from = (String) getSession().getAttribute("FROM");
		String departmentid = (String) getSession().getAttribute("BMJLID");
		if(departmentid==null) {
			departmentid="";
		}
		selectedReport.setFrom(from);
		selectedReport.setDepartmentIds(departmentid);
		selectedReport.setMechanismId(mechanismId);
		String username = (String) getSession().getAttribute("USERNAME");
		selectedReport.setUsername(username);
		if (itemsPayMentCollectionPlan == null||itemsPayMentCollectionPlan.size()==0) {
			itemsPayMentCollectionPlan = reportManager.getItemsPayMentCollectionPlan(selectedReport);
		}
		return itemsPayMentCollectionPlan;
	}

	public void setItemsPayMentCollectionPlan(List<Report> itemsPayMentCollectionPlan) {
		this.itemsPayMentCollectionPlan = itemsPayMentCollectionPlan;
	}

	public List<Report> getItemsWinOrderOpportUnity() {
		if(selectedReport==null){
			selectedReport=new Report();
		}
		String mechanismId = (String) getSession().getAttribute("MECHANISMID");
		String from = (String) getSession().getAttribute("FROM");
		String departmentid = (String) getSession().getAttribute("BMJLID");
		if(departmentid==null) {
			departmentid="";
		}
		selectedReport.setFrom(from);
		selectedReport.setDepartmentIds(departmentid);
		selectedReport.setMechanismId(mechanismId);
		String username = (String) getSession().getAttribute("USERNAME");
		selectedReport.setUsername(username);
		if (itemsWinOrderOpportUnity == null||itemsWinOrderOpportUnity.size()==0) {
			itemsWinOrderOpportUnity = reportManager.getItemsWinOrderOpportUnity(selectedReport);
		}
		return itemsWinOrderOpportUnity;
	}

	public void setItemsWinOrderOpportUnity(List<Report> itemsWinOrderOpportUnity) {
		this.itemsWinOrderOpportUnity = itemsWinOrderOpportUnity;
	}

	public List<Report> getItemsCuetransFormation() {
		return itemsCuetransFormation;
	}

	public void setItemsCuetransFormation(List<Report> itemsCuetransFormation) {
		this.itemsCuetransFormation = itemsCuetransFormation;
	}

	public List<Report> getItemsContractsUmmary() {
		if(selectedReport==null){
			selectedReport=new Report();
		}
		String mechanismId = (String) getSession().getAttribute("MECHANISMID");
		String from = (String) getSession().getAttribute("FROM");
		String departmentid = (String) getSession().getAttribute("BMJLID");
		if(departmentid==null) {
			departmentid="";
		}
		selectedReport.setFrom(from);
		selectedReport.setDepartmentIds(departmentid);
		selectedReport.setMechanismId(mechanismId);
		String username = (String) getSession().getAttribute("USERNAME");
		selectedReport.setUsername(username);
		if (itemsContractsUmmary == null||itemsContractsUmmary.size()==0) {
			itemsContractsUmmary = reportManager.getItemsContractsUmmary(selectedReport);
		}
		return itemsContractsUmmary;
	}

	public void setItemsContractsUmmary(List<Report> itemsContractsUmmary) {
		this.itemsContractsUmmary = itemsContractsUmmary;
	}

	public List<Report> getItemsPerformanceobjectivesYear() {
		return itemsPerformanceobjectivesYear;
	}

	public void setItemsPerformanceobjectivesYear(List<Report> itemsPerformanceobjectivesYear) {
		this.itemsPerformanceobjectivesYear = itemsPerformanceobjectivesYear;
	}

	public List<Report> getItemsNewBusiness() {
		if(selectedReport==null){
			selectedReport=new Report();
		}
		String mechanismId = (String) getSession().getAttribute("MECHANISMID");
		String from = (String) getSession().getAttribute("FROM");
		String departmentid = (String) getSession().getAttribute("BMJLID");
		if(departmentid==null) {
			departmentid="";
		}
		selectedReport.setFrom(from);
		selectedReport.setDepartmentIds(departmentid);
		selectedReport.setMechanismId(mechanismId);
		String username = (String) getSession().getAttribute("USERNAME");
		selectedReport.setUsername(username);
		if (itemsNewBusiness == null||itemsNewBusiness.size()==0) {
			itemsNewBusiness = reportManager.getItemsNewBusiness(selectedReport);
		}
		return itemsNewBusiness;
	}

	public void setItemsNewBusiness(List<Report> itemsNewBusiness) {
		this.itemsNewBusiness = itemsNewBusiness;
	}

	public BarChartModel getPerformanceobjectivesbarModel() {

		if(selectedReport==null){
			selectedReport=new Report();
		}
		String mechanismId = (String) getSession().getAttribute("MECHANISMID");
		String from = (String) getSession().getAttribute("FROM");
		String departmentid = (String) getSession().getAttribute("BMJLID");
		if(departmentid==null) {
			departmentid="";
		}
		selectedReport.setFrom(from);
		selectedReport.setDepartmentIds(departmentid);
		selectedReport.setMechanismId(mechanismId);
		String username = (String) getSession().getAttribute("USERNAME");
		selectedReport.setUsername(username);
		itemsPerformanceobjectives = reportManager.getItemsPerformanceobjectives(selectedReport);
		performanceobjectivesbarModel = new BarChartModel();
        ChartData data = new ChartData();

        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("赢单金额");
        barDataSet.setBackgroundColor("rgba(255, 99, 132, 0.2)");
        barDataSet.setBorderColor("rgb(255, 99, 132)");
        barDataSet.setBorderWidth(1);
        List<Number> values = new ArrayList<>();


        for(Report report : itemsPerformanceobjectives){
            values.add(report.getToWinASingleAmount());
        }
        
        barDataSet.setData(values);

        data.addChartDataSet(barDataSet);

        List<String> labels = new ArrayList<>();

        for(Report report : itemsPerformanceobjectives){
            labels.add(report.getColumn());
        }
        data.setLabels(labels);
        performanceobjectivesbarModel.setData(data);

        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        linearAxes.setOffset(true);
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        ticks.setBeginAtZero(true);
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);

        Title title = new Title();
        title.setDisplay(true);
        title.setText("按月");
        options.setTitle(title);

        performanceobjectivesbarModel.setOptions(options);
		return performanceobjectivesbarModel;
	}

	public void setPerformanceobjectivesbarModel(BarChartModel performanceobjectivesbarModel) {
		this.performanceobjectivesbarModel = performanceobjectivesbarModel;
	}

	public BarChartModel getPerformancegoalCompletionrankingModel() {

		if(selectedReport==null){
			selectedReport=new Report();
		}
		String mechanismId = (String) getSession().getAttribute("MECHANISMID");
		String from = (String) getSession().getAttribute("FROM");
		String departmentid = (String) getSession().getAttribute("BMJLID");
		if(departmentid==null) {
			departmentid="";
		}
		selectedReport.setFrom(from);
		selectedReport.setDepartmentIds(departmentid);
		selectedReport.setMechanismId(mechanismId);
		String username = (String) getSession().getAttribute("USERNAME");
		selectedReport.setUsername(username);
		itemsPerformancegoalCompletionranking = reportManager.getItemsPerformancegoalCompletionranking(selectedReport);
		performancegoalCompletionrankingModel = new BarChartModel();
        ChartData data = new ChartData();

        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("赢单金额");
        barDataSet.setBackgroundColor("rgba(255, 99, 132, 0.2)");
        barDataSet.setBorderColor("rgb(255, 99, 132)");
        barDataSet.setBorderWidth(1);
        List<Number> values = new ArrayList<>();


        for(Report report : itemsPerformancegoalCompletionranking){
            values.add(report.getToWinASingleAmount());
        }
        
        barDataSet.setData(values);

        data.addChartDataSet(barDataSet);

        List<String> labels = new ArrayList<>();

        for(Report report : itemsPerformancegoalCompletionranking){
            labels.add(report.getDepartmentName());
        }
        data.setLabels(labels);
        performancegoalCompletionrankingModel.setData(data);

        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        linearAxes.setOffset(true);
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        ticks.setBeginAtZero(true);
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);

        Title title = new Title();
        title.setDisplay(true);
        title.setText("按部");
        options.setTitle(title);

        performancegoalCompletionrankingModel.setOptions(options);
		return performancegoalCompletionrankingModel;
	}

	public void setPerformancegoalCompletionrankingModel(BarChartModel performancegoalCompletionrankingModel) {
		this.performancegoalCompletionrankingModel = performancegoalCompletionrankingModel;
	}

	public BarChartModel getSalesVolumeModel() {

		if(selectedReport==null){
			selectedReport=new Report();
		}
		String mechanismId = (String) getSession().getAttribute("MECHANISMID");
		String from = (String) getSession().getAttribute("FROM");
		String departmentid = (String) getSession().getAttribute("BMJLID");
		if(departmentid==null) {
			departmentid="";
		}
		selectedReport.setFrom(from);
		selectedReport.setDepartmentIds(departmentid);
		selectedReport.setMechanismId(mechanismId);
		String username = (String) getSession().getAttribute("USERNAME");
		selectedReport.setUsername(username);
		itemsSalesVolume = reportManager.getItemsSalesVolume(selectedReport);
		salesVolumeModel = new BarChartModel();
        ChartData data = new ChartData();

        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("合同金额");
        barDataSet.setBackgroundColor("rgba(255, 99, 132, 0.2)");
        barDataSet.setBorderColor("rgb(255, 99, 132)");
        barDataSet.setBorderWidth(1);
        List<Number> values = new ArrayList<>();


        for(Report report : itemsSalesVolume){
            values.add(report.getContractAmount());
        }
        
        barDataSet.setData(values);

        data.addChartDataSet(barDataSet);

        List<String> labels = new ArrayList<>();

        for(Report report : itemsSalesVolume){
            labels.add(report.getDepartmentName());
        }
        data.setLabels(labels);
        salesVolumeModel.setData(data);

        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        linearAxes.setOffset(true);
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        ticks.setBeginAtZero(true);
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);

        Title title = new Title();
        title.setDisplay(true);
        title.setText("合同金额");
        options.setTitle(title);

        salesVolumeModel.setOptions(options);
		return salesVolumeModel;
	}

	public void setSalesVolumeModel(BarChartModel salesVolumeModel) {
		this.salesVolumeModel = salesVolumeModel;
	}

	public BarChartModel getDepartmentHonorModel() {

		if(selectedReport==null){
			selectedReport=new Report();
		}
		String mechanismId = (String) getSession().getAttribute("MECHANISMID");
		String from = (String) getSession().getAttribute("FROM");
		String departmentid = (String) getSession().getAttribute("BMJLID");
		if(departmentid==null) {
			departmentid="";
		}
		selectedReport.setFrom(from);
		selectedReport.setDepartmentIds(departmentid);
		selectedReport.setMechanismId(mechanismId);
		String username = (String) getSession().getAttribute("USERNAME");
		selectedReport.setUsername(username);
		itemsDepartmentHonor = reportManager.getItemsDepartmentHonor(selectedReport);
		departmentHonorModel = new BarChartModel();
        ChartData data = new ChartData();

        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("合同金额");
        barDataSet.setBackgroundColor("rgba(255, 99, 132, 0.2)");
        barDataSet.setBorderColor("rgb(255, 99, 132)");
        barDataSet.setBorderWidth(1);
        List<Number> values = new ArrayList<>();


        for(Report report : itemsDepartmentHonor){
            values.add(report.getAggregateAmount());
        }
        
        barDataSet.setData(values);

        data.addChartDataSet(barDataSet);

        List<String> labels = new ArrayList<>();

        for(Report report : itemsDepartmentHonor){
            labels.add(report.getDepartmentName());
        }
        data.setLabels(labels);
        departmentHonorModel.setData(data);

        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        linearAxes.setOffset(true);
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        ticks.setBeginAtZero(true);
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);

        Title title = new Title();
        title.setDisplay(true);
        title.setText("合同金额");
        options.setTitle(title);

        departmentHonorModel.setOptions(options);
		return departmentHonorModel;
	}

	public void setDepartmentHonorModel(BarChartModel departmentHonorModel) {
		this.departmentHonorModel = departmentHonorModel;
	}

	public BarChartModel getPersonalHonorModel() {

		if(selectedReport==null){
			selectedReport=new Report();
		}
		String mechanismId = (String) getSession().getAttribute("MECHANISMID");
		String from = (String) getSession().getAttribute("FROM");
		String departmentid = (String) getSession().getAttribute("BMJLID");
		if(departmentid==null) {
			departmentid="";
		}
		selectedReport.setFrom(from);
		selectedReport.setDepartmentIds(departmentid);
		selectedReport.setMechanismId(mechanismId);
		String username = (String) getSession().getAttribute("USERNAME");
		selectedReport.setUsername(username);
		itemsPersonalHonor = reportManager.getItemsPersonalHonor(selectedReport);
		personalHonorModel = new BarChartModel();
        ChartData data = new ChartData();

        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("合同金额");
        barDataSet.setBackgroundColor("rgba(255, 99, 132, 0.2)");
        barDataSet.setBorderColor("rgb(255, 99, 132)");
        barDataSet.setBorderWidth(1);
        List<Number> values = new ArrayList<>();


        for(Report report : itemsPersonalHonor){
            values.add(report.getAggregateAmount());
        }
        
        barDataSet.setData(values);

        data.addChartDataSet(barDataSet);

        List<String> labels = new ArrayList<>();

        for(Report report : itemsPersonalHonor){
            labels.add(report.getUsername());
        }
        data.setLabels(labels);
        personalHonorModel.setData(data);

        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        linearAxes.setOffset(true);
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        ticks.setBeginAtZero(true);
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);

        Title title = new Title();
        title.setDisplay(true);
        title.setText("合同金额");
        options.setTitle(title);

        personalHonorModel.setOptions(options);
		return personalHonorModel;
	}

	public void setPersonalHonorModel(BarChartModel personalHonorModel) {
		this.personalHonorModel = personalHonorModel;
	}

	public BarChartModel getSalesVolumeModels() {

		if(selectedReport==null){
			selectedReport=new Report();
		}
		String mechanismId = (String) getSession().getAttribute("MECHANISMID");
		String from = (String) getSession().getAttribute("FROM");
		String departmentid = (String) getSession().getAttribute("BMJLID");
		if(departmentid==null) {
			departmentid="";
		}
		selectedReport.setFrom(from);
		selectedReport.setDepartmentIds(departmentid);
		selectedReport.setMechanismId(mechanismId);
		String username = (String) getSession().getAttribute("USERNAME");
		selectedReport.setUsername(username);
		itemsSalesVolumes = reportManager.getItemsSalesVolumes(selectedReport);
		salesVolumeModels = new BarChartModel();
        ChartData data = new ChartData();

        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("赢单金额");
        barDataSet.setBackgroundColor("rgba(255, 99, 132, 0.2)");
        barDataSet.setBorderColor("rgb(255, 99, 132)");
        barDataSet.setBorderWidth(1);
        List<Number> values = new ArrayList<>();


        for(Report report : itemsSalesVolumes){
            values.add(report.getContractAmount());
        }
        
        barDataSet.setData(values);

        data.addChartDataSet(barDataSet);

        List<String> labels = new ArrayList<>();

        for(Report report : itemsSalesVolumes){
            labels.add(report.getDepartmentName());
        }
        data.setLabels(labels);
        salesVolumeModels.setData(data);

        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        linearAxes.setOffset(true);
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        ticks.setBeginAtZero(true);
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);

        Title title = new Title();
        title.setDisplay(true);
        title.setText("赢单商机金额");
        options.setTitle(title);

        salesVolumeModels.setOptions(options);
		return salesVolumeModels;
	}

	public void setSalesVolumeModels(BarChartModel salesVolumeModels) {
		this.salesVolumeModels = salesVolumeModels;
	}

	public BarChartModel getPerformancegoalCompletionrankingbarModel() {
		return performancegoalCompletionrankingbarModel;
	}

	public void setPerformancegoalCompletionrankingbarModel(BarChartModel performancegoalCompletionrankingbarModel) {
		this.performancegoalCompletionrankingbarModel = performancegoalCompletionrankingbarModel;
	}

	public BarChartModel getSalesModel() {

		if(selectedReport==null){
			selectedReport=new Report();
		}
		String mechanismId = (String) getSession().getAttribute("MECHANISMID");
		String from = (String) getSession().getAttribute("FROM");
		String departmentid = (String) getSession().getAttribute("BMJLID");
		if(departmentid==null) {
			departmentid="";
		}
		selectedReport.setFrom(from);
		selectedReport.setDepartmentIds(departmentid);
		selectedReport.setMechanismId(mechanismId);
		String username = (String) getSession().getAttribute("USERNAME");
		selectedReport.setUsername(username);
		itemsSales = reportManager.getItemsSales(selectedReport);
		salesModel = new BarChartModel();
        ChartData data = new ChartData();

        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("预计售金");

        List<Number> values = new ArrayList<>();
        for(Report report : itemsSales){
            values.add(report.getEstimatedSalesAmount());
        }
        barDataSet.setData(values);

        List<String> bgColor = new ArrayList<>();
        bgColor.add("rgba(255, 99, 132, 0.2)");
        bgColor.add("rgba(255, 159, 64, 0.2)");
        bgColor.add("rgba(255, 205, 86, 0.2)");
        barDataSet.setBackgroundColor(bgColor);
        barDataSet.setBorderWidth(1);

        data.addChartDataSet(barDataSet);

        List<String> labels = new ArrayList<>();

        Date dNow = new Date();   //当前时间  
        Date dBefore = new Date();  
        Calendar calendar = Calendar.getInstance(); //得到日历  
        calendar.setTime(dNow);//把当前时间赋给日  
        calendar.add(Calendar.MONTH, +2);  //设置为前3  
        dBefore = calendar.getTime();   //得到3月的时间  
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM"); //设置时间格式  
        String defaultStartDate = sdf.format(dBefore);    //格式化前3月的时间  
        String defaultEndDate = sdf.format(dNow); //格式化当前时  
        
        calendar.setTime(dNow);//把当前时间赋给日  
        calendar.add(Calendar.MONTH, +1);  //设置为前3  
        dBefore = calendar.getTime();   //得到3月的时间  
        String defaultStartDate1 = sdf.format(dBefore);    //格式化前3月的时间  
        System.out.println("三个月之前时======="+defaultStartDate);  
        System.out.println("三个月之前时======="+defaultStartDate1);  
        System.out.println("当前时间==========="+defaultEndDate); 
        labels.add(defaultEndDate);
        labels.add(defaultStartDate1);
        labels.add(defaultStartDate);
        data.setLabels(labels);
        salesModel.setData(data);

        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        linearAxes.setOffset(true);
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        ticks.setBeginAtZero(true);
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);

        Title title = new Title();
        title.setDisplay(true);
        title.setText("售预测报");
        options.setTitle(title);

        Legend legend = new Legend();
        legend.setDisplay(true);
        legend.setPosition("top");
        LegendLabel legendLabels = new LegendLabel();
        legendLabels.setFontStyle("bold");
        legendLabels.setFontColor("#2980B9");
        legendLabels.setFontSize(24);
        legend.setLabels(legendLabels);
        options.setLegend(legend);

        // disable animation
        Animation animation = new Animation();
        animation.setDuration(0);
        options.setAnimation(animation);

        salesModel.setOptions(options);
		return salesModel;
	}

	public void setSalesModel(BarChartModel salesModel) {
		this.salesModel = salesModel;
	}

	public BarChartModel getPayMentCollectionPlanModel() {

		if(selectedReport==null){
			selectedReport=new Report();
		}
		String mechanismId = (String) getSession().getAttribute("MECHANISMID");
		String from = (String) getSession().getAttribute("FROM");
		String departmentid = (String) getSession().getAttribute("BMJLID");
		if(departmentid==null) {
			departmentid="";
		}
		selectedReport.setFrom(from);
		selectedReport.setDepartmentIds(departmentid);
		selectedReport.setMechanismId(mechanismId);
		String username = (String) getSession().getAttribute("USERNAME");
		selectedReport.setUsername(username);
		itemsPayMentCollectionPlan = reportManager.getItemsPayMentCollectionPlan(selectedReport);
		payMentCollectionPlanModel = new BarChartModel();
        ChartData data = new ChartData();

        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("计划回款金额");

        List<Number> values = new ArrayList<>();
        for(Report report : itemsPayMentCollectionPlan){
            values.add(report.getReceivableAmount());
        }
        barDataSet.setData(values);

        List<String> bgColor = new ArrayList<>();
        bgColor.add("rgba(255, 99, 132, 0.2)");
        bgColor.add("rgba(255, 159, 64, 0.2)");
        bgColor.add("rgba(255, 205, 86, 0.2)");
        barDataSet.setBackgroundColor(bgColor);
        barDataSet.setBorderWidth(1);

        data.addChartDataSet(barDataSet);

        List<String> labels = new ArrayList<>();
        for(Report report : itemsPayMentCollectionPlan){
            labels.add(report.getColumn());
        }
        data.setLabels(labels);
        payMentCollectionPlanModel.setData(data);

        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        linearAxes.setOffset(true);
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        ticks.setBeginAtZero(true);
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);

        Title title = new Title();
        title.setDisplay(true);
        title.setText("回款计划汇 ");
        options.setTitle(title);

        Legend legend = new Legend();
        legend.setDisplay(true);
        legend.setPosition("top");
        LegendLabel legendLabels = new LegendLabel();
        legendLabels.setFontStyle("bold");
        legendLabels.setFontColor("#2980B9");
        legendLabels.setFontSize(24);
        legend.setLabels(legendLabels);
        options.setLegend(legend);

        // disable animation
        Animation animation = new Animation();
        animation.setDuration(0);
        options.setAnimation(animation);

        payMentCollectionPlanModel.setOptions(options);
		return payMentCollectionPlanModel;
	}

	public void setPayMentCollectionPlanModel(BarChartModel payMentCollectionPlanModel) {
		this.payMentCollectionPlanModel = payMentCollectionPlanModel;
	}

	public BarChartModel getWinOrderOpportUnityModel() {

		if(selectedReport==null){
			selectedReport=new Report();
		}
		String mechanismId = (String) getSession().getAttribute("MECHANISMID");
		String from = (String) getSession().getAttribute("FROM");
		String departmentid = (String) getSession().getAttribute("BMJLID");
		if(departmentid==null) {
			departmentid="";
		}
		selectedReport.setFrom(from);
		selectedReport.setDepartmentIds(departmentid);
		selectedReport.setMechanismId(mechanismId);
		String username = (String) getSession().getAttribute("USERNAME");
		selectedReport.setUsername(username);
		itemsWinOrderOpportUnity = reportManager.getItemsWinOrderOpportUnity(selectedReport);
		winOrderOpportUnityModel = new BarChartModel();
        ChartData data = new ChartData();

        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("赢单金额");

        List<Number> values = new ArrayList<>();
        for(Report report : itemsWinOrderOpportUnity){
            values.add(report.getToWinASingleAmount());
        }
        barDataSet.setData(values);

        List<String> bgColor = new ArrayList<>();
        bgColor.add("rgba(255, 99, 132, 0.2)");
        bgColor.add("rgba(255, 159, 64, 0.2)");
        bgColor.add("rgba(255, 205, 86, 0.2)");
        barDataSet.setBackgroundColor(bgColor);
        barDataSet.setBorderWidth(1);

        data.addChartDataSet(barDataSet);

        List<String> labels = new ArrayList<>();
        for(Report report : itemsWinOrderOpportUnity){
            labels.add(report.getColumn());
        }
        data.setLabels(labels);
        winOrderOpportUnityModel.setData(data);

        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        linearAxes.setOffset(true);
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        ticks.setBeginAtZero(true);
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);

        Title title = new Title();
        title.setDisplay(true);
        title.setText("赢单商机汇");
        options.setTitle(title);

        Legend legend = new Legend();
        legend.setDisplay(true);
        legend.setPosition("top");
        LegendLabel legendLabels = new LegendLabel();
        legendLabels.setFontStyle("bold");
        legendLabels.setFontColor("#2980B9");
        legendLabels.setFontSize(24);
        legend.setLabels(legendLabels);
        options.setLegend(legend);

        // disable animation
        Animation animation = new Animation();
        animation.setDuration(0);
        options.setAnimation(animation);

        winOrderOpportUnityModel.setOptions(options);
		return winOrderOpportUnityModel;
	}

	public void setWinOrderOpportUnityModel(BarChartModel winOrderOpportUnityModel) {
		this.winOrderOpportUnityModel = winOrderOpportUnityModel;
	}

	public BarChartModel getCuetransFormationModel() {
		return cuetransFormationModel;
	}

	public void setCuetransFormationModel(BarChartModel cuetransFormationModel) {
		this.cuetransFormationModel = cuetransFormationModel;
	}

	public BarChartModel getContractsUmmaryModel() {

		if(selectedReport==null){
			selectedReport=new Report();
		}
		String mechanismId = (String) getSession().getAttribute("MECHANISMID");
		String from = (String) getSession().getAttribute("FROM");
		String departmentid = (String) getSession().getAttribute("BMJLID");
		if(departmentid==null) {
			departmentid="";
		}
		selectedReport.setFrom(from);
		selectedReport.setDepartmentIds(departmentid);
		selectedReport.setMechanismId(mechanismId);
		String username = (String) getSession().getAttribute("USERNAME");
		selectedReport.setUsername(username);
		itemsContractsUmmary = reportManager.getItemsContractsUmmary(selectedReport);
		contractsUmmaryModel = new BarChartModel();
        ChartData data = new ChartData();

        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("合同金额");

        List<Number> values = new ArrayList<>();
        for(Report report : itemsContractsUmmary){
            values.add(report.getTotalContractAmount());
        }
        barDataSet.setData(values);

        List<String> bgColor = new ArrayList<>();
        bgColor.add("rgba(255, 99, 132, 0.2)");
        bgColor.add("rgba(255, 159, 64, 0.2)");
        bgColor.add("rgba(255, 205, 86, 0.2)");
        barDataSet.setBackgroundColor(bgColor);
        barDataSet.setBorderWidth(1);

        data.addChartDataSet(barDataSet);

        List<String> labels = new ArrayList<>();
        for(Report report : itemsContractsUmmary){
            labels.add(report.getColumn());
        }
        data.setLabels(labels);
        contractsUmmaryModel.setData(data);

        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        linearAxes.setOffset(true);
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        ticks.setBeginAtZero(true);
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);

        Title title = new Title();
        title.setDisplay(true);
        title.setText("合同汇 ");
        options.setTitle(title);

        Legend legend = new Legend();
        legend.setDisplay(true);
        legend.setPosition("top");
        LegendLabel legendLabels = new LegendLabel();
        legendLabels.setFontStyle("bold");
        legendLabels.setFontColor("#2980B9");
        legendLabels.setFontSize(24);
        legend.setLabels(legendLabels);
        options.setLegend(legend);

        // disable animation
        Animation animation = new Animation();
        animation.setDuration(0);
        options.setAnimation(animation);

        contractsUmmaryModel.setOptions(options);
	
	
		return contractsUmmaryModel;
	}

	public void setContractsUmmaryModel(BarChartModel contractsUmmaryModel) {
		this.contractsUmmaryModel = contractsUmmaryModel;
	}

	public BarChartModel getPerformanceobjectivesModel() {
		return performanceobjectivesModel;
	}

	public void setPerformanceobjectivesModel(BarChartModel performanceobjectivesModel) {
		this.performanceobjectivesModel = performanceobjectivesModel;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Report getSelectedReport() {
		return selectedReport;
	}

	public void setSelectedReport(Report selectedReport) {
		this.selectedReport = selectedReport;
	}

	public BarChartModel getNewBusinessModel() {
		if(selectedReport==null){
			selectedReport=new Report();
		}
		String mechanismId = (String) getSession().getAttribute("MECHANISMID");
		String from = (String) getSession().getAttribute("FROM");
		String departmentid = (String) getSession().getAttribute("BMJLID");
		if(departmentid==null) {
			departmentid="";
		}
		selectedReport.setFrom(from);
		selectedReport.setDepartmentIds(departmentid);
		selectedReport.setMechanismId(mechanismId);
		String username = (String) getSession().getAttribute("USERNAME");
		selectedReport.setUsername(username);
		itemsNewBusiness = reportManager.getItemsNewBusiness(selectedReport);
		newBusinessModel = new BarChartModel();
        ChartData data = new ChartData();

        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("线索");
        barDataSet.setBackgroundColor("rgba(255, 99, 132, 0.2)");
        barDataSet.setBorderColor("rgb(255, 99, 132)");
        barDataSet.setBorderWidth(1);
        List<Number> values = new ArrayList<>();

        BarChartDataSet barDataSet2 = new BarChartDataSet();
        barDataSet2.setLabel("客户");
        barDataSet2.setBackgroundColor("rgba(255, 159, 64, 0.2)");
        barDataSet2.setBorderColor("rgb(255, 159, 64)");
        barDataSet2.setBorderWidth(1);
        List<Number> values2 = new ArrayList<>();

        BarChartDataSet barDataSet4 = new BarChartDataSet();
        barDataSet4.setLabel("商机");
        barDataSet4.setBackgroundColor("rgba(75, 192, 192, 0.2)");
        barDataSet4.setBorderColor("rgb(75, 192, 192)");
        barDataSet4.setBorderWidth(1);
        List<Number> values4 = new ArrayList<>();

        BarChartDataSet barDataSet5 = new BarChartDataSet();
        barDataSet5.setLabel("合同");
        barDataSet5.setBackgroundColor("rgba(54, 162, 235, 0.2)");
        barDataSet5.setBorderColor("rgb(54, 162, 235)");
        barDataSet5.setBorderWidth(1);
        List<Number> values5 = new ArrayList<>();

        for(Report report : itemsNewBusiness){
            values.add(report.getClueNumber());
            values2.add(report.getCustomerNumber());
            values4.add(report.getBusinessOpportunitiesNumber());
            values5.add(report.getContractsNumber());
        }
        
        barDataSet.setData(values);
        barDataSet2.setData(values2);
        barDataSet4.setData(values4);
        barDataSet5.setData(values5);

        data.addChartDataSet(barDataSet);
        data.addChartDataSet(barDataSet2);
        data.addChartDataSet(barDataSet4);
        data.addChartDataSet(barDataSet5);

        List<String> labels = new ArrayList<>();

        for(Report report : itemsNewBusiness){
            labels.add(report.getColumn());
        }
        data.setLabels(labels);
        newBusinessModel.setData(data);

        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        linearAxes.setOffset(true);
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        ticks.setBeginAtZero(true);
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);

        Title title = new Title();
        title.setDisplay(true);
        title.setText("按创建时间汇");
        options.setTitle(title);

        newBusinessModel.setOptions(options);
		return newBusinessModel;
	}

	public void setNewBusinessModel(BarChartModel newBusinessModel) {
		this.newBusinessModel = newBusinessModel;
	}

	public List<Report> getItems() {
		if(selectedReport==null){
			selectedReport=new Report();
		}
		String mechanismId = (String) getSession().getAttribute("MECHANISMID");
		String from = (String) getSession().getAttribute("FROM");
		String departmentid = (String) getSession().getAttribute("BMJLID");
		if(departmentid==null) {
			departmentid="";
		}
		selectedReport.setFrom(from);
		selectedReport.setDepartmentIds(departmentid);
		selectedReport.setMechanismId(mechanismId);
//		if (items == null||items.size()==0) {
			items = reportManager.getItems(selectedReport);
//		}
		return items;
	}

	public void setItems(List<Report> items) {
		this.items = items;
	}


	public BarChartModel getBarModel() {
		if(selectedReport==null){
			selectedReport=new Report();
		}
		String mechanismId = (String) getSession().getAttribute("MECHANISMID");
		String from = (String) getSession().getAttribute("FROM");
		String departmentid = (String) getSession().getAttribute("BMJLID");
		if(departmentid==null) {
			departmentid="";
		}
		selectedReport.setFrom(from);
		selectedReport.setDepartmentIds(departmentid);
		selectedReport.setMechanismId(mechanismId);
		String username = (String) getSession().getAttribute("USERNAME");
		selectedReport.setUsername(username);
		items = reportManager.getItems(selectedReport);
		barModel = new BarChartModel();
        ChartData data = new ChartData();

        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("跟进次数");

        List<Number> values = new ArrayList<>();
        for(Report report : items){
            values.add(report.getRanking());
            values.add(report.getClueNumber());
            values.add(report.getCustomerNumber());
            values.add(report.getBusinessOpportunitiesNumber());
            values.add(report.getContractsNumber());
        }
        barDataSet.setData(values);

        List<String> bgColor = new ArrayList<>();
        bgColor.add("rgba(255, 99, 132, 0.2)");
        bgColor.add("rgba(255, 159, 64, 0.2)");
        bgColor.add("rgba(255, 205, 86, 0.2)");
        bgColor.add("rgba(75, 192, 192, 0.2)");
        bgColor.add("rgba(54, 162, 235, 0.2)");
        bgColor.add("rgba(153, 102, 255, 0.2)");
        bgColor.add("rgba(201, 203, 207, 0.2)");
        barDataSet.setBackgroundColor(bgColor);
        barDataSet.setBorderWidth(1);

        data.addChartDataSet(barDataSet);

        List<String> labels = new ArrayList<>();
        labels.add("跟进次数");
        labels.add("跟进线索次数");
        labels.add("跟进客户次数");
        labels.add("跟进商机次数");
        labels.add("跟进合同次数");
        data.setLabels(labels);
        barModel.setData(data);

        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        linearAxes.setOffset(true);
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        ticks.setBeginAtZero(true);
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);

        Title title = new Title();
        title.setDisplay(true);
        title.setText("跟进记录报表");
        options.setTitle(title);

        Legend legend = new Legend();
        legend.setDisplay(true);
        legend.setPosition("top");
        LegendLabel legendLabels = new LegendLabel();
        legendLabels.setFontStyle("bold");
        legendLabels.setFontColor("#2980B9");
        legendLabels.setFontSize(24);
        legend.setLabels(legendLabels);
        options.setLegend(legend);

        // disable animation
        Animation animation = new Animation();
        animation.setDuration(0);
        options.setAnimation(animation);

        barModel.setOptions(options);
	
		return barModel;
	}


	public void setBarModel(BarChartModel barModel) {
		this.barModel = barModel;
	}


	public List<Report> getItemslx() {
		if(selectedReport==null){
			selectedReport=new Report();
		}
		String mechanismId = (String) getSession().getAttribute("MECHANISMID");
		String from = (String) getSession().getAttribute("FROM");
		String departmentid = (String) getSession().getAttribute("BMJLID");
		if(departmentid==null) {
			departmentid="";
		}
		selectedReport.setFrom(from);
		selectedReport.setDepartmentIds(departmentid);
		selectedReport.setMechanismId(mechanismId);
		if (itemslx == null||itemslx.size()==0) {
			itemslx = reportManager.getItemslx(selectedReport);
		}
		return itemslx;
	}


	public void setItemslx(List<Report> itemslx) {
		this.itemslx = itemslx;
	}


	public BarChartModel getBarModellx() {
		if(selectedReport==null){
			selectedReport=new Report();
		}
		String mechanismId = (String) getSession().getAttribute("MECHANISMID");
		String from = (String) getSession().getAttribute("FROM");
		String departmentid = (String) getSession().getAttribute("BMJLID");
		if(departmentid==null) {
			departmentid="";
		}
		selectedReport.setFrom(from);
		selectedReport.setDepartmentIds(departmentid);
		selectedReport.setMechanismId(mechanismId);
		String username = (String) getSession().getAttribute("USERNAME");
		selectedReport.setUsername(username);
		itemslx = reportManager.getItemslx(selectedReport);
		barModellx = new BarChartModel();
        ChartData data = new ChartData();

        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("跟进次数");

        List<Number> values = new ArrayList<>();
        for(Report report : itemslx){
            values.add(report.getRanking());
            values.add(report.getDh());
            values.add(report.getQq());
            values.add(report.getWx());
            values.add(report.getBf());
            values.add(report.getYj());
            values.add(report.getDx());
            values.add(report.getQt());
        }
        barDataSet.setData(values);

        List<String> bgColor = new ArrayList<>();
        bgColor.add("rgba(255, 99, 132, 0.2)");
        bgColor.add("rgba(255, 159, 64, 0.2)");
        bgColor.add("rgba(255, 205, 86, 0.2)");
        bgColor.add("rgba(75, 192, 192, 0.2)");
        bgColor.add("rgba(54, 162, 235, 0.2)");
        bgColor.add("rgba(153, 102, 255, 0.2)");
        bgColor.add("rgba(201, 203, 207, 0.2)");
        bgColor.add("rgba(255, 108, 86, 0.2)");
        barDataSet.setBackgroundColor(bgColor);
        barDataSet.setBorderWidth(1);

        data.addChartDataSet(barDataSet);

        List<String> labels = new ArrayList<>();
        labels.add("跟进次数");
        labels.add("电话次数");
        labels.add("QQ次数");
        labels.add("微信次数");
        labels.add("拜访次数");
        labels.add("邮件次数");
        labels.add("短信次数");
        labels.add("其他次数");
        data.setLabels(labels);
        barModellx.setData(data);

        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        linearAxes.setOffset(true);
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        ticks.setBeginAtZero(true);
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);

        Title title = new Title();
        title.setDisplay(true);
        title.setText("跟进类型报表");
        options.setTitle(title);

        Legend legend = new Legend();
        legend.setDisplay(true);
        legend.setPosition("top");
        LegendLabel legendLabels = new LegendLabel();
        legendLabels.setFontStyle("bold");
        legendLabels.setFontColor("#2980B9");
        legendLabels.setFontSize(24);
        legend.setLabels(legendLabels);
        options.setLegend(legend);

        // disable animation
        Animation animation = new Animation();
        animation.setDuration(0);
        options.setAnimation(animation);

        barModellx.setOptions(options);
		return barModellx;
	}


	public void setBarModellx(BarChartModel barModellx) {
		this.barModellx = barModellx;
	}


	public ReportManager getReportManager() {
		return reportManager;
	}


	public void setReportManager(ReportManager reportManager) {
		this.reportManager = reportManager;
	}


	
	

	
}
