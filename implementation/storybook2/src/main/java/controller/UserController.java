package controller;

import com.google.gson.Gson;
import model.Character;
import model.MainSummary;
import model.Story;
import model.Summary;
import org.json.simple.JSONObject;
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


@Controller
public class UserController {

    List<Story> storyList = null;

    private static final int BUFFER_SIZE = 4096;


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

    @RequestMapping(value = "/{sId}/saveStory",method = RequestMethod.GET)
    public @ResponseBody String getXML(HttpServletRequest req, Model model){

        Story storyObject = getStoryFromSession(req);

       // Story storyObject = storyList.get(fileId);
        String previousPath = req.getRequestURL().toString();
        DataSerializer ds = new DataSerializer();

        ds.serializeXML(storyObject);

       // req.setAttribute("pageName","Projects");
        return "saved";
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



    @RequestMapping("{sId}/playground.form")
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
        model.addAttribute("pageName","Projects");
        model.addAttribute("storyList",storyList);
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

        String fullPath = "P:/Ustoria/" + myStory.getTitle()+".xml";
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

        Story story = getStoryFromSession(request);
        story.setTitle(projectName);
        story.setDescription(projectDesc);
        story.setDate();

        request.getSession().setAttribute("story",story);

        JSONObject storySet = new JSONObject();
        storySet.put("title", story.getTitle()) ;
        storySet.put("description", story.getDescription()) ;
        storySet.put("date", story.getDate()) ;

        DataSerializer ds = new DataSerializer();

        ds.serializeXML(story);


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
