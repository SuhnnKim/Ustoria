package model;

import com.sun.xml.internal.txw2.annotation.XmlElement;

import javax.xml.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@XmlRootElement(name = "Character")
@XmlAccessorType(XmlAccessType.FIELD)
public class Character
{
    @XmlAttribute(name="id")
    private String id;

   // private List<Model> modelList;
   @XmlAttribute(name="character-name")
    private String name;

   // private List<Image> imageList;

   // private Model preferedModel;
    
   // private Image preferedImage;
   @XmlElementWrapper(name = "attributeMap")
   private Map<String,List<Attribute>> attributeList;

    @XmlAttribute(name="description")
    private String characterDescription;

    //private List<Scene> sceneList;



   // private List<Relationship> relationships;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }



    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public Map<String, List<Attribute>> getAttributeList() {
        return attributeList;
    }

    public void setAttributeList(Map<String, List<Attribute>> attributeList) {
        this.attributeList = attributeList;
    }

//    public List<Model> getModelList() {
//        return modelList;
//    }
//
//    public void setModelList(List<Model> modelList) {
//        this.modelList = modelList;
//    }
//
//    public List<Image> getImageList() {
//        return imageList;
//    }
//
//    public void setImageList(List<Image> imageList) {
//        this.imageList = imageList;
//    }
//
//    public Model getPreferedModel() {
//        return preferedModel;
//    }
//
//    public void setPreferedModel(Model preferedModel) {
//        this.preferedModel = preferedModel;
//    }
//
//    public Image getPreferedImage() {
//        return preferedImage;
//    }
//
//    public void setPreferedImage(Image preferedImage) {
//        this.preferedImage = preferedImage;
//    }

//    public List<Attribute> getAttributeList() {
//        return attributeList;
//    }
//
//    public void setAttributeList(String name,List<Attribute> attributeList) {
//
//        this.attributeList = attributeList;
//    }

    public String getCharacterDescription ()
    {
        return characterDescription;
    }

    public void setCharacterDescription (String characterDescription)
    {
        this.characterDescription = characterDescription;
    }

   // public List<Relationship> getRelationships ()
//    {
//        return relationships;
//    }

  
    @Override
    public String toString()
    {
        return null;
        //return "ClassPojo [id = "+id+", name = "+name+", character-description = "+characterDescription+", relationships = "+relationships+"]";
    }

}
			
			