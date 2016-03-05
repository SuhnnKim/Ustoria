package controller;

import com.google.gson.Gson;
import model.Attribute;
import model.Character;
import model.Story;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by liuwei on 2016-03-05.
 */
@Controller
public class CharacterController {

    @RequestMapping("character")
    public String Character(HttpServletRequest req, Model model){
        String email = (String)req.getSession().getAttribute("email");
        model.addAttribute("email",email);


        return "character";

    }

    @RequestMapping("selectCharacter")
    @ResponseBody
    public String selectCharacter(HttpServletRequest req, Model model,
                            @RequestParam(value = "id") String uuid){
        String email = (String)req.getSession().getAttribute("email");
        model.addAttribute("email",email);


        Story story = (Story)req.getSession().getAttribute("story");
        if (story == null){
            req.getSession().setAttribute("story", new Story());
        }
        story = (Story)req.getSession().getAttribute("story");

        List<Character> characterList = story.getCharacterList();

        Character selectCharacter = new Character();

        for (Character temp : characterList){
            if (temp.getId().equals(uuid)){
                selectCharacter = temp;
            }
        }

        Gson gson = new Gson();
        String jsonString = gson.toJson(selectCharacter);

        return jsonString;

    }

    @RequestMapping(value = "saveCharacter", method = RequestMethod.POST)
    @ResponseBody
    public String SaveCharacter(HttpServletRequest req, Model model,
                                @RequestParam(value = "id") String uuid,
                                @RequestParam(value = "name") String name,
                                @RequestParam(value = "desc") String desc,
                                @RequestParam(value = "relationList") List<String> relation,
                                @RequestParam(value = "roleList") List<String> role,
                                @RequestParam(value = "attributeList") List<String> att,
                                @RequestParam(value = "sceneList") List<String> scene){
        String email = (String)req.getSession().getAttribute("email");

        String name1 = uuid;


        Story story = (Story)req.getSession().getAttribute("story");
        if (story == null){
            req.getSession().setAttribute("story", new Story());
        }
        story = (Story)req.getSession().getAttribute("story");

        List<Character> characterList = story.getCharacterList();
        Character editCharacter = new Character();

        List<Character> sss = characterList;

        List<Attribute> relationshipList = new ArrayList<Attribute>();
        List<Attribute> roleList = new ArrayList<Attribute>();
        List<Attribute> attributeList = new ArrayList<Attribute>();
        List<Attribute> sceneList = new ArrayList<Attribute>();

        if (characterList.size() != 0){

            for (int i=0; i< characterList.size(); i++){
                //check the uuid

                if (characterList.get(i).getId().equals(uuid)){


                    //cast to List<relationship>
                    for (String s : relation){
                        Attribute attribute = new Attribute();
                        attribute.setName(s);
                        relationshipList.add(attribute);
                    }

                    //cast to List<role>
                    for (String s : role){
                        Attribute attribute = new Attribute();
                        attribute.setName(s);
                        roleList.add(attribute);
                    }

                    //cast to List<attribute>
                    for (String s : att){
                        Attribute attribute = new Attribute();
                        attribute.setName(s);
                        attributeList.add(attribute);
                    }

                    //cast to List<scene>
                    for (String s : scene){
                        Attribute attribute = new Attribute();
                        attribute.setName(s);
                        sceneList.add(attribute);
                    }

                    //init the attributeList
                    Map<String, List<Attribute>> attributeFinalList = new HashMap<String, List<Attribute>>();
                    attributeFinalList.put("relationship",relationshipList);
                    attributeFinalList.put("role",roleList);
                    attributeFinalList.put("attribute",attributeList);
                    attributeFinalList.put("scene",sceneList);

                    //set the attributeList
                    editCharacter.setAttributeList(attributeFinalList);
                    characterList.get(i).setName(name);
                    characterList.get(i).setAttributeList(attributeFinalList);
                    characterList.get(i).setCharacterDescription(desc);

                    break;
                }
            }
        }
        if (uuid.equals("")){
            UUID id = UUID.randomUUID();
            editCharacter.setId(id.toString());
            editCharacter.setName(name);
            editCharacter.setCharacterDescription(desc);

            for (String s : relation){
                Attribute attribute = new Attribute();
                attribute.setName(s);
                relationshipList.add(attribute);
            }

            //cast to List<role>
            for (String s : role){
                Attribute attribute = new Attribute();
                attribute.setName(s);
                roleList.add(attribute);
            }

            //cast to List<attribute>
            for (String s : att){
                Attribute attribute = new Attribute();
                attribute.setName(s);
                attributeList.add(attribute);
            }

            //cast to List<scene>
            for (String s : scene){
                Attribute attribute = new Attribute();
                attribute.setName(s);
                sceneList.add(attribute);
            }

            //init the attributeList
            Map<String, List<Attribute>> attributeFinalList = new HashMap<String, List<Attribute>>();
            attributeFinalList.put("relationship",relationshipList);
            attributeFinalList.put("role",roleList);
            attributeFinalList.put("attribute",attributeList);
            attributeFinalList.put("scene",sceneList);

            //set the attributeList
            editCharacter.setAttributeList(attributeFinalList);
            editCharacter.setName(name);
            editCharacter.setCharacterDescription(desc);

            characterList.add(editCharacter);

        }


        Gson gson = new Gson();
        String jsonString = gson.toJson(characterList);

        return jsonString;
    }

    @RequestMapping("newCharacter")
    @ResponseBody
    public String NewCharacter(HttpServletRequest req, Model model){
        String email = (String)req.getSession().getAttribute("email");
        model.addAttribute("email",email);


        Story story = (Story)req.getSession().getAttribute("story");
        if (story == null){
            req.getSession().setAttribute("story", new Story());
        }
        story = (Story)req.getSession().getAttribute("story");
        UUID uuid = UUID.randomUUID();

        Character newCharacter = new Character();
        newCharacter.setId(uuid.toString());
        story.saveCharacter(newCharacter);

        Gson gson = new Gson();
        String jsonString = gson.toJson(story.getCharacterList());

        return jsonString;
    }
}
