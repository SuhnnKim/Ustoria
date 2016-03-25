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
        return "admin/detail";
    }

    @RequestMapping("admin/save")
    public String Save(HttpServletRequest req, Model model){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        req.getSession().setAttribute("email",name);
        userDAO userDAO = context.getBean(userDAO.class);

        String username = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        Md5PasswordEncoder encoder = new Md5PasswordEncoder();



        return "admin";
    }
}
