package servlets.file;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import servlets.SceneSaver;


/**
 * @author David Santiago Barrera
 * @data 14/10/2012
 *
 */
public class Filesaver implements SceneSaver {

    @Override
    public Object load(String usuario) {
        String name = "/Users/davidsantiagobarrera/Documents/Pelotas-" + usuario + ".dat";
        Object obj = new Object();
        try {
            FileInputStream file = new FileInputStream(name);
            ObjectInputStream ois = new ObjectInputStream(file);
            obj = ois.readObject();
            System.out.println(obj);
            ois.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error FileNotFoundException");
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error Exception");
            System.out.println(e.getMessage());
        }
        return obj;
    }

    @Override
    public Boolean write(String usuario, Object obj) {
        String name = "/Users/davidsantiagobarrera/Documents/Pelotas-" + usuario + ".dat";
        FileOutputStream fOutS;
        ObjectOutputStream oOuts;
        try {
            fOutS = new FileOutputStream(name);
            oOuts = new ObjectOutputStream(fOutS);
            oOuts.writeObject(obj);
            System.out.println(obj);
            oOuts.close();
        } catch (Exception e) {
            System.out.println("Error Exception");
            System.out.println(e.getMessage());
        } finally {
        }
        return true;
    }
}
