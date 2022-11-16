package dafengqi.yunxiang.service.impl;

import java.io.Serializable;

import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dafengqi.yunxiang.dao.MechanismDao;
import dafengqi.yunxiang.model.Mechanism;
import dafengqi.yunxiang.service.MechanismManager;

@Service("mechanismManager")
public class MechanismManagerImpl extends GenericManagerImpl<Mechanism, Long> implements MechanismManager, Serializable {

    private PasswordEncoder passwordEncoder;
	MechanismDao mechanismDao;

	@Autowired
	public MechanismManagerImpl(MechanismDao mechanismDao) {

		this.mechanismDao = mechanismDao;

	}
	@Override
	public TreeNode getItems(Mechanism mechanism) {
		return mechanismDao.getItems(mechanism);
	}
	@Override
	public Mechanism edit(Mechanism mechanism) {
		return mechanismDao.edit(mechanism);
	}
	@Override
	public TreeNode createMls(Mechanism mechanism) {
		return mechanismDao.createMls(mechanism);
	}
	@Override
	public int saveMechanism(Mechanism mechanism) {
		return mechanismDao.saveMechanism(mechanism);
	}
	@Override
	public int update(Mechanism mechanism) {
		return mechanismDao.update(mechanism);
	}

    @Autowired
    @Qualifier("passwordEncoder")
    public void setPasswordEncoder(final PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

}