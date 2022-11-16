
package dafengqi.yunxiang.webapp.action;
  
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import dafengqi.yunxiang.model.Inquiry;
import dafengqi.yunxiang.model.InquiryProduct;
import dafengqi.yunxiang.model.Product;
import dafengqi.yunxiang.model.Role;
import dafengqi.yunxiang.service.InquiryManager;
import dafengqi.yunxiang.util.JsfUtil;

@ManagedBean(name = "inquiryController")
@SessionScoped
public class InquiryController extends BasePage implements Serializable {


	private static final long serialVersionUID = 6498739293085202452L;

	private InquiryManager inquiryManager;

	private List<Inquiry> items = null;

	private InquiryProduct inquiryProduct = new InquiryProduct();
	
	private List<InquiryProduct> inquiryProductList = new ArrayList<InquiryProduct>();

	private BigDecimal PreferentialRate = BigDecimal.ZERO;

	private List<Role> modules;
	
	private String id;

	private Inquiry selected;

	private InquiryProduct selectedProduct;

	public InquiryController() {
	}


	public void supplierSelected() {
		if (selected.getSupplierId() != null && !selected.getSupplierId().equals("")) {
		}
	}

	

	public void bj(BigDecimal bj) {
		String id = inquiryProduct.getId();
		if(id!=null) {
			System.out.println(id);
			System.out.println(bj);
			inquiryProductList.remove(inquiryProduct);
			addmx();
		}
	}
	public void addmx() {
		inquiryProduct.setId(UUID.randomUUID().toString());
		inquiryProductList.add(inquiryProduct);
		inquiryProduct = new InquiryProduct();
	}

	public List<Role> getModules() {
		if(modules==null) {
			String table = "role_module";
			String roleid = (String) getSession().getAttribute("ROLEID");
			String mechanismid = (String) getSession().getAttribute("MECHANISMID");
			modules=this.getModulesByRole(table, roleid, mechanismid, "询价单");
		}
		return modules;
	}

	public void setModules(List<Role> modules) {
		this.modules = modules;
	}
	


	public Inquiry reset() {
		selected=new Inquiry();
		return selected;
	}
	

	public String editmx() {
		String jgid = (String) getSession().getAttribute("MECHANISMID");
		return "";
	}
	
	public void editInquiryProduct() {
		BigDecimal a = BigDecimal.ZERO;
		if (selected.getSupplierId() == null || selected.getSupplierId().equals("")) {
			JsfUtil.info("请先选择供应商!");
		} else {
			if (inquiryProduct.getProductId() != "") {
				if (inquiryProduct.getQuantity().equals(a)) {
					JsfUtil.info("数量不可为0");
				} else {
					
				}
			} else {
				JsfUtil.warn("请选择商品");
			}
		}
	}
	public void addInquiryProduct() {
		inquiryProduct.setId(UUID.randomUUID().toString());
		BigDecimal a = BigDecimal.ZERO;
		if (selected.getSupplierId() == null || selected.getSupplierId().equals("")) {
			JsfUtil.info("请先选择供应商!");
		} else {
			if (inquiryProduct.getProductId() != "") {
				
				if (inquiryProduct.getQuantity().equals(a)) {
					JsfUtil.info("数量不可为0");
				} else {
					
					
					inquiryProductList.add(inquiryProduct);
					
				}
			} else {
				JsfUtil.warn("请选择商品");
			}
		}
		inquiryProduct = new InquiryProduct();
	}
	public String getMasterBarcode() {
//		String jgid = (String) getSession().getAttribute("MECHANISMID");
//		Product dto = inquiryManager.getProductByBarCode(inquiryProduct.getBarCode(),jgid);
//		if (!"".equals(dto.getId()) && dto.getId() != null) {
//			inquiryProduct.setPrice(dto.getRetailPrice());
//			inquiryProduct.setProductId(dto.getId());
//			inquiryProduct.setProductName(dto.getTitle());
//		} else {
//			inquiryProduct.setPrice(new BigDecimal(0));
////			inquiryProduct.setPurchasePrice(new BigDecimal(0));
//			inquiryProduct.setProductId("");
//			inquiryProduct.setProductName("");
//		}
		return "inquiryForm";
	}

