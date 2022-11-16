package dafengqi.yunxiang.dao;

import java.util.List;

import dafengqi.yunxiang.model.Customer;



public interface CustomerDao extends GenericDao<Customer, Long> {

	public List<?> getItems(Customer customer);
	public List<?> getItemsOfMy(Customer customer);
	public List<?> getItemsOfMyData(Customer customer);
	public List<?> getItemsHighseas(Customer customer);
	public Customer edit(Customer customer);
	public Customer relationMechanismCustomer(Customer customer);
	public int saveCustomer(Customer customer);
	public int rhighseas(String table, String id, String status,String mechanismId);
	public int rhighseas(String table, String id, String status,Customer customer);
	public int removeCustomer(String table, String id, String mechanismId);
	public int update(Customer customer);
	String excelToDb(String cjr, String jgid, String filePath, String jgmc, String departmentid, String departmentname);


}