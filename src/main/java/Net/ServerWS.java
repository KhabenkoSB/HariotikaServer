package Net;

import Domain.Character;
import com.google.gson.Gson;
import db.Login;
import db.Users;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/")
public class ServerWS   {

    private static Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());
    Session session;
    Login login;

    @OnOpen
    public void onOpen(Session peer) throws IOException, InterruptedException {
        System.out.println("Open Connection ..." + peer);
        peers.add(peer);
        session = peer;


    }
    @OnClose
    public void onClose(){
        System.out.println("Close Connection ...");
    }

    @OnMessage
    public String onMessage(String message) throws IOException, InterruptedException {
       parsingMessage(message);

        return null;
    }

    @OnError
    public void onError(Throwable e){
        e.printStackTrace();
    }


    public void sendMessage(String message) {
        this.session.getAsyncRemote().sendText(message);
    }

    public String getComand(String message) {
        String[] mass = message.split("#");
        return mass[0];
    }


     public  void parsingMessage(String message)
     {
         String[] comand = message.split("#");
         System.out.println(message);

         if (comand[0].equals("login"))
         {
             System.out.println("Сокеты: "+peers.size());
           login = new Login(comand[1],comand[2]);
           if (!comand[1].equals("null")){
             if (login.loginIsPresent() && login.checkPass(comand[2]))
                   sendMessage("login#1");
               else
                   sendMessage("login#2");
           }
           else if (comand[1].equals("null")) {
               login.createNewUser();
               Character character =  new Character("Maka", login.getUser().getLogin());
               System.out.println(character.getLogin());
               System.out.println(character.getName());
               login.getSession().beginTransaction();
               login.getSession().saveOrUpdate(character);
               login.getSession().getTransaction().commit();
               sendMessage("login#"+login.getUser().getLogin());
               Gson gson = new Gson();
               sendMessage(gson.toJson(character));

           }
         }
       }


}
