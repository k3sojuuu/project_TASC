package com.example.mediaservice.repository;

import com.example.mediaservice.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByTypeImg(Long typeImg);
    List<Image> findByUserId(Long userId);

    @Query("SELECT i.id FROM Image i WHERE i.fileName = :fileName AND i.userId = :userId AND i.typeImg = :typeImg")
    Long findIdByFileNameAndUserIdAndTypeImg(@Param("fileName") String fileName,
                                             @Param("userId") Long userId,
                                             @Param("typeImg") Long typeImg);
}
