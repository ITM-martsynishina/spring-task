package web.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import web.exception.DataBaseException;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static web.exception.ErrorTypes.*;

@Slf4j
@Repository
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> findAll(int page, int size) {

        if (page < 1 || size < 1) {
            throw new IllegalArgumentException("Page and size must be >=1");
        }

        int offset = (page - 1) * size;
        log.debug("Getting all users");

        try {
            List<User> users = entityManager.createQuery("select u from User u order by u.id", User.class).setFirstResult(offset).setMaxResults(size).getResultList();
            log.info("Selected {} users, {} page", users.size(), page);
            return users;
        } catch (RuntimeException e) {
            log.error("Error while getting all users", e);
            throw new DataBaseException(FIND_ALL_USERS_ERROR, "Error while getting all users", e);
        }
    }

    @Override
    public Long count() {

        log.debug("Getting users count");
        try {
            return entityManager.createQuery("select count(u) from User u", Long.class).getSingleResult();
        } catch (RuntimeException e) {
            log.error("Error while getting users count", e);
            throw new DataBaseException(GET_COUNT_ERROR, "Error while getting users count", e);
        }
    }


    @Override
    public User findById(Long id) {
        log.debug("Getting user with id {}", id);

        try {
            return entityManager.find(User.class, id);
        } catch (RuntimeException e) {
            log.error("Error while getting user with id {}", id, e);
            throw new DataBaseException(FIND_USER_ERROR, "Error while getting user with id " + id, e);
        }
    }

    @Override
    public void save(User user) {
        log.debug("Saving user {}", user);

        try {
            entityManager.persist(user);
            log.info("Saved user {}", user);
        } catch (RuntimeException e) {
            log.error("Error while saving user {}", user, e);
            throw new DataBaseException(CREATE_USER_ERROR, "Error while saving user " + user, e);
        }
    }

    @Override
    public int delete(Long id) {
        log.debug("Deleting user with id {}", id);

        try {
            int rows = entityManager.createQuery("delete from User u where u.id = :id").setParameter("id", id).executeUpdate();
            if (rows > 0) {
                log.info("Delete user with id {}", id);
            } else {
                log.info("User with id {} was not found", id);
            }
            return rows;
        } catch (RuntimeException e) {
            log.error("Error while deleting user whit id {}", id, e);
            throw new DataBaseException(DELETE_USER_ERROR, "Error while deleting user with id " + id, e);
        }
    }

    @Override
    public int update(Long id, User user) {
        log.debug("Updating user with id {}", id);

        try {
        int rows = entityManager.createQuery("""
                update User u
                set u.name = coalesce(:name, u.name),
                   u.lastName = coalesce(:lastName, u.lastName),
                   u.email = coalesce(:email, u.email),
                   u.birthDate = coalesce(:birthDate, u.birthDate)
                where u.id = :id
             """)
                .setParameter("id", id)
                .setParameter("name", user.getName())
                .setParameter("lastName", user.getLastName())
                .setParameter("email", user.getEmail())
                .setParameter("birthDate", user.getBirthDate())
                .executeUpdate();

        if (rows > 0) {
            log.info("User with id {} updated", id);
        } else {
            log.info("User with id {} not found", id);
        }
        return rows;
        } catch (RuntimeException e) {
            log.error("Error while updating user with id {}", id, e);
            throw new DataBaseException(UPDATE_USER_ERROR, "Error while updating user with id " + id, e);
        }
    }
}
