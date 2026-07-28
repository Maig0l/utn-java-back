package gg.wellplayed.backend.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {
	public static final String UPLOAD_DIR = "uploads";

	public String store(MultipartFile file) {
		try {
			Path uploadPath = Paths.get(UPLOAD_DIR);
			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}

			String originalName = file.getOriginalFilename();
			String extension = "";
			if (originalName != null && originalName.contains(".")) {
				extension = originalName.substring(originalName.lastIndexOf('.'));
			}
			String filename = UUID.randomUUID() + extension;

			Path target = uploadPath.resolve(filename);
			Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

			return filename;
		}
		catch (IOException e) {
			throw new RuntimeException("No se pudo guardar el archivo: " + e.getMessage(), e);
		}
	}
}
