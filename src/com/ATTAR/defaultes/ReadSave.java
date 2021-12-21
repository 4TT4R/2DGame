package com.ATTAR.defaultes;

import org.joml.*;
public class ReadSave {




    public static void ReadSave() {
        String save = FileReader.ReadFile("./","Save.xml");
        Vector2f PlayerPos = new Vector2f(Float.parseFloat(save.split("<PlayerPos>")[1].split("</PlayerPos>")[0].split("x=\"")[1].split("\"")[0].trim()),
                                          Float.parseFloat(save.split("<PlayerPos>")[1].split("</PlayerPos>")[0].split("y=\"")[1].replace("\"","").trim()));

        int Level = Integer.parseInt(save.split("<Level>")[1].split("</Level>")[0].trim());

        Vector4f Time = new Vector4f(Float.parseFloat(save.split("<Time>")[1].split("</Time>")[0].split("hour=\"")[1].split("\"")[0].trim()),
                                     Float.parseFloat(save.split("<Time>")[1].split("</Time>")[0].split("min=\"")[1].split("\"")[0].trim()),
                                     Float.parseFloat(save.split("<Time>")[1].split("</Time>")[0].split("sec=\"")[1].split("\"")[0].trim()),
                                     Float.parseFloat(save.split("<Time>")[1].split("</Time>")[0].split("milSec=\"")[1].split("\"")[0].trim()));
        Collector.setPlayerPos(PlayerPos);
        Collector.setLevel(Level);
        Collector.setTime(Time);
    }
}
