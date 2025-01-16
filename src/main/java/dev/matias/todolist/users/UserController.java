package dev.matias.todolist.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    // Automatically requests to Spring manage the life cycle of the Interface;
    @Autowired
    private IUserRepository userRepository;

    @PostMapping("/")
    public UserModel create(@RequestBody UserModel userModel) {
        // user created;
        return this.userRepository.save(userModel);
    }

}
