package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        String sql = "CREATE TABLE IF NOT exists users (" +
                "id BIGINT NOT NULL AUTO_INCREMENT, " +
                "name VARCHAR(50), " +
                "lastName VARCHAR(50), " +
                "age TINYINT, " +
                "PRIMARY KEY (id))";

        session.createSQLQuery(sql).executeUpdate();

        transaction.commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        String sql = "DROP TABLE IF EXISTS users";

        session.createSQLQuery(sql).executeUpdate();

        transaction.commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        User user = new User(name, lastName, age);
        session.save(user);

        transaction.commit();
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        User user = new User();

        session.delete(user);
        transaction.commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        List<User> user = null;
        try {
            user = session.createQuery("FROM User", User.class).getResultList();
            transaction.commit();
        } catch (NullPointerException e) {
            transaction.rollback();
        } finally {
            session.close();
        }
        return user;
    }

    @Override
    public void cleanUsersTable() {
        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.createQuery("DELETE FROM User").executeUpdate();
        transaction.commit();
        session.close();
    }
}
