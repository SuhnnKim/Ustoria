package controller;

import dao.userDAO;
import dao.userDAOImpl;
import entity.UsersEntity;
import org.hibernate.bytecode.buildtime.spi.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.ModelAndView;
import sun.security.provider.MD5;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuwei on 2016-02-22.
 */
@Controller
public class UserController {

    ArrayList<String> sidebarList = new ArrayList<String>();
    ArrayList<String> thumbnailList = new ArrayList<String>();
    int listNum = 0;

    @RequestMapping("login")
    public String Login(HttpServletRequest req, Model model){

        String email = req.getParameter("email-login");;

        String password = req.getParameter("password-login");

        String em = (String)req.getSession().getAttribute("email");
        if (em == null)
            req.getSession().setAttribute("email",email);

        model.addAttribute("email",email);

        return "home";

    }

    @RequestMapping("/signup.form")
    public String SignUp(HttpServletRequest req, Model model){


        String fname = req.getParameter("fname");;

        String lname = req.getParameter("lname");

        String email = req.getParameter("email-signup");

        String password = req.getParameter("password-signup");

        model.addAttribute("email",email);

        return "home";

    }

    @RequestMapping("/summary.form")
    public String Summary(HttpServletRequest req, Model model){
        String email = (String)req.getSession().getAttribute("email");
        model.addAttribute("email",email);
        return "summary";
    }

    @RequestMapping("character.form")
    public String Character(HttpServletRequest req, Model model){
        String email = (String)req.getSession().getAttribute("email");
        model.addAttribute("email",email);

        return "character";
    }

    @RequestMapping("/playground.form")
    public String Playground(HttpServletRequest req, Model model){
        String email = (String)req.getSession().getAttribute("email");
        model.addAttribute("email",email);
        return "playground";
    }

    @RequestMapping("/saveCharacter.form")
    public String SaveCharacter(HttpServletRequest req, Model model){
        String email = (String)req.getSession().getAttribute("email");
        model.addAttribute("email",email);

        String save = req.getParameter("save");
        if (save!=null&&save.equals("Save")){
            String name = req.getParameter("name");
            String desc = req.getParameter("desc");
            String[] Realatts = req.getParameterValues("relaAtt");
            String[] Roleatts = req.getParameterValues("roleAtt");
            String[] Attatts = req.getParameterValues("attAtt");
            String[] SceneAtts = req.getParameterValues("sceneAtt");

            String sidebar = "<li><a href='#'>"+name+"</a></li>";
            String thumbnail = "<img src='#' alt=''character' class='img-thumbnail' width='80' height='80' />";

            model.addAttribute("sidebar",sidebar);
            model.addAttribute("thumbnail",thumbnail);
            sidebarList.add(sidebar);
            thumbnailList.add(thumbnail);

            //sidebarList.set(listNum,sidebar);
            //thumbnailList.set()

            //sidebarList.set(listNum,sidebar);
            //thumbnailList.set(listNum,thumbnail);


//            String result = "";
//            for (String s : Realatts){
//                result += s;
//            }
//
//            result = result;
        }


        return "character";
    }

    @RequestMapping("/newCharacter.form")
    public String NewCharacter(HttpServletRequest req, Model model){
        String email = (String)req.getSession().getAttribute("email");
        model.addAttribute("email",email);

//        listNum++;
//        String sidebar = "";
//        String thumbnail = "";
//
//        if (sidebarList.get(0) != null){
//            for (int i=0; i <sidebar.length(); i++){
//                sidebar += sidebarList.get(i);
//                //thumbnail += thumbnailList.get(i);
//            }
//        }
//
//        model.addAttribute("sidebar",sidebar);
//        model.addAttribute("thumbnail",thumbnail);


        return "character";
    }







}
