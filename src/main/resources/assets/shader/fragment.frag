#version 460 core

in vec4 fColor;
in vec2 oTCoords;
in float oTID;
uniform sampler2D uTex_a[8];
out vec4 color;

void main()
{
    if(oTID>0)
    {
        int id = int(oTID);
        color = fColor*texture(uTex_a[id],oTCoords);
    }
    else
    {
        color = fColor;
    }

}