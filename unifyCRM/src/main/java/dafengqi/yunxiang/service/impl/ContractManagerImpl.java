package dafengqi.yunxiang.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dafengqi.yunxiang.dao.ContractDao;
import dafengqi.yunxiang.model.Contract;
import dafengqi.yunxiang.service.ContractManager;

@Service("contractManager")
public class ContractManagerImpl extends GenericManagerImpl<Contract, Long> implements ContractManager, Serializable {

	ContractDao contractDao;

	@Autowired
	public ContractManagerImpl(ContractDao contractDao) {

		this.contractDao = contractDao;

	}
	@Override
	public List getItems(Contract contract) {
		return contractDao.getItems(contract);
	}
	@Override
	public List getItemsOfMy(Contract contract) {
		return contractDao.getItemsOfMy(contract);
	}

	@Override
	public int saveContract(Contract contract) {
		return contractDao.saveContract(contract);
	}
	@Override
	public int update(Contract contract) {
		return contractDao.update(contract);
	}

	@Override
	public Contract edit(Contract contract) {
		return contractDao.edit(contract);
	}

	@Override
	public int followUp(String departmentid, String id, String type,Date time,String content,String name,String status,String lxrid,String lxrmc,Date followtime,String createId,String cjsj,String mechanismid,String createName,String from) {
		return contractDao.followUp(departmentid, id, type,time,content,name,status,lxrid,lxrmc,followtime,createId,cjsj,mechanismid,createName,from);
	}
}