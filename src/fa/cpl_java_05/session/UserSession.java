package fa.cpl_java_05.session;

import fa.cpl_java_05.model.user.UserModel;

public final class UserSession {

    private static UserSession instance;

    private  UserModel user;

    private UserSession(UserModel user){
        this.user = user;
    }

    private UserSession(){
    }

    public static UserSession setInstance(UserModel user){
        if(instance == null){
            instance = new UserSession(user);
        }
        return instance;
    }

    public static UserSession getInstance(){
        if(instance == null){
            instance = new UserSession();
        }
        return instance;
    }
    public  UserModel getUser(){
        return user;
    }

    public  void cleanSession(){
        user = null;
    }
}
