public class Pawn extends ChessPiece { //наследуется от абстрактного класса ChessPiece

    public Pawn(String color) {        //конструктор класса
        super(color);
    }

    @Override
    public String getColor() {
        return this.color;
    }

    public boolean checkPos(int pos) {   //вспомогательный метод для проверки нахождения пешки в пределах доски
        if (pos >= 0 && pos <= 7)
            return true;
        else return false;
    }

    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        if (((checkPos(line)) && (checkPos(toLine)) && (checkPos(column)) && (checkPos(toColumn)) && (line != toLine))) {
            //первое условие: стартуем с того, чтобы проверить, а не выходят ли за пределы поля наши координаты
            //и не отправили ли мы фигуру в точку, в которой она сейчас находится
            if (column == toColumn) { // далее ветвление по поводу ест ли наша фигура, или просто делает ход: если ест,
                // то по диагонали, а это значит изменение column на 1 клетку
                int addLine; //вспомогательные переменные для того,
                int dir;  // чтобы обыграть возможность первый ход сделать на 1 или 2 клетки вперёд

                if (color.equals("White")) {  // в случае белой пешки старт с 1 строки, ход вперёд
                    dir = 1;
                    addLine = 1; //не забываем, что работаем с индексами
                } else { // в случае с чёрной пешки старт с 8 строки и ход в противоположном направлении
                    dir = -1;
                    addLine = 6; // не забываем, что работаем с индексами
                }

                if (line + dir == toLine) {  //проверка возможности хода на 1 клетку вперёд
                    return chessBoard.board[toLine][toColumn] == null;
                }

                if (line == addLine && line + 2 * dir == toLine) {
                    return chessBoard.board[toLine][toColumn] == null && chessBoard.board[line + dir][column] == null; // проверка возможности хода на 2 клетки вперёд
                }
            } else { // ветвление на случай "еды" пешкой

                if (((Math.abs(toLine - line)) == 1 && (Math.abs(toColumn - column)) == 1 && (chessBoard.board[toLine][toColumn] != null))) {
                    return !chessBoard.board[toLine][toColumn].getColor().equals(color);
                } else return false;
            }
        }
        return false;
    }

    @Override
    public String getSymbol () { //метод, который возвращает символ фигуры
        return "P";
    }
}