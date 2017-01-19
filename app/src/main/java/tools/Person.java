package tools;

/**
 * Created by gesangdianzi on 2016/10/27.
 */
public class Person {
    private String name;
    private int id;
    private String imageId;
    private String address;
    private String sex;
    private String number;
    private String qq;
    private String remarks;
    private String home;
    private String phone;

    public Person(String name,int id,String imageId,String address,String sex,String number,String qq,String remarks,String home,String phone){
         this.name=name;
         this.id=id;
         this.home=home;
         this.phone=phone;
         this.imageId=imageId;
         this.address=address;
         this.sex=sex;
         this.number=number;
         this.qq=qq;
         this.remarks=remarks;
    }
    public String getHome(){
        return home;
    }
    public String getPhone() {
        return phone;
    }
    public String getRemarks() {
        return remarks;
    }
    public String getQq() {
        return qq;
    }
    public String getNumber(){
        return number;
    }
    public String getAddress(){
        return address;
    }
    public String getSex(){
        return sex;
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
