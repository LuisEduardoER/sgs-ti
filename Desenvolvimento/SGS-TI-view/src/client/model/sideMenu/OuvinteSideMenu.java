package client.model.sideMenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client.view.MainView;

public class OuvinteSideMenu implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent evt) {
		MainView.getInstance().openNewInternalContent(evt.getActionCommand(),null);
	}

}
