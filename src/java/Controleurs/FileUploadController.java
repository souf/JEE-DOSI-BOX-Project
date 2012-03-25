/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleurs;

/**
 *
 * @author ayassin
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@ManagedBean(name = "fileUploadController")
@SessionScoped
public class FileUploadController {

    private String filedestination;

    public void handleFileUpload(FileUploadEvent event) throws FileNotFoundException, IOException {
        System.out.println("je suis là");
        System.out.println("Uploaded: " + event.getFile().getFileName());

        // FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");  
        // FacesContext.getCurrentInstance().addMessage(null, msg); 

          //String ;
        FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage("teste", msg);

        byte[] conteudo = event.getFile().getContents();
        
        //String path = System.getProperty("user.dir" );
        
       String path=new File("").getAbsolutePath();
        System.out.println("path:"+ path);
        filedestination = path+"\\yassin-" + event.getFile().getFileName();
        System.out.println("slt:"+ filedestination);
        FileOutputStream fos = new FileOutputStream(filedestination);
        fos.write(conteudo);
        fos.close();

    }

    public String getFiledestination() {
        return filedestination;
    }

    public void setFiledestination(String filedestination) {
        this.filedestination = filedestination;
    }

    


}
