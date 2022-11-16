package dafengqi.yunxiang.dao;

import java.util.List;

import dafengqi.yunxiang.model.Announcement;


public interface AnnouncementDao extends GenericDao<Announcement, Long> {
	public List<?> getItems(Announcement announcement);
	public List<?> getItemsAll(Announcement announcement);
	public int saveAnnouncement(Announcement announcement);
	public int update(Announcement announcement);
	public Announcement edit(Announcement announcement);

}