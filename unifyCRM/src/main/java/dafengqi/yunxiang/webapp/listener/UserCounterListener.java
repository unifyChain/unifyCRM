package dafengqi.yunxiang.webapp.listener;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import dafengqi.yunxiang.model.User;
import dafengqi.yunxiang.util.ResourceManager;



public class UserCounterListener implements ServletContextListener, HttpSessionAttributeListener, HttpSessionListener {
    /**
     * Name of user counter variable
     */
    public static final String COUNT_KEY = "userCounter";
    /**
     * Name of users Set in the ServletContext
     */
    public static final String USERS_KEY = "userNames";
    /**
     * The default event we're looking to trap.
     */
    public static final String EVENT_KEY = HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;
    private transient ServletContext servletContext;
    private int counter;
    private Set<User> users;
	protected java.sql.Connection userConn;

    /**
     * Initialize the context
     *
     * @param sce the event
     */
    public synchronized void contextInitialized(ServletContextEvent sce) {
        servletContext = sce.getServletContext();
        servletContext.setAttribute((COUNT_KEY), Integer.toString(counter));
    }

    /**
     * Set the servletContext, users and counter to null
     *
     * @param event The servletContextEvent
     */
    public synchronized void contextDestroyed(ServletContextEvent event) {
        servletContext = null;
        users = null;
        counter = 0;
    }

    synchronized void incrementUserCounter() {
        counter = Integer.parseInt((String) servletContext.getAttribute(COUNT_KEY));
        counter++;
        servletContext.setAttribute(COUNT_KEY, Integer.toString(counter));
    }

    synchronized void decrementUserCounter() {
        int counter = Integer.parseInt((String) servletContext.getAttribute(COUNT_KEY));
        counter--;

        if (counter < 0) {
            counter = 0;
        }

        servletContext.setAttribute(COUNT_KEY, Integer.toString(counter));
    }

    @SuppressWarnings("unchecked")
    synchronized void addUsername(User user) {
        users = (Set<User>) servletContext.getAttribute(USERS_KEY);

        if (users == null) {
            users = new LinkedHashSet<User>();
        }

        if (!users.contains(user)) {
            users.add(user);
            servletContext.setAttribute(USERS_KEY, users);
            incrementUserCounter();
        }
    }

    @SuppressWarnings("unchecked")
    synchronized void removeUsername(User user) {
        users = (Set<User>) servletContext.getAttribute(USERS_KEY);

        if (users != null) {
            users.remove(user);
        }

        servletContext.setAttribute(USERS_KEY, users);
        decrementUserCounter();
    }


