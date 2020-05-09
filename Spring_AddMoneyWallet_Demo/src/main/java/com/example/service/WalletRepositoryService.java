package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.WalletRepositoryDao;
import com.example.model.WalletUser;

@Service
public class WalletRepositoryService {

	@Autowired
	WalletRepositoryDao dao;
	
	public void addUser(WalletUser user)
	{
		dao.save(user);
	}
	
	public void searchUser(WalletUser user) {
		Optional<WalletUser> find = dao.findById(user.getUserId());
		if (find.isPresent())
			System.out.println("Department Found " + user);
		else
			System.out.println("Department not Found");
	}
	
	public void showAllUser() {
	
		dao.findAll().forEach(System.out::println);
		
	}
	
}
