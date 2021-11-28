package exercise06;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestApp 
{
	public static void main(String[] args) 
	{
//		int[] numbers = {5,4,3,2,1};
//		numbers.s
		
		List<Integer> list = new ArrayList<>();
		list.add(10);
		list.add(1);
		list.add(5);
		
		for (Integer i : list)
		{
			System.out.println(i);
		}
		
//		Collections.sort(list);
//		for (Integer i : list)
//		{
//			System.out.println(i);
//		}
		
		Collections.sort(list, Collections.reverseOrder());
		for (Integer i : list)
		{
//			System.out.println(i);
			System.out.println(i + ": " + list.indexOf(i));
		}
	}
}
