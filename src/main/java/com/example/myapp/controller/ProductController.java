package com.example.myapp.controller;

import com.example.myapp.model.Product;
import com.example.myapp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    // Display the product list in the web interface
    @GetMapping
   public String getAllProducts(Model model) {
    List<Product> products = productRepository.findAll();
    model.addAttribute("products", products);
    return "index";  // Return to index.html (Thymeleaf template)
}

    // Show add product form in the web interface
    @GetMapping("/add")
   public String showAddProductForm(Model model) {
    model.addAttribute("product", new Product());  // Pass a blank Product object for form binding
    return "addProduct";  // Return to addProduct.html (Thymeleaf template)
}

    // Create a new product via the web form
    @PostMapping
    public String createProduct(@ModelAttribute Product product) {
        productRepository.save(product);
        return "redirect:/products";  // Redirect back to the product list page
    }

    // Show edit product form in the web interface
    @GetMapping("/{id}")
    public String showEditProductForm(@PathVariable Long id, Model model) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isPresent()) {
            model.addAttribute("product", productOpt.get());  // Pass the existing product for editing
            return "addProduct";  // Reuse addProduct.html for editing
        }
        return "redirect:/products";  // If product not found, redirect to list
    }

    // Delete a product via the web interface
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
        return "redirect:/products";  // Redirect back to the product list page
    }

    // ---------- New REST API Endpoints (JSON Responses) ----------

    // Return the list of products in JSON format
    @GetMapping("/api")
    @ResponseBody  // Ensures JSON output
    public List<Product> getAllProductsApi() {
        return productRepository.findAll();
    }

    // Create a new product via API
    @PostMapping("/api")
    @ResponseBody
    public Product createProductApi(@RequestBody Product product) {
        return productRepository.save(product);  // Return the saved product as JSON
    }

    // Get a single product by ID via API
    @GetMapping("/api/{id}")
    @ResponseBody
    public Product getProductByIdApi(@PathVariable Long id) {
        return productRepository.findById(id).orElse(null);
    }

    // Update a product via API
    @PutMapping("/api/{id}")
    @ResponseBody
    public Product updateProductApi(@PathVariable Long id, @RequestBody Product updatedProduct) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isPresent()) {
            Product existingProduct = productOpt.get();
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setPrice(updatedProduct.getPrice());
            return productRepository.save(existingProduct);  // Save the updated product and return it as JSON
        }
        return null;  // You might want to handle the case where the product does not exist
    }

    // Delete a product by ID via API
    @DeleteMapping("/api/{id}")
    @ResponseBody
    public void deleteProductApi(@PathVariable Long id) {
        productRepository.deleteById(id);
    }
}
