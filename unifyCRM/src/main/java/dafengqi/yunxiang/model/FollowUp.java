package dafengqi.yunxiang.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

@Entity
public class FollowUp implements Serializable {

	private static final long serialVersionUID = 6391872883644926431L;
	@Id
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 45)
	@Column(name = "id")
	private String id;
	private String followUp;
	private String[] followUps;
	private String time;
	private String content;
	private String followId;
	private String followName;
	private String followStatus;
	private String contactsId;
	private String contactsName;
	private String nextFollowTime;
	private String createId;
	private String createName;
	private String createDate;
	private String updateId;
	private String updateName;
	private String updateDate;
	private String departmentName;
	private String departmentId;
	private String mechanismId;
	private String departmentIds;
	private String[] froms;
	private List<Date> timeDateRange;
	private List<Date> nextFollowTimeDateRange;
	
	@Transient
	private String from;
	
	@Column(name = "app_id")
	private String appId;
	
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	
	
	
	public FollowUp() {
	}

	public FollowUp(String id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof FollowUp)) {
			return false;
		}
		FollowUp other = (FollowUp) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}


	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getFollowUp() {
		return followUp;
	}

	public void setFollowUp(String followUp) {
		this.followUp = followUp;
	}

	public String[] getFollowUps() {
		return followUps;
	}

	public void setFollowUps(String[] followUps) {
		this.followUps = followUps;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFollowId() {
		return followId;
	}

	public void setFollowId(String followId) {
		this.followId = followId;
	}

	public String getFollowName() {
		return followName;
	}

	public void setFollowName(String followName) {
		this.followName = followName;
	}

	public String getFollowStatus() {
		return followStatus;
	}

	public void setFollowStatus(String followStatus) {
		this.followStatus = followStatus;
	}

	public String getContactsId() {
		return contactsId;
	}

	public void setContactsId(String contactsId) {
		this.contactsId = contactsId;
	}

	public String getContactsName() {
		return contactsName;
	}

	public void setContactsName(String contactsName) {
		this.contactsName = contactsName;
	}

	public String getNextFollowTime() {
		return nextFollowTime;
	}

	public void setNextFollowTime(String nextFollowTime) {
		this.nextFollowTime = nextFollowTime;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUpdateId() {
		return updateId;
	}

	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String[] getFroms() {
		return froms;
	}

	public void setFroms(String[] froms) {
		this.froms = froms;
	}

	public List<Date> getTimeDateRange() {
		return timeDateRange;
	}

	public void setTimeDateRange(List<Date> timeDateRange) {
		this.timeDateRange = timeDateRange;
	}

	public List<Date> getNextFollowTimeDateRange() {
		return nextFollowTimeDateRange;
	}

	public void setNextFollowTimeDateRange(List<Date> nextFollowTimeDateRange) {
		this.nextFollowTimeDateRange = nextFollowTimeDateRange;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDepartmentIds() {
		return departmentIds;
	}

	public void setDepartmentIds(String departmentIds) {
		this.departmentIds = departmentIds;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getMechanismId() {
		return mechanismId;
	}

	public void setMechanismId(String mechanismId) {
		this.mechanismId = mechanismId;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}


	
	
}	
