package jp.co.sample.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Administrator;
import jp.co.sample.form.InsertAdministratorForm;
import jp.co.sample.service.AdministratorService;

@Controller
@RequestMapping("/")
public class AdministratorController {

	@Autowired
	private AdministratorService administratorService;
	
	/**
	 * フォームをインスタンス化し、リクエストスコープに格納
	 * 
	 * @return InsertAdministratorForm
	 */
	@ModelAttribute
	public InsertAdministratorForm setUpInsertAdministratorForm() {
		return new InsertAdministratorForm();
	}
	
	/**
	 * 管理者登録画面へフォワードする処理
	 */
	@RequestMapping("/toInsert")
	public String toInsert() {
		return "administrator/insert";
	}
	
	/**
	 * 管理者を登録する
	 * @param insertAdministratorForm フォームで入力した管理者情報
	 * @return 
	 */
	@RequestMapping("/insert")
	public String insert(InsertAdministratorForm insertAdministratorForm) {
		Administrator administrator = new Administrator();
		BeanUtils.copyProperties(insertAdministratorForm, administrator);
		administratorService.insert(administrator);
		return "administrator/login";
	}
}
