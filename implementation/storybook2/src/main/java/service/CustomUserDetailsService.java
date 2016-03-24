package service;

import dao.userDAO;
import entity.UsersEntity;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by liuwei on 2016-02-24.
 */
@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    private userDAO userDAO;
    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("hibernate-config.xml");


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDetails userDetails = null;

        //Find the user by username
        userDAO = context.getBean(userDAO.class);
        UsersEntity user = userDAO.getUserByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Cannot find the Email: " + username);
        }


        //User class is subclass of UserDetails
        //not null username, not null password, enable,
        //accountNonExpired, credentialsNonExpired, accountNonLocked, authorities
        //This is the authority user information, the username and password should be match
        userDetails = new User(user.getEmail(),user.getPassword(),true,true,true,true,getAuthorities(1));

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
        //return authority list
        //admin return ROLE_USER and ROLE_ADMIN
        //user return ROLE_USER
        return authList;
    }


}
