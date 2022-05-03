package com.ATTAR.scenes;

import java.io.Externalizable;
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
	private int lastGC = 0;
	private List<Integer> toRemove = new ArrayList<>();
	private List<Integer> toRemoveCh = new ArrayList<>();
	private List<float[]> projectiles = new ArrayList<>();
	private Projectile projectile;
	private Player player;
	private Vector4f vector4f;
	private boolean isLoaded,godMod;
	private PlayerMovement PM;
	private boolean pause, respawn, can_move, next_level, colliding;
	private Shader sdfShader;
	private HashMap<Integer, Float> delete = new HashMap<Integer, Float>();
	private Button Start, Quit;
	private CompRender fontRender;
	private ButtonListener buttonListener;
	private List<Button> Buttons;
	private Sdf sdf;
	private KeyListener keyListener;
	private Camera cam;
	private Vector2f CamSize, default_pos, under_tile_key, current_tile_pos, vector2f, tileKey;
	private LoadMap loadMap;
	private Sound bgSound;
	private AABB AABB = new AABB();
	private Physic physic;
	private Tiles current_tile, under_tile;
	private Vector2f Level_floor_ciling;
	private StopWatch stopWatch;
	private Background bg;
    private boolean ad = false;
    private float xds;

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

		if (colliding) {


			if (Collector.getBlockMap().containsKey(tileKey.set(getPlayerTileByCenter())) && Collector.getBlockMap().get(tileKey.set(getPlayerTileByCenter()))!=null) {

				current_tile = Collector.getBlockMap().get(tileKey);
				if (current_tile.isTriger()) {


					under_tile_key = new Vector2f(getPlayerTileByCenter().x, getPlayerTileByCenter().y - 1);
					under_tile = Collector.getBlockMap().get(under_tile_key);
					if (current_tile.getType().equals("to spike")) {
						if (under_tile.getType().equals("Block")) {
							BlockTimer.add(new BlockTimer(under_tile_key, under_tile, repair_time));
							Collector.getBlockMap().replace(under_tile_key, LoadTiles.replaceTile(under_tile.getPos(), AssetsPool.getTile(AssetsPool.spikes.get(under_tile.getID())), under_tile.getCam()));
						}
					} else if (current_tile.getType().equals("to floor")) {
						if (under_tile.getType().equals("Spike")) {
							BlockTimer.add(new BlockTimer(under_tile_key, under_tile, repair_time));
							Collector.getBlockMap().replace(under_tile_key, LoadTiles.replaceTile(under_tile.getPos(), AssetsPool.getTile(AssetsPool.blocks.get(under_tile.getID())), under_tile.getCam()));

						}
					} else if (current_tile.getType().equals("destroi") && Collector.getBlockMap().containsKey(under_tile_key) && Collector.getBlockMap().get(tileKey.set(under_tile_key))!=null) {
						ChangeTimer.add(new BlockTimer(under_tile_key, under_tile, destroi_time));


					}
				}
			}


			/*left collision beginning*/
			if (Collector.getBlockMap().containsKey(tileKey.set(getPlayerTileByCenter().x - 1, getPlayerTile().y)) && Collector.getBlockMap().get(tileKey)!=null) {
				current_tile = Collector.getBlockMap().get(tileKey);
				current_tile_pos = getPosByKey(tileKey);
				if (AABB.isAabbXCollision(player.getPos(),
						player.getSize(), PM.GetVector(),
						current_tile_pos,
						new Vector4f(current_tile.getAABB().x / 32 * 100, current_tile.getAABB().y / 32 * 100, current_tile.getAABB().z / 32 * 100, current_tile.getAABB().w / 32 * 100)) && current_tile.isSolid()) {


					if (current_tile.isKilling("Killing_R") && player.getHp() >= 2 && !godMod) {
						if (Collector.getBlockMap().containsKey(tileKey.set(getPlayerTileByCenter().x - 1, getPlayerTile().y + 1)) && Collector.getBlockMap().get(tileKey)!=null) {
							current_tile = Collector.getBlockMap().get(tileKey);
							current_tile_pos = getPosByKey(tileKey);
							if (current_tile.isKilling("Killing_R") || !AABB.isAabbXCollision(player.getPos(),
									player.getSize(), PM.GetVector(),
									current_tile_pos,
									new Vector4f(current_tile.getAABB().x / 32 * 100, current_tile.getAABB().y / 32 * 100, current_tile.getAABB().z / 32 * 100, current_tile.getAABB().w / 32 * 100)) && current_tile.isSolid()) {
								colliding = false;
								player.setHp(0);
							}
						} else {
							colliding = false;
							player.setHp(0);
						}

					}
					if (!current_tile.isTriger()) {

						PM.collideX = true;
						player.setPos(AABB.getCorrectPos().x, AABB.getCorrectPos().y);
					} else {
						under_tile_key = new Vector2f(getPlayerTileByCenter().x - 1, getPlayerTile().y - 1);
						under_tile = Collector.getBlockMap().get(under_tile_key);
						if (current_tile.getType().equals("to spike")) {
							if (under_tile.getType().equals("Block")) {
								BlockTimer.add(new BlockTimer(under_tile_key, under_tile, repair_time));
								Collector.getBlockMap().replace(under_tile_key, LoadTiles.replaceTile(under_tile.getPos(), AssetsPool.getTile(AssetsPool.spikes.get(under_tile.getID())), under_tile.getCam()));
							}
						} else if (current_tile.getType().equals("to floor")) {
							if (under_tile.getType().equals("Spike")) {
								BlockTimer.add(new BlockTimer(under_tile_key, under_tile, repair_time));
								Collector.getBlockMap().replace(under_tile_key, LoadTiles.replaceTile(under_tile.getPos(), AssetsPool.getTile(AssetsPool.blocks.get(under_tile.getID())), under_tile.getCam()));

							}
						} else if (current_tile.getType().equals("destroi") && Collector.getBlockMap().containsKey(under_tile_key) && Collector.getBlockMap().get(under_tile_key)!=null) {
							ChangeTimer.add(new BlockTimer(under_tile_key, under_tile, destroi_time));


						}
					}

				}
			}
			if (Collector.getBlockMap().containsKey(tileKey.set(getPlayerTileByCenter().x - 1, getPlayerTile().y + 1)) && Collector.getBlockMap().get(tileKey)!=null) {
				current_tile_pos = getPosByKey(tileKey);
				current_tile = Collector.getBlockMap().get(tileKey);
				if (AABB.isAabbXCollision(player.getPos(),
						player.getSize(), PM.GetVector(),
						current_tile_pos,
						new Vector4f(current_tile.getAABB().x / 32 * 100, current_tile.getAABB().y / 32 * 100, current_tile.getAABB().z / 32 * 100, current_tile.getAABB().w / 32 * 100)) && current_tile.isSolid()) {


					if (current_tile.isKilling("Killing_R") && player.getHp() >= 2 && !godMod) {
						if (Collector.getBlockMap().containsKey(tileKey.set(getPlayerTileByCenter().x - 1, getPlayerTile().y)) && Collector.getBlockMap().get(tileKey)!=null) {
							current_tile = Collector.getBlockMap().get(tileKey);
							current_tile_pos = getPosByKey(tileKey);
							if (current_tile.isKilling("Killing_R") || !AABB.isAabbXCollision(player.getPos(), player.getSize(), PM.GetVector(), current_tile_pos, new Vector4f(current_tile.getAABB().x / 32 * 100, current_tile.getAABB().y / 32 * 100, current_tile.getAABB().z / 32 * 100, current_tile.getAABB().w / 32 * 100)) && current_tile.isSolid()) {

								colliding = false;
								player.setHp(0);

							}
						} else {
							colliding = false;
								player.setHp(0);
						}
					}
					if (!current_tile.isTriger()) {
						PM.collideX = true;
						player.setPos(AABB.getCorrectPos().x, AABB.getCorrectPos().y);
					}
				}

			}
			/*left collision end*/
			/*right collision beginning*/
			if (Collector.getBlockMap().containsKey(tileKey.set(getPlayerTileByCenter().x + 1, getPlayerTile().y)) && Collector.getBlockMap().get(tileKey)!=null) {
				current_tile_pos = getPosByKey(tileKey);
				current_tile = Collector.getBlockMap().get(tileKey);
				if (AABB.isAabbXCollision(player.getPos(),
						player.getSize(), PM.GetVector(),
						current_tile_pos,
						new Vector4f(current_tile.getAABB().x / 32 * 100, current_tile.getAABB().y / 32 * 100, current_tile.getAABB().z / 32 * 100, current_tile.getAABB().w / 32 * 100)) && current_tile.isSolid()) {


					if (current_tile.isKilling("Killing_L") && player.getHp() >= 2 && !godMod) {
						if (Collector.getBlockMap().containsKey(tileKey.set(getPlayerTileByCenter().x + 1, getPlayerTile().y + 1)) && Collector.getBlockMap().get(tileKey)!=null) {
							current_tile = Collector.getBlockMap().get(tileKey);
							current_tile_pos = getPosByKey(tileKey);
							if (current_tile.isKilling("Killing_L") || !AABB.isAabbXCollision(player.getPos(),
									player.getSize(), PM.GetVector(),
									current_tile_pos,
									new Vector4f(current_tile.getAABB().x / 32 * 100, current_tile.getAABB().y / 32 * 100, current_tile.getAABB().z / 32 * 100, current_tile.getAABB().w / 32 * 100)) && current_tile.isSolid()) {
								colliding = false;
								player.setHp(0);
							}
						} else {
							colliding = false;
								player.setHp(0);
						}
					}
					if (!current_tile.isTriger()) {

						if (current_tile.getType().equals("Door_Close")) {
							can_move = false;
							next_level = true;
						}
						PM.collideX = true;
						player.setPos(AABB.getCorrectPos().x, AABB.getCorrectPos().y);
					} else {
						under_tile_key = new Vector2f(getPlayerTileByCenter().x + 1, getPlayerTile().y - 1);
						under_tile = Collector.getBlockMap().get(under_tile_key);
						current_tile_pos = getPosByKey(tileKey);
						if (current_tile.getType().equals("to spike")) {
							if (under_tile.getType().equals("Block")) {
								BlockTimer.add(new BlockTimer(under_tile_key, under_tile, repair_time));
								Collector.getBlockMap().replace(under_tile_key, LoadTiles.replaceTile(under_tile.getPos(), AssetsPool.getTile(AssetsPool.spikes.get(under_tile.getID())), under_tile.getCam()));
							}
						} else if (current_tile.getType().equals("to floor")) {
							if (under_tile.getType().equals("Spike")) {
								BlockTimer.add(new BlockTimer(under_tile_key, under_tile, repair_time));
								Collector.getBlockMap().replace(under_tile_key, LoadTiles.replaceTile(under_tile.getPos(), AssetsPool.getTile(AssetsPool.blocks.get(under_tile.getID())), under_tile.getCam()));
							}
						} else if (current_tile.getType().equals("destroi") && Collector.getBlockMap().containsKey(under_tile_key) && Collector.getBlockMap().get(under_tile_key)!=null) {
							ChangeTimer.add(new BlockTimer(under_tile_key, under_tile, destroi_time));

						}
					}
				}
			}
			if (Collector.getBlockMap().containsKey(tileKey.set(getPlayerTileByCenter().x + 1, getPlayerTile().y + 1)) && Collector.getBlockMap().get(tileKey)!=null) {
				current_tile_pos = getPosByKey(tileKey);
				current_tile = Collector.getBlockMap().get(tileKey);
				if (AABB.isAabbXCollision(player.getPos(),
						player.getSize(), PM.GetVector(),
						current_tile_pos,
						new Vector4f(current_tile.getAABB().x / 32 * 100, current_tile.getAABB().y / 32 * 100, current_tile.getAABB().z / 32 * 100, current_tile.getAABB().w / 32 * 100)) && current_tile.isSolid()) {


					if (current_tile.isKilling("Killing_L") && player.getHp() >= 2 && !godMod) {
						if (Collector.getBlockMap().containsKey(tileKey.set(getPlayerTileByCenter().x + 1, getPlayerTile().y)) && Collector.getBlockMap().get(tileKey)!=null) {
							current_tile = Collector.getBlockMap().get(tileKey);
							current_tile_pos = getPosByKey(tileKey);
							if (current_tile.isKilling("Killing_L") || !AABB.isAabbXCollision(player.getPos(),
									player.getSize(), PM.GetVector(),
									current_tile_pos,
									new Vector4f(current_tile.getAABB().x / 32 * 100, current_tile.getAABB().y / 32 * 100, current_tile.getAABB().z / 32 * 100, current_tile.getAABB().w / 32 * 100)) && current_tile.isSolid()) {

								colliding = false;
								player.setHp(0);
							}

						} else {
							colliding = false;
								player.setHp(0);
						}
					}
					if (!current_tile.isTriger()) {
						if (current_tile.getType().equals("Door_Close")) {
							can_move = false;
							next_level = true;
						}
						PM.collideX = true;
						player.setPos(AABB.getCorrectPos().x, AABB.getCorrectPos().y);
					}

				}

			}
			/*right collision end*/
			/*top collision beginning*/
			if (Collector.getBlockMap().containsKey(tileKey.set(getPlayerTile().x, getPlayerTileByCenter().y + 1)) && Collector.getBlockMap().get(tileKey)!=null) {
				current_tile_pos = getPosByKey(tileKey);
				current_tile = Collector.getBlockMap().get(tileKey);
				if (AABB.isAabbYCollision(player.getPos(),
						player.getSize(), PM.GetVector().x, physic.getGravityVector(),
						current_tile_pos,
						new Vector4f(current_tile.getAABB().x / 32 * 100, current_tile.getAABB().y / 32 * 100, current_tile.getAABB().z / 32 * 100, current_tile.getAABB().w / 32 * 100)) && current_tile.isSolid()) {


					if (current_tile.isKilling("Killing_B") && player.getHp() >= 2 && !godMod) {
						if (Collector.getBlockMap().containsKey(tileKey.set(getPlayerTile().x + 1, getPlayerTileByCenter().y + 1)) && Collector.getBlockMap().get(tileKey)!=null) {
							current_tile = Collector.getBlockMap().get(tileKey);
							current_tile_pos = getPosByKey(tileKey);
							if (current_tile.isKilling("Killing_B") || !AABB.isAabbYCollision(player.getPos(),
									player.getSize(), PM.GetVector().x, physic.getGravityVector(),
									current_tile_pos,
									new Vector4f(current_tile.getAABB().x / 32 * 100, current_tile.getAABB().y / 32 * 100, current_tile.getAABB().z / 32 * 100, current_tile.getAABB().w / 32 * 100)) && current_tile.isSolid()) {
								colliding = false;
								player.setHp(0);
							}
						} else {
							colliding = false;
								player.setHp(0);

						}
					}
					if (!current_tile.isTriger()) {

						physic.collidingY = true;
						player.setPos(AABB.getCorrectPos().x, AABB.getCorrectPos().y);
					}
				}

			}
			if (Collector.getBlockMap().containsKey(tileKey.set(getPlayerTile().x + 1, getPlayerTileByCenter().y + 1)) && Collector.getBlockMap().get(tileKey)!=null) {
				current_tile_pos = getPosByKey(tileKey);
				current_tile = Collector.getBlockMap().get(tileKey);
				if (AABB.isAabbYCollision(player.getPos(),
						player.getSize(), PM.GetVector().x, physic.getGravityVector(),
						current_tile_pos,
						new Vector4f(current_tile.getAABB().x / 32 * 100, current_tile.getAABB().y / 32 * 100, current_tile.getAABB().z / 32 * 100, current_tile.getAABB().w / 32 * 100)) && current_tile.isSolid()) {

					if (current_tile.isKilling("Killing_B") && player.getHp() >= 2 && !godMod) {
						if (Collector.getBlockMap().containsKey(tileKey.set(getPlayerTile().x, getPlayerTileByCenter().y + 1)) && Collector.getBlockMap().get(tileKey)!=null) {
							current_tile = Collector.getBlockMap().get(tileKey);
							current_tile_pos = getPosByKey(tileKey);
							if (current_tile.isKilling("Killing_B") || !AABB.isAabbYCollision(player.getPos(),
									player.getSize(), PM.GetVector().x, physic.getGravityVector(),
									current_tile_pos,
									new Vector4f(current_tile.getAABB().x / 32 * 100, current_tile.getAABB().y / 32 * 100, current_tile.getAABB().z / 32 * 100, current_tile.getAABB().w / 32 * 100)) && current_tile.isSolid()) {
								colliding = false;
								player.setHp(0);
							}
						} else {
							colliding = false;
								player.setHp(0);
						}
					}
					if (!current_tile.isTriger()) {

						physic.collidingY = true;
						player.setPos(AABB.getCorrectPos().x, AABB.getCorrectPos().y);
					}

				}

			}
			/*top collision end*/
			/*bottom collision beginning*/
			if (Collector.getBlockMap().containsKey(tileKey.set(getPlayerTile().x, getPlayerTileByCenter().y - 1)) && Collector.getBlockMap().get(tileKey)!=null) {
				current_tile_pos = getPosByKey(tileKey);

				current_tile = Collector.getBlockMap().get(tileKey);
				if (AABB.isAabbYCollision(player.getPos(),
						player.getSize(), PM.GetVector().x, physic.getGravityVector(),
						current_tile_pos,
						new Vector4f(current_tile.getAABB().x / 32 * 100, current_tile.getAABB().y / 32 * 100,
								current_tile.getAABB().z / 32 * 100, current_tile.getAABB().w / 32 * 100)) && current_tile.isSolid()) {

					if (current_tile.isKilling("Killing_T") && player.getHp() >= 2 && !godMod) {

						if (Collector.getBlockMap().containsKey(tileKey.set(getPlayerTile().x + 1, getPlayerTileByCenter().y - 1)) && Collector.getBlockMap().get(tileKey)!=null) {
							current_tile = Collector.getBlockMap().get(tileKey);
							current_tile_pos = getPosByKey(tileKey);
							if (current_tile.isKilling("Killing_T") || !AABB.isAabbYCollision(player.getPos(),
									player.getSize(), PM.GetVector().x, physic.getGravityVector(),
									current_tile_pos,
									new Vector4f(current_tile.getAABB().x / 32 * 100, current_tile.getAABB().y / 32 * 100, current_tile.getAABB().z / 32 * 100, current_tile.getAABB().w / 32 * 100)) && current_tile.isSolid()) {
								colliding = false;
								player.setHp(0);

							}
						} else {
							colliding = false;
								player.setHp(0);
						}
					}
					if (!current_tile.isTriger()) {

						physic.collidingY = true;
						PM.can_jump = true;
						player.setPos(AABB.getCorrectPos().x, AABB.getCorrectPos().y);
					} else {
						under_tile_key = new Vector2f(getPlayerTile().x, getPlayerTileByCenter().y - 2);
						under_tile = Collector.getBlockMap().get(under_tile_key);
						if (current_tile.getType().equals("to spike")) {
							if (under_tile.getType().equals("Block")) {
								BlockTimer.add(new BlockTimer(under_tile_key, under_tile, repair_time));
								Collector.getBlockMap().replace(under_tile_key, LoadTiles.replaceTile(under_tile.getPos(), AssetsPool.getTile(AssetsPool.spikes.get(under_tile.getID())), under_tile.getCam()));
							}
						} else if (current_tile.getType().equals("to floor")) {
							if (under_tile.getType().equals("Spike")) {
								BlockTimer.add(new BlockTimer(under_tile_key, under_tile, repair_time));
								Collector.getBlockMap().replace(under_tile_key, LoadTiles.replaceTile(under_tile.getPos(), AssetsPool.getTile(AssetsPool.blocks.get(under_tile.getID())), under_tile.getCam()));
							}
						} else if (current_tile.getType().equals("destroi") && Collector.getBlockMap().containsKey(under_tile_key) && Collector.getBlockMap().get(under_tile_key)!=null) {
							ChangeTimer.add(new BlockTimer(under_tile_key, under_tile, destroi_time));


						}
					}
				}


			}
			if (Collector.getBlockMap().containsKey(tileKey.set(getPlayerTile().x + 1, getPlayerTileByCenter().y - 1)) && Collector.getBlockMap().get(tileKey)!=null) {
				current_tile_pos = getPosByKey(tileKey);

				current_tile = Collector.getBlockMap().get(tileKey);
				if (AABB.isAabbYCollision(player.getPos(),
						player.getSize(), PM.GetVector().x, physic.getGravityVector(),
						current_tile_pos,

						new Vector4f(current_tile.getAABB().x / 32 * 100, current_tile.getAABB().y / 32 * 100, current_tile.getAABB().z / 32 * 100, current_tile.getAABB().w / 32 * 100)) && current_tile.isSolid()) {


					if (current_tile.isKilling("Killing_T") && player.getHp() >= 2 && !godMod) {
						if (Collector.getBlockMap().containsKey(tileKey.set(getPlayerTile().x, getPlayerTileByCenter().y - 1)) && Collector.getBlockMap().get(tileKey)!=null) {
							current_tile_pos = getPosByKey(tileKey);
							current_tile = Collector.getBlockMap().get(tileKey);
							if (current_tile.isKilling("Killing_T") || !AABB.isAabbYCollision(player.getPos(),
									player.getSize(), PM.GetVector().x, physic.getGravityVector(),
									current_tile_pos,
									new Vector4f(current_tile.getAABB().x / 32 * 100, current_tile.getAABB().y / 32 * 100, current_tile.getAABB().z / 32 * 100, current_tile.getAABB().w / 32 * 100)) && current_tile.isSolid()) {
								
								colliding = false;
								player.setHp(0);

							}
						} else {

							colliding = false;
								player.setHp(0);
						}
					}
					if (!current_tile.isTriger()) {

						PM.can_jump = true;

						physic.collidingY = true;
						player.setPos(AABB.getCorrectPos().x, AABB.getCorrectPos().y);
					} else {
						under_tile_key = new Vector2f(getPlayerTile().x + 1, getPlayerTileByCenter().y - 2);
						under_tile = Collector.getBlockMap().get(under_tile_key);
						if (current_tile.getType().equals("to spike")) {
							if (under_tile.getType().equals("Block")) {
								BlockTimer.add(new BlockTimer(under_tile_key, under_tile, repair_time));
								Collector.getBlockMap().replace(under_tile_key, LoadTiles.replaceTile(under_tile.getPos(), AssetsPool.getTile(AssetsPool.spikes.get(under_tile.getID())), under_tile.getCam()));
							}
						} else if (current_tile.getType().equals("to floor")) {
							if (under_tile.getType().equals("Spike")) {
								BlockTimer.add(new BlockTimer(under_tile_key, under_tile, repair_time));
								Collector.getBlockMap().replace(under_tile_key, LoadTiles.replaceTile(under_tile.getPos(), AssetsPool.getTile(AssetsPool.blocks.get(under_tile.getID())), under_tile.getCam()));
							}
						} else if (current_tile.getType().equals("destroi") && Collector.getBlockMap().containsKey(under_tile_key) && Collector.getBlockMap().get(under_tile_key)!=null) {

							ChangeTimer.add(new BlockTimer(under_tile_key, under_tile, destroi_time));

						}

					}
				}

			}
		}
		/*bottom collision end*/
		if (!physic.collidingY) {

			PM.can_jump=false;
		}
		if (colliding) {

			PM.setPlayerVector(returnVector2f(PM.GetVector().x,physic.GravityVector()));
		}

	}
	
	public GameScene(String MapName, long win, int i, SceneManager scmg) {
		godMod = true;
		colliding = true;
        vector2f = new Vector2f();
        tileKey = new Vector2f();
        vector4f = new Vector4f();
        ReadSave.ReadSave();
        stopWatch = new StopWatch(Collector.getTime());
        timer = 0;
        next_level = false;
        level = Collector.getLevel();
        can_move = true;
        BlockTimer = new ArrayList<>();
        ChangeTimer = new ArrayList<>();
        under_tile_key = new Vector2f();
        isLoaded =false;
        physic = new Physic();

        Buttons = new ArrayList<>();
        AssetsPool.addSound("Assets/Sounds/soundtrack/8_Bit_Retro_Funk.ogg", true);
        bgSound = AssetsPool.getSound("Assets/Sounds/soundtrack/8_Bit_Retro_Funk.ogg");
        cam = new Camera(new Vector2f(0), i);
        this.CamSize = cam.getSize();
        keyListener = new KeyListener(win);
        player = new Player(cam);
        default_pos= new Vector2f(100,115);
        player.setPos(Collector.getPlayerPos().x, Collector.getPlayerPos().y);
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
        bg = new Background(cam);
        projectile = new Projectile(cam);

        if (level == 0){

            Level_floor_ciling = new Vector2f(0,2450);
            Collector.setCeil(24);
			Collector.setFloor(0);
		}
		else if (level==1){

			Level_floor_ciling = new Vector2f(25*level+23,2450);
			Collector.setCeil(25*level+23);
			Collector.setFloor(25-1);
		}
		else{
			Level_floor_ciling = new Vector2f(25*level+23+25,2450);
			Collector.setCeil(24*level+23+25);
			Collector.setFloor(24*level+24);
		}
		bg.setPos(0, Collector.getFloor()*100);
		Buttons.add(Start = new Button(cam,new Vector2f(500,100),new Vector2f(50,160), win){
			@Override
			public void function() {

				pause = false;
			}
		});
		Buttons.add(Quit = new Button(cam,new Vector2f(500,100),new Vector2f(50,50), win){
			@Override
			public void function() {

				pause = false;
				scmg.switchScene("menu", null);
			}
		});


	}
	
	public void update() {




		if(!isLoaded) {

			load();

			cam.setCamPos(0,0);
		}
		else if (respawn) {
			for (int i = 0; i < BlockTimer.size(); i++) {
				BlockTimer.get(i).setEnd(true);
			}
			player.setPos(default_pos.x, default_pos.y);
			if (cam.getCamPos().x !=0) {

				cam.setCamPos(0, cam.getCamPos().y);
			}
			if (cam.getCamPos().y!=0){

				cam.setCamPos(cam.getCamPos().x,0);

			}
			Level_floor_ciling = new Vector2f(0,2450);
			Collector.setCeil(24);
			Collector.setFloor(0);
			cam.setCamPos(10000,0);
			fontRender.Update("Loading", new Vector2f(10000,500),0.09f,new Vector4f(1),1);
			isLoaded = false;
			level = 0;
			colliding = true;
			respawn = false;
		}


		else if(!pause) {

			if (level>=1) {

				bg.setPos(0, 0);
				bg.update(0);
			}
			else {
				bg.setPos(0, 0);
				bg.update(1);
			}
			stopWatch.addTime();

			if (player.getPos().y<-400) {
				if (colliding) {
					colliding = false;

					player.setHp(0);
				}
				player.setPos(player.getPos().x, -390);
			}

			nextLevel();
			tileChanger();

			if (!bgSound.isPlaying()) {
				bgSound.setVolume(8);
				bgSound.play();
			}
			physic.update();
			getCollision();
            updateProjectiles();
            if (CursorInput.isPressed(GLFW_MOUSE_BUTTON_1) && !ad) {
                projectiles.add(projectile.init(player.getPos().x, player.getPos().y+250, Collector.getCursorPos().x, Collector.getCursorPos().y));
                ad = true;
            }
            if (ad) {
                if (xds<1f) {
                    xds+=1f/5f;
                }
                else {
                    ad = false;
                    xds = 0;
                }
            }
			tileRender();
			updateHP();

			player.update(new Vector2f(PM.GetVector().x, physic.getGravityVector()));
			if (!can_move) {
				PM.collideX = true;
				PM.can_jump = false;
			}

			if (keyListener.isPressedOnce(GLFW_KEY_ESCAPE)){
				setPause();
			}
			camUpdate();




			fontRender.Update((int)stopWatch.getTime().x+":"+(int)stopWatch.getTime().y+":"+(int)stopWatch.getTime().z+":"+(int)stopWatch.getTime().w, returnVector2f(cam.getCamPos().x+20, cam.getCamPos().y+cam.getSize().y-60),0.05f, vector4f.set(1), 1);
			clean();
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
			fontRender.Update("Pause", returnVector2f(600, 500), 0.3f, vector4f.set(0, 1, 0, 1),0);
			fontRender.Update("Continue", returnVector2f(60, 180), 0.09375f, vector4f.set(0, 1, 0, 1),0);
			fontRender.Update("Main menu", returnVector2f(60, 70), 0.09375f, vector4f.set(0, 1, 0, 1),0);
		}
	}
	public void setPause() {
		if (player.getHp() <= 1){
			SaveWriter.WriteSave(new Vector2f(100,100), 0, stopWatch.getTime());

		}
		else{

			SaveWriter.WriteSave(player.getPos(), level, stopWatch.getTime());
		}

		pause = true;
	}
	public void load() {
		System.gc();
		LoadTiles.load(loadMap.getMap(), cam);
		isLoaded =true;

	}
	public void tileChanger() {
		for (int i = 0; i < ChangeTimer.size(); i++) {
			if (!ChangeTimer.get(i).isChange()) {
				ChangeTimer.get(i).updateR();
			}
			else {
				BlockTimer.add(new BlockTimer(ChangeTimer.get(i).getKey(), ChangeTimer.get(i).getTile(), repair_time));
				Collector.getBlockMap().remove(ChangeTimer.get(i).getKey());
				toRemoveCh.add(i);

			}
		}

		for (int i = 0; i < BlockTimer.size(); i++) {

			if (!BlockTimer.get(i).isEnd()) {
				BlockTimer.get(i).update();
			}
			else {
				if (Collector.getBlockMap().containsKey(BlockTimer.get(i).getKey()) && Collector.getBlockMap().get(BlockTimer.get(i).getKey())!=null) {
					Collector.getBlockMap().replace(BlockTimer.get(i).getKey(), BlockTimer.get(i).getTile());
				}
				else {
					Collector.getBlockMap().put(BlockTimer.get(i).getKey(), BlockTimer.get(i).getTile());

				}
				toRemove.add(i);

			}

		}
		for (int i = toRemoveCh.size()-1; i >=0; i--) {
			int x = toRemoveCh.get(i);

			ChangeTimer.remove(x);

		}
		toRemoveCh.clear();
		for (int i =toRemove.size()-1; i >=0; i--) {
			int x = toRemove.get(i);

			BlockTimer.remove(x);

		}
		toRemove.clear();
	}
	public void tileRender () {
		for (int i = Math.round((cam.getCamPos().x - 200) / 100); i < (Math.round((cam.getCamPos().x + (CamSize.x + 300)) / 100)); i++) {

			for (int j = Math.round((cam.getCamPos().y - 200) / 100); j < (Math.round((cam.getCamPos().y + (CamSize.y + 300)) / 100)); j++) {

				if (Collector.getBlockMap().containsKey(returnVector2f(i, j)) && Collector.getBlockMap().get(returnVector2f(i, j))!=null) {
//					System.out.println(new Vector2f(i, j));
					Collector.getBlockMap().get(returnVector2f(i, j)).setPos(i*100,j*100);

					Collector.getBlockMap().get(returnVector2f(i, j)).update();

				}
				else {
//					System.out.println(Collector.getBlockMap().get(returnVector2f(i,j)));
//					System.out.println(returnVector2f(i,j));
				}

			}
		}
	}
	public void updateHP() {
		if (player.getHp() < 2) {
			PM.setPlayerVector(new Vector2f(0));
//			physic.SetGravityVector(0);

		}
		if (player.getHp() < 2 && player.getHp() >0) {
			PM.collideX = true;

		}
		if (player.getHp()==0) {
//			physic.collidingY = false;
			physic.SetGravityVector(8f);

		}
		if (player.getHp() <= 1) {
			SaveWriter.WriteSave(new Vector2f(100,100), 0, stopWatch.getTime());
			fontRender.Update("Don't be trash", new Vector2f(cam.getCamPos().x, cam.getCamPos().y+500),0.09f, new Vector4f(1,1,1,1),1);
			player.setHp(player.getHp() + 1 / 120f);
		}
		else if (player.getHp() < 2 && player.getHp()>1){
			player.setHp(2);
			respawn = true;
		}
	}
	public void clean() {
		if ((int) stopWatch.getTime().y-lastGC >=1) {
			System.gc();
			lastGC = (int) stopWatch.getTime().y;
		}
	}
	public void camUpdate() {
		if (colliding) {


			if (player.getPos().x - (CamSize.x / 2) + (player.getScale().x * 50) > 0 && player.getPos().x - (CamSize.x / 2) + (player.getScale().x * 50) + cam.getSize().x < 10000) {
				cam.setCamPos(player.getPos().x - (CamSize.x / 2) + (player.getScale().x * 50), cam.getCamPos().y);
			} else if (player.getPos().x - (CamSize.x / 2) + (player.getScale().x * 50) < 0) {
				cam.setCamPos(0, cam.getCamPos().y);
			} else if (player.getPos().x - (CamSize.x / 2) + (player.getScale().x * 50) + cam.getSize().x > 10000) {
				cam.setCamPos(10000 - cam.getSize().x, cam.getCamPos().y);
			}
			if (player.getPos().y - (CamSize.y / 2) + (player.getScale().y * 50) > Level_floor_ciling.x && player.getPos().y + (CamSize.y / 2) + (player.getScale().y * 50) < Level_floor_ciling.y) {
				cam.setCamPos(cam.getCamPos().x, player.getPos().y - (CamSize.y / 2) + (player.getScale().y * 50));

			} else if (player.getPos().y - (CamSize.y / 2) + (player.getScale().y * 50) < Level_floor_ciling.x) {
				cam.setCamPos(cam.getCamPos().x, Level_floor_ciling.x);
			} else if (player.getPos().y + (CamSize.y / 2) + (player.getScale().y * 50) > Level_floor_ciling.y) {
				cam.setCamPos(cam.getCamPos().x, Level_floor_ciling.y - cam.getSize().y);
			}

		}
	}
	public void nextLevel() {
		if (next_level) {
			if (timer<1) {
				timer+=1d/60d;
			}
			else {
				timer = 0;
				next_level = false;
				level++;
				can_move = true;

				cam.setCamPos(10000,0);
				fontRender.Update("Loading", returnVector2f(10000,500),0.09f,vector4f.set(1),1);

				if (level==1){

					player.setPos(default_pos.x, default_pos.y);

					Collector.setCeil(25+23);
					Collector.setFloor(25-1);
					isLoaded = false;
				}
				else{
					player.setPos(default_pos.x, default_pos.y);

					Collector.setCeil(24*level+23+25);
					Collector.setFloor(24*level+24);
					isLoaded = false;
				}
			}

		}
	}
	public Vector2f getPosByKey(Vector2f key) {

		returnVector2f(key.x*100, key.y*100);
		return vector2f;
	}
	public Vector2f returnVector2f(float x, float y) {
		vector2f.set(x,y);
		return vector2f;
	}
	public void updateProjectiles() {
		for (int i = projectiles.size()-1; i >= 0; i--) {
			projectile.setDistanceAndPos(projectiles.get(i)[0], projectiles.get(i)[1], projectiles.get(i)[2], projectiles.get(i)[3]);
			projectile.update(player.getPos(), new Vector2f(0), player.getSize());
			if (projectile.updateArray()[4] >= 1) {

				if (!delete.containsKey(i)) {

					delete.put(i, 0f);
				}
				else{
					delete.replace(i, delete.get(i)+1);
				}
				projectile.updateArray(0f, 0f);
				projectile.updateArray(delete.get(i)+1);


				if (delete.get(i)>=7) {
					projectiles.remove(i);
					delete.remove(i);
				}
			}
			else {

				projectiles.set(i, projectile.updateArray());
			}
		}


	}
}
