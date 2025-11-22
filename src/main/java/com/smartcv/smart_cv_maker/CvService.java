package com.smartcv.smart_cv_maker;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class CvService {

    private final CvRepository cvRepository;
    private final AiService aiService;

    public CvService(CvRepository cvRepository, AiService aiService) {
        this.cvRepository = cvRepository;
        this.aiService = aiService;
    }

    @Transactional
    public Cv createAndAnalyzeCv(Cv cv) {
        // 1. Yeni CV'yi kaydet
        Cv savedCv = cvRepository.save(cv);

        // 2. AI analizi yap
        String aiSuggestion = aiService.getCvSuggestions(savedCv);

        // 3. AI önerisini kaydettiğimiz CV'ye ekle ve güncelle
        savedCv.setLastAiSuggestion(aiSuggestion);
        return cvRepository.save(savedCv);
    }

    @Transactional
    public Cv updateCv(Cv cv) {
        // ID olduğu için mevcut kaydı günceller
        Cv updatedCv = cvRepository.save(cv);

        // Güncellenmiş veriyi tekrar analiz et
        String aiSuggestion = aiService.getCvSuggestions(updatedCv);
        updatedCv.setLastAiSuggestion(aiSuggestion);

        // Güncel AI önerisi ile kaydı bitir
        return cvRepository.save(updatedCv);
    }

    public Optional<Cv> findById(Long id) {
        return cvRepository.findById(id);
    }
}