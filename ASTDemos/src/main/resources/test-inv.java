import java.util.ArrayList;
import java.lang.String;

public class test {
	private boolean test() {
		String st_test = "Hello";
		st_test.indexOf("ell");
		st_test.substring(2,4);
		return false;
	}

	private void test2(){
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add("Hello");
		arrayList.add(1, "World");
		String testStr = arrayList.get(0);
		testStr.substring(2,4);
	}
}