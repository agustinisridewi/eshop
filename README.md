Agus Tini Sridewi / 2306276004 / ADPRO A
## ---
# Reflection 1: clean code principles and secure coding practices

## Penerapan Clean Code Principles
### 1. **Pemisahan Tanggung Jawab (Separation of Concerns)**
Kode telah dipisahkan ke dalam beberapa lapisan:
- **Controller** (`ProductController.java`) menangani permintaan HTTP dan mengelola tampilan.
- **Service** (`ProductService.java`, `ProductServiceImpl.java`) menangani logika bisnis.
- **Repository** (`ProductRepository.java`) bertanggung jawab atas penyimpanan data.
- **Model** (`Product.java`) merepresentasikan entitas produk.

### 2. **Penamaan yang Bermakna**
- Metode dan variabel telah diberi nama yang jelas dan mencerminkan fungsinya.
- Contoh: `createProductPage()`, `editProductPost()`, `findProductById()` memudahkan pemahaman kode.

### 3. **Penggunaan Lombok untuk Enkapsulasi**
- Kelas `Product.java` menggunakan anotasi `@Getter` dan `@Setter`, sehingga kode lebih ringkas dan lebih mudah dibaca.

### 4. **Dependency Injection**
- `ProductController.java` menggunakan `@Autowired` untuk menyuntikkan `ProductService`, sehingga meningkatkan fleksibilitas dan pengujian kode.

---

## Penerapan Secure Coding Practices

### 1. **Mencegah NullPointerException dengan Optional atau Exception Handling**
- `findProductById()` sekarang melempar exception jika produk tidak ditemukan, untuk menghindari NullPointerException:
  ```java
  public Product findProductById(UUID productId) {
      return productData.stream()
          .filter(product -> product.getProductId().equals(productId))
          .findFirst()
          .orElseThrow(() -> new IllegalArgumentException("Produk tidak ditemukan"));
  }
  ```

### 2. **Menggunakan UUID untuk Mencegah Prediksi ID**
- UUID digunakan untuk `productId` alih-alih integer, sehingga lebih sulit ditebak oleh pengguna yang berniat jahat.

### 3. **Menghindari Eksposur Informasi Sensitif dalam Pesan Kesalahan**
- Tidak menampilkan stack trace atau detail error secara langsung ke pengguna. Sebagai gantinya, menggunakan exception handling global dengan `@ControllerAdvice`.

---

## Perbaikan yang Dapat Dilakukan

### 1. **Gunakan `ConcurrentHashMap` untuk Penyimpanan Data**
**Masalah:**
- `ProductRepository` menggunakan `ArrayList<Product>`, yang tidak aman untuk lingkungan multi-threaded.

**Solusi:**
- Ganti dengan `ConcurrentHashMap<UUID, Product>` untuk meningkatkan performa dan keamanan:
  ```java
  private Map<UUID, Product> productData = new ConcurrentHashMap<>();
  ```

Dengan perbaikan ini, kode lebih bersih, aman, dan efisien. 🚀

