package models;

import lombok.Data;

@Data
public class UserModel {
    private Data data = new Data();
    private Support support = new Support();

    @lombok.Data
    public static class Data {
        private int id;
        private String email;
        private String first_name;
        private String last_name;
        private String avatar;
    }

    @lombok.Data
    public static class Support {
        private String url;
        private String text;
    }
}