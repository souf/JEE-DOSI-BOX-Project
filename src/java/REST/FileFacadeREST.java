/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package REST;

import beans.File;
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
@Path("files")
public class FileFacadeREST extends AbstractFacade<File> {
    @PersistenceContext(unitName = "TP_FINALPU")
    private EntityManager em;

    public FileFacadeREST() {
        super(File.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(File entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(File entity) {
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
    public File find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<File> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<File> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    
    // methode par yassin et amina
    //par id user
    @GET
    @Path("file-{id}")
    @Produces({"application/xml", "application/json"})
    public List<File> findByIdUser(@PathParam("id") Integer id) {
        
     List<File> listFiles = new ArrayList<File>();
        //System.out.println("searchHotelsByCity");
        for (File current : super.findAll()) {
            if (current.getIdUser().getId()==id) {
                listFiles.add(current);
            }
        }
        return listFiles;
    }
    
    
    @GET
    @Path("tailleFile-{id}")
    @Produces({"application/xml", "application/json"})
    public double tailleByIdUser(@PathParam("id") Integer id) {
        double taille=0;
     //List<File> listFiles = new ArrayList<File>();
        //System.out.println("searchHotelsByCity");
        for (File current : super.findAll()) {
            if (current.getIdUser().getId()==id) {
               taille=taille+current.getTaille();
            }
        }
        return taille;
    }
    
    
    //fin de methodes de yassin et amina
    
}
