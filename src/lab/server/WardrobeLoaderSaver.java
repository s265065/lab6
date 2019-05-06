package lab.server;

import lab.Hat;
import lab.Item;
import lab.NegativeShelfNumberException;
import lab.Thing;

import java.io.*;
import java.util.Scanner;

public class WardrobeLoaderSaver {

    static void save(Wardrobe wardrobe, String filename) throws IOException{
        File file = new File(filename);
        FileWriter filewriter = new FileWriter(file);
        BufferedWriter bufwriter = new BufferedWriter(filewriter);

        for (Hat hat : wardrobe) {
            bufwriter.write(hat.size + "," + hat.color + "," + hat.contentlist() + "\n");
        }
        bufwriter.close();
    }

    static void load(Wardrobe wardrobe, String filename) throws IOException{
        int s=wardrobe.size();
        int i =0;
        while (i<s){wardrobe.remove(wardrobe.get(0));
            ++i;}
        try{
            Scanner fileScanner = new Scanner(new FileReader(filename));
            while(fileScanner.hasNextLine()){
                Hat hat = parseCSVLine(fileScanner.nextLine());
                if (!(hat.color.equals(""))){
                    wardrobe.add(hat);}}
        } catch (IOException e) {
            System.out.println("При загрузке гардероба произошла ошибка");
        }
    }

    private static Hat parseCSVLine(String textline) {
        String line = textline.replaceAll("\"", "");
        Scanner scanner = new Scanner(line);
        scanner.useDelimiter("\\s*,\\s*");
        if (scanner.hasNextInt()) {
            int size = scanner.nextInt();
            if (scanner.hasNext()) {
                String color = scanner.next();
                if (scanner.hasNextInt()){
                    int x = scanner.nextInt();
                    try {if (x<0)
                        throw new NegativeShelfNumberException();}
                    catch (NegativeShelfNumberException e){
                        System.out.println("Не удалось создать шляпу "+e.getMessage());
                        return new Hat(0,"", 0);}
                    Hat hat = new Hat(size, color, x);
                    if (scanner.hasNextLine()) {
                        String content = scanner.nextLine();
                        if (content != null) {
                            Scanner stscanner = new Scanner(content);
                            while (hat.checkspace() != -1) {
                                if (stscanner.hasNext()) {
                                    String item = stscanner.next();
                                    try {
                                        hat.addthing(new Thing(Item.valueOf(item.replaceAll(",", ""))));
                                    } catch (IllegalArgumentException e) {
                                        System.out.println("Введен предмет не из списка/ Название предмета введено с ошибкой(содержит кавычки или другие символы)");
                                    }
                                } else {
                                    break;
                                }
                            }
                        }
                    }
                    return hat;
                }
                else {
                    System.out.println("неверно введены данные. шляпа не может быть создана, т.к. отсутствует параметр num");}
            }
            else {
                System.out.println("неверно введены данные. шляпа не может быть создана, т.к. отсутствует параметр color");
            }
        }else {System.out.println("неверно введены данные. шляпа не может быть создана, т.к. отсутствует параметр size"); }
        return new Hat(0,"", 0);}
}
