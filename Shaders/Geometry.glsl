#type vertex
//				initialize glsl version
				#version 330 core

//				initialize pos a color vectors
				layout (location=0) in vec3 aPos;
				layout (location=1) in vec4 aColor;
				layout (location=2) in vec2 atexCord;


//				initialize out vec color
				uniform mat4 projection;
				uniform mat4 wiev;
				uniform vec4 uColor;
				uniform vec3 uScale;
				uniform vec2 uPos;


				out vec4 fColor;
				out vec2 ftexCord;

//				initialize uniform matricies variable for projection and wiev

//				call main method
				void main() {

//				setting fragment color to a color
				fColor = uColor;



//				setting up position

				gl_Position = projection *  vec4(vec3((aPos.x * uScale.x) + uPos.x, (aPos.y * uScale.y) + uPos.y, -100), 1.0);


				};
#type fragment
//				inicialize glsl version
				#version 330 core

//				inicialize vec4 fragment color
				in vec4 fColor;
				in vec2 ftexCord;

				uniform sampler2D TexSampler;
//				call main method
				void main(){
//				set color to each vector

				gl_FragColor = fColor;
				};