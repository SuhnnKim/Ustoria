package controller;

import com.google.gson.Gson;
import entity.UsersEntity;
import model.Character;
import model.MainSummary;
import model.Story;
import model.Summary;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.portlet.ModelAndView;
import util.DataSerializer;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import dao.userDAO;


@Controller
public class UserController {

    List<Story> storyList = null;

    private static final int BUFFER_SIZE = 4096;

    @Autowired
    private userDAO userDAO;
    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("hibernate-config.xml");

    @RequestMapping("login")
    public String Login(HttpServletRequest req, Model model){
        Story story = (Story)req.getSession().getAttribute("story");

        if (story == null){
            Story s = new Story();
            req.getSession().setAttribute("story",s);

        }
        story = (Story)req.getSession().getAttribute("story");
        MainSummary mainSummary = story.getSummary();
        req.setAttribute("characterList",story.getCharacterList());
        req.setAttribute("projectTitle",story.getTitle());
        req.setAttribute("summaryList",mainSummary.getSummaryList());


        model.addAttribute("pageName","Projects");
        return "index";

    }
    @RequestMapping("/{sId}/dialog")
//    @RequestMapping("dialog")
    public String Dialog(HttpServletRequest req, Model model){
//        Story story = (Story)req.getSession().getAttribute("story");
//
//        MainSummary mainSummary = story.getSummary();
//        req.setAttribute("characterList",story.getCharacterList());
//        req.setAttribute("projectTitle",story.getTitle());
//        req.setAttribute("summaryList",mainSummary.getSummaryList());
//
        model.addAttribute("pageName","Dialog");

        return "new_dialog";

    }

    /**
     * Saves a particular story and generates XML
     *
     * @param req HttpServletRequest which will have a different request and session objects.
     *
     * */

    @RequestMapping(value = "/{sId}/saveStory",method = RequestMethod.GET)
    public @ResponseBody String getXML(HttpServletRequest req){

        Story storyObject = getStoryFromSession(req);
        DataSerializer ds = new DataSerializer();
        ds.serializeXML(storyObject);

        return "saved";
//        return "index";
    }


//    @RequestMapping("dialog")
//    public String Dialog(HttpServletRequest req, Model model){
//        return "dialog";
//    }

    @RequestMapping("/signup.form")
    public String SignUp(HttpServletRequest req, Model model){


        String fname = req.getParameter("fname");

        String lname = req.getParameter("lname");

        String name = fname + " " + lname;

        String email = req.getParameter("email-signup");

        String password = req.getParameter("password-signup");

        model.addAttribute("email",email);

        model.addAttribute("pageName","Projects");
        userDAO userDAO = context.getBean(userDAO.class);

        UsersEntity user = new UsersEntity();
        user.setUserName(name);
        user.setEmail(email);
        Md5PasswordEncoder encoder = new Md5PasswordEncoder();
        String encoderPassword = encoder.encodePassword(password,null);
        user.setPassword(encoderPassword);

        userDAO.save(user);

        return "home";

    }


    /**
     * Redirects to Playground Page where you can add and connects Scenes
     * @param req Request Attribute
     * @return Playground jsp page
     */
    @RequestMapping("{sId}/playground.form")
    public String Playground(HttpServletRequest req){
        String email = (String)req.getSession().getAttribute("email");
        req.setAttribute("email",email);

        req.setAttribute("pageName","Playground");

        Story story = getStoryFromSession(req);
        MainSummary mainSummary = new MainSummary();
        mainSummary = story.getSummary();

        req.setAttribute("characterList",story.getCharacterList());
        req.setAttribute("projectTitle",story.getTitle());


        req.setAttribute("summaryList",mainSummary.getSummaryList());

        return "playground";
    }

    /**
     * It is the home page of user after Login. It will contain all the list of stories he has created uptil now.
     * All the de-serialization is done using JAXB
     * @param req Contains the request attribute
     * @return Home Jsp Page
     * @throws JAXBException
     */

