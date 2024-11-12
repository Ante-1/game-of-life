package schwender.ante.game_of_life;

public record GameBoard(int width, int height, boolean[] cells) {
    public void printBoard() {
        for (int i = 0; i < height(); i++) {
            for (int j = 0; j < width(); j++) {
                if (cells()[i * width() + j]) {
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
