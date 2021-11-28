import java.util.Iterator;
import java.util.LinkedList;
import java.util.ArrayList;

public class TestApp 
{
	public static void main(String[] args)
	{
//		ArrayList<String> list = new ArrayList<>();
//		list.add("Hello");
//		list.add("Aloha");
//		list.add("Bonjour");
//		
//		Iterator<String> iterator = list.iterator();
//		while (iterator.hasNext())
//		{
//			System.out.println(iterator.next());
//		}
		
//		LinkedList<String> list2 = new LinkedList<>();
//		list2.add("Goodbye");
//		list2.add("See you soon!");
//		list2.add("See you again!");
//		
//		Iterator<String> iterator = list2.iterator();
//		while (iterator.hasNext())
//		{
//			System.out.println(iterator.next());
//		}
		
		LinkedList<Integer> list3 = new LinkedList<>(); 
		list3.add(Integer.valueOf(10));
		list3.add(20);
		list3.add(Integer.parseInt("30"));
		
		Iterator<Integer> iterator = list3.iterator();
		while (iterator.hasNext())
		{
			System.out.println(iterator.next());
		}
	}
}



























