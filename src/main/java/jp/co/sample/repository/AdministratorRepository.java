package jp.co.sample.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Administrator;

@Repository
public class AdministratorRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<Administrator> ADMINISTRATOR_ROW_MAPPER = (rs, i) -> {
		Administrator administrator = new Administrator();
		administrator.setId(rs.getInt("id"));
		administrator.setName(rs.getString("name"));
		administrator.setMailAddress(rs.getString("mail_address"));
		administrator.setPassword((rs.getString("password")));
		return administrator;
	};

	/**
	 * 管理者情報を挿入する
	 * 
	 * @param administrator 管理者情報
	 * @return void
	 */
	public void insert(Administrator administrator) {
		// sql文の作成
		String sql = "INSERT INTO administrators(name, mail_address, password) VALUES(:name, :mailAddress, :password)";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", administrator.getName())
				.addValue("maiAddress", administrator.getMailAddress())
				.addValue("password", administrator.getPassword());
		template.update(sql, param);
	}

	/**
	 * メールアドレスとパスワードから管理者情報を取得する
	 * 
	 * @param mailAddress
	 * @param password
	 * @return
	 */
	public Administrator findByMailAddressAndPassword(String mailAddress, String password) {
		String sql = "SELECT id, name, mail_address, password FROM administrators WHERE mail_address = :mailAddress AND password = :password;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("maiAddress", mailAddress).addValue("password",
				password);
		Administrator administrator = template.queryForObject(sql, param, ADMINISTRATOR_ROW_MAPPER);
		return administrator;
	}

}
