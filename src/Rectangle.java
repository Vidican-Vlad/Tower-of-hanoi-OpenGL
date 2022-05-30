import com.jogamp.opengl.GL2;

public class Rectangle {
	Point leftL, rightL, rightU, leftU,Anchor = null;
	int pos;
	float r,g,b;
	GL2 gl;
	Point initP;
	
	public Rectangle(Point leftL, Point rightL, Point rightU, Point leftU, int pos, float r, float g, float b, GL2 gl) {
		super();
		this.leftL = leftL;
		this.rightL = rightL;
		this.rightU = rightU;
		this.leftU = leftU;
		this.pos = pos;
		this.r = r;
		this.g = g;
		this.b = b;
		this.gl = gl;
	}
	public float getLength()
	{
		return Math.abs(rightU.x - leftU.x);
	}
	
	public float getHeight() {
		System.out.println("height is" + Math.abs(rightU.y - leftL.y));
		
		return Math.abs(rightU.y - leftL.y);
	}
	public boolean containsPoint(float x, float y)
	{
		System.out.println(String.format("(%f,%f) - (%f,%f) - (%f,%f)", leftU.x, leftU.y, x, y, rightL.x, rightL.y));
		return (x > leftU.x && x < rightL.x && y > rightL.y && y < leftU.y);
	}
	public void incrementPos()
	{
		this.pos+=1;
	}
	public void decrementPos()
	{
		this.pos-=1;
	}
	public Point getLeftL() {
		return leftL;
	}
	public void moveRect(float x, float y)
	{
		if(this.Anchor == null)
			Anchor = new Point(x, y);
		else
		{
			float dx =  -1*(Anchor.x - x);
			float dy = -1*(Anchor.y - y);
			this.leftL.updatePosition(leftL.x+dx, leftL.y+dy);
			this.rightL.updatePosition(rightL.x+dx, rightL.y+dy);
			this.rightU.updatePosition(rightU.x+dx, rightU.y+dy);
			this.leftU.updatePosition(leftU.x+dx, leftU.y+dy);
			Anchor.updatePosition(x,y);
			this.drawRect();
		}
			
	}
	
	public void drawRect()
	{
		gl.glBegin(GL2.GL_QUADS);
		gl.glColor3f(r,g,b);
		// Lower left corner.
		//(x,y)
		gl.glVertex2f(leftL.x,leftL.y);

		// Lower right corner.
		gl.glVertex2f(rightL.x, rightL.y);

		// Upper right corner.
		gl.glVertex2f(rightU.x,rightU.y);

		// Upper left corner.
		gl.glVertex2f(leftU.x, leftU.y);
		gl.glEnd();
		gl.glFlush();
	}
	
	public void setInitialPoint() {
		this.initP = new Point(this.leftL.x, this.leftL.y);
	}
	
	public void jumpBack() {
		float length = this.getLength();
		float height = 0.2f;
		this.leftL.updatePosition(initP.x, initP.y);
		this.rightL.updatePosition(initP.x + length, initP.y);
		this.rightU.updatePosition(initP.x + length, initP.y + height);
		this.leftU.updatePosition(initP.x, initP.y + height);
		//System.out.println(String.format("(%f,%f) - (%f,%f)", this.leftL.x, this.leftL.y, this.rightU.x, this.rightU.y));
		this.Anchor = null;
	}
	
	public void jumpToRod(float x, float y)
	{
		float halfLength = this.getLength()/2;
		this.leftL.updatePosition(x-halfLength, y);
		this.rightL.updatePosition(x+halfLength, y);
		this.rightU.updatePosition(x+halfLength, y+0.2f);
		this.leftU.updatePosition(x-halfLength, y+0.2f);
		this.Anchor = null;
		this.setInitialPoint();
		
	}
	public float getCenterX()
	{
		return (float)((leftL.x +rightL.x)/2);
	}


	public void setLeftL(Point leftL) {
		this.leftL = leftL;
	}


	public Point getRightL() {
		return rightL;
	}


	public void setRightL(Point rightL) {
		this.rightL = rightL;
	}


	public Point getRightU() {
		return rightU;
	}


	public void setRightU(Point rightU) {
		this.rightU = rightU;
	}


	public Point getLeftU() {
		return leftU;
	}


	public void setLeftU(Point leftU) {
		this.leftU = leftU;
	}


	public float getR() {
		return r;
	}




	public void setR(float r) {
		this.r = r;
	}




	public float getG() {
		return g;
	}




	public void setG(float g) {
		this.g = g;
	}




	public float getB() {
		return b;
	}




	public void setB(float b) {
		this.b = b;
	}



	public int getPos() {
		return pos;
	}


	public void setPos(int pos) {
		this.pos = pos;
	}
	
	
	

}
