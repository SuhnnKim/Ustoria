package dao;

import entity.UsersEntity;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by liuwei on 2016-02-16.
 */
public class userDAOImpl implements userDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(UsersEntity user) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(user);
        tx.commit();
        session.close();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UsersEntity> list() {
        Session session = this.sessionFactory.openSession();
        List<UsersEntity> userList = session.createQuery("from UsersEntity").list();
        session.close();
        return userList;
    }

    public UsersEntity getUserByEmail(String email) {
        Session session = this.sessionFactory.openSession();

        Query query = session.createQuery("from UsersEntity where email = :email");
        query.setParameter("email", email);
        List users = query.list();

        return users == null || users.size() <= 0 ? null : (UsersEntity) users.get(0);

    }

}
