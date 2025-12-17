package web.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dto.PageDto;
import web.exception.UserNotFoundException;
import web.model.User;
import web.repository.UserRepository;

import java.util.List;

import static java.lang.Math.ceil;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public PageDto<User> findAll(int page, int size) {
        log.info("Service: findAll called");
        List<User> items = userRepository.findAll(page,size);
        long totalElements = userRepository.count();
        int totalPages = (int) ((totalElements + size - 1)/size);
        return new PageDto<>(items, page, size, totalElements, totalPages);
    }

    @Transactional(readOnly = true)
    @Override
    public User findById(Long id) {
        log.info("Service: findById called");
        User user = userRepository.findById(id);
        if (user == null) {
            log.error("User with id {} not found", id);
            throw new UserNotFoundException(id);
        }
        return user;
    }

    @Transactional
    @Override
    public void save(User user) {
        log.info("Service: save called");
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        log.info("Service: delete called");
        int rows = userRepository.delete(id);
        if (rows < 1) {
            log.error("User with id {} not found", id);
            throw new UserNotFoundException(id);
        }
    }

    @Transactional
    @Override
    public void update(Long id, User user) {
        log.info("Service: update called");
        int rows = userRepository.update(id, user);
        if (rows < 1) {
            log.error("User with id {} not found", id);
            throw new UserNotFoundException(id);
        }
    }
}
