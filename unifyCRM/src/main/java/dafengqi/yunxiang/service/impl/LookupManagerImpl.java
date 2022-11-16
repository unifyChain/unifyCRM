package dafengqi.yunxiang.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dafengqi.yunxiang.dao.LookupDao;
import dafengqi.yunxiang.model.LabelValue;
import dafengqi.yunxiang.model.Role;
import dafengqi.yunxiang.service.LookupManager;

import java.util.ArrayList;
import java.util.List;


/**
 * Implementation of LookupManager interface to talk to the persistence layer.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Service("lookupManager")
public class LookupManagerImpl implements LookupManager {
    @Autowired
    LookupDao dao;

    /**
     * {@inheritDoc}
     */
    public List<LabelValue> getAllRoles() {
        List<Role> roles = dao.getRoles();
        List<LabelValue> list = new ArrayList<LabelValue>();

        for (Role role1 : roles) {
            list.add(new LabelValue(role1.getName(), role1.getName()));
        }

        return list;
    }
}
