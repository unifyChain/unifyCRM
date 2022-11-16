package dafengqi.yunxiang.service;

import java.util.List;

import dafengqi.yunxiang.model.Announcement;

public interface AnnouncementManager extends GenericManager<Announcement, Long> {
	
	List<Announcement> getItems(Announcement announcement);
	List<Announcement> getItemsAll(Announcement announcement);
	int saveAnnouncement(Announcement announcement);
	int update(Announcement announcement);
	Announcement edit(Announcement announcement);

}