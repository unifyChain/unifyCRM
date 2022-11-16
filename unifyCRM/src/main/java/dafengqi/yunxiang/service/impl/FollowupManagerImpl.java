package dafengqi.yunxiang.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dafengqi.yunxiang.dao.FollowupDao;
import dafengqi.yunxiang.model.FollowUp;
import dafengqi.yunxiang.service.FollowupManager;

@Service("followupManager")
public class FollowupManagerImpl extends GenericManagerImpl<FollowUp, Long> implements FollowupManager, Serializable {

	FollowupDao followupDao;

	@Autowired
	public FollowupManagerImpl(FollowupDao followupDao) {

		this.followupDao = followupDao;

	}


	@Override
	public List getItems(FollowUp followup) {
		return followupDao.getItems(followup);
	}
	

}