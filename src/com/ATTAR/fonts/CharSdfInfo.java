package com.ATTAR.fonts;

import org.joml.Vector2i;

public class CharSdfInfo {




    public int TextureID, Advance;
    public Vector2i Size, Bearing;

    public CharSdfInfo(int textureID, int advance, Vector2i size, Vector2i bearing) {
        TextureID = textureID;
        Advance = advance;
        Size = size;
        Bearing = bearing;
    }
}
