package dafengqi.yunxiang.dao;

import java.util.List;

import dafengqi.yunxiang.model.DictType;


public interface DictTypeDao extends GenericDao<DictType, Long> {
	public List<?> getItems(DictType dictType);
	public int saveDictType(DictType dictType);
	public int update(DictType dictType);
	public DictType edit(DictType dictType);

}