package beans;

import beans.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2012-03-25T19:35:25")
@StaticMetamodel(File.class)
public class File_ { 

    public static volatile SingularAttribute<File, Integer> id;
    public static volatile SingularAttribute<File, Double> taille;
    public static volatile SingularAttribute<File, User> idUser;
    public static volatile SingularAttribute<File, String> nom;

}