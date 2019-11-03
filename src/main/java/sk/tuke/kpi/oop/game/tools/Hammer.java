package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Reactor;
import sk.tuke.kpi.oop.game.tools.BreakableTool;

public class Hammer extends BreakableTool<Reactor> {

    public Hammer(int remainingUses) {
        super(1);
        setAnimation(new Animation("sprites/hammer.png"));
    }

    @Override
    public void useWith(Reactor actor) {
        super.useWith(actor);
        actor.repair();
    }
}
