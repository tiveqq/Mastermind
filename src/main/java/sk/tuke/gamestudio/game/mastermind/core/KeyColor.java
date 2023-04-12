package sk.tuke.gamestudio.game.mastermind.core;

public enum KeyColor {
    NONE, WHITE, BLACK;

    public Object getCharKeyColor() {
        switch (this) {
            case NONE:
                return "\u2005" + " ";
            case WHITE:
                return "⚪️";
            case BLACK:
                return "⚫";
            default:
                return null;
        }
    }
}
