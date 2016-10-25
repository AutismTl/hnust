package tools;

/**
 * Created by gesangdianzi on 2016/10/25.
 */
public class Tools {

    public static int registerLibrary(String admin,String password){
        if("admin".equals(admin)&&"password".equals(password))
            return 1;
        else
            return 0;
    }

    public static int loginGrade(String admin,String password){
        if(registerLibrary(admin,password)==1){
            return 1;
        }else if(registerLibrary(admin,password)==2){
            return 2;
        }else if(registerLibrary(admin,password)==3){
            return 3;
        }else{
            return 0;
        }
    }
}
