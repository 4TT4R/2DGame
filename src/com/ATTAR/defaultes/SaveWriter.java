package com.ATTAR.defaultes;

import com.ATTAR.objects.Player;
import org.joml.*;
import java.io.*;

public class SaveWriter {




    public static void WriteSave(Vector2f PlayerPos, int level, Vector4f time) {
        try {
            File myObj = new File("Save.xml");
            myObj.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileWriter myWriter = new FileWriter("Save.xml");

            myWriter.write("<PlayerPos> x=\""+PlayerPos.x+"\", y=\""+ PlayerPos.y+"\" </PlayerPos>\r\n"+
                    "<Level>"+level+"</Level>\r\n"+
                    "<Time>"+"hour=\""+time.x+"\""+"min=\""+time.y+"\", sec=\""+time.z+"\", milSec=\""+time.w+"\" </Time>");
            myWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
