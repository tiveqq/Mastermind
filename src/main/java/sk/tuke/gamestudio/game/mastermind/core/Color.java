package sk.tuke.gamestudio.game.mastermind.core;

public enum Color {
    NONE, RED, GREEN, BLUE, YELLOW, ORANGE, VIOLET, SILVER, CHOCOLATE, PINK;

    public Color getColor(char color) {
        return switch (color) {
            case 'R' -> RED;
            case 'G' -> GREEN;
            case 'B' -> BLUE;
            case 'Y' -> YELLOW;
            case 'O' -> ORANGE;
            case 'V' -> VIOLET;
            case 'S' -> SILVER;
            case 'C' -> CHOCOLATE;
            case 'P' -> PINK;
            default -> null;
        };
    }

    public Object getCharColorConverting() {
        return switch (this) {
            case RED -> 'R';
            case GREEN -> 'G';
            case BLUE -> 'B';
            case YELLOW -> 'Y';
            case ORANGE -> 'O';
            case VIOLET -> 'V';
            case SILVER -> 'S';
            case NONE -> ' ';
            case CHOCOLATE -> 'C';
            case PINK -> 'P';
        };
    }

    public Object getCharColor() {
        return switch (this) {
            case RED -> "🔴";
            case GREEN -> "🟢";
            case BLUE -> "🔵";
            case YELLOW -> "🟡";
            case ORANGE -> "🟠";
            case VIOLET -> "🟣";
            case SILVER -> "🔘";
            case NONE -> "  ";
            case CHOCOLATE -> "🟤";
            case PINK -> "💖";
        };
    }
}

