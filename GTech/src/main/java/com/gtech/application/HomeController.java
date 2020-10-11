package com.gtech.application;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import com.google.gson.Gson;
import com.gtech.adapter.GTechAdapter;
import com.gtech.entity.User;

@Controller
@SessionAttributes("userID")
public class HomeController {
	@Autowired 
	protected GTechAdapter GTechAdapter;

	@RequestMapping(value="/ajaxLogin",method= {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String ajaxLogin(HttpServletRequest request, Model model,HttpSession session) throws ParseException {
		String username = request.getParameter("userName");
		String password = request.getParameter("userPassword");
		Random rnd = new Random();
		String loginToken = "";
		List<User> userList = GTechAdapter.getUserByUserNameAndPassword(username, password);
		if(userList.size()>0) {
			for(int i=0;i<5;i++) {
				loginToken = loginToken + (char) (97+rnd.nextInt(26));
			}
			session.setAttribute("loginID", loginToken);
			GTechAdapter.updateUserLoginToken(userList.get(0).getUserName(), loginToken);
			return loginToken;
		}else {
			return "0";
		}
	}
	@RequestMapping(value="/Login",method= {RequestMethod.GET,RequestMethod.POST})
	public String login(HttpServletRequest request, Model model,HttpSession session) {
		System.out.println("masuk login");

		model.addAttribute("action","/GTech/Home");
		return "Login";
	}
	@RequestMapping(value="/Home",method= {RequestMethod.GET,RequestMethod.POST})
	public String homePage(HttpServletRequest request, Model model) {
		System.out.println("masuk home");
		return "home";
	}
	
	@RequestMapping(value="/Register",method= {RequestMethod.GET,RequestMethod.POST})
	public String registerPage(HttpServletRequest request, Model model) {
		model.addAttribute("action","/GTech/Register/Action");
		return "register";
	}
	
	@RequestMapping(value="/Register/Action",method= {RequestMethod.GET,RequestMethod.POST})
	public String registerUser(HttpServletRequest request, Model model) throws ParseException {
		
		String userNameType=request.getParameter("userNameType");
		String userName = "";
		if(userNameType.equals("email")) {
			userName = request.getParameter("userEmail");
		}else {
			userName = request.getParameter("phoneNumber");
		}
		String password = request.getParameter("password");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String gender = request.getParameter("gender");
		String DoB = request.getParameter("DoB");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<User> userList = GTechAdapter.getUserList();
		
		User user = new User();
		user.setUserID(userList.size()+1);
		user.setUserName(userName);
		user.setPassword(password);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setDoB(sdf.parse(DoB));
		user.setLoginToken("");
		GTechAdapter.addObject(user);
		return "redirect:/Home";
	}
	@RequestMapping(value="/ajaxLogout",method= {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String logOut(HttpServletRequest request, Model model,HttpSession session) {
		request.getSession().removeAttribute("loginID");
		session.invalidate();
		return "1";
	}
	@RequestMapping(value="/Profile",method= {RequestMethod.GET,RequestMethod.POST})
	protected String profilePage(HttpServletRequest request, Model model, HttpSession session) {
		
		if(session.getAttribute("loginID")!=null){
			List<User> user = GTechAdapter.getUserByLoginToken(session.getAttribute("loginID").toString());
			model.addAttribute("user",user.get(0));
			return "Profile";
		}else {
			return "redirect:/ErrorPage";
		}
	}
	@RequestMapping(value="/ajaxUpdateProfile",method= {RequestMethod.GET,RequestMethod.POST})
	protected @ResponseBody String updateProfile(HttpServletRequest request, Model model,HttpSession session){
		
		String loginToken = session.getAttribute("loginID").toString();
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String gender = request.getParameter("gender");
		String DoB = request.getParameter("DoB");
		if(loginToken==null) {
			return "0";
		}else {
			GTechAdapter.updateUserProfile(firstName, lastName, gender, DoB, loginToken);
			return "1";
		}
	}
	@RequestMapping(value="/TransactionHistory",method= {RequestMethod.GET,RequestMethod.POST})
	public String userTransHistory(HttpServletRequest request, Model model) {
		
		return "TransactionHistory";
	}
	@RequestMapping(value="/Product",method= {RequestMethod.GET,RequestMethod.POST})
	public String product(HttpServletRequest request, Model model) {
		
		return "Product";
	}
}
