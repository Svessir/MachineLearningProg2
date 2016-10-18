package is.ru.machineLearning;


/**
 * Created by Kárii on 12.10.2016.
 */
public class State {
    public Vector2D position;
    public Vector2D velocity;
    private Actions actions = new Actions();

    public State(Vector2D position, Vector2D velocity){
        this.position = position;
        this.velocity = velocity;
    }

    public Vector2D getAction(Actions actions){
        return actions.actionsList[0];
    }
}
