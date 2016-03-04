package model;

import java.lang.*;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author AshirwadTank
 */
public class Scene {
    
    private String id;
    private String name;
    
   // private List<SceneObject> sceneObjects;
    
    private List<Scene> sceneOptions;
    
    private Scene nextScene;
    
   // private List<Callout> calloutList;
    
    private List<Summary> summary;

    private java.lang.Character character;

    public List<Summary> getSummary() {
        return summary;
    }





//    public void setSummary(Summary summary) {
//        this.summary = summary;
//    }
    public java.lang.Character getCharacter() {
        return character;
    }

    public void setCharacter(java.lang.Character characterObject) {
        this.character = characterObject;
    }
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public List<SceneObject> getSceneObjects() {
//        return sceneObjects;
//    }
//
//    public void setSceneObjects(List<SceneObject> sceneObjects) {
//        this.sceneObjects = sceneObjects;
//    }
//
//    public Scene getNextScene() {
//        return nextScene;
//    }
//
//    public void setNextScene(Scene nextScene) {
//        this.nextScene = nextScene;
//    }
//
//    public List<Callout> getCalloutList() {
//        return calloutList;
//    }
//
//    public void setCalloutList(List<Callout> calloutList) {
//        this.calloutList = calloutList;
//    }
    
    
    
    
}
