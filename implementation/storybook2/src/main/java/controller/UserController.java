package controller;

import com.google.gson.Gson;
import dao.userDAO;
import dao.userDAOImpl;
import entity.UsersEntity;
import model.Attribute;
import model.Character;
import model.Story;
import org.hibernate.bytecode.buildtime.spi.Logger;
import org.hibernate.mapping.Array;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.context.annotation.Role;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.portlet.ModelAndView;
import sun.security.provider.MD5;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;


@Controller
public class UserController {

    ArrayList<String> sidebarList = new ArrayList<String>();
    ArrayList<String> thumbnailList = new ArrayList<String>();
    int listNum = 0;

    @RequestMapping("login")
    public String Login(HttpServletRequest req, Model model){


        return "home";

    }

    @RequestMapping("/signup.form")
    public String SignUp(HttpServletRequest req, Model model){


        String fname = req.getParameter("fname");

        String lname = req.getParameter("lname");

        String email = req.getParameter("email-signup");

        String password = req.getParameter("password-signup");

        model.addAttribute("email",email);

        return "home";

    }



    @RequestMapping("/playground.form")
    public String Playground(HttpServletRequest req, Model model){
        String email = (String)req.getSession().getAttribute("email");
        model.addAttribute("email",email);
        return "playground";
    }

    @RequestMapping("home")
    public String Home(HttpServletRequest req, Model model){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        String email = (String)req.getSession().getAttribute("email");
        model.addAttribute("email",name);
        req.getSession().setAttribute("email",name);
        return "home";
    }








}
