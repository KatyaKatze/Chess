public abstract class ChessPiece {
    String color;               //поля класса
    boolean check = true;

    public ChessPiece(String color){
        this.color = color;     //конструктор класса
    }

    public abstract String getColor();         //методы, которые объявляются; без реализации
    public abstract boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn);
    public abstract String getSymbol();



}
