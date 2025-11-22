package com.smartcv.smart_cv_maker;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CvController {

    private final CvService cvService;
    private final PdfService pdfService;

    public CvController(CvService cvService, PdfService pdfService) {
        this.cvService = cvService;
        this.pdfService = pdfService;
    }

    // 1. Yeni CV Formu (/ ya da /create)
    @GetMapping({"/", "/create"})
    public String showCreateForm(Model model) {
        model.addAttribute("cv", new Cv());
        return "cv-form";
    }

    // 2. Yeni CV Oluşturma ve Analiz Etme
    @PostMapping("/generate-cv")
    public String generateCv(
            @ModelAttribute Cv cv
    ) {
        Cv analyzedCv = cvService.createAndAnalyzeCv(cv);
        // Sonuç sayfasına yönlendir (redirect)
        return "redirect:/cv/" + analyzedCv.getId() + "/result";
    }

    // 3. Var olan CV'yi Güncelleme ve Tekrar Analiz Etme
    @PostMapping("/update-cv")
    public String updateCv(@ModelAttribute Cv cv) {
        Cv updatedCv = cvService.updateCv(cv);
        // Sonuç sayfasına yönlendir
        return "redirect:/cv/" + updatedCv.getId() + "/result";
    }

    // 4. Sonuç Sayfası (Kayıtlı CV'yi ID ile gösterir)
    @GetMapping("/cv/{id}/result")
    public String showResult(@PathVariable Long id, Model model) {
        Cv cv = cvService.findById(id)
                .orElseThrow(() -> new RuntimeException("CV ID'si bulunamadı: " + id));

        model.addAttribute("cv", cv);
        model.addAttribute("aiSuggestion", cv.getLastAiSuggestion());

        return "cv-result";
    }

    // 5. Düzenleme Formu
    @GetMapping("/cv/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Cv cv = cvService.findById(id)
                .orElseThrow(() -> new RuntimeException("CV ID'si bulunamadı: " + id));

        model.addAttribute("cv", cv);
        return "cv-form";
    }

    // 6. PDF İndirme
    @GetMapping("/cv/{id}/download-pdf")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable Long id) {
        Cv cv = cvService.findById(id)
                .orElseThrow(() -> new RuntimeException("CV ID'si bulunamadı: " + id));

        byte[] pdfBytes = pdfService.generateCvPdf(cv, cv.getLastAiSuggestion());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        String filename = "SmartCV_Rapor_" + cv.getName().replace(" ", "_") + ".pdf";
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}