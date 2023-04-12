package sk.tuke.gamestudio.game.mastermind.core;

public enum Color {
    NONE, RED, GREEN, BLUE, YELLOW, ORANGE, VIOLET, SILVER, HAZEL, PINK;

    public Color getColor(char color) {
        switch (color) {
            case 'R':
                return RED;
            case 'G':
                return GREEN;
            case 'B':
                return BLUE;
            case 'Y':
                return YELLOW;
            case 'O':
                return ORANGE;
            case 'V':
                return VIOLET;
            case 'S':
                return SILVER;
            case 'H':
                return HAZEL;
            case 'P':
                return PINK;
            default:
                return null;
        }
    }

    public Object getCharColorConverting() {
        switch (this) {
            case RED:
                return 'R';
            case GREEN:
                return 'G';
            case BLUE:
                return 'B';
            case YELLOW:
                return 'Y';
            case ORANGE:
                return 'O';
            case VIOLET:
                return 'V';
            case SILVER:
                return 'S';
            case NONE:
                return ' ';
            case HAZEL:
                return 'H';
            case PINK:
                return 'P';
            default:
                return null;
        }
    }

    public Object getCharColor() {
        switch (this) {
            case RED:
                return "🔴";
            case GREEN:
                return "🟢";
            case BLUE:
                return "🔵";
            case YELLOW:
                return "🟡";
            case ORANGE:
                return "🟠";
            case VIOLET:
                return "🟣";
            case SILVER:
                return "🔘";
            case NONE:
                return "  ";
            case HAZEL:
                return "🟤";
            case PINK:
                return "💖";
            default:
                return null;
        }
    }
}

