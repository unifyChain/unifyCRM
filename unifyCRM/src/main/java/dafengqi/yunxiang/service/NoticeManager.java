package dafengqi.yunxiang.service;

import java.util.List;

import dafengqi.yunxiang.model.Notice;

public interface NoticeManager extends GenericManager<Notice, Long> {
	
	List<Notice> getItems(Notice notice);
	List<Notice> getItemsAll(Notice notice);
	int saveNotice(Notice notice);
	int update(Notice notice);
	Notice edit(Notice notice);

}