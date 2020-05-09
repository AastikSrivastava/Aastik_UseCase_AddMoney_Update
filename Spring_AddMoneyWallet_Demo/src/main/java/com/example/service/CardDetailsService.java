package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.CardDetailsDao;
import com.example.model.CardDetails;
import com.example.model.WalletUser;

@Service
public class CardDetailsService {

	@Autowired
	CardDetailsDao dao;
	
	CardDetails card;
	
	public void addCard(CardDetails card)
	{
		dao.save(card);
	}
	
	public CardDetails searchCard(int card_no) {
		
		Optional<CardDetails> find = dao.findById(card_no);
		System.out.println("finding card --- ");
		
		if (find.isPresent())
		{
		System.out.println("CardNo is found ");
		
		 card = find.get();
		
			return card;
		}
		else
		{
			System.out.println("1. CardNo is not found in database ");
			return null;
		}
		
		
	}
	
	public void showAllCard() 
	{
		dao.findAll().forEach(System.out::println);
	}
	
}
