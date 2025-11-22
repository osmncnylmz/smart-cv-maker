package com.smartcv.smart_cv_maker;

// Lombok kullanmadığımız varsayımıyla (kullanmak daha iyidir)
// manuel olarak getter/setter metotlarını ekleyelim.

public class CvRequest {
    private String name;
    private String email; // Yeni alan
    private String phone; // Yeni alan
    private String summary; // Yeni alan
    private String skills;
    private String education;
    private String experience;

    // --- Constructor (isteğe bağlı, gerek yok) ---

    // --- Getter ve Setter Metotları ---

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }
}