/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package REST;

import beans.User;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;

/**
 *
 * @author souf
 */
@Stateless
@Path("users")
public class UserFacadeREST extends AbstractFacade<User> {
    @PersistenceContext(unitName = "TP_FINALPU")
    private EntityManager em;

    public UserFacadeREST() {
        super(User.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(User entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(User entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public User find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<User> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<User> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @java.lang.Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
      //methode ajouter  par  yassin et amina 
    
    @GET
    @Path("user-{nom}")
    @Produces({"application/xml", "application/json"})
    public List<User> findByName(@PathParam("nom") String nom) {
                
        List<User> listUsers = new ArrayList<User>();
        //System.out.println("searchHotelsByCity");
        for (User current : super.findAll()) {
            if (current.getNom().equals(nom)) {
                listUsers.add(current);
            }
        }
        return listUsers;          
    }
    
    @GET
    @Path("user-{nom}-{prenom}")
    @Produces({"application/xml", "application/json"})
    public User findByNamePrenom(@PathParam("nom") String nom , @PathParam("prenom") String prenom) {
        User   user=null;       
        //System.out.println("searchHotelsByCity");
        for (User current : super.findAll()) {
            if (current.getNom().equals(nom) && current.getPrenom().equals(prenom)) {
                user=current;
            }
        }
        return user;          
    }
    
    
    @DELETE
    @Path("user-{nom}")
    public void removeByName(@PathParam("nom") String nom) {
        
       for (User current : super.findAll()) {
            if (current.getNom().equals(nom)) {
                super.remove(super.find(current.getId()));
            }
        }
       // super.remove(super.find(id));
    }
    //fin de mes methodes 
}
