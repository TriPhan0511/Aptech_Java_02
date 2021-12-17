import java.util.ArrayList;
import java.util.List;

public class TestApp 
{
	public static void main(String[] arsg)
	{
		List<String> list = new ArrayList<>();
		list.add("s001");
		list.add("s002");
		
//		System.out.println(list.indexOf("s001"));
		String s = Console.nextString("Enter id: ");
		System.out.println(list.indexOf(s));
	}
}
