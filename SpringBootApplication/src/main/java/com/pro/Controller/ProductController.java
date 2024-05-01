package com.pro.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	private final ProductService productService;
	@Autowired
	private CategoryService categoryService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping
	public ResponseEntity<Page<Product>> getAllProducts(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Product> products = productService.getAllProducts(pageable);
		return ResponseEntity.ok(products);
	}

	@PostMapping("/AddProduct")
	public Productdto createProduct(@RequestBody Productdto productDTO) {
		Product product = convertToProduct(productDTO);
		if (productDTO.getCategoryName() != null) {
			Category category = categoryService.findByName(productDTO.getCategoryName());
			if (category == null) {
			} else {
				product.setCategory(category);
				productDTO.setCategoryId(category.getCategoryid());
			}
		}
		Product createdProduct = productService.createProduct(product);
		return convertToProductDTO(createdProduct);
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

		Category category = product.getCategory();
		if (category != null) {
			productDTO.setCategoryName(category.getName());
		} else {
			productDTO.setCategoryName(null); 
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
