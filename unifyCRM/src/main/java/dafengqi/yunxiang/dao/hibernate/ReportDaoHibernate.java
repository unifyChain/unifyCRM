package dafengqi.yunxiang.dao.hibernate;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import dafengqi.yunxiang.dao.ReportDao;
import dafengqi.yunxiang.model.Report;
import dafengqi.yunxiang.util.ResourceManager;

@Repository("reportDao") 
public class ReportDaoHibernate  extends GenericDaoHibernate<Report, Long> implements ReportDao {
	protected java.sql.Connection userConn;
	final boolean isConnSupplied = (userConn != null);
	public ReportDaoHibernate() {
		super(Report.class);
	}
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String rq = new SimpleDateFormat("yyyyMMdd").format(new Date());
	
	
	
	//DC

	@Override
	public List<Report> getItemsContractsUmmary(Report report) {
		long t1 = System.currentTimeMillis();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		PreparedStatement ps3 = null;
		ResultSet rs3 = null;
		PreparedStatement ps4 = null;
		ResultSet rs4 = null;
		List<Report> list = new ArrayList<Report>();
		String mechanismId=report.getMechanismId();
		String from=report.getFrom() == null ? "" : report.getFrom();
		String departmentIds=report.getDepartmentIds();
		String username=report.getUsername();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String wSQL="";
			if(report.getStatus()==null){
				
			}else{
				if (!report.getStatus().equals("")) {
					wSQL += " and contract_status='" + report.getStatus() + "'";
				}
			}
			if(report.getType()==null){
				
			}else{
				if (!report.getType().equals("")) {
					wSQL += " and contract_type_name='" + report.getType() + "'";
				}
			}

			if(report.getDepartmentId()!=null){
				if(report.getDepartmentId().equals("")){
					
				}else{
					if (report.getDepartmentId() != null || !report.getDepartmentId().equals("")) {
						wSQL += " and department_id='" + report.getDepartmentId() + "'";
					}
				}
			}
			if(report.getUserId()!=null){
				if(report.getUserId().equals("")){
					
				}else{
					if (report.getUserId() != null || !report.getUserId().equals("")) {
						wSQL += " and create_id='" + report.getUserId() + "'";
					}
				}
			}

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
			SimpleDateFormat formatz = new SimpleDateFormat("yyyy");
			
			String nowdate=format.format(new Date());//当前月份
			String nowdatez=formatz.format(new Date());//当前月份
			
			Date d1 = new SimpleDateFormat("yyyy-MM").parse(nowdatez+"-01");//定义起始日期
			
			Date d2 = new SimpleDateFormat("yyyy-MM").parse(nowdatez+"-12");//定义结束日期 可以去当前月也可以手动写日期。
			if(report.getTime()==null){
			}else{
				if(report.getTime().equals("")){
				}else{
					d1 = new SimpleDateFormat("yyyy-MM").parse(report.getTime()+"-01");//定义起始日期
					d2 = new SimpleDateFormat("yyyy-MM").parse(report.getTime()+"-12");//定义结束日期 可以去当前月也可以手动写日期
				}
			}
			Calendar dd = Calendar.getInstance();//定义日期实例
			
			dd.setTime(d1);//设置日期起始时间
			
			while (dd.getTime().before(d2)) {
			
			SimpleDateFormat sdfzz = new SimpleDateFormat("yyyy-MM");

				String str = sdfzz.format(dd.getTime());
				report = new Report();
				BigDecimal kpje=new BigDecimal(0);
				BigDecimal yds=new BigDecimal(0);
				BigDecimal kdj=new BigDecimal(0);
				String sql2 = "";
				if(from.equals("全部")) {
					sql2 = "SELECT sum(total_contract_amount),COUNT(*) from crm_contract where 1=1  "+wSQL+"  and create_date like'" + str + "%'   and mechanism_id like'" + mechanismId + "%' ";
					ps2 = conn.prepareStatement(sql2);
					rs2 = ps2.executeQuery();
					while (rs2.next()) {
						kpje=rs2.getBigDecimal(1);
						yds=rs2.getBigDecimal(2);
						if(rs2.getBigDecimal(1)==null){
						}else{
							if(rs2.getBigDecimal(1).compareTo(new BigDecimal(0))==0){
							}else{
								kdj=kpje.divide(yds,2, BigDecimal.ROUND_HALF_UP);
							}
						}
					}
					report.setColumn(str);
					report.setTotalContractAmount(kpje);
					report.setContractNumber(yds);
					report.setTheGuestUnitPrice(kdj.setScale(2, BigDecimal.ROUND_HALF_UP));
					list.add(report);
					dd.add(Calendar.MONTH, 1);//进行当前日期月份加1
				}else {
					if(from.equals("本部门")) {

						String[] xh=departmentIds.split(",");
					    for(String bmid : xh){
					    	if(!bmid.equals("")){
					    		sql2 = "SELECT sum(total_contract_amount),COUNT(*) from crm_contract where 1=1  "+wSQL+"  and create_date like'" + str + "%' and department_id like'" + bmid + "%'  and mechanism_id like'" + mechanismId + "%' ";
								ps2 = conn.prepareStatement(sql2);
								rs2 = ps2.executeQuery();
								while (rs2.next()) {
									kpje=rs2.getBigDecimal(1);
									yds=rs2.getBigDecimal(2);
									if(rs2.getBigDecimal(1)==null){
									}else{
										if(rs2.getBigDecimal(1).compareTo(new BigDecimal(0))==0){
										}else{
											kdj=kpje.divide(yds,2, BigDecimal.ROUND_HALF_UP);
										}
									}
								}
								report.setColumn(str);
								report.setTotalContractAmount(kpje);
								report.setContractNumber(yds);
								report.setTheGuestUnitPrice(kdj.setScale(2, BigDecimal.ROUND_HALF_UP));
								list.add(report);
								dd.add(Calendar.MONTH, 1);//进行当前日期月份加1
					    	}
					    }
						sql2 = "SELECT sum(total_contract_amount),COUNT(*) from crm_contract where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' ";
						ps2 = conn.prepareStatement(sql2);
						rs2 = ps2.executeQuery();
						while (rs2.next()) {
							kpje=rs2.getBigDecimal(1);
							yds=rs2.getBigDecimal(2);
							if(rs2.getBigDecimal(1)==null){
							}else{
								if(rs2.getBigDecimal(1).compareTo(new BigDecimal(0))==0){
								}else{
									kdj=kpje.divide(yds,2, BigDecimal.ROUND_HALF_UP);
								}
							}
						}
						report.setColumn(str);
						report.setTotalContractAmount(kpje);
						report.setContractNumber(yds);
						report.setTheGuestUnitPrice(kdj.setScale(2, BigDecimal.ROUND_HALF_UP));
						list.add(report);
						dd.add(Calendar.MONTH, 1);//进行当前日期月份加1
					}else if(from.equals("未设置")) {
						sql2 = "SELECT sum(total_contract_amount),COUNT(*) from crm_contract where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' ";
						ps2 = conn.prepareStatement(sql2);
						rs2 = ps2.executeQuery();
						while (rs2.next()) {
							kpje=rs2.getBigDecimal(1);
							yds=rs2.getBigDecimal(2);
							if(rs2.getBigDecimal(1)==null){
							}else{
								if(rs2.getBigDecimal(1).compareTo(new BigDecimal(0))==0){
								}else{
									kdj=kpje.divide(yds,2, BigDecimal.ROUND_HALF_UP);
								}
							}
						}
						report.setColumn(str);
						report.setTotalContractAmount(kpje);
						report.setContractNumber(yds);
						report.setTheGuestUnitPrice(kdj.setScale(2, BigDecimal.ROUND_HALF_UP));
						list.add(report);
						dd.add(Calendar.MONTH, 1);//进行当前日期月份加1
					}else {

						String[] xh=departmentIds.split(",");
					    for(String bmid : xh){
					    	if(!bmid.equals("")){
					    		sql2 = "SELECT sum(total_contract_amount),COUNT(*) from crm_contract where 1=1  "+wSQL+"  and create_date like'" + str + "%' and department_id like'" + bmid + "'  and mechanism_id like'" + mechanismId + "%' ";
								ps2 = conn.prepareStatement(sql2);
								rs2 = ps2.executeQuery();
								while (rs2.next()) {
									kpje=rs2.getBigDecimal(1);
									yds=rs2.getBigDecimal(2);
									if(rs2.getBigDecimal(1)==null){
									}else{
										if(rs2.getBigDecimal(1).compareTo(new BigDecimal(0))==0){
										}else{
											kdj=kpje.divide(yds,2, BigDecimal.ROUND_HALF_UP);
										}
									}
								}
								report.setColumn(str);
								report.setTotalContractAmount(kpje);
								report.setContractNumber(yds);
								report.setTheGuestUnitPrice(kdj.setScale(2, BigDecimal.ROUND_HALF_UP));
								list.add(report);
								dd.add(Calendar.MONTH, 1);//进行当前日期月份加1
					    	}
					    }
						sql2 = "SELECT sum(total_contract_amount),COUNT(*) from crm_contract where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' ";
						ps2 = conn.prepareStatement(sql2);
						rs2 = ps2.executeQuery();
						while (rs2.next()) {
							kpje=rs2.getBigDecimal(1);
							yds=rs2.getBigDecimal(2);
							if(rs2.getBigDecimal(1)==null){
							}else{
								if(rs2.getBigDecimal(1).compareTo(new BigDecimal(0))==0){
								}else{
									kdj=kpje.divide(yds,2, BigDecimal.ROUND_HALF_UP);
								}
							}
						}
						report.setColumn(str);
						report.setTotalContractAmount(kpje);
						report.setContractNumber(yds);
						report.setTheGuestUnitPrice(kdj.setScale(2, BigDecimal.ROUND_HALF_UP));
						list.add(report);
						dd.add(Calendar.MONTH, 1);//进行当前日期月份加1
					}
				}

			
			}

			SimpleDateFormat sdfzz = new SimpleDateFormat("yyyy-MM");
			String zhrq=sdfzz.format(d2.getTime());
			report = new Report();
			BigDecimal hkje=new BigDecimal(0);
			BigDecimal kpje=new BigDecimal(0);
			BigDecimal yds=new BigDecimal(0);
			BigDecimal kdj=new BigDecimal(0);
			String sql2 = "SELECT sum(total_contract_amount),COUNT(*) from crm_contract where 1=1   "+wSQL+"  and create_date like'" + zhrq + "%'  and mechanism_id like'" + mechanismId + "%' ";
				if(from.equals("全部")) {
					sql2 = "SELECT sum(total_contract_amount),COUNT(*) from crm_contract where 1=1  "+wSQL+"  and create_date like'" + zhrq + "%'   and mechanism_id like'" + mechanismId + "%' ";
					ps2 = conn.prepareStatement(sql2);
					rs2 = ps2.executeQuery();
					while (rs2.next()) {
						kpje=rs2.getBigDecimal(1);
						yds=rs2.getBigDecimal(2);
						if(rs2.getBigDecimal(1)==null){
						}else{
							if(rs2.getBigDecimal(1).compareTo(new BigDecimal(0))==0){
							}else{
								kdj=kpje.divide(yds,2, BigDecimal.ROUND_HALF_UP);
							}
						}
					}
					report.setColumn(zhrq);
					report.setTotalContractAmount(kpje);
					report.setContractNumber(yds);
					report.setTheGuestUnitPrice(kdj.setScale(2, BigDecimal.ROUND_HALF_UP));
					list.add(report);
				}else{

					if(from.equals("本部门")) {

						String[] xh=departmentIds.split(",");
					    for(String bmid : xh){
					    	if(!bmid.equals("")){
					    		sql2 = "SELECT sum(total_contract_amount),COUNT(*) from crm_contract where 1=1  "+wSQL+"  and create_date like'" + zhrq + "%' and department_id like'" + bmid + "%'  and mechanism_id like'" + mechanismId + "%' ";
								ps2 = conn.prepareStatement(sql2);
								rs2 = ps2.executeQuery();
								while (rs2.next()) {
									kpje=rs2.getBigDecimal(1);
									yds=rs2.getBigDecimal(2);
									if(rs2.getBigDecimal(1)==null){
									}else{
										if(rs2.getBigDecimal(1).compareTo(new BigDecimal(0))==0){
										}else{
											kdj=kpje.divide(yds,2, BigDecimal.ROUND_HALF_UP);
										}
									}
								}
								report.setColumn(zhrq);
								report.setTotalContractAmount(kpje);
								report.setContractNumber(yds);
								report.setTheGuestUnitPrice(kdj.setScale(2, BigDecimal.ROUND_HALF_UP));
								list.add(report);
					    	}
					    }
						sql2 = "SELECT sum(total_contract_amount),COUNT(*) from crm_contract where 1=1  "+wSQL+"  and create_date like'" + zhrq + "%' and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' ";
						ps2 = conn.prepareStatement(sql2);
						rs2 = ps2.executeQuery();
						while (rs2.next()) {
							kpje=rs2.getBigDecimal(1);
							yds=rs2.getBigDecimal(2);
							if(rs2.getBigDecimal(1)==null){
							}else{
								if(rs2.getBigDecimal(1).compareTo(new BigDecimal(0))==0){
								}else{
									kdj=kpje.divide(yds,2, BigDecimal.ROUND_HALF_UP);
								}
							}
						}
						report.setColumn(zhrq);
						report.setTotalContractAmount(kpje);
						report.setContractNumber(yds);
						report.setTheGuestUnitPrice(kdj.setScale(2, BigDecimal.ROUND_HALF_UP));
						list.add(report);
					}else if(from.equals("未设置")) {
						sql2 = "SELECT sum(total_contract_amount),COUNT(*) from crm_contract where 1=1  "+wSQL+"  and create_date like'" + zhrq + "%' and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' ";
						ps2 = conn.prepareStatement(sql2);
						rs2 = ps2.executeQuery();
						while (rs2.next()) {
							kpje=rs2.getBigDecimal(1);
							yds=rs2.getBigDecimal(2);
							if(rs2.getBigDecimal(1)==null){
							}else{
								if(rs2.getBigDecimal(1).compareTo(new BigDecimal(0))==0){
								}else{
									kdj=kpje.divide(yds,2, BigDecimal.ROUND_HALF_UP);
								}
							}
						}
						report.setColumn(zhrq);
						report.setTotalContractAmount(kpje);
						report.setContractNumber(yds);
						report.setTheGuestUnitPrice(kdj.setScale(2, BigDecimal.ROUND_HALF_UP));
						list.add(report);
					}else {

						String[] xh=departmentIds.split(",");
					    for(String bmid : xh){
					    	if(!bmid.equals("")){
					    		sql2 = "SELECT sum(total_contract_amount),COUNT(*) from crm_contract where 1=1  "+wSQL+"  and create_date like'" + zhrq + "%' and department_id like'" + bmid + "'  and mechanism_id like'" + mechanismId + "%' ";
								ps2 = conn.prepareStatement(sql2);
								rs2 = ps2.executeQuery();
								while (rs2.next()) {
									kpje=rs2.getBigDecimal(1);
									yds=rs2.getBigDecimal(2);
									if(rs2.getBigDecimal(1)==null){
									}else{
										if(rs2.getBigDecimal(1).compareTo(new BigDecimal(0))==0){
										}else{
											kdj=kpje.divide(yds,2, BigDecimal.ROUND_HALF_UP);
										}
									}
								}
								report.setColumn(zhrq);
								report.setTotalContractAmount(kpje);
								report.setContractNumber(yds);
								report.setTheGuestUnitPrice(kdj.setScale(2, BigDecimal.ROUND_HALF_UP));
								list.add(report);
					    	}
					    }
						sql2 = "SELECT sum(total_contract_amount),COUNT(*) from crm_contract where 1=1  "+wSQL+"  and create_date like'" + zhrq + "%' and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' ";
						ps2 = conn.prepareStatement(sql2);
						rs2 = ps2.executeQuery();
						while (rs2.next()) {
							kpje=rs2.getBigDecimal(1);
							yds=rs2.getBigDecimal(2);
							if(rs2.getBigDecimal(1)==null){
							}else{
								if(rs2.getBigDecimal(1).compareTo(new BigDecimal(0))==0){
								}else{
									kdj=kpje.divide(yds,2, BigDecimal.ROUND_HALF_UP);
								}
							}
						}
						report.setColumn(zhrq);
						report.setTotalContractAmount(kpje);
						report.setContractNumber(yds);
						report.setTheGuestUnitPrice(kdj.setScale(2, BigDecimal.ROUND_HALF_UP));
						list.add(report);
					}
				}

//	        for (int i = 0; i < list.size() - 1; i++) {
//	            for (int j = i + 1; j < list.size(); j++) {
//	                if (list.get(i).equals(list.get(j))) {
//	                    list.remove(j);
//	                }
//	            }
//	        }
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("List<Report> getItemshthz(Report report)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		}
		return list;
	}

	@Override
	public List<Report> getItemsCustomerNumBerranKing(Report report) {
		long t1 = System.currentTimeMillis();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		List<Report> list = new ArrayList<Report>();
		String mechanismId=report.getMechanismId();
		String from=report.getFrom() == null ? "" : report.getFrom();
		String departmentIds=report.getDepartmentIds();
		String username=report.getUsername();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);

			String wSQL = "";

			if(report.getDepartmentId()!=null){
				if(report.getDepartmentId().equals("")){
					
				}else{
					if (report.getDepartmentId() != null || !report.getDepartmentId().equals("")) {
						wSQL += " and department_id='" + report.getDepartmentId() + "'";
					}
				}
			}
			String sql = "";
				if(from.equals("全部")) {
					sql = "SELECT count(*),create_name,department_name from crm_customer where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%' and data_type='客户'  GROUP BY create_name ORDER BY count(*) DESC ";
					ps = conn.prepareStatement(sql);
					rs = ps.executeQuery();
					int i=1;
					while (rs.next()) {
						report = new Report();
						report.setRanking(i++);
						report.setCustomerNumber(rs.getInt(1));
						report.setName(rs.getString(2));
						report.setDepartmentName(rs.getString(3));
						list.add(report);
					}

				}else{
					if(from.equals("本部门")) {

						String[] xh=departmentIds.split(",");
					    for(String bmid : xh){
					    	if(!bmid.equals("")){
					    		sql = "SELECT count(*),create_name,department_name from crm_customer where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%'  and department_id like'" + bmid + "%'  and data_type='客户' GROUP BY create_name ORDER BY count(*) DESC ";
					    		ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i++);
									report.setCustomerNumber(rs.getInt(1));
									report.setName(rs.getString(2));
									report.setDepartmentName(rs.getString(3));
									list.add(report);
								}
					    	}
					    }
						sql = "SELECT count(*),create_name,department_name from crm_customer where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%'  and create_id ='" + username + "'  and data_type='客户' GROUP BY create_name ORDER BY count(*) DESC ";
						ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						int i1=1;
						while (rs.next()) {
							report = new Report();
							report.setRanking(i1++);
							report.setCustomerNumber(rs.getInt(1));
							report.setName(rs.getString(2));
							report.setDepartmentName(rs.getString(3));
							list.add(report);
						}

						
				        for (int i = 0; i < list.size() - 1; i++) {
				            for (int j = i + 1; j < list.size(); j++) {
				                if (list.get(i).equals(list.get(j))) {
				                    list.remove(j);
				                }
				            }
				        }
					}else if(from.equals("未设置")) {
						sql = "SELECT count(*),create_name,department_name from crm_customer where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%'  and create_id ='" + username + "'  and data_type='客户' GROUP BY create_name ORDER BY count(*) DESC ";
						ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						int i1=1;
						while (rs.next()) {
							report = new Report();
							report.setRanking(i1++);
							report.setCustomerNumber(rs.getInt(1));
							report.setName(rs.getString(2));
							report.setDepartmentName(rs.getString(3));
							list.add(report);
						}
					}else {

						String[] xh=departmentIds.split(",");
					    for(String bmid : xh){
					    	if(!bmid.equals("")){
					    		sql = "SELECT count(*),create_name,department_name from crm_customer where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%'  and department_id like'" + bmid + "'  and data_type='客户' GROUP BY create_name ORDER BY count(*) DESC ";
					    		ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i++);
									report.setCustomerNumber(rs.getInt(1));
									report.setName(rs.getString(2));
									report.setDepartmentName(rs.getString(3));
									list.add(report);
								}
					    	}
					    }
						sql = "SELECT count(*),create_name,department_name from crm_customer where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%'  and create_id ='" + username + "'  and data_type='客户' GROUP BY create_name ORDER BY count(*) DESC ";
						ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						int i1=1;
						while (rs.next()) {
							report = new Report();
							report.setRanking(i1++);
							report.setCustomerNumber(rs.getInt(1));
							report.setName(rs.getString(2));
							report.setDepartmentName(rs.getString(3));
							list.add(report);
						}

						
				        for (int i = 0; i < list.size() - 1; i++) {
				            for (int j = i + 1; j < list.size(); j++) {
				                if (list.get(i).equals(list.get(j))) {
				                    list.remove(j);
				                }
				            }
				        }
					}
				}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("List<Report> getItemskhslpm(Report report)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		} finally {
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		}
		return list;
	}

	@Override
	public List<Report> getItemsCustomerNumBerranKings(Report report) {
		long t1 = System.currentTimeMillis();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		List<Report> list = new ArrayList<Report>();
		String from=report.getFrom() == null ? "" : report.getFrom();
		String departmentIds=report.getDepartmentIds();
		String username=report.getUsername();
		String mechanismId=report.getMechanismId();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);

			String wSQL = "";

			if(report.getDepartmentId()!=null){
				if(report.getDepartmentId().equals("")){
					
				}else{
					if (report.getDepartmentId() != null || !report.getDepartmentId().equals("")) {
						wSQL += " and department_id='" + report.getDepartmentId() + "'";
					}
				}
			}
			String sql = "";

				if(from.equals("全部")) {
					sql = "SELECT count(*),department_name from crm_customer where 1=1 "+wSQL+"  and data_type='客户' and mechanism_id like '"+mechanismId+"%' GROUP BY department_name ORDER BY count(*) DESC ";
					ps = conn.prepareStatement(sql);
					rs = ps.executeQuery();
					int i=1;
					while (rs.next()) {
						report = new Report();
						report.setRanking(i++);
						report.setCustomerNumber(rs.getInt(1));
						report.setDepartmentName(rs.getString(2));
						list.add(report);
					}
				}else{

					if(from.equals("本部门")) {

						String[] xh=departmentIds.split(",");
					    for(String bmid : xh){
					    	if(!bmid.equals("")){
					    		sql = "SELECT count(*),department_name from crm_customer where 1=1 "+wSQL+"  and data_type='客户' and mechanism_id like '"+mechanismId+"%' and department_id like'" + bmid + "%' GROUP BY department_name ORDER BY count(*) DESC ";
					    		ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i++);
									report.setCustomerNumber(rs.getInt(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
					    	}
					    }
						sql = "SELECT count(*),department_name from crm_customer where 1=1 "+wSQL+"  and data_type='客户' and mechanism_id like '"+mechanismId+"%' and create_id ='" + username + "' GROUP BY department_name ORDER BY count(*) DESC ";
						ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						int i1=1;
						while (rs.next()) {
							report = new Report();
							report.setRanking(i1++);
							report.setCustomerNumber(rs.getInt(1));
							report.setDepartmentName(rs.getString(2));
							list.add(report);
						}
				        for (int i = 0; i < list.size() - 1; i++) {
				            for (int j = i + 1; j < list.size(); j++) {
				                if (list.get(i).equals(list.get(j))) {
				                    list.remove(j);
				                }
				            }
				        }
					}else if(from.equals("未设置")) {

						sql = "SELECT count(*),department_name from crm_customer where 1=1 "+wSQL+"  and data_type='客户' and mechanism_id like '"+mechanismId+"%' and create_id ='" + username + "' GROUP BY department_name ORDER BY count(*) DESC ";
						ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						int i1=1;
						while (rs.next()) {
							report = new Report();
							report.setRanking(i1++);
							report.setCustomerNumber(rs.getInt(1));
							report.setDepartmentName(rs.getString(2));
							list.add(report);
						}
					}else {

						String[] xh=departmentIds.split(",");
					    for(String bmid : xh){
					    	if(!bmid.equals("")){
					    		sql = "SELECT count(*),department_name from crm_customer where 1=1 "+wSQL+"  and data_type='客户' and mechanism_id like '"+mechanismId+"%' and department_id like'" + bmid + "' GROUP BY department_name ORDER BY count(*) DESC ";
					    		ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i++);
									report.setCustomerNumber(rs.getInt(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
					    	}
					    }
						sql = "SELECT count(*),department_name from crm_customer where 1=1 "+wSQL+"  and data_type='客户' and mechanism_id like '"+mechanismId+"%' and create_id ='" + username + "' GROUP BY department_name ORDER BY count(*) DESC ";
						ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						int i1=1;
						while (rs.next()) {
							report = new Report();
							report.setRanking(i1++);
							report.setCustomerNumber(rs.getInt(1));
							report.setDepartmentName(rs.getString(2));
							list.add(report);
						}
				        for (int i = 0; i < list.size() - 1; i++) {
				            for (int j = i + 1; j < list.size(); j++) {
				                if (list.get(i).equals(list.get(j))) {
				                    list.remove(j);
				                }
				            }
				        }
					}
				}

			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("List<Report> getItemskhslpmbm(Report report)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		} finally {
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		}
		return list;
	}
	@Override
	public List<Report> getItemsDepartmentHonor(Report report)  {
		long t1 = System.currentTimeMillis();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		PreparedStatement ps3 = null;
		ResultSet rs3 = null;
		PreparedStatement ps4 = null;
		ResultSet rs4 = null;
		List<Report> list = new ArrayList<Report>();
		String mechanismId=report.getMechanismId();
		String from=report.getFrom() == null ? "" : report.getFrom();
		String departmentIds=report.getDepartmentIds();
		String username=report.getUsername();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);

			String wSQL = "";
			int i=0;
			if(report.getTimeToScreen()==null){
				report.setTimeToScreen("本月");
			}
			if(report.getResultsType()==null){
				report.setResultsType("合同金额");
			}

			if(report.getDepartmentId()!=null){
				if(report.getDepartmentId().equals("")){
					
				}else{
					if (report.getDepartmentId() != null || !report.getDepartmentId().equals("")) {
						wSQL += " and department_id='" + report.getDepartmentId() + "'";
					}
				}
				
			}
			if(report.getTimeToScreen().equals("上月")){

		        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		        Date date = new Date();
		        Calendar calendar = Calendar.getInstance();
		        calendar.setTime(date); // 设置为当前时间
		        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1); // 设置为上一个月
		        date = calendar.getTime();
		        String accDate = format.format(date);
				BigDecimal xse=new BigDecimal(0);
				if(report.getResultsType().equals("合同金额")){
					String sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
						if(from.equals("全部")) {
							sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								list.add(report);
							}
						}else{

							if(from.equals("本部门")) {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
							    		sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and department_id like'" + bmid + "%'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
							    		ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("合同金额");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											list.add(report);
										}
							    	}
							    }
							    sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and create_id ='" + username + "'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
							    ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
							}else if(from.equals("未设置")) {
							    sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and create_id ='" + username + "'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
							    ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
							}else {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
							    		sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and department_id like'" + bmid + "'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
							    		ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("合同金额");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											list.add(report);
										}
							    	}
							    }
							    sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and create_id ='" + username + "'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
							    ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
							}
						}
				}else if(report.getResultsType().equals("合同回款金额")){
					String sql = "";
						if(from.equals("全部")) {
							sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同回款金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								list.add(report);
							}
						}else{

							if(from.equals("本部门")) {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
							    		sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and department_id like'" + bmid + "%'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
							    		ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("合同回款金额");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											list.add(report);
										}
							    	}
							    }
							    sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and create_id ='" + username + "'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
							    ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同回款金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
							}else if(from.equals("未设置")) {
							    sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and create_id ='" + username + "'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
							    ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同回款金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
							}else {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
							    		sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and department_id like'" + bmid + "'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
							    		ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("合同回款金额");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											list.add(report);
										}
							    	}
							    }
							    sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and create_id ='" + username + "'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
							    ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同回款金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
							}
						}
				}else if(report.getResultsType().equals("合同数")){
					String sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY COUNT(*) desc";

						if(from.equals("全部")) {
							sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY COUNT(*) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同数");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								list.add(report);
							}
						}else{
							if(from.equals("本部门")) {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
							    		sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+" and department_id like'" + bmid + "%'  and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY COUNT(*) desc";
							    		ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("合同数");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											list.add(report);
										}
							    	}
							    }
							    sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+" and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY COUNT(*) desc";
							    ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
							}else if(from.equals("未设置")) {
							    sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+" and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY COUNT(*) desc";
							    ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
							}else {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
							    		sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+" and department_id like'" + bmid + "'  and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY COUNT(*) desc";
							    		ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("合同数");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											list.add(report);
										}
							    	}
							    }
							    sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+" and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY COUNT(*) desc";
							    ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
							}
						}
				}else if(report.getResultsType().equals("赢单商机数")){
					
					String sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY COUNT(*) desc";

						if(from.equals("全部")) {
							sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY COUNT(*) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("赢单商机数");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								list.add(report);
							}
						}else{
							if(from.equals("本部门")) {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and department_id like'" + bmid + "%'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY COUNT(*) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("赢单商机数");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											list.add(report);
										}
							    	}
							    }
								sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY COUNT(*) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
							}else if(from.equals("未设置")) {
								sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY COUNT(*) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
							}else {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and department_id like'" + bmid + "'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY COUNT(*) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("赢单商机数");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											list.add(report);
										}
							    	}
							    }
								sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY COUNT(*) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
							}
						}
				}else if(report.getResultsType().equals("赢单商机金额")){
					String sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
						if(from.equals("全部")) {
							sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";

							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("赢单商机金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								list.add(report);
							}
						}else{
							if(from.equals("本部门")) {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and department_id like'" + bmid + "%'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("赢单商机金额");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											list.add(report);
										}
							    	}
							    }
								sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
							}else if(from.equals("未设置")) {
								sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
							}else {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and department_id like'" + bmid + "'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("赢单商机金额");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											list.add(report);
										}
							    	}
							    }
								sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
							}
						}
					
				}
				
			}else if(report.getTimeToScreen().equals("下月")){

		        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		        Date date = new Date();
		        Calendar calendar = Calendar.getInstance();
		        calendar.setTime(date); // 设置为当前时间
		        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1); // 设置为上一个月
		        date = calendar.getTime();
		        String accDate = format.format(date);
				BigDecimal xse=new BigDecimal(0);

				if(report.getResultsType().equals("合同金额")){
					String sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
						if(from.equals("全部")) {
							sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								list.add(report);
							}
						}else{

							if(from.equals("本部门")) {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
							    		sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and department_id like'" + bmid + "%'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
							    		ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("合同金额");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											list.add(report);
										}
							    	}
							    }
							    sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and create_id ='" + username + "'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
							    ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
							}else if(from.equals("未设置")) {
							    sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and create_id ='" + username + "'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
							    ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
							}else {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
							    		sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and department_id like'" + bmid + "'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
							    		ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("合同金额");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											list.add(report);
										}
							    	}
							    }
							    sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and create_id ='" + username + "'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
							    ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
							}
						}
				}else if(report.getResultsType().equals("合同回款金额")){
					String sql = "";
					if(from.equals("全部")) {
						sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
						ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						int i1=1;
						while (rs.next()) {
							report = new Report();
							report.setRanking(i1++);
							report.setColumn("合同回款金额");
							report.setAggregateAmount(rs.getBigDecimal(1));
							report.setDepartmentName(rs.getString(2));
							list.add(report);
						}
					}else{

						if(from.equals("本部门")) {

							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
						    		sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and department_id like'" + bmid + "%'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
						    		ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("合同回款金额");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										list.add(report);
									}
						    	}
						    }
						    sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and create_id ='" + username + "'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
						    ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同回款金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								list.add(report);
							}
					        for (int i11 = 0; i11 < list.size() - 1; i11++) {
					            for (int j = i11 + 1; j < list.size(); j++) {
					                if (list.get(i11).equals(list.get(j))) {
					                    list.remove(j);
					                }
					            }
					        }
						}else if(from.equals("未设置")) {
						    sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and create_id ='" + username + "'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
						    ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同回款金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								list.add(report);
							}
						}else {

							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
						    		sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and department_id like'" + bmid + "'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
						    		ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("合同回款金额");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										list.add(report);
									}
						    	}
						    }
						    sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and create_id ='" + username + "'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
						    ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同回款金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								list.add(report);
							}
					        for (int i11 = 0; i11 < list.size() - 1; i11++) {
					            for (int j = i11 + 1; j < list.size(); j++) {
					                if (list.get(i11).equals(list.get(j))) {
					                    list.remove(j);
					                }
					            }
					        }
						}
					}
			}else if(report.getResultsType().equals("合同数")){
					String sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY COUNT(*) desc";

						if(from.equals("全部")) {
							sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY COUNT(*) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同数");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								list.add(report);
							}
						}else{
							if(from.equals("本部门")) {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
							    		sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+" and department_id like'" + bmid + "%'  and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY COUNT(*) desc";
							    		ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("合同数");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											list.add(report);
										}
							    	}
							    }
							    sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+" and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY COUNT(*) desc";
							    ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
							}else if(from.equals("未设置")) {
							    sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+" and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY COUNT(*) desc";
							    ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
							}else {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
							    		sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+" and department_id like'" + bmid + "'  and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY COUNT(*) desc";
							    		ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("合同数");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											list.add(report);
										}
							    	}
							    }
							    sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+" and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY COUNT(*) desc";
							    ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
							}
						}
				}else if(report.getResultsType().equals("赢单商机数")){
					
					String sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY COUNT(*) desc";

						if(from.equals("全部")) {
							sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY COUNT(*) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("赢单商机数");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								list.add(report);
							}
						}else{
							if(from.equals("本部门")) {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and department_id like'" + bmid + "%'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY COUNT(*) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("赢单商机数");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											list.add(report);
										}
							    	}
							    }
								sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY COUNT(*) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
							}else if(from.equals("未设置")) {
								sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY COUNT(*) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
							}else {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and department_id like'" + bmid + "'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY COUNT(*) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("赢单商机数");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											list.add(report);
										}
							    	}
							    }
								sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY COUNT(*) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
							}
						}
				}else if(report.getResultsType().equals("赢单商机金额")){
					String sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
						if(from.equals("全部")) {
							sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";

							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("赢单商机金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								list.add(report);
							}
						}else{
							if(from.equals("本部门")) {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and department_id like'" + bmid + "%'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("赢单商机金额");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											list.add(report);
										}
							    	}
							    }
								sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
							}else if(from.equals("未设置")) {
								sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
							}else {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and department_id like'" + bmid + "'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("赢单商机金额");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											list.add(report);
										}
							    	}
							    }
								sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
							}
						}
					
				}
				
			}else if(report.getTimeToScreen().equals("本年")){


				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
				SimpleDateFormat formatz = new SimpleDateFormat("yyyy");
				
				String nowdate=format.format(new Date());//当前月份
				String nowdatez=formatz.format(new Date());//当前月份
				
				Date d1 = new SimpleDateFormat("yyyy-MM").parse(nowdatez+"-01");//定义起始日期
				
				Date d2 = new SimpleDateFormat("yyyy-MM").parse(nowdatez+"-12");//定义结束日期 可以去当前月也可以手动写日期。
				
				Calendar dd = Calendar.getInstance();//定义日期实例
				
				dd.setTime(d1);//设置日期起始时间
				
				while (dd.getTime().before(d2)) {
				
				SimpleDateFormat sdfzz = new SimpleDateFormat("yyyy-MM");

					String accDate = sdfzz.format(dd.getTime());
					BigDecimal xse=new BigDecimal(0);

					if(report.getResultsType().equals("合同金额")){
						String sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
							if(from.equals("全部")) {
								sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
							}else{
								if(from.equals("本部门")) {

									
									String[] xh=departmentIds.split(",");
								    for(String bmid : xh){
								    	if(!bmid.equals("")){
								    		sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and department_id like'" + bmid + "%'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
								    		ps = conn.prepareStatement(sql);
											rs = ps.executeQuery();
											int i1=1;
											while (rs.next()) {
												report = new Report();
												report.setRanking(i1++);
												report.setColumn("合同金额");
												report.setAggregateAmount(rs.getBigDecimal(1));
												report.setDepartmentName(rs.getString(2));
												list.add(report);
											}
								    	}
								    }
								    sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and create_id ='" + username + "'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
								    ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("合同金额");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										list.add(report);
									}
							        for (int i11 = 0; i11 < list.size() - 1; i11++) {
							            for (int j = i11 + 1; j < list.size(); j++) {
							                if (list.get(i11).equals(list.get(j))) {
							                    list.remove(j);
							                }
							            }
							        }
								}else if(from.equals("未设置")) {
								    sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and create_id ='" + username + "'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
								    ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("合同金额");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										list.add(report);
									}
								}else {

									
									String[] xh=departmentIds.split(",");
								    for(String bmid : xh){
								    	if(!bmid.equals("")){
								    		sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and department_id like'" + bmid + "'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
								    		ps = conn.prepareStatement(sql);
											rs = ps.executeQuery();
											int i1=1;
											while (rs.next()) {
												report = new Report();
												report.setRanking(i1++);
												report.setColumn("合同金额");
												report.setAggregateAmount(rs.getBigDecimal(1));
												report.setDepartmentName(rs.getString(2));
												list.add(report);
											}
								    	}
								    }
								    sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and create_id ='" + username + "'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
								    ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("合同金额");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										list.add(report);
									}
							        for (int i11 = 0; i11 < list.size() - 1; i11++) {
							            for (int j = i11 + 1; j < list.size(); j++) {
							                if (list.get(i11).equals(list.get(j))) {
							                    list.remove(j);
							                }
							            }
							        }
								}
							}
					}else if(report.getResultsType().equals("合同回款金额")){
						String sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
						if(from.equals("全部")) {
							sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同回款金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								list.add(report);
							}
						}else{
							if(from.equals("本部门")) {

								
								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
							    		sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and department_id like'" + bmid + "%'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
							    		ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("合同回款金额");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											list.add(report);
										}
							    	}
							    }
							    sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and create_id ='" + username + "'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
							    ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同回款金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
							}else if(from.equals("未设置")) {
							    sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and create_id ='" + username + "'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
							    ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同回款金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
							}else {

								
								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
							    		sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and department_id like'" + bmid + "'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
							    		ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("合同回款金额");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											list.add(report);
										}
							    	}
							    }
							    sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and create_id ='" + username + "'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
							    ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同回款金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
							}
						}
				}else if(report.getResultsType().equals("合同数")){
						String sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY COUNT(*) desc";

							if(from.equals("全部")) {
								sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY COUNT(*) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
							}else{
								if(from.equals("本部门")) {
									String[] xh=departmentIds.split(",");
								    for(String bmid : xh){
								    	if(!bmid.equals("")){
								    		sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+" and department_id like'" + bmid + "%'  and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY COUNT(*) desc";
								    		ps = conn.prepareStatement(sql);
											rs = ps.executeQuery();
											int i1=1;
											while (rs.next()) {
												report = new Report();
												report.setRanking(i1++);
												report.setColumn("合同数");
												report.setAggregateAmount(rs.getBigDecimal(1));
												report.setDepartmentName(rs.getString(2));
												list.add(report);
											}
								    	}
								    }
								    sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+" and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY COUNT(*) desc";
								    ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("合同数");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										list.add(report);
									}
							        for (int i11 = 0; i11 < list.size() - 1; i11++) {
							            for (int j = i11 + 1; j < list.size(); j++) {
							                if (list.get(i11).equals(list.get(j))) {
							                    list.remove(j);
							                }
							            }
							        }
								}else if(from.equals("未设置")) {
								    sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+" and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY COUNT(*) desc";
								    ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("合同数");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										list.add(report);
									}
								}else {

									String[] xh=departmentIds.split(",");
								    for(String bmid : xh){
								    	if(!bmid.equals("")){
								    		sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+" and department_id like'" + bmid + "'  and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY COUNT(*) desc";
								    		ps = conn.prepareStatement(sql);
											rs = ps.executeQuery();
											int i1=1;
											while (rs.next()) {
												report = new Report();
												report.setRanking(i1++);
												report.setColumn("合同数");
												report.setAggregateAmount(rs.getBigDecimal(1));
												report.setDepartmentName(rs.getString(2));
												list.add(report);
											}
								    	}
								    }
								    sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+" and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY COUNT(*) desc";
								    ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("合同数");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										list.add(report);
									}
							        for (int i11 = 0; i11 < list.size() - 1; i11++) {
							            for (int j = i11 + 1; j < list.size(); j++) {
							                if (list.get(i11).equals(list.get(j))) {
							                    list.remove(j);
							                }
							            }
							        }
								}
							}
					}else if(report.getResultsType().equals("赢单商机数")){
						
						String sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY COUNT(*) desc";

							if(from.equals("全部")) {
								sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY COUNT(*) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
							}else{
								if(from.equals("本部门")) {

									String[] xh=departmentIds.split(",");
								    for(String bmid : xh){
								    	if(!bmid.equals("")){
											sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and department_id like'" + bmid + "%'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY COUNT(*) desc";
											ps = conn.prepareStatement(sql);
											rs = ps.executeQuery();
											int i1=1;
											while (rs.next()) {
												report = new Report();
												report.setRanking(i1++);
												report.setColumn("赢单商机数");
												report.setAggregateAmount(rs.getBigDecimal(1));
												report.setDepartmentName(rs.getString(2));
												list.add(report);
											}
								    	}
								    }
									sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY COUNT(*) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("赢单商机数");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										list.add(report);
									}
							        for (int i11 = 0; i11 < list.size() - 1; i11++) {
							            for (int j = i11 + 1; j < list.size(); j++) {
							                if (list.get(i11).equals(list.get(j))) {
							                    list.remove(j);
							                }
							            }
							        }
								}else if(from.equals("未设置")) {
									sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY COUNT(*) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("赢单商机数");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										list.add(report);
									}
								}else {

									String[] xh=departmentIds.split(",");
								    for(String bmid : xh){
								    	if(!bmid.equals("")){
											sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and department_id like'" + bmid + "'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY COUNT(*) desc";
											ps = conn.prepareStatement(sql);
											rs = ps.executeQuery();
											int i1=1;
											while (rs.next()) {
												report = new Report();
												report.setRanking(i1++);
												report.setColumn("赢单商机数");
												report.setAggregateAmount(rs.getBigDecimal(1));
												report.setDepartmentName(rs.getString(2));
												list.add(report);
											}
								    	}
								    }
									sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY COUNT(*) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("赢单商机数");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										list.add(report);
									}
							        for (int i11 = 0; i11 < list.size() - 1; i11++) {
							            for (int j = i11 + 1; j < list.size(); j++) {
							                if (list.get(i11).equals(list.get(j))) {
							                    list.remove(j);
							                }
							            }
							        }
								}
							}
					}else if(report.getResultsType().equals("赢单商机金额")){
						String sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
							if(from.equals("全部")) {
								sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";

								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
							}else{
								if(from.equals("本部门")) {

									String[] xh=departmentIds.split(",");
								    for(String bmid : xh){
								    	if(!bmid.equals("")){
											sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and department_id like'" + bmid + "%'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
											ps = conn.prepareStatement(sql);
											rs = ps.executeQuery();
											int i1=1;
											while (rs.next()) {
												report = new Report();
												report.setRanking(i1++);
												report.setColumn("赢单商机金额");
												report.setAggregateAmount(rs.getBigDecimal(1));
												report.setDepartmentName(rs.getString(2));
												list.add(report);
											}
								    	}
								    }
									sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("赢单商机金额");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										list.add(report);
									}
							        for (int i11 = 0; i11 < list.size() - 1; i11++) {
							            for (int j = i11 + 1; j < list.size(); j++) {
							                if (list.get(i11).equals(list.get(j))) {
							                    list.remove(j);
							                }
							            }
							        }
								}else if(from.equals("未设置")) {
									sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("赢单商机金额");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										list.add(report);
									}
								}else {

									String[] xh=departmentIds.split(",");
								    for(String bmid : xh){
								    	if(!bmid.equals("")){
											sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and department_id like'" + bmid + "'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
											ps = conn.prepareStatement(sql);
											rs = ps.executeQuery();
											int i1=1;
											while (rs.next()) {
												report = new Report();
												report.setRanking(i1++);
												report.setColumn("赢单商机金额");
												report.setAggregateAmount(rs.getBigDecimal(1));
												report.setDepartmentName(rs.getString(2));
												list.add(report);
											}
								    	}
								    }
									sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("赢单商机金额");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										list.add(report);
									}
							        for (int i11 = 0; i11 < list.size() - 1; i11++) {
							            for (int j = i11 + 1; j < list.size(); j++) {
							                if (list.get(i11).equals(list.get(j))) {
							                    list.remove(j);
							                }
							            }
							        }
								}
							}
						
					}
					
					dd.add(Calendar.MONTH, 1);//进行当前日期月份加1
				
				}

				SimpleDateFormat sdfzz = new SimpleDateFormat("yyyy-MM");
				String accDate=sdfzz.format(d2.getTime());
				BigDecimal xse=new BigDecimal(0);

				if(report.getResultsType().equals("合同金额")){
					String sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
						if(from.equals("全部")) {
							sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								list.add(report);
							}
						}else{

							if(from.equals("本部门")) {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
							    		sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and department_id like'" + bmid + "%'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
							    		ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("合同金额");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											list.add(report);
										}
							    	}
							    }
							    sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and create_id ='" + username + "'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
							    ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
							}else if(from.equals("未设置")) {
							    sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and create_id ='" + username + "'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
							    ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
							}else {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
							    		sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and department_id like'" + bmid + "'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
							    		ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("合同金额");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											list.add(report);
										}
							    	}
							    }
							    sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and create_id ='" + username + "'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
							    ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
							}
						}
				}else if(report.getResultsType().equals("合同回款金额")){
					String sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
					if(from.equals("全部")) {
						sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
						ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						int i1=1;
						while (rs.next()) {
							report = new Report();
							report.setRanking(i1++);
							report.setColumn("合同回款金额");
							report.setAggregateAmount(rs.getBigDecimal(1));
							report.setDepartmentName(rs.getString(2));
							list.add(report);
						}
					}else{

						if(from.equals("本部门")) {

							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
						    		sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and department_id like'" + bmid + "%'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
						    		ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("合同回款金额");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										list.add(report);
									}
						    	}
						    }
						    sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and create_id ='" + username + "'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
						    ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同回款金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								list.add(report);
							}
					        for (int i11 = 0; i11 < list.size() - 1; i11++) {
					            for (int j = i11 + 1; j < list.size(); j++) {
					                if (list.get(i11).equals(list.get(j))) {
					                    list.remove(j);
					                }
					            }
					        }
						}else if(from.equals("未设置")) {
						    sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and create_id ='" + username + "'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
						    ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同回款金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								list.add(report);
							}
						}else {

							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
						    		sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and department_id like'" + bmid + "'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
						    		ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("合同回款金额");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										list.add(report);
									}
						    	}
						    }
						    sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and create_id ='" + username + "'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
						    ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同回款金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								list.add(report);
							}
					        for (int i11 = 0; i11 < list.size() - 1; i11++) {
					            for (int j = i11 + 1; j < list.size(); j++) {
					                if (list.get(i11).equals(list.get(j))) {
					                    list.remove(j);
					                }
					            }
					        }
						}
					}
			}else if(report.getResultsType().equals("合同数")){
					String sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY COUNT(*) desc";

						if(from.equals("全部")) {
							sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY COUNT(*) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同数");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								list.add(report);
							}
						}else{
							if(from.equals("本部门")) {
								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
							    		sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+" and department_id like'" + bmid + "%'  and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY COUNT(*) desc";
							    		ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("合同数");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											list.add(report);
										}
							    	}
							    }
							    sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+" and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY COUNT(*) desc";
							    ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
								
							}else if(from.equals("未设置")) {
							    sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+" and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY COUNT(*) desc";
							    ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
							}else {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
							    		sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+" and department_id like'" + bmid + "'  and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY COUNT(*) desc";
							    		ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("合同数");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											list.add(report);
										}
							    	}
							    }
							    sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+" and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY COUNT(*) desc";
							    ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
							}
						}
				}else if(report.getResultsType().equals("赢单商机数")){
					
					String sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY COUNT(*) desc";

						if(from.equals("全部")) {
							sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY COUNT(*) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("赢单商机数");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								list.add(report);
							}
						}else{
							if(from.equals("本部门")) {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and department_id like'" + bmid + "%'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY COUNT(*) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("赢单商机数");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											list.add(report);
										}
							    	}
							    }
								sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY COUNT(*) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
							}else if(from.equals("未设置")) {
								sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY COUNT(*) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
							}else {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and department_id like'" + bmid + "'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY COUNT(*) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("赢单商机数");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											list.add(report);
										}
							    	}
							    }
								sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY COUNT(*) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
							}
						}
				}else if(report.getResultsType().equals("赢单商机金额")){
					String sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
						if(from.equals("全部")) {
							sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";

							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("赢单商机金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								list.add(report);
							}
						}else{
							if(from.equals("本部门")) {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and department_id like'" + bmid + "%'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("赢单商机金额");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											list.add(report);
										}
							    	}
							    }
								sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
							}else if(from.equals("未设置")) {
								sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
							}else {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and department_id like'" + bmid + "'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("赢单商机金额");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											list.add(report);
										}
							    	}
							    }
								sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
							}
						}
					
				}
				
			
				
				

				
			}else if(report.getTimeToScreen().equals("上半年")){


				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
				SimpleDateFormat formatz = new SimpleDateFormat("yyyy");
				
				String nowdate=format.format(new Date());//当前月份
				String nowdatez=formatz.format(new Date());//当前月份
				
				Date d1 = new SimpleDateFormat("yyyy-MM").parse(nowdatez+"-01");//定义起始日期
				
				Date d2 = new SimpleDateFormat("yyyy-MM").parse(nowdatez+"-06");//定义结束日期 可以去当前月也可以手动写日期。
				
				Calendar dd = Calendar.getInstance();//定义日期实例
				
				dd.setTime(d1);//设置日期起始时间
				
				while (dd.getTime().before(d2)) {
				
				SimpleDateFormat sdfzz = new SimpleDateFormat("yyyy-MM");

					String accDate = sdfzz.format(dd.getTime());
					BigDecimal xse=new BigDecimal(0);

					if(report.getResultsType().equals("合同金额")){
						String sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
							if(from.equals("全部")) {
								sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
							}else{
								if(from.equals("本部门")) {

									
									String[] xh=departmentIds.split(",");
								    for(String bmid : xh){
								    	if(!bmid.equals("")){
								    		sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and department_id like'" + bmid + "%'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
								    		ps = conn.prepareStatement(sql);
											rs = ps.executeQuery();
											int i1=1;
											while (rs.next()) {
												report = new Report();
												report.setRanking(i1++);
												report.setColumn("合同金额");
												report.setAggregateAmount(rs.getBigDecimal(1));
												report.setDepartmentName(rs.getString(2));
												list.add(report);
											}
								    	}
								    }
								    sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and create_id ='" + username + "'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
								    ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("合同金额");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										list.add(report);
									}
							        for (int i11 = 0; i11 < list.size() - 1; i11++) {
							            for (int j = i11 + 1; j < list.size(); j++) {
							                if (list.get(i11).equals(list.get(j))) {
							                    list.remove(j);
							                }
							            }
							        }
								}else if(from.equals("未设置")) {
								    sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and create_id ='" + username + "'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
								    ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("合同金额");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										list.add(report);
									}
								}else {

									
									String[] xh=departmentIds.split(",");
								    for(String bmid : xh){
								    	if(!bmid.equals("")){
								    		sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and department_id like'" + bmid + "'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
								    		ps = conn.prepareStatement(sql);
											rs = ps.executeQuery();
											int i1=1;
											while (rs.next()) {
												report = new Report();
												report.setRanking(i1++);
												report.setColumn("合同金额");
												report.setAggregateAmount(rs.getBigDecimal(1));
												report.setDepartmentName(rs.getString(2));
												list.add(report);
											}
								    	}
								    }
								    sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and create_id ='" + username + "'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
								    ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("合同金额");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										list.add(report);
									}
							        for (int i11 = 0; i11 < list.size() - 1; i11++) {
							            for (int j = i11 + 1; j < list.size(); j++) {
							                if (list.get(i11).equals(list.get(j))) {
							                    list.remove(j);
							                }
							            }
							        }
								}
							}
					}else if(report.getResultsType().equals("合同回款金额")){
						String sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
						if(from.equals("全部")) {
							sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同回款金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								list.add(report);
							}
						}else{
							if(from.equals("本部门")) {

								
								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
							    		sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and department_id like'" + bmid + "%'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
							    		ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("合同回款金额");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											list.add(report);
										}
							    	}
							    }
							    sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and create_id ='" + username + "'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
							    ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同回款金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
							}else if(from.equals("未设置")) {
							    sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and create_id ='" + username + "'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
							    ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同回款金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
							}else {

								
								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
							    		sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and department_id like'" + bmid + "'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
							    		ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("合同回款金额");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											list.add(report);
										}
							    	}
							    }
							    sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and create_id ='" + username + "'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
							    ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同回款金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
							}
						}
				}else if(report.getResultsType().equals("合同数")){
						String sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY COUNT(*) desc";

							if(from.equals("全部")) {
								sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY COUNT(*) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
							}else{
								if(from.equals("本部门")) {
									String[] xh=departmentIds.split(",");
								    for(String bmid : xh){
								    	if(!bmid.equals("")){
								    		sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+" and department_id like'" + bmid + "%'  and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY COUNT(*) desc";
								    		ps = conn.prepareStatement(sql);
											rs = ps.executeQuery();
											int i1=1;
											while (rs.next()) {
												report = new Report();
												report.setRanking(i1++);
												report.setColumn("合同数");
												report.setAggregateAmount(rs.getBigDecimal(1));
												report.setDepartmentName(rs.getString(2));
												list.add(report);
											}
								    	}
								    }
								    sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+" and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY COUNT(*) desc";
								    ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("合同数");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										list.add(report);
									}
							        for (int i11 = 0; i11 < list.size() - 1; i11++) {
							            for (int j = i11 + 1; j < list.size(); j++) {
							                if (list.get(i11).equals(list.get(j))) {
							                    list.remove(j);
							                }
							            }
							        }
								}else if(from.equals("未设置")) {
								    sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+" and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY COUNT(*) desc";
								    ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("合同数");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										list.add(report);
									}
								}else {
									String[] xh=departmentIds.split(",");
								    for(String bmid : xh){
								    	if(!bmid.equals("")){
								    		sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+" and department_id like'" + bmid + "'  and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY COUNT(*) desc";
								    		ps = conn.prepareStatement(sql);
											rs = ps.executeQuery();
											int i1=1;
											while (rs.next()) {
												report = new Report();
												report.setRanking(i1++);
												report.setColumn("合同数");
												report.setAggregateAmount(rs.getBigDecimal(1));
												report.setDepartmentName(rs.getString(2));
												list.add(report);
											}
								    	}
								    }
								    sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+" and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY COUNT(*) desc";
								    ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("合同数");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										list.add(report);
									}
							        for (int i11 = 0; i11 < list.size() - 1; i11++) {
							            for (int j = i11 + 1; j < list.size(); j++) {
							                if (list.get(i11).equals(list.get(j))) {
							                    list.remove(j);
							                }
							            }
							        }
								}
							}
					}else if(report.getResultsType().equals("赢单商机数")){
						
						String sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY COUNT(*) desc";

							if(from.equals("全部")) {
								sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY COUNT(*) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
							}else{
								if(from.equals("本部门")) {

									String[] xh=departmentIds.split(",");
								    for(String bmid : xh){
								    	if(!bmid.equals("")){
											sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and department_id like'" + bmid + "%'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY COUNT(*) desc";
											ps = conn.prepareStatement(sql);
											rs = ps.executeQuery();
											int i1=1;
											while (rs.next()) {
												report = new Report();
												report.setRanking(i1++);
												report.setColumn("赢单商机数");
												report.setAggregateAmount(rs.getBigDecimal(1));
												report.setDepartmentName(rs.getString(2));
												list.add(report);
											}
								    	}
								    }
									sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY COUNT(*) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("赢单商机数");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										list.add(report);
									}
							        for (int i11 = 0; i11 < list.size() - 1; i11++) {
							            for (int j = i11 + 1; j < list.size(); j++) {
							                if (list.get(i11).equals(list.get(j))) {
							                    list.remove(j);
							                }
							            }
							        }
								}else if(from.equals("未设置")) {
									sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY COUNT(*) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("赢单商机数");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										list.add(report);
									}
								}else {

									String[] xh=departmentIds.split(",");
								    for(String bmid : xh){
								    	if(!bmid.equals("")){
											sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and department_id like'" + bmid + "'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY COUNT(*) desc";
											ps = conn.prepareStatement(sql);
											rs = ps.executeQuery();
											int i1=1;
											while (rs.next()) {
												report = new Report();
												report.setRanking(i1++);
												report.setColumn("赢单商机数");
												report.setAggregateAmount(rs.getBigDecimal(1));
												report.setDepartmentName(rs.getString(2));
												list.add(report);
											}
								    	}
								    }
									sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY COUNT(*) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("赢单商机数");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										list.add(report);
									}
							        for (int i11 = 0; i11 < list.size() - 1; i11++) {
							            for (int j = i11 + 1; j < list.size(); j++) {
							                if (list.get(i11).equals(list.get(j))) {
							                    list.remove(j);
							                }
							            }
							        }
								}
							}
					}else if(report.getResultsType().equals("赢单商机金额")){
						String sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
							if(from.equals("全部")) {
								sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";

								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
							}else{
								if(from.equals("本部门")) {
									String[] xh=departmentIds.split(",");
								    for(String bmid : xh){
								    	if(!bmid.equals("")){
											sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and department_id like'" + bmid + "%'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
											ps = conn.prepareStatement(sql);
											rs = ps.executeQuery();
											int i1=1;
											while (rs.next()) {
												report = new Report();
												report.setRanking(i1++);
												report.setColumn("赢单商机金额"); 
												report.setAggregateAmount(rs.getBigDecimal(1));
												report.setDepartmentName(rs.getString(2));
												list.add(report);
											}
								    	}
								    }
									sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("赢单商机金额");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										list.add(report);
									}
							        for (int i11 = 0; i11 < list.size() - 1; i11++) {
							            for (int j = i11 + 1; j < list.size(); j++) {
							                if (list.get(i11).equals(list.get(j))) {
							                    list.remove(j);
							                }
							            }
							        }
								}else if(from.equals("未设置")) {
									sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("赢单商机金额");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										list.add(report);
									}
								}else {

									String[] xh=departmentIds.split(",");
								    for(String bmid : xh){
								    	if(!bmid.equals("")){
											sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and department_id like'" + bmid + "'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
											ps = conn.prepareStatement(sql);
											rs = ps.executeQuery();
											int i1=1;
											while (rs.next()) {
												report = new Report();
												report.setRanking(i1++);
												report.setColumn("赢单商机金额");
												report.setAggregateAmount(rs.getBigDecimal(1));
												report.setDepartmentName(rs.getString(2));
												list.add(report);
											}
								    	}
								    }
									sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("赢单商机金额");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										list.add(report);
									}
							        for (int i11 = 0; i11 < list.size() - 1; i11++) {
							            for (int j = i11 + 1; j < list.size(); j++) {
							                if (list.get(i11).equals(list.get(j))) {
							                    list.remove(j);
							                }
							            }
							        }
								}
							}
						
					}
					dd.add(Calendar.MONTH, 1);//进行当前日期月份加1
				
				}

				SimpleDateFormat sdfzz = new SimpleDateFormat("yyyy-MM");
				String accDate=sdfzz.format(d2.getTime());
				BigDecimal xse=new BigDecimal(0);

				if(report.getResultsType().equals("合同金额")){
					String sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
						if(from.equals("全部")) {
							sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								list.add(report);
							}
						}else{

							if(from.equals("本部门")) {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
							    		sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and department_id like'" + bmid + "%'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
							    		ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("合同金额");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											list.add(report);
										}
							    	}
							    }
							    sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and create_id ='" + username + "'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
							    ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
							}else if(from.equals("未设置")) {
							    sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and create_id ='" + username + "'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
							    ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
							}else {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
							    		sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and department_id like'" + bmid + "'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
							    		ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("合同金额");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											list.add(report);
										}
							    	}
							    }
							    sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and create_id ='" + username + "'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
							    ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
							}
						}
				}else if(report.getResultsType().equals("合同回款金额")){
					String sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
					if(from.equals("全部")) {
						sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
						ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						int i1=1;
						while (rs.next()) {
							report = new Report();
							report.setRanking(i1++);
							report.setColumn("合同回款金额");
							report.setAggregateAmount(rs.getBigDecimal(1));
							report.setDepartmentName(rs.getString(2));
							list.add(report);
						}
					}else{

						if(from.equals("本部门")) {

							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
						    		sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and department_id like'" + bmid + "%'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
						    		ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("合同回款金额");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										list.add(report);
									}
						    	}
						    }
						    sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and create_id ='" + username + "'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
						    ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同回款金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								list.add(report);
							}
					        for (int i11 = 0; i11 < list.size() - 1; i11++) {
					            for (int j = i11 + 1; j < list.size(); j++) {
					                if (list.get(i11).equals(list.get(j))) {
					                    list.remove(j);
					                }
					            }
					        }
						}else if(from.equals("未设置")) {
						    sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and create_id ='" + username + "'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
						    ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同回款金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								list.add(report);
							}
						}else {

							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
						    		sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and department_id like'" + bmid + "'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
						    		ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("合同回款金额");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										list.add(report);
									}
						    	}
						    }
						    sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and create_id ='" + username + "'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
						    ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同回款金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								list.add(report);
							}
					        for (int i11 = 0; i11 < list.size() - 1; i11++) {
					            for (int j = i11 + 1; j < list.size(); j++) {
					                if (list.get(i11).equals(list.get(j))) {
					                    list.remove(j);
					                }
					            }
					        }
						}
					}
			}else if(report.getResultsType().equals("合同数")){
					String sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY COUNT(*) desc";

						if(from.equals("全部")) {
							sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY COUNT(*) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同数");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								list.add(report);
							}
						}else{
							if(from.equals("本部门")) {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
							    		sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+" and department_id like'" + bmid + "%'  and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY COUNT(*) desc";
							    		ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("合同数");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											list.add(report);
										}
							    	}
							    }
							    sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+" and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY COUNT(*) desc";
							    ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
							}else if(from.equals("未设置")) {
							    sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+" and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY COUNT(*) desc";
							    ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
							}else {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
							    		sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+" and department_id like'" + bmid + "'  and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY COUNT(*) desc";
							    		ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("合同数");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											list.add(report);
										}
							    	}
							    }
							    sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+" and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY COUNT(*) desc";
							    ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
							}
						}
				}else if(report.getResultsType().equals("赢单商机数")){
					
					String sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY COUNT(*) desc";

						if(from.equals("全部")) {
							sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY COUNT(*) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("赢单商机数");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								list.add(report);
							}
						}else{
							if(from.equals("本部门")) {
								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and department_id like'" + bmid + "%'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY COUNT(*) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("赢单商机数");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											list.add(report);
										}
							    	}
							    }
								sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY COUNT(*) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
								
							}else if(from.equals("未设置")) {
								sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY COUNT(*) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
							}else {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and department_id like'" + bmid + "'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY COUNT(*) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("赢单商机数");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											list.add(report);
										}
							    	}
							    }
								sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY COUNT(*) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
							}
						}
				}else if(report.getResultsType().equals("赢单商机金额")){
					String sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
						if(from.equals("全部")) {
							sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";

							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("赢单商机金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								list.add(report);
							}
						}else{
							if(from.equals("本部门")) {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and department_id like'" + bmid + "%'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("赢单商机金额");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											list.add(report);
										}
							    	}
							    }
								sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
							}else if(from.equals("未设置")) {
								sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
							}else {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and department_id like'" + bmid + "'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("赢单商机金额");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											list.add(report);
										}
							    	}
							    }
								sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
							}
						}
					
				}
			
				
				

				
			}else if(report.getTimeToScreen().equals("下半年")){


				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
				SimpleDateFormat formatz = new SimpleDateFormat("yyyy");
				
				String nowdate=format.format(new Date());//当前月份
				String nowdatez=formatz.format(new Date());//当前月份
				
				Date d1 = new SimpleDateFormat("yyyy-MM").parse(nowdatez+"-07");//定义起始日期
				
				Date d2 = new SimpleDateFormat("yyyy-MM").parse(nowdatez+"-12");//定义结束日期 可以去当前月也可以手动写日期。
				
				Calendar dd = Calendar.getInstance();//定义日期实例
				
				dd.setTime(d1);//设置日期起始时间
				
				while (dd.getTime().before(d2)) {
				
				SimpleDateFormat sdfzz = new SimpleDateFormat("yyyy-MM");

					String accDate = sdfzz.format(dd.getTime());
					BigDecimal xse=new BigDecimal(0);

					if(report.getResultsType().equals("合同金额")){
						String sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
							if(from.equals("全部")) {
								sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
							}else{
								if(from.equals("本部门")) {

									
									String[] xh=departmentIds.split(",");
								    for(String bmid : xh){
								    	if(!bmid.equals("")){
								    		sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and department_id like'" + bmid + "%'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
								    		ps = conn.prepareStatement(sql);
											rs = ps.executeQuery();
											int i1=1;
											while (rs.next()) {
												report = new Report();
												report.setRanking(i1++);
												report.setColumn("合同金额");
												report.setAggregateAmount(rs.getBigDecimal(1));
												report.setDepartmentName(rs.getString(2));
												list.add(report);
											}
								    	}
								    }
								    sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and create_id ='" + username + "'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
								    ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("合同金额");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										list.add(report);
									}
							        for (int i11 = 0; i11 < list.size() - 1; i11++) {
							            for (int j = i11 + 1; j < list.size(); j++) {
							                if (list.get(i11).equals(list.get(j))) {
							                    list.remove(j);
							                }
							            }
							        }
								}else if(from.equals("未设置")) {
								    sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and create_id ='" + username + "'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
								    ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("合同金额");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										list.add(report);
									}
								}else {

									
									String[] xh=departmentIds.split(",");
								    for(String bmid : xh){
								    	if(!bmid.equals("")){
								    		sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and department_id like'" + bmid + "'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
								    		ps = conn.prepareStatement(sql);
											rs = ps.executeQuery();
											int i1=1;
											while (rs.next()) {
												report = new Report();
												report.setRanking(i1++);
												report.setColumn("合同金额");
												report.setAggregateAmount(rs.getBigDecimal(1));
												report.setDepartmentName(rs.getString(2));
												list.add(report);
											}
								    	}
								    }
								    sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and create_id ='" + username + "'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
								    ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("合同金额");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										list.add(report);
									}
							        for (int i11 = 0; i11 < list.size() - 1; i11++) {
							            for (int j = i11 + 1; j < list.size(); j++) {
							                if (list.get(i11).equals(list.get(j))) {
							                    list.remove(j);
							                }
							            }
							        }
								}
							}
					}else if(report.getResultsType().equals("合同回款金额")){
						String sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
						if(from.equals("全部")) {
							sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同回款金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								list.add(report);
							}
						}else{
							if(from.equals("本部门")) {

								
								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
							    		sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and department_id like'" + bmid + "%'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
							    		ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("合同回款金额");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											list.add(report);
										}
							    	}
							    }
							    sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and create_id ='" + username + "'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
							    ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同回款金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
							}else if(from.equals("未设置")) {
							    sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and create_id ='" + username + "'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
							    ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同回款金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
							}else {

								
								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
							    		sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and department_id like'" + bmid + "'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
							    		ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("合同回款金额");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											list.add(report);
										}
							    	}
							    }
							    sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and create_id ='" + username + "'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
							    ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同回款金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
							}
						}
				}else if(report.getResultsType().equals("合同数")){
						String sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY COUNT(*) desc";

							if(from.equals("全部")) {
								sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY COUNT(*) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
							}else{
								if(from.equals("本部门")) {

									String[] xh=departmentIds.split(",");
								    for(String bmid : xh){
								    	if(!bmid.equals("")){
								    		sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+" and department_id like'" + bmid + "%'  and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY COUNT(*) desc";
								    		ps = conn.prepareStatement(sql);
											rs = ps.executeQuery();
											int i1=1;
											while (rs.next()) {
												report = new Report();
												report.setRanking(i1++);
												report.setColumn("合同数");
												report.setAggregateAmount(rs.getBigDecimal(1));
												report.setDepartmentName(rs.getString(2));
												list.add(report);
											}
								    	}
								    }
								    sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+" and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY COUNT(*) desc";
								    ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("合同数");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										list.add(report);
									}
							        for (int i11 = 0; i11 < list.size() - 1; i11++) {
							            for (int j = i11 + 1; j < list.size(); j++) {
							                if (list.get(i11).equals(list.get(j))) {
							                    list.remove(j);
							                }
							            }
							        }
								}else if(from.equals("未设置")) {
								    sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+" and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY COUNT(*) desc";
								    ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("合同数");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										list.add(report);
									}
								}else {

									String[] xh=departmentIds.split(",");
								    for(String bmid : xh){
								    	if(!bmid.equals("")){
								    		sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+" and department_id like'" + bmid + "'  and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY COUNT(*) desc";
								    		ps = conn.prepareStatement(sql);
											rs = ps.executeQuery();
											int i1=1;
											while (rs.next()) {
												report = new Report();
												report.setRanking(i1++);
												report.setColumn("合同数");
												report.setAggregateAmount(rs.getBigDecimal(1));
												report.setDepartmentName(rs.getString(2));
												list.add(report);
											}
								    	}
								    }
								    sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+" and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY COUNT(*) desc";
								    ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("合同数");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										list.add(report);
									}
							        for (int i11 = 0; i11 < list.size() - 1; i11++) {
							            for (int j = i11 + 1; j < list.size(); j++) {
							                if (list.get(i11).equals(list.get(j))) {
							                    list.remove(j);
							                }
							            }
							        }
								}
							}
					}else if(report.getResultsType().equals("赢单商机数")){
						
						String sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY COUNT(*) desc";

							if(from.equals("全部")) {
								sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY COUNT(*) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
							}else{
								if(from.equals("本部门")) {

									String[] xh=departmentIds.split(",");
								    for(String bmid : xh){
								    	if(!bmid.equals("")){
											sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and department_id like'" + bmid + "%'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY COUNT(*) desc";
											ps = conn.prepareStatement(sql);
											rs = ps.executeQuery();
											int i1=1;
											while (rs.next()) {
												report = new Report();
												report.setRanking(i1++);
												report.setColumn("赢单商机数");
												report.setAggregateAmount(rs.getBigDecimal(1));
												report.setDepartmentName(rs.getString(2));
												list.add(report);
											}
								    	}
								    }
									sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY COUNT(*) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("赢单商机数");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										list.add(report);
									}
							        for (int i11 = 0; i11 < list.size() - 1; i11++) {
							            for (int j = i11 + 1; j < list.size(); j++) {
							                if (list.get(i11).equals(list.get(j))) {
							                    list.remove(j);
							                }
							            }
							        }
								}else if(from.equals("未设置")) {
									sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY COUNT(*) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("赢单商机数");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										list.add(report);
									}
								}else {

									String[] xh=departmentIds.split(",");
								    for(String bmid : xh){
								    	if(!bmid.equals("")){
											sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and department_id like'" + bmid + "'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY COUNT(*) desc";
											ps = conn.prepareStatement(sql);
											rs = ps.executeQuery();
											int i1=1;
											while (rs.next()) {
												report = new Report();
												report.setRanking(i1++);
												report.setColumn("赢单商机数");
												report.setAggregateAmount(rs.getBigDecimal(1));
												report.setDepartmentName(rs.getString(2));
												list.add(report);
											}
								    	}
								    }
									sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY COUNT(*) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("赢单商机数");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										list.add(report);
									}
							        for (int i11 = 0; i11 < list.size() - 1; i11++) {
							            for (int j = i11 + 1; j < list.size(); j++) {
							                if (list.get(i11).equals(list.get(j))) {
							                    list.remove(j);
							                }
							            }
							        }
								}
							}
					}else if(report.getResultsType().equals("赢单商机金额")){
						String sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
							if(from.equals("全部")) {
								sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";

								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
							}else{
								if(from.equals("本部门")) {

									String[] xh=departmentIds.split(",");
								    for(String bmid : xh){
								    	if(!bmid.equals("")){
											sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and department_id like'" + bmid + "%'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
											ps = conn.prepareStatement(sql);
											rs = ps.executeQuery();
											int i1=1;
											while (rs.next()) {
												report = new Report();
												report.setRanking(i1++);
												report.setColumn("赢单商机金额");
												report.setAggregateAmount(rs.getBigDecimal(1));
												report.setDepartmentName(rs.getString(2));
												list.add(report);
											}
								    	}
								    }
									sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("赢单商机金额");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										list.add(report);
									}
							        for (int i11 = 0; i11 < list.size() - 1; i11++) {
							            for (int j = i11 + 1; j < list.size(); j++) {
							                if (list.get(i11).equals(list.get(j))) {
							                    list.remove(j);
							                }
							            }
							        }
								}else if(from.equals("未设置")) {
									sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("赢单商机金额");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										list.add(report);
									}
								}else {

									String[] xh=departmentIds.split(",");
								    for(String bmid : xh){
								    	if(!bmid.equals("")){
											sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and department_id like'" + bmid + "'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
											ps = conn.prepareStatement(sql);
											rs = ps.executeQuery();
											int i1=1;
											while (rs.next()) {
												report = new Report();
												report.setRanking(i1++);
												report.setColumn("赢单商机金额");
												report.setAggregateAmount(rs.getBigDecimal(1));
												report.setDepartmentName(rs.getString(2));
												list.add(report);
											}
								    	}
								    }
									sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("赢单商机金额");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										list.add(report);
									}
							        for (int i11 = 0; i11 < list.size() - 1; i11++) {
							            for (int j = i11 + 1; j < list.size(); j++) {
							                if (list.get(i11).equals(list.get(j))) {
							                    list.remove(j);
							                }
							            }
							        }
								}
							}
						
					}
				
					dd.add(Calendar.MONTH, 1);//进行当前日期月份加1
				
				}

				SimpleDateFormat sdfzz = new SimpleDateFormat("yyyy-MM");
				String accDate=sdfzz.format(d2.getTime());
				BigDecimal xse=new BigDecimal(0);
				


				if(report.getResultsType().equals("合同金额")){
					String sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
						if(from.equals("全部")) {
							sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								list.add(report);
							}
						}else{

							if(from.equals("本部门")) {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
							    		sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and department_id like'" + bmid + "%'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
							    		ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("合同金额");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											list.add(report);
										}
							    	}
							    }
							    sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and create_id ='" + username + "'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
							    ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
							}else if(from.equals("未设置")) {
							    sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and create_id ='" + username + "'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
							    ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
							}else {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
							    		sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and department_id like'" + bmid + "'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
							    		ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("合同金额");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											list.add(report);
										}
							    	}
							    }
							    sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and create_id ='" + username + "'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
							    ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
							}
						}
				}else if(report.getResultsType().equals("合同回款金额")){
					String sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
					if(from.equals("全部")) {
						sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
						ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						int i1=1;
						while (rs.next()) {
							report = new Report();
							report.setRanking(i1++);
							report.setColumn("合同回款金额");
							report.setAggregateAmount(rs.getBigDecimal(1));
							report.setDepartmentName(rs.getString(2));
							list.add(report);
						}
					}else{

						if(from.equals("本部门")) {

							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
						    		sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and department_id like'" + bmid + "%'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
						    		ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("合同回款金额");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										list.add(report);
									}
						    	}
						    }
						    sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and create_id ='" + username + "'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
						    ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同回款金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								list.add(report);
							}
					        for (int i11 = 0; i11 < list.size() - 1; i11++) {
					            for (int j = i11 + 1; j < list.size(); j++) {
					                if (list.get(i11).equals(list.get(j))) {
					                    list.remove(j);
					                }
					            }
					        }
						}else if(from.equals("未设置")) {
						    sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and create_id ='" + username + "'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
						    ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同回款金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								list.add(report);
							}
						}else {

							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
						    		sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and department_id like'" + bmid + "'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
						    		ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("合同回款金额");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										list.add(report);
									}
						    	}
						    }
						    sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and create_id ='" + username + "'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
						    ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同回款金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								list.add(report);
							}
					        for (int i11 = 0; i11 < list.size() - 1; i11++) {
					            for (int j = i11 + 1; j < list.size(); j++) {
					                if (list.get(i11).equals(list.get(j))) {
					                    list.remove(j);
					                }
					            }
					        }
						}
					}
			}else if(report.getResultsType().equals("合同数")){
					String sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY COUNT(*) desc";

						if(from.equals("全部")) {
							sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY COUNT(*) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同数");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								list.add(report);
							}
						}else{
							if(from.equals("本部门")) {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
							    		sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+" and department_id like'" + bmid + "%'  and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY COUNT(*) desc";
							    		ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("合同数");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											list.add(report);
										}
							    	}
							    }
							    sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+" and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY COUNT(*) desc";
							    ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
							}else if(from.equals("未设置")) {
							    sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+" and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY COUNT(*) desc";
							    ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
							}else {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
							    		sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+" and department_id like'" + bmid + "'  and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY COUNT(*) desc";
							    		ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("合同数");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											list.add(report);
										}
							    	}
							    }
							    sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+" and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY COUNT(*) desc";
							    ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
							}
						}
				}else if(report.getResultsType().equals("赢单商机数")){
					
					String sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY COUNT(*) desc";

						if(from.equals("全部")) {
							sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY COUNT(*) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("赢单商机数");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								list.add(report);
							}
						}else{
							if(from.equals("本部门")) {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and department_id like'" + bmid + "%'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY COUNT(*) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("赢单商机数");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											list.add(report);
										}
							    	}
							    }
								sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY COUNT(*) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
							}else if(from.equals("未设置")) {
								sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY COUNT(*) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
							}else {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and department_id like'" + bmid + "'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY COUNT(*) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("赢单商机数");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											list.add(report);
										}
							    	}
							    }
								sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY COUNT(*) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
							}
						}
				}else if(report.getResultsType().equals("赢单商机金额")){
					String sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
						if(from.equals("全部")) {
							sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";

							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("赢单商机金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								list.add(report);
							}
						}else{
							if(from.equals("本部门")) {
								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and department_id like'" + bmid + "%'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("赢单商机金额");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											list.add(report);
										}
							    	}
							    }
								sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
							}else if(from.equals("未设置")) {
								sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
							}else {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and department_id like'" + bmid + "'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("赢单商机金额");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											list.add(report);
										}
							    	}
							    }
								sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
							}
						}
					
				}
			
				
				

				
			} else {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
				String accDate=format.format(new Date());//当前月份


				if(report.getResultsType().equals("合同金额")){
					String sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
						if(from.equals("全部")) {
							sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								list.add(report);
							}
						}else{

							if(from.equals("本部门")) {
								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
							    		sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and department_id like'" + bmid + "%'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
							    		ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("合同金额");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											list.add(report);
										}
							    	}
							    }
							    sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and create_id ='" + username + "'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
							    ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
							}else if(from.equals("未设置")) {
							    sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and create_id ='" + username + "'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
							    ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
								
							}else {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
							    		sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and department_id like'" + bmid + "'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
							    		ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("合同金额");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											list.add(report);
										}
							    	}
							    }
							    sql = "SELECT SUM(total_contract_amount),department_name from crm_contract where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and create_id ='" + username + "'  GROUP BY department_name ORDER BY SUM(total_contract_amount) desc";
							    ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
							}
						}
				}else if(report.getResultsType().equals("合同回款金额")){
					String sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
					if(from.equals("全部")) {
						sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
						ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						int i1=1;
						while (rs.next()) {
							report = new Report();
							report.setRanking(i1++);
							report.setColumn("合同回款金额");
							report.setAggregateAmount(rs.getBigDecimal(1));
							report.setDepartmentName(rs.getString(2));
							list.add(report);
						}
					}else{

						if(from.equals("本部门")) {
							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
						    		sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and department_id like'" + bmid + "%'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
						    		ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("合同回款金额");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										list.add(report);
									}
						    	}
						    }
						    sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and create_id ='" + username + "'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
						    ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同回款金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								list.add(report);
							}
						}else if(from.equals("未设置")) {
						    sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and create_id ='" + username + "'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
						    ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同回款金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								list.add(report);
							}
							
						}else {

							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
						    		sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and department_id like'" + bmid + "'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
						    		ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("合同回款金额");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										list.add(report);
									}
						    	}
						    }
						    sql = "SELECT SUM(collection_amount),department_name from crm_collection where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' and create_id ='" + username + "'  GROUP BY department_name ORDER BY SUM(collection_amount) desc";
						    ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同回款金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								list.add(report);
							}
						}
					}
			}else if(report.getResultsType().equals("合同数")){
					String sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY COUNT(*) desc";

						if(from.equals("全部")) {
							sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY COUNT(*) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同数");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								list.add(report);
							}
						}else{
							if(from.equals("本部门")) {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
							    		sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+" and department_id like'" + bmid + "%'  and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY COUNT(*) desc";
							    		ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("合同数");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											list.add(report);
										}
							    	}
							    }
							    sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+" and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY COUNT(*) desc";
							    ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
							}else if(from.equals("未设置")) {
							    sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+" and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY COUNT(*) desc";
							    ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
							}else {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
							    		sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+" and department_id like'" + bmid + "'  and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY COUNT(*) desc";
							    		ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("合同数");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											list.add(report);
										}
							    	}
							    }
							    sql = "SELECT COUNT(*),department_name from crm_contract where 1=1  "+wSQL+" and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY COUNT(*) desc";
							    ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
							}
						}
				}else if(report.getResultsType().equals("赢单商机数")){
					
					String sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY COUNT(*) desc";

						if(from.equals("全部")) {
							sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY COUNT(*) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("赢单商机数");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								list.add(report);
							}
						}else{
							if(from.equals("本部门")) {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and department_id like'" + bmid + "%'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY COUNT(*) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("赢单商机数");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											list.add(report);
										}
							    	}
							    }
								sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY COUNT(*) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
							}else if(from.equals("未设置")) {
								sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY COUNT(*) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
							}else {
								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and department_id like'" + bmid + "'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY COUNT(*) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("赢单商机数");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											list.add(report);
										}
							    	}
							    }
								sql = "SELECT COUNT(*),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY COUNT(*) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
								
							}
						}
				}else if(report.getResultsType().equals("赢单商机金额")){
					String sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
						if(from.equals("全部")) {
							sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";

							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("赢单商机金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								list.add(report);
							}
						}else{
							if(from.equals("本部门")) {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and department_id like'" + bmid + "%'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("赢单商机金额");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											list.add(report);
										}
							    	}
							    }
								sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
							}else if(from.equals("未设置")) {
								sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
							}else {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单' and department_id like'" + bmid + "'  and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("赢单商机金额");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											list.add(report);
										}
							    	}
							    }
								sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' and create_id ='" + username + "' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
							}
						}
					
				}
			}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("List<Report> getItemsbmry(Report report)" + (t2 - t1) + " ms");
		} catch (SQLException | ParseException e) {
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		} finally {
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		}
		return list;
	}

	@Override
	public List<Report> getItemsNewBusiness(Report report) {
		long t1 = System.currentTimeMillis();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		PreparedStatement ps3 = null;
		ResultSet rs3 = null;
		PreparedStatement ps4 = null;
		ResultSet rs4 = null;
		List<Report> list = new ArrayList<Report>();
		String mechanismId=report.getMechanismId();
		String from=report.getFrom() == null ? "" : report.getFrom();
		String departmentIds=report.getDepartmentIds();
		String username=report.getUsername();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);

			String wSQL = "";

			if(report.getDepartmentId()!=null){
				if(report.getDepartmentId().equals("")){
					
				}else{
					if (report.getDepartmentId() != null || !report.getDepartmentId().equals("")) {
						wSQL += " and department_id='" + report.getDepartmentId() + "'";
					}
				}
			}
			
			if(report.getUserId()!=null){

				if(report.getUserId().equals("")){
					
				}else{
					if (report.getUserId() != null || !report.getUserId().equals("")) {
						wSQL += " and create_id='" + report.getUserId() + "'";
					}
				}
			}
			int i=0;
			if(report.getTimeLatitude()==null){
				report.setTimeLatitude("月");
			}
			if(report.getTimeLatitude().equals("日")){
		        Date dNow = new Date();   //当前时间  
		        Date dBefore = new Date();  
		        Calendar calendar = Calendar.getInstance(); //得到日历  
		        calendar.setTime(dNow);//把当前时间赋给日历  
		        calendar.add(Calendar.DATE, -30);  //设置为前3月  
		        dBefore = calendar.getTime();   //得到前3月的时间  
		        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式  
		        String defaultStartDate = sdf.format(dBefore);    //格式化前3月的时间  
		        String defaultEndDate = sdf.format(dNow); //格式化当前时间  
				Date d1 = new SimpleDateFormat("yyyy-MM-dd").parse(defaultStartDate);//定义起始日期
				Date d2 = new SimpleDateFormat("yyyy-MM-dd").parse(defaultEndDate);//定义结束日期 可以去当前月也可以手动写日期。
				Calendar dd = Calendar.getInstance();//定义日期实例
				dd.setTime(d1);//设置日期起始时间
				int iz=30;
				while (dd.getTime().before(d2)) {
					int ic=iz--;
			        Calendar calendar1 = Calendar.getInstance(); //得到日历  
			        calendar1.setTime(dNow);//把当前时间赋给日历  
			        calendar1.add(Calendar.DATE, -ic);  //设置为前3月  
			        dBefore = calendar1.getTime();   //得到前3月的时间  
			        String str = sdf.format(dBefore); //格式化当前时间  
			        System.out.println("当前时间==========="+str); 
					report = new Report();
					int xs=0;
					int kh=0;
					int sj=0;
					int ht=0;
						if(from.equals("全部")) {
							String sql = "SELECT count(*) from crm_clue where 1=1  "+wSQL+"  and create_date like ? and mechanism_id like ? and (state!='xsc' or state is null) ";
							ps = conn.prepareStatement(sql);
							ps.setString(1, str+ "%");
							ps.setString(2, mechanismId+ "%");
							rs = ps.executeQuery();
							if (rs.next()) {
								xs=rs.getInt(1);
							}
							String sql2 = "SELECT count(*) from crm_customer where 1=1  "+wSQL+"  and create_date like ? and mechanism_id like ? and (status!='gh' or status is null) and data_type='客户'";
							ps2 = conn.prepareStatement(sql2);
							ps2.setString(1, str+ "%");
							ps2.setString(2, mechanismId+ "%");
							rs2 = ps2.executeQuery();
							if (rs2.next()) {
								kh=rs2.getInt(1);
							}
							String sql3 = "SELECT count(*) from crm_business_opportunity where 1=1   "+wSQL+"  and create_date like ? and mechanism_id like ? ";
							ps3 = conn.prepareStatement(sql3);
							ps3.setString(1, str+ "%");
							ps3.setString(2, mechanismId+ "%");
							rs3 = ps3.executeQuery();
							if (rs3.next()) {
								sj=rs3.getInt(1);
							}
							String sql4 = "SELECT count(*) from crm_contract where 1=1  "+wSQL+"   and create_date like ? and mechanism_id like ? ";
							ps4 = conn.prepareStatement(sql4);
							ps4.setString(1, str+ "%");
							ps4.setString(2, mechanismId+ "%");
							rs4 = ps4.executeQuery();
							if (rs4.next()) {
								ht=rs4.getInt(1);
							}
							report.setColumn(str);
							report.setClueNumber(xs);
							report.setCustomerNumber(kh);
							report.setBusinessOpportunitiesNumber(sj);
							report.setContractsNumber(ht);
							list.add(report);
							dd.add(Calendar.DATE, 1);//进行当前日期月份加1
					        SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式  
						}else  {

							if(from.equals("本部门")) {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										String sql = "SELECT count(*) from crm_clue where 1=1  "+wSQL+"  and create_date like ? and mechanism_id like ? and department_id like ? and (state!='xsc' or state is null) ";
										ps = conn.prepareStatement(sql);
										ps.setString(1, str+ "%");
										ps.setString(2, mechanismId+ "%");
										ps.setString(3, bmid+ "%");
										rs = ps.executeQuery();
										if (rs.next()) {
											xs=rs.getInt(1);
										}
										String sql2 = "SELECT count(*) from crm_customer where 1=1  "+wSQL+"   and create_date like ? and mechanism_id like ? and department_id like ? and (status!='gh' or status is null) and data_type='客户'";
										ps2 = conn.prepareStatement(sql2);
										ps2.setString(1, str+ "%");
										ps2.setString(2, mechanismId+ "%");
										ps2.setString(3, bmid+ "%");
										rs2 = ps2.executeQuery();
										if (rs2.next()) {
											kh=rs2.getInt(1);
										}
										String sql3 = "SELECT count(*) from crm_business_opportunity where 1=1   "+wSQL+"  and create_date like ? and mechanism_id like ? and department_id like ? ";
										ps3 = conn.prepareStatement(sql3);
										ps3.setString(1, str+ "%");
										ps3.setString(2, mechanismId+ "%");
										ps3.setString(3, bmid+ "%");
										rs3 = ps3.executeQuery();
										if (rs3.next()) {
											sj=rs3.getInt(1);
										}
										String sql4 = "SELECT count(*) from crm_contract where 1=1  "+wSQL+"   and create_date like ? and mechanism_id like ? and department_id like ? ";
										ps4 = conn.prepareStatement(sql4);
										ps4.setString(1, str+ "%");
										ps4.setString(2, mechanismId+ "%");
										ps4.setString(3, bmid+ "%");
										rs4 = ps4.executeQuery();
										if (rs4.next()) {
											ht=rs4.getInt(1);
										}
										report.setColumn(str);
										report.setClueNumber(xs);
										report.setCustomerNumber(kh);
										report.setBusinessOpportunitiesNumber(sj);
										report.setContractsNumber(ht);
										list.add(report);
							    	}
							    }
								String sql = "SELECT count(*) from crm_clue where 1=1  "+wSQL+"  and create_date like ? and mechanism_id like ? and create_id = ? and (state!='xsc' or state is null) ";
								ps = conn.prepareStatement(sql);
								ps.setString(1, str+ "%");
								ps.setString(2, mechanismId+ "%");
								ps.setString(3, username);
								rs = ps.executeQuery();
								if (rs.next()) {
									xs=rs.getInt(1);
								}
								String sql2 = "SELECT count(*) from crm_customer where 1=1  "+wSQL+"  and create_date like ? and mechanism_id like ? and create_id = ? and (status!='gh' or status is null) and data_type='客户'";
								ps2 = conn.prepareStatement(sql2);
								ps2.setString(1, str+ "%");
								ps2.setString(2, mechanismId+ "%");
								ps2.setString(3, username);
								rs2 = ps2.executeQuery();
								if (rs2.next()) {
									kh=rs2.getInt(1);
								}
								String sql3 = "SELECT count(*) from crm_business_opportunity where 1=1   "+wSQL+"  and create_date like ? and mechanism_id like ? and create_id = ?";
								ps3 = conn.prepareStatement(sql3);
								ps3.setString(1, str+ "%");
								ps3.setString(2, mechanismId+ "%");
								ps3.setString(3, username);
								rs3 = ps3.executeQuery();
								if (rs3.next()) {
									sj=rs3.getInt(1);
								}
								String sql4 = "SELECT count(*) from crm_contract where 1=1  "+wSQL+"   and create_date like ? and mechanism_id like ? and create_id = ?";
								ps4 = conn.prepareStatement(sql4);
								ps4.setString(1, str+ "%");
								ps4.setString(2, mechanismId+ "%");
								ps4.setString(3, username);
								rs4 = ps4.executeQuery();
								if (rs4.next()) {
									ht=rs4.getInt(1);
								}
								report.setColumn(str);
								report.setClueNumber(xs);
								report.setCustomerNumber(kh);
								report.setBusinessOpportunitiesNumber(sj);
								report.setContractsNumber(ht);
								list.add(report);
								dd.add(Calendar.DATE, 1);//进行当前日期月份加1
						        SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式  
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
							}else if(from.equals("未设置")) {
								String sql = "SELECT count(*) from crm_clue where 1=1  "+wSQL+"  and create_date like ? and mechanism_id like ? and create_id = ? and (state!='xsc' or state is null) ";
								ps = conn.prepareStatement(sql);
								ps.setString(1, str+ "%");
								ps.setString(2, mechanismId+ "%");
								ps.setString(3, username);
								rs = ps.executeQuery();
								if (rs.next()) {
									xs=rs.getInt(1);
								}
								String sql2 = "SELECT count(*) from crm_customer where 1=1  "+wSQL+"  and create_date like ? and mechanism_id like ? and create_id = ? and (status!='gh' or status is null) and data_type='客户'";
								ps2 = conn.prepareStatement(sql2);
								ps2.setString(1, str+ "%");
								ps2.setString(2, mechanismId+ "%");
								ps2.setString(3, username);
								rs2 = ps2.executeQuery();
								if (rs2.next()) {
									kh=rs2.getInt(1);
								}
								String sql3 = "SELECT count(*) from crm_business_opportunity where 1=1   "+wSQL+"  and create_date like ? and mechanism_id like ? and create_id = ?";
								ps3 = conn.prepareStatement(sql3);
								ps3.setString(1, str+ "%");
								ps3.setString(2, mechanismId+ "%");
								ps3.setString(3, username);
								rs3 = ps3.executeQuery();
								if (rs3.next()) {
									sj=rs3.getInt(1);
								}
								String sql4 = "SELECT count(*) from crm_contract where 1=1  "+wSQL+"   and create_date like ? and mechanism_id like ? and create_id = ?";
								ps4 = conn.prepareStatement(sql4);
								ps4.setString(1, str+ "%");
								ps4.setString(2, mechanismId+ "%");
								ps4.setString(3, username);
								rs4 = ps4.executeQuery();
								if (rs4.next()) {
									ht=rs4.getInt(1);
								}
								report.setColumn(str);
								report.setClueNumber(xs);
								report.setCustomerNumber(kh);
								report.setBusinessOpportunitiesNumber(sj);
								report.setContractsNumber(ht);
								list.add(report);
								dd.add(Calendar.DATE, 1);//进行当前日期月份加1
						        SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式  
							}else {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										String sql = "SELECT count(*) from crm_clue where 1=1  "+wSQL+"  and create_date like ? and mechanism_id like ? and department_id like ? and (state!='xsc' or state is null) ";
										ps = conn.prepareStatement(sql);
										ps.setString(1, str+ "%");
										ps.setString(2, mechanismId+ "%");
										ps.setString(3, bmid);
										rs = ps.executeQuery();
										if (rs.next()) {
											xs=rs.getInt(1);
										}
										String sql2 = "SELECT count(*) from crm_customer where 1=1  "+wSQL+"   and create_date like ? and mechanism_id like ? and department_id like ? and (status!='gh' or status is null) and data_type='客户'";
										ps2 = conn.prepareStatement(sql2);
										ps2.setString(1, str+ "%");
										ps2.setString(2, mechanismId+ "%");
										ps2.setString(3, bmid);
										rs2 = ps2.executeQuery();
										if (rs2.next()) {
											kh=rs2.getInt(1);
										}
										String sql3 = "SELECT count(*) from crm_business_opportunity where 1=1   "+wSQL+"  and create_date like ? and mechanism_id like ? and department_id like ? ";
										ps3 = conn.prepareStatement(sql3);
										ps3.setString(1, str+ "%");
										ps3.setString(2, mechanismId+ "%");
										ps3.setString(3, bmid);
										rs3 = ps3.executeQuery();
										if (rs3.next()) {
											sj=rs3.getInt(1);
										}
										String sql4 = "SELECT count(*) from crm_contract where 1=1  "+wSQL+"   and create_date like ? and mechanism_id like ? and department_id like ? ";
										ps4 = conn.prepareStatement(sql4);
										ps4.setString(1, str+ "%");
										ps4.setString(2, mechanismId+ "%");
										ps4.setString(3, bmid);
										rs4 = ps4.executeQuery();
										if (rs4.next()) {
											ht=rs4.getInt(1);
										}
										report.setColumn(str);
										report.setClueNumber(xs);
										report.setCustomerNumber(kh);
										report.setBusinessOpportunitiesNumber(sj);
										report.setContractsNumber(ht);
										list.add(report);
							    	}
							    }
								String sql = "SELECT count(*) from crm_clue where 1=1  "+wSQL+"  and create_date like ? and mechanism_id like ? and create_id = ? and (state!='xsc' or state is null) ";
								ps = conn.prepareStatement(sql);
								ps.setString(1, str+ "%");
								ps.setString(2, mechanismId+ "%");
								ps.setString(3, username);
								rs = ps.executeQuery();
								if (rs.next()) {
									xs=rs.getInt(1);
								}
								String sql2 = "SELECT count(*) from crm_customer where 1=1  "+wSQL+"  and create_date like ? and mechanism_id like ? and create_id = ? and (status!='gh' or status is null) and data_type='客户'";
								ps2 = conn.prepareStatement(sql2);
								ps2.setString(1, str+ "%");
								ps2.setString(2, mechanismId+ "%");
								ps2.setString(3, username);
								rs2 = ps2.executeQuery();
								if (rs2.next()) {
									kh=rs2.getInt(1);
								}
								String sql3 = "SELECT count(*) from crm_business_opportunity where 1=1   "+wSQL+"  and create_date like ? and mechanism_id like ? and create_id = ?";
								ps3 = conn.prepareStatement(sql3);
								ps3.setString(1, str+ "%");
								ps3.setString(2, mechanismId+ "%");
								ps3.setString(3, username);
								rs3 = ps3.executeQuery();
								if (rs3.next()) {
									sj=rs3.getInt(1);
								}
								String sql4 = "SELECT count(*) from crm_contract where 1=1  "+wSQL+"   and create_date like ? and mechanism_id like ? and create_id = ?";
								ps4 = conn.prepareStatement(sql4);
								ps4.setString(1, str+ "%");
								ps4.setString(2, mechanismId+ "%");
								ps4.setString(3, username);
								rs4 = ps4.executeQuery();
								if (rs4.next()) {
									ht=rs4.getInt(1);
								}
								report.setColumn(str);
								report.setClueNumber(xs);
								report.setCustomerNumber(kh);
								report.setBusinessOpportunitiesNumber(sj);
								report.setContractsNumber(ht);
								list.add(report);
								dd.add(Calendar.DATE, 1);//进行当前日期月份加1
						        SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式  
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
							}
						}
				}

				SimpleDateFormat sdfzz = new SimpleDateFormat("yyyy-MM-dd");
				String str=sdfzz.format(d2.getTime());
				report = new Report();
				int xs=0;
				int kh=0;
				int sj=0;
				int ht=0;
				if(from.equals("全部")) {
					String sql = "SELECT count(*) from crm_clue where 1=1  "+wSQL+"  and create_date like ? and mechanism_id like ? and (state!='xsc' or state is null) ";
					ps = conn.prepareStatement(sql);
					ps.setString(1, str+ "%");
					ps.setString(2, mechanismId+ "%");
					rs = ps.executeQuery();
					if (rs.next()) {
						xs=rs.getInt(1);
					}
					String sql2 = "SELECT count(*) from crm_customer where 1=1  "+wSQL+"  and create_date like ? and mechanism_id like ? and (status!='gh' or status is null) and data_type='客户'";
					ps2 = conn.prepareStatement(sql2);
					ps2.setString(1, str+ "%");
					ps2.setString(2, mechanismId+ "%");
					rs2 = ps2.executeQuery();
					if (rs2.next()) {
						kh=rs2.getInt(1);
					}
					String sql3 = "SELECT count(*) from crm_business_opportunity where 1=1   "+wSQL+" and create_date like ? and mechanism_id like ? ";
					ps3 = conn.prepareStatement(sql3);
					ps3.setString(1, str+ "%");
					ps3.setString(2, mechanismId+ "%");
					rs3 = ps3.executeQuery();
					if (rs3.next()) {
						sj=rs3.getInt(1);
					}
					String sql4 = "SELECT count(*) from crm_contract where 1=1  "+wSQL+"  and create_date like ? and mechanism_id like ?";
					ps4 = conn.prepareStatement(sql4);
					ps4.setString(1, str+ "%");
					ps4.setString(2, mechanismId+ "%");
					rs4 = ps4.executeQuery();
					if (rs4.next()) {
						ht=rs4.getInt(1);
					}
					report.setColumn(str);
					report.setClueNumber(xs);
					report.setCustomerNumber(kh);
					report.setBusinessOpportunitiesNumber(sj);
					report.setContractsNumber(ht);
					list.add(report);
					dd.add(Calendar.DATE, 1);//进行当前日期月份加1
			        SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式  
				}else  {

					if(from.equals("本部门")) {

						String[] xh=departmentIds.split(",");
					    for(String bmid : xh){
					    	if(!bmid.equals("")){
								String sql = "SELECT count(*) from crm_clue where 1=1  "+wSQL+"  and create_date like ? and mechanism_id like ? and department_id like ? and (state!='xsc' or state is null) ";
								ps = conn.prepareStatement(sql);
								ps.setString(1, str+ "%");
								ps.setString(2, mechanismId+ "%");
								ps.setString(3, bmid+ "%");
								rs = ps.executeQuery();
								if (rs.next()) {
									xs=rs.getInt(1);
								}
								String sql2 = "SELECT count(*) from crm_customer where 1=1  "+wSQL+"  and create_date like ? and mechanism_id like ? and department_id like ? and (status!='gh' or status is null) and data_type='客户'";
								ps2 = conn.prepareStatement(sql2);
								ps2.setString(1, str+ "%");
								ps2.setString(2, mechanismId+ "%");
								ps2.setString(3, bmid+ "%");
								rs2 = ps2.executeQuery();
								if (rs2.next()) {
									kh=rs2.getInt(1);
								}
								String sql3 = "SELECT count(*) from crm_business_opportunity where 1=1   "+wSQL+"  and create_date like ? and mechanism_id like ? and department_id like ?";
								ps3 = conn.prepareStatement(sql3);
								ps3.setString(1, str+ "%");
								ps3.setString(2, mechanismId+ "%");
								ps3.setString(3, bmid+ "%");
								rs3 = ps3.executeQuery();
								if (rs3.next()) {
									sj=rs3.getInt(1);
								}
								String sql4 = "SELECT count(*) from crm_contract where 1=1  "+wSQL+"  and create_date like ? and mechanism_id like ? and department_id like ?";
								ps4 = conn.prepareStatement(sql4);
								ps4.setString(1, str+ "%");
								ps4.setString(2, mechanismId+ "%");
								ps4.setString(3, bmid+ "%");
								rs4 = ps4.executeQuery();
								if (rs4.next()) {
									ht=rs4.getInt(1);
								}
								report.setColumn(str);
								report.setClueNumber(xs);
								report.setCustomerNumber(kh);
								report.setBusinessOpportunitiesNumber(sj);
								report.setContractsNumber(ht);
								list.add(report);
					    	}
					    }
						String sql = "SELECT count(*) from crm_clue where 1=1  "+wSQL+"  and create_date like ? and mechanism_id like ? and create_id = ? and (state!='xsc' or state is null) ";
						ps = conn.prepareStatement(sql);
						ps.setString(1, str+ "%");
						ps.setString(2, mechanismId+ "%");
						ps.setString(3, username);
						rs = ps.executeQuery();
						if (rs.next()) {
							xs=rs.getInt(1);
						}
						String sql2 = "SELECT count(*) from crm_customer where 1=1  "+wSQL+"  and create_date like ? and mechanism_id like ? and create_id = ? and (status!='gh' or status is null) and data_type='客户'";
						ps2 = conn.prepareStatement(sql2);
						ps2.setString(1, str+ "%");
						ps2.setString(2, mechanismId+ "%");
						ps2.setString(3, username);
						rs2 = ps2.executeQuery();
						if (rs2.next()) {
							kh=rs2.getInt(1);
						}
						String sql3 = "SELECT count(*) from crm_business_opportunity where 1=1   "+wSQL+"  and create_date like ? and mechanism_id like ? and create_id = ?";
						ps3 = conn.prepareStatement(sql3);
						ps3.setString(1, str+ "%");
						ps3.setString(2, mechanismId+ "%");
						ps3.setString(3, username);
						rs3 = ps3.executeQuery();
						if (rs3.next()) {
							sj=rs3.getInt(1);
						}
						String sql4 = "SELECT count(*) from crm_contract where 1=1  "+wSQL+"  and create_date like ? and mechanism_id like ? and create_id = ?";
						ps4 = conn.prepareStatement(sql4);
						ps4.setString(1, str+ "%");
						ps4.setString(2, mechanismId+ "%");
						ps4.setString(3, username);
						rs4 = ps4.executeQuery();
						if (rs4.next()) {
							ht=rs4.getInt(1);
						}
						report.setColumn(str);
						report.setClueNumber(xs);
						report.setCustomerNumber(kh);
						report.setBusinessOpportunitiesNumber(sj);
						report.setContractsNumber(ht);
						list.add(report);
				        for (int i11 = 0; i11 < list.size() - 1; i11++) {
				            for (int j = i11 + 1; j < list.size(); j++) {
				                if (list.get(i11).equals(list.get(j))) {
				                    list.remove(j);
				                }
				            }
				        }
					}else if(from.equals("未设置")) {

						String sql = "SELECT count(*) from crm_clue where 1=1  "+wSQL+"  and create_date like ? and mechanism_id like ? and create_id = ? and (state!='xsc' or state is null) ";
						ps = conn.prepareStatement(sql);
						ps.setString(1, str+ "%");
						ps.setString(2, mechanismId+ "%");
						ps.setString(3, username);
						rs = ps.executeQuery();
						if (rs.next()) {
							xs=rs.getInt(1);
						}
						String sql2 = "SELECT count(*) from crm_customer where 1=1  "+wSQL+"  and create_date like ? and mechanism_id like ? and create_id = ? and (status!='gh' or status is null) and data_type='客户'";
						ps2 = conn.prepareStatement(sql2);
						ps2.setString(1, str+ "%");
						ps2.setString(2, mechanismId+ "%");
						ps2.setString(3, username);
						rs2 = ps2.executeQuery();
						if (rs2.next()) {
							kh=rs2.getInt(1);
						}
						String sql3 = "SELECT count(*) from crm_business_opportunity where 1=1   "+wSQL+"  and create_date like ? and mechanism_id like ? and create_id = ?";
						ps3 = conn.prepareStatement(sql3);
						ps3.setString(1, str+ "%");
						ps3.setString(2, mechanismId+ "%");
						ps3.setString(3, username);
						rs3 = ps3.executeQuery();
						if (rs3.next()) {
							sj=rs3.getInt(1);
						}
						String sql4 = "SELECT count(*) from crm_contract where 1=1  "+wSQL+"  and create_date like ? and mechanism_id like ? and create_id = ?";
						ps4 = conn.prepareStatement(sql4);
						ps4.setString(1, str+ "%");
						ps4.setString(2, mechanismId+ "%");
						ps4.setString(3, username);
						rs4 = ps4.executeQuery();
						if (rs4.next()) {
							ht=rs4.getInt(1);
						}
						report.setColumn(str);
						report.setClueNumber(xs);
						report.setCustomerNumber(kh);
						report.setBusinessOpportunitiesNumber(sj);
						report.setContractsNumber(ht);
						list.add(report);
				        for (int i11 = 0; i11 < list.size() - 1; i11++) {
				            for (int j = i11 + 1; j < list.size(); j++) {
				                if (list.get(i11).equals(list.get(j))) {
				                    list.remove(j);
				                }
				            }
				        }
					}else {


						String[] xh=departmentIds.split(",");
					    for(String bmid : xh){
					    	if(!bmid.equals("")){
								String sql = "SELECT count(*) from crm_clue where 1=1  "+wSQL+"  and create_date like ? and mechanism_id like ? and department_id like ? and (state!='xsc' or state is null) ";
								ps = conn.prepareStatement(sql);
								ps.setString(1, str+ "%");
								ps.setString(2, mechanismId+ "%");
								ps.setString(3, bmid);
								rs = ps.executeQuery();
								if (rs.next()) {
									xs=rs.getInt(1);
								}
								String sql2 = "SELECT count(*) from crm_customer where 1=1  "+wSQL+"  and create_date like ? and mechanism_id like ? and department_id like ? and (status!='gh' or status is null) and data_type='客户'";
								ps2 = conn.prepareStatement(sql2);
								ps2.setString(1, str+ "%");
								ps2.setString(2, mechanismId+ "%");
								ps2.setString(3, bmid);
								rs2 = ps2.executeQuery();
								if (rs2.next()) {
									kh=rs2.getInt(1);
								}
								String sql3 = "SELECT count(*) from crm_business_opportunity where 1=1   "+wSQL+"  and create_date like ? and mechanism_id like ? and department_id like ?";
								ps3 = conn.prepareStatement(sql3);
								ps3.setString(1, str+ "%");
								ps3.setString(2, mechanismId+ "%");
								ps3.setString(3, bmid);
								rs3 = ps3.executeQuery();
								if (rs3.next()) {
									sj=rs3.getInt(1);
								}
								String sql4 = "SELECT count(*) from crm_contract where 1=1  "+wSQL+"  and create_date like ? and mechanism_id like ? and department_id like ?";
								ps4 = conn.prepareStatement(sql4);
								ps4.setString(1, str+ "%");
								ps4.setString(2, mechanismId+ "%");
								ps4.setString(3, bmid);
								rs4 = ps4.executeQuery();
								if (rs4.next()) {
									ht=rs4.getInt(1);
								}
								report.setColumn(str);
								report.setClueNumber(xs);
								report.setCustomerNumber(kh);
								report.setBusinessOpportunitiesNumber(sj);
								report.setContractsNumber(ht);
								list.add(report);
					    	}
					    }
						String sql = "SELECT count(*) from crm_clue where 1=1  "+wSQL+"  and create_date like ? and mechanism_id like ? and create_id = ? and (state!='xsc' or state is null) ";
						ps = conn.prepareStatement(sql);
						ps.setString(1, str+ "%");
						ps.setString(2, mechanismId+ "%");
						ps.setString(3, username);
						rs = ps.executeQuery();
						if (rs.next()) {
							xs=rs.getInt(1);
						}
						String sql2 = "SELECT count(*) from crm_customer where 1=1  "+wSQL+"  and create_date like ? and mechanism_id like ? and create_id = ? and (status!='gh' or status is null) and data_type='客户'";
						ps2 = conn.prepareStatement(sql2);
						ps2.setString(1, str+ "%");
						ps2.setString(2, mechanismId+ "%");
						ps2.setString(3, username);
						rs2 = ps2.executeQuery();
						if (rs2.next()) {
							kh=rs2.getInt(1);
						}
						String sql3 = "SELECT count(*) from crm_business_opportunity where 1=1   "+wSQL+"  and create_date like ? and mechanism_id like ? and create_id = ?";
						ps3 = conn.prepareStatement(sql3);
						ps3.setString(1, str+ "%");
						ps3.setString(2, mechanismId+ "%");
						ps3.setString(3, username);
						rs3 = ps3.executeQuery();
						if (rs3.next()) {
							sj=rs3.getInt(1);
						}
						String sql4 = "SELECT count(*) from crm_contract where 1=1  "+wSQL+"  and create_date like ? and mechanism_id like ? and create_id = ?";
						ps4 = conn.prepareStatement(sql4);
						ps4.setString(1, str+ "%");
						ps4.setString(2, mechanismId+ "%");
						ps4.setString(3, username);
						rs4 = ps4.executeQuery();
						if (rs4.next()) {
							ht=rs4.getInt(1);
						}
						report.setColumn(str);
						report.setClueNumber(xs);
						report.setCustomerNumber(kh);
						report.setBusinessOpportunitiesNumber(sj);
						report.setContractsNumber(ht);
						list.add(report);
				        for (int i11 = 0; i11 < list.size() - 1; i11++) {
				            for (int j = i11 + 1; j < list.size(); j++) {
				                if (list.get(i11).equals(list.get(j))) {
				                    list.remove(j);
				                }
				            }
				        }
					}
				}

				
			}else if(report.getTimeLatitude().equals("周")){
				
			}else if(report.getTimeLatitude().equals("年")){

		        Date dNow = new Date();   //当前时间  
		        Date dBefore = new Date();  
		        Calendar calendar = Calendar.getInstance(); //得到日历  
		        calendar.setTime(dNow);//把当前时间赋给日历  
		        calendar.add(Calendar.YEAR, -2);  //设置为前3月  
		        dBefore = calendar.getTime();   //得到前3月的时间  
		        SimpleDateFormat sdf=new SimpleDateFormat("yyyy"); //设置时间格式  
		        String defaultStartDate = sdf.format(dBefore);    //格式化前3月的时间  
		        String defaultEndDate = sdf.format(dNow); //格式化当前时间  
				Date d1 = new SimpleDateFormat("yyyy").parse(defaultStartDate);//定义起始日期
				Date d2 = new SimpleDateFormat("yyyy").parse(defaultEndDate);//定义结束日期 可以去当前月也可以手动写日期。
				Calendar dd = Calendar.getInstance();//定义日期实例
				dd.setTime(d1);//设置日期起始时间
				int iz=2;
				while (dd.getTime().before(d2)) {
					int ic=iz--;
			        Calendar calendar1 = Calendar.getInstance(); //得到日历  
			        calendar1.setTime(dNow);//把当前时间赋给日历  
			        calendar1.add(Calendar.YEAR, -ic);  //设置为前3月  
			        dBefore = calendar1.getTime();   //得到前3月的时间  
			        String str = sdf.format(dBefore); //格式化当前时间  
					report = new Report();
					int xs=0;
					int kh=0;
					int sj=0;
					int ht=0;
					if(from.equals("全部")) {
						String sql = "SELECT count(*) from crm_clue where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and (state!='xsc' or state is null) ";
						ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						if (rs.next()) {
							xs=rs.getInt(1);
						}
						String sql2 = "SELECT count(*) from crm_customer where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and (status!='gh' or status is null) and data_type='客户'";
						ps2 = conn.prepareStatement(sql2);
						rs2 = ps2.executeQuery();
						if (rs2.next()) {
							kh=rs2.getInt(1);
						}
						String sql3 = "SELECT count(*) from crm_business_opportunity where 1=1   "+wSQL+" and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' ";
						ps3 = conn.prepareStatement(sql3);
						rs3 = ps3.executeQuery();
						if (rs3.next()) {
							sj=rs3.getInt(1);
						}
						String sql4 = "SELECT count(*) from crm_contract where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' ";
						ps4 = conn.prepareStatement(sql4);
						rs4 = ps4.executeQuery();
						if (rs4.next()) {
							ht=rs4.getInt(1);
						}
						report.setColumn(str);
						report.setClueNumber(xs);
						report.setCustomerNumber(kh);
						report.setBusinessOpportunitiesNumber(sj);
						report.setContractsNumber(ht);
						list.add(report);
						dd.add(Calendar.YEAR, 1);//进行当前日期月份加1
				        SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式  
					}else  {

						if(from.equals("本部门")) {

							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
									String sql = "SELECT count(*) from crm_clue where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "%' and (state!='xsc' or state is null) ";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									if (rs.next()) {
										xs=rs.getInt(1);
									}
									String sql2 = "SELECT count(*) from crm_customer where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "%' and (status!='gh' or status is null) and data_type='客户'";
									ps2 = conn.prepareStatement(sql2);
									rs2 = ps2.executeQuery();
									if (rs2.next()) {
										kh=rs2.getInt(1);
									}
									String sql3 = "SELECT count(*) from crm_business_opportunity where 1=1   "+wSQL+" and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "%'";
									ps3 = conn.prepareStatement(sql3);
									rs3 = ps3.executeQuery();
									if (rs3.next()) {
										sj=rs3.getInt(1);
									}
									String sql4 = "SELECT count(*) from crm_contract where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "%'";
									ps4 = conn.prepareStatement(sql4);
									rs4 = ps4.executeQuery();
									if (rs4.next()) {
										ht=rs4.getInt(1);
									}
									report.setColumn(str);
									report.setClueNumber(xs);
									report.setCustomerNumber(kh);
									report.setBusinessOpportunitiesNumber(sj);
									report.setContractsNumber(ht);
									list.add(report);
						    	}
						    }
							String sql = "SELECT count(*) from crm_clue where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and (state!='xsc' or state is null) ";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							if (rs.next()) {
								xs=rs.getInt(1);
							}
							String sql2 = "SELECT count(*) from crm_customer where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and (status!='gh' or status is null) and data_type='客户'";
							ps2 = conn.prepareStatement(sql2);
							rs2 = ps2.executeQuery();
							if (rs2.next()) {
								kh=rs2.getInt(1);
							}
							String sql3 = "SELECT count(*) from crm_business_opportunity where 1=1   "+wSQL+" and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "'";
							ps3 = conn.prepareStatement(sql3);
							rs3 = ps3.executeQuery();
							if (rs3.next()) {
								sj=rs3.getInt(1);
							}
							String sql4 = "SELECT count(*) from crm_contract where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "'";
							ps4 = conn.prepareStatement(sql4);
							rs4 = ps4.executeQuery();
							if (rs4.next()) {
								ht=rs4.getInt(1);
							}
							report.setColumn(str);
							report.setClueNumber(xs);
							report.setCustomerNumber(kh);
							report.setBusinessOpportunitiesNumber(sj);
							report.setContractsNumber(ht);
							list.add(report);
							dd.add(Calendar.YEAR, 1);//进行当前日期月份加1
					        SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式  
				        for (int i11 = 0; i11 < list.size() - 1; i11++) {
				            for (int j = i11 + 1; j < list.size(); j++) {
				                if (list.get(i11).equals(list.get(j))) {
				                    list.remove(j);
				                }
				            }
				        }  
						}else if(from.equals("未设置")) {
							String sql = "SELECT count(*) from crm_clue where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and (state!='xsc' or state is null) ";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							if (rs.next()) {
								xs=rs.getInt(1);
							}
							String sql2 = "SELECT count(*) from crm_customer where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and (status!='gh' or status is null) and data_type='客户'";
							ps2 = conn.prepareStatement(sql2);
							rs2 = ps2.executeQuery();
							if (rs2.next()) {
								kh=rs2.getInt(1);
							}
							String sql3 = "SELECT count(*) from crm_business_opportunity where 1=1   "+wSQL+" and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "'";
							ps3 = conn.prepareStatement(sql3);
							rs3 = ps3.executeQuery();
							if (rs3.next()) {
								sj=rs3.getInt(1);
							}
							String sql4 = "SELECT count(*) from crm_contract where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "'";
							ps4 = conn.prepareStatement(sql4);
							rs4 = ps4.executeQuery();
							if (rs4.next()) {
								ht=rs4.getInt(1);
							}
							report.setColumn(str);
							report.setClueNumber(xs);
							report.setCustomerNumber(kh);
							report.setBusinessOpportunitiesNumber(sj);
							report.setContractsNumber(ht);
							list.add(report);
							dd.add(Calendar.YEAR, 1);//进行当前日期月份加1
					        SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式  
						}else {

							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
									String sql = "SELECT count(*) from crm_clue where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "' and (state!='xsc' or state is null) ";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									if (rs.next()) {
										xs=rs.getInt(1);
									}
									String sql2 = "SELECT count(*) from crm_customer where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "' and (status!='gh' or status is null) and data_type='客户'";
									ps2 = conn.prepareStatement(sql2);
									rs2 = ps2.executeQuery();
									if (rs2.next()) {
										kh=rs2.getInt(1);
									}
									String sql3 = "SELECT count(*) from crm_business_opportunity where 1=1   "+wSQL+" and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "'";
									ps3 = conn.prepareStatement(sql3);
									rs3 = ps3.executeQuery();
									if (rs3.next()) {
										sj=rs3.getInt(1);
									}
									String sql4 = "SELECT count(*) from crm_contract where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "'";
									ps4 = conn.prepareStatement(sql4);
									rs4 = ps4.executeQuery();
									if (rs4.next()) {
										ht=rs4.getInt(1);
									}
									report.setColumn(str);
									report.setClueNumber(xs);
									report.setCustomerNumber(kh);
									report.setBusinessOpportunitiesNumber(sj);
									report.setContractsNumber(ht);
									list.add(report);
						    	}
						    }
							String sql = "SELECT count(*) from crm_clue where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and (state!='xsc' or state is null) ";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							if (rs.next()) {
								xs=rs.getInt(1);
							}
							String sql2 = "SELECT count(*) from crm_customer where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and (status!='gh' or status is null) and data_type='客户'";
							ps2 = conn.prepareStatement(sql2);
							rs2 = ps2.executeQuery();
							if (rs2.next()) {
								kh=rs2.getInt(1);
							}
							String sql3 = "SELECT count(*) from crm_business_opportunity where 1=1   "+wSQL+" and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "'";
							ps3 = conn.prepareStatement(sql3);
							rs3 = ps3.executeQuery();
							if (rs3.next()) {
								sj=rs3.getInt(1);
							}
							String sql4 = "SELECT count(*) from crm_contract where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "'";
							ps4 = conn.prepareStatement(sql4);
							rs4 = ps4.executeQuery();
							if (rs4.next()) {
								ht=rs4.getInt(1);
							}
							report.setColumn(str);
							report.setClueNumber(xs);
							report.setCustomerNumber(kh);
							report.setBusinessOpportunitiesNumber(sj);
							report.setContractsNumber(ht);
							list.add(report);
							dd.add(Calendar.YEAR, 1);//进行当前日期月份加1
					        SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式  
				        for (int i11 = 0; i11 < list.size() - 1; i11++) {
				            for (int j = i11 + 1; j < list.size(); j++) {
				                if (list.get(i11).equals(list.get(j))) {
				                    list.remove(j);
				                }
				            }
				        }  
						}
					}
				}

				SimpleDateFormat sdfzz = new SimpleDateFormat("yyyy");
				String str=sdfzz.format(d2.getTime());
				report = new Report();
				int xs=0;
				int kh=0;
				int sj=0;
				int ht=0;
				if(from.equals("全部")) {
					String sql = "SELECT count(*) from crm_clue where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and (state!='xsc' or state is null) ";
					ps = conn.prepareStatement(sql);
					rs = ps.executeQuery();
					if (rs.next()) {
						xs=rs.getInt(1);
					}
					String sql2 = "SELECT count(*) from crm_customer where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and (status!='gh' or status is null) and data_type='客户'";
					ps2 = conn.prepareStatement(sql2);
					rs2 = ps2.executeQuery();
					if (rs2.next()) {
						kh=rs2.getInt(1);
					}
					String sql3 = "SELECT count(*) from crm_business_opportunity where 1=1   "+wSQL+" and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' ";
					ps3 = conn.prepareStatement(sql3);
					rs3 = ps3.executeQuery();
					if (rs3.next()) {
						sj=rs3.getInt(1);
					}
					String sql4 = "SELECT count(*) from crm_contract where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' ";
					ps4 = conn.prepareStatement(sql4);
					rs4 = ps4.executeQuery();
					if (rs4.next()) {
						ht=rs4.getInt(1);
					}
					report.setColumn(str);
					report.setClueNumber(xs);
					report.setCustomerNumber(kh);
					report.setBusinessOpportunitiesNumber(sj);
					report.setContractsNumber(ht);
					list.add(report);
					dd.add(Calendar.YEAR, 1);//进行当前日期月份加1
			        SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式  
				}else  {
					if(from.equals("本部门")) {

						String[] xh=departmentIds.split(",");
					    for(String bmid : xh){
					    	if(!bmid.equals("")){
								String sql = "SELECT count(*) from crm_clue where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "%' and (state!='xsc' or state is null) ";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								if (rs.next()) {
									xs=rs.getInt(1);
								}
								String sql2 = "SELECT count(*) from crm_customer where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "%' and (status!='gh' or status is null) and data_type='客户'";
								ps2 = conn.prepareStatement(sql2);
								rs2 = ps2.executeQuery();
								if (rs2.next()) {
									kh=rs2.getInt(1);
								}
								String sql3 = "SELECT count(*) from crm_business_opportunity where 1=1   "+wSQL+" and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "%'";
								ps3 = conn.prepareStatement(sql3);
								rs3 = ps3.executeQuery();
								if (rs3.next()) {
									sj=rs3.getInt(1);
								}
								String sql4 = "SELECT count(*) from crm_contract where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "%'";
								ps4 = conn.prepareStatement(sql4);
								rs4 = ps4.executeQuery();
								if (rs4.next()) {
									ht=rs4.getInt(1);
								}
								report.setColumn(str);
								report.setClueNumber(xs);
								report.setCustomerNumber(kh);
								report.setBusinessOpportunitiesNumber(sj);
								report.setContractsNumber(ht);
								list.add(report);
					    	}
					    }
						String sql = "SELECT count(*) from crm_clue where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and (state!='xsc' or state is null) ";
						ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						if (rs.next()) {
							xs=rs.getInt(1);
						}
						String sql2 = "SELECT count(*) from crm_customer where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and (status!='gh' or status is null) and data_type='客户'";
						ps2 = conn.prepareStatement(sql2);
						rs2 = ps2.executeQuery();
						if (rs2.next()) {
							kh=rs2.getInt(1);
						}
						String sql3 = "SELECT count(*) from crm_business_opportunity where 1=1   "+wSQL+" and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "'";
						ps3 = conn.prepareStatement(sql3);
						rs3 = ps3.executeQuery();
						if (rs3.next()) {
							sj=rs3.getInt(1);
						}
						String sql4 = "SELECT count(*) from crm_contract where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "'";
						ps4 = conn.prepareStatement(sql4);
						rs4 = ps4.executeQuery();
						if (rs4.next()) {
							ht=rs4.getInt(1);
						}
						report.setColumn(str);
						report.setClueNumber(xs);
						report.setCustomerNumber(kh);
						report.setBusinessOpportunitiesNumber(sj);
						report.setContractsNumber(ht);
						list.add(report);
			        for (int i11 = 0; i11 < list.size() - 1; i11++) {
			            for (int j = i11 + 1; j < list.size(); j++) {
			                if (list.get(i11).equals(list.get(j))) {
			                    list.remove(j);
			                }
			            }
			        }
					}else if(from.equals("未设置")) {
						String sql = "SELECT count(*) from crm_clue where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and (state!='xsc' or state is null) ";
						ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						if (rs.next()) {
							xs=rs.getInt(1);
						}
						String sql2 = "SELECT count(*) from crm_customer where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and (status!='gh' or status is null) and data_type='客户'";
						ps2 = conn.prepareStatement(sql2);
						rs2 = ps2.executeQuery();
						if (rs2.next()) {
							kh=rs2.getInt(1);
						}
						String sql3 = "SELECT count(*) from crm_business_opportunity where 1=1   "+wSQL+" and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "'";
						ps3 = conn.prepareStatement(sql3);
						rs3 = ps3.executeQuery();
						if (rs3.next()) {
							sj=rs3.getInt(1);
						}
						String sql4 = "SELECT count(*) from crm_contract where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "'";
						ps4 = conn.prepareStatement(sql4);
						rs4 = ps4.executeQuery();
						if (rs4.next()) {
							ht=rs4.getInt(1);
						}
						report.setColumn(str);
						report.setClueNumber(xs);
						report.setCustomerNumber(kh);
						report.setBusinessOpportunitiesNumber(sj);
						report.setContractsNumber(ht);
						list.add(report);
					}else {

						String[] xh=departmentIds.split(",");
					    for(String bmid : xh){
					    	if(!bmid.equals("")){
								String sql = "SELECT count(*) from crm_clue where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "' and (state!='xsc' or state is null) ";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								if (rs.next()) {
									xs=rs.getInt(1);
								}
								String sql2 = "SELECT count(*) from crm_customer where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "' and (status!='gh' or status is null) and data_type='客户'";
								ps2 = conn.prepareStatement(sql2);
								rs2 = ps2.executeQuery();
								if (rs2.next()) {
									kh=rs2.getInt(1);
								}
								String sql3 = "SELECT count(*) from crm_business_opportunity where 1=1   "+wSQL+" and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "'";
								ps3 = conn.prepareStatement(sql3);
								rs3 = ps3.executeQuery();
								if (rs3.next()) {
									sj=rs3.getInt(1);
								}
								String sql4 = "SELECT count(*) from crm_contract where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "'";
								ps4 = conn.prepareStatement(sql4);
								rs4 = ps4.executeQuery();
								if (rs4.next()) {
									ht=rs4.getInt(1);
								}
								report.setColumn(str);
								report.setClueNumber(xs);
								report.setCustomerNumber(kh);
								report.setBusinessOpportunitiesNumber(sj);
								report.setContractsNumber(ht);
								list.add(report);
					    	}
					    }
						String sql = "SELECT count(*) from crm_clue where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and (state!='xsc' or state is null) ";
						ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						if (rs.next()) {
							xs=rs.getInt(1);
						}
						String sql2 = "SELECT count(*) from crm_customer where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and (status!='gh' or status is null) and data_type='客户'";
						ps2 = conn.prepareStatement(sql2);
						rs2 = ps2.executeQuery();
						if (rs2.next()) {
							kh=rs2.getInt(1);
						}
						String sql3 = "SELECT count(*) from crm_business_opportunity where 1=1   "+wSQL+" and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "'";
						ps3 = conn.prepareStatement(sql3);
						rs3 = ps3.executeQuery();
						if (rs3.next()) {
							sj=rs3.getInt(1);
						}
						String sql4 = "SELECT count(*) from crm_contract where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "'";
						ps4 = conn.prepareStatement(sql4);
						rs4 = ps4.executeQuery();
						if (rs4.next()) {
							ht=rs4.getInt(1);
						}
						report.setColumn(str);
						report.setClueNumber(xs);
						report.setCustomerNumber(kh);
						report.setBusinessOpportunitiesNumber(sj);
						report.setContractsNumber(ht);
						list.add(report);
			        for (int i11 = 0; i11 < list.size() - 1; i11++) {
			            for (int j = i11 + 1; j < list.size(); j++) {
			                if (list.get(i11).equals(list.get(j))) {
			                    list.remove(j);
			                }
			            }
			        }
					}
				}

				
				
			}else {

				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
				SimpleDateFormat formatz = new SimpleDateFormat("yyyy");
				
				String nowdate=format.format(new Date());//当前月份
				String nowdatez=formatz.format(new Date());//当前月份
				
				Date d1 = new SimpleDateFormat("yyyy-MM").parse(nowdatez+"-01");//定义起始日期
				
				Date d2 = new SimpleDateFormat("yyyy-MM").parse(nowdatez+"-12");//定义结束日期 可以去当前月也可以手动写日期。
				
				Calendar dd = Calendar.getInstance();//定义日期实例
				
				dd.setTime(d1);//设置日期起始时间
				
				while (dd.getTime().before(d2)) {
				
				SimpleDateFormat sdfzz = new SimpleDateFormat("yyyy-MM");

					String str = sdfzz.format(dd.getTime());
					report = new Report();
					int xs=0;
					int kh=0;
					int sj=0;
					int ht=0;
					if(from.equals("全部")) {
						String sql = "SELECT count(*) from crm_clue where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and (state!='xsc' or state is null) ";
						ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						if (rs.next()) {
							xs=rs.getInt(1);
						}
						String sql2 = "SELECT count(*) from crm_customer where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and (status!='gh' or status is null) and data_type='客户'";
						ps2 = conn.prepareStatement(sql2);
						rs2 = ps2.executeQuery();
						if (rs2.next()) {
							kh=rs2.getInt(1);
						}
						String sql3 = "SELECT count(*) from crm_business_opportunity where 1=1   "+wSQL+" and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' ";
						ps3 = conn.prepareStatement(sql3);
						rs3 = ps3.executeQuery();
						if (rs3.next()) {
							sj=rs3.getInt(1);
						}
						String sql4 = "SELECT count(*) from crm_contract where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' ";
						ps4 = conn.prepareStatement(sql4);
						rs4 = ps4.executeQuery();
						if (rs4.next()) {
							ht=rs4.getInt(1);
						}  
						report.setColumn(str);
						report.setClueNumber(xs);
						report.setCustomerNumber(kh);
						report.setBusinessOpportunitiesNumber(sj);
						report.setContractsNumber(ht);
						list.add(report);
						dd.add(Calendar.MONTH, 1);//进行当前日期月份加1
					}else  {
						if(from.equals("本部门")) {

							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
									String sql = "SELECT count(*) from crm_clue where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "%' and (state!='xsc' or state is null) ";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									if (rs.next()) {
										xs=rs.getInt(1);
									}
									String sql2 = "SELECT count(*) from crm_customer where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "%' and (status!='gh' or status is null) and data_type='客户'";
									ps2 = conn.prepareStatement(sql2);
									rs2 = ps2.executeQuery();
									if (rs2.next()) {
										kh=rs2.getInt(1);
									}
									String sql3 = "SELECT count(*) from crm_business_opportunity where 1=1   "+wSQL+" and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "%'";
									ps3 = conn.prepareStatement(sql3);
									rs3 = ps3.executeQuery();
									if (rs3.next()) {
										sj=rs3.getInt(1);
									}
									String sql4 = "SELECT count(*) from crm_contract where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "%'";
									ps4 = conn.prepareStatement(sql4);
									rs4 = ps4.executeQuery();
									if (rs4.next()) {
										ht=rs4.getInt(1);
									}
									report.setColumn(str);
									report.setClueNumber(xs);
									report.setCustomerNumber(kh);
									report.setBusinessOpportunitiesNumber(sj);
									report.setContractsNumber(ht);
									list.add(report);
						    	}
						    }
							String sql = "SELECT count(*) from crm_clue where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and (state!='xsc' or state is null) ";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							if (rs.next()) {
								xs=rs.getInt(1);
							}
							String sql2 = "SELECT count(*) from crm_customer where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and (status!='gh' or status is null) and data_type='客户'";
							ps2 = conn.prepareStatement(sql2);
							rs2 = ps2.executeQuery();
							if (rs2.next()) {
								kh=rs2.getInt(1);
							}
							String sql3 = "SELECT count(*) from crm_business_opportunity where 1=1   "+wSQL+" and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "'";
							ps3 = conn.prepareStatement(sql3);
							rs3 = ps3.executeQuery();
							if (rs3.next()) {
								sj=rs3.getInt(1);
							}
							String sql4 = "SELECT count(*) from crm_contract where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "'";
							ps4 = conn.prepareStatement(sql4);
							rs4 = ps4.executeQuery();
							if (rs4.next()) {
								ht=rs4.getInt(1);
							}
							report.setColumn(str);
							report.setClueNumber(xs);
							report.setCustomerNumber(kh);
							report.setBusinessOpportunitiesNumber(sj);
							report.setContractsNumber(ht);
							list.add(report);
							dd.add(Calendar.MONTH, 1);//进行当前日期月份加1
						}else if(from.equals("未设置")) {

							String sql = "SELECT count(*) from crm_clue where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and (state!='xsc' or state is null) ";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							if (rs.next()) {
								xs=rs.getInt(1);
							}
							String sql2 = "SELECT count(*) from crm_customer where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and (status!='gh' or status is null) and data_type='客户'";
							ps2 = conn.prepareStatement(sql2);
							rs2 = ps2.executeQuery();
							if (rs2.next()) {
								kh=rs2.getInt(1);
							}
							String sql3 = "SELECT count(*) from crm_business_opportunity where 1=1   "+wSQL+" and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "'";
							ps3 = conn.prepareStatement(sql3);
							rs3 = ps3.executeQuery();
							if (rs3.next()) {
								sj=rs3.getInt(1);
							}
							String sql4 = "SELECT count(*) from crm_contract where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "'";
							ps4 = conn.prepareStatement(sql4);
							rs4 = ps4.executeQuery();
							if (rs4.next()) {
								ht=rs4.getInt(1);
							}
							report.setColumn(str);
							report.setClueNumber(xs);
							report.setCustomerNumber(kh);
							report.setBusinessOpportunitiesNumber(sj);
							report.setContractsNumber(ht);
							list.add(report);
							dd.add(Calendar.MONTH, 1);//进行当前日期月份加1
						}else {

							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
									String sql = "SELECT count(*) from crm_clue where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "' and (state!='xsc' or state is null) ";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									if (rs.next()) {
										xs=rs.getInt(1);
									}
									String sql2 = "SELECT count(*) from crm_customer where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "' and (status!='gh' or status is null) and data_type='客户'";
									ps2 = conn.prepareStatement(sql2);
									rs2 = ps2.executeQuery();
									if (rs2.next()) {
										kh=rs2.getInt(1);
									}
									String sql3 = "SELECT count(*) from crm_business_opportunity where 1=1   "+wSQL+" and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "'";
									ps3 = conn.prepareStatement(sql3);
									rs3 = ps3.executeQuery();
									if (rs3.next()) {
										sj=rs3.getInt(1);
									}
									String sql4 = "SELECT count(*) from crm_contract where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "'";
									ps4 = conn.prepareStatement(sql4);
									rs4 = ps4.executeQuery();
									if (rs4.next()) {
										ht=rs4.getInt(1);
									}
									report.setColumn(str);
									report.setClueNumber(xs);
									report.setCustomerNumber(kh);
									report.setBusinessOpportunitiesNumber(sj);
									report.setContractsNumber(ht);
									list.add(report);
						    	}
						    }
							String sql = "SELECT count(*) from crm_clue where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and (state!='xsc' or state is null) ";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							if (rs.next()) {
								xs=rs.getInt(1);
							}
							String sql2 = "SELECT count(*) from crm_customer where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and (status!='gh' or status is null) and data_type='客户'";
							ps2 = conn.prepareStatement(sql2);
							rs2 = ps2.executeQuery();
							if (rs2.next()) {
								kh=rs2.getInt(1);
							}
							String sql3 = "SELECT count(*) from crm_business_opportunity where 1=1   "+wSQL+" and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "'";
							ps3 = conn.prepareStatement(sql3);
							rs3 = ps3.executeQuery();
							if (rs3.next()) {
								sj=rs3.getInt(1);
							}
							String sql4 = "SELECT count(*) from crm_contract where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "'";
							ps4 = conn.prepareStatement(sql4);
							rs4 = ps4.executeQuery();
							if (rs4.next()) {
								ht=rs4.getInt(1);
							}
							report.setColumn(str);
							report.setClueNumber(xs);
							report.setCustomerNumber(kh);
							report.setBusinessOpportunitiesNumber(sj);
							report.setContractsNumber(ht);
							list.add(report);
							dd.add(Calendar.MONTH, 1);//进行当前日期月份加1
						}
					}
				
				}

				SimpleDateFormat sdfzz = new SimpleDateFormat("yyyy-MM");
				String str=sdfzz.format(d2.getTime());
				report = new Report();
				int xs=0;
				int kh=0;
				int sj=0;
				int ht=0;
				if(from.equals("全部")) {
					String sql = "SELECT count(*) from crm_clue where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and (state!='xsc' or state is null) ";
					ps = conn.prepareStatement(sql);
					rs = ps.executeQuery();
					if (rs.next()) {
						xs=rs.getInt(1);
					}
					String sql2 = "SELECT count(*) from crm_customer where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and (status!='gh' or status is null) and data_type='客户'";
					ps2 = conn.prepareStatement(sql2);
					rs2 = ps2.executeQuery();
					if (rs2.next()) {
						kh=rs2.getInt(1);
					}
					String sql3 = "SELECT count(*) from crm_business_opportunity where 1=1   "+wSQL+" and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' ";
					ps3 = conn.prepareStatement(sql3);
					rs3 = ps3.executeQuery();
					if (rs3.next()) {
						sj=rs3.getInt(1);
					}
					String sql4 = "SELECT count(*) from crm_contract where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' ";
					ps4 = conn.prepareStatement(sql4);
					rs4 = ps4.executeQuery();
					if (rs4.next()) {
						ht=rs4.getInt(1);
					}
					report.setColumn(str);
					report.setClueNumber(xs);
					report.setCustomerNumber(kh);
					report.setBusinessOpportunitiesNumber(sj);
					report.setContractsNumber(ht);
					list.add(report);
			        SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式  
				}else  {

					if(from.equals("本部门")) {

						String[] xh=departmentIds.split(",");
					    for(String bmid : xh){
					    	if(!bmid.equals("")){
								String sql = "SELECT count(*) from crm_clue where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "%' and (state!='xsc' or state is null) ";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								if (rs.next()) {
									xs=rs.getInt(1);
								}
								String sql2 = "SELECT count(*) from crm_customer where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "%' and (status!='gh' or status is null) and data_type='客户'";
								ps2 = conn.prepareStatement(sql2);
								rs2 = ps2.executeQuery();
								if (rs2.next()) {
									kh=rs2.getInt(1);
								}
								String sql3 = "SELECT count(*) from crm_business_opportunity where 1=1   "+wSQL+" and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "%'";
								ps3 = conn.prepareStatement(sql3);
								rs3 = ps3.executeQuery();
								if (rs3.next()) {
									sj=rs3.getInt(1);
								}
								String sql4 = "SELECT count(*) from crm_contract where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "%'";
								ps4 = conn.prepareStatement(sql4);
								rs4 = ps4.executeQuery();
								if (rs4.next()) {
									ht=rs4.getInt(1);
								}
								report.setColumn(str);
								report.setClueNumber(xs);
								report.setCustomerNumber(kh);
								report.setBusinessOpportunitiesNumber(sj);
								report.setContractsNumber(ht);
								list.add(report);
					    	}
					    }
						String sql = "SELECT count(*) from crm_clue where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and (state!='xsc' or state is null) ";
						ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						if (rs.next()) {
							xs=rs.getInt(1);
						}
						String sql2 = "SELECT count(*) from crm_customer where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and (status!='gh' or status is null) and data_type='客户'";
						ps2 = conn.prepareStatement(sql2);
						rs2 = ps2.executeQuery();
						if (rs2.next()) {
							kh=rs2.getInt(1);
						}
						String sql3 = "SELECT count(*) from crm_business_opportunity where 1=1   "+wSQL+" and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "'";
						ps3 = conn.prepareStatement(sql3);
						rs3 = ps3.executeQuery();
						if (rs3.next()) {
							sj=rs3.getInt(1);
						}
						String sql4 = "SELECT count(*) from crm_contract where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "'";
						ps4 = conn.prepareStatement(sql4);
						rs4 = ps4.executeQuery();
						if (rs4.next()) {
							ht=rs4.getInt(1);
						}
						report.setColumn(str);
						report.setClueNumber(xs);
						report.setCustomerNumber(kh);
						report.setBusinessOpportunitiesNumber(sj);
						report.setContractsNumber(ht);
						list.add(report);
					}else if(from.equals("未设置")) {

						String sql = "SELECT count(*) from crm_clue where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and (state!='xsc' or state is null) ";
						ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						if (rs.next()) {
							xs=rs.getInt(1);
						}
						String sql2 = "SELECT count(*) from crm_customer where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and (status!='gh' or status is null) and data_type='客户'";
						ps2 = conn.prepareStatement(sql2);
						rs2 = ps2.executeQuery();
						if (rs2.next()) {
							kh=rs2.getInt(1);
						}
						String sql3 = "SELECT count(*) from crm_business_opportunity where 1=1   "+wSQL+" and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "'";
						ps3 = conn.prepareStatement(sql3);
						rs3 = ps3.executeQuery();
						if (rs3.next()) {
							sj=rs3.getInt(1);
						}
						String sql4 = "SELECT count(*) from crm_contract where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "'";
						ps4 = conn.prepareStatement(sql4);
						rs4 = ps4.executeQuery();
						if (rs4.next()) {
							ht=rs4.getInt(1);
						}
						report.setColumn(str);
						report.setClueNumber(xs);
						report.setCustomerNumber(kh);
						report.setBusinessOpportunitiesNumber(sj);
						report.setContractsNumber(ht);
						list.add(report);
					}else {

						String[] xh=departmentIds.split(",");
					    for(String bmid : xh){
					    	if(!bmid.equals("")){
								String sql = "SELECT count(*) from crm_clue where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "' and (state!='xsc' or state is null) ";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								if (rs.next()) {
									xs=rs.getInt(1);
								}
								String sql2 = "SELECT count(*) from crm_customer where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "' and (status!='gh' or status is null) and data_type='客户'";
								ps2 = conn.prepareStatement(sql2);
								rs2 = ps2.executeQuery();
								if (rs2.next()) {
									kh=rs2.getInt(1);
								}
								String sql3 = "SELECT count(*) from crm_business_opportunity where 1=1   "+wSQL+" and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "'";
								ps3 = conn.prepareStatement(sql3);
								rs3 = ps3.executeQuery();
								if (rs3.next()) {
									sj=rs3.getInt(1);
								}
								String sql4 = "SELECT count(*) from crm_contract where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "'";
								ps4 = conn.prepareStatement(sql4);
								rs4 = ps4.executeQuery();
								if (rs4.next()) {
									ht=rs4.getInt(1);
								}
								report.setColumn(str);
								report.setClueNumber(xs);
								report.setCustomerNumber(kh);
								report.setBusinessOpportunitiesNumber(sj);
								report.setContractsNumber(ht);
								list.add(report);
					    	}
					    }
						String sql = "SELECT count(*) from crm_clue where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and (state!='xsc' or state is null) ";
						ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						if (rs.next()) {
							xs=rs.getInt(1);
						}
						String sql2 = "SELECT count(*) from crm_customer where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and (status!='gh' or status is null) and data_type='客户'";
						ps2 = conn.prepareStatement(sql2);
						rs2 = ps2.executeQuery();
						if (rs2.next()) {
							kh=rs2.getInt(1);
						}
						String sql3 = "SELECT count(*) from crm_business_opportunity where 1=1   "+wSQL+" and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "'";
						ps3 = conn.prepareStatement(sql3);
						rs3 = ps3.executeQuery();
						if (rs3.next()) {
							sj=rs3.getInt(1);
						}
						String sql4 = "SELECT count(*) from crm_contract where 1=1  "+wSQL+"  and create_date like'" + str + "%' and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "'";
						ps4 = conn.prepareStatement(sql4);
						rs4 = ps4.executeQuery();
						if (rs4.next()) {
							ht=rs4.getInt(1);
						}
						report.setColumn(str);
						report.setClueNumber(xs);
						report.setCustomerNumber(kh);
						report.setBusinessOpportunitiesNumber(sj);
						report.setContractsNumber(ht);
						list.add(report);
					}
				}
			}

//	        for (int i11 = 0; i11 < list.size() - 1; i11++) {
//	            for (int j = i11 + 1; j < list.size(); j++) {
//	                if (list.get(i11).equals(list.get(j))) {
//	                    list.remove(j);
//	                }
//	            }
//	        }
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
//			System.out.println("List<Report> getItemsnewbusiness(Report report)" + (t2 - t1) + " ms");
		} catch (SQLException | ParseException e) {
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		} finally {
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		}
		return list;
	}

	@Override
	public List<Report> getItemsPayMentCollectionPlan(Report report) {
		long t1 = System.currentTimeMillis();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		PreparedStatement ps3 = null;
		ResultSet rs3 = null;
		PreparedStatement ps4 = null;
		ResultSet rs4 = null;
		List<Report> list = new ArrayList<Report>();
		String mechanismId=report.getMechanismId();
		String from=report.getFrom() == null ? "" : report.getFrom();
		String departmentIds=report.getDepartmentIds();
		String username=report.getUsername();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);


			String wSQL = "";

			if(report.getDepartmentId()!=null){
				if(report.getDepartmentId().equals("")){
					
				}else{
					if (report.getDepartmentId() != null || !report.getDepartmentId().equals("")) {
						wSQL += " and department_id='" + report.getDepartmentId() + "'";
					}
				}
			}
			if(report.getUserId()!=null){
				if(report.getUserId().equals("")){
					
				}else{
					if (report.getUserId() != null || !report.getUserId().equals("")) {
						wSQL += " and create_id='" + report.getUserId() + "'";
					}
				}
			}
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
			SimpleDateFormat formatz = new SimpleDateFormat("yyyy");
			
			String nowdate=format.format(new Date());//当前月份
			String nowdatez=formatz.format(new Date());//当前月份

			Date d1 = new SimpleDateFormat("yyyy-MM").parse(nowdatez+"-01");//定义起始日期
			Date d2 = new SimpleDateFormat("yyyy-MM").parse(nowdatez+"-12");//定义结束日期 可以去当前月也可以手动写日期
			if(report.getTime()==null){
			}else{
				if(report.getTime().equals("")){
				}else{
					d1 = new SimpleDateFormat("yyyy-MM").parse(report.getTime()+"-01");//定义起始日期
					d2 = new SimpleDateFormat("yyyy-MM").parse(report.getTime()+"-12");//定义结束日期 可以去当前月也可以手动写日期
				}
			}
			
			Calendar dd = Calendar.getInstance();//定义日期实例
			
			dd.setTime(d1);//设置日期起始时间
			
			while (dd.getTime().before(d2)) {
			
			SimpleDateFormat sdfzz = new SimpleDateFormat("yyyy-MM");

				String str = sdfzz.format(dd.getTime());
				report = new Report();
				BigDecimal hkje=new BigDecimal(0);
				BigDecimal kpje=new BigDecimal(0);
				if(from.equals("全部")) {
					String sql = "SELECT sum(collection_amount) from crm_payment_collection_plan_subordinate where 1=1  and mechanism_id like'" + mechanismId + "%' "+wSQL+" and create_date like'" + str + "%' ";
					ps = conn.prepareStatement(sql);
					rs = ps.executeQuery();
					if (rs.next()) {
						hkje=rs.getBigDecimal(1);
					}
					String sql2 = "SELECT sum(invoicing_amount) from crm_invoicing where 1=1  and mechanism_id like'" + mechanismId + "%' and create_date like'" + str + "%' "+wSQL+"";
					ps2 = conn.prepareStatement(sql2);
					rs2 = ps2.executeQuery();
					if (rs2.next()) {
						kpje=rs2.getBigDecimal(1);
					}
					report.setColumn(str);
					report.setReceivableAmount(hkje);
					report.setInvoiceAmount(kpje);
					list.add(report);
					dd.add(Calendar.MONTH, 1);//进行当前日期月份加1
				}else{

					if(from.equals("本部门")) {

						String[] xh=departmentIds.split(",");
					    for(String bmid : xh){
					    	if(!bmid.equals("")){
					    		String sql = "SELECT sum(collection_amount) from crm_payment_collection_plan_subordinate where 1=1  and mechanism_id like'" + mechanismId + "%' "+wSQL+" and department_id like'" + bmid + "%'  and create_date like'" + str + "%' ";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								if (rs.next()) {
									hkje=rs.getBigDecimal(1);
								}
								String sql2 = "SELECT sum(invoicing_amount) from crm_invoicing where 1=1  and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "%' "+wSQL+"  and create_date like'" + str + "%' ";
								ps2 = conn.prepareStatement(sql2);
								rs2 = ps2.executeQuery();
								if (rs2.next()) {
									kpje=rs2.getBigDecimal(1);
								}
								report.setColumn(str);
								report.setReceivableAmount(hkje);
								report.setInvoiceAmount(kpje);
								list.add(report);
					    	}
					    }

			    		String sql = "SELECT sum(collection_amount) from crm_payment_collection_plan_subordinate where 1=1  and mechanism_id like'" + mechanismId + "%' "+wSQL+" and create_id ='" + username + "'  and create_date like'" + str + "%' ";
						ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						if (rs.next()) {
							hkje=rs.getBigDecimal(1);
						}
						String sql2 = "SELECT sum(invoicing_amount) from crm_invoicing where 1=1  and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' "+wSQL+"  and create_date like'" + str + "%' ";
						ps2 = conn.prepareStatement(sql2);
						rs2 = ps2.executeQuery();
						if (rs2.next()) {
							kpje=rs2.getBigDecimal(1);
						}
						report.setColumn(str);
						report.setReceivableAmount(hkje);
						report.setInvoiceAmount(kpje);
						list.add(report);
						dd.add(Calendar.MONTH, 1);//进行当前日期月份加1
				        for (int i11 = 0; i11 < list.size() - 1; i11++) {
				            for (int j = i11 + 1; j < list.size(); j++) {
				                if (list.get(i11).equals(list.get(j))) {
				                    list.remove(j);
				                }
				            }
				        }
					}else if(from.equals("未设置")) {

			    		String sql = "SELECT sum(collection_amount) from crm_payment_collection_plan_subordinate where 1=1  and mechanism_id like'" + mechanismId + "%' "+wSQL+" and create_id ='" + username + "'  and create_date like'" + str + "%' ";
						ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						if (rs.next()) {
							hkje=rs.getBigDecimal(1);
						}
						String sql2 = "SELECT sum(invoicing_amount) from crm_invoicing where 1=1  and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' "+wSQL+"  and create_date like'" + str + "%' ";
						ps2 = conn.prepareStatement(sql2);
						rs2 = ps2.executeQuery();
						if (rs2.next()) {
							kpje=rs2.getBigDecimal(1);
						}
						report.setColumn(str);
						report.setReceivableAmount(hkje);
						report.setInvoiceAmount(kpje);
						list.add(report);
						dd.add(Calendar.MONTH, 1);//进行当前日期月份加1
					}else {

						String[] xh=departmentIds.split(",");
					    for(String bmid : xh){
					    	if(!bmid.equals("")){
					    		String sql = "SELECT sum(collection_amount) from crm_payment_collection_plan_subordinate where 1=1  and mechanism_id like'" + mechanismId + "%' "+wSQL+" and department_id like'" + bmid + "'  and create_date like'" + str + "%' ";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								if (rs.next()) {
									hkje=rs.getBigDecimal(1);
								}
								String sql2 = "SELECT sum(invoicing_amount) from crm_invoicing where 1=1  and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "' "+wSQL+"  and create_date like'" + str + "%' ";
								ps2 = conn.prepareStatement(sql2);
								rs2 = ps2.executeQuery();
								if (rs2.next()) {
									kpje=rs2.getBigDecimal(1);
								}
								report.setColumn(str);
								report.setReceivableAmount(hkje);
								report.setInvoiceAmount(kpje);
								list.add(report);
					    	}
					    }

			    		String sql = "SELECT sum(collection_amount) from crm_payment_collection_plan_subordinate where 1=1  and mechanism_id like'" + mechanismId + "%' "+wSQL+" and create_id ='" + username + "'  and create_date like'" + str + "%' ";
						ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						if (rs.next()) {
							hkje=rs.getBigDecimal(1);
						}
						String sql2 = "SELECT sum(invoicing_amount) from crm_invoicing where 1=1  and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "'  "+wSQL+" and create_date like'" + str + "%' ";
						ps2 = conn.prepareStatement(sql2);
						rs2 = ps2.executeQuery();
						if (rs2.next()) {
							kpje=rs2.getBigDecimal(1);
						}
						report.setColumn(str);
						report.setReceivableAmount(hkje);
						report.setInvoiceAmount(kpje);
						list.add(report);
						dd.add(Calendar.MONTH, 1);//进行当前日期月份加1
				        for (int i11 = 0; i11 < list.size() - 1; i11++) {
				            for (int j = i11 + 1; j < list.size(); j++) {
				                if (list.get(i11).equals(list.get(j))) {
				                    list.remove(j);
				                }
				            }
				        }
					}
				}

			}

			SimpleDateFormat sdfzz = new SimpleDateFormat("yyyy-MM");
			String str=sdfzz.format(d2.getTime());
			report = new Report();
			BigDecimal hkje=new BigDecimal(0);
			BigDecimal kpje=new BigDecimal(0);
			if(from.equals("全部")) {
				String sql = "SELECT sum(collection_amount) from crm_payment_collection_plan_subordinate where 1=1  and mechanism_id like'" + mechanismId + "%' and create_date like'" + str + "%' ";
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				if (rs.next()) {
					hkje=rs.getBigDecimal(1);
				}
				String sql2 = "SELECT sum(invoicing_amount) from crm_invoicing where 1=1  and mechanism_id like'" + mechanismId + "%' and create_date like'" + str + "%' ";
				ps2 = conn.prepareStatement(sql2);
				rs2 = ps2.executeQuery();
				if (rs2.next()) {
					kpje=rs2.getBigDecimal(1);
				}
				report.setColumn(str);
				report.setReceivableAmount(hkje);
				report.setInvoiceAmount(kpje);
				list.add(report);

			}else{

				if(from.equals("本部门")) {

					String[] xh=departmentIds.split(",");
				    for(String bmid : xh){
				    	if(!bmid.equals("")){
				    		String sql = "SELECT sum(collection_amount) from crm_payment_collection_plan_subordinate where 1=1  and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "%'  and create_date like'" + str + "%' ";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							if (rs.next()) {
								hkje=rs.getBigDecimal(1);
							}
							String sql2 = "SELECT sum(invoicing_amount) from crm_invoicing where 1=1  and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "%'  and create_date like'" + str + "%' ";
							ps2 = conn.prepareStatement(sql2);
							rs2 = ps2.executeQuery();
							if (rs2.next()) {
								kpje=rs2.getBigDecimal(1);
							}
							report.setColumn(str);
							report.setReceivableAmount(hkje);
							report.setInvoiceAmount(kpje);
							list.add(report);
				    	}
				    }

		    		String sql = "SELECT sum(collection_amount) from crm_payment_collection_plan_subordinate where 1=1  and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "'  and create_date like'" + str + "%' ";
					ps = conn.prepareStatement(sql);
					rs = ps.executeQuery();
					if (rs.next()) {
						hkje=rs.getBigDecimal(1);
					}
					String sql2 = "SELECT sum(invoicing_amount) from crm_invoicing where 1=1  and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "'  and create_date like'" + str + "%' ";
					ps2 = conn.prepareStatement(sql2);
					rs2 = ps2.executeQuery();
					if (rs2.next()) {
						kpje=rs2.getBigDecimal(1);
					}
					report.setColumn(str);
					report.setReceivableAmount(hkje);
					report.setInvoiceAmount(kpje);
					list.add(report);

			        for (int i11 = 0; i11 < list.size() - 1; i11++) {
			            for (int j = i11 + 1; j < list.size(); j++) {
			                if (list.get(i11).equals(list.get(j))) {
			                    list.remove(j);
			                }
			            }
			        }
				}else if(from.equals("未设置")) {

		    		String sql = "SELECT sum(collection_amount) from crm_payment_collection_plan_subordinate where 1=1  and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "'  and create_date like'" + str + "%' ";
					ps = conn.prepareStatement(sql);
					rs = ps.executeQuery();
					if (rs.next()) {
						hkje=rs.getBigDecimal(1);
					}
					String sql2 = "SELECT sum(invoicing_amount) from crm_invoicing where 1=1  and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "'  and create_date like'" + str + "%' ";
					ps2 = conn.prepareStatement(sql2);
					rs2 = ps2.executeQuery();
					if (rs2.next()) {
						kpje=rs2.getBigDecimal(1);
					}
					report.setColumn(str);
					report.setReceivableAmount(hkje);
					report.setInvoiceAmount(kpje);
					list.add(report);

				}else {

					String[] xh=departmentIds.split(",");
				    for(String bmid : xh){
				    	if(!bmid.equals("")){
				    		String sql = "SELECT sum(collection_amount) from crm_payment_collection_plan_subordinate where 1=1  and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "'  and create_date like'" + str + "%' ";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							if (rs.next()) {
								hkje=rs.getBigDecimal(1);
							}
							String sql2 = "SELECT sum(invoicing_amount) from crm_invoicing where 1=1  and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "'  and create_date like'" + str + "%' ";
							ps2 = conn.prepareStatement(sql2);
							rs2 = ps2.executeQuery();
							if (rs2.next()) {
								kpje=rs2.getBigDecimal(1);
							}
							report.setColumn(str);
							report.setReceivableAmount(hkje);
							report.setInvoiceAmount(kpje);
							list.add(report);
				    	}
				    }

		    		String sql = "SELECT sum(collection_amount) from crm_payment_collection_plan_subordinate where 1=1  and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "'  and create_date like'" + str + "%' ";
					ps = conn.prepareStatement(sql);
					rs = ps.executeQuery();
					if (rs.next()) {
						hkje=rs.getBigDecimal(1);
					}
					String sql2 = "SELECT sum(invoicing_amount) from crm_invoicing where 1=1  and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "'  and create_date like'" + str + "%' ";
					ps2 = conn.prepareStatement(sql2);
					rs2 = ps2.executeQuery();
					if (rs2.next()) {
						kpje=rs2.getBigDecimal(1);
					}
					report.setColumn(str);
					report.setReceivableAmount(hkje);
					report.setInvoiceAmount(kpje);
					list.add(report);

			        for (int i11 = 0; i11 < list.size() - 1; i11++) {
			            for (int j = i11 + 1; j < list.size(); j++) {
			                if (list.get(i11).equals(list.get(j))) {
			                    list.remove(j);
			                }
			            }
			        }
				}
				
			}

			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("List<Report> getItemshkjh(Report report)" + (t2 - t1) + " ms");
		} catch (SQLException | ParseException e) {
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		} finally {
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		}
		return list;
	}

	@Override
	public List<Report> getItemsPerformancegoalCompletionranking(Report report) {
		long t1 = System.currentTimeMillis();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		PreparedStatement ps3 = null;
		ResultSet rs3 = null;
		PreparedStatement ps4 = null;
		ResultSet rs4 = null;
		List<Report> list = new ArrayList<Report>();
		String mechanismId=report.getMechanismId();
		String from=report.getFrom() == null ? "" : report.getFrom();
		String departmentIds=report.getDepartmentIds();
		String username=report.getUsername();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);

			String wSQL = "";

			if(report.getDepartmentId()!=null){
				if(report.getDepartmentId().equals("")){
					
				}else{
					if (report.getDepartmentId() != null || !report.getDepartmentId().equals("")) {
						wSQL += " and department_id='" + report.getDepartmentId() + "'";
					}
				}
			}
			int i=0;
			if(report.getTimeToScreen()==null){
				report.setTimeToScreen("本月");
			}
			if(report.getTimeToScreen().equals("上月")){

		        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		        Date date = new Date();
		        Calendar calendar = Calendar.getInstance();
		        calendar.setTime(date); // 设置为当前时间
		        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1); // 设置为上一个月
		        date = calendar.getTime();
		        String accDate = format.format(date);
				report = new Report();
				BigDecimal xse=new BigDecimal(0);
				String sql = "";
					if(from.equals("全部")) {
						sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1 "+wSQL+" and sales_stage_name='赢单'  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
						ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						int i1=1;
						while (rs.next()) {
							report.setRanking(i1++);
							report.setToWinASingleAmount(rs.getBigDecimal(1));
							report.setDepartmentName(rs.getString(2));
							list.add(report);
						}
					}else{
						if(from.equals("本部门")) {
							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
									sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1 "+wSQL+" and sales_stage_name='赢单' and department_id like'" + bmid + "%' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report.setRanking(i1++);
										report.setToWinASingleAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										list.add(report);
									}
						    	}
						    }
						    sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1 "+wSQL+" and sales_stage_name='赢单' and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
						    ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report.setRanking(i1++);
								report.setToWinASingleAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								list.add(report);
							}
					        for (int i11 = 0; i11 < list.size() - 1; i11++) {
					            for (int j = i11 + 1; j < list.size(); j++) {
					                if (list.get(i11).equals(list.get(j))) {
					                    list.remove(j);
					                }
					            }
					        }
						}else if(from.equals("未设置")) {
						    sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1 "+wSQL+" and sales_stage_name='赢单' and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
						    ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report.setRanking(i1++);
								report.setToWinASingleAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								list.add(report);
							}
						}else {
							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
									sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1 "+wSQL+" and sales_stage_name='赢单' and department_id like'" + bmid + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report.setRanking(i1++);
										report.setToWinASingleAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										list.add(report);
									}
						    	}
						    }
						    sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1 "+wSQL+" and sales_stage_name='赢单' and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
						    ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report.setRanking(i1++);
								report.setToWinASingleAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								list.add(report);
							}
					        for (int i11 = 0; i11 < list.size() - 1; i11++) {
					            for (int j = i11 + 1; j < list.size(); j++) {
					                if (list.get(i11).equals(list.get(j))) {
					                    list.remove(j);
					                }
					            }
					        }
							
						}
					}
				
			}else if(report.getTimeToScreen().equals("下月")){

		        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		        Date date = new Date();
		        Calendar calendar = Calendar.getInstance();
		        calendar.setTime(date); // 设置为当前时间
		        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1); // 设置为上一个月
		        date = calendar.getTime();
		        String accDate = format.format(date);
				report = new Report();
				BigDecimal xse=new BigDecimal(0);
				String sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1  "+wSQL+" and sales_stage_name='赢单'  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%' GROUP BY department_name ORDER BY SUM(estimated_amount) desc";


				if(from.equals("全部")) {
					sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1 "+wSQL+" and sales_stage_name='赢单'  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
					ps = conn.prepareStatement(sql);
					rs = ps.executeQuery();
					int i1=1;
					while (rs.next()) {
						report.setRanking(i1++);
						report.setToWinASingleAmount(rs.getBigDecimal(1));
						report.setDepartmentName(rs.getString(2));
						list.add(report);
					}
				}else{
					if(from.equals("本部门")) {

						String[] xh=departmentIds.split(",");
					    for(String bmid : xh){
					    	if(!bmid.equals("")){
								sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1 "+wSQL+" and sales_stage_name='赢单' and department_id like'" + bmid + "%' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report.setRanking(i1++);
									report.setToWinASingleAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
					    	}
					    }
					    sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1 "+wSQL+" and sales_stage_name='赢单' and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
					    ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						int i1=1;
						while (rs.next()) {
							report.setRanking(i1++);
							report.setToWinASingleAmount(rs.getBigDecimal(1));
							report.setDepartmentName(rs.getString(2));
							list.add(report);
						}
				        for (int i11 = 0; i11 < list.size() - 1; i11++) {
				            for (int j = i11 + 1; j < list.size(); j++) {
				                if (list.get(i11).equals(list.get(j))) {
				                    list.remove(j);
				                }
				            }
				        }
					}else if(from.equals("未设置")) {
					    sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1 "+wSQL+" and sales_stage_name='赢单' and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
					    ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						int i1=1;
						while (rs.next()) {
							report.setRanking(i1++);
							report.setToWinASingleAmount(rs.getBigDecimal(1));
							report.setDepartmentName(rs.getString(2));
							list.add(report);
						}
					}else {

						String[] xh=departmentIds.split(",");
					    for(String bmid : xh){
					    	if(!bmid.equals("")){
								sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1 "+wSQL+" and sales_stage_name='赢单' and department_id like'" + bmid + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report.setRanking(i1++);
									report.setToWinASingleAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
					    	}
					    }
					    sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1 "+wSQL+" and sales_stage_name='赢单' and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
					    ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						int i1=1;
						while (rs.next()) {
							report.setRanking(i1++);
							report.setToWinASingleAmount(rs.getBigDecimal(1));
							report.setDepartmentName(rs.getString(2));
							list.add(report);
						}
				        for (int i11 = 0; i11 < list.size() - 1; i11++) {
				            for (int j = i11 + 1; j < list.size(); j++) {
				                if (list.get(i11).equals(list.get(j))) {
				                    list.remove(j);
				                }
				            }
				        }
					}
				}
				
			}else if(report.getTimeToScreen().equals("本年")){


				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
				SimpleDateFormat formatz = new SimpleDateFormat("yyyy");
				
				String nowdate=format.format(new Date());//当前月份
				String nowdatez=formatz.format(new Date());//当前月份
				
				Date d1 = new SimpleDateFormat("yyyy-MM").parse(nowdatez+"-01");//定义起始日期
				
				Date d2 = new SimpleDateFormat("yyyy-MM").parse(nowdatez+"-12");//定义结束日期 可以去当前月也可以手动写日期。
				
				Calendar dd = Calendar.getInstance();//定义日期实例
				
				dd.setTime(d1);//设置日期起始时间
				
				while (dd.getTime().before(d2)) {
				
				SimpleDateFormat sdfzz = new SimpleDateFormat("yyyy-MM");

					String accDate = sdfzz.format(dd.getTime());
					report = new Report();
					BigDecimal xse=new BigDecimal(0);
					String sql = "";

					if(from.equals("全部")) {
						sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1 "+wSQL+" and sales_stage_name='赢单'  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
						ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						int i1=1;
						while (rs.next()) {
							report.setRanking(i1++);
							report.setToWinASingleAmount(rs.getBigDecimal(1));
							report.setDepartmentName(rs.getString(2));
							list.add(report);
						}
						dd.add(Calendar.MONTH, 1);//进行当前日期月份加1
					}else{
						if(from.equals("本部门")) {

							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
									sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1 "+wSQL+" and sales_stage_name='赢单' and department_id like'" + bmid + "%' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report.setRanking(i1++);
										report.setToWinASingleAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										list.add(report);
									}
						    	}
						    }
						    sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1 "+wSQL+" and sales_stage_name='赢单' and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
						    ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report.setRanking(i1++);
								report.setToWinASingleAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								list.add(report);
							}
							dd.add(Calendar.MONTH, 1);//进行当前日期月份加
					        for (int i11 = 0; i11 < list.size() - 1; i11++) {
					            for (int j = i11 + 1; j < list.size(); j++) {
					                if (list.get(i11).equals(list.get(j))) {
					                    list.remove(j);
					                }
					            }
					        }
						}else if(from.equals("未设置")) {

						    sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1 "+wSQL+" and sales_stage_name='赢单' and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
						    ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report.setRanking(i1++);
								report.setToWinASingleAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								list.add(report);
							}
							dd.add(Calendar.MONTH, 1);//进行当前日期月份加
						}else {
							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
									sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1 "+wSQL+" and sales_stage_name='赢单' and department_id like'" + bmid + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report.setRanking(i1++);
										report.setToWinASingleAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										list.add(report);
									}
						    	}
						    }
						    sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1 "+wSQL+" and sales_stage_name='赢单' and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
						    ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report.setRanking(i1++);
								report.setToWinASingleAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								list.add(report);
							}
							dd.add(Calendar.MONTH, 1);//进行当前日期月份加
					        for (int i11 = 0; i11 < list.size() - 1; i11++) {
					            for (int j = i11 + 1; j < list.size(); j++) {
					                if (list.get(i11).equals(list.get(j))) {
					                    list.remove(j);
					                }
					            }
					        }
							
						}
					}
					

				}

				SimpleDateFormat sdfzz = new SimpleDateFormat("yyyy-MM");
				String accDate=sdfzz.format(d2.getTime());
				report = new Report();
				BigDecimal xse=new BigDecimal(0);
				String sql = "";

				if(from.equals("全部")) {
					sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1 "+wSQL+" and sales_stage_name='赢单'  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
					ps = conn.prepareStatement(sql);
					rs = ps.executeQuery();
					int i1=1;
					while (rs.next()) {
						report.setRanking(i1++);
						report.setToWinASingleAmount(rs.getBigDecimal(1));
						report.setDepartmentName(rs.getString(2));
						list.add(report);
					}
				}else{
					if(from.equals("本部门")) {

						String[] xh=departmentIds.split(",");
					    for(String bmid : xh){
					    	if(!bmid.equals("")){
								sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1 "+wSQL+" and sales_stage_name='赢单' and department_id like'" + bmid + "%' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report.setRanking(i1++);
									report.setToWinASingleAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
					    	}
					    }
					    sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1 "+wSQL+" and sales_stage_name='赢单' and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
					    ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						int i1=1;
						while (rs.next()) {
							report.setRanking(i1++);
							report.setToWinASingleAmount(rs.getBigDecimal(1));
							report.setDepartmentName(rs.getString(2));
							list.add(report);
						}
				        for (int i11 = 0; i11 < list.size() - 1; i11++) {
				            for (int j = i11 + 1; j < list.size(); j++) {
				                if (list.get(i11).equals(list.get(j))) {
				                    list.remove(j);
				                }
				            }
				        }
					}else if(from.equals("未设置")) {
					    sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1 "+wSQL+" and sales_stage_name='赢单' and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
					    ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						int i1=1;
						while (rs.next()) {
							report.setRanking(i1++);
							report.setToWinASingleAmount(rs.getBigDecimal(1));
							report.setDepartmentName(rs.getString(2));
							list.add(report);
						}
					}else {

						String[] xh=departmentIds.split(",");
					    for(String bmid : xh){
					    	if(!bmid.equals("")){
								sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1 "+wSQL+" and sales_stage_name='赢单' and department_id like'" + bmid + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report.setRanking(i1++);
									report.setToWinASingleAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
					    	}
					    }
					    sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1 "+wSQL+" and sales_stage_name='赢单' and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
					    ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						int i1=1;
						while (rs.next()) {
							report.setRanking(i1++);
							report.setToWinASingleAmount(rs.getBigDecimal(1));
							report.setDepartmentName(rs.getString(2));
							list.add(report);
						}
				        for (int i11 = 0; i11 < list.size() - 1; i11++) {
				            for (int j = i11 + 1; j < list.size(); j++) {
				                if (list.get(i11).equals(list.get(j))) {
				                    list.remove(j);
				                }
				            }
				        }
					}
				}

			
				
				

				
			}else if(report.getTimeToScreen().equals("上半年")){


				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
				SimpleDateFormat formatz = new SimpleDateFormat("yyyy");
				
				String nowdate=format.format(new Date());//当前月份
				String nowdatez=formatz.format(new Date());//当前月份
				
				Date d1 = new SimpleDateFormat("yyyy-MM").parse(nowdatez+"-01");//定义起始日期
				
				Date d2 = new SimpleDateFormat("yyyy-MM").parse(nowdatez+"-06");//定义结束日期 可以去当前月也可以手动写日期。
				
				Calendar dd = Calendar.getInstance();//定义日期实例
				
				dd.setTime(d1);//设置日期起始时间
				
				while (dd.getTime().before(d2)) {
				
				SimpleDateFormat sdfzz = new SimpleDateFormat("yyyy-MM");

					String accDate = sdfzz.format(dd.getTime());
					report = new Report();
					BigDecimal xse=new BigDecimal(0);
					String sql = "";

					if(from.equals("全部")) {
						sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1 "+wSQL+" and sales_stage_name='赢单'  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
						ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						int i1=1;
						while (rs.next()) {
							report.setRanking(i1++);
							report.setToWinASingleAmount(rs.getBigDecimal(1));
							report.setDepartmentName(rs.getString(2));
							list.add(report);
						}
						dd.add(Calendar.MONTH, 1);//进行当前日期月份加1
					}else{
						if(from.equals("本部门")) {

							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
									sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1 "+wSQL+" and sales_stage_name='赢单' and department_id like'" + bmid + "%' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report.setRanking(i1++);
										report.setToWinASingleAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										list.add(report);
									}
						    	}
						    }
						    sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1 "+wSQL+" and sales_stage_name='赢单' and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
						    ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report.setRanking(i1++);
								report.setToWinASingleAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								list.add(report);
							}
							dd.add(Calendar.MONTH, 1);//进行当前日期月份加1
					        for (int i11 = 0; i11 < list.size() - 1; i11++) {
					            for (int j = i11 + 1; j < list.size(); j++) {
					                if (list.get(i11).equals(list.get(j))) {
					                    list.remove(j);
					                }
					            }
					        }
						}else if(from.equals("未设置")) {
						    sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1 "+wSQL+" and sales_stage_name='赢单' and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
						    ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report.setRanking(i1++);
								report.setToWinASingleAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								list.add(report);
							}
							dd.add(Calendar.MONTH, 1);//进行当前日期月份加1
						}else {
							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
									sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1 "+wSQL+" and sales_stage_name='赢单' and department_id like'" + bmid + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report.setRanking(i1++);
										report.setToWinASingleAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										list.add(report);
									}
						    	}
						    }
						    sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1 "+wSQL+" and sales_stage_name='赢单' and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
						    ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report.setRanking(i1++);
								report.setToWinASingleAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								list.add(report);
							}
							dd.add(Calendar.MONTH, 1);//进行当前日期月份加1
					        for (int i11 = 0; i11 < list.size() - 1; i11++) {
					            for (int j = i11 + 1; j < list.size(); j++) {
					                if (list.get(i11).equals(list.get(j))) {
					                    list.remove(j);
					                }
					            }
					        }
							
						}
					}

				
				}

				SimpleDateFormat sdfzz = new SimpleDateFormat("yyyy-MM");
				String accDate=sdfzz.format(d2.getTime());
				report = new Report();
				BigDecimal xse=new BigDecimal(0);
				String sql = "";

				if(from.equals("全部")) {
					sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1 "+wSQL+" and sales_stage_name='赢单'  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
					ps = conn.prepareStatement(sql);
					rs = ps.executeQuery();
					int i1=1;
					while (rs.next()) {
						report.setRanking(i1++);
						report.setToWinASingleAmount(rs.getBigDecimal(1));
						report.setDepartmentName(rs.getString(2));
						list.add(report);
					}
				}else{
					if(from.equals("本部门")) {

						String[] xh=departmentIds.split(",");
					    for(String bmid : xh){
					    	if(!bmid.equals("")){
								sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1 "+wSQL+" and sales_stage_name='赢单' and department_id like'" + bmid + "%' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report.setRanking(i1++);
									report.setToWinASingleAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
					    	}
					    }
					    sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1 "+wSQL+" and sales_stage_name='赢单' and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
					    ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						int i1=1;
						while (rs.next()) {
							report.setRanking(i1++);
							report.setToWinASingleAmount(rs.getBigDecimal(1));
							report.setDepartmentName(rs.getString(2));
							list.add(report);
						}
				        for (int i11 = 0; i11 < list.size() - 1; i11++) {
				            for (int j = i11 + 1; j < list.size(); j++) {
				                if (list.get(i11).equals(list.get(j))) {
				                    list.remove(j);
				                }
				            }
				        }
					}else if(from.equals("未设置")) {
					    sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1 "+wSQL+" and sales_stage_name='赢单' and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
					    ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						int i1=1;
						while (rs.next()) {
							report.setRanking(i1++);
							report.setToWinASingleAmount(rs.getBigDecimal(1));
							report.setDepartmentName(rs.getString(2));
							list.add(report);
						}
					}else {

						String[] xh=departmentIds.split(",");
					    for(String bmid : xh){
					    	if(!bmid.equals("")){
								sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1 "+wSQL+" and sales_stage_name='赢单' and department_id like'" + bmid + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report.setRanking(i1++);
									report.setToWinASingleAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
					    	}
					    }
					    sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1 "+wSQL+" and sales_stage_name='赢单' and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
					    ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						int i1=1;
						while (rs.next()) {
							report.setRanking(i1++);
							report.setToWinASingleAmount(rs.getBigDecimal(1));
							report.setDepartmentName(rs.getString(2));
							list.add(report);
						}
				        for (int i11 = 0; i11 < list.size() - 1; i11++) {
				            for (int j = i11 + 1; j < list.size(); j++) {
				                if (list.get(i11).equals(list.get(j))) {
				                    list.remove(j);
				                }
				            }
				        }
					}
				}

				
				

				
			}else if(report.getTimeToScreen().equals("下半年")){


				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
				SimpleDateFormat formatz = new SimpleDateFormat("yyyy");
				
				String nowdate=format.format(new Date());//当前月份
				String nowdatez=formatz.format(new Date());//当前月份
				
				Date d1 = new SimpleDateFormat("yyyy-MM").parse(nowdatez+"-07");//定义起始日期
				
				Date d2 = new SimpleDateFormat("yyyy-MM").parse(nowdatez+"-12");//定义结束日期 可以去当前月也可以手动写日期。
				
				Calendar dd = Calendar.getInstance();//定义日期实例
				
				dd.setTime(d1);//设置日期起始时间
				
				while (dd.getTime().before(d2)) {
				
				SimpleDateFormat sdfzz = new SimpleDateFormat("yyyy-MM");

					String accDate = sdfzz.format(dd.getTime());
					report = new Report();
					BigDecimal xse=new BigDecimal(0);
					String sql = "";

					if(from.equals("全部")) {
						sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1 "+wSQL+" and sales_stage_name='赢单'  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
						ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						int i1=1;
						while (rs.next()) {
							report.setRanking(i1++);
							report.setToWinASingleAmount(rs.getBigDecimal(1));
							report.setDepartmentName(rs.getString(2));
							list.add(report);
						}
						dd.add(Calendar.MONTH, 1);//进行当前日期月份加1
					}else{
						if(from.equals("本部门")) {

							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
									sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1 "+wSQL+" and sales_stage_name='赢单' and department_id like'" + bmid + "%' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report.setRanking(i1++);
										report.setToWinASingleAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										list.add(report);
									}
						    	}
						    }
						    sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1 "+wSQL+" and sales_stage_name='赢单' and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
						    ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report.setRanking(i1++);
								report.setToWinASingleAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								list.add(report);
							}
							dd.add(Calendar.MONTH, 1);//进行当前日期月份加1
					        for (int i11 = 0; i11 < list.size() - 1; i11++) {
					            for (int j = i11 + 1; j < list.size(); j++) {
					                if (list.get(i11).equals(list.get(j))) {
					                    list.remove(j);
					                }
					            }
					        }
						}else if(from.equals("未设置")) {
						    sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1 "+wSQL+" and sales_stage_name='赢单' and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
						    ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report.setRanking(i1++);
								report.setToWinASingleAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								list.add(report);
							}
							dd.add(Calendar.MONTH, 1);//进行当前日期月份加1
						}else {

							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
									sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1 "+wSQL+" and sales_stage_name='赢单' and department_id like'" + bmid + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report.setRanking(i1++);
										report.setToWinASingleAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										list.add(report);
									}
						    	}
						    }
						    sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1 "+wSQL+" and sales_stage_name='赢单' and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
						    ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report.setRanking(i1++);
								report.setToWinASingleAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								list.add(report);
							}
							dd.add(Calendar.MONTH, 1);//进行当前日期月份加1
					        for (int i11 = 0; i11 < list.size() - 1; i11++) {
					            for (int j = i11 + 1; j < list.size(); j++) {
					                if (list.get(i11).equals(list.get(j))) {
					                    list.remove(j);
					                }
					            }
					        }
						}
					}

				}

				SimpleDateFormat sdfzz = new SimpleDateFormat("yyyy-MM");
				String accDate=sdfzz.format(d2.getTime());
				report = new Report();
				BigDecimal xse=new BigDecimal(0);
				String sql = "";

				if(from.equals("全部")) {
					sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1 "+wSQL+" and sales_stage_name='赢单'  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
					ps = conn.prepareStatement(sql);
					rs = ps.executeQuery();
					int i1=1;
					while (rs.next()) {
						report.setRanking(i1++);
						report.setToWinASingleAmount(rs.getBigDecimal(1));
						report.setDepartmentName(rs.getString(2));
						list.add(report);
					}
				}else{
					if(from.equals("本部门")) {

						String[] xh=departmentIds.split(",");
					    for(String bmid : xh){
					    	if(!bmid.equals("")){
								sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1 "+wSQL+" and sales_stage_name='赢单' and department_id like'" + bmid + "%' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report.setRanking(i1++);
									report.setToWinASingleAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
					    	}
					    }
					    sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1 "+wSQL+" and sales_stage_name='赢单' and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
					    ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						int i1=1;
						while (rs.next()) {
							report.setRanking(i1++);
							report.setToWinASingleAmount(rs.getBigDecimal(1));
							report.setDepartmentName(rs.getString(2));
							list.add(report);
						}
				        for (int i11 = 0; i11 < list.size() - 1; i11++) {
				            for (int j = i11 + 1; j < list.size(); j++) {
				                if (list.get(i11).equals(list.get(j))) {
				                    list.remove(j);
				                }
				            }
				        }
					}else if(from.equals("未设置")) {
					    sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1 "+wSQL+" and sales_stage_name='赢单' and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
					    ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						int i1=1;
						while (rs.next()) {
							report.setRanking(i1++);
							report.setToWinASingleAmount(rs.getBigDecimal(1));
							report.setDepartmentName(rs.getString(2));
							list.add(report);
						}
					}else {

						String[] xh=departmentIds.split(",");
					    for(String bmid : xh){
					    	if(!bmid.equals("")){
								sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1 "+wSQL+" and sales_stage_name='赢单' and department_id like'" + bmid + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report.setRanking(i1++);
									report.setToWinASingleAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
					    	}
					    }
					    sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1 "+wSQL+" and sales_stage_name='赢单' and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
					    ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						int i1=1;
						while (rs.next()) {
							report.setRanking(i1++);
							report.setToWinASingleAmount(rs.getBigDecimal(1));
							report.setDepartmentName(rs.getString(2));
							list.add(report);
						}
				        for (int i11 = 0; i11 < list.size() - 1; i11++) {
				            for (int j = i11 + 1; j < list.size(); j++) {
				                if (list.get(i11).equals(list.get(j))) {
				                    list.remove(j);
				                }
				            }
				        }
					}
				}

				
				

				
			} else {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
				String accDate=format.format(new Date());//当前月份
				report = new Report();
				BigDecimal xse=new BigDecimal(0);
				String sql = "";

				if(from.equals("全部")) {
					sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1 "+wSQL+" and sales_stage_name='赢单'  and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
					ps = conn.prepareStatement(sql);
					rs = ps.executeQuery();
					int i1=1;
					while (rs.next()) {
						report.setRanking(i1++);
						report.setToWinASingleAmount(rs.getBigDecimal(1));
						report.setDepartmentName(rs.getString(2));
						list.add(report);
					}
				}else{
					if(from.equals("本部门")) {
						String[] xh=departmentIds.split(",");
					    for(String bmid : xh){
					    	if(!bmid.equals("")){
								sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1 "+wSQL+" and sales_stage_name='赢单' and department_id like'" + bmid + "%' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report.setRanking(i1++);
									report.setToWinASingleAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
					    	}
					    }
					    sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1 "+wSQL+" and sales_stage_name='赢单' and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
					    ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						int i1=1;
						while (rs.next()) {
							report.setRanking(i1++);
							report.setToWinASingleAmount(rs.getBigDecimal(1));
							report.setDepartmentName(rs.getString(2));
							list.add(report);
						}
				        for (int i11 = 0; i11 < list.size() - 1; i11++) {
				            for (int j = i11 + 1; j < list.size(); j++) {
				                if (list.get(i11).equals(list.get(j))) {
				                    list.remove(j);
				                }
				            }
				        }
					}else if(from.equals("未设置")) {
					    sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1 "+wSQL+" and sales_stage_name='赢单' and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
					    ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						int i1=1;
						while (rs.next()) {
							report.setRanking(i1++);
							report.setToWinASingleAmount(rs.getBigDecimal(1));
							report.setDepartmentName(rs.getString(2));
							list.add(report);
						}
					}else {

						String[] xh=departmentIds.split(",");
					    for(String bmid : xh){
					    	if(!bmid.equals("")){
								sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1 "+wSQL+" and sales_stage_name='赢单' and department_id like'" + bmid + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report.setRanking(i1++);
									report.setToWinASingleAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									list.add(report);
								}
					    	}
					    }
					    sql = "SELECT SUM(estimated_amount),department_name from crm_business_opportunity where 1=1 "+wSQL+" and sales_stage_name='赢单' and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY department_name ORDER BY SUM(estimated_amount) desc";
					    ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						int i1=1;
						while (rs.next()) {
							report.setRanking(i1++);
							report.setToWinASingleAmount(rs.getBigDecimal(1));
							report.setDepartmentName(rs.getString(2));
							list.add(report);
						}
				        for (int i11 = 0; i11 < list.size() - 1; i11++) {
				            for (int j = i11 + 1; j < list.size(); j++) {
				                if (list.get(i11).equals(list.get(j))) {
				                    list.remove(j);
				                }
				            }
				        }
					}
				}
			}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("List<Report> getItemsyjmbpm(Report report)" + (t2 - t1) + " ms");
		} catch (SQLException | ParseException e) {
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		} finally {
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		}
		return list;
	}
	@Override
	public List<Report> getItemsPersonalHonor(Report report) {
		long t1 = System.currentTimeMillis();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		PreparedStatement ps3 = null;
		ResultSet rs3 = null;
		PreparedStatement ps4 = null;
		ResultSet rs4 = null;
		List<Report> list = new ArrayList<Report>();
		String mechanismId=report.getMechanismId();
		String from=report.getFrom() == null ? "" : report.getFrom();
		String departmentIds=report.getDepartmentIds();
		String username=report.getUsername();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);

			String wSQL = "";
			if(report.getUserId()!=null){
				if(report.getUserId().equals("")){
					
				}else{
					if (report.getUserId() != null || !report.getUserId().equals("")) {
						wSQL += " and create_id='" + report.getUserId() + "'";
					}
				}
			}
			if(from.equals("用户")) {
				wSQL += " ";
			}
			int i=0;
			if(report.getTimeToScreen()==null){
				report.setTimeToScreen("本月");
			}
			if(report.getResultsType()==null){
				report.setResultsType("合同金额");
			}
			if(report.getTimeToScreen().equals("上月")){

		        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		        Date date = new Date();
		        Calendar calendar = Calendar.getInstance();
		        calendar.setTime(date); // 设置为当前时间
		        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1); // 设置为上一个月
		        date = calendar.getTime();
		        String accDate = format.format(date);
				BigDecimal xse=new BigDecimal(0);
				if(report.getResultsType().equals("合同金额")){
					String sql = "";
						if(from.equals("全部")) {
							sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1  "+wSQL+" and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
						}else{
							if(from.equals("本部门")) {
								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1  "+wSQL+" and department_id like'" + bmid + "%' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("合同金额");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											report.setUsername(rs.getString(3));
											list.add(report);
										}
							    	}
							    }
							    sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
							}else if(from.equals("未设置")) {
							    sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
							}else {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1  "+wSQL+" and department_id like'" + bmid + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("合同金额");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											report.setUsername(rs.getString(3));
											list.add(report);
										}
							    	}
							    }
							    sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
							}
						}
				}else if(report.getResultsType().equals("合同回款金额")){
					String sql = "";
					if(from.equals("全部")) {
						sql = "SELECT SUM(collection_amount),department_name,create_name from crm_collection where 1=1  "+wSQL+" and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(collection_amount) desc";
						ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						int i1=1;
						while (rs.next()) {
							report = new Report();
							report.setRanking(i1++);
							report.setColumn("合同回款金额");
							report.setAggregateAmount(rs.getBigDecimal(1));
							report.setDepartmentName(rs.getString(2));
							report.setUsername(rs.getString(3));
							list.add(report);
						}
					}else{
						if(from.equals("本部门")) {
							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
									sql = "SELECT SUM(collection_amount),department_name,create_name from crm_collection where 1=1  "+wSQL+" and department_id like'" + bmid + "%' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(collection_amount) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("合同回款金额");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										report.setUsername(rs.getString(3));
										list.add(report);
									}
						    	}
						    }
						    sql = "SELECT SUM(collection_amount),department_name,create_name from crm_collection where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(collection_amount) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同回款金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
						}else if(from.equals("未设置")) {
						    sql = "SELECT SUM(collection_amount),department_name,create_name from crm_collection where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(collection_amount) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同回款金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
						}else {

							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
									sql = "SELECT SUM(collection_amount),department_name,create_name from crm_collection where 1=1  "+wSQL+" and department_id like'" + bmid + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(collection_amount) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("合同回款金额");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										report.setUsername(rs.getString(3));
										list.add(report);
									}
						    	}
						    }
						    sql = "SELECT SUM(collection_amount),department_name,create_name from crm_collection where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(collection_amount) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同回款金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
						}
					}
			}else if(report.getResultsType().equals("合同数")){
					String sql = "";
					if(from.equals("全部")) {
						sql = "SELECT COUNT(*),department_name,create_name from crm_contract where 1=1  "+wSQL+" and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY COUNT(*) desc";
						ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						int i1=1;
						while (rs.next()) {
							report = new Report();
							report.setRanking(i1++);
							report.setColumn("合同数");
							report.setAggregateAmount(rs.getBigDecimal(1));
							report.setDepartmentName(rs.getString(2));
							report.setUsername(rs.getString(3));
							list.add(report);
						}
					}else{
						if(from.equals("本部门")) {
							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
									sql = "SELECT COUNT(*),department_name,create_name from crm_contract where 1=1  "+wSQL+" and department_id like'" + bmid + "%' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY COUNT(*) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("合同数");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										report.setUsername(rs.getString(3));
										list.add(report);
									}
						    	}
						    }
						    sql = "SELECT COUNT(*),department_name,create_name from crm_contract where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY COUNT(*) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同数");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
						}else if(from.equals("未设置")) {
						    sql = "SELECT COUNT(*),department_name,create_name from crm_contract where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY COUNT(*) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同数");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
						}else {
							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
									sql = "SELECT COUNT(*),department_name,create_name from crm_contract where 1=1  "+wSQL+" and department_id like'" + bmid + "' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY COUNT(*) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("合同数");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										report.setUsername(rs.getString(3));
										list.add(report);
									}
						    	}
						    }
						    sql = "SELECT COUNT(*),department_name,create_name from crm_contract where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY COUNT(*) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同数");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
						}
					}
				}else if(report.getResultsType().equals("赢单商机数")){
					String sql = "";
						if(from.equals("全部")) {
							sql = "SELECT COUNT(*),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY COUNT(*) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("赢单商机数");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
						}else {
							if(from.equals("本部门")) {
								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT COUNT(*),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and department_id like'" + bmid + "%' and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY COUNT(*) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("赢单商机数");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											report.setUsername(rs.getString(3));
											list.add(report);
										}
							    	}
							    }
								sql = "SELECT COUNT(*),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY COUNT(*) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
							}else if(from.equals("未设置")) {
								sql = "SELECT COUNT(*),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY COUNT(*) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
							}else {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT COUNT(*),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and department_id like'" + bmid + "' and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY COUNT(*) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("赢单商机数");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											report.setUsername(rs.getString(3));
											list.add(report);
										}
							    	}
							    }
								sql = "SELECT COUNT(*),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY COUNT(*) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
							}
						}
				}else if(report.getResultsType().equals("赢单商机金额")){
					String sql = "";
					if(from.equals("全部")) {
						sql = "SELECT SUM(estimated_amount),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%'  and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY SUM(estimated_amount) desc";
						ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						int i1=1;
						while (rs.next()) {
							report = new Report();
							report.setRanking(i1++);
							report.setColumn("赢单商机金额");
							report.setAggregateAmount(rs.getBigDecimal(1));
							report.setDepartmentName(rs.getString(2));
							report.setUsername(rs.getString(3));
							list.add(report);
						}
					}else {
						if(from.equals("本部门")) {
							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
									sql = "SELECT SUM(estimated_amount),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY SUM(estimated_amount) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("赢单商机金额");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										report.setUsername(rs.getString(3));
										list.add(report);
									}
						    	}
						    }
							sql = "SELECT SUM(estimated_amount),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY SUM(estimated_amount) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("赢单商机金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
							
						}else if(from.equals("未设置")) {
							sql = "SELECT SUM(estimated_amount),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY SUM(estimated_amount) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("赢单商机金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
						}else {
							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
									sql = "SELECT SUM(estimated_amount),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "' and department_id like'" + bmid + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY SUM(estimated_amount) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("赢单商机金额");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										report.setUsername(rs.getString(3));
										list.add(report);
									}
						    	}
						    }
							sql = "SELECT SUM(estimated_amount),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY SUM(estimated_amount) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("赢单商机金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
							
						}
					}
				}
				
			}else if(report.getTimeToScreen().equals("下月")){

		        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		        Date date = new Date();
		        Calendar calendar = Calendar.getInstance();
		        calendar.setTime(date); // 设置为当前时间
		        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1); // 设置为上一个月
		        date = calendar.getTime();
		        String accDate = format.format(date);
				BigDecimal xse=new BigDecimal(0);

				if(report.getResultsType().equals("合同金额")){
					String sql = "";
						if(from.equals("全部")) {
							sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1  "+wSQL+" and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
						}else{
							if(from.equals("本部门")) {
								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1  "+wSQL+" and department_id like'" + bmid + "%' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("合同金额");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											report.setUsername(rs.getString(3));
											list.add(report);
										}
							    	}
							    }
							    sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
								
							}else if(from.equals("未设置")) {
							    sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
							}else {
								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1  "+wSQL+" and department_id like'" + bmid + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("合同金额");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											report.setUsername(rs.getString(3));
											list.add(report);
										}
							    	}
							    }
							    sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
								
							}
						}
				}else if(report.getResultsType().equals("合同回款金额")){
					String sql = "";
					if(from.equals("全部")) {
						sql = "SELECT SUM(collection_amount),department_name,create_name from crm_collection where 1=1  "+wSQL+" and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(collection_amount) desc";
						ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						int i1=1;
						while (rs.next()) {
							report = new Report();
							report.setRanking(i1++);
							report.setColumn("合同回款金额");
							report.setAggregateAmount(rs.getBigDecimal(1));
							report.setDepartmentName(rs.getString(2));
							report.setUsername(rs.getString(3));
							list.add(report);
						}
					}else{
						if(from.equals("本部门")) {
							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
									sql = "SELECT SUM(collection_amount),department_name,create_name from crm_collection where 1=1  "+wSQL+" and department_id like'" + bmid + "%' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(collection_amount) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("合同回款金额");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										report.setUsername(rs.getString(3));
										list.add(report);
									}
						    	}
						    }
						    sql = "SELECT SUM(collection_amount),department_name,create_name from crm_collection where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(collection_amount) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同回款金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
							
						}else if(from.equals("未设置")) {
						    sql = "SELECT SUM(collection_amount),department_name,create_name from crm_collection where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(collection_amount) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同回款金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
						}else {
							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
									sql = "SELECT SUM(collection_amount),department_name,create_name from crm_collection where 1=1  "+wSQL+" and department_id like'" + bmid + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(collection_amount) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("合同回款金额");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										report.setUsername(rs.getString(3));
										list.add(report);
									}
						    	}
						    }
						    sql = "SELECT SUM(collection_amount),department_name,create_name from crm_collection where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(collection_amount) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同回款金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
							
						}
					}
			}else if(report.getResultsType().equals("合同数")){
					String sql = "";
					if(from.equals("全部")) {
						sql = "SELECT COUNT(*),department_name,create_name from crm_contract where 1=1  "+wSQL+" and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY COUNT(*) desc";
						ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						int i1=1;
						while (rs.next()) {
							report = new Report();
							report.setRanking(i1++);
							report.setColumn("合同数");
							report.setAggregateAmount(rs.getBigDecimal(1));
							report.setDepartmentName(rs.getString(2));
							report.setUsername(rs.getString(3));
							list.add(report);
						}
					}else{
						if(from.equals("本部门")) {
							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
									sql = "SELECT COUNT(*),department_name,create_name from crm_contract where 1=1  "+wSQL+" and department_id like'" + bmid + "%' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY COUNT(*) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("合同数");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										report.setUsername(rs.getString(3));
										list.add(report);
									}
						    	}
						    }
						    sql = "SELECT COUNT(*),department_name,create_name from crm_contract where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY COUNT(*) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同数");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
							
						}else if(from.equals("未设置")) {
						    sql = "SELECT COUNT(*),department_name,create_name from crm_contract where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY COUNT(*) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同数");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
							
						}else {
							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
									sql = "SELECT COUNT(*),department_name,create_name from crm_contract where 1=1  "+wSQL+" and department_id like'" + bmid + "' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY COUNT(*) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("合同数");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										report.setUsername(rs.getString(3));
										list.add(report);
									}
						    	}
						    }
						    sql = "SELECT COUNT(*),department_name,create_name from crm_contract where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY COUNT(*) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同数");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
							
						}
					}
				}else if(report.getResultsType().equals("赢单商机数")){
					String sql = "";
						if(from.equals("全部")) {
							sql = "SELECT COUNT(*),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY COUNT(*) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("赢单商机数");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
						}else {
							if(from.equals("本部门")) {
								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT COUNT(*),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and department_id like'" + bmid + "%' and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY COUNT(*) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("赢单商机数");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											report.setUsername(rs.getString(3));
											list.add(report);
										}
							    	}
							    }
								sql = "SELECT COUNT(*),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY COUNT(*) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
								
							}else if(from.equals("未设置")) {
								sql = "SELECT COUNT(*),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY COUNT(*) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
								
							}else {
								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT COUNT(*),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and department_id like'" + bmid + "' and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY COUNT(*) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("赢单商机数");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											report.setUsername(rs.getString(3));
											list.add(report);
										}
							    	}
							    }
								sql = "SELECT COUNT(*),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY COUNT(*) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
								
							}
						}
				}else if(report.getResultsType().equals("赢单商机金额")){
					String sql = "";
					if(from.equals("全部")) {
						sql = "SELECT SUM(estimated_amount),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%'  and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY SUM(estimated_amount) desc";
						ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						int i1=1;
						while (rs.next()) {
							report = new Report();
							report.setRanking(i1++);
							report.setColumn("赢单商机金额");
							report.setAggregateAmount(rs.getBigDecimal(1));
							report.setDepartmentName(rs.getString(2));
							report.setUsername(rs.getString(3));
							list.add(report);
						}
					}else {
						if(from.equals("本部门")) {

							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
									sql = "SELECT SUM(estimated_amount),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY SUM(estimated_amount) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("赢单商机金额");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										report.setUsername(rs.getString(3));
										list.add(report);
									}
						    	}
						    }
							sql = "SELECT SUM(estimated_amount),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY SUM(estimated_amount) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("赢单商机金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
						}else if(from.equals("未设置")) {
							sql = "SELECT SUM(estimated_amount),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY SUM(estimated_amount) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("赢单商机金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
							
						}else {
							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
									sql = "SELECT SUM(estimated_amount),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY SUM(estimated_amount) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("赢单商机金额");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										report.setUsername(rs.getString(3));
										list.add(report);
									}
						    	}
						    }
							sql = "SELECT SUM(estimated_amount),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY SUM(estimated_amount) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("赢单商机金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
							
						}
					}
				}
				
			}else if(report.getTimeToScreen().equals("本年")){


				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
				SimpleDateFormat formatz = new SimpleDateFormat("yyyy");
				
				String nowdate=format.format(new Date());//当前月份
				String nowdatez=formatz.format(new Date());//当前月份
				
				Date d1 = new SimpleDateFormat("yyyy-MM").parse(nowdatez+"-01");//定义起始日期
				
				Date d2 = new SimpleDateFormat("yyyy-MM").parse(nowdatez+"-12");//定义结束日期 可以去当前月也可以手动写日期。
				
				Calendar dd = Calendar.getInstance();//定义日期实例
				
				dd.setTime(d1);//设置日期起始时间
				
				while (dd.getTime().before(d2)) {
				
				SimpleDateFormat sdfzz = new SimpleDateFormat("yyyy-MM");

					String accDate = sdfzz.format(dd.getTime());
					BigDecimal xse=new BigDecimal(0);

					if(report.getResultsType().equals("合同金额")){
						String sql = "";
							if(from.equals("全部")) {
								sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1  "+wSQL+" and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
							}else{
								if(from.equals("本部门")) {
									String[] xh=departmentIds.split(",");
								    for(String bmid : xh){
								    	if(!bmid.equals("")){
											sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1  "+wSQL+" and department_id like'" + bmid + "%' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
											ps = conn.prepareStatement(sql);
											rs = ps.executeQuery();
											int i1=1;
											while (rs.next()) {
												report = new Report();
												report.setRanking(i1++);
												report.setColumn("合同金额");
												report.setAggregateAmount(rs.getBigDecimal(1));
												report.setDepartmentName(rs.getString(2));
												report.setUsername(rs.getString(3));
												list.add(report);
											}
								    	}
								    }
								    sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("合同金额");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										report.setUsername(rs.getString(3));
										list.add(report);
									}
									
								}else if(from.equals("未设置")) {
								    sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("合同金额");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										report.setUsername(rs.getString(3));
										list.add(report);
									}
								}else {
									String[] xh=departmentIds.split(",");
								    for(String bmid : xh){
								    	if(!bmid.equals("")){
											sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1  "+wSQL+" and department_id like'" + bmid + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
											ps = conn.prepareStatement(sql);
											rs = ps.executeQuery();
											int i1=1;
											while (rs.next()) {
												report = new Report();
												report.setRanking(i1++);
												report.setColumn("合同金额");
												report.setAggregateAmount(rs.getBigDecimal(1));
												report.setDepartmentName(rs.getString(2));
												report.setUsername(rs.getString(3));
												list.add(report);
											}
								    	}
								    }
								    sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("合同金额");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										report.setUsername(rs.getString(3));
										list.add(report);
									}
									
								}
							}
					}else if(report.getResultsType().equals("合同回款金额")){
						String sql = "";
						if(from.equals("全部")) {
							sql = "SELECT SUM(collection_amount),department_name,create_name from crm_collection where 1=1  "+wSQL+" and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(collection_amount) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同回款金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
						}else{
							if(from.equals("本部门")) {
								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT SUM(collection_amount),department_name,create_name from crm_collection where 1=1  "+wSQL+" and department_id like'" + bmid + "%' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(collection_amount) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("合同回款金额");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											report.setUsername(rs.getString(3));
											list.add(report);
										}
							    	}
							    }
							    sql = "SELECT SUM(collection_amount),department_name,create_name from crm_collection where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(collection_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同回款金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
								
							}else if(from.equals("未设置")) {
							    sql = "SELECT SUM(collection_amount),department_name,create_name from crm_collection where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(collection_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同回款金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
							}else {
								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT SUM(collection_amount),department_name,create_name from crm_collection where 1=1  "+wSQL+" and department_id like'" + bmid + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(collection_amount) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("合同回款金额");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											report.setUsername(rs.getString(3));
											list.add(report);
										}
							    	}
							    }
							    sql = "SELECT SUM(collection_amount),department_name,create_name from crm_collection where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(collection_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同回款金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
								
							}
						}
				}else if(report.getResultsType().equals("合同数")){
						String sql = "";
						if(from.equals("全部")) {
							sql = "SELECT COUNT(*),department_name,create_name from crm_contract where 1=1  "+wSQL+" and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY COUNT(*) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同数");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
						}else{
							if(from.equals("本部门")) {
								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT COUNT(*),department_name,create_name from crm_contract where 1=1  "+wSQL+" and department_id like'" + bmid + "%' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY COUNT(*) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("合同数");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											report.setUsername(rs.getString(3));
											list.add(report);
										}
							    	}
							    }
							    sql = "SELECT COUNT(*),department_name,create_name from crm_contract where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY COUNT(*) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
								
							}else if(from.equals("未设置")) {
							    sql = "SELECT COUNT(*),department_name,create_name from crm_contract where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY COUNT(*) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
								
							}else {
								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT COUNT(*),department_name,create_name from crm_contract where 1=1  "+wSQL+" and department_id like'" + bmid + "' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY COUNT(*) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("合同数");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											report.setUsername(rs.getString(3));
											list.add(report);
										}
							    	}
							    }
							    sql = "SELECT COUNT(*),department_name,create_name from crm_contract where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY COUNT(*) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
								
							}
						}
					}else if(report.getResultsType().equals("赢单商机数")){
						String sql = "";
							if(from.equals("全部")) {
								sql = "SELECT COUNT(*),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY COUNT(*) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
							}else {
								if(from.equals("本部门")) {
									String[] xh=departmentIds.split(",");
								    for(String bmid : xh){
								    	if(!bmid.equals("")){
											sql = "SELECT COUNT(*),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and department_id like'" + bmid + "%' and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY COUNT(*) desc";
											ps = conn.prepareStatement(sql);
											rs = ps.executeQuery();
											int i1=1;
											while (rs.next()) {
												report = new Report();
												report.setRanking(i1++);
												report.setColumn("赢单商机数");
												report.setAggregateAmount(rs.getBigDecimal(1));
												report.setDepartmentName(rs.getString(2));
												report.setUsername(rs.getString(3));
												list.add(report);
											}
								    	}
								    }
									sql = "SELECT COUNT(*),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY COUNT(*) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("赢单商机数");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										report.setUsername(rs.getString(3));
										list.add(report);
									}
									
								}else if(from.equals("未设置")) {
									sql = "SELECT COUNT(*),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY COUNT(*) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("赢单商机数");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										report.setUsername(rs.getString(3));
										list.add(report);
									}
									
								}else {
									String[] xh=departmentIds.split(",");
								    for(String bmid : xh){
								    	if(!bmid.equals("")){
											sql = "SELECT COUNT(*),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and department_id like'" + bmid + "' and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY COUNT(*) desc";
											ps = conn.prepareStatement(sql);
											rs = ps.executeQuery();
											int i1=1;
											while (rs.next()) {
												report = new Report();
												report.setRanking(i1++);
												report.setColumn("赢单商机数");
												report.setAggregateAmount(rs.getBigDecimal(1));
												report.setDepartmentName(rs.getString(2));
												report.setUsername(rs.getString(3));
												list.add(report);
											}
								    	}
								    }
									sql = "SELECT COUNT(*),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY COUNT(*) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("赢单商机数");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										report.setUsername(rs.getString(3));
										list.add(report);
									}
									
								}
							}
					}else if(report.getResultsType().equals("赢单商机金额")){
						String sql = "";
						if(from.equals("全部")) {
							sql = "SELECT SUM(estimated_amount),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%'  and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY SUM(estimated_amount) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("赢单商机金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
						}else {
							if(from.equals("本部门")) {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT SUM(estimated_amount),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY SUM(estimated_amount) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("赢单商机金额");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											report.setUsername(rs.getString(3));
											list.add(report);
										}
							    	}
							    }
								sql = "SELECT SUM(estimated_amount),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY SUM(estimated_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
							}else if(from.equals("未设置")) {
								sql = "SELECT SUM(estimated_amount),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY SUM(estimated_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
							}else {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT SUM(estimated_amount),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY SUM(estimated_amount) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("赢单商机金额");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											report.setUsername(rs.getString(3));
											list.add(report);
										}
							    	}
							    }
								sql = "SELECT SUM(estimated_amount),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY SUM(estimated_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
							}
						}
					}
					
					dd.add(Calendar.MONTH, 1);//进行当前日期月份加1
				
				}

				SimpleDateFormat sdfzz = new SimpleDateFormat("yyyy-MM");
				String accDate=sdfzz.format(d2.getTime());
				BigDecimal xse=new BigDecimal(0);

				if(report.getResultsType().equals("合同金额")){
					String sql = "";
						if(from.equals("全部")) {
							sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1  "+wSQL+" and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
						}else{
							if(from.equals("本部门")) {
								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1  "+wSQL+" and department_id like'" + bmid + "%' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("合同金额");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											report.setUsername(rs.getString(3));
											list.add(report);
										}
							    	}
							    }
							    sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
							}else if(from.equals("未设置")) {
							    sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
								
							}else {
								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1  "+wSQL+" and department_id like'" + bmid + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("合同金额");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											report.setUsername(rs.getString(3));
											list.add(report);
										}
							    	}
							    }
							    sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
								
							}
						}
				}else if(report.getResultsType().equals("合同回款金额")){
					String sql = "";
					if(from.equals("全部")) {
						sql = "SELECT SUM(collection_amount),department_name,create_name from crm_collection where 1=1  "+wSQL+" and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(collection_amount) desc";
						ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						int i1=1;
						while (rs.next()) {
							report = new Report();
							report.setRanking(i1++);
							report.setColumn("合同回款金额");
							report.setAggregateAmount(rs.getBigDecimal(1));
							report.setDepartmentName(rs.getString(2));
							report.setUsername(rs.getString(3));
							list.add(report);
						}
					}else{
						if(from.equals("本部门")) {
							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
									sql = "SELECT SUM(collection_amount),department_name,create_name from crm_collection where 1=1  "+wSQL+" and department_id like'" + bmid + "%' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(collection_amount) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("合同回款金额");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										report.setUsername(rs.getString(3));
										list.add(report);
									}
						    	}
						    }
						    sql = "SELECT SUM(collection_amount),department_name,create_name from crm_collection where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(collection_amount) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同回款金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
						}else if(from.equals("未设置")) {
						    sql = "SELECT SUM(collection_amount),department_name,create_name from crm_collection where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(collection_amount) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同回款金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
							
						}else {
							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
									sql = "SELECT SUM(collection_amount),department_name,create_name from crm_collection where 1=1  "+wSQL+" and department_id like'" + bmid + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(collection_amount) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("合同回款金额");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										report.setUsername(rs.getString(3));
										list.add(report);
									}
						    	}
						    }
						    sql = "SELECT SUM(collection_amount),department_name,create_name from crm_collection where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(collection_amount) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同回款金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
							
						}
					}
			}else if(report.getResultsType().equals("合同数")){
					String sql = "";
					if(from.equals("全部")) {
						sql = "SELECT COUNT(*),department_name,create_name from crm_contract where 1=1  "+wSQL+" and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY COUNT(*) desc";
						ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						int i1=1;
						while (rs.next()) {
							report = new Report();
							report.setRanking(i1++);
							report.setColumn("合同数");
							report.setAggregateAmount(rs.getBigDecimal(1));
							report.setDepartmentName(rs.getString(2));
							report.setUsername(rs.getString(3));
							list.add(report);
						}
					}else{
						if(from.equals("本部门")) {
							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
									sql = "SELECT COUNT(*),department_name,create_name from crm_contract where 1=1  "+wSQL+" and department_id like'" + bmid + "%' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY COUNT(*) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("合同数");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										report.setUsername(rs.getString(3));
										list.add(report);
									}
						    	}
						    }
						    sql = "SELECT COUNT(*),department_name,create_name from crm_contract where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY COUNT(*) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同数");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
						}else if(from.equals("未设置")) {
						    sql = "SELECT COUNT(*),department_name,create_name from crm_contract where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY COUNT(*) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同数");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
						}else {

							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
									sql = "SELECT COUNT(*),department_name,create_name from crm_contract where 1=1  "+wSQL+" and department_id like'" + bmid + "' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY COUNT(*) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("合同数");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										report.setUsername(rs.getString(3));
										list.add(report);
									}
						    	}
						    }
						    sql = "SELECT COUNT(*),department_name,create_name from crm_contract where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY COUNT(*) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同数");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
						}
					}
				}else if(report.getResultsType().equals("赢单商机数")){
					String sql = "";
						if(from.equals("全部")) {
							sql = "SELECT COUNT(*),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY COUNT(*) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("赢单商机数");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
						}else {
							if(from.equals("本部门")) {
								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT COUNT(*),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and department_id like'" + bmid + "%' and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY COUNT(*) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("赢单商机数");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											report.setUsername(rs.getString(3));
											list.add(report);
										}
							    	}
							    }
								sql = "SELECT COUNT(*),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY COUNT(*) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
								
							}else if(from.equals("未设置")) {
								sql = "SELECT COUNT(*),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY COUNT(*) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
							}else {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT COUNT(*),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and department_id like'" + bmid + "' and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY COUNT(*) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("赢单商机数");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											report.setUsername(rs.getString(3));
											list.add(report);
										}
							    	}
							    }
								sql = "SELECT COUNT(*),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY COUNT(*) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
							}
						}
				}else if(report.getResultsType().equals("赢单商机金额")){
					String sql = "";
					if(from.equals("全部")) {
						sql = "SELECT SUM(estimated_amount),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%'  and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY SUM(estimated_amount) desc";
						ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						int i1=1;
						while (rs.next()) {
							report = new Report();
							report.setRanking(i1++);
							report.setColumn("赢单商机金额");
							report.setAggregateAmount(rs.getBigDecimal(1));
							report.setDepartmentName(rs.getString(2));
							report.setUsername(rs.getString(3));
							list.add(report);
						}
					}else {
						if(from.equals("本部门")) {
							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
									sql = "SELECT SUM(estimated_amount),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY SUM(estimated_amount) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("赢单商机金额");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										report.setUsername(rs.getString(3));
										list.add(report);
									}
						    	}
						    }
							sql = "SELECT SUM(estimated_amount),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY SUM(estimated_amount) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("赢单商机金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
							
						}else if(from.equals("未设置")) {
							sql = "SELECT SUM(estimated_amount),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY SUM(estimated_amount) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("赢单商机金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
						}else {

							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
									sql = "SELECT SUM(estimated_amount),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY SUM(estimated_amount) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("赢单商机金额");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										report.setUsername(rs.getString(3));
										list.add(report);
									}
						    	}
						    }
							sql = "SELECT SUM(estimated_amount),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY SUM(estimated_amount) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("赢单商机金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
						}
					}
				}
			
				
				

				
			}else if(report.getTimeToScreen().equals("上半年")){


				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
				SimpleDateFormat formatz = new SimpleDateFormat("yyyy");
				
				String nowdate=format.format(new Date());//当前月份
				String nowdatez=formatz.format(new Date());//当前月份
				
				Date d1 = new SimpleDateFormat("yyyy-MM").parse(nowdatez+"-01");//定义起始日期
				
				Date d2 = new SimpleDateFormat("yyyy-MM").parse(nowdatez+"-06");//定义结束日期 可以去当前月也可以手动写日期。
				
				Calendar dd = Calendar.getInstance();//定义日期实例
				
				dd.setTime(d1);//设置日期起始时间
				
				while (dd.getTime().before(d2)) {
				
				SimpleDateFormat sdfzz = new SimpleDateFormat("yyyy-MM");

					String accDate = sdfzz.format(dd.getTime());
					BigDecimal xse=new BigDecimal(0);

					if(report.getResultsType().equals("合同金额")){
						String sql = "";
							if(from.equals("全部")) {
								sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1  "+wSQL+" and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
							}else{
								if(from.equals("本部门")) {
									String[] xh=departmentIds.split(",");
								    for(String bmid : xh){
								    	if(!bmid.equals("")){
											sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1  "+wSQL+" and department_id like'" + bmid + "%' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
											ps = conn.prepareStatement(sql);
											rs = ps.executeQuery();
											int i1=1;
											while (rs.next()) {
												report = new Report();
												report.setRanking(i1++);
												report.setColumn("合同金额");
												report.setAggregateAmount(rs.getBigDecimal(1));
												report.setDepartmentName(rs.getString(2));
												report.setUsername(rs.getString(3));
												list.add(report);
											}
								    	}
								    }
								    sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("合同金额");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										report.setUsername(rs.getString(3));
										list.add(report);
									}
									
								}else if(from.equals("未设置")) {
								    sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("合同金额");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										report.setUsername(rs.getString(3));
										list.add(report);
									}
									
								}else {
									String[] xh=departmentIds.split(",");
								    for(String bmid : xh){
								    	if(!bmid.equals("")){
											sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1  "+wSQL+" and department_id like'" + bmid + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
											ps = conn.prepareStatement(sql);
											rs = ps.executeQuery();
											int i1=1;
											while (rs.next()) {
												report = new Report();
												report.setRanking(i1++);
												report.setColumn("合同金额");
												report.setAggregateAmount(rs.getBigDecimal(1));
												report.setDepartmentName(rs.getString(2));
												report.setUsername(rs.getString(3));
												list.add(report);
											}
								    	}
								    }
								    sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("合同金额");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										report.setUsername(rs.getString(3));
										list.add(report);
									}
									
								}
							}
					}else if(report.getResultsType().equals("合同回款金额")){
						String sql = "";
						if(from.equals("全部")) {
							sql = "SELECT SUM(collection_amount),department_name,create_name from crm_collection where 1=1  "+wSQL+" and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(collection_amount) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同回款金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
						}else{
							if(from.equals("本部门")) {
								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT SUM(collection_amount),department_name,create_name from crm_collection where 1=1  "+wSQL+" and department_id like'" + bmid + "%' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(collection_amount) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("合同回款金额");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											report.setUsername(rs.getString(3));
											list.add(report);
										}
							    	}
							    }
							    sql = "SELECT SUM(collection_amount),department_name,create_name from crm_collection where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(collection_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同回款金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
								
							}else if(from.equals("未设置")) {
							    sql = "SELECT SUM(collection_amount),department_name,create_name from crm_collection where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(collection_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同回款金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
								
							}else {
								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT SUM(collection_amount),department_name,create_name from crm_collection where 1=1  "+wSQL+" and department_id like'" + bmid + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(collection_amount) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("合同回款金额");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											report.setUsername(rs.getString(3));
											list.add(report);
										}
							    	}
							    }
							    sql = "SELECT SUM(collection_amount),department_name,create_name from crm_collection where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(collection_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同回款金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
								
							}
						}
				}else if(report.getResultsType().equals("合同数")){
						String sql = "";
						if(from.equals("全部")) {
							sql = "SELECT COUNT(*),department_name,create_name from crm_contract where 1=1  "+wSQL+" and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY COUNT(*) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同数");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
						}else{
							if(from.equals("本部门")) {
								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT COUNT(*),department_name,create_name from crm_contract where 1=1  "+wSQL+" and department_id like'" + bmid + "%' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY COUNT(*) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("合同数");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											report.setUsername(rs.getString(3));
											list.add(report);
										}
							    	}
							    }
							    sql = "SELECT COUNT(*),department_name,create_name from crm_contract where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY COUNT(*) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
								
							}else if(from.equals("未设置")) {
							    sql = "SELECT COUNT(*),department_name,create_name from crm_contract where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY COUNT(*) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
								
							}else {
								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT COUNT(*),department_name,create_name from crm_contract where 1=1  "+wSQL+" and department_id like'" + bmid + "' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY COUNT(*) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("合同数");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											report.setUsername(rs.getString(3));
											list.add(report);
										}
							    	}
							    }
							    sql = "SELECT COUNT(*),department_name,create_name from crm_contract where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY COUNT(*) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
								
							}
						}
					}else if(report.getResultsType().equals("赢单商机数")){
						String sql = "";
							if(from.equals("全部")) {
								sql = "SELECT COUNT(*),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY COUNT(*) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
							}else {
								if(from.equals("本部门")) {
									String[] xh=departmentIds.split(",");
								    for(String bmid : xh){
								    	if(!bmid.equals("")){
											sql = "SELECT COUNT(*),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and department_id like'" + bmid + "%' and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY COUNT(*) desc";
											ps = conn.prepareStatement(sql);
											rs = ps.executeQuery();
											int i1=1;
											while (rs.next()) {
												report = new Report();
												report.setRanking(i1++);
												report.setColumn("赢单商机数");
												report.setAggregateAmount(rs.getBigDecimal(1));
												report.setDepartmentName(rs.getString(2));
												report.setUsername(rs.getString(3));
												list.add(report);
											}
								    	}
								    }
									sql = "SELECT COUNT(*),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY COUNT(*) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("赢单商机数");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										report.setUsername(rs.getString(3));
										list.add(report);
									}
									
								}else if(from.equals("未设置")) {
									sql = "SELECT COUNT(*),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY COUNT(*) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("赢单商机数");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										report.setUsername(rs.getString(3));
										list.add(report);
									}
								}else {

									String[] xh=departmentIds.split(",");
								    for(String bmid : xh){
								    	if(!bmid.equals("")){
											sql = "SELECT COUNT(*),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and department_id like'" + bmid + "' and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY COUNT(*) desc";
											ps = conn.prepareStatement(sql);
											rs = ps.executeQuery();
											int i1=1;
											while (rs.next()) {
												report = new Report();
												report.setRanking(i1++);
												report.setColumn("赢单商机数");
												report.setAggregateAmount(rs.getBigDecimal(1));
												report.setDepartmentName(rs.getString(2));
												report.setUsername(rs.getString(3));
												list.add(report);
											}
								    	}
								    }
									sql = "SELECT COUNT(*),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY COUNT(*) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("赢单商机数");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										report.setUsername(rs.getString(3));
										list.add(report);
									}
								}
							}
					}else if(report.getResultsType().equals("赢单商机金额")){
						String sql = "";
						if(from.equals("全部")) {
							sql = "SELECT SUM(estimated_amount),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%'  and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY SUM(estimated_amount) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("赢单商机金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
						}else {
							if(from.equals("本部门")) {
								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT SUM(estimated_amount),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY SUM(estimated_amount) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("赢单商机金额");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											report.setUsername(rs.getString(3));
											list.add(report);
										}
							    	}
							    }
								sql = "SELECT SUM(estimated_amount),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY SUM(estimated_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
								
							}else if(from.equals("未设置")) {
								sql = "SELECT SUM(estimated_amount),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY SUM(estimated_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
								
							}else {
								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT SUM(estimated_amount),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY SUM(estimated_amount) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("赢单商机金额");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											report.setUsername(rs.getString(3));
											list.add(report);
										}
							    	}
							    }
								sql = "SELECT SUM(estimated_amount),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY SUM(estimated_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
								
							}
						}
					}
					dd.add(Calendar.MONTH, 1);//进行当前日期月份加1
				
				}

				SimpleDateFormat sdfzz = new SimpleDateFormat("yyyy-MM");
				String accDate=sdfzz.format(d2.getTime());
				BigDecimal xse=new BigDecimal(0);

				if(report.getResultsType().equals("合同金额")){
					String sql = "";
						if(from.equals("全部")) {
							sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1  "+wSQL+" and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
						}else{
							if(from.equals("本部门")) {
								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1  "+wSQL+" and department_id like'" + bmid + "%' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("合同金额");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											report.setUsername(rs.getString(3));
											list.add(report);
										}
							    	}
							    }
							    sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
								
							}else if(from.equals("未设置")) {
							    sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
								
							}else {
								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1  "+wSQL+" and department_id like'" + bmid + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("合同金额");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											report.setUsername(rs.getString(3));
											list.add(report);
										}
							    	}
							    }
							    sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
								
							}
						}
				}else if(report.getResultsType().equals("合同回款金额")){
					String sql = "";
					if(from.equals("全部")) {
						sql = "SELECT SUM(collection_amount),department_name,create_name from crm_collection where 1=1  "+wSQL+" and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(collection_amount) desc";
						ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						int i1=1;
						while (rs.next()) {
							report = new Report();
							report.setRanking(i1++);
							report.setColumn("合同回款金额");
							report.setAggregateAmount(rs.getBigDecimal(1));
							report.setDepartmentName(rs.getString(2));
							report.setUsername(rs.getString(3));
							list.add(report);
						}
					}else{
						if(from.equals("本部门")) {
							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
									sql = "SELECT SUM(collection_amount),department_name,create_name from crm_collection where 1=1  "+wSQL+" and department_id like'" + bmid + "%' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(collection_amount) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("合同回款金额");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										report.setUsername(rs.getString(3));
										list.add(report);
									}
						    	}
						    }
						    sql = "SELECT SUM(collection_amount),department_name,create_name from crm_collection where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(collection_amount) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同回款金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
					        for (int i11 = 0; i11 < list.size() - 1; i11++) {
					            for (int j = i11 + 1; j < list.size(); j++) {
					                if (list.get(i11).equals(list.get(j))) {
					                    list.remove(j);
					                }
					            }
					        }
							
						}else if(from.equals("未设置")) {
						    sql = "SELECT SUM(collection_amount),department_name,create_name from crm_collection where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(collection_amount) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同回款金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
							
						}else {
							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
									sql = "SELECT SUM(collection_amount),department_name,create_name from crm_collection where 1=1  "+wSQL+" and department_id like'" + bmid + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(collection_amount) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("合同回款金额");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										report.setUsername(rs.getString(3));
										list.add(report);
									}
						    	}
						    }
						    sql = "SELECT SUM(collection_amount),department_name,create_name from crm_collection where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(collection_amount) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同回款金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
					        for (int i11 = 0; i11 < list.size() - 1; i11++) {
					            for (int j = i11 + 1; j < list.size(); j++) {
					                if (list.get(i11).equals(list.get(j))) {
					                    list.remove(j);
					                }
					            }
					        }
							
						}
					}
			}else if(report.getResultsType().equals("合同数")){
					String sql = "";
					if(from.equals("全部")) {
						sql = "SELECT COUNT(*),department_name,create_name from crm_contract where 1=1  "+wSQL+" and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY COUNT(*) desc";
						ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						int i1=1;
						while (rs.next()) {
							report = new Report();
							report.setRanking(i1++);
							report.setColumn("合同数");
							report.setAggregateAmount(rs.getBigDecimal(1));
							report.setDepartmentName(rs.getString(2));
							report.setUsername(rs.getString(3));
							list.add(report);
						}
					}else{
						if(from.equals("本部门")) {
							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
									sql = "SELECT COUNT(*),department_name,create_name from crm_contract where 1=1  "+wSQL+" and department_id like'" + bmid + "%' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY COUNT(*) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("合同数");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										report.setUsername(rs.getString(3));
										list.add(report);
									}
						    	}
						    }
						    sql = "SELECT COUNT(*),department_name,create_name from crm_contract where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY COUNT(*) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同数");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
					        for (int i11 = 0; i11 < list.size() - 1; i11++) {
					            for (int j = i11 + 1; j < list.size(); j++) {
					                if (list.get(i11).equals(list.get(j))) {
					                    list.remove(j);
					                }
					            }
					        }
							
						}else if(from.equals("未设置")) {
						    sql = "SELECT COUNT(*),department_name,create_name from crm_contract where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY COUNT(*) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同数");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
							
						}else {
							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
									sql = "SELECT COUNT(*),department_name,create_name from crm_contract where 1=1  "+wSQL+" and department_id like'" + bmid + "' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY COUNT(*) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("合同数");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										report.setUsername(rs.getString(3));
										list.add(report);
									}
						    	}
						    }
						    sql = "SELECT COUNT(*),department_name,create_name from crm_contract where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY COUNT(*) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同数");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
					        for (int i11 = 0; i11 < list.size() - 1; i11++) {
					            for (int j = i11 + 1; j < list.size(); j++) {
					                if (list.get(i11).equals(list.get(j))) {
					                    list.remove(j);
					                }
					            }
					        }
							
						}
					}
				}else if(report.getResultsType().equals("赢单商机数")){
					String sql = "";
						if(from.equals("全部")) {
							sql = "SELECT COUNT(*),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY COUNT(*) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("赢单商机数");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
						}else {
							if(from.equals("本部门")) {
								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT COUNT(*),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and department_id like'" + bmid + "%' and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY COUNT(*) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("赢单商机数");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											report.setUsername(rs.getString(3));
											list.add(report);
										}
							    	}
							    }
								sql = "SELECT COUNT(*),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY COUNT(*) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
								
							}else if(from.equals("未设置")) {
								sql = "SELECT COUNT(*),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY COUNT(*) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
								
							}else {
								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT COUNT(*),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and department_id like'" + bmid + "' and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY COUNT(*) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("赢单商机数");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											report.setUsername(rs.getString(3));
											list.add(report);
										}
							    	}
							    }
								sql = "SELECT COUNT(*),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY COUNT(*) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
								
							}
						}
				}else if(report.getResultsType().equals("赢单商机金额")){
					String sql = "";
					if(from.equals("全部")) {
						sql = "SELECT SUM(estimated_amount),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%'  and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY SUM(estimated_amount) desc";
						ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						int i1=1;
						while (rs.next()) {
							report = new Report();
							report.setRanking(i1++);
							report.setColumn("赢单商机金额");
							report.setAggregateAmount(rs.getBigDecimal(1));
							report.setDepartmentName(rs.getString(2));
							report.setUsername(rs.getString(3));
							list.add(report);
						}
					}else {
						if(from.equals("本部门")) {
							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
									sql = "SELECT SUM(estimated_amount),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY SUM(estimated_amount) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("赢单商机金额");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										report.setUsername(rs.getString(3));
										list.add(report);
									}
						    	}
						    }
							sql = "SELECT SUM(estimated_amount),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY SUM(estimated_amount) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("赢单商机金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
					        for (int i11 = 0; i11 < list.size() - 1; i11++) {
					            for (int j = i11 + 1; j < list.size(); j++) {
					                if (list.get(i11).equals(list.get(j))) {
					                    list.remove(j);
					                }
					            }
					        }
							
						}else if(from.equals("未设置")) {
							sql = "SELECT SUM(estimated_amount),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY SUM(estimated_amount) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("赢单商机金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
							
						}else {
							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
									sql = "SELECT SUM(estimated_amount),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY SUM(estimated_amount) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("赢单商机金额");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										report.setUsername(rs.getString(3));
										list.add(report);
									}
						    	}
						    }
							sql = "SELECT SUM(estimated_amount),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY SUM(estimated_amount) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("赢单商机金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
					        for (int i11 = 0; i11 < list.size() - 1; i11++) {
					            for (int j = i11 + 1; j < list.size(); j++) {
					                if (list.get(i11).equals(list.get(j))) {
					                    list.remove(j);
					                }
					            }
					        }
							
						}
					}
				}
			
				
				

				
			}else if(report.getTimeToScreen().equals("下半年")){


				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
				SimpleDateFormat formatz = new SimpleDateFormat("yyyy");
				
				String nowdate=format.format(new Date());//当前月份
				String nowdatez=formatz.format(new Date());//当前月份
				
				Date d1 = new SimpleDateFormat("yyyy-MM").parse(nowdatez+"-07");//定义起始日期
				
				Date d2 = new SimpleDateFormat("yyyy-MM").parse(nowdatez+"-12");//定义结束日期 可以去当前月也可以手动写日期。
				
				Calendar dd = Calendar.getInstance();//定义日期实例
				
				dd.setTime(d1);//设置日期起始时间
				
				while (dd.getTime().before(d2)) {
				
				SimpleDateFormat sdfzz = new SimpleDateFormat("yyyy-MM");

					String accDate = sdfzz.format(dd.getTime());
					BigDecimal xse=new BigDecimal(0);

					if(report.getResultsType().equals("合同金额")){
						String sql = "";
							if(from.equals("全部")) {
								sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1  "+wSQL+" and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
							}else{
								if(from.equals("本部门")) {
									String[] xh=departmentIds.split(",");
								    for(String bmid : xh){
								    	if(!bmid.equals("")){
											sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1  "+wSQL+" and department_id like'" + bmid + "%' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
											ps = conn.prepareStatement(sql);
											rs = ps.executeQuery();
											int i1=1;
											while (rs.next()) {
												report = new Report();
												report.setRanking(i1++);
												report.setColumn("合同金额");
												report.setAggregateAmount(rs.getBigDecimal(1));
												report.setDepartmentName(rs.getString(2));
												report.setUsername(rs.getString(3));
												list.add(report);
											}
								    	}
								    }
								    sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("合同金额");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										report.setUsername(rs.getString(3));
										list.add(report);
									}
							        for (int i11 = 0; i11 < list.size() - 1; i11++) {
							            for (int j = i11 + 1; j < list.size(); j++) {
							                if (list.get(i11).equals(list.get(j))) {
							                    list.remove(j);
							                }
							            }
							        }
									
								}else if(from.equals("未设置")) {
								    sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("合同金额");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										report.setUsername(rs.getString(3));
										list.add(report);
									}
									
								}else {
									String[] xh=departmentIds.split(",");
								    for(String bmid : xh){
								    	if(!bmid.equals("")){
											sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1  "+wSQL+" and department_id like'" + bmid + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
											ps = conn.prepareStatement(sql);
											rs = ps.executeQuery();
											int i1=1;
											while (rs.next()) {
												report = new Report();
												report.setRanking(i1++);
												report.setColumn("合同金额");
												report.setAggregateAmount(rs.getBigDecimal(1));
												report.setDepartmentName(rs.getString(2));
												report.setUsername(rs.getString(3));
												list.add(report);
											}
								    	}
								    }
								    sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("合同金额");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										report.setUsername(rs.getString(3));
										list.add(report);
									}
							        for (int i11 = 0; i11 < list.size() - 1; i11++) {
							            for (int j = i11 + 1; j < list.size(); j++) {
							                if (list.get(i11).equals(list.get(j))) {
							                    list.remove(j);
							                }
							            }
							        }
								}
							}
					}else if(report.getResultsType().equals("合同回款金额")){
						String sql = "";
						if(from.equals("全部")) {
							sql = "SELECT SUM(collection_amount),department_name,create_name from crm_collection where 1=1  "+wSQL+" and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(collection_amount) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同回款金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
						}else{
							if(from.equals("本部门")) {
								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT SUM(collection_amount),department_name,create_name from crm_collection where 1=1  "+wSQL+" and department_id like'" + bmid + "%' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(collection_amount) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("合同回款金额");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											report.setUsername(rs.getString(3));
											list.add(report);
										}
							    	}
							    }
							    sql = "SELECT SUM(collection_amount),department_name,create_name from crm_collection where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(collection_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同回款金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
								
							}else if(from.equals("未设置")) {
							    sql = "SELECT SUM(collection_amount),department_name,create_name from crm_collection where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(collection_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同回款金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
								
							}else {
								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT SUM(collection_amount),department_name,create_name from crm_collection where 1=1  "+wSQL+" and department_id like'" + bmid + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(collection_amount) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("合同回款金额");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											report.setUsername(rs.getString(3));
											list.add(report);
										}
							    	}
							    }
							    sql = "SELECT SUM(collection_amount),department_name,create_name from crm_collection where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(collection_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同回款金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
							}
						}
				}else if(report.getResultsType().equals("合同数")){
						String sql = "";
						if(from.equals("全部")) {
							sql = "SELECT COUNT(*),department_name,create_name from crm_contract where 1=1  "+wSQL+" and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY COUNT(*) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同数");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
						}else{
							if(from.equals("本部门")) {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT COUNT(*),department_name,create_name from crm_contract where 1=1  "+wSQL+" and department_id like'" + bmid + "%' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY COUNT(*) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("合同数");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											report.setUsername(rs.getString(3));
											list.add(report);
										}
							    	}
							    }
							    sql = "SELECT COUNT(*),department_name,create_name from crm_contract where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY COUNT(*) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
							}else if(from.equals("未设置")) {
							    sql = "SELECT COUNT(*),department_name,create_name from crm_contract where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY COUNT(*) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
							}else {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT COUNT(*),department_name,create_name from crm_contract where 1=1  "+wSQL+" and department_id like'" + bmid + "' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY COUNT(*) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("合同数");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											report.setUsername(rs.getString(3));
											list.add(report);
										}
							    	}
							    }
							    sql = "SELECT COUNT(*),department_name,create_name from crm_contract where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY COUNT(*) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
							}
						}
					}else if(report.getResultsType().equals("赢单商机数")){
						String sql = "";
							if(from.equals("全部")) {
								sql = "SELECT COUNT(*),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY COUNT(*) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
							}else {
								if(from.equals("本部门")) {

									String[] xh=departmentIds.split(",");
								    for(String bmid : xh){
								    	if(!bmid.equals("")){
											sql = "SELECT COUNT(*),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and department_id like'" + bmid + "%' and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY COUNT(*) desc";
											ps = conn.prepareStatement(sql);
											rs = ps.executeQuery();
											int i1=1;
											while (rs.next()) {
												report = new Report();
												report.setRanking(i1++);
												report.setColumn("赢单商机数");
												report.setAggregateAmount(rs.getBigDecimal(1));
												report.setDepartmentName(rs.getString(2));
												report.setUsername(rs.getString(3));
												list.add(report);
											}
								    	}
								    }
									sql = "SELECT COUNT(*),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY COUNT(*) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("赢单商机数");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										report.setUsername(rs.getString(3));
										list.add(report);
									}
							        for (int i11 = 0; i11 < list.size() - 1; i11++) {
							            for (int j = i11 + 1; j < list.size(); j++) {
							                if (list.get(i11).equals(list.get(j))) {
							                    list.remove(j);
							                }
							            }
							        }
								}else if(from.equals("未设置")) {
									sql = "SELECT COUNT(*),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY COUNT(*) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("赢单商机数");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										report.setUsername(rs.getString(3));
										list.add(report);
									}
								}else {

									String[] xh=departmentIds.split(",");
								    for(String bmid : xh){
								    	if(!bmid.equals("")){
											sql = "SELECT COUNT(*),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and department_id like'" + bmid + "' and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY COUNT(*) desc";
											ps = conn.prepareStatement(sql);
											rs = ps.executeQuery();
											int i1=1;
											while (rs.next()) {
												report = new Report();
												report.setRanking(i1++);
												report.setColumn("赢单商机数");
												report.setAggregateAmount(rs.getBigDecimal(1));
												report.setDepartmentName(rs.getString(2));
												report.setUsername(rs.getString(3));
												list.add(report);
											}
								    	}
								    }
									sql = "SELECT COUNT(*),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY COUNT(*) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("赢单商机数");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										report.setUsername(rs.getString(3));
										list.add(report);
									}
							        for (int i11 = 0; i11 < list.size() - 1; i11++) {
							            for (int j = i11 + 1; j < list.size(); j++) {
							                if (list.get(i11).equals(list.get(j))) {
							                    list.remove(j);
							                }
							            }
							        }
								}
							}
					}else if(report.getResultsType().equals("赢单商机金额")){
						String sql = "";
						if(from.equals("全部")) {
							sql = "SELECT SUM(estimated_amount),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%'  and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY SUM(estimated_amount) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("赢单商机金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
						}else {
							if(from.equals("本部门")) {
								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT SUM(estimated_amount),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY SUM(estimated_amount) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("赢单商机金额");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											report.setUsername(rs.getString(3));
											list.add(report);
										}
							    	}
							    }
								sql = "SELECT SUM(estimated_amount),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY SUM(estimated_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
								
							}else if(from.equals("未设置")) {
 								sql = "SELECT SUM(estimated_amount),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY SUM(estimated_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
							}else {
								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT SUM(estimated_amount),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY SUM(estimated_amount) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("赢单商机金额");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											report.setUsername(rs.getString(3));
											list.add(report);
										}
							    	}
							    }
								sql = "SELECT SUM(estimated_amount),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY SUM(estimated_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
								
							}
						}
					}
					dd.add(Calendar.MONTH, 1);//进行当前日期月份加1
				
				}

				SimpleDateFormat sdfzz = new SimpleDateFormat("yyyy-MM");
				String accDate=sdfzz.format(d2.getTime());
				BigDecimal xse=new BigDecimal(0);
				

				if(report.getResultsType().equals("合同金额")){
					String sql = "";
						if(from.equals("全部")) {
							sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1  "+wSQL+" and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
						}else{
							if(from.equals("本部门")) {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1  "+wSQL+" and department_id like'" + bmid + "%' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("合同金额");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											report.setUsername(rs.getString(3));
											list.add(report);
										}
							    	}
							    }
							    sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
							}else if(from.equals("未设置")) {
							    sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
							}else {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1  "+wSQL+" and department_id like'" + bmid + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("合同金额");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											report.setUsername(rs.getString(3));
											list.add(report);
										}
							    	}
							    }
							    sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
							}
						}
				}else if(report.getResultsType().equals("合同回款金额")){
					String sql = "";
					if(from.equals("全部")) {
						sql = "SELECT SUM(collection_amount),department_name,create_name from crm_collection where 1=1  "+wSQL+" and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(collection_amount) desc";
						ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						int i1=1;
						while (rs.next()) {
							report = new Report();
							report.setRanking(i1++);
							report.setColumn("合同回款金额");
							report.setAggregateAmount(rs.getBigDecimal(1));
							report.setDepartmentName(rs.getString(2));
							report.setUsername(rs.getString(3));
							list.add(report);
						}
					}else{
						if(from.equals("本部门")) {

							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
									sql = "SELECT SUM(collection_amount),department_name,create_name from crm_collection where 1=1  "+wSQL+" and department_id like'" + bmid + "%' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(collection_amount) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("合同回款金额");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										report.setUsername(rs.getString(3));
										list.add(report);
									}
						    	}
						    }
						    sql = "SELECT SUM(collection_amount),department_name,create_name from crm_collection where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(collection_amount) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同回款金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
					        for (int i11 = 0; i11 < list.size() - 1; i11++) {
					            for (int j = i11 + 1; j < list.size(); j++) {
					                if (list.get(i11).equals(list.get(j))) {
					                    list.remove(j);
					                }
					            }
					        }
						}else if(from.equals("未设置")) {
						    sql = "SELECT SUM(collection_amount),department_name,create_name from crm_collection where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(collection_amount) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同回款金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
						}else {

							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
									sql = "SELECT SUM(collection_amount),department_name,create_name from crm_collection where 1=1  "+wSQL+" and department_id like'" + bmid + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(collection_amount) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("合同回款金额");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										report.setUsername(rs.getString(3));
										list.add(report);
									}
						    	}
						    }
						    sql = "SELECT SUM(collection_amount),department_name,create_name from crm_collection where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(collection_amount) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同回款金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
					        for (int i11 = 0; i11 < list.size() - 1; i11++) {
					            for (int j = i11 + 1; j < list.size(); j++) {
					                if (list.get(i11).equals(list.get(j))) {
					                    list.remove(j);
					                }
					            }
					        }
						}
					}
			}else if(report.getResultsType().equals("合同数")){
					String sql = "";
					if(from.equals("全部")) {
						sql = "SELECT COUNT(*),department_name,create_name from crm_contract where 1=1  "+wSQL+" and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY COUNT(*) desc";
						ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						int i1=1;
						while (rs.next()) {
							report = new Report();
							report.setRanking(i1++);
							report.setColumn("合同数");
							report.setAggregateAmount(rs.getBigDecimal(1));
							report.setDepartmentName(rs.getString(2));
							report.setUsername(rs.getString(3));
							list.add(report);
						}
					}else{
						if(from.equals("本部门")) {
							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
									sql = "SELECT COUNT(*),department_name,create_name from crm_contract where 1=1  "+wSQL+" and department_id like'" + bmid + "%' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY COUNT(*) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("合同数");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										report.setUsername(rs.getString(3));
										list.add(report);
									}
						    	}
						    }
						    sql = "SELECT COUNT(*),department_name,create_name from crm_contract where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY COUNT(*) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同数");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
					        for (int i11 = 0; i11 < list.size() - 1; i11++) {
					            for (int j = i11 + 1; j < list.size(); j++) {
					                if (list.get(i11).equals(list.get(j))) {
					                    list.remove(j);
					                }
					            }
					        }
							
						}else if(from.equals("未设置")) {
						    sql = "SELECT COUNT(*),department_name,create_name from crm_contract where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY COUNT(*) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同数");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
							
						}else {

							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
									sql = "SELECT COUNT(*),department_name,create_name from crm_contract where 1=1  "+wSQL+" and department_id like'" + bmid + "' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY COUNT(*) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("合同数");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										report.setUsername(rs.getString(3));
										list.add(report);
									}
						    	}
						    }
						    sql = "SELECT COUNT(*),department_name,create_name from crm_contract where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY COUNT(*) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同数");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
					        for (int i11 = 0; i11 < list.size() - 1; i11++) {
					            for (int j = i11 + 1; j < list.size(); j++) {
					                if (list.get(i11).equals(list.get(j))) {
					                    list.remove(j);
					                }
					            }
					        }
						}
					}
				}else if(report.getResultsType().equals("赢单商机数")){
					String sql = "";
						if(from.equals("全部")) {
							sql = "SELECT COUNT(*),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY COUNT(*) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("赢单商机数");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
						}else {
							if(from.equals("本部门")) {
								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT COUNT(*),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and department_id like'" + bmid + "%' and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY COUNT(*) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("赢单商机数");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											report.setUsername(rs.getString(3));
											list.add(report);
										}
							    	}
							    }
								sql = "SELECT COUNT(*),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY COUNT(*) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
								
							}else if(from.equals("未设置")) {
								sql = "SELECT COUNT(*),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY COUNT(*) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
								
							}else {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT COUNT(*),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and department_id like'" + bmid + "' and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY COUNT(*) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("赢单商机数");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											report.setUsername(rs.getString(3));
											list.add(report);
										}
							    	}
							    }
								sql = "SELECT COUNT(*),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY COUNT(*) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
							}
						}
				}else if(report.getResultsType().equals("赢单商机金额")){
					String sql = "";
					if(from.equals("全部")) {
						sql = "SELECT SUM(estimated_amount),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%'  and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY SUM(estimated_amount) desc";
						ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						int i1=1;
						while (rs.next()) {
							report = new Report();
							report.setRanking(i1++);
							report.setColumn("赢单商机金额");
							report.setAggregateAmount(rs.getBigDecimal(1));
							report.setDepartmentName(rs.getString(2));
							report.setUsername(rs.getString(3));
							list.add(report);
						}
					}else {
						if(from.equals("本部门")) {
							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
									sql = "SELECT SUM(estimated_amount),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY SUM(estimated_amount) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("赢单商机金额");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										report.setUsername(rs.getString(3));
										list.add(report);
									}
						    	}
						    }
							sql = "SELECT SUM(estimated_amount),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY SUM(estimated_amount) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("赢单商机金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
					        for (int i11 = 0; i11 < list.size() - 1; i11++) {
					            for (int j = i11 + 1; j < list.size(); j++) {
					                if (list.get(i11).equals(list.get(j))) {
					                    list.remove(j);
					                }
					            }
					        }
							
						}else if(from.equals("未设置")) {
							sql = "SELECT SUM(estimated_amount),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY SUM(estimated_amount) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("赢单商机金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
							
						}else {

							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
									sql = "SELECT SUM(estimated_amount),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY SUM(estimated_amount) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("赢单商机金额");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										report.setUsername(rs.getString(3));
										list.add(report);
									}
						    	}
						    }
							sql = "SELECT SUM(estimated_amount),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY SUM(estimated_amount) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("赢单商机金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
					        for (int i11 = 0; i11 < list.size() - 1; i11++) {
					            for (int j = i11 + 1; j < list.size(); j++) {
					                if (list.get(i11).equals(list.get(j))) {
					                    list.remove(j);
					                }
					            }
					        }
						}
					}
				}
			
				
				

				
			} else {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
				String accDate=format.format(new Date());//当前月份

				if(report.getResultsType().equals("合同金额")){
					String sql = "";
						if(from.equals("全部")) {
							sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1  "+wSQL+" and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
						}else{
							if(from.equals("本部门")) {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1  "+wSQL+" and department_id like'" + bmid + "%' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("合同金额");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											report.setUsername(rs.getString(3));
											list.add(report);
										}
							    	}
							    }
							    sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
							}else if(from.equals("未设置")) {
							    sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
							}else {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1  "+wSQL+" and department_id like'" + bmid + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("合同金额");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											report.setUsername(rs.getString(3));
											list.add(report);
										}
							    	}
							    }
							    sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
							}
						}
				}else if(report.getResultsType().equals("合同数")){
					String sql = "";
					if(from.equals("全部")) {
						sql = "SELECT COUNT(*),department_name,create_name from crm_contract where 1=1  "+wSQL+" and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY COUNT(*) desc";
						ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						int i1=1;
						while (rs.next()) {
							report = new Report();
							report.setRanking(i1++);
							report.setColumn("合同数");
							report.setAggregateAmount(rs.getBigDecimal(1));
							report.setDepartmentName(rs.getString(2));
							report.setUsername(rs.getString(3));
							list.add(report);
						}
					}else{
						if(from.equals("本部门")) {
							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
									sql = "SELECT COUNT(*),department_name,create_name from crm_contract where 1=1  "+wSQL+" and department_id like'" + bmid + "%' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY COUNT(*) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("合同数");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										report.setUsername(rs.getString(3));
										list.add(report);
									}
						    	}
						    }
						    sql = "SELECT COUNT(*),department_name,create_name from crm_contract where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY COUNT(*) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同数");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
					        for (int i11 = 0; i11 < list.size() - 1; i11++) {
					            for (int j = i11 + 1; j < list.size(); j++) {
					                if (list.get(i11).equals(list.get(j))) {
					                    list.remove(j);
					                }
					            }
					        }
							
						}else if(from.equals("未设置")) {
						    sql = "SELECT COUNT(*),department_name,create_name from crm_contract where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY COUNT(*) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同数");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
							
						}else {
							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
									sql = "SELECT COUNT(*),department_name,create_name from crm_contract where 1=1  "+wSQL+" and department_id like'" + bmid + "' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY COUNT(*) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("合同数");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										report.setUsername(rs.getString(3));
										list.add(report);
									}
						    	}
						    }
						    sql = "SELECT COUNT(*),department_name,create_name from crm_contract where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY COUNT(*) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同数");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
					        for (int i11 = 0; i11 < list.size() - 1; i11++) {
					            for (int j = i11 + 1; j < list.size(); j++) {
					                if (list.get(i11).equals(list.get(j))) {
					                    list.remove(j);
					                }
					            }
					        }
							
						}
					}
				}else if(report.getResultsType().equals("赢单商机数")){
					String sql = "";
						if(from.equals("全部")) {
							sql = "SELECT COUNT(*),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+"  and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY COUNT(*) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("赢单商机数");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
						}else {
							if(from.equals("本部门")) {
								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT COUNT(*),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and department_id like'" + bmid + "%' and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY COUNT(*) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("赢单商机数");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											report.setUsername(rs.getString(3));
											list.add(report);
										}
							    	}
							    }
								sql = "SELECT COUNT(*),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY COUNT(*) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
								
							}else if(from.equals("未设置")) {
								sql = "SELECT COUNT(*),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY COUNT(*) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
								
							}else {
								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT COUNT(*),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and department_id like'" + bmid + "' and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY COUNT(*) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("赢单商机数");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											report.setUsername(rs.getString(3));
											list.add(report);
										}
							    	}
							    }
								sql = "SELECT COUNT(*),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY COUNT(*) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("赢单商机数");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
								
							}
						}
				}else if(report.getResultsType().equals("赢单商机金额")){
					String sql = "";
					if(from.equals("全部")) {
						sql = "SELECT SUM(estimated_amount),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%'  and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY SUM(estimated_amount) desc";
						ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						int i1=1;
						while (rs.next()) {
							report = new Report();
							report.setRanking(i1++);
							report.setColumn("赢单商机金额");
							report.setAggregateAmount(rs.getBigDecimal(1));
							report.setDepartmentName(rs.getString(2));
							report.setUsername(rs.getString(3));
							list.add(report);
						}
					}else {
						if(from.equals("本部门")) {
							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
									sql = "SELECT SUM(estimated_amount),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "%' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY SUM(estimated_amount) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("赢单商机金额");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										report.setUsername(rs.getString(3));
										list.add(report);
									}
						    	}
						    }
							sql = "SELECT SUM(estimated_amount),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY SUM(estimated_amount) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("赢单商机金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
					        for (int i11 = 0; i11 < list.size() - 1; i11++) {
					            for (int j = i11 + 1; j < list.size(); j++) {
					                if (list.get(i11).equals(list.get(j))) {
					                    list.remove(j);
					                }
					            }
					        }
							
						}else if(from.equals("未设置")) {
							sql = "SELECT SUM(estimated_amount),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY SUM(estimated_amount) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("赢单商机金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
							
						}else {

							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
									sql = "SELECT SUM(estimated_amount),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY SUM(estimated_amount) desc";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									int i1=1;
									while (rs.next()) {
										report = new Report();
										report.setRanking(i1++);
										report.setColumn("赢单商机金额");
										report.setAggregateAmount(rs.getBigDecimal(1));
										report.setDepartmentName(rs.getString(2));
										report.setUsername(rs.getString(3));
										list.add(report);
									}
						    	}
						    }
							sql = "SELECT SUM(estimated_amount),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and sales_stage_name='赢单'  and create_date like'" + accDate + "%' GROUP BY create_name ORDER BY SUM(estimated_amount) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("赢单商机金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
					        for (int i11 = 0; i11 < list.size() - 1; i11++) {
					            for (int j = i11 + 1; j < list.size(); j++) {
					                if (list.get(i11).equals(list.get(j))) {
					                    list.remove(j);
					                }
					            }
					        }
						}
					}
				}else if(report.getResultsType().equals("合同回款金额")){
					String sql = "";
						if(from.equals("全部")) {
							sql = "SELECT SUM(collection_amount),department_name,create_name from crm_collection where 1=1  "+wSQL+" and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(collection_amount) desc";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							int i1=1;
							while (rs.next()) {
								report = new Report();
								report.setRanking(i1++);
								report.setColumn("合同回款金额");
								report.setAggregateAmount(rs.getBigDecimal(1));
								report.setDepartmentName(rs.getString(2));
								report.setUsername(rs.getString(3));
								list.add(report);
							}
						}else{
							if(from.equals("本部门")) {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT SUM(collection_amount),department_name,create_name from crm_collection where 1=1  "+wSQL+" and department_id like'" + bmid + "%' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(collection_amount) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("合同回款金额");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											report.setUsername(rs.getString(3));
											list.add(report);
										}
							    	}
							    }
							    sql = "SELECT SUM(collection_amount),department_name,create_name from crm_collection where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(collection_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同回款金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
							}else if(from.equals("未设置")) {
							    sql = "SELECT SUM(collection_amount),department_name,create_name from crm_collection where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(collection_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同回款金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
							}else {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT SUM(collection_amount),department_name,create_name from crm_collection where 1=1  "+wSQL+" and department_id like'" + bmid + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(collection_amount) desc";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										int i1=1;
										while (rs.next()) {
											report = new Report();
											report.setRanking(i1++);
											report.setColumn("合同回款金额");
											report.setAggregateAmount(rs.getBigDecimal(1));
											report.setDepartmentName(rs.getString(2));
											report.setUsername(rs.getString(3));
											list.add(report);
										}
							    	}
							    }
							    sql = "SELECT SUM(collection_amount),department_name,create_name from crm_collection where 1=1  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%' and create_date like'" + accDate + "%'  GROUP BY create_name ORDER BY SUM(collection_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i1++);
									report.setColumn("合同回款金额");
									report.setAggregateAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
							}
						}
				}
			}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("List<Report> getItemsgrry(Report report)" + (t2 - t1) + " ms");
		} catch (SQLException | ParseException e) {
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		} finally {
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		}
		return list;
	}

	@Override
	public List<Report> getItemsSales(Report report) {
		long t1 = System.currentTimeMillis();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		List<Report> list = new ArrayList<Report>();
		String mechanismId=report.getMechanismId();
		String from=report.getFrom() == null ? "" : report.getFrom();
		String departmentIds=report.getDepartmentIds();
		String username=report.getUsername();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);

			String wSQL = "";

			if(report.getDepartmentId()!=null){
				if(report.getDepartmentId().equals("")){
					
				}else{
					if (report.getDepartmentId() != null || !report.getDepartmentId().equals("")) {
						wSQL += " and department_id='" + report.getDepartmentId() + "'";
					}
				}
			}
			if(report.getUserId()!=null){
				if(report.getUserId().equals("")){
					
				}else{
					if (report.getUserId() != null || !report.getUserId().equals("")) {
						wSQL += " and create_id='" + report.getUserId() + "'";
					}
				}
			}
			if(from.equals("用户")) {
				wSQL += " ";
			}
	        Date dNow = new Date();   //当前时间  
	        Date dBefore = new Date();  
	        Calendar calendar = Calendar.getInstance(); //得到日历  
	        calendar.setTime(dNow);//把当前时间赋给日历  
	        calendar.add(Calendar.MONTH, +2);  //设置为前3月  
	        dBefore = calendar.getTime();   //得到前3月的时间  
	        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM"); //设置时间格式  
	        String defaultStartDate = sdf.format(dBefore);    //格式化前3月的时间  
	        String defaultEndDate = sdf.format(dNow); //格式化当前时间  
	        
	        calendar.setTime(dNow);//把当前时间赋给日历  
	        calendar.add(Calendar.MONTH, +1);  //设置为前3月  
	        dBefore = calendar.getTime();   //得到前3月的时间  
	        String defaultStartDate1 = sdf.format(dBefore);    //格式化前3月的时间  
	        System.out.println("三个月之前时间======="+defaultStartDate);  
	        System.out.println("三个月之前时间======="+defaultStartDate1);  
	        System.out.println("当前时间==========="+defaultEndDate); 
				if(from.equals("全部")) {
					String sql = "SELECT count(*),estimated_amount from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like ? and expected_signing_date like ? ";
					ps = conn.prepareStatement(sql);
					ps.setString(1, mechanismId+"%");
					ps.setString(2, defaultEndDate+"%");
					rs = ps.executeQuery();
					int itj1=0;
					if (rs.next()) {
						report = new Report();
						report.setColumn(defaultEndDate);
						report.setBusinessOpportunitiesNumber(rs.getInt(1));
						report.setEstimatedSalesAmount(rs.getBigDecimal(2));
						list.add(report);
					}else{
						report = new Report();
						report.setColumn(defaultEndDate);
						report.setBusinessOpportunitiesNumber(0);
						report.setEstimatedSalesAmount(new BigDecimal(0));
						list.add(report);
						
					}
					String sql1 = "SELECT count(*),estimated_amount from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like ? and expected_signing_date like ? ";
					ps1 = conn.prepareStatement(sql1);
					ps1.setString(1, mechanismId+"%");
					ps1.setString(2, defaultStartDate1+"%");
					rs1 = ps1.executeQuery();
					if (rs1.next()) {
						report = new Report();
						report.setColumn(defaultStartDate1);
						report.setBusinessOpportunitiesNumber(rs1.getInt(1));
						report.setEstimatedSalesAmount(rs1.getBigDecimal(2));
						list.add(report);
					}else{

						report = new Report();
						report.setColumn(defaultStartDate1);
						report.setBusinessOpportunitiesNumber(0);
						report.setEstimatedSalesAmount(new BigDecimal(0));
						list.add(report);
					}
					String sql2 = "SELECT count(*),estimated_amount from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like ? and expected_signing_date like ?";
					ps2 = conn.prepareStatement(sql2);
					ps2.setString(1, mechanismId+"%");
					ps2.setString(2, defaultStartDate+"%");
					rs2 = ps2.executeQuery();
					if (rs2.next()) {
						report = new Report();
						report.setColumn(defaultStartDate);
						report.setBusinessOpportunitiesNumber(rs2.getInt(1));
						report.setEstimatedSalesAmount(rs2.getBigDecimal(2));
						list.add(report);
					}else{
						report = new Report();
						report.setColumn(defaultStartDate);
						report.setBusinessOpportunitiesNumber(0);
						report.setEstimatedSalesAmount(new BigDecimal(0));
						list.add(report);
					}
				}else {
					if(from.equals("本部门")) {


						String[] xh=departmentIds.split(",");
					    for(String bmid : xh){
					    	if(!bmid.equals("")){
								String sql = "SELECT count(*),estimated_amount from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like ? and department_id like ? and expected_signing_date like ? ";
								ps = conn.prepareStatement(sql);
								ps.setString(1, mechanismId+"%");
								ps.setString(2, bmid+"%");
								ps.setString(3, defaultEndDate+"%");
								rs = ps.executeQuery();
								int itj1=0;
								if (rs.next()) {
									report = new Report();
									report.setColumn(defaultEndDate);
									report.setBusinessOpportunitiesNumber(rs.getInt(1));
									report.setEstimatedSalesAmount(rs.getBigDecimal(2));
									list.add(report);
								}else{
									report = new Report();
									report.setColumn(defaultEndDate);
									report.setBusinessOpportunitiesNumber(0);
									report.setEstimatedSalesAmount(new BigDecimal(0));
									list.add(report);
								}
								String sql1 = "SELECT count(*),estimated_amount from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like ? and department_id like ? and expected_signing_date like ? ";
								ps1 = conn.prepareStatement(sql1);
								ps1.setString(1, mechanismId+"%");
								ps1.setString(2, bmid+"%");
								ps1.setString(3, defaultStartDate1+"%");
								rs1 = ps1.executeQuery();
								if (rs1.next()) {
									report = new Report();
									report.setColumn(defaultStartDate1);
									report.setBusinessOpportunitiesNumber(rs1.getInt(1));
									report.setEstimatedSalesAmount(rs1.getBigDecimal(2));
									list.add(report);
								}else{
									report = new Report();
									report.setColumn(defaultStartDate1);
									report.setBusinessOpportunitiesNumber(0);
									report.setEstimatedSalesAmount(new BigDecimal(0));
									list.add(report);
								}
								String sql2 = "SELECT count(*),estimated_amount from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like ? and department_id like ? and expected_signing_date like ? ";
								ps2 = conn.prepareStatement(sql2);
								ps2.setString(1, mechanismId+"%");
								ps2.setString(2, bmid+"%");
								ps2.setString(3, defaultStartDate+"%");
								rs2 = ps2.executeQuery();
								if (rs2.next()) {
									report = new Report();
									report.setColumn(defaultStartDate);
									report.setBusinessOpportunitiesNumber(rs2.getInt(1));
									report.setEstimatedSalesAmount(rs2.getBigDecimal(2));
									list.add(report);
								}else{
									report = new Report();
									report.setColumn(defaultStartDate);
									report.setBusinessOpportunitiesNumber(0);
									report.setEstimatedSalesAmount(new BigDecimal(0));
									list.add(report);
								}
					    	}
					    }

						String sql = "SELECT count(*),estimated_amount from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like ? and create_id =? and expected_signing_date like ? ";
						ps = conn.prepareStatement(sql);
						ps.setString(1, mechanismId+"%");
						ps.setString(2, username);
						ps.setString(3, defaultEndDate+"%");
						rs = ps.executeQuery();
						int itj1=0;
						if (rs.next()) {
							report = new Report();
							report.setColumn(defaultEndDate);
							report.setBusinessOpportunitiesNumber(rs.getInt(1));
							report.setEstimatedSalesAmount(rs.getBigDecimal(2));
							list.add(report);
						}else{
							report = new Report();
							report.setColumn(defaultEndDate);
							report.setBusinessOpportunitiesNumber(0);
							report.setEstimatedSalesAmount(new BigDecimal(0));
							list.add(report);
						}
						String sql1 = "SELECT count(*),estimated_amount from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like ? and create_id =? and expected_signing_date like ? ";
						ps1 = conn.prepareStatement(sql1);
						ps1.setString(1, mechanismId+"%");
						ps1.setString(2, username);
						ps1.setString(3, defaultStartDate1+"%");
						rs1 = ps1.executeQuery();
						if (rs1.next()) {
							report = new Report();
							report.setColumn(defaultStartDate1);
							report.setBusinessOpportunitiesNumber(rs1.getInt(1));
							report.setEstimatedSalesAmount(rs1.getBigDecimal(2));
							list.add(report);
						}else{
							report = new Report();
							report.setColumn(defaultStartDate1);
							report.setBusinessOpportunitiesNumber(0);
							report.setEstimatedSalesAmount(new BigDecimal(0));
							list.add(report);
						}
						String sql2 = "SELECT count(*),estimated_amount from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like ? and create_id =? and expected_signing_date like ?";
						ps2 = conn.prepareStatement(sql2);
						ps2.setString(1, mechanismId+"%");
						ps2.setString(2, username);
						ps2.setString(3, defaultStartDate+"%");
						rs2 = ps2.executeQuery();
						if (rs2.next()) {
							report = new Report();
							report.setColumn(defaultStartDate);
							report.setBusinessOpportunitiesNumber(rs2.getInt(1));
							report.setEstimatedSalesAmount(rs2.getBigDecimal(2));
							list.add(report);
						}else{
							report = new Report();
							report.setColumn(defaultStartDate);
							report.setBusinessOpportunitiesNumber(0);
							report.setEstimatedSalesAmount(new BigDecimal(0));
							list.add(report);
						}
					
			        for (int i11 = 0; i11 < list.size() - 1; i11++) {
			            for (int j = i11 + 1; j < list.size(); j++) {
			                if (list.get(i11).equals(list.get(j))) {
			                    list.remove(j);
			                }
			            }
			        }
					}else if(from.equals("未设置")) {

						String sql = "SELECT count(*),estimated_amount from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like ? and create_id =? and expected_signing_date like ? ";
						ps = conn.prepareStatement(sql);
						ps.setString(1, mechanismId+"%");
						ps.setString(2, username);
						ps.setString(3, defaultEndDate+"%");
						rs = ps.executeQuery();
						int itj1=0;
						if (rs.next()) {
							report = new Report();
							report.setColumn(defaultEndDate);
							report.setBusinessOpportunitiesNumber(rs.getInt(1));
							report.setEstimatedSalesAmount(rs.getBigDecimal(2));
							list.add(report);
						}else{
							report = new Report();
							report.setColumn(defaultEndDate);
							report.setBusinessOpportunitiesNumber(0);
							report.setEstimatedSalesAmount(new BigDecimal(0));
							list.add(report);
						}
						String sql1 = "SELECT count(*),estimated_amount from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like ? and create_id =? and expected_signing_date like ? ";
						ps1 = conn.prepareStatement(sql1);
						ps1.setString(1, mechanismId+"%");
						ps1.setString(2, username);
						ps1.setString(3, defaultStartDate1+"%");
						rs1 = ps1.executeQuery();
						if (rs1.next()) {
							report = new Report();
							report.setColumn(defaultStartDate1);
							report.setBusinessOpportunitiesNumber(rs1.getInt(1));
							report.setEstimatedSalesAmount(rs1.getBigDecimal(2));
							list.add(report);
						}else{
							report = new Report();
							report.setColumn(defaultStartDate1);
							report.setBusinessOpportunitiesNumber(0);
							report.setEstimatedSalesAmount(new BigDecimal(0));
							list.add(report);
						}
						String sql2 = "SELECT count(*),estimated_amount from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like ? and create_id =? and expected_signing_date like ?";
						ps2 = conn.prepareStatement(sql2);
						ps2.setString(1, mechanismId+"%");
						ps2.setString(2, username);
						ps2.setString(3, defaultStartDate+"%");
						rs2 = ps2.executeQuery();
						if (rs2.next()) {
							report = new Report();
							report.setColumn(defaultStartDate);
							report.setBusinessOpportunitiesNumber(rs2.getInt(1));
							report.setEstimatedSalesAmount(rs2.getBigDecimal(2));
							list.add(report);
						}else{
							report = new Report();
							report.setColumn(defaultStartDate);
							report.setBusinessOpportunitiesNumber(0);
							report.setEstimatedSalesAmount(new BigDecimal(0));
							list.add(report);
						}
					
					}else {


						String[] xh=departmentIds.split(",");
					    for(String bmid : xh){
					    	if(!bmid.equals("")){
								String sql = "SELECT count(*),estimated_amount from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like ? and department_id like ? and expected_signing_date like ? ";
								ps = conn.prepareStatement(sql);
								ps.setString(1, mechanismId+"%");
								ps.setString(2, bmid);
								ps.setString(3, defaultEndDate+"%");
								rs = ps.executeQuery();
								int itj1=0;
								if (rs.next()) {
									report = new Report();
									report.setColumn(defaultEndDate);
									report.setBusinessOpportunitiesNumber(rs.getInt(1));
									report.setEstimatedSalesAmount(rs.getBigDecimal(2));
									list.add(report);
								}else{
									report = new Report();
									report.setColumn(defaultEndDate);
									report.setBusinessOpportunitiesNumber(0);
									report.setEstimatedSalesAmount(new BigDecimal(0));
									list.add(report);
								}
								String sql1 = "SELECT count(*),estimated_amount from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like ? and department_id like ? and expected_signing_date like ? ";
								ps1 = conn.prepareStatement(sql1);
								ps1.setString(1, mechanismId+"%");
								ps1.setString(2, bmid);
								ps1.setString(3, defaultStartDate1+"%");
								rs1 = ps1.executeQuery();
								if (rs1.next()) {
									report = new Report();
									report.setColumn(defaultStartDate1);
									report.setBusinessOpportunitiesNumber(rs1.getInt(1));
									report.setEstimatedSalesAmount(rs1.getBigDecimal(2));
									list.add(report);
								}else{
									report = new Report();
									report.setColumn(defaultStartDate1);
									report.setBusinessOpportunitiesNumber(0);
									report.setEstimatedSalesAmount(new BigDecimal(0));
									list.add(report);
								}
								String sql2 = "SELECT count(*),estimated_amount from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like ? and department_id like ? and expected_signing_date like ? ";
								ps2 = conn.prepareStatement(sql2);
								ps2.setString(1, mechanismId+"%");
								ps2.setString(2, bmid);
								ps2.setString(3, defaultStartDate+"%");
								rs2 = ps2.executeQuery();
								if (rs2.next()) {
									report = new Report();
									report.setColumn(defaultStartDate);
									report.setBusinessOpportunitiesNumber(rs2.getInt(1));
									report.setEstimatedSalesAmount(rs2.getBigDecimal(2));
									list.add(report);
								}else{
									report = new Report();
									report.setColumn(defaultStartDate);
									report.setBusinessOpportunitiesNumber(0);
									report.setEstimatedSalesAmount(new BigDecimal(0));
									list.add(report);
								}
					    	}
					    }

						String sql = "SELECT count(*),estimated_amount from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like ? and create_id =? and expected_signing_date like ? ";
						ps = conn.prepareStatement(sql);
						ps.setString(1, mechanismId+"%");
						ps.setString(2, username);
						ps.setString(3, defaultEndDate+"%");
						rs = ps.executeQuery();
						int itj1=0;
						if (rs.next()) {
							report = new Report();
							report.setColumn(defaultEndDate);
							report.setBusinessOpportunitiesNumber(rs.getInt(1));
							report.setEstimatedSalesAmount(rs.getBigDecimal(2));
							list.add(report);
						}else{
							report = new Report();
							report.setColumn(defaultEndDate);
							report.setBusinessOpportunitiesNumber(0);
							report.setEstimatedSalesAmount(new BigDecimal(0));
							list.add(report);
						}
						String sql1 = "SELECT count(*),estimated_amount from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like ? and create_id =? and expected_signing_date like ? ";
						ps1 = conn.prepareStatement(sql1);
						ps1.setString(1, mechanismId+"%");
						ps1.setString(2, username);
						ps1.setString(3, defaultStartDate1+"%");
						rs1 = ps1.executeQuery();
						if (rs1.next()) {
							report = new Report();
							report.setColumn(defaultStartDate1);
							report.setBusinessOpportunitiesNumber(rs1.getInt(1));
							report.setEstimatedSalesAmount(rs1.getBigDecimal(2));
							list.add(report);
						}else{
							report = new Report();
							report.setColumn(defaultStartDate1);
							report.setBusinessOpportunitiesNumber(0);
							report.setEstimatedSalesAmount(new BigDecimal(0));
							list.add(report);
						}
						String sql2 = "SELECT count(*),estimated_amount from crm_business_opportunity where 1=1 "+wSQL+" and mechanism_id like ? and create_id =? and expected_signing_date like ?";
						ps2 = conn.prepareStatement(sql2);
						ps2.setString(1, mechanismId+"%");
						ps2.setString(2, username);
						ps2.setString(3, defaultStartDate+"%");
						rs2 = ps2.executeQuery();
						if (rs2.next()) {
							report = new Report();
							report.setColumn(defaultStartDate);
							report.setBusinessOpportunitiesNumber(rs2.getInt(1));
							report.setEstimatedSalesAmount(rs2.getBigDecimal(2));
							list.add(report);
						}else{
							report = new Report();
							report.setColumn(defaultStartDate);
							report.setBusinessOpportunitiesNumber(0);
							report.setEstimatedSalesAmount(new BigDecimal(0));
							list.add(report);
						}
					
			        for (int i11 = 0; i11 < list.size() - 1; i11++) {
			            for (int j = i11 + 1; j < list.size(); j++) {
			                if (list.get(i11).equals(list.get(j))) {
			                    list.remove(j);
			                }
			            }
			        }
					}
				}

			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("List<Report> getItemssales(Report report)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		} finally {
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		}
		return list;
	}

	@Override
	public List<Report> getItemsWinOrderOpportUnity(Report report) {
		long t1 = System.currentTimeMillis();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		PreparedStatement ps3 = null;
		ResultSet rs3 = null;
		PreparedStatement ps4 = null;
		ResultSet rs4 = null;
		List<Report> list = new ArrayList<Report>();
		String mechanismId=report.getMechanismId();
		String from=report.getFrom() == null ? "" : report.getFrom();
		String departmentIds=report.getDepartmentIds();
		String username=report.getUsername();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);

			String wSQL = "";

			if(report.getDepartmentId()!=null){
				if(report.getDepartmentId().equals("")){
					
				}else{
					if (report.getDepartmentId() != null || !report.getDepartmentId().equals("")) {
						wSQL += " and department_id='" + report.getDepartmentId() + "'";
					}
				}
			}
			if(report.getUserId()!=null){
				if(report.getUserId().equals("")){
					
				}else{
					if (report.getUserId() != null || !report.getUserId().equals("")) {
						wSQL += " and create_id='" + report.getUserId() + "'";
					}
				}
			}
			if(from.equals("用户")) {
				wSQL += " and create_id ='" + username + "'";
			}

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
			SimpleDateFormat formatz = new SimpleDateFormat("yyyy");
			
			String nowdate=format.format(new Date());//当前月份
			String nowdatez=formatz.format(new Date());//当前月份
			
			Date d1 = new SimpleDateFormat("yyyy-MM").parse(nowdatez+"-01");//定义起始日期
			
			Date d2 = new SimpleDateFormat("yyyy-MM").parse(nowdatez+"-12");//定义结束日期 可以去当前月也可以手动写日期。
			if(report.getTime()==null){
			}else{
				if(report.getTime().equals("")){
				}else{
					d1 = new SimpleDateFormat("yyyy-MM").parse(report.getTime()+"-01");//定义起始日期
					d2 = new SimpleDateFormat("yyyy-MM").parse(report.getTime()+"-12");//定义结束日期 可以去当前月也可以手动写日期
				}
			}
			Calendar dd = Calendar.getInstance();//定义日期实例
			
			dd.setTime(d1);//设置日期起始时间
			
			while (dd.getTime().before(d2)) {
			
			SimpleDateFormat sdfzz = new SimpleDateFormat("yyyy-MM");

				String str = sdfzz.format(dd.getTime());
				report = new Report();
				BigDecimal kpje=new BigDecimal(0);
				BigDecimal yds=new BigDecimal(0);
				String sql2 = "SELECT sum(estimated_amount),COUNT(*) from crm_business_opportunity where 1=1 and sales_stage_name='赢单'  "+wSQL+" and department_id like'" + departmentIds + "%'  and mechanism_id like'" + mechanismId + "%'  and create_date like'" + str + "%' ";
				if(from.equals("全部")) {
					sql2 = "SELECT sum(estimated_amount),COUNT(*) from crm_business_opportunity where 1=1 and sales_stage_name='赢单'  "+wSQL+"  and mechanism_id like'" + mechanismId + "%'  and create_date like'" + str + "%' ";
					ps2 = conn.prepareStatement(sql2);
					rs2 = ps2.executeQuery();
					BigDecimal kdj=new BigDecimal(0);
					while (rs2.next()) {
						kpje=rs2.getBigDecimal(1);
						yds=rs2.getBigDecimal(2);
						if(rs2.getBigDecimal(1)==null){
							
						}else{
							if(rs2.getBigDecimal(1).compareTo(new BigDecimal(0))==0){
								
							}else{

								kdj=kpje.divide(yds,2, BigDecimal.ROUND_HALF_UP);
							}
						}
					}
					report.setColumn(str);
					report.setToWinASingleAmount(kpje);
					report.setWinSingular(yds);
					report.setTheGuestUnitPrice(kdj.setScale(2, BigDecimal.ROUND_HALF_UP));
					list.add(report);
					dd.add(Calendar.MONTH, 1);//进行当前日期月份加1
				}else {
					if(from.equals("本部门")) {

						String[] xh=departmentIds.split(",");
					    for(String bmid : xh){
					    	if(!bmid.equals("")){
								sql2 = "SELECT sum(estimated_amount),COUNT(*) from crm_business_opportunity where 1=1 and sales_stage_name='赢单'  "+wSQL+" and department_id like'" + bmid + "%' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + str + "%' ";
								ps2 = conn.prepareStatement(sql2);
								rs2 = ps2.executeQuery();
								BigDecimal kdj=new BigDecimal(0);
								while (rs2.next()) {
									kpje=rs2.getBigDecimal(1);
									yds=rs2.getBigDecimal(2);
									if(rs2.getBigDecimal(1)==null){
										
									}else{
										if(rs2.getBigDecimal(1).compareTo(new BigDecimal(0))==0){
											
										}else{

											kdj=kpje.divide(yds,2, BigDecimal.ROUND_HALF_UP);
										}
									}
								}
								report.setColumn(str);
								report.setToWinASingleAmount(kpje);
								report.setWinSingular(yds);
								report.setTheGuestUnitPrice(kdj.setScale(2, BigDecimal.ROUND_HALF_UP));
								list.add(report);
					    	}
					    }
						sql2 = "SELECT sum(estimated_amount),COUNT(*) from crm_business_opportunity where 1=1 and sales_stage_name='赢单'  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + str + "%' ";
						ps2 = conn.prepareStatement(sql2);
						rs2 = ps2.executeQuery();
						BigDecimal kdj=new BigDecimal(0);
						while (rs2.next()) {
							kpje=rs2.getBigDecimal(1);
							yds=rs2.getBigDecimal(2);
							if(rs2.getBigDecimal(1)==null){
								
							}else{
								if(rs2.getBigDecimal(1).compareTo(new BigDecimal(0))==0){
									
								}else{

									kdj=kpje.divide(yds,2, BigDecimal.ROUND_HALF_UP);
								}
							}
						}
						report.setColumn(str);
						report.setToWinASingleAmount(kpje);
						report.setWinSingular(yds);
						report.setTheGuestUnitPrice(kdj.setScale(2, BigDecimal.ROUND_HALF_UP));
						list.add(report);
				        for (int i11 = 0; i11 < list.size() - 1; i11++) {
				            for (int j = i11 + 1; j < list.size(); j++) {
				                if (list.get(i11).equals(list.get(j))) {
				                    list.remove(j);
				                }
				            }
				        }

						dd.add(Calendar.MONTH, 1);//进行当前日期月份加1
					}else if(from.equals("未设置")) {
						sql2 = "SELECT sum(estimated_amount),COUNT(*) from crm_business_opportunity where 1=1 and sales_stage_name='赢单'  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + str + "%' ";
						ps2 = conn.prepareStatement(sql2);
						rs2 = ps2.executeQuery();
						BigDecimal kdj=new BigDecimal(0);
						while (rs2.next()) {
							kpje=rs2.getBigDecimal(1);
							yds=rs2.getBigDecimal(2);
							if(rs2.getBigDecimal(1)==null){
								
							}else{
								if(rs2.getBigDecimal(1).compareTo(new BigDecimal(0))==0){
									
								}else{

									kdj=kpje.divide(yds,2, BigDecimal.ROUND_HALF_UP);
								}
							}
						}
						report.setColumn(str);
						report.setToWinASingleAmount(kpje);
						report.setWinSingular(yds);
						report.setTheGuestUnitPrice(kdj.setScale(2, BigDecimal.ROUND_HALF_UP));
						list.add(report);

						dd.add(Calendar.MONTH, 1);//进行当前日期月份加1
					}else {

						String[] xh=departmentIds.split(",");
					    for(String bmid : xh){
					    	if(!bmid.equals("")){
								sql2 = "SELECT sum(estimated_amount),COUNT(*) from crm_business_opportunity where 1=1 and sales_stage_name='赢单'  "+wSQL+" and department_id like'" + bmid + "' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + str + "%' ";
								ps2 = conn.prepareStatement(sql2);
								rs2 = ps2.executeQuery();
								BigDecimal kdj=new BigDecimal(0);
								while (rs2.next()) {
									kpje=rs2.getBigDecimal(1);
									yds=rs2.getBigDecimal(2);
									if(rs2.getBigDecimal(1)==null){
										
									}else{
										if(rs2.getBigDecimal(1).compareTo(new BigDecimal(0))==0){
											
										}else{

											kdj=kpje.divide(yds,2, BigDecimal.ROUND_HALF_UP);
										}
									}
								}
								report.setColumn(str);
								report.setToWinASingleAmount(kpje);
								report.setWinSingular(yds);
								report.setTheGuestUnitPrice(kdj.setScale(2, BigDecimal.ROUND_HALF_UP));
								list.add(report);
					    	}
					    }
						sql2 = "SELECT sum(estimated_amount),COUNT(*) from crm_business_opportunity where 1=1 and sales_stage_name='赢单'  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + str + "%' ";
						ps2 = conn.prepareStatement(sql2);
						rs2 = ps2.executeQuery();
						BigDecimal kdj=new BigDecimal(0);
						while (rs2.next()) {
							kpje=rs2.getBigDecimal(1);
							yds=rs2.getBigDecimal(2);
							if(rs2.getBigDecimal(1)==null){
								
							}else{
								if(rs2.getBigDecimal(1).compareTo(new BigDecimal(0))==0){
									
								}else{

									kdj=kpje.divide(yds,2, BigDecimal.ROUND_HALF_UP);
								}
							}
						}
						report.setColumn(str);
						report.setToWinASingleAmount(kpje);
						report.setWinSingular(yds);
						report.setTheGuestUnitPrice(kdj.setScale(2, BigDecimal.ROUND_HALF_UP));
						list.add(report);
				        for (int i11 = 0; i11 < list.size() - 1; i11++) {
				            for (int j = i11 + 1; j < list.size(); j++) {
				                if (list.get(i11).equals(list.get(j))) {
				                    list.remove(j);
				                }
				            }
				        }

						dd.add(Calendar.MONTH, 1);//进行当前日期月份加1
					}
				}
			}

			SimpleDateFormat sdfzz = new SimpleDateFormat("yyyy-MM");
			String str=sdfzz.format(d2.getTime());
			report = new Report();
			BigDecimal hkje=new BigDecimal(0);
			BigDecimal kpje=new BigDecimal(0);
			BigDecimal yds=new BigDecimal(0);
			String sql2 = "SELECT sum(estimated_amount),COUNT(*) from crm_business_opportunity where 1=1 and sales_stage_name='赢单'  "+wSQL+" and department_id like'" + departmentIds + "%'  and mechanism_id like'" + mechanismId + "%'  and create_date like'" + str + "%' ";
			if(from.equals("全部")) {
				sql2 = "SELECT sum(estimated_amount),COUNT(*) from crm_business_opportunity where 1=1 and sales_stage_name='赢单'  "+wSQL+"  and mechanism_id like'" + mechanismId + "%'  and create_date like'" + str + "%' ";
				ps2 = conn.prepareStatement(sql2);
				rs2 = ps2.executeQuery();
				BigDecimal kdj=new BigDecimal(0);
				while (rs2.next()) {
					kpje=rs2.getBigDecimal(1);
					yds=rs2.getBigDecimal(2);
					if(rs2.getBigDecimal(1)==null){
						
					}else{
						if(rs2.getBigDecimal(1).compareTo(new BigDecimal(0))==0){
							
						}else{

							kdj=kpje.divide(yds,2, BigDecimal.ROUND_HALF_UP);
						}
					}
				}
				report.setColumn(str);
				report.setToWinASingleAmount(kpje);
				report.setWinSingular(yds);
				report.setTheGuestUnitPrice(kdj.setScale(2, BigDecimal.ROUND_HALF_UP));
				list.add(report);
			}else {
				if(from.equals("本部门")) {

					String[] xh=departmentIds.split(",");
				    for(String bmid : xh){
				    	if(!bmid.equals("")){
							sql2 = "SELECT sum(estimated_amount),COUNT(*) from crm_business_opportunity where 1=1 and sales_stage_name='赢单'  "+wSQL+" and department_id like'" + bmid + "%' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + str + "%' ";
							ps2 = conn.prepareStatement(sql2);
							rs2 = ps2.executeQuery();
							BigDecimal kdj=new BigDecimal(0);
							while (rs2.next()) {
								kpje=rs2.getBigDecimal(1);
								yds=rs2.getBigDecimal(2);
								if(rs2.getBigDecimal(1)==null){
									
								}else{
									if(rs2.getBigDecimal(1).compareTo(new BigDecimal(0))==0){
										
									}else{

										kdj=kpje.divide(yds,2, BigDecimal.ROUND_HALF_UP);
									}
								}
							}
							report.setColumn(str);
							report.setToWinASingleAmount(kpje);
							report.setWinSingular(yds);
							report.setTheGuestUnitPrice(kdj.setScale(2, BigDecimal.ROUND_HALF_UP));
							list.add(report);
				    	}
				    }
					sql2 = "SELECT sum(estimated_amount),COUNT(*) from crm_business_opportunity where 1=1 and sales_stage_name='赢单'  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + str + "%' ";
					ps2 = conn.prepareStatement(sql2);
					rs2 = ps2.executeQuery();
					BigDecimal kdj=new BigDecimal(0);
					while (rs2.next()) {
						kpje=rs2.getBigDecimal(1);
						yds=rs2.getBigDecimal(2);
						if(rs2.getBigDecimal(1)==null){
							
						}else{
							if(rs2.getBigDecimal(1).compareTo(new BigDecimal(0))==0){
								
							}else{

								kdj=kpje.divide(yds,2, BigDecimal.ROUND_HALF_UP);
							}
						}
					}
					report.setColumn(str);
					report.setToWinASingleAmount(kpje);
					report.setWinSingular(yds);
					report.setTheGuestUnitPrice(kdj.setScale(2, BigDecimal.ROUND_HALF_UP));
					list.add(report);
			        for (int i11 = 0; i11 < list.size() - 1; i11++) {
			            for (int j = i11 + 1; j < list.size(); j++) {
			                if (list.get(i11).equals(list.get(j))) {
			                    list.remove(j);
			                }
			            }
			        }
				}else if(from.equals("未设置")) {
					sql2 = "SELECT sum(estimated_amount),COUNT(*) from crm_business_opportunity where 1=1 and sales_stage_name='赢单'  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + str + "%' ";
					ps2 = conn.prepareStatement(sql2);
					rs2 = ps2.executeQuery();
					BigDecimal kdj=new BigDecimal(0);
					while (rs2.next()) {
						kpje=rs2.getBigDecimal(1);
						yds=rs2.getBigDecimal(2);
						if(rs2.getBigDecimal(1)==null){
							
						}else{
							if(rs2.getBigDecimal(1).compareTo(new BigDecimal(0))==0){
								
							}else{

								kdj=kpje.divide(yds,2, BigDecimal.ROUND_HALF_UP);
							}
						}
					}
					report.setColumn(str);
					report.setToWinASingleAmount(kpje);
					report.setWinSingular(yds);
					report.setTheGuestUnitPrice(kdj.setScale(2, BigDecimal.ROUND_HALF_UP));
					list.add(report);
				}else {

					String[] xh=departmentIds.split(",");
				    for(String bmid : xh){
				    	if(!bmid.equals("")){
							sql2 = "SELECT sum(estimated_amount),COUNT(*) from crm_business_opportunity where 1=1 and sales_stage_name='赢单'  "+wSQL+" and department_id like'" + bmid + "' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + str + "%' ";
							ps2 = conn.prepareStatement(sql2);
							rs2 = ps2.executeQuery();
							BigDecimal kdj=new BigDecimal(0);
							while (rs2.next()) {
								kpje=rs2.getBigDecimal(1);
								yds=rs2.getBigDecimal(2);
								if(rs2.getBigDecimal(1)==null){
									
								}else{
									if(rs2.getBigDecimal(1).compareTo(new BigDecimal(0))==0){
										
									}else{

										kdj=kpje.divide(yds,2, BigDecimal.ROUND_HALF_UP);
									}
								}
							}
							report.setColumn(str);
							report.setToWinASingleAmount(kpje);
							report.setWinSingular(yds);
							report.setTheGuestUnitPrice(kdj.setScale(2, BigDecimal.ROUND_HALF_UP));
							list.add(report);
				    	}
				    }
					sql2 = "SELECT sum(estimated_amount),COUNT(*) from crm_business_opportunity where 1=1 and sales_stage_name='赢单'  "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + str + "%' ";
					ps2 = conn.prepareStatement(sql2);
					rs2 = ps2.executeQuery();
					BigDecimal kdj=new BigDecimal(0);
					while (rs2.next()) {
						kpje=rs2.getBigDecimal(1);
						yds=rs2.getBigDecimal(2);
						if(rs2.getBigDecimal(1)==null){
							
						}else{
							if(rs2.getBigDecimal(1).compareTo(new BigDecimal(0))==0){
								
							}else{

								kdj=kpje.divide(yds,2, BigDecimal.ROUND_HALF_UP);
							}
						}
					}
					report.setColumn(str);
					report.setToWinASingleAmount(kpje);
					report.setWinSingular(yds);
					report.setTheGuestUnitPrice(kdj.setScale(2, BigDecimal.ROUND_HALF_UP));
					list.add(report);
			        for (int i11 = 0; i11 < list.size() - 1; i11++) {
			            for (int j = i11 + 1; j < list.size(); j++) {
			                if (list.get(i11).equals(list.get(j))) {
			                    list.remove(j);
			                }
			            }
			        }
				}
				
			}

			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("List<Report> getItemsydsjhz(Report report)" + (t2 - t1) + " ms");
		} catch (SQLException | ParseException e) {
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		} finally {
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		}
		return list;
	}
	@Override
	public List<Report> getItemsSalesVolume(Report report) {
		long t1 = System.currentTimeMillis();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		PreparedStatement ps3 = null;
		ResultSet rs3 = null;
		PreparedStatement ps4 = null;
		ResultSet rs4 = null;
		List<Report> list = new ArrayList<Report>();
		String mechanismId=report.getMechanismId();
		String from=report.getFrom() == null ? "" : report.getFrom();
		String departmentIds=report.getDepartmentIds();
		String username=report.getUsername();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);

			String wSQL = "";

			if(report.getDepartmentId()!=null){
				if(report.getDepartmentId().equals("")){
					
				}else{
					if (report.getDepartmentId() != null || !report.getDepartmentId().equals("")) {
						wSQL += " and department_id='" + report.getDepartmentId() + "'";
					}
				}
			}
			if(from.equals("用户")) {
				wSQL += " and create_id ='" + username + "'";
			}
			int i=0;
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
				String nowdate=format.format(new Date());//当前月份
				report = new Report();
				BigDecimal xse=new BigDecimal(0);
				String sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%'  and create_date like'" + nowdate + "%' GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
				if(from.equals("全部")) {
					sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1 "+wSQL+" and mechanism_id like'" + mechanismId + "%'  and create_date like'" + nowdate + "%' GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
					ps = conn.prepareStatement(sql);
					rs = ps.executeQuery();
					int i1=1;
					while (rs.next()) {
						report.setRanking(i1++);
						report.setContractAmount(rs.getBigDecimal(1));
						report.setDepartmentName(rs.getString(2));
						report.setUsername(rs.getString(3));
						list.add(report);
					}
				}else {
					if(from.equals("本部门")) {

						String[] xh=departmentIds.split(",");
					    for(String bmid : xh){
					    	if(!bmid.equals("")){
								sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1 "+wSQL+" and department_id like'" + bmid + "%' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + nowdate + "%' GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report.setRanking(i1++);
									report.setContractAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
					    	}
					    }
						sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1 "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + nowdate + "%' GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
						ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						int i1=1;
						while (rs.next()) {
							report.setRanking(i1++);
							report.setContractAmount(rs.getBigDecimal(1));
							report.setDepartmentName(rs.getString(2));
							report.setUsername(rs.getString(3));
							list.add(report);
						}
				        for (int i11 = 0; i11 < list.size() - 1; i11++) {
				            for (int j = i11 + 1; j < list.size(); j++) {
				                if (list.get(i11).equals(list.get(j))) {
				                    list.remove(j);
				                }
				            }
				        }
					}else if(from.equals("未设置")) {
						sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1 "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + nowdate + "%' GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
						ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						int i1=1;
						while (rs.next()) {
							report.setRanking(i1++);
							report.setContractAmount(rs.getBigDecimal(1));
							report.setDepartmentName(rs.getString(2));
							report.setUsername(rs.getString(3));
							list.add(report);
						}
					}else {
						String[] xh=departmentIds.split(",");
					    for(String bmid : xh){
					    	if(!bmid.equals("")){
								sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1 "+wSQL+" and department_id like'" + bmid + "' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + nowdate + "%' GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i1=1;
								while (rs.next()) {
									report.setRanking(i1++);
									report.setContractAmount(rs.getBigDecimal(1));
									report.setDepartmentName(rs.getString(2));
									report.setUsername(rs.getString(3));
									list.add(report);
								}
					    	}
					    }
						sql = "SELECT SUM(total_contract_amount),department_name,create_name from crm_contract where 1=1 "+wSQL+" and create_id ='" + username + "' and mechanism_id like'" + mechanismId + "%'  and create_date like'" + nowdate + "%' GROUP BY create_name ORDER BY SUM(total_contract_amount) desc";
						ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						int i1=1;
						while (rs.next()) {
							report.setRanking(i1++);
							report.setContractAmount(rs.getBigDecimal(1));
							report.setDepartmentName(rs.getString(2));
							report.setUsername(rs.getString(3));
							list.add(report);
						}
				        for (int i11 = 0; i11 < list.size() - 1; i11++) {
				            for (int j = i11 + 1; j < list.size(); j++) {
				                if (list.get(i11).equals(list.get(j))) {
				                    list.remove(j);
				                }
				            }
				        }
						
					}
				}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("List<Report> getItemsxseht(Report report)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		} finally {
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		}
		return list;
	}

	@Override
	public List<Report> getItemsSalesVolumes(Report report) {
		long t1 = System.currentTimeMillis();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		PreparedStatement ps3 = null;
		ResultSet rs3 = null;
		PreparedStatement ps4 = null;
		ResultSet rs4 = null;
		List<Report> list = new ArrayList<Report>();
		String mechanismId=report.getMechanismId();
		String from=report.getFrom() == null ? "" : report.getFrom();
		String departmentIds=report.getDepartmentIds();
		String username=report.getUsername();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);

			String wSQL = "";

			if(report.getDepartmentId()!=null){
				if(report.getDepartmentId().equals("")){
					
				}else{
					if (report.getDepartmentId() != null || !report.getDepartmentId().equals("")) {
						wSQL += " and department_id='" + report.getDepartmentId() + "'";
					}
				}
			}
			if(from.equals("用户")) {
				wSQL += " and create_id ='" + username + "'";
			}
			int i=0;
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
				String nowdate=format.format(new Date());//当前月份
				report = new Report();
				BigDecimal xse=new BigDecimal(0);
				String sql="";
				sql = "SELECT SUM(estimated_amount),department_name,create_name from crm_business_opportunity where 1=1 "+wSQL+"   and sales_stage_name='赢单' and mechanism_id like'" + mechanismId + "%' and create_date like'" + nowdate + "%' GROUP BY create_name ORDER BY SUM(estimated_amount) desc";
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				int i1=1;
				while (rs.next()) {
					report.setRanking(i1++);
					report.setContractAmount(rs.getBigDecimal(1));
					report.setDepartmentName(rs.getString(2));
					report.setUsername(rs.getString(3));
					list.add(report);
				}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("List<Report> getItemsxsesj(Report report)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		} finally {
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		}
		return list;
	}

	@Override
	public List<Report> getItemsPerformanceobjectives(Report report)  {
		long t1 = System.currentTimeMillis();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		PreparedStatement ps3 = null;
		ResultSet rs3 = null;
		PreparedStatement ps4 = null;
		ResultSet rs4 = null;
		List<Report> list = new ArrayList<Report>();
		String mechanismId=report.getMechanismId();
		String from=report.getFrom() == null ? "" : report.getFrom();
		String departmentIds=report.getDepartmentIds();
		String username=report.getUsername();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);

			String wSQL = "";
			int i=0;
			if(report.getTimeToScreen()==null){
				report.setTimeToScreen("本月");
			}

			if(report.getDepartmentId()!=null){
				if(report.getDepartmentId().equals("")){
					
				}else{
					if (report.getDepartmentId() != null || !report.getDepartmentId().equals("")) {
						wSQL += " and department_id='" + report.getDepartmentId() + "'";
					}
				}
			}
			if(report.getUserId()!=null){
				if(report.getUserId().equals("")){
					
				}else{
					if (report.getUserId() != null || !report.getUserId().equals("")) {
						wSQL += " and create_id='" + report.getUserId() + "'";
					}
				}
			}
			if(from.equals("用户")) {
				wSQL += " ";  
			}
			if(report.getTimeToScreen().equals("上月")){

		        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		        Date date = new Date();
		        Calendar calendar = Calendar.getInstance();
		        calendar.setTime(date); // 设置为当前时间
		        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1); // 设置为上一个月
		        date = calendar.getTime();
		        String accDate = format.format(date);
				report = new Report();
				BigDecimal xse=new BigDecimal(0);
				
				String sql = "";
					if(from.equals("全部")) {
						sql = "SELECT SUM(estimated_amount) from crm_business_opportunity where 1=1 and mechanism_id like'" + mechanismId + "%'  and sales_stage_name='赢单' "+wSQL+" and create_date like'" + accDate + "%' ";
						ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						if (rs.next()) {
							xse=rs.getBigDecimal(1);
						}
						report.setColumn(accDate);
						report.setToWinASingleAmount(xse);
						list.add(report);
					}else{
						if(from.equals("本部门")) {
							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
									sql = "SELECT SUM(estimated_amount) from crm_business_opportunity where 1=1 and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "%' and sales_stage_name='赢单' "+wSQL+" and create_date like'" + accDate + "%' ";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									if (rs.next()) {
										xse=rs.getBigDecimal(1);
									}
									report.setColumn(accDate);
									report.setToWinASingleAmount(xse);
									list.add(report);
						    	}
						    }
						    sql = "SELECT SUM(estimated_amount) from crm_business_opportunity where 1=1 and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and sales_stage_name='赢单' "+wSQL+" and create_date like'" + accDate + "%' ";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							if (rs.next()) {
								xse=rs.getBigDecimal(1);
							}
							report.setColumn(accDate);
							report.setToWinASingleAmount(xse);
							list.add(report);
					        for (int i11 = 0; i11 < list.size() - 1; i11++) {
					            for (int j = i11 + 1; j < list.size(); j++) {
					                if (list.get(i11).equals(list.get(j))) {
					                    list.remove(j);
					                }
					            }
					        }
						}else if(from.equals("未设置")) {
						    sql = "SELECT SUM(estimated_amount) from crm_business_opportunity where 1=1 and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and sales_stage_name='赢单' "+wSQL+" and create_date like'" + accDate + "%' ";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							if (rs.next()) {
								xse=rs.getBigDecimal(1);
							}
							report.setColumn(accDate);
							report.setToWinASingleAmount(xse);
							list.add(report);
						}else {

							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
									sql = "SELECT SUM(estimated_amount) from crm_business_opportunity where 1=1 and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "' and sales_stage_name='赢单' "+wSQL+" and create_date like'" + accDate + "%' ";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									if (rs.next()) {
										xse=rs.getBigDecimal(1);
									}
									report.setColumn(accDate);
									report.setToWinASingleAmount(xse);
									list.add(report);
						    	}
						    }
						    sql = "SELECT SUM(estimated_amount) from crm_business_opportunity where 1=1 and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and sales_stage_name='赢单' "+wSQL+" and create_date like'" + accDate + "%' ";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							if (rs.next()) {
								xse=rs.getBigDecimal(1);
							}
							report.setColumn(accDate);
							report.setToWinASingleAmount(xse);
							list.add(report);
					        for (int i11 = 0; i11 < list.size() - 1; i11++) {
					            for (int j = i11 + 1; j < list.size(); j++) {
					                if (list.get(i11).equals(list.get(j))) {
					                    list.remove(j);
					                }
					            }
					        }
						}
					}
				
			}else if(report.getTimeToScreen().equals("下月")){

		        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		        Date date = new Date();
		        Calendar calendar = Calendar.getInstance();
		        calendar.setTime(date); // 设置为当前时间
		        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1); // 设置为上一个月
		        date = calendar.getTime();
		        String accDate = format.format(date);
				report = new Report();
				BigDecimal xse=new BigDecimal(0);

				String sql = "";
					if(from.equals("全部")) {
						sql = "SELECT SUM(estimated_amount) from crm_business_opportunity where 1=1 and mechanism_id like'" + mechanismId + "%'  and sales_stage_name='赢单' "+wSQL+" and create_date like'" + accDate + "%' ";
						ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						if (rs.next()) {
							xse=rs.getBigDecimal(1);
						}
						report.setColumn(accDate);
						report.setToWinASingleAmount(xse);
						list.add(report);
					}else{
						if(from.equals("本部门")) {

							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
									sql = "SELECT SUM(estimated_amount) from crm_business_opportunity where 1=1 and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "%' and sales_stage_name='赢单' "+wSQL+" and create_date like'" + accDate + "%' ";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									if (rs.next()) {
										xse=rs.getBigDecimal(1);
									}
									report.setColumn(accDate);
									report.setToWinASingleAmount(xse);
									list.add(report);
						    	}
						    }
						    sql = "SELECT SUM(estimated_amount) from crm_business_opportunity where 1=1 and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and sales_stage_name='赢单' "+wSQL+" and create_date like'" + accDate + "%' ";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							if (rs.next()) {
								xse=rs.getBigDecimal(1);
							}
							report.setColumn(accDate);
							report.setToWinASingleAmount(xse);
							list.add(report);
					        for (int i11 = 0; i11 < list.size() - 1; i11++) {
					            for (int j = i11 + 1; j < list.size(); j++) {
					                if (list.get(i11).equals(list.get(j))) {
					                    list.remove(j);
					                }
					            }
					        }
						}else if(from.equals("未设置")) {
						    sql = "SELECT SUM(estimated_amount) from crm_business_opportunity where 1=1 and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and sales_stage_name='赢单' "+wSQL+" and create_date like'" + accDate + "%' ";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							if (rs.next()) {
								xse=rs.getBigDecimal(1);
							}
							report.setColumn(accDate);
							report.setToWinASingleAmount(xse);
							list.add(report);
						}else {

							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
									sql = "SELECT SUM(estimated_amount) from crm_business_opportunity where 1=1 and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "' and sales_stage_name='赢单' "+wSQL+" and create_date like'" + accDate + "%' ";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									if (rs.next()) {
										xse=rs.getBigDecimal(1);
									}
									report.setColumn(accDate);
									report.setToWinASingleAmount(xse);
									list.add(report);
						    	}
						    }
						    sql = "SELECT SUM(estimated_amount) from crm_business_opportunity where 1=1 and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and sales_stage_name='赢单' "+wSQL+" and create_date like'" + accDate + "%' ";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							if (rs.next()) {
								xse=rs.getBigDecimal(1);
							}
							report.setColumn(accDate);
							report.setToWinASingleAmount(xse);
							list.add(report);
					        for (int i11 = 0; i11 < list.size() - 1; i11++) {
					            for (int j = i11 + 1; j < list.size(); j++) {
					                if (list.get(i11).equals(list.get(j))) {
					                    list.remove(j);
					                }
					            }
					        }
						}
					}
				
			}else if(report.getTimeToScreen().equals("本年")){


				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
				SimpleDateFormat formatz = new SimpleDateFormat("yyyy");
				
				String nowdate=format.format(new Date());//当前月份
				String nowdatez=formatz.format(new Date());//当前月份
				
				Date d1 = new SimpleDateFormat("yyyy-MM").parse(nowdatez+"-01");//定义起始日期
				
				Date d2 = new SimpleDateFormat("yyyy-MM").parse(nowdatez+"-12");//定义结束日期 可以去当前月也可以手动写日期。
				
				Calendar dd = Calendar.getInstance();//定义日期实例
				
				dd.setTime(d1);//设置日期起始时间
				
				while (dd.getTime().before(d2)) {
				
				SimpleDateFormat sdfzz = new SimpleDateFormat("yyyy-MM");

					String accDate = sdfzz.format(dd.getTime());
					report = new Report();
					BigDecimal xse=new BigDecimal(0);

					String sql = "";
						if(from.equals("全部")) {
							sql = "SELECT SUM(estimated_amount) from crm_business_opportunity where 1=1 and mechanism_id like'" + mechanismId + "%'  and sales_stage_name='赢单' "+wSQL+" and create_date like'" + accDate + "%' ";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							if (rs.next()) {
								xse=rs.getBigDecimal(1);
							}
							report.setColumn(accDate);
							report.setToWinASingleAmount(xse);
							list.add(report);
						}else{
							if(from.equals("本部门")) {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT SUM(estimated_amount) from crm_business_opportunity where 1=1 and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "%' and sales_stage_name='赢单' "+wSQL+" and create_date like'" + accDate + "%' ";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										if (rs.next()) {
											xse=rs.getBigDecimal(1);
										}
										report.setColumn(accDate);
										report.setToWinASingleAmount(xse);
										list.add(report);
							    	}
							    }
							    sql = "SELECT SUM(estimated_amount) from crm_business_opportunity where 1=1 and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and sales_stage_name='赢单' "+wSQL+" and create_date like'" + accDate + "%' ";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								if (rs.next()) {
									xse=rs.getBigDecimal(1);
								}
								report.setColumn(accDate);
								report.setToWinASingleAmount(xse);
								list.add(report);
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
							}else if(from.equals("未设置")) {
							    sql = "SELECT SUM(estimated_amount) from crm_business_opportunity where 1=1 and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and sales_stage_name='赢单' "+wSQL+" and create_date like'" + accDate + "%' ";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								if (rs.next()) {
									xse=rs.getBigDecimal(1);
								}
								report.setColumn(accDate);
								report.setToWinASingleAmount(xse);
								list.add(report);
							}else {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT SUM(estimated_amount) from crm_business_opportunity where 1=1 and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "' and sales_stage_name='赢单' "+wSQL+" and create_date like'" + accDate + "%' ";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										if (rs.next()) {
											xse=rs.getBigDecimal(1);
										}
										report.setColumn(accDate);
										report.setToWinASingleAmount(xse);
										list.add(report);
							    	}
							    }
							    sql = "SELECT SUM(estimated_amount) from crm_business_opportunity where 1=1 and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and sales_stage_name='赢单' "+wSQL+" and create_date like'" + accDate + "%' ";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								if (rs.next()) {
									xse=rs.getBigDecimal(1);
								}
								report.setColumn(accDate);
								report.setToWinASingleAmount(xse);
								list.add(report);
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
							}
						}
					dd.add(Calendar.MONTH, 1);//进行当前日期月份加1
				
				}

				SimpleDateFormat sdfzz = new SimpleDateFormat("yyyy-MM");
				String accDate=sdfzz.format(d2.getTime());
				report = new Report();
				BigDecimal xse=new BigDecimal(0);

				String sql = "";
					if(from.equals("全部")) {
						sql = "SELECT SUM(estimated_amount) from crm_business_opportunity where 1=1 and mechanism_id like'" + mechanismId + "%'  and sales_stage_name='赢单' "+wSQL+" and create_date like'" + accDate + "%' ";
						ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						if (rs.next()) {
							xse=rs.getBigDecimal(1);
						}
						report.setColumn(accDate);
						report.setToWinASingleAmount(xse);
						list.add(report);
					}else{
						if(from.equals("本部门")) {

							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
									sql = "SELECT SUM(estimated_amount) from crm_business_opportunity where 1=1 and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "%' and sales_stage_name='赢单' "+wSQL+" and create_date like'" + accDate + "%' ";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									if (rs.next()) {
										xse=rs.getBigDecimal(1);
									}
									report.setColumn(accDate);
									report.setToWinASingleAmount(xse);
									list.add(report);
						    	}
						    }
						    sql = "SELECT SUM(estimated_amount) from crm_business_opportunity where 1=1 and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and sales_stage_name='赢单' "+wSQL+" and create_date like'" + accDate + "%' ";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							if (rs.next()) {
								xse=rs.getBigDecimal(1);
							}
							report.setColumn(accDate);
							report.setToWinASingleAmount(xse);
							list.add(report);
					        for (int i11 = 0; i11 < list.size() - 1; i11++) {
					            for (int j = i11 + 1; j < list.size(); j++) {
					                if (list.get(i11).equals(list.get(j))) {
					                    list.remove(j);
					                }
					            }
					        }
						}else if(from.equals("未设置")) {
						    sql = "SELECT SUM(estimated_amount) from crm_business_opportunity where 1=1 and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and sales_stage_name='赢单' "+wSQL+" and create_date like'" + accDate + "%' ";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							if (rs.next()) {
								xse=rs.getBigDecimal(1);
							}
							report.setColumn(accDate);
							report.setToWinASingleAmount(xse);
							list.add(report);
						}else {
							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
									sql = "SELECT SUM(estimated_amount) from crm_business_opportunity where 1=1 and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "' and sales_stage_name='赢单' "+wSQL+" and create_date like'" + accDate + "%' ";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									if (rs.next()) {
										xse=rs.getBigDecimal(1);
									}
									report.setColumn(accDate);
									report.setToWinASingleAmount(xse);
									list.add(report);
						    	}
						    }
						    sql = "SELECT SUM(estimated_amount) from crm_business_opportunity where 1=1 and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and sales_stage_name='赢单' "+wSQL+" and create_date like'" + accDate + "%' ";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							if (rs.next()) {
								xse=rs.getBigDecimal(1);
							}
							report.setColumn(accDate);
							report.setToWinASingleAmount(xse);
							list.add(report);
					        for (int i11 = 0; i11 < list.size() - 1; i11++) {
					            for (int j = i11 + 1; j < list.size(); j++) {
					                if (list.get(i11).equals(list.get(j))) {
					                    list.remove(j);
					                }
					            }
					        }
							
						}
					}
			
				
				

				
			}else if(report.getTimeToScreen().equals("上半年")){


				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
				SimpleDateFormat formatz = new SimpleDateFormat("yyyy");
				
				String nowdate=format.format(new Date());//当前月份
				String nowdatez=formatz.format(new Date());//当前月份
				
				Date d1 = new SimpleDateFormat("yyyy-MM").parse(nowdatez+"-01");//定义起始日期
				
				Date d2 = new SimpleDateFormat("yyyy-MM").parse(nowdatez+"-06");//定义结束日期 可以去当前月也可以手动写日期。
				
				Calendar dd = Calendar.getInstance();//定义日期实例
				
				dd.setTime(d1);//设置日期起始时间
				
				while (dd.getTime().before(d2)) {
				
				SimpleDateFormat sdfzz = new SimpleDateFormat("yyyy-MM");

					String accDate = sdfzz.format(dd.getTime());
					report = new Report();
					BigDecimal xse=new BigDecimal(0);

					String sql = "";
						if(from.equals("全部")) {
							sql = "SELECT SUM(estimated_amount) from crm_business_opportunity where 1=1 and mechanism_id like'" + mechanismId + "%'  and sales_stage_name='赢单' "+wSQL+" and create_date like'" + accDate + "%' ";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							if (rs.next()) {
								xse=rs.getBigDecimal(1);
							}
							report.setColumn(accDate);
							report.setToWinASingleAmount(xse);
							list.add(report);
					        dd.add(Calendar.MONTH, 1);//进行当前日期月份加1
						}else{
							if(from.equals("本部门")) {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT SUM(estimated_amount) from crm_business_opportunity where 1=1 and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "%' and sales_stage_name='赢单' "+wSQL+" and create_date like'" + accDate + "%' ";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										if (rs.next()) {
											xse=rs.getBigDecimal(1);
										}
										report.setColumn(accDate);
										report.setToWinASingleAmount(xse);
										list.add(report);
							    	}
							    }
							    sql = "SELECT SUM(estimated_amount) from crm_business_opportunity where 1=1 and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and sales_stage_name='赢单' "+wSQL+" and create_date like'" + accDate + "%' ";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								if (rs.next()) {
									xse=rs.getBigDecimal(1);
								}
								report.setColumn(accDate);
								report.setToWinASingleAmount(xse);
								list.add(report);
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
						        dd.add(Calendar.MONTH, 1);//进行当前日期月份加1
							}else if(from.equals("未设置")) {
							    sql = "SELECT SUM(estimated_amount) from crm_business_opportunity where 1=1 and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and sales_stage_name='赢单' "+wSQL+" and create_date like'" + accDate + "%' ";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								if (rs.next()) {
									xse=rs.getBigDecimal(1);
								}
								report.setColumn(accDate);
								report.setToWinASingleAmount(xse);
								list.add(report);
						        dd.add(Calendar.MONTH, 1);//进行当前日期月份加1
							}else {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT SUM(estimated_amount) from crm_business_opportunity where 1=1 and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "' and sales_stage_name='赢单' "+wSQL+" and create_date like'" + accDate + "%' ";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										if (rs.next()) {
											xse=rs.getBigDecimal(1);
										}
										report.setColumn(accDate);
										report.setToWinASingleAmount(xse);
										list.add(report);
							    	}
							    }
							    sql = "SELECT SUM(estimated_amount) from crm_business_opportunity where 1=1 and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and sales_stage_name='赢单' "+wSQL+" and create_date like'" + accDate + "%' ";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								if (rs.next()) {
									xse=rs.getBigDecimal(1);
								}
								report.setColumn(accDate);
								report.setToWinASingleAmount(xse);
								list.add(report);
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
						        dd.add(Calendar.MONTH, 1);//进行当前日期月份加1
							}
						}
				
				}

				SimpleDateFormat sdfzz = new SimpleDateFormat("yyyy-MM");
				String accDate=sdfzz.format(d2.getTime());
				report = new Report();
				BigDecimal xse=new BigDecimal(0);

				String sql = "";
					if(from.equals("全部")) {
						sql = "SELECT SUM(estimated_amount) from crm_business_opportunity where 1=1 and mechanism_id like'" + mechanismId + "%'  and sales_stage_name='赢单' "+wSQL+" and create_date like'" + accDate + "%' ";
						ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						if (rs.next()) {
							xse=rs.getBigDecimal(1);
						}
						report.setColumn(accDate);
						report.setToWinASingleAmount(xse);
						list.add(report);
					}else{
						if(from.equals("本部门")) {

							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
									sql = "SELECT SUM(estimated_amount) from crm_business_opportunity where 1=1 and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "%' and sales_stage_name='赢单' "+wSQL+" and create_date like'" + accDate + "%' ";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									if (rs.next()) {
										xse=rs.getBigDecimal(1);
									}
									report.setColumn(accDate);
									report.setToWinASingleAmount(xse);
									list.add(report);
						    	}
						    }
						    sql = "SELECT SUM(estimated_amount) from crm_business_opportunity where 1=1 and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and sales_stage_name='赢单' "+wSQL+" and create_date like'" + accDate + "%' ";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							if (rs.next()) {
								xse=rs.getBigDecimal(1);
							}
							report.setColumn(accDate);
							report.setToWinASingleAmount(xse);
							list.add(report);
					        for (int i11 = 0; i11 < list.size() - 1; i11++) {
					            for (int j = i11 + 1; j < list.size(); j++) {
					                if (list.get(i11).equals(list.get(j))) {
					                    list.remove(j);
					                }
					            }
					        }
						}else if(from.equals("未设置")) {
						    sql = "SELECT SUM(estimated_amount) from crm_business_opportunity where 1=1 and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and sales_stage_name='赢单' "+wSQL+" and create_date like'" + accDate + "%' ";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							if (rs.next()) {
								xse=rs.getBigDecimal(1);
							}
							report.setColumn(accDate);
							report.setToWinASingleAmount(xse);
							list.add(report);
						}else {

							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
									sql = "SELECT SUM(estimated_amount) from crm_business_opportunity where 1=1 and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "' and sales_stage_name='赢单' "+wSQL+" and create_date like'" + accDate + "%' ";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									if (rs.next()) {
										xse=rs.getBigDecimal(1);
									}
									report.setColumn(accDate);
									report.setToWinASingleAmount(xse);
									list.add(report);
						    	}
						    }
						    sql = "SELECT SUM(estimated_amount) from crm_business_opportunity where 1=1 and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and sales_stage_name='赢单' "+wSQL+" and create_date like'" + accDate + "%' ";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							if (rs.next()) {
								xse=rs.getBigDecimal(1);
							}
							report.setColumn(accDate);
							report.setToWinASingleAmount(xse);
							list.add(report);
					        for (int i11 = 0; i11 < list.size() - 1; i11++) {
					            for (int j = i11 + 1; j < list.size(); j++) {
					                if (list.get(i11).equals(list.get(j))) {
					                    list.remove(j);
					                }
					            }
					        }
						}
					}
			
				
				

				
			}else if(report.getTimeToScreen().equals("下半年")){


				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
				SimpleDateFormat formatz = new SimpleDateFormat("yyyy");
				
				String nowdate=format.format(new Date());//当前月份
				String nowdatez=formatz.format(new Date());//当前月份
				
				Date d1 = new SimpleDateFormat("yyyy-MM").parse(nowdatez+"-07");//定义起始日期
				
				Date d2 = new SimpleDateFormat("yyyy-MM").parse(nowdatez+"-12");//定义结束日期 可以去当前月也可以手动写日期。
				
				Calendar dd = Calendar.getInstance();//定义日期实例
				
				dd.setTime(d1);//设置日期起始时间
				
				while (dd.getTime().before(d2)) {
				
				SimpleDateFormat sdfzz = new SimpleDateFormat("yyyy-MM");

					String accDate = sdfzz.format(dd.getTime());
					report = new Report();
					BigDecimal xse=new BigDecimal(0);

					String sql = "";
						if(from.equals("全部")) {
							sql = "SELECT SUM(estimated_amount) from crm_business_opportunity where 1=1 and mechanism_id like'" + mechanismId + "%'  and sales_stage_name='赢单' "+wSQL+" and create_date like'" + accDate + "%' ";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							if (rs.next()) {
								xse=rs.getBigDecimal(1);
							}
							report.setColumn(accDate);
							report.setToWinASingleAmount(xse);
							list.add(report);
					        dd.add(Calendar.MONTH, 1);//进行当前日期月份加1
						}else{
							if(from.equals("本部门")) {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT SUM(estimated_amount) from crm_business_opportunity where 1=1 and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "%' and sales_stage_name='赢单' "+wSQL+" and create_date like'" + accDate + "%' ";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										if (rs.next()) {
											xse=rs.getBigDecimal(1);
										}
										report.setColumn(accDate);
										report.setToWinASingleAmount(xse);
										list.add(report);
							    	}
							    }
							    sql = "SELECT SUM(estimated_amount) from crm_business_opportunity where 1=1 and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and sales_stage_name='赢单' "+wSQL+" and create_date like'" + accDate + "%' ";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								if (rs.next()) {
									xse=rs.getBigDecimal(1);
								}
								report.setColumn(accDate);
								report.setToWinASingleAmount(xse);
								list.add(report);
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
						        dd.add(Calendar.MONTH, 1);//进行当前日期月份加1
							}else if(from.equals("未设置")) {
							    sql = "SELECT SUM(estimated_amount) from crm_business_opportunity where 1=1 and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and sales_stage_name='赢单' "+wSQL+" and create_date like'" + accDate + "%' ";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								if (rs.next()) {
									xse=rs.getBigDecimal(1);
								}
								report.setColumn(accDate);
								report.setToWinASingleAmount(xse);
								list.add(report);
						        dd.add(Calendar.MONTH, 1);//进行当前日期月份加1
							}else {

								String[] xh=departmentIds.split(",");
							    for(String bmid : xh){
							    	if(!bmid.equals("")){
										sql = "SELECT SUM(estimated_amount) from crm_business_opportunity where 1=1 and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "' and sales_stage_name='赢单' "+wSQL+" and create_date like'" + accDate + "%' ";
										ps = conn.prepareStatement(sql);
										rs = ps.executeQuery();
										if (rs.next()) {
											xse=rs.getBigDecimal(1);
										}
										report.setColumn(accDate);
										report.setToWinASingleAmount(xse);
										list.add(report);
							    	}
							    }
							    sql = "SELECT SUM(estimated_amount) from crm_business_opportunity where 1=1 and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and sales_stage_name='赢单' "+wSQL+" and create_date like'" + accDate + "%' ";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								if (rs.next()) {
									xse=rs.getBigDecimal(1);
								}
								report.setColumn(accDate);
								report.setToWinASingleAmount(xse);
								list.add(report);
						        for (int i11 = 0; i11 < list.size() - 1; i11++) {
						            for (int j = i11 + 1; j < list.size(); j++) {
						                if (list.get(i11).equals(list.get(j))) {
						                    list.remove(j);
						                }
						            }
						        }
						        dd.add(Calendar.MONTH, 1);//进行当前日期月份加1
							}
						}
				
				}

				SimpleDateFormat sdfzz = new SimpleDateFormat("yyyy-MM");
				String accDate=sdfzz.format(d2.getTime());
				report = new Report();
				BigDecimal xse=new BigDecimal(0);

				String sql = "";
					if(from.equals("全部")) {
						sql = "SELECT SUM(estimated_amount) from crm_business_opportunity where 1=1 and mechanism_id like'" + mechanismId + "%'  and sales_stage_name='赢单' "+wSQL+" and create_date like'" + accDate + "%' ";
						ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						if (rs.next()) {
							xse=rs.getBigDecimal(1);
						}
						report.setColumn(accDate);
						report.setToWinASingleAmount(xse);
						list.add(report);
					}else{

						if(from.equals("本部门")) {
							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
									sql = "SELECT SUM(estimated_amount) from crm_business_opportunity where 1=1 and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "%' and sales_stage_name='赢单' "+wSQL+" and create_date like'" + accDate + "%' ";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									if (rs.next()) {
										xse=rs.getBigDecimal(1);
									}
									report.setColumn(accDate);
									report.setToWinASingleAmount(xse);
									list.add(report);
						    	}
						    }
						    sql = "SELECT SUM(estimated_amount) from crm_business_opportunity where 1=1 and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and sales_stage_name='赢单' "+wSQL+" and create_date like'" + accDate + "%' ";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							if (rs.next()) {
								xse=rs.getBigDecimal(1);
							}
							report.setColumn(accDate);
							report.setToWinASingleAmount(xse);
							list.add(report);
					        for (int i11 = 0; i11 < list.size() - 1; i11++) {
					            for (int j = i11 + 1; j < list.size(); j++) {
					                if (list.get(i11).equals(list.get(j))) {
					                    list.remove(j);
					                }
					            }
					        }
							
						}else if(from.equals("未设置")) {
						    sql = "SELECT SUM(estimated_amount) from crm_business_opportunity where 1=1 and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and sales_stage_name='赢单' "+wSQL+" and create_date like'" + accDate + "%' ";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							if (rs.next()) {
								xse=rs.getBigDecimal(1);
							}
							report.setColumn(accDate);
							report.setToWinASingleAmount(xse);
							list.add(report);
						}else {

							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
									sql = "SELECT SUM(estimated_amount) from crm_business_opportunity where 1=1 and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "' and sales_stage_name='赢单' "+wSQL+" and create_date like'" + accDate + "%' ";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									if (rs.next()) {
										xse=rs.getBigDecimal(1);
									}
									report.setColumn(accDate);
									report.setToWinASingleAmount(xse);
									list.add(report);
						    	}
						    }
						    sql = "SELECT SUM(estimated_amount) from crm_business_opportunity where 1=1 and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and sales_stage_name='赢单' "+wSQL+" and create_date like'" + accDate + "%' ";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							if (rs.next()) {
								xse=rs.getBigDecimal(1);
							}
							report.setColumn(accDate);
							report.setToWinASingleAmount(xse);
							list.add(report);
					        for (int i11 = 0; i11 < list.size() - 1; i11++) {
					            for (int j = i11 + 1; j < list.size(); j++) {
					                if (list.get(i11).equals(list.get(j))) {
					                    list.remove(j);
					                }
					            }
					        }
						}
					}
			
				
				

				
			} else {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
				String accDate=format.format(new Date());//当前月份
				report = new Report();
				BigDecimal xse=new BigDecimal(0);

				String sql = "";
					if(from.equals("全部")) {
						sql = "SELECT SUM(estimated_amount) from crm_business_opportunity where 1=1 and mechanism_id like'" + mechanismId + "%'  and sales_stage_name='赢单' "+wSQL+" and create_date like'" + accDate + "%' ";
						ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						if (rs.next()) {
							xse=rs.getBigDecimal(1);
						}
						report.setColumn(accDate);
						report.setToWinASingleAmount(xse);
						list.add(report);
					}else{
						if(from.equals("本部门")) {

							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
									sql = "SELECT SUM(estimated_amount) from crm_business_opportunity where 1=1 and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "%' and sales_stage_name='赢单' "+wSQL+" and create_date like'" + accDate + "%' ";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									if (rs.next()) {
										xse=rs.getBigDecimal(1);
									}
									report.setColumn(accDate);
									report.setToWinASingleAmount(xse);
									list.add(report);
						    	}
						    }
						    sql = "SELECT SUM(estimated_amount) from crm_business_opportunity where 1=1 and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and sales_stage_name='赢单' "+wSQL+" and create_date like'" + accDate + "%' ";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							if (rs.next()) {
								xse=rs.getBigDecimal(1);
							}
							report.setColumn(accDate);
							report.setToWinASingleAmount(xse);
							list.add(report);
					        for (int i11 = 0; i11 < list.size() - 1; i11++) {
					            for (int j = i11 + 1; j < list.size(); j++) {
					                if (list.get(i11).equals(list.get(j))) {
					                    list.remove(j);
					                }
					            }
					        }
						}else if(from.equals("未设置")) {
						    sql = "SELECT SUM(estimated_amount) from crm_business_opportunity where 1=1 and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and sales_stage_name='赢单' "+wSQL+" and create_date like'" + accDate + "%' ";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							if (rs.next()) {
								xse=rs.getBigDecimal(1);
							}
							report.setColumn(accDate);
							report.setToWinASingleAmount(xse);
							list.add(report);
						}else {

							String[] xh=departmentIds.split(",");
						    for(String bmid : xh){
						    	if(!bmid.equals("")){
									sql = "SELECT SUM(estimated_amount) from crm_business_opportunity where 1=1 and mechanism_id like'" + mechanismId + "%' and department_id like'" + bmid + "' and sales_stage_name='赢单' "+wSQL+" and create_date like'" + accDate + "%' ";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									if (rs.next()) {
										xse=rs.getBigDecimal(1);
									}
									report.setColumn(accDate);
									report.setToWinASingleAmount(xse);
									list.add(report);
						    	}
						    }
						    sql = "SELECT SUM(estimated_amount) from crm_business_opportunity where 1=1 and mechanism_id like'" + mechanismId + "%' and create_id ='" + username + "' and sales_stage_name='赢单' "+wSQL+" and create_date like'" + accDate + "%' ";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							if (rs.next()) {
								xse=rs.getBigDecimal(1);
							}
							report.setColumn(accDate);
							report.setToWinASingleAmount(xse);
							list.add(report);
					        for (int i11 = 0; i11 < list.size() - 1; i11++) {
					            for (int j = i11 + 1; j < list.size(); j++) {
					                if (list.get(i11).equals(list.get(j))) {
					                    list.remove(j);
					                }
					            }
					        }
						}
					}
			}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("List<Report> getItemsyjmb(Report report)" + (t2 - t1) + " ms");
		} catch (SQLException | ParseException e) {
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		} finally {
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		}
		return list;
	}

	@Override
	public List<Report> getItemsSalesCollectionRanking(Report report) {
		long t1 = System.currentTimeMillis();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		List<Report> list = new ArrayList<Report>();
		String from=report.getFrom() == null ? "" : report.getFrom();String bmids=report.getDepartmentIds();
		String username=report.getUsername();
		String mechanismId=report.getMechanismId();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);

			String wSQL = "";

			if(report.getDepartmentId()!=null){
				if(report.getDepartmentId().equals("")){
					
				}else{
					if (report.getDepartmentId() != null || !report.getDepartmentId().equals("")) {
						wSQL += " and department_id='" + report.getDepartmentId() + "'";
					}
				}
			}
			if(from.equals("用户")) {
				wSQL += " ";
			}
			String sql = "";
				if(from.equals("全部")) {
					sql = "SELECT sum(collection_amount),create_name,department_name from crm_collection where 1=1 "+wSQL+" and mechanism_id like '"+mechanismId+"%' GROUP BY create_name ORDER BY sum(collection_amount) DESC ";
					ps = conn.prepareStatement(sql);
					rs = ps.executeQuery();
					int i=1;
					while (rs.next()) {
						report = new Report();
						report.setRanking(i++);
						report.setReceivableAmount(rs.getBigDecimal(1));
						report.setUsername(rs.getString(2));
						report.setDepartmentName(rs.getString(3));
						list.add(report);
					}
				}else {
					if(from.equals("本部门")) {

						String[] xh=bmids.split(",");
					    for(String bmid : xh){
					    	if(!bmid.equals("")){
								sql = "SELECT sum(collection_amount),create_name,department_name from crm_collection where 1=1 "+wSQL+" and mechanism_id like '"+mechanismId+"%' and department_id like'" + bmid + "%' GROUP BY create_name ORDER BY sum(collection_amount) DESC ";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i++);
									report.setReceivableAmount(rs.getBigDecimal(1));
									report.setUsername(rs.getString(2));
									report.setDepartmentName(rs.getString(3));
									list.add(report);
								}
					    	}
					    }
						sql = "SELECT sum(collection_amount),create_name,department_name from crm_collection where 1=1 "+wSQL+" and mechanism_id like '"+mechanismId+"%' and create_id ='" + username + "' GROUP BY create_name ORDER BY sum(collection_amount) DESC ";
						ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						int i=1;
						while (rs.next()) {
							report = new Report();
							report.setRanking(i++);
							report.setReceivableAmount(rs.getBigDecimal(1));
							report.setUsername(rs.getString(2));
							report.setDepartmentName(rs.getString(3));
							list.add(report);
						}
				        for (int i11 = 0; i11 < list.size() - 1; i11++) {
				            for (int j = i11 + 1; j < list.size(); j++) {
				                if (list.get(i11).equals(list.get(j))) {
				                    list.remove(j);
				                }
				            }
				        }
					}else if(from.equals("未设置")) {
						sql = "SELECT sum(collection_amount),create_name,department_name from crm_collection where 1=1 "+wSQL+" and mechanism_id like '"+mechanismId+"%' and create_id ='" + username + "' GROUP BY create_name ORDER BY sum(collection_amount) DESC ";
						ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						int i=1;
						while (rs.next()) {
							report = new Report();
							report.setRanking(i++);
							report.setReceivableAmount(rs.getBigDecimal(1));
							report.setUsername(rs.getString(2));
							report.setDepartmentName(rs.getString(3));
							list.add(report);
						}
					}else {
						String[] xh=bmids.split(",");
					    for(String bmid : xh){
					    	if(!bmid.equals("")){
								sql = "SELECT sum(collection_amount),create_name,department_name from crm_collection where 1=1 "+wSQL+" and mechanism_id like '"+mechanismId+"%' and department_id like'" + bmid + "' GROUP BY create_name ORDER BY sum(collection_amount) DESC ";
								ps = conn.prepareStatement(sql);
								rs = ps.executeQuery();
								int i=1;
								while (rs.next()) {
									report = new Report();
									report.setRanking(i++);
									report.setReceivableAmount(rs.getBigDecimal(1));
									report.setUsername(rs.getString(2));
									report.setDepartmentName(rs.getString(3));
									list.add(report);
								}
					    	}
					    }
						sql = "SELECT sum(collection_amount),create_name,department_name from crm_collection where 1=1 "+wSQL+" and mechanism_id like '"+mechanismId+"%' and create_id ='" + username + "' GROUP BY create_name ORDER BY sum(collection_amount) DESC ";
						ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();
						int i=1;
						while (rs.next()) {
							report = new Report();
							report.setRanking(i++);
							report.setReceivableAmount(rs.getBigDecimal(1));
							report.setUsername(rs.getString(2));
							report.setDepartmentName(rs.getString(3));
							list.add(report);
						}
				        for (int i11 = 0; i11 < list.size() - 1; i11++) {
				            for (int j = i11 + 1; j < list.size(); j++) {
				                if (list.get(i11).equals(list.get(j))) {
				                    list.remove(j);
				                }
				            }
				        }
						
					}
				}

			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("List<Report> getItemsxshkpm(Report report)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		} finally {
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		}
		return list;
	}

	@Override
	public List<Report> getItems(Report report) {
		long t1 = System.currentTimeMillis();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Report> list = new ArrayList<Report>();
		String jgid=report.getMechanismId();String ly=report.getFrom() == null ? "" : report.getFrom();String bmids=report.getDepartmentIds();
		String username=report.getUsername();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);

			String wSQL = "";
			int i=0;
			if(report.getTime()==null){
				
			}else{
				if(report.getTime().equals("全部")){
					
				}else {
					i++;
					if (report.getTime() != null || !report.getTime().equals("")) {
						wSQL += " and time>='" + report.getTime() + "'";
					}
					if (report.getZtime() != null || !report.getTime().equals("")) {
						wSQL += " and time<='" + report.getZtime() + "'";
					}
				}
			}
			if(report.getTime()==null){
				
			}else{
				if(report.getTime().equals("全部")){
					
				}else {
					if (report.getTime() != null || !report.getTime().equals("")) {
						wSQL += " and create_date>='" + report.getTime() + "'";
					}
					if (report.getZtime() != null || !report.getTime().equals("")) {
						wSQL += " and create_date<='" + report.getZtime() + "'";
					}
				}
			}
			if(report.getUserId()==null){
			}else{
					if (!report.getUserId().equals("")) {
						wSQL += " and create_id='" + report.getUserId() + "'";
					}
			}
			if(report.getDepartmentId()==null){
			}else{
				if (!report.getDepartmentId().equals("")) {
					wSQL += " and department_id='" + report.getDepartmentId() + "'";
				}
			}
			String sql = "";

				if(ly.equals("全部")) {
					sql = "SELECT count(*),froms,create_name from crm_follow_up where 1=1  "+wSQL+"  and mechanism_id like ?  GROUP BY froms";
					ps = conn.prepareStatement(sql);
					ps.setString(1, jgid+"%");
					rs = ps.executeQuery();
					int itj=0;
					int ixs=0;
					int ikh=0;
					int isj=0;
					int iht=0;
					while (rs.next()) {
						itj +=rs.getInt(1);
						if(rs.getString(2).equals("线索")){
							ixs =rs.getInt(1);
						}else if(rs.getString(2).equals("客户")){
							ikh =rs.getInt(1);
						}else if(rs.getString(2).equals("商机")){
							isj =rs.getInt(1);
						}else if(rs.getString(2).equals("合同")){
							iht =rs.getInt(1);
						}
					}
					report = new Report();
					report.setRanking(itj);
					report.setClueNumber(ixs);
					report.setCustomerNumber(ikh);
					report.setBusinessOpportunitiesNumber(isj);
					report.setContractsNumber(iht);
					list.add(report);
				}else {
					if(ly.equals("本部门")) {

						String[] xh=bmids.split(",");
					    for(String bmid : xh){
					    	if(!bmid.equals("")){
								sql = "SELECT count(*),froms,create_name from crm_follow_up where 1=1  "+wSQL+"  and mechanism_id like ?  and department_id like ?   GROUP BY froms";
								ps = conn.prepareStatement(sql);
								ps.setString(1, jgid+"%");
								ps.setString(2, bmid+"%");
								rs = ps.executeQuery();
								int itj=0;
								int ixs=0;
								int ikh=0;
								int isj=0;
								int iht=0;
								while (rs.next()) {
									itj +=rs.getInt(1);
									if(rs.getString(2).equals("线索")){
										ixs =rs.getInt(1);
									}else if(rs.getString(2).equals("客户")){
										ikh =rs.getInt(1);
									}else if(rs.getString(2).equals("商机")){
										isj =rs.getInt(1);
									}else if(rs.getString(2).equals("合同")){
										iht =rs.getInt(1);
									}
								}
								report = new Report();
								report.setRanking(itj);
								report.setClueNumber(ixs);
								report.setCustomerNumber(ikh);
								report.setBusinessOpportunitiesNumber(isj);
								report.setContractsNumber(iht);
								list.add(report);
					    	}
					    }

						sql = "SELECT count(*),froms,create_name from crm_follow_up where 1=1  "+wSQL+"  and mechanism_id like ?  and create_id like ?   GROUP BY froms";
						ps = conn.prepareStatement(sql);
						ps.setString(1, jgid+"%");
						ps.setString(2, username);
						rs = ps.executeQuery();
						int itj=0;
						int ixs=0;
						int ikh=0;
						int isj=0;
						int iht=0;
						while (rs.next()) {
							itj +=rs.getInt(1);
							if(rs.getString(2).equals("线索")){
								ixs =rs.getInt(1);
							}else if(rs.getString(2).equals("客户")){
								ikh =rs.getInt(1);
							}else if(rs.getString(2).equals("商机")){
								isj =rs.getInt(1);
							}else if(rs.getString(2).equals("合同")){
								iht =rs.getInt(1);
							}
						}
						report = new Report();
						report.setRanking(itj);
						report.setClueNumber(ixs);
						report.setCustomerNumber(ikh);
						report.setBusinessOpportunitiesNumber(isj);
						report.setContractsNumber(iht);
						list.add(report);

				        for (int i11 = 0; i11 < list.size() - 1; i11++) {
				            for (int j = i11 + 1; j < list.size(); j++) {
				                if (list.get(i11).equals(list.get(j))) {
				                    list.remove(j);
				                }
				            }
				        }
					}else if(ly.equals("未设置")) {

						sql = "SELECT count(*),froms,create_name from crm_follow_up where 1=1  "+wSQL+"  and mechanism_id like ?  and create_id like ?   GROUP BY froms";
						ps = conn.prepareStatement(sql);
						ps.setString(1, jgid+"%");
						ps.setString(2, username);
						rs = ps.executeQuery();
						int itj=0;
						int ixs=0;
						int ikh=0;
						int isj=0;
						int iht=0;
						while (rs.next()) {
							itj +=rs.getInt(1);
							if(rs.getString(2).equals("线索")){
								ixs =rs.getInt(1);
							}else if(rs.getString(2).equals("客户")){
								ikh =rs.getInt(1);
							}else if(rs.getString(2).equals("商机")){
								isj =rs.getInt(1);
							}else if(rs.getString(2).equals("合同")){
								iht =rs.getInt(1);
							}
						}
						report = new Report();
						report.setRanking(itj);
						report.setClueNumber(ixs);
						report.setCustomerNumber(ikh);
						report.setBusinessOpportunitiesNumber(isj);
						report.setContractsNumber(iht);
						list.add(report);
					}else {
						String[] xh=bmids.split(",");
					    for(String bmid : xh){
					    	if(!bmid.equals("")){
								sql = "SELECT count(*),froms,create_name from crm_follow_up where 1=1  "+wSQL+"  and mechanism_id like ?  and department_id like ?   GROUP BY froms";
								ps = conn.prepareStatement(sql);
								ps.setString(1, jgid+"%");
								ps.setString(2, bmid);
								rs = ps.executeQuery();
								int itj=0;
								int ixs=0;
								int ikh=0;
								int isj=0;
								int iht=0;
								while (rs.next()) {
									itj +=rs.getInt(1);
									if(rs.getString(2).equals("线索")){
										ixs =rs.getInt(1);
									}else if(rs.getString(2).equals("客户")){
										ikh =rs.getInt(1);
									}else if(rs.getString(2).equals("商机")){
										isj =rs.getInt(1);
									}else if(rs.getString(2).equals("合同")){
										iht =rs.getInt(1);
									}
								}
								report = new Report();
								report.setRanking(itj);
								report.setClueNumber(ixs);
								report.setCustomerNumber(ikh);
								report.setBusinessOpportunitiesNumber(isj);
								report.setContractsNumber(iht);
								list.add(report);
					    	}
					    }

						sql = "SELECT count(*),froms,create_name from crm_follow_up where 1=1  "+wSQL+"  and mechanism_id like ?  and create_id like ?   GROUP BY froms";
						ps = conn.prepareStatement(sql);
						ps.setString(1, jgid+"%");
						ps.setString(2, username);
						rs = ps.executeQuery();
						int itj=0;
						int ixs=0;
						int ikh=0;
						int isj=0;
						int iht=0;
						while (rs.next()) {
							itj +=rs.getInt(1);
							if(rs.getString(2).equals("线索")){
								ixs =rs.getInt(1);
							}else if(rs.getString(2).equals("客户")){
								ikh =rs.getInt(1);
							}else if(rs.getString(2).equals("商机")){
								isj =rs.getInt(1);
							}else if(rs.getString(2).equals("合同")){
								iht =rs.getInt(1);
							}
						}
						report = new Report();
						report.setRanking(itj);
						report.setClueNumber(ixs);
						report.setCustomerNumber(ikh);
						report.setBusinessOpportunitiesNumber(isj);
						report.setContractsNumber(iht);
						list.add(report);

				        for (int i11 = 0; i11 < list.size() - 1; i11++) {
				            for (int j = i11 + 1; j < list.size(); j++) {
				                if (list.get(i11).equals(list.get(j))) {
				                    list.remove(j);
				                }
				            }
				        }
					}
				}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("List<Report> getItems(Report report) {" + (t2 - t1) + " ms");
		} catch (SQLException e) {
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		} finally {
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		}
		return list;
	}

	@Override
	public List<Report> getItemslx(Report report) {
		long t1 = System.currentTimeMillis();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Report> list = new ArrayList<Report>();
		String mechanismId=report.getMechanismId();String from=report.getFrom() == null ? "" : report.getFrom();String bmids=report.getDepartmentIds();
		String username=report.getUsername();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);

			String wSQL = "";
			int i=0;
			if(report.getTime()==null){
				
			}else{
				if(report.getTime().equals("全部")){
					
				}else {
					i++;
					if (report.getTime() != null || !report.getTime().equals("")) {
						wSQL += " and time>='" + report.getTime() + "'";
					}
					if (report.getZtime() != null || !report.getTime().equals("")) {
						wSQL += " and time<='" + report.getZtime() + "'";
					}
				}
			}
			if(report.getTime()==null){
				
			}else{
				if(report.getTime().equals("全部")){
					
				}else {
					if (report.getTime() != null || !report.getTime().equals("")) {
						wSQL += " and create_date>='" + report.getTime() + "'";
					}
					if (report.getZtime() != null || !report.getTime().equals("")) {
						wSQL += " and create_date<='" + report.getZtime() + "'";
					}
				}
			}
			if(report.getUserId()==null){
			}else{
					if (!report.getUserId().equals("")) {
						wSQL += " and create_id='" + report.getUserId() + "'";
					}
			}
			if(report.getDepartmentId()==null){
			}else{
				if (!report.getDepartmentId().equals("")) {
					wSQL += " and department_id='" + report.getDepartmentId() + "'";
				}
			}
			String sql = "";
			if(from.equals("全部")) {
				sql = "SELECT count(*),follow_up,create_name from crm_follow_up where 1=1  "+wSQL+"  and mechanism_id like ? GROUP BY follow_up";
				ps = conn.prepareStatement(sql);
				ps.setString(1, mechanismId+"%");
				rs = ps.executeQuery();
				int itj=0;
				int idh=0;
				int iqq=0;
				int iwx=0;
				int ibf=0;
				int iyj=0;
				int idx=0;
				int iqt=0;
				while (rs.next()) {
					itj +=rs.getInt(1);
					if(rs.getString(2).equals("电话")){
						idh =rs.getInt(1);
					}else if(rs.getString(2).equals("QQ")){
						iqq =rs.getInt(1);
					}else if(rs.getString(2).equals("微信")){
						iwx =rs.getInt(1);
					}else if(rs.getString(2).equals("拜访")){
						ibf =rs.getInt(1);
					}else if(rs.getString(2).equals("邮件")){
						iyj =rs.getInt(1);
					}else if(rs.getString(2).equals("短信")){
						idx =rs.getInt(1);
					}else if(rs.getString(2).equals("其他")){
						iqt =rs.getInt(1);
					}
				}
				report = new Report();
				report.setRanking(itj);
				report.setDh(idh);
				report.setQq(iqq);
				report.setWx(iwx);
				report.setBf(ibf);
				report.setYj(iyj);
				report.setDx(idx);
				report.setQt(iqt);
				list.add(report);
			}else {
				if(from.equals("本部门")) {
					String[] xh=bmids.split(",");
				    for(String bmid : xh){
				    	if(!bmid.equals("")){
							sql = "SELECT count(*),follow_up,create_name from crm_follow_up where 1=1  "+wSQL+"  and mechanism_id like ? and department_id like ? GROUP BY follow_up";
							ps = conn.prepareStatement(sql);
							ps.setString(1, mechanismId+"%");
							ps.setString(2, bmid+"%");
							rs = ps.executeQuery();
							int itj=0;
							int idh=0;
							int iqq=0;
							int iwx=0;
							int ibf=0;
							int iyj=0;
							int idx=0;
							int iqt=0;
							while (rs.next()) {
								itj +=rs.getInt(1);
								if(rs.getString(2).equals("电话")){
									idh =rs.getInt(1);
								}else if(rs.getString(2).equals("QQ")){
									iqq =rs.getInt(1);
								}else if(rs.getString(2).equals("微信")){
									iwx =rs.getInt(1);
								}else if(rs.getString(2).equals("拜访")){
									ibf =rs.getInt(1);
								}else if(rs.getString(2).equals("邮件")){
									iyj =rs.getInt(1);
								}else if(rs.getString(2).equals("短信")){
									idx =rs.getInt(1);
								}else if(rs.getString(2).equals("其他")){
									iqt =rs.getInt(1);
								}
							}
							report = new Report();
							report.setRanking(itj);
							report.setDh(idh);
							report.setQq(iqq);
							report.setWx(iwx);
							report.setBf(ibf);
							report.setYj(iyj);
							report.setDx(idx);
							report.setQt(iqt);
							list.add(report);
				    	}
				    }
					sql = "SELECT count(*),follow_up,create_name from crm_follow_up where 1=1  "+wSQL+"  and mechanism_id like ? and create_id = ? GROUP BY follow_up";
					ps = conn.prepareStatement(sql);
					ps.setString(1, mechanismId+"%");
					ps.setString(2, username);
					rs = ps.executeQuery();
					int itj=0;
					int idh=0;
					int iqq=0;
					int iwx=0;
					int ibf=0;
					int iyj=0;
					int idx=0;
					int iqt=0;
					while (rs.next()) {
						itj +=rs.getInt(1);
						if(rs.getString(2).equals("电话")){
							idh =rs.getInt(1);
						}else if(rs.getString(2).equals("QQ")){
							iqq =rs.getInt(1);
						}else if(rs.getString(2).equals("微信")){
							iwx =rs.getInt(1);
						}else if(rs.getString(2).equals("拜访")){
							ibf =rs.getInt(1);
						}else if(rs.getString(2).equals("邮件")){
							iyj =rs.getInt(1);
						}else if(rs.getString(2).equals("短信")){
							idx =rs.getInt(1);
						}else if(rs.getString(2).equals("其他")){
							iqt =rs.getInt(1);
						}
					}
					report = new Report();
					report.setRanking(itj);
					report.setDh(idh);
					report.setQq(iqq);
					report.setWx(iwx);
					report.setBf(ibf);
					report.setYj(iyj);
					report.setDx(idx);
					report.setQt(iqt);
					list.add(report);
			        for (int i11 = 0; i11 < list.size() - 1; i11++) {
			            for (int j = i11 + 1; j < list.size(); j++) {
			                if (list.get(i11).equals(list.get(j))) {
			                    list.remove(j);
			                }
			            }
			        }
				}else if(from.equals("未设置")) {
					sql = "SELECT count(*),follow_up,create_name from crm_follow_up where 1=1  "+wSQL+"  and mechanism_id like ? and create_id = ? GROUP BY follow_up";
					ps = conn.prepareStatement(sql);
					ps.setString(1, mechanismId+"%");
					ps.setString(2, username);
					rs = ps.executeQuery();
					int itj=0;
					int idh=0;
					int iqq=0;
					int iwx=0;
					int ibf=0;
					int iyj=0;
					int idx=0;
					int iqt=0;
					while (rs.next()) {
						itj +=rs.getInt(1);
						if(rs.getString(2).equals("电话")){
							idh =rs.getInt(1);
						}else if(rs.getString(2).equals("QQ")){
							iqq =rs.getInt(1);
						}else if(rs.getString(2).equals("微信")){
							iwx =rs.getInt(1);
						}else if(rs.getString(2).equals("拜访")){
							ibf =rs.getInt(1);
						}else if(rs.getString(2).equals("邮件")){
							iyj =rs.getInt(1);
						}else if(rs.getString(2).equals("短信")){
							idx =rs.getInt(1);
						}else if(rs.getString(2).equals("其他")){
							iqt =rs.getInt(1);
						}
					}
					report = new Report();
					report.setRanking(itj);
					report.setDh(idh);
					report.setQq(iqq);
					report.setWx(iwx);
					report.setBf(ibf);
					report.setYj(iyj);
					report.setDx(idx);
					report.setQt(iqt);
					list.add(report);
				}else {

					String[] xh=bmids.split(",");
				    for(String bmid : xh){
				    	if(!bmid.equals("")){
							sql = "SELECT count(*),follow_up,create_name from crm_follow_up where 1=1  "+wSQL+"  and mechanism_id like ? and department_id =? GROUP BY follow_up";
							ps = conn.prepareStatement(sql);
							ps.setString(1, mechanismId+"%");
							ps.setString(2, bmid);
							rs = ps.executeQuery();
							int itj=0;
							int idh=0;
							int iqq=0;
							int iwx=0;
							int ibf=0;
							int iyj=0;
							int idx=0;
							int iqt=0;
							while (rs.next()) {
								itj +=rs.getInt(1);
								if(rs.getString(2).equals("电话")){
									idh =rs.getInt(1);
								}else if(rs.getString(2).equals("QQ")){
									iqq =rs.getInt(1);
								}else if(rs.getString(2).equals("微信")){
									iwx =rs.getInt(1);
								}else if(rs.getString(2).equals("拜访")){
									ibf =rs.getInt(1);
								}else if(rs.getString(2).equals("邮件")){
									iyj =rs.getInt(1);
								}else if(rs.getString(2).equals("短信")){
									idx =rs.getInt(1);
								}else if(rs.getString(2).equals("其他")){
									iqt =rs.getInt(1);
								}
							}
							report = new Report();
							report.setRanking(itj);
							report.setDh(idh);
							report.setQq(iqq);
							report.setWx(iwx);
							report.setBf(ibf);
							report.setYj(iyj);
							report.setDx(idx);
							report.setQt(iqt);
							list.add(report);
				    	}
				    }
					sql = "SELECT count(*),follow_up,create_name from crm_follow_up where 1=1  "+wSQL+"  and mechanism_id like ? and create_id = ? GROUP BY follow_up";
					ps = conn.prepareStatement(sql);
					ps.setString(1, mechanismId+"%");
					ps.setString(2, username);
					rs = ps.executeQuery();
					int itj=0;
					int idh=0;
					int iqq=0;
					int iwx=0;
					int ibf=0;
					int iyj=0;
					int idx=0;
					int iqt=0;
					while (rs.next()) {
						itj +=rs.getInt(1);
						if(rs.getString(2).equals("电话")){
							idh =rs.getInt(1);
						}else if(rs.getString(2).equals("QQ")){
							iqq =rs.getInt(1);
						}else if(rs.getString(2).equals("微信")){
							iwx =rs.getInt(1);
						}else if(rs.getString(2).equals("拜访")){
							ibf =rs.getInt(1);
						}else if(rs.getString(2).equals("邮件")){
							iyj =rs.getInt(1);
						}else if(rs.getString(2).equals("短信")){
							idx =rs.getInt(1);
						}else if(rs.getString(2).equals("其他")){
							iqt =rs.getInt(1);
						}
					}
					report = new Report();
					report.setRanking(itj);
					report.setDh(idh);
					report.setQq(iqq);
					report.setWx(iwx);
					report.setBf(ibf);
					report.setYj(iyj);
					report.setDx(idx);
					report.setQt(iqt);
					list.add(report);
			        for (int i11 = 0; i11 < list.size() - 1; i11++) {
			            for (int j = i11 + 1; j < list.size(); j++) {
			                if (list.get(i11).equals(list.get(j))) {
			                    list.remove(j);
			                }
			            }
			        }
				}
					
			}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("List<Report> getItemslx(Report report)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		} finally {
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		}
		return list;
	}
	
	
	
	

	
	
}
