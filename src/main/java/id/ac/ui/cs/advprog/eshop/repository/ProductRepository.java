package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();

    public Product create(Product product) {
        productData.add(product);
        return product;
    }

    public Product edit(String id, Product editedProduct) {
        Product updatedProduct = findProductById(id);
        updatedProduct.setProductName(editedProduct.getProductName());
        updatedProduct.setProductQuantity(editedProduct.getProductQuantity());

        return updatedProduct;
    }

    public Product findProductById(String productId) {
        return productData.stream()
                .filter(product -> product.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Produk tidak ditemukan"));
    }

    public Iterator<Product> findAll() {
        return productData.iterator();
    }

    public boolean delete(String id) {
        for (Product product : productData) {
            if (product.getProductId().equals(id)) {
                productData.remove(product);
                return true;
            }
        }
        return false;
    }

}