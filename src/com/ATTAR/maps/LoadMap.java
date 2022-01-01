package com.ATTAR.maps;


import com.ATTAR.defaultes.FileReader;
import com.ATTAR.objects.Tiles;
import org.joml.Vector2f;
import com.ATTAR.grafic.Camera;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoadMap {

    private Thread loader;
    String[] mapData;
    List<String> TileSource = new ArrayList<>();
    private HashMap<Vector2f, Integer> Map = new HashMap<>();
    private HashMap<String, Integer> Gids = new HashMap<>();

    private String txm_File;

    private int width, height;
    private boolean isMapLoaded = false;





    public HashMap<Vector2f, Integer> getMap() {return Map;}
    public void setMap(HashMap<Vector2f, Integer> Map){this.Map = Map;}
    public void putMap(Vector2f key, int value) {Map.put(key, value);}

    public void putGrid(String key, int value) {Gids.put(key, value);}

    public HashMap<String, Integer> getGids() {return Gids;}

    public String[] getMapData() {return mapData;}

    public void setMapData(String[] mapData) {this.mapData = mapData;}

    public List getTileSource() {return TileSource;}

    public void addTileSource(String TileSource) {this.TileSource.add(TileSource);}

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getTxm_File() {
        return txm_File;
    }

    public void setTxm_File(String txm_File) {
        this.txm_File = txm_File;
    }

    public boolean isMapLoaded() {
        return isMapLoaded;
    }

    public void setMapLoaded(boolean mapLoaded) {
        isMapLoaded = mapLoaded;
    }

    public Thread getLoader() {
        return loader;
    }

    public LoadMap(String MapName) {
        setMap(new HashMap<>());
        loader = new Thread(new Runnable() {
            @Override
            public void run() {

                setTxm_File(FileReader.ReadFile("./Maps/", MapName));
                setMapData(txm_File.split("\r\n"));
                for (int i = 0; i<getMapData().length; i++) {
                    String currentLine = getMapData()[i];

                    if (currentLine.trim().startsWith("<map version=\"")){
                        setWidth(Integer.parseInt((currentLine.split("width=\"")[1].split("\"")[0])));
                        setHeight(Integer.parseInt((currentLine.split("height=\"")[1].split("\"")[0])));
                    }
                    if(currentLine.trim().startsWith("<tileset") && currentLine.contains("source=\"")) {

                        String s = currentLine.split("source=\"")[1].split("\"")[0];
                        addTileSource(s);
                        putGrid(s, Integer.parseInt(currentLine.split("firstgid=\"")[1].split("\"")[0]));
                    }

                    else if (!currentLine.trim().startsWith("<") && !currentLine.trim().startsWith("</data") ) {


                        for (int jj = getHeight()-1; jj>=0; jj-- ){

                            String[] ss = currentLine.split(",");
                            for (int j = 0; j < ss.length; j++ ) {
                                if (ss[j].contains("0") || ss[j].contains("1") || ss[j].contains("2") || ss[j].contains("3") || ss[j].contains("4") || ss[j].contains("5") || ss[j].contains("6") || ss[j].contains("7") || ss[j].contains("8") || ss[j].contains("9")) {

                                    if (Integer.parseInt(ss[j].trim()) != 0) {

                                        putMap(new Vector2f(j, jj), Integer.parseInt(ss[j].trim()));

                                    }
                                }
                            }
                            i++;
                            if (i<getMapData().length){
                                currentLine = getMapData()[i];
                            }
                        }


                    }

                }


                loader = null;
            }


        });
        loader.start();



    }

}
