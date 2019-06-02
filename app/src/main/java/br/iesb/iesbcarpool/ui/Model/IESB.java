package br.iesb.iesbcarpool.ui.Model;

public enum IESB {
    SUL(-15.834830, -47.912905, 1),
    NORTE( -15.757084, -47.877803, 2),
    OESTE(-15.809871, -48.125265, 3)

    ;
    private double latitude;
    private double longitude;
    private int id;

    IESB( double latitude, double longitude, int id) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.id = id;
    }


    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public int getId() {
        return this.id;
    }

    public static IESB findIESBById(Integer id) {
        IESB retorno = null;
        for(IESB iesb : IESB.values()){
            if (iesb.id == id){
                retorno=iesb;
                break;
            }
        }
        return retorno;
    }

}
