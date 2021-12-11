package com.ATTAR.maps;

import com.ATTAR.defaultes.AssetsPool;
import com.ATTAR.defaultes.FileReader;
import com.ATTAR.objects.Tiles;
import org.joml.*;
import java.util.*;
public class TileSetLoad {




    private String[] TileSetData;
    private String TileSet;
    private Thread loader;
    private List<Float> width, height;
    private List<HashMap<String, Boolean>> Killing;
    private List<Boolean> Solid, animated, triger;
    private List<Integer> ID,fps;
    private List<Vector4f> AABB;
    private List<String> texture, type;
    private boolean TileLoaded = false;
    private HashMap<Vector2f, Tiles> BlockMap = new HashMap<>();
    public void addAABB(Vector4f AABB) {
        this.AABB.add(AABB);
    }

    public HashMap<Vector2f, Tiles> getBlockMap() {
        return BlockMap;
    }

    public void setBlockMap(HashMap<Vector2f, Tiles> blockMap) {
        BlockMap = blockMap;
    }

    public List<Boolean> getSolid() {
        return Solid;
    }

    public List<Boolean> getTriger() {
        return triger;
    }

    public List<HashMap<String, Boolean>> getKilling() {
        return Killing;
    }

    public List<Boolean> getAnimated() {
        return animated;
    }

    public List<Integer> getID() {
        return ID;
    }

    public List<Integer> getFps() {
        return fps;
    }

    public boolean isTileLoaded() {return TileLoaded;}

    public void setTileLoaded(boolean tileLoaded) {TileLoaded = tileLoaded;}

    public List<Float> getWidth() {
        return width;
    }

    public void addWidth(float value) {
        this.width.add(value);
    }

    public void addTriger(boolean value) {
        this.triger.add(value);
    }

    public void addID(int value) {
        this.ID.add(value);
    }

    public void addFps(int value) {
        this.fps.add(value);
    }

    public void addSolid(boolean value) {
        this.Solid.add(value);
    }

    public void addAnimated(boolean value) {
        this.animated.add(value);
    }

    public void addKilling(HashMap<String, Boolean> Killing) {
        this.Killing.add(Killing);
    }

    public List<Float> getHeight() {
        return height;
    }

    public List<Vector4f> getAABB() {
        return AABB;
    }

    public void addHeight(float value) {
        this.height.add(value);
    }

    public List<String> getTexture() {
        return texture;
    }


    public void addTexture(String value) {
        this.texture.add(value);
    }



    public List<String> getType() {
        return type;
    }


    public void addType(String value) {
        this.type.add(value);
    }

    public String getTileSet() {
        return TileSet;
    }

    public void setTileSet(String tileSet) {
        TileSet = tileSet;
    }

    public String[] getTileSetData() {
        return TileSetData;
    }

    public void setTileSetData(String[] tileSetData) {
        TileSetData = tileSetData;
    }

    public TileSetLoad() {
        width = new ArrayList<>();
        height = new ArrayList<>();
        Solid = new ArrayList<>();
        Killing = new ArrayList<>();
        animated = new ArrayList<>();
        AABB = new ArrayList<>();
        texture = new ArrayList<>();
        type = new ArrayList<>();
        triger = new ArrayList<>();
        ID = new ArrayList<>();
        fps = new ArrayList<>();

    }
    public void TileSetLoader() {


        setTileSet(FileReader.ReadFile("./Maps/", "TileSet.xml"));


    }


