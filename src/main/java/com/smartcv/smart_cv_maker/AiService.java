package com.smartcv.smart_cv_maker;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class AiService {

    private final WebClient webClient;
    private final String model;

    public AiService(
            WebClient.Builder webClientBuilder,
            @Value("${openai.api.key}") String apiKey,
            @Value("${openai.api.url}") String apiUrl,
            @Value("${openai.model}") String model) {

        this.webClient = webClientBuilder
                .baseUrl(apiUrl)
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .build();
        this.model = model;
    }

    private String buildPrompt(Cv cv) {
        // Yeni, daha güçlü ve detaylı kullanıcı prompt'u
        return String.format(
                "Aşağıdaki CV verilerini A'dan Z'ye analiz et. Cevabını 3 ana başlık altında topla: " +
                        "1. Kritik Zayıflıklar ve İyileştirme Alanları. " +
                        "2. Deneyimin Metrik Odaklı Güçlendirilmesi (STAR Metodu). " +
                        "3. Kariyer Hedefi ve Becerilerin Pazarlanması. " +
                        "Her bir öneriyi HTML <ul> ve <li> etiketleri içinde sun. Cevabın SADECE Türkçe olsun.\n\n" +
                        "CV Verileri:\n" +
                        "Ad: %s\n" +
                        "Özet: %s\n" +
                        "Beceriler: %s\n" +
                        "Eğitim: %s\n" +
                        "Deneyim: %s",
                cv.getName(),
                cv.getSummary(),
                cv.getSkills(),
                cv.getEducation(),
                cv.getExperience()
        );
    }

    public String getCvSuggestions(Cv cv) {
        String prompt = buildPrompt(cv);

        // GÜÇLENDİRİLMİŞ SİSTEM ROLÜ VE YÖNERGELERİ
        String systemInstruction = "Sen, bir işe alım yöneticisi (Recruiter) ve kıdemli CV uzmanısın. Amacın, sunulan CV verilerini en kritik zayıflıklarını ve en güçlü potansiyelini ortaya çıkararak analiz etmek. Deneyim bölümlerinde daima nicel (sayısal) başarıları vurgula ve adayın kariyer hedefini belirginleştirmesine yardımcı ol. Yanıtın, kullanıcının kolayca kopyalayıp CV'sine uygulayabileceği eyleme dönük tavsiyeler içermeli. Tüm çıktı, sadece Türkçe olmalı ve HTML formatında (<ul>, <li>, <b>) sunulmalıdır.";


        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", this.model);
        requestBody.put("messages", Arrays.asList(
                // Sistem mesajı güncellendi
                Map.of("role", "system", "content", systemInstruction),
                Map.of("role", "user", "content", prompt)
        ));

        try {
            String response = webClient.post()
                    .uri("")
                    .body(BodyInserters.fromValue(requestBody))
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);

            JsonNode choices = root.path("choices");
            if (choices.isArray() && choices.size() > 0) {
                String aiContent = choices.get(0)
                        .path("message").path("content")
                        .asText();
                // OpenAI yanıtı HTML olarak gelse bile, bazen ekstra yeni satırları temizlemek faydalıdır.
                return aiContent.trim();
            } else {
                return "Üzgünüz, yapay zeka boş bir yanıt döndürdü.";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Üzgünüz, yapay zeka önerisi alınırken bir hata oluştu: " + e.getMessage();
        }
    }
}