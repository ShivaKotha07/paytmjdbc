package com.paytm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import com.capgemini.exception.TechnicalException;

import com.capgemini.utils.Databaseutils;
import com.paytm.model.Account;

public class Customerdaoimpl implements Customerdao {
	private Databaseutils dbutil = null;

	public Customerdaoimpl() {
		dbutil = Databaseutils.getInstance();
	}
	public float getAmount(Account a) {
		try {
			Connection connection = dbutil.openDatabaseConnection();
			String query = "select * from account where phno='" + a.getPhno() + "'";
			Statement s = connection.createStatement();
			ResultSet re = s.executeQuery(query);
			re.next();
			Account a1 = new Account(re.getString("name"), re.getString("phno"), re.getString("pwd"),
					re.getString("email"), re.getFloat("bal"));
			dbutil.closeDatabaseConnection();
			return a1.getBal();
		} catch (SQLException | ClassNotFoundException e) {
			TechnicalException te = new TechnicalException(e, "there is some technical problem");
			throw te;
		}
	}

	@Override
	public int createCustomer(Account c) {
		try {
			Connection connection = dbutil.openDatabaseConnection();
			String query = "insert into account(name,phno,pwd,email,bal) values(?,?,?,?,?)";
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, c.getName());
			pstmt.setString(2, c.getPhno());

			pstmt.setString(3, c.getPwd());
			pstmt.setString(4, c.getEmail());
			pstmt.setDouble(5, c.getBal());

			int result = pstmt.executeUpdate();

			dbutil.closeDatabaseConnection();

			if (result >= 1)
				return result;
		} catch (SQLException | ClassNotFoundException e) {
			TechnicalException te = new TechnicalException(e, "there is some technical problem");
			throw te;
		}
		return 0;
	}

	@Override
	public Account createLogin(String name, String pwd) {

		try {
			Connection connection = dbutil.openDatabaseConnection();
			String query = "select * from account where name='" + name + "'";
			Statement s = connection.createStatement();
			ResultSet re = s.executeQuery(query);
			re.next();
			Account a = new Account(re.getString("name"), re.getString("phno"), re.getString("pwd"),
					re.getString("email"), re.getFloat("bal"));
			dbutil.closeDatabaseConnection();
			return a;
		} catch (SQLException | ClassNotFoundException e) {
			TechnicalException te = new TechnicalException(e, "there is some technical problem");
			throw te;
		}

	}

	@Override
	public float getbalance(Account a) {
		return getAmount(a);
	}

	@Override
	public float creditedbalance(Account a, float amt) {
		try {
			Connection connection = dbutil.openDatabaseConnection();
			float updatebalance = getAmount(a) + amt;
			String query = "update account set bal=" + updatebalance + "where phno='"+a.getPhno()+"'";
			Statement s = connection.createStatement();
			int result = s.executeUpdate(query);
			if (result == 1) {
				String typeoftransaction = "Credit";
				String transquery = "insert into transaction values ('" + a.getPhno() + "','" + amt + "','"+ typeoftransaction + "')";
				s = connection.createStatement();
				s.executeUpdate(transquery);
				dbutil.closeDatabaseConnection();
				return getAmount(a);
			}
		} catch (SQLException | ClassNotFoundException e) {
			TechnicalException te = new TechnicalException(e, "there is some technical problem");
			throw te;
		}

		return 0;

	}

	@Override
	public float removedbalance(Account a, float wamt) {
		if (wamt <= getAmount(a)) {
			try {
				Connection connection = dbutil.openDatabaseConnection();
				float updatebalance = getAmount(a) - wamt;
				String query = "update account set bal=" + updatebalance + "where phno='"+a.getPhno()+"'";
				Statement s = connection.createStatement();
				int result = s.executeUpdate(query);
				if (result == 1) {
					String typeoftransaction = "debit";
					String transquery = "insert into transaction values ('" + a.getPhno() + "','" + wamt + "','"+ typeoftransaction + "')";
					s = connection.createStatement();
					s.executeUpdate(transquery);
					dbutil.closeDatabaseConnection();
					return getAmount(a);
				}
			} catch (SQLException | ClassNotFoundException e) {
				TechnicalException te = new TechnicalException(e, "there is some technical problem");
				throw te;
			}
		} else {
			System.out.println("insufficent funds");
		}

		return 0;
	}

	@Override
	public float transferedbalance(Account a, float tamt, String phno) {

		if (tamt <= a.getBal()) {

			try {

				Connection connection = dbutil.openDatabaseConnection();

				float updatebalance = getAmount(a) - tamt;

				String query = "update account set bal=" + updatebalance + "where phno='"+a.getPhno()+"'";

				Statement s = connection.createStatement();

				int result = s.executeUpdate(query);

				if (result == 1) {

					String typeoftransaction = "Transfer";

					String transquery = "insert into transaction values ('" + a.getPhno() + "','" + tamt + "','"+ typeoftransaction + "')";

					s = connection.createStatement();

					s.executeUpdate(transquery);

					String query2 = "select * from account where phno='" + phno + "'";

					s = connection.createStatement();

					ResultSet re = s.executeQuery(query2);

					re.next();

					Account a2 = new Account(re.getString("name"), re.getString("phno"), re.getString("pwd"),
							re.getString("email"), re.getFloat("bal"));

					updatebalance = getAmount(a2) + tamt;

					String query3 ="update account set bal=" + updatebalance + "where phno='"+a2.getPhno()+"'";

					s = connection.createStatement();

					s.executeUpdate(query3);

					String transquery2 = "insert into transaction values ('" + a2.getPhno() + "','" + tamt + "','"+ typeoftransaction + "')";
					s = connection.createStatement();

					s.executeUpdate(transquery2);
					dbutil.closeDatabaseConnection();

					return getAmount(a);
				}

			} catch (SQLException | ClassNotFoundException e) {

				TechnicalException te = new TechnicalException(e, "there is some technical problem");

				throw te;

			}

		}

		else {

			System.out.println("insufficent funds");

		}

		return 0;
	}

	public void getTransactions(Account a){
		try {

		Connection connection=dbutil.openDatabaseConnection();

			String query = "select * from transaction where phno='"+a.getPhno()+"'";

			Statement s = connection.createStatement();

			ResultSet re =s.executeQuery(query);

			while(re.next()){
             
				System.out.println(re.getString("typeoftransaction")+" "+re.getDouble("amount"));
			}
		}catch (SQLException | ClassNotFoundException e) {

			TechnicalException te = new TechnicalException(e, "there is some technical problem");

			throw te;

		}
	}

	

}