package dafengqi.yunxiang.webapp.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import dafengqi.yunxiang.model.Dict;
import dafengqi.yunxiang.model.Role;
import dafengqi.yunxiang.service.DictManager;
import dafengqi.yunxiang.util.JsfUtil;

@ManagedBean(name = "dictController")
@SessionScoped
public class DictController extends BasePage implements Serializable {

	private static final long serialVersionUID = 6498739293085202452L;

	private List<Dict> items = null;//列表list

	private List<Role> modules;
	private DictManager dictManager;
	private List<?> customerCategory = new ArrayList();
	private List<?> testing_type = new ArrayList();
	private List<?> execution_type = new ArrayList();
	private List<?> priority = new ArrayList();
	private List<?> test_case_status = new ArrayList();
	private List<?> defect_priority = new ArrayList();
	private List<?> defect_type = new ArrayList();
	private List<?> severity = new ArrayList();
	private List<?> defect_status = new ArrayList();
	private List<?> project_type = new ArrayList();
	private List<?> requirement_type = new ArrayList();
	private List<?> requirement_status = new ArrayList();
	private List<?> os = new ArrayList();
	private List<?> device = new ArrayList();
	private List<?> brower = new ArrayList();
	private List<?> project_status = new ArrayList();
	private List<?> build_status = new ArrayList();
	private List<?> mechanism_type = new ArrayList();
	private List<?> contract_type = new ArrayList();
	private List<?> payment_type = new ArrayList();
	private List<?> follow_status = new ArrayList();
	private List<?> region = new ArrayList();
	private List<?> expense_type = new ArrayList();
	private List<?> brand = new ArrayList();
	private List<?> productType = new ArrayList();
	private List<?> clue_follow_status = new ArrayList();
	private List<?> product_name = new ArrayList();
	private List<?> valuation_unit = new ArrayList();
	private List<?> metering_unit = new ArrayList();
	private List<?> businessopportunities_unit = new ArrayList();
	private List<?> clue_source = new ArrayList();
	private List<?> customer_from = new ArrayList();
	private List<?> business_opportunity_source = new ArrayList();
	private List<?> sales_stage = new ArrayList();
	private List<?> opportunity_type = new ArrayList();
	private List<?> industry = new ArrayList();
	private List<?> staff_size = new ArrayList();
	private List<?> customer_type = new ArrayList();
	private List<?> categoryTypes = new ArrayList();
	private List<?> supplier_type = new ArrayList();
	
	private List<?> invoice_type = new ArrayList();
	private List<?> pay_type_mc = new ArrayList();
	private List<?> warehouse = new ArrayList();
	private List<?> collection_type = new ArrayList();
	private List<?> income_category = new ArrayList();
	private List<?> expenditrue_category = new ArrayList();
	private List<?> settlement_account = new ArrayList();
	private List<?> support = new ArrayList();

	private List<?> articleTypes = new ArrayList();
	private List<?> themeTypes = new ArrayList();
	private List<?> indexviews = new ArrayList();
	private List<?> direction = new ArrayList();
	private List<?> industry_zj = new ArrayList();
	private List<?> industry_qy = new ArrayList();
	

	private List<?> product_type = new ArrayList();
	private List<?> pipe_diameter = new ArrayList();
	private List<?> wall_thickness = new ArrayList();
	private List<?> length = new ArrayList();
	private List<?> texture_material = new ArrayList();
	

	private List<?> otherCitys = new ArrayList();
	
	private List<?> abnormals = new ArrayList();
	private List<?> customerCategorys=null;
	private List<?> oss=null;
	private List<?> devices=null;
	private List<?> browers=null;
	private List<?> testingTypes=null;
	private List<?> brands=null;
	private List<?> productTypes=null;
	private List<?> executionTypes=null;
	private List<?> regions=null;
	private List<?> prioritys=null;
	private List<?> testCaseStatuss=null;
	private List<?> defectPrioritys=null;
	private List<?> defectTypes=null;
	private List<?> severitys=null;
	private List<?> defectStatuss=null;
	private List<?> projectTypes=null;
	private List<?> requirementTypes=null;
	private List<?> requirementStatuss=null;
	private List<?> projectStatuss=null;
	private List<?> buildStatuss=null;
	private List<?> mechanismTypes=null;
	private List<?> contractTypes=null;
	private List<?> paymentTypes=null;
	private List<?> followStatuss=null;
	private List<?> expenseTypes=null;
	private List<?> clueFollowStatuss=null;
	private List<?> productNames=null;
	private List<?> businessopportunitiesUnits=null;
	private List<?> clueSources=null;
	private List<?> customerFroms=null;
	private List<?> businessOpportunitySources=null;
	private List<?> salesStages=null;
	private List<?> opportunityTypes=null;
	private List<?> industrys=null;
	private List<?> staffSizes=null;
	private List<?> customerTypes=null;
	private List<?> supplierTypes=null;
	
	private List<?> invoiceTypes=null;
	private List<?> payTypeMcs=null;
	private List<?> warehouses=null;
	private List<?> collectionTypes=null;
	private List<?> incomeCategorys=null;
	private List<?> expenditrueCategorys=null;
	private List<?> settlementAccounts=null;

	private List<?> indexviewss=null;
	private List<?> directions=null;
	private List<?> industryZjs=null;
	private List<?> industryQys=null;
	

	private List<?> textureMaterials=null;
	

	
	
	private Dict selected;
	private String dictType="";
	private String dictName="";

