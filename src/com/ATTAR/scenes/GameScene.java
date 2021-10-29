package com.ATTAR.scenes;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Set;


import com.ATTAR.Sound.Sound;
import com.ATTAR.components.CompRender;
import com.ATTAR.defaultes.AssetsPool;
import com.ATTAR.defaultes.ButtonListener;
import com.ATTAR.defaultes.KeyListener;
import com.ATTAR.defaultes.PlayerMovement;
import com.ATTAR.fonts.Sdf;
import com.ATTAR.grafic.Shader;

import com.ATTAR.maps.*;


import org.joml.*;

import com.ATTAR.grafic.Camera;
import com.ATTAR.objects.*;

import static org.lwjgl.glfw.GLFW.*;

public class GameScene extends Scene {





	private Player player;






	private HashMap<Vector2f, Tiles> BlockMap = new HashMap<>();



	private boolean isLoaded = false;
    private Set BlockPos;

	private PlayerMovement PM;
	private boolean pause;
	private Shader sdfShader;
	private Button Start, Quit;
	private CompRender fontRender;
	private ButtonListener buttonListener;
	private List<Button> Buttons = new ArrayList<>();
	private Sdf sdf;

	private KeyListener keyListener;
	Camera cam;
	private Vector2f CamSize;
	private LoadMap loadMap;
	private Sound bgsound;


	public GameScene(Vector2f CamSize, String MapName, long win, int i, SceneManager scmg) {
		isLoaded =false;
		AssetsPool.addSound("Assets/Sounds/soundtrack/8_Bit_Retro_Funk.ogg", true);
		bgsound = AssetsPool.getSound("Assets/Sounds/soundtrack/8_Bit_Retro_Funk.ogg");
		cam = new Camera(new Vector2f(0), i);
		this.CamSize = cam.getSize();
		keyListener = new KeyListener(win);
		player = new Player(cam);
		player.setPos(new Vector2f(100,300));
		loadMap = new LoadMap(MapName+".txm");
		PM = new PlayerMovement(win, player.getPos());
		pause = false;
		sdf = new Sdf();
		sdf.generateBitmap("C:/Windows/Fonts/arial.ttf", 128);
		sdfShader = new Shader("./Shaders/sdfShader.glsl");
		fontRender = new CompRender();
		fontRender.init(sdfShader, cam, sdf);

		buttonListener = new ButtonListener(win, new Vector2i(1,2));

		Buttons.add(Start = new Button(cam,new Vector2f(200,100),new Vector2f(50,160), win){
			@Override
			public void function() {

				pause = false;
			}
		});
		Buttons.add(Quit = new Button(cam,new Vector2f(200,100),new Vector2f(50,50), win){
			@Override
			public void function() {
				scmg.switchScene("menu", null);
			}
		});


	}


	public void update() {

		if (loadMap.getLoader().isAlive()) {
//            System.out.println("Some definitely useful loading screen tip: Try not die");
		}
		else if(!isLoaded){
			BlockMap = LoadTiles.load(loadMap.getMap(), cam);

			isLoaded =true;
		}


		else if(!pause) {
			if (!bgsound.isPlaying()) {
				bgsound.play();
			}

			player.setPos(PM.update());
			if (player.getHp() <= 0) {
				System.out.println("GameOver");
			}

			if (keyListener.isPressedOnce(GLFW_KEY_ESCAPE)){
				pause = true;
			}
			if (player.getPos().x - (CamSize.x / 2) + (player.getScale().x * 50) > 0) {
				cam.setCamPos(new Vector2f(player.getPos().x - (CamSize.x / 2) + (player.getScale().x * 50), cam.getCamPos().y));
			}
			if (player.getPos().y - (CamSize.y / 2) + (player.getScale().y * 50) > 0) {
				cam.setCamPos(new Vector2f(cam.getCamPos().x, player.getPos().y - (CamSize.y / 2) + (player.getScale().y * 50)));

			}
			for (int i = Math.round((cam.getCamPos().x - 200) / 100); i < (Math.round((cam.getCamPos().x + (CamSize.x + 300)) / 100)); i++) {

				for (int j = Math.round((cam.getCamPos().y - 200) / 100); j < (Math.round((cam.getCamPos().y + (CamSize.y + 300)) / 100)); j++) {

					if (BlockMap.containsKey(new Vector2f(i, j))) {
						BlockMap.get(new Vector2f(i, j)).update();

					}

				}
			}
			player.update();
		}
		else if(pause){
			if (bgsound.isPlaying()) {
				bgsound.pause();
			}
			buttonListener.update();

			int CurrentButton = buttonListener.getButtonId();

			if (keyListener.isPressedOnce(GLFW_KEY_ENTER)){
				Buttons.get(CurrentButton).function();
			}
			for (int i = 0; i<Buttons.size(); i++) {
				Buttons.get(i).update();
				if (i == CurrentButton) {
					Buttons.get(i).setSelected(true);
				}
				else {
					Buttons.get(i).setSelected(false);
				}
			}

		}

	}
	public void SetPlayerPos(Vector2f pos) {

			player.setPos(pos);

	}

	public Vector2f GetPlayerPos() {
		return player.getPos();
	}
	public Tiles initTiles(Vector2f pos, Vector3f scale, Vector2f Scales, int Type, Camera cam  ) {
		Tiles tiles = new Tiles(cam);
		tiles.init(Type);
		tiles.setScale(scale);
		tiles.setPos(new Vector2f(pos.x * (100*Scales.x), pos.y* (100*Scales.y)));
		return tiles;
	}
}
