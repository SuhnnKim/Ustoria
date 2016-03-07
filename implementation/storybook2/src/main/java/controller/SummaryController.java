package controller;


import com.google.gson.Gson;

import model.Character;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import model.*;
/**
 * Created by AshirwadTank on 3/2/2016.
 */
@Controller
public class SummaryController {

   // List<Summary> summaryList = new ArrayList();

    @RequestMapping(value={"/summary.form","/summary.jsp","/summary"} , method=RequestMethod.GET)
    public ModelAndView redirectToSummary(HttpServletRequest req, Model model){

        String email = (String)req.getSession().getAttribute("email");
        model.addAttribute("email",email);


        Story story = getStoryFromSession(req);
        MainSummary mainSummary = new MainSummary();
        mainSummary = story.getSummary();
        //req.setAttribute("summaryList",m.getSummaryList());

        ModelAndView m = new ModelAndView("summary");


        req.setAttribute("characterList",story.getCharacterList());
        //req.setAttribute("character", );

        req.setAttribute("summaryList",mainSummary.getSummaryList());
        req.setAttribute("summary",mainSummary.getFullSummary());
        return m;
    }

    /* Saving Summary Section */
    @RequestMapping(value="/AddSummarySection",method= RequestMethod.POST)
    public @ResponseBody String SaveSummarySection(HttpServletRequest request, @RequestParam(value = "summaryName") String summaryName,
                       @RequestParam(value = "summaryContent") String summaryContent){

  try {
      Story story = getStoryFromSession(request);


      MainSummary mainSummary = story.getSummary();
      List<Summary> summaryList = mainSummary.getSummaryList();


      Summary newSummary = new Summary();
      newSummary.setName(summaryName);
      newSummary.setContent(summaryContent);


      summaryList.add(newSummary);

      mainSummary.setSummaryList(summaryList); // set latest summaryList

      story.setSummary(mainSummary); // set main summary object

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
    /* Saving Main Summary */
    @RequestMapping(value="/SaveSummary",method=RequestMethod.POST)
    public @ResponseBody String SaveSummary(HttpServletRequest request,@RequestParam(value = "summaryData") String summaryText){

        Story story = getStoryFromSession(request);

       MainSummary m = story.getSummary();
        String formatedSummary = summaryText.replaceAll("(<br>|<br />)","\\n");
        m.setFullSummary(summaryText);

        story.setSummary(m);

        request.getSession().setAttribute("story",story);
        return m.getFullSummary().replaceAll("\\n","<br>");
    }


    @RequestMapping(value="/getSummarySectionText",method=RequestMethod.POST)
    public @ResponseBody String getSummarySectionText(HttpServletRequest request,@RequestParam(value = "summaryData") String summaryText){

        Story story = getStoryFromSession(request);

        MainSummary m = story.getSummary();
        Summary summary = m.getSummaryByName(summaryText);

        return summary.getContent().replaceAll("\\n","<br>");
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
