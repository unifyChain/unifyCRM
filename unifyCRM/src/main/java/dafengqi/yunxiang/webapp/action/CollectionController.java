package dafengqi.yunxiang.webapp.action;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.alibaba.fastjson.JSON;

import dafengqi.yunxiang.model.Collection;
import dafengqi.yunxiang.model.CollectionDetail;
import dafengqi.yunxiang.model.Invoicing;
import dafengqi.yunxiang.model.Role;
import dafengqi.yunxiang.service.CollectionManager;
import dafengqi.yunxiang.util.JsfUtil;

@ManagedBean(name = "collectionController")
@SessionScoped
public class CollectionController extends BasePage implements Serializable {
	@FacesConverter(forClass = Collection.class)
	public static class CollectionszControllerConverter implements Converter {

		@Override
		public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {

			return null;
		}

		@Override
		public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
			if (object == null) {
				return null;
			}
			if (object instanceof Collection) { 
				Collection o = (Collection) object;
				return getStringKey(o.getId());
			} else {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
						"object {0} is of type {1}; expected type: {2}",
						new Object[] { object, object.getClass().getName(), Collection.class.getName() });
				return null;
			}
		}

		java.lang.Integer getKey(String value) {
			java.lang.Integer key;
			key = Integer.valueOf(value);
			return key;
		}

		String getStringKey(String string) {
			StringBuilder sb = new StringBuilder();
			sb.append(string);
			return sb.toString();
		}

	}

	private static final long serialVersionUID = 6498739293085202452L;

	private List<CollectionDetail> items = null;
	private List<CollectionDetail> itemsOfMy = null;

	private List<CollectionDetail> collectionDetailList = new ArrayList<CollectionDetail>();

	private CollectionDetail collectionDetail;
	
	private List<Collection> itemsRecord = null;
	private List<Collection> itemsRecordOfMy = null;
	private List<Invoicing> itemsInvoicing = null;
	private List<Invoicing> itemsInvoicingOfMy = null;
	private List<CollectionDetail> cooperationitems = null;
	private CollectionManager collectionManager;

	private Collection selected;
	private CollectionDetail selectedCollection;
	private Invoicing selectedInvoicing;
	private List<Role> modules;
	private List<Role> modelsRecord;
	private List<Role> modelsInvoicing;
	private String id;

	public CollectionController() {
	}
	public Invoicing resets() {
		selectedInvoicing=new Invoicing();
		return selectedInvoicing;
	}
	public Collection reset() {
		selected=new Collection();
		return selected;
	}
	public void addCollectionDetail() {
		collectionDetail.setId(UUID.randomUUID().toString());
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		collectionDetail.setDetailDate(df.format(collectionDetail.getCollectionDate()));
		collectionDetail.setCollectionDate(collectionDetail.getCollectionDate());
		BigDecimal hkje = BigDecimal.ZERO;
		BigDecimal zje = BigDecimal.ZERO;
		for (CollectionDetail collectionDetail : collectionDetailList) {
			hkje = hkje.add(new BigDecimal(collectionDetail.getCollectionAmount()));
		}
		if (selected.getCollectionAmount() != null) {
			zje = new BigDecimal(selected.getCollectionAmount());
		}
		if(hkje.compareTo(zje)==0||hkje.compareTo(zje)==1) {
			JsfUtil.warn("不可继续新增!");
		}else{
			collectionDetailList.add(collectionDetail);
		}
		collectionDetail = new CollectionDetail();
	}
	public void calcHkje() {
		BigDecimal zje = BigDecimal.ZERO;
		BigDecimal hkje = BigDecimal.ZERO;
		BigDecimal hkzb = BigDecimal.ZERO;
		BigDecimal ssje = BigDecimal.ZERO;
		BigDecimal xjzb = BigDecimal.ZERO;
		BigDecimal yjhkje = BigDecimal.ZERO;
		for (CollectionDetail collectionDetails : collectionDetailList) {
			if(collectionDetail.getId()==null) {
				hkje = hkje.add(new BigDecimal(collectionDetails.getCollectionAmount()));
				hkzb = hkzb.add(new BigDecimal(collectionDetails.getCollectionProportion()));
			}else {
				if(collectionDetail.getId().equals(collectionDetails.getId())) {
					
				}else {
					hkje = hkje.add(new BigDecimal(collectionDetails.getCollectionAmount()));
					hkzb = hkzb.add(new BigDecimal(collectionDetails.getCollectionProportion()));
				}
			}
		}
		if (selected.getCollectionAmount() != null) {
			zje = new BigDecimal(selected.getCollectionAmount());
		}
		xjzb=hkzb.add(new BigDecimal(collectionDetail.getCollectionProportion()));
		if(xjzb.compareTo(new BigDecimal(100))==1) {
			JsfUtil.warn("不可大于合同金额!");
			collectionDetail.setCollectionAmount("0.00");
		}else {
			
			yjhkje =new BigDecimal(collectionDetail.getCollectionProportion()).multiply(zje).divide(new BigDecimal("100"));
			collectionDetail.setCollectionAmount(""+yjhkje);
		}
	}
	public void calcHkzb() {
		BigDecimal zje = BigDecimal.ZERO;
		BigDecimal hkje = BigDecimal.ZERO;
		BigDecimal ssje = BigDecimal.ZERO;
		BigDecimal yjhkzb = BigDecimal.ZERO;
		for (CollectionDetail collectionDetails : collectionDetailList) {
			if(collectionDetail.getId()==null) {
				hkje = hkje.add(new BigDecimal(collectionDetails.getCollectionAmount()));
			}else {
				if(collectionDetail.getId().equals(collectionDetails.getId())) {
					
				}else {
					hkje = hkje.add(new BigDecimal(collectionDetails.getCollectionAmount()));
				}
			}
		}
		if (selected.getCollectionAmount() != null) {
			zje = new BigDecimal(selected.getCollectionAmount());
		}
		ssje=zje.subtract(hkje);
		if(new BigDecimal(collectionDetail.getCollectionAmount()).compareTo(ssje)==1) {
			JsfUtil.warn("不可大于合同金额!");
			collectionDetail.setCollectionProportion("0.00");
		}else {
			yjhkzb = new BigDecimal(collectionDetail.getCollectionAmount()).divide(zje, 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
			collectionDetail.setCollectionProportion(""+yjhkzb);
		}
	}


	public String create() {
		if (selected != null) {
			setEmbeddableKeys();
			try {
				selected.setId(UUID.randomUUID().toString());
				String mechanismId = (String) getSession().getAttribute("MECHANISMID");
				selected.setMechanismId(mechanismId);
				selected.setId(UUID.randomUUID().toString());
				selected.setCreateDate(df.format(new Date()));
				selected.setUpdateDate(df.format(new Date()));
				String username = (String) getSession().getAttribute("USERNAME");
				String firstname = (String) getSession().getAttribute("NAME");
				String departmentid = (String) getSession().getAttribute("DEPARTMENTID");
				String departmentmc = (String) getSession().getAttribute("DEPARTMENTNAME");
				selected.setDepartmentId(departmentid);
				selected.setDepartmentName(departmentmc);
				selected.setCreateId(username);
				selected.setCreateName(firstname);
				int i = collectionManager.saveCollection(selected,collectionDetailList);
				if (i == 0) {
					JsfUtil.warn("回款计划新增失败!");
				} else if (i == 1) {
					this.sysLog("回款计划新增", JSON.toJSONString(selected));
					JsfUtil.info("回款计划新增成功!");
					selected=new Collection();
				} else {
					JsfUtil.error("回款计划新增错误!");
				}
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, "错误!");
			}
		}
		if (!JsfUtil.isValidationFailed()) {
			items = null; 
			itemsOfMy = null; 
		}
		return "collections";
	}
	public String createRecord() {
		if (selected != null) {
			setEmbeddableKeys();
			try {
				int k = 0;
				selected.setId(UUID.randomUUID().toString());
				String mechanismId = (String) getSession().getAttribute("MECHANISMID");
				selected.setMechanismId(mechanismId);
				selected.setId(UUID.randomUUID().toString());
				selected.setCreateDate(df.format(new Date()));
				selected.setUpdateDate(df.format(new Date()));
				String username = (String) getSession().getAttribute("USERNAME");
				String firstname = (String) getSession().getAttribute("NAME");
				selected.setCreateId(username);
				selected.setCreateName(firstname);
				String departmentid = (String) getSession().getAttribute("DEPARTMENTID");
				String departmentmc = (String) getSession().getAttribute("DEPARTMENTNAME");
				selected.setDepartmentId(departmentid);
				selected.setDepartmentName(departmentmc);
				int i = collectionManager.saveCollectionRecord(selected);
				if (i == 0) {
					JsfUtil.warn("回款记录新增失败!");
				} else if (i == 1) {
					this.sysLog("回款记录新增", JSON.toJSONString(selected));
					JsfUtil.info("回款记录新增成功!");
				} else {
					JsfUtil.error("回款记录新增错误!");
					}
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, "错误!");
			}
		}
		if (!JsfUtil.isValidationFailed()) {
			selected=new Collection();
			itemsRecordOfMy = null; 
			itemsRecord = null; 
		}
		return "collectionRecords";
	}
	public String createInvoicing() {
		if (selectedInvoicing != null) {
			setEmbeddableKeys();
			try {
				selectedInvoicing.setId(UUID.randomUUID().toString());
				String mechanismId = (String) getSession().getAttribute("MECHANISMID");
				selectedInvoicing.setId(UUID.randomUUID().toString());
				selectedInvoicing.setCreateDate(df.format(new Date()));
				selectedInvoicing.setUpdateDate(df.format(new Date()));
				String username = (String) getSession().getAttribute("USERNAME");
				String firstname = (String) getSession().getAttribute("NAME");
				selectedInvoicing.setCreateId(username);
				selectedInvoicing.setCreateName(firstname);
				String departmentid = (String) getSession().getAttribute("DEPARTMENTID");
				String departmentmc = (String) getSession().getAttribute("DEPARTMENTNAME");
				selectedInvoicing.setDepartmentId(departmentid);
				selectedInvoicing.setMechanismId(mechanismId);;
				selectedInvoicing.setDepartmentName(departmentmc);
				int i = collectionManager.saveCollectioncreateinvoicing(selectedInvoicing);
				if (i == 0) {
					JsfUtil.warn("开票记录失!");
				} else if (i == 1) {
					this.sysLog("开票记录新", JSON.toJSONString(selected));
					JsfUtil.info("开票记录成!");
				} else {
					JsfUtil.error("开票记录错!");
				}
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, "错误!");
			}
		}
		if (!JsfUtil.isValidationFailed()) {
			itemsInvoicing = null; 
			selectedInvoicing=new Invoicing();
		}
		return "billingRecords";
	}

	public String remove() {
		id = getRequest().getParameter("id");
		return null;
	}
	public void destroy() {
			setEmbeddableKeys();
			try {
				String username = (String) getSession().getAttribute("USERNAME");
				String mechanismId = (String) getSession().getAttribute("MECHANISMID");
					int i = this.remove("crm_payment_collection_plan_subordinate", id,mechanismId);
					if (i == 0) {
						JsfUtil.warn("回款计划删除失败!");
					} else if (i == 1) {
						this.sysLog("回款计划删除", id);
						JsfUtil.info("回款计划删除成功!");
					} else if (i == -2) {
						JsfUtil.error("回款计划数据被引用，请先删除子数据!");
					} else {
						JsfUtil.error("回款计划删除错误!");
					}

			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, "错误!");
			}
		if (!JsfUtil.isValidationFailed()) {
			selected = null; 
			selectedCollection = null; 
			items = null; 
			itemsOfMy = null; 
			itemsRecord = null; 
			itemsRecordOfMy = null; 
			itemsInvoicing = null; 
			itemsInvoicingOfMy = null; 
		}
	}
	public void destroyRecord() {
			setEmbeddableKeys();
			try {
//				if(selected.getExamine().equals("启用")){
//					JsfUtil.warn("数据已启,请勿删除!");
//				}else{
						String username = (String) getSession().getAttribute("USERNAME");
						String mechanismId = (String) getSession().getAttribute("MECHANISMID");
							int i = this.remove("crm_collection", id,mechanismId);
							if (i == 0) {
								JsfUtil.warn("回款记录删除失败!");
							} else if (i == 1) {
								this.sysLog("回款记录删除", JSON.toJSONString(id));
								JsfUtil.info("回款记录删除成功!");
							} else if (i == -2) {
								JsfUtil.error("回款记录数据被引用，请先删除子数据!");
							} else {
								JsfUtil.error("回款记录删除错误!");
							}
						
//				}
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, "错误!");
			}
		if (!JsfUtil.isValidationFailed()) {
			selected = null; 
			items = null; 
			itemsOfMy = null; 
			itemsRecord = null; 
			itemsRecordOfMy = null; 
			itemsInvoicing = null; 
			itemsInvoicingOfMy = null; 
		}
	}
	public void destroyss() {
			setEmbeddableKeys();
			try {
//				if(selected.getExamine().equals("启用")){
//					JsfUtil.warn("数据已启,请勿删除!");
//				}else{
					int k = 0;
					String mechanismId = (String) getSession().getAttribute("MECHANISMID");
					int i = this.remove("crm_invoicing", id,mechanismId);
					if (i == 0) {
						JsfUtil.warn("开票记录删除失!");
					} else if (i == 1) {
						this.sysLog("开票记录删", JSON.toJSONString(selected));
						JsfUtil.info("开票记录删除成!");
					} else if (i == -2) {
						JsfUtil.error("开票记录数据被引用，请先删除子数据!");
					} else {
						JsfUtil.error("开票记录删除错!");
					}
//				}
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, "错误!");
			}
		if (!JsfUtil.isValidationFailed()) {
			selected = null; 
			items = null; 
			itemsOfMy = null; 
			itemsRecord = null; 
			itemsRecordOfMy = null; 
			itemsInvoicing = null; 
			itemsInvoicingOfMy = null; 
		}
	}


	public List<CollectionDetail> calcht() {

		// if a user's id is passed in
		if(selected.getContractId()==null){
			selected.setCollectionAmount("");
			selected.setCollectionDate("");
		}else{

			if(selected.getContractId().equals("")){
				selected.setCollectionAmount("");
				selected.setCollectionDate("");
			}else{

				String[] sjlx = selected.getContractId().split("!_");
				selected.setCollectionAmount(sjlx[2]);
				selected.setCollectionDate(sjlx[3]);
				collectionDetailList = collectionManager.editmx(selected);
			}
		}
		
//		selected = xsdManager.calcZqk(selected);
//		selected.setZqk(selected.getZqk());
//		qk = selected.getZqk();
//		selected.setLsqk(selected.getZqk());
		return collectionDetailList;
	}

	public List<CollectionDetail> calcContract() {

		// if a user's id is passed in
		if(selected.getContractId()==null){
			selected.setCollectionAmount("");
			selected.setCollectionDate("");
		}else{

			if(selected.getContractId().equals("")){
				selected.setCollectionAmount("");
				selected.setCollectionDate("");
			}else{

				String[] sjlx = selected.getContractId().split("!_");
				selected.setCollectionAmount(sjlx[2]);
				selected.setCollectionDate(sjlx[3]);
				collectionDetailList = collectionManager.editmx(selected);
			}
		}
		
		return collectionDetailList;
	}
	public Collection view() {
			if(selected==null){
				selected=new Collection();
			}
			String id = getRequest().getParameter("id");
			selected.setId(id);
			selected = collectionManager.view(selected);
			collectionDetailList=selected.getList();
		return selected;
	}
	public Collection viewRecord() {
		if(selected==null){
			selected=new Collection();
		}
		String id = getRequest().getParameter("id");
		selected.setId(id);
		selected = collectionManager.viewRecord(selected);
	return selected;
}
	public Invoicing viewInvoicing() {
		if(selectedInvoicing==null){
			selectedInvoicing=new Invoicing();
		}
		String id = getRequest().getParameter("id");
		selectedInvoicing.setId(id);
		selectedInvoicing = collectionManager.viewInvoicing(selectedInvoicing);
	return selectedInvoicing;
}
	public List<CollectionDetail> getItems() {
		if(selected==null){
			selected=new Collection();
		}
		String mechanismId = (String) getSession().getAttribute("MECHANISMID");
		String from = (String) getSession().getAttribute("FROM");
		String departmentid = (String) getSession().getAttribute("BMJLID");
		if(departmentid==null) {
			departmentid="";
		}
		selected.setMechanismId(mechanismId);
		selected.setFrom(from);
		selected.setDepartmentId(departmentid);
		String username = (String) getSession().getAttribute("USERNAME");
		selected.setCreateName(username);
		items = collectionManager.getItems(selected);
		return items;
	}
	public List<CollectionDetail> getItemsOfMy() {
		if(selected==null){
			selected=new Collection();
		}
		String mechanismId = (String) getSession().getAttribute("MECHANISMID");
		String from = (String) getSession().getAttribute("FROM");
		String departmentid = (String) getSession().getAttribute("BMJLID");
		if(departmentid==null) {
			departmentid="";
		}
		selected.setMechanismId(mechanismId);
		selected.setFrom(from);
		selected.setDepartmentId(departmentid);
		String username = (String) getSession().getAttribute("USERNAME");
		selected.setCreateName(username);
		itemsOfMy = collectionManager.getItemsOfMy(selected);
		return itemsOfMy;
	}
	public void setItemsOfMy(List<CollectionDetail> itemsOfMy) {
		this.itemsOfMy = itemsOfMy;
	}

	public List<Collection> getItemsRecord() {
		if (itemsRecord == null) {
			if(selected==null){
				selected=new Collection();
			}
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String from = (String) getSession().getAttribute("FROM");
			String departmentid = (String) getSession().getAttribute("BMJLID");
			if(departmentid==null) {
				departmentid="";
			}
			selected.setMechanismId(mechanismId);
			selected.setFrom(from);
			selected.setDepartmentId(departmentid);
			String username = (String) getSession().getAttribute("USERNAME");
			selected.setCreateName(username);
			itemsRecord = collectionManager.getItemsRecord(selected);
		}
		return itemsRecord;
	}
	public List<Collection> getItemsRecordOfMy() {
		if (itemsRecordOfMy == null) {
			if(selected==null){
				selected=new Collection();
			}
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String from = (String) getSession().getAttribute("FROM");
			String departmentid = (String) getSession().getAttribute("BMJLID");
			if(departmentid==null) {
				departmentid="";
			}
			selected.setMechanismId(mechanismId);
			selected.setFrom(from);
			selected.setDepartmentId(departmentid);
			String username = (String) getSession().getAttribute("USERNAME");
			selected.setCreateName(username);
			itemsRecordOfMy = collectionManager.getItemsRecordOfMy(selected);
		}
		return itemsRecordOfMy;
	}
	public List<Invoicing> getItemsInvoicing() {
		if (itemsInvoicing == null) {
			if(selectedInvoicing==null){
				selectedInvoicing=new Invoicing();
			}
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			selectedInvoicing.setMechanismId(mechanismId);
			String dyid = (String) getSession().getAttribute("DYID");
			selectedInvoicing.setId(dyid);
			String from = (String) getSession().getAttribute("FROM");
			selectedInvoicing.setFrom(from);
			String departmentid = (String) getSession().getAttribute("BMJLID");
			if(departmentid==null) {
				departmentid="";
			}
			selectedInvoicing.setDepartmentId(departmentid);
			String username = (String) getSession().getAttribute("USERNAME");
			selectedInvoicing.setCreateName(username);
			itemsInvoicing = collectionManager.getItemsInvoicing(selectedInvoicing);
		}
		return itemsInvoicing;
	}
	public List<Invoicing> getItemsInvoicingOfMy() {
		if (itemsInvoicingOfMy == null) {
			if(selectedInvoicing==null){
				selectedInvoicing=new Invoicing();
			}
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			selectedInvoicing.setMechanismId(mechanismId);
			String dyid = (String) getSession().getAttribute("DYID");
			selectedInvoicing.setId(dyid);
			String from = (String) getSession().getAttribute("FROM");
			selectedInvoicing.setFrom(from);
			String departmentid = (String) getSession().getAttribute("BMJLID");
			if(departmentid==null) {
				departmentid="";
			}
			selectedInvoicing.setDepartmentId(departmentid);
			String username = (String) getSession().getAttribute("USERNAME");
			selectedInvoicing.setCreateName(username);
			itemsInvoicingOfMy = collectionManager.getItemsInvoicingOfMy(selectedInvoicing);
		}
		return itemsInvoicingOfMy;
	}

	public void setItemsInvoicing(List<Invoicing> itemsInvoicing) {
		this.itemsInvoicing = itemsInvoicing;
	}
	public List<CollectionDetail> getCooperationitems() {
		if (cooperationitems == null) {
			if(selected==null){
				selected=new Collection();
			}
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			selected.setMechanismId(mechanismId);
			String username = (String) getSession().getAttribute("USERNAME");
			selected.setCreateName(username);
			cooperationitems = collectionManager.getItems(selected);
		}
		return cooperationitems;
	}

	public CollectionManager getCollectionManager() {
		return collectionManager;
	}

	public Collection getSelected() {
		return selected;
	}

	protected void initializeEmbeddableKey() {
	}
	public String prepareCreateRecord() {
		selected = new Collection();
		collectionDetail = new CollectionDetail();
		collectionDetailList = new ArrayList<CollectionDetail>();
		return "collectionrecordForm";
	}
	public String prepareCreate() {
		selected = new Collection();
		collectionDetail = new CollectionDetail();
		collectionDetailList = new ArrayList<CollectionDetail>();
		return "collectionForm";
	}
	public String prepareCreateinvoicing() {
		selectedInvoicing = new Invoicing();
		return "collectionlnvoicingForm";
	}
	
	public CollectionDetail prepareCreatemx() {

		collectionDetail = new CollectionDetail();
		initializeEmbeddableKey();
		return collectionDetail;
	}

	public void editCollectionDetail() {
		collectionDetailList.remove(collectionDetail);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		collectionDetail.setDetailDate(df.format(collectionDetail.getCollectionDate()));
		collectionDetail.setCollectionDate(collectionDetail.getCollectionDate());
		collectionDetailList.add(collectionDetail);
	}
	public void deleteAction() {
		collectionDetailList.remove(collectionDetail);
	}

	protected void setEmbeddableKeys() {
	}

	public void setCollectionManager(CollectionManager collectionManager) {
		this.collectionManager = collectionManager;
	}

	public void setCollectionszManager(CollectionManager collectionManager) {
		this.collectionManager = collectionManager;
	}

	public void setSelected(Collection selected) {
		this.selected = selected;
	}


	public void setItemsRecord(List<Collection> itemsRecord) {
		this.itemsRecord = itemsRecord;
	}


	public void setCooperationitems(List<CollectionDetail> cooperationitems) {
		this.cooperationitems = cooperationitems;
	}

	public List<CollectionDetail> getCollectionDetailList() {
		return collectionDetailList;
	}

	public void setCollectionDetailList(List<CollectionDetail> collectionDetailList) {
		this.collectionDetailList = collectionDetailList;
	}

	public CollectionDetail getCollectionDetail() {
		return collectionDetail;
	}

	public void setCollectionDetail(CollectionDetail collectionDetail) {
		this.collectionDetail = collectionDetail;
	} 

	public Invoicing getSelectedInvoicing() {
		return selectedInvoicing;
	}

	public void setSelectedInvoicing(Invoicing selectedInvoicing) {
		this.selectedInvoicing = selectedInvoicing;
	}

	public CollectionDetail getSelectedCollection() {
		return selectedCollection;
	}
	public void setSelectedCollection(CollectionDetail selectedCollection) {
		this.selectedCollection = selectedCollection;
	}
	public List<Role> getModules() {
		if(modules==null) {
			String table = "role_module";
			String roleid = (String) getSession().getAttribute("ROLEID");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			modules=this.getModulesByRole(table, roleid, mechanismId, "回款计划");
		}
		return modules;
	
	}
	public void setModules(List<Role> modules) {
		this.modules = modules;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<Role> getModelsRecord() {
		if(modelsRecord==null) {
			String table = "role_module";
			String roleid = (String) getSession().getAttribute("ROLEID");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			modelsRecord=this.getModulesByRole(table, roleid, mechanismId, "回款记录");
		}
		return modelsRecord;
	
	}
	public void setModelsRecord(List<Role> modelsRecord) {
		this.modelsRecord = modelsRecord;
	}
	public List<Role> getModelsInvoicing() {
		if(modelsInvoicing==null) {
			String table = "role_module";
			String roleid = (String) getSession().getAttribute("ROLEID");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			modelsInvoicing=this.getModulesByRole(table, roleid, mechanismId, "开票记录");
		}
		return modelsInvoicing;
	}
	public void setModelsInvoicing(List<Role> modelsInvoicing) {
		this.modelsInvoicing = modelsInvoicing;
	}
	public void setItemsRecordOfMy(List<Collection> itemsRecordOfMy) {
		this.itemsRecordOfMy = itemsRecordOfMy;
	}
	public void setItemsInvoicingOfMy(List<Invoicing> itemsInvoicingOfMy) {
		this.itemsInvoicingOfMy = itemsInvoicingOfMy;
	}

	public List<CollectionDetail> search() {
		items = null; 
		itemsOfMy = null; 
		itemsRecord = null; 
		itemsRecordOfMy = null; 
		itemsInvoicing = null; 
		itemsInvoicingOfMy = null; 
		return items;
	}

	public List<Invoicing> searchs() {
		itemsInvoicing = null; 
		itemsInvoicingOfMy = null; 
		return itemsInvoicing;
	}
	public void invoicingDate() {
		selectedInvoicing.setInvoicingDateRange(new ArrayList<>());
	}
	public void invoicingDateRange() {
		selectedInvoicing.setInvoicingDates("");
	}
	public void dateRange() {
		selected.setCollectionDate("");
	}
	public void collectionDate() {
		selected.setDateRange(new ArrayList<>());
	}
	public void dateRanges() {
		selected.setCollectionDatess("");
	}
	public void collectionDates() {
		selected.setDateRanges(new ArrayList<>());
	}



}
