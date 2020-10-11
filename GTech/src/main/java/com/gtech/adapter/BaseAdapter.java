package com.gtech.adapter;

import java.util.ArrayList
;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseAdapter {
	@Autowired
	protected SessionFactory sfPsikometri;
	@Autowired 
	protected SessionFactory sfGtech;
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sfPsikometri = sessionFactory;
	}
	public boolean addObject(Object object) {
		Session session = sfPsikometri.openSession();
		try {
			session.beginTransaction();
			session.save(object);
			session.getTransaction().commit();
		} catch(HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return false;
		} finally {
			session.close();
		}
		return true;
	}
}
