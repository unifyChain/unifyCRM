package dafengqi.yunxiang.dao.hibernate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import dafengqi.yunxiang.dao.FollowupDao;
import dafengqi.yunxiang.model.FollowUp;
import dafengqi.yunxiang.util.ResourceManager;

@Repository("followupDao")
public class FollowupDaoHibernate extends GenericDaoHibernate<FollowUp, Long> implements FollowupDao {
	protected java.sql.Connection userConn;
	final boolean isConnSupplied = (userConn != null);
	public FollowupDaoHibernate() {
		super(FollowUp.class);
	}
	

	@Override
	public List<FollowUp> getItems(FollowUp followup) {
		long t1 = System.currentTimeMillis();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<FollowUp> list = new ArrayList<FollowUp>();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);

			String zsql = "";
			if (followup.getFroms()!=null) {
				if (followup.getFroms().length!=0) {

					zsql += " and (";
					for(int i=0;i<followup.getFroms().length;i++) {
						zsql += "  ly='" + followup.getFroms()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length()-2);
					zsql += ")";
				}
			}
			if (followup.getFollowUps()!=null) {
				if (followup.getFollowUps().length!=0) {

					zsql += " and (";
					for(int i=0;i<followup.getFollowUps().length;i++) {
						zsql += "  follow_up='" + followup.getFollowUps()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length()-2);
					zsql += ")";
				}
			}
			if (followup.getDepartmentIds()!=null) {
				if (!followup.getDepartmentIds().equals("")) {
						zsql += "and department_id='" + followup.getDepartmentIds()+"'";
				}
			}
			if(followup.getTime()==null) {
				if(followup.getTimeDateRange()!=null) {
					int i=0;
					for(Date t:followup.getTimeDateRange()){
						if(i==0) {
							zsql += " and time>='" + df.format(t) + "'";
							i++;
						}else {
							zsql += " and time<='" + df.format(t) + "'";
						}
					}
				}
			}else {
				if (!followup.getTime().equals("")) {
					if(followup.getTime().equals("今日")) {
						zsql += " and time>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayStartTime().getTime()) + "'";
						zsql += " and time<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayEndTime().getTime()) + "'";
					}
					if(followup.getTime().equals("本周")) {
						zsql += " and time>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentWeekDayStartTime().getTime()) + "'";
						zsql += " and time<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentWeekDayEndTime().getTime()) + "'";
					}
					if(followup.getTime().equals("本月")) {
						zsql += " and time>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthStartTime().getTime()) + "'";
						zsql += " and time<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthEndTime().getTime()) + "'";
					}
					if(followup.getTime().equals("本季")) {
						zsql += " and time>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentQuarterStartTime().getTime()) + "'";
						zsql += " and time<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentQuarterEndTime().getTime()) + "'";
					}
					if(followup.getTime().equals("本年")) {
						zsql += " and time>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearStartTime().getTime()) + "'";
						zsql += " and time<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearEndTime().getTime()) + "'";
					}
				}else {
					if(followup.getTimeDateRange()!=null) {
						int i=0;
						for(Date t:followup.getTimeDateRange()){
							if(i==0) {
								zsql += " and time>='" + df.format(t) + "'";
								i++;
							}else {
								zsql += " and time<='" + df.format(t) + "'";
							}
						}
					}
				}
			}
			if(followup.getNextFollowTime()==null) {
				if(followup.getNextFollowTimeDateRange()!=null) {
					int i=0;
					for(Date t:followup.getNextFollowTimeDateRange()){
						if(i==0) {
							zsql += " and create_date>='" + df.format(t) + "'";
							i++;
						}else {
							zsql += " and create_date<='" + df.format(t) + "'";
						}
					}
				}
			}else {
				if (!followup.getNextFollowTime().equals("")) {
					if(followup.getNextFollowTime().equals("今日")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayStartTime().getTime()) + "'";
						zsql += " and create_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayEndTime().getTime()) + "'";
					}
					if(followup.getNextFollowTime().equals("本周")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentWeekDayStartTime().getTime()) + "'";
						zsql += " and create_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentWeekDayEndTime().getTime()) + "'";
					}
					if(followup.getNextFollowTime().equals("本月")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthStartTime().getTime()) + "'";
						zsql += " and create_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthEndTime().getTime()) + "'";
					}
					if(followup.getNextFollowTime().equals("本季")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentQuarterStartTime().getTime()) + "'";
						zsql += " and create_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentQuarterEndTime().getTime()) + "'";
					}
					if(followup.getNextFollowTime().equals("本年")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearStartTime().getTime()) + "'";
						zsql += " and create_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearEndTime().getTime()) + "'";
					}
				}else {
					if(followup.getNextFollowTimeDateRange()!=null) {
						int i=0;
						for(Date t:followup.getNextFollowTimeDateRange()){
							if(i==0) {
								zsql += " and create_date>='" + df.format(t) + "'";
								i++;
							}else {
								zsql += " and create_date<='" + df.format(t) + "'";
							}
						}
					}
				}
			}

			String sql = "";

			sql = "SELECT id,follow_up,time,content,follow_id,follow_mc,follow_status,contacts_id,contacts_mc,next_follow_time,create_id,create_name,create_date,update_id,update_name,update_date,mechanism_id,froms FROM  crm_follow_up where 1=1  "+zsql+"  and mechanism_id like'" + followup.getMechanismId() + "%'  ";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				followup = new FollowUp();
				followup.setId(rs.getString(1));
				followup.setFollowUp(rs.getString(2));
				followup.setTime(rs.getString(3));
				followup.setContent(rs.getString(4));
				followup.setFollowId(rs.getString(5));
				followup.setFollowName(rs.getString(6));
				followup.setFollowStatus(rs.getString(7));
				followup.setContactsId(rs.getString(8));
				followup.setContactsName(rs.getString(9));
				followup.setNextFollowTime(rs.getString(10));
				followup.setCreateId(rs.getString(11));
				followup.setCreateName(rs.getString(12));
				followup.setCreateDate(rs.getString(13));
				followup.setUpdateId(rs.getString(14));
				followup.setUpdateName(rs.getString(15));
				followup.setUpdateDate(rs.getString(16));
				followup.setMechanismId(rs.getString(17));
				followup.setFrom(rs.getString(18));
				list.add(followup);
			}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("List<Followup> getItems(Followup followup)" + (t2 - t1) + " ms");
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