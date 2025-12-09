package com.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import net.bytebuddy.implementation.attribute.AnnotationAppender.Target.OnMethod;

@SpringBootApplication
public class SpringbootHibernateApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootHibernateApplication.class, args);
		
		Configuration cfg=new Configuration();
		 cfg.configure("com/example/hibernate.cfg.xml");
		 
		 
		 SessionFactory sessionFactory=cfg.buildSessionFactory();
		 Session session=sessionFactory.openSession();
		 Transaction transaction=session.beginTransaction();
		 
		 
		 One obj=new One();
		 obj.setName("tushar");
		 obj.setPass("12345");
		 
		 session.save(obj);
		 transaction.commit();
	}

}
