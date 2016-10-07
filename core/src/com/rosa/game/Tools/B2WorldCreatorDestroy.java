package com.rosa.game.Tools;

import com.rosa.game.Sprites.Enemies.Bun;

public class B2WorldCreatorDestroy {


    public void update(float dt) {
        for (Bun bun : B2WorldCreator.buns) {
            bun.update(dt);
            if (bun.isDestroyed())
                B2WorldCreator.buns.removeValue(bun, true);
        }
    }
}