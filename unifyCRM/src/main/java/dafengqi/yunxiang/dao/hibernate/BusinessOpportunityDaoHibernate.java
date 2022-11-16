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

import dafengqi.yunxiang.dao.BusinessOpportunityDao;
import dafengqi.yunxiang.model.BusinessOpportunity;
import dafengqi.yunxiang.model.Contract;
import dafengqi.yunxiang.model.Cost;
import dafengqi.yunxiang.util.ResourceManager;

@Repository("businessOpportunityDao")
public class BusinessOpportunityDaoHibernate extends GenericDaoHibernate<BusinessOpportunity, Long>
		implements BusinessOpportunityDao {
	protected java.sql.Connection userConn;
	final boolean isConnSupplied = (userConn != null);

	public BusinessOpportunityDaoHibernate() {
		super(BusinessOpportunity.class);
	}

	@Override
	public List<BusinessOpportunity> getItems(BusinessOpportunity businessOpportunity) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<BusinessOpportunity> list = new ArrayList<BusinessOpportunity>();
		String username = businessOpportunity.getCreateName();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String sql = "";
			String zsql = "";
			if (businessOpportunity.getExpectedSigningDates() == null) {
				if (businessOpportunity.getExpectedSigningDateRange() != null) {
					int i = 0;
					for (Date t : businessOpportunity.getExpectedSigningDateRange()) {
						if (i == 0) {
							zsql += " and expected_signing_date>='" + df.format(t) + "'";
							i++;
						} else {
							zsql += " and expected_signing_date<='" + df.format(t) + "'";
						}
					}
				}
			} else {
				if (!businessOpportunity.getExpectedSigningDates().equals("")) {
					if (businessOpportunity.getExpectedSigningDates().equals("今日")) {
						zsql += " and expected_signing_date>='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayStartTime().getTime())
								+ "'";
						zsql += " and expected_signing_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayEndTime().getTime())
								+ "'";
					}
					if (businessOpportunity.getExpectedSigningDates().equals("本周")) {
						zsql += " and expected_signing_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayStartTime().getTime()) + "'";
						zsql += " and expected_signing_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayEndTime().getTime()) + "'";
					}
					if (businessOpportunity.getExpectedSigningDates().equals("本月")) {
						zsql += " and expected_signing_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentMonthStartTime().getTime()) + "'";
						zsql += " and expected_signing_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthEndTime().getTime())
								+ "'";
					}
					if (businessOpportunity.getExpectedSigningDates().equals("本季")) {
						zsql += " and expected_signing_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterStartTime().getTime()) + "'";
						zsql += " and expected_signing_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterEndTime().getTime()) + "'";
					}
					if (businessOpportunity.getExpectedSigningDates().equals("本年")) {
						zsql += " and expected_signing_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentYearStartTime().getTime()) + "'";
						zsql += " and expected_signing_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearEndTime().getTime())
								+ "'";
					}
				} else {
					if (businessOpportunity.getExpectedSigningDateRange() != null) {
						int i = 0;
						for (Date t : businessOpportunity.getExpectedSigningDateRange()) {
							if (i == 0) {
								zsql += " and expected_signing_date>='" + df.format(t) + "'";
								i++;
							} else {
								zsql += " and expected_signing_date<='" + df.format(t) + "'";
							}
						}
					}
				}
			}

			if (businessOpportunity.getCreateDate() == null) {
				if (businessOpportunity.getCreateDateRange() != null) {
					int i = 0;
					for (Date t : businessOpportunity.getCreateDateRange()) {
						if (i == 0) {
							zsql += " and create_date>='" + df.format(t) + "'";
							i++;
						} else {
							zsql += " and create_date<='" + df.format(t) + "'";
						}
					}
				}
			} else {
				if (!businessOpportunity.getCreateDate().equals("")) {
					if (businessOpportunity.getCreateDate().equals("今日")) {
						zsql += " and create_date>='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayStartTime().getTime())
								+ "'";
						zsql += " and create_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayEndTime().getTime())
								+ "'";
					}
					if (businessOpportunity.getCreateDate().equals("本周")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayStartTime().getTime()) + "'";
						zsql += " and create_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayEndTime().getTime()) + "'";
					}
					if (businessOpportunity.getCreateDate().equals("本月")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentMonthStartTime().getTime()) + "'";
						zsql += " and create_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthEndTime().getTime())
								+ "'";
					}
					if (businessOpportunity.getCreateDate().equals("本季")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterStartTime().getTime()) + "'";
						zsql += " and create_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterEndTime().getTime()) + "'";
					}
					if (businessOpportunity.getCreateDate().equals("本年")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentYearStartTime().getTime()) + "'";
						zsql += " and create_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearEndTime().getTime())
								+ "'";
					}
				} else {
					if (businessOpportunity.getCreateDateRange() != null) {
						int i = 0;
						for (Date t : businessOpportunity.getCreateDateRange()) {
							if (i == 0) {
								zsql += " and create_date>='" + df.format(t) + "'";
								i++;
							} else {
								zsql += " and create_date<='" + df.format(t) + "'";
							}
						}
					}
				}
			}
			if (businessOpportunity.getSalesStages() != null) {
				if (businessOpportunity.getSalesStages().length != 0) {

					zsql += " and (";
					for (int i = 0; i < businessOpportunity.getSalesStages().length; i++) {
						zsql += "  sales_stage='" + businessOpportunity.getSalesStages()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (businessOpportunity.getEstimatedAmount() != null) {
				if (!businessOpportunity.getEstimatedAmount().equals("")) {
					zsql += " and estimated_amount>=" + businessOpportunity.getEstimatedAmount() + "";
				}
			}
			if (businessOpportunity.getEstimatedAmounts() != null) {
				if (!businessOpportunity.getEstimatedAmounts().equals("")) {
					zsql += " and estimated_amount<=" + businessOpportunity.getEstimatedAmounts() + "";
				}
			}
			if (businessOpportunity.getPersonIds() != null) {
				if (businessOpportunity.getPersonIds().length != 0) {

					zsql += " and (";
					for (int i = 0; i < businessOpportunity.getPersonIds().length; i++) {
						zsql += "  person_id='" + businessOpportunity.getPersonIds()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (businessOpportunity.getDepartmentIds() != null) {
				if (!businessOpportunity.getDepartmentIds().equals("")) {
					zsql += "and department_id='" + businessOpportunity.getDepartmentIds() + "'";
				}
			}
			if (businessOpportunity.getCustomerIds() != null) {
				if (businessOpportunity.getCustomerIds().length != 0) {

					zsql += " and (";
					for (int i = 0; i < businessOpportunity.getCustomerIds().length; i++) {
						zsql += "  customer_id='" + businessOpportunity.getCustomerIds()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (businessOpportunity.getBusinessOpportunityTypes() != null) {
				if (businessOpportunity.getBusinessOpportunityTypes().length != 0) {

					zsql += " and (";
					for (int i = 0; i < businessOpportunity.getBusinessOpportunityTypes().length; i++) {
						zsql += "  business_opportunity_type='" + businessOpportunity.getBusinessOpportunityTypes()[i]
								+ "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}

			if (businessOpportunity.getBusinessOpportunityTimes() == null) {
				if (businessOpportunity.getBusinessOpportunityTimeDateRange() != null) {
					int i = 0;
					for (Date t : businessOpportunity.getBusinessOpportunityTimeDateRange()) {
						if (i == 0) {
							zsql += " and business_opportunity_time>='" + df.format(t) + "'";
							i++;
						} else {
							zsql += " and business_opportunity_time<='" + df.format(t) + "'";
						}
					}
				}
			} else {
				if (!businessOpportunity.getBusinessOpportunityTimes().equals("")) {
					if (businessOpportunity.getBusinessOpportunityTimes().equals("今日")) {
						zsql += " and business_opportunity_time>='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayStartTime().getTime())
								+ "'";
						zsql += " and business_opportunity_time<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayEndTime().getTime())
								+ "'";
					}
					if (businessOpportunity.getBusinessOpportunityTimes().equals("本周")) {
						zsql += " and business_opportunity_time>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayStartTime().getTime()) + "'";
						zsql += " and business_opportunity_time<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayEndTime().getTime()) + "'";
					}
					if (businessOpportunity.getBusinessOpportunityTimes().equals("本月")) {
						zsql += " and business_opportunity_time>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentMonthStartTime().getTime()) + "'";
						zsql += " and business_opportunity_time<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthEndTime().getTime())
								+ "'";
					}
					if (businessOpportunity.getBusinessOpportunityTimes().equals("本季")) {
						zsql += " and business_opportunity_time>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterStartTime().getTime()) + "'";
						zsql += " and business_opportunity_time<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterEndTime().getTime()) + "'";
					}
					if (businessOpportunity.getBusinessOpportunityTimes().equals("本年")) {
						zsql += " and business_opportunity_time>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentYearStartTime().getTime()) + "'";
						zsql += " and business_opportunity_time<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearEndTime().getTime())
								+ "'";
					}
				} else {
					if (businessOpportunity.getBusinessOpportunityTimeDateRange() != null) {
						int i = 0;
						for (Date t : businessOpportunity.getBusinessOpportunityTimeDateRange()) {
							if (i == 0) {
								zsql += " and business_opportunity_time>='" + df.format(t) + "'";
								i++;
							} else {
								zsql += " and business_opportunity_time<='" + df.format(t) + "'";
							}
						}
					}
				}
			}
			if (businessOpportunity.getBusinessOpportunitySources() != null) {
				if (businessOpportunity.getBusinessOpportunitySources().length != 0) {

					zsql += " and (";
					for (int i = 0; i < businessOpportunity.getBusinessOpportunitySources().length; i++) {
						zsql += "  business_opportunity_source='"
								+ businessOpportunity.getBusinessOpportunitySources()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (businessOpportunity.getCreateIds() != null) {
				if (businessOpportunity.getCreateIds().length != 0) {

					zsql += " and (";
					for (int i = 0; i < businessOpportunity.getCreateIds().length; i++) {
						zsql += "  create_id='" + businessOpportunity.getCreateIds()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}

			if (businessOpportunity.getNextFollowTimes() == null) {
				if (businessOpportunity.getNextFollowTimeDateRange() != null) {
					int i = 0;
					for (Date t : businessOpportunity.getNextFollowTimeDateRange()) {
						if (i == 0) {
							zsql += " and next_follow_time>='" + df.format(t) + "'";
							i++;
						} else {
							zsql += " and next_follow_time<='" + df.format(t) + "'";
						}
					}
				}
			} else {
				if (!businessOpportunity.getNextFollowTimes().equals("")) {
					if (businessOpportunity.getNextFollowTimes().equals("今日")) {
						zsql += " and next_follow_time>='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayStartTime().getTime())
								+ "'";
						zsql += " and next_follow_time<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayEndTime().getTime())
								+ "'";
					}
					if (businessOpportunity.getNextFollowTimes().equals("本周")) {
						zsql += " and next_follow_time>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayStartTime().getTime()) + "'";
						zsql += " and next_follow_time<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayEndTime().getTime()) + "'";
					}
					if (businessOpportunity.getNextFollowTimes().equals("本月")) {
						zsql += " and next_follow_time>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentMonthStartTime().getTime()) + "'";
						zsql += " and next_follow_time<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthEndTime().getTime())
								+ "'";
					}
					if (businessOpportunity.getNextFollowTimes().equals("本季")) {
						zsql += " and next_follow_time>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterStartTime().getTime()) + "'";
						zsql += " and next_follow_time<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterEndTime().getTime()) + "'";
					}
					if (businessOpportunity.getNextFollowTimes().equals("本年")) {
						zsql += " and next_follow_time>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentYearStartTime().getTime()) + "'";
						zsql += " and next_follow_time<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearEndTime().getTime())
								+ "'";
					}
				} else {
					if (businessOpportunity.getNextFollowTimeDateRange() != null) {
						int i = 0;
						for (Date t : businessOpportunity.getNextFollowTimeDateRange()) {
							if (i == 0) {
								zsql += " and next_follow_time>='" + df.format(t) + "'";
								i++;
							} else {
								zsql += " and next_follow_time<='" + df.format(t) + "'";
							}
						}
					}
				}
			}
			if (businessOpportunity.getUpdateDate() == null) {
				if (businessOpportunity.getUpdateDateRange() != null) {
					int i = 0;
					for (Date t : businessOpportunity.getUpdateDateRange()) {
						if (i == 0) {
							zsql += " and update_date>='" + df.format(t) + "'";
							i++;
						} else {
							zsql += " and update_date<='" + df.format(t) + "'";
						}
					}
				}
			} else {
				if (!businessOpportunity.getUpdateDate().equals("")) {
					if (businessOpportunity.getUpdateDate().equals("今日")) {
						zsql += " and update_date>='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayStartTime().getTime())
								+ "'";
						zsql += " and update_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayEndTime().getTime())
								+ "'";
					}
					if (businessOpportunity.getUpdateDate().equals("本周")) {
						zsql += " and update_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayStartTime().getTime()) + "'";
						zsql += " and update_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayEndTime().getTime()) + "'";
					}
					if (businessOpportunity.getUpdateDate().equals("本月")) {
						zsql += " and update_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentMonthStartTime().getTime()) + "'";
						zsql += " and update_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthEndTime().getTime())
								+ "'";
					}
					if (businessOpportunity.getUpdateDate().equals("本季")) {
						zsql += " and update_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterStartTime().getTime()) + "'";
						zsql += " and update_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterEndTime().getTime()) + "'";
					}
					if (businessOpportunity.getUpdateDate().equals("本年")) {
						zsql += " and update_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentYearStartTime().getTime()) + "'";
						zsql += " and update_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearEndTime().getTime())
								+ "'";
					}
				} else {
					if (businessOpportunity.getUpdateDateRange() != null) {
						int i = 0;
						for (Date t : businessOpportunity.getUpdateDateRange()) {
							if (i == 0) {
								zsql += " and update_date>='" + df.format(t) + "'";
								i++;
							} else {
								zsql += " and update_date<='" + df.format(t) + "'";
							}
						}
					}
				}
			}
			if (businessOpportunity.getFrom().equals("全部")) {
				sql = "SELECT id,business_opportunity_title,customer_name,estimated_amount,expected_signing_date,sales_stage,sales_stage_name,next_follow_time,business_opportunity_type,business_opportunity_type_name,business_opportunity_time,business_opportunity_source,person_id,person_name,department_id,department_name,audit,remarks,create_id,create_name,create_date,update_id,update_name,update_date,customer_id,crm_responsible_id,project_debriefing,degree_of_importance,crm_business_opportunitycol_classification,reminder_frequency,sys_project_debriefing_id,sys_reminder_settings_id,crm_product_id,crm_contacts_id,crm_payment_collection_plan_id,mechanism_id,state FROM  crm_business_opportunity    where 1=1  and mechanism_id like ? "
						+ zsql + " order by create_date desc ";
				ps = conn.prepareStatement(sql);
				ps.setString(1, businessOpportunity.getMechanismId() + "%");
				rs = ps.executeQuery();
				while (rs.next()) {
					businessOpportunity = new BusinessOpportunity();
					businessOpportunity.setId(rs.getString(1));
					businessOpportunity.setBusinessOpportunityTitle(rs.getString(2));
					businessOpportunity.setCustomerName(rs.getString(3));
					businessOpportunity.setEstimatedAmount(rs.getString(4));
					businessOpportunity.setExpectedSigningDates(rs.getString(5));
					businessOpportunity.setSalesStage(rs.getString(6));
					businessOpportunity.setSalesStageName(rs.getString(7));
					businessOpportunity.setNextFollowTimes(rs.getString(8));
					businessOpportunity.setBusinessOpportunityType(rs.getString(9));
					businessOpportunity.setBusinessOpportunityTypeName(rs.getString(10));
					businessOpportunity.setBusinessOpportunityTimes(rs.getString(11));
					businessOpportunity.setBusinessOpportunitySource(rs.getString(12));
					businessOpportunity.setPersonId(rs.getString(13));
					businessOpportunity.setPersonName(rs.getString(14));
					businessOpportunity.setDepartmentId(rs.getString(15));
					businessOpportunity.setDepartmentName(rs.getString(16));
					businessOpportunity.setAudit(rs.getString(17));
					businessOpportunity.setRemarks(rs.getString(18));
					businessOpportunity.setCreateId(rs.getString(19));
					businessOpportunity.setCreateName(rs.getString(20));
					businessOpportunity.setCreateDate(rs.getString(21));
					businessOpportunity.setUpdateId(rs.getString(22));
					businessOpportunity.setUpdateName(rs.getString(23));
					businessOpportunity.setUpdateDate(rs.getString(24));
					businessOpportunity.setCustomerId(rs.getString(25));
					businessOpportunity.setCrmResponsibleId(rs.getString(26));
					businessOpportunity.setProjectDebriefing(rs.getString(27));
					businessOpportunity.setDegreeOfImportance(rs.getString(28));
					businessOpportunity.setCrmBusinessOpportunitycolClassification(rs.getString(29));
					businessOpportunity.setReminderFrequency(rs.getString(30));
					businessOpportunity.setSysProjectDebriefingId(rs.getString(31));
					businessOpportunity.setSysReminderSettingsId(rs.getString(32));
					businessOpportunity.setCrmProductId(rs.getString(33));
					businessOpportunity.setCrmContactsId(rs.getString(34));
					businessOpportunity.setCrmPaymentCollectionPlanId(rs.getString(35));
					businessOpportunity.setMechanismId(rs.getString(36));
					businessOpportunity.setState(rs.getString(37));
					list.add(businessOpportunity);
				}
			} else {

				if (businessOpportunity.getFrom().equals("本部")) {

					String[] xh = businessOpportunity.getDepartmentId().split(",");
					for (String bmid : xh) {
						if (!bmid.equals("")) {
							sql = "SELECT id,business_opportunity_title,customer_name,estimated_amount,expected_signing_date,sales_stage,sales_stage_name,next_follow_time,business_opportunity_type,business_opportunity_type_name,business_opportunity_time,business_opportunity_source,person_id,person_name,department_id,department_name,audit,remarks,create_id,create_name,create_date,update_id,update_name,update_date,customer_id,crm_responsible_id,project_debriefing,degree_of_importance,crm_business_opportunitycol_classification,reminder_frequency,sys_project_debriefing_id,sys_reminder_settings_id,crm_product_id,crm_contacts_id,crm_payment_collection_plan_id,mechanism_id,state FROM  crm_business_opportunity    where 1=1  and mechanism_id like ? and department_id like ? "
									+ zsql + " order by create_date desc ";

							ps = conn.prepareStatement(sql);
							ps.setString(1, businessOpportunity.getMechanismId() + "%");
							ps.setString(2, bmid + "%");
							rs = ps.executeQuery();
							while (rs.next()) {
								businessOpportunity = new BusinessOpportunity();
								businessOpportunity.setId(rs.getString(1));
								businessOpportunity.setBusinessOpportunityTitle(rs.getString(2));
								businessOpportunity.setCustomerName(rs.getString(3));
								businessOpportunity.setEstimatedAmount(rs.getString(4));
								businessOpportunity.setExpectedSigningDates(rs.getString(5));
								businessOpportunity.setSalesStage(rs.getString(6));
								businessOpportunity.setSalesStageName(rs.getString(7));
								businessOpportunity.setNextFollowTimes(rs.getString(8));
								businessOpportunity.setBusinessOpportunityType(rs.getString(9));
								businessOpportunity.setBusinessOpportunityTypeName(rs.getString(10));
								businessOpportunity.setBusinessOpportunityTimes(rs.getString(11));
								businessOpportunity.setBusinessOpportunitySource(rs.getString(12));
								businessOpportunity.setPersonId(rs.getString(13));
								businessOpportunity.setPersonName(rs.getString(14));
								businessOpportunity.setDepartmentId(rs.getString(15));
								businessOpportunity.setDepartmentName(rs.getString(16));
								businessOpportunity.setAudit(rs.getString(17));
								businessOpportunity.setRemarks(rs.getString(18));
								businessOpportunity.setCreateId(rs.getString(19));
								businessOpportunity.setCreateName(rs.getString(20));
								businessOpportunity.setCreateDate(rs.getString(21));
								businessOpportunity.setUpdateId(rs.getString(22));
								businessOpportunity.setUpdateName(rs.getString(23));
								businessOpportunity.setUpdateDate(rs.getString(24));
								businessOpportunity.setCustomerId(rs.getString(25));
								businessOpportunity.setCrmResponsibleId(rs.getString(26));
								businessOpportunity.setProjectDebriefing(rs.getString(27));
								businessOpportunity.setDegreeOfImportance(rs.getString(28));
								businessOpportunity.setCrmBusinessOpportunitycolClassification(rs.getString(29));
								businessOpportunity.setReminderFrequency(rs.getString(30));
								businessOpportunity.setSysProjectDebriefingId(rs.getString(31));
								businessOpportunity.setSysReminderSettingsId(rs.getString(32));
								businessOpportunity.setCrmProductId(rs.getString(33));
								businessOpportunity.setCrmContactsId(rs.getString(34));
								businessOpportunity.setCrmPaymentCollectionPlanId(rs.getString(35));
								businessOpportunity.setMechanismId(rs.getString(36));
								businessOpportunity.setState(rs.getString(37));
								list.add(businessOpportunity);
							}
						}
					}
					sql = "SELECT id,business_opportunity_title,customer_name,estimated_amount,expected_signing_date,sales_stage,sales_stage_name,next_follow_time,business_opportunity_type,business_opportunity_type_name,business_opportunity_time,business_opportunity_source,person_id,person_name,department_id,department_name,audit,remarks,create_id,create_name,create_date,update_id,update_name,update_date,customer_id,crm_responsible_id,project_debriefing,degree_of_importance,crm_business_opportunitycol_classification,reminder_frequency,sys_project_debriefing_id,sys_reminder_settings_id,crm_product_id,crm_contacts_id,crm_payment_collection_plan_id,mechanism_id,state FROM  crm_business_opportunity    where 1=1  and mechanism_id like ? and create_id=? "
							+ zsql + " order by create_date desc ";
					ps = conn.prepareStatement(sql);
					ps.setString(1, businessOpportunity.getMechanismId() + "%");
					ps.setString(2, username);
					rs = ps.executeQuery();
					while (rs.next()) {
						businessOpportunity = new BusinessOpportunity();
						businessOpportunity.setId(rs.getString(1));
						businessOpportunity.setBusinessOpportunityTitle(rs.getString(2));
						businessOpportunity.setCustomerName(rs.getString(3));
						businessOpportunity.setEstimatedAmount(rs.getString(4));
						businessOpportunity.setExpectedSigningDates(rs.getString(5));
						businessOpportunity.setSalesStage(rs.getString(6));
						businessOpportunity.setSalesStageName(rs.getString(7));
						businessOpportunity.setNextFollowTimes(rs.getString(8));
						businessOpportunity.setBusinessOpportunityType(rs.getString(9));
						businessOpportunity.setBusinessOpportunityTypeName(rs.getString(10));
						businessOpportunity.setBusinessOpportunityTimes(rs.getString(11));
						businessOpportunity.setBusinessOpportunitySource(rs.getString(12));
						businessOpportunity.setPersonId(rs.getString(13));
						businessOpportunity.setPersonName(rs.getString(14));
						businessOpportunity.setDepartmentId(rs.getString(15));
						businessOpportunity.setDepartmentName(rs.getString(16));
						businessOpportunity.setAudit(rs.getString(17));
						businessOpportunity.setRemarks(rs.getString(18));
						businessOpportunity.setCreateId(rs.getString(19));
						businessOpportunity.setCreateName(rs.getString(20));
						businessOpportunity.setCreateDate(rs.getString(21));
						businessOpportunity.setUpdateId(rs.getString(22));
						businessOpportunity.setUpdateName(rs.getString(23));
						businessOpportunity.setUpdateDate(rs.getString(24));
						businessOpportunity.setCustomerId(rs.getString(25));
						businessOpportunity.setCrmResponsibleId(rs.getString(26));
						businessOpportunity.setProjectDebriefing(rs.getString(27));
						businessOpportunity.setDegreeOfImportance(rs.getString(28));
						businessOpportunity.setCrmBusinessOpportunitycolClassification(rs.getString(29));
						businessOpportunity.setReminderFrequency(rs.getString(30));
						businessOpportunity.setSysProjectDebriefingId(rs.getString(31));
						businessOpportunity.setSysReminderSettingsId(rs.getString(32));
						businessOpportunity.setCrmProductId(rs.getString(33));
						businessOpportunity.setCrmContactsId(rs.getString(34));
						businessOpportunity.setCrmPaymentCollectionPlanId(rs.getString(35));
						businessOpportunity.setMechanismId(rs.getString(36));
						businessOpportunity.setState(rs.getString(37));
						list.add(businessOpportunity);
					}

					for (int i = 0; i < list.size() - 1; i++) {
						for (int j = i + 1; j < list.size(); j++) {
							if (list.get(i).equals(list.get(j))) {
								list.remove(j);
							}
						}
					}
				} else if (businessOpportunity.getFrom().equals("未设")) {
					sql = "SELECT id,business_opportunity_title,customer_name,estimated_amount,expected_signing_date,sales_stage,sales_stage_name,next_follow_time,business_opportunity_type,business_opportunity_type_name,business_opportunity_time,business_opportunity_source,person_id,person_name,department_id,department_name,audit,remarks,create_id,create_name,create_date,update_id,update_name,update_date,customer_id,crm_responsible_id,project_debriefing,degree_of_importance,crm_business_opportunitycol_classification,reminder_frequency,sys_project_debriefing_id,sys_reminder_settings_id,crm_product_id,crm_contacts_id,crm_payment_collection_plan_id,mechanism_id,state FROM  crm_business_opportunity    where 1=1  and mechanism_id like ? and create_id=? "
							+ zsql + " order by create_date desc ";
					ps = conn.prepareStatement(sql);
					ps.setString(1, businessOpportunity.getMechanismId() + "%");
					ps.setString(2, username);
					rs = ps.executeQuery();
					while (rs.next()) {
						businessOpportunity = new BusinessOpportunity();
						businessOpportunity.setId(rs.getString(1));
						businessOpportunity.setBusinessOpportunityTitle(rs.getString(2));
						businessOpportunity.setCustomerName(rs.getString(3));
						businessOpportunity.setEstimatedAmount(rs.getString(4));
						businessOpportunity.setExpectedSigningDates(rs.getString(5));
						businessOpportunity.setSalesStage(rs.getString(6));
						businessOpportunity.setSalesStageName(rs.getString(7));
						businessOpportunity.setNextFollowTimes(rs.getString(8));
						businessOpportunity.setBusinessOpportunityType(rs.getString(9));
						businessOpportunity.setBusinessOpportunityTypeName(rs.getString(10));
						businessOpportunity.setBusinessOpportunityTimes(rs.getString(11));
						businessOpportunity.setBusinessOpportunitySource(rs.getString(12));
						businessOpportunity.setPersonId(rs.getString(13));
						businessOpportunity.setPersonName(rs.getString(14));
						businessOpportunity.setDepartmentId(rs.getString(15));
						businessOpportunity.setDepartmentName(rs.getString(16));
						businessOpportunity.setAudit(rs.getString(17));
						businessOpportunity.setRemarks(rs.getString(18));
						businessOpportunity.setCreateId(rs.getString(19));
						businessOpportunity.setCreateName(rs.getString(20));
						businessOpportunity.setCreateDate(rs.getString(21));
						businessOpportunity.setUpdateId(rs.getString(22));
						businessOpportunity.setUpdateName(rs.getString(23));
						businessOpportunity.setUpdateDate(rs.getString(24));
						businessOpportunity.setCustomerId(rs.getString(25));
						businessOpportunity.setCrmResponsibleId(rs.getString(26));
						businessOpportunity.setProjectDebriefing(rs.getString(27));
						businessOpportunity.setDegreeOfImportance(rs.getString(28));
						businessOpportunity.setCrmBusinessOpportunitycolClassification(rs.getString(29));
						businessOpportunity.setReminderFrequency(rs.getString(30));
						businessOpportunity.setSysProjectDebriefingId(rs.getString(31));
						businessOpportunity.setSysReminderSettingsId(rs.getString(32));
						businessOpportunity.setCrmProductId(rs.getString(33));
						businessOpportunity.setCrmContactsId(rs.getString(34));
						businessOpportunity.setCrmPaymentCollectionPlanId(rs.getString(35));
						businessOpportunity.setMechanismId(rs.getString(36));
						businessOpportunity.setState(rs.getString(37));
						list.add(businessOpportunity);
					}

				} else {

					String[] xh = businessOpportunity.getDepartmentId().split(",");
					for (String bmid : xh) {
						if (!bmid.equals("")) {
							sql = "SELECT id,business_opportunity_title,customer_name,estimated_amount,expected_signing_date,sales_stage,sales_stage_name,next_follow_time,business_opportunity_type,business_opportunity_type_name,business_opportunity_time,business_opportunity_source,person_id,person_name,department_id,department_name,audit,remarks,create_id,create_name,create_date,update_id,update_name,update_date,customer_id,crm_responsible_id,project_debriefing,degree_of_importance,crm_business_opportunitycol_classification,reminder_frequency,sys_project_debriefing_id,sys_reminder_settings_id,crm_product_id,crm_contacts_id,crm_payment_collection_plan_id,mechanism_id,state FROM  crm_business_opportunity    where 1=1  and mechanism_id like ? and department_id like ? "
									+ zsql + " order by create_date desc ";
							ps = conn.prepareStatement(sql);
							ps.setString(1, businessOpportunity.getMechanismId() + "%");
							ps.setString(2, bmid);
							rs = ps.executeQuery();
							while (rs.next()) {
								businessOpportunity = new BusinessOpportunity();
								businessOpportunity.setId(rs.getString(1));
								businessOpportunity.setBusinessOpportunityTitle(rs.getString(2));
								businessOpportunity.setCustomerName(rs.getString(3));
								businessOpportunity.setEstimatedAmount(rs.getString(4));
								businessOpportunity.setExpectedSigningDates(rs.getString(5));
								businessOpportunity.setSalesStage(rs.getString(6));
								businessOpportunity.setSalesStageName(rs.getString(7));
								businessOpportunity.setNextFollowTimes(rs.getString(8));
								businessOpportunity.setBusinessOpportunityType(rs.getString(9));
								businessOpportunity.setBusinessOpportunityTypeName(rs.getString(10));
								businessOpportunity.setBusinessOpportunityTimes(rs.getString(11));
								businessOpportunity.setBusinessOpportunitySource(rs.getString(12));
								businessOpportunity.setPersonId(rs.getString(13));
								businessOpportunity.setPersonName(rs.getString(14));
								businessOpportunity.setDepartmentId(rs.getString(15));
								businessOpportunity.setDepartmentName(rs.getString(16));
								businessOpportunity.setAudit(rs.getString(17));
								businessOpportunity.setRemarks(rs.getString(18));
								businessOpportunity.setCreateId(rs.getString(19));
								businessOpportunity.setCreateName(rs.getString(20));
								businessOpportunity.setCreateDate(rs.getString(21));
								businessOpportunity.setUpdateId(rs.getString(22));
								businessOpportunity.setUpdateName(rs.getString(23));
								businessOpportunity.setUpdateDate(rs.getString(24));
								businessOpportunity.setCustomerId(rs.getString(25));
								businessOpportunity.setCrmResponsibleId(rs.getString(26));
								businessOpportunity.setProjectDebriefing(rs.getString(27));
								businessOpportunity.setDegreeOfImportance(rs.getString(28));
								businessOpportunity.setCrmBusinessOpportunitycolClassification(rs.getString(29));
								businessOpportunity.setReminderFrequency(rs.getString(30));
								businessOpportunity.setSysProjectDebriefingId(rs.getString(31));
								businessOpportunity.setSysReminderSettingsId(rs.getString(32));
								businessOpportunity.setCrmProductId(rs.getString(33));
								businessOpportunity.setCrmContactsId(rs.getString(34));
								businessOpportunity.setCrmPaymentCollectionPlanId(rs.getString(35));
								businessOpportunity.setMechanismId(rs.getString(36));
								businessOpportunity.setState(rs.getString(37));
								list.add(businessOpportunity);
							}
						}
					}
					sql = "SELECT id,business_opportunity_title,customer_name,estimated_amount,expected_signing_date,sales_stage,sales_stage_name,next_follow_time,business_opportunity_type,business_opportunity_type_name,business_opportunity_time,business_opportunity_source,person_id,person_name,department_id,department_name,audit,remarks,create_id,create_name,create_date,update_id,update_name,update_date,customer_id,crm_responsible_id,project_debriefing,degree_of_importance,crm_business_opportunitycol_classification,reminder_frequency,sys_project_debriefing_id,sys_reminder_settings_id,crm_product_id,crm_contacts_id,crm_payment_collection_plan_id,mechanism_id,state FROM  crm_business_opportunity    where 1=1  and mechanism_id like ? and create_id=? "
							+ zsql + " order by create_date desc ";
					ps = conn.prepareStatement(sql);
					ps.setString(1, businessOpportunity.getMechanismId() + "%");
					ps.setString(2, username);
					rs = ps.executeQuery();
					while (rs.next()) {
						businessOpportunity = new BusinessOpportunity();
						businessOpportunity.setId(rs.getString(1));
						businessOpportunity.setBusinessOpportunityTitle(rs.getString(2));
						businessOpportunity.setCustomerName(rs.getString(3));
						businessOpportunity.setEstimatedAmount(rs.getString(4));
						businessOpportunity.setExpectedSigningDates(rs.getString(5));
						businessOpportunity.setSalesStage(rs.getString(6));
						businessOpportunity.setSalesStageName(rs.getString(7));
						businessOpportunity.setNextFollowTimes(rs.getString(8));
						businessOpportunity.setBusinessOpportunityType(rs.getString(9));
						businessOpportunity.setBusinessOpportunityTypeName(rs.getString(10));
						businessOpportunity.setBusinessOpportunityTimes(rs.getString(11));
						businessOpportunity.setBusinessOpportunitySource(rs.getString(12));
						businessOpportunity.setPersonId(rs.getString(13));
						businessOpportunity.setPersonName(rs.getString(14));
						businessOpportunity.setDepartmentId(rs.getString(15));
						businessOpportunity.setDepartmentName(rs.getString(16));
						businessOpportunity.setAudit(rs.getString(17));
						businessOpportunity.setRemarks(rs.getString(18));
						businessOpportunity.setCreateId(rs.getString(19));
						businessOpportunity.setCreateName(rs.getString(20));
						businessOpportunity.setCreateDate(rs.getString(21));
						businessOpportunity.setUpdateId(rs.getString(22));
						businessOpportunity.setUpdateName(rs.getString(23));
						businessOpportunity.setUpdateDate(rs.getString(24));
						businessOpportunity.setCustomerId(rs.getString(25));
						businessOpportunity.setCrmResponsibleId(rs.getString(26));
						businessOpportunity.setProjectDebriefing(rs.getString(27));
						businessOpportunity.setDegreeOfImportance(rs.getString(28));
						businessOpportunity.setCrmBusinessOpportunitycolClassification(rs.getString(29));
						businessOpportunity.setReminderFrequency(rs.getString(30));
						businessOpportunity.setSysProjectDebriefingId(rs.getString(31));
						businessOpportunity.setSysReminderSettingsId(rs.getString(32));
						businessOpportunity.setCrmProductId(rs.getString(33));
						businessOpportunity.setCrmContactsId(rs.getString(34));
						businessOpportunity.setCrmPaymentCollectionPlanId(rs.getString(35));
						businessOpportunity.setMechanismId(rs.getString(36));
						businessOpportunity.setState(rs.getString(37));
						list.add(businessOpportunity);
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
			System.out.println(
					"List<BusinessOpportunity> getItems(BusinessOpportunity businessOpportunity)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		} finally {
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		}
		return list;
	}

	@Override
	public List<BusinessOpportunity> getItemsOfMy(BusinessOpportunity businessOpportunity) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<BusinessOpportunity> list = new ArrayList<BusinessOpportunity>();
		String username = businessOpportunity.getCreateName();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String sql = "";

			String zsql = "";
			if (businessOpportunity.getExpectedSigningDates() == null) {
				if (businessOpportunity.getExpectedSigningDateRange() != null) {
					int i = 0;
					for (Date t : businessOpportunity.getExpectedSigningDateRange()) {
						if (i == 0) {
							zsql += " and expected_signing_date>='" + df.format(t) + "'";
							i++;
						} else {
							zsql += " and expected_signing_date<='" + df.format(t) + "'";
						}
					}
				}
			} else {
				if (!businessOpportunity.getExpectedSigningDates().equals("")) {
					if (businessOpportunity.getExpectedSigningDates().equals("今日")) {
						zsql += " and expected_signing_date>='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayStartTime().getTime())
								+ "'";
						zsql += " and expected_signing_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayEndTime().getTime())
								+ "'";
					}
					if (businessOpportunity.getExpectedSigningDates().equals("本周")) {
						zsql += " and expected_signing_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayStartTime().getTime()) + "'";
						zsql += " and expected_signing_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayEndTime().getTime()) + "'";
					}
					if (businessOpportunity.getExpectedSigningDates().equals("本月")) {
						zsql += " and expected_signing_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentMonthStartTime().getTime()) + "'";
						zsql += " and expected_signing_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthEndTime().getTime())
								+ "'";
					}
					if (businessOpportunity.getExpectedSigningDates().equals("本季")) {
						zsql += " and expected_signing_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterStartTime().getTime()) + "'";
						zsql += " and expected_signing_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterEndTime().getTime()) + "'";
					}
					if (businessOpportunity.getExpectedSigningDates().equals("本年")) {
						zsql += " and expected_signing_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentYearStartTime().getTime()) + "'";
						zsql += " and expected_signing_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearEndTime().getTime())
								+ "'";
					}
				} else {
					if (businessOpportunity.getExpectedSigningDateRange() != null) {
						int i = 0;
						for (Date t : businessOpportunity.getExpectedSigningDateRange()) {
							if (i == 0) {
								zsql += " and expected_signing_date>='" + df.format(t) + "'";
								i++;
							} else {
								zsql += " and expected_signing_date<='" + df.format(t) + "'";
							}
						}
					}
				}
			}

			if (businessOpportunity.getCreateDate() == null) {
				if (businessOpportunity.getCreateDateRange() != null) {
					int i = 0;
					for (Date t : businessOpportunity.getCreateDateRange()) {
						if (i == 0) {
							zsql += " and create_date>='" + df.format(t) + "'";
							i++;
						} else {
							zsql += " and create_date<='" + df.format(t) + "'";
						}
					}
				}
			} else {
				if (!businessOpportunity.getCreateDate().equals("")) {
					if (businessOpportunity.getCreateDate().equals("今日")) {
						zsql += " and create_date>='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayStartTime().getTime())
								+ "'";
						zsql += " and create_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayEndTime().getTime())
								+ "'";
					}
					if (businessOpportunity.getCreateDate().equals("本周")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayStartTime().getTime()) + "'";
						zsql += " and create_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayEndTime().getTime()) + "'";
					}
					if (businessOpportunity.getCreateDate().equals("本月")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentMonthStartTime().getTime()) + "'";
						zsql += " and create_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthEndTime().getTime())
								+ "'";
					}
					if (businessOpportunity.getCreateDate().equals("本季")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterStartTime().getTime()) + "'";
						zsql += " and create_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterEndTime().getTime()) + "'";
					}
					if (businessOpportunity.getCreateDate().equals("本年")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentYearStartTime().getTime()) + "'";
						zsql += " and create_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearEndTime().getTime())
								+ "'";
					}
				} else {
					if (businessOpportunity.getCreateDateRange() != null) {
						int i = 0;
						for (Date t : businessOpportunity.getCreateDateRange()) {
							if (i == 0) {
								zsql += " and create_date>='" + df.format(t) + "'";
								i++;
							} else {
								zsql += " and create_date<='" + df.format(t) + "'";
							}
						}
					}
				}
			}
			if (businessOpportunity.getSalesStages() != null) {
				if (businessOpportunity.getSalesStages().length != 0) {

					zsql += " and (";
					for (int i = 0; i < businessOpportunity.getSalesStages().length; i++) {
						zsql += "sales_stage='" + businessOpportunity.getSalesStages()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (businessOpportunity.getEstimatedAmount() != null) {
				if (!businessOpportunity.getEstimatedAmount().equals("")) {
					zsql += "and estimated_amount<='" + businessOpportunity.getEstimatedAmount() + "'";
				}
			}
			if (businessOpportunity.getEstimatedAmounts() != null) {
				if (!businessOpportunity.getEstimatedAmounts().equals("")) {
					zsql += "and estimated_amount>='" + businessOpportunity.getEstimatedAmounts() + "'";
				}
			}
			if (businessOpportunity.getCustomerIds() != null) {
				if (businessOpportunity.getCustomerIds().length != 0) {

					zsql += " and (";
					for (int i = 0; i < businessOpportunity.getCustomerIds().length; i++) {
						zsql += "  customer_id='" + businessOpportunity.getCustomerIds()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (businessOpportunity.getBusinessOpportunityTypes() != null) {
				if (businessOpportunity.getBusinessOpportunityTypes().length != 0) {

					zsql += " and (";
					for (int i = 0; i < businessOpportunity.getBusinessOpportunityTypes().length; i++) {
						zsql += "  business_opportunity_type='" + businessOpportunity.getBusinessOpportunityTypes()[i]
								+ "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}

			if (businessOpportunity.getBusinessOpportunityTimes() == null) {
				if (businessOpportunity.getBusinessOpportunityTimeDateRange() != null) {
					int i = 0;
					for (Date t : businessOpportunity.getBusinessOpportunityTimeDateRange()) {
						if (i == 0) {
							zsql += " and business_opportunity_time>='" + df.format(t) + "'";
							i++;
						} else {
							zsql += " and business_opportunity_time<='" + df.format(t) + "'";
						}
					}
				}
			} else {
				if (!businessOpportunity.getBusinessOpportunityTimes().equals("")) {
					if (businessOpportunity.getBusinessOpportunityTimes().equals("今日")) {
						zsql += " and business_opportunity_time>='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayStartTime().getTime())
								+ "'";
						zsql += " and business_opportunity_time<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayEndTime().getTime())
								+ "'";
					}
					if (businessOpportunity.getBusinessOpportunityTimes().equals("本周")) {
						zsql += " and business_opportunity_time>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayStartTime().getTime()) + "'";
						zsql += " and business_opportunity_time<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayEndTime().getTime()) + "'";
					}
					if (businessOpportunity.getBusinessOpportunityTimes().equals("本月")) {
						zsql += " and business_opportunity_time>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentMonthStartTime().getTime()) + "'";
						zsql += " and business_opportunity_time<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthEndTime().getTime())
								+ "'";
					}
					if (businessOpportunity.getBusinessOpportunityTimes().equals("本季")) {
						zsql += " and business_opportunity_time>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterStartTime().getTime()) + "'";
						zsql += " and business_opportunity_time<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterEndTime().getTime()) + "'";
					}
					if (businessOpportunity.getBusinessOpportunityTimes().equals("本年")) {
						zsql += " and business_opportunity_time>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentYearStartTime().getTime()) + "'";
						zsql += " and business_opportunity_time<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearEndTime().getTime())
								+ "'";
					}
				} else {
					if (businessOpportunity.getBusinessOpportunityTimeDateRange() != null) {
						int i = 0;
						for (Date t : businessOpportunity.getBusinessOpportunityTimeDateRange()) {
							if (i == 0) {
								zsql += " and business_opportunity_time>='" + df.format(t) + "'";
								i++;
							} else {
								zsql += " and business_opportunity_time<='" + df.format(t) + "'";
							}
						}
					}
				}
			}
			if (businessOpportunity.getBusinessOpportunitySources() != null) {
				if (businessOpportunity.getBusinessOpportunitySources().length != 0) {

					zsql += " and (";
					for (int i = 0; i < businessOpportunity.getBusinessOpportunitySources().length; i++) {
						zsql += "  business_opportunity_source='"
								+ businessOpportunity.getBusinessOpportunitySources()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (businessOpportunity.getCreateIds() != null) {
				if (businessOpportunity.getCreateIds().length != 0) {

					zsql += " and (";
					for (int i = 0; i < businessOpportunity.getCreateIds().length; i++) {
						zsql += "  create_id='" + businessOpportunity.getCreateIds()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}

			if (businessOpportunity.getNextFollowTimes() == null) {
				if (businessOpportunity.getNextFollowTimeDateRange() != null) {
					int i = 0;
					for (Date t : businessOpportunity.getNextFollowTimeDateRange()) {
						if (i == 0) {
							zsql += " and next_follow_time>='" + df.format(t) + "'";
							i++;
						} else {
							zsql += " and next_follow_time<='" + df.format(t) + "'";
						}
					}
				}
			} else {
				if (!businessOpportunity.getNextFollowTimes().equals("")) {
					if (businessOpportunity.getNextFollowTimes().equals("今日")) {
						zsql += " and next_follow_time>='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayStartTime().getTime())
								+ "'";
						zsql += " and next_follow_time<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayEndTime().getTime())
								+ "'";
					}
					if (businessOpportunity.getNextFollowTimes().equals("本周")) {
						zsql += " and next_follow_time>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayStartTime().getTime()) + "'";
						zsql += " and next_follow_time<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayEndTime().getTime()) + "'";
					}
					if (businessOpportunity.getNextFollowTimes().equals("本月")) {
						zsql += " and next_follow_time>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentMonthStartTime().getTime()) + "'";
						zsql += " and next_follow_time<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthEndTime().getTime())
								+ "'";
					}
					if (businessOpportunity.getNextFollowTimes().equals("本季")) {
						zsql += " and next_follow_time>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterStartTime().getTime()) + "'";
						zsql += " and next_follow_time<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterEndTime().getTime()) + "'";
					}
					if (businessOpportunity.getNextFollowTimes().equals("本年")) {
						zsql += " and next_follow_time>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentYearStartTime().getTime()) + "'";
						zsql += " and next_follow_time<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearEndTime().getTime())
								+ "'";
					}
				} else {
					if (businessOpportunity.getNextFollowTimeDateRange() != null) {
						int i = 0;
						for (Date t : businessOpportunity.getNextFollowTimeDateRange()) {
							if (i == 0) {
								zsql += " and next_follow_time>='" + df.format(t) + "'";
								i++;
							} else {
								zsql += " and next_follow_time<='" + df.format(t) + "'";
							}
						}
					}
				}
			}
			if (businessOpportunity.getUpdateDate() == null) {
				if (businessOpportunity.getUpdateDateRange() != null) {
					int i = 0;
					for (Date t : businessOpportunity.getUpdateDateRange()) {
						if (i == 0) {
							zsql += " and update_date>='" + df.format(t) + "'";
							i++;
						} else {
							zsql += " and update_date<='" + df.format(t) + "'";
						}
					}
				}
			} else {
				if (!businessOpportunity.getUpdateDate().equals("")) {
					if (businessOpportunity.getUpdateDate().equals("今日")) {
						zsql += " and update_date>='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayStartTime().getTime())
								+ "'";
						zsql += " and update_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayEndTime().getTime())
								+ "'";
					}
					if (businessOpportunity.getUpdateDate().equals("本周")) {
						zsql += " and update_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayStartTime().getTime()) + "'";
						zsql += " and update_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayEndTime().getTime()) + "'";
					}
					if (businessOpportunity.getUpdateDate().equals("本月")) {
						zsql += " and update_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentMonthStartTime().getTime()) + "'";
						zsql += " and update_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthEndTime().getTime())
								+ "'";
					}
					if (businessOpportunity.getUpdateDate().equals("本季")) {
						zsql += " and update_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterStartTime().getTime()) + "'";
						zsql += " and update_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterEndTime().getTime()) + "'";
					}
					if (businessOpportunity.getUpdateDate().equals("本年")) {
						zsql += " and update_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentYearStartTime().getTime()) + "'";
						zsql += " and update_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearEndTime().getTime())
								+ "'";
					}
				} else {
					if (businessOpportunity.getUpdateDateRange() != null) {
						int i = 0;
						for (Date t : businessOpportunity.getUpdateDateRange()) {
							if (i == 0) {
								zsql += " and update_date>='" + df.format(t) + "'";
								i++;
							} else {
								zsql += " and update_date<='" + df.format(t) + "'";
							}
						}
					}
				}
			}
			sql = "SELECT id,business_opportunity_title,customer_name,estimated_amount,expected_signing_date,sales_stage,sales_stage_name,next_follow_time,business_opportunity_type,business_opportunity_type_name,business_opportunity_time,business_opportunity_source,person_id,person_name,department_id,department_name,audit,remarks,create_id,create_name,create_date,update_id,update_name,update_date,customer_id,crm_responsible_id,project_debriefing,degree_of_importance,crm_business_opportunitycol_classification,reminder_frequency,sys_project_debriefing_id,sys_reminder_settings_id,crm_product_id,crm_contacts_id,crm_payment_collection_plan_id,mechanism_id,state FROM  crm_business_opportunity    where 1=1  and mechanism_id like ? and create_id=? "
					+ zsql + " order by create_date desc ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, businessOpportunity.getMechanismId() + "%");
			ps.setString(2, businessOpportunity.getCreateName());
			rs = ps.executeQuery();
			while (rs.next()) {
				businessOpportunity = new BusinessOpportunity();
				businessOpportunity.setId(rs.getString(1));
				businessOpportunity.setBusinessOpportunityTitle(rs.getString(2));
				businessOpportunity.setCustomerName(rs.getString(3));
				businessOpportunity.setEstimatedAmount(rs.getString(4));
				businessOpportunity.setExpectedSigningDates(rs.getString(5));
				businessOpportunity.setSalesStage(rs.getString(6));
				businessOpportunity.setSalesStageName(rs.getString(7));
				businessOpportunity.setNextFollowTimes(rs.getString(8));
				businessOpportunity.setBusinessOpportunityType(rs.getString(9));
				businessOpportunity.setBusinessOpportunityTypeName(rs.getString(10));
				businessOpportunity.setBusinessOpportunityTimes(rs.getString(11));
				businessOpportunity.setBusinessOpportunitySource(rs.getString(12));
				businessOpportunity.setPersonId(rs.getString(13));
				businessOpportunity.setPersonName(rs.getString(14));
				businessOpportunity.setDepartmentId(rs.getString(15));
				businessOpportunity.setDepartmentName(rs.getString(16));
				businessOpportunity.setAudit(rs.getString(17));
				businessOpportunity.setRemarks(rs.getString(18));
				businessOpportunity.setCreateId(rs.getString(19));
				businessOpportunity.setCreateName(rs.getString(20));
				businessOpportunity.setCreateDate(rs.getString(21));
				businessOpportunity.setUpdateId(rs.getString(22));
				businessOpportunity.setUpdateName(rs.getString(23));
				businessOpportunity.setUpdateDate(rs.getString(24));
				businessOpportunity.setCustomerId(rs.getString(25));
				businessOpportunity.setCrmResponsibleId(rs.getString(26));
				businessOpportunity.setProjectDebriefing(rs.getString(27));
				businessOpportunity.setDegreeOfImportance(rs.getString(28));
				businessOpportunity.setCrmBusinessOpportunitycolClassification(rs.getString(29));
				businessOpportunity.setReminderFrequency(rs.getString(30));
				businessOpportunity.setSysProjectDebriefingId(rs.getString(31));
				businessOpportunity.setSysReminderSettingsId(rs.getString(32));
				businessOpportunity.setCrmProductId(rs.getString(33));
				businessOpportunity.setCrmContactsId(rs.getString(34));
				businessOpportunity.setCrmPaymentCollectionPlanId(rs.getString(35));
				businessOpportunity.setMechanismId(rs.getString(36));
				businessOpportunity.setState(rs.getString(37));
				list.add(businessOpportunity);
			}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println(
					"List<BusinessOpportunity> getItems(BusinessOpportunity businessOpportunity)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		} finally {
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		}
		return list;
	}

	@Override
	public BusinessOpportunity edit(BusinessOpportunity businessOpportunity) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		List<BusinessOpportunity> list = new ArrayList<BusinessOpportunity>();
		List<Contract> contractList = new ArrayList<Contract>();
		List<Cost> costList = new ArrayList<Cost>();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String sql = "SELECT id,business_opportunity_title,customer_name,estimated_amount,expected_signing_date,sales_stage,sales_stage_name,next_follow_time,business_opportunity_type,business_opportunity_type_name,business_opportunity_time,business_opportunity_source,person_id,person_name,department_id,department_name,audit,remarks,create_id,create_name,create_date,update_id,update_name,update_date,customer_id,crm_responsible_id,project_debriefing,degree_of_importance,crm_business_opportunitycol_classification,reminder_frequency,sys_project_debriefing_id,sys_reminder_settings_id,crm_product_id,crm_contacts_id,crm_payment_collection_plan_id,mechanism_id,state,business_opportunity_source_name,estimatedSalesQuantity,biddingProcurementDate,unit,customer_no FROM  crm_business_opportunity    where 1=1 and id=? order by create_date desc ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, businessOpportunity.getId());
			rs = ps.executeQuery();
			while (rs.next()) {
				businessOpportunity = new BusinessOpportunity();
				businessOpportunity.setId(rs.getString(1));
				businessOpportunity.setBusinessOpportunityTitle(rs.getString(2));
				businessOpportunity.setCustomerName(rs.getString(3));
				businessOpportunity.setEstimatedAmount(rs.getString(4));
				if (rs.getString(5) == null) {

				} else {
					if (rs.getString(5).equals("")) {

					} else {
						businessOpportunity.setExpectedSigningDate(rs.getDate(5));
					}
				}
				businessOpportunity.setSalesStage(rs.getString(6) + "!_" + rs.getString(7));
				businessOpportunity.setSalesStageName(rs.getString(7));
				if (rs.getString(8) == null) {

				} else {
					if (rs.getString(8).equals("")) {

					} else {
						businessOpportunity.setNextFollowTime(rs.getDate(8));
					}
				}
				businessOpportunity.setBusinessOpportunityType(rs.getString(9) + "!_" + rs.getString(10));
				businessOpportunity.setBusinessOpportunityTypeName(rs.getString(10));
				if (rs.getString(11) == null) {

				} else {
					if (rs.getString(11).equals("")) {

					} else {
						businessOpportunity.setBusinessOpportunityTime(rs.getDate(11));
					}
				}
				businessOpportunity.setBusinessOpportunitySource(rs.getString(12) + "!_" + rs.getString(38));
				businessOpportunity.setPersonId(rs.getString(13) + "!_" + rs.getString(14));
				businessOpportunity.setPersonName(rs.getString(14));
				businessOpportunity.setDepartmentId(rs.getString(15) + "!_" + rs.getString(16));
				businessOpportunity.setDepartmentName(rs.getString(16));
				businessOpportunity.setAudit(rs.getString(17));
				businessOpportunity.setRemarks(rs.getString(18));
				businessOpportunity.setCreateId(rs.getString(19));
				businessOpportunity.setCreateName(rs.getString(20));
				businessOpportunity.setCreateDate(rs.getString(21));
				businessOpportunity.setUpdateId(rs.getString(22));
				businessOpportunity.setUpdateName(rs.getString(23));
				businessOpportunity.setUpdateDate(rs.getString(24));
				businessOpportunity.setCustomerId(rs.getString(25) + "!_" + rs.getString(3) + "!_" + rs.getString(42));
				businessOpportunity.setCrmResponsibleId(rs.getString(26));
				businessOpportunity.setProjectDebriefing(rs.getString(27));
				businessOpportunity.setDegreeOfImportance(rs.getString(28));
				businessOpportunity.setCrmBusinessOpportunitycolClassification(rs.getString(29));
				businessOpportunity.setReminderFrequency(rs.getString(30));
				businessOpportunity.setSysProjectDebriefingId(rs.getString(31));
				businessOpportunity.setSysReminderSettingsId(rs.getString(32));
				businessOpportunity.setCrmProductId(rs.getString(33));
				businessOpportunity.setCrmContactsId(rs.getString(34));
				businessOpportunity.setCrmPaymentCollectionPlanId(rs.getString(35));
				businessOpportunity.setMechanismId(rs.getString(36));
				businessOpportunity.setState(rs.getString(37));
				businessOpportunity.setEstimatedSalesQuantity(rs.getBigDecimal(39));
				if (rs.getString(40) == null) {

				} else {
					if (rs.getString(40).equals("")) {

					} else {
						businessOpportunity.setBiddingProcurementDate(rs.getDate(40));
					}
				}
				businessOpportunity.setUnit(rs.getString(41));
			}
			sql = "SELECT id,contract_title,customer_id,customer_name,business_opportunity_id,business_opportunity_name,contract_start_date,contract_end_date,total_contract_amount,signing_date,contract_status,contract_no,contract_type,contract_type_name,payment_type,payment_type_name,our_signatory,client_signatory,next_follow_time,contract_category,audit,person_id,person_name,department_id,department_name,remarks,create_id,create_name,create_date,update_id,update_name,update_date,crm_responsible_id,implementation,production_time,output,shipping_time,shipment_volume,settlement_time,settlement_volume,collection_time,collection_amount,contract_unsettled_quantity,unsettled_shipment_quantity,accounts_receivable,collaborator_name,mechanism_id,state FROM  crm_contract  where 1=1 and mechanism_id like ? and business_opportunity_id=? order by create_date desc ";
			ps1 = conn.prepareStatement(sql);
			ps1.setString(1, businessOpportunity.getMechanismId() + "%");
			ps1.setString(2, businessOpportunity.getId());
			rs1 = ps1.executeQuery();
			while (rs1.next()) {
				Contract contract = new Contract();
				contract.setId(rs1.getString(1));
				contract.setContractTitle(rs1.getString(2));
				contract.setCustomerId(rs1.getString(3));
				contract.setCustomerName(rs1.getString(4));
				contract.setBusinessOpportunityId(rs1.getString(5));
				contract.setBusinessOpportunityName(rs1.getString(6));
				contract.setContractStartDate(rs1.getDate(7));
				contract.setContractEndDate(rs1.getDate(8));
				contract.setTotalContractAmount(rs1.getString(9));
				contract.setSigningDate(rs1.getDate(10));
				contract.setContractStatus(rs1.getString(11));
				contract.setContractNo(rs1.getString(12));
				contract.setContractType(rs1.getString(13));
				contract.setContractTypeName(rs1.getString(14));
				contract.setPaymentType(rs1.getString(15));
				contract.setPaymentTypeName(rs1.getString(16));
				contract.setOurSignatory(rs1.getString(17));
				contract.setClientSignatory(rs1.getString(18));
				contract.setNextFollowTime(rs1.getDate(19));
				contract.setContractCategory(rs1.getString(20));
				contract.setAudit(rs1.getString(21));
				contract.setPersonId(rs1.getString(22));
				contract.setPersonName(rs1.getString(23));
				contract.setDepartmentId(rs1.getString(24));
				contract.setDepartmentName(rs1.getString(25));
				contract.setRemarks(rs1.getString(26));
				contract.setCreateId(rs1.getString(27));
				contract.setCreateName(rs1.getString(28));
				contract.setCreateDate(rs1.getString(29));
				contract.setUpdateId(rs1.getString(30));
				contract.setUpdateName(rs1.getString(31));
				contract.setUpdateDate(rs1.getString(32));
				contract.setCrmResponsibleId(rs1.getString(33));
				contract.setImplementation(rs1.getString(34));
				contract.setProductionTime(rs1.getString(35));
				contract.setOutput(rs1.getString(36));
				contract.setShippingTime(rs1.getString(37));
				contract.setShipmentVolume(rs1.getString(38));
				contract.setSettlementTime(rs1.getString(39));
				contract.setSettlementVolume(rs1.getString(40));
				contract.setCollectionTime(rs1.getString(41));
				contract.setCollectionAmount(rs1.getString(42));
				contract.setContractUnsettledQuantity(rs1.getString(43));
				contract.setUnsettledShipmentQuantity(rs1.getString(44));
				contract.setAccountsReceivable(rs1.getString(45));
				contract.setCollaboratorName(rs1.getString(46));
				contract.setMechanismId(rs1.getString(47));
				contract.setState(rs1.getString(48));
				contractList.add(contract);
			}
			businessOpportunity.setContractList(contractList);
			sql = "SELECT id,expense_type,expense_type_name,cost_description,expense_amount,time_of_occurrence,customer_id,customer_name,contacts_id,contacts_name,business_id,business_name,contract_id,contract_name,follow_up_record,follow_up_record_name,visit_sign_in,enclosure,person_id,person_name,create_id,create_name,create_date,update_id,update_name,update_date,mechanism_id FROM  crm_cost    where 1=1 and mechanism_id=? and business_id=? order by create_date desc ";
			ps2 = conn.prepareStatement(sql);
			ps2.setString(1, businessOpportunity.getMechanismId());
			ps2.setString(2, businessOpportunity.getId());
			rs2 = ps2.executeQuery();
			while (rs2.next()) {
				Cost cost = new Cost();
				cost.setId(rs2.getString(1));
				cost.setExpenseType(rs2.getString(2));
				cost.setExpenseTypeName(rs2.getString(3));
				cost.setCostDescription(rs2.getString(4));
				cost.setExpenseAmount(rs2.getString(5));
				cost.setTimeOfOccurrence(rs2.getDate(6));
				cost.setCustomerId(rs2.getString(7));
				cost.setCustomerName(rs2.getString(8));
				cost.setContactsId(rs2.getString(9));
				cost.setContactsName(rs2.getString(10));
				cost.setBusinessId(rs2.getString(11));
				cost.setBusinessName(rs2.getString(12));
				cost.setContractId(rs2.getString(13));
				cost.setContractName(rs2.getString(14));
				cost.setFollowUpRecord(rs2.getString(15));
				cost.setFollowUpRecordName(rs2.getString(16));
				cost.setVisitSignIn(rs2.getString(17));
				cost.setEnclosure(rs2.getString(18));
				cost.setPersonId(rs2.getString(19));
				cost.setPersonName(rs2.getString(20));
				cost.setCreateId(rs2.getString(21));
				cost.setCreateName(rs2.getString(22));
				cost.setCreateDate(rs2.getString(23));
				cost.setUpdateId(rs2.getString(24));
				cost.setUpdateName(rs2.getString(25));
				cost.setUpdateDate(rs2.getString(26));
				cost.setMechanismId(rs2.getString(27));
				costList.add(cost);
			}
			businessOpportunity.setCostList(costList);
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println(
					"List<BusinessOpportunity> getItems(BusinessOpportunity businessOpportunity)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
			ResourceManager.close(rs);
			ResourceManager.close(rs1);
			ResourceManager.close(rs2);
			ResourceManager.close(ps);
			ResourceManager.close(ps1);
			ResourceManager.close(ps2);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		} finally {
			ResourceManager.close(rs);
			ResourceManager.close(rs1);
			ResourceManager.close(rs2);
			ResourceManager.close(ps);
			ResourceManager.close(ps1);
			ResourceManager.close(ps2);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		}
		return businessOpportunity;
	}

	@Override
	public int saveBusinessOpportunity(BusinessOpportunity businessOpportunity) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String ibusinessOpportunitySQL = "INSERT INTO crm_business_opportunity(id,business_opportunity_title,customer_id,customer_name,estimated_amount,expected_signing_date,sales_stage,sales_stage_name,next_follow_time,business_opportunity_type,business_opportunity_type_name,business_opportunity_time,business_opportunity_source,business_opportunity_source_name,person_id,person_name,department_id,department_name,audit,remarks,create_id,create_name,create_date,update_id,update_name,update_date,crm_responsible_id,project_debriefing,degree_of_importance,crm_business_opportunitycol_classification,reminder_frequency,sys_project_debriefing_id,sys_reminder_settings_id,crm_product_id,crm_contacts_id,crm_payment_collection_plan_id,mechanism_id,state,estimatedSalesQuantity,biddingProcurementDate,unit,customer_no)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(ibusinessOpportunitySQL);

			ps.setString(1, businessOpportunity.getId());
			ps.setString(2, businessOpportunity.getBusinessOpportunityTitle());

			if (businessOpportunity.getCustomerId() == null) {
				ps.setString(3, "");
				ps.setString(4, "");
				ps.setString(42, null);
			} else {
				if (businessOpportunity.getCustomerId().equals("")) {
					ps.setString(3, "");
					ps.setString(4, "");
					ps.setString(42, null);
				} else {
					String[] businessOpportunitylx = businessOpportunity.getCustomerId().split("!_");
					ps.setString(3, businessOpportunitylx[0]);
					ps.setString(4, businessOpportunitylx[1]);
					ps.setString(42, businessOpportunitylx[2]);
				}
			}
			ps.setString(5, businessOpportunity.getEstimatedAmount());
			if (businessOpportunity.getExpectedSigningDate() == null) {
				ps.setString(6, "");
			} else {
				if (businessOpportunity.getExpectedSigningDate().equals("")) {
					ps.setString(6, "");
				} else {
					ps.setString(6, df.format(businessOpportunity.getExpectedSigningDate()));
				}
			}
			if (businessOpportunity.getSalesStage() == null) {
				ps.setString(7, "");
				ps.setString(8, "");
			} else {
				if (businessOpportunity.getSalesStage().equals("")) {
					ps.setString(7, "");
					ps.setString(8, "");
				} else {
					String[] businessOpportunitylx = businessOpportunity.getSalesStage().split("!_");
					ps.setString(7, businessOpportunitylx[0]);
					ps.setString(8, businessOpportunitylx[1]);
				}
			}
			if (businessOpportunity.getNextFollowTime() == null) {
				ps.setString(9, "");
			} else {
				if (businessOpportunity.getNextFollowTime().equals("")) {
					ps.setString(9, "");
				} else {
					ps.setString(9, df.format(businessOpportunity.getNextFollowTime()));
				}
			}
			if (businessOpportunity.getBusinessOpportunityType() == null) {
				ps.setString(10, "");
				ps.setString(11, "");
			} else {
				if (businessOpportunity.getBusinessOpportunityType().equals("")) {
					ps.setString(10, "");
					ps.setString(11, "");
				} else {
					String[] businessOpportunitylx = businessOpportunity.getBusinessOpportunityType().split("!_");
					ps.setString(10, businessOpportunitylx[0]);
					ps.setString(11, businessOpportunitylx[1]);
				}
			}
			if (businessOpportunity.getBusinessOpportunityTime() == null) {
				ps.setString(12, "");
			} else {
				if (businessOpportunity.getBusinessOpportunityTime().equals("")) {
					ps.setString(12, "");
				} else {
					ps.setString(12, df.format(businessOpportunity.getBusinessOpportunityTime()));
				}
			}
			if (businessOpportunity.getBusinessOpportunitySource() == null) {
				ps.setString(13, "");
				ps.setString(14, "");
			} else {
				if (businessOpportunity.getBusinessOpportunitySource().equals("")) {
					ps.setString(13, "");
					ps.setString(14, "");
				} else {
					String[] businessOpportunitylx = businessOpportunity.getBusinessOpportunitySource().split("!_");
					ps.setString(13, businessOpportunitylx[0]);
					ps.setString(14, businessOpportunitylx[1]);
				}
			}
			if (businessOpportunity.getPersonId() == null) {
				ps.setString(15, "");
				ps.setString(16, "");
			} else {
				if (businessOpportunity.getPersonId().equals("")) {
					ps.setString(15, "");
					ps.setString(16, "");
				} else {
					String[] businessOpportunitylx = businessOpportunity.getPersonId().split("!_");
					ps.setString(15, businessOpportunitylx[0]);
					ps.setString(16, businessOpportunitylx[1]);
				}
			}
			if (businessOpportunity.getDepartmentId() == null) {
				ps.setString(17, "");
				ps.setString(18, "");
			} else {
				if (businessOpportunity.getDepartmentId().equals("")) {
					ps.setString(17, "");
					ps.setString(18, "");
				} else {
					String[] businessOpportunitylx = businessOpportunity.getDepartmentId().split("!_");
					ps.setString(17, businessOpportunitylx[0]);
					ps.setString(18, businessOpportunitylx[1]);
				}
			}
			ps.setString(19, businessOpportunity.getAudit());
			ps.setString(20, businessOpportunity.getRemarks());
			ps.setString(21, businessOpportunity.getCreateId());
			ps.setString(22, businessOpportunity.getCreateName());
			ps.setString(23, businessOpportunity.getCreateDate());
			ps.setString(24, businessOpportunity.getUpdateId());
			ps.setString(25, businessOpportunity.getUpdateName());
			ps.setString(26, businessOpportunity.getUpdateDate());
			ps.setString(27, businessOpportunity.getCrmResponsibleId());
			ps.setString(28, businessOpportunity.getProjectDebriefing());
			ps.setString(29, businessOpportunity.getDegreeOfImportance());
			ps.setString(30, businessOpportunity.getCrmBusinessOpportunitycolClassification());
			ps.setString(31, businessOpportunity.getReminderFrequency());
			ps.setString(32, businessOpportunity.getSysProjectDebriefingId());
			ps.setString(33, businessOpportunity.getSysReminderSettingsId());
			ps.setString(34, businessOpportunity.getCrmProductId());
			ps.setString(35, businessOpportunity.getCrmContactsId());
			ps.setString(36, businessOpportunity.getCrmPaymentCollectionPlanId());
			ps.setString(37, businessOpportunity.getMechanismId());
			ps.setString(38, businessOpportunity.getState());
			ps.setBigDecimal(39, businessOpportunity.getEstimatedSalesQuantity());
			if (businessOpportunity.getBiddingProcurementDate() == null) {
				ps.setString(40, "");
			} else {
				if (businessOpportunity.getBiddingProcurementDate().equals("")) {
					ps.setString(40, "");
				} else {
					ps.setString(40, df.format(businessOpportunity.getBiddingProcurementDate()));
				}
			}
			ps.setString(41, businessOpportunity.getUnit());
			rv = ps.executeUpdate();
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println(
					"int saveBusinessOpportunity(BusinessOpportunity businessOpportunity)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
			rv = -1;
			ResourceManager.close(ps);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		} finally {
			ResourceManager.close(ps);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		}
		return rv;
	}

	@Override
	public int update(BusinessOpportunity businessOpportunity) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String ubusinessOpportunitySQL = "update crm_business_opportunity set state=?,business_opportunity_title=?,customer_id=?,customer_name=?,estimated_amount=?,expected_signing_date=?,sales_stage=?,sales_stage_name=?,next_follow_time=?,business_opportunity_type=?,business_opportunity_type_name=?,business_opportunity_time=?,business_opportunity_source=?,business_opportunity_source_name=?,person_id=?,person_name=?,department_id=?,department_name=?,audit=?,remarks=?,create_id=?,create_name=?,create_date=?,update_id=?,update_name=?,update_date=?,crm_responsible_id=?,project_debriefing=?,degree_of_importance=?,crm_business_opportunitycol_classification=?,reminder_frequency=?,sys_project_debriefing_id=?,sys_reminder_settings_id=?,crm_product_id=?,crm_contacts_id=?,crm_payment_collection_plan_id=?,mechanism_id=?,estimatedSalesQuantity=?,biddingProcurementDate=?,unit=?,customer_no=? where 1=1 and id=? ";
			ps = conn.prepareStatement(ubusinessOpportunitySQL);
			ps.setString(1, businessOpportunity.getState());
			ps.setString(2, businessOpportunity.getBusinessOpportunityTitle());

			if (businessOpportunity.getCustomerId() == null) {
				ps.setString(3, "");
				ps.setString(4, "");
				ps.setString(41, null);
			} else {
				if (businessOpportunity.getCustomerId().equals("")) {
					ps.setString(3, "");
					ps.setString(4, "");
					ps.setString(41, null);
				} else {
					String[] businessOpportunitylx = businessOpportunity.getCustomerId().split("!_");
					ps.setString(3, businessOpportunitylx[0]);
					ps.setString(4, businessOpportunitylx[1]);
					ps.setString(41, businessOpportunitylx[2]);
				}
			}
			ps.setString(5, businessOpportunity.getEstimatedAmount());
			if (businessOpportunity.getExpectedSigningDate() == null) {
				ps.setString(6, "");
			} else {
				if (businessOpportunity.getExpectedSigningDate().equals("")) {
					ps.setString(6, "");
				} else {
					ps.setString(6, df.format(businessOpportunity.getExpectedSigningDate()));
				}
			}
			if (businessOpportunity.getSalesStage() == null) {
				ps.setString(7, "");
				ps.setString(8, "");
			} else {
				if (businessOpportunity.getSalesStage().equals("")) {
					ps.setString(7, "");
					ps.setString(8, "");
				} else {
					String[] businessOpportunitylx = businessOpportunity.getSalesStage().split("!_");
					ps.setString(7, businessOpportunitylx[0]);
					ps.setString(8, businessOpportunitylx[1]);
				}
			}
			if (businessOpportunity.getNextFollowTime() == null) {
				ps.setString(9, "");
			} else {
				if (businessOpportunity.getNextFollowTime().equals("")) {
					ps.setString(9, "");
				} else {
					ps.setString(9, df.format(businessOpportunity.getNextFollowTime()));
				}
			}
			if (businessOpportunity.getBusinessOpportunityType() == null) {
				ps.setString(10, "");
				ps.setString(11, "");
			} else {
				if (businessOpportunity.getBusinessOpportunityType().equals("")) {
					ps.setString(10, "");
					ps.setString(11, "");
				} else {
					String[] businessOpportunitylx = businessOpportunity.getBusinessOpportunityType().split("!_");
					ps.setString(10, businessOpportunitylx[0]);
					ps.setString(11, businessOpportunitylx[1]);
				}
			}
			if (businessOpportunity.getBusinessOpportunityTime() == null) {
				ps.setString(12, "");
			} else {
				if (businessOpportunity.getBusinessOpportunityTime().equals("")) {
					ps.setString(12, "");
				} else {
					ps.setString(12, df.format(businessOpportunity.getBusinessOpportunityTime()));
				}
			}
			if (businessOpportunity.getBusinessOpportunitySource() == null) {
				ps.setString(13, "");
				ps.setString(14, "");
			} else {
				if (businessOpportunity.getBusinessOpportunitySource().equals("")) {
					ps.setString(13, "");
					ps.setString(14, "");
				} else {
					String[] businessOpportunitylx = businessOpportunity.getBusinessOpportunitySource().split("!_");
					ps.setString(13, businessOpportunitylx[0]);
					ps.setString(14, businessOpportunitylx[1]);
				}
			}
			if (businessOpportunity.getPersonId() == null) {
				ps.setString(15, "");
				ps.setString(16, "");
			} else {
				if (businessOpportunity.getPersonId().equals("")) {
					ps.setString(15, "");
					ps.setString(16, "");
				} else {
					String[] businessOpportunitylx = businessOpportunity.getPersonId().split("!_");
					ps.setString(15, businessOpportunitylx[0]);
					ps.setString(16, businessOpportunitylx[1]);
				}
			}
			if (businessOpportunity.getDepartmentId() == null) {
				ps.setString(17, "");
				ps.setString(18, "");
			} else {
				if (businessOpportunity.getDepartmentId().equals("")) {
					ps.setString(17, "");
					ps.setString(18, "");
				} else {
					String[] businessOpportunitylx = businessOpportunity.getDepartmentId().split("!_");
					ps.setString(17, businessOpportunitylx[0]);
					ps.setString(18, businessOpportunitylx[1]);
				}
			}
			ps.setString(19, businessOpportunity.getAudit());
			ps.setString(20, businessOpportunity.getRemarks());
			ps.setString(21, businessOpportunity.getCreateId());
			ps.setString(22, businessOpportunity.getCreateName());
			ps.setString(23, businessOpportunity.getCreateDate());
			ps.setString(24, businessOpportunity.getUpdateId());
			ps.setString(25, businessOpportunity.getUpdateName());
			ps.setString(26, businessOpportunity.getUpdateDate());
			ps.setString(27, businessOpportunity.getCrmResponsibleId());
			ps.setString(28, businessOpportunity.getProjectDebriefing());
			ps.setString(29, businessOpportunity.getDegreeOfImportance());
			ps.setString(30, businessOpportunity.getCrmBusinessOpportunitycolClassification());
			ps.setString(31, businessOpportunity.getReminderFrequency());
			ps.setString(32, businessOpportunity.getSysProjectDebriefingId());
			ps.setString(33, businessOpportunity.getSysReminderSettingsId());
			ps.setString(34, businessOpportunity.getCrmProductId());
			ps.setString(35, businessOpportunity.getCrmContactsId());
			ps.setString(36, businessOpportunity.getCrmPaymentCollectionPlanId());
			ps.setString(37, businessOpportunity.getMechanismId());
			ps.setBigDecimal(38, businessOpportunity.getEstimatedSalesQuantity());
			if (businessOpportunity.getBiddingProcurementDate() == null) {
				ps.setString(39, "");
			} else {
				if (businessOpportunity.getBiddingProcurementDate().equals("")) {
					ps.setString(39, "");
				} else {
					ps.setString(39, df.format(businessOpportunity.getBiddingProcurementDate()));
				}
			}
			ps.setString(40, businessOpportunity.getUnit());
			ps.setString(42, businessOpportunity.getId());
			rv = ps.executeUpdate();
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println(
					"int updateBusinessOpportunity(businessOpportunity businessOpportunity)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
			rv = -1;
			ResourceManager.close(ps);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		} finally {
			ResourceManager.close(ps);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		}
		return rv;
	}

}