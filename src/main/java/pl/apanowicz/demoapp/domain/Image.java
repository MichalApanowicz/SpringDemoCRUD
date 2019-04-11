package pl.apanowicz.demoapp.domain;

import pl.apanowicz.demoapp.dto.ImageDto;

import java.util.Objects;

public final class Image {

    private String url;

    public Image(String url){
        this.url = url;
    }

    public Image(ImageDto image){
        if(image != null){
            this.url = image.getUrl();
        }
    }

    public String getUrl(){
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image that = (Image) o;
        return Objects.equals(url, that.url);
    }

    @Override
    public String toString() {
        return "image{" +
                "url='" + url + '\'' +
                '}';
    }
}
