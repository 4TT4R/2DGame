package com.ATTAR.maps;

import com.ATTAR.defaultes.FileReader;
import com.ATTAR.grafic.Camera;
import com.ATTAR.objects.Tiles;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.List;
import java.util.Set;


public class TileSetLoad {




    private String[] TileSetData;
    private String TileSet;
    private Thread loader;
    private HashMap<Integer, Float> width, height;
    private HashMap<Integer, String> texture;
    private boolean TileLoaded = false;
    private HashMap<Vector2f, Tiles> BlockMap = new HashMap<>();
    private Set<Vector2f> BlockPos;

    public HashMap<Vector2f, Tiles> getBlockMap() {
        return BlockMap;
    }

    public void setBlockMap(HashMap<Vector2f, Tiles> blockMap) {
        BlockMap = blockMap;
    }

    public boolean isTileLoaded() {return TileLoaded;}

    public void setTileLoaded(boolean tileLoaded) {TileLoaded = tileLoaded;}

    public HashMap<Integer, Float> getWidth() {
        return width;
    }

    public void putWidth(int key, float value) {
        this.width.put(key,value);
    }

    public HashMap<Integer, Float> getHeight() {
        return height;
    }


    public void putHeight(int key, float value) {
        this.height.put(key,value);
    }

    public HashMap<Integer, String> getTexture() {
        return texture;
    }


    public void putTexture(int key, String value) {
        this.texture.put(key,value);
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

    public void TileSetLoader(String TileSetName) {


        setTileSet(FileReader.ReadFile("./Maps/", TileSetName));


    }


    public void interpretData (HashMap<String, Integer> Gids, List<String> TileSource) {
        loader = new Thread(new Runnable() {
            @Override
            public void run() {

                for (int ix = 0; ix < TileSource.size(); ix++){
                    TileSetLoader(TileSource.get(ix));
                    setTileSetData(TileSet.split("\r\n"));
                    int gid = Gids.get(TileSource.get(ix));
                    for (int i = 0; i<getTileSetData().length; i++) {
                        String currentLine = getTileSetData()[i];
                        if (currentLine.startsWith("<tile id=\"")) {
                            int id = Integer.parseInt(currentLine.split("id=\"")[1].split("\"")[0]+gid);
                            while (!currentLine.startsWith("</tile>")) {
                                if (currentLine.startsWith("<image")){
                                    putWidth(id, Float.parseFloat(currentLine.split("width=\"")[1].split("\"")[0]));
                                    putHeight(id, Float.parseFloat(currentLine.split("height=\"")[1].split("\"")[0]));
                                    putTexture(id,currentLine.split("source=\"")[1].split("\"")[0].replace("../eclipse-workspace/2DGameEngine","."));
                                }
                                i++;
                                currentLine = getTileSetData()[i];
                                }
                            }
                        }
                }
                setTileLoaded(true);
            }

        });
        if (!loader.isAlive()) {
            setTileLoaded(false);
            loader.start();
        }
    }

}
