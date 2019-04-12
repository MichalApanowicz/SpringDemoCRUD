package pl.apanowicz.demoapp.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.URL;
import pl.apanowicz.demoapp.domain.Image;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public final class ImageDto {

    @URL
    @NotEmpty(message = "Url cannot be empty")
    @NotNull(message = "Url cannot be null")
    private String url;

    @JsonCreator
    public ImageDto(@JsonProperty("url") String url){
        this.url = url;
    }
    public ImageDto(Image image){
        this.url = image.getUrl();
    }

    public String getUrl(){
        return this.url;
    }

    @Override
    public String toString() {
        return "ImageDto{" +
                "url='" + url + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageDto that = (ImageDto) o;
        return Objects.equals(url, that.url);
    }
}
