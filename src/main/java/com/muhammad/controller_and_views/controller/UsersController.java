package com.muhammad.controller_and_views.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.muhammad.controller_and_views.modells.User;
import com.muhammad.controller_and_views.service.UserService;

@Controller
public class UsersController {
	
	private final UserService userService;
    
    public UsersController(UserService userService) {
        this.userService = userService;
    }
    
    @RequestMapping("/registration")
    public String registerForm(@ModelAttribute("user") User user) {
        return "view/registrationPage.jsp";
    }
    @RequestMapping("/login")
    public String login() {
        return "view/loginPage.jsp";
    }
    
    @RequestMapping(value="/registration", method=RequestMethod.POST)
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session) {
        if(result.hasErrors()) {
        	return "redirect:/registration";
        }else {
        	userService.registerUser(user);
        	return "redirect:/home";
        }
    }
    
    @RequestMapping(value="/login", method=RequestMethod.POST)
    public String loginUser(@RequestParam("email") String email, @RequestParam("password") String password, Model model, HttpSession session) {
        boolean isAuthenticated = userService.authenticateUser(email, password);
        if(isAuthenticated) {
        	User user = userService.findByEmail(email);
        	session.setAttribute("userId", user.getId());
        	return "redirect:/home";
        }else {
        	return "redirect:/login";
        }
    }
    
    @RequestMapping("/home")
    public String home(HttpSession session, Model model) {
        Long id = (Long) session.getAttribute("userId");
        if(id != null) {
        	User user = userService.findUserById(id);
        	model.addAttribute("user" , user);
        	return "view/homePage.jsp";
        }else {
        	return "redirect:/login";
        }
    }
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
    	session.invalidate();
        return "redirect:/login";
    }
}

