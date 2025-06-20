public class King extends ChessPiece {  //наследуется от абстрактного класса ChessPiece

    public King(String color) {        //конструктор класса
        super(color);
    }

    @Override
    public String getColor() {
        return this.color;
    }

    public boolean checkPos(int pos) {   //вспомогательный метод для проверки нахождения короля в пределах доски
        if (pos >= 0 && pos <= 7)
            return true;
        else return false;
    }

    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        if (((checkPos(line)) && (checkPos(toLine)) && (checkPos(column)) && (checkPos(toColumn)) && ((line != toLine) && (column != toColumn)))) {
            //первое условие: стартуем с того, чтобы проверить, а не выходят ли за пределы поля наши координаты
            //и не остаёмся ли мы на одном месте, притом что мы можем двигаться или по горизонтали, или по вертикали, или по диагонали
            //поэтому должно выполняться условие одновременного несовпадания конечных и начальных строк и столбцов
            if (((Math.abs(line - toLine) != 1) || (Math.abs(column - toColumn) != 1)))  //обязательное условие - перемещение на 1 клетку в любом напрвлении
                return false;
            if (isUnderAttack(chessBoard, toLine, toColumn)) //король не должен находиться под атакой
                return false;
            if (chessBoard.board[toLine][toColumn] != null) { //не разрешить пройти, если там стоит фигура своего цвета
                return !chessBoard.board[toLine][toColumn].getColor().equals(color);
            }
            return true;
        } else return false;
    }

    public boolean isUnderAttack(ChessBoard chessBoard, int line, int column) { //вспомогательный метод
        //проверяем, находится ли поле, на котором стоит король (или куда собирается пойти), под атакой
        if ((checkPos(line)) && (checkPos(column))) {
            for (int i = 0; i < 7; i++) { //проходим по строкам
                for (int j = 0; j < 7; j++) { //проходим по столбцам
                    if (chessBoard.board[i][j] != null) { //если данная точка занята
                        if (((!chessBoard.board[i][j].getColor().equals(color)) && (chessBoard.board[i][j].canMoveToPosition(chessBoard, i, j, line, column)))) {
                            //если в точке фигура другого цвета и true по методу canMoveToPosition
                            return true;
                        }
                    }
                }
            }
            return false;
        } else return false;
    }

    @Override
    public String getSymbol() { //метод, который возвращает символ фигуры
        return "K";
    }

}
