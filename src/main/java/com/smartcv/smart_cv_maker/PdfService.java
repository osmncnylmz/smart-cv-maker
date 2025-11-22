package com.smartcv.smart_cv_maker;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PdfService {

    public byte[] generateCvPdf(Cv cv, String aiSuggestion) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try (PdfWriter writer = new PdfWriter(baos);
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf)) {

            document.add(new Paragraph("SMART CV - Öneri Raporu")
                    .setFontSize(24).setBold().setTextAlignment(TextAlignment.CENTER).setMarginBottom(20));

            document.add(new Paragraph("Kullanıcı Tarafından Girilen Veriler")
                    .setFontSize(18).setBold().setUnderline());

            document.add(new Paragraph("Ad Soyad: " + cv.getName()));
            document.add(new Paragraph("E-posta: " + cv.getEmail()));
            document.add(new Paragraph("Özet: " + cv.getSummary()));
            document.add(new Paragraph("Beceriler: " + cv.getSkills()));
            document.add(new Paragraph("Eğitim: " + cv.getEducation()));
            document.add(new Paragraph("Deneyim: " + cv.getExperience()));

            document.add(new Paragraph("--------------------------------------------------").setMarginTop(15).setMarginBottom(15));

            document.add(new Paragraph("Yapay Zeka (AI) Geliştirme Önerileri")
                    .setFontSize(20).setBold().setTextAlignment(TextAlignment.CENTER).setMarginTop(20));

            // AI önerisini PDF'e metin olarak ekle (HTML etiketlerini temizle)
            String cleanAiSuggestion = aiSuggestion
                    .replaceAll("<br/>", "\n")
                    .replaceAll("</?ul>", "")
                    .replaceAll("</?li>", "")
                    .replaceAll("<b>", "")
                    .replaceAll("</b>", "");

            document.add(new Paragraph(cleanAiSuggestion)
                    .setFontSize(12).setMarginTop(10));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return baos.toByteArray();
    }
}