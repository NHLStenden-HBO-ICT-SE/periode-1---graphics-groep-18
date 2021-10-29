# Ray Tracer 18
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
In 800x400 (wat de default is) is het ongeveer een minuut.


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
#### Project uitpakken
1. Pak de .zip uit in een folder.
2. Open het project met IntelliJ IDEA (de buitenste folder). (andere IDE mag ook, maar geen installatie instructies voor)

#### Lib (JavaFX) toevoegen
3. Klik op File > Project Structure
4. Klik op het 'Libraries' tabje.
5. Verwijder alle libraries die erin staan met het minnetje.
6. Voeg de library toe:
   1. Klik op +
   2. Selecteer Java
   3. Navigeer naar de `uitgepakte folder > RayTracer > src > javafx > javafx` en klik de `lib` folder aan.
   4. Voeg hem toe en Apply.
   
#### VM opties toevoegen
7. In IntelliJ IDEA, probeer de app te starten door rechtermuisknop op Main en die te runnen. Nu is de goede file geselecteerd.
8. Rechtsbovenin op de `Main` dropdown > `Edit Configuration`
9. Naast tab `Build and run` > `Modify options` > `Add VM options`
10. Voeg in het nieuwe VM Options veld het volgende toe:
    1. Vervang het onderstaande pad, met het absolute path van het lib bestand in de uitgepakte folder
       1. `--module-path F:\GitHub\RayTracing\RayTracer\src\javafx\javafx2\lib --add-modules=javafx.controls,javafx.fxml`

#### Runnen
11. Nu kun je de applicatie starten. Soms moet je de lib opnieuw toevoegen tot het werkt.

Java versie: [16.0.1](https://www.oracle.com/java/technologies/javase/jdk16-archive-downloads.html)

