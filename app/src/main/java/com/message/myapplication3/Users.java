package com.message.myapplication3;

public class Users {


    public String email;
    public String password;

    public String name;
    public int year_of_birth;
    public String nationality;

    public String nt_language;
    public String second_language;
    public String first_ln_language;

    public String image;
    public String status;
    public String thumb_image;



    public Users(){

    }

    public Users(String email, String password,
                 String name, int year_of_birth, String nationality,
                 String nt_language, String second_language, String first_ln_language,
                 String image, String status, String thumb_image) {
        this.email = email;
        this.password = password;

        this.name = name;
        this.year_of_birth = year_of_birth;
        this.nationality = nationality;

        this.nt_language = nt_language;
        this.second_language = second_language;
        this.first_ln_language = first_ln_language;

        this.image = image;
        this.status = status;
        this.thumb_image = thumb_image;
    }

    public String getEmail(){return email;}

    public void setEmail(String email) { this.email = email; }

    public String getPassword(){return password;}

    public void setPassword(String password) { this.password = password; }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYearOfBirth() {
        return year_of_birth;
    }

    public void setYearOfBirth(int year_of_birth) {
        this.year_of_birth = year_of_birth;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }



    public String getNTLanguage() {
        return nt_language;
    }

    public void setNTLanguage(String nt_language) {
        this.nt_language = nt_language;
    }

    public String getSecondLanguage() {
        return second_language;
    }

    public void setSecondLanguage(String second_language) {
        this.second_language = second_language;
    }

    public String getFirstLNLanguage() {
        return first_ln_language;
    }

    public void setFirstLNLanguage(String first_ln_language) {
        this.first_ln_language = first_ln_language;
    }



    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getThumb_image() {
        return thumb_image;
    }

    public void setThumb_image(String thumb_image) {
        this.thumb_image = thumb_image;
    }

}
