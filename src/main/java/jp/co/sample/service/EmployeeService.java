package jp.co.sample.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sample.domain.Employee;
import jp.co.sample.repository.EmployeeRepository;

/**
 * @author moritasoshi 管理者情報を操作するサービス
 */
@Service
@Transactional
public class EmployeeService {

	@Autowired
	private EmployeeRepository repository;

	public List<Employee> showList() {
		return repository.findAll();
	}
	
	public Employee showDetail(Integer id) {
		return repository.load(id);
	}

	public void updateDependentsCount(Employee employee) {
		repository.update(employee);
	}
}
