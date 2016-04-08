package model;

import sun.applet.Main;

import javax.xml.bind.annotation.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@XmlRootElement(name="story")
@XmlAccessorType(XmlAccessType.NONE)
public class Story {


    private int id;


    @XmlAttribute(name="author")
    private String author;
    @XmlAttribute(name="title")
    private String title;
    @XmlAttribute(name="description")
    private String description;

    @XmlAttribute(name="date")
    private String date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date today = null;
        try {
             today = dateFormat.parse(dateFormat.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.date = today.toString();
    }

    // protected List<Attribute> attributeDefinitions;
    @XmlElement(name = "Summary")
    private MainSummary summary;


    private Map<String, String> globalVariables;
   //private List<RelationshipDefinition> relationshipDefinitions;
    
   // private LayoutDefinition layoutDefinition;
  //  @XmlElement(name="Characters")
    private List<Character> characterList;


    public List<Character> getCharacterList() {
        return characterList;
    }

    public void setCharacterList(List<Character> characterList) {
        this.characterList = characterList;
    }
    //private List<Roles> rolesList;

    //private List<Scene> sceneList;

   

    //private ScriptDefinitions scriptDefinitions;

    public Story() {
        globalVariables = new HashMap();
        //attributeDefinitions = new ArrayList();
        summary = new MainSummary();

        //relationshipDefinitions = new ArrayList();
        //sceneList = new LinkedList();
        //rolesList = new ArrayList();
        characterList = new ArrayList();
    }

    public MainSummary getSummary() {
        return summary;
    }

    public void setSummary(MainSummary summary) {
        this.summary = summary;
    }
//   // public List<Summary> getSummaryList() {
//        return summaryList;
//    }
//
//    public void putSummary(int index, Summary summary) {
//        this.summaryList.add(index, summary);
//    }

//    public Summary getSummaryById(String id) {
//        for (Summary summary : summaryList) {
//            if (summary.getId().equals(id)) {
//                return summary;
//            }
//        }
//        return null;
//    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

//    public List<Attribute> getAttributeDefinitions() {
//        return attributeDefinitions;
//    }
//
//    public void setAttributeDefinitions(List<Attribute> attributeDefinitions) {
//        this.attributeDefinitions = attributeDefinitions;
//    }

    public Map<String, String> getGlobalVariables() {
        return globalVariables;
    }

    public void setGlobalVariables(Map<String, String> globalVariables) {
        this.globalVariables = globalVariables;
    }

//    public List<RelationshipDefinition> getRelationshipDefinitions() {
//        return relationshipDefinitions;
//    }
//
//    public void setRelationshipDefinitions(List<RelationshipDefinition> relationshipDefinitions) {
//        this.relationshipDefinitions = relationshipDefinitions;
//    }

//    //public List<Character> getCharacterList() {
//        return characterList;
//    }
//
//    //public void setCharacterList(List<Character> characterList) {
//        this.characterList = characterList;
//    }

//    public List<Roles> getRolesList() {
//        return rolesList;
//    }
//
//    public void setRolesList(List<Roles> rolesList) {
//        this.rolesList = rolesList;
//    }
//
//    public List<Scene> getSceneList() {
//        return sceneList;
//    }
//
//    public void setSceneList(List<Scene> sceneList) {
//        this.sceneList = sceneList;
//    }
//
//
//
//    public List<Attribute> getAttributeList() {
//        return attributeDefinitions;
//    }
//
//    public Attribute getAttributeById(String id) {
//        for (Attribute attribute : attributeDefinitions) {
//            if (attribute.getId().equals(id)) {
//                return attribute;
//            }
//        }
//        return null;
//    }
//
//    public void putAttribute(Attribute attribute) {
//        attributeDefinitions.add(attribute);
//    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public List<Roles> getRoleDefinitions() {
//        return rolesList;
//    }
//
//
//    public List<Scene> getSceneDefinitions() {
//        return sceneList;
//    }
//

    public Map<String, String> getGlobalVariablesList() {
        return globalVariables;
    }

    public String getGlobalVariableById(String id) {
        return globalVariables.get(id);
    }

    public void putGlobalVariable(String id, String value) {
        globalVariables.put(id, value);
    }

//
//    public LayoutDefinition getLayoutDefinitions() {
//        return layoutDefinition;
//    }
//
//    public void setLayoutDefinitions(LayoutDefinition layoutDefinition) {
//        this.layoutDefinition = layoutDefinition;
//    }

   
    @Override
    public String toString() {
        //return "ClassPojo [summary = " + summaryList.toString() + ", author = " + author + ", title = " + title + ", character-definitions = " + characterList.toString() + ", attribute-definitions = " + attributeDefinitions + ", name = " + name + ", role-definitions = " + rolesList.toString() + ", scene-definitions = " + sceneList + ", global-variables = " + globalVariables + ", relationship-definitions = " + relationshipDefinitions + ", layout-definition = " + layoutDefinition + "]";
       return  "{title:"+this.getTitle()+",description:"+this.getDescription()+",date:"+this.getDate()+"}";
       // return "Story Class";
    }

    public void saveCharacter(Character newCharacter) {
        characterList.add(newCharacter);
    }
}
