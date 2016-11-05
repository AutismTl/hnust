package tools;

/**
 * Created by gesangdianzi on 2016/10/25.
 */


public class Tools {

    public static int loginGrade(String admin,String password,int grade){
        if(grade==1&&"admin".equals(admin)&&"password".equals(password)){
            return 1;
        }else if(grade==2&&"admin".equals(admin)&&"password".equals(password)){
            return 2;
        }else if(grade==3&&"admin".equals(admin)&&"password".equals(password)){
            return 3;
        }else{
            return 0;
        }
    }
}
