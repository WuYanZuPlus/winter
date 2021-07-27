package com.jianghu.winter;

import java.util.regex.Pattern;

/**
 * @Author: daniel.hu
 * @Date: 2021/6/17 16:40
 */
public class RegexUtil {

    public static String getRegex(String input){
        String result = input.replaceAll("(/:[A-Za-z0-9]+)", "/[A-Za-z0-9]+");
        return result + "[/]?";
    }

    public static void main(String[] args) {
        String regex = getRegex("a/b/:id/c/:type/e");

        System.out.println(regex);

        Pattern test = Pattern.compile("a/b/[A-Za-z0-9]+/c/[A-Za-z0-9]+/e[/]?");
    }
}
