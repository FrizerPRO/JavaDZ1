package org.example;

import java.util.ArrayList;
//Сначала горизонтальная, потом вертикальная.
public class GameBoard {
    private ArrayList<ArrayList<CellStatus>> currentField = new ArrayList<ArrayList<CellStatus>>(8);

    public GameBoard() {
        for (int i = 0; i < 8; i++) {
            currentField.add(new ArrayList<CellStatus>(8));
            for (int j = 0; j < 8; j++) {
                currentField.get(i).add(j, CellStatus.None);
            }
        }
        addNewMove(PlayerColor.Black, new Coordinates(3, 3));
        addNewMove(PlayerColor.Black, new Coordinates(4, 4));
        addNewMove(PlayerColor.White, new Coordinates(4, 3));
        addNewMove(PlayerColor.White, new Coordinates(3, 4));
    }

    public GameBoard(GameBoard original) {
        currentField = new ArrayList<>();
        for (var cell :
                original.currentField) {
            currentField.add(new ArrayList<>(cell));
        }
    }

    public ArrayList<Coordinates> getAllMoves(PlayerColor color) {
        ArrayList<Coordinates> result = new ArrayList<>();
        CellStatus cellStatus = switch (color) {
            case White -> CellStatus.PossibleForWhite;
            case Black -> CellStatus.PossibleForBlack;
        };

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (currentField.get(i).get(j) == cellStatus || currentField.get(i).get(j) == CellStatus.PossibleForBoth) {
                    result.add(new Coordinates(i, j));
                }
            }
        }
        return result;
    }

    public Double addNewMove(PlayerColor color, Coordinates coordinates) {
        int x = coordinates.getFirst();
        int y = coordinates.getSecond();
        currentField.get(x).set(y, color == PlayerColor.Black ? CellStatus.Black : CellStatus.White);
        Double sum = refactorTableForCell(color, new Coordinates(x, y), true);
        clearTablePartly();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (currentField.get(i).get(j) == CellStatus.White) {
                    refactorTableForCell(PlayerColor.White, new Coordinates(i, j), false);
                } else if (currentField.get(i).get(j) == CellStatus.Black) {
                    refactorTableForCell(PlayerColor.Black, new Coordinates(i, j), false);
                }
            }
        }
        return sum;
    }

    private void clearTablePartly() {
        for (var row :
                currentField) {
            for (int i = 0; i < 8; i++) {
                if (row.get(i) != CellStatus.White && row.get(i) != CellStatus.Black) {
                    row.set(i, CellStatus.None);
                }
            }
        }
    }

    private Double refactorTableForCell(PlayerColor color, Coordinates coordinates, boolean makeRecolor) {
        int x = coordinates.getFirst();
        int y = coordinates.getSecond();
        CellStatus ourColor = CellStatus.None, oppositeColor = CellStatus.None, ourPossibleColor = CellStatus.None, oppositePossibleColor = CellStatus.None;
        switch (color) {
            case Black -> {
                ourColor = CellStatus.Black;
                oppositeColor = CellStatus.White;
                ourPossibleColor = CellStatus.PossibleForBlack;
                oppositePossibleColor = CellStatus.PossibleForWhite;
            }
            case White -> {
                ourColor = CellStatus.White;
                oppositeColor = CellStatus.Black;
                ourPossibleColor = CellStatus.PossibleForWhite;
                oppositePossibleColor = CellStatus.PossibleForBlack;
            }
        }
        boolean didMetOpposite = false;
        ArrayList<Coordinates> allPossibleCells = new ArrayList<>();
        ArrayList<Coordinates> cellsToRecolor = new ArrayList<>();
        ArrayList<Coordinates> intermediateCells = new ArrayList<>();
        for (int i = x + 1; i < 8; i++) {
            if (currentField.get(i).get(y) != oppositeColor) {
                if (didMetOpposite) {
                    allPossibleCells.add(new Coordinates(i, y));
                    if (currentField.get(i).get(y) == ourColor) {
                        cellsToRecolor.addAll(intermediateCells);
                    }
                }
                break;
            } else {
                intermediateCells.add(new Coordinates(i, y));
                didMetOpposite = true;
            }
        }
        intermediateCells.clear();
        didMetOpposite = false;
        for (int i = x - 1; i >= 0; i--) {
            if (currentField.get(i).get(y) != oppositeColor) {
                if (didMetOpposite) {
                    if (currentField.get(i).get(y) == ourColor) {
                        cellsToRecolor.addAll(intermediateCells);
                    }
                    allPossibleCells.add(new Coordinates(i, y));
                }

                break;
            } else {
                intermediateCells.add(new Coordinates(i, y));
                didMetOpposite = true;
            }
        }
        intermediateCells.clear();
        didMetOpposite = false;
        for (int i = y + 1; i < 8; i++) {
            if (currentField.get(x).get(i) != oppositeColor) {
                if (didMetOpposite) {
                    if (currentField.get(x).get(i) == ourColor) {
                        cellsToRecolor.addAll(intermediateCells);
                    }
                    allPossibleCells.add(new Coordinates(x, i));
                }
                break;
            } else {
                intermediateCells.add(new Coordinates(x, i));
                didMetOpposite = true;
            }
        }
        intermediateCells.clear();
        didMetOpposite = false;
        for (int i = y - 1; i >= 0; i--) {
            if (currentField.get(x).get(i) != oppositeColor) {
                if (didMetOpposite) {
                    if (currentField.get(x).get(i) == ourColor) {
                        cellsToRecolor.addAll(intermediateCells);
                    }
                    allPossibleCells.add(new Coordinates(x, i));
                }

                break;
            } else {
                intermediateCells.add(new Coordinates(x, i));
                didMetOpposite = true;
            }
        }
        intermediateCells.clear();
        didMetOpposite = false;
        for (int i = 1; x + i < 8 && y + i < 8; i++) {
            if (currentField.get(x + i).get(y + i) != oppositeColor) {
                if (didMetOpposite) {
                    if (currentField.get(x + i).get(y + i) == ourColor) {
                        cellsToRecolor.addAll(intermediateCells);
                    }
                    allPossibleCells.add(new Coordinates(x + i, y + i));
                }

                break;
            } else {
                intermediateCells.add(new Coordinates(x + i, y + i));
                didMetOpposite = true;
            }
        }
        intermediateCells.clear();
        didMetOpposite = false;
        for (int i = 1; x - i >= 0 && y - i >= 0; i++) {
            if (currentField.get(x - i).get(y - i) != oppositeColor) {
                if (didMetOpposite) {
                    if (currentField.get(x - i).get(y - i) == ourColor) {
                        cellsToRecolor.addAll(intermediateCells);
                    }
                    allPossibleCells.add(new Coordinates(x - i, y - i));
                }

                break;
            } else {
                intermediateCells.add(new Coordinates(x - i, y - i));
                didMetOpposite = true;
            }
        }
        intermediateCells.clear();
        didMetOpposite = false;
        for (int i = 1; x - i >= 0 && y + i < 8; i++) {
            if (currentField.get(x - i).get(y + i) != oppositeColor) {
                if (didMetOpposite) {
                    if (currentField.get(x - i).get(y + i) == ourColor) {
                        cellsToRecolor.addAll(intermediateCells);
                    }
                    allPossibleCells.add(new Coordinates(x - i, y + i));
                }

                break;
            } else {
                intermediateCells.add(new Coordinates(x - i, y + i));
                didMetOpposite = true;
            }
        }
        intermediateCells.clear();
        didMetOpposite = false;
        for (int i = 1; y - i >= 0 && x + i < 8; i++) {
            if (currentField.get(x + i).get(y - i) != oppositeColor) {
                if (didMetOpposite) {
                    if (currentField.get(x + i).get(y - i) == ourColor) {
                        cellsToRecolor.addAll(intermediateCells);
                    }
                    allPossibleCells.add(new Coordinates(x + i, y - i));
                }

                break;
            } else {
                intermediateCells.add(new Coordinates(x + i, y - i));
                didMetOpposite = true;
            }
        }

        if (makeRecolor) {
            Double sumForMove = 0.0;
            for (var cell :
                    cellsToRecolor) {
                if (cell.getFirst() % 7 == 0 || cell.getSecond() % 7 == 0) {
                    sumForMove += 2;
                } else {
                    sumForMove += 1;
                }
                currentField.get(cell.getFirst()).set(cell.getSecond(), ourColor);
            }
            if (coordinates.getFirst() % 7 == 0 || coordinates.getSecond() % 7 == 0) {
                if (coordinates.getFirst() % 7 == 0 && coordinates.getSecond() % 7 == 0) {
                    sumForMove += 0.8;
                } else {
                    sumForMove += 0.4;
                }
            }
            return sumForMove;
        }
        for (var cell :
                allPossibleCells) {
            int i = cell.getFirst();
            int j = cell.getSecond();
            if (currentField.get(i).get(j) == CellStatus.None) {
                currentField.get(i).set(j, ourPossibleColor);
            } else if (currentField.get(i).get(j) == oppositePossibleColor) {
                currentField.get(i).set(j, CellStatus.PossibleForBoth);
            }
        }
        return 0.0;
    }

    public int getPointsFor(PlayerColor color) {
        var cellTOFind = switch (color) {
            case White -> CellStatus.White;
            case Black -> CellStatus.Black;
        };
        int counter = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (currentField.get(i).get(j) == cellTOFind) {
                    counter++;
                }
            }
        }
        return counter;
    }

    @Override
    public String toString() {
        String result = "";
        System.out.println();
        for (int i = 7; i >= 0; i--) {
            var row = currentField.get(i);
            for (var cell :
                    row) {
                String cellSustain = switch (cell) {
                    case None -> "0";
                    case Black -> "B";
                    case White -> "W";
                    case PossibleForBlack -> "b";
                    case PossibleForWhite -> "w";
                    case PossibleForBoth -> "|";
                };
                result += (cellSustain + " ");
            }
            result += "\n";
        }
        return result;
    }
}
