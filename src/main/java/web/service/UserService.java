package web.service;

import web.dto.PageDto;
import web.model.User;

public interface UserService {

    PageDto<User> findAll(int page, int size);

    User findById(Long id);

    void save(User user);

    void delete(Long id);

    void update(Long id, User user);
}
