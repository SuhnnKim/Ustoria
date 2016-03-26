package model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Summary")
@XmlAccessorType(XmlAccessType.FIELD)
public class Summary
{
    //private SummarySection summarySection;

    //private SummarySequence summarySequence;
    @XmlAttribute(name="id")
    private String id;
    @XmlAttribute(name="content")
    private String content;
    @XmlAttribute(name="summary-name")
    private String name;
    // private String sceneId;
    
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name  = name;
    }
    
    

//    public SummarySection getSummarySection ()
//    {
//        return summarySection;
//    }
//
//    public void setSummarySection (SummarySection summarySection)
//    {
//        this.summarySection = summarySection;
//    }
//
//    public SummarySequence getSummarySequence ()
//    {
//        return summarySequence;
//    }
//
//    public void setSummarySequence (SummarySequence summarySequence)
//    {
//        this.summarySequence = summarySequence;
//    }
//
//    @Override
//    public String toString()
//    {
//        return "ClassPojo [summary-section = "+summarySection+", summary-sequence = "+summarySequence+"]";
//    }
}
			
			