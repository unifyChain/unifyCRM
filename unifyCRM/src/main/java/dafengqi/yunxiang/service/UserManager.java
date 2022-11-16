package dafengqi.yunxiang.service;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import dafengqi.yunxiang.dao.UserDao;
import dafengqi.yunxiang.model.User;


public interface UserManager extends GenericManager<User, Long> {
	List<User> getItems(User user);

	User edit(User user);

    void setPasswordEncoder(PasswordEncoder passwordEncoder);
	
	int update(User user);
	
	
    void setUserDao(UserDao userDao);


    User getUser(String userId);

    User getUserByUsername(String username) throws UsernameNotFoundException;

    List<User> getUsers();
    User saveUser(User user) throws UserExistsException;
    void removeUser(User user);

    void removeUser(String userId);
    List<User> search(String searchTerm);

    /**
     * Builds a recovery password url by replacing placeholders with username and generated recovery token.
     * 
     * UrlTemplate should include two placeholders '{username}' for username and '{token}' for the recovery token.
     * 
     * @param user
     * @param urlTemplateurl
     *            template including two placeholders '{username}' and '{token}'
     * @return
     */
    String buildRecoveryPasswordUrl(User user, String urlTemplate);

    /**
     *
     * @param user
     * @return
     */
    String generateRecoveryToken(User user);

    /**
     *
     * @param username
     * @param token
     * @return
     */
    boolean isRecoveryTokenValid(String username, String token);

    /**
     * 
     * @param user
     * @param token
     * @return
     */
    boolean isRecoveryTokenValid(User user, String token);

    /**
     * Sends a password recovery email to username.
     *
     * @param username
     * @param urlTemplate
     *            url template including two placeholders '{username}' and '{token}'
     */
    void sendPasswordRecoveryEmail(String username, String urlTemplate);

    /**
     * 
     * @param username
     * @param currentPassword
     * @param recoveryToken
     * @param newPassword
     * @param applicationUrl
     * @return
     * @throws UserExistsException
     */
    User updatePassword(String username, String currentPassword, String recoveryToken, String newPassword, String applicationUrl) throws UserExistsException;
}
