package com.example.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.model.WalletAccount;

@Repository
public interface AccountDao extends JpaRepository<WalletAccount, Integer> {

	
	@Query(value="select account_balance from Wallet_Account where user_id = :user_id  ", nativeQuery=true)
	double getAccountBalance(@Param("user_id") int user_id);
	
	
	@Query(value="update wallet_account set account_balance = :account_balance where user_id = :user_id  ",nativeQuery=true)
	@Modifying
	@Transactional
	int updateBalance(@Param("user_id") int user_id, @Param("account_balance") double account_balance);

}
