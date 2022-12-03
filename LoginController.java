package com.sumit.onlineexam2;

import java.util.HashMap;
import java.util.List;


import org.hibernate.query.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
	@Autowired
	SessionFactory factory;
	
	@RequestMapping("showLogin")
	public String showLogin() {
		
		
		return "login";
		
	}
@RequestMapping("showregister")
	public String showregister(){
		
		
		
		return "register";
		
	}
@RequestMapping("validate")
public ModelAndView validate(String username,String password,HttpServletRequest request) {
	
	System.out.println("username from browser " + username);
	System.out.println("password from browser " + password);
	
	ModelAndView modelAndView=new ModelAndView();
	
	Session session=factory.openSession();
	
	Users users=null;
	
	if(!username.equals("admin"))
	{
		users=session.load(Users.class,username);
	
		System.out.println(users.getUsername() + " " + users.getPassword());
	}
	
	if(username.equals("admin") && password.equals("admin"))
	{
		modelAndView.setViewName("questionmanagement");
		modelAndView.addObject("message","welcome " + username);
	}
	
	else if(username.equals(users.getUsername()) && password.equals(users.getPassword()))
	{
		HttpSession httpsession=request.getSession(); 
		httpsession.setAttribute("username",username);
	
		modelAndView.setViewName("welcome");
		modelAndView.addObject("message","welcome " + username);
	}
	
	else
	{
		modelAndView.setViewName("login");
		modelAndView.addObject("message","invalid credentials");
	}
	
	return modelAndView;
	
}
	

@RequestMapping("startExam")
public ModelAndView startExam(String selectedSubject,HttpServletRequest request) {
	System.out.println(selectedSubject);
	
	Session session=factory.openSession();
	
	HttpSession httpsession=request.getSession();
	httpsession.setAttribute("qno", 0);
	httpsession.setAttribute("timeremaining",120);
	
	
	//List listOfQuestion=session.createCriteria(Questions.class).add(Restrictions.eq("subject", selectedSubject)).list();
	Query query=session.createQuery("from Questions where subject=:subject");
	query.setParameter("subject",selectedSubject);
	List<Questions> listOfQuestion=query.list();
	ModelAndView modelAndView=new ModelAndView();
	modelAndView.setViewName("question");
	modelAndView.addObject("listOfQuestion", listOfQuestion);
	modelAndView.addObject("question",listOfQuestion.get(0));
	httpsession.setAttribute("allquestions", listOfQuestion);
	
	HashMap<Integer,Answer> hashmap=new HashMap<Integer,Answer>();
	
	httpsession.setAttribute("submittedDetails", hashmap);
	httpsession.setAttribute("score",0);
	
	return modelAndView;
	
}
}
