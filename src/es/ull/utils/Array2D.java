package es.ull.utils;

public class Array2D<Type> {
	private Object array[];
	private int rows;
	private int columns;

	public Array2D(int m, int n) {
		setArray(new Object[m * n]);
		setM(m);
		setN(n);
	}

	private int getIndex(int row, int column) {
		return getColumns() * row + column;
	}

	@SuppressWarnings("unchecked")
	// la funcion "set" asegura el tipo.
	public Type get(int row, int column) {
		return (Type) getArray()[getIndex(row, column)];
	}

	public void set(int row, int column, Type value) {
		getArray()[getIndex(row, column)] = value;
	}

	public Array2D<Type> copy(int row1, int column1, int row2, int column2) {
		Array2D<Type> array = new Array2D<>(row2 - row1, column2 - column1);
		int row = 0;
		int column = 0;
		for (int i = row1; i < row2; i++) {
			for (int j = column1; j < column2; j++) {
				array.set(row, column, get(i, j));
				column++;
			}
			row++;
		}
		// array.insertSubarray(0, 0, this); //TODO
		return array;
	}

	// public void insertSubarray(int rowInit, int columnInit, Array2D<Type>
	// submatrix){
	// int rowEnd = submatrix.getRows() < getRows() ? submatrix.getRows() :
	// getRows();
	// int columnEnd = submatrix.getColumns() < getColumns() ?
	// submatrix.getColumns() : getColumns();
	//
	// for(int i = rowInit; i < rowEnd; i++){
	// for(int j = columnEnd; j<columnEnd;j++){
	// set(i, j, submatrix.get(i, j));
	// }
	// }
	// }

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

}
