package zunza.myshop.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
import zunza.myshop.domain.ProductImage;

@Component
public class ImageUtil {

	public static final String PATH = System.getProperty("user.dir") + "/src/main/resources/static/images/";

	private byte[] resizeImage(MultipartFile file, int width, int height) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Thumbnails.of(new ByteArrayInputStream(file.getBytes()))
			.size(width, height)
			.toOutputStream(baos);
		return baos.toByteArray();
	}

	private String saveImage(byte[] imageBytes, String type) throws IOException {
		UUID uuid = UUID.randomUUID();
		Path path = Paths.get(PATH + type + uuid);
		Files.write(path, imageBytes);
		return PATH + type + uuid;
	}

	public List<ProductImage> convertToEntitiesWithResizeAndSave (
		MultipartFile mainImage,
		List<MultipartFile> images) throws IOException {

		List<ProductImage> productImages = new ArrayList<>();

		// 섬네일 이미지 처리
		byte[] thumbnailImage = resizeImage(mainImage, ImageSize.THUMBNAIL_IMAGE_WIDTH, ImageSize.THUMBNAIL_IMAGE_HEIGHT);
		String thumbnailUrl = saveImage(thumbnailImage, "thumbnail-");
		String thumbnailImageName = thumbnailUrl.split(PATH)[1];
		productImages.add(ProductImage.builder()
			.imageName(thumbnailImageName)
			.imageUrl(thumbnailUrl)
			.build());

		// 메인 이미지 처리
		byte[] resizedMainImage = resizeImage(mainImage, ImageSize.NORMAL_IMAGE_WIDTH, ImageSize.NORMAL_IMAGE_HEIGHT);
		String mainImageUrl = saveImage(resizedMainImage, "main-");
		String mainImageName = mainImageUrl.split(PATH)[1];
		productImages.add(ProductImage.builder()
			.imageName(mainImageName)
			.imageUrl(mainImageUrl)
			.build());

		// 추가 이미지 처리
		for (MultipartFile image : images) {
			byte[] resizedImage = resizeImage(image, ImageSize.NORMAL_IMAGE_WIDTH, ImageSize.NORMAL_IMAGE_HEIGHT);
			String subImageUrl = saveImage(resizedImage, "sub-");
			String subImageName = subImageUrl.split(PATH)[1];
			productImages.add(ProductImage.builder()
				.imageName(subImageName)
				.imageUrl(subImageUrl)
				.build());
		}

		return productImages;
	}
}
