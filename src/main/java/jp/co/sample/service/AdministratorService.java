package jp.co.sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sample.domain.Administrator;
import jp.co.sample.repository.AdministratorRepository;

/**
 * @author moritasoshi 管理者情報を操作するサービス
 */
@Service
@Transactional
public class AdministratorService {

	@Autowired
	private AdministratorRepository repository;

	public void insert(Administrator administrator) throws DuplicateKeyException{
		repository.insert(administrator);
	}
	
	public Administrator login(String mailAddress, String password) {
		return repository.findByMailAddressAndPassword(mailAddress, password);
	}
}
