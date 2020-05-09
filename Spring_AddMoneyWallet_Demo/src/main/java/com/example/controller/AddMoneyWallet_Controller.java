package com.example.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dao.AccountDao;
import com.example.dao.CardDetailsDao;
import com.example.dao.UserDao;
import com.example.dao.WalletRepositoryDao;
import com.example.error.NegativeAmountException;
import com.example.error.RecordNotFoundException;
import com.example.model.CardDetails;
import com.example.model.WalletAccount;
import com.example.model.WalletUser;
import com.example.service.CardDetailsService;
import com.example.service.WalletRepositoryService;

@RestController
@RequestMapping("/wallet")
@CrossOrigin(value="http://localhost:4200")
public class AddMoneyWallet_Controller {
	
	@Autowired
	AccountDao accountDao;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	CardDetailsDao cardDetailsDao;
	
	//WalletRepositoryDao walletDao;
	
	CardDetails cardDetails;
	
	@Autowired
	CardDetailsService cardservice;
	
	@GetMapping("/cardDetails/{card_no}")
	@ExceptionHandler(RecordNotFoundException.class)
	public CardDetails findByCardNo(@PathVariable("card_no") int card_no )
	{
		try
		{
			
		if(cardservice.searchCard(card_no)!=null)
		{
			
			System.out.println("CardNo is found in database");
			return cardservice.searchCard(card_no);	
			
			
	//	return new ResponseEntity<CardDetails>(cardDetails,HttpStatus.OK);
			
		
		}
		
		else
		{
			
		System.out.println("2. CardNo is not found in database ");	
			
			throw new  RecordNotFoundException(" CardNo is not found in database");
			
		}
		
		}
		
		catch (RecordNotFoundException e) {
			
			
			System.out.println("Card record not found");
			
			//return new ResponseEntity(null,HttpStatus.NOT_FOUND);
			return null;
			
		}
		
	}
	
	
	//@CrossOrigin(origins = "http://localhost:4200")
		@PutMapping("/addMoney/{user_id}/{amount}/{card_no}")
		@ExceptionHandler(NegativeAmountException.class)
		public CardDetails addMoney(@PathVariable("user_id") int user_id , @PathVariable("amount") double amount , @PathVariable("card_no") int card_no)
		{
			
			try
			
			{
				
	                    	System.out.println("userid is taken from session storage");
	                    	System.out.println("userid : "+user_id);
					
		            
				                              if(amount>cardDetailsDao.getAccountBalanceInCard(card_no))
				                                   
				                              {
				                            	  throw new  NegativeAmountException("Balance is not added to acccount due to insufficient balance");
			                                	
				                              }
				                              
				     
			                         	else
			                         		
				                        
			                         	{
				
					//deducting amount from Card	
			                                 	double cardBalance = cardDetailsDao.getAccountBalanceInCard(card_no);
				
			                                  	cardBalance = cardBalance - amount;
				
			                                    	cardDetailsDao.updateBalanceInCard(card_no,cardBalance);
				
				
				// adding money to the wallet		
			                                  double useridbalance =  accountDao.getAccountBalance(user_id);
			  
			                                          amount = amount + useridbalance;
			  
			                                                  accountDao.updateBalance(user_id, amount);
			   
			                                    System.out.println("Balance is successfully added to account");
			                                    
			                                    
			                               //   return new ResponseEntity<String>("Balance is successfully added to account",HttpStatus.OK);
			                                 
			                                    return  cardservice.searchCard(card_no);
			  
				                          }
			                    }

			
			catch (NegativeAmountException e) {
				
				//return new ResponseEntity<String>(e.getMessage(),HttpStatus.NOT_FOUND);
				System.out.println(e.getMessage());
				
				return null;
			}
						

		}

