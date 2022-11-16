package dafengqi.yunxiang.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dafengqi.yunxiang.dao.DictTypeDao;
import dafengqi.yunxiang.model.DictType;
import dafengqi.yunxiang.service.DictTypeManager;

@Service("dictTypeManager")
public class DictTypeManagerImpl extends GenericManagerImpl<DictType, Long> implements DictTypeManager, Serializable {

	DictTypeDao dictTypeDao;

	@Autowired
	public DictTypeManagerImpl(DictTypeDao dictTypeDao) {

		this.dictTypeDao = dictTypeDao;

	}
	@Override
	public List getItems(DictType dictType) {
		return dictTypeDao.getItems(dictType);	
	}
	@Override
	public int saveDictType(DictType dictType) {
		return dictTypeDao.saveDictType(dictType);
	}
	@Override
	public int update(DictType dictType) {
		return dictTypeDao.update(dictType);
	}
	@Override
	public DictType edit(DictType dictType) {
		return dictTypeDao.edit(dictType);
	}

}