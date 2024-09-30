import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.lang.Math;

class Polynomial {

    public double[] coeff;
    public int[] expo;

    Polynomial() {
        this.coeff = new double[0];
        this.expo = new int[0];
    }

    Polynomial(double[] c, int[] e) {
        coeff = c;
        expo = e;
    }

    Polynomial(File f) throws IOException {
        Scanner filescanner = new Scanner(f);
        String equation = filescanner.nextLine();

        int[] indexnegatives = new int[equation.length()];
        int negativeindex = 0;
        for (int i = 0; i < equation.length(); i++) {
            if (equation.charAt(i) == '-') {
                indexnegatives[negativeindex] = i;
                negativeindex++;
            }
        }

        String newequation = equation;
        int m = 0;
        while (indexnegatives[m] != 0) {
            newequation = equation.substring(0, indexnegatives[m]) + "+"
                    + equation.substring(indexnegatives[m]);
            m++;
            equation = newequation;
        }

        String[] equationterms = equation.split("\\+");

        double[] coeffarr = new double[equationterms.length];
        int[] expoarr = new int[equationterms.length];

        for (int k = 0; k < equationterms.length; k++) {
            String[] termparts = equationterms[k].split("x");
            if (termparts.length == 1) {
                coeffarr[k] = Double.parseDouble(termparts[0]);
                expoarr[k] = 0;
            } else {
                coeffarr[k] = Double.parseDouble(termparts[0]);
                expoarr[k] = Integer.parseInt(termparts[1]);
            }
        }

        coeff = coeffarr;
        expo = expoarr;
    }

    public Polynomial add(Polynomial other) {
    // Find the maximum possible number of terms in the resulting polynomial
    int maxTerms = this.coeff.length + other.coeff.length;

    // Create temporary arrays to hold the coefficients and exponents
    double[] tempCoeff = new double[maxTerms];
    int[] tempExpo = new int[maxTerms];

    // Index for the resulting polynomial
    int index = 0;

    // Use two pointers to traverse both polynomials
    int i = 0, j = 0;

    // Traverse through both polynomials and add coefficients with the same exponents
    while (i < this.coeff.length && j < other.coeff.length) {
        if (this.expo[i] == other.expo[j]) {
            // Same exponent, add coefficients
            tempCoeff[index] = this.coeff[i] + other.coeff[j];
            tempExpo[index] = this.expo[i];
            i++;
            j++;
        } else if (this.expo[i] > other.expo[j]) {
            // Add the term from the first polynomial
            tempCoeff[index] = this.coeff[i];
            tempExpo[index] = this.expo[i];
            i++;
        } else {
            // Add the term from the second polynomial
            tempCoeff[index] = other.coeff[j];
            tempExpo[index] = other.expo[j];
            j++;
        }
        index++;
    }

    // If there are remaining terms in the first polynomial
    while (i < this.coeff.length) {
        tempCoeff[index] = this.coeff[i];
        tempExpo[index] = this.expo[i];
        i++;
        index++;
    }

    // If there are remaining terms in the second polynomial
    while (j < other.coeff.length) {
        tempCoeff[index] = other.coeff[j];
        tempExpo[index] = other.expo[j];
        j++;
        index++;
    }

    // Create final arrays with the correct size
    double[] finalCoeff = new double[index];
    int[] finalExpo = new int[index];

    // Copy the non-zero terms to the final arrays
    for (int k = 0; k < index; k++) {
        finalCoeff[k] = tempCoeff[k];
        finalExpo[k] = tempExpo[k];
    }

    return new Polynomial(finalCoeff, finalExpo);
}

    public double evaluate(double x) {
        double finalvalue = 0;
        for (int i = 0; i < this.coeff.length; i++) {
            finalvalue += this.coeff[i] * Math.pow(x, this.expo[i]);
        }
        return finalvalue;
    }

    public void saveToFile(String filename) throws IOException {
        String equation = "";
        String[] equationterms = new String[this.coeff.length];
        for (int i = 0; i < this.coeff.length; i++) {
            if (this.expo[i] == 0) {
                equationterms[i] = Double.toString(this.coeff[i]);
            } else {
                equationterms[i] = Double.toString(this.coeff[i]) + "x" + this.expo[i];
            }
        }

        equation = equationterms[0];
        for (int k = 1; k < equationterms.length; k++) {
            if (equationterms[k].charAt(0) == '-') {
                equation += equationterms[k];
            } else {
                equation += "+" + equationterms[k];
            }
        }

        FileWriter writer = new FileWriter(filename);
        writer.write(equation);
        writer.close();
        System.out.println("Successfully wrote to the file.");
    }

    public boolean hasRoot(double x) {
        return this.evaluate(x) == 0;
    }

	// e.g (6 + 5x + 4x^2)(3 + 9x + 8x^3)
	public Polynomial multiply(Polynomial other) {
    Polynomial newpoly = new Polynomial();
    double[] tempcoeff = new double[this.coeff.length * other.coeff.length];
    int[] tempexpo = new int[this.coeff.length * other.coeff.length];

    // Step 1: Apply distributive property
    int k = 0;
    for (int i = 0; i < this.coeff.length; i++) {
        for (int j = 0; j < other.coeff.length; j++) {
            tempcoeff[k] = this.coeff[i] * other.coeff[j];
            tempexpo[k] = this.expo[i] + other.expo[j];  // Add exponents
            k++;  // Increment k
        }
    }

    // Step 2: Combine like terms (same exponents)
    for (int m = 0; m < tempcoeff.length - 1; m++) {
        for (int n = m + 1; n < tempcoeff.length; n++) {
            if (tempexpo[m] == tempexpo[n]) {
                tempcoeff[m] += tempcoeff[n];
                tempcoeff[n] = 0;
                tempexpo[n] = 0;
            }
        }
    }

    // Step 3: Count non-zero terms
    int count = 0;
    for (int l = 0; l < tempexpo.length; l++) {
        if (tempexpo[l] != 0) {
            count++;
        }
    }

    // Step 4: Create new arrays for the resulting polynomial
    double[] newcoeff = new double[count];
    int[] newexpo = new int[count];

    int r = 0;
    for (int l = 0; l < tempcoeff.length; l++) {
        if (tempexpo[l] != 0) {
            newcoeff[r] = tempcoeff[l];
            newexpo[r] = tempexpo[l];
            r++;
        }
    }

    newpoly.coeff = newcoeff;
    newpoly.expo = newexpo;

    return newpoly;
	}
}
