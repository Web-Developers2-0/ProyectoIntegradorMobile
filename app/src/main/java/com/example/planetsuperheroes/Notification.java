package com.example.planetsuperheroes;

public class Notification {
    private String title;
    private String detail;
    private String id; // ID único de la notificación
    private String timestamp;

    public Notification(String title, String detail, String id, String timestamp) {
        this.title = title;
        this.detail = detail;
        this.id = id;
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public String getDetail() {
        return detail;
    }

    public String getId() {
        return id;
    }

    public String getTimestamp() {
        return timestamp;
    }
}



