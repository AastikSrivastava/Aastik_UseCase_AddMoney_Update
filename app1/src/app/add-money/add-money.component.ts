import { Component, OnInit } from '@angular/core';
import { CardDetails } from './add-money';
import { AddMoneyService } from '../add-money.service';
import { Router } from '@angular/router';
import { error } from '@angular/compiler/src/util';
import { flatten } from '@angular/compiler';

@Component({
  selector: 'app-add-money',
  templateUrl: './add-money.component.html',
  styleUrls: ['./add-money.component.css']
})
export class AddMoneyComponent implements OnInit {

  //session user_id
  userId = 1;


  cardNo: number;
  amount: number;

  //CardNo validation
  isEmpty = true;
  isValidCard = true;
  cardStatus = false;
  isCardCheck = true;


  //........//
//month year check
isValidMonth=true;
isValidYear=true;



  //Amount Validation
  isAmountEmpty = true;
  isValidAmount = true;
  isAmountProceed = false;


  //Add Money Check
  isAddDone = true;
  isNotAdd = true

  cardsearch: CardDetails;



  carddetails_1 = true;

  support = false;


  amountStatus = false;

  ngOnInit(): void {
    this.cardsearch = new CardDetails();

  }  
  constructor(private service: AddMoneyService, private router: Router) { }

  checkCard() {

    this.service.cardSearch(this.cardsearch.cardNo).subscribe(
      data => {

if(data!=null)
{

       // this.cardsearch = data;

        console.log("Card Details ::-- ");

        console.log(data.cardNo);
        console.log(data.amount);
        console.log(data.expiryMonth);
        console.log(data.expiryYear);
        console.log("if else condi.")


//........//
        if(data.expiryMonth==this.cardsearch.expiryMonth && data.expiryYear==this.cardsearch.expiryYear)
        {

this.cardStatus=

          this.isCardCheck = true;

          this.amountStatus = true;
  
          this.isAddDone = true;
          
        this.isNotAdd = true;

        console.log("month and year checking done ")

        }

        //........//
        if((data.expiryMonth!=this.cardsearch.expiryMonth) || (data.expiryYear!=this.cardsearch.expiryYear))
        {

          this.cardStatus = false;

          this.isCardCheck = false;


          this.amountStatus = false;


          this.isAddDone = true;


          this.isNotAdd = true;

          console.log("month and year checking not getting done ")

        } 


        console.log(data);

      //   this.isCardCheck = true;

      //   this.amountStatus = true;

      //   this.isAddDone = true;
      // this.isNotAdd = true;

        console.log("data is comming")

}

if(data==null)
{

  console.log(data);

  this.cardStatus = false;

  this.isCardCheck = false;

  this.amountStatus = false;

  this.isAddDone = true;
      this.isNotAdd = true;

  console.log(" data null is comming ");

}
  

        //  alertService.success('Card found!');

      },
      error => {
        
        this.cardStatus = false;

        this.isCardCheck = false;

        this.amountStatus = false;

        this.isAddDone = true;
      this.isNotAdd = true;

        console.log(error);

        console.log(" error is comming ");
      }
    );
  }

  addMoneyToWallet() {

    this.service.addMoneyToWallet(this.userId,this.cardsearch.amount,this.cardsearch.cardNo).subscribe(

    data => {

      console.log(data);

  
    if(data!=null){


    
      this.isAddDone = false;
      this.isNotAdd = true;

      console.log(" balance is going on ")

      console.log(data);

      }

      if(data==null)
      {
        this.isAddDone = true;
        this.isNotAdd = false;
       
        console.log(" balance is not going on ")
        
        console.log(data);
        
      }

    },

    error => {

      this.isAddDone = true;
      this.isNotAdd = false;


      console.log(" balance is not going on with error coming ")

      console.log(error);

    }

    );

  }

  onSubmit() {

    this.checkCard();
  }

  onAdd() {
    
    this.addMoneyToWallet();
  }

  public onChangeCard(event: any): void {

    console.log(event);

    if (event == null) {
      this.isEmpty = false;
      this.isValidCard = true;
      this.cardStatus = false;
      this.amountStatus=false;
      //........//
      // this.isCardCheck=true;
    }
    else if (event <= 0) {
      this.isEmpty = true;
      this.isValidCard = false;
      this.cardStatus = false;
      this.amountStatus=false;
      //........//
      // this.isCardCheck=true;
    }
    else {
      this.isEmpty = true;
      this.isValidCard = true;
      //........//
     // this.cardStatus = true;  
      //this.isCardCheck=true;

    }


  }


  public onChangeAmount(event: any): void {

    console.log(event);

    if (event == null) {

      this.isAmountEmpty = false;
      this.isValidAmount = true;
      this.isAmountProceed = false;

    }
    else if (event <= 0) {
      this.isAmountEmpty = true;
      this.isValidAmount = false;
      this.isAmountProceed = false;

    }
    else {

      this.isAmountEmpty = true;
      this.isValidAmount = true;
      this.isAmountProceed = true;

    }

    
  }


  //........//
  public onChangeMonth(event: any): void
  {
    console.log(event);


    if(event==null || event.length==0)
    {
      this.cardStatus=false; 
    }

    else if(event<=0)
    {
       this.isValidMonth=false;
       this.cardStatus=false;
       this.amountStatus=false;
      //  this.isCardCheck=true;
    
    }
    
    
    else
    {
      this.isValidMonth=true;
       this.cardStatus=true;
       this.amountStatus=false;
      // this.isCardCheck=true;
    }
    


  }

  //........//
  public onChangeYear(event: any):void

  {

    console.log(event);


    if(event==null || event.length==0)
    {
      this.cardStatus=false;
    }

    else if(event<=0)
    {
      this.isValidYear=false;
      this.cardStatus=false;
      // this.isCardCheck=true;
      this.amountStatus=false;
    }
    
    else
    {
      this.isValidYear=true;
     this.cardStatus=true;
     this.amountStatus=false;
   //  this.isCardCheck=true;
    
    }
    



  }



}
