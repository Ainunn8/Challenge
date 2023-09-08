package org.binaracademy;
import org.binaracademy.model.menu;
import org.binaracademy.model.pesanan;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean menuPesanan = true;
        List<pesanan> daftarPesanan = new ArrayList<>();
        StringBuilder struk = new StringBuilder();

        System.out.println("====================================");
        System.out.println("   Selamat datang di BinarFund");
        System.out.println("====================================");

        menu [] Menu1 = {
                new menu("Nasi Goreng \t", 15000),
                new menu("Mie Ayam \t", 13000),
                new menu("Nasi + Ayam \t", 18000),
                new menu("Es Teh Manis ", 5000),
                new menu("Es Jeruk \t", 5000)
        };

        while (menuPesanan) {
            System.out.println("Silahkan pilih makanan: ");
            for (int i = 0; i < Menu1.length; i++) {
                System.out.println((i + 1) + ". " + Menu1[i].getNamaMakanan() + "\t\t | Rp " + Menu1[i].getHargaMakanan());
            }

            System.out.println("99. Pesan dan Bayar");
            System.out.println("0. Keluar Aplikasi");
            System.out.print("Pilih menu : ");

            try {
                int pilihan = scanner.nextInt();
                if (pilihan >= 1 && pilihan <= Menu1.length) {
                    System.out.println("====================================");
                    System.out.println("\t\tBerapa pesanan anda");
                    System.out.println("====================================");
                    System.out.print("qty => ");
                    int jumlahBarang = scanner.nextInt();
                    if (jumlahBarang > 0) {
                        pesanan pesanan1 = new pesanan(Menu1[pilihan - 1], jumlahBarang);
                        daftarPesanan.add(pesanan1);
                        System.out.println("Pesanan " + pesanan1.getMenuPesanan().getNamaMakanan() + " sebanyak " + pesanan1.getJumlah() + " berhasil ditambahkan." );
                    } else {
                        throw new PengecualianJumlahException("Jumlah pesanan harus lebih dari 0.");
                    }
                } else if (pilihan == 99) {
                    pesanDanBayar(daftarPesanan,struk, scanner);
                    menuPesanan = false;
                } else if (pilihan == 0) {
                    menuPesanan = false;
                } else {
                    System.out.println("Pilihan tidak valid.");
                }
            } catch (InputMismatchException e) {
                System.out.println("=================================");
                System.out.println("Mohon masukkan input pilihan anda");
                System.out.println("=================================");
                System.out.println("(Y) untuk lanjut");
                System.out.println("(n) untuk keluar");
                String huruf = scanner.next();
                if (huruf.equals("Y") ) {
                    System.out.println("=============================");
                    System.out.println("Minimal 1 jumlah pesanan!");
                    System.out.println("=============================");
                }
            } catch (PengecualianJumlahException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("\nTerima kasih telah menggunakan layanan kami!");
        scanner.close();
    }
    public static void pesanDanBayar(List<pesanan> daftarPesanan, StringBuilder struk, Scanner scanner) {
        System.out.println("==========================================");
        System.out.println("\t\tKonfirmasi & Pembayaran");
        System.out.println("==========================================");

        double totalHarga = 0;
        for (pesanan pesanan1 : daftarPesanan) {
            struk.append(pesanan1.getMenuPesanan().getNamaMakanan()).append("\t\t").append(pesanan1.getJumlah()).append(" \t Rp ").append(pesanan1.getTotal()).append("\n");
            totalHarga += pesanan1.getTotal();
        }

        System.out.println(struk);
        System.out.println("----------------------------------------  +");
        System.out.println("Total \t\t\t\t\t\t " + totalHarga);

        System.out.print("Masukkan jumlah uang Anda: Rp ");
        double jumlahUang = scanner.nextDouble();

        if (jumlahUang < totalHarga) {
            System.out.println("Maaf, uang Anda tidak cukup.");
        } else {
            double kembalian = jumlahUang - totalHarga;
            System.out.println("Kembalian Anda: Rp " + kembalian);

            // Simpan struk ke file
            try {
                FileWriter writer = new FileWriter("struk_pembayaran.txt");
                writer.write("==========================================\n");
                writer.write("\t Binarfud\n");
                writer.write("==========================================\n");

                writer.write("Terima kasih sudah memesan di Binarfud\n");
                writer.write("Dibawah ini adalah pesanan anda\n\n");
                writer.write(struk.toString());
                writer.write("----------------------------------------- +\n");
                writer.write("Total \t\t\t\t\t\t Rp "+ totalHarga + "\n\n");
                writer.write("Pembayaran : BinarCash\n");
                writer.write("==========================================\n");
                writer.write("Simpan struk ini sebagai bukti pembayaran\n");
                writer.write("==========================================\n");
                writer.close();
                System.out.println("Struk pembayaran telah disimpan dalam file 'struk_pembayaran.txt'.");
            } catch (IOException e) {
                System.out.println("Terjadi kesalahan saat menyimpan struk pembayaran.");
            }
        }
    }
}


