import java.util.ArrayList;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

public class Rod {
	
	Point ver1, ver2;
	Point hor1, hor2;
	public ArrayList<Rectangle> rects = new ArrayList<Rectangle>();
	GL2 gl;
	
	public Rod() {
		// TODO Auto-generated constructor stub
	}
	
	
	public Rod(Point ver1, Point ver2, Point hor1, Point hor2, GL2 gl) {
		super();
		this.ver1 = ver1;
		this.ver2 = ver2;
		this.hor1 = hor1;
		this.hor2 = hor2;
		this.gl = gl;
	}
	
	public void addRect(Rectangle r)
	{
		for(Rectangle rect : rects)
		{
			rect.incrementPos();
		}
		rects.add(0,r);
		//r.setInitialPoint();
	}
	
	public void drawRod()
	{
		gl.glColor3f(-1.0f, 0.8f, 0.9f);
		gl.glBegin(GL.GL_LINES);
		gl.glVertex2f( this.ver1.x, this.ver1.y);
	    gl.glVertex2f( this.ver2.x, this.ver2.y);
	    gl.glVertex2f( this.hor1.x, this.hor1.y);
	    gl.glVertex2f( this.hor2.x, this.hor2.y);
		gl.glEnd();
		gl.glFlush();
		
		for(Rectangle rect : rects)
		{
			rect.drawRect();
		}
	}
	
	public void removeRect()
	{
//		
		this.rects.remove(0);
		this.rects.trimToSize();

	}
	public Point getVer1() {
		return ver1;
	}
	public void setVer1(Point ver1) {
		this.ver1 = ver1;
	}
	public Point getVer2() {
		return ver2;
	}
	public void setVer2(Point ver2) {
		this.ver2 = ver2;
	}
	public Point getHor1() {
		return hor1;
	}
	public void setHor1(Point hor1) {
		this.hor1 = hor1;
	}
	public Point getHor2() {
		return hor2;
	}
	public void setHor2(Point hor2) {
		this.hor2 = hor2;
	}
	
	

}
