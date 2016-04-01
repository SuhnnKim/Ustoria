package controller;

import dao.userDAO;
import entity.UsersEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

/**
 * Created by liuwei on 2016-03-25.
 */
@Controller
public class AdminController {

    @Autowired
    private userDAO userDAO;
    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("hibernate-config.xml");

    @RequestMapping("admin")
    public String Admin(HttpServletRequest req, Model model){

        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        req.getSession().setAttribute("email",name);
        userDAO userDAO = context.getBean(userDAO.class);
        List users = userDAO.list();

        model.addAttribute("users",users);

        return "admin";
    }

    @RequestMapping("delete")
    public String Delete(HttpServletRequest req, Model model){

        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        req.getSession().setAttribute("email",name);
        userDAO userDAO = context.getBean(userDAO.class);

        int id = Integer.parseInt(req.getParameter("id"));
        userDAO.delete(id);

        List users = userDAO.list();

        model.addAttribute("users",users);

        return "admin";
    }

    @RequestMapping("edit")
    public String Edit(HttpServletRequest req, Model model){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        req.getSession().setAttribute("email",name);
        userDAO userDAO = context.getBean(userDAO.class);

        int id = Integer.parseInt(req.getParameter("id"));

        UsersEntity user = userDAO.findBy(id);


        model.addAttribute("user",user);
        model.addAttribute("role",user.getRole());
        return "admin/detail";
    }

    @RequestMapping("save")
    public String Save(HttpServletRequest req, Model model){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        req.getSession().setAttribute("email",name);
        userDAO userDAO = context.getBean(userDAO.class);

        String id = req.getParameter("id");

        UsersEntity user = userDAO.findBy(Integer.parseInt(id));

        String username = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String role = req.getParameter("role");
        int r = Integer.parseInt(role);


        if (password != ""){
            Md5PasswordEncoder encoder = new Md5PasswordEncoder();
            String encoderPassword = encoder.encodePassword(password,null);
            user.setPassword(encoderPassword);
        }

        user.setEmail(email);
        user.setRole(r);
        user.setUserName(username);

        userDAO.Update(user);

        List users = userDAO.list();

        model.addAttribute("users",users);
        return "admin";
    }

    @RequestMapping("add")
    public String Add(){
        return "admin/newUser";
    }

    @RequestMapping("saveNew")
    public String SaveNew(HttpServletRequest req, Model model){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        req.getSession().setAttribute("email",name);
        userDAO userDAO = context.getBean(userDAO.class);

        String username = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        Md5PasswordEncoder encoder = new Md5PasswordEncoder();

        String encoderPassword = encoder.encodePassword(password,null);

        String role = req.getParameter("role");

        int r = Integer.parseInt(role);


        UsersEntity user = new UsersEntity();
        user.setEmail(email);
        user.setPassword(encoderPassword);
        user.setRole(r);
        user.setUserName(username);
        userDAO.save(user);

        List users = userDAO.list();

        model.addAttribute("users",users);

        return "admin";
    }

}
