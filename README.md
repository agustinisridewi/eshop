

Agus Tini Sridewi / 2306276004 / ADPRO A

[![pmd](https://github.com/agustinisridewi/eshop/actions/workflows/pmd.yml/badge.svg)](https://github.com/agustinisridewi/eshop/actions/workflows/pmd.yml)
[![Scorecard supply-chain security](https://github.com/agustinisridewi/eshop/actions/workflows/scorecard.yml/badge.svg)](https://github.com/agustinisridewi/eshop/actions/workflows/scorecard.yml)
[![Continuous Integration (CI)](https://github.com/agustinisridewi/eshop/actions/workflows/ci.yml/badge.svg)](https://github.com/agustinisridewi/eshop/actions/workflows/ci.yml)

🔗 [Link E Shop](https://supporting-gayal-agustinisridewi-336f9427.koyeb.app/) 🔗

# Module 1
<details>
    <summary><strong> 📌Reflection 1: Clean Code Principles and Secure Coding Practices </strong></summary>

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
          .orElse(null);
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
</details>

<details>
    <summary><strong> 📌Reflection 2: Unit & Functional Testing </strong></summary>


### 1. Unit Testing
- Setelah menulis dan menjalankan Unit Test saya merasa sangat amat terbantu dalam menguji kode guna mendeteksi bug lebih awal.
  Hal ini membuat saya yakin akan fungsionalitas kode saya, sebelum digabungkan dengan fitur lainnya.

- Jumlah unit test bergantung pada kompleksitas kode, tetapi semakin banyak skenario yang diuji semakin baik antisipasi fungsionalitas kode.
  Maka dari itu, jika ada percabangan dalam kode, setiap cabang perlu diuji, termasuk skenario positif dan negatif, serta pengujian batas untuk menangani input ekstrem.

- 100% Code Coverage bukan berarti tidak Ada bug karena code coverage hanya menunjukkan bahwa semua baris kode telah dieksekusi dalam pengujian,
  tetapi tidak menjamin logikanya benar. Bug masih bisa terjadi jika pengujian tidak mencakup semua kemungkinan skenario atau jika ada interaksi dengan komponen eksternal seperti database atau API.

### 2. **Refleksi terhadap Functional Test Suite Baru**
Menambahkan functional test baru tanpa perencanaan dapat menyebabkan duplikasi kode dan menurunkan kualitas kode.
Jika setiap test suite memiliki konfigurasi yang sama, lebih baik membuat kelas abstrak atau utility yang dapat digunakan ulang agar kode tetap bersih dan mudah dipelihara.
Dengan cara ini, pengujian menjadi lebih efisien dan perubahan pada aplikasi lebih mudah dikelola.

</details>


# Module 2
<details>
    <summary><strong> 📌Reflection:  </strong></summary>

### 1. List the code quality issue(s) that you fixed during the exercise and explain your strategy on fixing them

1. UseUtilityClass (EshopApplication.java)

   Strategi -> Menambahkan konstruktor private untuk mencegah instansiasi.
2. Unnecessary Imports

   Strategi -> Menghapus import yang tidak digunakan di:

  - ProductController.java (Spring annotations)
  - ProductRepository.java (UUID import)
3. Unnecessary Modifier (ProductService.java)

   Strategi -> Menghapus keyword public yang tidak perlu pada metode dalam interface karena sudah public secara default.

### 2. Look at your CI/CD workflows (GitHub)/pipelines (GitLab). Do you think the current implementation has met the definition of Continuous Integration and Continuous Deployment?

Setelah melihat hasil CI/CD workflows, yang memastikan integrasi dan deployment berjalan otomatis dan efisien, saya percaya kode saya telah memenuhi definisi CI/CD.
Untuk CI, GitHub Actions digunakan untuk menjalankan test suite dengan Gradle serta melakukan code scanning dengan OSSF Scorecard dan PMD. Hal ini membantu menjaga kualitas kode dan mencegah potensi kesalahan sejak awal.
Sementara untuk CD, saya memanfaatkan Koyeb yang secara otomatis melakukan deployment setiap kali ada perubahan di branch utama. Dengan dukungan Dockerfile, saya dapat mengatur environment deployment sesuai kebutuhan, memastikan proses rilis berjalan lancar.
</details>


