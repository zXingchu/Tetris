package ui.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import control.GameControl;
import ui.Img;
import util.FrameUtil;

@SuppressWarnings("serial")
public class JFrameConfig extends JFrame {

	private JButton btnOk = new JButton("ȷ��");

	private JButton btnCancel = new JButton("ȡ��");

	private JButton btnUser = new JButton("Ӧ��");

	private TextCtrl[] keyTexts = new TextCtrl[8];

	private JLabel errorMsg = new JLabel();

	@SuppressWarnings("rawtypes")
	private JList skinList = null;

	private JPanel skinView = null;

	@SuppressWarnings("rawtypes")
	private DefaultListModel skinData = new DefaultListModel();

	private GameControl gameControl;

	private Image[] skinViewList;

	private final static Image IMG_PSP = new ImageIcon("data/psp.jpg").getImage();

	private final static String[] METHOD_NAMES = { "keyRight", "keyUp", "keyLeft", "keyDown", "keyFunLeft", "keyFunUp",
			"keyFunRight", "keyFunDown", };

	private final static String PATH = "data/cotrol.dat";

	public JFrameConfig(GameControl gameControl) {
		// �����Ϸ����������
		this.gameControl = gameControl;
		// ���ò��ֹ�����Ϊ���߽粼�֡�
		this.setLayout(new BorderLayout());
		// ��ʼ�����������
		this.initKeyText();
		// ��������
		this.add(this.createMainPanel(), BorderLayout.CENTER);
		// ��Ӱ�ť���
		this.add(this.createButtonPanel(), BorderLayout.SOUTH);
		//

		this.setResizable(false);
		// ���ô��ڴ�С
		this.setSize(640, 350);
		FrameUtil.setFrameCenter(this);
		// TODO test
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	/**
	 * ��ʼ�����������
	 */
	private void initKeyText() {
		int x = 28;
		int y = 52;
		int w = 64;
		int h = 20;
		for (int i = 0; i < 4; i++) {
			keyTexts[i] = new TextCtrl(x, y, w, h, METHOD_NAMES[i]);
			y += 28;
		}
		x = 560;
		y = 55;
		for (int i = 4; i < keyTexts.length; i++) {
			keyTexts[i] = new TextCtrl(x, y, w, h, METHOD_NAMES[i]);
			y += 28;
		}
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PATH));
			@SuppressWarnings("unchecked")
			HashMap<Integer, String> cfgSet = (HashMap<Integer, String>) ois.readObject();
			Set<Entry<Integer, String>> entrySet = cfgSet.entrySet();
			for (Entry<Integer, String> entry : entrySet) {
				for (TextCtrl ct : keyTexts) {
					if (ct.getMethodName().equals(entry.getValue()))
						ct.setKeyCode(entry.getKey());
				}
			}
			ois.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ������ť���
	 */
	private JPanel createButtonPanel() {
		// ������ť��壬��ʽ����
		JPanel jp = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		this.errorMsg.setForeground(Color.RED);
		jp.add(this.errorMsg);
		this.btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (writeConfig())
					setVisible(false);
				gameControl.setOver();
			}
		});
		jp.add(this.btnOk);
		this.btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				gameControl.setOver();
			}
		});
		jp.add(this.btnCancel);
		this.btnUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				writeConfig();
			}
		});
		jp.add(this.btnUser);
		return jp;
	}

	/**
	 * ��������壨ѡ���壩
	 * 
	 * @return
	 */
	private JTabbedPane createMainPanel() {
		JTabbedPane jtp = new JTabbedPane();
		jtp.addTab("��������", this.createControlPanel());
		jtp.addTab("Ƥ������", this.createSkinPanel());
		return jtp;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private JPanel createSkinPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		File dir = new File(Img.GRAPHICS_PATH);
		File[] files = dir.listFiles();
		this.skinViewList = new Image[files.length];
		for (int i = 0; i < files.length; i++) {
			this.skinData.addElement(files[i].getName());
			this.skinViewList[i] = new ImageIcon(files[i].getPath() + "/view.jpg").getImage();
		}

		this.skinList = new JList(this.skinData);
		this.skinList.setSelectedIndex(0);
		this.skinList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				repaint();
			}

		});
		this.skinView = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				g.drawImage(skinViewList[skinList.getSelectedIndex()], 0, 0, null);
			}
		};

		panel.add(new JScrollPane(this.skinList), BorderLayout.WEST);
		panel.add(this.skinView, BorderLayout.CENTER);
		return panel;
	}

	/**
	 * �����������
	 * 
	 * @return
	 */
	private JPanel createControlPanel() {
		JPanel jp = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				g.drawImage(IMG_PSP, 0, 0, null);
			}
		};
		jp.setLayout(null);
		for (int i = 0; i < keyTexts.length; i++) {
			jp.add(keyTexts[i]);
		}
		return jp;
	}

	/**
	 * д����Ϸ����
	 * 
	 * @return
	 */
	private boolean writeConfig() {
		HashMap<Integer, String> keySet = new HashMap<Integer, String>();
		for (int i = 0; i < keyTexts.length; i++) {
			int keyCode = this.keyTexts[i].getKeyCode();
			if (keySet.containsKey(keyCode)) {
				this.errorMsg.setText("�ظ�����");
				return false;
			}
			keySet.put(keyCode, this.keyTexts[i].getMethodName());
		}
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PATH));
			oos.writeObject(keySet);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
			this.errorMsg.setText(e.getMessage());
			return false;
		}
		this.errorMsg.setText(null);
		return true;

	}

}
