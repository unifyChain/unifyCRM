
package dafengqi.yunxiang.webapp.action;
  
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import javax.servlet.http.HttpServletResponse;

import dafengqi.yunxiang.model.Product;
import dafengqi.yunxiang.model.Quote;
import dafengqi.yunxiang.model.QuoteProduct;
import dafengqi.yunxiang.model.Role;
import dafengqi.yunxiang.service.QuoteManager;
import dafengqi.yunxiang.util.JsfUtil;

@ManagedBean(name = "quoteController")
@SessionScoped
public class QuoteController extends BasePage implements Serializable {


	private static final long serialVersionUID = 6498739293085202452L;

	private QuoteManager quoteManager;

	private List<Quote> items = null;

	private QuoteProduct quoteProduct = new QuoteProduct();
	
	private List<QuoteProduct> quoteProductList = new ArrayList<QuoteProduct>();

	private BigDecimal PreferentialRate = BigDecimal.ZERO;

	private List<Role> modules;
	
	private String id;

	private Quote selected;

	private QuoteProduct selectedProduct;

	public QuoteController() {
	}



	

	public void bj(BigDecimal bj) {
		String id = quoteProduct.getId();
		if(id!=null) {
			System.out.println(id);
			System.out.println(bj);
			quoteProductList.remove(quoteProduct);
			addmx();
		}
	}
	public void addmx() {
		quoteProduct.setId(UUID.randomUUID().toString());
		quoteProductList.add(quoteProduct);
		quoteProduct = new QuoteProduct();
	}

	public List<Role> getModules() {
		if(modules==null) {
			String table = "role_module";
			String roleid = (String) getSession().getAttribute("ROLEID");
			String mechanismid = (String) getSession().getAttribute("MECHANISMID");
			modules=this.getModulesByRole(table, roleid, mechanismid, "报价单");
		}
		return modules;
	}

	public void setModules(List<Role> modules) {
		this.modules = modules;
	}
	


	public Quote reset() {
		selected=new Quote();
		return selected;
	}
	

	public String editmx() {
		String jgid = (String) getSession().getAttribute("MECHANISMID");
		return "";
	}
	
