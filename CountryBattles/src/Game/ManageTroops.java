package Game;

public class ManageTroops {
    private String country1;
    private String country2;
    private int[] troops1;
    private int[] troops2;
    private Object troopsData;
    private Object countryCodeData;
    public ManageTroops(String country1, String country2, Object[] data){
        this.country1 = country1;
        this.country2 = country2;
        this.troopsData = data[2];
        this.countryCodeData = data[1]
    }
    public void CreateTroops(){

    }
}
