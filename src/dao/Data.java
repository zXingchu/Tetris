package dao;

import java.util.List;

import dto.Player;

/**
 * ���ݳ־ò�ӿ�
 * @author 26492
 *
 */
public interface Data {

	/**
	 * ��ȡ����
	 * @return
	 */
	public List<Player> loadData();
	
	/**
	 * �洢����
	 * @param pla
	 */
	public void saveData(Player pla);

}
