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
    private List<Boolean> Solid, Killing, animated;

    private List<Vector4f> AABB;
    private List<String> texture;
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

    public List<Boolean> getKilling() {
        return Killing;
    }

    public boolean isTileLoaded() {return TileLoaded;}

    public void setTileLoaded(boolean tileLoaded) {TileLoaded = tileLoaded;}

    public List<Float> getWidth() {
        return width;
    }

    public void addWidth(float value) {
        this.width.add(value);
    }

    public void addSolid(boolean value) {
        this.Solid.add(value);
    }

    public void addAnimated(boolean value) {
        this.animated.add(value);
    }

    public void addKilling(boolean value) {
        this.Killing.add(value);
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

    }
    public void TileSetLoader() {


        setTileSet(FileReader.ReadFile("./Maps/", "TileSet.tsx"));


    }


    public void fillAsPool() {
        for (int i = 0; i<getWidth().size(); i++){
            System.out.println(getSolid().get(i));
            AssetsPool.addTile(new Tiles(getAABB().get(i), getTexture().get(i), new Vector2f(getWidth().get(i), getHeight().get(i)), getSolid().get(i), getKilling().get(i), false));
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
                            while (!currentLine.trim().startsWith("</tile>")) {
                                if (currentLine.trim().startsWith("<image")){

                                    addWidth(Float.parseFloat(currentLine.split("width=\"")[1].split("\"")[0]));
                                    addHeight(Float.parseFloat(currentLine.split("height=\"")[1].split("\"")[0]));
                                    addTexture(currentLine.split("source=\"")[1].split("\"")[0].split("../../Game Textures/Textures/")[1].split("\"")[0]);

                                }
                                if (currentLine.trim().startsWith("<property")) {

                                    if (currentLine.split("name=\"")[1].split("\"")[0].equals("Killing")){
                                        if (currentLine.split("value=\"")[1].split("\"")[0].equals("true")){

                                            addKilling(true);

                                        }
                                        else if (currentLine.split("value=\"")[1].split("\"")[0].equals("false")) {
                                            addKilling(false);
                                        }
                                    }
                                    else if (currentLine.split("name=\"")[1].split("\"")[0].equals("Solid")){
                                        if (currentLine.split("value=\"")[1].split("\"")[0].equals("true")){
                                            addSolid(true);
                                        }

                                        else if (currentLine.split("value=\"")[1].split("\"")[0].equals("false")) {
                                            addSolid(false);
                                        }
                                    }
                                    else if (currentLine.split("name=\"")[1].split("\"")[0].equals("Animated")){
                                        if (currentLine.split("value=\"")[1].split("\"")[0].equals("true")){
                                            addAnimated(true);
                                        }

                                        else if (currentLine.split("value=\"")[1].split("\"")[0].equals("false")) {
                                            addAnimated(false);
                                        }
                                    }
                                }
                                if (currentLine.trim().startsWith("<object id")) {
                                    addAABB(new Vector4f(Float.parseFloat(currentLine.split("x=\"")[1].split("\"")[0]), Float.parseFloat(currentLine.split("y=\"")[1].split("\"")[0]), Float.parseFloat(currentLine.split("width=\"")[1].split("\"")[0]), Float.parseFloat(currentLine.split("height=\"")[1].split("\"")[0])));
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
