package assignment2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class Util {
	public static int[] getRandomEightPuzzleState() {
		Integer[] integerArray = {0,1,2,3,4,5,6,7,8};
		List<Integer> integerList = Arrays.asList(integerArray);
		Collections.shuffle(integerList);
		integerList.toArray(integerArray);
		return Stream.of(integerArray).mapToInt(i->i).toArray();
	}

}
