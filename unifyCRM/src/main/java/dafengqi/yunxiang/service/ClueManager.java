package dafengqi.yunxiang.service;

import java.util.Date;
import java.util.List;

import dafengqi.yunxiang.model.Clue;

public interface ClueManager extends GenericManager<Clue, Long> {

	List<Clue> getItems(Clue clue);
	List<Clue> getItemsOfMy(Clue clue);
	List<Clue> getCluePool(Clue clue);
	Clue edit(Clue clue);

	int saveClue(Clue clue);
	int rhighseas(String table, String id, String status,String mechanismId);
	int rhighseas(String table, String id, String status,Clue clue);

	int update(Clue clue);
	int followUp(String departmentid, String id, String type,Date time,String content,String name,String status,String lxrid,String lxrmc,Date followtime,String createId,String cjsj,String mechanismid,String createName,String from);

}