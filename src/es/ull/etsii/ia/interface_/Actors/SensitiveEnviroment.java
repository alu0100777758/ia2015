package es.ull.etsii.ia.interface_.Actors;

import es.ull.utils.Array2D;

public interface SensitiveEnviroment {
	Array2D<Actor> getVision(Actor sensor);
}
