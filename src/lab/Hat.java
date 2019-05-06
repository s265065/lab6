package lab;

import lab.json.*;

import java.io.Serializable;
import java.util.Date;

public class Hat  implements Serializable {
    public String color;
    public int size;
    public int num;
    private Date createdDate =new Date();
    public Thing[] content;

    public String getColor(){
        StringBuilder result = new StringBuilder();
        result.append("шляпа с цветом"+this.color);
        return result.toString();
    }

    /**
     * Добавляет предмет в шляпу
     * @param obj предмет, который нужно добавить
     */
    public void addthing(Thing obj){
        if (checkspace()!=-1){
            if (checkitem(obj)==-1){
            System.out.println("Объект " + obj.rus(obj.name.toString()) + " был успешно добавлен в шляпу.");
            this.content[checkspace()]=obj;}
            else {System.out.println("Объект " + obj.rus(obj.name.toString()) +" уже есть в этой шляпе");}
        }
        else {
            System.out.println("В шляпе не осталось места. Пожалуйста удалите какой-нибудь предмет прежде чем добавлять новый.\n" +
                    "Объект" + obj.rus(obj.name.toString()) + "не был добавлен в шляпу.");
        }
    }

    /**
     * Проверяет есть ли в шляпе свободное место
     * @return индекс ближайшей свободной ячейки; -1, если свободного места не осталось
     */
    public int checkspace(){
        for (int i=0; i < this.size; i++){
            if (this.content[i]==null){return i;}
        }
        return -1;
    }

    /**
     * Метод для того чтобы узнать только содержимое шляпы
     * @return строку в которой перечисленно все содержимое шляпы
     */
    public String contentlist(){
        StringBuilder result = new StringBuilder();
        for (int i=0; i < this.size; i++) {
            if (this.content[i]!=null)
                result.append(this.content[i].name.toString()).append(" ");
        }
        return result.toString();
    }

    /**
     * Проверяет есть ли заданный предмет в шляпе
     * @param item предмет, наличие которого нужно проверить
     * @return индекс найденного предмета; -1, если предмета в шляпе нет
     */
    private int checkitem(Thing item){
        for (int i=0; i < this.size; i++){
            if (this.content[i]!=null)
                if ((this.content[i].name).equals(item.name)){return i;}
        }
        return -1;
    }

    /**
     * Удаляет предмет из шляпы
     * @param obj предмет, который нужно удалить
     */
    public void deletething(Thing obj) {
        for (int i=0; i < this.size; i++){
            if (this.content[i]!=null)
                if ((this.content[i].name).equals(obj.name)){this.content[i]=null;}
        }
    }

    /**
     * Выводит информацию о шляпе: размер, цвет, местоположение, дату создания и содержимое
     */
    public String showHat(){
        StringBuilder result= new StringBuilder();
        result.append("Размер шляпы "+this.size+"; Цвет шляпы "+ this.color+"; Расположение шляпы: полка №"+this.num+";"+ " Дата создания: "+this.createdDate);
        for (int i=0; i < this.size; i++){
            if (this.content[i]!=null)
                result.append("В шляпе лежит "+this.content[i].rus(this.content[i].name.toString())+"\n ");
        }
    return result.toString();
    }

    public Hat(int a, String c, int x){
        this.size=a;
        this.num=x;
        this.color=c;
        this.content= new Thing[a];
    }

    public Hat(int a, String c, int x, Thing[] arr){
        this.size=a;
        this.num=x;
        this.color=c;
        this.content=arr;
    }

    public Hat(int a, int x, JSONString c){
        this.num=x;
        this.size=a;
        this.color=c.toString();
        this.content= new Thing[a];
    }
}
