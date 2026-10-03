package data;

import models.stays.Stay;

public final class DummyStays {
    private DummyStays() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    public static final Stay[] data = new Stay[] {
            new Stay("Grand Hyatt Jakarta",
                    "Jl. M.H. Thamrin No.Kav. 28-30, Gondangdia, Kec. Menteng, Jakarta, DKI Jakarta",
                    100, 3220908),
            new Stay("Pullman Jakarta Indonesia Thamrin CBD",
                    "Jl. M.H. Thamrin No.59, RT.9/RW.5, Gondangdia, Kec. Menteng, Kota Jakarta Pusat, DKI Jakarta",
                    100, 1885979),
            new Stay("Stanley Wahid Hasyim Jakarta",
                    "Jl. K.H. Wahid Hasyim No.65, RT.1/RW.4, Gondangdia, Kec. Menteng, Jakarta, DKI Jakarta",
                    100, 744883),

            new Stay("Hyatt Place Makassar",
                    "Jl. Jend. Sudirman No.31, Mangkura, Kec. Ujung Pandang, Kota Makassar, Sulawesi Selatan",
                    100, 1070720),
            new Stay("Four Points by Sheraton Makassar",
                    "Jl. Andi Djemma No.130, Banta-Bantaeng, Kec. Rappocini, Kota Makassar, Sulawesi Selatan",
                    100, 797294),
            new Stay("CLARO HOTEL MAKASSAR",
                    "Jl. A. P. Pettarani No.03, Mannuruki, Kec. Tamalate, Kota Makassar, Sulawesi Selatan",
                    100, 824672),

            new Stay("Swiss-Belhotel Papua, Jayapura",
                    "Pusat Bisnis Jayapura, Jl. Pacific Permai, Bayangkara, Kec. Jayapura Utara, Kota Jayapura, Papua",
                    100, 1094182),
            new Stay("Mercure Hotel Jayapura",
                    "Jl. Ahmad Yani No.12, Gurabesi, Kec. Jayapura Utara, Kota Jayapura, Papua",
                    100, 544480),
    };
}
