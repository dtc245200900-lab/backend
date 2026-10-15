package com.codegym.math;

/**
 * Utility methods for finding the real solutions of equations of the form
 * ax^2 + bx + c = 0.
 */
public final class QuadraticEquationSolver {
    private QuadraticEquationSolver() {
    }

    /**
     * Solves ax^2 + bx + c = 0 over the real numbers.
     *
     * @param a coefficient of x squared
     * @param b coefficient of x
     * @param c constant term
     * @return an empty array when there are no real solutions, a one-element
     *         array for one real solution, or a two-element array for two real
     *         solutions; a linear equation returns its single solution
     * @throws IllegalArgumentException when a, b, and c are all zero, because
     *         every real number is then a solution and the result is not finite
     */
    public static double[] solve(double a, double b, double c) {
        if (a == 0.0) {
            if (b == 0.0) {
                if (c == 0.0) {
                    throw new IllegalArgumentException(
                            "The equation 0 = 0 has infinitely many solutions.");
                }
                return new double[0];
            }
            return new double[] { -c / b };
        }

        double discriminant = b * b - 4.0 * a * c;
        if (discriminant < 0.0) {
            return new double[0];
        }
        if (discriminant == 0.0) {
            return new double[] { -b / (2.0 * a) };
        }

        double sqrtDiscriminant = Math.sqrt(discriminant);
        double q = -0.5 * (b + Math.copySign(sqrtDiscriminant, b));
        double root1 = q / a;
        double root2 = c / q;
        return root1 <= root2
                ? new double[] { root1, root2 }
                : new double[] { root2, root1 };
    }
}
