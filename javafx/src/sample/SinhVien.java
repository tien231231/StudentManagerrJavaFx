package sample;
public class SinhVien {
    private String name;
    private String id;
    private int year;

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id=id;
    }
    public int getYear(){
        return year;
    }
    public void setYear(int year){
        this.year=year;
    }

    public SinhVien(String id,String name, int year){
        this.id=id;
        this.name=name;
        this.year=year;
    }
}
