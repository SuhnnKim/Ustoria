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

        session.close();
        return users == null || users.size() <= 0 ? null : (UsersEntity) users.get(0);

    }

    @Override
    public void delete(int id) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("delete from UsersEntity where id = :id");
        query.setParameter("id",id);
        query.executeUpdate();
        tx.commit();
        session.close();
    }

    @Override
    public void Update(UsersEntity user) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.merge(user);
        tx.commit();
        session.close();
    }

    @Override
    public UsersEntity findBy(int id) {
        Session session = this.sessionFactory.openSession();
        UsersEntity user = null;
        Query query = session.createQuery("select u from UsersEntity as u where id = :id");
        query.setParameter("id",id);
        user = (UsersEntity) query.list().get(0);
        return user;

    }
}
