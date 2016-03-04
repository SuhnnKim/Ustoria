package service;

import dao.userDAO;
import entity.UsersEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by liuwei on 2016-02-24.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    private userDAO userDAO;
    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("hibernate-config.xml");


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDetails userDetails = null;

        //Find the user by username
        userDAO userDAO = context.getBean(userDAO.class);


        //User is subclass of
        //userDetails = new User();
        System.out.print(userDetails.toString());

        return userDetails;
    }

    @SuppressWarnings("deprecation")
    public Collection<GrantedAuthority> getAuthorities(Integer access) {

        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>(2);
        // all users have role user
        authList.add(new GrantedAuthorityImpl("ROLE_USER"));
        // access is 1 then the role is admin
        if (access.compareTo(1) == 0) {
            authList.add(new GrantedAuthorityImpl("ROLE_ADMIN"));
        }
        //返回权限列表，像管理员账号，zhangsan。返回的authList的值为 :[ROLE_USER, ROLE_ADMIN]，而普通用户返回的值为[ROLE_USER]
        return authList;
    }
}
