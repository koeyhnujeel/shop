package zunza.myshop.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Category {
	TOP("상의", "001"),
	KNIT("니트", "001001"),
	SWEATSHIRT("맨투맨", "001002"),
	HOODIE("후드티", "001003"),
	SHIRT("셔츠", "001004"),

	PANTS("바지", "002"),
	DENIM_PANTS("청바지", "002001"),
	COTTON_PANTS("면바지", "002002"),
	SLACKS_PANTS("슬랙스", "002003"),

	OUTER("아우터", "003"),
	CARDIGAN("가디건", "003001"),
	COAT("코트", "003002"),
	PADDING("패딩", "003003"),

	SHOES("신발", "004"),
	CANVAS("캔버스", "004001"),
	DRESS_SHOES("구두", "004002"),
	SLIPPER("슬리퍼", "004003"),

	BAG("가방", "005001"),
	BACKPACK("백팩", "005002"),
	SHOULDER_BAG("숄더백", "005003"),

	HEADWEAR("모자", "006"),
	CAP("캡", "006001"),
	BEANIE("비니", "006002");

	private final String name;
	private final String code;

	Category(String name, String code) {
		this.name = name;
		this.code = code;
	}

	@JsonValue
	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}

	@JsonCreator
	public static Category from(String value) {
		for (Category category : Category.values()) {
			if (category.getName().equals(value)) {
				return category;
			}
		}
		return null;
	}
}
