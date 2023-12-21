package com.hahow.tung.constant;

public class MyTest {

	public static void main(String[] args) {
		// enum類型的測試
		ProductCategory category = ProductCategory.FOOD;
		String s = category.name();
		System.out.println(s); 
		// 會印出FOOD
		
		String s2 = "CAR";
		ProductCategory category2 = ProductCategory.valueOf(s2);
		System.out.println(category2);
		// 如果ProductCategory裡面有"CAR"的話，就會印出"CAR"

	}

}
