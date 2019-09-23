package com.paytm.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.paytm.dao.Customerdao;
import com.paytm.dao.Customerdaoimpl;
import com.paytm.model.Account;

public class Customerserviceimpl implements Customerservice {
	private Customerdao dao = new Customerdaoimpl();

	@Override
	public int addCustomer(Account c) {
		int result = dao.createCustomer(c);
		return result;
	}

	@Override
	public Account getAccountLogin(String name, String pwd) {
		 Account a = dao.createLogin(name, pwd);
		return a;
	}

	@Override
	public float showbalance(Account a) {
		float bal1=dao.getbalance(a);

		return bal1;
	}

	@Override
	public float depositbalance(Account a, float amt) {
		float depbal=dao.creditedbalance(a,amt);
		return depbal;
	}

	@Override
	public float withdrawamount(Account a, float wamt) {
		float withdraw=dao.removedbalance(a,wamt);
		return withdraw;
	}

	@Override
	public float transferfund(Account a, float tamt, String phno) {
		float transfund=dao.transferedbalance(a,tamt,phno);
		return transfund;
	}
	/*public List<Account> getall(){
		List<Account> accounts = dao.getallacc();
		return accounts;
	}*/

	@Override
	public boolean validateName(String name) {
		// TODO Auto-generated method stub
		if(name.matches("^[a-zA-Z0-9_-]{3,15}$"))
			return true;
		return false;
	}

	@Override
	public boolean validatePhone(String phno) {
		// TODO Auto-generated method stub
		if(phno.matches("(0/91)?[7-9][0-9]{9}"))
		return true;
	return false;
	}

	@Override
	public boolean validatepwd(String pwd) {
		if(pwd.matches(".*[0-9]{1,}.*") && pwd.matches(".*[@#$]{1,}.*") && pwd.length()>=6 && pwd.length()<=20)
        {
                        return true;
        }
		return false;
	}

	@Override
	public boolean validateEmail(String email) {
		if(email.matches("^[a-zA-Z0-9_+&*-]+(?:\\."+ 
                "[a-zA-Z0-9_+&*-]+)*@" + 
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
                "A-Z]{2,7}$"))
			return true;
		return false;
	}
	public  void printTransactions(Account a){
		dao.getTransactions(a);
	}
	
}
