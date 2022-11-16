package dafengqi.yunxiang.webapp.action;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.CroppedImage;
import org.primefaces.model.TreeNode;
import org.primefaces.model.file.UploadedFile;

import dafengqi.yunxiang.model.Mechanism;
import dafengqi.yunxiang.model.User;
import dafengqi.yunxiang.service.MechanismManager;
import dafengqi.yunxiang.util.JsfUtil;

@ManagedBean(name = "mechanismController")
@SessionScoped
public class MechanismController extends BasePage implements Serializable {
	
	private static String ENDPOINT;
	private static String ACCESS_KEY_ID;
	private static String ACCESS_KEY_SECRET;
	private static String BACKET_NAME;
	private static String HY_FOLDER;
	String rq = new SimpleDateFormat("yyyyMMdd").format(new Date());


    private CroppedImage croppedImage;

    private UploadedFile originalImageFile;

    public CroppedImage getCroppedImage() {
        return croppedImage;
    }

    public void setCroppedImage(CroppedImage croppedImage) {
        this.croppedImage = croppedImage;
    }

    public UploadedFile getOriginalImageFile() {
        return originalImageFile;
    }

	
	@FacesConverter(forClass = Mechanism.class)
	public static class MechanismszControllerConverter implements Converter {

		@Override
		public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {

			return null;
		}

		@Override
		public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
			if (object == null) {
				return null;
			}
			if (object instanceof Mechanism) {
				Mechanism o = (Mechanism) object;
				return getStringKey(o.getId());
			} else {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
						"object {0} is of type {1}; expected type: {2}",
						new Object[] { object, object.getClass().getName(), Mechanism.class.getName() });
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

	private TreeNode items = null;//列表list


	private MechanismManager mechanismManager;


	private Mechanism selected;//字段model

	

	private User selecteduser;//字段model

	private String[] directions;
	private String[] industrys;
	
	
    private TreeNode root;
	private TreeNode selectedNode;
	private String zcyzm;
	private TreeNode<Mechanism> selecteds;

	public MechanismController() {
	}

	public void displaySelectedMultiple(TreeNode nodes) {
			if(nodes==null){
				JsfUtil.warn("请选择上级机构!");
			}else{
				String nodestr = nodes.getData().toString();
				String[] nodearr = nodestr.split("-");
				selected.setMechanismId(nodearr[1]);
				selected.setMechanismName(nodearr[0]);
			}
	}
	public String create() {
		if (selected != null) {
			setEmbeddableKeys();
			try {
				int k = 0;
				String table = "js_sys_mechanism";
				selected.setId(UUID.randomUUID().toString());
//				k = this.valiAllByCfcx(table, selected.getName(), "name");
//				if (k == 1) {
//					JsfUtil.warn("名称：" + selected.getName() + "重复");
//				} else if (k == 0) {
					if(selected.getMechanismId()==null){

						selected.setId(UUID.randomUUID().toString());
						selected.setCreateDate(df.format(new Date()));
						selected.setUpdateDate(df.format(new Date()));
						String username = (String) getSession().getAttribute("USERNAME");
						selected.setCreateId(username);
						String appid = (String) getSession().getAttribute("APPID");
						selected.setAppId(appid);
						int i = mechanismManager.saveMechanism(selected);
						if (i == 0) {
							JsfUtil.warn("机构新增失败!");
						} else if (i == 1) {
							JsfUtil.info("机构新增成功!");
						} else {
							JsfUtil.error("机构新增错误!");
						}
					  
					}else{

						if(selected.getMechanismId().length()>=16){
							JsfUtil.warn("不支持创建!");
						}else{
							selected.setId(UUID.randomUUID().toString());
							selected.setCreateDate(df.format(new Date()));
							selected.setUpdateDate(df.format(new Date()));
							String username = (String) getSession().getAttribute("USERNAME");
							selected.setCreateId(username);
							String appid = (String) getSession().getAttribute("APPID");
							selected.setAppId(appid);
							int i = mechanismManager.saveMechanism(selected);
							if (i == 0) {
								JsfUtil.warn("机构新增失败!");
							} else if (i == 1) {
								JsfUtil.info("机构新增成功!");
							} else {
								JsfUtil.error("机构新增错误!");
							}
						}
					}
//				} else if (k == -1) {
//					JsfUtil.error("失败!");
//				}
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, "机构新增错误!");
			}
		}
		if (!JsfUtil.isValidationFailed()) {
			items = null; 
			root=null;
		}
		return "mechanism";
		
	}



