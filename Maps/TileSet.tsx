<?xml version="1.0" encoding="UTF-8"?>
<tileset version="1.5" tiledversion="1.7.0" name="TileSet" tilewidth="32" tileheight="32" tilecount="5" columns="0">
 <grid orientation="orthogonal" width="1" height="1"/>
 <tile id="0">
  <properties>
   <property name="Killing" type="bool" value="false"/>
   <property name="Solid" type="bool" value="true"/>
  </properties>
  <image width="32" height="32" source="../../Game Textures/Textures/Brick.png"/>
  <objectgroup draworder="index" id="2">
   <object id="1" x="0" y="0" width="32" height="32"/>
  </objectgroup>
 </tile>
 <tile id="1">
  <properties>
   <property name="Killing" type="bool" value="false"/>
   <property name="Solid" type="bool" value="true"/>
  </properties>
  <image width="32" height="32" source="../../Game Textures/Textures/Floor.png"/>
  <objectgroup draworder="index" id="2">
   <object id="1" x="0" y="0" width="32" height="32"/>
  </objectgroup>
 </tile>
 <tile id="2">
  <properties>
   <property name="Killing" type="bool" value="false"/>
   <property name="Solid" type="bool" value="true"/>
  </properties>
  <image width="32" height="32" source="../../Game Textures/Textures/Floor_Fill.png"/>
  <objectgroup draworder="index" id="2">
   <object id="1" x="0" y="0" width="32" height="32"/>
  </objectgroup>
 </tile>
 <tile id="3">
  <properties>
   <property name="Killing" type="bool" value="false"/>
   <property name="Solid" type="bool" value="true"/>
  </properties>
  <image width="32" height="32" source="../../Game Textures/Textures/Floor_L_Corner.png"/>
  <objectgroup draworder="index" id="2">
   <object id="1" x="0" y="0" width="32" height="32"/>
  </objectgroup>
 </tile>
 <tile id="4">
  <properties>
   <property name="Killing" type="bool" value="false"/>
   <property name="Solid" type="bool" value="true"/>
  </properties>
  <image width="32" height="32" source="../../Game Textures/Textures/Floor_R_Corner.png"/>
  <objectgroup draworder="index" id="2">
   <object id="1" x="0" y="0" width="32" height="32">
    <properties>
     <property name="Solid" type="bool" value="false"/>
    </properties>
   </object>
  </objectgroup>
 </tile>
</tileset>
