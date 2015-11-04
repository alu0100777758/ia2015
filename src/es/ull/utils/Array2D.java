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
//		System.out.println("Trow: " + getRows() + "   TCol:" + getColumns());
		if(!(row < getRows() && column < getColumns()))
				System.out.println("ERROR: row : "+ row + "    column: " +column);
		return (getColumns() * row) + column;
	}

	@SuppressWarnings("unchecked")
	// la funcion "set" asegura el tipo.
	public Type get(int row, int column) {
		return (Type) getArray()[getIndex(row, column)];
	}

	public void set(int row, int column, Type value) {
//		getArray()[getIndex(row, column)] = value;
		set(value, row, column);
	}

	public Array2D<Type> copy(int row1, int column1, int row2, int column2) {
		Array2D<Type> array = new Array2D<Type>(row2 - (row1-1), column2 - (column1-1));
		System.out.println("maxIndex: "+getIndex(array.getRows(), array.getColumns()));
		System.out.println("arraylenght = " + array.array.length);
		System.out.println("Copiando ("+row1+","+column1+") - ("+row2+","+column2+") a matriz("+array.getRows()+","+array.getColumns()+")" );
		int row = 0;
		int column = 0;
		for (int i = row1; row < array.getRows(); i++) {
			for (int j = column1; column < array.getColumns(); j++) {
				array.set(row, column, get(i, j));
				column++;
//				System.out.println("peta en i = " +i +"\t j = "+j +"  con index= " + getIndex(row, column));
			}
			row++;
		}
		// array.insertSubarray(0, 0, this); //TODO
		return array;
	}
	public String toString(){
		String string = new String();
		for(int i= 0; i < getRows(); i++ ){
			for(int j = 0; j < getColumns(); j++){
				string += (" | " + get(i, j));
			}
			string += "\n";
		}
		return string;
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
	public void switchElements(int x1, int y1, int x2, int y2){
		Object dummy = get(x1, y1);
		set(x1, y1, get(x2, y2));
		set(dummy, x2, y2);
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

}
