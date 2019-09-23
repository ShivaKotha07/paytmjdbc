package com.paytm.ui;
import java.util.Scanner;
import com.paytm.model.Account;
import com.paytm.services.Customerservice;
import com.paytm.services.Customerserviceimpl;
import com.capgemini.exception.TechnicalException;
import com.paytm.dao.Customerdaoimpl;

public class Main {

	public static void main(String[] args) {
		try {

			Scanner sc = new Scanner(System.in);

			boolean flag = true;

			String name = null;

			String phno = null;

			String pwd = null;

			String email = null;

			while (true) {

				System.out.println("****welcome to paytm management system****\n");

				System.out.println("1.register\n2.login\n3.exit ");

				System.out.println("enter your choice");

				int ch = sc.nextInt();

				Customerservice service = new Customerserviceimpl();

				switch (ch) {

				case 1:
					boolean valid = false;

					do {
						valid = false;
						System.out.println("enter your username:");

						name = sc.next();

						if (!service.validateName(name))

						{

							System.out.println(
									"*******enter valid username********\n1.it should contain only a-z,A-Z,0-9,_\n2. length should be 3 to 15 characters");

							valid = true;

							continue;

						}

						System.out.println("enter your phone number:");

						phno = sc.next();

						if (!service.validatePhone(phno))

						{

							System.out.println(
									"*******enter valid phone number*******\n1.phone number must contain only 0-9\n2.length should be 10 ");

							valid = true;

							continue;

						}

						System.out.println("enter your password:");

						pwd = sc.next();

						if (!service.validatepwd(pwd))

						{

							System.out.println(
									"*******enter valid password*******\n1.it must contain 6 to 20 characters\n2.contain special characters like @,$\n3.contain numbers ");

							valid = true;

							continue;

						}

						System.out.println("enter your email:");

						email = sc.next();

						if (!service.validateEmail(email))

						{

							System.out.println("*******enter valid email*******\n1.it must contain @gmail.com ");
							valid = true;

						}

					} while (valid);

					Account c = new Account(name, phno, pwd, email, 0);

					int result = service.addCustomer(c);

					if (result >= 1) {

						System.out.println("sucessfully registered");

					} else {

						System.out.println("something went wrong");

					}

					break;

				case 2:

					System.out.println("enter your username");

					String name1 = sc.next();

					System.out.println("enter your password:");

					String pwd1 = sc.next();

					Account a = service.getAccountLogin(name1, pwd1);

					if (a != null) {

						flag = true;

						System.out.println("sucessfully logged in");

						while (flag) {

							System.out.println(

									"1.showbalance\n2.deposit\n3.withdraw\n4.fund transfer\n5.print transcations\n6.signout");

							System.out.println("enter your choice");

							int ch1 = sc.nextInt();

							switch (ch1) {

							case 1:

								float bal = service.showbalance(a);

								System.out.println("Account balance is:" + bal);

								break;

							case 2:

								System.out.println("enter deposit amount:");

								float amt = sc.nextFloat();

								float amt1 = service.depositbalance(a, amt);

								System.out.println("after deposit balance is:" + amt1);

								break;

							case 3:

								System.out.println("enter withdraw amount");

								float amt2 = sc.nextFloat();
								if (a.getBal() >= amt2) {
									float w = service.withdrawamount(a, amt2);

									System.out.println("after withdraw balance is:" + w);
								} else {
									System.out.println("insuffient funds, please add money to account");
								}
								break;

							case 4:

								System.out.println("enter transfer amount");
								float transamt = sc.nextFloat();
								System.out.println("enter the phone number  to whom you want to transfer the amount");
								String phno1 = sc.next();
								float updatedamt = service.transferfund(a, transamt, phno1);
								System.out.println("updated balance" + updatedamt);
								break;
							case 5:
								service.printTransactions(a);
								break;
							case 6:
								flag = false;
								break;

							}

						}

					} else {

						System.out.println("something went wrong");

					}

					break;

				case 3:
					sc.close();
					System.exit(0);

				default:

					System.out.println("wrong choice");

				}
			}
		} catch (TechnicalException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();

		}
	}

}