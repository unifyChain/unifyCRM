package dafengqi.yunxiang.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.security.core.GrantedAuthority;


@Entity
@Table(name = "role_module")
public class RoleModule extends BaseObject implements Serializable {
    private static final long serialVersionUID = 3690197650654049848L;
    private Long id;
    private String name;
    private String type;
	@Size(max = 45)
	@Column(name = "mechanism_id")
    private String mechanismId;
	@Size(max = 45)
	@Column(name = "mechanism_name")
    private String mechanismName;
	@Size(max = 45)
	@Column(name = "role_id")
    private String roleId;
	@Size(max = 45)
	@Column(name = "role_name")
    private String roleName;
	@Size(max = 45)
	@Column(name = "module_id")
    private String moduleId;
	@Size(max = 45)
	@Column(name = "module_name")
    private String moduleName;
	@Size(max = 45)
	@Column(name = "parent_module_id")
    private String parentModuleId;
	@Size(max = 45)
	@Column(name = "parent_module_name")
    private String parentModuleName;
	@Transient
	private String ids;
	private String system;
    public RoleModule() {
    }

	public RoleModule(String ids, String name, String type, String moduleId, String moduleMc, String system) {
		this.ids = ids;
		this.name = name;
		this.type = type;
		this.moduleId = moduleId;
		this.moduleName = moduleMc;
		this.system = system;
	}
    public RoleModule(final String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public String getName() {
        return this.name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }


    /**
     * {@inheritDoc}
     */
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RoleModule)) {
            return false;
        }

        final RoleModule role = (RoleModule) o;

        return !(name != null ? !name.equals(role.name) : role.name != null);

    }

    /**
     * {@inheritDoc}
     */
    public int hashCode() {
        return (name != null ? name.hashCode() : 0); 
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
                .append(this.name)
                .toString();
    }

	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMechanismId() {
		return mechanismId;
	}

	public void setMechanismId(String mechanismId) {
		this.mechanismId = mechanismId;
	}

	public String getMechanismName() {
		return mechanismName;
	}

	public void setMechanismName(String mechanismName) {
		this.mechanismName = mechanismName;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getParentModuleId() {
		return parentModuleId;
	}

	public void setParentModuleId(String parentModuleId) {
		this.parentModuleId = parentModuleId;
	}

	public String getParentModuleName() {
		return parentModuleName;
	}

	public void setParentModuleName(String parentModuleName) {
		this.parentModuleName = parentModuleName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	

	
}
