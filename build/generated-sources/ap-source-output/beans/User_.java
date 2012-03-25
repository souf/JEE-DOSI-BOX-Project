package beans;

import beans.File;
import beans.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2012-03-25T19:35:25")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile SingularAttribute<User, Integer> id;
    public static volatile SingularAttribute<User, String> prenom;
    public static volatile ListAttribute<User, File> fileList;
    public static volatile SingularAttribute<User, String> email;
    public static volatile ListAttribute<User, User> userList1;
    public static volatile SingularAttribute<User, String> motDePasse;
    public static volatile ListAttribute<User, User> userList;
    public static volatile SingularAttribute<User, String> nom;

}