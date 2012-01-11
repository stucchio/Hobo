package org.styloot.hobo;

public class CIELabColor {
    public final double L;
    public final double a;
    public final double b;
    public CIELabColor(double li, double ai, double bi) {
	L = li; a = ai; b = bi;
    }

    public static CIELabColor CIELabFromRGB(int r, int g, int b) {
	return CIELabFromRGB((byte)(r-128), (byte)(g-128), (byte)(b-128));
    }

    public static CIELabColor CIELabFromRGB(byte r, byte g, byte b) {
	//First Calculate XYZ
	double var_R = ( (double)(r+128) / 255.0);
	double var_G = ( (double)(g+128) / 255.0);
	double var_B = ( (double)(b+128) / 255.0);

	if (var_R > 0.04045) {
	    var_R = Math.pow(( ( var_R + 0.055 ) / 1.055 ), 2.4);
	} else {
	    var_R /= 12.92;
	}
	if (var_G > 0.04045) {
	    var_G = Math.pow(( ( var_G + 0.055 ) / 1.055 ), 2.4);
	} else {
	    var_G /= 12.92;
	}
	if (var_B > 0.04045) {
	    var_B = Math.pow(( ( var_B + 0.055 ) / 1.055 ), 2.4);
	} else {
	    var_B /= 12.92;
	}
	var_R *= 100;
	var_G *= 100;
	var_B *= 100;
	double X = var_R * 0.4124 + var_G * 0.3576 + var_B * 0.1805;
	double Y = var_R * 0.2126 + var_G * 0.7152 + var_B * 0.0722;
	double Z = var_R * 0.0193 + var_G * 0.1192 + var_B * 0.9505;

	//Now convert to CIE
	double var_X = X / ref_X;
	double var_Y = Y / ref_Y;
	double var_Z = Z / ref_Z;

	if (var_X > 0.008856)
	    var_X = Math.pow(var_X,  1.0/3.0 );
	else
	    var_X = ( 7.787 * var_X ) + ( 16. / 116. );
	if (var_Y > 0.008856)
	    var_Y = Math.pow(var_Y,  1.0/3.0 );
	else
	    var_Y = ( 7.787 * var_Y ) + ( 16. / 116. );
	if (var_Z > 0.008856)
	    var_Z = Math.pow(var_Z,  1.0/3.0 );
	else
	    var_Z = ( 7.787 * var_Z ) + ( 16. / 116. );
	return new CIELabColor(( 116. * var_Y ) - 16.0, 500. * ( var_X - var_Y ), 200. * ( var_Y - var_Z ));
    }

    private static double ref_X =  95.047;
    private static double ref_Y = 100.000;
    private static double ref_Z = 108.883;

    private static double KL = 1;
    private static double K1 = 0.045;
    private static double K2 = 0.015;
    public static final double SQRT_REGULARIZATION = 0.00001;

    public double distanceTo(CIELabColor other) {
	//Color distance
	return Math.sqrt(distance2To(other));
    }

    public double distance2To(CIELabColor other) {
	//Color distance squared
	double dL = L - other.L;
	double da = a - other.a;
	double db = b - other.b;
	double Cx = Math.sqrt(a*a+b*b + 0.00001);
	double Cy = Math.sqrt(other.a*other.a+other.b*other.b + 0.00001);
	double dC = Cx - Cy;
	double dH = Math.sqrt(da*da+db*db-dC*dC);
	return (Math.pow(dL/KL,2) + Math.pow(dC/(1+K1*Cy), 2) + Math.pow(dH/(1+K2*Cy), 2));
    }

    public String toString() {
	return "CIELab(" + L + ", " + a + ", " + b + ")";
    }

    public static void main(String[] args) {
	System.out.println(CIELabFromRGB(255,16,0)); //CIELab(53.63283192339698, 78.95144586146552, 67.359200613973)
	System.out.println(CIELabFromRGB(25,16,125)); //CIELab(15.788332034963975, 40.974868070447116, -58.06751132781398)
    }
}