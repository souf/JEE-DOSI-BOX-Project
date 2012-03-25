/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleurs;

import DAO.UserJpaController;
import java.util.Map;
import java.util.Vector;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import beans.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;

/**
 *
 * @author Soufiane
 */
public class Login {

    private User currentUser = null;
    FacesMessage message = new FacesMessage();
    @Resource
    UserTransaction utx;
    UserJpaController userJpa;
    @PersistenceUnit(unitName = "TP_FINALPU")
    EntityManagerFactory emf;

    public String login() {
        boolean trouve = false;

        //Return a mutable Map representing the request scope attributes for the current application.
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        Map map = context.getSessionMap();
        User user = (User) map.get("user");
        userJpa = new UserJpaController(utx, emf);
        List<User> users = userJpa.findUserEntities();
//        System.out.println("chui là");
//        User malika = new User();
//        malika.setId(Integer.SIZE);
//        malika.setEmail("gouchen.m@gmail.com");
//        malika.setMotDePasse("gouchma");
//        malika.setNom("GOUCHENE");
//        malika.setPrenom("hh");
//        try{
//                    userJpa.create(malika);
//                    System.out.println("hopla");
//
//        }catch(Exception e){
//            System.out.println(e.getMessage()+"hhhhhhhhhhhhhh");
//        }
        if (user != null) {           
            for (int i = 0; i < users.size(); i++) {
                User myUser = users.get(i);
                System.out.println(myUser.getNom());
                System.out.println(myUser.getMotDePasse());
                if (user.getMotDePasse().equals(myUser.getMotDePasse()) && user.getNom().equals(myUser.getNom())) {
                    currentUser = user;
                    user.setEmail(myUser.getEmail());
                    user.setId(myUser.getId());
                    trouve = true ;
                } 
            }
        }
        
        if(trouve){
            return "index";
            
        }else{
            return "login";
            
        }

        }
    

    public void logout() {
        currentUser = null;
        ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true)).removeAttribute("user");
    }

    public void test() {
        System.out.println("ceci est un test :) ");
    }

    public String deconnexion() {
        logout();
        return "index";
    }

    public boolean isLoggedIn() {

        if (currentUser != null) {
            return true;
        }
        return false;

    }

    public User getCurrent() {
        return this.currentUser;
    }

    public void SetCurrent(User user) {
        this.currentUser = user;
    }

    public void verifyAccess() {
        ExternalContext context2 = FacesContext.getCurrentInstance().getExternalContext();
        Map map = context2.getSessionMap();
        User user = (User) map.get("user");
        if (user == null) {
            try {
                context2.redirect("./faces/login.xhtml");
            } catch (IOException ex) {
            }
        } else {
            if (user.getId() != null) {
                try {
                    System.out.println(user.toString());
                    context2.redirect("./faces/profil.xhtml");
                } catch (IOException ex) {
                }

            } else {
                try {
                    context2.redirect("faces/login.xhtml");
                } catch (IOException ex) {
                }

            }


        }
    }

    public void lister() {
        //      List<beans.File> files = new ArrayList<beans.File> ();
        //     User user = (User) map.get("user");
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        Map map = context.getSessionMap();
        User user = (User) map.get("user");
        System.out.println("hello : " + user.getEmail());
        userJpa = new UserJpaController(utx, emf);
        List<beans.File> files = userJpa.findFilesUser(user.getId());
        System.out.println("tiraraaaa: " + files.size());

        //  return "listFiles" ;

    }
    
    
        
    public String inscription(){
         ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        Map map = context.getSessionMap();
        User user = (User) map.get("user");
        System.out.println(user.getEmail());
        System.out.println(user.getMotDePasse());
        System.out.println(user.getNom());
        user.setId(Integer.SIZE);
        userJpa = new UserJpaController(utx, emf);
        try{
            userJpa.create(user);
        }catch(Exception e){
            
        }
        return "index";
    }
}
