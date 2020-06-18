package jp.co.sample.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Employee;

@Repository
public class EmployeeRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<Employee> EMPLOYEE_ROW_MAPPER = (rs, i) -> {
		Employee employee = new Employee();
		employee.setId(rs.getInt("id"));
		employee.setName(rs.getString("name"));
		employee.setImage(rs.getString("image"));
		employee.setGender(rs.getString("gender"));
		employee.setHireDate(rs.getDate("hire_date"));
		employee.setMailAddress(rs.getString("mail_address"));
		employee.setZipCode(rs.getString("zip_code"));
		employee.setAddress(rs.getString("address"));
		employee.setTelephone(rs.getString("telephone"));
		employee.setSalary(rs.getInt("salary"));
		employee.setCharacteristics(rs.getString("characteristics"));
		employee.setDependentsCount(String.valueOf(rs.getInt("dependents_count")));
		return employee;
	};

	/**
	 * 従業員一覧情報を入社日順で取得する 従業員が存在しない場合はサイズ0件の従業員一覧を返す
	 * 
	 * @return List<Employee> employeeList 全従業員一覧
	 */
	public List<Employee> findAll() {
		// SQL文を作成
		String sql = "SELECT * FROM employees ORDER BY hire_date DESC";

		// SQLの実行
		List<Employee> employeeList = template.query(sql, EMPLOYEE_ROW_MAPPER);

		return employeeList;
	}

	/**
	 * 主キーから従業員を取得する 従業員が存在しない場合は例外発生
	 * 
	 * @param id ID
	 * @return 検索された従業員情報
	 */
	public Employee load(Integer id) {
		// SQL文を作成
		String sql = "SELECT * FROM employees WHERE id = :id";

		// ラベル(プレースホルダ名)に代入するパラメータを用意
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

		// SQLの実行
		Employee employee = template.queryForObject(sql, param, EMPLOYEE_ROW_MAPPER);

		return employee;
	}

	/**
	 * 従業員情報を変更する （扶養人数のみ変更）
	 * 
	 * @param employee
	 * @return 追加 or 更新された従業員情報
	 */
	public void update(Employee employee) {
		String sql = "UPDATE employees SET dependents_count=:dependentsCount WHERE id=:id";
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("dependentsCount", Integer.parseInt(employee.getDependentsCount())).addValue("id", employee.getId());
		template.update(sql, param);
	}

}
