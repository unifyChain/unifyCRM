package dafengqi.yunxiang.service;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import dafengqi.yunxiang.model.User;

/**
 * Web Service interface so hierarchy of Generic Manager isn't carried through.
 */

@Path("/users")
public interface UserService {
    /**
     * Retrieves a user by userId.  An exception is thrown if user not found
     *
     * @param userId the identifier for the user
     * @return User
     */
    @GET
    @Path("{id}")
    User getUser(@PathParam("id") String userId);

    /**
     * Finds a user by their username.
     *
     * @param username the user's username used to login
     * @return User a populated user object
     */
    User getUserByUsername(@PathParam("username") String username);

    /**
     * Retrieves a list of all users.
     *
     * @return List
     */
    @GET
    List<User> getUsers();

    /**
     * Saves a user's information
     *
     * @param user the user's information
     * @return updated user
     * @throws UserExistsException thrown when user already exists
     */
    @POST
    User saveUser(User user) throws UserExistsException;

    /**
     * Removes a user from the database by their userId
     *
     * @param userId the user's id
     */
    @DELETE
    void removeUser(String userId);
}
