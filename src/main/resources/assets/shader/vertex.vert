#version 460 core
layout (location=0) in vec3 aPos;
layout (location=1) in vec4 aColor;
layout (location=2) in vec2 aTCoords;
layout (location=3) in float aTID;

uniform mat4 uProjection;
uniform mat4 uView;


out vec4 fColor;
out vec2 oTCoords;
out float oTID;


void main()
{
    fColor = aColor;
    oTCoords = aTCoords;
    oTID = aTID;
    gl_Position =uProjection * uView * vec4(aPos, 1.0);
}