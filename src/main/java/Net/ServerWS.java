package Net;

import Domain.Arena;
import Domain.Character;
import Domain.GlobalUpdate;
import Domain.PartOfBody;
import com.google.gson.Gson;
import db.Login;
import db.Users;

import java.io.IOException;
import java.util.*;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/")
public class ServerWS   {

    private static Map<String,Session> sessionMap = Collections.synchronizedMap(new HashMap<String, Session>());
    private static Map<String,Character> characterMap = Collections.synchronizedMap(new HashMap<String, Character>());
    private static GlobalUpdate globalUpdate = new GlobalUpdate();
    private static Arena arena = new Arena();

    Gson gson = new Gson();
    Session session;
    Login login;


    @OnOpen
    public void onOpen(Session peer) throws IOException, InterruptedException {
        System.out.println("Open Connection ..." + peer);
        session = peer;
    }

    @OnClose
    public void onClose(){
        System.out.println("Close Connection ...");
    }

    @OnMessage
    public void onMessage(String message) throws IOException, InterruptedException {
       parsingMessage(message);

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
         if (comand[0].equals("RegToBattle"))
         {
             // 0 - команда, 1- номер боя 2- имя персонажа,3 куда бьет, 4 - что защищяет
             regToBattle(comand);
         }
         if (comand[0].equals("CancelRegBattle"))
         {
          cancelRegBattle(comand);
         }
     }


       public void verifyLogin(String[] comand){
             //  System.out.println("Сокеты: "+peers.size());
               login = new Login(comand[1],comand[2]);
               if (!comand[1].equals("null")){
                   if (login.loginIsPresent() && login.checkPass(comand[2])) {
                       sessionMap.put(login.getCharacter().getName(), session);

                       if (!characterMap.containsKey(login.getCharacter().getName())) {
                           characterMap.put(login.getCharacter().getName(), login.getCharacter());
                           System.out.println("Обновил в мапу чаров");
                           System.out.println(gson.toJson(characterMap.get(login.getCharacter().getName())));

                       }

                     //  sendMessage("login#1#" + gson.toJson(login.getCharacter())); //Код ошибки: 1 - отправка данных
                       System.out.println(characterMap.get(login.getCharacter()));
                       sendMessage("login#1#" + gson.toJson(characterMap.get(login.getCharacter().getName())));


                   }
                   else
                       sendMessage("login#2"); //Код ошибки 2 - Неверный логин и парольм
               }
               else if (comand[1].equals("null")) {
                   login.createNewUser();
                   sessionMap.put(login.getCharacter().getName(), session);
                   if (!characterMap.containsKey(login.getCharacter().getName()))
                   characterMap.put(login.getCharacter().getName(),login.getCharacter());
                   sendMessage("login#"+login.getUser().getLogin()+"#"+gson.toJson(login.getCharacter()));
                   sendMessage("login#1#"+gson.toJson(login.getCharacter()));

               }
       }
       public  void updateBattle(String[] comand ){
           Long number = Long.valueOf(comand[1]);
           String name = comand[2];
           PartOfBody wereHit = PartOfBody.valueOf(comand[3]);
           PartOfBody whatDef = PartOfBody.valueOf(comand[4]);

           if (arena.getBattleList().get(Long.valueOf(number)).getPlayer1().getName().equals(name)){
               //Мыпервый игрок
               arena.getBattleList().get(number).setPlayer1Hit(wereHit);
               arena.getBattleList().get(number).setPlayer1Def(whatDef);
               arena.getBattleList().get(number).setPlayer1IsReady(true);

           }
           else if (arena.getBattleList().get(Long.valueOf(number)).getPlayer2().getName().equals(name)) {
               arena.getBattleList().get(number).setPlayer2Hit(wereHit);
               arena.getBattleList().get(number).setPlayer2Def(whatDef);
               arena.getBattleList().get(number).setPlayer2IsReady(true);
              }

       }

       public void regToBattle(String[] comand){
           arena.addToArena(login.getCharacter());
           System.out.println("Зареган на батл");
           sendMessage("RegisteredInBattle#true");


           Character character = new Character();
           character.setName("Bot");
           character.setHP(20);
           character.setStrength(1);
           character.setLvl(1);
           characterMap.put(character.getName(), character);
           arena.addToArena(character);


       }

    public void cancelRegBattle(String[] comand){
        arena.cancelRegBattle(login.getCharacter());
        System.out.println("Рег на батл отменен");
        sendMessage("RegisteredInBattle#false");

    }


    public static GlobalUpdate getGlobalUpdate() {
        return globalUpdate;
    }

    public static void setGlobalUpdate(GlobalUpdate globalUpdate) {
        ServerWS.globalUpdate = globalUpdate;
    }

    public static Map<String, Character> getCharacterMap() {
        return characterMap;
    }

    public static void setCharacterMap(Map<String, Character> characterMap) {
        ServerWS.characterMap = characterMap;
    }

    public static Map<String, Session> getSessionMap() {
        return sessionMap;
    }

    public static void setSessionMap(Map<String, Session> sessionMap) {
        ServerWS.sessionMap = sessionMap;
    }

    public ServerWS getSoket(){
           return this;
    }
}
