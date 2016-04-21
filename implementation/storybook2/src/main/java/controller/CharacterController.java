package controller;

import com.google.gson.Gson;
import model.Attribute;
import model.Character;
import model.MainSummary;
import model.Story;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.portlet.ModelAndView;
import sun.applet.Main;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by liuwei on 2016-03-05.
 */
@Controller
public class CharacterController {

    @RequestMapping(value = {"/{sId}/character.form","character.form"})
    public String Character(HttpServletRequest req, Model model){
        String email = (String)req.getSession().getAttribute("email");
        req.setAttribute("email",email);

        Story story = (Story)req.getSession().getAttribute("story");
        if (story == null){
            req.getSession().setAttribute("story", new Story());
        }
        story = (Story)req.getSession().getAttribute("story");

        MainSummary m = story.getSummary();
        req.setAttribute("summaryList",m.getSummaryList());
        req.setAttribute("projectTitle",story.getTitle());

        String t = "<img src='/Ustoria/resources/img/default-character-image.png' alt='character1' class='img-thumbnail' width='80' height='80' />";

        for (int i =0;i<story.getCharacterList().size();i++){
            t += t;
        }
        req.setAttribute("pageName","Character");
        req.setAttribute("characterList",story.getCharacterList());
        return "character";

    }

    @RequestMapping(value = {"{sId}/selectCharacter","selectCharacter"})
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

    @RequestMapping(value = {"{sId}/saveCharacter","saveCharacter"}, method = RequestMethod.POST)
    @ResponseBody
    public String SaveCharacter(HttpServletRequest req, Model model,
                                @RequestParam(value = "id") String uuid,
                                @RequestParam(value = "name") String name,
                                @RequestParam(value = "desc") String desc,
                                @RequestParam(value = "relationList") List<String> relation,
                                @RequestParam(value = "roleList") List<String> role,
                                @RequestParam(value = "attributeList") List<String> att,
                                @RequestParam(value = "sceneList") List<String> scene){


        Story story = (Story)req.getSession().getAttribute("story");
        if (story == null){
            req.getSession().setAttribute("story", new Story());
        }
        story = (Story)req.getSession().getAttribute("story");

        List<Character> characterList = story.getCharacterList();
        Character editCharacter = new Character();


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

        System.out.print(jsonString);

        return jsonString;
    }

    @RequestMapping(value = {"{sId}/newCharacter","newCharacter"})
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

    @RequestMapping("{sId}/loadCharacter")
    public String loadCharacter(HttpServletRequest req, Model model){

        String id = req.getParameter("id");
        Story story = (Story)req.getSession().getAttribute("story");
        List<Character> list = story.getCharacterList();
        Character character = new Character();

        for (int i = 0; i<list.size(); i++){
            if (list.get(i).getId().equals(id))
                character = list.get(i);
        }
        model.addAttribute("pageName","Character");

        model.addAttribute("loadCharacter",character);
        model.addAttribute("characterList",story.getCharacterList());
        MainSummary m = story.getSummary();
        model.addAttribute("summaryList",m.getSummaryList());

        String name = character.getName();

        return "character";

    }

    @RequestMapping(value = "test", method = RequestMethod.POST)
    @ResponseBody
    public String test(HttpServletRequest req, @RequestParam(value = "id") String json){


        JSONObject jsonObject = new JSONObject(json);

        Iterator it = jsonObject.keys();

        String s = "s";
        while (it.hasNext()){
            String key = (String) it.next();
            Object o = jsonObject.get(key);
            System.out.println("key: "+ key);
            System.out.println("O: " + o);
        }

        Gson gson = new Gson();
        String jsonString = gson.toJson(s);

        return jsonString;

    }
}
