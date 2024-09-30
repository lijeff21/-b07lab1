 //main class

public class Driver {
	public static void main(String [] args) {
		Polynomial p = new Polynomial();

		System.out.println("should be 0.0 : " + p.evaluate(3));
		double [] c1 = {-4, -6, 7};
		int [] d1 = {0, 4, 5};
		Polynomial p1 = new Polynomial(c1, d1);
		double [] c2 = {1, -7, 6, 4};
		int [] d2 = {0, 2, 4, 5};
  		Polynomial p2 = new Polynomial(c2, d2);
		Polynomial s = p1.add(p2);
		Polynomial x = p1.multiply(p2);

		System.out.println("s(1) should equal 1.0 :" + s.evaluate(1));
		System.out.println("x(1) should equal 0.0 and be x=1 a root:" + x.evaluate(0));

	}
}
