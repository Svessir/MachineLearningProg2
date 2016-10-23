package is.ru.machineLearning.test;

import is.ru.machineLearning.learning.Action;
import is.ru.machineLearning.learning.ValueIteration;
import is.ru.machineLearning.math.Vector2D;
import is.ru.machineLearning.raceCar.RaceCar;
import is.ru.machineLearning.raceCar.RaceCarState;
import is.ru.machineLearning.raceCar.TrackType;

/**
 * Created by Sverrir on 15.10.2016.
 */
public class ValueIterationTest {

    public static TrackType[][] getTrack() {
        TrackType I = TrackType.INSIDE;
        TrackType F = TrackType.FINISH;
        TrackType O = TrackType.OUTSIDE;
        TrackType S = TrackType.START;

        TrackType[][] track =
                {
                        {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I,I,I,I,I,O,O,O,O},
                        {O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,O,O,O},
                        {O,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,O},
                        {S,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                        {S,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                        {S,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                        {S,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                        {S,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                        {S,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                        {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I,I},
                        {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                        {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                        {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                        {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                        {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                        {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                        {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,F,F,F,F,F,F},
                        {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,F,F,F,F,F,F},
                        {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,F,F,F,F,F,F},
                        {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,F,F,F,F,F,F},
                        {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,F,F,F,F,F,F},
                        {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,F,F,F,F,F,F}
                };
        return track;
    }

    public static void main(String[] args) {
        RaceCar rc = new RaceCar(getTrack());
        ValueIteration valueIteration = new ValueIteration(rc, 1, 0.01);
        valueIteration.solve();
        rc.setState(new RaceCarState(new Vector2D(8,0), new Vector2D(0,0)));
        rc.print();
        Vector2D currentAction = rc.getOptimalPolicyAction();
        System.out.println(rc);
        do {
            rc.transition(currentAction);
            System.out.println(rc);
        }while((currentAction = rc.getOptimalPolicyAction()) != null);
    }
}
