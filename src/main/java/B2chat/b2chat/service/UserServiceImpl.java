package B2chat.b2chat.service;

import B2chat.b2chat.Utils;
import B2chat.b2chat.entity.User;
import B2chat.b2chat.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User createUser(User user) {
        validUser(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(Long id, User updatedUser) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException(Utils.USER_NOT_FOUND);
        }
        validUser(updatedUser);
        updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        updatedUser.setId(id);
        return userRepository.save(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException(Utils.USER_NOT_FOUND);
        }
        userRepository.deleteById(id);
    }

    private boolean isValidEmail(String email) {
        return email != null && email.matches(Utils.REGEX_EMAIL);
    }

    public void validUser(User user){
        if (!isValidEmail(user.getEmail())) {
            throw new IllegalArgumentException(Utils.EMAIL_INVALID);
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException(Utils.EMAIL_DUPLICATE);
        }
    }
}
