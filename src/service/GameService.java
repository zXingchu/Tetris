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
	 * 启动主线程开始游戏
	 */
	public void startGame();
	
	/**
	 * 游戏主要行为
	 */
	public void mainAction();
}
