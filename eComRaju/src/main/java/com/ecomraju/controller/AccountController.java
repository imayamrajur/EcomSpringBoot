package com.ecomraju.controller;

import java.io.File;
import java.io.IOException;

import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ecomraju.bean.MailInfo;
import com.ecomraju.dao.CustomerDAO;
import com.ecomraju.entity.Customer;
import com.ecomraju.service.CookieService;
import com.ecomraju.service.MailService;

@Controller
public class AccountController {
	@Autowired
	CustomerDAO dao;
	
	@Autowired
	HttpSession session;
	
	@Autowired
	CookieService cookie;
	
	@Autowired
	ServletContext app;
	
	@Autowired
	MailService mailer;
	
	@Autowired
	HttpServletRequest request;
	
	@GetMapping("/account/login")
	public String login( Model model) {
		Cookie ckid = cookie.read("userid");
		Cookie ckpw = cookie.read("ckpw");
		if(ckid != null) {
			String uid = ckid.getValue();
			String pwd = ckpw.getValue();
			model.addAttribute("uid", uid);
			model.addAttribute("pwd", pwd);
		}
		return "account/login";
	}
	
	@PostMapping("/account/login")
	public String login(Model model,
			@RequestParam("id") String id,
			@RequestParam("pw") String pw,
			@RequestParam(value="rm", defaultValue="false") boolean rm) {
		Customer user = dao.findById(id);
		if(user == null) {
			model.addAttribute("message", "Invalid Username!");
		}else if(!pw.equals(user.getPassword())) {
			model.addAttribute("message", "Invalid password!");
		}else if(!user.getActivated()) {
			model.addAttribute("message", "User Inactivated!");
		}else {
			model.addAttribute("message", "Login Successfully");
			session.setAttribute("user", user);
			if(rm == true) {
				cookie.create("userid", user.getId(), 30);
				cookie.create("pass", user.getPassword(), 30);
			}else {
				cookie.delete("userid");
				cookie.delete("pass");
			}//go Back applicable product page
			
			String backUrl = (String) session.getAttribute("back-url");
			if(backUrl != null) {
				return "redirect:" + backUrl;
			}
		}
		return "account/login";
	}
	
	@RequestMapping("/account/logoff")
	public String logout(Model model) {
		session.removeAttribute("user");
		return "redirect:/home/index";
		
	}
	
	@GetMapping("/account/register")
	public String register( Model model ) {
		Customer user = new Customer();
		model.addAttribute("form",user);
		return "account/register";
	}
	
	@PostMapping("/account/register")
	public String register( Model model, 
			@Validated @ModelAttribute("form") Customer user, BindingResult errors, 
			@RequestParam("photo_file") MultipartFile file) throws IllegalStateException, IOException, MessagingException {
		if(errors.hasErrors()) {
			model.addAttribute("message", "Please Form fill Correctly ");
			return "account/register";
		}else {
			Customer user2 = dao.findById(user.getId());
			if(user2 != null) {
				model.addAttribute("message", " Username is Used! ");
			}
		}
		if(file.isEmpty()) {
			user.setPhoto("user.jpg");
		}else {
			String dir = app.getRealPath("/static/imagies/customers");
			File f = new File(dir, file.getOriginalFilename());
			file.transferTo(f);
			user.setPhoto(f.getName());
			
		}
		user.setActivated(false);
		user.setAdmin(false);
		dao.create(user);
		model.addAttribute("message", "Register successfully");
		
		String from = "imayamrajur@gmail.com";
		String to = user.getEmail();
		String subject = "Welcome";
		String url = request.getRequestURL().toString().replace("register", "activate/"+user.getId());
		String body = "Click <a href='"+url+"'>Activate</a>";
		MailInfo mail = new MailInfo(from, to, subject, body);
		mailer.send(mail);
		
		return "account/register";
	}
	
	@GetMapping("/account/activate/{id}")
	public String activate( Model model, @PathVariable("id") String id ) {
		Customer user = dao.findById(id);
		user.setActivated(true);
		user.setAdmin(true);
		dao.update(user);
		return "redirect:/account/login";
	}

	@GetMapping("/account/forgot")
	public String forgot( Model model ) {
		return "account/forgot";
	}
	
	@PostMapping("/account/forgot")
	public String forget( Model model, @RequestParam("id") String id,
			@RequestParam("email") String email ) throws MessagingException {
		Customer user = dao.findById(id);
		if(user == null) {
			model.addAttribute("message", "Invalid Username");
		}else if(!email.equals(user.getEmail())) {
			model.addAttribute("message", "Invalid Email Address");
		}else {
			String from = "imayamrajur@gmail.com";
			String to = user.getEmail();
			String subject = "Forgot Password";
			String body = "Your Password is   "+user.getPassword();
			MailInfo mail = new MailInfo(from, to, subject, body);
			mailer.send(mail);
			model.addAttribute("message", "Your Password was sent Your Registed Email Address");
		}
		model.addAttribute("form",user);
		return "redirect:/account/login";
	}
	
	
	@GetMapping("/account/change")
	public String change( Model model ) {
		return "account/change";
	}
	
	@PostMapping("/account/change")
	public String change( Model model, @RequestParam("id") String id,
			@RequestParam("pw") String pw, 
			@RequestParam("pw1") String pw1, 
			@RequestParam("pw2") String pw2 ) {
		if(!pw1.equals(pw2)) {
			model.addAttribute("message", "Conform Password is not match!");
		}else {
		Customer user = dao.findById(id);
		if(user == null) {
			model.addAttribute("message", "Invalid Username");
		}else if(!pw.equals(user.getPassword())) {
			model.addAttribute("message", "Invalid Password!");
		}else {
			user.setPassword(pw1);
			dao.update(user);
			model.addAttribute("message", "Change Password Successfully");
		}
		
	}
		return "redirect:/account/login";
	}
	
	@GetMapping("/account/edit")
	public String edit( Model model ) {
		Customer user = (Customer)session.getAttribute("user");
		model.addAttribute("form",user);
		return "account/edit";
	}
	
	@PostMapping("/account/edit")
	public String edit( Model model, 
			@ModelAttribute("form") Customer user,
			@RequestParam("photo_file") MultipartFile file) throws IllegalStateException, IOException {
		if(!file.isEmpty()) {
			user.setPhoto("user.jpg");
			String dir = app.getRealPath("/static/imagies/customers");
			File f = new File(dir, file.getOriginalFilename());
			file.transferTo(f);
			user.setPhoto(f.getName());
			
		}
		
		dao.update(user);
		session.setAttribute("user", user);
		model.addAttribute("message", "Account Updated successfully");
		return "account/edit";
	}

}
