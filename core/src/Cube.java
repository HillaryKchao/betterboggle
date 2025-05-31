import java.util.Arrays;
import java.util.Random;

public class Cube {
    private String[] letters;
    private String displayedLetter;

    public Cube(String[] letters) {
        this.letters = letters;
    }

    public String getDisplayedLetter() {
        return this.displayedLetter;
    }

    public void setDisplayedLetter(String letter) {
        this.displayedLetter = letter;
    }

    public void roll() {
        Random rand = new Random();
        int index = rand.nextInt(0, 6);
        setDisplayedLetter(this.letters[index]);
    }

    public Letter toLetter() {
        return new Letter(this.displayedLetter);
    }

    @Override
    public String toString() {
        return Arrays.toString(this.letters);
    }
}
