package fa.cpl_java_05.session;

import fa.cpl_java_05.model.user.UserModel;

public final class UserSession {

    private static UserSession instance;

    private static UserModel user;

    private UserSession(UserModel user){
        this.user = user;
    }

    public static UserSession setInstance(UserModel user){
        if(instance != null){
            instance = new UserSession(user);
        }
        return instance;
    }

    public static UserModel getUser(){
        return user;
    }

    public static void cleanSession(){
        user = null;
    }
}
