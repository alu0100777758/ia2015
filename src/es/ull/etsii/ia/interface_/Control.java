package es.ull.etsii.ia.interface_;

import javax.swing.BoxLayout;
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
import es.ull.etsii.ia.interface_.simulation.FootballField;
import es.ull.etsii.ia.interface_.simulation.HiveMemory;

/**
 * Clase encargada de gestionar la logica del programa.
 * @author Javier Martin Hernandez y Tomas Rodriguez Martin 
 */
public final class Control {
	private static Control instance = null;					//	Instancia Singleton.
	private int stepDelay = GridControls.DEFAULT_FPS*1000;	//	Delay por defecto.
	private CellRoboCup window = new CellRoboCup();			//	Ventana de la aplicacion.
	private FootballField grid = new FootballField();		// 	Panel donde se dibuja la simulacion.
	private GridControls gridControls = new GridControls();	// 	Panel donde se dibujan los controles.
	private ControlsEventManager controlPanelEventManager;	// 	Manejador de eventos de los controles.
	private TimerEventManager timerManager;					//	Manejador de eventos del temporizador.
	private Timer stepTimer;								//	Temporizador de turnos
	private boolean walking = false;						//	True si la simulacion se encuentra en ejecucion.
	private MouseControl mouseControl = new MouseControl();	//	Manejador de eventos del raton.
	private Robo_Player last_bot;							// 	Robo player en proceso de orientacion. 
	private Ball ball;										// 	Unica pelota de la simulacion.
	private HiveMemory [] teamsMemory = new HiveMemory[2];	//	memorias Colmena en uso.

	private Control() {
		resetTeamMemory();
	}
	/**
	 * devuelve la instancia singleton de la clase
	 * @return Control
	 */
	public static Control getInstance() {
		if (instance == null) {
			instance = new Control();
		}
		return instance;
	}

	/**
	 * reinicia las memorias colmena.
	 */
	private void resetTeamMemory() {
		getTeamsMemory()[0] = new HiveMemory();
		getTeamsMemory()[1] = new HiveMemory();
	}


	/**
	 * metodo que construye la interfaz.
	 */
	public void buildInterface() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		mainPanel.add(grid);
		mainPanel.add(gridControls);
		window.add(mainPanel);
	}

	/**
	 * metodo encargado de inicializar los diferentes gestores de eventos.
	 */
	public void setEventManagers() {
		setControlPanelEventManager(new ControlsEventManager(gridControls));
		setTimerManager(new TimerEventManager());
	}

	/**
	 * Metodo que desarrolla todos los pasos necesarios para lanzar la
	 * aplicacion tal como se define.
	 */
	public void play() {
		buildInterface();
		setEventManagers();
		gridControls.setListener(getControlPanelEventManager());
		setStepTimer(new Timer(stepDelay, timerManager));
		grid.addMouseListener(mouseControl);
		grid.addMouseMotionListener(mouseControl);
		window.setVisible(true);
	}

	/**
	 * metodo encargado de actualizar la densidad de la rejilla. setea el numero de columnas a "x" y el de filas a "y"
	 * @param x
	 * @param y
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
			grid.tick();
			grid.repaint();
	}

	/**
	 * metodo encargado de avanzar en la simulacion.
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
	 * metodo que reinicia y limpia el estado mostrado por pantalla.
	 */
	public void reset() {
		setWalking(false);
		resetTeamMemory();
		getGridControls().getStartStop().setText(GridControls.START_TEXT);
		getControlPanelEventManager().setStart(false);
		grid.reset();
	}

	/**
	 * metodo encargado de comenzar la simulacion.
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

	/**
	 * actualiza el tamaño de la rejilla.
	 */
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

	/**
	 * inicia la operacion de insercion de un nuevo actor.
	 * @param point 
	 */
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
					Actor.FACE_NORTH, getGrid(), getTeamsMemory()[gridControls.getActorType()]));
			getGrid().addActor(getLast_bot());
			getTeamsMemory()[gridControls.getActorType()].setHiveSize(getTeamsMemory()[gridControls.getActorType()].getHiveSize()+1);
				}
			}
			else{
				if(getGrid().getBall() == null){
					getGrid().setBall(new Ball(getGrid(),  getGrid().toSystem(point)));
				}
			}
			window.repaint();
		}
	}

	/**
	 * confirma la posicion de los actores en edicion.
	 * @param point2d
	 */
	public void clickReleased(Point2D point2d) {
		if(getLast_bot() == null){
			getGrid().getBall().setPos(getGrid().toSystem(point2d));

		}else
		setLast_bot(null);
	}

	/**
	 * efectua las operaciones de actualizacion necesarias cuando esta en modo edicion de balon y se arrastra el raton.
	 * @param point
	 */
	public void dragged(Point2D point) {
		if (getLast_bot() != null) {
			setRobotRotation(point);
		}
		else if(getGrid().getBall() != null){
			getGrid().getBall().setPos(grid.toSystem(point)); 
		}
		window.repaint();
	}

	/**
	 * rota el actor en edicion hacia la direccion basica mas cercana a la representada mediante point
	 * @param point 
	 */
	private void setRobotRotation(Point2D point) {
		Point2D difference = grid.toSystem(point).substract(
				getLast_bot().getPos());
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



	public FootballField getGrid() {
		return grid;
	}

	public void setGrid(FootballField grid) {
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

	public void step() {
		launchTick();
	}

	public HiveMemory[] getTeamsMemory() {
		return teamsMemory;
	}

	public void setTeamsMemory(HiveMemory[] teamsMemory) {
		this.teamsMemory = teamsMemory;
	}
	
	
}
