# travel-cli

Proyek Java sederhana untuk tugas kelompok, dimana kita ditugaskan untuk
mengembangkan aplikasi pemesanan perjalanan berbasis konsol Java yang
terinspirasi dari platform seperti Traveloka atau Tiket.com.

## Fitur Aplikasi

- [x] **Pencarian Penerbangan:** Pengguna dapat mencari jadwal pesawat
      berdasarkan kota asal, tujuan, tanggal, dan jumlah penumpang, serta
      melihat rincian ketersediaan penerbangan.

- [ ] **Pencarian Hotel:** Pengguna dapat mencari ketersediaan hotel berdasarkan
      kota, tanggal check-in/check-out, dan jumlah tamu beserta rincian
      fasilitas dan harganya.

- [ ] **Pemesanan Penerbangan:** Pengguna memasukkan data penumpang untuk
      memesan penerbangan yang dipilih, lalu sistem akan menerbitkan nomor
      konfirmasi pemesanan acak 6 digit.

- [ ] **Pemesanan Hotel:** Pengguna memesan hotel berdasarkan ID, memasukkan
      data tamu, dan menerima nomor konfirmasi reservasi hotel.

- [ ] **Pembatalan Reservasi:** Pengguna dapat membatalkan pesanan penerbangan
      ataupun hotel dengan menginput nomor konfirmasi.

- [ ] **Lihat Semua Pemesanan (Opsional):** Sistem menampilkan daftar seluruh
      pesanan saat ini milik pengguna beserta detail dan nomor konfirmasinya.

- [ ] **Validasi dan Penanganan Kesalahan (_Error Handling_):** Program wajib
      menangani input tidak valid dengan baik, seperti menangkap error saat
      pengguna memasukkan huruf pada kolom angka atau mencari ID yang tidak
      tersedia, lalu meminta input ulang.

## Prasyarat

- JDK v17+

Bisa di install di: <https://adoptium.net/temurin/releases>

## Cara Pakai

1. Cukup jalankan perintah berikut:

   ```bash
   # Compile
   javac -d bin -sourcepath src src/Main.java

   # Run
   java -cp bin Main
   ```
