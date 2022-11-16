package dafengqi.yunxiang.service;

import org.primefaces.model.TreeNode;

import dafengqi.yunxiang.model.Mechanism;

public interface MechanismManager extends GenericManager<Mechanism, Long> {

	TreeNode getItems(Mechanism mechanism);
	Mechanism edit(Mechanism mechanism);
	public TreeNode createMls(Mechanism mechanism);
	int saveMechanism(Mechanism mechanism);
	int update(Mechanism mechanism);

}