package es.ull.etsii.ia.interface_;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.Timer;

import es.ull.etsii.ia.interface_.Actors.Actor;
import es.ull.etsii.ia.interface_.Actors.Ball;
import es.ull.etsii.ia.interface_.Actors.Robo_Player;
import es.ull.etsii.ia.interface_.geometry.Point2D;
import es.ull.etsii.ia.interface_.gui.GridControls;
import es.ull.etsii.ia.interface_.listeners.ControlsEventManager;
import es.ull.etsii.ia.interface_.listeners.MouseControl;
import es.ull.etsii.ia.interface_.listeners.TimerEventManager;

/**
 * @author Javier Martin Hernandez y Tomas Rodriguez Martin 
 * Clase encargada de gestionar la logica del programa.
 */
public final class Control {
	private static Control instance = null;					//	Instancia Singleton.
	private int stepDelay = GridControls.DEFAULT_FPS*1000;	//	Delay por defecto.
	private CellRoboCup window = new CellRoboCup();			//	Ventana de la aplicacion.
	private IA_State grid = new IA_State();//= new GridStatusPanel();	// 	Panel donde se dibuja la simulacion.
	private GridControls gridControls = new GridControls();	// 	Panel donde se dibujan los controles.
	private ControlsEventManager controlPanelEventManager;	// 	Manejador de eventos de los controles.
	private TimerEventManager timerManager;					//	Manejador de eventos del temporizador.
	private Timer stepTimer;								//	Temporizador de turnos
	private boolean walking = false;						//	True si la simulacion se encuentra en ejecucion.
	private MouseControl mouseControl = new MouseControl();	//	Manejador de eventos del raton.
	private Robo_Player last_bot;							// 	Robo player en proceso de orientacion. 
	private Ball ball;										// 	Unica pelota de la simulacion.
	
	

	private Control() {
	}

	/**
	 * @return Instancia singleton de la clase
	 */
	public static Control getInstance() {
		if (instance == null) {
			instance = new Control();
		}
		return instance;
	}

	/**
	 * m�todo que construye la interfaz.
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
	 * M�todo que desarrolla todos los pasos necesarios para lanzar la
	 * aplicaci�n tal como se define.
	 */
	public void play() {
		buildInterface();
		setEventManagers();
		gridControls.setListener(getControlPanelEventManager());
//		grid.turnOnPath();
		setStepTimer(new Timer(stepDelay, timerManager));
		grid.addMouseListener(mouseControl);
		grid.addMouseMotionListener(mouseControl);
		window.setVisible(true);
	}

	/**
	 * @param x
	 *            n�mero de filas.
	 * @param y
	 *            n�mero de columnas. m�todo encargado de actualizar la
	 *            densidad de la rejilla.
	 */
	public void setGridPointsDensity(int x, int y) {
		getGrid().sethPoints(x);
		getGrid().setvPoints(y);
		getGrid().repaint();
	}

	/**
	 * metodo encargado de marcar el inicio de turno de la simulacion.
	 */
	public void launchTick() {
//		try {
			grid.tick();
			grid.repaint();
//			if (gridControls.borderCheck.isSelected() && grid.atBorder())
//				walking = false;
//		} catch (OutOfSystemException e) {
//			walking = false;
//		}
	}

//	public void setPathColor(Color color) {
//		grid.pathColor(color);
//		grid.repaint();
//	}

	/**
	 * @return Color m�todo que devuelve el color seleccionado por el usuario
	 *         desde un dialogo de selecci�n de color.
	 */
	public Color getColorFromDialog() {
		return JColorChooser.showDialog(window, "Choose Background Color",
				gridControls.getBackground());
	}

	/**
	 * m�todo encargado de avanzar en la simulaci�n.
	 */
	public void stepForward() {
		walkingOngrid: {
			if (!walking) {
				stepTimer.stop();
				break walkingOngrid;
			}
			stepTimer.setDelay(gridControls.getDelay());
			launchTick();
		}
	}

	/**
	 * m�todo que reinicia y limpia el estado mostrado por pantalla.
	 */
	public void reset() {
		setBall(null);
		grid.reset();
	}

	/**
	 * m�todo encargado de comenzar la simulaci�n.
	 */
	public void tryToRunWithDelay() {
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

	public void updateDimension() {
		int x = 0, y = 0;
		boolean parseError = false;
		try {
			x = gridControls.gethPoints() + 1;
			y = gridControls.getvPoints() + 1;
		} catch (NumberFormatException exception) {
			parseError = true;
		} finally {
			if (!parseError) {
				setGridPointsDensity(x, y);
				reset();
			}
		}
	}

	public void clickPressed(Point2D point) {
		if (getLast_bot() == null) {
			if(gridControls.getActorType() < 2){
				Point2D inSystem = getGrid().toSystem(point);
				Robo_Player robot;
				try {
					robot = (Robo_Player)getGrid().getMapState().get((int)inSystem.x(), (int)inSystem.y());
					robot.setTeam(gridControls.getActorType());
				} catch (Exception e) {
				robot = (Robo_Player)getGrid().getMapState().get((int)inSystem.x(), (int)inSystem.y());
			setLast_bot(new Robo_Player((short) gridControls.getActorType(), inSystem, getGrid(),
					Actor.FACE_NORTH, getGrid()));
			getGrid().addActor(getLast_bot());
				}
			}
			else{
				if(getBall() == null){
					setBall(new Ball(getGrid(),  getGrid().toSystem(point)));
					getGrid().getActors().add(getBall());
				}
			}
				
			window.repaint();
		}
	}

	public void clickReleased(Point2D point2d) {
		setLast_bot(null);
	}

	public void dragged(Point2D point) {
		if (getLast_bot() != null) {
			Point2D difference = grid.toSystem(point).substract(
					getLast_bot().getPos());
//			System.out.println("difference : " + difference);
			if (Math.abs(difference.x()) > Math.abs(difference.y())) {
				if (difference.x() < 0)
					getLast_bot().setFacing(Actor.FACE_WEST);
				else
					getLast_bot().setFacing(Actor.FACE_EAST);
			}
			else{
				if (difference.y() < 0)
					getLast_bot().setFacing(Actor.FACE_NORTH);
				else
					getLast_bot().setFacing(Actor.FACE_SOUTH);
			}
		}
		else if(getBall() != null)
			getBall().setPos(grid.toSystem(point));
		window.repaint();
	}
	// ******************Getters & Setters********************
	public Ball getBall() {
		return ball;
	}

	public void setBall(Ball ball) {
		this.ball = ball;
	}

	public Robo_Player getLast_bot() {
		return last_bot;
	}

	public void setLast_bot(Robo_Player last_bot) {
		this.last_bot = last_bot;
	}

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



	public IA_State getGrid() {
		return grid;
	}

	public void setGrid(IA_State grid) {
		this.grid = grid;
	}

	public GridControls getGridControls() {
		return gridControls;
	}

	public void setGridControls(GridControls gridControls) {
		this.gridControls = gridControls;
	}

	public CellRoboCup getWindow() {
		return window;
	}

	public void setWindow(CellRoboCup window) {
		this.window = window;
	}
	
}
