package controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import model.*;
/**
 * Created by AshirwadTank on 3/2/2016.
 */
@Controller
public class SummaryController {

    List<Summary> summaryList = new ArrayList();

    @RequestMapping(value="/summary" , method=RequestMethod.GET)
    public String redirectToSummary(HttpServletRequest req, Model model){

        //ModelAndView m = new ModelAndView("summary");
        System.out.println("Inside Controller");
        model.addAttribute("summaryList","Hello World");
        return "summary";
    }

    /* Saving Summary Section */
    @RequestMapping(value="/AddSummarySection",method= RequestMethod.POST)
    public @ResponseBody
    List<Summary>
    SaveSummarySection(HttpServletRequest request, @RequestParam(value = "summaryName") String summaryName,
                       @RequestParam(value = "summaryContent") String summaryContent){


        Summary newSummary = new Summary();

        newSummary.setName(summaryName);
        newSummary.setContent(summaryContent);
        summaryList.add(newSummary);

        return summaryList;
    }

    /* Saving Main Summary */
    @RequestMapping(value="/SaveSummary",method=RequestMethod.POST)
    public String SaveSummary(HttpServletRequest request,@RequestParam(value = "summaryData") String summaryText){

      // MainSummary m = new MainSummary();
        //m.setFullSummary(summaryText);

        return summaryText;
    }

}
