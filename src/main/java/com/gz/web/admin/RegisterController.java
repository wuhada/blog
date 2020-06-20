package com.gz.web.admin;

import com.gz.po.User;
import com.gz.service.UserService;
import com.gz.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.servlet.http.HttpSession;

/**
 * @authod wu
 * @date 2020/6/8 15:12
 */
@Controller
@RequestMapping("/admin")
public class RegisterController {

    @Autowired
    private UserService userService;


    @GetMapping("/register/input")
    public String toRegister(HttpSession session) {
        session.setAttribute("user",new User());
        return  "admin/register";
    }

    @PostMapping("/register")
    public String register(@RequestParam("avatar") MultipartFile file
            , @RequestParam("username") String username
            , @RequestParam("nickname") String nickname
            , @RequestParam("email") String email
            , @RequestParam("password") String password
            ,RedirectAttributes attributes) {
        User user = new User();
        user.setType(1);
        user.setPassword(password);
        user.setEmail(email);
        user.setAvatar(file.getOriginalFilename());
        user.setUsername(username);
        user.setNickname(nickname);
        FileUtils.upload(file, "E:/image", file.getOriginalFilename());
        User user1 = userService.saveUser(user);
        if (user1 == null) {
            attributes.addFlashAttribute("message","用户名已存在！");
            return "redirect:/admin/register/input";
        }
        return "redirect:/admin";
    }

    @GetMapping("/get")
    public String getImage(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return "redirect:/img/" + user.getAvatar();
    }

}
