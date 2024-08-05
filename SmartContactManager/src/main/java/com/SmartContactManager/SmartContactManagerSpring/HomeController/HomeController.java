package com.SmartContactManager.SmartContactManagerSpring.HomeController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.SmartContactManager.Helper.Message;
import com.SmartContactManager.SmartContactManagerSpring.Entites.User;
import com.SmartContactManager.SmartContactManagerSpring.dao.UserRepository;

@Controller
public class HomeController {
	

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	
	@GetMapping("/test")
@ResponseBody
public String test() {
		User user=new User();
		user.setName("Abhishek Sharma");
		user.setEmail("abhisheksharmabca2020@gmail.com");
		userRepository.save(user);
		return "Working";
}
	
	
	@RequestMapping("/")
	public String homes(Model models) {
			models.addAttribute("tittle", "Smart-Contact Manager");
			return "home";
	}
	

	
@RequestMapping("/home")
public String home(Model models) {
		models.addAttribute("tittle", "Smart-Contact Manager");
		return "home";
}	


@RequestMapping("/about")
public String about(Model models) {
		models.addAttribute("tittle", "About-Contact Manager");
		return "about";
}

@RequestMapping("/signin")
public String login(Model models) {
		models.addAttribute("tittle", "Login-Contact Manager");
		models.addAttribute("user",new User() );
		return "login";
}



@RequestMapping("/signup")
public String signup(Model models) {
		models.addAttribute("tittle", "Register-Contact Manager");
		models.addAttribute("user",new User() );
		return "signup";
}

// handler for registering user

@RequestMapping(value="/do_register",method=RequestMethod.POST)
public String registerUser(@Valid @ModelAttribute("user") User user,BindingResult bindingResult,
		@RequestParam(value="agreement",defaultValue="false") boolean agreement,Model models,HttpSession session) { 
	System.out.println("My name is	");
	try {
		System.out.println("My name is abhishek we in try box");
		if(!agreement) {
			System.out.println("You does not agree the terms and condition");
			throw new Exception(" You do not agree term and condition");
		}
		if(bindingResult.hasErrors()==true) {
			System.out.println("ERROR"+bindingResult.toString());
			models.addAttribute("user", "user");
			return "signup";
			
		}
			
		user.setRole("ROLE_USER");
		user.setEnable(true);
		user.setImageurl("contact.JPG");
		// this is use to encode the password in the data base
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		System.out.println("Agreement"+agreement);
		System.out.println("USER"+user);
		User result=this.userRepository.save(user);
		models.addAttribute("user", new User());
		session.setAttribute("message", new Message("Sucessfully Register","alert-success"));
		System.out.println();
		return "signup";
	}
	catch(Exception e){
		System.out.println("My name is	cath1");
	e.printStackTrace();
	models.addAttribute("user",user);
session.setAttribute("message", new Message("something went wrong :  "+e.getMessage(),"alert-danger"));
return "signup";
	}
	
}




}
