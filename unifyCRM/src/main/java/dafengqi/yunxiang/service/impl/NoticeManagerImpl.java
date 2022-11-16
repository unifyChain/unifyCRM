package dafengqi.yunxiang.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dafengqi.yunxiang.dao.NoticeDao;
import dafengqi.yunxiang.model.Notice;
import dafengqi.yunxiang.service.NoticeManager;

@Service("noticeManager")
public class NoticeManagerImpl extends GenericManagerImpl<Notice, Long> implements NoticeManager, Serializable {

	NoticeDao noticeDao;

	@Autowired
	public NoticeManagerImpl(NoticeDao noticeDao) {

		this.noticeDao = noticeDao;

	}
	@Override
	public List getItems(Notice notice) {
		return noticeDao.getItems(notice);	
	}
	@Override
	public List getItemsAll(Notice notice) {
		return noticeDao.getItemsAll(notice);	
	}
	@Override
	public int saveNotice(Notice notice) {
		return noticeDao.saveNotice(notice);
	}
	@Override
	public int update(Notice notice) {
		return noticeDao.update(notice);
	}
	@Override
	public Notice edit(Notice notice) {
		return noticeDao.edit(notice);
	}

}