	public DictController() {
		
	}
	public void create() {
		if (selected != null) {
			setEmbeddableKeys();
			try {
				int k = 0;
				String table = "sys_dict";
				String mechanismid = (String) getSession().getAttribute("MECHANISMID");
				k = dictManager.isExistByDict(table, selected.getDictLabel(),selected.getDictValue(), selected.getParentCode(),mechanismid);
				if (k == 1) {
					JsfUtil.warn("字典标签：" + selected.getDictLabel()+"或 字典键值：" + selected.getDictValue() + "重复");
				} else if (k == 0) {
					String dictType = (String) getSession().getAttribute("DICTTYPE");
					selected.setParentCode(dictType);
						selected.setDictCode(UUID.randomUUID().toString());
						selected.setCreateDate(df.format(new Date()));
						selected.setUpdateDate(df.format(new Date()));
						String username = (String) getSession().getAttribute("USERNAME");
						String mechanismmc = (String) getSession().getAttribute("MECHANISMNAME");
						selected.setMechanismId(mechanismid);
						selected.setMechanismName(mechanismmc);
						int i = dictManager.saveDict(selected);
						if (i == 0) {
							JsfUtil.warn("字典新增失败!");
						} else if (i == 1) {
							JsfUtil.info("字典新增成功!");
						} else {
							JsfUtil.error("字典新增错误!");
						}
				}
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, "字典新增错误!");
			}
		}
		if (!JsfUtil.isValidationFailed()) {
			items = null; // Invalidate list of items to trigger re-query.
		}
		
	}
	public Dict edit() {
		String id = getRequest().getParameter("id");
		if(selected==null){
			selected=new Dict();
		}
		selected.setDictCode(id);;
		selected = dictManager.edit(selected);
		return selected;
	}

	public void destroy() {
		if (selected != null) {
			setEmbeddableKeys();
			try {
				if(selected.getStatus().equals("启用")){
					JsfUtil.warn("数据已启用,请勿删除!");
				}else{
					String mechanismId = (String) getSession().getAttribute("MECHANISMID");
					int i = this.remove("sys_dict", selected.getDictCode(),mechanismId);
					if (i == 0) {
						JsfUtil.warn("字典删除失败!");
					} else if (i == 1) {
						JsfUtil.info("字典删除成功!");
					} else {
						JsfUtil.error("字典删除错误!");
					}
				}
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, "字典删除错误!");
			}
		} else {
			JsfUtil.warn("字典删除失败!");
		}
		if (!JsfUtil.isValidationFailed()) {
			selected = null; // Remove selection
			items = null; // Invalidate list of items to trigger re-query.
		}
	}

	public List<Dict> getItems() {
		String sftz = (String) getSession().getAttribute("SFTZ");
			String dictType = (String) getSession().getAttribute("DICTTYPE");
			if (selected == null) {
				selected = new Dict();
			}
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String mechanismName = (String) getSession().getAttribute("MECHANISMNAME");
			selected.setMechanismId(mechanismId);
			selected.setMechanismName(mechanismName);
			selected.setParentCode(dictType);
			String from = (String) getSession().getAttribute("FROM");
			selected.setFrom(from);
			items = dictManager.getItems(selected);
			getSession().setAttribute("SFTZ", "否");

		return items;
	}
	public DictManager getDictManager() {
		return dictManager;
	}

	public Dict getSelected() {
		return selected;
	}

	protected void initializeEmbeddableKey() {
	}
	public Dict prepareCreate() {
		selected = new Dict();
		initializeEmbeddableKey();
		return selected;
	}

	protected void setEmbeddableKeys() {
	}

	public void setDictManager(DictManager dictManager) {
		this.dictManager = dictManager;
	}

	public void setDictszManager(DictManager dictManager) {
		this.dictManager = dictManager;
	}

	public void setSelected(Dict selected) {
		this.selected = selected;
	}
	public void enable() {
		if (selected != null) {
			setEmbeddableKeys();
			try {
				String mechanismid = (String) getSession().getAttribute("MECHANISMID");
				int i = this.enable("sys_dict", selected.getDictCode(),"dict_code",mechanismid);
				if (i == 0) {
					JsfUtil.warn("字典启用失败!");
				} else if (i == 1) {
					JsfUtil.info("字典启用成功!");
				} else {
					JsfUtil.error("字典启用错误!");
				}
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, "字典启用错误!");
			}
		} else {
			JsfUtil.warn("字典启用失败!");
		}
		if (!JsfUtil.isValidationFailed()) {
			selected = null; // Remove selection
			items = null; // Invalidate list of items to trigger re-query.
		}
	}
	public void disable() {
		if (selected != null) {
			setEmbeddableKeys();
			try {
				String mechanismid = (String) getSession().getAttribute("MECHANISMID");
				int i = this.disable("sys_dict", selected.getDictCode(),"dict_code",mechanismid);
				if (i == 0) {
					JsfUtil.warn("字典禁用失败!");
				} else if (i == 1) {
					JsfUtil.info("字典禁用成功!");
				} else {
					JsfUtil.error("字典禁用错误!");
				}
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, "字典禁用错误!");
			}
		} else {
			JsfUtil.warn("字典禁用失败!");
		}
		if (!JsfUtil.isValidationFailed()) {
			selected = null; // Remove selection
			items = null; // Invalidate list of items to trigger re-query.
		}
	}
	public void update() {
		if (selected != null) {
			setEmbeddableKeys();
			try { 
				int k = 0;
				String table = "sys_dict";
				String mechanismid = (String) getSession().getAttribute("MECHANISMID");
				k = dictManager.isExistByDict(table, selected.getDictLabel(),selected.getDictValue(),selected.getDictCode(),selected.getParentCode(),mechanismid);
				if (k == 1) {
					JsfUtil.warn("字典标签：" + selected.getDictLabel()+"或 字典键值：" + selected.getDictValue() + "重复");
				} else if (k == 0) {
					selected.setCreateDate(df.format(new Date()));
					selected.setUpdateDate(df.format(new Date()));
					int i = dictManager.update(selected);
					if (i == 0) {
						JsfUtil.warn("字典更新失败!");
					} else if (i == 1) {
						JsfUtil.info("字典更新成功!");
					} else {
						JsfUtil.error("字典更新错误!");
					}
				}
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, "字典更新错误!");
			}
		} else {
			JsfUtil.warn("字典更新失败!");
		}
		if (!JsfUtil.isValidationFailed()) {
			items = null; // Invalidate list of items to trigger re-query.
		}
	}

	public String getDictType(){
		dictType = (String) getSession().getAttribute("DICTTYPE"); 
		return dictType;
	}

	public void setDictType(String dictType) {
		this.dictType = dictType;
	}

	public String getDictName() {
		dictName = (String) getSession().getAttribute("DICTNAME"); 
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	public List<?> getFollow_status() {
		if(selected==null){
			selected=new Dict();
		}
		selected.setDictType("follow_status");
		String mechanismid = (String) getSession().getAttribute("MECHANISMID");
		String mechanismmc = (String) getSession().getAttribute("MECHANISMNAME");
		selected.setMechanismId(mechanismid);
		selected.setMechanismName(mechanismmc);
		String from = (String) getSession().getAttribute("FROM");
		selected.setFrom(from);
		follow_status = dictManager.getItemsByDictType(selected);
		return follow_status;
	}

	public void setFollow_status(List<?> follow_status) {
		this.follow_status = follow_status;
	}

	public List<?> getCustomer_from() {
		if(selected==null){
			selected=new Dict();
		}
		selected.setDictType("customer_from");
		String mechanismid = (String) getSession().getAttribute("MECHANISMID");
		String mechanismmc = (String) getSession().getAttribute("MECHANISMNAME");
		selected.setMechanismId(mechanismid);
		selected.setMechanismName(mechanismmc);
		String from = (String) getSession().getAttribute("FROM");
		selected.setFrom(from);
		customer_from = dictManager.getItemsByDictType(selected);
		return customer_from;
	}

	public void setCustomer_from(List<?> customer_from) {
		this.customer_from = customer_from;
	}

	public List<?> getIndustry() {
		if(selected==null){
			selected=new Dict();
		}
		selected.setDictType("industry");
		String mechanismid = (String) getSession().getAttribute("MECHANISMID");
		String mechanismmc = (String) getSession().getAttribute("MECHANISMNAME");
		selected.setMechanismId(mechanismid);
		selected.setMechanismName(mechanismmc);
		String from = (String) getSession().getAttribute("FROM");
		selected.setFrom(from);
		industry = dictManager.getItemsByDictType(selected);
		return industry;
	}

	public void setIndustry(List<?> industry) {
		this.industry = industry;
	}

	public List<?> getStaff_size() {
		if(selected==null){
			selected=new Dict();
		}
		selected.setDictType("staff_size");
		String mechanismid = (String) getSession().getAttribute("MECHANISMID");
		String mechanismmc = (String) getSession().getAttribute("MECHANISMNAME");
		selected.setMechanismId(mechanismid);
		selected.setMechanismName(mechanismmc);
		String from = (String) getSession().getAttribute("FROM");
		selected.setFrom(from);
		staff_size = dictManager.getItemsByDictType(selected);
		return staff_size;
	}

	public void setStaff_size(List<?> staff_size) {
		this.staff_size = staff_size;
	}

	public List<?> getCustomer_type() {
		if(selected==null){
			selected=new Dict();
		}
		selected.setDictType("customer_type");
		String mechanismid = (String) getSession().getAttribute("MECHANISMID");
		String mechanismmc = (String) getSession().getAttribute("MECHANISMNAME");
		selected.setMechanismId(mechanismid);
		selected.setMechanismName(mechanismmc);
		String from = (String) getSession().getAttribute("FROM");
		selected.setFrom(from);
		customer_type = dictManager.getItemsByDictType(selected);
		return customer_type;
	}

	public void setCustomer_type(List<?> customer_type) {
		this.customer_type = customer_type;
	}
	
	

	public List<?> getSupplier_type() {
		
		if(selected==null){
			selected=new Dict();
		}
		selected.setDictType("supplier_type");
		String mechanismid = (String) getSession().getAttribute("MECHANISMID");
		String mechanismmc = (String) getSession().getAttribute("MECHANISMNAME");
		selected.setMechanismId(mechanismid);
		selected.setMechanismName(mechanismmc);
		String from = (String) getSession().getAttribute("FROM");
		selected.setFrom(from);
		supplier_type = dictManager.getItemsByDictType(selected);
		
		return supplier_type;
	}
	public void setSupplier_type(List<?> supplier_type) {
		this.supplier_type = supplier_type;
	}
	public List<?> getClue_follow_status() {
		if(selected==null){
			selected=new Dict();
		}
		selected.setDictType("clue_follow_status");
		String mechanismid = (String) getSession().getAttribute("MECHANISMID");
		String mechanismmc = (String) getSession().getAttribute("MECHANISMNAME");
		selected.setMechanismId(mechanismid);
		selected.setMechanismName(mechanismmc);
		String from = (String) getSession().getAttribute("FROM");
		selected.setFrom(from);
		clue_follow_status = dictManager.getItemsByDictType(selected);
		return clue_follow_status;
	}

	public void setClue_follow_status(List<?> clue_follow_status) {
		this.clue_follow_status = clue_follow_status;
	}

	public List<?> getClue_source() {
		if(selected==null){
			selected=new Dict();
		}
		selected.setDictType("customer_from");
		String mechanismid = (String) getSession().getAttribute("MECHANISMID");
		String mechanismmc = (String) getSession().getAttribute("MECHANISMNAME");
		selected.setMechanismId(mechanismid);
		selected.setMechanismName(mechanismmc);
		String from = (String) getSession().getAttribute("FROM");
		selected.setFrom(from);
		clue_source = dictManager.getItemsByDictType(selected);
		return clue_source;
	}

	public void setClue_source(List<?> clue_source) {
		this.clue_source = clue_source;
	}

	public List<?> getSales_stage() {
		if(selected==null){
			selected=new Dict();
		}
		selected.setDictType("sales_stage");
		String mechanismid = (String) getSession().getAttribute("MECHANISMID");
		String mechanismmc = (String) getSession().getAttribute("MECHANISMNAME");
		selected.setMechanismId(mechanismid);
		selected.setMechanismName(mechanismmc);
		String from = (String) getSession().getAttribute("FROM");
		selected.setFrom(from);
		sales_stage = dictManager.getItemsByDictType(selected);
		return sales_stage;
	}

	public void setSales_stage(List<?> sales_stage) {
		this.sales_stage = sales_stage;
	}
	
	public List<?> getOpportunity_type() {
		if(selected==null){
			selected=new Dict();
		}
		selected.setDictType("opportunity_type");
		String mechanismid = (String) getSession().getAttribute("MECHANISMID");
		String mechanismmc = (String) getSession().getAttribute("MECHANISMNAME");
		selected.setMechanismId(mechanismid);
		selected.setMechanismName(mechanismmc);
		String from = (String) getSession().getAttribute("FROM");
		selected.setFrom(from);
		opportunity_type = dictManager.getItemsByDictType(selected);
		return opportunity_type;
	}

	public void setOpportunity_type(List<?> opportunity_type) {
		this.opportunity_type = opportunity_type;
	}

	public List<?> getContract_type() {
		if(selected==null){
			selected=new Dict();
		}
		selected.setDictType("contract_type");
		String mechanismid = (String) getSession().getAttribute("MECHANISMID");
		String mechanismmc = (String) getSession().getAttribute("MECHANISMNAME");
		selected.setMechanismId(mechanismid);
		selected.setMechanismName(mechanismmc);
		String from = (String) getSession().getAttribute("FROM");
		selected.setFrom(from);
		contract_type = dictManager.getItemsByDictType(selected);
		return contract_type;
	}

	public void setContract_type(List<?> contract_type) {
		this.contract_type = contract_type;
	}

	public List<?> getPayment_type() {
		if(selected==null){
			selected=new Dict();
		}
		selected.setDictType("payment_type");
		String mechanismid = (String) getSession().getAttribute("MECHANISMID");
		String mechanismmc = (String) getSession().getAttribute("MECHANISMNAME");
		selected.setMechanismId(mechanismid);
		selected.setMechanismName(mechanismmc);
		String from = (String) getSession().getAttribute("FROM");
		selected.setFrom(from);
		payment_type = dictManager.getItemsByDictType(selected);
		return payment_type;
	}

	public void setPayment_type(List<?> payment_type) {
		this.payment_type = payment_type;
	}

	public List<?> getPay_type_mc() {
		if(selected==null){
			selected=new Dict();
		}
		selected.setDictType("pay_type_mc");
		String mechanismid = (String) getSession().getAttribute("MECHANISMID");
		String mechanismmc = (String) getSession().getAttribute("MECHANISMNAME");
		selected.setMechanismId(mechanismid);
		selected.setMechanismName(mechanismmc);
		String from = (String) getSession().getAttribute("FROM");
		selected.setFrom(from);
		pay_type_mc = dictManager.getItemsByDictType(selected);
		return pay_type_mc;
	}

	public void setPay_type_mc(List<?> pay_type_mc) {
		this.pay_type_mc = pay_type_mc;
	}

	public List<?> getCollection_type() {
		if(selected==null){
			selected=new Dict();
		}
		selected.setDictType("collection_type");
		String mechanismid = (String) getSession().getAttribute("MECHANISMID");
		String mechanismmc = (String) getSession().getAttribute("MECHANISMNAME");
		selected.setMechanismId(mechanismid);
		selected.setMechanismName(mechanismmc);
		String from = (String) getSession().getAttribute("FROM");
		selected.setFrom(from);
		collection_type = dictManager.getItemsByDictType(selected);
		return collection_type;
	}

	public void setCollection_type(List<?> collection_type) {
		this.collection_type = collection_type;
	}
	
	

	public List<?> getIncome_category() {
		if(selected==null){
			selected=new Dict();
		}
		selected.setDictType("income_category");
		String mechanismid = (String) getSession().getAttribute("MECHANISMID");
		String mechanismmc = (String) getSession().getAttribute("MECHANISMNAME");
		selected.setMechanismId(mechanismid);
		selected.setMechanismName(mechanismmc);
		String from = (String) getSession().getAttribute("FROM");
		selected.setFrom(from);
		income_category = dictManager.getItemsByDictType(selected);
		return income_category;
	}

	public void setIncome_category(List<?> income_category) {
		this.income_category = income_category;
	}

	public List<?> getExpenditrue_category() {
		if(selected==null){
			selected=new Dict();
		}
		selected.setDictType("expenditrue_category");
		String mechanismid = (String) getSession().getAttribute("MECHANISMID");
		String mechanismmc = (String) getSession().getAttribute("MECHANISMNAME");
		selected.setMechanismId(mechanismid);
		selected.setMechanismName(mechanismmc);
		String from = (String) getSession().getAttribute("FROM");
		selected.setFrom(from);
		expenditrue_category = dictManager.getItemsByDictType(selected);
		return expenditrue_category;
	}

	public void setExpenditrue_category(List<?> expenditrue_category) {
		this.expenditrue_category = expenditrue_category;
	}

	public List<?> getInvoice_type() {
		if(selected==null){
			selected=new Dict();
		}
		selected.setDictType("invoice_type");
		String mechanismid = (String) getSession().getAttribute("MECHANISMID");
		String mechanismmc = (String) getSession().getAttribute("MECHANISMNAME");
		selected.setMechanismId(mechanismid);
		selected.setMechanismName(mechanismmc);
		String from = (String) getSession().getAttribute("FROM");
		selected.setFrom(from);
		invoice_type = dictManager.getItemsByDictType(selected);
		return invoice_type;
	}

	public void setInvoice_type(List<?> invoice_type) {
		this.invoice_type = invoice_type;
	}

	public List<?> getMechanism_type() {
		if(selected==null){
			selected=new Dict();
		}
		selected.setDictType("mechanism_type");
		String mechanismid = (String) getSession().getAttribute("MECHANISMID");
		String mechanismmc = (String) getSession().getAttribute("MECHANISMNAME");
		selected.setMechanismId(mechanismid);
		selected.setMechanismName(mechanismmc);
		String from = (String) getSession().getAttribute("FROM");
		selected.setFrom(from);
		mechanism_type = dictManager.getItemsByDictType(selected);
		return mechanism_type;
	}

	public void setMechanism_type(List<?> mechanism_type) {
		this.mechanism_type = mechanism_type;
	}

	public List<?> getExpense_type() {
		if(selected==null){
			selected=new Dict();
		}
		selected.setDictType("expense_type");
		String mechanismid = (String) getSession().getAttribute("MECHANISMID");
		String mechanismmc = (String) getSession().getAttribute("MECHANISMNAME");
		selected.setMechanismId(mechanismid);
		selected.setMechanismName(mechanismmc);
		String from = (String) getSession().getAttribute("FROM");
		selected.setFrom(from);
		expense_type = dictManager.getItemsByDictType(selected);
		return expense_type;
	}

	public void setExpense_type(List<?> expense_type) {
		this.expense_type = expense_type;
	}

	public List<?> getBrand() {
		if(selected==null){
			selected=new Dict();
		}
		selected.setDictType("brand");
		String mechanismid = (String) getSession().getAttribute("MECHANISMID");
		String mechanismmc = (String) getSession().getAttribute("MECHANISMNAME");
		selected.setMechanismId(mechanismid);
		selected.setMechanismName(mechanismmc);
		String from = (String) getSession().getAttribute("FROM");
		selected.setFrom(from);
		brand = dictManager.getItemsByDictType(selected);
		return brand;
	}

	public void setBrand(List<?> brand) {
		this.brand = brand;
	}

	public List<?> getProductType() {
		if(selected==null){
			selected=new Dict();
		}
		selected.setDictType("productType");
		String mechanismid = (String) getSession().getAttribute("MECHANISMID");
		String mechanismmc = (String) getSession().getAttribute("MECHANISMNAME");
		selected.setMechanismId(mechanismid);
		selected.setMechanismName(mechanismmc);
		String from = (String) getSession().getAttribute("FROM");
		selected.setFrom(from);
		productType = dictManager.getItemsByDictType(selected);
		return productType;
	}

	public void setProductType(List<?> productType) {
		this.productType = productType;
	}
	public List<?> getWarehouse() {
		if(selected==null){
			selected=new Dict();
		}
		selected.setDictType("warehouse");
		String mechanismid = (String) getSession().getAttribute("MECHANISMID");
		String mechanismmc = (String) getSession().getAttribute("MECHANISMNAME");
		selected.setMechanismId(mechanismid);
		selected.setMechanismName(mechanismmc);
		String from = (String) getSession().getAttribute("FROM");
		selected.setFrom(from);
		warehouse = dictManager.getItemsByDictType(selected);
		return warehouse;
	}

	public void setWarehouse(List<?> warehouse) {
		this.warehouse = warehouse;
	}

	public List<?> getRegion() {
		if(selected==null){
			selected=new Dict();
		}
		selected.setDictType("region");
		String mechanismid = (String) getSession().getAttribute("MECHANISMID");
		String mechanismmc = (String) getSession().getAttribute("MECHANISMNAME");
		selected.setMechanismId(mechanismid);
		selected.setMechanismName(mechanismmc);
		String from = (String) getSession().getAttribute("FROM");
		selected.setFrom(from);
		region = dictManager.getItemsByDictType(selected);
		return region;
	}

	public void setRegion(List<?> region) {
		this.region = region;
	}


	public List<?> getSettlement_account() {
		if(selected==null){
			selected=new Dict();
		}
		selected.setDictType("settlement_account");
		String mechanismid = (String) getSession().getAttribute("MECHANISMID");
		String mechanismmc = (String) getSession().getAttribute("MECHANISMNAME");
		selected.setMechanismId(mechanismid);
		selected.setMechanismName(mechanismmc);
		String from = (String) getSession().getAttribute("FROM");
		selected.setFrom(from);
		settlement_account = dictManager.getItemsByDictType(selected);
		
		return settlement_account;
	}

	public void setSettlement_account(List<?> settlement_account) {
		this.settlement_account = settlement_account;
	}


	public void setIndexviews(List<?> indexviews) {
		this.indexviews = indexviews;
	}

	public void setDirection(List<?> direction) {
		this.direction = direction;
	}

	

	public void setIndustry_zj(List<?> industry_zj) {
		this.industry_zj = industry_zj;
	}
	

	public void setIndustry_qy(List<?> industry_qy) {
		this.industry_qy = industry_qy;
	}

	public List<?> getBusinessopportunities_unit() {

		if(selected==null){
			selected=new Dict();
		}
		selected.setDictType("businessopportunities_unit");
		String mechanismid = (String) getSession().getAttribute("MECHANISMID");
		String mechanismmc = (String) getSession().getAttribute("MECHANISMNAME");
		selected.setMechanismId(mechanismid);
		selected.setMechanismName(mechanismmc);
		String from = (String) getSession().getAttribute("FROM");
		selected.setFrom(from);
		businessopportunities_unit = dictManager.getItemsByDictType(selected);
		return businessopportunities_unit;
	}

	public void setBusinessopportunities_unit(List<?> businessopportunities_unit) {
		this.businessopportunities_unit = businessopportunities_unit;
	}

	public List<?> getProduct_name() {
		if(selected==null){
			selected=new Dict();
		}
		selected.setDictType("product_name");
		String mechanismid = (String) getSession().getAttribute("MECHANISMID");
		String mechanismmc = (String) getSession().getAttribute("MECHANISMNAME");
		selected.setMechanismId(mechanismid);
		selected.setMechanismName(mechanismmc);
		String from = (String) getSession().getAttribute("FROM");
		selected.setFrom(from);
		product_name = dictManager.getItemsByDictType(selected);
		return product_name;
	}

	public void setProduct_name(List<?> product_name) {
		this.product_name = product_name;
	}

	public List<?> getBusiness_opportunity_source() {
		if(selected==null){
			selected=new Dict();
		}
		selected.setDictType("business_opportunity_source");
		String mechanismid = (String) getSession().getAttribute("MECHANISMID");
		String mechanismmc = (String) getSession().getAttribute("MECHANISMNAME");
		selected.setMechanismId(mechanismid);
		selected.setMechanismName(mechanismmc);
		String from = (String) getSession().getAttribute("FROM");
		selected.setFrom(from);
		business_opportunity_source = dictManager.getItemsByDictType(selected);
		return business_opportunity_source;
	}

	public void setBusiness_opportunity_source(List<?> business_opportunity_source) {
		this.business_opportunity_source = business_opportunity_source;
	}
	public List<?> getProject_type() {
		if(selected==null){
			selected=new Dict();
		}
		selected.setDictType("project_type");
		String mechanismid = (String) getSession().getAttribute("MECHANISMID");
		String mechanismmc = (String) getSession().getAttribute("MECHANISMNAME");
		selected.setMechanismId(mechanismid);
		selected.setMechanismName(mechanismmc);
		String from = (String) getSession().getAttribute("FROM");
		selected.setFrom(from);
		project_type = dictManager.getItemsByDictType(selected);
		return project_type;
	}
	public void setProject_type(List<?> project_type) {
		this.project_type = project_type;
	}
	public List<?> getOs() {
		if(selected==null){
			selected=new Dict();
		}
		selected.setDictType("os");
		String mechanismid = (String) getSession().getAttribute("MECHANISMID");
		String mechanismmc = (String) getSession().getAttribute("MECHANISMNAME");
		selected.setMechanismId(mechanismid);
		selected.setMechanismName(mechanismmc);
		String from = (String) getSession().getAttribute("FROM");
		selected.setFrom(from);
		os = dictManager.getItemsByDictType(selected);
		return os;
	}
	public void setOs(List<?> os) {
		this.os = os;
	}
	public List<?> getDevice() {
		if(selected==null){
			selected=new Dict();
		}
		selected.setDictType("device");
		String mechanismid = (String) getSession().getAttribute("MECHANISMID");
		String mechanismmc = (String) getSession().getAttribute("MECHANISMNAME");
		selected.setMechanismId(mechanismid);
		selected.setMechanismName(mechanismmc);
		String from = (String) getSession().getAttribute("FROM");
		selected.setFrom(from);
		device = dictManager.getItemsByDictType(selected);
		return device;
	}
	public void setDevice(List<?> device) {
		this.device = device;
	}
	public List<?> getBrower() {
		if(selected==null){
			selected=new Dict();
		}
		selected.setDictType("brower");
		String mechanismid = (String) getSession().getAttribute("MECHANISMID");
		String mechanismmc = (String) getSession().getAttribute("MECHANISMNAME");
		selected.setMechanismId(mechanismid);
		selected.setMechanismName(mechanismmc);
		String from = (String) getSession().getAttribute("FROM");
		selected.setFrom(from);
		brower = dictManager.getItemsByDictType(selected);
		return brower;
	}
	public void setBrower(List<?> brower) {
		this.brower = brower;
	}
	public List<?> getProject_status() {
		if(selected==null){
			selected=new Dict();
		}
		selected.setDictType("project_status");
		String mechanismid = (String) getSession().getAttribute("MECHANISMID");
		String mechanismmc = (String) getSession().getAttribute("MECHANISMNAME");
		selected.setMechanismId(mechanismid);
		selected.setMechanismName(mechanismmc);
		String from = (String) getSession().getAttribute("FROM");
		selected.setFrom(from);
		project_status = dictManager.getItemsByDictType(selected);
		return project_status;
	}
	public void setProject_status(List<?> project_status) {
		this.project_status = project_status;
	}
	public List<?> getBuild_status() {
		if(selected==null){
			selected=new Dict();
		}
		selected.setDictType("build_status");
		String mechanismid = (String) getSession().getAttribute("MECHANISMID");
		String mechanismmc = (String) getSession().getAttribute("MECHANISMNAME");
		selected.setMechanismId(mechanismid);
		selected.setMechanismName(mechanismmc);
		String from = (String) getSession().getAttribute("FROM");
		selected.setFrom(from);
		build_status = dictManager.getItemsByDictType(selected);
		return build_status;
	}
	public void setBuild_status(List<?> build_status) {
		this.build_status = build_status;
	}
	public List<?> getRequirement_type() {
		if(selected==null){
			selected=new Dict();
		}
		selected.setDictType("requirement_type");
		String mechanismid = (String) getSession().getAttribute("MECHANISMID");
		String mechanismmc = (String) getSession().getAttribute("MECHANISMNAME");
		selected.setMechanismId(mechanismid);
		selected.setMechanismName(mechanismmc);
		String from = (String) getSession().getAttribute("FROM");
		selected.setFrom(from);
		requirement_type = dictManager.getItemsByDictType(selected);
		return requirement_type;
	}
	public void setRequirement_type(List<?> requirement_type) {
		this.requirement_type = requirement_type;
	}
	public List<?> getRequirement_status() {
		if(selected==null){
			selected=new Dict();
		}
		selected.setDictType("requirement_status");
		String mechanismid = (String) getSession().getAttribute("MECHANISMID");
		String mechanismmc = (String) getSession().getAttribute("MECHANISMNAME");
		selected.setMechanismId(mechanismid);
		selected.setMechanismName(mechanismmc);
		String from = (String) getSession().getAttribute("FROM");
		selected.setFrom(from);
		requirement_status = dictManager.getItemsByDictType(selected);
		return requirement_status;
	}
	public void setRequirement_status(List<?> requirement_status) {
		this.requirement_status = requirement_status;
	}
	public List<?> getDefect_type() {
		if(selected==null){
			selected=new Dict();
		}
		selected.setDictType("defect_type");
		String mechanismid = (String) getSession().getAttribute("MECHANISMID");
		String mechanismmc = (String) getSession().getAttribute("MECHANISMNAME");
		selected.setMechanismId(mechanismid);
		selected.setMechanismName(mechanismmc);
		String from = (String) getSession().getAttribute("FROM");
		selected.setFrom(from);
		defect_type = dictManager.getItemsByDictType(selected);
		return defect_type;
	}
	public void setDefect_type(List<?> defect_type) {
		this.defect_type = defect_type;
	}
	public List<?> getSeverity() {
		if(selected==null){
			selected=new Dict();
		}
		selected.setDictType("severity");
		String mechanismid = (String) getSession().getAttribute("MECHANISMID");
		String mechanismmc = (String) getSession().getAttribute("MECHANISMNAME");
		selected.setMechanismId(mechanismid);
		selected.setMechanismName(mechanismmc);
		String from = (String) getSession().getAttribute("FROM");
		selected.setFrom(from);
		severity = dictManager.getItemsByDictType(selected);
		return severity;
	}
	public void setSeverity(List<?> severity) {
		this.severity = severity;
	}
	public List<?> getDefect_status() {
		if(selected==null){
			selected=new Dict();
		}
		selected.setDictType("defect_status");
		String mechanismid = (String) getSession().getAttribute("MECHANISMID");
		String mechanismmc = (String) getSession().getAttribute("MECHANISMNAME");
		selected.setMechanismId(mechanismid);
		selected.setMechanismName(mechanismmc);
		String from = (String) getSession().getAttribute("FROM");
		selected.setFrom(from);
		defect_status = dictManager.getItemsByDictType(selected);
		return defect_status;
	}
	public void setDefect_status(List<?> defect_status) {
		this.defect_status = defect_status;
	}
	public List<?> getDefect_priority() {
		if(selected==null){
			selected=new Dict();
		}
		selected.setDictType("defect_priority");
		String mechanismid = (String) getSession().getAttribute("MECHANISMID");
		String mechanismmc = (String) getSession().getAttribute("MECHANISMNAME");
		selected.setMechanismId(mechanismid);
		selected.setMechanismName(mechanismmc);
		String from = (String) getSession().getAttribute("FROM");
		selected.setFrom(from);
		defect_priority = dictManager.getItemsByDictType(selected);
		return defect_priority;
	}
	public void setDefect_priority(List<?> defect_priority) {
		this.defect_priority = defect_priority;
	}
	public List<?> getTesting_type() {
		if(selected==null){
			selected=new Dict();
		}
		selected.setDictType("testing_type");
		String mechanismid = (String) getSession().getAttribute("MECHANISMID");
		String mechanismmc = (String) getSession().getAttribute("MECHANISMNAME");
		selected.setMechanismId(mechanismid);
		selected.setMechanismName(mechanismmc);
		String from = (String) getSession().getAttribute("FROM");
		selected.setFrom(from);
		testing_type = dictManager.getItemsByDictType(selected);
		return testing_type;
	}
	public void setTesting_type(List<?> testing_type) {
		this.testing_type = testing_type;
	}
	public List<?> getExecution_type() {
		if(selected==null){
			selected=new Dict();
		}
		selected.setDictType("execution_type");
		String mechanismid = (String) getSession().getAttribute("MECHANISMID");
		String mechanismmc = (String) getSession().getAttribute("MECHANISMNAME");
		selected.setMechanismId(mechanismid);
		selected.setMechanismName(mechanismmc);
		String from = (String) getSession().getAttribute("FROM");
		selected.setFrom(from);
		execution_type = dictManager.getItemsByDictType(selected);
		return execution_type;
	}
	public void setExecution_type(List<?> execution_type) {
		this.execution_type = execution_type;
	}
	public List<?> getPriority() {
		if(selected==null){
			selected=new Dict();
		}
		selected.setDictType("priority");
		String mechanismid = (String) getSession().getAttribute("MECHANISMID");
		String mechanismmc = (String) getSession().getAttribute("MECHANISMNAME");
		selected.setMechanismId(mechanismid);
		selected.setMechanismName(mechanismmc);
		String from = (String) getSession().getAttribute("FROM");
		selected.setFrom(from);
		priority = dictManager.getItemsByDictType(selected);
		return priority;
	}
	public void setPriority(List<?> priority) {
		this.priority = priority;
	}
	public List<?> getTest_case_status() {
		if(selected==null){
			selected=new Dict();
		}
		selected.setDictType("test_case_status");
		String mechanismid = (String) getSession().getAttribute("MECHANISMID");
		String mechanismmc = (String) getSession().getAttribute("MECHANISMNAME");
		selected.setMechanismId(mechanismid);
		selected.setMechanismName(mechanismmc);
		String from = (String) getSession().getAttribute("FROM");
		selected.setFrom(from);
		test_case_status = dictManager.getItemsByDictType(selected);
		return test_case_status;
	}
	public void setTest_case_status(List<?> test_case_status) {
		this.test_case_status = test_case_status;
	}
	public List<?> getCustomerCategory() {
		if(selected==null){
			selected=new Dict();
		}
		selected.setDictType("customer_category");
		String mechanismid = (String) getSession().getAttribute("MECHANISMID");
		String mechanismmc = (String) getSession().getAttribute("MECHANISMNAME");
		selected.setMechanismId(mechanismid);
		selected.setMechanismName(mechanismmc);
		String from = (String) getSession().getAttribute("FROM");
		selected.setFrom(from);
		customerCategory = dictManager.getItemsByDictType(selected);
	
		return customerCategory;
	}
	public void setCustomerCategory(List<?> customerCategory) {
		this.customerCategory = customerCategory;
	}
	public List<?> getAbnormals() {
		
		if(selected==null){
			selected=new Dict();
		}
		selected.setDictType("abnormals");
		String mechanismid = (String) getSession().getAttribute("MECHANISMID");
		String mechanismmc = (String) getSession().getAttribute("MECHANISMNAME");
		selected.setMechanismId(mechanismid);
		selected.setMechanismName(mechanismmc);
		String from = (String) getSession().getAttribute("FROM");
		selected.setFrom(from);
		abnormals = dictManager.getItemsByDictType(selected);
		
		return abnormals;
	}
	public void setAbnormals(List<?> abnormals) {
		this.abnormals = abnormals;
	}
	public List<?> getSupport() {
		
		if(selected==null){
			selected=new Dict();
		}
		selected.setDictType("support");
		String mechanismid = (String) getSession().getAttribute("MECHANISMID");
		String mechanismmc = (String) getSession().getAttribute("MECHANISMNAME");
		selected.setMechanismId(mechanismid);
		selected.setMechanismName(mechanismmc);
		String from = (String) getSession().getAttribute("FROM");
		selected.setFrom(from);
		support = dictManager.getItemsByDictType(selected);
	
		return support;
	}
	public void setSupport(List<?> support) {
		this.support = support;
	}

	public List<?> getFollowStatuss() {
		if(selected==null){
			selected=new Dict();
		}
		if(followStatuss==null) {
			selected.setDictType("follow_status");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String mechanismName = (String) getSession().getAttribute("MECHANISMNAME");
			selected.setMechanismId(mechanismId);
			selected.setMechanismName(mechanismName);
			String from = (String) getSession().getAttribute("FROM");
			selected.setFrom(from);
			followStatuss = dictManager.getItemsByDictType(selected);
		}
		return followStatuss;
	}

	public void setFollowStatuss(List<?> followStatuss) {
		this.followStatuss = followStatuss;
	}

	public List<?> getCustomerFroms() {
		if(selected==null){
			selected=new Dict();
		}
		if(customerFroms==null) {
			selected.setDictType("customer_from");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String mechanismName = (String) getSession().getAttribute("MECHANISMNAME");
			selected.setMechanismId(mechanismId);
			selected.setMechanismName(mechanismName);
			String from = (String) getSession().getAttribute("FROM");
			selected.setFrom(from);
			customerFroms = dictManager.getItemsByDictType(selected);
		}
		return customerFroms;
	}

	public void setCustomer_froms(List<?> customerFroms) {
		this.customerFroms = customerFroms;
	}

	public List<?> getIndustrys() {
		if(selected==null){
			selected=new Dict();
		}
		if(industrys==null) {
			selected.setDictType("industry");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String mechanismName = (String) getSession().getAttribute("MECHANISMNAME");
			selected.setMechanismId(mechanismId);
			selected.setMechanismName(mechanismName);
			String from = (String) getSession().getAttribute("FROM");
			selected.setFrom(from);
			industrys = dictManager.getItemsByDictType(selected);
		}
		return industrys;
	}

	public void setIndustrys(List<?> industry) {
		this.industrys = industrys;
	}

	public List<?> getStaffSizes() {
		if(selected==null){
			selected=new Dict();
		}
		if(staffSizes==null) {
			selected.setDictType("staff_size");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String mechanismName = (String) getSession().getAttribute("MECHANISMNAME");
			selected.setMechanismId(mechanismId);
			selected.setMechanismName(mechanismName);
			String from = (String) getSession().getAttribute("FROM");
			selected.setFrom(from);
			staffSizes = dictManager.getItemsByDictType(selected);
		}
		return staffSizes;
	}

	public void setStaffSizes(List<?> staffSizes) {
		this.staffSizes = staffSizes;
	}

	public List<?> getCustomerTypes() {
		if(selected==null){
			selected=new Dict();
		}
		if(customerTypes==null) {
			selected.setDictType("customer_type");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String mechanismName = (String) getSession().getAttribute("MECHANISMNAME");
			selected.setMechanismId(mechanismId);
			selected.setMechanismName(mechanismName);
			String from = (String) getSession().getAttribute("FROM");
			selected.setFrom(from);
			customerTypes = dictManager.getItemsByDictType(selected);
		}
		return customerTypes;
	}

	public void setCustomerTypes(List<?> customerTypes) {
		this.customerTypes = customerTypes;
	}
	
	

	public List<?> getSupplierTypes() {
		
		if(selected==null){
			selected=new Dict();
		}
		if(supplierTypes==null) {
			selected.setDictType("supplier_type");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String mechanismName = (String) getSession().getAttribute("MECHANISMNAME");
			selected.setMechanismId(mechanismId);
			selected.setMechanismName(mechanismName);
			String from = (String) getSession().getAttribute("FROM");
			selected.setFrom(from);
			supplierTypes = dictManager.getItemsByDictType(selected);
		}
		
		return supplierTypes;
	}
	public void setSupplierTypes(List<?> supplierTypes) {
		this.supplierTypes = supplierTypes;
	}
	public List<?> getClueFollowStatuss() {
		if(selected==null){
			selected=new Dict();
		}
		if(clueFollowStatuss==null) {
			selected.setDictType("clue_follow_status");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String mechanismName = (String) getSession().getAttribute("MECHANISMNAME");
			selected.setMechanismId(mechanismId);
			selected.setMechanismName(mechanismName);
			String from = (String) getSession().getAttribute("FROM");
			selected.setFrom(from);
			clueFollowStatuss = dictManager.getItemsByDictType(selected);
		}
		return clueFollowStatuss;
	}

	public void setClueFollowStatuss(List<?> clueFollowStatuss) {
		this.clueFollowStatuss = clueFollowStatuss;
	}

	public List<?> getClueSources() {
		if(selected==null){
			selected=new Dict();
		}
		if(clueSources==null) {
			selected.setDictType("clue_source");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String mechanismName = (String) getSession().getAttribute("MECHANISMNAME");
			selected.setMechanismId(mechanismId);
			selected.setMechanismName(mechanismName);
			String from = (String) getSession().getAttribute("FROM");
			selected.setFrom(from);
			clueSources = dictManager.getItemsByDictType(selected);
		}
		return clueSources;
	}

	public void setClueSources(List<?> clueSources) {
		this.clueSources = clueSources;
	}

	public List<?> getSalesStages() {
		if(selected==null){
			selected=new Dict();
		}
		if(salesStages==null) {
			selected.setDictType("sales_stage");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String mechanismName = (String) getSession().getAttribute("MECHANISMNAME");
			selected.setMechanismId(mechanismId);
			selected.setMechanismName(mechanismName);
			String from = (String) getSession().getAttribute("FROM");
			selected.setFrom(from);
			salesStages = dictManager.getItemsByDictType(selected);
		}
		return salesStages;
	}

	public void setSalesStages(List<?> salesStages) {
		this.salesStages = salesStages;
	}
	
	public List<?> getOpportunityTypes() {
		if(selected==null){
			selected=new Dict();
		}
		if(opportunityTypes==null) {
			selected.setDictType("opportunity_type");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String mechanismName = (String) getSession().getAttribute("MECHANISMNAME");
			selected.setMechanismId(mechanismId);
			selected.setMechanismName(mechanismName);
			String from = (String) getSession().getAttribute("FROM");
			selected.setFrom(from);
			opportunityTypes = dictManager.getItemsByDictType(selected);
		}
		return opportunityTypes;
	}

	public void setOpportunityTypes(List<?> opportunityTypes) {
		this.opportunityTypes = opportunityTypes;
	}

	public List<?> getContractTypes() {
		if(selected==null){
			selected=new Dict();
		}
		if(contractTypes==null) {
			selected.setDictType("contract_type");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String mechanismName = (String) getSession().getAttribute("MECHANISMNAME");
			selected.setMechanismId(mechanismId);
			selected.setMechanismName(mechanismName);
			String from = (String) getSession().getAttribute("FROM");
			selected.setFrom(from);
			contractTypes = dictManager.getItemsByDictType(selected);
		}
		return contractTypes;
	}

	public void setContractTypes(List<?> contractTypes) {
		this.contractTypes = contractTypes;
	}

	public List<?> getPaymentTypes() {
		if(selected==null){
			selected=new Dict();
		}
		if(paymentTypes==null) {
			selected.setDictType("payment_type");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String mechanismName = (String) getSession().getAttribute("MECHANISMNAME");
			selected.setMechanismId(mechanismId);
			selected.setMechanismName(mechanismName);
			String from = (String) getSession().getAttribute("FROM");
			selected.setFrom(from);
			paymentTypes = dictManager.getItemsByDictType(selected);
		}
		return paymentTypes;
	}

	public void setPaymentTypes(List<?> paymentTypes) {
		this.paymentTypes = paymentTypes;
	}

	public List<?> getCategoryTypes() {
		if(selected==null){
			selected=new Dict();
		}
		if(categoryTypes==null) {
			selected.setDictType("category_type");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String mechanismName = (String) getSession().getAttribute("MECHANISMNAME");
			selected.setMechanismId(mechanismId);
			selected.setMechanismName(mechanismName);
			String from = (String) getSession().getAttribute("FROM");
			selected.setFrom(from);
			categoryTypes = dictManager.getItemsByDictType(selected);
		}
		return categoryTypes;
	}

	public void setCategoryTypes(List<?> categoryTypes) {
		this.categoryTypes = categoryTypes;
	}

	public List<?> getPayTypeMcs() {
		if(selected==null){
			selected=new Dict();
		}
		if(payTypeMcs==null) {
			selected.setDictType("pay_type_mc");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String mechanismName = (String) getSession().getAttribute("MECHANISMNAME");
			selected.setMechanismId(mechanismId);
			selected.setMechanismName(mechanismName);
			String from = (String) getSession().getAttribute("FROM");
			selected.setFrom(from);
			payTypeMcs = dictManager.getItemsByDictType(selected);
		}
		return payTypeMcs;
	}

	public void setPay_type_mcs(List<?> payTypeMcs) {
		this.payTypeMcs = payTypeMcs;
	}

	public List<?> getCollectionTypes() {
		if(selected==null){
			selected=new Dict();
		}
		if(collectionTypes==null) {
			selected.setDictType("collection_type");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String mechanismName = (String) getSession().getAttribute("MECHANISMNAME");
			selected.setMechanismId(mechanismId);
			selected.setMechanismName(mechanismName);
			String from = (String) getSession().getAttribute("FROM");
			selected.setFrom(from);
			collectionTypes = dictManager.getItemsByDictType(selected);
		}
		return collectionTypes;
	}

	public void setCollectionTypes(List<?> collectionTypes) {
		this.collectionTypes = collectionTypes;
	}
	
	

	public List<?> getIncomeCategorys() {
		if(selected==null){
			selected=new Dict();
		}
		if(incomeCategorys==null) {
			selected.setDictType("income_category");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String mechanismName = (String) getSession().getAttribute("MECHANISMNAME");
			selected.setMechanismId(mechanismId);
			selected.setMechanismName(mechanismName);
			String from = (String) getSession().getAttribute("FROM");
			selected.setFrom(from);
			incomeCategorys = dictManager.getItemsByDictType(selected);
		}
		return incomeCategorys;
	}

	public void setIncomeCategorys(List<?> incomeCategorys) {
		this.incomeCategorys = incomeCategorys;
	}

	public List<?> getExpenditrueCategorys() {
		if(selected==null){
			selected=new Dict();
		}
		if(expenditrueCategorys==null) {
			selected.setDictType("expenditrue_category");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String mechanismName = (String) getSession().getAttribute("MECHANISMNAME");
			selected.setMechanismId(mechanismId);
			selected.setMechanismName(mechanismName);
			String from = (String) getSession().getAttribute("FROM");
			selected.setFrom(from);
			expenditrueCategorys = dictManager.getItemsByDictType(selected);
		}
		return expenditrueCategorys;
	}

	public void setExpenditrueCategorys(List<?> expenditrueCategorys) {
		this.expenditrueCategorys = expenditrueCategorys;
	}

	public List<?> getInvoiceTypes() {
		if(selected==null){
			selected=new Dict();
		}
		if(invoiceTypes==null) {
			selected.setDictType("invoice_type");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String mechanismName = (String) getSession().getAttribute("MECHANISMNAME");
			selected.setMechanismId(mechanismId);
			selected.setMechanismName(mechanismName);
			String from = (String) getSession().getAttribute("FROM");
			selected.setFrom(from);
			invoiceTypes = dictManager.getItemsByDictType(selected);
		}
		return invoiceTypes;
	}

	public void setInvoice_types(List<?> invoiceTypes) {
		this.invoiceTypes = invoiceTypes;
	}

	public List<?> getMechanismTypes() {
		if(selected==null){
			selected=new Dict();
		}
		if(mechanismTypes==null) {
			selected.setDictType("mechanism_type");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String mechanismName = (String) getSession().getAttribute("MECHANISMNAME");
			selected.setMechanismId(mechanismId);
			selected.setMechanismName(mechanismName);
			String from = (String) getSession().getAttribute("FROM");
			selected.setFrom(from);
			mechanismTypes = dictManager.getItemsByDictType(selected);
		}
		return mechanismTypes;
	}

	public void setMechanismTypes(List<?> mechanismTypes) {
		this.mechanismTypes = mechanismTypes;
	}

	public List<?> getExpenseTypes() {
		if(selected==null){
			selected=new Dict();
		}
		if(expenseTypes==null) {
			selected.setDictType("expense_type");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String mechanismName = (String) getSession().getAttribute("MECHANISMNAME");
			selected.setMechanismId(mechanismId);
			selected.setMechanismName(mechanismName);
			String from = (String) getSession().getAttribute("FROM");
			selected.setFrom(from);
			expenseTypes = dictManager.getItemsByDictType(selected);
		}
		return expenseTypes;
	}

	public void setExpenseTypes(List<?> expenseTypes) {
		this.expenseTypes = expenseTypes;
	}

	public List<?> getBrands() {
		if(selected==null){
			selected=new Dict();
		}
		if(brands==null) {
			selected.setDictType("brand");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String mechanismName = (String) getSession().getAttribute("MECHANISMNAME");
			selected.setMechanismId(mechanismId);
			selected.setMechanismName(mechanismName);
			String from = (String) getSession().getAttribute("FROM");
			selected.setFrom(from);
			brands = dictManager.getItemsByDictType(selected);
		}
		return brands;
	}

	public void setBrands(List<?> brands) {
		this.brands = brands;
	}

	public List<?> getProductTypes() {
		if(selected==null){
			selected=new Dict();
		}
		if(productTypes==null) {
			selected.setDictType("productType");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String mechanismName = (String) getSession().getAttribute("MECHANISMNAME");
			selected.setMechanismId(mechanismId);
			selected.setMechanismName(mechanismName);
			String from = (String) getSession().getAttribute("FROM");
			selected.setFrom(from);
			productTypes = dictManager.getItemsByDictType(selected);
		}
		return productTypes;
	}

	public List<?> getWarehouses() {
		if(selected==null){
			selected=new Dict();
		}
		if(warehouses==null) {
			selected.setDictType("warehouse");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String mechanismName = (String) getSession().getAttribute("MECHANISMNAME");
			selected.setMechanismId(mechanismId);
			selected.setMechanismName(mechanismName);
			String from = (String) getSession().getAttribute("FROM");
			selected.setFrom(from);
			warehouses = dictManager.getItemsByDictType(selected);
		}
		return warehouses;
	}


	public List<?> getRegions() {
		if(selected==null){
			selected=new Dict();
		}
		if(regions==null) {
			selected.setDictType("region");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String mechanismName = (String) getSession().getAttribute("MECHANISMNAME");
			selected.setMechanismId(mechanismId);
			selected.setMechanismName(mechanismName);
			String from = (String) getSession().getAttribute("FROM");
			selected.setFrom(from);
			regions = dictManager.getItemsByDictType(selected);
		}
		return regions;
	}


	public List<?> getSettlementAccounts() {
		if(selected==null){
			selected=new Dict();
		}
		if(settlementAccounts==null) {
			selected.setDictType("settlement_account");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String mechanismName = (String) getSession().getAttribute("MECHANISMNAME");
			selected.setMechanismId(mechanismId);
			selected.setMechanismName(mechanismName);
			String from = (String) getSession().getAttribute("FROM");
			selected.setFrom(from);
			settlementAccounts = dictManager.getItemsByDictType(selected);
		}
		
		return settlementAccounts;
	}


	public List<?> getArticleTypes() {

		if(selected==null){
			selected=new Dict();
		}
		if(articleTypes==null) {
			selected.setDictType("article_status");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String mechanismName = (String) getSession().getAttribute("MECHANISMNAME");
			selected.setMechanismId(mechanismId);
			selected.setMechanismName(mechanismName);
			String from = (String) getSession().getAttribute("FROM");
			selected.setFrom(from);
			articleTypes = dictManager.getItemsByDictType(selected);
		}
		return articleTypes;
	}

	public void setArticleTypes(List<?> articleTypes) {
		this.articleTypes = articleTypes;
	}

	public void setThemeTypes(List<?> themeTypes) {
		this.themeTypes = themeTypes;
	}

	public List<?> getBusinessopportunitiesUnits() {

		if(selected==null){
			selected=new Dict();
		}
		if(businessopportunitiesUnits==null) {
			selected.setDictType("businessopportunities_unit");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String mechanismName = (String) getSession().getAttribute("MECHANISMNAME");
			selected.setMechanismId(mechanismId);
			selected.setMechanismName(mechanismName);
			String from = (String) getSession().getAttribute("FROM");
			selected.setFrom(from);
			businessopportunitiesUnits = dictManager.getItemsByDictType(selected);
		}
		return businessopportunitiesUnits;
	}


	public List<?> getProductNames() {
		if(selected==null){
			selected=new Dict();
		}
		if(productNames==null) {
			selected.setDictType("product_name");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String mechanismName = (String) getSession().getAttribute("MECHANISMNAME");
			selected.setMechanismId(mechanismId);
			selected.setMechanismName(mechanismName);
			String from = (String) getSession().getAttribute("FROM");
			selected.setFrom(from);
			productNames = dictManager.getItemsByDictType(selected);
		}
		return productNames;
	}


	public List<?> getBusinessOpportunitySources() {
		if(selected==null){
			selected=new Dict();
		}
		if(businessOpportunitySources==null) {

			selected.setDictType("business_opportunity_source");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String mechanismName = (String) getSession().getAttribute("MECHANISMNAME");
			selected.setMechanismId(mechanismId);
			selected.setMechanismName(mechanismName);
			String from = (String) getSession().getAttribute("FROM");
			selected.setFrom(from);
			businessOpportunitySources = dictManager.getItemsByDictType(selected);
		}
		return businessOpportunitySources;
	}

	public List<Role> getModules() {
		if(modules==null) {
			String table = "role_module";
			String roleid = (String) getSession().getAttribute("ROLEID");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			modules=this.getModulesByRole(table, roleid, mechanismId, "字典数据");
		}
		return modules;
	}
	public void setModules(List<Role> modules) {
		this.modules = modules;
	}
	public List<?> getProjectTypes() {
		if(selected==null){
			selected=new Dict();
		}
		if(projectTypes==null) {
			selected.setDictType("project_type");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String mechanismName = (String) getSession().getAttribute("MECHANISMNAME");
			selected.setMechanismId(mechanismId);
			selected.setMechanismName(mechanismName);
			String from = (String) getSession().getAttribute("FROM");
			selected.setFrom(from);
			projectTypes = dictManager.getItemsByDictType(selected);
		}
		return projectTypes;
	}
	public List<?> getOss() {
		if(selected==null){
			selected=new Dict();
		}
		if(oss==null) {
			selected.setDictType("os");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String mechanismName = (String) getSession().getAttribute("MECHANISMNAME");
			selected.setMechanismId(mechanismId);
			selected.setMechanismName(mechanismName);
			String from = (String) getSession().getAttribute("FROM");
			selected.setFrom(from);
			oss = dictManager.getItemsByDictType(selected);
		}
		return oss;
	}
	public List<?> getDevices() {
		if(selected==null){
			selected=new Dict();
		}
		if(devices==null) {
			selected.setDictType("device");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String mechanismName = (String) getSession().getAttribute("MECHANISMNAME");
			selected.setMechanismId(mechanismId);
			selected.setMechanismName(mechanismName);
			String from = (String) getSession().getAttribute("FROM");
			selected.setFrom(from);
			devices = dictManager.getItemsByDictType(selected);
		}
		return devices;
	}
	public List<?> getBrowers() {
		if(selected==null){
			selected=new Dict();
		}
		if(browers==null) {
			selected.setDictType("brower");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String mechanismName = (String) getSession().getAttribute("MECHANISMNAME");
			selected.setMechanismId(mechanismId);
			selected.setMechanismName(mechanismName);
			String from = (String) getSession().getAttribute("FROM");
			selected.setFrom(from);
			browers = dictManager.getItemsByDictType(selected);
		}
		return browers;
	}
	public List<?> getProjectStatuss() {
		if(selected==null){
			selected=new Dict();
		}
		if(projectStatuss==null) {

			selected.setDictType("project_status");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String mechanismName = (String) getSession().getAttribute("MECHANISMNAME");
			selected.setMechanismId(mechanismId);
			selected.setMechanismName(mechanismName);
			String from = (String) getSession().getAttribute("FROM");
			selected.setFrom(from);
			projectStatuss = dictManager.getItemsByDictType(selected);
		}
		return projectStatuss;
	}
	public List<?> getBuildStatuss() {
		if(selected==null){
			selected=new Dict();
		}
		if(buildStatuss==null) {
			selected.setDictType("build_status");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String mechanismName = (String) getSession().getAttribute("MECHANISMNAME");
			selected.setMechanismId(mechanismId);
			selected.setMechanismName(mechanismName);
			String from = (String) getSession().getAttribute("FROM");
			selected.setFrom(from);
			buildStatuss = dictManager.getItemsByDictType(selected);
		}
		return buildStatuss;
	}
	public List<?> getRequirementTypes() {
		if(selected==null){
			selected=new Dict();
		}
		if(requirementTypes==null) {

			selected.setDictType("requirement_type");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String mechanismName = (String) getSession().getAttribute("MECHANISMNAME");
			selected.setMechanismId(mechanismId);
			selected.setMechanismName(mechanismName);
			String from = (String) getSession().getAttribute("FROM");
			selected.setFrom(from);
			requirementTypes = dictManager.getItemsByDictType(selected);
		}
		return requirementTypes;
	}
	public List<?> getRequirementStatuss() {
		if(selected==null){
			selected=new Dict();
		}
		if(requirementStatuss==null) {

			selected.setDictType("requirement_status");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String mechanismName = (String) getSession().getAttribute("MECHANISMNAME");
			selected.setMechanismId(mechanismId);
			selected.setMechanismName(mechanismName);
			String from = (String) getSession().getAttribute("FROM");
			selected.setFrom(from);
			requirementStatuss = dictManager.getItemsByDictType(selected);
		}
		return requirementStatuss;
	}
	public List<?> getDefectTypes() {
		if(selected==null){
			selected=new Dict();
		}
		if(defectTypes==null) {

			selected.setDictType("defect_type");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String mechanismName = (String) getSession().getAttribute("MECHANISMNAME");
			selected.setMechanismId(mechanismId);
			selected.setMechanismName(mechanismName);
			String from = (String) getSession().getAttribute("FROM");
			selected.setFrom(from);
			defectTypes = dictManager.getItemsByDictType(selected);
		}
		return defectTypes;
	}
	public List<?> getSeveritys() {
		if(selected==null){
			selected=new Dict();
		}
		if(severitys==null) {

			selected.setDictType("severity");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String mechanismName = (String) getSession().getAttribute("MECHANISMNAME");
			selected.setMechanismId(mechanismId);
			selected.setMechanismName(mechanismName);
			String from = (String) getSession().getAttribute("FROM");
			selected.setFrom(from);
			severitys = dictManager.getItemsByDictType(selected);
		}
		return severitys;
	}
	public List<?> getDefectStatuss() {
		if(selected==null){
			selected=new Dict();
		}
		if(defectStatuss==null) {
			selected.setDictType("defect_status");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String mechanismName = (String) getSession().getAttribute("MECHANISMNAME");
			selected.setMechanismId(mechanismId);
			selected.setMechanismName(mechanismName);
			String from = (String) getSession().getAttribute("FROM");
			selected.setFrom(from);
			defectStatuss = dictManager.getItemsByDictType(selected);
		}
		return defectStatuss;
	}
	public List<?> getDefectPrioritys() {
		if(selected==null){
			selected=new Dict();
		}
		if(defectPrioritys==null) {
			selected.setDictType("defect_priority");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String mechanismName = (String) getSession().getAttribute("MECHANISMNAME");
			selected.setMechanismId(mechanismId);
			selected.setMechanismName(mechanismName);
			String from = (String) getSession().getAttribute("FROM");
			selected.setFrom(from);
			defectPrioritys = dictManager.getItemsByDictType(selected);
		}
		return defectPrioritys;
	}
	public List<?> getTestingTypes() {
		if(selected==null){
			selected=new Dict();
		}
		if(testingTypes==null) {
			selected.setDictType("testing_type");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String mechanismName = (String) getSession().getAttribute("MECHANISMNAME");
			selected.setMechanismId(mechanismId);
			selected.setMechanismName(mechanismName);
			String from = (String) getSession().getAttribute("FROM");
			selected.setFrom(from);
			testingTypes = dictManager.getItemsByDictType(selected);
		}
		return testingTypes;
	}
	public List<?> getExecutionTypes() {
		if(selected==null){
			selected=new Dict();
		}
		if(executionTypes==null) {
			selected.setDictType("execution_type");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String mechanismName = (String) getSession().getAttribute("MECHANISMNAME");
			selected.setMechanismId(mechanismId);
			selected.setMechanismName(mechanismName);
			String from = (String) getSession().getAttribute("FROM");
			selected.setFrom(from);
			executionTypes = dictManager.getItemsByDictType(selected);
		}
		return executionTypes;
	}
	public List<?> getPrioritys() {
		if(selected==null){
			selected=new Dict();
		}
		if(prioritys==null) {

			selected.setDictType("priority");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String mechanismName = (String) getSession().getAttribute("MECHANISMNAME");
			selected.setMechanismId(mechanismId);
			selected.setMechanismName(mechanismName);
			String from = (String) getSession().getAttribute("FROM");
			selected.setFrom(from);
			prioritys = dictManager.getItemsByDictType(selected);
		}
		return prioritys;
	}
	public List<?> getTestCaseStatuss() {
		if(selected==null){
			selected=new Dict();
		}
		if(testCaseStatuss==null) {
			selected.setDictType("test_case_status");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String mechanismName = (String) getSession().getAttribute("MECHANISMNAME");
			selected.setMechanismId(mechanismId);
			selected.setMechanismName(mechanismName);
			String from = (String) getSession().getAttribute("FROM");
			selected.setFrom(from);
			testCaseStatuss = dictManager.getItemsByDictType(selected);
		}
		return testCaseStatuss;
	}
	public List<?> getCustomerCategorys() {
		if(selected==null){
			selected=new Dict();
		}
		if(customerCategorys==null) {

			selected.setDictType("customer_category");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String mechanismName = (String) getSession().getAttribute("MECHANISMNAME");
			selected.setMechanismId(mechanismId);
			selected.setMechanismName(mechanismName);
			String from = (String) getSession().getAttribute("FROM");
			selected.setFrom(from);
			customerCategorys = dictManager.getItemsByDictType(selected);
		}
	
		return customerCategorys;
	}
	

}
