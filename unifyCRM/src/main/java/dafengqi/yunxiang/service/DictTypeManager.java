package dafengqi.yunxiang.service;

import java.util.List;

import dafengqi.yunxiang.model.DictType;

public interface DictTypeManager extends GenericManager<DictType, Long> {

	List<DictType> getItems(DictType dictType);
	int saveDictType(DictType dictType);
	int update(DictType dictType);
	DictType edit(DictType dictType);

}