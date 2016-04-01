package controller;

import com.google.gson.Gson;
import dao.userDAO;
import dao.userDAOImpl;
import entity.UsersEntity;
import model.Attribute;
import model.Character;
import model.MainSummary;
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
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.*;


@Controller
public class UserController {


    //@RequestMapping("login")
    public String Login(HttpServletRequest req, Model model){

        model.addAttribute("pageName","Projects");
        return "home";

    }
    @RequestMapping("dialog")
    public String Dialog(HttpServletRequest req, Model model){
        Story story = (Story)req.getSession().getAttribute("story");

        MainSummary mainSummary = story.getSummary();
        req.setAttribute("characterList",story.getCharacterList());
        req.setAttribute("projectTitle",story.getName());
        req.setAttribute("summaryList",mainSummary.getSummaryList());
        model.addAttribute("pageName","Dialog");
        model.addAttribute("characterList",story.getCharacterList());
        model.addAttribute("projectTitle",story.getName());
        model.addAttribute("summaryList",mainSummary.getSummaryList());
        return "dialog";

    }

    @RequestMapping("getXML")
    public String getXML(HttpServletRequest req,Model model){

        Story storyObject = getStoryFromSession(req);
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(Story.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            //Marshal the employees list in console
            jaxbMarshaller.marshal(storyObject, System.out);

            //Marshal the employees list in file
            jaxbMarshaller.marshal(storyObject, new File("P:/story.xml"));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        model.addAttribute("pageName","Projects");
        return "home";
    }

   // @RequestMapping("/signup.form")
    public String SignUp(HttpServletRequest req, Model model){


        String fname = req.getParameter("fname");

        String lname = req.getParameter("lname");

        String email = req.getParameter("email-signup");

        String password = req.getParameter("password-signup");

        model.addAttribute("email",email);

        model.addAttribute("pageName","Projects");

        return "home";

    }



    @RequestMapping("/playground.form")
    public ModelAndView Playground(HttpServletRequest req, Model model){
        String email = (String)req.getSession().getAttribute("email");
        model.addAttribute("email",email);

        model.addAttribute("pageName","Playground");

//        model.addAttribute("characterList",story.getCharacterList());
      //  MainSummary m = story.getSummary();
       // model.addAttribute("summaryList",m.getSummaryList());

        Story story = getStoryFromSession(req);
        MainSummary mainSummary = new MainSummary();
        mainSummary = story.getSummary();
        //req.setAttribute("summaryList",m.getSummaryList());

        ModelAndView m = new ModelAndView("playground");


        req.setAttribute("characterList",story.getCharacterList());
        req.setAttribute("projectTitle",story.getName());
        //req.setAttribute("character", );

        req.setAttribute("summaryList",mainSummary.getSummaryList());

        return m;
    }

    @RequestMapping("home")
    public String Home(HttpServletRequest req, Model model){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        String email = (String)req.getSession().getAttribute("email");
        model.addAttribute("email",name);
        req.getSession().setAttribute("email",name);
        model.addAttribute("pageName","Projects");
        return "home";
    }

    @RequestMapping("search")
    public String Search(HttpServletRequest req, Model model){
        Story story = getStoryFromSession(req);

        String search = req.getParameter("search");
        int CharacterCount = 0;
        List<Character> foundCharacterList = new ArrayList<Character>();
        for(Character c : story.getCharacterList()){
            if(c.getName().contains(search)){
                CharacterCount++;
                foundCharacterList.add(c);
            }
        }
        model.addAttribute("search",search);
        model.addAttribute("CharacterCount",CharacterCount);
        model.addAttribute("foundCharacterList",foundCharacterList);

        req.getSession().setAttribute("search",search);
        return "search";
    }

    public Story getStoryFromSession(HttpServletRequest request){

        HttpSession session = request.getSession();

        Story story = new Story();
        if (session.getAttribute("story")== null){
            session.setAttribute("story", new Story());
        }
        else {
            story = (Story) request.getSession().getAttribute("story");
        }
        return story;
    }






}
