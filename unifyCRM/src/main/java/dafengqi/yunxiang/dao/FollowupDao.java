package dafengqi.yunxiang.dao;

import java.util.List;

import dafengqi.yunxiang.model.FollowUp;

public interface FollowupDao extends GenericDao<FollowUp, Long> {


	public List<?> getItems(FollowUp followup);

}