package client.util;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

public class ViewUtils {
	public static Point getScreenCenter(Component panel){
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension panelSize = panel.getSize();
		
		int x = (screenSize.width - panelSize.width)/2; 
		int y = (screenSize.height - panelSize.height)/2;
		
		return new Point(x,y);
	}
}
