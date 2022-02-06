package com.ecomraju.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecomraju.dao.OrderDAO;
import com.ecomraju.dao.OrderDetailDAO;
import com.ecomraju.entity.Order;

@Controller
public class OrderManager {
	@Autowired
	OrderDAO dao;
	@Autowired
	OrderDetailDAO ddao;
	
	@RequestMapping("admin/order/index")
	public String index(Model model) {
		Order entity = new Order();
		model.addAttribute("entity", entity);
		model.addAttribute("details", ddao.findByOrder(entity));
		model.addAttribute("list", dao.findAll());
		return "admin/order/index";
	}
	
	@RequestMapping("admin/order/edit/{id}")
	public String edit(Model model, @PathVariable("id") Integer id) {
		Order entity = dao.findById(id);
		model.addAttribute("entity", entity);
		model.addAttribute("details", ddao.findByOrder(entity));
		model.addAttribute("list", dao.findAll());
		return "admin/order/index";
	}
	
	@RequestMapping("admin/order/create")
	public String create(RedirectAttributes model, @ModelAttribute("entity") Order entity) {
		dao.create(entity);
		model.addAttribute("message", "Successfully Added");
		return "redirect:/admin/order/index";
	}
	
	@RequestMapping("admin/order/update")
	public String update(RedirectAttributes model, @ModelAttribute("entity") Order entity) {
		dao.update(entity);
		model.addAttribute("message", "Update Successfully");
		return "redirect:/admin/order/edit/"+entity.getId();
	}
	
	@RequestMapping(value= {"/admin/order/delete","admin/order/delete/{id}"})
	public String delete(RedirectAttributes model, 
			@RequestParam(value="id", required = false) Integer id1, 
			@PathVariable(value = "id", required = false) Integer id2) {
		
		if(id1 !=null) {
			dao.delete(id1);
		}else {
			dao.delete(id2);
		}
		model.addAttribute("message", "Deleted Successfully");
		return "redirect:/admin/order/index";
	}

}
