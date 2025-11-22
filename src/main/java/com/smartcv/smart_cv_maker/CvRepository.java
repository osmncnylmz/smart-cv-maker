package com.smartcv.smart_cv_maker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CvRepository extends JpaRepository<Cv, Long> {
    // Spring Data JPA tüm temel veritabanı CRUD işlemlerini otomatik sağlar.
}