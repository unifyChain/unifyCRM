package dafengqi.yunxiang.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dafengqi.yunxiang.dao.DictDao;
import dafengqi.yunxiang.model.Dict;
import dafengqi.yunxiang.service.DictManager;

@Service("dictManager")
public class DictManagerImpl extends GenericManagerImpl<Dict, Long> implements DictManager, Serializable {

	DictDao dictDao;

	@Autowired
	public DictManagerImpl(DictDao dictDao) {

		this.dictDao = dictDao;

	}

	@Override
	public List getItems(Dict dict) {
		return dictDao.getItems(dict);
	}
	@Override
	public List getItemsByDictType(Dict dict) {
		return dictDao.getItemsByDictType(dict);
	}
	@Override
	public int saveDict(Dict dict) {
		return dictDao.saveDict(dict);
	}
	@Override
	public int isExistByDict(String table, String dictLabel,String dictValue,String id,String mechanismid) {
		return dictDao.isExistByDict( table,  dictLabel, dictValue, id, mechanismid);
	}
	@Override
	public int isExistByDict(String table, String dictLabel,String dictValue,String id,String parentCode,String mechanismid) {
		return dictDao.isExistByDict( table,  dictLabel, dictValue, id, parentCode, mechanismid);
	}
	@Override
	public int update(Dict dict) {
		return dictDao.update(dict);
	}

	@Override
	public Dict edit(Dict dict) {
		return dictDao.edit(dict);
	}

}