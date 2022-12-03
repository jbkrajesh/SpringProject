package com.sumit.onlineexam2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class TimeController {
	
	@RequestMapping("getRemainingTime")
	public int getRemainingTime(HttpServletRequest request) {
		
	
		HttpSession httpsession=request.getSession();
		httpsession.setAttribute("timeremaining", (Integer)httpsession.getAttribute("timeremaining")-1);
		
		System.out.println("remaining time is"+httpsession.getAttribute("timeremaining"));
		return (int)httpsession.getAttribute("timeremaining");
		
	}

}
