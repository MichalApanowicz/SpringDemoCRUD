package pl.apanowicz.demoapp.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public final class Image {

    @URL
    @NotEmpty(message = "Url cannot be empty")
    @NotNull(message = "Url cannot be null")
    private final String url;

    @JsonCreator
    public Image(String url){
        this.url = url;
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
        return "Image{" +
                "url='" + url + '\'' +
                '}';
    }
}
