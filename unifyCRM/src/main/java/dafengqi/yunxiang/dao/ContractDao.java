package dafengqi.yunxiang.dao;

import java.util.List;

import dafengqi.yunxiang.model.Contract;

public interface ContractDao extends GenericDao<Contract, Long> {
	public List<?> getItems(Contract contract);
	public List<?> getItemsOfMy(Contract contract);
	public Contract edit(Contract contract);
	public int saveContract(Contract contract);
	public int update(Contract contract);

}