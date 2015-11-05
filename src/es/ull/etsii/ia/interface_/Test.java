package es.ull.etsii.ia.interface_;

import es.ull.utils.Array2D;

public class Test {
	public static void main(String[] args) {
		Array2D<Integer> arry = new Array2D<>(5, 4);
		for(int i = 0; i<arry.getRows(); i++){
			for(int j = 0; j< arry.getColumns(); j++){
				arry.set(i, j, i*arry.getColumns()+j);
			}
		}
		for(Integer i : arry){
			System.out.println("accediendo: " + i);
		}
		System.out.println(arry);
		Array2D<Integer> arry2 = arry.copy(1, 2, arry.getRows()-1, arry.getColumns()-1);
		System.out.println("Arry2 \n "+arry2);
	}
}
