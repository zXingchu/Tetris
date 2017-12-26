package service;


public interface GameService {

	public void keyUp();
	
	public boolean keyDown();
	
	public void keyLeft();
	
	public void keyRight();
	
	public void keyFunUp();
	
	public void keyFunDown();

	public void keyFunLeft();
	
	public void keyFunRight();

	/**
	 * �������߳̿�ʼ��Ϸ
	 */
	public void startGame();
	
	/**
	 * ��Ϸ��Ҫ��Ϊ
	 */
	public void mainAction();
}
