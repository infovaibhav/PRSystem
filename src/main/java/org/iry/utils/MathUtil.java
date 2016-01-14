package org.iry.utils;

import java.text.DecimalFormat;

/**
 * This class is use to perform mathematical operations.
 * 
 * @author vpatil
 */
public final class MathUtil {

	private static final DecimalFormat FORMATTER = new DecimalFormat("##0.0######");

	private static final DecimalFormat SHORT_FORMATTER = new DecimalFormat("##0.##");

	private static final DecimalFormat K_FORMATTER = new DecimalFormat("#,###.#");

	private static final DecimalFormat K_FORMATTER_WITHOUT_DECIMAL = new DecimalFormat("#,###");

	/**
	 * API to round and absolute the double value.
	 * 
	 * @param value - value to round.
	 * @return - rounded and absolute value.
	 */
	public static Long roundAndAbs(final Double value) {
		return abs(round(value));
	}

	/**
	 * API to round the double value.
	 * 
	 * @param value - value to round.
	 * @return - rounded value.
	 */
	public static Long round(final Double value) {
		Long returnValue = 0L;
		if (value != null) {
			returnValue = Math.round(value);
		}
		return returnValue;
	}

	/**
	 * API to round the double value.
	 * 
	 * @param value - value to round.
	 * @return - rounded value.
	 */
	public static Double parseDoubleString(final String value) {
		if (value == null || value.isEmpty()) {
			return 0d;
		}
		Double returnValue = 0d;

		if (value.endsWith("k")) {
			returnValue = Double.valueOf(value.replace("k", "")) * 1000;
		} else if (value.endsWith("m")) {
			returnValue = Double.valueOf(value.replace("m", "")) * 1000000;
		} else {
			returnValue = Double.valueOf(value);
		}
		return returnValue;
	}

	/**
	 * API to round the double value.
	 * 
	 * @param value - value to round.
	 * @return - rounded value.
	 */
	public static String roundAsString(final Double value) {
		if (value.equals(Double.NaN)) {
			return "$0";
		}
		
		String returnValue = "0";
		if (value != null) {
			if (Math.abs(value) < 1000) {
				returnValue = String.valueOf(Math.round(value));
			} else if (Math.abs(value) >= 1000 && Math.abs(value) < 1000000) {
				Double tval = Double.valueOf(Math.round(value / 100));
				returnValue = String.valueOf(K_FORMATTER.format(tval / 10)) + "k";
				if (returnValue.indexOf('.') > 2) {
					returnValue = String.valueOf(K_FORMATTER_WITHOUT_DECIMAL.format(tval / 10)) + "k";
				}
			} else {
				Double tval = Double.valueOf(Math.round(value / 100000));
				returnValue = String.valueOf(K_FORMATTER.format(tval / 10)) + "m";
				if (returnValue.indexOf('.') > 2) {
					returnValue = String.valueOf(K_FORMATTER_WITHOUT_DECIMAL.format(tval / 10)) + "m";
				}
			}
		}
		if (returnValue.startsWith("-")) {
			returnValue = returnValue.replace('-', '+');
		}
		return "$" + returnValue;
	}

	/**
	 * API to round the double value.
	 * 
	 * @param value - value to round.
	 * @return - rounded value.
	 */
	public static String roundAsStringWithoutSign(final Double value) {
		if (value.equals(Double.NaN)) {
			return "0";
		}
		String returnValue = "0";
		if (value != null) {
			if (Math.abs(value) < 1000) {
				returnValue = String.valueOf(Math.round(value));
			} else if (Math.abs(value) >= 1000 && Math.abs(value) < 1000000) {
				Double tval = Double.valueOf(Math.round(value / 100));
				returnValue = String.valueOf(K_FORMATTER.format(tval / 10)) + "k";
				if (returnValue.indexOf('.') > 2) {
					returnValue = String.valueOf(K_FORMATTER_WITHOUT_DECIMAL.format(tval / 10)) + "k";
				}
			} else {
				Double tval = Double.valueOf(Math.round(value / 100000));
				returnValue = String.valueOf(K_FORMATTER.format(tval / 10)) + "m";
				if (returnValue.indexOf('.') > 2) {
					returnValue = String.valueOf(K_FORMATTER_WITHOUT_DECIMAL.format(tval / 10)) + "m";
				}
			}
		}
		if (returnValue.startsWith("-")) {
			returnValue = returnValue.replace('-', ' ');
		}
		return returnValue.trim();
	}

