package controller;

import dao.userDAO;
import dao.userDAOImpl;
import entity.UsersEntity;
import model.Attribute;
import model.Character;
import model.Story;
import org.hibernate.bytecode.buildtime.spi.Logger;
import org.json.simple.JSONObject;
import org.springframework.context.annotation.Role;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.ModelAndView;
import sun.security.provider.MD5;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;


@Controller
public class UserController {

    ArrayList<String> sidebarList = new ArrayList<String>();
    ArrayList<String> thumbnailList = new ArrayList<String>();
    int listNum = 0;

    @RequestMapping("login")
    public String Login(HttpServletRequest req, Model model){

        String email = req.getParameter("email-login");

        String password = req.getParameter("password-login");

        String em = (String)req.getSession().getAttribute("email");

        //User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
        } else {
            String username = principal.toString();
        }

        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        if (em == null)
            req.getSession().setAttribute("email",name);

        model.addAttribute("email",email);

        return "home";

    }

    @RequestMapping("/signup.form")
    public String SignUp(HttpServletRequest req, Model model){


        String fname = req.getParameter("fname");

        String lname = req.getParameter("lname");

        String email = req.getParameter("email-signup");

        String password = req.getParameter("password-signup");

        model.addAttribute("email",email);

        return "home";

    }


    @RequestMapping("character.form")
    public String Character(HttpServletRequest req, Model model){
        String email = (String)req.getSession().getAttribute("email");
        model.addAttribute("email",email);

        return "character";
    }

    @RequestMapping("/playground.form")
    public String Playground(HttpServletRequest req, Model model){
        String email = (String)req.getSession().getAttribute("email");
        model.addAttribute("email",email);
        return "playground";
    }

    @RequestMapping("/saveCharacter.form")
    public String SaveCharacter(HttpServletRequest req, Model model){

        String email = (String)req.getSession().getAttribute("email");
        model.addAttribute("email",email);

        Story story = (Story)req.getSession().getAttribute("story");
        if (story == null){
            req.getSession().setAttribute("story", new Story());
        }
        story = (Story)req.getSession().getAttribute("story");

        List<Character> characterList = story.getCharacterList();
        Character editCharacter = new Character();

        if (characterList.size() != 0){
            for (Character temp : characterList){
                if (temp.getId().equals(""))
                    editCharacter = temp;
            }
        }


        String save = req.getParameter("save");
        if (save!=null&&save.equals("Save")){
            String name = req.getParameter("name");
            String desc = req.getParameter("desc");
            String[] Relaatts = req.getParameterValues("relaAtt");
            String[] Roleatts = req.getParameterValues("roleAtt");
            String[] Attatts = req.getParameterValues("attAtt");
            String[] SceneAtts = req.getParameterValues("sceneAtt");

            List<Attribute> relationList = new ArrayList<Attribute>();
            List<Attribute> roleList = new ArrayList<Attribute>();
            List<Attribute> attList = new ArrayList<Attribute>();
            List<Attribute> sceneList = new ArrayList<Attribute>();

            if (Relaatts != null){
                for (String realatt : Relaatts){
                    Attribute temp = new Attribute();
                    temp.setName(realatt);
                    relationList.add(temp);
                }
            }


            if (Roleatts != null){
                for (String role : Roleatts){
                    Attribute temp = new Attribute();
                    temp.setName(role);
                    roleList.add(temp);
                }
            }

            if (Attatts != null){
                for (String att : Attatts){
                    Attribute temp = new Attribute();
                    temp.setName(att);
                    attList.add(temp);
                }
            }

            if (SceneAtts != null){
                for (String scene : SceneAtts){
                    Attribute temp = new Attribute();
                    temp.setName(scene);
                    sceneList.add(temp);
                }
            }


            Map<String, List<Attribute>> attlist = new HashMap<String, List<Attribute>>();
            attlist.put("relationList",relationList);
            attlist.put("roleList",roleList);
            attlist.put("attList",attList);
            attlist.put("sceneList",sceneList);


            String sidebar = "<li><a href='#'>"+name+"</a></li>";
            String thumbnail = "<img src='storybook/resources/img/default-character-image.png' alt=''character' class='img-thumbnail' width='80' height='80' />";

            model.addAttribute("sidebar",sidebar);
            model.addAttribute("thumbnail",thumbnail);

            editCharacter.setName(name);
            editCharacter.setCharacterDescription(desc);
            editCharacter.setAttributeList(attlist);

//            story.addCharacter(getCharacter);

        }


        return "character";
    }

    @RequestMapping("/newCharacter.form")
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
        model.addAttribute("uuid",uuid.toString());

        story.addCharacter(newCharacter);

        return "character";
    }







}