    public void attributeAdded(HttpSessionBindingEvent event) {
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		PreparedStatement ps4 = null;
		ResultSet rs4 = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		PreparedStatement ps3 = null;
		ResultSet rs3 = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
        if (event.getName().equals(EVENT_KEY) && !isAnonymous()) {
			SecurityContext securityContext = (SecurityContext) event.getValue();

	       
	        String appid=null;
	        try {
	            InputStream is = servletContext.getResourceAsStream("/META-INF/MANIFEST.MF");
	            if (is == null) {
		        	System.out.println("META-INF/MANIFEST.MF not found.");
	            } else {
	                Manifest mf = new Manifest();
	                mf.read(is);
	                Attributes atts = mf.getMainAttributes();
	                appid = atts.getValue("App-Id");
	                event.getSession().setAttribute("APPID", appid);
	                
	            }
	        } catch (IOException e) {
	        	System.out.println("I/O Exception reading manifest: " + e.getMessage());
	        }
            if (securityContext != null && securityContext.getAuthentication().getPrincipal() instanceof User) {
				User user = (User) securityContext.getAuthentication().getPrincipal();
				event.getSession().setAttribute("USERID", user.getId());
				event.getSession().setAttribute("MECHANISMID", user.getMechanismId());
				event.getSession().setAttribute("MECHANISMNAME", user.getMechanismName());
				event.getSession().setAttribute("DEPARTMENTID", user.getDepartmentId());
				event.getSession().setAttribute("DEPARTMENTNAME", user.getDepartmentName());
				event.getSession().setAttribute("USERNAME", user.getUsername());
				event.getSession().setAttribute("NAME", user.getFirstName());
				event.getSession().setAttribute("LASTNAME", user.getLastName());
				event.getSession().setAttribute("ROLENAME", securityContext.getAuthentication().getAuthorities()
						.toString().replace("[", "").replace("]", ""));
				event.getSession().setAttribute("ROLENAMES", securityContext.getAuthentication().getAuthorities().toString().replace("[", "").replace("]", ""));
				System.out.println("attributeAdded:"+user.getId()+"--------" + user.getUsername() + "-----" + securityContext
						.getAuthentication().getAuthorities().toString().replace("[", "").replace("]", ""));
                addUsername(user);

				try {
					conn = isConnSupplied ? userConn : ResourceManager.getConnection();
					String urSQL = "SELECT a.role_id FROM user_role a  where a.user_id = ?";
					ps = conn.prepareStatement(urSQL);
					ps.setString(1, ""+user.getId());
					rs = ps.executeQuery();
					while (rs.next()) {
						event.getSession().setAttribute("ROLEID", rs.getString(1));
					}
					String umSQL = "SELECT a.department_id,a.department_name FROM user_mechanism a  where a.user_id like ?";
					ps1 = conn.prepareStatement(umSQL);
					ps1.setString(1, ""+user.getId());
					rs1 = ps1.executeQuery();
					StringBuffer buff = new StringBuffer();
					int i=0;
					while (rs1.next()) {
						buff.append(rs1.getString(1));
						i++;
					}
					String type="";
					if(i==0) {
						urSQL = "SELECT a.role_id FROM user_role a  where a.user_id = ?";
						ps2 = conn.prepareStatement(urSQL);
						ps2.setString(1, ""+user.getId());
						rs2 = ps2.executeQuery();
						String roleid="";
						while (rs2.next()) {
							roleid=rs2.getString(1);
						}
						String rSQL = "SELECT a.name FROM role a  where a.id = ?";
						ps3 = conn.prepareStatement(rSQL);
						ps3.setString(1, ""+user.getId());
						rs3 = ps3.executeQuery();
						String name="";
						while (rs3.next()) {
							name=rs3.getString(1);
						}
						String rmSQL = "SELECT a.department_id,a.department_name,a.data_range FROM role_mechanism a  where a.role_id like ?";
						ps4 = conn.prepareStatement(rmSQL);
						ps4.setString(1, roleid);
						rs4 = ps4.executeQuery();
						buff = new StringBuffer();
						while (rs4.next()) {
							buff.append(rs4.getString(1));
							type=rs4.getString(3);
						}
						if(type.equals("")) {
							if(name.equals("ROLE_COMPANY")) {
								event.getSession().setAttribute("FROM", "全部");
								
							}else {
								event.getSession().setAttribute("FROM", "未设置");
								
							}
						}else {
							
							if(type.equals("未设置")) {
								event.getSession().setAttribute("FROM", "未设置");
							}else if(type.equals("自定义数据")) {
								event.getSession().setAttribute("DEPARTMENTAUTHORITY", buff.toString());
							}else if(type.equals("全部")) {
								event.getSession().setAttribute("FROM", "全部");
							}else {
								event.getSession().setAttribute("FROM", "本部门");
								event.getSession().setAttribute("DEPARTMENTAUTHORITY", user.getDepartmentId());
							}
						}
					}else {
						event.getSession().setAttribute("DEPARTMENTAUTHORITY", buff.toString());
					}
					
				} catch (SQLException e) {
					ResourceManager.close(rs);
					ResourceManager.close(ps);
					ResourceManager.close(rs1);
					ResourceManager.close(ps1);
					ResourceManager.close(rs2);
					ResourceManager.close(ps2);
					ResourceManager.close(rs3);
					ResourceManager.close(ps3);
					ResourceManager.close(rs4);
					ResourceManager.close(ps4);
					if (!isConnSupplied) {
						ResourceManager.close(conn);
					}
				} finally {
					ResourceManager.close(rs);
					ResourceManager.close(ps);
					ResourceManager.close(rs1);
					ResourceManager.close(ps1);
					ResourceManager.close(rs2);
					ResourceManager.close(ps2);
					ResourceManager.close(rs3);
					ResourceManager.close(ps3);
					ResourceManager.close(rs4);
					ResourceManager.close(ps4);
					if (!isConnSupplied) {
						ResourceManager.close(conn);
					}
				}
            }
        }
    }

    private boolean isAnonymous() {
        AuthenticationTrustResolver resolver = new AuthenticationTrustResolverImpl();
        SecurityContext ctx = SecurityContextHolder.getContext();
        if (ctx != null) {
            Authentication auth = ctx.getAuthentication();
            return resolver.isAnonymous(auth);
        }
        return true;
    }

    /**
     * When user's logout, remove their name from the hashMap
     *
     * @param event the session binding event
     * @see javax.servlet.http.HttpSessionAttributeListener#attributeRemoved(javax.servlet.http.HttpSessionBindingEvent)
     */
    public void attributeRemoved(HttpSessionBindingEvent event) {
        if (event.getName().equals(EVENT_KEY) && !isAnonymous()) {
            SecurityContext securityContext = (SecurityContext) event.getValue();
            Authentication auth = securityContext.getAuthentication();
            if (auth != null && (auth.getPrincipal() instanceof User)) {
                User user = (User) auth.getPrincipal();
                removeUsername(user);
            }
        }
    }

    /**
     * Needed for Acegi Security 1.0, as it adds an anonymous user to the session and
     * then replaces it after authentication. http://forum.springframework.org/showthread.php?p=63593
     *
     * @param event the session binding event
     * @see javax.servlet.http.HttpSessionAttributeListener#attributeReplaced(javax.servlet.http.HttpSessionBindingEvent)
     */
    public void attributeReplaced(HttpSessionBindingEvent event) {
        if (event.getName().equals(EVENT_KEY) && !isAnonymous()) {
            final SecurityContext securityContext = (SecurityContext) event.getValue();
            if (securityContext.getAuthentication() != null
                    && securityContext.getAuthentication().getPrincipal() instanceof User) {
                final User user = (User) securityContext.getAuthentication().getPrincipal();
                addUsername(user);
            }
        }
    }
    
    public void sessionCreated(HttpSessionEvent se) { 
    }
    
    public void sessionDestroyed(HttpSessionEvent se) {
        Object obj = se.getSession().getAttribute(EVENT_KEY);
        if (obj != null) {
            se.getSession().removeAttribute(EVENT_KEY);
        }
    }
}
