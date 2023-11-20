package com.dhandev.rekapin.utils

enum class TransactionCategory {
    Food {
        override fun toString() = "Makan & Minum"
    },
    Transportation {
        override fun toString() = "Transportasi"
    },
    Donation {
        override fun toString() = "Donasi"
    },
    Bills {
        override fun toString() = "Tagihan"
    },
    Health {
        override fun toString() = "Kesehatan"
    },
    Exercise {
        override fun toString() = "Olahraga"
    },
    Education {
        override fun toString() = "Edukasi"
    },
    Income {
        override fun toString() = "Gaji"
    },
    Gift {
        override fun toString() = "Hadiah"
    }
}