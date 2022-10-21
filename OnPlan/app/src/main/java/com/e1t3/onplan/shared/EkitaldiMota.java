package com.e1t3.onplan.shared;

public enum EkitaldiMota {
    OSPAKIZUNA,IKUSKIZUNA,HITZALDIA,ERAKUSKETA,BESTE_MOTA;

    public String toString(){
        switch (this){
            case OSPAKIZUNA:
                return "Ospakizuna";
            case IKUSKIZUNA:
                return "Ikuskizuna";
            case HITZALDIA:
                return "Hitzaldia";
            case ERAKUSKETA:
                return "Erakusketa";
            case BESTE_MOTA:
                return "Beste mota";
            default:
                return "Error";
        }
    }
}
