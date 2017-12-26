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
 * ������Ҽ����¼� ���ƻ��� ������Ϸ�߼�
 * 
 * @author ���˳�
 *
 */
public class GameControl {

	/**
	 * ���ݷ��ʽӿ�
	 */
	private Data dataA;
	private Data dataB;

	/**
	 * ��Ϸ�߼���
	 */
	private GameService gameService;

	/**
	 * ��Ϸ�����
	 */
	private JPanelGame panelGame;

	private JFrameConfig frameConfig;
	
	private JFrameSavePoint frameSavePoint=null;
	
	/**
	 * ��Ϸ��Ϊ����
	 */
	private Map<Integer, Method> actionList;

	private GameDto dto = null;

	/**
	 * ��Ϸ�����߳�
	 */
	private Thread gameThread = null;

	public GameControl() {
		// ������Ϸ����Դ
		this.dto = new GameDto();
		// ������Ϸ�߼���(������Ϸ����Դ)
		this.gameService = new GameTetris(this.dto);
		// �������캯����������
		this.dataA = this.createDtaObject(GameConfig.getDATA_CONFIG().getDataA());
		this.dataB = this.createDtaObject(GameConfig.getDATA_CONFIG().getDataB());
		this.dto.setDbRecode(dataA.loadData());
		this.dto.setDiskRecode(dataB.loadData());
		// ������Ϸ���
		this.panelGame = new JPanelGame(this, this.dto);
		// ��ȡ�û���������
		this.setControlConfig();
		this.frameConfig = new JFrameConfig(this);
		//�����������
		this.frameSavePoint=new JFrameSavePoint(this);
		// ������Ϸ���ڣ���װ��Ϸ���
		@SuppressWarnings("unused")
		JFrame frame = new JFrameGame(this.panelGame);
	}

	/**
	 * ��ȡ�û���������
	 */
	private void setControlConfig() {
		// �����������뷽������ӳ������
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
	 * �������ݶ���
	 * 
	 * @param cfg
	 * @return
	 */
	private Data createDtaObject(DataInterfaceConfig cfg) {

		try {
			// ��������
			Class<?> cls = Class.forName(cfg.getClassName());
			// ��ù��캯��
			Constructor<?> ctr = cls.getConstructor(HashMap.class);
			// ��������
			return (Data) ctr.newInstance(cfg.getParam());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * ������ҿ��ƾ�����Ϊ����
	 * 
	 * @param keyCode
	 */
	public void actionByKeyCode(int keyCode) {

		try {
			if (this.actionList.containsKey(keyCode))
				// ���÷���
				this.actionList.get(keyCode).invoke(this.gameService);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.panelGame.repaint();
	}

	/**
	 * ��ʾ��ҿ��ƴ���
	 */
	public void showUserConfig() {
		this.frameConfig.setVisible(true);
	}

	/**
	 * �Ӵ��ڹر��¼�
	 */
	public void setOver() {
		this.setControlConfig();
		this.panelGame.repaint();

	}

	/**
	 * ��ʼ�����¼�
	 */
	public void start() {
		// ��尴ť����Ϊ���ɵ��
		this.panelGame.buttonSwitch(false);
		this.frameConfig.setVisible(false);
		this.frameSavePoint.setVisible(false);
		// ��Ϸ���ݳ�ʼ��
		this.gameService.startGame();
		this.panelGame.repaint();
		// ���������߳�
		this.gameThread = new MainThread();
		gameThread.start();
		this.panelGame.repaint();
	}

	/**
	 * ��Ϸ��������
	 */
	public void afterLose() {
		//��ʾ����ķִ���
		this.frameSavePoint.show(this.dto.getNowPoint());
		//�ָ���ť���
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
					// ��Ϸ����Ϊ
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
	 * �������
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
