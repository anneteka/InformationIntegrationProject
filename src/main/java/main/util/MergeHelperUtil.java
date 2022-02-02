package main.util;

import java.util.Arrays;

public class MergeHelperUtil {
    public static int getPersonNameMatchesCount(String[] names1, String[] names2){
        // sort arrays from longest name to shortes to first find full name matches
        sortInDescLength(names1);
        sortInDescLength(names2);
        
        int matches = 0;
        for (String name1: names1){
            for (String name2: names2){
                if (name1.length() > 0 && name2.length() > 0){ // empty name has no matching
                    if (name1.length() > 2 && name2.length() > 2){ // names are full names, not only letter + dot
                        if (name1.equals(name2)) 
                            matches++;

                    }else if (matches > 0){                         // only compare first letter of name with dot if a "real" match was already found
                        if (name1.charAt(0) == (name2.charAt(0))) 
                            matches++;
                    }
                }
            }
        }
        return matches;
    }

    public static int getExactMatchesCount(String[] names1, String[] names2){
        // sort arrays from longest name to shortes to first find full name matches
        sortInDescLength(names1);
        sortInDescLength(names2);
        
        int matches = 0;
        for (String name1: names1){
            for (String name2: names2){
                if (name1.length() > 0 && name2.length() > 0){ // empty name has no matching
                    if (name1.equals(name2)) 
                            matches++;
                }
            }
        }
        
        return matches;
    }

    private static void sortInDescLength(String[] str){
        Arrays.sort(str, (str1, str2) -> str2.length() - str1.length());
    }
}
