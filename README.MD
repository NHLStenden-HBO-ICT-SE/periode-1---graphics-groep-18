#Ray Tracer 18

## 📒Note
Wij vinden dat Rik Wildschut een hoger cijfer verdient, omdat hij veel meer werk heeft verzet dan de rest van de groep.

## Objecten
- Driehoeken 
- Spheres 
- Planes 
- Box 

##Final Scene
Onze final scene bestaat uit 4 custom objecten (`.obj`) en een sphere die reflectief is.
In totaal zijn dit meer dan 11.000 objecten.
De resolutie was 1400x700.
Dit duurde met 12 threads op een Ryzen 5 2600 ongeveer 2.5 minuten.


## Basis Onderdelen (60%)
#### Camera([`Camera`](./RayTracer/src/RayTracer18/Camera.java))
#### Schermvlak([`Camera`](./RayTracer/src/RayTracer18/Camera.java))
#### Aantal primitieve objecten([`Primitives/*`](./RayTracer/src/RayTracer18/Primitives))
#### Lichtbronnen([`Lights/*`](./RayTracer/src/RayTracer18/Lights))
#### Renderer ([`Renderer`](./RayTracer/src/RayTracer18/Renderer.java))
#### Interface/Debugger ([`Customizer`](./RayTracer/src/RayTracer18/Customizer.java) & [`Main`](./RayTracer/src/RayTracer18/Main.java))




## Bonus Onderdelen (40%)
#### ⭐Driehoeken ([`Primitives/Triangle`](./RayTracer/src/RayTracer18/Primitives/Triangle.java))
Möller–Trumbore intersection algorithme. Schrijft ook de uv waardes op.
#### ⭐OBJ importer ([`Primitives/ObjLoader`](./RayTracer/src/RayTracer18/Primitives/ObjLoader.java))
Leest een `.obj` bestand en maakt driehoeken aan van alle faces.
Als er quads in het bestand zitten zet hij ze om naar 2 driehoeken.
#### ⭐Textures/UV Maps([`Triangle#getColorAt`](./RayTracer/src/RayTracer18/Primitives/Triangle.java))
Gebruikt de UV maps uit het `.obj` bestand samen met de uv waarden uit het snijpunt(`Triangle#calculateIntersection`) om de driehoek textuur te geven.

#### ⭐Colored Lights ([`Lights`](./RayTracer/src/RayTracer18/Lights))
Meerdere gekleurde lichten die mengen. Als je lichten heel dicht bij elkaar plaatst zie je wat visual bugs.
Maar iets verder weg is het prima


#### ⭐Multithreading([`Renderer`](./RayTracer/src/RayTracer18/Renderer.java))
Gebruikt alle threads die het huidige systeem heeft om de scene te renderen.
Hij verdeeld de canvas in vertical delen voor elke thread om te renderer.
Bij elke UI update(`Renderer extends AnimationTimer`) worden de berekende pixels geschreven.

#### ⭐Hierachy met customizations([`Customizer`](./RayTracer/src/RayTracer18/Customizer.java))
In de hierachy kun je waardes van objecten en lichten aanpassen.
Dit zijn dingen als kleur, positie en rotatie bijvoorbeeld.
Selecteer een object/licht in de hierachy en dan kun je de waardes aanpassen, klik dan op apply om de scene te re-renderen.
#### ⭐Object rotatie ([`ObjLoader#rotateX/Y/Z`](./RayTracer/src/RayTracer18/Primitives/ObjLoader.java))
We kunnen driehoeken draaien om de origin van dat driehoek of om de origin van een ObjLoaded model.
Wordt gebruikt in de final render om het nhl logo wat te draaien.

#####Notes
- Er zijn veel functies uitcommented die te maken hebben met normals, we hadden interpolated normals maar die zijn last minute weggehaald
- Alle objecten, als ze draaien, draaien ze om de origin van het object heen. Vaak is die origin niet in het object dus draait het om bijvoobeeld 0,0,0 heen.

###Setup
Hoe run je dit