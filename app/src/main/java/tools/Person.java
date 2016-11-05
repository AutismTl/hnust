package tools;

/**
 * Created by gesangdianzi on 2016/10/27.
 */
public class Person {
    private String name;
    private int id;
    private String imageId;


    public Person(String name,int id,String imageId){
         this.name=name;
         this.id=id;
         this.imageId=imageId;
    }
    public String getImageId(){
        return imageId;
    }
    public String getName(){
        return name;
    }
    public int getId(){
        return id;
    }
}
