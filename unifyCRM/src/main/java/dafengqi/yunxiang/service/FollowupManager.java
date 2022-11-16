package dafengqi.yunxiang.service;

import java.util.List;

import dafengqi.yunxiang.model.FollowUp;

public interface FollowupManager extends GenericManager<FollowUp, Long> {

	List<FollowUp> getItems(FollowUp followup);

}