package dao;

import entity.UsersEntity;
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

    @SuppressWarnings("unchecked")
    public UsersEntity getUser(String username){
        Session session = this.sessionFactory.openSession();
        UsersEntity user = (UsersEntity) session.byId(username);
        session.close();
        return user;
    }



}
