package dafengqi.yunxiang.service;

import java.util.List;

import dafengqi.yunxiang.model.Cost;

public interface CostManager extends GenericManager<Cost, Long> {

	List<Cost> getItems(Cost cost);
	List<Cost> getItemsOfMy(Cost cost);
	Cost edit(Cost cost);
	int saveCost(Cost cost);
	int update(Cost cost);

}