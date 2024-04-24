package zunza.myshop.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnails;

import zunza.myshop.constant.ImageSize;
import zunza.myshop.domain.Product;

@Component
public class ImageUtil {

	public static final String PATH = System.getProperty("user.dir") + "/src/main/resources/static/images/";
	public static final String RESPONSE_PATH = "http://localhost:8080/";

	public String saveImage(MultipartFile image) throws IOException {
		UUID uuid = UUID.randomUUID();
		String extension = image.getOriginalFilename().split("\\.")[1];
		Path path = Paths.get(PATH + uuid + "." + extension);
		Files.write(path, image.getBytes());
		return RESPONSE_PATH + uuid + "." + extension;
	}

	private void removeImage(String imageUrl) {
		File file = new File(imageUrl);
		file.delete();
	}
}
