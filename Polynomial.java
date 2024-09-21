import java.lang.Math;

class Polynomial {
	
	public double [] coeff;
	
	Polynomial () {
		
		coeff = new double[1];
	}
	
	Polynomial (double [] c) {
		coeff = c;
		
	}
	
	public Polynomial add (Polynomial other) {
		
		//creates an array with one element (0); but array will later be reassigned
		Polynomial newpoly = new Polynomial();
		double [] newcoeff;
		
		if (this.coeff.length >= other.coeff.length) {
			newcoeff = new double [this.coeff.length];
			for (int i =0; i< this.coeff.length; i++) {
				if (i<other.coeff.length) {
					newcoeff[i] = this.coeff[i] + other.coeff[i];
				}
				else {
					newcoeff[i] = this.coeff[i];
				}
			}
		}
		else {
			newcoeff = new double [other.coeff.length];
			for (int i=0; i<other.coeff.length; i++) {
				if (i<this.coeff.length) {
					newcoeff[i] = this.coeff[i] + other.coeff[i];
				}
				else {
					newcoeff[i] = other.coeff[i];
				}
			}
		}
		
		newpoly.coeff = newcoeff;
		
		return newpoly;
	}
	
	
	
	public double evaluate (double x) {
		
		double finalvalue = 0;
		
		for (int i=0; i<this.coeff.length; i++) {
			
			finalvalue += this.coeff[i]*Math.pow(x,i);
		}
		
		return finalvalue;
		
	}
	
	
	
	public boolean hasRoot (double x) {
		
		if (this.evaluate(x) == 0) {
			return true;
		}
		
		return false;
	}
}