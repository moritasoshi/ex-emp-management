package jp.co.sample.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
		UpdateEmployeeForm updateEmployeeForm = new UpdateEmployeeForm();
		BeanUtils.copyProperties(employee, updateEmployeeForm);
		model.addAttribute("updateEmployeeForm", updateEmployeeForm);
		return "employee/detail";
	}

	@RequestMapping("/submit")
	public String submit(@Validated UpdateEmployeeForm updateEmployeeForm, BindingResult result, Integer id,
			Model model) {
		if (result.hasErrors()) {
			System.out.println(result);
			model.addAttribute("updateEmployeeForm", updateEmployeeForm);
			return "employee/detail";
		}
		Employee employee = new Employee();
		employee.setId(id);
		employee.setDependentsCount(updateEmployeeForm.getDependentsCount());
		employeeService.updateDependentsCount(employee);
		return showList();
	}
}
