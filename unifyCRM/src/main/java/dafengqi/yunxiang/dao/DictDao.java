package dafengqi.yunxiang.dao;

import java.util.List;

import dafengqi.yunxiang.model.Dict;

public interface DictDao extends GenericDao<Dict, Long> {
	public List<?> getItems(Dict dict);
	public List<?> getItemsByDictType(Dict dict);
	public int saveDict(Dict dict);
	public int isExistByDict(String table, String dictLabel,String dictValue,String id,String mechanismid);
	public int isExistByDict(String table, String dictLabel,String dictValue,String id,String parentCode,String mechanismid);
	public int update(Dict dict);
	public Dict edit(Dict dict);

}