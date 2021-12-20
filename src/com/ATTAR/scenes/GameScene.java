package com.ATTAR.scenes;

import java.util.*;



import com.ATTAR.Sound.Sound;
import com.ATTAR.components.CompRender;
import com.ATTAR.defaultes.*;
import com.ATTAR.fonts.Sdf;
import com.ATTAR.grafic.*;
import com.ATTAR.maps.*;
import com.ATTAR.physic.*;

import org.joml.*;
import com.ATTAR.objects.*;
import static org.lwjgl.glfw.GLFW.*;

public class GameScene extends Scene {



	private double repair_time = 4d;
	private double destroi_time = 0.7d;
	private int level = 0;
	private List<Integer> toRemove = new ArrayList<>();
	private List<Integer> toRemoveCh = new ArrayList<>();
	private Player player;
	private HashMap<Vector2f, Tiles> BlockMap;
	private boolean isLoaded,godMod;
	private PlayerMovement PM;
	private boolean pause, respawn, can_move, next_level;
	private Shader sdfShader;
	private Button Start, Quit;
	private CompRender fontRender;
	private ButtonListener buttonListener;
	private List<Button> Buttons;
	private Sdf sdf;
	private KeyListener keyListener;
	private Camera cam;
	private Vector2f CamSize, default_pos, under_tile_key;
	private LoadMap loadMap;
	private Sound bgSound;
	private AABB AABB = new AABB();
	private Physic physic;
	private Tiles current_tile, under_tile;
	private Vector2f Level_floor_ciling;
	public Vector2f getPlayerTileByCenter() {
		return new Vector2f((int)Math.floor((player.getPos().x+50)/100), (int)Math.floor((player.getPos().y+50)/100));
	}
	public Vector2f getPlayerTile() {
		return new Vector2f((int)Math.floor(player.getPos().x/100), (int)Math.floor(player.getPos().y/100));
	}
	private double timer;
	List<BlockTimer> BlockTimer;
	List<BlockTimer> ChangeTimer;
	public void getCollision() {
		PM.collideX = false;
		physic.collidingY = false;
		if(PM.jump) {

			physic.SetGravityVector(PM.GetVector().y);
			PM.jump = false;
		}
		/*left collision beginning*/
		if(BlockMap.containsKey(new Vector2f(getPlayerTileByCenter().x-1, getPlayerTile().y))) {
			current_tile = BlockMap.get(new Vector2f(getPlayerTileByCenter().x-1, getPlayerTile().y));

			if (AABB.isAabbXCollision(player.getPos(),
					new Vector2f(player.getScale().x*83,player.getScale().y*83),PM.GetVector(),
					current_tile.getPos(),
					new Vector4f(current_tile.getAABB().x/32*100, current_tile.getAABB().y/32*100, current_tile.getAABB().z/32*100, current_tile.getAABB().w/32*100))&& current_tile.isSolid()) {
				

				if (current_tile.isKilling("Killing_R") && player.getHp()>=2 && !godMod) {
					if(BlockMap.containsKey(new Vector2f(getPlayerTileByCenter().x-1, getPlayerTile().y+1))) {
						current_tile = BlockMap.get(new Vector2f(getPlayerTileByCenter().x-1, getPlayerTile().y+1));

						if (current_tile.isKilling("Killing_R") || !AABB.isAabbXCollision(player.getPos(),
								new Vector2f(player.getScale().x*83,player.getScale().y*83),PM.GetVector(),
								current_tile.getPos(),
								new Vector4f(current_tile.getAABB().x/32*100, current_tile.getAABB().y/32*100, current_tile.getAABB().z/32*100, current_tile.getAABB().w/32*100)) && current_tile.isSolid()) {
							player.setHp(0);
						}
					}
					else {
						player.setHp(0);
					}

				}
				if(!current_tile.isTriger()){

					PM.collideX = true;
					player.setPos(AABB.getCorrectPos());
				}
				else {
					under_tile_key = new Vector2f(getPlayerTileByCenter().x-1, getPlayerTile().y-1);
					under_tile = BlockMap.get(under_tile_key);
					if (current_tile.getType().equals("to spike")) {
						if (under_tile.getType().equals("Block")) {
							BlockTimer.add(new BlockTimer(under_tile_key,under_tile,repair_time));
							BlockMap.replace(under_tile_key, LoadTiles.replaceTile(under_tile.getPos(), AssetsPool.getTile(AssetsPool.spikes.get(under_tile.getID())), under_tile.getCam()));
						}
					}
					else if(current_tile.getType().equals("to floor")) {
						if (under_tile.getType().equals("Spike")) {
							BlockTimer.add(new BlockTimer(under_tile_key,under_tile,repair_time));
							BlockMap.replace(under_tile_key, LoadTiles.replaceTile(under_tile.getPos(), AssetsPool.getTile(AssetsPool.blocks.get(under_tile.getID())), under_tile.getCam()));

						}
					}
					else if(current_tile.getType().equals("destroi") && BlockMap.containsKey(under_tile_key)) {
						ChangeTimer.add(new BlockTimer(under_tile_key,under_tile,destroi_time));


					}
				}

			}
		}
		if(BlockMap.containsKey(new Vector2f(getPlayerTileByCenter().x-1, getPlayerTile().y+1))) {
			current_tile = BlockMap.get(new Vector2f(getPlayerTileByCenter().x-1, getPlayerTile().y+1));
			if (AABB.isAabbXCollision(player.getPos(),
					new Vector2f(player.getScale().x*83,player.getScale().y*83),PM.GetVector(),
					current_tile.getPos(),
					new Vector4f(current_tile.getAABB().x/32*100, current_tile.getAABB().y/32*100, current_tile.getAABB().z/32*100, current_tile.getAABB().w/32*100)) && current_tile.isSolid()) {
				
				
				if (current_tile.isKilling("Killing_R") && player.getHp()>=2 && !godMod) {
					if (BlockMap.containsKey(new Vector2f(getPlayerTileByCenter().x-1, getPlayerTile().y))) {
						current_tile =BlockMap.get(new Vector2f(getPlayerTileByCenter().x-1, getPlayerTile().y));
						if (current_tile.isKilling("Killing_R") || !AABB.isAabbXCollision(player.getPos(), new Vector2f(player.getScale().x * 83, player.getScale().y * 83), PM.GetVector(), current_tile.getPos(), new Vector4f(current_tile.getAABB().x / 32 * 100, current_tile.getAABB().y / 32 * 100, current_tile.getAABB().z / 32 * 100, current_tile.getAABB().w / 32 * 100)) && current_tile.isSolid()) {

							player.setHp(0);

						}
					}
					else {
						player.setHp(0);
					}
				}
				if(!current_tile.isTriger()){
					PM.collideX = true;
					player.setPos(AABB.getCorrectPos());
				}
			}

		}
		/*left collision end*/
		/*right collision beginning*/
		if(BlockMap.containsKey(new Vector2f(getPlayerTileByCenter().x+1, getPlayerTile().y))) {
			current_tile = BlockMap.get(new Vector2f(getPlayerTileByCenter().x+1, getPlayerTile().y));
			if (AABB.isAabbXCollision(player.getPos(),
					new Vector2f(player.getScale().x*83,player.getScale().y*83), PM.GetVector(),
					current_tile.getPos(),
					new Vector4f(current_tile.getAABB().x/32*100, current_tile.getAABB().y/32*100, current_tile.getAABB().z/32*100, current_tile.getAABB().w/32*100)) && current_tile.isSolid()) {


				if (current_tile.isKilling("Killing_L") && player.getHp() >= 2 && !godMod) {
					if (BlockMap.containsKey(new Vector2f(getPlayerTileByCenter().x + 1, getPlayerTile().y + 1))) {
						current_tile = BlockMap.get(new Vector2f(getPlayerTileByCenter().x + 1, getPlayerTile().y + 1));
						if (current_tile.isKilling("Killing_L") || !AABB.isAabbXCollision(player.getPos(),
								new Vector2f(player.getScale().x * 83, player.getScale().y * 83), PM.GetVector(),
								current_tile.getPos(),
								new Vector4f(current_tile.getAABB().x / 32 * 100, current_tile.getAABB().y / 32 * 100, current_tile.getAABB().z / 32 * 100, current_tile.getAABB().w / 32 * 100)) && current_tile.isSolid()) {
							player.setHp(0);
						}
					}
					else {
						player.setHp(0);
					}
				}
				if(!current_tile.isTriger()){

					if (current_tile.getType().equals("Door_Close")) {
						can_move = false;
						next_level = true;
					}
					PM.collideX = true;
					player.setPos(AABB.getCorrectPos());
				}
				else {
					under_tile_key = new Vector2f(getPlayerTileByCenter().x+1, getPlayerTile().y-1);
					under_tile = BlockMap.get(under_tile_key);
					if (current_tile.getType().equals("to spike")) {
						if (under_tile.getType().equals("Block")) {
							BlockTimer.add(new BlockTimer(under_tile_key,under_tile,repair_time));
							BlockMap.replace(under_tile_key, LoadTiles.replaceTile(under_tile.getPos(), AssetsPool.getTile(AssetsPool.spikes.get(under_tile.getID())), under_tile.getCam()));
						}
					}
					else if(current_tile.getType().equals("to floor")) {
						if (under_tile.getType().equals("Spike")) {
							BlockTimer.add(new BlockTimer(under_tile_key,under_tile,repair_time));
							BlockMap.replace(under_tile_key, LoadTiles.replaceTile(under_tile.getPos(), AssetsPool.getTile(AssetsPool.blocks.get(under_tile.getID())), under_tile.getCam()));
						}
					}
					else if(current_tile.getType().equals("destroi") && BlockMap.containsKey(under_tile_key)) {
						ChangeTimer.add(new BlockTimer(under_tile_key,under_tile,destroi_time));


					}
				}
			}
		}
		if(BlockMap.containsKey(new Vector2f(getPlayerTileByCenter().x+1, getPlayerTile().y+1))) {
			current_tile = BlockMap.get(new Vector2f(getPlayerTileByCenter().x+1, getPlayerTile().y+1));
			if (AABB.isAabbXCollision(player.getPos(),
					new Vector2f(player.getScale().x*83,player.getScale().y*83), PM.GetVector(),
					current_tile.getPos(),
					new Vector4f(current_tile.getAABB().x/32*100, current_tile.getAABB().y/32*100, current_tile.getAABB().z/32*100, current_tile.getAABB().w/32*100))&& current_tile.isSolid()) {



				if (current_tile.isKilling("Killing_L") && player.getHp()>=2 && !godMod) {
					if(BlockMap.containsKey(new Vector2f(getPlayerTileByCenter().x+1, getPlayerTile().y))) {
						current_tile = BlockMap.get(new Vector2f(getPlayerTileByCenter().x+1, getPlayerTile().y));
						if (current_tile.isKilling("Killing_L") || !AABB.isAabbXCollision(player.getPos(),
								new Vector2f(player.getScale().x*83,player.getScale().y*83), PM.GetVector(),
								current_tile.getPos(),
								new Vector4f(current_tile.getAABB().x/32*100, current_tile.getAABB().y/32*100, current_tile.getAABB().z/32*100, current_tile.getAABB().w/32*100)) && current_tile.isSolid()) {

							player.setHp(0);
						}

					}
					else {
						player.setHp(0);
					}
				}
				if(!current_tile.isTriger()){
					if (current_tile.getType().equals("Door_Close")) {
						can_move = false;
						next_level = true;
					}
					PM.collideX = true;
					player.setPos(AABB.getCorrectPos());
				}

			}

		}
		/*right collision end*/
		/*top collision beginning*/
		if(BlockMap.containsKey(new Vector2f(getPlayerTile().x, getPlayerTileByCenter().y+1))) {
			current_tile = BlockMap.get(new Vector2f(getPlayerTile().x, getPlayerTileByCenter().y+1));
			if (AABB.isAabbYCollision(player.getPos(),
					new Vector2f(player.getScale().x*83,player.getScale().y*83), new Vector2f(PM.GetVector().x,physic.getGravityVector()),
					current_tile.getPos(),
					new Vector4f(current_tile.getAABB().x/32*100, current_tile.getAABB().y/32*100, current_tile.getAABB().z/32*100, current_tile.getAABB().w/32*100))&& current_tile.isSolid()) {


				if (current_tile.isKilling("Killing_B") && player.getHp()>=2 && !godMod) {
					if(BlockMap.containsKey(new Vector2f(getPlayerTile().x+1, getPlayerTileByCenter().y+1))) {
						current_tile = BlockMap.get(new Vector2f(getPlayerTile().x+1, getPlayerTileByCenter().y+1));
						if (current_tile.isKilling("Killing_B") || !AABB.isAabbYCollision(player.getPos(),
								new Vector2f(player.getScale().x*83,player.getScale().y*83), new Vector2f(PM.GetVector().x,physic.getGravityVector()),
								current_tile.getPos(),
								new Vector4f(current_tile.getAABB().x/32*100, current_tile.getAABB().y/32*100, current_tile.getAABB().z/32*100, current_tile.getAABB().w/32*100))&& current_tile.isSolid()) {
							player.setHp(0);
						}
					}
					else {
						player.setHp(0);

					}
				}
				if(!current_tile.isTriger()){

					physic.collidingY = true;
					player.setPos(AABB.getCorrectPos());
				}
			}

		}
		if(BlockMap.containsKey(new Vector2f(getPlayerTile().x+1, getPlayerTileByCenter().y+1))) {
			current_tile = BlockMap.get(new Vector2f(getPlayerTile().x+1, getPlayerTileByCenter().y+1));
			if (AABB.isAabbYCollision(player.getPos(),
					new Vector2f(player.getScale().x*83,player.getScale().y*83), new Vector2f(PM.GetVector().x,physic.getGravityVector()),
					current_tile.getPos(),
					new Vector4f(current_tile.getAABB().x/32*100, current_tile.getAABB().y/32*100, current_tile.getAABB().z/32*100, current_tile.getAABB().w/32*100))&& current_tile.isSolid()) {

				if (current_tile.isKilling("Killing_B") && player.getHp()>=2 && !godMod) {
					if(BlockMap.containsKey(new Vector2f(getPlayerTile().x, getPlayerTileByCenter().y+1))) {
						current_tile = BlockMap.get(new Vector2f(getPlayerTile().x, getPlayerTileByCenter().y+1));
						if (current_tile.isKilling("Killing_B") || !AABB.isAabbYCollision(player.getPos(),
								new Vector2f(player.getScale().x*83,player.getScale().y*83), new Vector2f(PM.GetVector().x,physic.getGravityVector()),
								current_tile.getPos(),
								new Vector4f(current_tile.getAABB().x/32*100, current_tile.getAABB().y/32*100, current_tile.getAABB().z/32*100, current_tile.getAABB().w/32*100))&& current_tile.isSolid()) {
							player.setHp(0);
						}
					}
					else {
						player.setHp(0);
					}
				}
				if(!current_tile.isTriger()){

					physic.collidingY = true;
					player.setPos(AABB.getCorrectPos());
				}

			}

		}
		/*top collision end*/
		/*bottom collision beginning*/
		if(BlockMap.containsKey(new Vector2f(getPlayerTile().x, getPlayerTileByCenter().y-1))) {
			current_tile = BlockMap.get(new Vector2f(getPlayerTile().x, getPlayerTileByCenter().y-1));
			if (AABB.isAabbYCollision(player.getPos(),
					new Vector2f(player.getScale().x*83,player.getScale().y*83), new Vector2f(PM.GetVector().x,physic.getGravityVector()),
					current_tile.getPos(),
					new Vector4f(current_tile.getAABB().x/32*100, current_tile.getAABB().y/32*100,
					current_tile.getAABB().z/32*100, current_tile.getAABB().w/32*100))&& current_tile.isSolid()) {

				if (current_tile.isKilling("Killing_T") && player.getHp()>=2 && !godMod) {

					if(BlockMap.containsKey(new Vector2f(getPlayerTile().x+1, getPlayerTileByCenter().y-1))) {
						current_tile = BlockMap.get(new Vector2f(getPlayerTile().x+1, getPlayerTileByCenter().y-1));
						if (current_tile.isKilling("Killing_T") || !AABB.isAabbYCollision(player.getPos(),
								new Vector2f(player.getScale().x*83,player.getScale().y*83), new Vector2f(PM.GetVector().x,physic.getGravityVector()),
								current_tile.getPos(),
								new Vector4f(current_tile.getAABB().x/32*100, current_tile.getAABB().y/32*100, current_tile.getAABB().z/32*100, current_tile.getAABB().w/32*100))&& current_tile.isSolid()) {
							player.setHp(0);

						}
					}
					else {
						player.setHp(0);
					}
				}
				if(!current_tile.isTriger()){

					physic.collidingY = true;
					PM.can_jump =true;
					player.setPos(AABB.getCorrectPos());
				}
				else {
					under_tile_key = new Vector2f(getPlayerTile().x, getPlayerTileByCenter().y-2);
					under_tile = BlockMap.get(under_tile_key);
					if (current_tile.getType().equals("to spike")) {
						if (under_tile.getType().equals("Block")) {
							BlockTimer.add(new BlockTimer(under_tile_key,under_tile,repair_time));
							BlockMap.replace(under_tile_key, LoadTiles.replaceTile(under_tile.getPos(), AssetsPool.getTile(AssetsPool.spikes.get(under_tile.getID())), under_tile.getCam()));
						}
					}
					else if(current_tile.getType().equals("to floor")) {
						if (under_tile.getType().equals("Spike")) {
							BlockTimer.add(new BlockTimer(under_tile_key,under_tile,repair_time));
							BlockMap.replace(under_tile_key, LoadTiles.replaceTile(under_tile.getPos(), AssetsPool.getTile(AssetsPool.blocks.get(under_tile.getID())), under_tile.getCam()));
						}
					}
					else if(current_tile.getType().equals("destroi") && BlockMap.containsKey(under_tile_key)) {
						ChangeTimer.add(new BlockTimer(under_tile_key,under_tile,destroi_time));


					}
				}
			}


		}
		if(BlockMap.containsKey(new Vector2f(getPlayerTile().x+1, getPlayerTileByCenter().y-1))) {
			current_tile = BlockMap.get(new Vector2f(getPlayerTile().x+1, getPlayerTileByCenter().y-1));
			if (AABB.isAabbYCollision(player.getPos(),
					new Vector2f(player.getScale().x*83,player.getScale().y*83), new Vector2f(PM.GetVector().x,physic.getGravityVector()),
					current_tile.getPos(),
					new Vector4f(current_tile.getAABB().x/32*100, current_tile.getAABB().y/32*100, current_tile.getAABB().z/32*100, current_tile.getAABB().w/32*100))&& current_tile.isSolid()) {


				if (current_tile.isKilling("Killing_T") && player.getHp()>=2 && !godMod) {
					if(BlockMap.containsKey(new Vector2f(getPlayerTile().x, getPlayerTileByCenter().y-1))) {
						current_tile = BlockMap.get(new Vector2f(getPlayerTile().x, getPlayerTileByCenter().y-1));
						if (current_tile.isKilling("Killing_T") || !AABB.isAabbYCollision(player.getPos(),
								new Vector2f(player.getScale().x*83,player.getScale().y*83), new Vector2f(PM.GetVector().x,physic.getGravityVector()),
								current_tile.getPos(),
								new Vector4f(current_tile.getAABB().x/32*100, current_tile.getAABB().y/32*100, current_tile.getAABB().z/32*100, current_tile.getAABB().w/32*100))&& current_tile.isSolid()) {
							player.setHp(0);

						}
					}
					else {

						player.setHp(0);
					}
				}
				if(!current_tile.isTriger()){

					PM.can_jump =true;

					physic.collidingY = true;
					player.setPos(AABB.getCorrectPos());
				}
				else {
					under_tile_key = new Vector2f(getPlayerTile().x+1, getPlayerTileByCenter().y-2);
					under_tile = BlockMap.get(under_tile_key);
					if (current_tile.getType().equals("to spike")) {
						if (under_tile.getType().equals("Block")) {
							BlockTimer.add(new BlockTimer(under_tile_key,under_tile,repair_time));
							BlockMap.replace(under_tile_key, LoadTiles.replaceTile(under_tile.getPos(), AssetsPool.getTile(AssetsPool.spikes.get(under_tile.getID())), under_tile.getCam()));
						}
					}
					else if(current_tile.getType().equals("to floor")) {
						if (under_tile.getType().equals("Spike")) {
							BlockTimer.add(new BlockTimer(under_tile_key,under_tile,repair_time));
							BlockMap.replace(under_tile_key, LoadTiles.replaceTile(under_tile.getPos(), AssetsPool.getTile(AssetsPool.blocks.get(under_tile.getID())), under_tile.getCam()));
						}
					}
					else if(current_tile.getType().equals("destroi") && BlockMap.containsKey(under_tile_key)) {

						ChangeTimer.add(new BlockTimer(under_tile_key,under_tile,destroi_time));

					}

				}
			}

		}
		/*bottom collision end*/
		if (!physic.collidingY) {

			PM.can_jump=false;
		}
		PM.setPlayerVector(new Vector2f(PM.GetVector().x,physic.GravityVector()));

	}
	public GameScene(String MapName, long win, int i, SceneManager scmg) {
		godMod = false;
		timer = 0;
		next_level = false;
		can_move = true;
		BlockTimer = new ArrayList<>();
		ChangeTimer = new ArrayList<>();
		under_tile_key = new Vector2f();
		isLoaded =false;
		physic = new Physic();
		BlockMap = new HashMap<>();
		Buttons = new ArrayList<>();
		AssetsPool.addSound("Assets/Sounds/soundtrack/8_Bit_Retro_Funk.ogg", true);
		bgSound = AssetsPool.getSound("Assets/Sounds/soundtrack/8_Bit_Retro_Funk.ogg");
		cam = new Camera(new Vector2f(0), i);
		this.CamSize = cam.getSize();
		keyListener = new KeyListener(win);
		player = new Player(cam);
		default_pos= new Vector2f(100,115);
		player.setPos(default_pos);
		loadMap = new LoadMap(MapName+".xml");
		PM = new PlayerMovement(win, player.getPos());
		pause = false;
		sdf = new Sdf();
		sdf.generateBitmap("C:/Windows/Fonts/arial.ttf", 1024);
		sdfShader = new Shader("./Shaders/sdfShader.glsl");
		fontRender = new CompRender();
		fontRender.init(sdfShader, cam, sdf);
		respawn = false;
		buttonListener = new ButtonListener(win, new Vector2i(1,2));
		Level_floor_ciling = new Vector2f(0,2450);
		Buttons.add(Start = new Button(cam,new Vector2f(500,100),new Vector2f(50,160), win){
			@Override
			public void function() {

				pause = false;
			}
		});
		Buttons.add(Quit = new Button(cam,new Vector2f(500,100),new Vector2f(50,50), win){
			@Override
			public void function() {
				scmg.switchScene("menu", null);
			}
		});


	}


