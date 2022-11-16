package dafengqi.yunxiang.dao;

import java.util.List;

import dafengqi.yunxiang.model.Application;

/**
 * Application Data Access Object (DAO) interface.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public interface ApplicationDao extends GenericDao<Application, Long> {
    /**
     * Gets application information based on applicationname
     * @param applicationname the applicationname
     * @return populated application object
     */
	public int saveApplication(Application application);

    /**
     * Removes a application from the database by name
     * @param applicationname the application's applicationname
     */
	public List<?> getItems(Application application);
	public List<?> getItemsMenu(Application application);
	public List<?> getItemsManagement(Application application);
}
