#Ray Tracer 18

##Objecten
- Driehoeken 
- Spheres 
- Planes 
- Box 


##

##Basis Onderdelen (60%)
####Camera(`Camera`)
####Schermvlak(`Camera`)
####Aantal primitieve objecten(`Primitives/*`)
####Lichtbronnen(`Lights/*`)
####Renderer (`Renderer`)
####Interface/Debugger (`Customizer` & `Main`)




##Bonus Onderdelen (40%)
####⭐Driehoeken (`Primitives/Triangle`)
Möller–Trumbore intersection algorithme. Schrijft ook de uv waardes op.
####⭐OBJ importer (`Primitives/ObjLoader`)
Leest een `.obj` bestand en maakt driehoeken aan van alle faces.
Als er quads in het bestand zitten zet hij ze om naar 2 driehoeken.
####⭐Smooth Shading/Normal interpolation (`Primives/Triangle#getNormalAt`)
Gebruikt de vertex normals uit `.obj` file bij het opvragen van normal gebruikt
hij de baycentric waarden(uv coördinaten) van het snijpunt om de normaal te berekenen.
<img src="./interpolatednormals.png" width="180" height="180">
####⭐Textures/UV Maps(`Triangle#getColorAt`)
Gebruikt de UV maps uit het `.obj` bestand samen met de uv waarden uit het snijpunt(`Triangle#calculateIntersection`) om de driehoek textuur te geven.


####⭐Multithreading(`Renderer`)
Gebruikt alle threads die het huidige systeem heeft om de scene te renderen.
Hij verdeeld de canvas in vertical delen voor elke thread om te renderer.
Bij elke UI update(`Renderer extends AnimationTimer`) worden de berekende pixels geschreven.

####⭐Hierachy met customizations(`Customizer`)
In de hierachy kun je waardes van objecten en lichten aanpassen.
Dit zijn dingen als kleur, positie en rotatie bijvoorbeeld.
