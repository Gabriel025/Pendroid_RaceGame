package com.race2135.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2D;

/**
 * A játék elindításakor megjelenő kezdőképernyőről továbblépve választhatunk járművet,
 * illetve a pályát, melyen végig akarunk menni.
 *
 * A játék irányítása:
 * -A képernyő bal alsó sarkában található két, a fék és a gázpedál célját
 *  szolgáló mező (bal: fék, jobb: gáz). Ezeken az ujjunk függőleges helyzete adja meg,
 *  hogy a pedál mennyire van lenyomva.
 * -A képernyő jobb oldalán található a sebességváltó, melyet fokozatonként lehet
 *  húzással irányítani. (rükverc - üres - 1 - 2 - 3 - 4 - 5)
 *  ()
 * -Kanyarodni az eszköz jobbra, illetve balra döntésével lehet. (gyorsulásmérő)
 *  Az eszközt nem érdemes asztalra fektetni játék közben, így ugyanis jelentősen romolhat
 *  a szenzor pontossága.
 *
 * A játék menete:
 * A játékos visszaszámlálás után indulhat el(Ekkor a kocsi 1-es sebességbe kerül autómatikusan),
 * és amikor körbeér a pályán, a játék rövidesen kiírja az elért köridőt.
 * Erről a képernyőről visszajuthat a játék első képernyőjére.
 *
 * Megvalósított extra funkciók:
 * -Sebességváltó
 * -Motorhang
 * -Választható autók, illetve pályák
 * -Egy homokos, csúszósabb pálya
 */

public class Main extends Game {
    public static Texture black;

	@Override
	public void create() {
        Box2D.init();

        CarInfo.init();
        LevelInfo.init();
        black = new Texture("black.png");

        setScreen(new MainMenu(this));
	}
}
