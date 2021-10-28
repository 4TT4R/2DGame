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
				ftexCord = atexCord;

//				fColor = vec4 (1.0,1.0,0,1.0);
//				setting up position

				gl_Position = projection * wiev * vec4(vec3((aPos.x * uScale.x) + uPos.x, (aPos.y * uScale.y) + uPos.y, -5), 1.0);
//			    gl_Position = vec4(aPos, 1.0);

				};
#type fragment
//				inicialize glsl version
				#version 330 core

//				inicialize vec4 fragment color
				in vec4 fColor;
				in vec2 ftexCord;
                out vec4 color;
				uniform sampler2D TexSampler;
//				call main method
				void main(){
//				set color to each vector
				color = texture(TexSampler, ftexCord);
//				gl_FragColor = fColor;
				};