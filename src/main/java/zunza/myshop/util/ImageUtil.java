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
import zunza.myshop.domain.ProductImage;

@Component
public class ImageUtil {

	public static final String PATH = System.getProperty("user.dir") + "/src/main/resources/static/images/";
	public static final String RESPONSE_PATH = "http://localhost:8080/";

	private byte[] resizeImage(MultipartFile file, int width, int height) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Thumbnails.of(new ByteArrayInputStream(file.getBytes()))
			.forceSize(width, height)
			.toOutputStream(baos);
		return baos.toByteArray();
	}

	private String saveImage(byte[] imageBytes, String type, String fileName) throws IOException {
		UUID uuid = UUID.randomUUID();
		String extension = fileName.split("\\.")[1];
		Path path = Paths.get(PATH + type + uuid + "." + extension);
		Files.write(path, imageBytes);
		return RESPONSE_PATH + type + uuid + "." + extension;
	}

	private ProductImage createImage(
		MultipartFile productImage,
		int width,
		int height,
		String type) throws IOException {

		byte[] resizeImage = resizeImage(productImage, width, height);
		String imageUrl = saveImage(resizeImage, type, productImage.getOriginalFilename());
		String imageName = imageUrl.split(RESPONSE_PATH)[1];

		return ProductImage.of(imageName, imageUrl);
	}

	private void updateImage(
		MultipartFile newImage,
		ProductImage productImage,
		int width,
		int height,
		String type) throws IOException {

		byte[] resizeImage = resizeImage(newImage, width, height);
		String imageUrl = saveImage(resizeImage, type, newImage.getOriginalFilename());
		String imageName = imageUrl.split(RESPONSE_PATH)[1];

		productImage.update(imageName, imageUrl);
	}

	private void removeImage(String imageUrl) {
		File file = new File(imageUrl);
		file.delete();
	}

	public List<ProductImage> convertToEntitiesWithResizeAndSave (
		MultipartFile mainImage,
		List<MultipartFile> images) throws IOException {

		List<ProductImage> productImages = new ArrayList<>();

		// 섬네일 이미지 처리
		ProductImage thumbnailImage = createImage(
			mainImage,
			ImageSize.THUMBNAIL_IMAGE_WIDTH,
			ImageSize.THUMBNAIL_IMAGE_HEIGHT,
			"thumbnail-");
		productImages.add(thumbnailImage);

		// 메인 이미지 처리
		ProductImage main = createImage(
			mainImage,
			ImageSize.NORMAL_IMAGE_WIDTH,
			ImageSize.NORMAL_IMAGE_HEIGHT,
			"main-");
		productImages.add(main);

		// 추가 이미지 처리
		for (MultipartFile image : images) {
			ProductImage subImage = createImage(
				image,
				ImageSize.NORMAL_IMAGE_WIDTH,
				ImageSize.NORMAL_IMAGE_HEIGHT,
				"sub-");
			productImages.add(subImage);
		}

		return productImages;
	}

	public void updateMainImage(MultipartFile newImage, ProductImage mainImage, ProductImage thumbnail) throws IOException {
		removeImage(mainImage.getImageUrl());
		removeImage(thumbnail.getImageUrl());

		updateImage(newImage, mainImage, ImageSize.NORMAL_IMAGE_WIDTH, ImageSize.NORMAL_IMAGE_HEIGHT, "main-");
		updateImage(newImage, thumbnail, ImageSize.THUMBNAIL_IMAGE_WIDTH, ImageSize.THUMBNAIL_IMAGE_HEIGHT, "thumbnail");
	}
}
