package com.msx.tagvalue.ui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class Dao {
	public Dao() {
		
		
		
	}
	
	public void save(Vector<?> vector,int number){
		
		Connection con = SQLConnection.getConnection();
		PreparedStatement statement = null;
		try {
			statement = con.prepareStatement("insert into doublelist (Number,First,Second) values (?,?,?)");
			for(Object object : vector){
				Vector<?> v = (Vector<?>)object;
				Object first = v.elementAt(0);
				Object second = v.elementAt(1);
				if (first == null || second == null
						|| first.toString().equals("") || second.toString().equals("")) {
					continue;
				}
				statement.setString(1, String.valueOf(number));
				statement.setString(2, first.toString());
				statement.setString(3, second.toString());
				statement.execute();
			}
			con.close();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
public void update(Vector<?> vector,int number){
		
		Connection con = SQLConnection.getConnection();
		PreparedStatement statement = null;
		try {
			statement = con.prepareStatement("update doublelist set Second = ? where First = ? and Number = " + number);
			for(Object object : vector){
				Vector<?> v = (Vector<?>)object;
				Object first = v.elementAt(0);
				Object second = v.elementAt(1);
				if (first == null || second == null
						|| first.toString().equals("") || second.toString().equals("")) {
					continue;
				}
				ResultSet set = statement.executeQuery("select first from doublelist where first = '" + first + "' and number = " + number);
				if (set.next()){
					statement.setString(1, second.toString());
					statement.setString(2, first.toString());
					statement.execute();
				} else {
					statement.execute("insert into doublelist (Number,First,Second) values ("+number+",'"+first+"','"+second+"')");
					statement.execute();
				}
			}
			con.close();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	public int lastNumber(){
		Connection con = SQLConnection.getConnection("information_schema");
		try {
			PreparedStatement statement = con.prepareStatement("select table_rows from tables where table_name = 'doublelist'");
			ResultSet set = statement.executeQuery();
			set.next();
			int n = set.getInt(1);
			statement.execute("use lifeache");
			set = statement.executeQuery("select number from doublelist limit 1 offset " + (n - 1));
			set.next();
			n = set.getInt(1);
			con.close();
			return n;
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return 0;
	}
	
	public boolean checkNumber(int n){
		Connection con = SQLConnection.getConnection();
		try {
			PreparedStatement statement = con.prepareStatement("select number from doublelist where number = " + n);
			ResultSet set = statement.executeQuery();
			if (set.next()){
				return true;
			}
		con.close();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return false;
	}
	
	public Vector<Vector<String>> getList(int n){
		Connection con = SQLConnection.getConnection();
		try {
			PreparedStatement statement = con.prepareStatement("select first,second from doublelist where number = " + n);
			ResultSet set = statement.executeQuery();
			Vector<Vector<String>> list = new Vector<Vector<String>>();
			while(set.next()){
				Vector<String> v = new Vector<String>();
				v.add(set.getString(1));
				v.add(set.getString(2));
				list.add(v);
			}
			con.close();
			return list;
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return null;
	}
}
