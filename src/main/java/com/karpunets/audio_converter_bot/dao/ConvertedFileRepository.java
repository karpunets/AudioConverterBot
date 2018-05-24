package com.karpunets.audio_converter_bot.dao;

import com.karpunets.audio_converter_bot.model.ConvertedFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConvertedFileRepository extends JpaRepository<ConvertedFile, Long> {
}