	public TreeNode getItems() {
		if (items == null) {
			if(selected==null){
				selected=new Mechanism();
			}
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			selected.setId(mechanismId);
			String username = (String) getSession().getAttribute("USERNAME");
			selected.setCreateId(username);
			items = mechanismManager.getItems(selected);
		}

		return items;
	}


	public MechanismManager getMechanismManager() {
		return mechanismManager;
	}

	public Mechanism getSelected() {
		return selected;
	}

	protected void initializeEmbeddableKey() {
	}

	public String prepareCreate() {
		selected = new Mechanism();
		selected.setFrom("新增");
		root=null;
		return "mechanismForm";
	}

	public String edit() {

		String id = getRequest().getParameter("id");
		if(selected==null){
			selected=new Mechanism();
		}
		selected.setId(id);
		String mechanismId = (String) getSession().getAttribute("MECHANISMID");
		selected.setMechanismId(mechanismId);
		selected = mechanismManager.edit(selected);
		selected.setFrom("编辑");
		return "mechanismForm";
	}

	public String view() {
		String id = getRequest().getParameter("id");
		if(selected==null){
			selected=new Mechanism();
		}
		selected.setId(id);
		String mechanismId = (String) getSession().getAttribute("MECHANISMID");
		selected.setMechanismId(mechanismId);
		selected = mechanismManager.edit(selected);
		selected.setFrom("查看");
		return "mechanismForm";
	}


    public void onNodeSelectList(NodeSelectEvent event) {
		Mechanism nodes=selecteds.getData();
		selected=nodes;
    }

    public TreeNode onNodeSelect(NodeSelectEvent event) {
		String nodestr = event.getTreeNode().toString();
		String[] nodearr = nodestr.split("-");
		selected.setMechanismId(nodearr[1]);
		items = mechanismManager.getItems(selected);
		return items;
    }
	protected void setEmbeddableKeys() {
	}

	public void setMechanismManager(MechanismManager mechanismManager) {
		this.mechanismManager = mechanismManager;
	}

	public void setMechanismszManager(MechanismManager mechanismManager) {
		this.mechanismManager = mechanismManager;
	}

	public void setSelected(Mechanism selected) {
		this.selected = selected;
	}

	public String update() {
		if (selected != null) {
			setEmbeddableKeys();
			try {
				int k = 0;
				String table = "js_sys_mechanism";
					selected.setCreateDate(df.format(new Date()));
					selected.setUpdateDate(df.format(new Date()));
					int i = mechanismManager.update(selected);
					if (i == 0) {
						JsfUtil.warn("机构更新失败!");
					} else if (i == 1) {
						JsfUtil.info("机构更新成功!");
					} else {
						JsfUtil.error("机构更新错误!");
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
			root = null; 
		}
		return "mechanism";
	}
	public TreeNode getRoot() {
		if(selected==null) {
			selected=new Mechanism();
		}
		String mechanismId = (String) getSession().getAttribute("MECHANISMID");
		selected.setMechanismId(mechanismId);
		String username = (String) getSession().getAttribute("USERNAME");
		selected.setCreateId(username);
		if (root == null) {
			root = mechanismManager.createMls(selected);
		}
		return root;
	}
	
	
	
	
	public void setRoot(TreeNode root) {
		this.root = root;
	}
	public TreeNode getSelectedNode() {
		return selectedNode;
	}
	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	public TreeNode getSelecteds() {
		return selecteds;
	}

	public void setSelecteds(TreeNode selecteds) {
		this.selecteds = selecteds;
	}
	public String getZcyzm() {
		return zcyzm;
	}
	public void setZcyzm(String zcyzm) {
		this.zcyzm = zcyzm;
	}
	public User getSelecteduser() {
		return selecteduser;
	}
	public void setSelecteduser(User selecteduser) {
		this.selecteduser = selecteduser;
	}

	public String[] getDirections() {
		return directions;
	}

	public void setDirections(String[] directions) {
		this.directions = directions;
	}

	public String[] getIndustrys() {
		return industrys;
	}

	public void setIndustrys(String[] industrys) {
		this.industrys = industrys;
	}



}
