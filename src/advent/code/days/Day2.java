package advent.code.days;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Day2 implements Runnable {

    @Override
    public void run() {
        File file = new File("src/resources/day2-input.txt");

        final List<GameRound> rounds = new ArrayList<>();
        try (Stream<String> ss = Files.lines(Paths.get(file.getPath()))) {
            AtomicInteger totalPoints = new AtomicInteger();
            ss.forEach(s -> {

                char[] data = new char[2];
                data[0] = s.charAt(0);
                data[1] = s.charAt(2);

                GameRound r = new GameRound(data[0], data[1]);
                rounds.add(r);
                int delta = r.calculatePoints();

                totalPoints.addAndGet(delta);
            });

            System.out.println("DAY 2: \tTotal points: " + totalPoints.get());

        } catch (Exception e) {
            System.err.println("Something went wrong " + e.getMessage());
        }
    }


    public static class GameRound {
        public GameRound(char opponentsMove, char myMove) {
            this.opponentsMove = mapMoveToShape(opponentsMove);
            this.myMove = mapMoveToShape(myMove);
        }

        public int calculatePoints() {
            int myHandPoint = getShapeCost(myMove);
            int roundPoints = 0;

            // R P S
            // A B C
            // X Y Z

            /**     MY MOVE
             *      R   P   S
             * R    D   W   L
             * P    L   D   W
             * S    W   L   D
             */

            if (myMove != opponentsMove) {
                switch (myMove) {
                    case 'R' -> roundPoints = opponentsMove == 'S' ? 6 : 0;
                    case 'P' -> roundPoints = opponentsMove == 'R' ? 6 : 0;
                    case 'S' -> roundPoints = opponentsMove == 'P' ? 6 : 0;
                }
            }
            int points = myHandPoint + roundPoints;
            return points;
        }

        private final char opponentsMove;
        private final char myMove;

        private char mapMoveToShape(char move)
        {
            return switch (move){
                case 'X', 'A' -> 'R';
                case 'Y', 'B' -> 'P';
                case 'Z', 'C' -> 'S';
                default -> '?';
            };
        }

        private int getShapeCost(char chr) {
            return switch (chr) {
                case 'X' -> 1;
                case 'Y' -> 2;
                case 'Z' -> 3;
                default -> 0;
            };
        }

    }


}
