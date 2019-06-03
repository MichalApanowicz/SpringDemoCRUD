package pl.apanowicz.demoapp.domain;

public class ProductSearchParameters {
    private final String tag;

    public ProductSearchParameters(String tag){
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }
}
