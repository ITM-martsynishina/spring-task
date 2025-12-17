package web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

    private final UserService userService;

    @GetMapping
    public String printUsers(ModelMap model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        model.addAttribute("usersPage", userService.findAll(page,size));
        model.addAttribute("newUser", new User());
        return "users";
    }

    @PostMapping
    public String saveUser(@ModelAttribute("newUser") User newUser) {
        userService.save(newUser);
        return "redirect:/users";
    }

    @GetMapping(value = "/{id}")
    public String showUser(ModelMap model, @PathVariable Long id) {
        model.addAttribute("user", userService.findById(id));
        return "user";
    }

    @PostMapping(value = "/{id}/delete")
    public String deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return "redirect:/users";
    }

    @PostMapping(value = "/{id}/update")
    public String updateUser(@ModelAttribute("user") User updateUser, @PathVariable Long id) {
        userService.update(id, updateUser);
        return "redirect:/users";
    }
}
