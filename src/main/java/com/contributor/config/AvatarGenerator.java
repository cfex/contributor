package com.contributor.config;

public abstract class AvatarGenerator {
    public static String generate(String value) {
        return "https://avatars.dicebear.com/api/bottts/" + value + ".svg";
    }
}
