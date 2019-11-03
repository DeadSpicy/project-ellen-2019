package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Reactor;
import sk.tuke.kpi.oop.game.tools.BreakableTool;

public class FireExtinguisher extends BreakableTool<Reactor> {
    public FireExtinguisher() {
        super(1);
        setAnimation(new Animation("sprites/extinguisher.png"));
    }

    @Override
    public void useWith(Reactor actor) {
        super.useWith(actor);
        actor.extinguish();
    }
}
