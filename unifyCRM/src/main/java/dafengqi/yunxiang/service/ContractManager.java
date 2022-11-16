package dafengqi.yunxiang.service;

import java.util.Date;
import java.util.List;

import dafengqi.yunxiang.model.Contract;

public interface ContractManager extends GenericManager<Contract, Long> {

	List<Contract> getItems(Contract contract);
	List<Contract> getItemsOfMy(Contract contract);
	Contract edit(Contract contract);

	int saveContract(Contract contract);

	int update(Contract contract);
	int followUp(String departmentid, String id, String type,Date time,String content,String name,String status,String lxrid,String lxrmc,Date followtime,String createId,String cjsj,String mechanismid,String createName,String from);

}