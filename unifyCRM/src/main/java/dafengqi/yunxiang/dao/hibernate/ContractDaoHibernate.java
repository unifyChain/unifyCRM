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

import dafengqi.yunxiang.dao.ContractDao;
import dafengqi.yunxiang.model.CollectionDetail;
import dafengqi.yunxiang.model.Contract;
import dafengqi.yunxiang.model.Cost;
import dafengqi.yunxiang.model.Invoicing;
import dafengqi.yunxiang.util.ResourceManager;

@Repository("contractDao")
public class ContractDaoHibernate extends GenericDaoHibernate<Contract, Long> implements ContractDao {
	protected java.sql.Connection userConn;
	final boolean isConnSupplied = (userConn != null);

	public ContractDaoHibernate() {
		super(Contract.class);
	}

	@Override
	public List<Contract> getItems(Contract contract) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Contract> list = new ArrayList<Contract>();
		String username = contract.getCreateName();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String sql = "";

			String zsql = "";
			if (contract.getContractStatuss() != null) {
				if (contract.getContractStatuss().length != 0) {

					zsql += " and (";
					for (int i = 0; i < contract.getContractStatuss().length; i++) {
						zsql += "  contract_status='" + contract.getContractStatuss()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (contract.getPersonIds() != null) {
				if (contract.getPersonIds().length != 0) {

					zsql += " and (";
					for (int i = 0; i < contract.getPersonIds().length; i++) {
						zsql += "  person_id='" + contract.getPersonIds()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (contract.getTotalContractAmount() != null) {
				if (!contract.getTotalContractAmount().equals("")) {
					zsql += " and total_contract_amount>=" + contract.getTotalContractAmount() + "";
				}
			}
			if (contract.getTotalContractAmounts() != null) {
				if (!contract.getTotalContractAmounts().equals("")) {
					zsql += " and total_contract_amount<=" + contract.getTotalContractAmounts() + "";
				}
			}
			if (contract.getDepartmentIds() != null) {
				if (!contract.getDepartmentIds().equals("")) {
					zsql += "and department_id='" + contract.getDepartmentIds() + "'";
				}
			}
			if (contract.getContractStartDates() == null) {
				if (contract.getContractStartDateRange() != null) {
					int i = 0;
					for (Date t : contract.getContractStartDateRange()) {
						if (i == 0) {
							zsql += " and contract_start_date>='" + df.format(t) + "'";
							i++;
						} else {
							zsql += " and contract_start_date<='" + df.format(t) + "'";
						}
					}
				}
			} else {
				if (!contract.getContractStartDates().equals("")) {
					if (contract.getContractStartDates().equals("今日")) {
						zsql += " and contract_start_date>='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayStartTime().getTime())
								+ "'";
						zsql += " and contract_start_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayEndTime().getTime())
								+ "'";
					}
					if (contract.getContractStartDates().equals("本周")) {
						zsql += " and contract_start_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayStartTime().getTime()) + "'";
						zsql += " and contract_start_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayEndTime().getTime()) + "'";
					}
					if (contract.getContractStartDates().equals("本月")) {
						zsql += " and contract_start_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentMonthStartTime().getTime()) + "'";
						zsql += " and contract_start_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthEndTime().getTime())
								+ "'";
					}
					if (contract.getContractStartDates().equals("本季")) {
						zsql += " and contract_start_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterStartTime().getTime()) + "'";
						zsql += " and contract_start_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterEndTime().getTime()) + "'";
					}
					if (contract.getContractStartDates().equals("本年")) {
						zsql += " and contract_start_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentYearStartTime().getTime()) + "'";
						zsql += " and contract_start_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearEndTime().getTime())
								+ "'";
					}
				} else {
					if (contract.getContractStartDateRange() != null) {
						int i = 0;
						for (Date t : contract.getContractStartDateRange()) {
							if (i == 0) {
								zsql += " and contract_start_date>='" + df.format(t) + "'";
								i++;
							} else {
								zsql += " and contract_start_date<='" + df.format(t) + "'";
							}
						}
					}
				}
			}
			if (contract.getContractEndDates() == null) {
				if (contract.getContractEndDateRange() != null) {
					int i = 0;
					for (Date t : contract.getContractEndDateRange()) {
						if (i == 0) {
							zsql += " and contract_end_date>='" + df.format(t) + "'";
							i++;
						} else {
							zsql += " and contract_end_date<='" + df.format(t) + "'";
						}
					}
				}
			} else {
				if (!contract.getContractEndDates().equals("")) {
					if (contract.getContractEndDates().equals("今日")) {
						zsql += " and contract_end_date>='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayStartTime().getTime())
								+ "'";
						zsql += " and contract_end_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayEndTime().getTime())
								+ "'";
					}
					if (contract.getContractEndDates().equals("本周")) {
						zsql += " and contract_end_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayStartTime().getTime()) + "'";
						zsql += " and contract_end_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayEndTime().getTime()) + "'";
					}
					if (contract.getContractEndDates().equals("本月")) {
						zsql += " and contract_end_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentMonthStartTime().getTime()) + "'";
						zsql += " and contract_end_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthEndTime().getTime())
								+ "'";
					}
					if (contract.getContractEndDates().equals("本季")) {
						zsql += " and contract_end_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterStartTime().getTime()) + "'";
						zsql += " and contract_end_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterEndTime().getTime()) + "'";
					}
					if (contract.getContractEndDates().equals("本年")) {
						zsql += " and contract_end_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentYearStartTime().getTime()) + "'";
						zsql += " and contract_end_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearEndTime().getTime())
								+ "'";
					}
				} else {
					if (contract.getContractEndDateRange() != null) {
						int i = 0;
						for (Date t : contract.getContractEndDateRange()) {
							if (i == 0) {
								zsql += " and contract_end_date>='" + df.format(t) + "'";
								i++;
							} else {
								zsql += " and contract_end_date<='" + df.format(t) + "'";
							}
						}
					}
				}
			}
			if (contract.getSigningDates() == null) {
				if (contract.getSigningDateRange() != null) {
					int i = 0;
					for (Date t : contract.getSigningDateRange()) {
						if (i == 0) {
							zsql += " and signing_date>='" + df.format(t) + "'";
							i++;
						} else {
							zsql += " and signing_date<='" + df.format(t) + "'";
						}
					}
				}
			} else {
				if (!contract.getSigningDates().equals("")) {
					if (contract.getSigningDates().equals("今日")) {
						zsql += " and signing_date>='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayStartTime().getTime())
								+ "'";
						zsql += " and signing_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayEndTime().getTime())
								+ "'";
					}
					if (contract.getSigningDates().equals("本周")) {
						zsql += " and signing_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayStartTime().getTime()) + "'";
						zsql += " and signing_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayEndTime().getTime()) + "'";
					}
					if (contract.getSigningDates().equals("本月")) {
						zsql += " and signing_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentMonthStartTime().getTime()) + "'";
						zsql += " and signing_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthEndTime().getTime())
								+ "'";
					}
					if (contract.getSigningDates().equals("本季")) {
						zsql += " and signing_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterStartTime().getTime()) + "'";
						zsql += " and signing_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterEndTime().getTime()) + "'";
					}
					if (contract.getSigningDates().equals("本年")) {
						zsql += " and signing_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentYearStartTime().getTime()) + "'";
						zsql += " and signing_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearEndTime().getTime())
								+ "'";
					}
				} else {
					if (contract.getSigningDateRange() != null) {
						int i = 0;
						for (Date t : contract.getSigningDateRange()) {
							if (i == 0) {
								zsql += " and signing_date>='" + df.format(t) + "'";
								i++;
							} else {
								zsql += " and signing_date<='" + df.format(t) + "'";
							}
						}
					}
				}
			}
			if (contract.getContractTypes() != null) {
				if (contract.getContractTypes().length != 0) {

					zsql += " and (";
					for (int i = 0; i < contract.getContractTypes().length; i++) {
						zsql += "  contract_type='" + contract.getContractTypes()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (contract.getPaymentTypes() != null) {
				if (contract.getPaymentTypes().length != 0) {

					zsql += " and (";
					for (int i = 0; i < contract.getPaymentTypes().length; i++) {
						zsql += "  payment_type='" + contract.getPaymentTypes()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (contract.getCustomerIds() != null) {
				if (contract.getCustomerIds().length != 0) {

					zsql += " and (";
					for (int i = 0; i < contract.getCustomerIds().length; i++) {
						zsql += "  customer_id='" + contract.getCustomerIds()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (contract.getCreateIds() != null) {
				if (contract.getCreateIds().length != 0) {

					zsql += " and (";
					for (int i = 0; i < contract.getCreateIds().length; i++) {
						zsql += "  create_id='" + contract.getCreateIds()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}

			if (contract.getNextFollowTimes() == null) {
				if (contract.getNextFollowTimeDateRange() != null) {
					int i = 0;
					for (Date t : contract.getNextFollowTimeDateRange()) {
						if (i == 0) {
							zsql += " and next_follow_time>='" + df.format(t) + "'";
							i++;
						} else {
							zsql += " and next_follow_time<='" + df.format(t) + "'";
						}
					}
				}
			} else {
				if (!contract.getNextFollowTimes().equals("")) {
					if (contract.getNextFollowTimes().equals("今日")) {
						zsql += " and next_follow_time>='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayStartTime().getTime())
								+ "'";
						zsql += " and next_follow_time<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayEndTime().getTime())
								+ "'";
					}
					if (contract.getNextFollowTimes().equals("本周")) {
						zsql += " and next_follow_time>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayStartTime().getTime()) + "'";
						zsql += " and next_follow_time<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayEndTime().getTime()) + "'";
					}
					if (contract.getNextFollowTimes().equals("本月")) {
						zsql += " and next_follow_time>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentMonthStartTime().getTime()) + "'";
						zsql += " and next_follow_time<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthEndTime().getTime())
								+ "'";
					}
					if (contract.getNextFollowTimes().equals("本季")) {
						zsql += " and next_follow_time>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterStartTime().getTime()) + "'";
						zsql += " and next_follow_time<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterEndTime().getTime()) + "'";
					}
					if (contract.getNextFollowTimes().equals("本年")) {
						zsql += " and next_follow_time>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentYearStartTime().getTime()) + "'";
						zsql += " and next_follow_time<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearEndTime().getTime())
								+ "'";
					}
				} else {
					if (contract.getNextFollowTimeDateRange() != null) {
						int i = 0;
						for (Date t : contract.getNextFollowTimeDateRange()) {
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
			if (contract.getCreateDate() == null) {
				if (contract.getCreateDateRange() != null) {
					int i = 0;
					for (Date t : contract.getCreateDateRange()) {
						if (i == 0) {
							zsql += " and create_date>='" + df.format(t) + "'";
							i++;
						} else {
							zsql += " and create_date<='" + df.format(t) + "'";
						}
					}
				}
			} else {
				if (!contract.getCreateDate().equals("")) {
					if (contract.getCreateDate().equals("今日")) {
						zsql += " and create_date>='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayStartTime().getTime())
								+ "'";
						zsql += " and create_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayEndTime().getTime())
								+ "'";
					}
					if (contract.getCreateDate().equals("本周")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayStartTime().getTime()) + "'";
						zsql += " and create_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayEndTime().getTime()) + "'";
					}
					if (contract.getCreateDate().equals("本月")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentMonthStartTime().getTime()) + "'";
						zsql += " and create_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthEndTime().getTime())
								+ "'";
					}
					if (contract.getCreateDate().equals("本季")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterStartTime().getTime()) + "'";
						zsql += " and create_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterEndTime().getTime()) + "'";
					}
					if (contract.getCreateDate().equals("本年")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentYearStartTime().getTime()) + "'";
						zsql += " and create_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearEndTime().getTime())
								+ "'";
					}
				} else {
					if (contract.getCreateDateRange() != null) {
						int i = 0;
						for (Date t : contract.getCreateDateRange()) {
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

			if (contract.getUpdateDate() == null) {
				if (contract.getUpdateDateRange() != null) {
					int i = 0;
					for (Date t : contract.getUpdateDateRange()) {
						if (i == 0) {
							zsql += " and update_date>='" + df.format(t) + "'";
							i++;
						} else {
							zsql += " and update_date<='" + df.format(t) + "'";
						}
					}
				}
			} else {
				if (!contract.getUpdateDate().equals("")) {
					if (contract.getUpdateDate().equals("今日")) {
						zsql += " and update_date>='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayStartTime().getTime())
								+ "'";
						zsql += " and update_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayEndTime().getTime())
								+ "'";
					}
					if (contract.getUpdateDate().equals("本周")) {
						zsql += " and update_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayStartTime().getTime()) + "'";
						zsql += " and update_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayEndTime().getTime()) + "'";
					}
					if (contract.getUpdateDate().equals("本月")) {
						zsql += " and update_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentMonthStartTime().getTime()) + "'";
						zsql += " and update_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthEndTime().getTime())
								+ "'";
					}
					if (contract.getUpdateDate().equals("本季")) {
						zsql += " and update_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterStartTime().getTime()) + "'";
						zsql += " and update_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterEndTime().getTime()) + "'";
					}
					if (contract.getUpdateDate().equals("本年")) {
						zsql += " and update_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentYearStartTime().getTime()) + "'";
						zsql += " and update_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearEndTime().getTime())
								+ "'";
					}
				} else {
					if (contract.getUpdateDateRange() != null) {
						int i = 0;
						for (Date t : contract.getUpdateDateRange()) {
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

			if (contract.getFrom().equals("全部")) {

				sql = "SELECT id,contract_title,customer_id,customer_name,business_opportunity_id,business_opportunity_name,contract_start_date,contract_end_date,total_contract_amount,signing_date,contract_status,contract_no,contract_type,contract_type_name,payment_type,payment_type_name,our_signatory,client_signatory,next_follow_time,contract_category,audit,person_id,person_name,department_id,department_name,remarks,create_id,create_name,create_date,update_id,update_name,update_date,crm_responsible_id,implementation,production_time,output,shipping_time,shipment_volume,settlement_time,settlement_volume,collection_time,collection_amount,contract_unsettled_quantity,unsettled_shipment_quantity,accounts_receivable,collaborator_name,mechanism_id,state FROM  crm_contract  where 1=1 and mechanism_id like ? "
						+ zsql + " order by create_date desc ";
				ps = conn.prepareStatement(sql);
				ps.setString(1, contract.getMechanismId() + "%");
				rs = ps.executeQuery();
				while (rs.next()) {
					contract = new Contract();
					contract.setId(rs.getString(1));
					contract.setContractTitle(rs.getString(2));
					contract.setCustomerId(rs.getString(3));
					contract.setCustomerName(rs.getString(4));
					contract.setBusinessOpportunityId(rs.getString(5));
					contract.setBusinessOpportunityName(rs.getString(6));
					contract.setContractStartDate(rs.getDate(7));
					contract.setContractEndDate(rs.getDate(8));
					contract.setTotalContractAmount(rs.getString(9));
					contract.setSigningDate(rs.getDate(10));
					contract.setContractStatus(rs.getString(11));
					contract.setContractNo(rs.getString(12));
					contract.setContractType(rs.getString(13));
					contract.setContractTypeName(rs.getString(14));
					contract.setPaymentType(rs.getString(15));
					contract.setPaymentTypeName(rs.getString(16));
					contract.setOurSignatory(rs.getString(17));
					contract.setClientSignatory(rs.getString(18));
					contract.setNextFollowTime(rs.getDate(19));
					contract.setContractCategory(rs.getString(20));
					contract.setAudit(rs.getString(21));
					contract.setPersonId(rs.getString(22));
					contract.setPersonName(rs.getString(23));
					contract.setDepartmentId(rs.getString(24));
					contract.setDepartmentName(rs.getString(25));
					contract.setRemarks(rs.getString(26));
					contract.setCreateId(rs.getString(27));
					contract.setCreateName(rs.getString(28));
					contract.setCreateDate(rs.getString(29));
					contract.setUpdateId(rs.getString(30));
					contract.setUpdateName(rs.getString(31));
					contract.setUpdateDate(rs.getString(32));
					contract.setCrmResponsibleId(rs.getString(33));
					contract.setImplementation(rs.getString(34));
					contract.setProductionTime(rs.getString(35));
					contract.setOutput(rs.getString(36));
					contract.setShippingTime(rs.getString(37));
					contract.setShipmentVolume(rs.getString(38));
					contract.setSettlementTime(rs.getString(39));
					contract.setSettlementVolume(rs.getString(40));
					contract.setCollectionTime(rs.getString(41));
					contract.setCollectionAmount(rs.getString(42));
					contract.setContractUnsettledQuantity(rs.getString(43));
					contract.setUnsettledShipmentQuantity(rs.getString(44));
					contract.setAccountsReceivable(rs.getString(45));
					contract.setCollaboratorName(rs.getString(46));
					contract.setMechanismId(rs.getString(47));
					contract.setState(rs.getString(48));
					list.add(contract);
				}
			} else {
				if (contract.getFrom().equals("本部")) {

					String[] xh = contract.getDepartmentId().split(",");
					for (String bmid : xh) {
						if (!bmid.equals("")) {
							sql = "SELECT id,contract_title,customer_id,customer_name,business_opportunity_id,business_opportunity_name,contract_start_date,contract_end_date,total_contract_amount,signing_date,contract_status,contract_no,contract_type,contract_type_name,payment_type,payment_type_name,our_signatory,client_signatory,next_follow_time,contract_category,audit,person_id,person_name,department_id,department_name,remarks,create_id,create_name,create_date,update_id,update_name,update_date,crm_responsible_id,implementation,production_time,output,shipping_time,shipment_volume,settlement_time,settlement_volume,collection_time,collection_amount,contract_unsettled_quantity,unsettled_shipment_quantity,accounts_receivable,collaborator_name,mechanism_id,state FROM  crm_contract  where 1=1 and mechanism_id like ? and department_id like ? "
									+ zsql + " order by create_date desc ";

							ps = conn.prepareStatement(sql);
							ps.setString(1, contract.getMechanismId() + "%");
							ps.setString(2, bmid + "%");

							rs = ps.executeQuery();
							while (rs.next()) {
								contract = new Contract();
								contract.setId(rs.getString(1));
								contract.setContractTitle(rs.getString(2));
								contract.setCustomerId(rs.getString(3));
								contract.setCustomerName(rs.getString(4));
								contract.setBusinessOpportunityId(rs.getString(5));
								contract.setBusinessOpportunityName(rs.getString(6));
								contract.setContractStartDate(rs.getDate(7));
								contract.setContractEndDate(rs.getDate(8));
								contract.setTotalContractAmount(rs.getString(9));
								contract.setSigningDate(rs.getDate(10));
								contract.setContractStatus(rs.getString(11));
								contract.setContractNo(rs.getString(12));
								contract.setContractType(rs.getString(13));
								contract.setContractTypeName(rs.getString(14));
								contract.setPaymentType(rs.getString(15));
								contract.setPaymentTypeName(rs.getString(16));
								contract.setOurSignatory(rs.getString(17));
								contract.setClientSignatory(rs.getString(18));
								contract.setNextFollowTime(rs.getDate(19));
								contract.setContractCategory(rs.getString(20));
								contract.setAudit(rs.getString(21));
								contract.setPersonId(rs.getString(22));
								contract.setPersonName(rs.getString(23));
								contract.setDepartmentId(rs.getString(24));
								contract.setDepartmentName(rs.getString(25));
								contract.setRemarks(rs.getString(26));
								contract.setCreateId(rs.getString(27));
								contract.setCreateName(rs.getString(28));
								contract.setCreateDate(rs.getString(29));
								contract.setUpdateId(rs.getString(30));
								contract.setUpdateName(rs.getString(31));
								contract.setUpdateDate(rs.getString(32));
								contract.setCrmResponsibleId(rs.getString(33));
								contract.setImplementation(rs.getString(34));
								contract.setProductionTime(rs.getString(35));
								contract.setOutput(rs.getString(36));
								contract.setShippingTime(rs.getString(37));
								contract.setShipmentVolume(rs.getString(38));
								contract.setSettlementTime(rs.getString(39));
								contract.setSettlementVolume(rs.getString(40));
								contract.setCollectionTime(rs.getString(41));
								contract.setCollectionAmount(rs.getString(42));
								contract.setContractUnsettledQuantity(rs.getString(43));
								contract.setUnsettledShipmentQuantity(rs.getString(44));
								contract.setAccountsReceivable(rs.getString(45));
								contract.setCollaboratorName(rs.getString(46));
								contract.setMechanismId(rs.getString(47));
								contract.setState(rs.getString(48));
								list.add(contract);
							}
						}
					}
					sql = "SELECT id,contract_title,customer_id,customer_name,business_opportunity_id,business_opportunity_name,contract_start_date,contract_end_date,total_contract_amount,signing_date,contract_status,contract_no,contract_type,contract_type_name,payment_type,payment_type_name,our_signatory,client_signatory,next_follow_time,contract_category,audit,person_id,person_name,department_id,department_name,remarks,create_id,create_name,create_date,update_id,update_name,update_date,crm_responsible_id,implementation,production_time,output,shipping_time,shipment_volume,settlement_time,settlement_volume,collection_time,collection_amount,contract_unsettled_quantity,unsettled_shipment_quantity,accounts_receivable,collaborator_name,mechanism_id,state FROM  crm_contract  where 1=1 and mechanism_id like ? and create_id=? "
							+ zsql + " order by create_date desc ";

					ps = conn.prepareStatement(sql);
					ps.setString(1, contract.getMechanismId() + "%");
					ps.setString(2, username);

					rs = ps.executeQuery();
					while (rs.next()) {
						contract = new Contract();
						contract.setId(rs.getString(1));
						contract.setContractTitle(rs.getString(2));
						contract.setCustomerId(rs.getString(3));
						contract.setCustomerName(rs.getString(4));
						contract.setBusinessOpportunityId(rs.getString(5));
						contract.setBusinessOpportunityName(rs.getString(6));
						contract.setContractStartDate(rs.getDate(7));
						contract.setContractEndDate(rs.getDate(8));
						contract.setTotalContractAmount(rs.getString(9));
						contract.setSigningDate(rs.getDate(10));
						contract.setContractStatus(rs.getString(11));
						contract.setContractNo(rs.getString(12));
						contract.setContractType(rs.getString(13));
						contract.setContractTypeName(rs.getString(14));
						contract.setPaymentType(rs.getString(15));
						contract.setPaymentTypeName(rs.getString(16));
						contract.setOurSignatory(rs.getString(17));
						contract.setClientSignatory(rs.getString(18));
						contract.setNextFollowTime(rs.getDate(19));
						contract.setContractCategory(rs.getString(20));
						contract.setAudit(rs.getString(21));
						contract.setPersonId(rs.getString(22));
						contract.setPersonName(rs.getString(23));
						contract.setDepartmentId(rs.getString(24));
						contract.setDepartmentName(rs.getString(25));
						contract.setRemarks(rs.getString(26));
						contract.setCreateId(rs.getString(27));
						contract.setCreateName(rs.getString(28));
						contract.setCreateDate(rs.getString(29));
						contract.setUpdateId(rs.getString(30));
						contract.setUpdateName(rs.getString(31));
						contract.setUpdateDate(rs.getString(32));
						contract.setCrmResponsibleId(rs.getString(33));
						contract.setImplementation(rs.getString(34));
						contract.setProductionTime(rs.getString(35));
						contract.setOutput(rs.getString(36));
						contract.setShippingTime(rs.getString(37));
						contract.setShipmentVolume(rs.getString(38));
						contract.setSettlementTime(rs.getString(39));
						contract.setSettlementVolume(rs.getString(40));
						contract.setCollectionTime(rs.getString(41));
						contract.setCollectionAmount(rs.getString(42));
						contract.setContractUnsettledQuantity(rs.getString(43));
						contract.setUnsettledShipmentQuantity(rs.getString(44));
						contract.setAccountsReceivable(rs.getString(45));
						contract.setCollaboratorName(rs.getString(46));
						contract.setMechanismId(rs.getString(47));
						contract.setState(rs.getString(48));
						list.add(contract);
					}

					for (int i = 0; i < list.size() - 1; i++) {
						for (int j = i + 1; j < list.size(); j++) {
							if (list.get(i).equals(list.get(j))) {
								list.remove(j);
							}
						}
					}
				} else if (contract.getFrom().equals("未设")) {

					sql = "SELECT id,contract_title,customer_id,customer_name,business_opportunity_id,business_opportunity_name,contract_start_date,contract_end_date,total_contract_amount,signing_date,contract_status,contract_no,contract_type,contract_type_name,payment_type,payment_type_name,our_signatory,client_signatory,next_follow_time,contract_category,audit,person_id,person_name,department_id,department_name,remarks,create_id,create_name,create_date,update_id,update_name,update_date,crm_responsible_id,implementation,production_time,output,shipping_time,shipment_volume,settlement_time,settlement_volume,collection_time,collection_amount,contract_unsettled_quantity,unsettled_shipment_quantity,accounts_receivable,collaborator_name,mechanism_id,state FROM  crm_contract  where 1=1 and mechanism_id like ? and create_id=? "
							+ zsql + " order by create_date desc ";

					ps = conn.prepareStatement(sql);
					ps.setString(1, contract.getMechanismId() + "%");
					ps.setString(2, username);

					rs = ps.executeQuery();
					while (rs.next()) {
						contract = new Contract();
						contract.setId(rs.getString(1));
						contract.setContractTitle(rs.getString(2));
						contract.setCustomerId(rs.getString(3));
						contract.setCustomerName(rs.getString(4));
						contract.setBusinessOpportunityId(rs.getString(5));
						contract.setBusinessOpportunityName(rs.getString(6));
						contract.setContractStartDate(rs.getDate(7));
						contract.setContractEndDate(rs.getDate(8));
						contract.setTotalContractAmount(rs.getString(9));
						contract.setSigningDate(rs.getDate(10));
						contract.setContractStatus(rs.getString(11));
						contract.setContractNo(rs.getString(12));
						contract.setContractType(rs.getString(13));
						contract.setContractTypeName(rs.getString(14));
						contract.setPaymentType(rs.getString(15));
						contract.setPaymentTypeName(rs.getString(16));
						contract.setOurSignatory(rs.getString(17));
						contract.setClientSignatory(rs.getString(18));
						contract.setNextFollowTime(rs.getDate(19));
						contract.setContractCategory(rs.getString(20));
						contract.setAudit(rs.getString(21));
						contract.setPersonId(rs.getString(22));
						contract.setPersonName(rs.getString(23));
						contract.setDepartmentId(rs.getString(24));
						contract.setDepartmentName(rs.getString(25));
						contract.setRemarks(rs.getString(26));
						contract.setCreateId(rs.getString(27));
						contract.setCreateName(rs.getString(28));
						contract.setCreateDate(rs.getString(29));
						contract.setUpdateId(rs.getString(30));
						contract.setUpdateName(rs.getString(31));
						contract.setUpdateDate(rs.getString(32));
						contract.setCrmResponsibleId(rs.getString(33));
						contract.setImplementation(rs.getString(34));
						contract.setProductionTime(rs.getString(35));
						contract.setOutput(rs.getString(36));
						contract.setShippingTime(rs.getString(37));
						contract.setShipmentVolume(rs.getString(38));
						contract.setSettlementTime(rs.getString(39));
						contract.setSettlementVolume(rs.getString(40));
						contract.setCollectionTime(rs.getString(41));
						contract.setCollectionAmount(rs.getString(42));
						contract.setContractUnsettledQuantity(rs.getString(43));
						contract.setUnsettledShipmentQuantity(rs.getString(44));
						contract.setAccountsReceivable(rs.getString(45));
						contract.setCollaboratorName(rs.getString(46));
						contract.setMechanismId(rs.getString(47));
						contract.setState(rs.getString(48));
						list.add(contract);
					}

				} else {

					String[] xh = contract.getDepartmentId().split(",");
					for (String bmid : xh) {
						if (!bmid.equals("")) {
							sql = "SELECT id,contract_title,customer_id,customer_name,business_opportunity_id,business_opportunity_name,contract_start_date,contract_end_date,total_contract_amount,signing_date,contract_status,contract_no,contract_type,contract_type_name,payment_type,payment_type_name,our_signatory,client_signatory,next_follow_time,contract_category,audit,person_id,person_name,department_id,department_name,remarks,create_id,create_name,create_date,update_id,update_name,update_date,crm_responsible_id,implementation,production_time,output,shipping_time,shipment_volume,settlement_time,settlement_volume,collection_time,collection_amount,contract_unsettled_quantity,unsettled_shipment_quantity,accounts_receivable,collaborator_name,mechanism_id,state FROM  crm_contract  where 1=1 and mechanism_id like ? and department_id like ? "
									+ zsql + " order by create_date desc ";

							ps = conn.prepareStatement(sql);
							ps.setString(1, contract.getMechanismId() + "%");
							ps.setString(2, bmid);

							rs = ps.executeQuery();
							while (rs.next()) {
								contract = new Contract();
								contract.setId(rs.getString(1));
								contract.setContractTitle(rs.getString(2));
								contract.setCustomerId(rs.getString(3));
								contract.setCustomerName(rs.getString(4));
								contract.setBusinessOpportunityId(rs.getString(5));
								contract.setBusinessOpportunityName(rs.getString(6));
								contract.setContractStartDate(rs.getDate(7));
								contract.setContractEndDate(rs.getDate(8));
								contract.setTotalContractAmount(rs.getString(9));
								contract.setSigningDate(rs.getDate(10));
								contract.setContractStatus(rs.getString(11));
								contract.setContractNo(rs.getString(12));
								contract.setContractType(rs.getString(13));
								contract.setContractTypeName(rs.getString(14));
								contract.setPaymentType(rs.getString(15));
								contract.setPaymentTypeName(rs.getString(16));
								contract.setOurSignatory(rs.getString(17));
								contract.setClientSignatory(rs.getString(18));
								contract.setNextFollowTime(rs.getDate(19));
								contract.setContractCategory(rs.getString(20));
								contract.setAudit(rs.getString(21));
								contract.setPersonId(rs.getString(22));
								contract.setPersonName(rs.getString(23));
								contract.setDepartmentId(rs.getString(24));
								contract.setDepartmentName(rs.getString(25));
								contract.setRemarks(rs.getString(26));
								contract.setCreateId(rs.getString(27));
								contract.setCreateName(rs.getString(28));
								contract.setCreateDate(rs.getString(29));
								contract.setUpdateId(rs.getString(30));
								contract.setUpdateName(rs.getString(31));
								contract.setUpdateDate(rs.getString(32));
								contract.setCrmResponsibleId(rs.getString(33));
								contract.setImplementation(rs.getString(34));
								contract.setProductionTime(rs.getString(35));
								contract.setOutput(rs.getString(36));
								contract.setShippingTime(rs.getString(37));
								contract.setShipmentVolume(rs.getString(38));
								contract.setSettlementTime(rs.getString(39));
								contract.setSettlementVolume(rs.getString(40));
								contract.setCollectionTime(rs.getString(41));
								contract.setCollectionAmount(rs.getString(42));
								contract.setContractUnsettledQuantity(rs.getString(43));
								contract.setUnsettledShipmentQuantity(rs.getString(44));
								contract.setAccountsReceivable(rs.getString(45));
								contract.setCollaboratorName(rs.getString(46));
								contract.setMechanismId(rs.getString(47));
								contract.setState(rs.getString(48));
								list.add(contract);
							}
						}
					}
					sql = "SELECT id,contract_title,customer_id,customer_name,business_opportunity_id,business_opportunity_name,contract_start_date,contract_end_date,total_contract_amount,signing_date,contract_status,contract_no,contract_type,contract_type_name,payment_type,payment_type_name,our_signatory,client_signatory,next_follow_time,contract_category,audit,person_id,person_name,department_id,department_name,remarks,create_id,create_name,create_date,update_id,update_name,update_date,crm_responsible_id,implementation,production_time,output,shipping_time,shipment_volume,settlement_time,settlement_volume,collection_time,collection_amount,contract_unsettled_quantity,unsettled_shipment_quantity,accounts_receivable,collaborator_name,mechanism_id,state FROM  crm_contract  where 1=1 and mechanism_id like ? and create_id=? "
							+ zsql + " order by create_date desc ";

					ps = conn.prepareStatement(sql);
					ps.setString(1, contract.getMechanismId() + "%");
					ps.setString(2, username);

					rs = ps.executeQuery();
					while (rs.next()) {
						contract = new Contract();
						contract.setId(rs.getString(1));
						contract.setContractTitle(rs.getString(2));
						contract.setCustomerId(rs.getString(3));
						contract.setCustomerName(rs.getString(4));
						contract.setBusinessOpportunityId(rs.getString(5));
						contract.setBusinessOpportunityName(rs.getString(6));
						contract.setContractStartDate(rs.getDate(7));
						contract.setContractEndDate(rs.getDate(8));
						contract.setTotalContractAmount(rs.getString(9));
						contract.setSigningDate(rs.getDate(10));
						contract.setContractStatus(rs.getString(11));
						contract.setContractNo(rs.getString(12));
						contract.setContractType(rs.getString(13));
						contract.setContractTypeName(rs.getString(14));
						contract.setPaymentType(rs.getString(15));
						contract.setPaymentTypeName(rs.getString(16));
						contract.setOurSignatory(rs.getString(17));
						contract.setClientSignatory(rs.getString(18));
						contract.setNextFollowTime(rs.getDate(19));
						contract.setContractCategory(rs.getString(20));
						contract.setAudit(rs.getString(21));
						contract.setPersonId(rs.getString(22));
						contract.setPersonName(rs.getString(23));
						contract.setDepartmentId(rs.getString(24));
						contract.setDepartmentName(rs.getString(25));
						contract.setRemarks(rs.getString(26));
						contract.setCreateId(rs.getString(27));
						contract.setCreateName(rs.getString(28));
						contract.setCreateDate(rs.getString(29));
						contract.setUpdateId(rs.getString(30));
						contract.setUpdateName(rs.getString(31));
						contract.setUpdateDate(rs.getString(32));
						contract.setCrmResponsibleId(rs.getString(33));
						contract.setImplementation(rs.getString(34));
						contract.setProductionTime(rs.getString(35));
						contract.setOutput(rs.getString(36));
						contract.setShippingTime(rs.getString(37));
						contract.setShipmentVolume(rs.getString(38));
						contract.setSettlementTime(rs.getString(39));
						contract.setSettlementVolume(rs.getString(40));
						contract.setCollectionTime(rs.getString(41));
						contract.setCollectionAmount(rs.getString(42));
						contract.setContractUnsettledQuantity(rs.getString(43));
						contract.setUnsettledShipmentQuantity(rs.getString(44));
						contract.setAccountsReceivable(rs.getString(45));
						contract.setCollaboratorName(rs.getString(46));
						contract.setMechanismId(rs.getString(47));
						contract.setState(rs.getString(48));
						list.add(contract);
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
			System.out.println("List<Contract> getItems(Contract contract)" + (t2 - t1) + " ms");
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
	public List<Contract> getItemsOfMy(Contract contract) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Contract> list = new ArrayList<Contract>();
		String username = contract.getCreateName();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String sql = "";

			String zsql = "";
			if (contract.getContractStatuss() != null) {
				if (contract.getContractStatuss().length != 0) {
					zsql += " and (";
					for (int i = 0; i < contract.getContractStatuss().length; i++) {
						zsql += "  contract_status='" + contract.getContractStatuss()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (contract.getTotalContractAmount() != null) {
				if (!contract.getTotalContractAmount().equals("")) {
					zsql += "and total_contract_amount>=" + contract.getTotalContractAmount() + "";
				}
			}
			if (contract.getTotalContractAmounts() != null) {
				if (!contract.getTotalContractAmounts().equals("")) {
					zsql += "and total_contract_amount<=" + contract.getTotalContractAmounts() + "";
				}
			}
			if (contract.getContractStartDates() == null) {
				if (contract.getContractStartDateRange() != null) {
					int i = 0;
					for (Date t : contract.getContractStartDateRange()) {
						if (i == 0) {
							zsql += " and contract_start_date>='" + df.format(t) + "'";
							i++;
						} else {
							zsql += " and contract_start_date<='" + df.format(t) + "'";
						}
					}
				}
			} else {
				if (!contract.getContractStartDates().equals("")) {
					if (contract.getContractStartDates().equals("今日")) {
						zsql += " and contract_start_date>='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayStartTime().getTime())
								+ "'";
						zsql += " and contract_start_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayEndTime().getTime())
								+ "'";
					}
					if (contract.getContractStartDates().equals("本周")) {
						zsql += " and contract_start_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayStartTime().getTime()) + "'";
						zsql += " and contract_start_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayEndTime().getTime()) + "'";
					}
					if (contract.getContractStartDates().equals("本月")) {
						zsql += " and contract_start_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentMonthStartTime().getTime()) + "'";
						zsql += " and contract_start_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthEndTime().getTime())
								+ "'";
					}
					if (contract.getContractStartDates().equals("本季")) {
						zsql += " and contract_start_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterStartTime().getTime()) + "'";
						zsql += " and contract_start_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterEndTime().getTime()) + "'";
					}
					if (contract.getContractStartDates().equals("本年")) {
						zsql += " and contract_start_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentYearStartTime().getTime()) + "'";
						zsql += " and contract_start_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearEndTime().getTime())
								+ "'";
					}
				} else {
					if (contract.getContractStartDateRange() != null) {
						int i = 0;
						for (Date t : contract.getContractStartDateRange()) {
							if (i == 0) {
								zsql += " and contract_start_date>='" + df.format(t) + "'";
								i++;
							} else {
								zsql += " and contract_start_date<='" + df.format(t) + "'";
							}
						}
					}
				}
			}
			if (contract.getContractEndDates() == null) {
				if (contract.getContractEndDateRange() != null) {
					int i = 0;
					for (Date t : contract.getContractEndDateRange()) {
						if (i == 0) {
							zsql += " and contract_end_date>='" + df.format(t) + "'";
							i++;
						} else {
							zsql += " and contract_end_date<='" + df.format(t) + "'";
						}
					}
				}
			} else {
				if (!contract.getContractEndDates().equals("")) {
					if (contract.getContractEndDates().equals("今日")) {
						zsql += " and contract_end_date>='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayStartTime().getTime())
								+ "'";
						zsql += " and contract_end_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayEndTime().getTime())
								+ "'";
					}
					if (contract.getContractEndDates().equals("本周")) {
						zsql += " and contract_end_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayStartTime().getTime()) + "'";
						zsql += " and contract_end_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayEndTime().getTime()) + "'";
					}
					if (contract.getContractEndDates().equals("本月")) {
						zsql += " and contract_end_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentMonthStartTime().getTime()) + "'";
						zsql += " and contract_end_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthEndTime().getTime())
								+ "'";
					}
					if (contract.getContractEndDates().equals("本季")) {
						zsql += " and contract_end_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterStartTime().getTime()) + "'";
						zsql += " and contract_end_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterEndTime().getTime()) + "'";
					}
					if (contract.getContractEndDates().equals("本年")) {
						zsql += " and contract_end_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentYearStartTime().getTime()) + "'";
						zsql += " and contract_end_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearEndTime().getTime())
								+ "'";
					}
				} else {
					if (contract.getContractEndDateRange() != null) {
						int i = 0;
						for (Date t : contract.getContractEndDateRange()) {
							if (i == 0) {
								zsql += " and contract_end_date>='" + df.format(t) + "'";
								i++;
							} else {
								zsql += " and contract_end_date<='" + df.format(t) + "'";
							}
						}
					}
				}
			}
			if (contract.getSigningDates() == null) {
				if (contract.getSigningDateRange() != null) {
					int i = 0;
					for (Date t : contract.getSigningDateRange()) {
						if (i == 0) {
							zsql += " and signing_date>='" + df.format(t) + "'";
							i++;
						} else {
							zsql += " and signing_date<='" + df.format(t) + "'";
						}
					}
				}
			} else {
				if (!contract.getSigningDates().equals("")) {
					if (contract.getSigningDates().equals("今日")) {
						zsql += " and signing_date>='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayStartTime().getTime())
								+ "'";
						zsql += " and signing_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayEndTime().getTime())
								+ "'";
					}
					if (contract.getSigningDates().equals("本周")) {
						zsql += " and signing_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayStartTime().getTime()) + "'";
						zsql += " and signing_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayEndTime().getTime()) + "'";
					}
					if (contract.getSigningDates().equals("本月")) {
						zsql += " and signing_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentMonthStartTime().getTime()) + "'";
						zsql += " and signing_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthEndTime().getTime())
								+ "'";
					}
					if (contract.getSigningDates().equals("本季")) {
						zsql += " and signing_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterStartTime().getTime()) + "'";
						zsql += " and signing_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterEndTime().getTime()) + "'";
					}
					if (contract.getSigningDates().equals("本年")) {
						zsql += " and signing_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentYearStartTime().getTime()) + "'";
						zsql += " and signing_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearEndTime().getTime())
								+ "'";
					}
				} else {
					if (contract.getSigningDateRange() != null) {
						int i = 0;
						for (Date t : contract.getSigningDateRange()) {
							if (i == 0) {
								zsql += " and signing_date>='" + df.format(t) + "'";
								i++;
							} else {
								zsql += " and signing_date<='" + df.format(t) + "'";
							}
						}
					}
				}
			}
			if (contract.getContractTypes() != null) {
				if (contract.getContractTypes().length != 0) {

					zsql += " and (";
					for (int i = 0; i < contract.getContractTypes().length; i++) {
						zsql += "  contract_type='" + contract.getContractTypes()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (contract.getPaymentTypes() != null) {
				if (contract.getPaymentTypes().length != 0) {

					zsql += " and (";
					for (int i = 0; i < contract.getPaymentTypes().length; i++) {
						zsql += "  payment_type='" + contract.getPaymentTypes()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (contract.getCustomerIds() != null) {
				if (contract.getCustomerIds().length != 0) {

					zsql += " and (";
					for (int i = 0; i < contract.getCustomerIds().length; i++) {
						zsql += "  customer_id='" + contract.getCustomerIds()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (contract.getCreateIds() != null) {
				if (contract.getCreateIds().length != 0) {

					zsql += " and (";
					for (int i = 0; i < contract.getCreateIds().length; i++) {
						zsql += "  create_id='" + contract.getCreateIds()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}

			if (contract.getNextFollowTimes() == null) {
				if (contract.getNextFollowTimeDateRange() != null) {
					int i = 0;
					for (Date t : contract.getNextFollowTimeDateRange()) {
						if (i == 0) {
							zsql += " and next_follow_time>='" + df.format(t) + "'";
							i++;
						} else {
							zsql += " and next_follow_time<='" + df.format(t) + "'";
						}
					}
				}
			} else {
				if (!contract.getNextFollowTimes().equals("")) {
					if (contract.getNextFollowTimes().equals("今日")) {
						zsql += " and next_follow_time>='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayStartTime().getTime())
								+ "'";
						zsql += " and next_follow_time<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayEndTime().getTime())
								+ "'";
					}
					if (contract.getNextFollowTimes().equals("本周")) {
						zsql += " and next_follow_time>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayStartTime().getTime()) + "'";
						zsql += " and next_follow_time<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayEndTime().getTime()) + "'";
					}
					if (contract.getNextFollowTimes().equals("本月")) {
						zsql += " and next_follow_time>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentMonthStartTime().getTime()) + "'";
						zsql += " and next_follow_time<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthEndTime().getTime())
								+ "'";
					}
					if (contract.getNextFollowTimes().equals("本季")) {
						zsql += " and next_follow_time>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterStartTime().getTime()) + "'";
						zsql += " and next_follow_time<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterEndTime().getTime()) + "'";
					}
					if (contract.getNextFollowTimes().equals("本年")) {
						zsql += " and next_follow_time>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentYearStartTime().getTime()) + "'";
						zsql += " and next_follow_time<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearEndTime().getTime())
								+ "'";
					}
				} else {
					if (contract.getNextFollowTimeDateRange() != null) {
						int i = 0;
						for (Date t : contract.getNextFollowTimeDateRange()) {
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
			if (contract.getCreateDate() == null) {
				if (contract.getCreateDateRange() != null) {
					int i = 0;
					for (Date t : contract.getCreateDateRange()) {
						if (i == 0) {
							zsql += " and create_date>='" + df.format(t) + "'";
							i++;
						} else {
							zsql += " and create_date<='" + df.format(t) + "'";
						}
					}
				}
			} else {
				if (!contract.getCreateDate().equals("")) {
					if (contract.getCreateDate().equals("今日")) {
						zsql += " and create_date>='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayStartTime().getTime())
								+ "'";
						zsql += " and create_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayEndTime().getTime())
								+ "'";
					}
					if (contract.getCreateDate().equals("本周")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayStartTime().getTime()) + "'";
						zsql += " and create_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayEndTime().getTime()) + "'";
					}
					if (contract.getCreateDate().equals("本月")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentMonthStartTime().getTime()) + "'";
						zsql += " and create_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthEndTime().getTime())
								+ "'";
					}
					if (contract.getCreateDate().equals("本季")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterStartTime().getTime()) + "'";
						zsql += " and create_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterEndTime().getTime()) + "'";
					}
					if (contract.getCreateDate().equals("本年")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentYearStartTime().getTime()) + "'";
						zsql += " and create_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearEndTime().getTime())
								+ "'";
					}
				} else {
					if (contract.getCreateDateRange() != null) {
						int i = 0;
						for (Date t : contract.getCreateDateRange()) {
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

			if (contract.getUpdateDate() == null) {
				if (contract.getUpdateDateRange() != null) {
					int i = 0;
					for (Date t : contract.getUpdateDateRange()) {
						if (i == 0) {
							zsql += " and update_date>='" + df.format(t) + "'";
							i++;
						} else {
							zsql += " and update_date<='" + df.format(t) + "'";
						}
					}
				}
			} else {
				if (!contract.getUpdateDate().equals("")) {
					if (contract.getUpdateDate().equals("今日")) {
						zsql += " and update_date>='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayStartTime().getTime())
								+ "'";
						zsql += " and update_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayEndTime().getTime())
								+ "'";
					}
					if (contract.getUpdateDate().equals("本周")) {
						zsql += " and update_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayStartTime().getTime()) + "'";
						zsql += " and update_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayEndTime().getTime()) + "'";
					}
					if (contract.getUpdateDate().equals("本月")) {
						zsql += " and update_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentMonthStartTime().getTime()) + "'";
						zsql += " and update_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthEndTime().getTime())
								+ "'";
					}
					if (contract.getUpdateDate().equals("本季")) {
						zsql += " and update_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterStartTime().getTime()) + "'";
						zsql += " and update_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterEndTime().getTime()) + "'";
					}
					if (contract.getUpdateDate().equals("本年")) {
						zsql += " and update_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentYearStartTime().getTime()) + "'";
						zsql += " and update_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearEndTime().getTime())
								+ "'";
					}
				} else {
					if (contract.getUpdateDateRange() != null) {
						int i = 0;
						for (Date t : contract.getUpdateDateRange()) {
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
			sql = "SELECT id,contract_title,customer_id,customer_name,business_opportunity_id,business_opportunity_name,contract_start_date,contract_end_date,total_contract_amount,signing_date,contract_status,contract_no,contract_type,contract_type_name,payment_type,payment_type_name,our_signatory,client_signatory,next_follow_time,contract_category,audit,person_id,person_name,department_id,department_name,remarks,create_id,create_name,create_date,update_id,update_name,update_date,crm_responsible_id,implementation,production_time,output,shipping_time,shipment_volume,settlement_time,settlement_volume,collection_time,collection_amount,contract_unsettled_quantity,unsettled_shipment_quantity,accounts_receivable,collaborator_name,mechanism_id,state FROM  crm_contract  where 1=1 and mechanism_id like ? and create_id=? "
					+ zsql + " order by create_date desc ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, contract.getMechanismId() + "%");
			ps.setString(2, contract.getCreateName());
			rs = ps.executeQuery();
			while (rs.next()) {
				contract = new Contract();
				contract.setId(rs.getString(1));
				contract.setContractTitle(rs.getString(2));
				contract.setCustomerId(rs.getString(3));
				contract.setCustomerName(rs.getString(4));
				contract.setBusinessOpportunityId(rs.getString(5));
				contract.setBusinessOpportunityName(rs.getString(6));
				contract.setContractStartDate(rs.getDate(7));
				contract.setContractEndDate(rs.getDate(8));
				contract.setTotalContractAmount(rs.getString(9));
				contract.setSigningDate(rs.getDate(10));
				contract.setContractStatus(rs.getString(11));
				contract.setContractNo(rs.getString(12));
				contract.setContractType(rs.getString(13));
				contract.setContractTypeName(rs.getString(14));
				contract.setPaymentType(rs.getString(15));
				contract.setPaymentTypeName(rs.getString(16));
				contract.setOurSignatory(rs.getString(17));
				contract.setClientSignatory(rs.getString(18));
				contract.setNextFollowTime(rs.getDate(19));
				contract.setContractCategory(rs.getString(20));
				contract.setAudit(rs.getString(21));
				contract.setPersonId(rs.getString(22));
				contract.setPersonName(rs.getString(23));
				contract.setDepartmentId(rs.getString(24));
				contract.setDepartmentName(rs.getString(25));
				contract.setRemarks(rs.getString(26));
				contract.setCreateId(rs.getString(27));
				contract.setCreateName(rs.getString(28));
				contract.setCreateDate(rs.getString(29));
				contract.setUpdateId(rs.getString(30));
				contract.setUpdateName(rs.getString(31));
				contract.setUpdateDate(rs.getString(32));
				contract.setCrmResponsibleId(rs.getString(33));
				contract.setImplementation(rs.getString(34));
				contract.setProductionTime(rs.getString(35));
				contract.setOutput(rs.getString(36));
				contract.setShippingTime(rs.getString(37));
				contract.setShipmentVolume(rs.getString(38));
				contract.setSettlementTime(rs.getString(39));
				contract.setSettlementVolume(rs.getString(40));
				contract.setCollectionTime(rs.getString(41));
				contract.setCollectionAmount(rs.getString(42));
				contract.setContractUnsettledQuantity(rs.getString(43));
				contract.setUnsettledShipmentQuantity(rs.getString(44));
				contract.setAccountsReceivable(rs.getString(45));
				contract.setCollaboratorName(rs.getString(46));
				contract.setMechanismId(rs.getString(47));
				contract.setState(rs.getString(48));
				list.add(contract);
			}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("List<Contract> getItems(Contract contract)" + (t2 - t1) + " ms");
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
	public Contract edit(Contract contract) {
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
		List<Cost> costList = new ArrayList<Cost>();
		List<Contract> list = new ArrayList<Contract>();
		List<CollectionDetail> collectionDetailList = new ArrayList<CollectionDetail>();
		List<Invoicing> collectionList = new ArrayList<Invoicing>();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String sql = "SELECT id,contract_title,customer_id,customer_name,business_opportunity_id,business_opportunity_name,contract_start_date,contract_end_date,total_contract_amount,signing_date,contract_status,contract_no,contract_type,contract_type_name,payment_type,payment_type_name,our_signatory,client_signatory,next_follow_time,contract_category,audit,person_id,person_name,department_id,department_name,remarks,create_id,create_name,create_date,update_id,update_name,update_date,crm_responsible_id,implementation,production_time,output,shipping_time,shipment_volume,settlement_time,settlement_volume,collection_time,collection_amount,contract_unsettled_quantity,unsettled_shipment_quantity,accounts_receivable,collaborator_name,mechanism_id,state,customer_no FROM  crm_contract    where 1=1 and id=? order by create_date desc ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, contract.getId());
			rs = ps.executeQuery();
			while (rs.next()) {
				contract = new Contract();
				contract.setId(rs.getString(1));
				contract.setContractTitle(rs.getString(2));
				contract.setCustomerId(rs.getString(3) + "!_" + rs.getString(4) + "!_" + rs.getString(49));
				contract.setCustomerName(rs.getString(4));
				contract.setBusinessOpportunityId(rs.getString(5) + "!_" + rs.getString(6));
				contract.setBusinessOpportunityName(rs.getString(6));
				contract.setContractStartDate(rs.getDate(7));
				contract.setContractEndDate(rs.getDate(8));
				contract.setTotalContractAmount(rs.getString(9));
				contract.setSigningDate(rs.getDate(10));
				contract.setContractStatus(rs.getString(11));
				contract.setContractNo(rs.getString(12));
				contract.setContractType(rs.getString(13) + "!_" + rs.getString(14));
				contract.setContractTypeName(rs.getString(14));
				contract.setPaymentType(rs.getString(15) + "!_" + rs.getString(16));
				contract.setPaymentTypeName(rs.getString(16));
				contract.setOurSignatory(rs.getString(17));
				contract.setClientSignatory(rs.getString(18));
				contract.setNextFollowTime(rs.getDate(19));
				contract.setContractCategory(rs.getString(20));
				contract.setAudit(rs.getString(21));
				contract.setPersonId(rs.getString(22) + "!_" + rs.getString(23));
				contract.setPersonName(rs.getString(23));
				contract.setDepartmentId(rs.getString(24) + "!_" + rs.getString(25));
				contract.setDepartmentName(rs.getString(25));
				contract.setRemarks(rs.getString(26));
				contract.setCreateId(rs.getString(27));
				contract.setCreateName(rs.getString(28));
				contract.setCreateDate(rs.getString(29));
				contract.setUpdateId(rs.getString(30));
				contract.setUpdateName(rs.getString(31));
				contract.setUpdateDate(rs.getString(32));
				contract.setCrmResponsibleId(rs.getString(33));
				contract.setImplementation(rs.getString(34));
				contract.setProductionTime(rs.getString(35));
				contract.setOutput(rs.getString(36));
				contract.setShippingTime(rs.getString(37));
				contract.setShipmentVolume(rs.getString(38));
				contract.setSettlementTime(rs.getString(39));
				contract.setSettlementVolume(rs.getString(40));
				contract.setCollectionTime(rs.getString(41));
				contract.setCollectionAmount(rs.getString(42));
				contract.setContractUnsettledQuantity(rs.getString(43));
				contract.setUnsettledShipmentQuantity(rs.getString(44));
				contract.setAccountsReceivable(rs.getString(45));
				contract.setCollaboratorName(rs.getString(46));
				contract.setMechanismId(rs.getString(47));
				contract.setState(rs.getString(48));
			}

			sql = "SELECT id,number,crm_payment_collection_plan_id,collection_date,collection_proportion,collection_amount,remark,ys4,ys5,ys6,create_id,create_name,create_date,update_id,update_name,update_date,collection_id,mechanism_id,contract_name,customer_name FROM  crm_payment_collection_plan_subordinate  where 1=1 and mechanism_id like ? and contract_id=? order by create_date desc ";
			ps1 = conn.prepareStatement(sql);
			ps1.setString(1, contract.getMechanismId() + "%");
			ps1.setString(2, contract.getId());
			rs1 = ps1.executeQuery();
			while (rs1.next()) {
				CollectionDetail collectionDetail = new CollectionDetail();
				collectionDetail.setId(rs1.getString(1));
				collectionDetail.setNumber(rs1.getString(2));
				collectionDetail.setCrmPaymentCollectionPlanId(rs1.getString(3));
				collectionDetail.setCollectionDate(rs1.getDate(4));
				collectionDetail.setDetailDate(rs1.getString(4));
				collectionDetail.setCollectionProportion(rs1.getString(5));
				collectionDetail.setCollectionAmount(rs1.getString(6));
				collectionDetail.setRemark(rs1.getString(7));
				collectionDetail.setYs4(rs1.getString(8));
				collectionDetail.setYs5(rs1.getString(9));
				collectionDetail.setYs6(rs1.getString(10));
				collectionDetail.setCreateId(rs1.getString(11));
				collectionDetail.setCreateName(rs1.getString(12));
				collectionDetail.setCreateDate(rs1.getString(13));
				collectionDetail.setUpdateId(rs1.getString(14));
				collectionDetail.setUpdateName(rs1.getString(15));
				collectionDetail.setUpdateDate(rs1.getString(16));
				collectionDetail.setCollectionId(rs1.getString(17));
				collectionDetail.setMechanismId(rs1.getString(18));
				collectionDetail.setContractName(rs1.getString(19));
				collectionDetail.setCustomerName(rs1.getString(20));
				collectionDetailList.add(collectionDetail);
			}
			contract.setCollectionmxList(collectionDetailList);

			sql = "SELECT invoicing_date,invoicing_content,invoicing_amount,customer_id,customer_name,contract_id,contract_name,invoice_type,invoicing_num,contract_leader,remark,create_id,create_name,create_date,update_id,update_name,update_date,sys_invoicing_type_id,id FROM  crm_invoicing  where 1=1   and mechanism_id like ? and contract_id=? order by create_date desc ";
			ps2 = conn.prepareStatement(sql);
			ps2.setString(1, contract.getMechanismId() + "%");
			ps2.setString(2, contract.getId());
			rs2 = ps2.executeQuery();
			while (rs2.next()) {
				Invoicing invoicing = new Invoicing();
				invoicing.setInvoicingDate(rs2.getDate(1));
				invoicing.setInvoicingContent(rs2.getString(2));
				invoicing.setInvoicingAmount(rs2.getBigDecimal(3));
				invoicing.setCustomerId(rs2.getString(4));
				invoicing.setCustomerName(rs2.getString(5));
				invoicing.setContractId(rs2.getString(6));
				invoicing.setContractName(rs2.getString(7));
				invoicing.setInvoiceType(rs2.getString(8));
				invoicing.setInvoicingNum(rs2.getString(9));
				invoicing.setContractLeader(rs2.getString(10));
				invoicing.setRemark(rs2.getString(11));
				invoicing.setCreateId(rs2.getString(12));
				invoicing.setCreateName(rs2.getString(13));
				invoicing.setCreateDate(rs2.getString(14));
				invoicing.setUpdateId(rs2.getString(15));
				invoicing.setUpdateName(rs2.getString(16));
				invoicing.setUpdateDate(rs2.getString(17));
				invoicing.setSysInvoicingTypeId(rs2.getString(18));
				invoicing.setId(rs2.getString(19));
				collectionList.add(invoicing);
			}
			contract.setCollectionList(collectionList);
			sql = "SELECT id,expense_type,expense_type_name,cost_description,expense_amount,time_of_occurrence,customer_id,customer_name,contacts_id,contacts_name,business_id,business_name,contract_id,contract_name,follow_up_record,follow_up_record_name,visit_sign_in,enclosure,person_id,person_name,create_id,create_name,create_date,update_id,update_name,update_date,mechanism_id FROM  crm_cost    where 1=1 and mechanism_id=? and contract_id=? order by create_date desc ";
			ps3 = conn.prepareStatement(sql);
			ps3.setString(1, contract.getMechanismId());
			ps3.setString(2, contract.getId());
			rs3 = ps3.executeQuery();
			while (rs3.next()) {
				Cost cost = new Cost();
				cost.setId(rs3.getString(1));
				cost.setExpenseType(rs3.getString(2));
				cost.setExpenseTypeName(rs3.getString(3));
				cost.setCostDescription(rs3.getString(4));
				cost.setExpenseAmount(rs3.getString(5));
				cost.setTimeOfOccurrence(rs3.getDate(6));
				cost.setCustomerId(rs3.getString(7));
				cost.setCustomerName(rs3.getString(8));
				cost.setContactsId(rs3.getString(9));
				cost.setContactsName(rs3.getString(10));
				cost.setBusinessId(rs3.getString(11));
				cost.setBusinessName(rs3.getString(12));
				cost.setContractId(rs3.getString(13));
				cost.setContractName(rs3.getString(14));
				cost.setFollowUpRecord(rs3.getString(15));
				cost.setFollowUpRecordName(rs3.getString(16));
				cost.setVisitSignIn(rs3.getString(17));
				cost.setEnclosure(rs3.getString(18));
				cost.setPersonId(rs3.getString(19));
				cost.setPersonName(rs3.getString(20));
				cost.setCreateId(rs3.getString(21));
				cost.setCreateName(rs3.getString(22));
				cost.setCreateDate(rs3.getString(23));
				cost.setUpdateId(rs3.getString(24));
				cost.setUpdateName(rs3.getString(25));
				cost.setUpdateDate(rs3.getString(26));
				cost.setMechanismId(rs3.getString(27));
				costList.add(cost);
			}
			contract.setCostList(costList);
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("List<Contract> getItems(Contract contract)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
			ResourceManager.close(rs);
			ResourceManager.close(rs1);
			ResourceManager.close(rs2);
			ResourceManager.close(rs3);
			ResourceManager.close(ps);
			ResourceManager.close(ps1);
			ResourceManager.close(ps2);
			ResourceManager.close(ps3);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		} finally {
			ResourceManager.close(rs);
			ResourceManager.close(rs1);
			ResourceManager.close(rs2);
			ResourceManager.close(rs3);
			ResourceManager.close(ps);
			ResourceManager.close(ps1);
			ResourceManager.close(ps2);
			ResourceManager.close(ps3);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		}
		return contract;
	}

	@Override
	public int saveContract(Contract contract) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String icontractSQL = "INSERT INTO crm_contract(id,contract_title,customer_id,customer_name,business_opportunity_id,business_opportunity_name,contract_start_date,contract_end_date,total_contract_amount,signing_date,contract_status,contract_no,contract_type,contract_type_name,payment_type,payment_type_name,our_signatory,client_signatory,next_follow_time,contract_category,audit,person_id,person_name,department_id,department_name,remarks,create_id,create_name,create_date,update_id,update_name,update_date,crm_responsible_id,implementation,production_time,output,shipping_time,shipment_volume,settlement_time,settlement_volume,collection_time,collection_amount,contract_unsettled_quantity,unsettled_shipment_quantity,accounts_receivable,collaborator_name,mechanism_id,state,customer_no)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(icontractSQL);

			ps.setString(1, contract.getId());
			ps.setString(2, contract.getContractTitle());
			if (contract.getCustomerId() == null) {
				ps.setString(3, "");
				ps.setString(4, "");
				ps.setString(49, null);
			} else {
				if (contract.getCustomerId().equals("")) {
					ps.setString(3, "");
					ps.setString(4, "");
					ps.setString(49, null);
				} else {
					String[] contractlx = contract.getCustomerId().split("!_");
					ps.setString(3, contractlx[0]);
					ps.setString(4, contractlx[1]);
					ps.setString(49, contractlx[2]);
				}
			}
			if (contract.getBusinessOpportunityId() == null) {
				ps.setString(5, "");
				ps.setString(6, "");
			} else {
				if (contract.getBusinessOpportunityId().equals("")) {
					ps.setString(5, "");
					ps.setString(6, "");
				} else {
					String[] contractlx = contract.getBusinessOpportunityId().split("!_");
					ps.setString(5, contractlx[0]);
					ps.setString(6, contractlx[1]);
				}
			}
			ps.setString(7, df.format(contract.getContractStartDate()));
			ps.setString(8, df.format(contract.getContractEndDate()));
			ps.setString(9, contract.getTotalContractAmount());
			ps.setString(10, df.format(contract.getSigningDate()));
			ps.setString(11, contract.getContractStatus());
			ps.setString(12, contract.getContractNo());
			if (contract.getContractType() == null) {
				ps.setString(13, "");
				ps.setString(14, "");
			} else {
				if (contract.getContractType().equals("")) {
					ps.setString(13, "");
					ps.setString(14, "");
				} else {
					String[] contractlx = contract.getContractType().split("!_");
					ps.setString(13, contractlx[0]);
					ps.setString(14, contractlx[1]);
				}
			}
			if (contract.getPaymentType() == null) {
				ps.setString(15, "");
				ps.setString(16, "");
			} else {
				if (contract.getPaymentType().equals("")) {
					ps.setString(15, "");
					ps.setString(16, "");
				} else {
					String[] contractlx = contract.getPaymentType().split("!_");
					ps.setString(15, contractlx[0]);
					ps.setString(16, contractlx[1]);
				}
			}
			ps.setString(17, contract.getOurSignatory());
			ps.setString(18, contract.getClientSignatory());
			ps.setString(19, df.format(contract.getNextFollowTime()));
			ps.setString(20, contract.getContractCategory());
			ps.setString(21, contract.getAudit());
			if (contract.getPersonId() == null) {
				ps.setString(22, "");
				ps.setString(23, "");
			} else {
				if (contract.getPersonId().equals("")) {
					ps.setString(22, "");
					ps.setString(23, "");
				} else {
					String[] contractlx = contract.getPersonId().split("!_");
					ps.setString(22, contractlx[0]);
					ps.setString(23, contractlx[1]);
				}
			}
			if (contract.getDepartmentId() == null) {
				ps.setString(24, "");
				ps.setString(25, "");
			} else {
				if (contract.getDepartmentId().equals("")) {
					ps.setString(24, "");
					ps.setString(25, "");
				} else {
					String[] contractlx = contract.getDepartmentId().split("!_");
					ps.setString(24, contractlx[0]);
					ps.setString(25, contractlx[1]);
				}
			}
			ps.setString(26, contract.getRemarks());
			ps.setString(27, contract.getCreateId());
			ps.setString(28, contract.getCreateName());
			ps.setString(29, contract.getCreateDate());
			ps.setString(30, contract.getUpdateId());
			ps.setString(31, contract.getUpdateName());
			ps.setString(32, contract.getUpdateDate());
			ps.setString(33, contract.getCrmResponsibleId());
			ps.setString(34, contract.getImplementation());
			ps.setString(35, contract.getProductionTime());
			ps.setString(36, contract.getOutput());
			ps.setString(37, contract.getShippingTime());
			ps.setString(38, contract.getShipmentVolume());
			ps.setString(39, contract.getSettlementTime());
			ps.setString(40, contract.getSettlementVolume());
			ps.setString(41, contract.getCollectionTime());
			ps.setString(42, contract.getCollectionAmount());
			ps.setString(43, contract.getContractUnsettledQuantity());
			ps.setString(44, contract.getUnsettledShipmentQuantity());
			ps.setString(45, contract.getAccountsReceivable());
			ps.setString(46, contract.getCollaboratorName());
			ps.setString(47, contract.getMechanismId());
			ps.setString(48, contract.getState());
			rv = ps.executeUpdate();
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("int saveContract(Contract contract)" + (t2 - t1) + " ms");
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
	public int update(Contract contract) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String ucontractSQL = "update crm_contract set state=?,contract_title=?,customer_id=?,customer_name=?,business_opportunity_id=?,business_opportunity_name=?,contract_start_date=?,contract_end_date=?,total_contract_amount=?,signing_date=?,contract_status=?,contract_no=?,contract_type=?,contract_type_name=?,payment_type=?,payment_type_name=?,our_signatory=?,client_signatory=?,next_follow_time=?,contract_category=?,audit=?,person_id=?,person_name=?,department_id=?,department_name=?,remarks=?,create_id=?,create_name=?,create_date=?,update_id=?,update_name=?,update_date=?,crm_responsible_id=?,implementation=?,production_time=?,output=?,shipping_time=?,shipment_volume=?,settlement_time=?,settlement_volume=?,collection_time=?,collection_amount=?,contract_unsettled_quantity=?,unsettled_shipment_quantity=?,accounts_receivable=?,collaborator_name=?,mechanism_id=?,customer_no=? where 1=1 and id=? ";
			ps = conn.prepareStatement(ucontractSQL);
			ps.setString(1, contract.getState());
			ps.setString(2, contract.getContractTitle());
			if (contract.getCustomerId() == null) {
				ps.setString(3, "");
				ps.setString(4, "");
				ps.setString(48, null);
			} else {
				if (contract.getCustomerId().equals("")) {
					ps.setString(3, "");
					ps.setString(4, "");
					ps.setString(48, null);
				} else {
					String[] contractlx = contract.getCustomerId().split("!_");
					ps.setString(3, contractlx[0]);
					ps.setString(4, contractlx[1]);
					ps.setString(48, contractlx[2]);
				}
			}
			if (contract.getBusinessOpportunityId() == null) {
				ps.setString(5, "");
				ps.setString(6, "");
			} else {
				if (contract.getBusinessOpportunityId().equals("")) {
					ps.setString(5, "");
					ps.setString(6, "");
				} else {
					String[] contractlx = contract.getBusinessOpportunityId().split("!_");
					ps.setString(5, contractlx[0]);
					ps.setString(6, contractlx[1]);
				}
			}
			ps.setString(7, df.format(contract.getContractStartDate()));
			ps.setString(8, df.format(contract.getContractEndDate()));
			ps.setString(9, contract.getTotalContractAmount());
			ps.setString(10, df.format(contract.getSigningDate()));
			ps.setString(11, contract.getContractStatus());
			ps.setString(12, contract.getContractNo());
			if (contract.getContractType() == null) {
				ps.setString(13, "");
				ps.setString(14, "");
			} else {
				if (contract.getContractType().equals("")) {
					ps.setString(13, "");
					ps.setString(14, "");
				} else {
					String[] contractlx = contract.getContractType().split("!_");
					ps.setString(13, contractlx[0]);
					ps.setString(14, contractlx[1]);
				}
			}
			if (contract.getPaymentType() == null) {
				ps.setString(15, "");
				ps.setString(16, "");
			} else {
				if (contract.getPaymentType().equals("")) {
					ps.setString(15, "");
					ps.setString(16, "");
				} else {
					String[] contractlx = contract.getPaymentType().split("!_");
					ps.setString(15, contractlx[0]);
					ps.setString(16, contractlx[1]);
				}
			}
			ps.setString(17, contract.getOurSignatory());
			ps.setString(18, contract.getClientSignatory());
			ps.setString(19, df.format(contract.getNextFollowTime()));
			ps.setString(20, contract.getContractCategory());
			ps.setString(21, contract.getAudit());
			if (contract.getPersonId() == null) {
				ps.setString(22, "");
				ps.setString(23, "");
			} else {
				if (contract.getPersonId().equals("")) {
					ps.setString(22, "");
					ps.setString(23, "");
				} else {
					String[] contractlx = contract.getPersonId().split("!_");
					ps.setString(22, contractlx[0]);
					ps.setString(23, contractlx[1]);
				}
			}
			if (contract.getDepartmentId() == null) {
				ps.setString(24, "");
				ps.setString(25, "");
			} else {
				if (contract.getDepartmentId().equals("")) {
					ps.setString(24, "");
					ps.setString(25, "");
				} else {
					String[] contractlx = contract.getDepartmentId().split("!_");
					ps.setString(24, contractlx[0]);
					ps.setString(25, contractlx[1]);
				}
			}
			ps.setString(26, contract.getRemarks());
			ps.setString(27, contract.getCreateId());
			ps.setString(28, contract.getCreateName());
			ps.setString(29, contract.getCreateDate());
			ps.setString(30, contract.getUpdateId());
			ps.setString(31, contract.getUpdateName());
			ps.setString(32, contract.getUpdateDate());
			ps.setString(33, contract.getCrmResponsibleId());
			ps.setString(34, contract.getImplementation());
			ps.setString(35, contract.getProductionTime());
			ps.setString(36, contract.getOutput());
			ps.setString(37, contract.getShippingTime());
			ps.setString(38, contract.getShipmentVolume());
			ps.setString(39, contract.getSettlementTime());
			ps.setString(40, contract.getSettlementVolume());
			ps.setString(41, contract.getCollectionTime());
			ps.setString(42, contract.getCollectionAmount());
			ps.setString(43, contract.getContractUnsettledQuantity());
			ps.setString(44, contract.getUnsettledShipmentQuantity());
			ps.setString(45, contract.getAccountsReceivable());
			ps.setString(46, contract.getCollaboratorName());
			ps.setString(47, contract.getMechanismId());
			ps.setString(49, contract.getId());
			rv = ps.executeUpdate();
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("int updateContract(contract contract)" + (t2 - t1) + " ms");
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