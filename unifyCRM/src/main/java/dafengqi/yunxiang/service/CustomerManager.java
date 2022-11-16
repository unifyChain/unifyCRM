package dafengqi.yunxiang.service;

import java.util.Date;
import java.util.List;

import dafengqi.yunxiang.model.Customer;

public interface CustomerManager extends GenericManager<Customer, Long> {
	
	List<Customer> getItems(Customer customers);
	List<Customer> getItemsOfMy(Customer customers);
	List<Customer> getItemsHighseas(Customer customers);
	List<Customer> getItemsOfMyData(Customer customers);
	Customer edit(Customer customers);
	Customer relationMechanismCustomer(Customer customers);
	int saveCustomer(Customer customers);
	int rhighseas(String table, String id, String status,String mechanismId);
	int rhighseas(String table, String id, String status,Customer customers);
	int removeCustomer(String table, String id, String mechanismId);
	int update(Customer customers);
	String excelToDb(String cjr, String jgid, String filePath, String jgmc, String departmentid, String departmentname);
	int followUp(String departmentid, String id, String type,Date time,String content,String name,String status,String lxrid,String lxrmc,Date followtime,String createId,String cjsj,String mechanismid,String createName,String from);

}