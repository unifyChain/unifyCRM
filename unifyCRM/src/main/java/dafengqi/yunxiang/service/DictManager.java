package dafengqi.yunxiang.service;

import java.util.List;

import dafengqi.yunxiang.model.Dict;

public interface DictManager extends GenericManager<Dict, Long> {

	List<Dict> getItems(Dict dict);
	List<Dict> getItemsByDictType(Dict dict);
	int saveDict(Dict dict);
	int isExistByDict(String table, String dictLabel,String dictValue,String id,String mechanismid);
	int isExistByDict(String table, String dictLabel,String dictValue,String id,String parentCode,String mechanismid);
	int update(Dict dict);
	Dict edit(Dict selected);

}