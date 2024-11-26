package schwender.ante.game_of_life.persistance;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Arrays;

@Entity
// @Table(name = "...") specify the name of the table that corresponds to the entity
// I don't use it here because I let hibernate generate the database
// (see spring.jpa.hibernate.ddl-auto in application.properties)
public class GameBoardEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @NotEmpty String name;
    private @NotNull @Min(0) @Max(2000) Integer width;
    @NotNull @Min(0) @Max(2000) Integer height;
    @NotNull @Column(length=2000*2000) private byte[] cells;

    protected GameBoardEntity() {}

    public GameBoardEntity(String name, Integer width, Integer height, @NotNull byte[] cells) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.cells = cells;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public @NotNull @Max(2000) Integer getWidth() {
        return width;
    }

    public void setWidth(@NotNull @Max(2000) Integer width) {
        this.width = width;
    }

    public @NotNull @Max(2000) Integer getHeight() {
        return height;
    }

    public void setHeight(@NotNull @Max(2000) Integer height) {
        this.height = height;
    }

    @NotNull
    public byte[] getCells() {
        return cells;
    }

    public void setCells(@NotNull byte[] cells) {
        this.cells = cells;
    }

    public @NotEmpty String getName() {
        return name;
    }

    public void setName(@NotEmpty String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "GameBoardEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", cells=" + Arrays.toString(cells) +
                '}';
    }
}