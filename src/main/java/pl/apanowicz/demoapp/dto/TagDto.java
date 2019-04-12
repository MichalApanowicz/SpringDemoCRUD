package pl.apanowicz.demoapp.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class TagDto {

    @NotNull(message = "Name cannot be null")
    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @JsonCreator
    public TagDto(@JsonProperty("name") String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
         this.name = name;
    }

    @Override
    public String toString() {
        return "TagDto{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagDto that = (TagDto) o;
        return Objects.equals(name, that.name);
    }
}
