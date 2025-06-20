public class Queen extends ChessPiece {  //наследуется от абстрактного класса ChessPiece

    public Queen(String color) {        //конструктор класса
        super(color);
    }

    @Override
    public String getColor() {
        return this.color;
    }

    public boolean checkPos(int pos) {   //вспомогательный метод для проверки нахождения ферзи в пределах доски
        if (pos >= 0 && pos <= 7)
            return true;
        else return false;
    }

    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        //то, что ферзь (королева) ходит по диагонали и по прямой, означает, что логика её движения - это либо как слон, либо как ладья
        //в ходе такого ветвления с самого начала нужно убедиться, что ферзь не остаётся на месте и не выходит за пределы поля
        //копирую "слоновую" реализацию метода:
        if (((checkPos(line)) && (checkPos(toLine)) && (checkPos(column)) && (checkPos(toColumn))
                //стартуем с того, чтобы проверить, а не выходят ли за пределы поля наши координаты
                && (line != toLine) && (column != toColumn) && (((Math.abs(toLine - line)) == (Math.abs(toColumn - column))))
                //не отправили ли мы фигуру в точку, в которой она сейчас находится, и диагональ ли это (для диагонали дельта между строками должна быть равна
                //дельте между столбцами
                && (chessBoard.board[toLine][toColumn] == null || !chessBoard.board[toLine][toColumn].color.equals(this.color)) &&
                (chessBoard.board[line][column] != null))) { //если место занято
            if (!chessBoard.board[line][column].equals(this)) { //проверка того, не стоит ли фигура нашего цвета в данной позиции
                return false;
            }
            //субъективно - самая сложная часть реализации логики фигуры: то, что слон (в данном случае ферзь) движется в этом случае только по любой диагонали, написать несложно, а вот проверить, есть ли на пути
            //до конечной точки другие фигуры (любого цвета) и как-то это зафиксировать, без просмотра решения у меня вообще никак не получилось.
            //теперь идея проверки понятна: сначала определяем направление движения, а затем проверяем каждую входящую в наш интервал клетку. Записываем точки в массив.
            //ветвление по направлению движения фигуры - слева - направо вниз и наоборот
            if ((column == getMin(column, toColumn) && line == getMax(line, toLine)) ||
                    (toColumn == getMin(column, toColumn) && toLine == getMax(line, toLine))) {
                int fromL = getMax(line, toLine);
                int fromC = getMin(column, toColumn);
                int toL = getMin(line, toLine);
                int toC = getMax(column, toColumn);
                int[][] positions = new int[toC - fromC][1]; //наш двумерный массив целых чисел positions имеет toC - fromC строк и 1 столбец
                for (int i = 1; i < toC - fromC; i++) {
                    if (chessBoard.board[fromL - i][fromC + i] == null) {
                        positions[i - 1] = new int[]{fromL - i, fromC + i}; //совмещение инициализации массива и его объявления
                    } else if (!chessBoard.board[fromL - i][fromC + i].color.equals(this.color) && fromL - i == toLine) {
                        positions[i - 1] = new int[]{fromL - i, fromC + i};
                    } else {
                        return false;
                    }
                }
                return true;
            } else {
                int fromL = getMin(line, toLine);
                int fromC = getMin(column, toColumn);
                int toL = getMax(line, toLine);
                int toC = getMax(column, toColumn);
                int[][] positions = new int[toC - fromC][1];
                for (int i = 1; i < toC - fromC; i++) {
                    if (chessBoard.board[fromL + i][fromC + i] == null) {
                        positions[i - 1] = new int[]{fromL + i, fromC + i};
                    } else if (!chessBoard.board[fromL + i][fromC + i].color.equals(this.color) && fromL + i == toLine) {
                        positions[i - 1] = new int[]{fromL + i, fromC + i};
                    } else {
                        return false;
                    }
                }
                return true;
            }
            //далее код реализации логики ладьи:
        } else if (((checkPos(line)) && (checkPos(toLine)) && (checkPos(column)) && (checkPos(toColumn)))) {
            //первое условие: стартуем с того, чтобы проверить, а не выходят ли за пределы поля наши координаты
            //далее ветвление: если ферзь ходит по горизонтали
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
                        return false; //не разрешить пройти, если там стоит фигура своего цвета и эта фигура не сама ферзь
                    else
                        return !chessBoard.board[toLine][column].getColor().equals(this.color) && chessBoard.board[toLine][column] != this;
                    //иначе - вернуть true, если цвет не совпадает и в точке нет себя же
                } else return true;

                //если ферзь ходит по горизонтали
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
                    if (chessBoard.board[toLine][toColumn].getColor().equals(this.color) && chessBoard.board[toLine][toColumn] != this) //не разрешить пройти, если там стоит фигура своего цвета и эта фигура не сама ферзь
                        return false;
                    else
                        return !chessBoard.board[toLine][toColumn].getColor().equals(this.color) && chessBoard.board[toLine][toColumn] != this;
                    //иначе - вернуть true, если цвет не совпадает и в точке нет себя же
                } else return true;
            } else return false;
        } else return false;
    }

    @Override
    public String getSymbol() { //метод, который возвращает символ фигуры
        return "Q";
    }

    public int getMax(int a, int b) {        //вспомогательный метод
        return Math.max(a, b);
    }

    public int getMin(int a, int b) {        //вспомогательный метод
        return Math.min(a, b);
    }

}









