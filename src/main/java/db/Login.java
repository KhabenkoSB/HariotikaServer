package db;


import Domain.Character;
import Net.ServerWS;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;


public class Login  {

    private Session session;
    private Users user;
    private Character character;


    public Login(String userLogin, String pass) {

       this.session = HibernateUtil2.getSessionFactory().openSession();;
       this.user = new Users();
       this.user.setLogin(userLogin);
       this.user.setPass(pass);
    }
/*
    @Override
    public void run() {
        session = HibernateUtil2.getSessionFactory().openSession();
        session.beginTransaction();
        session.saveOrUpdate(user);
        session.getTransaction().commit();
    }

*/
    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }


    public  boolean loginIsPresent(){
        System.out.println(user.getLogin());
        String hql = "from Users where login = '"+user.getLogin()+"'";
        Query query = session.createQuery(hql);
        boolean isLogin = query.list().isEmpty() ;
             return !isLogin;
    }

    public  boolean checkPass(String pass){
        String hql = "from Users where login = '"+user.getLogin()+"'";
        Query query = session.createQuery(hql);
        List<Users> users = query.list();
        if (loginIsPresent()) {
           user = users.get(0);
            System.out.println("++++++++++++++Логин верифицирован++++++++++++++");
            if (user.getPass().toString().equals(pass)) {
                System.out.println("++++++++++++++Пароль верифицирован++++++++++++++");
                hql = "from Character WHERE login='"+user.getLogin()+"'";
                query = session.createQuery(hql);
                List<Character> characters =  query.list();
                character=characters.get(0);
                return true;
            }
            else
                return false;

        }

        return false;
    }


    public void save(Object object){
        session.beginTransaction();
        session.saveOrUpdate(object);
        session.getTransaction().commit();
    }

    public  void createNewUser(){
        String hql = "from Users";
        Query query = session.createQuery(hql);
        user.setLogin("Player"+query.list().size());
        user.setPass("null");
        save(user);
        createNewChar();


    }

    public  void createNewChar(){
        character = new Character(user.getLogin(),user.getLogin());
        character.setLvl(1);
        character.setHP(20);
        character.setStrength(1);
        character.setArmor(1);
        save(character);

    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }


}
