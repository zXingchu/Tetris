package control;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.swing.JFrame;
import config.DataInterfaceConfig;
import config.GameConfig;
import dao.Data;
import dto.GameDto;
import dto.Player;
import service.GameService;
import service.GameTetris;
import ui.window.JFrameConfig;
import ui.window.JFrameGame;
import ui.window.JFrameSavePoint;
import ui.window.JPanelGame;

/**
 * 接受玩家键盘事件 控制画面 控制游戏逻辑
 * 
 * @author 张兴楚
 *
 */
public class GameControl {

	/**
	 * 数据访问接口
	 */
	private Data dataA;
	private Data dataB;

	/**
	 * 游戏逻辑层
	 */
	private GameService gameService;

	/**
	 * 游戏界面层
	 */
	private JPanelGame panelGame;

	private JFrameConfig frameConfig;
	
	private JFrameSavePoint frameSavePoint=null;
	
	/**
	 * 游戏行为控制
	 */
	private Map<Integer, Method> actionList;

	private GameDto dto = null;

	/**
	 * 游戏下落线程
	 */
	private Thread gameThread = null;

	public GameControl() {
		// 创建游戏数据源
		this.dto = new GameDto();
		// 创建游戏逻辑块(连接游戏数据源)
		this.gameService = new GameTetris(this.dto);
		// 创建构造函数创建对象
		this.dataA = this.createDtaObject(GameConfig.getDATA_CONFIG().getDataA());
		this.dataB = this.createDtaObject(GameConfig.getDATA_CONFIG().getDataB());
		this.dto.setDbRecode(dataA.loadData());
		this.dto.setDiskRecode(dataB.loadData());
		// 创建游戏面板
		this.panelGame = new JPanelGame(this, this.dto);
		// 读取用户控制设置
		this.setControlConfig();
		this.frameConfig = new JFrameConfig(this);
		//保存分数窗口
		this.frameSavePoint=new JFrameSavePoint(this);
		// 创建游戏窗口，安装游戏面板
		@SuppressWarnings("unused")
		JFrame frame = new JFrameGame(this.panelGame);
	}

	/**
	 * 读取用户控制设置
	 */
	private void setControlConfig() {
		// 创建键盘码与方法名的映射数组
		this.actionList = new HashMap<Integer, Method>();
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(new FileInputStream("data/cotrol.dat"));
			@SuppressWarnings("unchecked")
			HashMap<Integer, String> cfgSet = (HashMap<Integer, String>) ois.readObject();
			Set<Entry<Integer, String>> entrySet = cfgSet.entrySet();
			for (Entry<Integer, String> entry : entrySet) {
				actionList.put(entry.getKey(), this.gameService.getClass().getMethod(entry.getValue()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 创建数据对象
	 * 
	 * @param cfg
	 * @return
	 */
	private Data createDtaObject(DataInterfaceConfig cfg) {

		try {
			// 获得类对象
			Class<?> cls = Class.forName(cfg.getClassName());
			// 获得构造函数
			Constructor<?> ctr = cls.getConstructor(HashMap.class);
			// 创建对象
			return (Data) ctr.newInstance(cfg.getParam());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 根据玩家控制决定行为调用
	 * 
	 * @param keyCode
	 */
	public void actionByKeyCode(int keyCode) {

		try {
			if (this.actionList.containsKey(keyCode))
				// 调用方法
				this.actionList.get(keyCode).invoke(this.gameService);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.panelGame.repaint();
	}

	/**
	 * 显示玩家控制窗口
	 */
	public void showUserConfig() {
		this.frameConfig.setVisible(true);
	}

	/**
	 * 子窗口关闭事件
	 */
	public void setOver() {
		this.setControlConfig();
		this.panelGame.repaint();

	}

	/**
	 * 开始按键事件
	 */
	public void start() {
		// 面板按钮设置为不可点击
		this.panelGame.buttonSwitch(false);
		this.frameConfig.setVisible(false);
		this.frameSavePoint.setVisible(false);
		// 游戏数据初始化
		this.gameService.startGame();
		this.panelGame.repaint();
		// 创建下落线程
		this.gameThread = new MainThread();
		gameThread.start();
		this.panelGame.repaint();
	}

	/**
	 * 游戏结束处理
	 */
	public void afterLose() {
		//显示保存的分窗口
		this.frameSavePoint.show(this.dto.getNowPoint());
		//恢复按钮点击
		this.panelGame.buttonSwitch(true);
		
	}

	private class MainThread extends Thread {
		@Override
		public void run() {
			while (dto.isStart()) {
				try {
					Thread.sleep(dto.getSleepTime());
					if (dto.isPause()) {
						continue;
					}
					// 游戏主行为
					gameService.mainAction();
					panelGame.repaint();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			afterLose();
		}
	}

	/**
	 * 保存分数
	 * @param name
	 */
	public void savePoint(String name) {
		Player pla=new Player(name, this.dto.getNowPoint());
		this.dataA.saveData(pla);
		this.dataB.saveData(pla);
		
		this.dto.setDbRecode(dataA.loadData());
		this.dto.setDiskRecode(dataB.loadData());
		this.panelGame.repaint();
	}

}
