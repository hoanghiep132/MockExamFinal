package fa.cpl_java_05.session;

import fa.cpl_java_05.entities.user.User;

public final class UserSession {

    private static UserSession instance;

    private static User user;

    private UserSession(User user){
        this.user = user;
    }

    public static UserSession setInstance(User user){
        if(instance != null){
            instance = new UserSession(user);
        }
        return instance;
    }

    public static User getUser(){
        return user;
    }

    public static void cleanSession(){
        user = null;
    }
}
