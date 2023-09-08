package org.binaracademy.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class pesanan {
    private menu menuPesanan;
    private int jumlah;

    public double getTotal() {
        return menuPesanan.getHargaMakanan() * jumlah;
    }

}