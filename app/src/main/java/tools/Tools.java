package tools;

/**
 * Created by gesangdianzi on 2016/10/25.
 */


public class Tools {

    public static int loginGrade(String admin,String password,int grade){
        if(grade==1&&"tl".equals(admin)&&"password".equals(password)){
            return 1;
        }else if(grade==2&&"tl".equals(admin)&&"password".equals(password)){
            return 2;
        }else if(grade==3&&"tl".equals(admin)&&"password".equals(password)){
            return 3;
        }else if("admin".equals(admin)&&"password".equals(password)){
            return 0;
        }else{
            return 4;
        }
    }
}
