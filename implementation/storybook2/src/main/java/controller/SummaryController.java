package controller;


import com.google.gson.Gson;

import model.Character;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.portlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import model.*;
import util.DataSerializer;

/**
 * Created by AshirwadTank on 3/2/2016.
 */
@Controller
public class SummaryController {



    /*@RequestMapping(value={"/summary.form","/summary" }, method=RequestMethod.GET)
    public ModelAndView redirectToSummary(HttpServletRequest req, Model model){

        String email = (String)req.getSession().getAttribute("email");
        model.addAttribute("email",email);
        model.addAttribute("pageName","Summary");

        Story story = getStoryFromSession(req);


        MainSummary mainSummary = new MainSummary();
        mainSummary = story.getSummary();


        ModelAndView m = new ModelAndView("summary");


        req.setAttribute("characterList",story.getCharacterList());
        req.setAttribute("projectTitle",story.getTitle());


        req.setAttribute("summaryList",mainSummary.getSummaryList());
        req.setAttribute("summary",mainSummary.getFullSummary());
        return m;
    }*/

    /* Saving Summary Section */
    @RequestMapping(value="{sId}/AddSummarySection",method= RequestMethod.POST)
    public @ResponseBody String saveSummarySection(HttpServletRequest request, @RequestParam(value = "summaryName") String summaryName,
                       @RequestParam(value = "summaryContent") String summaryContent){

  try {
      Story story = getStoryFromSession(request);


      MainSummary mainSummary = story.getSummary();
      List<Summary> summaryList = mainSummary.getSummaryList();

    //  System.out.println(summaryContent);

      Summary newSummary = new Summary();
      newSummary.setName(summaryName);
      newSummary.setContent(summaryContent);

      String summarySectionId =UUID.randomUUID().toString();
      newSummary.setId(summarySectionId);

      summaryList.add(newSummary);

      //mainSummary.setSummaryList(summaryList); // set latest summaryList

      //story.setSummary(mainSummary); // set main summary object

      request.getSession().setAttribute("story",story);

      Gson gson = new Gson();
     String jsonString = gson.toJson(summaryList);
      return jsonString;

  }
  catch(Exception e){
      System.out.println(e.getMessage());
  }
        return  null;
    }

    @RequestMapping(value="{sId}/deleteSummarySection",method= RequestMethod.POST)
    public @ResponseBody String deleteSummarySection(HttpServletRequest request, @RequestParam(value = "summaryId") String summaryId){

        try {
            Story story = getStoryFromSession(request);



            MainSummary mainSummary = story.getSummary();


            mainSummary.deleteSummarySection(summaryId);


           // mainSummary.setSummaryList(summaryList);
           // request.getSession().setAttribute("story",story);

            Gson gson = new Gson();
            String jsonString = gson.toJson(mainSummary.getSummaryList());
            return jsonString;

        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return  null;
    }



    /* Saving Main Summary */
    @RequestMapping(value="{sId}/SaveSummary",method=RequestMethod.POST)
    public @ResponseBody String SaveSummary(HttpServletRequest request,@RequestParam(value = "summaryData") String summaryText){

        Story story = getStoryFromSession(request);

       MainSummary m = story.getSummary();
        String formatedSummary = summaryText.replaceAll("(<br>|<br />)","\\n");
        m.setFullSummary(summaryText);

        story.setSummary(m);

        request.getSession().setAttribute("story",story);
        return m.getFullSummary();
    }


    @RequestMapping(value="{sId}/getSummarySectionText",method=RequestMethod.POST)
    public @ResponseBody String getSummarySectionText(HttpServletRequest request,@RequestParam(value = "summaryData") String summaryText){

        Story story = getStoryFromSession(request);

        MainSummary m = story.getSummary();
        Summary summary = m.getSummaryByName(summaryText);

        return summary.getContent();
    }



    /* Create or set session object */
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
