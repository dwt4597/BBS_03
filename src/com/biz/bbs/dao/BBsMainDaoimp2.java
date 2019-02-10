package com.biz.bbs.dao;

import java.sql.*;
import java.util.*;

import com.biz.bbs.vo.*;

public class BBsMainDaoimp2 implements BBsMainDao{
	Connection dbConn;
	
	public BBsMainDaoimp2() {
		this.dbconnection();
	}

	private void dbconnection() {
		// TODO Auto-generated method stub
		String dbDriver = "oracle.jdbc.driver.OracleDriver";
		try {
			Class.forName(dbDriver);
			
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String user = "bbsUSER";
			String password = "1234";
			
			dbConn = DriverManager.getConnection(url, user, password);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void insert(BBsMainVO vo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<BBsMainVO> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BBsMainVO findById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(BBsMainVO vo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
		
	}
}
