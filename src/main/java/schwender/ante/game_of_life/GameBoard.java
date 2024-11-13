package schwender.ante.game_of_life;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

public record GameBoard(
        @NotNull @Max(2000) Integer width,
        @NotNull @Max(2000) Integer height,
        @NotNull int[] cells) {

    public void printBoard() {
        for (int i = 0; i < height(); i++) {
            for (int j = 0; j < width(); j++) {
                if (cells()[i * width() + j] == 1) {
                    System.out.print("X");
                } else {
                    System.out.print(".");
                }
                if (j == width() - 1) {
                    System.out.println();
                }
            }
        }
        System.out.println();
    }
}
