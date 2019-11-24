package com.edureka.hibernate.client;

import com.edureka.hibernate.model.Address;
import com.edureka.hibernate.model.Course;
import com.edureka.hibernate.model.User;
import com.edureka.hibernate.model.Vehicle;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.*;
import org.hibernate.engine.spi.TypedValue;
import org.hibernate.internal.CriteriaImpl;

import java.util.List;

public class Client {
    public static void main(String[] args) {

        //Load the configuration file and create a session factory instance
        SessionFactory sessionFactory = new Configuration()
                                            .configure("hibernate.cfg.xml")
                                            .buildSessionFactory();
        //Create an instance of session from the session factory
        Session session = sessionFactory.openSession();
        //start the transaction from the session
        Transaction transaction = session.beginTransaction();
        Address address = new Address();
        address.setCity("Bangalore");
        address.setState("karnataka");

        Vehicle honda = new Vehicle();
        honda.setMake("Honda");
        honda.setModel("CITY");
        honda.setNumber("KA-05-MT9889");

        Vehicle bmw = new Vehicle();
        bmw.setMake("BMW");
        bmw.setModel("X5");
        bmw.setNumber("KA-05-MT7889");

        Course java = new Course();
        java.setCourseName("java");
        java.setPrice(15000);

        saveUser(session, new User("Vikas", 34), address, honda, bmw, java);
       /* saveUser(session, new User("Ram", 35));
        saveUser(session, new User("Vinod", 37));
        saveUser(session, new User("John", 43));
        saveUser(session, new User("Naveen", 56));
        saveUser(session, new User("Asha", 53));
        saveUser(session, new User("Ramesh", 50));
        saveUser(session, new User("Kishore", 56));*/

        listAllUsers(session);
        //updating the user
       // updateUser(session);

        //deleting the user
        //deleteUser(session);



        //commit the transaction
        transaction.commit();
        //close the session and the transaction
        session.close();
        sessionFactory.close();
    }

    private static void listAllUsers(Session session) {
       // List users = session.createQuery("FROM User where id < 4 order by name").list();
        List<User> users = session.createCriteria(User.class)
                .setProjection( Projections.projectionList()
                .add( Projections.max("age") )
                ).list();
        System.out.println("*****************");
        System.out.println(users);
        System.out.println("*****************");
    }

    private static void deleteUser(Session session) {
        User savedUser = session.get(User.class, 1);
        session.delete(savedUser);
    }

    private static void updateUser(Session session) {
        User savedUser = session.get(User.class, 1);
        savedUser.setName("Vinay");
        savedUser.setAge(56);
        session.saveOrUpdate(savedUser);
    }

    private static void saveUser(Session session, User user, Address address, Vehicle honda, Vehicle bmw, Course java) {
        //save the user to the database/table
        user.addVehicle(honda);
        user.addVehicle(bmw);
        user.setAddress(address);
        user.addCourse(java);
        java.addUser(user);
        session.save(user);
    }
}