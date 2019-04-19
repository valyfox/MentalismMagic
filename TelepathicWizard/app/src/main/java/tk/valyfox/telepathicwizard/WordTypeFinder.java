package tk.valyfox.telepathicwizard;

public class WordTypeFinder {

    public static int getWordType(String word) {

        Character c = Character.toUpperCase(word.charAt(0));
        switch (c) {
            case 'A':
                return 0;
            case 'B':
                return 1;
            case 'C':
                return 1;
            case 'D':
                return 1;
            case 'E':
                return 0;
            case 'F':
                return 0;
            case 'G':
                return 1;
            case 'H':
                return 0;
            case 'I':
                return 0;
            case 'J':
                return 1;
            case 'K':
                return 0;
            case 'L':
                return 0;
            case 'M':
                return 0;
            case 'N':
                return 0;
            case 'O':
                return 1;
            case 'P':
                return 1;
            case 'Q':
                return 1;
            case 'R':
                return 1;
            case 'S':
                return 1;
            case 'T':
                return 0;
            case 'U':
                return 1;
            case 'V':
                return 0;
            case 'W':
                return 0;
            case 'X':
                return 0;
            case 'Y':
                return 0;
            case 'Z':
                return 0;
            default:
                return -1;
        }
    }
}
