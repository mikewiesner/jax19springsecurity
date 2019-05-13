package pu.employee.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	@RequestMapping("/")
	public String redirectToStart() {
		return "redirect:/employee";
	}
	
}
