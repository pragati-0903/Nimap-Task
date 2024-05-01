package com.pro.Controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pro.dto.Productdto;
import com.pro.model.Category;
import com.pro.model.Product;
import com.pro.service.CategoryService;
import com.pro.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;
	
	
	@PostMapping("/AddProduct")
	public Productdto createProduct(@RequestBody Productdto productDTO) {
	    Product product = convertToProduct(productDTO);
	    // If categoryName is provided, find the corresponding Category by name
	    if (productDTO.getCategoryName() != null) {
	        Category category = categoryService.findByName(productDTO.getCategoryName());
	        if (category == null) {
	            // Handle case where category does not exist
	            // You can throw an exception, log a message, or handle it as needed
	        } else {
	            // Set the found Category in the Product
	            product.setCategory(category);
	            // Set the category ID in the ProductDTO for response
	            productDTO.setCategoryId(category.getCategoryid());
	        }
	    }
	    // Save the Product in the database
	    Product createdProduct = productService.createProduct(product);

	    // Convert the created Product back to ProductDTO and return it
	    return convertToProductDTO(createdProduct);
	}

	
	    @GetMapping
	    public ResponseEntity<List<Product>> getAllProducts() {
	        List<Product> products = productService.getAllProducts();
	        return ResponseEntity.ok(products);
	    }

  
	    @GetMapping("/{productid}")
		public Productdto getProductById(@PathVariable Long productid) {
			Product product = productService.getProductById(productid);
			return convertToProductDTO(product);
		}




	    @PutMapping("/{productid}")
		public Productdto updateProduct(@PathVariable Long productid, @RequestBody Productdto productDTO) {
			Product product = convertToProduct(productDTO);
			Product updatedProduct = productService.updateProduct(productid, product);
			return convertToProductDTO(updatedProduct);
		}

	    @DeleteMapping("/{productid}")
		public String deleteProduct(@PathVariable Long productid) {
			productService.deleteProduct(productid);
			return "Delete SuccessFully...!";
		}

	    private Productdto convertToProductDTO(Product product) {
	        Productdto productDTO = new Productdto();
	        productDTO.setProductid(product.getProductid());
	        productDTO.setName(product.getName());
	        productDTO.setPrice(product.getPrice());
	        

	        // Check if category is not null before accessing its name
	        Category category = product.getCategory();
	        if (category != null) {
	            productDTO.setCategoryName(category.getName());
	        } else {
	            productDTO.setCategoryName(null); // Or any other handling you prefer for null categories
	        }

	        return productDTO;
	    }

		private Product convertToProduct(Productdto productDTO) {
			Product product = new Product();
			product.setProductid(productDTO.getProductid());
			product.setName(productDTO.getName());
			product.setPrice(productDTO.getPrice());

			return product;
		}
}
