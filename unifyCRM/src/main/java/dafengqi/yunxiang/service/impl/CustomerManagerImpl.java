package dafengqi.yunxiang.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dafengqi.yunxiang.dao.CustomerDao;
import dafengqi.yunxiang.model.Customer;
import dafengqi.yunxiang.service.CustomerManager;

@Service("customerManager")
public class CustomerManagerImpl extends GenericManagerImpl<Customer, Long> implements CustomerManager, Serializable {

	CustomerDao customerDao;

	@Autowired
	public CustomerManagerImpl(CustomerDao customerDao) {

		this.customerDao = customerDao;

	}
	@Override
	public List getItems(Customer customer) {
		return customerDao.getItems(customer);
	}
	@Override
	public List getItemsOfMy(Customer customer) {
		return customerDao.getItemsOfMy(customer);
	}
	@Override
	public List getItemsOfMyData(Customer customer) {
		return customerDao.getItemsOfMyData(customer);
	}
	@Override
	public List getItemsHighseas(Customer customer) {
		return customerDao.getItemsHighseas(customer);
	}
	@Override
	public int rhighseas(String table, String id, String status,String mechanismId) {
		return customerDao.rhighseas(table,id,status,mechanismId);
	}
	@Override
	public int rhighseas(String table, String id, String status,Customer customers) {
		return customerDao.rhighseas(table,id,status,customers);
	}
	@Override
	public int removeCustomer(String table, String id, String mechanismId) {
		return customerDao.removeCustomer(table,id,mechanismId);
	}
	@Override
	public int saveCustomer(Customer customer) {
		return customerDao.saveCustomer(customer);
	}
	@Override
	public int update(Customer customer) {
		return customerDao.update(customer);
	}

	@Override
	public Customer edit(Customer customer) {
		return customerDao.edit(customer);
	}

	@Override
	public Customer relationMechanismCustomer(Customer customer) {
		return customerDao.relationMechanismCustomer(customer);
	}

	@Override
	public String excelToDb(String cjr, String jgid, String filePath, String jgmc, String departmentid, String departmentname) {
		return customerDao.excelToDb(cjr, jgid, filePath, jgmc, departmentid, departmentname);
	}
	@Override
	public int followUp(String departmentid, String id, String type,Date time,String content,String name,String status,String lxrid,String lxrmc,Date followtime,String createId,String cjsj,String mechanismid,String createName,String from) {
		return customerDao.followUp(departmentid, id, type,time,content,name,status,lxrid,lxrmc,followtime,createId,cjsj,mechanismid,createName,from);
	}
}