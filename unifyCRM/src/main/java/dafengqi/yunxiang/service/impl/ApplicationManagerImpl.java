package dafengqi.yunxiang.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dafengqi.yunxiang.dao.ApplicationDao;
import dafengqi.yunxiang.model.Application;
import dafengqi.yunxiang.service.ApplicationManager;

/**
 * Implementation of ApplicationManager interface.
 *
 * @author <a href="mailto:dan@getrolling.com">Dan Kibler</a>
 */
@Service("applicationManager")
public class ApplicationManagerImpl extends GenericManagerImpl<Application, Long> implements ApplicationManager {
    ApplicationDao applicationDao;

    @Autowired
    public ApplicationManagerImpl(ApplicationDao applicationDao) {
        super(applicationDao);
        this.applicationDao = applicationDao;
    }

    public int saveApplication(Application application) {
        return applicationDao.saveApplication(application);
    }

	@Override
	public List getItems(Application application) {
		return applicationDao.getItems(application);
	}
	@Override
	public List getItemsMenu(Application application) {
		return applicationDao.getItemsMenu(application);
	}
	@Override
	public List getItemsManagement(Application application) {
		return applicationDao.getItemsManagement(application);
	}


}