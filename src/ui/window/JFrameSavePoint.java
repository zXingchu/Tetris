package ui.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import control.GameControl;
import util.FrameUtil;

@SuppressWarnings("serial")
public class JFrameSavePoint extends JFrame {

	private JButton btnOk = null;

	private JLabel lbPoint = null;

	private JTextField txName = null;

	private JLabel errMsg = null;

	private GameControl gameControl = null;

	public JFrameSavePoint(GameControl gameControl) {
		this.gameControl = gameControl;
		this.setTitle("保存记录");
		this.setSize(256, 128);
		FrameUtil.setFrameCenter(this);
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		this.createCom();
		this.createAction();
	}

	/**
	 * 显示窗口
	 * @param point
	 */
	public void show(int point){
		this.lbPoint.setText("您的得分是："+point);
		this.repaint();
		this.setVisible(true);
	}
	
	/**
	 * 创建事件监听
	 */
	private void createAction() {
		this.btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name=txName.getText();
				if (name.length() > 16||name==null||name.equals(""))
					errMsg.setText("可能您的名字好长或太短，请缩成16位下1位以上");
				else {
					setVisible(false);
					gameControl.savePoint(name);
				}
			}
		});

	}

	private void createCom() {
		JPanel north = new JPanel(new FlowLayout(FlowLayout.LEFT));
		this.lbPoint = new JLabel("");
		north.add(this.lbPoint);
		this.errMsg = new JLabel();
		this.errMsg.setForeground(Color.RED);
		north.add(this.errMsg);
		this.add(north, BorderLayout.NORTH);
		JPanel center = new JPanel(new FlowLayout(FlowLayout.LEFT));
		this.txName = new JTextField(10);
		center.add(new JLabel("您的名字是"));
		center.add(this.txName);
		this.add(center, BorderLayout.CENTER);
		this.btnOk = new JButton("确定");
		JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER));
		south.add(btnOk);
		this.add(south, BorderLayout.SOUTH);

	}

}
