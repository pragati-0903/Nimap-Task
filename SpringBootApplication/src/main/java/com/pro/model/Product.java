package com.pro.model;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Entity

public class Product {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long productid;
	    private String name;
	    private double price;

	    @ManyToOne
	    @JoinColumn(name = "category_id")
	    private Category category;

		public Long getProductid() {
			return productid;
		}

		public void setProductid(Long productid) {
			this.productid = productid;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public double getPrice() {
			return price;
		}

		public void setPrice(double price) {
			this.price = price;
		}

		public Category getCategory() {
			return category;
		}

		public void setCategory(Category category) {
			this.category = category;
		}


		
		

	
	    
}
