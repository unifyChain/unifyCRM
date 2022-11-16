package dafengqi.yunxiang.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dafengqi.yunxiang.dao.ClueDao;
import dafengqi.yunxiang.model.Clue;
import dafengqi.yunxiang.service.ClueManager;

@Service("clueManager")
public class ClueManagerImpl extends GenericManagerImpl<Clue, Long> implements ClueManager, Serializable {

	ClueDao clueDao;

	@Autowired
	public ClueManagerImpl(ClueDao clueDao) {

		this.clueDao = clueDao;

	}
	@Override
	public List getItems(Clue clue) {
		return clueDao.getItems(clue);
	}
	@Override
	public List getItemsOfMy(Clue clue) {
		return clueDao.getItemsOfMy(clue);
	}
	@Override
	public List getCluePool(Clue clue) {
		return clueDao.getCluePool(clue);
	}
	@Override
	public int saveClue(Clue clue) {
		return clueDao.saveClue(clue);
	}
	@Override
	public int rhighseas(String table, String id, String status,String mechanismId) {
		return clueDao.rhighseas(table,id,status,mechanismId);
	}
	@Override
	public int rhighseas(String table, String id, String status,Clue clue) {
		return clueDao.rhighseas(table,id,status,clue);
	}
	@Override
	public int update(Clue clue) {
		return clueDao.update(clue);
	}

	@Override
	public Clue edit(Clue clue) {
		return clueDao.edit(clue);
	}
	@Override
	public int followUp(String departmentid, String id, String type,Date time,String content,String name,String status,String lxrid,String lxrmc,Date followtime,String createId,String cjsj,String mechanismid,String createName,String from) {
		return clueDao.followUp(departmentid, id, type,time,content,name,status,lxrid,lxrmc,followtime,createId,cjsj,mechanismid,createName,from);
	}

}