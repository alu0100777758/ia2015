package es.ull.etsii.ia.interface_;

import java.awt.Color;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.Timer;
/**
 * @author Javier Mart�n Hern�ndez
 *	Clase encargada de gestionar la l�gica del programa.
 */
public final class Control {
	private static final Point2D SOUTH = new Point2D(0, -1);
	private static final Point2D NORTH = new Point2D(0, 1);
	private static final Point2D EAST = new Point2D(1, 0);
	private static final Point2D WEST = new Point2D(-1, 0);
	private Random rand = new Random();
	private static Control instance = null;
	private int stepDelay = 1000;
	public static final Point2D[] DIRECTION_POINTS = { WEST,
			EAST, NORTH, SOUTH };
	private RandomPathWindow window = new RandomPathWindow();
	private GridStatusPanel grid = new GridStatusPanel();
	private GridControls gridControls = new GridControls();
	private ControlsEventManager controlPanelEventManager;
	private TimerEventManager timerManager;
	private Timer stepTimer;
	private boolean walking = false;
	private MouseControl mouseControl = new MouseControl();

	public TimerEventManager getTimerManager() {
		return timerManager;
	}

	public void setTimerManager(TimerEventManager timerManager) {
		this.timerManager = timerManager;
	}

	public Timer getStepTimer() {
		return stepTimer;
	}

	public void setStepTimer(Timer stepTimer) {
		this.stepTimer = stepTimer;
	}

	public ControlsEventManager getControlPanelEventManager() {
		return controlPanelEventManager;
	}

	public void setControlPanelEventManager(
			ControlsEventManager controlPanelEventManager) {
		this.controlPanelEventManager = controlPanelEventManager;
	}

	public boolean isWalking() {
		return walking;
	}

	public void setWalking(boolean walking) {
		this.walking = walking;
	}

	private Control() {
	}

	public static Control getInstance() {
		if (instance == null) {
			instance = new Control();
		}
		return instance;
	}

	/**
	 *  m�todo que construye la interfaz.
	 */
	public void buildInterface() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		mainPanel.add(grid);
		mainPanel.add(gridControls);
		window.add(mainPanel);
	}
	
	/**
	 * m�todo encargado de inicializar los diferentes gestores de eventos.
	 */
	public void setEventManagers() {
		setControlPanelEventManager(new ControlsEventManager(gridControls));
		setTimerManager(new TimerEventManager());
	}
	
	/**
	 * M�todo que desarrolla todos los pasos necesarios para lanzar la aplicaci�n tal como se define.
	 */
	public void play() {
		buildInterface();
		setEventManagers();
		gridControls.setListener(getControlPanelEventManager());
		grid.turnOnPath();
		setStepTimer(new Timer(stepDelay, timerManager));
		grid.addMouseListener(mouseControl);
		window.setVisible(true);
	}
	
	/**
	 * @param point punto donde se ha detectado un click
	 * m�todo lanzado por el gestor de eventos del rat�n a fin de situar la posici�n inicial del camino en base a la posici�n del rat�n.
	 */
	public void clickedIn(Point2D point) {
		grid.path.clear();
		grid.path.setStart(grid.toSystem(point));
		grid.path.getActors().add(new Robo_Player((short) 1,grid.toSystem(point), grid, Actor.NORTH));
		grid.repaint();
	}

	/**
	 * @return Point2D 
	 * m�todo que devuelve una posicion aleatoria v�lida para el estado actual del programa (punto que se sumar� al ultimo punto incluido en el camino);
	 */
	public Point2D randomMove() {
		Point2D randomPoint = DIRECTION_POINTS[rand.nextInt(4)];
		if ((!gridControls.revisitCheck.isSelected())
				&& grid.path.hasVisited(grid.path.getLast().add(randomPoint))) {
			try {
				return randomMove();
			} catch (StackOverflowError e) {
				setWalking(false);
				return new Point2D(0, 0);
			}
		}
		return randomPoint;
	}

	/**
	 * @param x n�mero de filas.
	 * @param y n�mero de columnas.
	 * m�todo encargado de actualizar la densidad de la rejilla.
	 */
	public void setGridPointsDensity(int x, int y) {
		grid.sethPoints(x);
		grid.setvPoints(y);
		grid.repaint();
	}

	/**
	 *  m�todo encargado de avanzar a una posicion aleatoria factible para el camino actual.
	 */
	public void launchTick() {
		try {
			grid.path.addRelative(randomMove());
			grid.repaint();
			if (gridControls.borderCheck.isSelected() && grid.atBorder())
				walking = false;
		} catch (OutOfSystemException e) {
			walking = false;
		}
	}

	public void setPathColor(Color color) {
		grid.pathColor(color);
		grid.repaint();
	}

	/**
	 * @return Color 
	 * m�todo que devuelve un color aleatorio
	 */
	public Color getRandomColor() {
		return new Color(rand.nextInt(256), rand.nextInt(256),
				rand.nextInt(256));
	}

	/**
	 * @return Color
	 * m�todo que devuelve el color seleccionado por el usuario desde un dialogo de selecci�n de color.
	 */
	public Color getColorFromDialog() {
		return JColorChooser.showDialog(window, "Choose Background Color",
				gridControls.getBackground());
	}

	/**
	 * m�todo encargado de avanzar en el camino.
	 */
	void stepForward() {
		walkingOngrid: {
			if (!walking) {
				stepTimer.stop();
				break walkingOngrid;
			}
			launchTick();
		}
	}

	/**
	 * m�todo que reinicia y limpia el camino mostrado por pantalla.
	 */
	void reset() {
		grid.updatePath();
		grid.repaint();
	}

	/**
	 * m�todo encargado de comenzar la simulaci�n.
	 */
	void tryToRunWithDelay() {
		int stepsDelay = 0;
		boolean parseError = false;
		try {
			stepsDelay = gridControls.getDelay();
		} catch (NumberFormatException exception) {
			parseError = true;
		} finally {
			if (!parseError) {
				stepTimer.setDelay(stepsDelay);
			}
		}
		launchTick();
		stepTimer.start();
		walking = true;
	}

	/**
	 * m�todo encargado de actualizar el punto inicial (desde el panel de controles)
	 */
	void trySetPathStart() {
		int x = 0, y = 0;
		boolean parseError = false;
		try {
			x = gridControls.gethStart();
			y = gridControls.getvStart();
		} catch (NumberFormatException exception) {
			parseError = true;
		} finally {
			if (!parseError) {
				setPathStart(x, y);
			}
		}
	}


	void setPathStart(int x, int y) {
		grid.updatePath();
		grid.setStart(new Point2D(x, y));
		grid.repaint();
	}

	void trySetGridPoints() {
		int x = 0, y = 0;
		boolean parseError = false;
		try {
			x = gridControls.gethPoints();
			y = gridControls.getvPoints();
		} catch (NumberFormatException exception) {
			parseError = true;
		} finally {
			if (!parseError) {
				setGridPointsDensity(x, y);
			}
		}
	}
}
