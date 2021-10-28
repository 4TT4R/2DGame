#type vertex
#version 330 core
layout (location = 0) in vec4 vertex; // <vec2 pos, vec2 tex>
out vec2 TexCoords;

uniform mat4 projection;

void main()
{
    gl_Position = projection * vec4(vertex.xy, -5, 1.0);
    TexCoords = vertex.zw;
}

#type fragment
#version 330 core

in vec2 TexCoords;
vec2 txCoords;
out vec4 color;

uniform sampler2D uFontTexture;
uniform vec4 textColor;

void main()
{
    float c = texture(uFontTexture, TexCoords).r;

            //color = vec4(0,TexCoords.x, TexCoords.y,  1);

            color = vec4(1, 1, 1, c)*textColor;






}