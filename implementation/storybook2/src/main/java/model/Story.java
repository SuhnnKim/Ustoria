package model;

import sun.applet.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Story {

    private String author;
    private String title;
    private String name;

   // protected List<Attribute> attributeDefinitions;
    private MainSummary summary;
    private Map<String, String> globalVariables;
   //private List<RelationshipDefinition> relationshipDefinitions;
    
   // private LayoutDefinition layoutDefinition;

    public List<Character> getCharacterList() {
        return characterList;
    }

    public void setCharacterList(List<Character> characterList) {
        this.characterList = characterList;
    }

    private List<Character> characterList;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    return "Story Class";
    }

    public void addCharacter(Character character) {
        characterList.add(character);
    }
}
