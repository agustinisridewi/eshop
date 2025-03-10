package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();

    public Product create(Product product) {
        productData.add(product);
        return product;
    }

    public Product edit(String id, Product editedProduct) {
        Product updatedProduct = findProductById(id);
        if (updatedProduct == null) {
            return null; // If product is not found, return null
        }
        updatedProduct.setProductName(editedProduct.getProductName());
        updatedProduct.setProductQuantity(editedProduct.getProductQuantity());
        return updatedProduct;
    }

    public Product findProductById(String productId) {
        return productData.stream()
                .filter(product -> product.getProductId().equals(productId))
                .findFirst()
                .orElse(null);
    }

    public Iterator<Product> findAll() {
        return productData.iterator();
    }

    public void delete (Product product) {
        productData.remove(product);
    }

}