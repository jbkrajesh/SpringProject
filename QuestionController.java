package com.sumit.onlineexam2;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class QuestionController {

	@RequestMapping("endexam")
	public ModelAndView endexam(HttpServletRequest request) {

		HttpSession httpsession = request.getSession();
		HashMap<Integer, Answer> hashmap = (HashMap) httpsession.getAttribute("submittedDetails");
		Collection<Answer> allAnswers = hashmap.values();
		for (Answer answer : allAnswers) {
			if (answer.originalAnswer.equals(answer.submittedAnswer)) {
				httpsession.setAttribute("score", (Integer) httpsession.getAttribute("score") + 1);

			}

		}
		ModelAndView ModelAndView = new ModelAndView();
		ModelAndView.setViewName("score");
		ModelAndView.addObject("finalscore", httpsession.getAttribute("score"));
		ModelAndView.addObject("allAnswers", allAnswers);
		httpsession.invalidate();

		return ModelAndView;

	}

	@RequestMapping("next")
	public ModelAndView next(HttpServletRequest request) {
		HttpSession httpsession = request.getSession();
		Integer i = (Integer) httpsession.getAttribute("qno");
		int nextQno = i+1;
		List<Questions> list = (List<Questions>) httpsession.getAttribute("allquestions");
		ModelAndView ModelAndView = new ModelAndView();
		ModelAndView.setViewName("question");
		if (nextQno<list.size()) {
			Questions question = list.get(nextQno);

			ModelAndView.addObject("question", question);
			httpsession.setAttribute("qno", nextQno);
			
			HashMap<Integer, Answer> hashmap =(HashMap) httpsession.getAttribute("submittedDetails");
			Answer answer=hashmap.get(question.qno);
			
			String previousAnswer="";
			if(answer!=null) {
				previousAnswer=answer.getSubmittedAnswer();
			}
					
			ModelAndView.addObject("previousAnswer", previousAnswer);
			
			
			
			
		} else {
			ModelAndView.addObject("question", list.get(list.size() - 1));
			ModelAndView.addObject("message", "Click on privious button");
		}
		return ModelAndView;

	}

	@RequestMapping("previous")
	public ModelAndView previous(HttpServletRequest request) {
		HttpSession httpsession = request.getSession();
		Integer i = (Integer) httpsession.getAttribute("qno");
		int previousQno=i-1;
		List<Questions> list = (List<Questions>) httpsession.getAttribute("allquestions");
		ModelAndView ModelAndView = new ModelAndView();
		ModelAndView.setViewName("question");
		if (previousQno>=0) {
			Questions question = list.get(previousQno);

			ModelAndView.addObject("question", question);
			httpsession.setAttribute("qno", previousQno);
			
			HashMap<Integer, Answer> hashmap =(HashMap)httpsession.getAttribute("submittedDetails");
			Answer answer=hashmap.get(question.qno);
			
			String previousAnswer="";
			if(answer!=null) {
				previousAnswer=answer.getSubmittedAnswer();
			}
					
			ModelAndView.addObject("previousAnswer", previousAnswer);
			
			
		} else {
			ModelAndView.addObject("question", list.get(0));
			ModelAndView.addObject("message", "Click on next button ");
		}
		return ModelAndView;

	}

	@RequestMapping("saveResponse")
	public void saveResponse(Answer answer, HttpServletRequest request) {
		System.out.println(answer);
		HttpSession httpsession = request.getSession();
		List<Questions> list = (List<Questions>) httpsession.getAttribute("allquestions");
		for (Questions questions : list) {

			if (questions.qno == answer.qno) {
				String ans = questions.answer;
				answer.setOriginalAnswer(ans);
				break;
			}
		}
		System.out.println(answer);
		HashMap<Integer, Answer> map = (HashMap) httpsession.getAttribute("submittedDetails");
		map.put(answer.qno, answer);
		System.out.println(map);

	}
}
