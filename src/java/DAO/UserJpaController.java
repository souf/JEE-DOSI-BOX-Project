/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import beans.User;
import java.util.ArrayList;
import java.util.List;
import beans.File;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author souf
 */
public class UserJpaController implements Serializable {

    public UserJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(User user) throws RollbackFailureException, Exception {
        if (user.getUserList() == null) {
            user.setUserList(new ArrayList<User>());
        }
        if (user.getUserList1() == null) {
            user.setUserList1(new ArrayList<User>());
        }
        if (user.getFileList() == null) {
            user.setFileList(new ArrayList<File>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<User> attachedUserList = new ArrayList<User>();
            for (User userListUserToAttach : user.getUserList()) {
                userListUserToAttach = em.getReference(userListUserToAttach.getClass(), userListUserToAttach.getId());
                attachedUserList.add(userListUserToAttach);
            }
            user.setUserList(attachedUserList);
            List<User> attachedUserList1 = new ArrayList<User>();
            for (User userList1UserToAttach : user.getUserList1()) {
                userList1UserToAttach = em.getReference(userList1UserToAttach.getClass(), userList1UserToAttach.getId());
                attachedUserList1.add(userList1UserToAttach);
            }
            user.setUserList1(attachedUserList1);
            List<File> attachedFileList = new ArrayList<File>();
            for (File fileListFileToAttach : user.getFileList()) {
                fileListFileToAttach = em.getReference(fileListFileToAttach.getClass(), fileListFileToAttach.getId());
                attachedFileList.add(fileListFileToAttach);
            }
            user.setFileList(attachedFileList);
            em.persist(user);
            for (User userListUser : user.getUserList()) {
                userListUser.getUserList().add(user);
                userListUser = em.merge(userListUser);
            }
            for (User userList1User : user.getUserList1()) {
                userList1User.getUserList().add(user);
                userList1User = em.merge(userList1User);
            }
            for (File fileListFile : user.getFileList()) {
                User oldIdUserOfFileListFile = fileListFile.getIdUser();
                fileListFile.setIdUser(user);
                fileListFile = em.merge(fileListFile);
                if (oldIdUserOfFileListFile != null) {
                    oldIdUserOfFileListFile.getFileList().remove(fileListFile);
                    oldIdUserOfFileListFile = em.merge(oldIdUserOfFileListFile);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(User user) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            User persistentUser = em.find(User.class, user.getId());
            List<User> userListOld = persistentUser.getUserList();
            List<User> userListNew = user.getUserList();
            List<User> userList1Old = persistentUser.getUserList1();
            List<User> userList1New = user.getUserList1();
            List<File> fileListOld = persistentUser.getFileList();
            List<File> fileListNew = user.getFileList();
            List<String> illegalOrphanMessages = null;
            for (File fileListOldFile : fileListOld) {
                if (!fileListNew.contains(fileListOldFile)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain File " + fileListOldFile + " since its idUser field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<User> attachedUserListNew = new ArrayList<User>();
            for (User userListNewUserToAttach : userListNew) {
                userListNewUserToAttach = em.getReference(userListNewUserToAttach.getClass(), userListNewUserToAttach.getId());
                attachedUserListNew.add(userListNewUserToAttach);
            }
            userListNew = attachedUserListNew;
            user.setUserList(userListNew);
            List<User> attachedUserList1New = new ArrayList<User>();
            for (User userList1NewUserToAttach : userList1New) {
                userList1NewUserToAttach = em.getReference(userList1NewUserToAttach.getClass(), userList1NewUserToAttach.getId());
                attachedUserList1New.add(userList1NewUserToAttach);
            }
            userList1New = attachedUserList1New;
            user.setUserList1(userList1New);
            List<File> attachedFileListNew = new ArrayList<File>();
            for (File fileListNewFileToAttach : fileListNew) {
                fileListNewFileToAttach = em.getReference(fileListNewFileToAttach.getClass(), fileListNewFileToAttach.getId());
                attachedFileListNew.add(fileListNewFileToAttach);
            }
            fileListNew = attachedFileListNew;
            user.setFileList(fileListNew);
            user = em.merge(user);
            for (User userListOldUser : userListOld) {
                if (!userListNew.contains(userListOldUser)) {
                    userListOldUser.getUserList().remove(user);
                    userListOldUser = em.merge(userListOldUser);
                }
            }
            for (User userListNewUser : userListNew) {
                if (!userListOld.contains(userListNewUser)) {
                    userListNewUser.getUserList().add(user);
                    userListNewUser = em.merge(userListNewUser);
                }
            }
            for (User userList1OldUser : userList1Old) {
                if (!userList1New.contains(userList1OldUser)) {
                    userList1OldUser.getUserList().remove(user);
                    userList1OldUser = em.merge(userList1OldUser);
                }
            }
            for (User userList1NewUser : userList1New) {
                if (!userList1Old.contains(userList1NewUser)) {
                    userList1NewUser.getUserList().add(user);
                    userList1NewUser = em.merge(userList1NewUser);
                }
            }
            for (File fileListNewFile : fileListNew) {
                if (!fileListOld.contains(fileListNewFile)) {
                    User oldIdUserOfFileListNewFile = fileListNewFile.getIdUser();
                    fileListNewFile.setIdUser(user);
                    fileListNewFile = em.merge(fileListNewFile);
                    if (oldIdUserOfFileListNewFile != null && !oldIdUserOfFileListNewFile.equals(user)) {
                        oldIdUserOfFileListNewFile.getFileList().remove(fileListNewFile);
                        oldIdUserOfFileListNewFile = em.merge(oldIdUserOfFileListNewFile);
                    }
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = user.getId();
                if (findUser(id) == null) {
                    throw new NonexistentEntityException("The user with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            User user;
            try {
                user = em.getReference(User.class, id);
                user.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The user with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<File> fileListOrphanCheck = user.getFileList();
            for (File fileListOrphanCheckFile : fileListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the File " + fileListOrphanCheckFile + " in its fileList field has a non-nullable idUser field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<User> userList = user.getUserList();
            for (User userListUser : userList) {
                userListUser.getUserList().remove(user);
                userListUser = em.merge(userListUser);
            }
            List<User> userList1 = user.getUserList1();
            for (User userList1User : userList1) {
                userList1User.getUserList().remove(user);
                userList1User = em.merge(userList1User);
            }
            em.remove(user);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<User> findUserEntities() {
        return findUserEntities(true, -1, -1);
    }

    public List<User> findUserEntities(int maxResults, int firstResult) {
        return findUserEntities(false, maxResults, firstResult);
    }

    private List<User> findUserEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(User.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public User findUser(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }

    public int getUserCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<User> rt = cq.from(User.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    //***********************

    public List<beans.File> findFilesUser(Integer idUser) {
        EntityManager em = getEntityManager();
        List<File> liste = (List<File>) new ArrayList<beans.File>();
        try {

            String queryString = "SELECT f FROM File f WHERE f.idUser =" + idUser;
            Query query = em.createQuery(queryString);
            liste = query.getResultList();
            for (File f : liste) {
                System.out.println("holaa");
                System.out.println(f.getIdUser() + "<br>");
            }
            em.close();
            return liste;
        }
        catch(Exception e){
        System.out.println("erreur ici ");
        }
        finally {
            return liste;
        }

    }
}
