public class Horse extends ChessPiece{ //наследуется от абстрактного класса ChessPiece

    public Horse(String color){        //конструктор класса
        super(color);
    }

    @Override
    public String getColor(){
        return this.color;
    }

    public boolean checkPos(int pos) {   //вспомогательный метод для проверки нахождения коня в пределах доски
        if (pos >= 0 && pos <= 7)
            return true;
        else return false;
    }

    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        if (((checkPos(line)) && (checkPos(toLine)) && (checkPos(column)) && (checkPos(toColumn)))) {
            //первое условие: стартуем с того, чтобы проверить, а не выходят ли за пределы поля наши координаты
            if (((line != toLine) && (column != toColumn)) && (chessBoard.board[toLine][toColumn] == null) ||
                    //при выполнении первого условия переходим к проверке второго: не отправили ли мы фигуру в точку,
                    //в которой она сейчас находится, и нет ли там фигуры нашего цвета
                    ((!chessBoard.board[toLine][toColumn].color.equals(this.color)) && (chessBoard.board[line][column] != null))) {
                if (!chessBoard.board[line][column].equals(this)) {
                    return false;
                }
                if ((Math.abs(toLine - line)) + (Math.abs(toColumn - column)) == 3) {
                    return true;
                    // третье условие: проверка хода коня буквой "г": происходят ли 2 шага по горизонтали и 1 по вертикали и наоборот
                }
            }
        } else return false;
    return false;
    }


    @Override
    public String getSymbol() { //метод, который возвращает символ фигуры
        return "H";
    }

}


