package com.example.user.cinemaapplication.Classes;

/**
 * Created by User on 22.11.2017.
 */

public class SessionClass {

    private int id;
    private int idTheater;
    private int idAuditorium;
    private String nameAuditorium;
    private int idEvent;
    private String nameEvent;
    private String acronymEvent;
    private String date;
    private long start;
    private long end;
    private int idTypeVideo;
    private String nameTypeVideo;
    private String logoTypeVideo;
    private String acronymTypeVideo;
    private int idTypeAudio;
    private String nameTypeAudio;
    private String logoTypeAudio;
    private String acronymTypeAudio;
    private int idTypeLanguage;
    private String nameTypeLanguage;
    private String logoTypeLanguage;
    private String acronymTypeLanguage;
    private int idAgeLimit;
    private String nameAgeLimit;
    private String logoAgeLimit;
    private String acronymAgeLimit;
    private int idSalesSchema;
    private String timeDelete;
    private int quantity;

    public String getTimeDelete(){
        return timeDelete;
    }
    public void setTimeDelete(String timeDelete){
        this.timeDelete = timeDelete;
    }

    private int getQuantity(){
        return quantity;
    }
    private void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public String getDate(){
        return date;
    }
    public void setDate(String date){
        this.date = date;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdTheater() {
        return idTheater;
    }

    public void setIdTheater(int idTheater) {
        this.idTheater = idTheater;
    }

    public int getIdAuditorium() {
        return idAuditorium;
    }

    public void setIdAuditorium(int idAuditorium) {
        this.idAuditorium = idAuditorium;
    }

    public String getNameAuditorium() {
        return nameAuditorium;
    }

    public void setNameAuditorium(String nameAudtiorium) {
        this.nameAuditorium = nameAudtiorium;
    }

    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

    public String getNameEvent() {
        return nameEvent;
    }

    public void setNameEvent(String nameEvent) {
        this.nameEvent = nameEvent;
    }

    public String getAcronymEvent() {
        return acronymEvent;
    }

    public void setAcronymEvent(String acronymEvent) {
        this.acronymEvent = acronymEvent;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public int getIdTypeVideo() {
        return idTypeVideo;
    }

    public void setIdTypeVideo(int idTypeVideo) {
        this.idTypeVideo = idTypeVideo;
    }

    public String getNameTypeVideo() {
        return nameTypeVideo;
    }

    public void setNameTypeVideo(String nameTypeVideo) {
        this.nameTypeVideo = nameTypeVideo;
    }

    public String getLogoTypeVideo() {
        return logoTypeVideo;
    }

    public void setLogoTypeVideo(String logoTypeVideo) {
        this.logoTypeVideo = logoTypeVideo;
    }

    public String getAcronymTypeVideo() {
        return acronymTypeVideo;
    }

    public void setAcronymTypeVideo(String acronymTypeVideo) {
        this.acronymTypeVideo = acronymTypeVideo;
    }

    public int getIdTypeAudio() {
        return idTypeAudio;
    }

    public void setIdTypeAudio(int idTypeAudio) {
        this.idTypeAudio = idTypeAudio;
    }

    public String getNameTypeAudio() {
        return nameTypeAudio;
    }

    public void setNameTypeAudio(String nameTypeAudio) {
        this.nameTypeAudio = nameTypeAudio;
    }

    public String getLogoTypeAudio() {
        return logoTypeAudio;
    }

    public void setLogoTypeAudio(String logoTypeAudio) {
        this.logoTypeAudio = logoTypeAudio;
    }

    public String getAcronymTypeAudio() {
        return acronymTypeAudio;
    }

    public void setAcronymTypeAudio(String acronymTypeAudio) {
        this.acronymTypeAudio = acronymTypeAudio;
    }

    public int getIdTypeLanguage() {
        return idTypeLanguage;
    }

    public void setIdTypeLanguage(int idTypeLanguage) {
        this.idTypeLanguage = idTypeLanguage;
    }

    public String getNameTypeLanguage() {
        return nameTypeLanguage;
    }

    public void setNameTypeLanguage(String nameTypeLanguage) {
        this.nameTypeLanguage = nameTypeLanguage;
    }

    public String getLogoTypeLanguage() {
        return logoTypeLanguage;
    }

    public void setLogoTypeLanguage(String logotTypeLanguage) {
        this.logoTypeLanguage = logotTypeLanguage;
    }

    public String getAcronymTypeLanguage() {
        return acronymTypeLanguage;
    }

    public void setAcronymTypeLanguage(String acronymTypeLanguage) {
        this.acronymTypeLanguage = acronymTypeLanguage;
    }

    public int getIdAgeLimit() {
        return idAgeLimit;
    }

    public void setIdAgeLimit(int idAgeLimit) {
        this.idAgeLimit = idAgeLimit;
    }

    public String getNameAgeLimit() {
        return nameAgeLimit;
    }

    public void setNameAgeLimit(String nameAgeLimit) {
        this.nameAgeLimit = nameAgeLimit;
    }

    public String getLogoAgeLimit() {
        return logoAgeLimit;
    }

    public void setLogoAgeLimit(String logoAgeLimit) {
        this.logoAgeLimit = logoAgeLimit;
    }

    public String getAcronymAgeLimit() {
        return acronymAgeLimit;
    }

    public void setAcronymAgeLimit(String acronymAgeLimit) {
        this.acronymAgeLimit = acronymAgeLimit;
    }

    public int getIdSalesSchema() {
        return idSalesSchema;
    }

    public void setIdSalesSchema(int idSalesSchema) {
        this.idSalesSchema = idSalesSchema;
    }

    /*  "id": 149,
                "idTheater": 1,
                "idAuditorium": 6,
                "nameAuditorium": "Зал 4",
                "idEvent": 7,
                "nameEvent": "Леди Макбет",
                "acronymEvent": "Леди Макбет",
                "date": "2017-11-22",
                "start": 1511360400000,
                "end": 1511366700000,
                "idTypeVideo": 1,
                "nameTypeVideo": "2D",
                "logoTypeVideo": null,
                "acronymTypeVideo": "2D",
                "idTypeAudio": 1,
                "nameTypeAudio": "Обычный",
                "logoTypeAudio": null,
                "acronymTypeAudio": "-",
                "idTypeLanguage": 1,
                "nameTypeLanguage": "Русский",
                "logoTypeLanguage": null,
                "acronymTypeLanguage": "RU",
                "idAgeLimit": 8,
                "nameAgeLimit": "лицам до 17 лет обязательно присутствие взрослого",
                "logoAgeLimit": null,
                "acronymAgeLimit": "R17",
                "idSalesSchema": 1

       */

}
