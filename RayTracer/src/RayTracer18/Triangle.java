package RayTracer18;

import static RayTracer18.Vector3.cross;
import static RayTracer18.Vector3.dot;

public class Triangle {


    public Vector3 p1, p2, p3;

    public Triangle(Vector3 p1, Vector3 p2, Vector3 p3){
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }





    public Vector3 intersect( Ray ray)
    {
        Vector3 ro = ray.origin;
        Vector3 rd = ray.direction;

        Vector3 v0 = this.p1.clone();
        Vector3 v1 = this.p2.clone();
        Vector3 v2 = this.p3.clone();

        Vector3 v1v0 = Vector3.sub(v1 , v0);
        Vector3 v2v0 = Vector3.sub(v2 , v0);
        Vector3 rov0 = Vector3.sub(ro , v0);
        Vector3  n = cross( v1v0, v2v0 );
        Vector3  q = cross( rov0, rd );
        double d = 1.0/dot( rd, n );
        double u = d*dot( Vector3.multiplyScalar(q,-1), v2v0 );
        double v = d*dot(  q, v1v0 );
        double t = d*dot( Vector3.multiplyScalar(n, -1), rov0 );
        if( u<0.0 || u>1.0 || v<0.0 || (u+v)>1.0 ) t = -1.0;
        return new Vector3( t, u, v );
    }
}
