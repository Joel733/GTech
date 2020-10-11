package com.gtech.adapter;

import java.util.ArrayList;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;

import com.gtech.entity.User;
public class GTechAdapter extends BaseAdapter{
	
	public List<User> getUserList(){
		Session session = sfGtech.openSession();
		List<User> list = null;
		try {
			list = session.createQuery("From User").list();
			return list;
		} catch (Exception e) {
			return new ArrayList<User>();
		}finally {
			session.close();
		}
	}
	public List<User> getUserByUserNameAndPassword(String username, String password){
		Session session = sfGtech.openSession();
		List<User> list = null;
		try {
			list = session.createQuery("from User where userName='"+username+"' and password = '"+password+"'").list();
			return list;
		} catch (Exception e) {
			return new ArrayList<User>();
		}finally {
			session.close();
		}
	}
	public void updateUserLoginToken(String username, String loginToken) {
		Session session = sfGtech.openSession();
		try {
			session.beginTransaction();
			Query query = session.createSQLQuery("UPDATE gtech_test.user SET loginToken ='"+loginToken+"' WHERE username = '"+username+"'");
			query.executeUpdate();
			session.getTransaction().commit();
		}catch(Exception e) {
			e.getStackTrace();
			session.getTransaction().rollback();
		}finally {
			session.close();
		}
	}
	public List<User> getUserByLoginToken(String loginToken){
		Session session = sfGtech.openSession();
		List<User> list = null;
		try {
			list = session.createQuery("From User where loginToken='"+loginToken+"' ").list();
			return list;
		} catch (Exception e) {
			return new ArrayList<User>();
		}finally {
			session.close();
		}
	}
	public void updateUserProfile(String firstName, String lastName, String gender, String DoB, String loginToken) {
		Session session = sfGtech.openSession();
		try {
			session.beginTransaction();
			Query query = session.createSQLQuery("UPDATE gtech_test.user SET firstName ='"+firstName+"',lastName = '"+lastName+"', gender='"+gender+"', DoB = '"+DoB+"' WHERE loginToken = '"+loginToken+"'");
			query.executeUpdate();
			session.getTransaction().commit();
		}catch(Exception e) {
			e.getStackTrace();
			session.getTransaction().rollback();
		}finally {
			session.close();
		}
	}
}
