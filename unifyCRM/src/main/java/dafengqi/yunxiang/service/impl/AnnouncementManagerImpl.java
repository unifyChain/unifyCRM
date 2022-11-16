package dafengqi.yunxiang.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dafengqi.yunxiang.dao.AnnouncementDao;
import dafengqi.yunxiang.model.Announcement;
import dafengqi.yunxiang.service.AnnouncementManager;

@Service("announcementManager")
public class AnnouncementManagerImpl extends GenericManagerImpl<Announcement, Long> implements AnnouncementManager, Serializable {

	AnnouncementDao announcementDao;

	@Autowired
	public AnnouncementManagerImpl(AnnouncementDao announcementDao) {

		this.announcementDao = announcementDao;

	}
	@Override
	public List getItems(Announcement announcement) {
		return announcementDao.getItems(announcement);	
	}
	@Override
	public List getItemsAll(Announcement announcement) {
		return announcementDao.getItemsAll(announcement);	
	}
	@Override
	public int saveAnnouncement(Announcement announcement) {
		return announcementDao.saveAnnouncement(announcement);
	}
	@Override
	public int update(Announcement announcement) {
		return announcementDao.update(announcement);
	}
	@Override
	public Announcement edit(Announcement announcement) {
		return announcementDao.edit(announcement);
	}

}