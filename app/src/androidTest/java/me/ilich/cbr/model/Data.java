package me.ilich.cbr.model;

import java.util.ArrayList;
import java.util.List;

public class Data {

    Valute valuteA1 = new Valute("idA", "codeA", "audA", 1, "A", 1.0);
    Valute valuteA2 = new Valute("idA", "codeA", "audA", 1, "A", 1.0);
    Valute valuteB = new Valute("idB", "codeB", "audB", 2, "B", 2.0);
    Valute valuteC = new Valute("idC", "codeC", "audC", 3, "C", 3.0);

    Valute rur = new Valute("","","", 1, "", 1.0);
    Valute usd = new Valute("", "", "", 1, "", 64.7380);
    Valute uah = new Valute("", "", "", 10, "", 25.4373);

    Valute valuteInvalidNominal = new Valute("idA", "codeA", "audA", 0, "A", 1.0);
    Valute valuteInvalidValue = new Valute("idA", "codeA", "audA", 10, "A", 0.0);

    List<Valute> listA1 = new ArrayList<>();
    List<Valute> listA2 = new ArrayList<>();
    List<Valute> listB = new ArrayList<>();
    List<Valute> listC = new ArrayList<>();

    ValCurs valCursA1 = new ValCurs("dateA", "A", listA1);
    ValCurs valCursA2 = new ValCurs("dateA", "A", listA1);
    ValCurs valCursB = new ValCurs("dateB", "B", listB);
    ValCurs valCursC = new ValCurs("dateC", "C", listC);

    public Data() {
        listA1.add(valuteA1);
        listA1.add(valuteB);
        listA1.add(valuteC);
        listA2.add(valuteA1);
        listA2.add(valuteB);
        listA2.add(valuteC);
        listB.add(valuteB);
        listC.add(valuteC);
    }

}
