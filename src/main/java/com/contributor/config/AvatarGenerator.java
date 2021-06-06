package com.contributor.config;

public abstract class AvatarGenerator {

    /**
     * The easiest way to quickly create avatars.
     * It is for demonstration purposes only and is not considered close to a good solution.
     *
     * @param value can be any random string(including special characters)
     * @return full path to avatar.com that will create an avatar based on the value
     */
    public static String generate(String value) {
        return "https://avatars.dicebear.com/api/bottts/" + value + ".svg";
    }
}
