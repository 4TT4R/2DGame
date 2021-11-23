package com.ATTAR.fonts;

import com.ATTAR.grafic.Shader;
import com.mlomb.freetypejni.*;

import org.joml.*;
import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import java.nio.*;
import java.util.*;
import static com.mlomb.freetypejni.FreeType.*;
import static com.mlomb.freetypejni.FreeTypeConstants.*;
import static org.lwjgl.opengl.ARBVertexArrayObject.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;


public class Sdf {
    public int textureId = -1;
    private Map<Character, CharSdfInfo> Characters = new HashMap();
    private int VAO, VBO;
    public Sdf() {
        VAO = glGenVertexArrays();
        glBindVertexArray(VAO);
        VBO = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, VBO);
        glBufferData(GL_ARRAY_BUFFER, Float.BYTES * 6*4,  GL_DYNAMIC_DRAW);
        glVertexAttribPointer(0, 4, GL_FLOAT, false, 4 * Float.BYTES, 0);

        glEnableVertexAttribArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);

    }

    public void RenderText(Shader s, String msg, float x, float y, float scale, Vector4f color, Matrix4f projection) {


        for (int i = 0; i<msg.length(); i++) {

            char c = msg.charAt(i);

            CharSdfInfo ch = Characters.getOrDefault(c, new CharSdfInfo(0,0, new Vector2i(0),new Vector2i(0)));
            float xpos = x + ch.Bearing.x * scale;
            float ypos = y - (ch.Size.y - ch.Bearing.y) * scale;

            float w = ch.Size.x * scale;
            float h = ch.Size.y * scale;
            // update VBO for each character
            float vertices[] = new float[]{
                 xpos,     ypos + h,   0.0f, 0.0f ,
                 xpos,     ypos,       0.0f, 1.0f ,
                 xpos + w, ypos,       1.0f, 1.0f ,

                 xpos,     ypos + h,   0.0f, 0.0f ,
                 xpos + w, ypos,       1.0f, 1.0f ,
                 xpos + w, ypos + h,   1.0f, 0.0f
        };
            glBindBuffer(GL_ARRAY_BUFFER, VBO);
            glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);

            // render glyph texture over quad
            s.use();
            glActiveTexture(GL_TEXTURE0);
            glBindTexture(GL_TEXTURE_2D, ch.TextureID);
            s.uploadTexture("uFontTexture", 0);
            s.uploadVec4f("textColor",color);
            s.uploadMat4f("projection", projection);
            glBindVertexArray(VAO);
            glDrawArrays(GL_TRIANGLES, 0, 6);


            // update content of VBO memory



            glBindBuffer(GL_ARRAY_BUFFER, 0);
            // render quad
            // now advance cursors for next glyph (note that advance is number of 1/64 pixels)
            x += (ch.Advance >> 6) * scale; // bitshift by 6 to get value in pixels (2^6 = 64)

        }
        glBindVertexArray(0);
        glBindTexture(GL_TEXTURE_2D, 0);
        s.detach();
    }

    public void generateBitmap( String fontFile, int Font_Size) {




        Library library = FreeType.newLibrary();
        assert(library != null);

        Face face = library.newFace(fontFile, 0);
        FT_Set_Pixel_Sizes(face.getPointer(), 0, Font_Size);

        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        for (char c = 0; (int)c < 128; c++) {

            if (FT_Load_Char(face.getPointer(), c, FT_LOAD_RENDER)) {
                System.out.println("FreeType could not generate character.");
                continue;
            }

                int glyphHeight = face.getGlyphSlot().getBitmap().getRows();
                int glyphWidth = face.getGlyphSlot().getBitmap().getWidth();
            this.textureId = glGenTextures();
            glBindTexture(GL_TEXTURE_2D, textureId);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);

            glTexImage2D(GL_TEXTURE_2D, 0, GL_RED, glyphWidth, glyphHeight,
                    0, GL_RED, GL_UNSIGNED_BYTE, face.getGlyphSlot().getBitmap().getBuffer());



                Characters.put(c,
                        new CharSdfInfo(this.textureId,
                                face.getGlyphSlot().getAdvance().getX(),
                                new Vector2i(face.getGlyphSlot().getBitmap().getWidth(),
                                        face.getGlyphSlot().getBitmap().getRows()),
                                new Vector2i(face.getGlyphSlot().getBitmapLeft(),
                                        face.getGlyphSlot().getBitmapTop())));

        }


        free(library, face);
    }

    private void free(Library library, Face font) {
        FT_Done_Face(font.getPointer());
        FT_Done_FreeType(library.getPointer());
    }

}
