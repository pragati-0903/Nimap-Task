package com.pro.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pro.model.Category;
import com.pro.model.Product;
import com.pro.repository.CategoryRepository;
import com.pro.repository.ProductRepository;

@Service
public class ProductService {
	 @Autowired
	    private ProductRepository productRepository;
	 
	  public ProductService(ProductRepository productRepository) {
	        this.productRepository = productRepository;
	    }
	
	    public Product createProduct(Product product) {
	        return productRepository.save(product);
	    }
	  

		public Page<Product> getAllProducts(Pageable pageable) {
			return productRepository.findAll(pageable);
		}

	public Product getProductById(Long productid) {
		Optional <Product> p = productRepository.findById(productid);
		if (p.isPresent()) {
			return p.get();
		}
		return null;
	}


	public Product updateProduct(Long productid, Product product) {
		Optional<Product> optionalProduct = productRepository.findById(productid);
		 if (optionalProduct.isPresent()) {
			 Product existingProduct = optionalProduct.get(); 
			 existingProduct.setName(product.getName());
	            existingProduct.setPrice(product.getPrice());
	            return productRepository.save(existingProduct);
	        } else {
	            throw new NoSuchElementException("Product with id " + productid + " not found");
	        }
	}

	
	public void deleteProduct(Long productid) {
		
        if (productRepository.existsById(productid)) {
            productRepository.deleteById(productid);
        } else {
            throw new NoSuchElementException("Product with id " + productid + " not found");
        }

		
	}





}
