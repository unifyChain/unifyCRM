package dafengqi.yunxiang.dao;

import java.util.List;

import dafengqi.yunxiang.model.Notice;


public interface NoticeDao extends GenericDao<Notice, Long> {
	public List<?> getItems(Notice notice);
	public List<?> getItemsAll(Notice notice);
	public int saveNotice(Notice notice);
	public int update(Notice notice);
	public Notice edit(Notice notice);

}