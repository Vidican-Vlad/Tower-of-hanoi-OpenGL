
import java.util.ArrayList;
import java.util.Random;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.gl2.GLUT;

import jogamp.nativewindow.windows.RECT;

public class MainFrame
		extends JFrame
		implements GLEventListener,
		KeyListener
{

	private GLCanvas canvas;
	private Animator animator;

	// For specifying the positions of the clipping planes (increase/decrease the distance) modify this variable.
 	// It is used by the glOrtho method.
	public double v_size = 2.0;
	
	public float lenght= 0.05f;
	public ArrayList<Rectangle> rects = new ArrayList<Rectangle>();
	public Rectangle rectD = null;
	private GLUT glut;
	public boolean over = false;
	
	ArrayList<Rod> rods = new ArrayList<Rod>();
	public int n=3;
	public int width= 1200;
	public int height =600;
	private MouseInput mouseL = new MouseInput(this);

	// Application main entry point
	public static void main(String args[]) 
	{
		new MainFrame();
	}

	// Default constructor
	public MainFrame()
	{
		super("Java OpenGL");
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		
		this.setSize(width, height);
		
		this.initializeJogl();
		
		this.setVisible(true);
	}
	
	private void initializeJogl()
	{
		// Creating a new GL profile.
		GLProfile glprofile = GLProfile.getDefault();
		// Creating an object to manipulate OpenGL parameters.
		GLCapabilities capabilities = new GLCapabilities(glprofile);
		
		// Setting some OpenGL parameters.
		capabilities.setHardwareAccelerated(true);
		capabilities.setDoubleBuffered(true);

		// Try to enable 2x anti aliasing. It should be supported on most hardware.
	        capabilities.setNumSamples(2);
        	capabilities.setSampleBuffers(true);
		
		// Creating an OpenGL display widget -- canvas.
		this.canvas = new GLCanvas(capabilities);
		
		// Adding the canvas in the center of the frame.
		this.getContentPane().add(this.canvas);
		
		// Adding an OpenGL event listener to the canvas.
		this.canvas.addGLEventListener(this);
		this.canvas.addKeyListener(this);
		this.canvas.addMouseListener(mouseL);
		this.canvas.addMouseMotionListener(mouseL);
		
		
		// Creating an animator that will redraw the scene 40 times per second.
		this.animator = new Animator(this.canvas);
			
		// Starting the animator.
		this.animator.start();
	}
	
	public void init(GLAutoDrawable canvas)
	{
		// Obtaining the GL instance associated with the canvas.
		GL2 gl = canvas.getGL().getGL2();
		
		// Setting the clear color -- the color which will be used to erase the canvas.
		gl.glClearColor(0, 0, 0, 0);
		
		// Selecting the modelview matrix.
		gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
		
		glut = new GLUT();
		
		setRods();
		
		generateRectangle(n);
		
		

	}
	
	public void display(GLAutoDrawable canvas)
	{
		GL2 gl = canvas.getGL().getGL2();
		gl.glClearColor(0, 0, 0, 0);
		
		// Erasing the canvas -- filling it with the clear color.
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);

		// Add your scene code here
		
		gl.glLineWidth(2f);
		drawRods();
		drawDraggedRects();
//		drawRectangle();
		// Specify the raster position.
		gl.glColor3f(1.0f,1.0f,1.0f);
		gl.glRasterPos2d(-1.8, 1.7);
		// Render the text in the scene.
		glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, "Choose disk number  "+n);
		
		gl.glBegin(GL.GL_TRIANGLES);
		
			gl.glVertex2f(-1.1f, 1.8f); // v1
			gl.glVertex2f(-0.9f, 1.8f); // v2
			gl.glVertex2f(-1.0f, 1.6f); // v3
			
			
			gl.glVertex2f(-0.8f, 1.6f); // v1
			gl.glVertex2f(-0.6f, 1.6f); // v2
			gl.glVertex2f(-0.7f, 1.8f); // v3
		gl.glEnd();
		
		gl.glBegin(GL.GL_LINES);
		
			gl.glVertex2f(-1.1f, 1.8f);
			gl.glVertex2f(-1.1f, 1.6f);
			gl.glVertex2f(-0.9f, 1.6f);
			gl.glVertex2f(-0.9f, 1.8f);
			gl.glVertex2f(-1.1f, 1.6f);
			gl.glVertex2f(-0.9f, 1.6f);
			
			gl.glVertex2f(-0.8f, 1.6f);
			gl.glVertex2f(-0.8f, 1.8f);
			gl.glVertex2f(-0.6f, 1.6f);
			gl.glVertex2f(-0.6f, 1.8f);
			gl.glVertex2f(-0.8f, 1.8f);
			gl.glVertex2f(-0.6f, 1.8f);
		gl.glEnd();
	
		drawWinText();
		
		// Forcing the scene to be rendered.
		gl.glFlush();
	}
	
	public void generateRectangle(int n) {
		float width=0.2f;
		if(rects.size()!=0) {
			rects.clear();
			}
		rects.trimToSize();
		for(int i=0;i<n;i++) {;
			Random ran = new Random();
			float r = ran.nextFloat();
			float g = ran.nextFloat();
			float b = ran.nextFloat();
			Point leftL = new Point(-1.8f+lenght*i, -1.4f+width*i);
			Point rightL = new Point(-0.9f-lenght*i, -1.4f+width*i);
			Point rightU= new Point(-0.9f-lenght*i, -1.2f+width*i);
			Point leftU = new Point(-1.8f+lenght*i, -1.2f+width*i);
			
			rects.add(new Rectangle(leftL,rightL,rightU,leftU,0,r,g,b, this.canvas.getGL().getGL2()));
		}
		Rod left = rods.get(0);
		if(left.rects.size()!=0) {
			left.rects.clear();
		}
		for(Rectangle rect : rects)
		{
			left.addRect(rect);
		}
		rects.trimToSize();
	}
	
	public void drawDraggedRects() {
		if(rectD != null) {
			rectD.drawRect();
		}
	}
	
	public void drawWinText() {
		if(this.over == true) {
			GL2 gl = canvas.getGL().getGL2();
			gl.glColor3f(1.0f,1.0f,1.0f);
			gl.glRasterPos2d(-0.1f, -0.1f);
			// Render the text in the scene.
			glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, "YOU WON");
		}
	}
		
	public void drawRectangle() {
		GL2 gl = canvas.getGL().getGL2();
		for(int i=0;i<rects.size();i++) {
			
			gl.glBegin(GL2.GL_QUADS);
				gl.glColor3f(rects.get(i).getR(),rects.get(i).getG(),rects.get(i).getB());
				// Lower left corner.
				//(x,y)
				gl.glVertex2f(rects.get(i).getLeftL().getX(),rects.get(i).getLeftL().getY());

				// Lower right corner.
				gl.glVertex2f(rects.get(i).getRightL().getX(),rects.get(i).getRightL().getY());

				// Upper right corner.
				gl.glVertex2f(rects.get(i).getRightU().getX(),rects.get(i).getRightU().getY());

				// Upper left corner.
				gl.glVertex2f(rects.get(i).getLeftU().getX(),rects.get(i).getLeftU().getY());
			gl.glEnd();
		}
				
	}
	
	public void reshape(GLAutoDrawable canvas, int left, int top, int width, int height)
	{
		GL2 gl = canvas.getGL().getGL2();
		
		this.width = width;
		this.height = height;
		
		// Selecting the viewport -- the display area -- to be the entire widget.
		gl.glViewport(0, 0, width, height);
		
		// Determining the width to height ratio of the widget.
		//double ratio = (double) width / (double) height;
		
		// Selecting the projection matrix.
		gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
		
		gl.glLoadIdentity();
		
		// Selecting the view volume to be x from 0 to 1, y from 0 to 1, z from -1 to 1.
		// But we are careful to keep the aspect ratio and enlarging the width or the height.

//		if (ratio < 1)
//			gl.glOrtho(-v_size, v_size, -v_size, v_size / ratio, -1, 1);
//		else
//			gl.glOrtho(-v_size, v_size * ratio, -v_size, v_size, -1, 1);
		gl.glOrtho(-v_size, v_size, -v_size, v_size, -1, 1);

		// Selecting the modelview matrix.
		gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);		
	}
	
	public void displayChanged(GLAutoDrawable canvas, boolean modeChanged, boolean deviceChanged)
	{
		return;
	}

	@Override
	public void dispose(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub
	}
	
	public void setRods() {
		rods.add(new Rod(new Point(-1.35f, -1.4f), new Point(-1.35f, 1.3f), new Point(-1.9f,-1.4f),new Point(-0.8f,-1.4f), this.canvas.getGL().getGL2()));
		rods.add(new Rod(new Point(-0.05f, -1.4f), new Point(-0.05f, 1.3f), new Point(-0.6f, -1.4f),new Point(0.5f, -1.4f),  this.canvas.getGL().getGL2()));
		rods.add(new Rod(new Point(1.25f, -1.4f), new Point(1.25f, 1.3f), new Point(0.7f, -1.4f),new Point(1.8f, -1.4f),  this.canvas.getGL().getGL2()));
		
	}
	
	public void drawRods() {
		for(Rod r : this.rods)
			r.drawRod();
	}

//
//	@Override
//	public void mouseDragged(MouseEvent e) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void mouseMoved(MouseEvent e) {
//		// TODO Auto-generated method stub
//		
//	}

//	@Override


//	@Override
//	public void mousePressed(MouseEvent e) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void mouseReleased(MouseEvent e) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void mouseEntered(MouseEvent e) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void mouseExited(MouseEvent e) {
//		// TODO Auto-generated method stub
//		
//	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}