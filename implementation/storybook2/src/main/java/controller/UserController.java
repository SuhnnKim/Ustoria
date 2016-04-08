package controller;

import com.google.gson.Gson;
import dao.userDAO;
import dao.userDAOImpl;
import entity.UsersEntity;
import model.*;
import model.Character;
import org.hibernate.bytecode.buildtime.spi.Logger;
import org.hibernate.mapping.Array;
import org.hibernate.type.IntegerType;
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
import sun.applet.Main;
import sun.security.provider.MD5;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Path;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.*;


@Controller
public class UserController {

    List<Story> storyList = null;

    //@RequestMapping("login")
    public String Login(HttpServletRequest req, Model model){
        Story story = (Story)req.getSession().getAttribute("story");

        MainSummary mainSummary = story.getSummary();
        req.setAttribute("characterList",story.getCharacterList());
        req.setAttribute("projectTitle",story.getTitle());
        req.setAttribute("summaryList",mainSummary.getSummaryList());


        model.addAttribute("pageName","Projects");
        return "home";

    }
    @RequestMapping("dialog")
    public String Dialog(HttpServletRequest req, Model model){
        Story story = (Story)req.getSession().getAttribute("story");

        MainSummary mainSummary = story.getSummary();
        req.setAttribute("characterList",story.getCharacterList());
        req.setAttribute("projectTitle",story.getTitle());
        req.setAttribute("summaryList",mainSummary.getSummaryList());

        model.addAttribute("pageName","Dialog");

        return "dialog";

    }

    /**
     * Saves a particular story and generates XML
     *
     * @param req HttpServletRequest which will have a different request and session objects.
     *
     * */

    @RequestMapping("getXML/{fileId}")
    public String getXML(HttpServletRequest req, Model model, @PathVariable int fileId){

       // Story storyObject = getStoryFromSession(req);

        Story storyObject = storyList.get(fileId);
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(Story.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(storyObject, new File("P:/Ustoria/"+storyObject.getTitle()+".xml"));
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
        req.setAttribute("projectTitle",story.getTitle());
        //req.setAttribute("character", );

        req.setAttribute("summaryList",mainSummary.getSummaryList());

        return m;
    }

    @RequestMapping("home")
    public String Home(HttpServletRequest req, Model model) throws JAXBException {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        String email = (String)req.getSession().getAttribute("email");
        model.addAttribute("email",name);
        req.getSession().setAttribute("email",name);


        File folder = new File("P:/Ustoria/");
        File[] listOfFiles = folder.listFiles();

        Story stories=null;
        storyList= new ArrayList<Story>();
        int count=0;

        /* Iterating The list of files in folder */

        for (File file : listOfFiles) {
        /* isFile Check */
            if (file.isFile()) {

                JAXBContext jaxbContext = JAXBContext.newInstance(Story.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

                /* Converting  XML to Story Object */
                stories = (Story) jaxbUnmarshaller.unmarshal( new File("P:/Ustoria/"+file.getName()));

                stories.setId(count);
                count++;


                storyList.add(stories);

            }
        }

        model.addAttribute("pageName","Projects");
        model.addAttribute("storyList",storyList);
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

        int SummaryCount = 0;

        MainSummary wholeSummary = story.getSummary(); // get Main Summary Object

        String mainSummaryText = wholeSummary.getFullSummary(); // get whole summary

        List<Summary> summarySection = wholeSummary.getSummaryList(); //list of all summary sections

        List<Summary> foundSummaryList = new ArrayList();

        for(Summary s : summarySection){
            if(s.getContent().contains(search)){
                SummaryCount++;
                foundSummaryList.add(s);
            }
        }

        model.addAttribute("search",search);
        model.addAttribute("CharacterCount",CharacterCount);
        model.addAttribute("foundCharacterList",foundCharacterList);

        model.addAttribute("SummaryCount",SummaryCount);
        model.addAttribute("foundSummaryList",foundSummaryList);


        req.getSession().setAttribute("search",search);
        return "search";
    }


    @RequestMapping(value={"/summary.form","/summary" }, method=RequestMethod.GET)
    public ModelAndView redirectToSummary(HttpServletRequest req, Model model){

        String email = (String)req.getSession().getAttribute("email");

        int storyId = Integer.parseInt(req.getParameter("storyId"));
        Story story = storyList.get(storyId);

        req.setAttribute("story",story);

        if(story == null) {
            story = getStoryFromSession(req);
        }
        else{
            HttpSession newSession = req.getSession();
            newSession.setAttribute("story",story);
        }

        MainSummary mainSummary = new MainSummary();
        mainSummary = story.getSummary();


        ModelAndView summaryModel = new ModelAndView("summary");
        model.addAttribute("email",email);
        model.addAttribute("pageName","Summary");

        req.setAttribute("characterList",story.getCharacterList());
        req.setAttribute("projectTitle",story.getTitle());
        req.setAttribute("summaryList",mainSummary.getSummaryList());
        req.setAttribute("summary",mainSummary.getFullSummary());

        return summaryModel;
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