	public void editQuoteProduct() {
		BigDecimal a = BigDecimal.ZERO;
//		if (selected.getCustomerId() == null || selected.getCustomerId().equals("")) {
//			JsfUtil.info("请先选择客户!");
//		} else {
			if (quoteProduct.getProductId() != "") {
				if (quoteProduct.getQuantity().equals(a)) {
					JsfUtil.info("数量不可为0");
				} else {
					
				}
			} else {
				JsfUtil.warn("请选择商品");
			}
//		}
	}
	public void addQuoteProduct() {
		quoteProduct.setId(UUID.randomUUID().toString());
		BigDecimal a = BigDecimal.ZERO;
//		if (selected.getCustomerId() == null || selected.getCustomerId().equals("")) {
//			JsfUtil.info("请先选择客户!");
//		} else {
			if (quoteProduct.getProductId() != "") {
				
				if (quoteProduct.getQuantity().equals(a)) {
					JsfUtil.info("数量不可为0");
				} else {
					
					
					quoteProductList.add(quoteProduct);
					
				}
			} else {
				JsfUtil.warn("请选择商品");
			}
//		}
		quoteProduct = new QuoteProduct();
	}
	public String getMasterBarcode() {
//		String jgid = (String) getSession().getAttribute("MECHANISMID");
//		Product dto = quoteManager.getProductByBarCode(quoteProduct.getBarCode(),jgid);
//		if (!"".equals(dto.getId()) && dto.getId() != null) {
//			quoteProduct.setPrice(dto.getRetailPrice());
//			quoteProduct.setProductId(dto.getId());
//			quoteProduct.setProductName(dto.getTitle());
//		} else {
//			quoteProduct.setPrice(new BigDecimal(0));
////			quoteProduct.setPurchasePrice(new BigDecimal(0));
//			quoteProduct.setProductId("");
//			quoteProduct.setProductName("");
//		}
		return "quoteForm";
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
				
				int i = quoteManager.saveQuote(selected,quoteProductList);
				if (i == 0) {
					JsfUtil.warn("报价单新增失败!");
				} else if (i == 1) {
					JsfUtil.info("报价单新增成功!");
				} else {
					JsfUtil.error("报价单新增错误!");
				}
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, "报价单新增错误!");
			}
		}
		if (!JsfUtil.isValidationFailed()) {
			items = null;
			selected = null;
			quoteProductList=null;
		}
		return "quotes";
	}


	
	public String remove() {
		String bm = getRequest().getParameter("id");
		id=bm;
		return "quotes";
	}

	
	public String destroy() {
		String url="quotes";
		if (selected != null) {
			setEmbeddableKeys();
			try {
					String mechanismId = (String) getSession().getAttribute("MECHANISMID");
					int i = this.remove("quote", id,mechanismId);
					if (i == 0) {
						JsfUtil.warn("报价单删除失败!");
					} else if (i == 1) {
						JsfUtil.info("报价单删除成功!");
						this.remove("quotedetail", "quote_id", id,mechanismId);
					} else {
						JsfUtil.error("报价单删除错误!");
					}
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, "报价单删除错误!");
			}
		} else {
			JsfUtil.warn("报价单删除失败!");
		}
		if (!JsfUtil.isValidationFailed()) {
			selected = null; 
			items = null;
		}
		
		return url;
	}
	
	

	public List<Quote> getItems() {
		if (items == null) {
			if(selected==null){
				selected=new Quote();
			}
//			selected.setCustomerType("qb");
			String jgid = (String) getSession().getAttribute("MECHANISMID");
			selected.setMechanismId(jgid);
			String username = (String) getSession().getAttribute("USERNAME");
			selected.setCreateName(username);
			items = quoteManager.getItems(selected);
		}
		return items;
	}
	
	

	


	public List<Quote> search() {
		
		if(selected==null){
			selected=new Quote();
		}
		String jgid = (String) getSession().getAttribute("MECHANISMID");
		selected.setMechanismId(jgid);
		String username = (String) getSession().getAttribute("USERNAME");
		selected.setCreateName(username);
		items = quoteManager.getItems(selected);

		return items;
	}

	public QuoteManager getQuoteManager() {
		return quoteManager;
	}

	public Quote getSelected() {
		return selected;
	}

	protected void initializeEmbeddableKey() {
	}

	public String prepareCreate() {
		selected = new Quote();
		quoteProductList = new ArrayList<QuoteProduct>();
		selected.setQuoteProductList(null);
		selected.setType("新增");
		return "quoteDetailForm";
	}
	public String prepareCreatemx() {
		quoteProduct = new QuoteProduct();
		return "";
	}

	public String edit() {

		String id = getRequest().getParameter("id");
		if(selected==null){
			selected=new Quote();
		}
		selected.setId(id);
		String mechanismId = (String) getSession().getAttribute("MECHANISMID");
		selected.setMechanismId(mechanismId);
		selected = quoteManager.edit(selected);
		quoteProductList=selected.getQuoteProductList();
		selected.setType("编辑");
		return "quoteDetailForm";
	}
	public String edits() {

		String id = getRequest().getParameter("id");
		if(selected==null){
			selected=new Quote();
		}
		selected.setId(id);
		String mechanismId = (String) getSession().getAttribute("MECHANISMID");
		selected.setMechanismId(mechanismId);
		selected = quoteManager.edit(selected);
		quoteProductList=selected.getQuoteProductList();
		selected.setType("编辑");
		return "quoteDetailForms";
	}

	public String view() {
		String id = getRequest().getParameter("id");
		if(selected==null){
			selected=new Quote();
		}
		selected.setId(id);
		String mechanismId = (String) getSession().getAttribute("MECHANISMID");
		selected.setMechanismId(mechanismId);
		selected = quoteManager.edit(selected);
		quoteProductList=selected.getQuoteProductList();
		selected.setType("查看");
		return "quoteDetailForm";
	}
		 
	    private HttpServletResponse response=getResponse();
	    
	public HttpServletResponse  ppt() throws IOException  {
		 try {
	            // path是指欲下载的文件的路径。
	            File file = new File("D:\\测试ppt.pptx");
	            // 取得文件名。
	            String filename = file.getName();
	            // 取得文件的后缀名。
	            String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();
	  
	            // 以流的形式下载文件。
	            InputStream fis = new BufferedInputStream(new FileInputStream("D:\\测试ppt.pptx"));
	            byte[] buffer = new byte[fis.available()];
	            fis.read(buffer);
	            fis.close();
	            // 清空response
	            response.reset();
	            // 设置response的Header
	            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
	            response.addHeader("Content-Length", "" + file.length());
	            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
	            response.setContentType("application/octet-stream");
	            toClient.write(buffer);
	            toClient.flush();
	            toClient.close();
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
		return response;

     }
	protected void setEmbeddableKeys() {
	}

	public void setQuoteManager(QuoteManager quoteManager) {
		this.quoteManager = quoteManager;
	}

	public void setQuoteszManager(QuoteManager quoteManager) {
		this.quoteManager = quoteManager;
	}

	public void setSelected(Quote selected) {
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
				
				int i = quoteManager.update(selected,quoteProductList);
				if (i == 0) {
					JsfUtil.warn("报价单更新失败!");
				} else if (i == 1) {
					JsfUtil.info("报价单更新成功!");
					
				} else {
					JsfUtil.error("报价单更新错误!");
				}
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, "报价单更新错误!");
			}
		} else {
			JsfUtil.warn("报价单更新失败!");
		}
		if (!JsfUtil.isValidationFailed()) {
			items = null;
			selected = null;
			quoteProductList=null;
		}
		return "quotes";
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
				
				int i = quoteManager.update(selected,quoteProductList);
				if (i == 0) {
					JsfUtil.warn("报价单更新失败!");
				} else if (i == 1) {
					JsfUtil.info("报价单更新成功!");
					
				} else {
					JsfUtil.error("报价单更新错误!");
				}
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, "报价单更新错误!");
			}
		} else {
			JsfUtil.warn("报价单更新失败!");
		}
		if (!JsfUtil.isValidationFailed()) {
			items = null;
			selected = null;
			quoteProductList=null;
		}
		return "quotes";
	}

	public QuoteProduct getQuoteProduct() {
		return quoteProduct;
	}

	public void setQuoteProduct(QuoteProduct quoteProduct) {
		this.quoteProduct = quoteProduct;
	}

	public List<QuoteProduct> getQuoteProductList() {
		return quoteProductList;
	}

	public void setQuoteProductList(List<QuoteProduct> quoteProductList) {
		this.quoteProductList = quoteProductList;
	}


	public BigDecimal getPreferentialRate() {
		return PreferentialRate;
	}


	public void setPreferentialRate(BigDecimal preferentialRate) {
		PreferentialRate = preferentialRate;
	}


	public QuoteProduct getSelectedProduct() {
		return selectedProduct;
	}


	public void setSelectedProduct(QuoteProduct selectedProduct) {
		this.selectedProduct = selectedProduct;
	}




	



}
