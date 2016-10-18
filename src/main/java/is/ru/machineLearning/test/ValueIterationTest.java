package is.ru.machineLearning.test;

import is.ru.machineLearning.learning.ValueIteration;
import is.ru.machineLearning.raceCar.RaceCar;
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
                        {O,O,O,S,S,S,S,S,S,O,O,O,O,O,O,O,O,O,O,O,O,O},
                        {O,O,O,I,I,I,I,I,I,O,O,O,O,O,O,O,O,O,O,O,O,O},
                        {O,O,O,I,I,I,I,I,I,O,O,O,O,O,O,O,O,O,O,O,O,O},
                        {O,O,I,I,I,I,I,I,I,O,O,O,O,O,O,O,O,O,O,O,O,O},
                        {O,O,I,I,I,I,I,I,I,O,O,O,O,O,O,O,O,O,O,O,O,O},
                        {O,O,I,I,I,I,I,I,I,O,O,O,O,O,O,O,O,O,O,O,O,O},
                        {O,O,I,I,I,I,I,I,I,O,O,O,O,O,O,O,O,O,O,O,O,O},
                        {O,O,I,I,I,I,I,I,I,O,O,O,O,O,O,O,O,O,O,O,O,O},
                        {O,O,I,I,I,I,I,I,I,O,O,O,O,O,O,O,O,O,O,O,O,O},
                        {O,O,I,I,I,I,I,I,I,O,O,O,O,O,O,O,O,O,O,O,O,O},
                        {O,I,I,I,I,I,I,I,I,O,O,O,O,O,O,O,O,O,O,O,O,O},
                        {O,I,I,I,I,I,I,I,I,O,O,O,O,O,O,O,O,O,O,O,O,O},
                        {O,I,I,I,I,I,I,I,I,O,O,O,O,O,O,O,O,O,O,O,O,O},
                        {O,I,I,I,I,I,I,I,I,O,O,O,O,O,O,O,O,O,O,O,O,O},
                        {O,I,I,I,I,I,I,I,I,O,O,O,O,O,O,O,O,O,O,O,O,O},
                        {O,I,I,I,I,I,I,I,I,O,O,O,O,O,O,O,O,O,O,O,O,O},
                        {O,I,I,I,I,I,I,I,I,O,O,O,O,O,O,O,O,O,O,O,O,O},
                        {O,I,I,I,I,I,I,I,I,O,O,O,O,O,O,O,O,O,O,O,O,O},
                        {I,I,I,I,I,I,I,I,I,O,O,O,O,O,O,O,O,O,O,O,O,O},
                        {I,I,I,I,I,I,I,I,I,O,O,O,O,O,O,O,O,O,O,O,O,O},
                        {I,I,I,I,I,I,I,I,I,O,O,O,O,O,O,O,O,O,O,O,O,O},
                        {I,I,I,I,I,I,I,I,I,O,O,O,O,O,O,O,O,O,O,O,O,O},
                        {I,I,I,I,I,I,I,I,I,O,O,O,O,O,O,O,O,O,O,O,O,O},
                        {I,I,I,I,I,I,I,I,I,O,O,O,O,O,O,O,O,O,O,O,O,O},
                        {I,I,I,I,I,I,I,I,I,O,O,O,O,O,O,O,O,O,O,O,O,O},
                        {I,I,I,I,I,I,I,I,I,I,O,O,O,O,O,O,O,O,O,O,O,O},
                        {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,F,F,F,F,F,F},
                        {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,F,F,F,F,F,F},
                        {O,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,F,F,F,F,F,F},
                        {O,O,I,I,I,I,I,I,I,I,I,I,I,I,I,I,F,F,F,F,F,F},
                        {O,O,I,I,I,I,I,I,I,I,I,I,I,I,I,I,F,F,F,F,F,F},
                        {O,O,O,I,I,I,I,I,I,I,I,I,I,I,I,I,F,F,F,F,F,F}
                };
        return track;
    }

    public static void main(String[] args) {
        RaceCar rc = new RaceCar(getTrack());
        ValueIteration valueIteration = new ValueIteration(rc, 1, 0.00000001);
        valueIteration.solve();
    }
}
