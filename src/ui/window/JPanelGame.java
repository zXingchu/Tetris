package ui.window;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import config.FrameConfig;
import config.GameConfig;
import config.LayerConfig;
import control.GameControl;
import control.PlayerControl;
import dto.GameDto;
import ui.Img;
import ui.Layer;

@SuppressWarnings("serial")
public class JPanelGame extends JPanel {

	private static final int BTN_SIZE_W = GameConfig.getFRAME_CONFIG().getButtonConfig().getButtonW();

	private static final int BTN_SIZE_H = GameConfig.getFRAME_CONFIG().getButtonConfig().getButtonH();

	private ArrayList<Layer> layers = null;

	private JButton btnStart;

	private JButton btnConfig;

	private GameControl gameControl = null;

	public JPanelGame(GameControl gameControl, GameDto dto) {
		// ������Ϸ������
		this.setGameControl(gameControl);
		this.setLayout(null);
		// ��ʼ�����
		this.initComponent();
		// ��ʼ����
		this.initLayer(dto);
		// ��װ���̼�����
		this.addKeyListener(new PlayerControl(gameControl));
	}

	/**
	 * ��ʼ�����
	 */
	private void initComponent() {
		// ��ʼ����ʼ��ť�����ð�ť
		this.btnStart = new JButton(Img.BTN_START);
		this.btnConfig = new JButton(Img.BTN_CONFIG);
		// ���ÿ�ʼ��ť�����ð�ťλ��
		this.btnStart.setBounds(GameConfig.getFRAME_CONFIG().getButtonConfig().getStartX(),
				GameConfig.getFRAME_CONFIG().getButtonConfig().getStartY(), BTN_SIZE_W, BTN_SIZE_H);
		this.btnConfig.setBounds(GameConfig.getFRAME_CONFIG().getButtonConfig().getUserConfigX(),
				GameConfig.getFRAME_CONFIG().getButtonConfig().getUserConfigY(), BTN_SIZE_W, BTN_SIZE_H);
		this.add(btnStart);
		this.add(btnConfig);
		this.btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameControl.start();
				// ���ؽ���
				requestFocus();
			}
		});
		this.btnConfig.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				gameControl.showUserConfig();
			}
		});
	}

	/**
	 * ��ʼ����
	 */
	private void initLayer(GameDto dto) {
		try {
			// �����Ϸ����
			FrameConfig fCfg = GameConfig.getFRAME_CONFIG();
			// ��ò�����
			List<LayerConfig> layersCfg = fCfg.getLayersConfig();
			// ������Ϸ������
			layers = new ArrayList<Layer>(layersCfg.size());
			// �������в����
			for (LayerConfig layerCfg : layersCfg) {
				// ��������
				Class<?> cls = Class.forName(layerCfg.getClassname());
				// ��ù��캯��
				Constructor<?> ctr = cls.getConstructor(int.class, int.class, int.class, int.class);
				// �������캯����������
				Layer layer = (Layer) ctr.newInstance(layerCfg.getX(), layerCfg.getY(), layerCfg.getW(),
						layerCfg.getH());
				layer.setDto(dto);
				layers.add(layer);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		// ���û��෽��?????
		super.paintComponent(g);
		// ������Ϸ����
		for (int i = 0; i < layers.size(); layers.get(i++).paint(g))
			;
	}

	/**
	 * ���ư�ť�Ƿ�ɵ��
	 */
	public void buttonSwitch(boolean onOff) {
		this.btnConfig.setEnabled(onOff);
		this.btnStart.setEnabled(onOff);
	}

	/**
	 * 
	 */
	public void setGameControl(PlayerControl control) {
		this.addKeyListener(control);
	}

	public void setGameControl(GameControl gameControl) {
		this.gameControl = gameControl;
	}

}
