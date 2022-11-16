package dafengqi.yunxiang.service;

import java.util.List;

import dafengqi.yunxiang.model.Application;

/**
 * Business Service Interface to handle communication between web and
 * persistence layer.
 *
 * @author <a href="mailto:dan@getrolling.com">Dan Kibler </a>
 */
public interface ApplicationManager extends GenericManager<Application, Long> {
    int saveApplication(Application application);
	List<Application> getItemsMenu(Application application);
	List<Application> getItems(Application application);
	List<Application> getItemsManagement(Application application);
}
