package dafengqi.yunxiang.webapp.action;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.TreeNode;
import org.primefaces.model.file.UploadedFile;

import com.alibaba.fastjson.JSON;

import dafengqi.yunxiang.model.BusinessOpportunity;
import dafengqi.yunxiang.model.Contacts;
import dafengqi.yunxiang.model.Contract;
import dafengqi.yunxiang.model.Cost;
import dafengqi.yunxiang.model.Customer;
import dafengqi.yunxiang.model.FollowUp;
import dafengqi.yunxiang.model.Role;
import dafengqi.yunxiang.service.CustomerManager;
import dafengqi.yunxiang.util.JsfUtil;

@ManagedBean(name = "customerController")
@SessionScoped
public class CustomerController extends BasePage implements Serializable {
	@FacesConverter(forClass = Customer.class)
	public static class CustomerszControllerConverter implements Converter {

		@Override
		public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {

			return null;
		}

		@Override
		public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
			if (object == null) {
				return null;
			}
			if (object instanceof Customer) {
				Customer o = (Customer) object;
				return getStringKey(o.getId());
			} else {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
						"object {0} is of type {1}; expected type: {2}",
						new Object[] { object, object.getClass().getName(), Customer.class.getName() });
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
	
	private List<Customer> items = null;
	private List<Contacts> itemsContacts = null;
	private List<BusinessOpportunity> itemsBusinessOpportunity = null;
	private List<Contract> itemsContract = null;
	private List<Cost> itemsCost = null;
	private List<FollowUp> itemsFollowup = null;
	private List<Customer> itemsOfMy = null;
	private List<Customer> itemsOfMyCustomer = null;
	private List<Customer> itemsOfMySupplier = null;
	private List<Customer> selectedCustomers ;

	private List<Customer> customerHighseas = null;
	private List<Role> modules;
	private List<Role> modelsHighseas;
    private StreamedContent file;
	
	private List<?> parentCustomer = new ArrayList();
	

	private CustomerManager customerManager;

	private Customer selected;
	private Customer selecteds;
	private String id;

	public Customer reset() {
		selected=new Customer();
		return selected;
	}

	public void displaySelectedMultipledepartment(TreeNode nodes) {
			if(selected==null) {
				selected=new Customer();
			}
			if(nodes==null){
				JsfUtil.warn("请选择上级机构!");
			}else{
				String nodestr = nodes.getData().toString();
				String[] nodearr = nodestr.split("-");
				selected.setDepartmentIds(nodearr[1]);
				selected.setDepartmentName(nodearr[0]);
			}
	}

