package web.repository;

import web.model.User;

import java.util.List;

public interface UserRepository {

    List<User> findAll(int page, int size);

    User findById(Long id);

    void save(User user);

    int delete(Long id);

    int update(Long id, User user);

    Long count();
}
