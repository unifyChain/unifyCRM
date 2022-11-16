package dafengqi.yunxiang.dao;

import org.primefaces.model.TreeNode;

import dafengqi.yunxiang.model.Mechanism;

public interface MechanismDao extends GenericDao<Mechanism, Long> {


	public TreeNode getItems(Mechanism mechanism);

	public Mechanism edit(Mechanism mechanism);

	public TreeNode createMls(Mechanism mechanism);

	public int saveMechanism(Mechanism mechanism);

	public int update(Mechanism mechanism);

}