package me.rik;

public class LightSource {
    public int intensity;
    public Vector3 position;
    public Color color;

    public LightSource(int intensity, Vector3 position, Color color){
        this.intensity = intensity;
        this.color = color;
        this.position = position;
    }
}