	/**
	 * API to round the double value.
	 * 
	 * @param value - value to round.
	 * @return - rounded value.
	 */
	public static String costWithCurrency(final Double value) {
		return "$" + Double.valueOf(SHORT_FORMATTER.format(value));
	}

	/**
	 * API to round the double value.
	 * 
	 * @param value - value to round.
	 * @return - rounded value.
	 */
	public static String roundAsString(final Long value) {
		String returnValue = "0";
		if (value != null) {
			if (Math.abs(value) < 1000) {
				returnValue = String.valueOf(Math.round(value));
			} else if (Math.abs(value) >= 1000 && Math.abs(value) < 1000000) {
				Double tval = Double.valueOf(Math.round(value / 100));
				returnValue = String.valueOf(K_FORMATTER.format(tval / 10)) + "k";
				if (returnValue.indexOf('.') > 2) {
					returnValue = String.valueOf(K_FORMATTER_WITHOUT_DECIMAL.format(tval / 10)) + "k";
				}
			} else {
				Double tval = Double.valueOf(Math.round(value / 100000));
				returnValue = String.valueOf(K_FORMATTER.format(tval / 10)) + "m";
				if (returnValue.indexOf('.') > 2) {
					returnValue = String.valueOf(K_FORMATTER_WITHOUT_DECIMAL.format(tval / 10)) + "m";
				}
			}
		}
		if (returnValue.startsWith("-")) {
			returnValue = returnValue.replace('-', '+');
		}
		return returnValue;
	}

	/**
	 * API to round the double value.
	 * 
	 * @param value - value to round.
	 * @return - rounded value.
	 */
	public static String roundAsStringWithoutSign(final Long value) {
		String returnValue = "0";
		if (value != null) {
			if (Math.abs(value) < 1000) {
				returnValue = String.valueOf(Math.round(value));
			} else if (Math.abs(value) >= 1000 && Math.abs(value) < 1000000) {
				Double tval = Double.valueOf(Math.round(value / 100));
				returnValue = String.valueOf(K_FORMATTER.format(tval / 10)) + "k";
				if (returnValue.indexOf('.') > 2) {
					returnValue = String.valueOf(K_FORMATTER_WITHOUT_DECIMAL.format(tval / 10)) + "k";
				}
			} else {
				Double tval = Double.valueOf(Math.round(value / 100000));
				returnValue = String.valueOf(K_FORMATTER.format(tval / 10)) + "m";
				if (returnValue.indexOf('.') > 2) {
					returnValue = String.valueOf(K_FORMATTER_WITHOUT_DECIMAL.format(tval / 10)) + "m";
				}
			}
		}
		if (returnValue.startsWith("-")) {
			returnValue = returnValue.replace('-', ' ');
		}
		return returnValue.trim();
	}

	/**
	 * API to round the double value.
	 * 
	 * @param value - value to round.
	 * @return - rounded value.
	 */
	public static Long round(final Float value) {
		Long returnValue = 0L;
		if (value != null) {
			returnValue = (long) Math.round(value);
		}
		return returnValue;
	}

	/**
	 * API to returns the absolute value of a <code>long</code> value.
	 * 
	 * @param value - value.
	 * @return - absolute value.
	 */
	public static Long abs(final Long value) {
		return Math.abs(value);
	}

	/**
	 * API to abs the double value.
	 * 
	 * @param value - value to abs.
	 * @return - abs value.
	 */
	public static Double abs(final Double value) {
		return Math.abs(value);
	}

	/**
	 * API to abs the double value.
	 * 
	 * @param value - value to abs.
	 * @return - abs value.
	 */
	public static Double getFormattedDouble(final Double value) {
		return Double.valueOf(FORMATTER.format(value));
	}

	public static Double getShortFormattedDouble(final Double value) {
		return Double.valueOf(SHORT_FORMATTER.format(value));
	}
}