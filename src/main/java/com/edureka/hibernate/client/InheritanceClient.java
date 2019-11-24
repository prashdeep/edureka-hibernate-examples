package com.edureka.hibernate.client;

import com.edureka.hibernate.model.ContractEmployee;
import com.edureka.hibernate.model.Employee;
import com.edureka.hibernate.model.RegularEmployee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class InheritanceClient {
    public static void main(String[] args) {


        //Load the configuration file and create a session factory instance
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();
        //Create an instance of session from the session factory
        Session session = sessionFactory.openSession();
        //start the transaction from the session
        Transaction transaction = session.beginTransaction();
        RegularEmployee regularEmployee = new RegularEmployee();
        regularEmployee.setName("Regular-Employee");
        regularEmployee.setFixedSalary(56000);

        ContractEmployee contractEmployee = new ContractEmployee();
        contractEmployee.setName("Contract-Employee");
        contractEmployee.setPayPerHour(4500);

        session.save(regularEmployee);
        session.save(contractEmployee);

        transaction.commit();
        session.close();
        sessionFactory.close();



    }
}