	public String create() {
		if (selected != null) {
			try {
				
				String jgid = (String) getSession().getAttribute("MECHANISMID");
				String jgname = (String) getSession().getAttribute("MECHANISMNAME");
				
				selected.setMechanismId(jgid);
				selected.setMechanismName(jgname);
				selected.setId(UUID.randomUUID().toString());
				selected.setCreateDate(df.format(new Date()));
				selected.setUpdateDate(df.format(new Date()));
				String username = (String) getSession().getAttribute("USERNAME");
				String firstname = (String) getSession().getAttribute("NAME");
				selected.setCreateId(username);
				selected.setCreateName(firstname);
				
				int i = inquiryManager.saveInquiry(selected,inquiryProductList);
				if (i == 0) {
					JsfUtil.warn("询价单新增失败!");
				} else if (i == 1) {
					JsfUtil.info("询价单新增成功!");
				} else {
					JsfUtil.error("询价单新增错误!");
				}
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, "询价单新增错误!");
			}
		}
		if (!JsfUtil.isValidationFailed()) {
			items = null;
			selected = null;
			inquiryProductList=null;
		}
		return "inquirys";
	}


	
	public String remove() {
		String bm = getRequest().getParameter("id");
		id=bm;
		return "inquirys";
	}

	
	public String destroy() {
		String url="inquirys";
		if (selected != null) {
			setEmbeddableKeys();
			try {
					String mechanismId = (String) getSession().getAttribute("MECHANISMID");
					int i = this.remove("inquiry", id,mechanismId);
					if (i == 0) {
						JsfUtil.warn("询价单删除失败!");
					} else if (i == 1) {
						JsfUtil.info("询价单删除成功!");
						this.remove("inquirydetail", "inquiry_id", id,mechanismId);
					} else {
						JsfUtil.error("询价单删除错误!");
					}
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, "询价单删除错误!");
			}
		} else {
			JsfUtil.warn("询价单删除失败!");
		}
		if (!JsfUtil.isValidationFailed()) {
			selected = null; 
			items = null;
		}
		
		return url;
	}
	
	

	public List<Inquiry> getItems() {
		if (items == null) {
			if(selected==null){
				selected=new Inquiry();
			}
//			selected.setCustomerType("qb");
			String jgid = (String) getSession().getAttribute("MECHANISMID");
			selected.setMechanismId(jgid);
			String username = (String) getSession().getAttribute("USERNAME");
			selected.setCreateName(username);
			items = inquiryManager.getItems(selected);
		}
		return items;
	}
	
	

	


	public List<Inquiry> search() {
		
		if(selected==null){
			selected=new Inquiry();
		}
		String jgid = (String) getSession().getAttribute("MECHANISMID");
		selected.setMechanismId(jgid);
		String username = (String) getSession().getAttribute("USERNAME");
		selected.setCreateName(username);
		items = inquiryManager.getItems(selected);

		return items;
	}

	public InquiryManager getInquiryManager() {
		return inquiryManager;
	}

	public Inquiry getSelected() {
		return selected;
	}

	protected void initializeEmbeddableKey() {
	}

	public String prepareCreate() {
		selected = new Inquiry();
		inquiryProductList = new ArrayList<InquiryProduct>();
		selected.setInquiryProductList(null);
		selected.setType("新增");
		return "inquiryDetailForm";
	}
	public String prepareCreatemx() {
		inquiryProduct = new InquiryProduct();
		return "";
	}

	public String edit() {

		String id = getRequest().getParameter("id");
		if(selected==null){
			selected=new Inquiry();
		}
		selected.setId(id);
		String mechanismId = (String) getSession().getAttribute("MECHANISMID");
		selected.setMechanismId(mechanismId);
		selected = inquiryManager.edit(selected);
		inquiryProductList=selected.getInquiryProductList();
		selected.setType("编辑");
		return "inquiryDetailForm";
	}
	public String edits() {

		String id = getRequest().getParameter("id");
		if(selected==null){
			selected=new Inquiry();
		}
		selected.setId(id);
		String mechanismId = (String) getSession().getAttribute("MECHANISMID");
		selected.setMechanismId(mechanismId);
		selected = inquiryManager.edit(selected);
		inquiryProductList=selected.getInquiryProductList();
		selected.setType("编辑");
		return "inquiryDetailForms";
	}

	public String view() {
		String id = getRequest().getParameter("id");
		if(selected==null){
			selected=new Inquiry();
		}
		selected.setId(id);
		String mechanismId = (String) getSession().getAttribute("MECHANISMID");
		selected.setMechanismId(mechanismId);
		selected = inquiryManager.edit(selected);
		inquiryProductList=selected.getInquiryProductList();
		selected.setType("查看");
		return "inquiryDetailForm";
	}
	protected void setEmbeddableKeys() {
	}

	public void setInquiryManager(InquiryManager inquiryManager) {
		this.inquiryManager = inquiryManager;
	}

	public void setInquiryszManager(InquiryManager inquiryManager) {
		this.inquiryManager = inquiryManager;
	}

	public void setSelected(Inquiry selected) {
		this.selected = selected;
	}

	public String update() {
		if (selected != null) {
			setEmbeddableKeys();
			try {
				
				String jgid = (String) getSession().getAttribute("MECHANISMID");
				selected.setMechanismId(jgid);
				selected.setUpdateDate(df.format(new Date()));
				String username = (String) getSession().getAttribute("USERNAME");
				String firstname = (String) getSession().getAttribute("NAME");
				selected.setUpdateId(username);
				selected.setUpdateName(firstname);
				selected.setType("否");
				
				int i = inquiryManager.update(selected,inquiryProductList);
				if (i == 0) {
					JsfUtil.warn("询价单更新失败!");
				} else if (i == 1) {
					JsfUtil.info("询价单更新成功!");
					
				} else {
					JsfUtil.error("询价单更新错误!");
				}
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, "询价单更新错误!");
			}
		} else {
			JsfUtil.warn("询价单更新失败!");
		}
		if (!JsfUtil.isValidationFailed()) {
			items = null;
			selected = null;
			inquiryProductList=null;
		}
		return "inquirys";
	}
	public String updates() {
		if (selected != null) {
			setEmbeddableKeys();
			try {
				
				String jgid = (String) getSession().getAttribute("MECHANISMID");
				selected.setMechanismId(jgid);
				selected.setUpdateDate(df.format(new Date()));
				String username = (String) getSession().getAttribute("USERNAME");
				String firstname = (String) getSession().getAttribute("NAME");
				selected.setUpdateId(username);
				selected.setUpdateName(firstname);
				selected.setType("是");
				
				int i = inquiryManager.update(selected,inquiryProductList);
				if (i == 0) {
					JsfUtil.warn("询价单更新失败!");
				} else if (i == 1) {
					JsfUtil.info("询价单更新成功!");
					
				} else {
					JsfUtil.error("询价单更新错误!");
				}
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, "询价单更新错误!");
			}
		} else {
			JsfUtil.warn("询价单更新失败!");
		}
		if (!JsfUtil.isValidationFailed()) {
			items = null;
			selected = null;
			inquiryProductList=null;
		}
		return "inquirys";
	}

	public InquiryProduct getInquiryProduct() {
		return inquiryProduct;
	}

	public void setInquiryProduct(InquiryProduct inquiryProduct) {
		this.inquiryProduct = inquiryProduct;
	}

	public List<InquiryProduct> getInquiryProductList() {
		return inquiryProductList;
	}

	public void setInquiryProductList(List<InquiryProduct> inquiryProductList) {
		this.inquiryProductList = inquiryProductList;
	}


	public BigDecimal getPreferentialRate() {
		return PreferentialRate;
	}


	public void setPreferentialRate(BigDecimal preferentialRate) {
		PreferentialRate = preferentialRate;
	}


	public InquiryProduct getSelectedProduct() {
		return selectedProduct;
	}


	public void setSelectedProduct(InquiryProduct selectedProduct) {
		this.selectedProduct = selectedProduct;
	}

	



}
