package ui.window;

import javax.swing.JFrame;

import config.FrameConfig;
import config.GameConfig;
import util.FrameUtil;

@SuppressWarnings("serial")
public class JFrameGame extends JFrame {

	public JFrameGame(JPanelGame panelgame) {
		// �����Ϸ����
		FrameConfig fCfg = GameConfig.getFRAME_CONFIG();
		// ���ñ���
		this.setTitle(fCfg.getTitle());
		// ���Ĭ�Ϲر�����
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// ���ô��ڴ�С
		this.setSize(fCfg.getWidth(), fCfg.getHeight());
		// �������û��ı䴰�ڴ�С
		this.setResizable(false);
		// ����
		FrameUtil.setFrameCenter(this);
		// ����Ĭ��Panel
		this.setContentPane(panelgame);
		
		this.setVisible(true);
	}
}
