package com.ecomraju.admin.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecomraju.dao.ProductDAO;
import com.ecomraju.entity.Product;

@Controller
public class ProductManager {
	@Autowired
	ProductDAO dao;
	@Autowired
	ServletContext app;
	
	@RequestMapping("admin/product/index")
	public String index(Model model) {
		Product entity = new Product();
		model.addAttribute("entity", entity);
		model.addAttribute("list", dao.findAll());
		return "admin/product/index";
	}
	
	@RequestMapping("admin/product/edit/{id}")
	public String edit(Model model, @PathVariable("id") Integer id) {
		Product entity = dao.findById(id);
		model.addAttribute("entity", entity);
		model.addAttribute("list", dao.findAll());
		return "admin/product/index";
	}
	
	@RequestMapping("admin/product/create")
	public String create(RedirectAttributes model, @ModelAttribute("entity") Product entity , 
			@RequestParam("image_file") MultipartFile file) throws IllegalStateException, IOException {
		if(!file.isEmpty()) {
		
			entity.setImage(file.getOriginalFilename());
			String path = app.getRealPath("/static/imagies/products/"+entity.getImage());
			file.transferTo(new File(path));
		}
		dao.create(entity);
		model.addAttribute("message", "Successfully Added");
		return "redirect:/admin/product/index";
	}
	
	@RequestMapping("admin/product/update")
	public String update(RedirectAttributes model, 
			@ModelAttribute("entity") Product entity, 
			@RequestParam("image_file") MultipartFile file) throws IllegalStateException, IOException {
		if(file.isEmpty()) {
			entity.setImage("adduser.jpg");
		}else {
			entity.setImage(file.getOriginalFilename());
			String path = app.getRealPath("/static/imagies/products/"+entity.getImage());
			file.transferTo(new File(path));
		}
		dao.update(entity);
		model.addAttribute("message", "Update Successfully");
		return "redirect:/admin/product/edit/"+entity.getId();
	}
	
	@RequestMapping(value= {"/admin/product/delete","admin/product/delete/{id}"})
	public String delete(RedirectAttributes model, 
			@RequestParam(value="id", required = false) Integer id1, 
			@PathVariable(value = "id", required = false) Integer id2) {
		
		if(id1 !=null) {
			dao.delete(id1);
		}else {
			dao.delete(id2);
		}
		model.addAttribute("message", "Deleted Successfully");
		return "redirect:/admin/product/index";
	}

}
