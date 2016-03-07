package model;



import java.util.ArrayList;
import java.util.List;

/**
 * Created by AshirwadTank on 3/2/2016.
 */
public class MainSummary {

    private String fullSummary;
    private List<Summary> summaryList;

    public MainSummary(){

        summaryList = new ArrayList<Summary>();
        fullSummary = "";
    }

    public List<Summary> getSummaryList() {
        return summaryList;
    }

    public void setSummaryList(List<Summary> summaryList){
        this.summaryList = summaryList;
    }

    public void addSummarySection(Summary summarySection){
        this.summaryList.add(summarySection);
    }

    public void deleteSummarySection(int summaryIndex){
        this.summaryList.remove(summaryIndex);
    }

    public void updateSummarySectionByIndex(Summary summarySection,int index){
       for(int i=0;i< summaryList.size();i++){
           if(i == index){
               summaryList.set(index,summarySection);
               return;
           }
       }
    }

    public Summary getSummaryByName(String name){
        System.out.println(name);
        for(int i=0;i< this.summaryList.size();i++){
            if(this.summaryList.get(i).getName().equals(name)){
                System.out.println(summaryList.get(i).getName());
                return this.summaryList.get(i);

            }
        }

        return null;
    }


    public String getFullSummary() {
        return fullSummary;
    }

    public void setFullSummary(String fullSummary) {
        this.fullSummary = fullSummary;
    }
}