    public void fillAsPool() {
        for (int i = 0; i<getWidth().size(); i++){

            AssetsPool.addTile(new Tiles(getAABB().get(i), getTexture().get(i), new Vector2f(getWidth().get(i), getHeight().get(i)),
                    getSolid().get(i), getTriger().get(i), getKilling().get(i), getAnimated().get(i), getType().get(i),getFps().get(i), getID().get(i)));
        }
    }
    public void interpretData () {
        loader = new Thread(new Runnable() {
            @Override
            public void run() {


                    TileSetLoader();
                    setTileSetData(TileSet.split("\r\n"));

                    for (int i = 0; i<getTileSetData().length; i++) {
                        String currentLine = getTileSetData()[i];

                        if (currentLine.trim().startsWith("<tile id=\"")) {
                            if(currentLine.trim().contains("type=\"")){
                                addType(currentLine.split("type=\"")[1].split("\"")[0]);
                            }
                            else {
                                addType(null);
                            }
                            while (!currentLine.trim().startsWith("</tile>")) {
                                if (currentLine.trim().startsWith("<image")){

                                    addWidth(Float.parseFloat(currentLine.split("width=\"")[1].split("\"")[0]));
                                    addHeight(Float.parseFloat(currentLine.split("height=\"")[1].split("\"")[0]));
                                    addTexture(currentLine.split("source=\"")[1].split("\"")[0].split("../../Game Textures/Textures/")[1].split("\"")[0]);

                                }
                                if (currentLine.trim().startsWith("<property")) {

                                    if (currentLine.split("name=\"")[1].split("\"")[0].equals("Killing_B")){
                                        HashMap<String, Boolean> hashMap = new HashMap<>();
                                        if (currentLine.split("value=\"")[1].split("\"")[0].equals("true")){
                                            hashMap.put("Killing_B", true);
                                        }
                                        else if (currentLine.split("value=\"")[1].split("\"")[0].equals("false")) {
                                            hashMap.put("Killing_B", false);
                                        }
                                        if (getTileSetData()[i+1].split("value=\"")[1].split("\"")[0].equals("true")){
                                            hashMap.put("Killing_L", true);
                                        }
                                        else if (getTileSetData()[i+1].split("value=\"")[1].split("\"")[0].equals("false")) {
                                            hashMap.put("Killing_L", false);
                                        }
                                        if (getTileSetData()[i+2].split("value=\"")[1].split("\"")[0].equals("true")){
                                            hashMap.put("Killing_R", true);
                                        }
                                        else if (getTileSetData()[i+2].split("value=\"")[1].split("\"")[0].equals("false")) {
                                            hashMap.put("Killing_R", false);
                                        }
                                        if (getTileSetData()[i+3].split("value=\"")[1].split("\"")[0].equals("true")){
                                            hashMap.put("Killing_T", true);
                                        }
                                        else if (getTileSetData()[i+3].split("value=\"")[1].split("\"")[0].equals("false")) {
                                            hashMap.put("Killing_T", false);
                                        }
                                        addKilling(hashMap);
                                    }

                                    if (currentLine.split("name=\"")[1].split("\"")[0].equals("Solid")){
                                        if (currentLine.split("value=\"")[1].split("\"")[0].equals("true")){
                                            addSolid(true);
                                        }

                                        else if (currentLine.split("value=\"")[1].split("\"")[0].equals("false")) {
                                            addSolid(false);
                                        }
                                    }
                                    if (currentLine.split("name=\"")[1].split("\"")[0].equals("ID")){
                                        addID(Integer.parseInt(currentLine.split("value=\"")[1].split("\"")[0].trim()));
                                            addSolid(true);

                                    }

                                    if (currentLine.split("name=\"")[1].split("\"")[0].equals("Animated")){
                                        if (currentLine.split("value=\"")[1].split("\"")[0].equals("true")){
                                            addAnimated(true);
                                            addFps(Integer.parseInt(getTileSetData()[i+1].split("value=\"")[1].split("\"")[0].trim()));
                                        }

                                        else if (currentLine.split("value=\"")[1].split("\"")[0].equals("false")) {
                                            addAnimated(false);
                                            addFps(0);
                                        }
                                    }
                                    if (currentLine.split("name=\"")[1].split("\"")[0].equals("Triger")){
                                        if (currentLine.split("value=\"")[1].split("\"")[0].equals("true")){
                                            addTriger(true);
                                        }
                                        else if (currentLine.split("value=\"")[1].split("\"")[0].equals("false")) {
                                            addTriger(false);
                                        }
                                    }
                                }
                                if (currentLine.trim().startsWith("<object id")) {
                                    addAABB(new Vector4f(Float.parseFloat(currentLine.split("x=\"")[1].split("\"")[0]),32-Float.parseFloat(currentLine.split("y=\"")[1].split("\"")[0])-Float.parseFloat(currentLine.split("height=\"")[1].split("\"")[0]), Float.parseFloat(currentLine.split("width=\"")[1].split("\"")[0]), Float.parseFloat(currentLine.split("height=\"")[1].split("\"")[0])));
                                }
                                i++;
                                currentLine = getTileSetData()[i];
                                }
                            }
                        }

                    setTileLoaded(true);
                }



        });
        if (!loader.isAlive()) {

            loader.start();
        }
    }

}