	public void relationMechanism() {
		selecteds=new Customer();
		String mechanismId = (String) getSession().getAttribute("MECHANISMID");
		if(mechanismId.equals(selected.getRelationMechanismId())) {
			JsfUtil.warn("不支持绑定自己机构!");
		}else {
			selecteds = customerManager.relationMechanismCustomer(selected);
			if (!"".equals(selecteds.getRelationMechanismName()) && selecteds.getRelationMechanismName() != null) {
				selected.setRelationMechanismId(selecteds.getRelationMechanismId());
				selected.setRelationMechanismName(selecteds.getRelationMechanismName());
			} else {
				selected.setRelationMechanismId("");
				selected.setRelationMechanismName("");
				JsfUtil.warn("无此机构!");
			}
		}
	}
	public CustomerController() {
        file = DefaultStreamedContent.builder()
                .name("客户导入模板(大风起).xls")
                .contentType("application/x-xls")
                .stream(() -> FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/template/客户导入模板(大风起).xls"))
                .build();
	}
	public String create() {
		if (selected != null) {
			setEmbeddableKeys();
			try {
				int k = 0;
				String mechanismId = (String) getSession().getAttribute("MECHANISMID");
				String id = this.getID("crm_customer", mechanismId,"客户");
				selected.setId(id);

				
				selected.setMechanismId(mechanismId);
				selected.setCreateDate(df.format(new Date()));;
				String username = (String) getSession().getAttribute("USERNAME");
				String firstname = (String) getSession().getAttribute("NAME");
				selected.setCreateId(username);
				selected.setCreateName(firstname);
				int i = customerManager.saveCustomer(selected);
				if (i == 0) {
					JsfUtil.warn("客户新增失败!");
				} else if (i == 1) {
					this.sysLog("客户新增", JSON.toJSONString(selected));
					JsfUtil.info("客户新增成功!");
				} else {
					JsfUtil.error("客户新增错误!");
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
		return "customers";
	}
	public String createFollowUp() {
		if (selected != null) {
			setEmbeddableKeys();
			try {
				int i = 0;
				String mechanismid = (String) getSession().getAttribute("MECHANISMID");
				selected.setMechanismId(mechanismid);
				String username = (String) getSession().getAttribute("USERNAME");
				String firstname = (String) getSession().getAttribute("NAME");
				selected.setCreateId(username);
				selected.setCreateName(firstname);
				selected.setCreateDate(df.format(new Date()));
				String departmentid = (String) getSession().getAttribute("DEPARTMENTID");
				i =  customerManager.followUp(departmentid,selected.getId(), selected.getFolloType(),selected.getTime(),selected.getContent(),selected.getName(),selected.getFollowStatus(),selected.getContactsId(),selected.getContactsName(),selected.getNextFollowTime(),selected.getCreateId(),selected.getCreateDate(),selected.getMechanismId(),selected.getCreateName(),"客户");
//				
				if (i == 0) {
					JsfUtil.warn("客户写跟进失败!");
				} else if (i == 1) {
					this.sysLog("客户写跟进", JSON.toJSONString(selected));
					JsfUtil.info("客户写跟进成功!");
				} else {
					JsfUtil.error("客户写跟进错误!");
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
		return "customers";
	}
	public List<Customer> search() {
		items = null; 
		itemsOfMy = null; 
		customerHighseas=null;
		return items;
	}
	public void dateRange() {
		selected.setCreateDate("");
		
	}
	public void dateRanges() {
		selected.setNextFollowTimes("");
	}
	public void updateDateRange() {
		selected.setUpdateDate("");
		
	}
	public void createDate() {
		selected.setCreateDateRange(new ArrayList<>());
	}
	public void nextFollowTimes() {
		selected.setNextFollowTimeDateRange(new ArrayList<>());
	}
	public void updateDate() {
		selected.setUpdateDateRange(new ArrayList<>());
	}
	public String destroy() {
			setEmbeddableKeys();
			try {
				String mechanismid = (String) getSession().getAttribute("MECHANISMID");
						int i = customerManager.removeCustomer("crm_customer", id,mechanismid);
						if (i == 0) {
							JsfUtil.warn("客户删除失败!");
						} else if (i == 1) {
							this.sysLog("客户删除", JSON.toJSONString(id));
							JsfUtil.info("客户删除成功!");
						} else if (i == -2) {
							JsfUtil.error("客户数据被引用，请先删除子数据!");
						} else {
							JsfUtil.error("客户删除错误!");
						}
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, "错误!");
			}
		if (!JsfUtil.isValidationFailed()) {
			selected = null; 
			items = null; 
			itemsOfMy = null; 
		}
		return "customers";
	}

	public String remove() {
		id = getRequest().getParameter("id");
	return "customers";
}
	public String removes() {
		id = getRequest().getParameter("id");
	return "customerhighseas";
}
	public String customerHighseass() {
		if (selected != null) {
			setEmbeddableKeys();
			try {
//				if(selected.getExamine().equals("启用")){
//					JsfUtil.warn("数据已启用,请勿删除!");
//				}else{
					int k = 0;
					String table = "customer";
					String mechanismId = (String) getSession().getAttribute("MECHANISMID");
						int i = customerManager.rhighseas("crm_customer", id,"gh",mechanismId);
						if (i == 0) {
							JsfUtil.warn("失败!");
						} else if (i == 1) {
							JsfUtil.info("成功!");
						} else {
							JsfUtil.error("错误!");
						}
//				}
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, "错误!");
			}
		} else {
			JsfUtil.warn("请选择需要转移的数据!");
		}
		if (!JsfUtil.isValidationFailed()) {
			selected = null; 
			items = null; 
			itemsOfMy = null; 
			customerHighseas = null; 
		}
		return "customers";
	}
	public String customer() {
		if (selected != null) {
			setEmbeddableKeys();
			try {
//				if(selected.getExamine().equals("启用")){
//					JsfUtil.warn("数据已启用,请勿删除!");
//				}else{
					int k = 0;
					String table = "customer";
					String mechanismId = (String) getSession().getAttribute("MECHANISMID");
					String firstname = (String) getSession().getAttribute("NAME");
					Long userid = (Long) getSession().getAttribute("USERID");
					String username = (String) getSession().getAttribute("USERNAME");
					String departmentid = (String) getSession().getAttribute("DEPARTMENTID");
					String departmentname = (String) getSession().getAttribute("DEPARTMENTNAME");
					selected.setMechanismId(mechanismId);
					selected.setPersonId(""+userid);
					selected.setPersonName(username);
					selected.setDepartmentId(departmentid);
					selected.setDepartmentName(departmentname);
					selected.setCreateId(username);
					selected.setCreateName(firstname);
						int i = customerManager.rhighseas("crm_customer", id,"",selected);
						if (i == 0) {
							JsfUtil.warn("失败!");
						} else if (i == 1) {
							JsfUtil.info("成功!");
						} else {
							JsfUtil.error("错误!");
						}
//				}
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, "错误!");
			}
		} else {
			JsfUtil.warn("请选择需要转移的数据!");
		}
		if (!JsfUtil.isValidationFailed()) {
			selected = null; 
			items = null; 
			itemsOfMy = null; 
			customerHighseas = null; 
		}
		return "customerhighseas";
	}
	public String customerHighseasdxs() {
		String tzym="";
		if (selected != null) {
			setEmbeddableKeys();
			try {
//				if(selected.getExamine().equals("启用")){
//					JsfUtil.warn("数据已启用,请勿删除!");
//				}else{
					int k = 0;
					if(selectedCustomers.size()==0) {
						JsfUtil.warn("请选择数据转移!");
					}else {
						tzym="customerHighseas";
						for(Customer customer :selectedCustomers) {
							String mechanismId = (String) getSession().getAttribute("MECHANISMID");
							int i = customerManager.rhighseas("customer", selected.getId(),"gh",mechanismId);
							if (i == 0) {
								JsfUtil.warn("失败!");
							} else if (i == 1) {
								JsfUtil.info("成功!");
							} else {
								JsfUtil.error("错误!");
							}
						}
					}
//				}
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, "错误!");
			}
		} else {
			JsfUtil.warn("请选择需要转移的数据!");
		}
		if (!JsfUtil.isValidationFailed()) {
			selected = null; 
			items = null; 
			itemsOfMy = null; 
			customerHighseas = null; 
		}
		return tzym;
	}

	public String customerHighseasss() {
		if (selected != null) {
			setEmbeddableKeys();
			try {
//				if(selected.getExamine().equals("启用")){
//					JsfUtil.warn("数据已启用,请勿删除!");
//				}else{
					int k = 0;
					String table = "kh";
					String mechanismId = (String) getSession().getAttribute("MECHANISMID");
					int i = customerManager.rhighseas("customer", selected.getId(),"",mechanismId);
						if (i == 0) {
							JsfUtil.warn("失败!");
						} else if (i == 1) {
							JsfUtil.info("成功!");
						} else {
							JsfUtil.error("错误!");
						}
//				}
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, "错误!");
			}
		} else {
			JsfUtil.warn("请选择需要转移的数据!");
		}
		if (!JsfUtil.isValidationFailed()) {
			selected = null; 
			items = null; 
			itemsOfMy = null; 
			customerHighseas = null; 
		}
		return "customers";
	}
	public List<Customer> getItems() {
		if (items == null) {
			if(selected==null){
				selected=new Customer();
			}
			String mechanismid = (String) getSession().getAttribute("MECHANISMID");
			String from = (String) getSession().getAttribute("FROM");
			String departmentid = (String) getSession().getAttribute("BMJLID");
			if(departmentid==null) {
				departmentid="";
			}
			String sfbrxg = (String) getSession().getAttribute("SFBRXG");
			selected.setMechanismId(mechanismid);
			selected.setFrom(from);
			selected.setDepartmentId(departmentid);;
			String username = (String) getSession().getAttribute("USERNAME");
			selected.setCreateName(username);;
			items = customerManager.getItems(selected);
		}
		return items;
	}
	
//	public List<Customer> getCustomerHighseas() {
//		if (customerHighseas == null) {
//			if(selected==null){
//				selected=new Customer();
//			}
//			selected.setKhlx("gh");
//			String mechanismid = (String) getSession().getAttribute("MECHANISMID");
//			String from = (String) getSession().getAttribute("FROM");
//			String departmentid = (String) getSession().getAttribute("DEPARTMENTID");
//			String sfbrxg = (String) getSession().getAttribute("SFBRXG");
//			selected.setSfbrxg(sfbrxg);
//			selected.setMechanismId(mechanismid);
//			selected.setFrom(from);
//			selected.setBmid(departmentid);
//			String username = (String) getSession().getAttribute("USERNAME");
//			selected.setCjrmc(username);
//			customerHighseas = customerManager.getItemsgh(selected);
//		}
//		return customerHighseas;
//	}
	

	public CustomerManager getCustomerManager() {
		return customerManager;
	} 

	public Customer getSelected() {
		return selected;
	}

	protected void initializeEmbeddableKey() {
	}
	public String prepareCreate() {
		selected = new Customer();
		selected.setType("新增");
		Long userid = (Long) getSession().getAttribute("USERID");
		String name = (String) getSession().getAttribute("NAME");
		String departmentid = (String) getSession().getAttribute("DEPARTMENTID");
		String departmentmc = (String) getSession().getAttribute("DEPARTMENTNAME");
		selected.setDepartmentId(departmentid+"!_"+departmentmc);
		selected.setPersonId(userid+"!_"+name);

		String mechanismId = (String) getSession().getAttribute("MECHANISMID");
		String id = this.getID("crm_customer", mechanismId,"客户");
		selected.setId(id);
		
		return "customerForm";
	}
	public String edit() {

		String id = getRequest().getParameter("id");
		if(selected==null){
			selected=new Customer();
		}
		selected.setId(id);
		String mechanismId = (String) getSession().getAttribute("MECHANISMID");
		selected.setMechanismId(mechanismId);
		selected = customerManager.edit(selected);
		selected.setType("编辑");
		return "customerForm";
	}
	public String viewHighseas() {
		String id = getRequest().getParameter("id");
		if(selected==null){
			selected=new Customer();
		}
		selected.setId(id);
		String mechanismId = (String) getSession().getAttribute("MECHANISMID");
		selected.setMechanismId(mechanismId);
		getSession().setAttribute("DYID", id);
		selected = customerManager.edit(selected);
		itemsContacts=selected.getContactsList();
		itemsBusinessOpportunity=selected.getBusinessOpportunityList();
		itemsContract=selected.getContractList();
		itemsCost=selected.getCostList();
		itemsFollowup=selected.getFollowupList();
		selected.setType("查看");
		return "customerhighseasForm";
	}
	public String view() {
		String id = getRequest().getParameter("id");
		if(selected==null){
			selected=new Customer();
		}
		selected.setId(id);
		String mechanismId = (String) getSession().getAttribute("MECHANISMID");
		selected.setMechanismId(mechanismId);
		getSession().setAttribute("DYID", id);
		selected = customerManager.edit(selected);
		itemsContacts=selected.getContactsList();
		itemsBusinessOpportunity=selected.getBusinessOpportunityList();
		itemsContract=selected.getContractList();
		itemsCost=selected.getCostList();
		itemsFollowup=selected.getFollowupList();
		selected.setType("查看");
		return "customerForm";
	}
	public Customer viewkhgh() {
		String id = getRequest().getParameter("id");
		if(selected==null){
			selected=new Customer();
		}
		selected.setId(id);
		getSession().setAttribute("DYID", id);
		selected = customerManager.edit(selected);
		selected.setType("查看");
		return selected;
	}
	protected void setEmbeddableKeys() {
	}

	public void setCustomerManager(CustomerManager customerManager) {
		this.customerManager = customerManager;
	}

	public void setCustomerszManager(CustomerManager customerManager) {
		this.customerManager = customerManager;
	}

	public void setSelected(Customer selected) {
		this.selected = selected;
	}
	public String update() {
		if (selected != null) {
			setEmbeddableKeys();
			try {
					String mechanismid = (String) getSession().getAttribute("MECHANISMID");
					selected.setMechanismId(mechanismid);
					selected.setUpdateDate(df.format(new Date()));
					String username = (String) getSession().getAttribute("USERNAME");
					String firstname = (String) getSession().getAttribute("NAME");
					selected.setUpdateId(username);
					selected.setUpdateName(firstname);
					int i = customerManager.update(selected);
					if (i == 0) {
						JsfUtil.warn("客户编辑失败!");
					} else if (i == 1) {
						this.sysLog("客户编辑", JSON.toJSONString(selected));
						JsfUtil.info("客户编辑成功!");
					} else {
						JsfUtil.error("客户编辑错误!");
					}
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, "错误!");
			}
		} else {
			JsfUtil.warn("失败!");
		}
		if (!JsfUtil.isValidationFailed()) {
			items = null; 
			itemsOfMy = null; 
		}
		return "customers";
	}

	public void uploadExcel(FileUploadEvent e) throws IOException {
		UploadedFile uploadedFile = e.getFile();
		String nodepath = this.getClass().getResource("/").getPath();
		String filePath = nodepath.substring(1, nodepath.length() - 16);
		filePath=filePath+"upload/";
		byte[] bytes = null;
		if (null != uploadedFile) {
			bytes = uploadedFile.getContent();
			String filename = FilenameUtils.getName(uploadedFile.getFileName());
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath + filename)));
			stream.write(bytes);
			stream.close();
			excelToDb(filePath + filename);
		}
	}

	public void excelToDb(String filePath) {
		String cjr = getRequest().getRemoteUser();
		String jgid = (String) getSession().getAttribute("MECHANISMID");
			String jgmc = (String) getSession().getAttribute("MECHANISMNAME");
		String departmentid = (String) getSession().getAttribute("DEPARTMENTID");
			String departmentname = (String) getSession().getAttribute("DEPARTMENTNAME");
		String result = customerManager.excelToDb(cjr, jgid, filePath, jgmc, departmentid, departmentname);
//
//		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, getMessage("info.tip"), result);
//		RequestContext.getCurrentInstance().showMessageInDialog(message);
		items = null;
		getItems();

	}
	public List<?> getParentCustomer() {
		if(selected==null){
			selected=new Customer();
		}
		String mechanismid = (String) getSession().getAttribute("MECHANISMID");
		String from = (String) getSession().getAttribute("FROM");
		String departmentid = (String) getSession().getAttribute("BMJLID");
		if(departmentid==null) {
			departmentid="";
		}
		String sfbrxg = (String) getSession().getAttribute("SFBRXG");
		selected.setMechanismId(mechanismid);
		selected.setFrom(from);
		selected.setDepartmentId(departmentid);;
		String username = (String) getSession().getAttribute("USERNAME");
		selected.setCreateName(username);;
		parentCustomer = customerManager.getItems(selected);
		return parentCustomer;
	}

	public void setParentCustomer(List<?> parentCustomer) {
		this.parentCustomer = parentCustomer;
	}
	


	public List<Customer> getSelectedCustomers() {
		return selectedCustomers;
	}

	public void setSelectedCustomers(List<Customer> selectedCustomers) {
		this.selectedCustomers = selectedCustomers;
	}
	public List<Role> getModules() {
		if(modules==null) {
			String table = "role_module";
			String roleid = (String) getSession().getAttribute("ROLEID");
			String mechanismid = (String) getSession().getAttribute("MECHANISMID");
			modules=this.getModulesByRole(table, roleid, mechanismid, "客户");
		}
		return modules;
	}
	public void setModules(List<Role> modules) {
		this.modules = modules;
	}
	public List<Role> getModelsHighseas() {
		if(modelsHighseas==null) {
			String table = "role_module";
			String roleid = (String) getSession().getAttribute("ROLEID");
			String mechanismid = (String) getSession().getAttribute("MECHANISMID");
			modelsHighseas=this.getModulesByRole(table, roleid, mechanismid, "客户公海");
		}
	
		return modelsHighseas;
	}
	public void setModelsHighseas(List<Role> modelsHighseas) {
		this.modelsHighseas = modelsHighseas;
	}
	public List<Customer> getCustomerHighseas() {
		if(selected==null){
			selected=new Customer();
		}
		if (customerHighseas == null) {
			String mechanismid = (String) getSession().getAttribute("MECHANISMID");
			String from = (String) getSession().getAttribute("FROM");
			String departmentid = (String) getSession().getAttribute("DEPARTMENTID");
			selected.setMechanismId(mechanismid);
			selected.setFrom(from);
			selected.setDepartmentId(departmentid);;
			String username = (String) getSession().getAttribute("USERNAME");
			selected.setCreateName(username);;
			customerHighseas = customerManager.getItemsHighseas(selected);
		}
		return customerHighseas;
	}

	public void setCustomerHighseas(List<Customer> customerHighseas) {
		this.customerHighseas = customerHighseas;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<Customer> getItemsOfMy() {
		if(selected==null){
			selected=new Customer();
		}
		String mechanismid = (String) getSession().getAttribute("MECHANISMID");
		String from = (String) getSession().getAttribute("FROM");
		String departmentid = (String) getSession().getAttribute("DEPARTMENTID");
		selected.setMechanismId(mechanismid);
		selected.setFrom(from);
		selected.setDepartmentId(departmentid);
		String username = (String) getSession().getAttribute("USERNAME");
		selected.setCreateName(username);
		selected.setFrom("客户");
		itemsOfMy = customerManager.getItemsOfMy(selected);
		return itemsOfMy;
	}
	public void setItemsOfMy(List<Customer> itemsOfMy) {
		this.itemsOfMy = itemsOfMy;
	}
	
	public List<Customer> getItemsOfMyCustomer() {
		
		if(selected==null){
			selected=new Customer();
		}
		String mechanismid = (String) getSession().getAttribute("MECHANISMID");
		String from = (String) getSession().getAttribute("FROM");
		String departmentid = (String) getSession().getAttribute("DEPARTMENTID");
		selected.setMechanismId(mechanismid);
		selected.setFrom(from);
		selected.setDepartmentId(departmentid);
		String username = (String) getSession().getAttribute("USERNAME");
		selected.setCreateName(username);
		selected.setFrom("客户");
		itemsOfMyCustomer = customerManager.getItemsOfMyData(selected);
		
		
		return itemsOfMyCustomer;
	}
	
	public List<Customer> getItemsOfMySupplier() {
		
		if(selected==null){
			selected=new Customer();
		}
		String mechanismid = (String) getSession().getAttribute("MECHANISMID");
		String from = (String) getSession().getAttribute("FROM");
		String departmentid = (String) getSession().getAttribute("DEPARTMENTID");
		selected.setMechanismId(mechanismid);
		selected.setFrom(from);
		selected.setDepartmentId(departmentid);
		String username = (String) getSession().getAttribute("USERNAME");
		selected.setCreateName(username);
		selected.setFrom("供应商");
		itemsOfMySupplier = customerManager.getItemsOfMyData(selected);
		
		
		return itemsOfMySupplier;
	}
	public void setItemsOfMySupplier(List<Customer> itemsOfMySupplier) {
		this.itemsOfMySupplier = itemsOfMySupplier;
	}
	public StreamedContent getFile() {
		return file;
	}
	public void setFile(StreamedContent file) {
		this.file = file;
	}
	public List<Contacts> getItemsContacts() {
		return itemsContacts;
	}
	public void setItemsContacts(List<Contacts> itemsContacts) {
		this.itemsContacts = itemsContacts;
	}
	public List<BusinessOpportunity> getItemsBusinessOpportunity() {
		return itemsBusinessOpportunity;
	}
	public void setItemsBusinessOpportunity(List<BusinessOpportunity> itemsBusinessOpportunity) {
		this.itemsBusinessOpportunity = itemsBusinessOpportunity;
	}
	public List<Contract> getItemsContract() {
		return itemsContract;
	}
	public void setItemsContract(List<Contract> itemsContract) {
		this.itemsContract = itemsContract;
	}
	public List<Cost> getItemsCost() {
		return itemsCost;
	}
	public void setItemsCost(List<Cost> itemsCost) {
		this.itemsCost = itemsCost;
	}
	public List<FollowUp> getItemsFollowup() {
		return itemsFollowup;
	}
	public void setItemsFollowup(List<FollowUp> itemsFollowup) {
		this.itemsFollowup = itemsFollowup;
	}

	public void setItemsOfMyCustomer(List<Customer> itemsOfMyCustomer) {
		this.itemsOfMyCustomer = itemsOfMyCustomer;
	}

	public Customer getSelecteds() {
		return selecteds;
	}

	public void setSelecteds(Customer selecteds) {
		this.selecteds = selecteds;
	}



}
