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
		// 联接游戏控制器
		this.setGameControl(gameControl);
		this.setLayout(null);
		// 初始化组件
		this.initComponent();
		// 初始化层
		this.initLayer(dto);
		// 安装键盘监听器
		this.addKeyListener(new PlayerControl(gameControl));
	}

	/**
	 * 初始化组件
	 */
	private void initComponent() {
		// 初始化开始按钮和设置按钮
		this.btnStart = new JButton(Img.BTN_START);
		this.btnConfig = new JButton(Img.BTN_CONFIG);
		// 设置开始按钮和设置按钮位置
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
				// 返回焦点
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
	 * 初始化层
	 */
	private void initLayer(GameDto dto) {
		try {
			// 获得游戏配置
			FrameConfig fCfg = GameConfig.getFRAME_CONFIG();
			// 获得层配置
			List<LayerConfig> layersCfg = fCfg.getLayersConfig();
			// 创建游戏层数组
			layers = new ArrayList<Layer>(layersCfg.size());
			// 创建所有层对象
			for (LayerConfig layerCfg : layersCfg) {
				// 获得类对象
				Class<?> cls = Class.forName(layerCfg.getClassname());
				// 获得构造函数
				Constructor<?> ctr = cls.getConstructor(int.class, int.class, int.class, int.class);
				// 创建构造函数创建对象
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
		// 调用基类方法?????
		super.paintComponent(g);
		// 绘制游戏界面
		for (int i = 0; i < layers.size(); layers.get(i++).paint(g))
			;
	}

	/**
	 * 控制按钮是否可点击
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
