
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseInput implements MouseListener,MouseMotionListener{
	MainFrame mf;
	Rectangle dragged=null;
	Rod rTemp = new Rod();
	
	public MouseInput(MainFrame mf)
	{
		this.mf = mf;
	}
	
	float pixelsTof(int n, int maxVal) {
        
        float cr = (float) (this.mf.v_size*2/maxVal);
        return (float) (n*cr-this.mf.v_size);
        
    }


	public void mouseClicked(MouseEvent e) {
	float x = pixelsTof(e.getX(), mf.width);
    float y = pixelsTof(e.getY(), mf.height);
   // System.out.println(String.format("( %d, %d ) => ( %f, %f )",e.getX(),e.getY(),x,y*-1));
    System.out.println("clicked");
    if(x>(-1.1f) & x<(-0.9f) & (y*-1)<1.8f & (y*-1)>1.6f) {
    	if(mf.n>3) {
        	mf.n-=1;
        	mf.generateRectangle(mf.n);
    	}
    }
    if(x>(-0.8f) & x<(-0.6f) & (y*-1)<1.8f & (y*-1)>1.6f) {
    	if(mf.n<9) {
    		mf.n+=1;
        	mf.generateRectangle(mf.n);

    	}
    	
    }	
    //System.out.println("released");
}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if(this.dragged != null)
		{
			for(Rod r : mf.rods)
			{
				if(this.dragged.getCenterX() < r.getHor2().x && this.dragged.getCenterX()>r.getHor1().x)
				{
//					System.out.println(String.format("size = %d hor1= %f", r.rects.size(), r.hor1.y));

					if(r.rects.isEmpty() || this.dragged.getLength() < r.rects.get(0).getLength())
					{
						this.dragged.jumpToRod(r.ver1.x, (r.hor1.y + r.rects.size() *0.2f));
						r.addRect(dragged);
						chefIfwon();
					}
					else
					{
						System.out.println(this.dragged.getLength()+ " "+ r.rects.get(0).getLength());
						rTemp.addRect(dragged);
						this.dragged.jumpBack();
					}
						
					break;
				}				
			}
			this.dragged = null;
			
		}
		
		//this.dragged.jumpBack();
		
		
	}
	public void chefIfwon() {
		if(mf.rods.get(2).rects.size() == mf.n) {
			mf.over = true;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
		if(dragged != null)
		{
			float x = this.pixelsTof(e.getX(), mf.width);
			float y = this.pixelsTof(e.getY(), mf.height);
			this.dragged.moveRect(x, -1*y);
		}
		
		
		//System.out.println(" "+e.getX()+" "+e.getY());
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		for(Rod r : mf.rods)
		{
			if(r.rects.size() == 0)
				continue;
			
			float x = this.pixelsTof(e.getX(), mf.width);
			float y = this.pixelsTof(e.getY(), mf.height);
			if(r.rects.get(0).containsPoint(x, -1*y))
			{
				System.out.println("e pressed");
				this.dragged = r.rects.get(0);
				mf.rectD = this.dragged;
				r.removeRect();
				rTemp = r;
				this.dragged.setInitialPoint();
				this.dragged.moveRect(x, -1*y);
				break;
			}
		}
		
	}


}
