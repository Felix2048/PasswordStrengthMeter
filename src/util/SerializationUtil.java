package util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @title: SerializationUtil
 * @description:
 * @author: Felix
 **/

public class SerializationUtil {

    public static void serialize(Object object, String filepath) throws Exception {
        FileOutputStream fos = new FileOutputStream(filepath);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(object);
        oos.flush();
        oos.close();

    }

    public static Object deserialize(String filepath) throws Exception {
        FileInputStream fis = new FileInputStream(filepath);
        ObjectInputStream ois = new ObjectInputStream(fis);
        return ois.readObject();
    }
}
