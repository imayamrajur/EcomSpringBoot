package com.ecomraju.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecomraju.bean.MailInfo;
import com.ecomraju.dao.ProductDAO;
import com.ecomraju.entity.Product;
import com.ecomraju.service.CookieService;
import com.ecomraju.service.MailService;

@Controller
public class ProductController {
	@Autowired
	ProductDAO pdao;
	
	@Autowired
	CookieService cookie;
	
	@Autowired
	MailService mail;
	
	@RequestMapping("/product/list-by-category/{cid}")
	public String listByCategory(Model model , @PathVariable("cid") Integer categoryId) {
		List<Product> list = pdao.findByCategoryId(categoryId);
		model.addAttribute("list", list);
		return "product/list";
	}
	
	@RequestMapping("/product/list-by-special/{id}")
	public String listBySpecial(Model model , @PathVariable("id") Integer id) {
		List<Product> list = pdao.findBySpecial(id);
		model.addAttribute("list", list);
		return "product/list";
	}
	
	@RequestMapping("/product/list-by-keywords")
	public String listByKeywords(Model model ,
			 @RequestParam("keywords") String keywords) {
		List<Product> list = pdao.findByKeywords(keywords);
		model.addAttribute("list", list);
		return "product/list";
	}
	
	@RequestMapping("/product/detail/{id}")
	public String details(Model model , @PathVariable("id") Integer id) {
		Product prod = pdao.findById(id);
		model.addAttribute("prod", prod);
		
		// Viewer Count
		prod.setViewCount(prod.getViewCount() + 1);
		pdao.update(prod);
		// Category list
		List<Product> list = pdao.findByCategoryId(prod.getCategory().getId());
		model.addAttribute("list", list);
		// Favorite list
		Cookie favo = cookie.read("favo");
		if(favo != null) {
			String ids = favo.getValue();
			List<Product> favo_list = pdao.findByIds(ids);
			model.addAttribute("list", favo_list);
		}
		
		//Viewed Product list
		Cookie viewed = cookie.read("viewed");
		String value = id.toString();
		if(viewed != null) {
			value = viewed.getValue();
			value += "," + id.toString();
		}
		cookie.create("viewed", value, 10);
		List<Product> viewed_list = pdao.findByIds(value);
		model.addAttribute("viewed", viewed_list);
				
		return "product/detail";
	}
	
	@ResponseBody
	@RequestMapping("/product/add-to-favo/{id}")
	public boolean addToFavorite(Model model , @PathVariable("id") Integer id) {
		Cookie favo = cookie.read("favo");
		String value = id.toString();
		if(favo != null) {
			value = favo.getValue();
			if(!value.contains(id.toString())) {
				value += ","+id.toString();
			}
			else {
				return false;
			}
		}
		cookie.create("favo", value, 30);
		return true;
	}
	
	@ResponseBody
	@RequestMapping("/product/send-to-friend")
	public boolean sendToFriend(Model model , MailInfo info, HttpServletRequest req) {
		//Send mail
		info.setSubject("New Product");
		try {
			String id = req.getParameter("id");
			String link = req.getRequestURL().toString().replace("send-to-friend", "detail/"+id);
			info.setBody(info.getBody()+"<hr><a href='"+link+"'>ClickHere</a>");
			mail.send(info);
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}

}