		@RequestMapping("/showAll")
	    public List<CardDetails> show()
	    {

			cardDetailsDao.findAll().forEach(System.out::println);
		
			System.out.println("\nALL card details called ");
			
	    	return cardDetailsDao.findAll();

	    	
	    }
	

//	//@CrossOrigin(origins = "http://localhost:4200")
//	@PutMapping("/addMoney/{user_id}/{amount}/{cardno}")
//	@ExceptionHandler(NegativeAmountException.class)
//	public String addMoney(@PathVariable("user_id") int user_id , @PathVariable("amount") double amount , @PathVariable("cardno") int cardno )
//	{
//		
//		try
//		{
//					
//                    Optional<WalletUser> findById = userDao.findById(user_id);
//	
//                    if(findById.isPresent())
//		
//                    {
//                    
//                    	System.out.println("userid is present");
//				 CardDetails usercardno;
//					
//					
//             	usercardno = cardservice.searchCard(cardno);
//
//
//	                  	if(usercardno!=null)
//
//		                    {
//				                  		                    
//			 
//			                              if(amount>usercardno.getAmountAvailable())
//			                                   
//			                              {
//			                                   	return "Card don't have sufficient balance";
//				
//		                                	
//			                              }
//			                              
//			                              
//			
//		                         	else
//		                         		
//			                        
//		                         	{
//			
//				//deducting amount from Card	
//		                                 	double cardBalance = usercardno.getAmountAvailable();
//			
//		                                  	cardBalance = cardBalance - amount;
//			
//		                                    	usercardno.setAmountAvailable(cardBalance);
//			
//			
//			// adding money to the wallet		
//		                                  double useridbalance =  accountDao.getAccountBalance(user_id);
//		  
//		                                          amount = amount + useridbalance;
//		  
//		                                                  accountDao.updateBalance(user_id, amount);
//		   
//		                                    return "Balance is successfully added to account";
//		  
//			                          }
//		                    }
//		                        	
//		
//		                else
//		                	
//		                     {
//		                	
//			                      throw new RecordNotFoundException("entered cardno is not present");
//		
//		                     }
//		
//		                   }
//                    else
//                    {
//                    	throw new RecordNotFoundException("entered userid is not present");
//                    }
//                    
//			
//		       
//			
//		}
//		
//		
//		catch (RecordNotFoundException e) {
//			
//			return  "Balance is not added to account because entered userid is not present";
//		}
//		
//
//	}
		

//		   
//		
//		@RequestMapping("/showAllUser")
//		public List<WalletUser> showUser()
//		{
//			System.out.println("All Wallet users : ");
//			
//			
//			
//			walletDao.findAll().forEach(System.out::println);
//			
//			return walletDao.findAll();
//			
//			
//		}
//		
		
		
		
	
	
	
	
	
	
//	@GetMapping("/cardDetails/{cardno}")
//	
//	public CardDetails findByCardNo(@PathVariable("cardno") int cardno )
//	{
//		
//		return	cardservice.searchCard(cardno);
//				
//		
//	}
//	
//	@GetMapping("/addMoney")
//	public CardDetails addMoney(@RequestParam(value = "user_id") int user_id , @RequestParam(value = "cardobject") CardDetails cardobject )
//	{
//		
//		return cardobject;
//
//	}

	
//	@PutMapping("/addMoney")
//	public WalletUser addMoney( @Valid @RequestBody CardDetails cardobject)
//	{
//		
//      	CardDetails usercardno;
//				
//			//deducting amount from Card		
//			usercardno = cardservice.searchCard(cardobject.getCardNo());
//			
////			if(amount>usercardno.getAmountAvailable())
////			{
////				return null;
////			}
////			
////			else
////			{
//			
////			double cardBalance = usercardno.getAmountAvailable();
////			
////			cardBalance = cardBalance - amount;
////			
////			usercardno.setAmountAvailable(cardBalance);
////			
//			// adding money to the wallet		
////		  double x =  accountDao.getAccountBalance(user_id);
////		  
////		  amount = amount + x;
////		  
////		  accountDao.updateBalance(user_id, amount);
////		  
//		   
//		  return useraddmoney;
//		  
//	}	
	
	
	
//	@PostMapping("/addMoney")
//	public String addMoneyWallet(@RequestBody CardDetails card )
//	{
////
////			if(amount<=0)
////			{
////				throw new NegativeAmountException("Balance should not be zero or negative");
////			}
//	//		else
//			{
//			//	System.out.println("Enter the Card details");
//				
//				if(cardservice.searchCard(card)!=null)
//				{
//					cardDetails=cardservice.searchCard(card);
//					
////					if(amount>cardDetails.getAmountAvailable())
////					{
////						System.out.println("Credit Card does not have enough balance");
////					}
////					else
//					{
//				
//					// deducting amount from Card
//				double cardBalance = cardDetails.getAmountAvailable();
//				
//				cardBalance = cardBalance - card.getAmountAvailable();
//					
//				cardDetails.setAmountAvailable(cardBalance);
//				
//				
//				// adding money to wallet
//		//			double walletBalance = walletAccount.getAccountBalance();
//					
//				//	amount=amount + walletBalance;
//					
//			//		walletAccount.setAccountBalance(amount);
//					}
//					
//				}
//				else
//				{
//					System.out.println("CardNo is not found in database ");
//					
//					cardservice.addCard(cardDetails);
//				}
//				
//				
//				return "Balance is added to the account";
//				
//			}
//			
//	}
//	
	
}
