package jp.co.sample.controller;

import java.util.Objects;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Administrator;
import jp.co.sample.form.InsertAdministratorForm;
import jp.co.sample.form.LoginForm;
import jp.co.sample.service.AdministratorService;

@Controller
@RequestMapping("/")
public class AdministratorController {

	@Autowired
	private AdministratorService administratorService;
	@Autowired
	private HttpSession session;

	/**
	 * フォームをインスタンス化し、リクエストスコープに格納
	 * 
	 * @return InsertAdministratorForm
	 */
	@ModelAttribute
	public InsertAdministratorForm setUpInsertAdministratorForm() {
		return new InsertAdministratorForm();
	}
	@ModelAttribute
	public LoginForm setUpLoginForm() {
		return new LoginForm();
	}

	/**
	 * 管理者登録画面へフォワードする処理
	 */
	@RequestMapping("/toInsert")
	public String toInsert() {
		return "administrator/insert";
	}

	/**
	 * ログイン画面へフォワードする処理
	 */
	@RequestMapping("/")
	public String toLogin() {
		return "administrator/login";
	}

	/**
	 * 管理者を登録する
	 * 
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

	/**
	 * ログインを行う
	 * 
	 * @param loginForm
	 * @param model
	 * @return
	 */
	@RequestMapping("/login")
	public String login(LoginForm loginForm, Model model) {
		String mailAddress = loginForm.getMailAddress();
		String password = loginForm.getPassword();
		Administrator administrator = administratorService.login(mailAddress, password);
		String errorMessage = "「メールアドレスまたはパスワードが不正です。」";
		if (Objects.isNull(administrator)) {
			model.addAttribute("errorMessage", errorMessage);
			return toLogin();
		} else {
			session.setAttribute("administratorName", administrator.getName());
			return "forward:/employee/showList";
		}
	}
	
	/**
	 * ログアウトを行う
	 * 
	 * @return ログイン画面にフォワード
	 */
	@RequestMapping("/logout")
	public String logput() {
		session.invalidate();
		return "forward:/";
	}
}
