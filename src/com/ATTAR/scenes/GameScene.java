package com.ATTAR.scenes;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Set;


import com.ATTAR.Sound.Sound;
import com.ATTAR.components.CompRender;
import com.ATTAR.defaultes.*;
import com.ATTAR.fonts.Sdf;
import com.ATTAR.grafic.Shader;

import com.ATTAR.maps.*;


import com.ATTAR.physic.AABB;
import com.ATTAR.physic.Physic;
import org.joml.*;

import com.ATTAR.grafic.Camera;
import com.ATTAR.objects.*;

import static org.lwjgl.glfw.GLFW.*;

public class GameScene extends Scene {





	private Player player;






	private HashMap<Vector2f, Tiles> BlockMap = new HashMap<>();



	private boolean isLoaded = false;


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
	private Vector2f CamSize, playerTile;
	private LoadMap loadMap;
	private Sound bgsound;
	private AABB AABB = new AABB();
	private Physic physic;
	public Vector2f getPlayerTileByCenter() {
		return new Vector2f((int)Math.floor((player.getPos().x+50)/100), (int)Math.floor((player.getPos().y+50)/100));
	}

	public Vector2f getPlayerTile() {
		return new Vector2f((int)Math.floor(player.getPos().x/100), (int)Math.floor(player.getPos().y/100));
	}
	public void getCollision() {
		PM.collideX = false;
		physic.collidingY = false;
		if(PM.jump) {
			System.out.println("Jumping");
			physic.SetGravityVector(PM.GetVector().y);
			PM.jump = false;
		}
		if(BlockMap.containsKey(new Vector2f(getPlayerTileByCenter().x-1, getPlayerTile().y))) {

			if (AABB.isAabbXCollision(player.getPos(),
					new Vector2f(player.getScale().x*83,player.getScale().y*100),PM.GetVector(),
					BlockMap.get(new Vector2f(getPlayerTileByCenter().x-1, getPlayerTile().y)).getPos(),
					new Vector2f(100))&& BlockMap.get(new Vector2f(getPlayerTileByCenter().x-1, getPlayerTile().y)).isSolid()) {
				player.setPos(AABB.getCorrectPos());
				if (BlockMap.get(new Vector2f(getPlayerTileByCenter().x-1, getPlayerTile().y)).isKilling()) {
					player.setHp(0);
				}
				PM.collideX = true;
			}

		}
		if(BlockMap.containsKey(new Vector2f(getPlayerTileByCenter().x-1, getPlayerTile().y+1))) {

			if (AABB.isAabbXCollision(player.getPos(),
					new Vector2f(player.getScale().x*83,player.getScale().y*100),PM.GetVector(),
					BlockMap.get(new Vector2f(getPlayerTileByCenter().x-1, getPlayerTile().y+1)).getPos(),
					new Vector2f(100)) && BlockMap.get(new Vector2f(getPlayerTileByCenter().x-1, getPlayerTile().y+1)).isSolid()) {
				player.setPos(AABB.getCorrectPos());
				if (BlockMap.get(new Vector2f(getPlayerTileByCenter().x-1, getPlayerTile().y+1)).isKilling()) {
					player.setHp(0);
				}
				PM.collideX = true;
			}

		}
		if(BlockMap.containsKey(new Vector2f(getPlayerTileByCenter().x+1, getPlayerTile().y))) {

			if (AABB.isAabbXCollision(player.getPos(),
					new Vector2f(player.getScale().x*83,player.getScale().y*100), PM.GetVector(),
					BlockMap.get(new Vector2f(getPlayerTileByCenter().x+1, getPlayerTile().y)).getPos(),
					new Vector2f(100))&&
					BlockMap.get(new Vector2f(getPlayerTileByCenter().x+1, getPlayerTile().y)).isSolid()) {

				player.setPos(AABB.getCorrectPos());
				if (BlockMap.get(new Vector2f(getPlayerTileByCenter().x+1, getPlayerTile().y)).isKilling()) {
					player.setHp(0);
				}
				PM.collideX = true;
			}

		}
		if(BlockMap.containsKey(new Vector2f(getPlayerTileByCenter().x+1, getPlayerTile().y+1))) {

			if (AABB.isAabbXCollision(player.getPos(),
					new Vector2f(player.getScale().x*83,player.getScale().y*100), PM.GetVector(),
					BlockMap.get(new Vector2f(getPlayerTileByCenter().x+1, getPlayerTile().y+1)).getPos(),
					new Vector2f(100))&& BlockMap.get(new Vector2f(getPlayerTileByCenter().x+1, getPlayerTile().y+1)).isSolid()) {

				player.setPos(AABB.getCorrectPos());
				if (BlockMap.get(new Vector2f(getPlayerTileByCenter().x+1, getPlayerTile().y+1)).isKilling()) {
					player.setHp(0);
				}
				PM.collideX = true;
			}

		}
		if(BlockMap.containsKey(new Vector2f(getPlayerTile().x, getPlayerTileByCenter().y+1))) {

			if (AABB.isAabbYCollision(player.getPos(),
					new Vector2f(player.getScale().x*83,player.getScale().y*100), new Vector2f(PM.GetVector().x,physic.getGravityVector()),
					BlockMap.get(new Vector2f(getPlayerTile().x, getPlayerTileByCenter().y+1)).getPos(),
					new Vector2f(100))&& BlockMap.get(new Vector2f(getPlayerTile().x, getPlayerTileByCenter().y+1)).isSolid()) {
				player.setPos(AABB.getCorrectPos());
				if (BlockMap.get(new Vector2f(getPlayerTile().x, getPlayerTileByCenter().y+1)).isKilling()) {
					player.setHp(0);
				}
				physic.collidingY = true;

			}

		}
		if(BlockMap.containsKey(new Vector2f(getPlayerTile().x+1, getPlayerTileByCenter().y+1))) {

			if (AABB.isAabbYCollision(player.getPos(),
					new Vector2f(player.getScale().x*83,player.getScale().y*100), new Vector2f(PM.GetVector().x,physic.getGravityVector()),
					BlockMap.get(new Vector2f(getPlayerTile().x+1, getPlayerTileByCenter().y+1)).getPos(),
					new Vector2f(100))&& BlockMap.get(new Vector2f(getPlayerTile().x+1, getPlayerTileByCenter().y+1)).isSolid()) {
				player.setPos(AABB.getCorrectPos());
				if (BlockMap.get(new Vector2f(getPlayerTile().x+1, getPlayerTileByCenter().y+1)).isKilling()) {
					player.setHp(0);
				}
				physic.collidingY = true;


			}

		}
		if(BlockMap.containsKey(new Vector2f(getPlayerTile().x, getPlayerTileByCenter().y-1))) {
			if (AABB.isAabbYCollision(player.getPos(),
					new Vector2f(player.getScale().x*83,player.getScale().y*100), new Vector2f(PM.GetVector().x,physic.getGravityVector()),
					BlockMap.get(new Vector2f(getPlayerTile().x, getPlayerTileByCenter().y-1)).getPos(),
					new Vector2f(100))&& BlockMap.get(new Vector2f(getPlayerTile().x, getPlayerTileByCenter().y-1)).isSolid()) {
				player.setPos(AABB.getCorrectPos());
				if (BlockMap.get(new Vector2f(getPlayerTile().x, getPlayerTileByCenter().y-1)).isKilling()) {
					player.setHp(0);
				}
				physic.collidingY = true;
				PM.can_jump =true;

			}

		}
		if(BlockMap.containsKey(new Vector2f(getPlayerTile().x+1, getPlayerTileByCenter().y-1))) {

			if (AABB.isAabbYCollision(player.getPos(),
					new Vector2f(player.getScale().x*83,player.getScale().y*100), new Vector2f(PM.GetVector().x,physic.getGravityVector()),
					BlockMap.get(new Vector2f(getPlayerTile().x+1, getPlayerTileByCenter().y-1)).getPos(),
					new Vector2f(100))&& BlockMap.get(new Vector2f(getPlayerTile().x+1, getPlayerTileByCenter().y-1)).isSolid()) {
				player.setPos(AABB.getCorrectPos());

				physic.collidingY = true;
				if (BlockMap.get(new Vector2f(getPlayerTile().x+1, getPlayerTileByCenter().y-1)).isKilling()) {
					player.setHp(0);
				}
				PM.can_jump =true;
				PM.collideY = true;
			}

		}
		PM.setPlayerVector(new Vector2f(PM.GetVector().x,physic.GravityVector()));

	}
	public GameScene(Vector2f CamSize, String MapName, long win, int i, SceneManager scmg) {

		isLoaded =false;
		physic = new Physic();
		BlockMap = new HashMap<>();
		Buttons = new ArrayList<>();
		AssetsPool.addSound("Assets/Sounds/soundtrack/8_Bit_Retro_Funk.ogg", true);
		bgsound = AssetsPool.getSound("Assets/Sounds/soundtrack/8_Bit_Retro_Funk.ogg");
		cam = new Camera(new Vector2f(0), i);
		this.CamSize = cam.getSize();
		keyListener = new KeyListener(win);
		player = new Player(cam);
		player.setPos(new Vector2f(100,350));
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
				bgsound.setVolume(8);
				bgsound.play();
			}
			physic.update();
			getCollision();
			if (player.getHp() <= 0) {
				PM.setPlayerVector(new Vector2f(0));
				PM.collideX = true;
				physic.SetGravityVector(0);

			}
			player.setPos(new Vector2f(player.getPos().x+PM.GetVector().x, player.getPos().y+physic.getGravityVector()));



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

			if (player.getHp() <= 0) {

				fontRender.Update("Game Over", player.getPos(),0.5f, new Vector4f(1,1,1,1),1);
			}
		}
		else if(pause){
			fontRender.Update("Pause", new Vector2f(300, 300), 0.4f, new Vector4f(0, 1, 0, 1),0);

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


				else{

					Buttons.get(i).setSelected(false);
				}
			}
			System.out.println("pause");
			fontRender.Update("Pause", new Vector2f(300, 400), 0.4f, new Vector4f(0, 1, 0, 1),0);

		}

	}
	public void SetPlayerPos(Vector2f pos) {

			player.setPos(pos);

	}

	public Vector2f GetPlayerPos() {
		return player.getPos();
	}

}