    @RequestMapping("home")
    public String Home(HttpServletRequest req, Model model) throws JAXBException {

        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        String email = (String)req.getSession().getAttribute("email");
        req.setAttribute("email",name);
        model.addAttribute("email",name);
        req.getSession().setAttribute("email",name);


        File folder = new File("/Users/liuwei/Desktop/Temp/");
        File[] listOfFiles = folder.listFiles();

        Story stories=null;
        storyList= new ArrayList<Story>();
        int count=0;

        DataSerializer ds = new DataSerializer();
        /* Iterating The list of files in folder */

        for (File file : listOfFiles) {
        /* isFile Check */
            if (file.isFile()) {

                stories = ds.deSerializeXML(file);

                stories.setId(count);

                count++;

                storyList.add(stories);

            }
        }

        req.getSession().setAttribute("storyBook",storyList);
        req.setAttribute("pageName","Projects");
        req.setAttribute("storyList",storyList);
        return "home";
    }

    /**
     *
     *
     * @param req
     * @param model
     * @return
     */
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


    /**
     *
     *
     * @param req
     * @param model
     * @param sId the id of the story from the story object . It is a Path Variable
     * @return redirects to summary page
     */
    @RequestMapping(value={"/{sId}/summary.form","/{sId}/summary" }, method=RequestMethod.GET)
    public String redirectToSummary(HttpServletRequest req, Model model,@PathVariable int sId){

        String email = (String)req.getSession().getAttribute("email");

        List<Story> storyBook = (List<Story>) req.getSession().getAttribute("storyBook");
        //int storyId = Integer.parseInt(req.getParameter("storyId"));
        Story story = storyBook.get(sId);

       // req.setAttribute("story",story);

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
        req.setAttribute("email",email);
        req.setAttribute("pageName","Summary");

        req.setAttribute("characterList",story.getCharacterList());
        req.setAttribute("projectTitle",story.getTitle());
        req.setAttribute("summaryList",mainSummary.getSummaryList());
        req.setAttribute("summary",mainSummary.getFullSummary());

        return "summary";
    }


    /**
     *
     *
     *
     * @param request HttpServletRequest Object
     * @param response HttpServletResponse Object
     * @return Redirects to the home page
     * @throws IOException
     */
    @RequestMapping(value="/downloadXML/{sId}",method = RequestMethod.GET)
    public String downloadFile(HttpServletRequest request, HttpServletResponse response,@PathVariable int sId) throws IOException {


        Story myStory = storyList.get(sId);

        String fullPath = "/Users/liuwei/Desktop/Temp/" + myStory.getTitle()+".xml";
        File downloadFile = new File(fullPath);
        FileInputStream inputStream = new FileInputStream(downloadFile);


        // get MIME type of the file
        MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
        String mimeType = mimeTypesMap.getContentType(downloadFile);
        if (mimeType == null) {
            // set to binary type if MIME mapping not found
            mimeType = "application/octet-stream";
        }
        System.out.println("MIME type: " + mimeType);

        // set content attributes for the response
        response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());

        // set headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
                downloadFile.getName());
        response.setHeader(headerKey, headerValue);

        // get output stream of the response
        OutputStream outStream = response.getOutputStream();

        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = -1;

        // write bytes read from the input stream into the output stream
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }

        inputStream.close();
        outStream.close();


        return "redirect:/home";
    }

    @RequestMapping(value="/CreateStory",method=RequestMethod.POST)
    public @ResponseBody String createStory(HttpServletRequest request,@RequestParam(value = "projectName") String projectName,@RequestParam(value = "projectDesc") String projectDesc){

        Story newStory = new Story();
        newStory.setTitle(projectName);
        newStory.setDescription(projectDesc);
        newStory.setDate();

        //request.getSession().setAttribute("story",story);

        storyList.add(newStory);

        int storyArrayId = storyList.lastIndexOf(newStory);
        JSONObject storySet = new JSONObject();
        storySet.put("title", newStory.getTitle()) ;
        storySet.put("description", newStory.getDescription()) ;
        storySet.put("date", newStory.getDate()) ;
        storySet.put("storyArrayid",storyArrayId);

        DataSerializer ds = new DataSerializer();

        ds.serializeXML(newStory);


        Gson gson = new Gson();
        String storyString = gson.toJson(storySet);

        return storyString;
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
