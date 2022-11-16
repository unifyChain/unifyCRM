package dafengqi.yunxiang.dao;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import dafengqi.yunxiang.model.User;


public interface UserDao extends GenericDao<User, Long> {


	public List<?> getItems(User user);
	public User edit(User user);
	public User saveUser(User user);
	public int update(User user);
	
    @Transactional
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    List<User> getUsers();

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    String getUserPassword(Long userId);
    
}
