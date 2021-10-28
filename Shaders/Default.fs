//				inicialize glsl version
				#version 330 core

//				inicialize vec4 fragment color
				in vec4 fColor;
				in vec2 ftexCord;

				uniform sampler2D TexSampler;
//				call main method
				void main(){
//				set color to each vector
				gl_FragColor = texture(TexSampler, ftexCord);
//				gl_FragColor = fColor;
				};