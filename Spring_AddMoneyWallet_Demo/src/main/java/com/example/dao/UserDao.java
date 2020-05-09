package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.WalletUser;

@Repository
public interface UserDao extends JpaRepository<WalletUser, Integer>{
	
}