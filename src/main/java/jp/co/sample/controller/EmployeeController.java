package jp.co.sample.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Employee;
import jp.co.sample.form.UpdateEmployeeForm;
import jp.co.sample.service.EmployeeService;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private HttpSession session;
	

	@RequestMapping("/showList")
	public String showList() {
		List<Employee> employeeList = employeeService.showList();
		session.setAttribute("employeeList", employeeList);
		return "employee/list";
	}
	
	@RequestMapping("/showDetail")
	public String showDetail(Integer id, Model model) {
		Employee employee = employeeService.showDetail(id);
		model.addAttribute("employee", employee);
		UpdateEmployeeForm updateEmployeeForm = new UpdateEmployeeForm();
		model.addAttribute("updateEmployeeForm", updateEmployeeForm);
		return "employee/detail";
	}
	
	@RequestMapping("/submit")
	public String submit(UpdateEmployeeForm updateEmployeeForm, Integer id) {
		Employee employee = new Employee();
		employee.setId(id);
		employee.setDependentsCount(updateEmployeeForm.getDependentsCount());
		employeeService.updateDependentsCount(employee);
		return showList();
	}
}
