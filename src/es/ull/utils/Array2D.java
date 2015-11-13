package es.ull.utils;

import java.util.Iterator;

import es.ull.etsii.ia.interface_.geometry.Point2D;

/**
 * clase encargada de representar un array bidimensional.
 * @author Javier Martin hernandez
 *
 * @param <Type> tipo de datos que contiene el array.
 */
public class Array2D<Type> implements Iterable<Type> {
	private Object array[];
	private int rows;
	private int columns;

	/**
	 * @param m filas.
	 * @param n columnas.
	 */
	public Array2D(int m, int n) {
		setArray(new Object[m * n]);
		setM(m);
		setN(n);
	}
	/**
	 * constructor de copia.
	 * @param array 
	 */
	public Array2D(Array2D<Type> array){
		setArray(array.getArray());
		setM(array.getRows());
		setN(array.getColumns());
	}

	/**
	 * devuelve el indice en el array unidimensional.
	 * @param row
	 * @param column.
	 * @return int
	 */
	private int getIndex(int row, int column) {
		if ((row > getRows()) || (column > getColumns())) {
			System.out.println("ERROR: row : " + row + "    column: " + column);
			return -1;
		}
		return (getColumns() * row) + column;
	}

	/**
	 * devuelve el elemento en la fila "row" y columna "column".
	 * @param row
	 * @param column
	 * @return Type
	 */
	@SuppressWarnings("unchecked")
	// la funcion "set" asegura el tipo.
	public Type get(int row, int column) {
		int index = getIndex(row, column);
		return index < getRows()*getColumns()? (Type)getArray()[getIndex(row, column)] : null;
	}

	/**
	 * devuelve un array con los datos ubicados desde row1,column1 hasta row2,column2.
	 * @param row1
	 * @param column1
	 * @param row2
	 * @param column2 
	 * @return Array2D<Type>
	 */
	public Array2D<Type> copy(int row1, int column1, int row2, int column2) {
		Array2D<Type> array = new Array2D<Type>(row2 - (row1 - 1), column2
				- (column1 - 1));
		for (int i = 0; i < array.getRows(); i++) {
			for (int j = 0; j < array.getColumns(); j++) {
				array.set(i, j, get(row1 + i, column1 + j));
			}
		}
		return array;
	}

	/**
	 * intercambia el objeto en la  fila "row1" columna "col1" por el de la fila "row2" columna "col2".
	 * @param row1
	 * @param col1
	 * @param row2
	 * @param col2
	 */
	public void switchElements(int row1, int col1, int row2, int col2) {
		Object dummy = get(row1, col1);
		set(row1, col1, get(row2, col2));
		set(dummy, row2, col2);
	}
	@Override
	public String toString() {
		String string = new String();
		for (int i = 0; i < getRows(); i++) {
			for (int j = 0; j < getColumns(); j++) {
				string += (" | " + get(i, j));
			}
			string += "\n";
		}
		return string;
	}

	// ******************Getters & Setters********************
	public Type get(Point2D pos){
		return get((int)pos.y(),(int)pos.x());
	}
	
	public void set(int row, int column, Type value) {
		set(value, row, column);
	}
	private void set(Object value, int row, int column) {
		getArray()[getIndex(row, column)] = value;
	}

	protected Object[] getArray() {
		return array;
	}

	protected void setArray(Object[] array) {
		this.array = array;
	}

	public int getRows() {
		return rows;
	}

	protected void setM(int m) {
		this.rows = m;
	}

	public int getColumns() {
		return columns;
	}

	protected void setN(int n) {
		this.columns = n;
	}


	@Override
	public Iterator<Type> iterator() {
		return new Array2DIterator<Type>();
	}
	/**
	 * @author Javier Martin Hernandez
	 *
	 * @param <Typein> tipo del contenido del array.
	 */
	private class Array2DIterator<Typein> implements Iterator<Typein> {
		int index = 0;
		
		@Override
		public boolean hasNext() {
			return index < getArray().length;
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public Typein next() {
			index++;
			return (Typein) getArray()[index - 1];
		}
	}

}
