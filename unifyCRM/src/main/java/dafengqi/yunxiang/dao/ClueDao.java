package dafengqi.yunxiang.dao;

import java.util.List;

import dafengqi.yunxiang.model.Clue;

public interface ClueDao extends GenericDao<Clue, Long> {

	public List<?> getItems(Clue clue);
	public List<?> getItemsOfMy(Clue clue);
	public List<?> getCluePool(Clue clue);
	public Clue edit(Clue clue);
	public int saveClue(Clue clue);
	public int rhighseas(String table, String id, String status,String mechanismId);
	public int rhighseas(String table, String id, String status,Clue clue);
	public int update(Clue clue);
}