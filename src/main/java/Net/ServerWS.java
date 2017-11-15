package Net;

import Domain.Arena;
import Domain.Character;
import Domain.PartOfBody;
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
    private static Arena arena = new Arena();
    Gson gson = new Gson();
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


     public  void parsingMessage(String message) {
         String[] comand = message.split("#");
         System.out.println(message);
         if (comand[0].equals("login")) {
             verifyLogin(comand);
         }
         if (comand[0].equals("Battle"))
         {
             updateBattle(comand);
         }
     }


       public void verifyLogin(String[] comand){
               System.out.println("Сокеты: "+peers.size());
               login = new Login(comand[1],comand[2]);
               if (!comand[1].equals("null")){
                   if (login.loginIsPresent() && login.checkPass(comand[2]))
                       sendMessage("login#1#"+gson.toJson(login.getCharacter())); //Код ошибки: 1 - отправка данных
                   else
                       sendMessage("login#2"); //Код ошибки 2 - Неверный логин и пароль
               }
               else if (comand[1].equals("null")) {
                   login.createNewUser();
                   sendMessage("login#"+login.getUser().getLogin()+"#"+gson.toJson(login.getCharacter()));

               }
       }
       public  void updateBattle(String[] comand ){
           String number = comand[1];
           String name = comand[2];
           PartOfBody wereHit = PartOfBody.valueOf(comand[3]);
           PartOfBody whatDef = PartOfBody.valueOf(comand[4]);

       }



}
