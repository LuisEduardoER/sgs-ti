package client.model.sideMenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import common.util.Utils;

import client.view.MainView;

public class OuvinteSideMenu implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent evt) {
		Utils.printMsg(this.getClass().getName(), "Acao executada - " + evt.getActionCommand());
		MainView.getInstance().openNewInternalContent(evt.getActionCommand(),null);
	}

}
