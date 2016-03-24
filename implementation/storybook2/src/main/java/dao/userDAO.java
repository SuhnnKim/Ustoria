package dao;

import entity.UsersEntity;

import java.util.List;

/**
 * Created by liuwei on 2016-02-16.
 */
public interface userDAO {

    public void save(UsersEntity user);

    public List<UsersEntity> list();

    public UsersEntity getUserByEmail(String email);


}
