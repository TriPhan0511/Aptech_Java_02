package version02;

import java.util.ArrayList;
import java.util.List;

public class TestApp 
{
	public static void main(String[] args) 
	{
//		StringBuilder builder = new StringBuilder();
//		builder.append("hello");
//		String s = null;
//		builder.append(s);
//		System.out.println(builder.toString());
		
//		String s = "";
//		System.out.println(s.length());
		
		List<String> list = new ArrayList<>();
		list.add("su001");
		list.add("su002");
		list.add("su003");
		
//		String subjectCode = "su001";
		String subjectCode = "su100";
		System.out.println(list.indexOf(subjectCode));
	}
}
