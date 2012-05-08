package com.mtihc.minecraft.core1;

import com.mtihc.minecraft.core1.exceptions.ArgumentFormatException;
import com.mtihc.minecraft.core1.exceptions.ArgumentIndexException;


public class ArgumentIterator {

	private String[] args;
	private int index;

	/**
	 * Constructor
	 * @param args The arguments to iterate over
	 */
	public ArgumentIterator(String[] args) {
		this.args = args;
		this.index = -1;
	}
	
	/**
	 * Reset this iterator. So method <code>next()</code> returns the first argument.
	 */
	public void beforeFist() {
		index = -1;
	}
	
	public void back() {
		index -= 1;
		if(index < 0) {
			index = -1;
		}
	}
	
	public int getIndex() {
		return index;
	}
	
	public int getSize() {
		return args.length;
	}
	
	/**
	 * Returns whether this iterator has more elements
	 * @return true if the next element exists
	 */
	public boolean hasNext() {
		if (args == null || args.length == 0) {
			return false;
		}
		int nextIndex = index + 1;
		if (nextIndex < 0 || nextIndex > args.length - 1) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Try to get the next argument
	 * @return The next agument as String
	 * @throws ArgumentIndexException When there is no next argument
	 */
	public String next() throws ArgumentIndexException {
		try {
			// firt try to get result with next index
			String result = args[index + 1];
			// if it didn't throw exception, increment index
			index++;
			return result;
		} catch (NullPointerException e) {
		} catch (IndexOutOfBoundsException e) {
		}
		throw new ArgumentIndexException("Reached the end of the list of arguments.");
	}
	
	/**
	 * Try to get the next argument as Double.
	 * @return The next argument as Double
	 * @throws ArgumentIndexException When there is no next argument
	 * @throws ArgumentFormatException When the argument String could not be parsed to a number
	 */
	public double nextDouble() throws ArgumentIndexException, ArgumentFormatException {
		double result;
		try {
			result = Double.parseDouble(next());
		} catch (NumberFormatException e) {
			index--;
			throw new ArgumentFormatException(
					"Incorrect argument format. Expected a number.", e);
		}
		return result;
	}

	/**
	 * Try to get the next argument as Integer
	 * @return The next argument as Integer
	 * @throws ArgumentIndexException When there is no next argument
	 * @throws ArgumentFormatException The argument String could not be parsed to a number
	 */
	public int nextInt() throws ArgumentIndexException, ArgumentFormatException {
		double next = nextDouble();
		int result = (int) next;
		return result;
	}

	
	public String nextMessage() throws ArgumentIndexException {
		String result = next();
		while(hasNext()) {
			result += " " + next();
		}
		return result;
	}
}
