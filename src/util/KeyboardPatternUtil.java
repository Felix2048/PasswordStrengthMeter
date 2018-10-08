package util;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @title: KeyboardPatternUtil
 * @description:
 * @author: Felix
 * @date: 7/28/2018 10:33
 **/

public class KeyboardPatternUtil {

    private Set<String> keyboardPatternSet = new HashSet<>();

    public KeyboardPatternUtil() {
        try {
            //  Read file
            InputStream inputStream = new FileInputStream("res/keyboardPattern");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            //  Read a password
            String keyboardPattern = null;
            int count = 0;
            while((keyboardPattern = bufferedReader.readLine()) != null) {
                keyboardPatternSet.add(keyboardPattern);
            }

            //  close
            inputStream.close();
            inputStreamReader.close();
            bufferedReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * return a substring of keyboard pattern in the keyboardPatternSet
     * if not in the set, return empty string
     * @param string
     * @return subString
     */
    public String substringkeyboradpattern(String string) {

        String keyborad = "";

        if (string.length() >= 4) {
            String keyboard4 = string.substring(0, 4);  //  keyboard pattern of length 4
            if (keyboardPatternSet.contains(keyboard4)) {
                keyborad = keyboard4;
            }
        }
        if (string.length() >= 5) {
            String keyboard5 = string.substring(0, 5);  //  keyboard pattern of length 4
            if (keyboardPatternSet.contains(keyboard5)) {
                keyborad = keyboard5;
            }
        }
        if (string.length() >= 6) {
            String keyboard6 = string.substring(0, 6);  //  keyboard pattern of length 4
            if (keyboardPatternSet.contains(keyboard6)) {
                keyborad = keyboard6;
            }
        }

        return keyborad;
    }
}
