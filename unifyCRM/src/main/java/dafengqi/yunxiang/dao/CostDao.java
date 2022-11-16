package dafengqi.yunxiang.dao;

import java.util.List;

import dafengqi.yunxiang.model.Cost;

public interface CostDao extends GenericDao<Cost, Long> {
	public List<?> getItems(Cost cost);
	public List<?> getItemsOfMy(Cost cost);
	public Cost edit(Cost cost);
	public int saveCost(Cost cost);
	public int update(Cost cost);

}