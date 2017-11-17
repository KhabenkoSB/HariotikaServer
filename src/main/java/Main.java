import Domain.Arena;
import Domain.Battle;
import Domain.Character;
import Domain.PartOfBody;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import db.HibernateUtil;
import db.HibernateUtil2;
import db.Login;
import db.Users;
import org.hibernate.Session;

import javax.ws.rs.HEAD;

public class Main {
    public static void main(String[] args) throws InterruptedException {

       Arena arena = new Arena();
        Character character = new Character("Maka","Maka");
        Character character2 = new Character("Maka","Maka");
        Character character3 = new Character("Maka","Maka");
        Character character4 = new Character("Maka","Maka");

     character.setLvl(1);
        arena.addToArena(character);
        character.setName("Maka2");
        arena.addToArena(character);

        System.out.println(arena.getCharQueue().get(1));


        Battle battle = new Battle(1,character,character);
        Gson gson = new Gson();
        Character character1 = new Character();
        character.setLvl(1);
        character.setHP(3);
        character.setStrength(1);
        character.setArmor(1);


         gson = new Gson();

        System.out.println(character.getName());
        System.out.println(gson.toJson(battle.getPlayer1()));
        System.out.println(gson.toJson(battle.getPlayer2()));

        System.out.println(gson.toJson(battle));



   /*


        System.out.println("Maven + Hibernate + PgSQL");
        Session session = HibernateUtil2.getSessionFactory().openSession();
      //  Login login = new Login("Maka","2");
        Users user = new Users();
        user.setLogin("Maka2");
        session.beginTransaction();
        session.saveOrUpdate(user);
        session.getTransaction().commit();
        Login login = new Login("Serg","1");
        //login.start();
//        login.save();
//        System.out.println(login.login());
       /*
        Session session = HibernateUtil.getSessionFactory().openSession();


        User user = new  User("Maka","2");
   //     User user2 = new  User("Maka3","4");

        String hql = "from User where login = '"+user.getLogin()+"'";
        Query query = session.createQuery(hql);

        if (query.list().isEmpty()) {
            session.beginTransaction();
            System.out.println("++++++++++++++Логин не существует++++++++++++++");
            session.saveOrUpdate(user);
            session.getTransaction().commit();
           // session.close();
        }


        for (int i = 0; i <20 ; i++) {
            Thread.sleep(3000);
            user.setPass(String.valueOf(i));
            System.out.println(user.getLogin());
            System.out.println(user.getPass());
            session.beginTransaction();
            session.saveOrUpdate(user);
            session.getTransaction().commit();

        }




    //  session.close();
*/
    }

}

