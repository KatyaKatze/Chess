public class Rook extends ChessPiece {   //наследуется от абстрактного класса ChessPiece

    public Rook(String color) {        //конструктор класса
        super(color);
    }

    @Override
    public String getColor() {
        return this.color;
    }

    public boolean checkPos(int pos) {   //вспомогательный метод для проверки нахождения ладьи в пределах доски
        if (pos >= 0 && pos <= 7)
            return true;
        else return false;
    }

    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        if (((checkPos(line)) && (checkPos(toLine)) && (checkPos(column)) && (checkPos(toColumn)))) {
            //первое условие: стартуем с того, чтобы проверить, а не выходят ли за пределы поля наши координаты
            //далее ветвление: если ладья ходит по горизонтали
            if ((column == toColumn) && (line != toLine)) {
                for (int i = getMin(line, toLine); i < getMax(line, toLine); i++) { //опять же подсмотрела идею с перебором по заданному интервалу
                    if (chessBoard.board[i][column] != null) {
                        if (chessBoard.board[i][column] == this && i == getMax(line, toLine))
                            return false;
                        if (chessBoard.board[i][column].getColor().equals(this.color) && i == toLine) //не может пройти через фигуру своего цвета
                            return false;
                        else if (!chessBoard.board[i][column].getColor().equals(this.color) && i == toLine) //может съесть фигуру другого цвета, если она в этой точке
                            return true;
                    }
                }
                if (chessBoard.board[toLine][column] != null) { //если конечная точка занята
                    if (chessBoard.board[toLine][column].getColor().equals(this.color) && chessBoard.board[toLine][column] != this)
                        return false; //не разрешить пройти, если там стоит фигура своего цвета и эта фигура не сама ладья
                    else
                        return !chessBoard.board[toLine][column].getColor().equals(this.color) && chessBoard.board[toLine][column] != this;
                    //иначе - вернуть true, если цвет не совпадает и в точке нет себя же
                } else return true;

                //если ладья ходит по горизонтали
            } else if (((line == toLine) && (column != toColumn))) {
                for (int i = getMin(toColumn, column); i < getMax(column, toColumn); i++) {
                    if (chessBoard.board[line][i] != null) {
                        if (chessBoard.board[line][i] == this && i == getMax(column, toColumn))
                            return false;
                        else if (chessBoard.board[line][i].getColor().equals(this.color) && i == toColumn) //не может пройти через фигуру своего цвета
                            return false;
                        else if (!chessBoard.board[line][i].getColor().equals(this.color) && i == toColumn) //может съесть фигуру другого цвета, если она в этой точке
                            return true;
                    }
                }

                if (chessBoard.board[toLine][toColumn] != null) { //если координата(точка) занята
                    if (chessBoard.board[toLine][toColumn].getColor().equals(this.color) && chessBoard.board[toLine][toColumn] != this) //не разрешить пройти, если там стоит фигура своего цвета и эта фигура не сама ладья
                        return false;
                    else
                        return !chessBoard.board[toLine][toColumn].getColor().equals(this.color) && chessBoard.board[toLine][toColumn] != this;
                    //иначе - вернуть true, если цвет не совпадает и в точке нет себя же
                } else return true;
            } else return false;
        } else return false;
    }

    @Override
    public String getSymbol () {    //метод, который возвращает символ фигуры
        return "R";
    }

    public int getMax(int a, int b) {   //вспомогательный метод
        return Math.max(a, b);
    }

    public int getMin(int a, int b) {   //вспомогательный метод
        return Math.min(a, b);
    }
}