	public void update() {


		if (respawn){
			for (int i = 0; i < BlockTimer.size(); i++) {
				BlockTimer.get(i).setEnd(true);
			}
			player.setPos(default_pos);
			if (cam.getCamPos().x !=0) {

				cam.setCamPos(new Vector2f(0, cam.getCamPos().y));
			}
			if (cam.getCamPos().y!=0){

				cam.setCamPos(new Vector2f(cam.getCamPos().x,0));

			}
			Level_floor_ciling = new Vector2f(0,2450);
			level = 0;
			respawn = false;
		}

		else if(!isLoaded){

			BlockMap = LoadTiles.load(loadMap.getMap(), cam);
			isLoaded =true;
		}


		else if(!pause) {

			if (player.getPos().y<Level_floor_ciling.x-400) {
				player.setHp(0);
				player.setPos(new Vector2f(player.getPos().x, Level_floor_ciling.x-390));
			}
			if (next_level) {
				if (timer<1) {
					timer+=1d/60d;
				}
				else {
					timer = 0;
					next_level = false;
					level++;
					can_move = true;
					if (level==1){

						player.setPos(new Vector2f(default_pos.x, 2500*level));
						Level_floor_ciling = new Vector2f(2500*level-100, 2500*level+2350);
					}
					else{
						player.setPos(new Vector2f(default_pos.x, 2400*level+2500));
						Level_floor_ciling = new Vector2f(2400*level+2400, 2400*level+2350+2500);

					}
				}

			}
			for (int i = 0; i < ChangeTimer.size(); i++) {
				if (!ChangeTimer.get(i).isChange()) {
					ChangeTimer.get(i).updateR();
				}
				else {
					BlockTimer.add(new BlockTimer(ChangeTimer.get(i).getKey(), ChangeTimer.get(i).getTile(), repair_time));
					BlockMap.remove(ChangeTimer.get(i).getKey());
					toRemoveCh.add(i);

				}
			}

			for (int i = 0; i < BlockTimer.size(); i++) {

				if (!BlockTimer.get(i).isEnd()) {
					BlockTimer.get(i).update();
				}
				else {
					if (BlockMap.containsKey(BlockTimer.get(i).getKey())) {
						BlockMap.replace(BlockTimer.get(i).getKey(), BlockTimer.get(i).getTile());
					}
					else {
						BlockMap.put(BlockTimer.get(i).getKey(), BlockTimer.get(i).getTile());

					}
					toRemove.add(i);

				}

			}
			for (int i = toRemoveCh.size()-1; i > -1; i--) {
				int x = toRemoveCh.get(i);

				ChangeTimer.remove(x);

			}
			toRemoveCh.clear();
			for (int i = toRemove.size()-1; i > -1; i--) {
				int x = toRemove.get(i);

				BlockTimer.remove(x);

			}
			toRemove.clear();

			if (!bgSound.isPlaying()) {
				bgSound.setVolume(8);
				bgSound.play();
			}
			physic.update();
			getCollision();
			if (player.getHp() < 2) {
				PM.setPlayerVector(new Vector2f(0));
				PM.collideX = true;
				physic.SetGravityVector(0);

			}
			if (!can_move) {
				PM.collideX = true;
				PM.can_jump = false;
			}
//			player.setPos(new Vector2f(player.getPos().x+PM.GetVector().x, player.getPos().y+physic.getGravityVector()));
			player.update(new Vector2f(PM.GetVector().x, physic.getGravityVector()));
			if (keyListener.isPressedOnce(GLFW_KEY_ESCAPE)){
				pause = true;
			}
			if (player.getPos().x - (CamSize.x / 2) + (player.getScale().x * 50) > 0) {
				cam.setCamPos(new Vector2f(player.getPos().x - (CamSize.x / 2) + (player.getScale().x * 50), cam.getCamPos().y));
			}
			else {
				cam.setCamPos(new Vector2f(0,cam.getCamPos().y));
			}
			if (player.getPos().y - (CamSize.y / 2) + (player.getScale().y * 50) > Level_floor_ciling.x && player.getPos().y + (CamSize.y / 2) + (player.getScale().y * 50) < Level_floor_ciling.y) {
				cam.setCamPos(new Vector2f(cam.getCamPos().x, player.getPos().y - (CamSize.y / 2) + (player.getScale().y * 50)));

			}

			else if (player.getPos().y - (CamSize.y / 2) + (player.getScale().y * 50) < Level_floor_ciling.x){
				cam.setCamPos(new Vector2f(cam.getCamPos().x,Level_floor_ciling.x));
			}
			else if (player.getPos().y + (CamSize.y / 2) + (player.getScale().y * 50) > Level_floor_ciling.y) {
				cam.setCamPos(new Vector2f(cam.getCamPos().x, Level_floor_ciling.y-cam.getSize().y));
			}
			for (int i = Math.round((cam.getCamPos().x - 200) / 100); i < (Math.round((cam.getCamPos().x + (CamSize.x + 300)) / 100)); i++) {

				for (int j = Math.round((cam.getCamPos().y - 200) / 100); j < (Math.round((cam.getCamPos().y + (CamSize.y + 300)) / 100)); j++) {

					if (BlockMap.containsKey(new Vector2f(i, j))) {
						BlockMap.get(new Vector2f(i, j)).update();

					}

				}
			}


			if (player.getHp() <= 1) {
				fontRender.Update("Don't be trash", player.getPos(),0.5f, new Vector4f(1,1,1,1),1);
				player.setHp(player.getHp() + 1 / 120f);
			}
			else if (player.getHp() < 2 && player.getHp()>1){
				player.setHp(2);
				respawn = true;
			}
		}
		else if(pause){

			if (bgSound.isPlaying()) {
				bgSound.pause();
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
			fontRender.Update("Pause", new Vector2f(600, 500), 0.3f, new Vector4f(0, 1, 0, 1),0);
			fontRender.Update("Continue", new Vector2f(60, 180), 0.09375f, new Vector4f(0, 1, 0, 1),0);
			fontRender.Update("Main menu", new Vector2f(60, 70), 0.09375f, new Vector4f(0, 1, 0, 1),0);
		}

	}

}
