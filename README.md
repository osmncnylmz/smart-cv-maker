Smart CV Maker
Smart CV Maker, kullanıcıların basit bir form doldurarak hızlı bir şekilde CV oluşturabileceği bir web uygulamasıdır. Kullanıcı bilgileri işlendikten sonra sistem otomatik olarak CV’yi oluşturur ve PDF formatında indirilebilir hale getirir.

🚀 Özellikler
Form üzerinden CV oluşturma
Otomatik CV şablonu
PDF çıktısı alma
Modern ve sade arayüz
Spring Boot + HTML/CSS entegre çalışma yapısı

🛠️ Kullanılan Teknolojiler
Backend:
Java
Spring Boot
Spring Web
Thymeleaf
Frontend:
HTML
CSS
Diğer:
PDF oluşturma servisi
Katmanlı mimari (Controller → Service → Repository)


📁 Proje Yapısı
smart-cv-maker/
 ├── src/main/java/com/smartcv/smart_cv_maker/
 │     ├── CvController.java
 │     ├── CvService.java
 │     ├── CvRepository.java
 │     ├── Cv.java
 │     ├── PdfService.java
 │     └── SmartCvMakerApplication.java
 │
 ├── src/main/resources/
 │     ├── templates/
 │     │      ├── cv-form.html
 │     │      └── cv-result.html
 │     ├── static/css/style.css
 │     └── application.properties
 │
 ├── build.gradle
 ├── settings.gradle
 └── README.md
 
▶️ Projeyi Çalıştırma
1. Bağımlılıkları yükle
./gradlew build
2. Uygulamayı başlat
./gradlew bootRun
3. Tarayıcıdan aç
http://localhost:8080
📬 İletişim
Osman Can Yılmaz
GitHub: https://github.com/osmncnylmz
