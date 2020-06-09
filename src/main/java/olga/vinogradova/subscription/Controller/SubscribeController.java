package olga.vinogradova.subscription.Controller;

import olga.vinogradova.subscription.Model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

@Controller
public class SubscribeController {

public List<User> emailList = new ArrayList<>();
    Dictionary<String, String> all = new Hashtable();

    @GetMapping("/subscribe")
    public String showForm( Model model) {
        User user = new User();
        model.addAttribute("user", user);

       return "add_form";
    }

    @PostMapping("/subscribe")
    public String submitForm(@ModelAttribute("user") User user)
    {
        Boolean unique = true;
        for(int i = 0; i < emailList.size(); i++)
        {
            if (emailList.get(i).getEmail().equals(user.getEmail())){
                unique = false;

            } break;
        }
        if (unique && !user.getEmail().isEmpty()){
            emailList.add(user);
            return "success";
        }
        else return "add_form";
    }

    @RequestMapping("/list")
    @ResponseBody
    public String showAll(){


        for(int j = 0; j < emailList.size(); j++)
        {
            all.put(emailList.get(j).getName(),emailList.get(j).getEmail());

        }
        return all.toString();
    }

    @GetMapping("/unsubscribe")
    public String unsubscribeForm(Model model1) {
        User old_user = new User();

       model1.addAttribute("old_user", old_user);

        return "remove_form";
    }

    @PostMapping("/unsubscribe")

    @ResponseBody
    public String unsubscribed(@ModelAttribute("old_user") User old_user){
        for(int j = 0; j < emailList.size(); j++)
        {
            User obj = emailList.get(j);

            if(obj.getEmail().equals(old_user.getEmail())){
                old_user = obj;
                emailList.remove(j);

            }
        }
        return  old_user.getEmail() + " has been unsubscribed! " +
                old_user.getName() + ", we are sorry to see you go... =( ";

    }

}
